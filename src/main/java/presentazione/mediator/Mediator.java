package presentazione.mediator;
import java.util.*;
import dominio.*;

public interface Mediator {
    void search(String tipo, String s);
    void filtra(String tipo, String s);
    void sort(String tipo);
    void aggiungi();
    void rimuovi();
    void modifica();
    void refreshList(List<Film> lista);
    void refreshList();
}
