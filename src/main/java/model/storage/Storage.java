package model.storage;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Storage <T> {
    T save (T entity);
    Optional<T> findById (long id);
    Optional<T>  findByName (String name);
    Set<T> findAll();
    boolean isExist(long id);
    boolean isExist(String name);
    T update (T entity);
    void delete(T entity);

}
