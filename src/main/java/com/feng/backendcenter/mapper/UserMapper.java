package com.feng.backendcenter.mapper;

import com.feng.backendcenter.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 17247
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-09-29 00:38:00
* @Entity com.feng.backendcenter.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




