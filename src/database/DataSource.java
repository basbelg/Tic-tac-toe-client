package database;

import java.util.List;

public interface DataSource {
    boolean insert(Object obj);
    boolean delete(Object obj);
    boolean update(Object obj);
    Object get(String s);
    List<Object> list(Object obj);
    List<Object> query(Object obj, String filter);
}
