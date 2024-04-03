package com.cqupt.software_9.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqupt.software_9.common.R;
import com.cqupt.software_9.entity.AdminLoginParam;
import com.cqupt.software_9.entity.RespBean;
import com.cqupt.software_9.entity.User;
import com.cqupt.software_9.entity.UserLog;
import com.cqupt.software_9.mapper.UserMapper;
import com.cqupt.software_9.service.UserLogService;
import com.cqupt.software_9.service.UserService;
import com.cqupt.software_9.tool.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

/**
 * 用户登录
 */
@RequestMapping("/user")
@RestController
public class UserController {

//    @Autowired
//    private UserService userService;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserLogService userLogService;
//
////    public UserController(UserService userService,UserLogService userLogService){
////        this.userService=userService;
////        this
////
////    }
//
    @PostMapping("/signUp")
    public R signUp(@RequestBody User user) {

        // 检查用户名是否已经存在
//        user.setUid(0);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());

        User existUser = userService.getOne(queryWrapper);

        if (existUser != null){
            return new R<>(500,"用户已经存在",null);
        }

        String pwd = user.getPassword();

        // 对密码进行加密处理

        String password = SecurityUtil.hashDataSHA256(pwd);

        user.setPassword(password);
        user.setCreateTime(new Date());
        user.setUpdateTime(null);
        System.out.println(user);
        userService.save(user);


        //  操作日志记录

        UserLog userLog = new UserLog();

        QueryWrapper queryWrapper1  = new QueryWrapper<>();
        queryWrapper1.eq("username",user.getUsername());

        User one = userService.getOne(queryWrapper1);
        Integer uid = one.getUid();

        // userLog.setId(1);
        userLog.setUid(uid);
        userLog.setOpTime(new Date());
        userLog.setOpType("用户注册");

        userLogService.save(userLog);

        return new R<>(200,"成功",null);

    }
//
//    @PostMapping("/login")
//    public R login(@RequestBody User user, HttpServletResponse response, HttpServletRequest request){
//
//        String userName = user.getUsername();
//
//        System.out.println(userName+"1");
//
//        QueryWrapper queryWrapper = new QueryWrapper<>();
//
//        queryWrapper.eq("username",user.getUsername());
//        System.out.println(queryWrapper+"3");
//        User getUser = userService.getOne(queryWrapper);
//        System.out.println(getUser+"2");
//
//        if (getUser != null){
//            String password = getUser.getPassword();
//            // 进行验证密码
//            String pwd = user.getPassword();
//            String sha256 = SecurityUtil.hashDataSHA256(pwd);
//            if (sha256.equals(password)){
//                // 验证成功
//                UserLog userLog = new UserLog();
//                userLog.setUid(getUser.getUid());
//                userLog.setOpTime(new Date());
//                userLog.setOpType("登录系统");
//                userLog.setUsername(userName);
//                userLogService.save(userLog);
//                // session认证
//                HttpSession session = request.getSession();
//                session.setAttribute("username",user.getUsername());
//                session.setAttribute("userId",getUser.getUid());
//
//                String uid = getUser.getUid().toString();
//                Cookie cookie = new Cookie("userId",uid );
//                response.addCookie(cookie);
//
//                //封装user对象返回
//                User user1 = new User();
//                user1.setUid(getUser.getUid());
//                user1.setUsername(getUser.getUsername());
//
//                return new R<>(200,"登录成功",user1);
//            }else {
//                return new R<>(500,"密码错误请重新输入",null);
//            }
//
//        }else {
//            return new R<>(500,"用户不存在",null);
//        }
//    }
//
//
//    @PostMapping("/logout")
//    public R logout(HttpServletRequest request){
//
//        HttpSession session = request.getSession();
//        Integer userId = (Integer) session.getAttribute("userId");
//        session.invalidate();
//        return new R<>(200,"退出成功",null);
//    }

    /**
     * 登录之后返回token
     */
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return userService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);

    }

    @GetMapping("/info")
    public User getUserInfo(Principal principal){
        if(null == principal){
            return null;
        }
        String username = principal.getName();
        User user = userService.getUserByUserName(username);
        user.setPassword(null);
        return user;

    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功！");
    }

    @GetMapping("/getUid/{username}")
    public Integer getUid(@PathVariable("username") String username){
        return userMapper.getUid(username);
    }
}
