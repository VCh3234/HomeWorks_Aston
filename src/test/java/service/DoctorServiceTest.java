package service;

import model.Doctor;
import model.PaidClinic;
import model.Patient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DoctorServiceTest {

    private static PatientService patientService;

    private static DoctorService doctorService;

    private static HealthCareInstitutionService healthCareInstitutionService;

    @BeforeAll
    static void init() {
        patientService = new PatientService();
        doctorService = new DoctorService();
        healthCareInstitutionService = new HealthCareInstitutionService();
    }

    @Test
    void update_updateEntityInDb() {
        Doctor doctor = new Doctor();
        doctor.setPhoneNumber("testPhone");
        doctor.setDescription("testDescription");
        doctor.setSalary(1000);

        doctorService.addNew(doctor);
        doctor.setDescription("testDescriptionChanged");
        doctorService.update(doctor);

        Doctor doctorActual = doctorService.getOneByIndex(doctor.getId());
        assertEquals(doctor, doctorActual);
    }

    @Test
    void getAll_returnAllDoctorsFromDb() {
        List<Doctor> doctorList = doctorService.getAll();
        Set<Doctor> doctorSet = new HashSet<>(doctorList);

        Doctor doctor = new Doctor();
        doctor.setPhoneNumber("testPhone");
        doctor.setDescription("testDescription");
        doctor.setSalary(1000);

        doctorService.addNew(doctor);
        doctorSet.add(doctor);

        List<Doctor> doctorListActual = doctorService.getAll();
        Set<Doctor> doctorSetActual = new HashSet<>(doctorListActual);
        assertEquals(doctorSet, doctorSetActual);
    }

    @Test
    void deleteOneById_deleteRecordFromDb() {
        Doctor doctor = new Doctor();
        doctor.setPhoneNumber("testPhone");
        doctor.setDescription("testDescription");
        doctor.setSalary(1000);
        doctorService.addNew(doctor);

        List<Doctor> doctorList = doctorService.getAll();
        assertTrue(doctorList.contains(doctor));

        doctorService.deleteOneById(doctor.getId());
        doctorList = doctorService.getAll();
        assertFalse(doctorList.contains(doctor));
    }

    @Test
    void getOneByIndexWithPatient_returnDoctorInstanceWithPatientList() {
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
        doctor = doctorService.getOneByIndexWithPatient(doctor.getId());
        assertTrue(doctor.getPatientList().contains(patient));
    }

    @Test
    void changeRelationInstitution_addOrUpdateRelationship() {
        PaidClinic paidClinic = new PaidClinic();
        paidClinic.setOwner("testOwner");
        paidClinic.setDescription("testDescription");
        paidClinic.setAddress("testAddress");
        healthCareInstitutionService.addNew(paidClinic);

        Doctor doctor = new Doctor();
        doctor.setPhoneNumber("testPhone");
        doctor.setDescription("testDescription");
        doctor.setSalary(1000);
        doctorService.addNew(doctor);

        doctorService.changeRelationInstitution(doctor.getId(), paidClinic.getId());

        doctor = doctorService.getOneByIndexWithPatient(doctor.getId());
        assertEquals(paidClinic, doctor.getInstitution());
    }

    @Test
    void deleteRelationWithInstitution_deleteRelationship() {
        PaidClinic paidClinic = new PaidClinic();
        paidClinic.setOwner("testOwner");
        paidClinic.setDescription("testDescription");
        paidClinic.setAddress("testAddress");
        healthCareInstitutionService.addNew(paidClinic);

        Doctor doctor = new Doctor();
        doctor.setPhoneNumber("testPhone");
        doctor.setDescription("testDescription");
        doctor.setSalary(1000);
        doctorService.addNew(doctor);

        doctorService.changeRelationInstitution(doctor.getId(), paidClinic.getId());

        doctor = doctorService.getOneByIndexWithPatient(doctor.getId());
        assertEquals(paidClinic, doctor.getInstitution());

        doctorService.deleteRelationWithInstitution(doctor.getId());
        doctor = doctorService.getOneByIndexWithPatient(doctor.getId());
        assertNull(doctor.getInstitution());
    }

    @Test
    void addPatient_shouldThrowUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()-> doctorService.addPatient(1, 1));

    }

    @Test
    void deletePatient_shouldThrowUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()-> doctorService.deletePatient(1, 1));
    }
}