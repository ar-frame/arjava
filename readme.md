# JAVA版 ar http service

# build getCoder
mvn install:install-file -Dfile=libs/GetCoder.jar -DgroupId=GetCoder -DartifactId=GetCoder -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar

# 启动目录
http://ip:8080/start/arws

# 启动命令
mvn sprin-boot:run

## worker编写
service 目录， 参考User.java

# 数据库操作

## 查询条件
HashMap<String, Object> conds = new HashMap<>();
conds.put("id > ", 0); // 参考arphp


## 查询
int i3 = new Mysql().read("default").table("user").select("count(*) as t").where(conds).queryInt();
### 列表数组
JSONArray res = new Mysql().read("default").table("user").select("*").where(conds).queryAll("");
### 查一行
JSONObject jsonObject = new Mysql().read("default").table("user").select("*").where(conds).queryRow("");

## 添加
HashMap<String, Object> insertData = new HashMap<>();
insertData.put("email", "yy");

long insert = new Mysql().read("default").table("user").insert(insertData);

## 更新
HashMap<String, Object> updateData = new HashMap<>();
updateData.put("name", "hhh");

conds.put("id", 3);
int update = 0;
update = new Mysql().read("default").where(conds).table("user").update(updateData);
update > 0 表示更新成功

# 删除
int delete = 0;
conds.put("id > ", 50);
delete = new Mysql().read("default").where(conds).table("user").delete();