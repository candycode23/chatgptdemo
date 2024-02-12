package com.outencandice.chatgptdemo.chatgptbot;

import com.outencandice.chatgptdemo.FormInputDTO;
import com.outencandice.chatgptdemo.OpenAiApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
//@RequestMapping("/api.openai.com/v1/completions")
//@CrossOrigin("*")
public class ChatGptController {

    private static final String MAIN_PAGE = "index";

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private OpenAiApiClient client;

    private String chatWithGpt3(String message) throws Exception {
        CompletionRequest completion = CompletionRequest.defaultWith(message);
        String postBodyJson = jsonMapper.writeValueAsString(completion);
        String responseBody = client.postToOpenAiApi(postBodyJson, OpenAiApiClient.OpenAiService.GPT_3);
        CompletionResponse completionResponse = jsonMapper.readValue(responseBody, CompletionResponse.class);
        return completionResponse.firstAnswer().orElseThrow();
    }

    @GetMapping(path = "/")
    public String index() {
        return MAIN_PAGE;
    }

    @PostMapping(path = "/")
    public String chat(Model model, @ModelAttribute FormInputDTO dto) {
        try {
            model.addAttribute("request", dto.prompt());
            model.addAttribute("response", chatWithGpt3(dto.prompt()));
        } catch (Exception e) {
            model.addAttribute("response", "Error in communication with OpenAI ChatGPT API.");
        }



        return MAIN_PAGE;
    }
}


