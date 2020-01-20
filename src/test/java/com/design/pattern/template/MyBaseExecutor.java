package com.design.pattern.template;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.List;

/**
 *     模板模式应该是使用的比较广泛的一个模式，我们在父类中定义算法骨架，将具体实现交由子类去实现
 *     Mybatis中org.apache.ibatis.executor.BaseExecutor就是一个标准的模板模式
 */
public abstract class MyBaseExecutor  implements  MyExecutor {

    // 比如update()方法，定义了update的骨架方法，真正的执行doUpdate()则由子类（如SimpleExecutor）去实现
    // 总结：我们在写一个多种实现方式的方法时，可参考该模式
    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        // 其他逻辑处理
        return doUpdate(ms, parameter);
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException {
        return queryFromDatabase(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }

    private <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) {
        return  doQuery(ms, parameter, rowBounds, resultHandler, boundSql);
    }


    // 模板模式  : 在子类中实现具体的逻辑
    protected abstract <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql);


    // 模板模式：需要在子类中实现具体的逻辑
    protected abstract int doUpdate(MappedStatement ms, Object parameter) throws SQLException;


}
