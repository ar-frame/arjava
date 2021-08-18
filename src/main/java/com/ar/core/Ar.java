package com.ar.core;

import com.ar.comp.db.Db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

public class Ar {

    // applications collections
    static private HashMap<String , Object> _a = new HashMap<String , Object>();
    // components collections
    static private HashMap<String , Object> _c = new HashMap<String , Object>();
    // config
    static private HashMap<String , String> _config = new HashMap<String , String>();

    static {
        init();
    }

    static public void init()
    {
        initConfig();

    }


    static public void run()
    {
        App.run();

        App.close();

    }

    static private void initConfig()
    {
        InputStream in = Db.class.getClassLoader().getResourceAsStream("config.properties");
        Properties props = new Properties();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            props.load(br);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(Object s: props.keySet()){
            String ckey = s.toString().trim();
            String property = props.getProperty(ckey).trim();
            _config.put(ckey, property);
        }
    }

    public static String getConfig(String mark)
    {
        return _config.get(mark);
    }

    public static void setConfig(String mark, String val)
    {
        if (mark.trim().length() > 0) {
            _config.put(mark.trim(), val.trim());
        }
    }

    static public void setA(String aname, Object object)
    {
        _a.put(aname, object);
    }

    static public Object a(String aname)
    {
        return _a.get(aname);
    }
}
