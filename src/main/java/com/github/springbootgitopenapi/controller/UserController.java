package com.github.springbootgitopenapi.controller;

import com.github.springbootgitopenapi.model.GithubResponse;
import com.github.springbootgitopenapi.model.User;
import com.github.springbootgitopenapi.model.UsersResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

     final RestTemplate restTemplate;

     final String GITHUB_PATH="https://api.github.com/";

/*     @GetMapping("/users")
     public List<User> searchUser(@RequestParam(name = "q") String queries){
         ResponseEntity<UsersResponse> forEntity=restTemplate.getForEntity(String.format(GITHUB_PATH+"/search/users?q=%s",queries), UsersResponse.class);
         return forEntity.getBody().getItems();
     }*/

    @GetMapping("/users/{login}")
    public User getUser(@PathVariable("login") String login){
        ResponseEntity<User> forEntity = restTemplate.getForEntity(String.format(GITHUB_PATH+"users/%s", login), User.class);
        return forEntity.getBody();
    }
}
