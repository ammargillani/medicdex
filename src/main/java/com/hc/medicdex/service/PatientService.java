package com.hc.medicdex.service;

import com.hc.medicdex.dto.PatientDto;
import com.hc.medicdex.dto.UserEntityDto;
import com.hc.medicdex.entity.HospitalEntity;
import com.hc.medicdex.entity.PatientEntity;
import com.hc.medicdex.entity.UserEntity;
import com.hc.medicdex.mapper.PatientMapper;
import com.hc.medicdex.repository.HospitalRepository;
import com.hc.medicdex.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class PatientService {
    private final PatientRepository repository;
    private final PatientMapper patientMapper;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private UserManagerService userManagerService;


    public PatientDto save(PatientDto patientDto) {
        UserEntity user = userManagerService.getAuthenticatedUser();
        PatientEntity patient = patientMapper.toEntity(patientDto);
        patient.setHospital(hospitalRepository.findByUser(user).get());

        return patientMapper.toDto(repository.save(patient));
    }

    public void deleteById(Integer id) {
        Optional<PatientEntity> patient = repository.findById(id);
        patient.get().getStaffEntities().removeAll(patient.get().getStaffEntities());
        repository.delete(patient.get());
    }

    public PatientDto findById(Integer id) {
        return patientMapper.toDto(repository.findById(id).get());
    }

    public Page<PatientDto> findByCondition(PatientDto patientDto, Pageable pageable) {
        Page<PatientEntity> entityPage = repository.findAll(pageable);
        List<PatientEntity> entities = entityPage.getContent();
        return new PageImpl<>(patientMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public PatientDto update(PatientDto patientDto, Integer id) {
        PatientDto data = findById(id);
        PatientEntity entity = patientMapper.toEntity(patientDto);
        //BeanUtil.copyProperties(data, entity);
        return save(patientMapper.toDto(entity));
    }

    public Map<String,Object> findAllPatientByType(int page, int size , String patientType){
        Map<String,Object> result = new HashMap<>();
        List<PatientDto> patientDtoList = new ArrayList<>();
        UserEntity user = userManagerService.getAuthenticatedUser();
        HospitalEntity hospital = hospitalRepository.findByUser(user).get();
        Page<PatientEntity> patientEntities = repository.findByPatientTypeAndHospital(PageRequest.of(page-1, size), patientType, hospital);
        patientDtoList = patientMapper.toDto(patientEntities.getContent());
        result.put("Patients", patientDtoList);
        result.put("currentPage", patientEntities.getNumber());
        result.put("totalItems", patientEntities.getTotalElements());
        result.put("totalPages", patientEntities.getTotalPages());
        result.put("size", patientEntities.getSize());
        return result;
    }
    public Map<String,Object> findAllPatients(int page, int size){
        Map<String,Object> result = new HashMap<>();
        Page<PatientEntity> patientEntityList;
        List<PatientDto> patientDtos = new ArrayList<>();
        UserEntity user = userManagerService.getAuthenticatedUser();
        Optional<HospitalEntity> hospital = hospitalRepository.findByUser(user);
        patientEntityList = repository.findAllByHospital(PageRequest.of(page-1, size),hospital.get());
        patientDtos = patientMapper.toDto(patientEntityList.getContent());
        result.put("patients",patientDtos);
        result.put("opd",patientDtos.stream().filter(p->p.getPatientType().equals("OPD") ||
                        p.getPatientType().equals("opd"))
                        .count());
        result.put("ipd",patientDtos.stream().filter(p->p.getPatientType().equals("IPD") ||
                        p.getPatientType().equals("ipd"))
                        .count());
        result.put("currentPage", patientEntityList.getNumber());
        result.put("totalItems", patientEntityList.getTotalElements());
        result.put("totalPages", patientEntityList.getTotalPages());
        result.put("size", patientEntityList.getSize());
        return result;
    }
}