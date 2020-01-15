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
package org.apache.ibatis.binding;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.domain.blog.Blog;

import java.util.List;

public interface MapperWithMany {

    @Select({
        "SELECT *",
        "FROM blog"
    })
    @Results({
        @Result(
               property = "author", column = "author_id",
               many = @Many(select = "selectPostsById"))
    })
    List<Blog> selectWithBothOneAndMany();

    /**
     * 上面会报错：
     * org.apache.ibatis.builder.BuilderException: Cannot use both @One and @Many annotations in the same @Result
     *
     *  one = @One(select = "org.apache.ibatis.binding.BoundAuthorMapper.selectAuthor"),
     *    many = @Many(select = "selectPostsById")  应该只能配置一个
     */
}
