package service;

import lombok.extern.log4j.Log4j2;
import model.Doctor;
import repository.DoctorRepository;

import java.util.List;

@Log4j2
public class DoctorService implements CrudService<Doctor>, PatientOwner<Doctor>, InstitutionOwner {

    private final DoctorRepository doctorRepository;

    public DoctorService() {
        this.doctorRepository = new DoctorRepository();
    }

    @Override
    public void addNew(Doctor doctor) {
        log.info("Save new doctor");
        doctorRepository.saveNew(doctor);
    }

    @Override
    public void update(Doctor doctor) {
        log.info("Update doctor");
        doctorRepository.update(doctor);
    }

    @Override
    public Doctor getOneByIndex(int id) {
        log.info("Get one doctor");
        return doctorRepository.getOneByIndex(id);
    }

    @Override
    public List<Doctor> getAll() {
        log.info("Get all doctors");
        return doctorRepository.getAll();
    }

    @Override
    public void deleteOneById(int id) {
        log.info("Delete doctor");
        doctorRepository.deleteOneById(id);
    }

    @Override
    public Doctor getOneByIndexWithPatient(int id) {
        log.info("Get doctor with patients");
        return doctorRepository.getOneByIndexWithPatients(id);
    }

    @Override
    public void changeRelationInstitution(int doctorId, int institutionId) {
        log.info("Change doctor-institution relation");
        doctorRepository.changeRelation(doctorId, institutionId);
    }

    @Override
    public void deleteRelationWithInstitution(int doctorId) {
        log.info("Delete doctor-institution relation");
        doctorRepository.deleteRelation(doctorId);
    }

    @Override
    public void addPatient(int patientId, int doctorId) {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void deletePatient(int patientId, int doctorId) {
        throw new UnsupportedOperationException("Unsupported operation");
    }
}