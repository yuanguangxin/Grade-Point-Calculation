package edu.hlju.csti.web.sq.mapper;

import edu.hlju.csti.web.sq.model.Rank;
import edu.hlju.csti.web.sq.model.RankExample.Criteria;
import edu.hlju.csti.web.sq.model.RankExample.Criterion;
import edu.hlju.csti.web.sq.model.RankExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class RankSqlProvider {

    public String countByExample(RankExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("rank");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(RankExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("rank");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Rank record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("rank");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getStuId() != null) {
            sql.VALUES("stu_id", "#{stuId,jdbcType=VARCHAR}");
        }
        
        if (record.getInfo() != null) {
            sql.VALUES("info", "#{info,jdbcType=VARCHAR}");
        }
        
        if (record.getPoint() != null) {
            sql.VALUES("point", "#{point,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByExample(RankExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("stu_id");
        sql.SELECT("info");
        sql.SELECT("point");
        sql.FROM("rank");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Rank record = (Rank) parameter.get("record");
        RankExample example = (RankExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("rank");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getStuId() != null) {
            sql.SET("stu_id = #{record.stuId,jdbcType=VARCHAR}");
        }
        
        if (record.getInfo() != null) {
            sql.SET("info = #{record.info,jdbcType=VARCHAR}");
        }
        
        if (record.getPoint() != null) {
            sql.SET("point = #{record.point,jdbcType=VARCHAR}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("rank");
        
        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("stu_id = #{record.stuId,jdbcType=VARCHAR}");
        sql.SET("info = #{record.info,jdbcType=VARCHAR}");
        sql.SET("point = #{record.point,jdbcType=VARCHAR}");
        
        RankExample example = (RankExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Rank record) {
        SQL sql = new SQL();
        sql.UPDATE("rank");
        
        if (record.getStuId() != null) {
            sql.SET("stu_id = #{stuId,jdbcType=VARCHAR}");
        }
        
        if (record.getInfo() != null) {
            sql.SET("info = #{info,jdbcType=VARCHAR}");
        }
        
        if (record.getPoint() != null) {
            sql.SET("point = #{point,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, RankExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}