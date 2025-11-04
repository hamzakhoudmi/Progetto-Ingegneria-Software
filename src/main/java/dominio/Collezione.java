package dominio;
import java.util.*;

interface Collezione {

    List<Film> getFilms();

    void add( Film f );
    Film remove( Film f );
    //void setStrategy( Strategy s );
    //ricerca();
    //filter();
    //sort();
    //salva();

    Iteratore<Film> creaIteratore();

}//Collezione
