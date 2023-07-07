package service;

import configuration.ConnectionBuilder;
import model.Doctor;
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

import static org.junit.jupiter.api.Assertions.*;

class InstitutionDoctorServiceTest {

    static InstitutionDoctorService institutionDoctorService;
    static DoctorService doctorService;

    static HealthCareInstitutionService healthCareInstitutionService;

    @BeforeAll
    static void init() {
        institutionDoctorService = new InstitutionDoctorService();
        doctorService = new DoctorService();
        healthCareInstitutionService = new HealthCareInstitutionService();
    }

    @BeforeEach
    void config() throws IOException, SQLException {
        String schema = Files.readString(Paths.get("src/test/resources/schema.sql"));
        Statement statement = ConnectionBuilder.getConnection().createStatement();
        statement.executeUpdate(schema);
    }

    @Test
    void saveNewRelationship_test_default() throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setSalary(1000);
        doctor.setDescription("test");
        doctor.setPhoneNumber("375333024545");
        doctorService.addNew(doctor);

        HealthCareInstitution healthCareInstitution = new HealthCareInstitution();
        healthCareInstitution.setId(1);
        healthCareInstitution.setDescription("testDescription");
        healthCareInstitution.setAddress("testAddress");
        healthCareInstitution.setFree(true);
        healthCareInstitutionService.addNew(healthCareInstitution);

        institutionDoctorService.saveNewRelationship(healthCareInstitution.getId(), doctor.getId());
        List<Doctor> doctorList = institutionDoctorService.getAllById(healthCareInstitution.getId());
        assertTrue(doctorList.contains(doctor));
    }

    @Test
    void deleteRelation_test_default() throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setSalary(1000);
        doctor.setDescription("test");
        doctor.setPhoneNumber("375333024545");
        doctorService.addNew(doctor);

        HealthCareInstitution healthCareInstitution = new HealthCareInstitution();
        healthCareInstitution.setId(1);
        healthCareInstitution.setDescription("testDescription");
        healthCareInstitution.setAddress("testAddress");
        healthCareInstitution.setFree(true);
        healthCareInstitutionService.addNew(healthCareInstitution);

        institutionDoctorService.saveNewRelationship(healthCareInstitution.getId(), doctor.getId());
        List<Doctor> doctorList = institutionDoctorService.getAllById(healthCareInstitution.getId());
        assertTrue(doctorList.contains(doctor));

        institutionDoctorService.deleteRelation(doctor.getId());

        List<Doctor> doctorList2 = institutionDoctorService.getAllById(healthCareInstitution.getId());
        assertFalse(doctorList2.contains(doctor));
    }

    @Test
    void getAllById_test_default() throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setSalary(1000);
        doctor.setDescription("test");
        doctor.setPhoneNumber("375333024545");
        doctorService.addNew(doctor);

        Doctor doctor2 = new Doctor();
        doctor2.setId(2);
        doctor2.setSalary(1000);
        doctor2.setDescription("testTEST");
        doctor2.setPhoneNumber("375333024545");
        doctorService.addNew(doctor2);

        HealthCareInstitution healthCareInstitution = new HealthCareInstitution();
        healthCareInstitution.setId(1);
        healthCareInstitution.setDescription("testDescription");
        healthCareInstitution.setAddress("testAddress");
        healthCareInstitution.setFree(true);
        healthCareInstitutionService.addNew(healthCareInstitution);

        institutionDoctorService.saveNewRelationship(healthCareInstitution.getId(), doctor.getId());
        institutionDoctorService.saveNewRelationship(healthCareInstitution.getId(), doctor2.getId());
        List<Doctor> doctorListTest = institutionDoctorService.getAllById(healthCareInstitution.getId());
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctor);
        doctorList.add(doctor2);
        assertEquals(doctorList, doctorListTest);
    }
}