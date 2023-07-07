package repository;

import configuration.EntityManagerBuilder;
import lombok.extern.log4j.Log4j2;
import model.Patient;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Class for access to DB for operations with patient entity
 */

@Log4j2
public class PatientRepository implements Repository<Patient> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveNew(Patient patient) {
        log.info("Save new patient");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(patient);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(Patient patient) {
        log.info("Update patient");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Patient patientForUpdate = entityManager.find(Patient.class, patient.getId());
            patientForUpdate.setAddress(patient.getAddress());
            patientForUpdate.setPhoneNumber(patient.getPhoneNumber());
            patientForUpdate.setInsuranceNumber(patient.getInsuranceNumber());
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Patient getOneByIndex(int id) {
        log.info("Get patient");
        entityManager = EntityManagerBuilder.getEntityManager();
        Patient patient;
        try {
            entityManager.getTransaction().begin();
            patient = entityManager.find(Patient.class, id);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        patient.setDoctorList(null);
        return patient;
    }

    @Override
    public List<Patient> getAll() {
        log.info("Get all patients");
        entityManager = EntityManagerBuilder.getEntityManager();
        List<Patient> patientList;
        try {
            entityManager.getTransaction().begin();
            patientList = entityManager
                    .createQuery("from Patient")
                    .getResultList();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        patientList.forEach(patient -> patient.setDoctorList(null));
        return patientList;
    }

    @Override
    public void deleteOneById(int id) {
        log.info("Delete one patient");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager
                    .createQuery("delete from Patient p where p.id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Gives patient with doctorList from DB
     *
     * @param id id of the patient object
     */
    public Patient getOneByIndexWithDoctor(int id) {
        log.info("Get one patient with doctors");
        Patient patient;
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            patient = (Patient) entityManager
                    .createQuery("select p from Patient p left join fetch p.doctorList where p.id=:id")
                    .setParameter("id", id)
                    .getSingleResult();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        patient.getDoctorList().forEach(doctor -> doctor.setPatientList(null));
        return patient;
    }

    /**
     * Ads the relation between patient-doctor from DB
     *
     * @param doctorId  id of the doctor entity
     * @param patientId id of the patient entity
     */
    public void addDoctorForPatient(int patientId, int doctorId) {
        log.info("Add one doctor to patient");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();

//             ****** Делает два дополнительных запроса в бд *******

//            Patient patient = entityManager.getReference(Patient.class, patientId);
//            Doctor doctor = entityManager.getReference(Doctor.class, doctorId);
//
//            doctor.getPatientList().add(patient);

//            Один запрос в бд
            entityManager
                    .createNativeQuery("insert into doctor_patient (d_id, p_id) values (?,?)")
                    .setParameter(1, doctorId)
                    .setParameter(2, patientId)
                    .executeUpdate();

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Deletes the relation between patient-doctor from repository
     *
     * @param doctorId  id of the doctor entity
     * @param patientId id of the patient entity
     */
    public void deleteDoctorForPatient(int patientId, int doctorId) {
        log.info("Delete relation patient-doctor");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();

            entityManager
                    .createNativeQuery("delete from doctor_patient where d_id = ? and p_id = ?")
                    .setParameter(1, doctorId)
                    .setParameter(2, patientId)
                    .executeUpdate();

            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Return a list of patients which have insurance number more than given number
     *
     * @param insuranceNumber number for filtering the request
     */
    public List<Patient> getAllPatientsByInsuranceNumberMore(int insuranceNumber) {
        log.info("Get all patients by insurance number");
        List<Patient> patientList;
        Session session = EntityManagerBuilder.getSessionFactory();
        try {
            session.getTransaction().begin();
            Query<Patient> query = session.createQuery("from Patient");
            query.setCacheable(true);
            patientList = query.getResultList();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        patientList.forEach(patient -> patient.setDoctorList(null));
        return patientList;
    }
}