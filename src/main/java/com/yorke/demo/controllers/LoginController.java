package com.objectiva.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    @PostMapping("/successful")
    public ResponseEntity loginSuccess(){
        log.info("Get back some information after login successfully.");
        return ResponseEntity.ok("Login successfully!");
    }
    @PostMapping("/failed")
    public ResponseEntity loginFailed(){
        log.info("Get back some error message after login failed.");
        return ResponseEntity.ok("Login failed!");
    }
}
