### 我的博客
[用户行为分析-埋点实时数仓实践](https://mp.csdn.net/editor/html/109784875)

### 功能
- 用户一对多关联逻辑实现

### 参考文档
- [标识用户](https://manual.sensorsdata.cn/sa/latest/tech_knowledge_user-7540285.html)

### 测试数据

1.
{
  "distinct_id": "X",
  "project":"dev",
  "type": "track",
  "event": "安装App",
  "properties": {
  }
}

2.
{
  "distinct_id": "A",
  "original_id": "X",
  "project":"dev",
  "type": "track_signup",
  "event": "$SignUp",
  "properties": {
  }
}

3/4.
{
  "distinct_id": "A",
  "type": "track",
  "project":"dev",
  "event": "使用App",
  "properties": {
    "$is_login_id": true
  }
}

5.
{
  "distinct_id": "B",
  "original_id": "X",
  "project":"dev",
  "type": "track_signup",
  "event": "$SignUp",
  "properties": {
  }
}

6.
{
  "distinct_id": "B",
  "type": "track",
  "event": "使用App",
  "project":"dev",
  "properties": {
    "$is_login_id": true
  }
}

7.
{
  "distinct_id": "Y",
  "type": "track",
  "event": "使用App",
  "project":"dev",
  "properties": {
  }
}

8.
{
  "distinct_id": "A",
  "original_id": "Y",
  "type": "track_signup",
  "event": "$SignUp",
  "project":"dev",
  "properties": {
  }
}

9.
{
  "distinct_id": "A",
  "type": "track",
  "event": "使用App",
  "project":"dev",
  "properties": {
    "$is_login_id": true
  }
}


