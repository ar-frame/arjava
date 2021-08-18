package com.ar.comp.rpc;

import com.ar.comp.hash.Cipher;
import com.google.gson.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Service {

    protected static String TAG_MSG_SEP = "___SERVICE_STD_OUT_SEP___";

    static public JsonObject decrypt(String jString)
    {
        String jsonString = Cipher.decrypt(jString);
        JsonParser parser = new JsonParser();
        JsonObject jObject2 = parser.parse(jsonString).getAsJsonObject().get("data").getAsJsonObject();
        return jObject2;
    }

    static public void response(String data)
    {
        String responseStr = TAG_MSG_SEP + encrypt(data);
        execResponse(responseStr);
    }

    static public void response(Boolean data)
    {
        String responseStr = TAG_MSG_SEP + encrypt(data);
        execResponse(responseStr);
    }

    static public void response(Integer data)
    {
        String responseStr = TAG_MSG_SEP + encrypt(data);
        execResponse(responseStr);
    }

    static public void response(JsonObject data)
    {
        String responseStr = TAG_MSG_SEP + encrypt(data);
        execResponse(responseStr);
    }

    static public void response(JsonArray data)
    {
        String responseStr = TAG_MSG_SEP + encrypt(data);
        execResponse(responseStr);
    }

    static public void response(Float data)
    {
        String responseStr = TAG_MSG_SEP + encrypt(data);
        execResponse(responseStr);
    }

    static public void response()
    {
        Gson gson = new Gson();
        Map map = new HashMap();

        map.put("type", "null");
        map.put("data", null);

        String json = gson.toJson(map);

        String cipherText = Cipher.encrypt(json);

        String responseStr = TAG_MSG_SEP + cipherText;
        execResponse(responseStr);
    }

    static public String encrypt(Float data)
    {
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("type", "float");
        map.put("data", data);
        String json = gson.toJson(map);

        String cipherText = Cipher.encrypt(json);

        return cipherText;
    }

    static public String encrypt(JsonArray data)
    {
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("type", "array");
        map.put("data", data);
        String json = gson.toJson(map);

        String cipherText = Cipher.encrypt(json);

        return cipherText;
    }

    static public String encrypt(JsonObject data)
    {
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("type", "object");
        map.put("data", data);
        String json = gson.toJson(map);

        String cipherText = Cipher.encrypt(json);

        return cipherText;
    }

    static public String encrypt(Integer data)
    {
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("type", "int");
        map.put("data", data);
        String json = gson.toJson(map);

        String cipherText = Cipher.encrypt(json);

        return cipherText;
    }

    static public String encrypt(Boolean data)
    {
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("type", "bool");
        map.put("data", data);
        String json = gson.toJson(map);

        String cipherText = Cipher.encrypt(json);

        return cipherText;
    }


    static public String encrypt(String data)
    {
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("type", "string");
        map.put("data", data);
        String json = gson.toJson(map);

        String cipherText = Cipher.encrypt(json);

        return cipherText;
    }

    static public void execResponse(String resString)
    {
//        System.out.println(resString);

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();


        try {
            response.getWriter().write(resString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
