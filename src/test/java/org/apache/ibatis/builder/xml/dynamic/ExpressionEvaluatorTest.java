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
package org.apache.ibatis.builder.xml.dynamic;

import java.util.HashMap;

import org.apache.ibatis.domain.blog.Author;
import org.apache.ibatis.domain.blog.Section;
import org.apache.ibatis.scripting.xmltags.ExpressionEvaluator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExpressionEvaluatorTest {

    private ExpressionEvaluator evaluator = new ExpressionEvaluator();

    @Test
    public void shouldCompareStringsReturnTrue() {
        Author author = new Author(1, "cbegin", "******", "cbegin@apache.org", "N/A", Section.NEWS);
        boolean value = evaluator.evaluateBoolean("username == 'cbegin'", author);
        System.out.println(value == true);
        assertEquals(true, value);
    }

    @Test
    public void shouldCompareStringsReturnFalse() {
        Author author = new Author(1, "cbegin", "******", "cbegin@apache.org", "N/A", Section.NEWS);
        boolean value = evaluator.evaluateBoolean("username == 'norm'", author);
        assertEquals(false, value);
    }

    @Test
    public void shouldReturnTrueIfNotNull() {
        Author author = new Author(1, "cbegin", "******", "cbegin@apache.org", "N/A", Section.NEWS);
        boolean value = evaluator.evaluateBoolean("username", author);
        assertEquals(true, value);
    }

    @Test
    public void shouldReturnFalseIfNull() {
        Author author =  new Author(1, "cbegin", null, "cbegin@apache.org", "N/A", Section.NEWS);
        boolean value = evaluator.evaluateBoolean("password", author);
        assertEquals(false, value);
    }

    @Test
    public void shouldReturnTrueIfNotZero() {
        boolean value = evaluator.evaluateBoolean("id", new Author(1, "cbegin", null, "cbegin@apache.org", "N/A", Section.NEWS));
        assertEquals(true, value);
    }

    @Test
    public void shouldReturnFalseIfZero() {
        boolean value = evaluator.evaluateBoolean("id", new Author(0, "cbegin", null, "cbegin@apache.org", "N/A", Section.NEWS));
        assertEquals(false, value);
    }

    @Test
    public void shouldIterateOverIterable() {
        final HashMap<String, String[]> parameterObject = new HashMap<String, String[]>() {{
            put("array", new String[]{"1", "2", "3"});
        }};
        final Iterable<?> iterable = evaluator.evaluateIterable("array", parameterObject);
        int i = 0;
        for (Object o : iterable) {
            assertEquals(String.valueOf(++i), o);
        }
    }


}
