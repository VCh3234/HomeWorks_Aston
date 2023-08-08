package service;

/**
 * Provides creation, updating and deleting relationship between entity and institution
 */
public interface InstitutionOwner {
    /**
     * Adds or change relation between entity and institution in repository
     *
     * @param ownerId       id of the entity
     * @param institutionId id of the institution entity
     */
    void changeRelationInstitution(int ownerId, int institutionId);

    /**
     * Deletes relation between entity and institution in repository
     *
     * @param ownerId id of the entity
     */
    void deleteRelationWithInstitution(int ownerId);
}