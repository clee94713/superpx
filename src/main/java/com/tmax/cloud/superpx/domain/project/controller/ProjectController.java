package com.tmax.cloud.superpx.domain.project.controller;

import com.tmax.cloud.superpx.data.primary.model.entity.ReferenceEntity;
import com.tmax.cloud.superpx.domain.project.dto.ProjectDTO;
import com.tmax.cloud.superpx.domain.project.service.ProjectService;
import com.tmax.cloud.superpx.domain.reference.dto.ReferenceDTO;
import com.tmax.cloud.superpx.domain.reference.service.ReferenceService;
import com.tmax.cloud.superpx.global.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("project")
@Tag(name = "project", description = "project api")
public class ProjectController {
    private final ProjectService projectService;
    private final ReferenceService referenceService;

    @Operation(summary = "project 생성", description = "project 생성")
    @PostMapping
    public ResponseEntity<DataResponse<ProjectDTO.ProjectGet>> createProject(@RequestBody ProjectDTO.ProjectCreate projectDTO) {
        return DataResponse.success(projectService.createProject(projectDTO));
    }

    @Operation(summary = "project 삭제", description = "project 삭제")
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable(value = "id") Long id) {
        //프로젝트에 있는 ref 삭제
        List<ReferenceDTO.ReferenceGet> referenceDTOs = referenceService.getReferences(id);
        for(ReferenceDTO.ReferenceGet referenceDTO : referenceDTOs) {
            Long referenceId = referenceDTO.getId();
            referenceService.deleteReference(referenceId);
        }
        //프로젝트 삭제
        projectService.deleteProject(id);
    }

    @Operation(summary = "project list 가져오기", description = "project list 가져오기")
    @GetMapping("/list")
    public ResponseEntity<DataResponse<List<ProjectDTO.ProjectGet>>> getProjects() {
        return DataResponse.success(projectService.getProjects());
    }
}
