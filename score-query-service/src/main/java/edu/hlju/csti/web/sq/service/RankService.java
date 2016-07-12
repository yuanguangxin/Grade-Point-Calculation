package edu.hlju.csti.web.sq.service;


import edu.hlju.csti.web.sq.dao.mapper.RankMapper;
import edu.hlju.csti.web.sq.model.Rank;
import edu.hlju.csti.web.sq.model.RankExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RankService {
    @Autowired
    private RankMapper rankMapper;

    public void insert(Rank rank) {
        RankExample rankExample = new RankExample();
        RankExample.Criteria criteria = rankExample.createCriteria();
        criteria.andStuIdEqualTo(rank.getStuId());
        if (rankMapper.selectByExample(rankExample).size() == 0) {
            rankMapper.insert(rank);
        } else {
            int id = rankMapper.selectByExample(rankExample).get(0).getId();
            rank.setId(id);
            rankMapper.updateByPrimaryKey(rank);
        }
    }

    public List<Rank> getRank() {
        RankExample rankExample = new RankExample();
        RankExample.Criteria criteria = rankExample.createCriteria();
        criteria.andIdIsNotNull();
        List<Rank> list = rankMapper.selectByExample(rankExample);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 1; j < list.size() - i; j++) {
                if (Double.parseDouble(list.get(j - 1).getPoint()) < Double.parseDouble(list.get(j).getPoint())) {
                    Rank rank = list.get(j - 1);
                    list.set((j - 1), list.get(j));
                    list.set(j, rank);
                }
            }
        }
        List<Rank> list1 = new ArrayList<>();
        System.out.println(list.size());
        try{
            for (int i = 0; i < 5; i++) {
                list1.add(list.get(i));
            }
        }catch (Exception ignored){}
        return list1;
    }
}
