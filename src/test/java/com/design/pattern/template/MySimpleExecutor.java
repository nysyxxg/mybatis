package com.design.pattern.template;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.List;

/**
 *  模板模式
 * 在子类中实现父类中定义的抽象方法，完成具体的实现逻辑代码。
 */
public class MySimpleExecutor  extends MyBaseExecutor {
    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        // 实现具体的业务逻辑
        return null;
    }

    @Override
    protected int doUpdate(MappedStatement ms, Object parameter) throws SQLException {
        // 实现具体的业务逻辑
        return 0;
    }
}
