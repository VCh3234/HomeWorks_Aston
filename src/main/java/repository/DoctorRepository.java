package repository;

import configuration.ConnectionBuilder;
import lombok.extern.log4j.Log4j2;
import model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for access to DB for operations with doctor entity
 */
@Log4j2
public class DoctorRepository implements Repository<Doctor> {

    @Override
    public void saveNew(Doctor doctor) throws SQLException {
        Connection c = ConnectionBuilder.getConnection();
        log.info("Save doctor from repository");
        PreparedStatement ps = c.prepareStatement("INSERT INTO doctor(description, salary, phone_number) values(?,?,?)");
        ps.setString(1, doctor.getDescription());
        ps.setInt(2, doctor.getSalary());
        ps.setString(3, doctor.getPhoneNumber());
        int result = ps.executeUpdate();
        if (result == 0) {
            log.error("Failed to save doctor");
            throw new SQLException("Nothing was updated");
        }
    }

    @Override
    public void update(Doctor doctor) throws SQLException {
        try (Connection c = ConnectionBuilder.getConnection()) {
            log.info("Update doctor from repository");
            PreparedStatement ps = c.prepareStatement("UPDATE doctor SET description = ?, salary = ?, phone_number = ? WHERE id = ?");
            ps.setString(1, doctor.getDescription());
            ps.setInt(2, doctor.getSalary());
            ps.setString(3, doctor.getPhoneNumber());
            ps.setInt(4, doctor.getId());
            int result = ps.executeUpdate();
            if (result == 0) {
                log.error("Failed to update doctor");
                throw new SQLException("Nothing was updated");
            }
        }
    }

    @Override
    public Doctor getOneByIndex(int i) throws SQLException {
        log.info("Get doctor from repository");
        Doctor doctor;
        try (Connection c = ConnectionBuilder.getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM doctor WHERE id = ?");
            ps.setInt(1, i);
            ps.execute();
            ResultSet result = ps.getResultSet();
            if (result == null) {
                log.error("Failed to get doctor");
                throw new SQLException("Nothing was got");
            }
            result.next();
            doctor = new Doctor();
            doctor.setId(i);
            doctor.setDescription(result.getString("description"));
            doctor.setPhoneNumber(result.getString("phone_number"));
            doctor.setSalary(result.getInt("salary"));
        }
        return doctor;
    }

    @Override
    public List<Doctor> getAll() throws SQLException {
        log.info("Get all doctors from repository");
        List<Doctor> doctorList = new ArrayList<>();
        try (Connection c = ConnectionBuilder.getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM doctor");
            ps.execute();
            ResultSet result = ps.getResultSet();
            if (result == null) {
                log.error("Failed to get doctors");
                throw new SQLException("Nothing was got");
            }
            while (result.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(result.getInt("id"));
                doctor.setDescription(result.getString("description"));
                doctor.setPhoneNumber(result.getString("phone_number"));
                doctor.setSalary(result.getInt("salary"));
                doctorList.add(doctor);
            }
        }
        return doctorList;
    }

    @Override
    public void deleteOneById(int i) throws SQLException {
        log.info("Delete doctor from repository");
        Connection c = ConnectionBuilder.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM doctor WHERE id = ?");
        ps.setInt(1, i);
        int result = ps.executeUpdate();
        if (result == 0) {
            log.error("Failed to delete doctor");
            throw new SQLException("Nothing was deleted");
        }
    }
}