package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 微信登录
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 自动注册
     * @param user
     */
    void insert(User user);

    /**
     * 根据id查询
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(Long userId);

    /**
     * 根据动态条件统计用户数据
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
