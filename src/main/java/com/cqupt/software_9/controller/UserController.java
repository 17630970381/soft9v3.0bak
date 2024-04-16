package com.cqupt.software_9.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqupt.software_9.common.R;
import com.cqupt.software_9.common.Result;
import com.cqupt.software_9.entity.AdminLoginParam;
import com.cqupt.software_9.entity.RespBean;
import com.cqupt.software_9.entity.User;
import com.cqupt.software_9.entity.UserLog;
import com.cqupt.software_9.mapper.UserMapper;
import com.cqupt.software_9.service.UserLogService;
import com.cqupt.software_9.service.UserService;
import com.cqupt.software_9.tool.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserDetailsService userDetailsService;
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
    /**
     * 获取用户所有信息
     */
    @GetMapping("/getmessage/{uid}")
    public Result<List<User>> getall(@PathVariable("uid") Integer uid){
        User user = userMapper.selectById(uid);
        user.setPassword(null);
        return Result.success("200",user);
    }

    //修改密码，根据用户名匹配密码是否正确
    @PostMapping("/VerifyPas")
    public Result VerifyPas(@RequestBody Map<String, String> request){
        String username = request.get("username");
        String password = request.get("password");
        System.out.println(username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(!password.equals(userDetails.getPassword())){
            return Result.success(200,"密码不匹配",false);
        }
        return Result.success(200,"密码匹配",true);
    }



    //修改密码
    @PostMapping("/updatePas")
    public Result updatePas(@RequestBody Map<String, String> requests) {
        try {
            String username = requests.get("username");
            String password = requests.get("password");
            // 假设 userMapper 是 MyBatis 的一个 Mapper 接口
            int updatedRows = userMapper.updateByname(password, username);

            //  操作日志记录

            UserLog userLog = new UserLog();

            QueryWrapper queryWrapper1  = new QueryWrapper<>();
            queryWrapper1.eq("username",username);

            User one = userService.getOne(queryWrapper1);
            Integer uid = one.getUid();
            userLog.setUsername(username);
            // userLog.setId(1);
            userLog.setUid(uid);
            userLog.setOpTime(new Date());


            if (updatedRows > 0) {
                userLog.setOpType("用户修改密码成功");

                userLogService.save(userLog);
                // 更新成功，返回成功结果
                return Result.success("200", "更新成功");
            } else {
                userLog.setOpType("用户修改密码失败");

                userLogService.save(userLog);
                // 更新失败，没有记录被更新
                return Result.success("404", "更新失败，用户不存在或密码未更改");
            }
        } catch (Exception e) {
            String username = requests.get("username");
            UserLog userLog = new UserLog();
            QueryWrapper queryWrapper1  = new QueryWrapper<>();
            queryWrapper1.eq("username",username);
            User one = userService.getOne(queryWrapper1);
            Integer uid = one.getUid();
            // userLog.setId(1);
            userLog.setUid(uid);
            userLog.setOpTime(new Date());
            userLog.setOpType("用户修改密码失败，发生未知错误");
            userLogService.save(userLog);
            // 处理可能出现的任何异常，例如数据库连接失败等
            // 记录异常信息，根据实际情况决定是否需要发送错误日志
            // 这里返回一个通用的错误信息
            return Result.success("500", "更新失败，发生未知错误");
        }
    }

    //修改个人信息
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        try {
            // 假设 userMapper 是 MyBatis 的一个 Mapper 接口

            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("uid",user.getUid());
            int updatedRows = userMapper.update(user, wrapper);
            //  操作日志记录

            UserLog userLog = new UserLog();
            // userLog.setId(1);
            userLog.setUsername(user.getUsername());
            userLog.setUid(user.getUid());
            userLog.setOpTime(new Date());

            if (updatedRows > 0) {
                // 更新成功，返回成功结果
                userLog.setOpType("用户修改个人信息成功");
                userLogService.save(userLog);

                return Result.success("200", "更新成功");
            } else {

                userLog.setOpType("用户修改个人信息失败");
                userLogService.save(userLog);
                // 更新失败，没有记录被更新
                return Result.success("404", "更新失败，用户不存在");
            }
        } catch (Exception e) {
            // 处理可能出现的任何异常，例如数据库连接失败等
            // 记录异常信息，根据实际情况决定是否需要发送错误日志
            // 这里返回一个通用的错误信息
            return Result.success("500", "更新失败，发生未知错误");
        }
    }



}
