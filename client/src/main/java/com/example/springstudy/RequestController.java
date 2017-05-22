package com.example.springstudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Acer on 22.05.2017.
 */
@RestController
public class RequestController {
    @Autowired
    private FeignInterface feignInterface;


    @RequestMapping("/heyService")
    public String getSomething(@RequestParam("str")String string) {
        return feignInterface.surprise(Math.abs(string.hashCode()) % 5);
    }
}
