package system.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.client.HttpClientErrorException;
import java.util.Map;

@RestController
@RequestMapping("/projectaiapi")
@CrossOrigin
public class AIController {

    //private final String API_KEY = "API KEY"; // <-- paste your key here


@PostMapping
public String askAI(@RequestBody Map<String, String> body) {
    String prompt = body.get("prompt");
    String url = "https://api.openai.com/v1/chat/completions";

    Map<String, Object> request = Map.of(
            "model", "gpt-3.5-turbo",
            "messages", new Object[]{ Map.of("role", "user", "content", prompt) }
    );

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(API_KEY);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
    RestTemplate restTemplate = new RestTemplate();

    try {
        Map response = restTemplate.postForObject(url, entity, Map.class);
        var choices = (java.util.List) response.get("choices");
        Map firstChoice = (Map) choices.get(0);
        Map message = (Map) firstChoice.get("message");
        return (String) message.get("content");
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode().value() == 429) {
            return "API quota exceeded. Please check your OpenAI account or billing plan.";
        }
        e.printStackTrace();
        return "Error calling OpenAI API";
    } catch (Exception e) {
        e.printStackTrace();
        return "Error parsing ChatGPT response";
    }
}
}