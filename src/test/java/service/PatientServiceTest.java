package service;

import model.Doctor;
import model.Patient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatientServiceTest {

    private static PatientService patientService;
    private static DoctorService doctorService;

    @BeforeAll
    static void init() {
        patientService = new PatientService();
        doctorService = new DoctorService();
    }

    @Test
    void addNew_addRecordInDb() {
        Patient patient = new Patient();
        patient.setAddress("testAdress");
        patient.setPhoneNumber("testPhone");
        patient.setInsuranceNumber(1111);
        patientService.addNew(patient);

        Patient patientTest = patientService.getOneByIndex(patient.getId());
        assertEquals(patient, patientTest);
    }

    @Test
    void update_updateDataOfPatientInDb() {
        Patient patient = new Patient();
        patient.setAddress("testAdress");
        patient.setPhoneNumber("testPhone");
        patient.setInsuranceNumber(2);
        patientService.addNew(patient);
        patient.setInsuranceNumber(2222);
        patientService.update(patient);

        Patient patientActual = patientService.getOneByIndex(patient.getId());
        assertEquals(patient, patientActual);
    }

    @Test
    void getOneByIndex_returnInstanceByIdInDb() {
        Patient patient = new Patient();
        patient.setAddress("testAdress");
        patient.setPhoneNumber("testPhone");
        patient.setInsuranceNumber(3333);
        patientService.addNew(patient);

        Patient patientActual = patientService.getOneByIndex(patient.getId());
        assertEquals(patient, patientActual);
    }

    @Test
    void getAll_returnAllInstancesOfPatients() {
        List<Patient> patientList = patientService.getAll();

        Patient patient = new Patient();
        patient.setAddress("testAdress");
        patient.setPhoneNumber("testPhone");
        patient.setInsuranceNumber(44444);

        patientService.addNew(patient);
        patientList.add(patient);

        List<Patient> patientListActual = patientService.getAll();
        assertEquals(patientList, patientListActual);
    }

    @Test
    void deleteOneById_deleteRecordAboutPatient() {
        Patient patient = new Patient();
        patient.setAddress("testAdress");
        patient.setPhoneNumber("testPhone");
        patient.setInsuranceNumber(44444);

        patientService.addNew(patient);
        List<Patient> patientList = patientService.getAll();

        Patient patientActual = patientList.remove(0);
        patientService.deleteOneById(patientActual.getId());

        List<Patient> patientListActual = patientService.getAll();
        assertEquals(patientList, patientListActual);
    }

    @Test
    void addDoctor_addRelationshipInDb() {
        Patient patient = new Patient();
        patient.setInsuranceNumber(123);
        patient.setAddress("testAddress");
        patient.setPhoneNumber("testPhone");
        patientService.addNew(patient);

        Doctor doctor = new Doctor();
        doctor.setPhoneNumber("testPhone");
        doctor.setDescription("testDescription");
        doctor.setSalary(1000);
        doctorService.addNew(doctor);

        patientService.addDoctor(patient.getId(), doctor.getId());
        patient = patientService.getOneByIndexWithDoctor(patient.getId());
        assertTrue(patient.getDoctorList().contains(doctor));
    }

    @Test
    void deleteDoctor_deleteRelationshipInDb() {
        Patient patient = new Patient();
        patient.setInsuranceNumber(123);
        patient.setAddress("testAddress");
        patient.setPhoneNumber("testPhone");
        patientService.addNew(patient);

        Doctor doctor = new Doctor();
        doctor.setPhoneNumber("testPhone");
        doctor.setDescription("testDescription");
        doctor.setSalary(1000);
        doctorService.addNew(doctor);

        patientService.addDoctor(patient.getId(), doctor.getId());
        patient = patientService.getOneByIndexWithDoctor(patient.getId());
        assertTrue(patient.getDoctorList().contains(doctor));

        patientService.deleteDoctor(patient.getId(), doctor.getId());
        patient = patientService.getOneByIndexWithDoctor(patient.getId());
        assertFalse(patient.getDoctorList().contains(doctor));
    }
}