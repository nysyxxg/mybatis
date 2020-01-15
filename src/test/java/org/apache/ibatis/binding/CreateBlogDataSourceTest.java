package org.apache.ibatis.binding;

import org.apache.ibatis.BaseDataTest;

import javax.sql.DataSource;

public class CreateBlogDataSourceTest {
    /**
     * 创建数据源，并执行sql脚本
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        DataSource dataSource = BaseDataTest.createBlogDataSource();
        BaseDataTest.runScript(dataSource, BaseDataTest.BLOG_DDL);
        BaseDataTest.runScript(dataSource, BaseDataTest.BLOG_DATA);
    }
}
