package com.xuexi.settings.service;

import com.xuexi.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User queryUserByLoginActAndLoginPwd(Map<String,Object> map);

    List<User> QueryAllUser();
}
