package cn.com.orenda.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.com.orenda.base.BaseDaoImpl;
import cn.com.orenda.dao.ResourceDao;
import cn.com.orenda.model.Tree;
import cn.com.orenda.model.Resource;
import cn.com.orenda.util.DBUtils;

public class ResourceDaoImpl extends BaseDaoImpl<Resource> implements ResourceDao {

	
	
	/**
	 * 根据父id获取子节点们 
	 */
	public List<Tree> getChildrenByParentId(String id) throws Exception {
		Connection conn = DBUtils.createConn();
		String sql = "";
		if("".equals(id) || id == null){
			sql = "select * from resource where parent_id = 999999";
		} else {
			sql = "select * from resource where parent_id = " + id ;
		}
		PreparedStatement ps = DBUtils.getPs(conn, sql);
		ResultSet rs = ps.executeQuery();
		
		List<Resource> rlist = new ArrayList<Resource>();
		while(rs.next()){
			Resource r = new Resource();
			r.setId(rs.getInt("id"));
			r.setIcon(rs.getString("icon"));
			r.setChecked(rs.getInt("checked"));
			r.setName(rs.getString("name"));
			r.setUrl(rs.getString("url"));
			r.setParent_id(rs.getInt("parent_id"));
			rlist.add(r);
		}
		
		List<Tree> tlist = new ArrayList<Tree>();
		for (Iterator iterator = rlist.iterator(); iterator.hasNext();) {
			Resource resource = (Resource) iterator.next();
			Tree tree = new Tree();
			tree.setId(resource.getId());
			tree.setText(resource.getName());
			tree.setChecked(resource.getChecked());
			tree.setIconCls(resource.getIcon());
			tree.setParent_id(resource.getParent_id());
			if(getChildren(resource.getId()).size() > 0){
				tree.setState("closed");
			} else {
				tree.setState("open");
			}
			Map<String, Object>  map = new HashMap<String, Object>();
			map.put("url", resource.getUrl());
			tree.setAttributes(map);
			tlist.add(tree);
		}
		
		

		
		
		return tlist;
	}


	
	
	/**
	 * 根据pid 获取孩子 
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public List<Resource> getChildren(int pid) throws Exception{
		Connection conn = DBUtils.createConn();
		String sql = "select * from resource where parent_id = " + pid;
		PreparedStatement ps = DBUtils.getPs(conn, sql);
		ResultSet rs = ps.executeQuery();
		
		List<Resource> children = new ArrayList<Resource>();
		while(rs.next()){
			Resource r = new Resource();
			r.setId(rs.getInt("id"));
			r.setIcon(rs.getString("icon"));
			r.setChecked(rs.getInt("checked"));
			r.setName(rs.getString("name"));
			r.setUrl(rs.getString("url"));
			r.setParent_id(rs.getInt("parent_id"));
			children.add(r);
		}		
		
		
		return children;
	}
	
	
	
	

}
