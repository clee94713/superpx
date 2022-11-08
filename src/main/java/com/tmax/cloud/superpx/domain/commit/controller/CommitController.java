package com.tmax.cloud.superpx.domain.commit.controller;

import com.tmax.cloud.superpx.domain.commit.dto.CommitDTO;
import com.tmax.cloud.superpx.domain.commit.service.CommitService;
import com.tmax.cloud.superpx.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("commit")
public class CommitController {
    private final CommitService commitService;

    @Operation(summary = "commit 생성", description = "commit 생성")
    @PostMapping
    public ResponseEntity<DataResponse<CommitDTO.Get>> createCommit(@RequestBody CommitDTO.Create commitDTO) {
        return DataResponse.success(commitService.createCommit(commitDTO));
    }

    @Operation(summary = "commit revert", description = "commit revert")
    @DeleteMapping
    public ResponseEntity<DataResponse<CommitDTO.Get>> revertCommit(@RequestBody CommitDTO.Revert commitDTO) {
        return DataResponse.success(commitService.revertCommit(commitDTO));
    }

    @Operation(summary = "commit 가져오기", description = "commit 가져오기")
    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<CommitDTO.Get>> getCommit(@PathVariable(value = "id") Long id) {
        return DataResponse.success(commitService.getCommit(id));
    }

    @Operation(summary = "commit 히스토리 가져오기", description = "commit 히스토리 가져오기")
    @GetMapping("/history/{referenceId}")
    public ResponseEntity<DataResponse<List<CommitDTO.Get>>> getCommitHistory(@PathVariable(value = "referenceId") Long referenceId) {
        return DataResponse.success(commitService.getCommitHistory(referenceId));
    }

    @Operation(summary = "HEAD 가져오기", description = "HEAD commitId 가져오기")
    @GetMapping("/{referenceId}/HEAD")
    public ResponseEntity<DataResponse<Long>> getReferenceHEAD(@PathVariable(value = "id") Long id) {
        return DataResponse.success(commitService.getHEAD(id));
    }
}
