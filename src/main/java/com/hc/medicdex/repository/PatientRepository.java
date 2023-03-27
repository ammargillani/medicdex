package com.hc.medicdex.repository;

import com.hc.medicdex.entity.HospitalEntity;
import com.hc.medicdex.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Integer>, JpaSpecificationExecutor<PatientEntity> {
    Page<PatientEntity> findByPatientTypeAndHospital(PageRequest pageable, String patientType, HospitalEntity hospital);
    Page<PatientEntity> findAllByHospital(PageRequest pageable, HospitalEntity hospital);
}