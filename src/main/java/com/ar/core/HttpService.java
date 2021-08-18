package com.ar.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class HttpService {

    protected void response(String data)
    {
        com.ar.comp.rpc.Service.response(data);
    }

    protected void response(Integer data)
    {
        com.ar.comp.rpc.Service.response(data);
    }

    protected void response(JsonArray data)
    {
        com.ar.comp.rpc.Service.response(data);
    }

    protected void response(JsonObject data)
    {
        com.ar.comp.rpc.Service.response(data);
    }

    protected void response(Boolean data)
    {
        com.ar.comp.rpc.Service.response(data);
    }

    protected void response(Float data)
    {
        com.ar.comp.rpc.Service.response(data);
    }

    protected void response()
    {
        com.ar.comp.rpc.Service.response();
    }

}
