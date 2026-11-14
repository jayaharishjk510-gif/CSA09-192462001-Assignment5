import java.util.List;
import java.util.Optional;

/**
 * Task A3.2: Generic Data Access Object (DAO) Interface.
 * Encapsulates standard CRUD operations for any domain entity type.
 *
 * @param <T> Domain entity type (e.g., Student, Course, Marks)
 * @param <K> Primary key type (e.g., String, Integer, Long)
 */
public interface Dao<T, K> {

    /**
     * Persists a new entity into the underlying database.
     * @param entity the entity to be inserted
     * @return true if insertion succeeded, false otherwise
     */
    boolean insert(T entity);

    /**
     * Retrieves an entity by its unique primary key.
     * @param id the primary key value
     * @return an Optional containing the entity if found, or empty Optional
     */
    Optional<T> findById(K id);

    /**
     * Retrieves all entities belonging to this domain table.
     * @return List of all domain entities
     */
    List<T> findAll();

    /**
     * Updates an existing entity's attributes.
     * @param entity the updated entity containing primary key
     * @return true if row was successfully updated, false otherwise
     */
    boolean update(T entity);

    /**
     * Deletes an entity matching the given primary key.
     * @param id primary key of record to remove
     * @return true if deletion succeeded, false otherwise
     */
    boolean delete(K id);
}
