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
 * Class for access to DB for operations with institution-doctor relations (one-to-many)*/
@Log4j2
public class InstitutionDoctorRepository implements OneToManyRepository {

    @Override
    public void saveNewRelationship(int institutionId, int doctorId) throws SQLException {
        Connection c = ConnectionBuilder.getConnection();
        log.info("Save institution-doctor relation");
        PreparedStatement ps =
                c.prepareStatement("UPDATE doctor SET health_care_institution_id = ? WHERE id = ?");
        ps.setInt(1, institutionId);
        ps.setInt(2, doctorId);
        int result = ps.executeUpdate();
        if (result == 0) {
            log.error("Failed to save relation");
            throw new SQLException("Nothing was updated");
        }
    }

    @Override
    public void deleteRelation(int doctorId) throws SQLException {
        log.info("Delete institution-doctor relation");
        Connection c = ConnectionBuilder.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE doctor SET health_care_institution_id = NULL WHERE id = ?");
        ps.setInt(1, doctorId);
        int result = ps.executeUpdate();
        if (result == 0) {
            log.error("Failed to delete patient-doctor relation");
            throw new SQLException("Nothing was deleted");
        }
    }

    @Override
    public List<Doctor> getAllById(int institutionId) throws SQLException {
        log.info("Get all doctors of institution");
        List<Doctor> doctorList = new ArrayList<>();
        Connection c = ConnectionBuilder.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM doctor WHERE health_care_institution_id = ?");
        ps.setInt(1, institutionId);
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