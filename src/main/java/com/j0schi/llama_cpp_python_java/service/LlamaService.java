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


    /**
     * Пост запрос с текстом, возвращает предложение целиком.
     * @param message
     * @param url
     * @return
     * @throws JsonProcessingException
     */
    public String executePostRequest(MessageRequest message, String url) throws JsonProcessingException {

        // Устанавливаем заголовки запроса (Content-Type: application/json)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Преобразуем объект в JSON строку
        String jsonBody = objectMapper.writeValueAsString(message);

        // Создаем HttpEntity с JSON телом запроса и заголовками
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        String response = "";
        do {

            // Выполняем POST-запрос с использованием RestTemplate.exchange()
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // Получаем тело ответа
            response = responseEntity.getBody();

            response = assembleString(response);

        } while (!containsLetters(response));

        log.info(response);

        return response;
    }

    public static boolean containsLetters(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }

        return false;
    }

    public String assembleString(String data) {
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

