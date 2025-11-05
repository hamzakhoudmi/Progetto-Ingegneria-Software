package applicazione.strategy.ricerca;
import dominio.Film;

public class RicercaByRegista implements RicercaStrategy {
    private String regista;

    public RicercaByRegista( String regista ) {
        this.regista = regista;
    }//costruttore

    public boolean accept( Film f ) {
        return regista.equalsIgnoreCase( f.getRegista() );
    }//accept

}//RicercaByRegista
