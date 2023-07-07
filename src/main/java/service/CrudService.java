package service;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface providing CRUD operations with entities.
 * T - the type of the object
 */
public interface CrudService<T> {

    /**
     * Creates new record into database
     *
     * @param t the object for saving
     */
    void addNew(T t) throws SQLException;

    /**
     * Saves updated data about object into database
     *
     * @param t an object for updating
     */
    void update(T t) throws SQLException;

    /**
     * Gets the instance of required entity
     *
     * @param i the id of entity in DB
     * @return the instance of entity
     */
    T getOneByIndex(int i) throws SQLException;

    /**
     * Gets all instances of required entity
     *
     * @return all instances of entity
     */
    List<T> getAll() throws SQLException;

    /**
     * Deletes the record about object in DB
     *
     * @param i the id of entity in DB
     */
    void deleteOneById(int i) throws SQLException;
}