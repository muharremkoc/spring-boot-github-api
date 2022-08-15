package com.github.springbootgitopenapi.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

public interface RepositoriesService {

     String cloneRepository(@RequestParam(name = "url") String url) throws IOException, InterruptedException;
}
