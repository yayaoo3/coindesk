package com.oswin.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
public class CoinDeskController {

    private static final Logger LOGGER = LogManager.getLogger(CoinDeskController.class);



    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello";
    }

}
