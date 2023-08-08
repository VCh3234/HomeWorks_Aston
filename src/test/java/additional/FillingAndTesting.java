package additional;

import configuration.EntityManagerBuilder;
import lombok.extern.log4j.Log4j2;
import model.Patient;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import repository.PatientRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// У меня получилось одинаковое время с индексом и без, думаю,
// возможно это связано с тем, что на машине стоит ssd, и время запроса больше зависит от ЦП
@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FillingAndTesting {

    @PersistenceContext
    private EntityManager entityManager;

    private PatientRepository patientService = new PatientRepository();

    @Test
    @Order(1)
    void addRows() {
        entityManager = EntityManagerBuilder.getEntityManager();
        entityManager.getTransaction().begin();
        for (int i = 0; i < 1_000_000; i++) {
            Patient patient = new Patient();
            patient.setPhoneNumber("test");
            patient.setAddress("test");
            patient.setInsuranceNumber((int) (Math.random() * 1_000_000));
            entityManager.persist(patient);
        }
        entityManager.getTransaction().commit();
    }

    @Test
    @Order(2)
    void getWithoutIndex() {
        List<Patient> patientList = patientService.getAllPatientsByInsuranceNumberMore(400_000);

        long start = System.currentTimeMillis();
        List<Patient> patientList2 = patientService.getAllPatientsByInsuranceNumberMore(400_000);
        long end = System.currentTimeMillis();
        log.info("Size of list: " + patientList2.size());
        log.info("Duration: " + (end - start) + " millis");
    }

    @Test
    @Order(3)
    void createIndex() {
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("create index patient_insurance_number_index" +
                            " on public.patient (p_insurance_number)")
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Test
    @Order(4)
    void getWithIndex() {
        List<Patient> patientList = patientService.getAllPatientsByInsuranceNumberMore(400_000);

        long start = System.currentTimeMillis();
        patientList = patientService.getAllPatientsByInsuranceNumberMore(400_000);
        long end = System.currentTimeMillis();
        log.info("Size of list: " + patientList.size());
        log.info("Duration with index: " + (end - start) + " millis");
        patientList = patientService.getAllPatientsByInsuranceNumberMore(400_000);
    }
}