package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("select * from credentials where credentials.credentialid = #{credentialid}")
    public Credentials findById(int credentialid);

    @Select("select * from credentials where credentials.userid = #{userid}")
    public List<Credentials> findAllByUserid(int userid);

    @Insert("insert into credentials(url,username, key, password, userid ) VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    public int addCredential(Credentials credentials);

    @Update("UPDATE credentials SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialid}")
    public int update(Credentials credential);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialid}")
    public int delete(int credentialid);
}
