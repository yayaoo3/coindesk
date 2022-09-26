package com.oswin.controller;

import com.oswin.model.Demo2;
import com.oswin.repository.Demo2Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DemoController {

    private static final Logger LOGGER = LogManager.getLogger(DemoController.class);
    @Resource
    private Demo2Repository demo2Repository;
    @Transactional
    @RequestMapping(value = "/addDemo", method = RequestMethod.POST)
    public String addDemo(@RequestBody Demo2 demo) {
        LOGGER.info("addDemo: {}",demo);
        //demoRepository.save(demo);
        demo2Repository.save(demo);
        return "Hello";
    }

    @Transactional
    @RequestMapping(value = "/findDemo", method = RequestMethod.GET)
    public List<Demo2> findDemo() {
        return demo2Repository.findAll();
    }

    @Transactional
    @RequestMapping(value = "/deleteDemo/{id}", method = RequestMethod.DELETE)
    public String deleteDemo(@PathVariable String id) {
        return demo2Repository.deleteById(id) == 1 ? "success" : "failed" ;
    }

    @Transactional
    @RequestMapping(value = "/updateDemo", method = RequestMethod.PUT)
    public String updateDemo(@RequestBody Demo2 demo) {
        return demo2Repository.update(demo.getName(),demo.getType(),demo.getId()) == 1 ? "success" : "failed" ;
    }
}
