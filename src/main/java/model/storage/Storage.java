package model.storage;

import java.util.List;
import java.util.Optional;

public interface Storage <T> {
    T save (T entity);
    Optional<T> findById (long id);
    Optional<T>  findByName (String name);
    List<Optional<T>> findAll();
    boolean isExist(long id);
    boolean isExist(String name);
    T update (T entity);
    void delete(T entity);

}
