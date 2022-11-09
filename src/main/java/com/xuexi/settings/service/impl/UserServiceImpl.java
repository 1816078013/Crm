package com.xuexi.settings.service.impl;

import com.xuexi.settings.domain.User;
import com.xuexi.settings.mapper.UserMapper;
import com.xuexi.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByLoginActAndLoginPwd(Map<String,Object> map) {
       User user = userMapper.SelectByLoginActAndLoginPwd(map);
       return  user;
    }

    @Override
    public List<User> QueryAllUser() {
        List<User> userList = userMapper.SelectAllUser();
        return  userList;
    }


}
