package com.tmax.cloud.superpx.domain.commit.controller;

import com.tmax.cloud.superpx.domain.commit.service.CommitService;
import com.tmax.cloud.superpx.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("commit")
public class CommitController {
    private final CommitService commitService;

    @Operation(summary = " HEAD 가져오기", description = "HEAD commitId 가져오기")
    @GetMapping("/{referenceId}/HEAD")
    public ResponseEntity<DataResponse<Long>> getReferenceHEAD(@PathVariable(value = "id") Long id) {
        return DataResponse.success(commitService.getHEAD(id));
    }
}
