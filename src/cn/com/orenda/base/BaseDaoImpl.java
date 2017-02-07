package cn.com.orenda.base;

import cn.com.orenda.util.DBUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BaseDaoImpl<Entity> implements BaseDao<Entity> {
    //public
    protected Class clazz;


    public BaseDaoImpl() {

        //System.out.println(this.getClass());
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        //������ʵ���Ͳ����Ķ���
        clazz = (Class) pt.getActualTypeArguments()[0];
        System.out.println(clazz);
    }


    /**
     * ���淽��
     */
    public void save(Entity obj) throws Exception {

        //obj.getSimpleName();
        Connection conn = DBUtils.createConn();
        String sql = "insert into " + clazz.getSimpleName() + " values(null ";
        // ���Ի�ȡ�����������ı���
        Field[] fs = clazz.getDeclaredFields();
        //System.out.println(fs.length);

        for (int i = 1; i < fs.length; i++) {
            sql += ",? ";
        }
        sql = sql + ")";
        System.out.println(sql);

        //����Ԥ����
        PreparedStatement ps = DBUtils.getPs(conn, sql);

        //ps.setString(1, user.getName());

        for (int i = 1; i < fs.length; i++) {
            //ƴ�ӷ���������
            String MethodName = "get" + Character.toUpperCase(fs[i].getName().charAt(0)) + fs[i].getName().substring(1);
            Method m = clazz.getMethod(MethodName);
            ps.setObject(i, m.invoke(obj));
        }
        ps.executeUpdate();
        DBUtils.close(ps);
        DBUtils.close(conn);

    }

    /**
     * ���·���
     */
    public void update(Entity obj) throws Exception {

        Connection conn = DBUtils.createConn();
        // update user set name = ? , age = ? where id = ?
        String sql = " update " + clazz.getSimpleName() + " set  ";
        Field[] fs = clazz.getDeclaredFields();
        for (int i = 1; i < fs.length; i++) {
            sql += fs[i].getName() + "=?,";
        }
        sql = sql.substring(0, sql.length() - 1) + " where id = ? ";

        PreparedStatement ps = DBUtils.getPs(conn, sql);

        for (int i = 1; i < fs.length; i++) {
            String methodName = "get" + Character.toUpperCase(fs[i].getName().charAt(0)) + fs[i].getName().substring(1);
            Method m = clazz.getMethod(methodName);
            ps.setObject(i, m.invoke(obj));// user.getName();
        }
        Method m2 = clazz.getMethod("getId");
        ps.setInt(fs.length, (Integer) m2.invoke(obj));

        ps.executeUpdate();
        DBUtils.close(ps);
        DBUtils.close(conn);

    }

    /**
     * ����һ��id ���Ҷ���
     */
    public Entity findById(int id) throws Exception {
        Connection conn = DBUtils.createConn();
        String sql = " select * from  " + clazz.getSimpleName() + " where id = " + id;
        PreparedStatement ps = DBUtils.getPs(conn, sql);
        ResultSet rs = ps.executeQuery();
        Entity entity = (Entity) clazz.newInstance();
        if (rs.next()) {
            Field[] fs = clazz.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {

                String methodName = "set" + Character.toUpperCase(fs[i].getName().charAt(0)) + fs[i].getName().substring(1);
                Method m = clazz.getDeclaredMethod(methodName, fs[i].getType());
                m.invoke(entity, rs.getObject(fs[i].getName()));
            }
        }
        DBUtils.close(rs);
        DBUtils.close(ps);
        DBUtils.close(conn);
        return entity;
    }

    /**
     * ��ѯ����
     */
    public List<Entity> findAll() throws Exception {
        Connection conn = DBUtils.createConn();
        String sql = " select * from " + clazz.getSimpleName();
        PreparedStatement ps = DBUtils.getPs(conn, sql);

        List<Entity> list = new ArrayList<Entity>();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Entity entity = (Entity) clazz.newInstance();

            Field[] fs = clazz.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                String methodName = "set" + Character.toUpperCase(fs[i].getName().charAt(0)) + fs[i].getName().substring(1);
                Method m = clazz.getMethod(methodName, fs[i].getType());
                m.invoke(entity, rs.getObject(fs[i].getName()));
            }

            list.add(entity);
        }
        DBUtils.close(rs);
        DBUtils.close(ps);
        DBUtils.close(conn);
        return list;
    }

    /**
     * ɾ������
     */
    public void delete(int id) throws Exception {
        Connection conn = DBUtils.createConn();
        String sql = " delete from " + clazz.getSimpleName() + " where id =" + id;
        PreparedStatement ps = DBUtils.getPs(conn, sql);
        ps.executeUpdate(sql);
        DBUtils.close(ps);
        DBUtils.close(conn);
    }


    /**
     * ������ѯ�ķ����װ����
     *
     * @param sql
     * @param params
     * @return
     */
    public List<Entity> queryListForParams(String sql, Object[] params) throws Exception {
        Connection conn = DBUtils.createConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            // ����statement����(��װ��sql)
            ps = conn.prepareStatement(sql); // select * from org where id = ? and name = ?  [1 , z3]
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            Field[] fs = clazz.getDeclaredFields();
            rs = ps.executeQuery();
            while (rs.next()) {
                Object obj = clazz.getConstructor().newInstance();
                for (int i = 0; i < fs.length; i++) {
                    String methodName = "set" + fs[i].getName().substring(0, 1).toUpperCase() + fs[i].getName().substring(1);
                    Method m = clazz.getMethod(methodName, fs[i].getType());
                    Object value = rs.getObject(fs[i].getName());
                    m.invoke(obj, value);
                }
                list.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(rs);
            DBUtils.close(ps);
            DBUtils.close(conn);
        }
        return list;
    }
}
