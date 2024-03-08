package com.jycoding.login.model;


import com.jycoding.login.dto.JoinReqDto;
import com.jycoding.login.dto.LoginReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {
    public int insert(JoinReqDto joinReqDto);

    public int updateById(User user);
    public int deleteById(int id);
    public List<User> findAll();
    public User findById(int id);

    public User findByUsernameAndPassword(LoginReqDto loginReqDto);

    public User findByUsername(String username);
}
