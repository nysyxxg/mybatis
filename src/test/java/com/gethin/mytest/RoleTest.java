package com.gethin.mytest;

import com.gethin.po.Role;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 查看方式2
 */
public class RoleTest {

    @Test
    public void testCache2() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory  sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            String sql = "com.gethin.mapper.RoleMapper.getRole";
            Role role = (Role)sqlSession.selectOne(sql, Long.valueOf(1));
            System.out.println(role.getId() + ":" + role.getRoleName() + ":" + role.getNote());
            // 注意，这里一定要提交。 不提交还是会查询两次数据库
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}
