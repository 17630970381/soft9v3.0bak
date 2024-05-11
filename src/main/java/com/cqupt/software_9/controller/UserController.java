package com.cqupt.software_9.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.cqupt.software_9.vo.UpdateStatusVo;
import com.cqupt.software_9.vo.UserPwd;
import com.cqupt.software_9.vo.VerifyUserQ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        Integer role = user.getRole();
        User existUser = userService.getOne(queryWrapper);

        if (existUser != null){
            return new R<>(500,"用户已经存在",null);
        }

        String pwd = user.getPassword();

        // 对密码进行加密处理

        String password = SecurityUtil.hashDataSHA256(pwd);

        user.setPassword(password);
        user.setRole(role);
        user.setCreateTime(new Date());
        user.setUpdateTime(null);
        user.setUserStatus("0");
        System.out.println(user);
        userService.save(user);


        //  操作日志记录

        UserLog userLog = new UserLog();

        QueryWrapper queryWrapper1  = new QueryWrapper<>();
        queryWrapper1.eq("username",user.getUsername());

        User one = userService.getOne(queryWrapper1);
        String uid = one.getUid();

        // userLog.setId(1);
        userLog.setUid(uid);
        userLog.setOpTime(new Date());
        userLog.setOpType("用户注册");
        userLog.setRole(1);
        userLogService.save(userLog);

        return new R<>(200,"成功",null);

    }
