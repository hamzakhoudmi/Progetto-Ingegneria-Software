package applicazione.strategy.ricerca;
import dominio.Film;

public interface RicercaStrategy {

    boolean accept( Film f );

}
