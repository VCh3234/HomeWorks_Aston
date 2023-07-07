package service;

/**
 * Provides creation, updating and deleting relationship between entity (T) and institution
 */
public interface DoctorOwner<T> {

    /**
     * Gives T object with its doctors from repository
     *
     * @param ownerId id of the T entity
     */
    T getOneByIndexWithDoctor(int ownerId);

    /**
     * Ads the relation between T-doctor from repository
     *
     * @param doctorId id of the doctor entity
     * @param ownerId  id of the T entity
     */
    void addDoctor(int ownerId, int doctorId);

    /**
     * Deletes the relation between doctor-T from repository
     *
     * @param doctorId id of the doctor entity
     * @param ownerId  id of the T entity
     */
    void deleteDoctor(int ownerId, int doctorId);
}