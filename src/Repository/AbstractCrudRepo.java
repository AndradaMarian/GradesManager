package Repository;


import Domain.HasID;

import java.util.HashMap;
import java.util.Map;

/**
 * @param <ID>
 * - an unique ID to identify the E type
 * @param <E>
 * - a type of Objects
 */
public abstract class AbstractCrudRepo<ID,E extends HasID<ID>> implements CrudRepository<ID,E> {
    Map<ID,E> list;

    /**
     */
    public AbstractCrudRepo() {
        this.list=new HashMap<>();
    }

    /**
     * @param id the id of the entity to be returned
     * @return an entity of type E
     */
    @Override
    public E findOne(ID id) {
       return list.get(id);
    }

    /**
     * @return an entity of type E
     */
    @Override
    public Iterable<E> findAll() {
        return list.values();
    }

    /**
     * @param entity an entity of type E
     * @return an entity of type E
     */
    @Override
    public E save(E entity) throws Exception {

        return this.list.putIfAbsent(entity.getID(),entity);
    }

    /**
     * @param id -an ID to indentify the E type
     * @return
     * -null if the entity already in list
     */
@Override
    public E delete(ID id) {

        return list.remove(id);
    }

    /**
     * @param entity - an entity to update the old one with the same ID
     * @return the entity
     */
@Override
    public E update(E entity) {

        list.replace(entity.getID(),entity);
        return entity;
    }
    public int size(){
    return list.size();
    }

}
