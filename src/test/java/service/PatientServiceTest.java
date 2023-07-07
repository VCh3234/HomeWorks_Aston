package service;

import configuration.ConnectionBuilder;
import model.Patient;
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

class PatientServiceTest {


    static PatientService patientService;

    @BeforeAll
    static void init() {
        patientService = new PatientService();
    }

    @BeforeEach
    void config() throws IOException, SQLException {
        String schema = Files.readString(Paths.get("src/test/resources/schema.sql"));
        Statement statement = ConnectionBuilder.getConnection().createStatement();
        statement.executeUpdate(schema);
    }
    @Test
    void addNew_test_default() throws SQLException {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setAddress("testAddress");
        patient.setInsuranceNumber(100);
        patient.setPhoneNumber("phone");
        patientService.addNew(patient);
        Patient patientTest = patientService.getOneByIndex(1);
        assertEquals(patient, patientTest);
    }

    @Test
    void update_test_default() throws SQLException {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setAddress("testAddress");
        patient.setInsuranceNumber(100);
        patient.setPhoneNumber("phone");
        patientService.addNew(patient);
        patient.setPhoneNumber("phone2");
        patientService.update(patient);
        Patient patientTest = patientService.getOneByIndex(1);
        assertEquals(patient, patientTest);
    }

    @Test
    void getOneByIndex_test_default() throws SQLException {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setAddress("testAddress");
        patient.setInsuranceNumber(100);
        patient.setPhoneNumber("phone");
        patientService.addNew(patient);
        Patient patientTest = patientService.getOneByIndex(1);
        assertEquals(patient, patientTest);
    }

    @Test
    void getAll_test_default() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        Patient patient = new Patient();
        patient.setId(1);
        patient.setAddress("test");
        patient.setPhoneNumber("test");
        patient.setInsuranceNumber(100);
        patientList.add(patient);
        patientService.addNew(patient);
        List<Patient> patientsListTest = patientService.getAll();
        assertEquals(patientList, patientsListTest);
    }

    @Test
    void deleteOneById_test_default() throws SQLException {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setAddress("testAddress");
        patient.setInsuranceNumber(100);
        patient.setPhoneNumber("phone");
        patientService.addNew(patient);
        patientService.deleteOneById(1);
        assertThrows(SQLException.class, ()-> patientService.getOneByIndex(1));
    }
}