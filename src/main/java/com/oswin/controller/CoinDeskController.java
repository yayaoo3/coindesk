package com.oswin.controller;

import com.oswin.model.Coin;
import com.oswin.repository.CoinRepository;
import com.oswin.service.CoindeskService;
import com.oswin.service.ExchangeRatesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private CoindeskService coindeskService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello";
    }


    @RequestMapping(value = "/example/coindesk", method = RequestMethod.GET, produces = "application/json")
    public String getexampleCoindesk() {
        return coindeskService.getCoindeskAPI();
    }

    @RequestMapping(value = "/convert/coindesk", method = RequestMethod.GET, produces = "application/json")
    public String convertCoindesk() {
        return coindeskService.getConvertCoin();
    }

    @RequestMapping(value = "/coindesk", method = RequestMethod.GET)
    public List<Coin> getCoindesk() {
        return coindeskService.getCoinList();
    }

    @RequestMapping(value = "/coindesk/{id}", method = RequestMethod.GET)
    public Coin getCoindeskById(@PathVariable String id) {
        return coindeskService.getCoinById(id);
    }

    @RequestMapping(value = "/v2/coindesk", method = RequestMethod.GET, produces = "application/json")
    public String getV2Coindesk() {
        return coindeskService.getV2Coin();
    }

    @RequestMapping(value = "/v2/coindesk/{id}", method = RequestMethod.GET,produces = "application/json")
    public String getV2CoindeskById(@PathVariable String id) {
        return coindeskService.getV2CoinById(id);
    }

    @Transactional
    @RequestMapping(value = "/coindesk", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity addCoindesk(@RequestBody Coin coin) {
        int flag = coindeskService.addCoin(coin);
        if (flag == 1) {
            return new ResponseEntity("success", HttpStatus.OK);
        } else {
            return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @RequestMapping(value = "/coindesk", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateCoindesk(@RequestBody Coin coin) {
        int flag = coindeskService.updateCoin(coin);
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
        return coindeskService.deleteCoin(id) == 1 ? "success" : "failed";
    }
}
