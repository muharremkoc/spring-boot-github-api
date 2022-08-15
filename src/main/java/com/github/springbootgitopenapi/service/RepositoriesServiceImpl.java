package com.github.springbootgitopenapi.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepositoriesServiceImpl implements RepositoriesService{

    @Value("${output.path}")
    String FIRST_OUTPUT;

    @Override
    public String cloneRepository(String url) throws IOException, InterruptedException {
        Process process=Runtime.getRuntime().exec(String.format("cmd.exe /c git clone %s",url),
                null,
                new File(FIRST_OUTPUT));
        System.out.println(process.getOutputStream());
        int exitVal = process.waitFor();

        if (exitVal == 0) System.out.println("shell command success");
        process.destroy();
        return "shell execute";
    }
}
