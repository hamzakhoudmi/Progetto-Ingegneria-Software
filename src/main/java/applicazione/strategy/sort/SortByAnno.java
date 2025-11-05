package applicazione.strategy.sort;
import dominio.Film;

public class SortByAnno implements SortStrategy {

    public int compare( Film f1, Film f2 ) {
        return Integer.compare( f1.getAnno(), f2.getAnno() );
    }

}//sortbyanno
