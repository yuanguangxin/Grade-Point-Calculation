package edu.hlju.csti.web.sq.dao.mapper;

import edu.hlju.csti.web.sq.model.UserKey;
import edu.hlju.csti.web.sq.model.UserKeyExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserKeyMapper {
    @SelectProvider(type = UserKeySqlProvider.class, method = "countByExample")
    int countByExample(UserKeyExample example);

    @DeleteProvider(type = UserKeySqlProvider.class, method = "deleteByExample")
    int deleteByExample(UserKeyExample example);

    @Delete({
            "delete from user_key",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into user_key (id, school_num, ",
            "public_key, private_key, ",
            "login_cookie, update_date)",
            "values (#{id,jdbcType=BIGINT}, #{schoolNum,jdbcType=VARCHAR}, ",
            "#{publicKey,jdbcType=VARCHAR}, #{privateKey,jdbcType=VARCHAR}, ",
            "#{loginCookie,jdbcType=VARCHAR}, #{updateDate,jdbcType=TIMESTAMP})"
    })
    int insert(UserKey record);

    @InsertProvider(type = UserKeySqlProvider.class, method = "insertSelective")
    int insertSelective(UserKey record);

    @SelectProvider(type = UserKeySqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "school_num", property = "schoolNum", jdbcType = JdbcType.VARCHAR),
            @Result(column = "public_key", property = "publicKey", jdbcType = JdbcType.VARCHAR),
            @Result(column = "private_key", property = "privateKey", jdbcType = JdbcType.VARCHAR),
            @Result(column = "login_cookie", property = "loginCookie", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_date", property = "updateDate", jdbcType = JdbcType.TIMESTAMP)
    })
    List<UserKey> selectByExample(UserKeyExample example);

    @Select({
            "select",
            "id, school_num, public_key, private_key, login_cookie, update_date",
            "from user_key",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "school_num", property = "schoolNum", jdbcType = JdbcType.VARCHAR),
            @Result(column = "public_key", property = "publicKey", jdbcType = JdbcType.VARCHAR),
            @Result(column = "private_key", property = "privateKey", jdbcType = JdbcType.VARCHAR),
            @Result(column = "login_cookie", property = "loginCookie", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_date", property = "updateDate", jdbcType = JdbcType.TIMESTAMP)
    })
    UserKey selectByPrimaryKey(Long id);

    @UpdateProvider(type = UserKeySqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UserKey record, @Param("example") UserKeyExample example);

    @UpdateProvider(type = UserKeySqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") UserKey record, @Param("example") UserKeyExample example);

    @UpdateProvider(type = UserKeySqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserKey record);

    @Update({
            "update user_key",
            "set school_num = #{schoolNum,jdbcType=VARCHAR},",
            "public_key = #{publicKey,jdbcType=VARCHAR},",
            "private_key = #{privateKey,jdbcType=VARCHAR},",
            "login_cookie = #{loginCookie,jdbcType=VARCHAR},",
            "update_date = #{updateDate,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserKey record);

    @Select({
            "select * from user_key",
            "where school_num = #{schoolNum,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "school_num", property = "schoolNum", jdbcType = JdbcType.VARCHAR),
            @Result(column = "public_key", property = "publicKey", jdbcType = JdbcType.VARCHAR),
            @Result(column = "private_key", property = "privateKey", jdbcType = JdbcType.VARCHAR),
            @Result(column = "login_cookie", property = "loginCookie", jdbcType = JdbcType.VARCHAR),
            @Result(column = "update_date", property = "updateDate", jdbcType = JdbcType.TIMESTAMP)
    })
    public UserKey selectBySchoolNum(@Param("schoolNum") String schoolNum);
}