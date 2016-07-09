package edu.hlju.csti.web.sq.mapper;

import edu.hlju.csti.web.sq.model.Student;
import edu.hlju.csti.web.sq.model.StudentExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface StudentMapper {
    @SelectProvider(type=StudentSqlProvider.class, method="countByExample")
    int countByExample(StudentExample example);

    @DeleteProvider(type=StudentSqlProvider.class, method="deleteByExample")
    int deleteByExample(StudentExample example);

    @Delete({
        "delete from student",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into student (id, username, ",
        "password)",
        "values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR})"
    })
    int insert(Student record);

    @InsertProvider(type=StudentSqlProvider.class, method="insertSelective")
    int insertSelective(Student record);

    @SelectProvider(type=StudentSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR)
    })
    List<Student> selectByExample(StudentExample example);

    @Select({
        "select",
        "id, username, password",
        "from student",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR)
    })
    Student selectByPrimaryKey(Integer id);

    @UpdateProvider(type=StudentSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    @UpdateProvider(type=StudentSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    @UpdateProvider(type=StudentSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Student record);

    @Update({
        "update student",
        "set username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Student record);
}