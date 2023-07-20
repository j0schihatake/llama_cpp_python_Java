package com.j0schi.llama_cpp_python_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class LlamaCppPythonJavaApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LlamaCppPythonJavaApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8083"));
        app.run(args);
    }

}
