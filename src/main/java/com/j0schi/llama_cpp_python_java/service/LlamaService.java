package com.j0schi.llama_cpp_python_java.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j0schi.llama_cpp_python_java.model.MessageRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@AllArgsConstructor
public class LlamaService {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public String test(){

        String url = "http://localhost:8000/llama2";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        // Получаем тело ответа
        String responseBody = responseEntity.getBody();

        // Получаем статус код
        int statusCode = responseEntity.getStatusCodeValue();

        // Получаем заголовки ответа
        HttpHeaders headers = responseEntity.getHeaders();

        // Далее вы можете обработать полученные данные
        System.out.println("Response Body: " + responseBody);
        System.out.println("Status Code: " + statusCode);
        System.out.println("Headers: " + headers);

        // Обработка responseBody:
        String result = assembleString(responseBody);
        log.info(result);
        return result;
    }


    public String executePostRequest(String message, String url) throws JsonProcessingException {

        MessageRequest messageDto = new MessageRequest();
        messageDto.setMessage(message);

        // Устанавливаем заголовки запроса (Content-Type: application/json)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Преобразуем объект в JSON строку
        String jsonBody = objectMapper.writeValueAsString(messageDto);

        // Создаем HttpEntity с JSON телом запроса и заголовками
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        // Выполняем POST-запрос с использованием RestTemplate.exchange()
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Получаем тело ответа
        String response = responseEntity.getBody();

        response = assembleString(response);

        log.info(response);

        return response;
    }

    public static String assembleString(String data) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] lines = data.split("\n");

        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.startsWith("data:")) {
                String text = trimmedLine.substring("data:".length()).trim();
                stringBuilder.append(text).append(" ");
            }
        }

        return stringBuilder.toString().trim();
    }

}

