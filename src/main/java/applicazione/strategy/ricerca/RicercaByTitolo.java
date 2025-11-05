package applicazione.strategy.ricerca;
import dominio.Film;

public class RicercaByTitolo implements RicercaStrategy {
    private String titolo;

    public RicercaByTitolo( String titolo ) {
        this.titolo = titolo;
    }//costruttore

    public boolean accept( Film f ) {
        return titolo.equalsIgnoreCase( f.getTitolo() );
    }//accept

}//RicercaByTitolo
