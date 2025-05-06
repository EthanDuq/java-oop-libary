package models;
import java.util.List;

public interface LibraryService<T>{
    void addItem(T item);
    void removeItem(T item);
    T getItem(int id);
    List<T> getItems();
}
