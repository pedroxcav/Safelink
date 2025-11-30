package com.safelink.api.service.client;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AzureClient {
    @Value("${keys.api}")
    private String apiKey;
    private final WebClient webClient2;

    public AzureClient(WebClient webClient2) {
        this.webClient2 = webClient2;
    }

    public String generateGuide(String information) {
        String body = """
                {
                    "messages": [
                    {
                        "role": "system",
                        "content": "Você é um especialista em segurança digital. Sua tarefa é gerar um guia de ação imediatamente após a vítima relatar um golpe. Regras obrigatórias: gere exatamente 6 passos práticos, curtos e objetivos; cada passo deve começar com número e ponto (ex: 1. ...); a resposta deve ser totalmente personalizada com base nas informações enviadas pelo usuário; escreva instruções claras, diretas e imediatamente aplicáveis; NÃO inclua título, introdução, conclusão, alertas, disclaimers ou texto extra; NÃO escreva nada além dos tópicos de 1 a 6. Formato obrigatório da resposta: 1. ...\\n 2. ...\\n 3. ...\\n 4. ...\\n 5. ...\\n 6. ..."
                    },
                    {
                        "role": "user",
                        "content": "%s"
                    }
                    ],
                    "max_tokens": 200,
                    "temperature": 0
                }
                """.formatted(information);

        String json = webClient2.post()
                .bodyValue(body)
                .header("Content-Type", "application/json")
                .header("api-key", apiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return new JSONObject(json)
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }
}
