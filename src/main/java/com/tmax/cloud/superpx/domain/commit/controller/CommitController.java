package com.tmax.cloud.superpx.domain.commit.controller;

import com.tmax.cloud.superpx.domain.commit.dto.CommitDTO;
import com.tmax.cloud.superpx.domain.commit.service.CommitService;
import com.tmax.cloud.superpx.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("commit")
@Tag(name = "commit", description = "commit api")
public class CommitController {
    private final CommitService commitService;

    @Operation(summary = "commit 생성", description = "commit 생성")
    @PostMapping
    public ResponseEntity<DataResponse<CommitDTO.CommitGet>> createCommit(@RequestBody CommitDTO.CommitCreate commitDTO) {
        return DataResponse.success(commitService.createCommit(commitDTO));
    }

    @Operation(summary = "commit revert", description = "commit revert")
    @DeleteMapping
    public ResponseEntity<DataResponse<CommitDTO.CommitGet>> revertCommit(@RequestBody CommitDTO.CommitRevert commitDTO) {
        return DataResponse.success(commitService.revertCommit(commitDTO));
    }

    @Operation(summary = "commit 가져오기", description = "commit 가져오기")
    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<CommitDTO.CommitGet>> getCommit(@PathVariable(value = "id") Long id) {
        return DataResponse.success(commitService.getCommit(id));
    }

    @Operation(summary = "commit 히스토리 가져오기", description = "commit 히스토리 가져오기")
    @GetMapping("/history/{referenceId}")
    public ResponseEntity<DataResponse<List<CommitDTO.CommitGet>>> getCommitHistory(@PathVariable(value = "referenceId") Long referenceId) {
        return DataResponse.success(commitService.getCommitHistory(referenceId));
    }

    @Operation(summary = "HEAD 가져오기", description = "HEAD commitId 가져오기")
    @GetMapping("/{referenceId}/HEAD")
    public ResponseEntity<DataResponse<Long>> getReferenceHEAD(@PathVariable(value = "referenceId") Long id) {
        return DataResponse.success(commitService.getHEAD(id));
    }
}
