package service;

import lombok.extern.log4j.Log4j2;
import model.HealthCareInstitution;
import model.PaidClinic;
import model.PublicClinic;
import repository.HealthCareInstitutionRepository;

import java.util.List;

@Log4j2
public class HealthCareInstitutionService implements CrudService<HealthCareInstitution> {

    private final HealthCareInstitutionRepository healthCareInstitutionRepository;

    public HealthCareInstitutionService() {
        healthCareInstitutionRepository = new HealthCareInstitutionRepository();
    }

    @Override
    public void addNew(HealthCareInstitution healthCareInstitution) {
        log.info("Save new institution");
        healthCareInstitutionRepository.saveNew(healthCareInstitution);
    }

    @Override
    public void update(HealthCareInstitution healthCareInstitution) {
        log.info("Update institution");
        healthCareInstitutionRepository.update(healthCareInstitution);
    }

    @Override
    public HealthCareInstitution getOneByIndex(int i) {
        log.info("Get one institution");
        return healthCareInstitutionRepository.getOneByIndex(i);
    }

    @Override
    public List<HealthCareInstitution> getAll() {
        log.info("Get all institution");
        return healthCareInstitutionRepository.getAll();
    }

    @Override
    public void deleteOneById(int i) {
        log.info("Delete institution");
        healthCareInstitutionRepository.deleteOneById(i);
    }

    /**
     * Gives all paid clinics from repository
     */
    public List<PaidClinic> getAllPaidClinic() {
        log.info("Get all paid clinics");
        return healthCareInstitutionRepository.getAllPaidClinic();
    }

    /**
     * Gives all public clinics from repository
     */
    public List<PublicClinic> getAllPublicClinic() {
        log.info("Get all public clinics");
        return healthCareInstitutionRepository.getAllPublicClinic();
    }

    /**
     * Gives common clinic from repository with its doctors
     *
     * @param id id of the clinic
     */
    public HealthCareInstitution getOneByIndexWithDoctor(int id) {
        log.info("Get institution with doctors");
        return healthCareInstitutionRepository.getOneByIndexWithDoctors(id);
    }
}