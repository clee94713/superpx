package com.tmax.cloud.superpx.domain.project.service;

import com.tmax.cloud.superpx.data.primary.model.entity.ProjectEntity;
import com.tmax.cloud.superpx.data.primary.repository.ProjectRepository;
import com.tmax.cloud.superpx.domain.project.dto.ProjectDTO;
import com.tmax.cloud.superpx.global.error.exception.BusinessException;
import com.tmax.cloud.superpx.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;

    //프로젝트 생성
    public ProjectDTO.Get createProject(ProjectDTO.Create newProjectDTO) {
        ProjectEntity newProjectEntity =
                ProjectEntity.builder()
                        .name(newProjectDTO.getName())
                        .build();

        newProjectEntity = projectRepository.save(newProjectEntity);

        return modelMapper.map(newProjectEntity, ProjectDTO.Get.class);
    }

    //프로젝트 삭제
    //DB에는 남겨 놔야 하는지
    public void deleteProject(Long projectId) {
        Optional<ProjectEntity> optionalProjectEntity = projectRepository.findById(projectId);

        if(!optionalProjectEntity.isPresent()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        ProjectEntity projectEntity = optionalProjectEntity.get();
        projectRepository.delete(projectEntity);
    }

    //프로젝트 리스트 가져오기
    public List<ProjectDTO.Get> getProject() {
        return modelMapper.map(projectRepository.findAll(), new TypeToken<List<ProjectDTO.Get>>(){}.getType());
    }
}
