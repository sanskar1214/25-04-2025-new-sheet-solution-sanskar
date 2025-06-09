package repository;

import java.util.*;

public class Repository<T> {
    private Map<String, T> items = new HashMap<>();

    public void add(String id, T item) {
        items.put(id, item);
    }

    public T get(String id) {
        return items.get(id);
    }

    public Collection<T> getAll() {
        return items.values();
    }

    public void remove(String id) {
        items.remove(id);
    }
}
