package com.design.pattern.factory;

import com.design.pattern.factory.dbcp.MyDBCPDataSourceFactory;
import com.design.pattern.factory.jndi.MyJndiDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Properties;

public class MyDataSourceFactoryTest {

    @Test
    public void testMyDataSourceFactory() throws Exception {
//      String type = "cp30";
//      String type = "dbcp";
        String type = "jndi";
        MyDataSourceFactory dataSourceFactory = dataSourceElement(type);
        DataSource dataSource = dataSourceFactory.getDataSource();
        System.out.println("最后获取数据源类名称：" + dataSource.getClass().getName());
    }


    private static MyDataSourceFactory dataSourceElement(String type) throws Exception {
        Class<?> clalz = null;
        if (type.equals("cp30")) {
            // clalz = MyCP30DataSourceFactory.class;
            clalz = (Class<?>) Resources.classForName("com.design.pattern.factory.cp30.MyCP30DataSourceFactory");
        } else if (type.equals("dbcp")) {
            clalz = MyDBCPDataSourceFactory.class;
        } else if (type.equals("jndi")) {
            clalz = MyJndiDataSourceFactory.class;
        }
        // 通过反射创建对象
        MyDataSourceFactory factory = (MyDataSourceFactory) clalz.newInstance();
        return factory;
    }
}
