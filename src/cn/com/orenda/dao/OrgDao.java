package cn.com.orenda.dao;

import cn.com.orenda.base.BaseDao;
import cn.com.orenda.model.Org;

import java.util.List;

public interface OrgDao extends BaseDao<Org> {

    List<Org> findList(String id) throws Exception;

    public List<Org> getChildren(int id) throws Exception;

}
