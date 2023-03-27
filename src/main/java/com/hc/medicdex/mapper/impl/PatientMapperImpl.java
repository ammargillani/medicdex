package com.hc.medicdex.mapper.impl;

import com.hc.medicdex.dto.PatientDto;
import com.hc.medicdex.entity.PatientEntity;
import com.hc.medicdex.mapper.PatientMapper;
import com.hc.medicdex.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class PatientMapperImpl implements PatientMapper {
    @Autowired
    StaffService staffService;
    public PatientMapperImpl() {
        super();
    }

    @Override
    public PatientEntity toEntity(PatientDto dto) {
        PatientEntity patient = PatientEntity.builder()
                .patientName(dto.getPatientName())
                .age(dto.getAge())
                .cnic(dto.getCnic())
                .gender(dto.getGender())
                .fatherName(dto.getFatherName())
                .patientType(dto.getPatientType())
                .staffEntities(staffService.dtoTOEntityList(dto.getStaffEntities())).build();
        return patient;
    }

    @Override
    public PatientDto toDto(PatientEntity entity) {
        PatientDto patient = PatientDto.builder()
                .patientName(entity.getPatientName())
                .age(entity.getAge())
                .cnic(entity.getCnic())
                .gender(entity.getGender())
                .fatherName(entity.getFatherName())
                .patientType(entity.getPatientType())
                //.staffEntities(staffService.entityToDtoList(entity.getStaffEntities()))
                .build();
        return patient;
        //return null;
    }

    @Override
    public List<PatientEntity> toEntity(List<PatientDto> dtoList) {

        List<PatientEntity> patientList = new ArrayList<>();
        for (PatientDto patientDto:dtoList) {
            PatientEntity patient = PatientEntity.builder()
                    .id(patientDto.getId())
                    .patientName(patientDto.getPatientName())
                    .fatherName(patientDto.getFatherName())
                    .patientType(patientDto.getPatientType())
                    .gender(patientDto.getGender())
                    .age(patientDto.getAge())
                    .cnic(patientDto.getCnic())
                    .build();
            patientList.add(patient);
        }
        return patientList;
    }

    @Override
    public List<PatientDto> toDto(List<PatientEntity> entityList) {
        List<PatientDto> patientDtoList = new ArrayList<>();
        for (PatientEntity patientEntity:entityList) {
            PatientDto patientDto = PatientDto.builder()
                    .id(patientEntity.getId())
                    .patientName(patientEntity.getPatientName())
                    .fatherName(patientEntity.getFatherName())
                    .patientType(patientEntity.getPatientType())
                    .gender(patientEntity.getGender())
                    .age(patientEntity.getAge())
                    .cnic(patientEntity.getCnic())
                    .build();
            patientDtoList.add(patientDto);
        }
        return patientDtoList;
    }

    @Override
    public Set<PatientDto> toDto(Set<PatientEntity> entityList) {
        return null;
    }

    @Override
    public PatientDto toDto(Optional<PatientEntity> byId) {
        return null;
    }
}
