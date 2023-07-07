package repository;

import configuration.ConnectionBuilder;
import lombok.extern.log4j.Log4j2;
import model.HealthCareInstitution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class for access to DB for operations with healthCareInstitution entity*/
@Log4j2
public class HealthCareInstitutionRepository implements Repository<HealthCareInstitution> {

    @Override
    public void saveNew(HealthCareInstitution healthCareInstitution) throws SQLException {
        log.info("Save institution in repository");
        Connection c = ConnectionBuilder.getConnection();
        PreparedStatement ps =
                c.prepareStatement("INSERT INTO health_care_institution(description, address, free) values(?, ?,?)");
        ps.setString(1, healthCareInstitution.getDescription());
        ps.setBoolean(3, healthCareInstitution.isFree());
        ps.setString(2, healthCareInstitution.getAddress());
        int result = ps.executeUpdate();
        if (result == 0) {
            log.error("Failed to save institution");
            throw new SQLException("Nothing was updated");
        }
    }

    @Override
    public void update(HealthCareInstitution healthCareInstitution) throws SQLException {
        log.info("Update institution in repository");
        try (Connection c = ConnectionBuilder.getConnection()) {
            PreparedStatement ps =
                    c.prepareStatement("UPDATE health_care_institution SET description = ?, address = ?, free = ? WHERE id = ?");
            ps.setString(1, healthCareInstitution.getDescription());
            ps.setBoolean(3, healthCareInstitution.isFree());
            ps.setString(2, healthCareInstitution.getAddress());
            ps.setInt(4, healthCareInstitution.getId());
            int result = ps.executeUpdate();
            if (result == 0) {
                log.error("Failed to update institution");
                throw new SQLException("Nothing was updated");
            }
        }
    }

    @Override
    public HealthCareInstitution getOneByIndex(int i) throws SQLException {
        log.info("Get institution from repository");
        HealthCareInstitution healthCareInstitution;
        try (Connection c = ConnectionBuilder.getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM health_care_institution WHERE id = ?");
            ps.setInt(1, i);
            ps.execute();
            ResultSet result = ps.getResultSet();
            if (result == null) {
                log.error("Failed to get institution");
                throw new SQLException("Nothing was got");
            }

            result.next();
            healthCareInstitution = new HealthCareInstitution();
            healthCareInstitution.setId(i);
            healthCareInstitution.setDescription(result.getString("description"));
            healthCareInstitution.setAddress(result.getString("address"));
            healthCareInstitution.setFree(result.getBoolean("free"));
        }
        return healthCareInstitution;
    }

    @Override
    public List<HealthCareInstitution> getAll() throws SQLException {
        log.info("Get all institutions from repository");
        List<HealthCareInstitution> healthCareInstitutionList = new ArrayList<>();
        try (Connection c = ConnectionBuilder.getConnection()) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM health_care_institution");
            ps.execute();
            ResultSet result = ps.getResultSet();
            if (result == null) {
                log.error("Failed to get institutions");
                throw new SQLException("Nothing was got");
            }
            while (result.next()) {
                HealthCareInstitution healthCareInstitution = new HealthCareInstitution();
                healthCareInstitution.setId(result.getInt("id"));
                healthCareInstitution.setDescription(result.getString("description"));
                healthCareInstitution.setAddress(result.getString("address"));
                healthCareInstitution.setFree(result.getBoolean("free"));
                healthCareInstitutionList.add(healthCareInstitution);
            }
        }
        return healthCareInstitutionList;
    }

    @Override
    public void deleteOneById(int i) throws SQLException {
        log.info("Delete institution from repository");
        Connection c = ConnectionBuilder.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM health_care_institution WHERE id = ?");
        ps.setInt(1, i);
        int result = ps.executeUpdate();
        if (result == 0) {
            log.error("Failed to delete institution");
            throw new SQLException("Nothing was deleted");
        }
    }
}