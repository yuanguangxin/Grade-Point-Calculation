package party.csti.gpa.analysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import party.csti.gpa.util.http.HttpUtil;

import java.text.DecimalFormat;
import java.util.*;

public class Analysis {
    private String[] sub_names;    //课程名
    private String[] sub_points;   //课程学分
    private String[] sub_scores;   //课程分数
    private String[] sub_grades;   //课程年级
    private String[] sub_types;    //课程类别
    private String userInfo;       //用户信息
    private List<String> every_year_points = new ArrayList<>();//每年绩点
    private List<Double> every_year_credit = new ArrayList<>();//每年学分

    private void getScores(HttpUtil httpUtil, String userCode, String password, String a) {
        String s1 = httpUtil.getBody(userCode, password, a).get("message");
        if (s1 == null) return;
        Document doc = Jsoup.parse(s1);
        Elements cons = doc.getElementsByClass("ui_table_style02");
        if (cons.size() == 0) {
            return;
        }
        int sub_num = cons.get(0).child(0).children().size() - 1;
        sub_grades = new String[sub_num];
        sub_names = new String[sub_num];
        sub_points = new String[sub_num];
        sub_scores = new String[sub_num];
        sub_types = new String[sub_num];
        for (int i = 1; i <= sub_num; i++) {
            String grade = cons.get(0).child(0).child(i).child(1).text();
            String name = cons.get(0).child(0).child(i).child(3).text();
            String point = cons.get(0).child(0).child(i).child(6).text();
            String score = cons.get(0).child(0).child(i).child(7).text();
            String type = cons.get(0).child(0).child(i).child(4).text();
            sub_grades[i - 1] = grade;
            sub_names[i - 1] = name;
            sub_points[i - 1] = point.split(" ")[0];
            sub_scores[i - 1] = score;
            sub_types[i - 1] = type;
        }

        String s2 = httpUtil.getBody(userCode, password, a).get("userInfo");
        Document doc2 = Jsoup.parse(s2);
        Elements info = doc2.getElementsByClass("userinfo");
        userInfo = info.get(0).text();
    }

    public Map getGradePoint(HttpUtil httpUtil, String userCode, String password, String a) {
        getScores(httpUtil, userCode, password, a);
        DecimalFormat df = new DecimalFormat("#.000");
        if (sub_names == null) {
            return null;
        }
        Map map = new HashMap();
        double this_sum_point = 0;
        double this_sum_score = 0;
        double sum_point = 0;
        double sum_score = 0;
        String[] year = new String[2];
        if (sub_grades.length != 0) {
            year[0] = sub_grades[0].substring(0, 9);
        }
        double pp = 0;
        double ss = 0;
        int k = 0;
        String this_year = "";
        if (sub_grades.length > 0) {
            this_year = sub_grades[0].split("，")[0];
        }
        for (int i = 0; i < sub_points.length; i++) {
            if (!sub_grades[i].substring(0, 9).equals(year[0].substring(0, 9))) {
                k++;
                every_year_points.add(df.format(ss / (10 * pp)));
                year[0] = sub_grades[i].substring(0, 9);
                pp = 0;
                ss = 0;
            }
            if (Double.parseDouble(sub_scores[i]) >= 60) {
                if (every_year_credit.size() <= k) {
                    every_year_credit.add(Double.parseDouble(sub_points[i]));
                } else {
                    every_year_credit.set(k, every_year_credit.get(k) + Double.parseDouble(sub_points[i]));
                }
                pp += Double.parseDouble(sub_points[i]);
                ss += Double.parseDouble(sub_scores[i]) * Double.parseDouble(sub_points[i]);
            }
            if (Double.parseDouble(sub_scores[i]) >= 60) {
                if (sub_grades[i].indexOf(this_year) != -1) {
                    this_sum_point += Double.parseDouble(sub_points[i]);
                    this_sum_score += Double.parseDouble(sub_scores[i]) * Double.parseDouble(sub_points[i]);
                }
                sum_point += Double.parseDouble(sub_points[i]);
                sum_score += Double.parseDouble(sub_scores[i]) * Double.parseDouble(sub_points[i]);
            }
        }
        if (pp != 0) {
            year[1] = String.valueOf(ss / (10 * pp));
        }
        every_year_points.add(df.format(Double.parseDouble(year[1])));
        map.put("sub_names", sub_names);
        map.put("sub_points", sub_points);
        map.put("sub_scores", sub_scores);
        map.put("gp", df.format(sum_score / (10 * sum_point)));
        map.put("this_gp", df.format(this_sum_score / (10 * this_sum_point)));
        map.put("credit", sum_point);
        map.put("sub_types", sub_types);
        map.put("userInfo", userInfo);
        map.put("every_year_points", every_year_points);
        map.put("every_year_credit", every_year_credit);
        return map;
    }
}
