package com.j0schi.llama_cpp_python_java.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j0schi.llama_cpp_python_java.utils.PythonExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Bean
    public PythonExecutor pythonExecutor(){
        return new PythonExecutor();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
