package additional;

import configuration.EntityManagerBuilder;
import lombok.extern.log4j.Log4j2;
import model.Patient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.PatientRepository;
import service.PatientService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Log4j2
public class CacheTest {

    @PersistenceContext
    EntityManager entityManager;

    private static int id;

    @BeforeAll
    static void init() {
        Patient patient = new Patient();
        patient.setPhoneNumber("qwe");
        patient.setAddress("qwe");
        patient.setInsuranceNumber(123);
        new PatientService().addNew(patient);
        id = patient.getId();
    }

    @Test
    void secondLevelCacheTest() throws InterruptedException {
        log.info("Wait 31 secs");
        Thread.sleep(31_000);

        entityManager = EntityManagerBuilder.getEntityManager();

        log.info("Get patient for the first time");
        Patient patient = entityManager.find(Patient.class, id);
        patient.setDoctorList(null);
        log.info(patient);
        patient = null;
        entityManager.close();

        entityManager = EntityManagerBuilder.getEntityManager();
        log.info("Get patient for the second time");
        patient = entityManager.find(Patient.class, id);
        patient.setDoctorList(null);
        log.info(patient);
        patient = null;
        entityManager.close();

        log.info("Wait 31 secs");
        Thread.sleep(31_000);

        log.info("Get patient for the third time");
        entityManager = EntityManagerBuilder.getEntityManager();
        patient = entityManager.find(Patient.class, id);
        patient.setDoctorList(null);
        log.info(patient);
        entityManager.close();
    }

    @Test
    void secondLevelCacheTest_forQuery() {
        log.info("First request");
        List<Patient> list = new PatientRepository().getAllPatientsByInsuranceNumberMore(100);
        log.info("Second request");
        list = new PatientRepository().getAllPatientsByInsuranceNumberMore(100);
    }
}