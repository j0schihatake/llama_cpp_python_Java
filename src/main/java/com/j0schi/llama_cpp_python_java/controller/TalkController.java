package com.j0schi.llama_cpp_python_java.controller;

import com.j0schi.llama_cpp_python_java.model.MessageRequest;
import org.json.JSONObject;
import org.python.core.PySequenceList;
import org.python.util.PythonInterpreter;
import org.python.core.PyObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TalkController {

    @PostMapping("/talk")
    public ResponseEntity<JSONObject> talk(@RequestBody MessageRequest request) {
        String message = request.getMessage();

        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from llama import llm");

        interpreter.set("message", message);
        interpreter.exec("result = llm(message)");

        PyObject result = interpreter.get("result");

        interpreter.close();

        if (result != null && result.isSequenceType()) {
            PySequenceList sequenceList = (PySequenceList) result;
            StringBuilder responseBuilder = new StringBuilder();
            for (PyObject item : sequenceList.asIterable()) {
                responseBuilder.append(item.toString()).append(" ");
            }

            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("response", responseBuilder.toString().trim());

            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}