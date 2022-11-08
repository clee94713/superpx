package com.tmax.cloud.superpx.domain.reference.controller;

import com.tmax.cloud.superpx.domain.reference.dto.ReferenceDTO;
import com.tmax.cloud.superpx.domain.reference.service.ReferenceService;
import com.tmax.cloud.superpx.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "reference 삭제", description = "reference 삭제, master 브랜치 삭제 불가")
    @DeleteMapping("/{id}")
    public void deleteReference(@PathVariable(value = "id") Long id) {
        referenceService.deleteReference(id);
    }

    @Operation(summary = "reference list 가져오기", description = "해당 프로젝트의 reference list 가져오기")
    @GetMapping("/list/{projectId}")
    public ResponseEntity<DataResponse<List<ReferenceDTO.Get>>> getReferences(@PathVariable(value = "projectId") Long projectId) {
        return DataResponse.success(referenceService.getReferences(projectId));
    }

    @Operation(summary = "reference 가져오기", description = "reference 가져오기")
    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<ReferenceDTO.Get>> getReference(@PathVariable(value = "id") Long id) {
        return DataResponse.success(referenceService.getReference(id));
    }
}
