package com.service;

import com.mapper.RankMapper;
import com.models.Rank;
import com.models.RankExample;

import java.util.ArrayList;
import java.util.List;

public class RankService {
    private RankMapper rankMapper;

    public RankMapper getRankMapper() {
        return rankMapper;
    }

    public void setRankMapper(RankMapper rankMapper) {
        this.rankMapper = rankMapper;
    }

    public void insert(Rank rank){
        RankExample rankExample = new RankExample();
        RankExample.Criteria criteria = rankExample.createCriteria();
        criteria.andStuIdEqualTo(rank.getStuId());
        if(rankMapper.selectByExample(rankExample).size()==0){
            rankMapper.insert(rank);
        }else {
            int id = rankMapper.selectByExample(rankExample).get(0).getId();
            rank.setId(id);
            rankMapper.updateByPrimaryKey(rank);
        }
    }

    public List<Rank> getRank(){
        RankExample rankExample = new RankExample();
        RankExample.Criteria criteria = rankExample.createCriteria();
        criteria.andIdIsNotNull();
        List<Rank> list = rankMapper.selectByExample(rankExample);
        for(int i=0;i<list.size();i++){
            for(int j=1;j<list.size()-i;j++){
                if(Double.parseDouble(list.get(j-1).getPoint().toString())<Double.parseDouble(list.get(j).getPoint().toString())){
                    Rank rank = list.get(j - 1);
                    list.set((j - 1), list.get(j));
                    list.set(j, rank);
                }
            }
        }
        List<Rank> list1 = new ArrayList<>();
        for(int i=0;i<5;i++){
            list1.add(list.get(i));
        }
        return list1;
    }
}
