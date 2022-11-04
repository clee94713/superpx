package com.tmax.cloud.superpx.domain.project.controller;

import com.tmax.cloud.superpx.domain.project.dto.ProjectDTO;
import com.tmax.cloud.superpx.domain.project.service.ProjectService;
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
@RequestMapping("project")
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "프로젝트 생성", description = "프로젝트 생성")
    @PostMapping
    public ResponseEntity<DataResponse<ProjectDTO.Get>> createProject(@RequestBody ProjectDTO.Create projectDTO) {
        return DataResponse.success(projectService.createProject(projectDTO));
    }

    @Operation(summary = "프로젝트 삭제", description = "프로젝트 삭제")
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable(value = "id") Long id) {
        projectService.deleteProject(id);
    }

    @Operation(summary = "프로젝트 리스트 가져오기", description = "프로젝트 리스트 가져오기")
    @GetMapping("/list")
    public ResponseEntity<DataResponse<List<ProjectDTO.Get>>> getProjects() {
        return DataResponse.success(projectService.getProjects());
    }
}
