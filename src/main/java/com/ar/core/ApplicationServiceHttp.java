package com.ar.core;

import com.ar.comp.hash.Cipher;
import com.ar.comp.rpc.Service;
import com.ar.comp.util.Tools;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ApplicationServiceHttp {

    public void start() throws ServiceException {
        HashMap<String, String> data = parseHttpServiceHanlder();

        String cName = data.get("class");
        Class<?> c = null;

        String params = data.get("param");

        JsonParser parser = new JsonParser();
        JsonArray paramsArray = parser.parse(params).getAsJsonArray();

        int pLength = paramsArray.size();

        String workerName = data.get("method").trim() + "Worker";
        String className = "service." + Tools.trimStringWith(cName, "Ws.");

        Object obj = null;
        try {
            c = Class.forName(className);
            obj= c.getDeclaredConstructor().newInstance();


        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
            System.out.println(className);
            System.out.println("ClassNotFoundException");

            throw new ServiceException("ws service do not hava a class " + className);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
            System.out.println(workerName);
            System.out.println("NoSuchMethodException");
        }

        if (pLength == 0) {
            Method method = null;
            try {
                method = c.getMethod (workerName);
                try {
                    method.invoke (obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                throw new ServiceException("ws service do not hava a method " + workerName);
            }
        } else if (pLength == 1) {
            try {
                Method method = null;
                method = c.getMethod (workerName, String.class);
                try {

                    Object[] argsArr = new Object[pLength];
                    for (int i = 0; i < pLength; i++) {
                        argsArr[i] = paramsArray.get(i).getAsString().trim();
                    }

                    method.invoke (obj, argsArr);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                throw new ServiceException("ws service do not hava a method " + workerName);
            }

        } else if (pLength == 2) {

            try {
                Method method = null;
                method = c.getMethod (workerName, String.class, String.class);
                try {

                    Object[] argsArr = new Object[pLength];
                    for (int i = 0; i < pLength; i++) {
                        argsArr[i] = paramsArray.get(i).getAsString().trim();
                    }

                    method.invoke (obj, argsArr);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                throw new ServiceException("ws service do not hava a method " + workerName);
            }

        } else if (pLength == 3) {

            try {
                Method method = null;
                method = c.getMethod (workerName, String.class,String.class,String.class);
                try {

                    Object[] argsArr = new Object[pLength];
                    for (int i = 0; i < pLength; i++) {
                        argsArr[i] = paramsArray.get(i).getAsString().trim();
                    }

                    method.invoke (obj, argsArr);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                throw new ServiceException("ws service do not hava a method " + workerName);
            }

        } else if (pLength == 4) {
            try {
                Method method = null;
                method = c.getMethod (workerName, String.class, String.class, String.class, String.class);
                try {

                    Object[] argsArr = new Object[pLength];
                    for (int i = 0; i < pLength; i++) {
                        argsArr[i] = paramsArray.get(i).getAsString().trim();
                    }

                    method.invoke (obj, argsArr);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                throw new ServiceException("ws service do not hava a method " + workerName);
            }


        } else if (pLength == 5) {
            try {
                Method method = null;
                method = c.getMethod (workerName, String.class, String.class, String.class, String.class, String.class);
                try {

                    Object[] argsArr = new Object[pLength];
                    for (int i = 0; i < pLength; i++) {
                        argsArr[i] = paramsArray.get(i).getAsString().trim();
                    }

                    method.invoke (obj, argsArr);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                throw new ServiceException("ws service do not hava a method " + workerName);
            }


        } else if (pLength == 6) {
            try {
                Method method = null;
                method = c.getMethod (workerName, String.class, String.class, String.class, String.class, String.class, String.class);
                try {

                    Object[] argsArr = new Object[pLength];
                    for (int i = 0; i < pLength; i++) {
                        argsArr[i] = paramsArray.get(i).getAsString().trim();
                    }

                    method.invoke (obj, argsArr);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                throw new ServiceException("ws service do not hava a method " + workerName);
            }



        } else if (pLength == 7) {

            try {
                Method method = null;
                method = c.getMethod (workerName, String.class, String.class, String.class, String.class, String.class, String.class, String.class);
                try {

                    Object[] argsArr = new Object[pLength];
                    for (int i = 0; i < pLength; i++) {
                        argsArr[i] = paramsArray.get(i).getAsString().trim();
                    }

                    method.invoke (obj, argsArr);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                throw new ServiceException("ws service do not hava a method " + workerName);
            }

        } else if (pLength == 8) {
            try {
                Method method = null;
                method = c.getMethod (workerName, String.class,
                        String.class, String.class,
                        String.class, String.class,
                        String.class, String.class, String.class);
                try {

                    Object[] argsArr = new Object[pLength];
                    for (int i = 0; i < pLength; i++) {
                        argsArr[i] = paramsArray.get(i).getAsString().trim();
                    }

                    method.invoke (obj, argsArr);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                throw new ServiceException("ws service do not hava a method " + workerName);
            }

        }
    }

    protected void runSerice(HashMap<String, String> data)
    {



    }


    protected HashMap<String, String> parseHttpServiceHanlder()
    {
//        String ws = "7B2274797065223A226F626A656374222C2264617461223A7B22636C617373223A227365727665722E63746C2E7A6A78662E55736572222C226D6574686F64223A227265616446696C654C697374222C22706172616D223A5B223633222C2245453A35373A46383A31303A44333A4145222C22363057532D656535376638313064336165225D2C22617574685369676E223A7B22415554485F4749545F4F504B4559223A2261626364616C61616C3132336C61666C313261646E67616C616C61646E696F31323330333132222C22415554485F5345525645525F4F504B4559223A22736572616167616C646E69616C616C6473686761646C31323331326C61736466616161222C22415554485F5345525645525F544F4F4C4B4559223A2274736572616167616C646E69616C41757468546B6579313268686861736466616161227D2C22434C49454E545F534552564552223A7B224D494244495253223A225C2F78616D70705C2F7068705C2F6578747261735C2F6D696273222C224D5953514C5F484F4D45223A225C5C78616D70705C5C6D7973716C5C5C62696E222C224F50454E53534C5F434F4E46223A225C2F78616D70705C2F6170616368655C2F62696E5C2F6F70656E73736C2E636E66222C225048505F504541525F535953434F4E465F444952223A225C5C78616D70705C5C706870222C225048505243223A225C5C78616D70705C5C706870222C22544D50223A225C5C78616D70705C5C746D70222C22485454505F484F5354223A226C6F63616C686F7374222C22485454505F434F4E4E454354494F4E223A226B6565702D616C697665222C22434F4E54454E545F4C454E475448223A22313037222C22485454505F43414348455F434F4E54524F4C223A226D61782D6167653D30222C22485454505F5345435F43485F5541223A225C224368726F6D69756D5C223B763D5C2239325C222C205C22204E6F7420413B4272616E645C223B763D5C2239395C222C205C22476F6F676C65204368726F6D655C223B763D5C2239325C22222C22485454505F5345435F43485F55415F4D4F42494C45223A223F30222C22485454505F555047524144455F494E5345435552455F5245515545535453223A2231222C22485454505F4F524947494E223A22687474703A5C2F5C2F6C6F63616C686F7374222C22434F4E54454E545F54595045223A226170706C69636174696F6E5C2F782D7777772D666F726D2D75726C656E636F646564222C22485454505F555345525F4147454E54223A224D6F7A696C6C615C2F352E30202857696E646F7773204E542031302E303B2057696E36343B2078363429204170706C655765624B69745C2F3533372E333620284B48544D4C2C206C696B65204765636B6F29204368726F6D655C2F39322E302E343531352E313331205361666172695C2F3533372E3336222C22485454505F414343455054223A22746578745C2F68746D6C2C6170706C69636174696F6E5C2F7868746D6C2B786D6C2C6170706C69636174696F6E5C2F786D6C3B713D302E392C696D6167655C2F617669662C696D6167655C2F776562702C696D6167655C2F61706E672C2A5C2F2A3B713D302E382C6170706C69636174696F6E5C2F7369676E65642D65786368616E67653B763D62333B713D302E39222C22485454505F5345435F46455443485F53495445223A2273616D652D6F726967696E222C22485454505F5345435F46455443485F4D4F4445223A226E61766967617465222C22485454505F5345435F46455443485F55534552223A223F31222C22485454505F5345435F46455443485F44455354223A22646F63756D656E74222C22485454505F52454645524552223A22687474703A5C2F5C2F6C6F63616C686F73745C2F7A68696A69616F78756566754261636B67726F756E645C2F617231305F7A6A78665C2F636C69656E745C2F222C22485454505F4143434550545F454E434F44494E47223A22677A69702C206465666C6174652C206272222C22485454505F4143434550545F4C414E4755414745223A227A682D434E2C7A683B713D302E39222C22485454505F434F4F4B4945223A22486D5F6C76745F31323833313062623936643766643333323330393562633035313630386633623D313631343233393932353B205F626C5F7569643D586D6B4F326C33706B61706B3564726A68746B37723273664F4F746A3B205048505345535349443D716164673168646C746434763569376472366E63366F33716E32222C2250415448223A22433A5C5C57696E646F77735C5C73797374656D33323B433A5C5C57696E646F77733B433A5C5C57696E646F77735C5C53797374656D33325C5C5762656D3B433A5C5C57696E646F77735C5C53797374656D33325C5C57696E646F7773506F7765725368656C6C5C5C76312E305C5C3B433A5C5C57696E646F77735C5C53797374656D33325C5C4F70656E5353485C5C3B433A5C5C50726F6772616D2046696C65735C5C4F70656E56504E5C5C62696E3B433A5C5C50726F6772616D2046696C65735C5C4769745C5C636D643B433A5C5C55736572735C5C41646D696E6973747261746F725C5C417070446174615C5C4C6F63616C5C5C50726F6772616D735C5C507974686F6E5C5C507974686F6E33393B433A5C5C50726F6772616D2046696C65735C5C546F72746F6973654769745C5C62696E3B433A5C5C50726F6772616D2046696C65732028783836295C5C57696E646F7773204B6974735C5C382E315C5C57696E646F777320506572666F726D616E636520546F6F6C6B69745C5C3B433A5C5C50726F6772616D2046696C65735C5C6E6F64656A735C5C3B433A5C5C55736572735C5C41646D696E6973747261746F725C5C417070446174615C5C4C6F63616C5C5C4D6963726F736F66745C5C57696E646F7773417070733B433A5C5C55736572735C5C41646D696E6973747261746F725C5C417070446174615C5C4C6F63616C5C5C50726F6772616D735C5C4D6963726F736F667420565320436F64655C5C62696E3B433A5C5C55736572735C5C41646D696E6973747261746F725C5C417070446174615C5C526F616D696E675C5C6E706D3B433A5C5C6164623B433A5C5C50726F6772616D2046696C65732028783836295C5C4E6574536172616E675C5C5866747020375C5C3B433A5C5C50726F6772616D2046696C65732028783836295C5C4E6574536172616E675C5C587368656C6C20375C5C3B433A5C5C6368726F6D652D6472697665723B433A5C5C55736572735C5C41646D696E6973747261746F725C5C417070446174615C5C4C6F63616C5C5C4D6963726F736F66745C5C57696E646F7773417070733B433A5C5C55736572735C5C41646D696E6973747261746F725C5C417070446174615C5C4C6F63616C5C5C50726F6772616D735C5C4D6963726F736F667420565320436F64655C5C62696E3B433A5C5C55736572735C5C41646D696E6973747261746F725C5C417070446174615C5C526F616D696E675C5C6E706D3B433A5C5C78616D70705C5C7068703B222C2253797374656D526F6F74223A22433A5C5C57696E646F7773222C22434F4D53504543223A22433A5C5C57696E646F77735C5C73797374656D33325C5C636D642E657865222C2250415448455854223A222E434F4D3B2E4558453B2E4241543B2E434D443B2E5642533B2E5642453B2E4A533B2E4A53453B2E5753463B2E5753483B2E4D5343222C2257494E444952223A22433A5C5C57696E646F7773222C225345525645525F5349474E4154555245223A223C616464726573733E4170616368655C2F322E342E3433202857696E363429204F70656E53534C5C2F312E312E3167205048505C2F372E342E3720536572766572206174206C6F63616C686F737420506F72742038303C5C2F616464726573733E5C6E222C225345525645525F534F465457415245223A224170616368655C2F322E342E3433202857696E363429204F70656E53534C5C2F312E312E3167205048505C2F372E342E37222C225345525645525F4E414D45223A226C6F63616C686F7374222C225345525645525F41444452223A223A3A31222C225345525645525F504F5254223A223830222C2252454D4F54455F41444452223A223A3A31222C22444F43554D454E545F524F4F54223A22433A5C2F706870222C22524551554553545F534348454D45223A2268747470222C22434F4E544558545F505245464958223A22222C22434F4E544558545F444F43554D454E545F524F4F54223A22433A5C2F706870222C225345525645525F41444D494E223A22706F73746D6173746572406C6F63616C686F7374222C225343524950545F46494C454E414D45223A22433A5C2F7068705C2F7A68696A69616F78756566754261636B67726F756E645C2F617231305F7A6A78665C2F636C69656E745C2F696E6465782E706870222C2252454D4F54455F504F5254223A223631363930222C22474154455741595F494E54455246414345223A224347495C2F312E31222C225345525645525F50524F544F434F4C223A22485454505C2F312E31222C22524551554553545F4D4554484F44223A22504F5354222C2251554552595F535452494E47223A22222C22524551554553545F555249223A225C2F7A68696A69616F78756566754261636B67726F756E645C2F617231305F7A6A78665C2F636C69656E745C2F222C225343524950545F4E414D45223A225C2F7A68696A69616F78756566754261636B67726F756E645C2F617231305F7A6A78665C2F636C69656E745C2F696E6465782E706870222C225048505F53454C46223A225C2F7A68696A69616F78756566754261636B67726F756E645C2F617231305F7A6A78665C2F636C69656E745C2F696E6465782E706870222C22524551554553545F54494D455F464C4F4154223A313632393235333031382E3534382C22524551554553545F54494D45223A313632393235333031387D7D7D";

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String ws = request.getParameter("ws");

        JsonObject decrypt = Service.decrypt(ws);

        Ar.setConfig("RAW_CLIENT_REQUEST", decrypt.toString());

//        System.out.println(decrypt);


        HashMap<String, String> objectObjectHashMap = new HashMap<>();

        objectObjectHashMap.put("class", decrypt.get("class").getAsString());
        objectObjectHashMap.put("method", decrypt.get("method").getAsString());
        objectObjectHashMap.put("param", decrypt.get("param").toString());
        objectObjectHashMap.put("authSign", decrypt.get("authSign").toString());
        objectObjectHashMap.put("CLIENT_SERVER", decrypt.get("CLIENT_SERVER").toString());

//        System.out.println(objectObjectHashMap);

        return objectObjectHashMap;

    }


}
