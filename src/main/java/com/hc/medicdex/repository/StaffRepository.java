package com.hc.medicdex.repository;

import com.hc.medicdex.entity.HospitalEntity;
import com.hc.medicdex.entity.StaffEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffRepository extends JpaRepository<StaffEntity, Integer> {
    Page<StaffEntity> findAllByHospital(PageRequest pageable, HospitalEntity hospital);
    @Query("SELECT s FROM StaffEntity s WHERE s.staffType LIKE %?1%"
            + " OR s.specialization LIKE %?1%"
            + " OR s.staffName LIKE %?1%"
            + " OR CONCAT(s.fee, '') LIKE %?1%")
    public Page<StaffEntity> searchByHospital(PageRequest pageable,String keyword, HospitalEntity hospital);
}