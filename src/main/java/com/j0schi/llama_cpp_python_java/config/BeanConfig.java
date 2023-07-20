package com.j0schi.llama_cpp_python_java.config;

import com.j0schi.llama_cpp_python_java.utils.PythonExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public PythonExecutor pythonExecutor() {
        return new PythonExecutor();
    }
}
