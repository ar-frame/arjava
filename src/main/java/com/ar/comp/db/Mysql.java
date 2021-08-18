package com.ar.comp.db;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ar.core.DbException;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Mysql extends Db{

    public String lastSql = "";
    // last insert id
    public Long lastInsertId = Long.valueOf(0);


    protected HashMap<String , String> options = new HashMap<>() {
        {
            put("columns", "*");
            put("table", "");
            put("join", "");
            put("where", "");
            put("group", "");
            put("having", "");
            put("order", "");
            put("limit", "");
            put("union", "");
            put("comment", "");
            put("data", "");
            put("set", "");
            put("source", "Model");
        }
    };

    public void flushOptions() {
        this.options = new HashMap<>() {
            {
                put("columns", "*");
                put("table", "");
                put("join", "");
                put("where", "");
                put("group", "");
                put("having", "");
                put("order", "");
                put("limit", "");
                put("union", "");
                put("comment", "");
                put("data", "");
                put("set", "");
                put("source", "Model");
            };
        };
    }


    public Integer queryInt()
    {
        return query("", Integer.class);
    }

    public String queryString()
    {
        return query("", String.class);
    }

    public JSONObject queryRow(String sql) {
        limit(1);
        return getJSONObject(sql, new Object[]{});
    }

    public JSONArray queryAll(String sql) {
        return getJSONArray(sql, false);
    }

    public long insert(HashMap<String, Object> insertData) {
        data(insertData);
        this.lastSql = this.buildInsertSql();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            String lastsql = this.lastSql;
            getDbConnection().update(new PreparedStatementCreator() {

                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(lastsql, Statement.RETURN_GENERATED_KEYS);
                    return ps;
                }
            }, keyHolder);
        } catch (DbException e) {
            e.printStackTrace();
        }
        this.flushOptions();

        lastInsertId = keyHolder.getKey().longValue();
        return lastInsertId;
    }

    public int update(HashMap<String, Object> updateData) throws DbException {
        int flag = 0;
        columns(updateData);
        this.lastSql = this.buildUpdateSql();
//        System.out.println(lastSql);
        try {
            flag = getDbConnection().update(this.lastSql);
        } catch (DbException e) {
            e.printStackTrace();
        }
        this.flushOptions();
        return flag;
    }

    public int delete() throws DbException {
        int flag = 0;
        this.lastSql = this.buildDeleteSql();
        try {
            flag = getDbConnection().update(this.lastSql);
        } catch (DbException e) {
            e.printStackTrace();
        }
        this.flushOptions();
        return flag;
    }

    protected JSONObject getJSONObject(String sql, Object[] params) {
        return getJSONObject(sql, params, false);
    }

    protected JSONObject getJSONObject(String sql, Object[] params, final boolean toUpper) {
        resetQueryObject(sql);
        JSONObject jo = new JSONObject();
        try {
            return getDbConnection().query(this.lastSql, (PreparedStatementSetter)null, new ResultSetExtractor<JSONObject>() {
                @Override
                public JSONObject extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                    ResultSetMetaData rsd = resultSet.getMetaData();

//                    System.out.println(rsd);
                    int clength = rsd.getColumnCount();
                    String columnName;
                    try {
                        if (resultSet.next()) {
                            for (int i = 0; i < clength; i++) {
                                columnName = rsd.getColumnLabel(i + 1);
                                columnName = toUpper ? columnName.toUpperCase() : columnName.toLowerCase();
                                jo.put(columnName, resultSet.getObject(i + 1));
                            }
                        }
                        return jo;
                    } catch (Exception e) {
                        return null;
                    }

                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }


    protected  <T> T query(String sql, Class<T> type)  {
        JdbcTemplate conn = null;
        resetQueryObject(sql);
        try {
            conn = getDbConnection();
        } catch (DbException e) {
            e.printStackTrace();
        }

        if (type.toString().equals("interface java.util.List")) {
            return (T) conn.queryForList(sql, type);
        } else {
            return (T) conn.queryForObject(sql, type);
        }
    }

    protected void resetQueryObject(String sql)
    {
        if (sql.length() == 0) {
            sql = buildSelectSql();
        }
        this.lastSql = sql;

        System.out.println("last sql: " + sql);
        this.flushOptions();
    }

    protected JSONArray getJSONArray(String sql, final boolean toUpper) {
        resetQueryObject(sql);
        try {
            return getDbConnection().query(this.lastSql, (PreparedStatementSetter)null, new ResultSetExtractor<JSONArray>() {
                @Override
                public JSONArray extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                    ResultSetMetaData rsd = resultSet.getMetaData();
                    int clength = rsd.getColumnCount();
                    JSONArray ja = new JSONArray();
                    String columnName;
                    try {
                        while (resultSet.next()) {
                            JSONObject jo = new JSONObject();

                            for (int i = 0; i < clength; i++) {
                                columnName = rsd.getColumnLabel(i + 1);
                                columnName = toUpper ? columnName.toUpperCase() : columnName.toLowerCase();
                                jo.put(columnName, resultSet.getObject(i + 1));
                            }
                            ja.put(jo);
                        }
                    } catch (Exception e) {

                    }
                    return ja;
                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }

        return new JSONArray();

    }

    public String buildInsertSql()
    {
        String originSql = "INSERT INTO %TABLE%%DATA%%COMMENT%";
       
        originSql = originSql.toLowerCase();

        for (String key : options.keySet()) {
            originSql = originSql.replace("%"+key+"%", options.get(key));
        }
        return originSql;
    }

    public String buildUpdateSql() throws DbException {
        String originSql = "UPDATE %TABLE%%SET%%WHERE%%COMMENT%";

        originSql = originSql.toLowerCase();

        if (this.options.get("where").length() == 0) {
            throw new DbException("invalid empty sql conditions set ");
        }

        for (String key : options.keySet()) {
            originSql = originSql.replace("%"+key+"%", options.get(key));
        }
        return originSql;
    }

    public String buildDeleteSql() throws DbException {
        String originSql = "DELETE FROM %TABLE%%WHERE%%COMMENT%";

        originSql = originSql.toLowerCase();

        if (this.options.get("where").length() == 0) {
            throw new DbException("invalid empty sql conditions del ");
        }

        for (String key : options.keySet()) {
            originSql = originSql.replace("%"+key+"%", options.get(key));
        }
        return originSql;
    }

    public String buildSelectSql()
    {
        String originSql = "SELECT %COLUMNS% FROM %TABLE%%JOIN%%WHERE%%GROUP%%HAVING%%ORDER%%LIMIT% %UNION%%COMMENT%";

        originSql = originSql.toLowerCase();

        for (String key : options.keySet()) {
            originSql = originSql.replace("%"+key+"%", options.get(key));
        }
        return originSql;
    }

    public Mysql group(String group)
    {
        options.put("group", group.length() == 0 ? "" : " GROUP BY " + group);
        return this;
    }

    public Mysql having(String having)
    {
        options.put("having", having.length() == 0 ? "" : " HAVING BY " + having);
        return this;
    }

    public Mysql order(String order)
    {
        options.put("order", order.length() == 0 ? "" : " ORDER BY " + order);
        return this;
    }

    public Mysql table(String table)
    {
        options.put("table", table);;
        return this;

    }

    public Mysql limit(Integer limit)
    {
        options.put("limit", limit == 0 ? "" : " LIMIT " + String.valueOf(limit));
        return this;
    }

    public Mysql select(String select)
    {
        options.put("columns", select.length() == 0 ? "*" : select);
        return this;
    }

    public Mysql where(HashMap<String, Object> conds)
    {
        String conStr = buildCondition(conds);
        options.put("where", conStr.length() == 0 ? "" : " WHERE " + conStr);
        return this;
    }


    public String buildCondition(HashMap<String, Object> conds)
    {
        return getConditonString(conds);
    }

    /*
     * get condition string sql
     *
     * useage
     *
       HashMap<String, Object> conds = new HashMap<>();
       conds.put("aa", "333");
       conds.put("aa4", 11);
       conds.put("aa9 > ", 11);
       conds.put("aa5", new int[]{1,2,3});
       conds.put("aa6", new String[]{"a", "b", "c"});
       String sql = getConditonString(conds);
       System.out.println(sql);
    */
    public static String getConditonString(HashMap<String, Object> conds) {
        String sql = "";

        for (String key : conds.keySet()) {

            Object val = conds.get(key);

            key = key.trim();

            String opt = "=";
            if (key.indexOf(" ") > 0) {
                String[] arrs = key.split(" ");
                key = StringEscapeUtils.escapeXSI(arrs[0]);
                opt = arrs[1];
            } else {
                key = StringEscapeUtils.escapeXSI(key);
            }

            if (val.getClass().toString().equals("class java.lang.Integer")) {

            } else if (val.getClass().toString().equals("class java.lang.String")) {
                val = String.format("'%s'", StringEscapeUtils.escapeXSI(val.toString()));
            } else if (val.getClass().toString().equals("class [I")) {
                String instr = "";
                for (int i = 0; i < Array.getLength(val); i++) {
                    if (i == 0) {
                        instr = "(";

                        instr = instr + Array.get(val, i);
                    } else {
                        instr = instr + "," + Array.get(val, i);
                    }

                    if (i == Array.getLength(val) - 1) {
                        instr = instr + ")";
                    }
                }
                val = String.format("%s", instr);
                opt = "in";
            } else if (val.getClass().toString().equals("class [Ljava.lang.String;")) {
                String instr = "";
                for(int i = 0; i < Array.getLength(val); i++) {
                    if (i == 0) {
                        instr = "(";

                        instr = instr + String.format("'%s'", StringEscapeUtils.escapeXSI(Array.get(val, i).toString()));
                    } else {
                        instr = instr + "," + String.format("'%s'", StringEscapeUtils.escapeXSI(Array.get(val, i).toString()));
                    }

                    if (i == Array.getLength(val) - 1) {
                        instr = instr + ")";
                    }
                }
                val = String.format("%s", instr);
                opt = "in";
            } else {
                continue;
            }

            if (sql.length() > 0) {
                sql = sql + String.format(" AND `%s` %s %s ", key, opt, val);
            } else {
                sql = sql + String.format(" `%s` %s %s ", key, opt, val);
            }
        }
        return sql;
    }


    protected void data(HashMap<String, Object> stringObjectHashMap)
    {
        ArrayList<String> klist = new ArrayList<String>();

        for (String key : stringObjectHashMap.keySet()) {
            klist.add("`" + StringEscapeUtils.escapeXSI(key) + "`");
        }

        String keystring = "(" + String.join(",", klist) + ")";
        ArrayList<String> vallist = new ArrayList<String>();

        for (Object val : stringObjectHashMap.values()) {
            if (val instanceof Integer) {
                vallist.add(StringEscapeUtils.escapeXSI(val.toString()));
            } else {
                vallist.add("'" + StringEscapeUtils.escapeXSI(val.toString()) +"'");
            }
        }
        String valstring = " VALUES (" + String.join(",", vallist) + ")";
        options.put("data", keystring + valstring);
    }

    protected void columns(HashMap<String, Object> stringObjectHashMap)
    {
        ArrayList<String> klist = new ArrayList<String>();

        for (String key : stringObjectHashMap.keySet()) {
            Object val = stringObjectHashMap.get(key);
            if (val instanceof Integer) {
                klist.add("`" + StringEscapeUtils.escapeXSI(key) + "` = " + val);
            } else {
                klist.add("`" + StringEscapeUtils.escapeXSI(key) + "` = " + "'" + StringEscapeUtils.escapeXSI(val.toString()) + "'");
            }
        }
        options.put("set", " SET " + String.join(",", klist));
    }

}
