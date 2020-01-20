package com.lxj;

import com.lxj.bean.Employee;
import com.lxj.mapper.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {
 
	@Test
	public void testMybatisSource() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//2. 从SqlSession工厂 SqlSessionFactory中创建一个SqlSession，进行数据库操作
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			//3.使用SqlSession查询
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(1);
			System.out.println(employee);
		}finally {
			openSession.close();
		}
	
	}
 
	private SqlSessionFactory getSqlSessionFactory() throws IOException {
		/*
		 * 1.加载mybatis的配置文件，初始化mybatis，创建出SqlSessionFactory，是创建SqlSession的工厂
		 * 这里只是为了演示的需要，SqlSessionFactory临时创建出来，在实际的使用中，SqlSessionFactory只需要创建一次，当作单例来使用
		 */
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);

		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
		return sqlSessionFactory;
	}
 
}