package com.j0schi.llama_cpp_python_java.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j0schi.llama_cpp_python_java.model.MessageRequest;
import com.j0schi.llama_cpp_python_java.service.LlamaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class TalkController {

    String apiUrl = "http://localhost:8000/talk";

    private final LlamaService llamaService;

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>(llamaService.test(), HttpStatus.OK);
    }

    @PostMapping("/talk")
    public ResponseEntity<String> llama(@RequestBody MessageRequest request) throws JsonProcessingException {
        String message = request.getMessage();
        return new ResponseEntity<>(llamaService.executePostRequest(message, apiUrl), HttpStatus.OK);
    }
}