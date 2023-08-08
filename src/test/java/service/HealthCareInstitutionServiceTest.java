package service;

import model.Doctor;
import model.HealthCareInstitution;
import model.PaidClinic;
import model.PublicClinic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.NoResultException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HealthCareInstitutionServiceTest {

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
    void addNew_addRecordInDb() {
        PaidClinic paidClinic = new PaidClinic();
        paidClinic.setAddress("testAddress");
        paidClinic.setDescription("testDescription");
        paidClinic.setOwner("testOwner");
        healthCareInstitutionService.addNew(paidClinic);
        HealthCareInstitution institutionActual = healthCareInstitutionService.getOneByIndex(paidClinic.getId());
        assertEquals(paidClinic, institutionActual);
    }

    @Test
    void update_updateEntityInDb() {
        PaidClinic paidClinic = new PaidClinic();
        paidClinic.setAddress("testAddress");
        paidClinic.setDescription("testDescription");
        paidClinic.setOwner("testOwner");

        healthCareInstitutionService.addNew(paidClinic);
        paidClinic.setOwner("testOwnerChanged");
        healthCareInstitutionService.update(paidClinic);

        HealthCareInstitution institutionActual = healthCareInstitutionService.getOneByIndex(paidClinic.getId());
        assertEquals(paidClinic, institutionActual);
    }

    @Test
    void getAll_returnAllInstitutions_withPolymorphism() {
        PaidClinic paidClinic = new PaidClinic();
        paidClinic.setAddress("testAddress");
        paidClinic.setDescription("testDescription");
        paidClinic.setOwner("testOwner");
        healthCareInstitutionService.addNew(paidClinic);

        List<HealthCareInstitution> institutionList = healthCareInstitutionService.getAll();

        PublicClinic publicClinic = new PublicClinic();
        publicClinic.setDescription("testDescription");
        publicClinic.setAddress("testAddress");
        publicClinic.setState("Belarus");
        healthCareInstitutionService.addNew(publicClinic);

        assertFalse(institutionList.contains(publicClinic));
        institutionList = healthCareInstitutionService.getAll();
        assertTrue(institutionList.contains(publicClinic));
    }

    @Test
    void deleteOneById_shouldThrowNoResultException() {
        PublicClinic publicClinic = new PublicClinic();
        publicClinic.setDescription("testDescription");
        publicClinic.setAddress("testAddress");
        publicClinic.setState("Belarus");
        healthCareInstitutionService.addNew(publicClinic);

        healthCareInstitutionService.deleteOneById(publicClinic.getId());
        assertThrows(NoResultException.class, () -> healthCareInstitutionService.getOneByIndex(publicClinic.getId()));

    }

    @Test
    void getAllPaidClinic_returnAllPaidClinics() {
        List<PaidClinic> paidClinicList = healthCareInstitutionService.getAllPaidClinic();
        int count = paidClinicList.size();

        PublicClinic publicClinic = new PublicClinic();
        publicClinic.setDescription("testDescription");
        publicClinic.setAddress("testAddress");
        publicClinic.setState("Belarus");
        healthCareInstitutionService.addNew(publicClinic);
        PaidClinic paidClinic = new PaidClinic();
        paidClinic.setAddress("testAddress");
        paidClinic.setDescription("testDescription");
        paidClinic.setOwner("testOwner");
        healthCareInstitutionService.addNew(paidClinic);

        paidClinicList = healthCareInstitutionService.getAllPaidClinic();
        assertEquals(++count, paidClinicList.size());
    }

    @Test
    void getAllPublicClinic_returnAllPublicClinics() {
        List<PublicClinic> publicClinicList = healthCareInstitutionService.getAllPublicClinic();
        int count = publicClinicList.size();

        PublicClinic publicClinic = new PublicClinic();
        publicClinic.setDescription("testDescription");
        publicClinic.setAddress("testAddress");
        publicClinic.setState("Belarus");
        healthCareInstitutionService.addNew(publicClinic);
        PaidClinic paidClinic = new PaidClinic();
        paidClinic.setAddress("testAddress");
        paidClinic.setDescription("testDescription");
        paidClinic.setOwner("testOwner");
        healthCareInstitutionService.addNew(paidClinic);

        publicClinicList = healthCareInstitutionService.getAllPublicClinic();
        assertEquals(++count, publicClinicList.size());
    }

    @Test
    void getOneByIndexWithDoctor_returnInstitutionWithListOfDoctors() {
        PublicClinic publicClinic = new PublicClinic();
        publicClinic.setDescription("testDescription");
        publicClinic.setAddress("testAddress");
        publicClinic.setState("Belarus");
        healthCareInstitutionService.addNew(publicClinic);

        Doctor doctor = new Doctor();
        doctor.setPhoneNumber("testPhone");
        doctor.setDescription("testDescription");
        doctor.setSalary(1000);
        doctorService.addNew(doctor);

        doctorService.changeRelationInstitution(doctor.getId(), publicClinic.getId());
        HealthCareInstitution institution = healthCareInstitutionService.getOneByIndexWithDoctor(publicClinic.getId());
        assertTrue(institution.getDoctorList().contains(doctor));
    }
}