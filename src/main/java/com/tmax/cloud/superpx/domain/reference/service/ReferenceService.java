package com.tmax.cloud.superpx.domain.reference.service;

import com.tmax.cloud.superpx.data.primary.model.entity.CommitEntity;
import com.tmax.cloud.superpx.data.primary.model.entity.CommitInReferenceEntity;
import com.tmax.cloud.superpx.data.primary.model.entity.CommitParentEntity;
import com.tmax.cloud.superpx.data.primary.model.entity.ReferenceEntity;
import com.tmax.cloud.superpx.data.primary.repository.CommitInReferenceRepository;
import com.tmax.cloud.superpx.data.primary.repository.CommitParentRepository;
import com.tmax.cloud.superpx.data.primary.repository.CommitRepository;
import com.tmax.cloud.superpx.data.primary.repository.ReferenceRepository;
import com.tmax.cloud.superpx.domain.reference.dto.ReferenceDTO;
import com.tmax.cloud.superpx.global.error.exception.BusinessException;
import com.tmax.cloud.superpx.global.error.exception.ErrorCode;
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
public class ReferenceService {
    private final ModelMapper modelMapper;
    private final ReferenceRepository referenceRepository;
    private final CommitInReferenceRepository commitInReferenceRepository;
    private final CommitParentRepository commitParentRepository;
    private final CommitRepository commitRepository;

    //ref 생성
    @Transactional
    public ReferenceDTO.Get createReference(ReferenceDTO.Create newReferenceDTO) {
        ReferenceEntity newReferenceEntity =
                ReferenceEntity.builder()
                        .projectId(newReferenceDTO.getProjectId())
                        .name(newReferenceDTO.getName())
                        .type(newReferenceDTO.getType())
                        .build();

        //todo 중복 검사

        if(newReferenceDTO.getName() == "master") {
            newReferenceEntity = referenceRepository.save(newReferenceEntity);

            return modelMapper.map(newReferenceEntity, ReferenceDTO.Get.class);
        }

        newReferenceEntity = referenceRepository.save(newReferenceEntity);

        CommitInReferenceEntity newCommitInReferenceEntity =
                CommitInReferenceEntity.builder()
                        .referenceId(newReferenceDTO.getCurrentReferenceId())
                        .commitId(newReferenceDTO.getCurrentCommitId())
                        .build();

        commitInReferenceRepository.save(newCommitInReferenceEntity);

        return modelMapper.map(newReferenceEntity, ReferenceDTO.Get.class);
    }

    //ref 삭제
    //todo 마스터 브랜치는 삭제 가능 여부 논의 필요
    @Transactional
    public void deleteReference(Long referenceId) {
        Optional<ReferenceEntity> optionalReferenceEntity = referenceRepository.findById(referenceId);

        if(!optionalReferenceEntity.isPresent()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        //해당 reference에 물려있는 commit_in_reference 레코드 삭제, dangling commit 삭제
        List<CommitInReferenceEntity> commitInReferenceEntities = commitInReferenceRepository.findAllByReferenceId(referenceId);
        for(CommitInReferenceEntity commitInReferenceEntity : commitInReferenceEntities) {
            Long commitId = commitInReferenceEntity.getCommitId();

            commitInReferenceRepository.delete(commitInReferenceEntity);

            Optional<CommitInReferenceEntity> optionalCommitInReferenceEntity = commitInReferenceRepository.findByCommitId(commitId);
            
            if(!optionalCommitInReferenceEntity.isPresent()) {
                commitRepository.deleteById(commitId);
            }
        }

        ReferenceEntity referenceEntity = optionalReferenceEntity.get();
        referenceRepository.delete(referenceEntity);
    }

    //ref list 가져오기
    public List<ReferenceDTO.Get> getReferences(Long projectId) {
        return modelMapper.map(referenceRepository.findAllByProjectId(projectId), new TypeToken<List<ReferenceDTO>>(){}.getType());
    }

    //ref 정보 가져오기
    //todo (소스코드 구조 구축 후) 하위 디렉토리 및 파일 정보 가져오기
    public ReferenceDTO.Get getReference(Long referenceId) {
        Optional<ReferenceEntity> optionalReferenceEntity = referenceRepository.findById(referenceId);

        if(!optionalReferenceEntity.isPresent()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        ReferenceEntity referenceEntity = optionalReferenceEntity.get();

        return modelMapper.map(referenceEntity, ReferenceDTO.Get.class);
    }

    //todo 머지
}
