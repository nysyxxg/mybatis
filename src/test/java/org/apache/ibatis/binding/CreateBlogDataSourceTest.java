package org.apache.ibatis.binding;

import org.apache.ibatis.BaseDataTest;

import javax.sql.DataSource;

public class CreateBlogDataSourceTest {
    public static void main(String[] args) throws Exception{
        DataSource dataSource = BaseDataTest.createBlogDataSource();
        BaseDataTest.runScript(dataSource, BaseDataTest.BLOG_DDL);
        BaseDataTest.runScript(dataSource, BaseDataTest.BLOG_DATA);
    }
}
