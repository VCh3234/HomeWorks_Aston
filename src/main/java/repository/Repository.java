package repository;

import java.util.List;

/**
 * Provides methods for interaction with DB
 */
public interface Repository<T> {

    /**
     * Creates new record into database
     *
     * @param t the object for saving
     */
    void saveNew(T t);

    /**
     * Saves updated data about object into DB
     *
     * @param t the object for updating
     */
    void update(T t);

    /**
     * Gets the instance of required entity
     *
     * @param i the id of entity in DB
     * @return the instance of entity
     */
    T getOneByIndex(int i);

    /**
     * Gets all instances of required entity
     *
     * @return all instances of entity
     */
    List<T> getAll();

    /**
     * Deletes the record about object in DB
     *
     * @param i the id of entity in DB
     */
    void deleteOneById(int i);
}