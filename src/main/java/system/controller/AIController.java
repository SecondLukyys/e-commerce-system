package system.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projectaiapi")
@CrossOrigin
public class AIController {

    private final String API_KEY = "KEY_HERE";

    @PostMapping
    public String askAI(@RequestBody Map<String, String> body) {

        String prompt = body.get("prompt");

        String url = "https://openrouter.ai/api/v1/chat/completions";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("HTTP-Referer", "http://localhost:8080");
        headers.set("X-Title", "My Portfolio App");
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> request = Map.of(
                "model", "qwen/qwen3.6-plus:free",
                "messages", new Object[]{
                        Map.of("role", "system", "content", "You are a helpful assistant."),
                        Map.of("role", "user", "content", prompt)
                },
                "max_tokens", 150,
                "temperature", 0.7
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            Map response = restTemplate.postForObject(url, entity, Map.class);

            List choices = (List) response.get("choices");
            Map firstChoice = (Map) choices.get(0);
            Map message = (Map) firstChoice.get("message");

            return (String) message.get("content");

        } catch (HttpClientErrorException e) {
            return "API error: " + e.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing response";
        }
    }
}