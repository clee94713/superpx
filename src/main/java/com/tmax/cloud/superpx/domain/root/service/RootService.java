package com.tmax.cloud.superpx.domain.root.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tmax.cloud.superpx.domain.root.dto.SampleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RootService {
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final WebClient webClient;

    public SampleDTO.Get createSample(SampleDTO.Create createSample) {
        String uuid = UUID.randomUUID().toString().replace("-","");

        SampleDTO.Get getSample = modelMapper.map(createSample, SampleDTO.Get.class);
        getSample.setUuid(uuid);
        getSample.setPassword(bCryptPasswordEncoder.encode(createSample.getPassword()));
        getSample.setCreatedDate(LocalDateTime.now());

        return getSample;
    }

    public JsonNode webClientPostExample() {
        SampleDTO.Create sample = SampleDTO.Create.builder()
                .name("가나다")
                .gender("여자")
                .password("pass1234")
                .birthDate("19990101")
                .build();

        // Response Body가 정해진 클래스가 아닌 경우 Response를 JsonNode로 받는 방법
        Mono<JsonNode> response = webClient.mutate()
                .baseUrl("http://127.0.0.1:8080")
                .build()
                .post()
                .uri("/root/post")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(sample))
                .retrieve()
                .onStatus(HttpStatus::isError, rsp -> Mono.empty())
                .bodyToMono(JsonNode.class);

        log.info("JsonNodeSample Response: {}", response.block());

        return Objects.requireNonNull(response.block()).get("data");
    }

    public JsonNode webClientGetExample() {
        Mono<JsonNode> response = webClient.mutate()
                .baseUrl("http://127.0.0.1:8080")
                .build()
                .get()
                .uri("/root/web-client/POST")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, rsp -> Mono.empty())
                .bodyToMono(JsonNode.class);

        return Objects.requireNonNull(response.block()).get("data");
    }
}
