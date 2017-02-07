package cn.com.orenda.dao;


import cn.com.orenda.base.BaseDao;
import cn.com.orenda.model.Tree;
import cn.com.orenda.model.Resource;

import java.util.List;

public interface ResourceDao extends BaseDao<Resource> {

    List<Tree> getChildrenByParentId(String id) throws Exception;

    public List<Resource> getChildren(int pid) throws Exception;
}
