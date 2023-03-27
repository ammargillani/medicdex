package com.hc.medicdex.controller;

import com.hc.medicdex.dto.PatientDto;
import com.hc.medicdex.service.PatientService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/patient")
@RestController
@Slf4j

public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated PatientDto patientDto) {
        patientService.save(patientDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> findById(@PathVariable("id") Integer id) {
        PatientDto patient = patientService.findById(id);
        return ResponseEntity.ok(patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        Optional.ofNullable(patientService.findById(id));
        patientService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<PatientDto>> pageQuery(PatientDto patientDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PatientDto> patientPage = patientService.findByCondition(patientDto, pageable);
        return ResponseEntity.ok(patientPage);
    }
    @GetMapping()
    public ResponseEntity<?> findAllPatientByType(@RequestParam int page, @RequestParam int size ,@RequestParam String patientType){
        //patientService.findAllPatientByType(patientType);
        return new ResponseEntity<>(patientService.findAllPatientByType(page,size,patientType), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAllPatient(@RequestParam int page, @RequestParam int size ){
        //patientService.findAllPatientByType(patientType);
        return new ResponseEntity<>(patientService.findAllPatients(page, size), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated PatientDto patientDto, @PathVariable("id") Integer id) {
        patientService.update(patientDto, id);
        return ResponseEntity.ok().build();
    }
}