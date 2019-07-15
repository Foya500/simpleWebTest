package com.foya.dao;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.foya.entity.UserEntity;


public interface  UserDao extends CrudRepository<UserEntity, String>  {

    List<UserEntity> findByEmail(String email);

    UserEntity findByUserId(String userId);

    List<UserEntity> findByCreateDate(Date createDate);

    // custom query example and return a stream
    @Query("select c from UserEntity c where c.name like :name")
    Stream<UserEntity> findLikeUserName(@Param("name") String name);

}
