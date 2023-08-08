package repository;

import configuration.EntityManagerBuilder;
import lombok.extern.log4j.Log4j2;
import model.HealthCareInstitution;
import model.PaidClinic;
import model.PublicClinic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Log4j2
public class HealthCareInstitutionRepository implements Repository<HealthCareInstitution> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveNew(HealthCareInstitution healthCareInstitution) {
        log.info("Save clinic in DB");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(healthCareInstitution);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(HealthCareInstitution healthCareInstitution) {
        log.info("Update clinic in DB");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            HealthCareInstitution healthCareInstitutionDb = entityManager.find(HealthCareInstitution.class, healthCareInstitution.getId());
            healthCareInstitutionDb.setDescription(healthCareInstitution.getDescription());
            healthCareInstitutionDb.setAddress(healthCareInstitution.getAddress());
            if (healthCareInstitution instanceof PaidClinic p) {
                ((PaidClinic) healthCareInstitutionDb).setOwner(p.getOwner());
            } else if (healthCareInstitution instanceof PublicClinic p) {
                ((PublicClinic) healthCareInstitutionDb).setState(p.getState());
            }
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public HealthCareInstitution getOneByIndex(int id) {
        log.info("Get clinic from DB");
        HealthCareInstitution healthCareInstitution;
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            healthCareInstitution = (HealthCareInstitution) entityManager
                    .createQuery("from HealthCareInstitution h where h.id=:id")
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
        healthCareInstitution.setDoctorList(null);
        return healthCareInstitution;
    }

    @Override
    public List<HealthCareInstitution> getAll() {
        log.info("Get all institutions");
        entityManager = EntityManagerBuilder.getEntityManager();
        List<HealthCareInstitution> healthCareInstitutions;
        try {
            healthCareInstitutions = entityManager
                    .createQuery("from HealthCareInstitution")
                    .getResultList();
        } finally {
            entityManager.close();
        }
        healthCareInstitutions.forEach(healthCareInstitution -> healthCareInstitution.setDoctorList(null));
        return healthCareInstitutions;
    }

    @Override
    public void deleteOneById(int id) {
        log.info("Delete clinic from DB");
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("delete from HealthCareInstitution h where h.id=:id");
            query.setParameter("id", id);
            query.executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    /**
     * Gives all paid clinics from DB
     */
    public List<PaidClinic> getAllPaidClinic() {
        log.info("Get all paid institutions");
        entityManager = EntityManagerBuilder.getEntityManager();
        List<PaidClinic> paidClinics;
        try {
            paidClinics = entityManager
                    .createQuery("from PaidClinic")
                    .getResultList();
        } finally {
            entityManager.close();
        }
        paidClinics.forEach(healthCareInstitution -> healthCareInstitution.setDoctorList(null));
        return paidClinics;
    }

    /**
     * Gives all paid clinics from DB
     */
    public List<PublicClinic> getAllPublicClinic() {
        log.info("Get all public institutions");
        entityManager = EntityManagerBuilder.getEntityManager();
        List<PublicClinic> publicClinics;
        try {
            publicClinics = entityManager
                    .createQuery("from PublicClinic")
                    .getResultList();
        } finally {
            entityManager.close();
        }
        publicClinics.forEach(healthCareInstitution -> healthCareInstitution.setDoctorList(null));
        return publicClinics;
    }

    /**
     * Gives all paid clinics from DB with its doctors
     *
     * @param id id of the clinic
     */
    public HealthCareInstitution getOneByIndexWithDoctors(int id) {
        log.info("Get one clinic with doctors");
        HealthCareInstitution institution;
        entityManager = EntityManagerBuilder.getEntityManager();
        try {
            institution = (HealthCareInstitution) entityManager
                    .createQuery("from HealthCareInstitution h left join fetch h.doctorList where h.id=:id")
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
        institution.getDoctorList().forEach(doctor -> {
            doctor.setPatientList(null);
            doctor.setInstitution(null);
        });
        return institution;
    }
}