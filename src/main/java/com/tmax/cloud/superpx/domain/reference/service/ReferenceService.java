package com.tmax.cloud.superpx.domain.reference.service;

import com.tmax.cloud.superpx.data.primary.model.entity.CommitInReferenceEntity;
import com.tmax.cloud.superpx.data.primary.model.entity.CommitParentEntity;
import com.tmax.cloud.superpx.data.primary.model.entity.ReferenceEntity;
import com.tmax.cloud.superpx.data.primary.repository.CommitInReferenceRepository;
import com.tmax.cloud.superpx.data.primary.repository.CommitParentRepository;
import com.tmax.cloud.superpx.data.primary.repository.ReferenceRepository;
import com.tmax.cloud.superpx.domain.reference.dto.ReferenceDTO;
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
public class ReferenceService {
    private final ModelMapper modelMapper;
    private final ReferenceRepository referenceRepository;
    private final CommitInReferenceRepository commitInReferenceRepository;
    private final CommitParentRepository commitParentRepository;

    //ref 생성
    //todo 트렌젝션 고려
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

    //ref 삭제, 마스터 브랜치는 삭제 불가
    //todo transaction 고려
    public void deleteReference(Long referenceId) {
        Optional<ReferenceEntity> optionalReferenceEntity = referenceRepository.findById(referenceId);

        //todo master 브랜치 검사

        if(!optionalReferenceEntity.isPresent()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        //todo 해당 reference에 물려있는 commit_in_reference 레코드 삭제

        //todo dangling commit 삭제

        ReferenceEntity referenceEntity = optionalReferenceEntity.get();
        referenceRepository.delete(referenceEntity);
    }

    //ref list 가져오기
    public List<ReferenceDTO.Get> getReferences(Long projectId) {
        return modelMapper.map(referenceRepository.findAllByProjectId(projectId), new TypeToken<List<ReferenceDTO>>(){}.getType());
    }

    //ref 정보 가져오기
    //todo 하위 디렉토리 및 파일 정보 가져오기
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
