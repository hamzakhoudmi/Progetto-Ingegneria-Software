package applicazione.strategy.filter;
import dominio.Film;
import dominio.Film.Stato;

public class FilterByStato implements FilterStrategy {
    private Stato stato;

    public FilterByStato( Stato stato ) {
        this.stato = stato;
    }//costruttore

    public boolean include( Film f ) {
        return stato == f.getStato();
    }//include

}//FilterByStato
