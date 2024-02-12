package com.outencandice.chatgptdemo.chatgptbot;

public record CompletionRequest(String model, String prompt, double temperature, int max_tokens) {

    public static CompletionRequest defaultWith(String prompt){
        return new CompletionRequest("text-davinci-003", "You are a credit advisor. Only answer questions related to credit. If question is not about credit advise not able to assist.  " + prompt, 0.7, 300);

    }

}
