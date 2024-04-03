package com.cqupt.software_9.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.software_9.entity.RespBean;
import com.cqupt.software_9.entity.User;
import com.cqupt.software_9.mapper.UserMapper;
import com.cqupt.software_9.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDetailsService userDetailsService;


//    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;

    @Resource
//    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {

        String captcha = (String)request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code)){
            return RespBean.error("验证码不能为空");
        }
        if(!captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码输入错误，请重新输入");
        }
        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(null == userDetails || !password.equals(userDetails.getPassword())){
            return RespBean.error("用户名密码不正确");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("账号被禁用，请联系管理员");
        }
        //更新security登录用户对象
//        UsernamePasswordAuthenticationToken authenticationToken = new
//                UsernamePasswordAuthenticationToken(userDetails,
//                null,userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//
//        //生成token
//        String token = jwtTokenUtil.generateToken(userDetails);
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("token",token);
//        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功");
    }
    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */

    @Override
    public User getUserByUserName(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }



}
