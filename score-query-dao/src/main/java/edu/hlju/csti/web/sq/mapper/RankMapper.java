package edu.hlju.csti.web.sq.mapper;

import edu.hlju.csti.web.sq.model.Rank;
import edu.hlju.csti.web.sq.model.RankExample;
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

public interface RankMapper {
    @SelectProvider(type=RankSqlProvider.class, method="countByExample")
    int countByExample(RankExample example);

    @DeleteProvider(type=RankSqlProvider.class, method="deleteByExample")
    int deleteByExample(RankExample example);

    @Delete({
        "delete from rank",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into rank (id, stu_id, ",
        "info, point)",
        "values (#{id,jdbcType=INTEGER}, #{stuId,jdbcType=VARCHAR}, ",
        "#{info,jdbcType=VARCHAR}, #{point,jdbcType=VARCHAR})"
    })
    int insert(Rank record);

    @InsertProvider(type=RankSqlProvider.class, method="insertSelective")
    int insertSelective(Rank record);

    @SelectProvider(type=RankSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="stu_id", property="stuId", jdbcType=JdbcType.VARCHAR),
        @Result(column="info", property="info", jdbcType=JdbcType.VARCHAR),
        @Result(column="point", property="point", jdbcType=JdbcType.VARCHAR)
    })
    List<Rank> selectByExample(RankExample example);

    @Select({
        "select",
        "id, stu_id, info, point",
        "from rank",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="stu_id", property="stuId", jdbcType=JdbcType.VARCHAR),
        @Result(column="info", property="info", jdbcType=JdbcType.VARCHAR),
        @Result(column="point", property="point", jdbcType=JdbcType.VARCHAR)
    })
    Rank selectByPrimaryKey(Integer id);

    @UpdateProvider(type=RankSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Rank record, @Param("example") RankExample example);

    @UpdateProvider(type=RankSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Rank record, @Param("example") RankExample example);

    @UpdateProvider(type=RankSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Rank record);

    @Update({
        "update rank",
        "set stu_id = #{stuId,jdbcType=VARCHAR},",
          "info = #{info,jdbcType=VARCHAR},",
          "point = #{point,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Rank record);
}