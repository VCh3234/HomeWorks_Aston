package service;

/**
 * Provides creation, updating and deleting relationship between entity (T) and patient
 */
public interface PatientOwner<T> {

    /**
     * Gives T object with its doctors from repository
     *
     * @param ownerId id of the T entity
     */
    T getOneByIndexWithPatient(int ownerId);

    /**
     * Ads the relation between T-doctor from repository
     *
     * @param ownerId   id of the doctor entity
     * @param patientId id of the T entity
     */
    void addPatient(int patientId, int ownerId);

    /**
     * Deletes the relation between patient-T from repository
     *
     * @param ownerId   id of the patient entity
     * @param patientId id of the T entity
     */
    void deletePatient(int patientId, int ownerId);
}