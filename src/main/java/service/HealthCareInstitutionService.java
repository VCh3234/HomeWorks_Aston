package service;

import lombok.extern.log4j.Log4j2;
import model.HealthCareInstitution;
import repository.HealthCareInstitutionRepository;

import java.sql.SQLException;
import java.util.List;

@Log4j2
public class HealthCareInstitutionService implements CrudService<HealthCareInstitution> {

    private HealthCareInstitutionRepository healthCareInstitutionRepository;

    public HealthCareInstitutionService() {
        healthCareInstitutionRepository = new HealthCareInstitutionRepository();
    }

    @Override
    public void addNew(HealthCareInstitution healthCareInstitution) throws SQLException {
        log.info("Save new institution");
        healthCareInstitutionRepository.saveNew(healthCareInstitution);
    }

    @Override
    public void update(HealthCareInstitution healthCareInstitution) throws SQLException {
        log.info("Update institution");
        healthCareInstitutionRepository.update(healthCareInstitution);
    }

    @Override
    public HealthCareInstitution getOneByIndex(int i) throws SQLException {
        log.info("Get one institution");
        return healthCareInstitutionRepository.getOneByIndex(i);
    }

    @Override
    public List<HealthCareInstitution> getAll() throws SQLException {
        log.info("Get all institution");
        return healthCareInstitutionRepository.getAll();
    }

    @Override
    public void deleteOneById(int i) throws SQLException {
        log.info("Delete institution");
        healthCareInstitutionRepository.deleteOneById(i);
    }
}