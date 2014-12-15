package com.liqpay;

import java.security.MessageDigest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LiqPayUtil {
    public static byte[] sha1(String param) {
        MessageDigest SHA = null;
        try {
            SHA = MessageDigest.getInstance("SHA-1");
            SHA.reset();
            SHA.update(param.getBytes("UTF-8"));
        } catch (Exception e) {
            System.err.println(e);
        }
        return SHA.digest();
    }

    public static String base64_encode(byte[] bytes) {
        String str = DatatypeConverter.printBase64Binary(bytes);
        return str;
    }

    public static ArrayList<Object> getArray(Object object2) throws ParseException {
        ArrayList<Object> list = new ArrayList<Object>();
        JSONArray jsonArr = (JSONArray) object2;
        for (Object aJsonArr : jsonArr) {
            if (aJsonArr instanceof JSONObject) {
                list.add(parseJson((JSONObject) aJsonArr));
            } else {
                list.add(aJsonArr);
            }
        }
        return list;
    }


    public static HashMap<String, Object> parseJson(JSONObject jsonObject) throws ParseException {
        HashMap<String, Object> data = new HashMap<String, Object>();
        @SuppressWarnings("unchecked")
        Set<Object> set = jsonObject.keySet();
        for (Object obj : set) {
            if (jsonObject.get(obj) instanceof JSONArray) {
                data.put(obj.toString(), getArray(jsonObject.get(obj)));
            } else {
                if (jsonObject.get(obj) instanceof JSONObject) {
                    data.put(obj.toString(), parseJson((JSONObject) jsonObject.get(obj)));
                } else {
                    data.put(obj.toString(), jsonObject.get(obj));
                }
            }
        }
        return data;
    }
}
