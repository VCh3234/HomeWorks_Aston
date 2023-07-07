package repository;

import model.Doctor;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface provides create and delete operations with one-to-many
 * relations between entities.
 */
public interface OneToManyRepository {

    /**
     * Add relation in DB
     *
     * @param i the first id
     * @param j the second id
     */
    void saveNewRelationship(int i, int j) throws SQLException;
    /**
     * Delete relation in DB
     *
     * @param i the first id
     */
    void deleteRelation(int i) throws SQLException;
    /**
     * Get all relations from DB
     *
     * @param id the id for search
     */
    List<Doctor> getAllById(int id) throws SQLException;
}