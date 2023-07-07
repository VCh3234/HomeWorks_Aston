package service;

import configuration.ConnectionBuilder;
import model.Doctor;
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

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatientDoctorServiceTest {

    static PatientDoctorService patientDoctorService;
    static DoctorService doctorService;
    static PatientService patientService;

    @BeforeAll
    static void init() {
        patientDoctorService = new PatientDoctorService();
        doctorService = new DoctorService();
        patientService = new PatientService();
    }

    @BeforeEach
    void config() throws IOException, SQLException {
        String schema = Files.readString(Paths.get("src/test/resources/schema.sql"));
        Statement statement = ConnectionBuilder.getConnection().createStatement();
        statement.executeUpdate(schema);
    }
    @Test
    void addRelation_test_default() throws SQLException {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setAddress("testAddress");
        patient.setInsuranceNumber(100);
        patient.setPhoneNumber("phone");
        patientService.addNew(patient);

        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setSalary(1000);
        doctor.setDescription("test");
        doctor.setPhoneNumber("375333024545");
        doctorService.addNew(doctor);

        patientDoctorService.addRelation(patient.getId(), doctor.getId());
        List<Doctor> doctorList = patientDoctorService.getAllById(patient.getId());
        assertTrue(doctorList.contains(doctor));
    }

    @Test
    void deleteRelation_test_default() throws SQLException {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setAddress("testAddress");
        patient.setInsuranceNumber(100);
        patient.setPhoneNumber("phone");
        patientService.addNew(patient);

        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setSalary(1000);
        doctor.setDescription("test");
        doctor.setPhoneNumber("375333024545");
        doctorService.addNew(doctor);

        patientDoctorService.addRelation(patient.getId(), doctor.getId());
        List<Doctor> doctorList = patientDoctorService.getAllById(patient.getId());
        assertTrue(doctorList.contains(doctor));

        patientDoctorService.deleteRelation(patient.getId(), doctor.getId());

        List<Doctor> doctorListTest = patientDoctorService.getAllById(patient.getId());
        assertFalse(doctorListTest.contains(doctor));
    }

    @Test
    void getAllById_test_default() throws SQLException {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setAddress("testAddress");
        patient.setInsuranceNumber(100);
        patient.setPhoneNumber("phone");
        patientService.addNew(patient);

        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setSalary(1000);
        doctor.setDescription("test");
        doctor.setPhoneNumber("375333024545");
        doctorService.addNew(doctor);

        Doctor doctor2 = new Doctor();
        doctor2.setId(2);
        doctor2.setSalary(100000);
        doctor2.setDescription("testDescription");
        doctor2.setPhoneNumber("375333024545");
        doctorService.addNew(doctor2);

        patientDoctorService.addRelation(patient.getId(), doctor.getId());
        patientDoctorService.addRelation(patient.getId(), doctor2.getId());
        List<Doctor> doctorListTest = patientDoctorService.getAllById(patient.getId());
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctor);
        doctorList.add(doctor2);
        assertEquals(doctorList, doctorListTest);
    }
}