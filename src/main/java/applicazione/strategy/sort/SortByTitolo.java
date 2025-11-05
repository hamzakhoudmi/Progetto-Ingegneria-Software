package applicazione.strategy.sort;
import dominio.Film;

public class SortByTitolo implements SortStrategy {

    public int compare( Film f1, Film f2 ) {
        if( f1==null || f2==null ) return 0;
        String t1 = f1.getTitolo(), t2 = f2.getTitolo();
        if( t1==null && t2==null ) return 0;
        if( t1==null ) return -1;
        if( t2==null ) return 1;
        return t1.compareToIgnoreCase( t2 );
    }

}//SortByTitolo
