package party.csti.gpa.service;

import org.springframework.stereotype.Service;
import party.csti.gpa.dao.InformationMapper;
import party.csti.gpa.model.Information;
import party.csti.gpa.model.InformationExample;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InfoService {
    @Resource
    private InformationMapper informationMapper;

    public void updateInfo(Information info) {
        InformationExample informationExample = new InformationExample();
        InformationExample.Criteria criteria = informationExample.createCriteria();
        criteria.andStuIdEqualTo(info.getStuId());
        if (informationMapper.selectByExample(informationExample).size() == 0) {
            informationMapper.insert(info);
        } else {
            Information information = informationMapper.selectByExample(informationExample).get(0);
            information.setInfo(info.getInfo());
            information.setCredit(info.getCredit());
            information.setNpoint(info.getNpoint());
            information.setTpoint(info.getTpoint());
            informationMapper.updateByPrimaryKey(information);
        }
    }

    public List<Information> queryInfo(String name) {
        InformationExample informationExample = new InformationExample();
        InformationExample.Criteria criteria = informationExample.createCriteria();
        if (name.equals("")) {
            criteria.andIdIsNotNull();
        } else {
            criteria.andInfoLike("%" + name + "%");
        }
        return informationMapper.selectByExample(informationExample);
    }

    public List<Information> search(String name) {
        InformationExample informationExample = new InformationExample();
        InformationExample.Criteria criteria = informationExample.createCriteria();
        if (name.equals("")) {
            criteria.andIdIsNull();
        } else {
            criteria.andInfoLike("%" + name + "%");
        }
        return informationMapper.selectByExample(informationExample);
    }

    public List<Information> getNpointRank() {
        List<Information> list = queryInfo("");
        for (int i = 0; i < list.size(); i++) {
            for (int j = 1; j < list.size() - i; j++) {
                if (Double.parseDouble(list.get(j - 1).getNpoint().toString()) < Double.parseDouble(list.get(j).getNpoint().toString())) {
                    Information info = list.get(j - 1);
                    list.set((j - 1), list.get(j));
                    list.set(j, info);
                }
            }
        }
        if (list.size() >= 5) {
            return list.subList(0, 5);
        } else {
            return list.subList(0, list.size());
        }
    }

    public List<Information> getTpointRank() {
        List<Information> list = queryInfo("");
        for (int i = 0; i < list.size(); i++) {
            for (int j = 1; j < list.size() - i; j++) {
                if (Double.parseDouble(list.get(j - 1).getTpoint().toString()) < Double.parseDouble(list.get(j).getTpoint().toString())) {
                    Information info = list.get(j - 1);
                    list.set((j - 1), list.get(j));
                    list.set(j, info);
                }
            }
        }
        if (list.size() >= 5) {
            return list.subList(0, 5);
        } else {
            return list.subList(0, list.size());
        }
    }
}
