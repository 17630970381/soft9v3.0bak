package com.cqupt.software_9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqupt.software_9.entity.User;
import com.cqupt.software_9.vo.UserPwd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    String getUid(String username);

    List<User> getall();

    Integer updateByname(String newpassword,String username);

    String getUidByUsername(String username);

    User selectByUid(String uid);
    void addTableSize(String uid, float tableSize);
    void minusTableSize(String uid, float tableSize);

    //yx 新增
    //用户管理新增
    void saveUser(User user);
    List<User> selectUserPage(int offset, int pageSize);
    int countUsers();
    boolean updateStatusById(String uid, Integer role, double allSize, String status, double uploadSize);
    void removeUserById(String uid);

    void updatePwd(UserPwd user);

    //ssq
    @Update("UPDATE \"user\" SET upload_size = upload_size-#{size} WHERE uid = #{id}")
    int decUpdateUserColumnById(@Param("id") String id, @Param("size") Double size);

    @Update("UPDATE \"user\" SET upload_size = upload_size+#{size} WHERE uid = #{id}")
    int recoveryUpdateUserColumnById(@Param("id") String id, @Param("size") Double size);

}
