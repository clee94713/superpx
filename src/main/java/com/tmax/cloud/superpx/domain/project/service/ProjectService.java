package com.tmax.cloud.superpx.domain.project.service;

import com.tmax.cloud.superpx.data.primary.model.entity.ProjectEntity;
import com.tmax.cloud.superpx.data.primary.model.enums.ReferenceType;
import com.tmax.cloud.superpx.data.primary.repository.ProjectRepository;
import com.tmax.cloud.superpx.domain.project.dto.ProjectDTO;
import com.tmax.cloud.superpx.domain.reference.dto.ReferenceDTO;
import com.tmax.cloud.superpx.global.error.exception.BusinessException;
import com.tmax.cloud.superpx.global.error.exception.ErrorCode;
import com.tmax.cloud.superpx.domain.reference.service.ReferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;
    private final ReferenceService referenceService;

    //프로젝트 생성
    @Transactional
    public ProjectDTO.ProjectGet createProject(ProjectDTO.ProjectCreate newProjectDTO) {
        //todo 중복 검사

        ProjectEntity newProjectEntity =
                ProjectEntity.builder()
                        .name(newProjectDTO.getName())
                        .build();

        newProjectEntity = projectRepository.save(newProjectEntity);

        //master 브랜치 생성
        ReferenceDTO.ReferenceCreate newReferenceDTO = new ReferenceDTO.ReferenceCreate();
        newReferenceDTO.setProjectId(newProjectEntity.getId());
        newReferenceDTO.setName("master");
        newReferenceDTO.setType(ReferenceType.BRANCH);

        referenceService.createReference(newReferenceDTO);

        return modelMapper.map(newProjectEntity, ProjectDTO.ProjectGet.class);
    }

    //프로젝트 삭제
    //todo DB에는 남겨 놔야 하는지 논의 필요
    public void deleteProject(Long projectId) {
        Optional<ProjectEntity> optionalProjectEntity = projectRepository.findById(projectId);

        if(!optionalProjectEntity.isPresent()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        ProjectEntity projectEntity = optionalProjectEntity.get();
        projectRepository.delete(projectEntity);
    }

    //프로젝트 리스트 가져오기
    public List<ProjectDTO.ProjectGet> getProjects() {
        return modelMapper.map(projectRepository.findAll(), new TypeToken<List<ProjectDTO.ProjectGet>>(){}.getType());
    }
}
