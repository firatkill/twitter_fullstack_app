package com.x.twitter.Controller;

import com.x.twitter.Model.DTO.Auth.Authrequest;
import com.x.twitter.Model.DTO.Auth.CreateUserRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public void create(@RequestBody CreateUserRequest createUserRequest){

    }
    @PostMapping("/login")
    public void login(@RequestBody Authrequest authrequest){

    }
}
