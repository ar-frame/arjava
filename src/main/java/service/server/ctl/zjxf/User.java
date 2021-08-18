package service.server.ctl.zjxf;

import com.ar.core.HttpService;

public class User extends HttpService {
    public void readFileListWorker(String s1, String s2, String s3)
    {
        response("this is res 3");
    }

    public void readFileListWorker(String s1)
    {
        response("this is res 1");
    }

    public void readFileListWorker()
    {
        response("this is res 0");
    }
}
