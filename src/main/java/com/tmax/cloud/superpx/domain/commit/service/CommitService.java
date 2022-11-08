package com.tmax.cloud.superpx.domain.commit.service;

import com.tmax.cloud.superpx.data.primary.model.entity.CommitEntity;
import com.tmax.cloud.superpx.data.primary.model.entity.CommitInReferenceEntity;
import com.tmax.cloud.superpx.data.primary.model.entity.CommitParentEntity;
import com.tmax.cloud.superpx.data.primary.repository.CommitInReferenceRepository;
import com.tmax.cloud.superpx.data.primary.repository.CommitParentRepository;
import com.tmax.cloud.superpx.data.primary.repository.CommitRepository;
import com.tmax.cloud.superpx.domain.commit.dto.CommitDTO;
import com.tmax.cloud.superpx.global.error.exception.BusinessException;
import com.tmax.cloud.superpx.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommitService {
    private final ModelMapper modelMapper;
    private final CommitRepository commitRepository;
    private final CommitInReferenceRepository commitInReferenceRepository;
    private final CommitParentRepository commitParentRepository;

    //todo commit 생성, transaction 고려
    public CommitDTO.Get createCommit(CommitDTO.Create newCommitDTO) {
        CommitEntity newCommitEntity =
                CommitEntity.builder()
                        .projectId(newCommitDTO.getProjectId())
                        .message(newCommitDTO.getMessage())
                        .authorId(newCommitDTO.getAuthorId())
                        .build();

        newCommitEntity = commitRepository.save(newCommitEntity);

        //todo commit_in_reference

        //todo commit_parent

        return modelMapper.map(newCommitEntity, CommitDTO.Get.class);
    }

    //commit revert
    //todo 가능 범위 정해야 할 듯, 중간에 다른 브랜치 끼어있는 경우
    public CommitDTO.Get revertCommit(CommitDTO.Revert revertCommitDTO) {
        Optional<CommitEntity> optionalCommitEntity = commitRepository.findById(revertCommitDTO.getRevertId());

        if(!optionalCommitEntity.isPresent()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        CommitEntity commitEntity = optionalCommitEntity.get();

        //todo 사이의 commit 삭제

        //todo 관련된 commit_in_reference, commit_parent 삭제

        return modelMapper.map(commitEntity, CommitDTO.Get.class);
    }

    //commit 가져오기
    //todo 속해있는 path 가져오기 추가
    public CommitDTO.Get getCommit(Long id) {
        Optional<CommitEntity> optionalCommitEntity = commitRepository.findById(id);

        if(!optionalCommitEntity.isPresent()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        CommitEntity commitEntity = optionalCommitEntity.get();

        return modelMapper.map(commitEntity, CommitDTO.Get.class);
    }

    //commit 히스토리 가져오기
    public List<CommitDTO.Get> getCommitHistory(Long referenceId) {
        Long HEADId = getHEAD(referenceId);
        List<CommitDTO.Get> historyList = new ArrayList<CommitDTO.Get>();
        Long tempId = HEADId;

        while(true) {
            Optional<CommitEntity> optionalCommitEntity = commitRepository.findById(tempId);

            if (!optionalCommitEntity.isPresent()) {
                throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
            }

            CommitEntity commitEntity = optionalCommitEntity.get();
            historyList.add(modelMapper.map(commitEntity, CommitDTO.Get.class));

            Optional<CommitParentEntity> optionalCommitParentEntity = commitParentRepository.findByCommitId(tempId);

            if (!optionalCommitParentEntity.isPresent()) {
                throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
            }

            CommitParentEntity commitParentEntity = optionalCommitParentEntity.get();

            if (commitParentEntity.getParentId() == null) {
                break;
            }

            tempId = commitParentEntity.getParentId();
        }

        return historyList;
    }

    //HEAD commit 가져오기
    public Long getHEAD(Long referenceId) {
        List<CommitInReferenceEntity> commitInReferenceEntities = commitInReferenceRepository.findAllByReferenceId(referenceId);

        for(CommitInReferenceEntity commitInReferenceEntity : commitInReferenceEntities) {
            Long commitId = commitInReferenceEntity.getCommitId();
            List<CommitParentEntity> commitParentEntities = commitParentRepository.findAllByParentId(commitId);
            if(commitParentEntities.isEmpty()) {
                return commitId;
            }

            boolean flag = true;
            for(CommitParentEntity commitParentEntity : commitParentEntities) {
                Long commitIdLoop = commitParentEntity.getCommitId();
                List<CommitInReferenceEntity> commitInReferenceEntitiesLoop = commitInReferenceRepository.findAllByCommitId(commitIdLoop);


                for(CommitInReferenceEntity temp : commitInReferenceEntitiesLoop) {
                    if(referenceId.equals(temp.getReferenceId())) {
                        flag = false;
                        break;
                    }
                }
            }
            if(flag) {
                return commitId;
            }
        }
        throw new BusinessException(ErrorCode.REFERENCE_HEAD_NOT_FOUND);
    }
}
