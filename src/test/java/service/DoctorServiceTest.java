package service;

import configuration.ConnectionBuilder;
import model.Doctor;
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
import static org.junit.Assert.assertThrows;

class DoctorServiceTest {

    static DoctorService doctorService;

    @BeforeAll
    static void init() {
        doctorService = new DoctorService();
    }

    @BeforeEach
    void config() throws IOException, SQLException {
        String schema = Files.readString(Paths.get("src/test/resources/schema.sql"));
        Statement statement = ConnectionBuilder.getConnection().createStatement();
        statement.executeUpdate(schema);
    }

    @Test
    void addNew_test_default() throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setSalary(1000);
        doctor.setDescription("test");
        doctor.setPhoneNumber("375333024545");
        doctorService.addNew(doctor);
        Doctor testDoctor = doctorService.getOneByIndex(1);
        assertEquals(doctor, testDoctor);
    }

    @Test
    void update_test_default() throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setSalary(1000);
        doctor.setDescription("test");
        doctor.setPhoneNumber("375333024545");
        doctorService.addNew(doctor);
        doctor.setDescription("test2");
        doctorService.update(doctor);
        Doctor testDoctor = doctorService.getOneByIndex(1);
        assertEquals(doctor, testDoctor);
    }

    @Test
    void getOneByIndex_test_default() throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setSalary(1000);
        doctor.setDescription("test2");
        doctor.setPhoneNumber("375333024545");
        doctorService.addNew(doctor);
        Doctor testDoctor = doctorService.getOneByIndex(1);
        assertEquals(doctor, testDoctor);
    }

    @Test
    void getAll_test_default() throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setSalary(1000);
        doctor.setDescription("test");
        doctor.setPhoneNumber("375333024545");
        doctorService.addNew(doctor);
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctor);
        List<Doctor> testDoctorList = doctorService.getAll();
        assertEquals(doctorList, testDoctorList);
    }

    @Test
    void deleteOneById_test_default() throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setSalary(1000);
        doctor.setDescription("test");
        doctor.setPhoneNumber("375333024545");
        doctorService.addNew(doctor);
        doctorService.deleteOneById(1);
        assertThrows(SQLException.class, ()-> doctorService.getOneByIndex(1));
    }
}