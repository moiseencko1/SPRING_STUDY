package com.example.springstudy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Acer on 22.05.2017.
 */
@RestController
public class ResponseController {
    private final String[] strings = new String[5];

    public ResponseController() {
        strings[0] = "VOT-ETA-DA";
        strings[1] = "VOLODYA";
        strings[2] = "SECRET-WORD";
        strings[3] = "JAVA-RESPECT";
        strings[4] = "GREEN DAY";
    }

    @RequestMapping("/get-word")
    public String surprise(int key) {
        return key >= 0 & key < 5 ? strings[key] : "ops - trouble";
    }
}


