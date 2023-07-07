package service;

import lombok.extern.log4j.Log4j2;
import model.Patient;
import repository.PatientRepository;

import java.sql.SQLException;
import java.util.List;

@Log4j2
public class PatientService implements CrudService<Patient> {

    private PatientRepository patientRepository;

    public PatientService() {
        this.patientRepository = new PatientRepository();
    }

    @Override
    public void addNew(Patient patient) throws SQLException {
        log.info("Save new patient");
        patientRepository.saveNew(patient);
    }

    @Override
    public void update(Patient patient) throws SQLException {
        log.info("Update patient");
        patientRepository.update(patient);
    }

    @Override
    public Patient getOneByIndex(int i) throws SQLException {
        log.info("Get one patient");
        return patientRepository.getOneByIndex(i);
    }

    @Override
    public List<Patient> getAll() throws SQLException {
        log.info("Get all patient");
        return patientRepository.getAll();
    }

    @Override
    public void deleteOneById(int i) throws SQLException {
        log.info("Delete patient");
        patientRepository.deleteOneById(i);
    }
}