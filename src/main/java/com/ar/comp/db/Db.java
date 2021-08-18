package com.ar.comp.db;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class Db {
    
    // connectionMark"
    public String connectionMark = "read.default";

    public static HashMap<String, JdbcTemplate> readConnections = new HashMap<String, JdbcTemplate>();
    public static HashMap<String, JdbcTemplate> writeConnections = new HashMap<String, JdbcTemplate>();
    public static HashMap<String, Mysql> dbHolder = new HashMap<String, Mysql>();

    public JdbcTemplate getDbConnection() throws com.ar.core.DbException
    {
        JdbcTemplate dbc = null;
        if (this.connectionMark.length() == 0 || this.connectionMark.indexOf(".") == -1) {
            throw new com.ar.core.DbException("Connection Mark Error : " + this.connectionMark);
        } else {
            String[] marks = this.connectionMark.split("\\.");
            String type = marks[0];
            String mark = marks[1];
            switch (type) {
                case "read":
                    dbc = readCon(mark);
                    break;
                case "write":
                    dbc = writeCon(mark);
                    break;
                default:
                    throw new com.ar.core.DbException("Connection Mark DataBase Type Error : " + this.connectionMark); 
            }
        }
        return dbc;
    }

    public JdbcTemplate readCon(String mark) {
        String dbMark = "read." + mark;
        if (readConnections.get(dbMark) == null) {
            addConnection(dbMark, "read");
        }
        return readConnections.get(dbMark);
    }

    public JdbcTemplate writeCon(String mark) {
        String dbMark = "write." + mark;
        if (writeConnections.get(dbMark) == null) {
            addConnection(dbMark, "write");
        }
        return writeConnections.get(dbMark);
    }

    public Mysql read(String mark)
    {
        String dbMark = "read." + mark;
        connectionMark = dbMark;

        if (readConnections.get(dbMark) == null) {
            addConnection(dbMark, "read");
        }

        if (dbHolder.get(dbMark) == null) {
            Mysql db  = new Mysql();
            db.connectionMark = connectionMark;
            dbHolder.put(dbMark, db);
        }

        return dbHolder.get(dbMark);
    }

    public Mysql write(String mark)
    {
        String dbMark = "write." + mark;
        connectionMark = dbMark;

        if (writeConnections.get(dbMark) == null) {
            addConnection(dbMark, "write");
        }

        if (dbHolder.get(dbMark) == null) {
            Mysql db  = new Mysql();
            db.connectionMark = connectionMark;
            dbHolder.put(dbMark, db);
        }

        return dbHolder.get(dbMark);
    }

    public void addConnection(String mark, String dbtype)
    {
        HashMap<String, String> cfg = getDbConfig(mark);

        SimpleDriverDataSource ds = new SimpleDriverDataSource();
        try {
            ds.setDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ds.setUrl("jdbc:mysql://"+cfg.get("host")+":"+cfg.get("port")+"/"+cfg.get("dbname"));
        ds.setUsername(cfg.get("user"));
        ds.setPassword(cfg.get("pass"));

        JdbcTemplate jtm = new JdbcTemplate(ds);

        if (dbtype.equals("read")) {
            readConnections.put(mark, jtm);
        } else {
            writeConnections.put(mark, jtm);
        }
    }

    public HashMap<String, String> getDbConfig(String mark)
    {
        HashMap<String, HashMap<String, String>> globalConfig = config();
        return globalConfig.get(mark);
    }

    public HashMap<String, HashMap<String , String>> config()
    {
        InputStream in = Db.class.getClassLoader().getResourceAsStream("db.properties");

    
        Properties props = new Properties();  
        BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
        try {
            props.load(br);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        HashMap<String, HashMap<String , String>> mapCcontent = new HashMap<String, HashMap<String , String>>();

        // System.out.println("---------------");
        // System.out.print(props);
        HashMap<String, String> map = new HashMap<String, String>();
        // System.out.print(map);

        for(Object s: props.keySet()){  
            //System.out.println(s+":"+props.getProperty(s.toString()));
            String ckey = s.toString();
            String[] arr = ckey.split("\\.");

            if (arr.length != 3) {
                System.out.println("key err:" + arr.toString());
                System.out.println("key:"+ckey);
            } else {
                String realKey = arr[0] +"."+ arr[1];

                if (mapCcontent.get(realKey) == null) {
                    map = new HashMap<String, String>();
                } else {
                    map = mapCcontent.get(realKey);
                }
    
                map.put(arr[2], props.getProperty(ckey));
    
                mapCcontent.put(realKey, map);
            }
           
        }

        return mapCcontent;

    }

}
