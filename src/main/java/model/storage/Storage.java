package model.storage;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Storage <T> {
    T save (T entity);
    Optional<T>  findByName (String name);
    Set<T> findAll();
    T update (T entity);
    List<String>  delete(T entity);

}
