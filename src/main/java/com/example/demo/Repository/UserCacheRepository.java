package com.example.demo.Repository;

import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class UserCacheRepository {

    private final String USER_KEY_PREFIX = "usr::";

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //To store the data by setting the key and value in the redis where key is based
    //on username and value is the user Object
    public void set(User user){
        String key = getKey(user.getUsername());
        redisTemplate.opsForValue().set(key,user,24, TimeUnit.HOURS);
    }

    //To get the User object based on the key from the redis
    public User get(String username){
        String key = getKey(username);
        return (User) redisTemplate.opsForValue().get(key);
    }

    //To create the key using prefix
    public String getKey(String username){
        return USER_KEY_PREFIX+username;
    }
}
