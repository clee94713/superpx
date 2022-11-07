package com.tmax.cloud.superpx.domain.reference.controller;

import com.tmax.cloud.superpx.domain.reference.dto.ReferenceDTO;
import com.tmax.cloud.superpx.domain.reference.service.ReferenceService;
import com.tmax.cloud.superpx.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("reference")
public class ReferenceController {
    private final ReferenceService referenceService;

    @Operation(summary = "reference 생성", description = "reference 생성")
    @PostMapping
    public ResponseEntity<DataResponse<ReferenceDTO.Get>> createReference(@RequestBody ReferenceDTO.Create referenceDTO) {
        return DataResponse.success(referenceService.createReference(referenceDTO));
    }

}
