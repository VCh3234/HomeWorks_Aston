package service;

import lombok.extern.log4j.Log4j2;
import model.Doctor;
import repository.InstitutionDoctorRepository;

import java.sql.SQLException;
import java.util.List;

@Log4j2
public class InstitutionDoctorService implements OneToManyService {

    private InstitutionDoctorRepository institutionDoctorRepository;

    public InstitutionDoctorService() {
        this.institutionDoctorRepository = new InstitutionDoctorRepository();
    }

    @Override
    public void saveNewRelationship(int institutionId, int doctorId) throws SQLException {
        log.info("Save new relation institution-doctor");
        institutionDoctorRepository.saveNewRelationship(institutionId, doctorId);
    }

    @Override
    public void deleteRelation(int doctorId) throws SQLException {
        log.info("Delete relation institution-doctor");
        institutionDoctorRepository.deleteRelation(doctorId);
    }

    @Override
    public List<Doctor> getAllById(int institutionId) throws SQLException {
        log.info("Get all relations institution-doctor");
        return institutionDoctorRepository.getAllById(institutionId);
    }
}