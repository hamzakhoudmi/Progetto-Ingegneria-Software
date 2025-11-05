package applicazione.strategy.sort;
import dominio.Film;

public class SortByVoto implements SortStrategy {

    public int compare( Film f1, Film f2 ) {
        return Integer.compare( f1.getVoto(), f2.getVoto() );
    }

}//SortByVoto
