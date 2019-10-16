package com.booledata.llspringparent.controller;

import com.booledata.llspringparent.api.auth.AuthControllerApi;
import com.booledata.llspringparent.common.model.response.ResponseResult;
import com.booledata.llspringparent.model.ucenter.request.LoginRequest;
import com.booledata.llspringparent.model.ucenter.response.JwtResult;
import com.booledata.llspringparent.model.ucenter.response.LoginResult;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController implements AuthControllerApi {
    @Override
    @PostMapping("userLogin")
    public LoginResult login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    @PostMapping("/userLogout")
    public ResponseResult logout() {
        return null;
    }

    @Override
    @GetMapping("/getJwt")
    public JwtResult userjwt() {
        return null;
    }
}
