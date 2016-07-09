package edu.hlju.csti.web.sq.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Analysis {
    private static Logger logger = LoggerFactory.getLogger(Analysis.class);
    String[] sub_names;
    String[] sub_points;
    String[] sub_scores;
    String[] sub_grades;
    String[] sub_types;
    String userinfo;
    ArrayList<String[]> every_year_points = new ArrayList<>();
    double[] s = new double[4];

    public void getScores(CookieUtil cookieUtil, String userCode, String password, String a) {
        String s1 = cookieUtil.getBody(userCode, password, a)[0];
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

        String s2 = cookieUtil.getBody(userCode, password, a)[2];
        Document doc2 = Jsoup.parse(s2);
        Elements info = doc2.getElementsByClass("userinfo");
        userinfo = info.get(0).text();
    }

    public List getGradePoint() {
        logger.info("sub_name is :{}", Arrays.toString(sub_names));
        if (sub_names == null) {
            return null;
        }
        List list = new ArrayList<>();
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
        for (int i = 0; i < sub_points.length; i++) {
            if (!sub_grades[i].substring(0, 9).equals(year[0].substring(0, 9))) {
                k++;
                year[1] = String.valueOf(ss / (10 * pp));
                every_year_points.add(year.clone());
                year[0] = sub_grades[i].substring(0, 9);
                pp = 0;
                ss = 0;
            }
            if (Double.parseDouble(sub_scores[i]) >= 60) {
                s[k] += Double.parseDouble(sub_points[i]);
                pp += Double.parseDouble(sub_points[i]);
                ss += Double.parseDouble(sub_scores[i]) * Double.parseDouble(sub_points[i]);
            }
            if (Double.parseDouble(sub_scores[i]) >= 60) {
                if (sub_grades[i].contains("2015-2016")) {
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
        every_year_points.add(year);
        list.add(sub_grades);
        list.add(sub_names);
        list.add(sub_points);
        list.add(sub_scores);
        list.add(sum_score / (10 * sum_point));
        list.add(this_sum_score / (10 * this_sum_point));
        list.add(sum_point);
        list.add(sub_types);
        list.add(userinfo);
        list.add(every_year_points);
        list.add(s);
        return list;
    }
}