//
    @PostMapping("/login")
    public R login(@RequestBody AdminLoginParam adminLoginParam, HttpServletResponse response, HttpServletRequest request){

        String code = adminLoginParam.getCode();
        String captcha = (String)request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code)){
            return new R<>(500,"验证码不能为空",null);
        }
        if(!captcha.equalsIgnoreCase(code)){
            return new R<>(500,"验证码输入错误，请重新输入",null);
        }
        String userName = adminLoginParam.getUsername();

        System.out.println(userName+"1");

        QueryWrapper queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("username",adminLoginParam.getUsername());
        System.out.println(queryWrapper+"3");
        User getUser = userService.getOne(queryWrapper);
        System.out.println(getUser+"2");

        if (getUser != null){
            String password = getUser.getPassword();
            // 进行验证密码
            String pwd = adminLoginParam.getPassword();
            String sha256 = SecurityUtil.hashDataSHA256(pwd);
            if (sha256.equals(password)){
                // 验证成功
                UserLog userLog = new UserLog();
                userLog.setUid(getUser.getUid());
                userLog.setOpTime(new Date());
                userLog.setOpType("登录系统");
                userLog.setUsername(userName);
                userLogService.save(userLog);
                // session认证
                HttpSession session = request.getSession();
                session.setAttribute("username",adminLoginParam.getUsername());
                session.setAttribute("userId",getUser.getUid());

                String uid = getUser.getUid().toString();
                Cookie cookie = new Cookie("userId",uid );
                response.addCookie(cookie);

                //封装user对象返回
                User user1 = new User();
                user1.setUid(getUser.getUid());
                user1.setUsername(getUser.getUsername());
                user1.setRole(getUser.getRole());
                user1.setUserStatus(getUser.getUserStatus());

                return new R<>(200,"登录成功",user1);
            }else {
                return new R<>(500,"密码错误请重新输入",null);
            }

        }else {
            return new R<>(500,"用户不存在",null);
        }
    }
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
//    @PostMapping("/login")
//    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
//        return userService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
//
//    }

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
     * 加日志
     */
    @PostMapping("/logout")
    public RespBean logout(){

        return RespBean.success("注销成功！");
    }

    @GetMapping("/getUid/{username}")
    public String getUid(@PathVariable("username") String username){
        return userMapper.getUid(username);
    }
    /**
     * 获取用户所有信息
     */
    //个人中心开始
    @GetMapping("/getmessage/{uid}")
    public Result<List<User>> getall(@PathVariable("uid") String uid){
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
            String uid = one.getUid();
//            userLog.setUsername(username);
            // userLog.setId(1);
//            userLog.setUid(uid);
//            userLog.setOpTime(new Date());


            if (updatedRows > 0) {
                userLog.setOpType("用户修改密码成功");

//                userLogService.save(userLog);
                userLogService.insertLog(uid,0,userLog.getOpType());
                // 更新成功，返回成功结果
                return Result.success(200, "更新成功");
            } else {
                userLog.setOpType("用户修改密码失败");

                userLogService.save(userLog);
                // 更新失败，没有记录被更新
                return Result.success(404, "更新失败，用户不存在或密码未更改");
            }
        } catch (Exception e) {
            String username = requests.get("username");
            UserLog userLog = new UserLog();
            QueryWrapper queryWrapper1  = new QueryWrapper<>();
            queryWrapper1.eq("username",username);
            User one = userService.getOne(queryWrapper1);
            String uid = one.getUid();
            // userLog.setId(1);
            userLog.setUid(uid);
            userLog.setOpTime(new Date());
            userLog.setOpType("用户修改密码失败，发生未知错误");
            userLogService.save(userLog);
            // 处理可能出现的任何异常，例如数据库连接失败等
            // 记录异常信息，根据实际情况决定是否需要发送错误日志
            // 这里返回一个通用的错误信息
            return Result.success(500, "更新失败，发生未知错误");
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

    /**
     *  检查用户名是否重复
     * @param username
     * @return
     */
    @GetMapping("/checkRepetition/{username}")
    public Result checkRepetition(@PathVariable("username") String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        if(user != null){
            return Result.success(200, "用户名已存在");
        }else {
            return Result.success(200, "用户名可用");
        }
    }
    //个人中心结束


    //新增  yx
    // 判断用户名是否可用
    @GetMapping("/querUserNameExist")
    public R querUserNameExist(@RequestParam String userName){
        User existUser = userService.getUserByUserName(userName);
        if (existUser != null){
            return new R<>(500,"用户已经存在",null);
        }
        return new R(200, "用户名可用" , null);
    }
    //新注册
    @PostMapping("/signUp1")
    public R signUp1(@RequestBody User user) throws ParseException {

        System.out.println("321123132"+user);
        // 检查用户名是否已经存在
        user.setUid("0");
        User existUser = userService.getUserByUserName(user.getUsername());
        if (existUser != null){
            return new R<>(500,"用户已经存在",null);
        }
        String pwd = user.getPassword();
        // 对密码进行加密处理
        String password = SecurityUtil.hashDataSHA256(pwd);
        user.setPassword(password);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = inputFormat.parse(date);
        user.setCreateTime(date1);
        user.setUpdateTime(null);
        user.setRole(1);
        user.setUid( String.valueOf(new Random().nextInt()) );
        user.setUploadSize(200);
        user.setAllSize(200);
        userService.save(user);
//          操作日志记录
        UserLog userLog = new UserLog();
        User one = userService.getUserByUserName(user.getUsername());
        String uid = one.getUid();
//       userLog.setId(new Random().nextInt());
        userLog.setUid(uid);
        Date OpTime = inputFormat.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        userLog.setOpTime(OpTime);
        userLog.setOpType("用户注册");
        userLogService.save(userLog);
        return new R<>(200,"成功",null);
    }

    @PostMapping("/login1")
    public Result login1(@RequestBody User user, HttpServletResponse response, HttpServletRequest request){

        // 判断验证编码
        String code = request.getSession().getAttribute("code").toString();
        if(code==null) return Result.fail(500,"验证码已过期！");
        if(user.getCode()==null || !user.getCode().equals(code)) {
            return Result.fail(500, "验证码错误!");
        }

        String userName = user.getUsername();
        User getUser = userService.getUserByUserName(userName);
        String password = getUser.getPassword();
        if (getUser != null){
            // 用户状态校验
            // 判断用户是否激活
            if (getUser.getUserStatus().equals("0")){
                return Result.fail("该账户未激活");
            }
            if (getUser.getUserStatus().equals("2")){
                return Result.fail("该账户已经被禁用");
            }

            String userStatus = getUser.getUserStatus();
            if(userStatus.equals("0")){ // 待激活
                return Result.fail(500,"账户未激活！");
            }else if(userStatus.equals("2")){
                return Result.fail(500,"用户已被禁用!");
            }

            // 进行验证密码
            String pwd = user.getPassword();
            String sha256 = SecurityUtil.hashDataSHA256(pwd);
            if (sha256.equals(password)){
                // 验证成功
                UserLog userLog = new UserLog();
                userLog.setUid(getUser.getUid());
                Date opTime = new Date();
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(opTime);
                userLog.setOpTime(opTime);
                userLog.setOpType("登录系统");
                userLog.setUsername(userName);
                System.out.println("userlog:"+userLog);
                userLogService.save(userLog);
                // session认证
                HttpSession session = request.getSession();
                session.setAttribute("username",user.getUsername());
                session.setAttribute("userId",getUser.getUid());
                return Result.success(200, "登录成功", getUser);
            }else {
                return Result.success(500,"密码错误请重新输入",null);
            }
        }else {
            return Result.success(500,"用户不存在",null);
        }
    }

    @GetMapping("/info1")
    public User getUserInfo1(Principal principal){
        if(null == principal){
            return null;
        }
        String username = principal.getName();
        User user = userService.getUserByUserName(username);
        user.setPassword(null);
        return user;

    }
    @PostMapping("/newlogout")
    public R logout(HttpServletRequest request){

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        session.invalidate();
        return new R<>(200,"退出成功",null);
    }

    /**
     * 管理员中心查看得所有用户信息
     *
     * @return
     */
    @GetMapping("/allUser")
    public Map<String, Object> allUser(@RequestParam(defaultValue = "1") int pageNum,
                                       @RequestParam(defaultValue = "10") int pageSize){

        return userService.getUserPage(pageNum, pageSize);

    }

    //新方法
    @GetMapping("/querUser")
    public List<User> querUser(){

        return userService.querUser();

    }
    /**
     *
     *  管理员修改用户状态
     * @return
     */
    @PostMapping("updateStatus")
    public Result  updateStatus(@RequestBody UpdateStatusVo updateStatusVo){
        // 根据 id  修改用户状态   角色
        boolean b = userService.updateStatusById(updateStatusVo.getUid() ,updateStatusVo.getRole(),updateStatusVo.getAllSize(), updateStatusVo.getStatus(), updateStatusVo.getUserid());
        if (b) return  Result.success(200 , "修改用户状态成功");
        return  Result.fail("修改失败");
    }
    //新方法
    @PostMapping("delUser")
    public Result delUser(@RequestBody UpdateStatusVo updateStatusVo){

        String uid = updateStatusVo.getUid();
        String userid = updateStatusVo.getUserid();
        boolean b = userService.removeUserById(uid,userid);
        if (b) return Result.success(200 , "删除成功");
        return Result.fail(200 , "删除失败");
    }
    // 忘记密码功能
    @GetMapping("/queryQuestions")
    public R  forgotPwd(@RequestParam String username){
        User user = userService.getUserByUserName(username);
        String answer1 = user.getAnswer_1().split(":")[0];
        String answer2 = user.getAnswer_2().split(":")[0];
        String answer3 = user.getAnswer_3().split(":")[0];
        List<String> answers = new ArrayList<>();
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        return new R<>(200, "查询用户密保问题成功",answers );
    }


    // 验证问题


    @PostMapping("/verify")
    public Result verify(@RequestBody VerifyUserQ verifyUserQ){
        // 用户名   密保问题 和 答案
        QueryWrapper queryWrapper = new QueryWrapper<>()
                .eq("username",verifyUserQ.getUsername())
                .eq("answer_1" , verifyUserQ.getQ1()).eq("answer_2" , verifyUserQ.getQ2()).eq("answer_3" , verifyUserQ.getQ3());
        User user = userService.getOne(queryWrapper);

        if (user == null){
            return Result.fail("验证失败");
        }else {
            return Result.success(200 ," 验证成功，请重置密码");
        }

    }


    @PostMapping("updatePwd")
    public Result  updatePwd(@RequestBody UserPwd user){
        String password = user.getPassword();
        String sha256 = SecurityUtil.hashDataSHA256(password);
        user.setPassword(sha256);
        System.out.println(user);
        userService.updatePwd(user);
        return Result.success(200 , "修改密码成功");
    }

    //新增结束


    @GetMapping("/getAllUserByPage")
    public Result getAllUserByPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam Integer role,
                                   @RequestParam String searchUser,
                                   @RequestParam String userStatus){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(userStatus),"user_status",userStatus);
        wrapper.like(StringUtils.isNotBlank(searchUser),"username",searchUser);
        if(role != null){
            wrapper.eq("role",role);
        }
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(page);
    }

    @GetMapping("/getTransferUserList")
    public Result getTransferUserList(@RequestParam("uid") String uid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("uid", uid);
        List<User> userList = userMapper.selectList(queryWrapper);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (User user : userList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("key", user.getUid());
            resultMap.put("label", user.getUsername());
            resultList.add(resultMap);
        }
        return  Result.success(200,"获得成功",resultList);
    }

}
