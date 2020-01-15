/*
 *    Copyright 2009-2012 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.builder;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.domain.blog.Author;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

public class XmlMapperBuilderTest {

    @Test
    public void shouldSuccessfullyLoadXMLMapperFile() throws Exception {
        Configuration configuration = new Configuration();
        String resource = "org/apache/ibatis/builder/AuthorMapper.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        XMLMapperBuilder builder = new XMLMapperBuilder(inputStream, configuration, resource, configuration.getSqlFragments());
        builder.parse(); // 解析xml配置，并创建映射关系

        MapperRegistry mapperRegistry = configuration.getMapperRegistry();
        Collection<Class<?>> classes = mapperRegistry.getMappers();
        Iterator<Class<?>> obj = classes.iterator();
        while (obj.hasNext()) {
            Class<?> clazz = obj.next();
            System.out.println(clazz.getSimpleName());
            System.out.println(clazz.getName());
            Method[] methods = clazz.getMethods();// 或者这个类的所有方法
            for (Method method : methods) {  // 打印所有方法
                System.out.println(method.getName());
            }
            // 通过反射调用某个对象的方法
//            Method method = clazz.getMethod("selectAuthor", Integer.class);
//            Object obj2 = method.invoke(clazz.newInstance(),101);
//            System.out.println(obj2);

            Collection<String> nameList = configuration.getMappedStatementNames();
            List<String> valueTypeList = new ArrayList<String>();
            valueTypeList.addAll(nameList);
            for (String key : valueTypeList) {
                System.out.println(key);
            }

            Collection<MappedStatement> mappedStatements = configuration.getMappedStatements();
            List<MappedStatement> mappedStatementsList = new ArrayList<MappedStatement>();
            mappedStatementsList.addAll(mappedStatements);
            for (MappedStatement statement : mappedStatementsList) {
                SqlSource sqlSource = statement.getSqlSource();
                ParameterMap parameterMap = statement.getParameterMap();

                System.out.println("参数：" + parameterMap.getId() + " --->" + parameterMap.getType() + "---->");
                List<ParameterMapping> parameterMappings = parameterMap.getParameterMappings();
                for (ParameterMapping mapping : parameterMappings) {
                    System.out.println(mapping.getProperty());
                    System.out.println(mapping.getTypeHandler().getClass());
                    System.out.println(mapping.getJavaType().getName());
                }

                if (parameterMap.getId().contains("updateAuthorIfNecessary")) {
                    System.out.println("参数：" + parameterMap.getId());
                    Author author = new Author();
                    author.setUsername("xuxiaoguang");
                    author.setPassword("********");
                    author.setEmail("nysyxxg@163.com");
                    author.setBio("bio");
                    BoundSql sql = sqlSource.getBoundSql(author);
                    System.out.println("SQL: " + statement.getId() + "----->" + sql.getSql());
                } else {
                    BoundSql sql = sqlSource.getBoundSql(parameterMappings);
                    System.out.println("SQL: " + statement.getId() + "----->" + sql.getSql());
                }
                System.out.println("--------------------------------------------------");
            }
        }
    }

    @Test
    public void shouldNotLoadTheSameNamespaceFromTwoResourcesWithDifferentNames() throws Exception {
        Configuration configuration = new Configuration();
        String resource = "org/apache/ibatis/builder/AuthorMapper.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        XMLMapperBuilder builder = new XMLMapperBuilder(inputStream, configuration, "name1", configuration.getSqlFragments());
        builder.parse();

        InputStream inputStream2 = Resources.getResourceAsStream(resource);
        XMLMapperBuilder builder2 = new XMLMapperBuilder(inputStream2, configuration, "name2", configuration.getSqlFragments());
        builder2.parse();
    }

}
