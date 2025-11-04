package dominio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ConcurrentModificationException;
import static org.junit.jupiter.api.Assertions.*;

public class CollezioneSingletonTest {
    private CollezioneSingleton catalogo;
    private Film f1, f2;

    @BeforeEach
    void setUp() {
        catalogo = CollezioneSingleton.CATALOGO;
        catalogo.getFilms().clear();  //elimino le vecchie modifiche
        f1 = new Film.ConcreteBuilder( "Inception", "Nolan" ).anno(2010).build();
        f2 = new Film.ConcreteBuilder( "Interstellar", "Nolan" ).anno(2014).build();
    }

    @Test
    void testSingletonUnicita() {
        CollezioneSingleton catalogo2 = CollezioneSingleton.CATALOGO;
        assertSame( catalogo, catalogo2, "due istanze singleton non uguali" );
    }

    @Test
    void testAddFilmDuplicato() {
        catalogo.add( f1 );
        catalogo.add( f1 );
        assertEquals( 1, catalogo.getFilms().size(), "oggetti duplicati");
    }
    @Test
    void TestAddFilm() {
        catalogo.add( f1 );
        assertEquals( 1, catalogo.getFilms().size() );
        assertEquals( "Inception", catalogo.getFilms().get(0).getTitolo() );
    }

    @Test
    void testRemoveFilm() {
        catalogo.add( f1 );
        Film f = catalogo.remove( f1 );
        assertEquals( f1, f );
        assertTrue( catalogo.getFilms().isEmpty(), "elemento non rimosso" );
    }

    @Test
    void testIteratore() {
        catalogo.add( f1 );
        catalogo.add( f2 );
        Iteratore<Film> it = catalogo.creaIteratore();
        assertTrue( it.hasNext() );
        it.next();
        Film corrente = it.currentItem();
        assertEquals( f1.getTitolo(), corrente.getTitolo() );
    }

    @Test
    void testIteratoreRemove() {
        catalogo.add( f1 );
        Iteratore<Film> it = catalogo.creaIteratore();
        it.next();
        Film f = it.remove();
        assertFalse( it.hasNext(), "iteratore.remove funziona male" );
    }

}
