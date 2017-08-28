package com.happyabc.dao;

import com.happyabc.base.BaseDao;
import com.happyabc.model.Org;

import java.util.List;

public interface OrgDao extends BaseDao<Org> {

    List<Org> findList(String id) throws Exception;

    public List<Org> getChildren(int id) throws Exception;

}
