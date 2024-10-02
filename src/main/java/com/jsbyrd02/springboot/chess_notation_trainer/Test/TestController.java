package com.jsbyrd02.springboot.chess_notation_trainer.Test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/")
    public String test() {
        return "Hello World!";
    }
}
