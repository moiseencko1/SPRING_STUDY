package com.example.springstudy;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Acer on 22.05.2017.
 */
@FeignClient("z")
public interface FeignInterface {

    @RequestMapping("/server/get-word")
    String surprise(@RequestParam("key") int key);
}
