package com.oswin.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.oswin.model.Coin;
import com.oswin.model.Demo2;
import com.oswin.model.ForeignCurrency;
import com.oswin.repository.CoinRepository;
import com.oswin.repository.Demo2Repository;
import com.oswin.service.ExchangeRatesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CoinDeskController {

    private static final Logger LOGGER = LogManager.getLogger(CoinDeskController.class);

    @Resource
    private CoinRepository coinRepository;

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello";
    }

    @Transactional
    @RequestMapping(value = "/findCoin", method = RequestMethod.GET)
    public List<Coin> findCoin() {
        return coinRepository.findAll();
    }

    @RequestMapping(value = "/example/coindesk", method = RequestMethod.GET)
    public String getexampleCoindesk() {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        Gson gson = new Gson();
        ResponseEntity response = restTemplate.getForEntity(url, String.class);
        return response.getStatusCode().value() == 200 ? response.getBody().toString() : "Failed";

    }

    @RequestMapping(value = "/convert/coindesk", method = RequestMethod.GET)
    public Coin convertCoindesk() {
        String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
        Gson gson = new Gson();
        ResponseEntity response = restTemplate.getForEntity(url, String.class);
        return exchangeRatesService.getCoin(response.getBody().toString());
    }

    @RequestMapping(value = "/coindesk", method = RequestMethod.GET)
    public List<Coin> getCoindesk() {
        return coinRepository.findAll();
    }

    @RequestMapping(value = "/v2/coindesk", method = RequestMethod.GET, produces = "application/json")
    public String getV2Coindesk() {
        LOGGER.info(exchangeRatesService.getCoin(coinRepository.findAll()));
        return exchangeRatesService.getCoin(coinRepository.findAll());
    }

    @Transactional
    @RequestMapping(value = "/coindesk", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateCoindesk(@RequestBody Coin coin) {
        int flag = coinRepository.update(coin.getChartName(), coin.getDisclaimer(), coin.getId());
        if (flag == 1) {
            return new ResponseEntity(coinRepository.findById(coin.getId()), HttpStatus.OK);
        } else if (flag == 0) {
            return new ResponseEntity("not found data", HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @RequestMapping(value = "/coindesk/{id}", method = RequestMethod.DELETE)
    public String deleteCoindesk(@PathVariable String id) {
        return coinRepository.deleteById(id) == 1 ? "success" : "failed";
    }
}
