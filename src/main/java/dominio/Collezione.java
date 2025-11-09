package dominio;
import java.util.*;
import applicazione.strategy.sort.*;
import applicazione.strategy.ricerca.*;
import applicazione.strategy.filter.*;

public interface Collezione {

    void add( Film f );
    Film remove( Film f );
    int size();
    boolean isEmpty();
    void clear();

    void setSortStrategy( SortStrategy s );
    void setRicercaStrategy( RicercaStrategy s );
    void setFilterStrategy( FilterStrategy s );

    void sort();
    List<Film> filtra();
    void annullaFiltri();
    List<Film> search();

    void salva();

    Iteratore<Film> creaIteratore();

}//Collezione
