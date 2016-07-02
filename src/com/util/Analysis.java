package com.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Analysis {
    String[] sub_names;
    String[] sub_points;
    String[] sub_scores;
    String[] sub_grades;
    String[] sub_types;
    public void getScores(String userCode,String password,String a){
        String s = CookieUtil.getBody(userCode,password,a);
        Document doc = Jsoup.parse(s);
        Elements cons= doc.getElementsByClass("ui_table_style02");
        if(cons.size()==0){
            return;
        }
        int sub_num = cons.get(0).child(0).children().size()-1;
        sub_grades = new String[sub_num];
        sub_names = new String[sub_num];
        sub_points = new String[sub_num];
        sub_scores = new String[sub_num];
        sub_types = new String[sub_num];
        for(int i=1;i<=sub_num;i++){
            String grade = cons.get(0).child(0).child(i).child(1).text();
            String name = cons.get(0).child(0).child(i).child(3).text();
            String point = cons.get(0).child(0).child(i).child(6).text();
            String score = cons.get(0).child(0).child(i).child(7).text();
            String type = cons.get(0).child(0).child(i).child(4).text();
            sub_grades[i-1] = grade;
            sub_names[i-1] = name;
            sub_points[i-1] = point.split(" ")[0];
            sub_scores[i-1] = score;
            sub_types[i-1] = type;
        }
    }

    public List<String[]> getGradePoint(){
        if(sub_names==null){
            return null;
        }
        List list = new ArrayList<>();
        double this_sum_point = 0;
        double this_sum_score = 0;
        double sum_point = 0;
        double sum_score = 0;
        for (int i=0;i<sub_points.length;i++){
            if(Double.parseDouble(sub_scores[i])>=60){
                if(sub_grades[i].indexOf("2015-2016")!=-1){
                    this_sum_point+=Double.parseDouble(sub_points[i]);
                    this_sum_score+=Double.parseDouble(sub_scores[i])*Double.parseDouble(sub_points[i]);
                }
                sum_point+=Double.parseDouble(sub_points[i]);
                sum_score+=Double.parseDouble(sub_scores[i])*Double.parseDouble(sub_points[i]);
            }
        }
        list.add(sub_grades);
        list.add(sub_names);
        list.add(sub_points);
        list.add(sub_scores);
        list.add(sum_score/(10*sum_point));
        list.add(this_sum_score/(10*this_sum_point));
        list.add(sum_point);
        list.add(sub_types);
        return list;
    }
}
