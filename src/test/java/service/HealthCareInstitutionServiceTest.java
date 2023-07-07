package service;

import configuration.ConnectionBuilder;
import model.HealthCareInstitution;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HealthCareInstitutionServiceTest {

    static HealthCareInstitutionService healthCareInstitutionService;

    @BeforeAll
    static void init() {
        healthCareInstitutionService = new HealthCareInstitutionService();
    }

    @BeforeEach
    void config() throws IOException, SQLException {
        String schema = Files.readString(Paths.get("src/test/resources/schema.sql"));
        Statement statement = ConnectionBuilder.getConnection().createStatement();
        statement.executeUpdate(schema);
    }

    @Test
    void addNew_test_default() throws SQLException {
        HealthCareInstitution institution = new HealthCareInstitution();
        institution.setAddress("test");
        institution.setId(1);
        institution.setDescription("testDescription");
        institution.setFree(true);
        healthCareInstitutionService.addNew(institution);
        HealthCareInstitution institutionTest = healthCareInstitutionService.getOneByIndex(1);
        assertEquals(institution, institutionTest);
    }

    @Test
    void update_test_default() throws SQLException {
        HealthCareInstitution institution = new HealthCareInstitution();
        institution.setAddress("test");
        institution.setId(1);
        institution.setDescription("testDescription");
        institution.setFree(true);
        healthCareInstitutionService.addNew(institution);
        institution.setDescription("testDescription2");
        healthCareInstitutionService.update(institution);
        HealthCareInstitution institutionTest = healthCareInstitutionService.getOneByIndex(1);
        assertEquals(institution, institutionTest);
    }

    @Test
    void getOneByIndex_test_default() throws SQLException {
        HealthCareInstitution institution = new HealthCareInstitution();
        institution.setAddress("test");
        institution.setId(1);
        institution.setDescription("testDescription");
        institution.setFree(true);
        healthCareInstitutionService.addNew(institution);
        HealthCareInstitution institutionTest = healthCareInstitutionService.getOneByIndex(1);
        assertEquals(institution, institutionTest);
    }

    @Test
    void getAll_test_default() throws SQLException {
        List<HealthCareInstitution> institutionList = new ArrayList<>();
        HealthCareInstitution institution = new HealthCareInstitution();
        institution.setAddress("test");
        institution.setId(1);
        institution.setDescription("testDescription");
        institution.setFree(true);
        institutionList.add(institution);
        healthCareInstitutionService.addNew(institution);
        List<HealthCareInstitution> institutionListTest = healthCareInstitutionService.getAll();
        assertEquals(institutionList, institutionListTest);
    }

    @Test
    void deleteOneById_test_default() throws SQLException {
        HealthCareInstitution institution = new HealthCareInstitution();
        institution.setAddress("test");
        institution.setId(1);
        institution.setDescription("testDescription");
        institution.setFree(true);
        healthCareInstitutionService.addNew(institution);
        healthCareInstitutionService.deleteOneById(1);
        assertThrows(SQLException.class, () -> healthCareInstitutionService.getOneByIndex(1));
    }
}