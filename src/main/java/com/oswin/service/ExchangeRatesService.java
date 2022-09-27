package com.oswin.service;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.oswin.model.Coin;
import com.oswin.model.ForeignCurrency;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExchangeRatesService {
    private static final Logger LOGGER = LogManager.getLogger(ExchangeRatesService.class);

    @Autowired
    private Gson gson;


    public Coin getCoin(String result) {
        Coin coin = new Coin();
        JsonObject data = gson.fromJson(result, JsonObject.class);
        LOGGER.info(result);

        coin.setChartName(data.getAsJsonPrimitive("chartName").getAsString());
        coin.setDisclaimer(data.getAsJsonPrimitive("disclaimer").getAsString());

        JsonObject time = data.getAsJsonObject("time");
        String date = time.getAsJsonPrimitive("updatedISO").getAsString();
        coin.setUpdatedTime(convertStringToDate(date));
        LOGGER.debug(covertDateFormat(date));

        JsonObject bpi = data.getAsJsonObject("bpi");
        LOGGER.info(bpi);
        Iterator<String> keys = bpi.keySet().iterator();
        LOGGER.info(keys);
        while (keys.hasNext()) {
            String key = keys.next();
            if (bpi.getAsJsonObject(key) instanceof JsonObject) {
                // do something with jsonObject here
                LOGGER.info(key + ":" + bpi.getAsJsonObject(key));
                LOGGER.info(bpi.getAsJsonObject(key).toString());
                ForeignCurrency fc = gson.fromJson(bpi.getAsJsonObject(key).toString(),ForeignCurrency.class);
                LOGGER.info(fc);
                coin.getForeignCurrency().add(fc);
            }
        }
        return coin;
    }


    public String convertDateToString(Date dt) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8")); //Specify time zone
        String dateToString = df.format(dt);
        return dateToString;
    }

    public String covertDateFormat(String date) {
        org.joda.time.format.DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
        DateTime time = parser2.parseDateTime(date);
        return convertDateToString(time.toDate());
    }
    public Date convertStringToDate(String date) {
        org.joda.time.format.DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
        DateTime time = parser2.parseDateTime(date);
        return time.toDate();
    }


    public String getCoin(List<Coin> coin) {
        JsonArray data = gson.fromJson(gson.toJson(coin), JsonArray.class);

        return data.toString();
    }

}
