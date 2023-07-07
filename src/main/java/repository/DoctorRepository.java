package repository;

import configuration.EntityManagerBuilder;
import lombok.extern.log4j.Log4j2;
import model.Doctor;
import model.HealthCareInstitution;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Class for access to DB for operations with doctor entity
 */
@Log4j2
public class DoctorRepository implements Repository<Doctor> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveNew(Doctor doctor) {
        log.info("Save doctor in DB");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(doctor);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(Doctor doctor) {
        log.info("Update doctor in DB");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Doctor doctorFromDb = entityManager.find(Doctor.class, doctor.getId());
            doctorFromDb.setDescription(doctor.getDescription());
            doctorFromDb.setSalary(doctor.getSalary());
            doctorFromDb.setPhoneNumber(doctor.getPhoneNumber());
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Doctor getOneByIndex(int id) {
        log.info("Get doctor from DB");
        Doctor doctor;
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            doctor = (Doctor) entityManager
                    .createQuery("from Doctor d left join fetch d.institution where d.id=:id")
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
        doctor.setPatientList(null);
        if (doctor.getInstitution() != null) {
            doctor.getInstitution().setDoctorList(null);
        }
        return doctor;
    }

    @Override
    public List<Doctor> getAll() {
        log.info("Get all doctors from DB");
        List<Doctor> doctorList;
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            /*
            ************************** N+1******************
            doctorList = entityManager.createQuery("from Doctor").getResultList();
            doctorList.forEach(doctor -> {
                if (doctor.getInstitution() != null) {
                    doctor.getInstitution().setDoctorList(null);
                }
            });
            */
            doctorList = entityManager.createQuery("from Doctor d left join fetch d.institution").getResultList();
        } finally {
            entityManager.close();
        }
//        ********************* Если убрать этот код то будет LazyInitialization ****************************
        doctorList.forEach(doctor -> doctor.setPatientList(null));
        doctorList.forEach(doctor -> {
            if (doctor.getInstitution() != null) {
                doctor.getInstitution().setDoctorList(null);
            }
        });
//        **************************************************************************************************
        return doctorList;
    }

    @Override
    public void deleteOneById(int i) {
        log.info("Delete doctor from DB");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("delete from Doctor d where d.id=:i");
            query.setParameter("i", i);
            query.executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Gives doctor entity with its patients from DB
     *
     * @param id id of the doctor entity
     */
    public Doctor getOneByIndexWithPatients(int id) {
        log.info("Get one doctor with patients");
        Doctor doctor;
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            doctor = (Doctor) entityManager
                    .createQuery("select d from Doctor d left join fetch d.patientList left join fetch d.institution where d.id=:id")
                    .setParameter("id", id)
                    .getSingleResult();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        doctor.getPatientList().forEach(patient -> patient.setDoctorList(null));
        if (doctor.getInstitution() != null) {
            doctor.getInstitution().setDoctorList(null);
        }
        return doctor;
    }

    /**
     * Ads or change relation between doctor and institution in DB
     *
     * @param doctorId      id of the doctor entity
     * @param institutionId id of the institution entity
     */
    public void changeRelation(int doctorId, int institutionId) {
        log.info("Change doctor-institution relation");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Doctor doctor = entityManager.getReference(Doctor.class, doctorId);
            HealthCareInstitution healthCareInstitution = entityManager.getReference(HealthCareInstitution.class, institutionId);
            doctor.setInstitution(healthCareInstitution);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Deletes relation between doctor and institution in DB
     *
     * @param doctorId id of the doctor entity
     */
    public void deleteRelation(int doctorId) {
        log.info("Delete doctor-institution relation");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager
                    .createQuery("update Doctor d set d.institution=:institution where d.id=:id")
                    .setParameter("institution", null)
                    .setParameter("id", doctorId)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}