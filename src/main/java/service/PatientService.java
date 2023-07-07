package service;

import lombok.extern.log4j.Log4j2;
import model.Patient;
import repository.PatientRepository;

import java.util.List;

@Log4j2
public class PatientService implements CrudService<Patient>, DoctorOwner<Patient> {

    private final PatientRepository patientRepository;

    public PatientService() {
        this.patientRepository = new PatientRepository();
    }

    @Override
    public void addNew(Patient patient) {
        log.info("Save new patient");
        patientRepository.saveNew(patient);
    }

    @Override
    public void update(Patient patient) {
        log.info("Update patient");
        patientRepository.update(patient);
    }

    @Override
    public Patient getOneByIndex(int i) {
        log.info("Get one patient");
        return patientRepository.getOneByIndex(i);
    }

    @Override
    public List<Patient> getAll() {
        log.info("Get all patient");
        return patientRepository.getAll();
    }

    @Override
    public void deleteOneById(int i) {
        log.info("Delete patient");
        patientRepository.deleteOneById(i);
    }

    @Override
    public Patient getOneByIndexWithDoctor(int id) {
        log.info("Get patient with its doctors");
        return patientRepository.getOneByIndexWithDoctor(id);
    }

    @Override
    public void addDoctor(int patientId, int doctorId) {
        log.info("Add doctor to patient");
        patientRepository.addDoctorForPatient(patientId, doctorId);
    }

    @Override
    public void deleteDoctor(int patientId, int doctorId) {
        log.info("Delete relation patient-doctor");
        patientRepository.deleteDoctorForPatient(patientId, doctorId);
    }
}