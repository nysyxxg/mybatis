package com.design.pattern.compose.demo02;

import org.apache.ibatis.scripting.xmltags.DynamicContext;

public interface MySqlNode {
  void apply(String context);
}
