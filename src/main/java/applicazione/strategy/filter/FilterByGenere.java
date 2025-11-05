package applicazione.strategy.filter;
import dominio.Film;

public class FilterByGenere implements FilterStrategy {
    private String genere;

    public FilterByGenere( String genere ) {
        this.genere = genere;
    }//costruttore

    public boolean include( Film f ) {
        return genere.equals( f.getGenere() );
    }

}//FilterByGenere
