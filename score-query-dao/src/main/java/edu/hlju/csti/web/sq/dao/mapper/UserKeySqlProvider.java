package edu.hlju.csti.web.sq.dao.mapper;

import edu.hlju.csti.web.sq.model.UserKey;
import edu.hlju.csti.web.sq.model.UserKeyExample.Criteria;
import edu.hlju.csti.web.sq.model.UserKeyExample.Criterion;
import edu.hlju.csti.web.sq.model.UserKeyExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class UserKeySqlProvider {

    public String countByExample(UserKeyExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("user_key");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(UserKeyExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("user_key");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(UserKey record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user_key");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getSchoolNum() != null) {
            sql.VALUES("school_num", "#{schoolNum,jdbcType=VARCHAR}");
        }
        
        if (record.getPublicKey() != null) {
            sql.VALUES("public_key", "#{publicKey,jdbcType=VARCHAR}");
        }
        
        if (record.getPrivateKey() != null) {
            sql.VALUES("private_key", "#{privateKey,jdbcType=VARCHAR}");
        }
        
        if (record.getLoginCookie() != null) {
            sql.VALUES("login_cookie", "#{loginCookie,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateDate() != null) {
            sql.VALUES("update_date", "#{updateDate,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(UserKeyExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("school_num");
        sql.SELECT("public_key");
        sql.SELECT("private_key");
        sql.SELECT("login_cookie");
        sql.SELECT("update_date");
        sql.FROM("user_key");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        UserKey record = (UserKey) parameter.get("record");
        UserKeyExample example = (UserKeyExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("user_key");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getSchoolNum() != null) {
            sql.SET("school_num = #{record.schoolNum,jdbcType=VARCHAR}");
        }
        
        if (record.getPublicKey() != null) {
            sql.SET("public_key = #{record.publicKey,jdbcType=VARCHAR}");
        }
        
        if (record.getPrivateKey() != null) {
            sql.SET("private_key = #{record.privateKey,jdbcType=VARCHAR}");
        }
        
        if (record.getLoginCookie() != null) {
            sql.SET("login_cookie = #{record.loginCookie,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateDate() != null) {
            sql.SET("update_date = #{record.updateDate,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("user_key");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("school_num = #{record.schoolNum,jdbcType=VARCHAR}");
        sql.SET("public_key = #{record.publicKey,jdbcType=VARCHAR}");
        sql.SET("private_key = #{record.privateKey,jdbcType=VARCHAR}");
        sql.SET("login_cookie = #{record.loginCookie,jdbcType=VARCHAR}");
        sql.SET("update_date = #{record.updateDate,jdbcType=TIMESTAMP}");
        
        UserKeyExample example = (UserKeyExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(UserKey record) {
        SQL sql = new SQL();
        sql.UPDATE("user_key");
        
        if (record.getSchoolNum() != null) {
            sql.SET("school_num = #{schoolNum,jdbcType=VARCHAR}");
        }
        
        if (record.getPublicKey() != null) {
            sql.SET("public_key = #{publicKey,jdbcType=VARCHAR}");
        }
        
        if (record.getPrivateKey() != null) {
            sql.SET("private_key = #{privateKey,jdbcType=VARCHAR}");
        }
        
        if (record.getLoginCookie() != null) {
            sql.SET("login_cookie = #{loginCookie,jdbcType=VARCHAR}");
        }
        
        if (record.getUpdateDate() != null) {
            sql.SET("update_date = #{updateDate,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, UserKeyExample example, boolean includeExamplePhrase) {
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