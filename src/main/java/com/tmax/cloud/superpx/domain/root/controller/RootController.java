package com.tmax.cloud.superpx.domain.root.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.tmax.cloud.superpx.global.response.DataResponse;
import com.tmax.cloud.superpx.domain.root.dto.SampleDTO;
import com.tmax.cloud.superpx.domain.root.service.RootService;
import com.tmax.cloud.superpx.global.error.exception.BusinessException;
import com.tmax.cloud.superpx.global.error.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "RootController")
@RequestMapping("/root")
public class RootController {
    private final RootService rootService;

    @Operation(summary = "����1", description = "Post API ����, DTO ���, RequestBody ��뿡 ���� ������ ������.")
    @PostMapping("/post")
    public ResponseEntity<DataResponse<SampleDTO.Get>> createApp(@RequestBody SampleDTO.Create createSample) {
        return DataResponse.success(rootService.createSample(createSample));
    }

    @Operation(summary = "����2", description = "Get API ����, WebClient ���, PathVariable ��뿡 ���� ������ ������.")
    @GetMapping("/web-client/{type}")
    public ResponseEntity<DataResponse<JsonNode>> useWebClientPost(@Parameter(required = true, description = "{POST|GET} ȣ�� ����") @PathVariable String type) {
        if(type.equals("POST")) {
            return DataResponse.success(rootService.webClientPostExample());
        }
        else if(type.equals("GET")) {
            return DataResponse.success((rootService.webClientGetExample()));
        }

        throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
    }
}
