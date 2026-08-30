package com.taskmanager.api.facades;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpenAiServiceFacadeImpl implements AiServiceFacade {

    @Override
    public String generateTaskSummary(String prompt) {
        log.info("Facade intercepting core request. Building HTTP request for AI provider...");

        // ---------------------------------------------------------
        // COMPLEXITY SHIELDED FROM THE APPLICATION CORE:
        // 1. Fetching API Keys from environment
        // 2. Setting HTTP Headers (Authorization: Bearer ...)
        // 3. Constructing JSON payload (model, messages, temperature)
        // 4. Executing RestTemplate/WebClient HTTP POST
        // 5. Parsing the nested JSON response to extract the string
        // ---------------------------------------------------------

        log.info("Executing external API call to LLM...");

        // Mocking the returned AI response for our current build
        return "AI Summary generated for prompt: [" + prompt + "]. " +
                "This task requires immediate attention based on its status.";
    }
}