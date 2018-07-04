package com.bdqn.service.impl;

import org.springframework.stereotype.Service;

import com.bdqn.entity.User;
import com.bdqn.service.RedisService;
@Service("userRedisService")
public class UserRedisServiceImpl extends RedisService<User>{
	
	//自定义redis key作为Hash表的key名称
    private static final String REDIS_KEY = "USER_KEY";

    @Override
    protected String getRedisKey() {
        // TODO Auto-generated method stub
        return REDIS_KEY;
    }

}
