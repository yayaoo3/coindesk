package com.oswin.service;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.oswin.util.MyException;
import com.oswin.model.Coin;
import com.oswin.model.ForeignCurrency;
import com.oswin.repository.CoinRepository;
import com.oswin.repository.ExchangeRatesRepository;
import com.oswin.util.CoinSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.oswin.util.CoinSpec.*;

@Service
public class CoindeskService {
    private static final Logger LOGGER = LogManager.getLogger(CoindeskService.class);

    @Autowired
    private Gson gson;

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private CoinRepository coinRepository;

    @Resource
    private ExchangeRatesRepository exchangeRatesRepository;

    public String getCoindeskAPI() {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        Gson gson = new Gson();
        ResponseEntity response = restTemplate.getForEntity(url, String.class);
        return response.getStatusCode().value() == 200 ? response.getBody().toString() : "Failed";
    }

    public String getConvertCoin() {
        String result = getCoindeskAPI();
        if (result.equals("Failed")) {
            return null;
        }
        Coin coin = new Coin();
        JsonObject data = gson.fromJson(result, JsonObject.class);
        LOGGER.debug(result);
        /**parse data from CoindeskAPI**/
        coin.setChartName(data.getAsJsonPrimitive("chartName").getAsString());
        //coin.setDisclaimer(data.getAsJsonPrimitive("disclaimer").getAsString());

        JsonObject time = data.getAsJsonObject("time");
        String date = time.getAsJsonPrimitive("updatedISO").getAsString();
        coin.setUpdatedTime(convertStringToDate(date));
        LOGGER.debug(covertDateFormat(date));

        JsonObject bpi = data.getAsJsonObject("bpi");
        LOGGER.debug(bpi);
//        /**動態解析外幣匯率**/
//        Iterator<String> keys = bpi.keySet().iterator();
//        LOGGER.info(keys);
//        while (keys.hasNext()) {
//            String key = keys.next();
//            if (bpi.getAsJsonObject(key) instanceof JsonObject) {
//                // do something with jsonObject here
//                LOGGER.info(key + ":" + bpi.getAsJsonObject(key));
//                ForeignCurrency fc = gson.fromJson(bpi.getAsJsonObject(key).toString(), ForeignCurrency.class);
//                LOGGER.info(fc);
//                coin.getForeignCurrency().add(fc);
//            }
//        }

        /**try to convert chinese name**/
        if (coin.getChineseName() == null || coin.getChineseName().equals("")) {
            coin.setChineseName(getCoinChineseName(coin.getChartName()));
        }

        JsonObject convertCoin = gson.fromJson(gson.toJson(coin), JsonObject.class);
        convertCoin.addProperty("updatedTime",covertDateFormat(date));
        convertCoin.remove("foreignCurrency");
        convertCoin.add("bpi",bpi);
        LOGGER.info(convertCoin);
        return convertCoin.toString();
    }

    public Coin getCoinById(String id) {
        Coin coin = coinRepository.findById(id);
        if (coin.getChineseName() == null || coin.getChineseName().equals("")) {
            coin.setChineseName(getCoinChineseName(coin.getChartName()));
        }
        return coin;
    }

    public List<Coin> getCoinList() {
        List<Coin> coinList =  coinRepository.findAll();
        for(Coin coin : coinList) {
            /**try to convert chinese name**/
            if (coin.getChineseName() == null || coin.getChineseName().equals("")) {
                coin.setChineseName(getCoinChineseName(coin.getChartName()));
            }
        }
        return coinList;
    }

    public int addCoin(Coin coin) {
        int f = 0;
        try {
            f = coinRepository.saveCoin(coin.getId(), coin.getChartName(),  coin.getChineseName());
            if (f == 0)
                return 0;
            for (ForeignCurrency fc : coin.getForeignCurrency()) {
                exchangeRatesRepository.saveForeignCurrency(coin.getId(), fc.getCode(), fc.getSymbol(), fc.getDescription(), fc.getRate_float());
            }
        }catch (Exception e){
            LOGGER.error(e);
            throw new MyException("add Coin ERROR");
        }

        return f;
    }

    public int deleteCoin(String id) {
        exchangeRatesRepository.deleteById(id);
        return coinRepository.deleteById(id);
    }

    public int updateCoin(Coin coin) {
        int f;
        f = coinRepository.update(coin.getChartName(), coin.getChineseName(), coin.getId());
        if (f == 0)
            return 0;
        for (ForeignCurrency fc : coin.getForeignCurrency()) {
            exchangeRatesRepository.update(fc.getSymbol(), fc.getDescription(), fc.getRate_float(), coin.getId(), fc.getCode());
        }
        return 1;
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


    public String getV2Coin() {
        List<Coin> coinList = getCoinList();
        JsonArray result = new JsonArray();
        for(Coin coin : coinList){
            result.add(v2ConvertCoin(coin));
        }
        return result.toString();
    }

    public String getV2CoinById(String id) {
        Coin coin = getCoinById(id);
        if(coin==null){
            return "not found";
        }

        return v2ConvertCoin(coin).toString();
    }

    public JsonObject v2ConvertCoin(Coin coin){
        JsonObject data = gson.fromJson(gson.toJson(coin), JsonObject.class);
        JsonArray foreignCurrency = data.getAsJsonArray("foreignCurrency");
        LOGGER.info(foreignCurrency);
        JsonObject bpi = new JsonObject();
        for(JsonElement json : foreignCurrency){
            LOGGER.info(json);
            LOGGER.info(json.getAsJsonObject().get("code").getAsString());
            JsonObject code = new JsonObject();
            code.addProperty("code",json.getAsJsonObject().get("code").getAsString());
            code.addProperty("symbol",json.getAsJsonObject().get("symbol").getAsString());
            code.addProperty("description",json.getAsJsonObject().get("description").getAsString());
            code.addProperty("rate_float",json.getAsJsonObject().get("rate_float").getAsDouble());
            bpi.add(json.getAsJsonObject().get("code").getAsString(),code);
        }
        data.add("bpi",bpi);
        data.remove("foreignCurrency");
        return data;
    }

    public static String getCoinChineseName(String ChartName) {
        try {
            CoinSpec coinSpec = CoinSpec.valueOf(ChartName);
            switch (coinSpec) {
                case Bitcoin:
                    return Bitcoin.getChineseName();
                case ETH:
                    return ETH.getChineseName();
                case Dogecoin:
                    return Dogecoin.getChineseName();
                case TestId:
                    return TestId.getChineseName();
            }
        }catch (Exception e){
            LOGGER.error(e);
        }
        return null;
    }


}
