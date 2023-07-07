package service;

import lombok.extern.log4j.Log4j2;
import model.Doctor;
import repository.PatientDoctorRepository;

import java.sql.SQLException;
import java.util.List;

@Log4j2
public class PatientDoctorService implements ManyToManyService {

    private PatientDoctorRepository patientDoctorRepository;

    public PatientDoctorService() {
        this.patientDoctorRepository = new PatientDoctorRepository();
    }

    @Override
    public void addRelation(int patientId, int doctorId) throws SQLException {
        log.info("Save new relation patient-doctor");
        patientDoctorRepository.saveNewRelationship(patientId, doctorId);
    }

    @Override
    public void deleteRelation(int patientId, int doctorId) throws SQLException {
        log.info("Delete relation patient-doctor");
        patientDoctorRepository.deleteRelation(patientId, doctorId);
    }

    @Override
    public List<Doctor> getAllById(int patientId) throws SQLException {
        log.info("Delete relation patient-doctor");
        return patientDoctorRepository.getAllById(patientId);
    }
}