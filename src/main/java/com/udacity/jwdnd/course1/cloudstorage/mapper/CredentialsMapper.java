package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("select * from credentials where credentials.credentialid = #{id}")
    Credentials findById(@Param("id") Integer id);

    @Select("select * from credentials where credentials.userid = #{userid}")
    List<Credentials> findAllByUserid(@Param("userid") Integer userid);

    @Insert("insert into credentials(url,username,skeleton,password, userid ) VALUES (#{credential.url}, #{credential.username},#{credential.skeleton},#{credential.password}, #{userid})")
    Integer addCredential(@Param("credential") Credentials credential, @Param("userid") Integer userid);

    @Update("UPDATE credentials SET url = #{credential.url}, username = #{credential.username}, skeleton = #{credential.skeleton}, password = #{credential.password} WHERE credentialid = #{credential.credentialid}")
    Integer update(@Param("credential") Credentials credential);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialid}")
    Integer delete(@Param("credentialid") Integer credentialid);
}
