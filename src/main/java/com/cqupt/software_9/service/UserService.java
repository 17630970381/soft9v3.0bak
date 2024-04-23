package com.cqupt.software_9.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqupt.software_9.entity.RespBean;
import com.cqupt.software_9.entity.User;
import com.cqupt.software_9.vo.UserPwd;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password,String code, HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    User getUserByUserName(String username);

    void addTableSize(String uid, float tableSize);
    void minusTableSize(String uid, float tableSize);

    //新加的
    void saveUser(User user);
    Map<String, Object> getUserPage(int pageNum, int pageSize);
    List<User> querUser();
    boolean updateStatusById(String uid, Integer role ,double uploadSize, String status,String userid);
    boolean removeUserById(String uid,String userid);
    boolean updatePwd(UserPwd user);
}
