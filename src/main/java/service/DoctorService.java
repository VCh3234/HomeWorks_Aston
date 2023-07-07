package service;

import lombok.extern.log4j.Log4j2;
import model.Doctor;
import repository.DoctorRepository;

import java.sql.SQLException;
import java.util.List;

@Log4j2
public class DoctorService implements CrudService<Doctor> {
    private DoctorRepository doctorRepository;

    public DoctorService() {
        this.doctorRepository = new DoctorRepository();
    }

    @Override
    public void addNew(Doctor doctor) throws SQLException {
        log.info("Save new doctor");
        doctorRepository.saveNew(doctor);
    }

    @Override
    public void update(Doctor doctor) throws SQLException {
        log.info("Update doctor");
        doctorRepository.update(doctor);
    }

    @Override
    public Doctor getOneByIndex(int i) throws SQLException {
        log.info("Get one doctor");
        return doctorRepository.getOneByIndex(i);
    }

    @Override
    public List<Doctor> getAll() throws SQLException {
        log.info("Get all doctors");
        return doctorRepository.getAll();
    }

    @Override
    public void deleteOneById(int i) throws SQLException {
        log.info("Delete doctor");
        doctorRepository.deleteOneById(i);
    }
}