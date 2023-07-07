package repository;

import configuration.ConnectionBuilder;
import lombok.extern.log4j.Log4j2;
import model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class for access to DB for operations with patient entity*/
@Log4j2
public class PatientRepository implements Repository<Patient> {

    @Override
    public void saveNew(Patient patient) throws SQLException {
        Connection c = ConnectionBuilder.getConnection();
        log.info("Save patient from repository");
        PreparedStatement ps =
                c.prepareStatement("INSERT INTO patient(address, phone_number, insurance_number) values(?, ?, ?)");
        ps.setString(1, patient.getAddress());
        ps.setString(2, patient.getPhoneNumber());
        ps.setInt(3, patient.getInsuranceNumber());
        int result = ps.executeUpdate();
        if (result == 0) {
            log.error("Failed to save patient");
            throw new SQLException("Nothing was updated");
        }
    }

    @Override
    public void update(Patient patient) throws SQLException {
        try (Connection c = ConnectionBuilder.getConnection()) {
            log.info("Update patient from repository");
            PreparedStatement ps =
                    c.prepareStatement("UPDATE patient SET address = ?, phone_number = ?, insurance_number = ? WHERE id = ?");
            ps.setString(1, patient.getAddress());
            ps.setString(2, patient.getPhoneNumber());
            ps.setInt(3, patient.getInsuranceNumber());
            ps.setInt(4, patient.getId());
            int result = ps.executeUpdate();
            if (result == 0) {
                log.error("Failed to update patient");
                throw new SQLException("Nothing was updated");
            }
        }
    }

    @Override
    public Patient getOneByIndex(int i) throws SQLException {
        Patient patient;
        try (Connection c = ConnectionBuilder.getConnection()) {
            log.info("Get patient from repository");
            PreparedStatement ps = c.prepareStatement("SELECT * FROM patient WHERE id = ?");
            ps.setInt(1, i);
            ps.execute();
            ResultSet result = ps.getResultSet();
            if (result == null) {
                log.error("Failed to get patient");
                throw new SQLException("Nothing was got");
            }

            result.next();
            patient = new Patient();
            patient.setId(i);
            patient.setAddress(result.getString("address"));
            patient.setPhoneNumber(result.getString("phone_number"));
            patient.setInsuranceNumber(result.getInt("insurance_number"));
        }
        return patient;
    }

    @Override
    public List<Patient> getAll() throws SQLException {
        List<Patient> patientArrayList = new ArrayList<>();
        try (Connection c = ConnectionBuilder.getConnection()) {
            log.info("Get all patients from repository");
            PreparedStatement ps = c.prepareStatement("SELECT * FROM patient");
            ps.execute();
            ResultSet result = ps.getResultSet();
            if (result == null) {
                log.error("Failed to get patient");
                throw new SQLException("Nothing was got");
            }
            while (result.next()) {
                Patient patient = new Patient();
                patient.setId(result.getInt("id"));
                patient.setAddress(result.getString("address"));
                patient.setPhoneNumber(result.getString("phone_number"));
                patient.setInsuranceNumber(result.getInt("insurance_number"));
                patientArrayList.add(patient);
            }
        }
        return patientArrayList;
    }

    @Override
    public void deleteOneById(int i) throws SQLException {
        log.info("Delete patient from repository");
        Connection c = ConnectionBuilder.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM patient WHERE id = ?");
        ps.setInt(1, i);
        int result = ps.executeUpdate();
        if (result == 0) {
            log.error("Failed to delete patient");
            throw new SQLException("Nothing was deleted");
        }
    }
}