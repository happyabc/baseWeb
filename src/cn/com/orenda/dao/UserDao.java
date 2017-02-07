package cn.com.orenda.dao;

import cn.com.orenda.model.User;
import cn.com.orenda.base.BaseDao;

import java.util.List;
import java.util.Map;


public interface UserDao extends BaseDao<User> {

    List<User> findByPagination(int currentPage, int pageSize, Map<String, Object> m) throws Exception;

    public int getTotal(Map<String, Object> m) throws Exception;

    List<User> searchByName(String q) throws Exception;

}
