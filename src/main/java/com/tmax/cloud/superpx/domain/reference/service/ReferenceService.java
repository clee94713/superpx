package com.tmax.cloud.superpx.domain.reference.service;

import com.tmax.cloud.superpx.data.primary.model.entity.ReferenceEntity;
import com.tmax.cloud.superpx.data.primary.repository.ReferenceRepository;
import com.tmax.cloud.superpx.domain.reference.dto.ReferenceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReferenceService {
    private final ModelMapper modelMapper;
    private final ReferenceRepository referenceRepository;

    //todo init ref 생성,
    public ReferenceDTO.Get createReference(ReferenceDTO.Create newReferenceDTO) {
        ReferenceEntity newReferenceEntity =
                ReferenceEntity.builder()
                        .projectId(newReferenceDTO.getProjectId())
                        .name(newReferenceDTO.getName())
                        .type(newReferenceDTO.getType())
                        .build();

        newReferenceEntity = referenceRepository.save(newReferenceEntity);

        return modelMapper.map(newReferenceEntity, ReferenceDTO.Get.class);
    }

    //todo 기존 브랜치에서 ref 생성

    //todo ref 삭제, 마스터 브랜치는 삭제 불가

    //todo ref list 가져오기

    //todo ref 정보 가져오기, 하위 디렉토리 및 파일 정보 가져오기

    //todo ref HEAD 가져오기
}
