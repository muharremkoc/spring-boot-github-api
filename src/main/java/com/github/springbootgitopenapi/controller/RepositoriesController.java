package com.github.springbootgitopenapi.controller;

import com.github.springbootgitopenapi.model.CloneRepository;
import com.github.springbootgitopenapi.service.RepositoriesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepositoriesController {

     final RepositoriesService repositoriesService;

    Environment environment;

    final String GITHUB_PATH="https://api.github.com/";



    final RestTemplate restTemplate;

    final List<CloneRepository> clonesAPI=new ArrayList<>();


    @GetMapping("/repositories/{q}")
    public List<CloneRepository> repositories(@PathVariable(name = "q") String query) {
        JSONArray root = new JSONArray(callAPI(query));
        for (int i=0;i<root.length();i++){
            String name = root.getJSONObject(i).get("name").toString();
            String clone_url = root.getJSONObject(i).get("clone_url").toString();
            String created_date = root.getJSONObject(i).get("created_at").toString();
          CloneRepository cloneRepository= CloneRepository.builder().created_date(created_date).name(name).cloneUrl(clone_url).build();
           clonesAPI.add(cloneRepository);
        }
      return clonesAPI;
    }



    private String callAPI(String query){
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity=new HttpEntity<>(headers);

        return restTemplate.exchange(GITHUB_PATH+"users/"+query+"/repos", HttpMethod.GET,entity,String.class).getBody();
    }

    @PostMapping("/cloned/repository")
    public String cloneRepository(@RequestParam(name = "url") String url) throws IOException, InterruptedException {
          return repositoriesService.cloneRepository(url);
    }

    @GetMapping("/download/repository")
    public void downloadRepositoryWithZip(@RequestParam(name = "name") String name, @RequestParam(name = "repoName") String repoName, HttpServletResponse response) throws IOException, InterruptedException {
        InputStream in = new URL(GITHUB_PATH+"repos/"+name+"/"+repoName+"/zipball").openStream();
        byte[] bytes = IOUtils.toByteArray(in);

        response.setContentType("APPLICATION/DOWNLOAD");
        response.setHeader("Content-disposition", "attachment; filename=\""+repoName+".zip\"");
        response.getOutputStream().write(bytes);

    }
}
