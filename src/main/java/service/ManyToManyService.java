package service;

import model.Doctor;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface provides create and delete operations with many-to-many
 * relations between entities.
 */
public interface ManyToManyService {

    /**
     * Add relation in data storage
     *
     * @param i the first id
     * @param j the second id
     */
    void addRelation(int i, int j) throws SQLException;
    /**
     * Delete relation in data storage
     *
     * @param i the first id
     * @param j the second id
     */
    void deleteRelation(int i, int j) throws SQLException;
    /**
     * Get all relations from DB
     *
     * @param id id of entity which we get relations of
     */
    List<Doctor> getAllById(int id) throws SQLException;
}