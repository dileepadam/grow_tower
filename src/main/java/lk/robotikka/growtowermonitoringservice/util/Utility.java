package lk.robotikka.growtowermonitoringservice.util;

import com.google.gson.Gson;

import java.util.Date;

public class Utility {

    public static String objectToJson(Object object) {

        return new Gson().toJson(object);
    }

    public static Date getSystemDate() {
        return new Date();
    }

}
