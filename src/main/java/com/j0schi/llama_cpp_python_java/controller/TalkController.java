package com.j0schi.llama_cpp_python_java.controller;

import com.j0schi.llama_cpp_python_java.model.MessageRequest;
import com.j0schi.llama_cpp_python_java.utils.PythonExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TalkController {

    private final PythonExecutor pythonExecutor;

    @PostMapping("/talk")
    public ResponseEntity<String> llama(@RequestBody MessageRequest request) {
        String message = request.getMessage();
        int maxTokens = 300;
        String[] stop = new String[]{"\n", " Q:"};
        boolean echo = true;

        String response = pythonExecutor.runLlm(message, maxTokens, stop, echo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}