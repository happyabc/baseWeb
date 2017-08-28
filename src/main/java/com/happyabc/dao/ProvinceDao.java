package com.happyabc.dao;

import com.happyabc.base.BaseDao;
import com.happyabc.model.City;
import com.happyabc.model.Province;

import java.util.List;

public interface ProvinceDao extends BaseDao<Province> {

    List<City> findCitiesByProId(int parseInt) throws Exception;
}
