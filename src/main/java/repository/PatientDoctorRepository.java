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
 * Class for access to DB for operations with patient-doctor relations (many-to-many)*/
@Log4j2
public class PatientDoctorRepository implements ManyToManyRepository {

    @Override
    public void saveNewRelationship(int patientId, int doctorId) throws SQLException {
        Connection c = ConnectionBuilder.getConnection();
        log.info("Save patient-doctor relation");
        PreparedStatement ps =
                c.prepareStatement("INSERT INTO doctor_patient(p_id, d_id) values(?,?)");
        ps.setInt(1, patientId);
        ps.setInt(2, doctorId);
        int result = ps.executeUpdate();
        if (result == 0) {
            log.error("Failed to save relation");
            throw new SQLException("Nothing was updated");
        }
    }

    @Override
    public void deleteRelation(int patientId, int doctorId) throws SQLException {
        log.info("Delete patient-doctor relation");
        Connection c = ConnectionBuilder.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM doctor_patient WHERE p_id= ? AND d_id = ?");
        ps.setInt(1, patientId);
        ps.setInt(2, doctorId);
        int result = ps.executeUpdate();
        if (result == 0) {
            log.error("Failed to delete patient-doctor relation");
            throw new SQLException("Nothing was deleted");
        }
    }

    @Override
    public List<Doctor> getAllById(int patientId) throws SQLException {
        log.info("Get all doctors of patient");
        List<Doctor> doctorList = new ArrayList<>();
        Connection c = ConnectionBuilder.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT " +
                "doctor.id, doctor.description, doctor.phone_number, doctor.salary " +
                "FROM doctor_patient dp " +
                "JOIN patient ON dp.p_id = patient.id " +
                "JOIN doctor ON dp.d_id = doctor.id " +
                "WHERE patient.id = ?");
        ps.setInt(1, patientId);
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
        return doctorList;
    }
}