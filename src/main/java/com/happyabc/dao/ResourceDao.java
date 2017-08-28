package com.happyabc.dao;


import com.happyabc.base.BaseDao;
import com.happyabc.model.Tree;
import com.happyabc.model.Resource;

import java.util.List;

public interface ResourceDao extends BaseDao<Resource> {

    List<Tree> getChildrenByParentId(String id) throws Exception;

    public List<Resource> getChildren(int pid) throws Exception;
}
