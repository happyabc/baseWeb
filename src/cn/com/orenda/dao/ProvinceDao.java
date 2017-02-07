package cn.com.orenda.dao;

import cn.com.orenda.base.BaseDao;
import cn.com.orenda.model.City;
import cn.com.orenda.model.Province;

import java.util.List;

public interface ProvinceDao extends BaseDao<Province> {

    List<City> findCitiesByProId(int parseInt) throws Exception;
}
