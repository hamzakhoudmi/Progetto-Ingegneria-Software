package applicazione.strategy.filter;
import dominio.Film;

public interface FilterStrategy {

    boolean include( Film f );

}//FilterStrategy
