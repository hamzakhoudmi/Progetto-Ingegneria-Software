package dominio;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import applicazione.strategy.sort.*;
import applicazione.strategy.filter.*;
import applicazione.strategy.ricerca.*;


public class TestingStrategies {
    private CollezioneSingleton catalogo;
    private Film f1, f2, f3;

    @BeforeEach
    void setUp() {
        catalogo = CollezioneSingleton.CATALOGO;
        catalogo.clear();

        f1 = new Film.ConcreteBuilder( "Hunger Games", "Ross" ).anno( 2012 )
                .voto( 10 ).stato( Film.Stato.DA_VEDERE ).genere( "Fantascenza" )
                .build();
        f2 = new Film.ConcreteBuilder( "Goodfellas", "Scorsese" ).voto( 7 )
                .genere( "Gangster" ).anno( 1990 ).stato( Film.Stato.IN_VISIONE )
                .build();
        f3 = new Film.ConcreteBuilder( "Le iene", "Tarantino" ).genere( "Gangster" )
                .voto( 8 ).anno( 1992 ).stato( Film.Stato.VISTO )
                .build();

        catalogo.add( f1 );
        catalogo.add( f2 );
        catalogo.add( f3 );
    }//setUp

    @Test
    void testSortByAnno() {
        catalogo.setSortStrategy( new SortByAnno() );
        catalogo.sort();

        Iteratore<Film> it = catalogo.creaIteratore();
        if( it.hasNext() ) {
            it.next();
            assertEquals( 1990, it.currentItem().getAnno() );
        } else {
            System.out.println( "problema con l'iteratore." );
        }
        if( it.hasNext() ) it.next();
        if( it.hasNext() ) {
            it.next();
            assertEquals( 2012, it.currentItem().getAnno(), "Problema con ordinamentoAnno oppure con iteratore." );
        }
    }//sortByAnno

    @Test
    void testSortByVoto() {
        catalogo.setSortStrategy( new SortByVoto() );
        catalogo.sort();

        Iteratore<Film> it = catalogo.creaIteratore();
        assertTrue( it.hasNext(), "l'iteratore non legge il primo elemento." );
        it.next();
        assertEquals( 7, it.currentItem().getVoto(), "Problema con sort by voto." );
    }//testSortByVoto

    @Test
    void testFilters() {
        catalogo.setFilterStrategy( new FilterByGenere( "Gangster" ) );
        List<Film> filtrati = catalogo.filtra();
        assertEquals( 2, filtrati.size(), "filtro di genere fallito." );

        //ricerca applicando il filtro
        catalogo.setRicercaStrategy( new RicercaByTitolo( "Hunger Games" ) );
        List<Film> cercati = catalogo.search();
        assertEquals( 0, cercati.size(), "la ricerca+filter è stata invece fatta senza filter" );

        catalogo.setFilterStrategy( new FilterByStato( Film.Stato.VISTO ) );
        filtrati = catalogo.filtra();
        assertEquals( 1, filtrati.size(), "doppio filtraggio fallito." );

    }//testfilters

    @Test
    void testRicercaByTitolo() {
        catalogo.setRicercaStrategy( new RicercaByTitolo( "Hunger Games" ) );
        List<Film> cercati = catalogo.search();
        assertEquals( 1, cercati.size(), "ricerca per titolo fallita." );
    }//testRicercaByTirolo

    @Test
    void testRicercaByRegista() {
        catalogo.setRicercaStrategy( new RicercaByRegista( "Tarantino" ) );
        List<Film> cercati = catalogo.search();
        assertEquals( "Le iene", cercati.get( 0 ).getTitolo(), "ricerca per regista fallita." );
    }//testRicercaByRegista

    @Test
    void testSortNaturale() {
        //ordinamento naturale scelto: per titolo e se stesso titolo allora per regista
        catalogo.setRicercaStrategy( null );
        catalogo.setFilterStrategy( null );
        catalogo.setSortStrategy( null );

        catalogo.add( new Film.ConcreteBuilder( "Le iene", "ItaliaUno").build() ); //adesso ci sono due film con stesso titolo
        catalogo.sort();

        Iteratore<Film> it = catalogo.creaIteratore();
        assertTrue( it.hasNext(), "l'iteratore non legge il primo elemento." );
        it.next();
        assertTrue( it.hasNext(), "l'iteratore non legge il secondo elemento." );
        it.next();
        assertTrue( it.hasNext(), "l'iteratore non legge il terzo elemento." );
        it.next();
        assertEquals( "ItaliaUno", it.currentItem().getRegista(), "sort naturale fallito." );
        assertTrue( it.hasNext(), "l'iteratore non legge il quarto elemento." );
        it.next();
        assertEquals( "Tarantino", it.currentItem().getRegista(), "sort naturale fallito." );
    }//testSortNaturale -> ossia usando Comparable anziché Comparator

}//TestingStrategies
