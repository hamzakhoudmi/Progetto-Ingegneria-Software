package dominio;

public interface Iteratore<T> {

    T currentItem();
    void next();
    boolean hasNext();
    T remove();

}//Iteratore
