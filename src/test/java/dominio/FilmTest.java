package dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class FilmTest {
    @Test
    void testBuilderObbligatori() {  //quindi assumo che gli attributi facoltativi siano nulli
        Builder b = new Film.ConcreteBuilder( "Batman", "Nolan");
        Film f = b.build();
        assertEquals( "Batman", f.getTitolo() );
        assertEquals( Film.Stato.DA_VEDERE, f.getStato() );
        assertEquals( "Nolan", f.getRegista() );
        assertNull( f.getGenere() );
        assertEquals( 0, f.getAnno() );
        assertEquals( 0, f.getVoto() );
    }
    @Test
    void testBuilder() {
        Film f = new Film.ConcreteBuilder( "Hunger Games", "Ross" )
                .genere("Fantascienza").stato(Film.Stato.VISTO).anno(2012)
                .voto(10).build();
        assertEquals( "Hunger Games", f.getTitolo() );
        assertEquals( "Ross", f.getRegista() );
        assertEquals( "Fantascienza", f.getGenere() );
        assertEquals( 2012, f.getAnno() );
        assertEquals( 10, f.getVoto() );
    }
    @Test
    void testGetSet() {
        Film f = new Film.ConcreteBuilder( "Shutter Island", "Scorsese" ).build();
        f.setTitolo( "Hateful Eight" );
        f.setRegista( "Tarantino" );
        f.setGenere( "western" );
        f.setStato( Film.Stato.IN_VISIONE );
        f.setAnno( 2015 );
        f.setVoto( 8 );

        assertEquals( "Hateful Eight", f.getTitolo() );
        assertEquals( "Tarantino", f.getRegista() );
        assertEquals( "western", f.getGenere() );
        assertEquals( Film.Stato.IN_VISIONE, f.getStato() );
        assertEquals( 2015, f.getAnno() );
        assertEquals( 8, f.getVoto() );
    }

    @Test
    void testSame() {
        Film f1 = new Film.ConcreteBuilder( "Titolo1", "Regista1" ).build();
        Film f2 = new Film.ConcreteBuilder( "Titolo1", "Regista2" ).build();
        assertFalse( f1.same( f2 ), "i due film sono gli stessi" );
    }
}
