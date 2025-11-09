package dominio;
import java.util.*;
import applicazione.strategy.sort.*;
import applicazione.strategy.ricerca.*;
import applicazione.strategy.filter.*;
import infrastruttura.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public enum CollezioneSingleton implements Collezione {
    CATALOGO;
    private List<Film> films = new ArrayList<>();
    private List<Film> filterFilms = new ArrayList<>();
    private int modCounter = 0;                      //gestisce i conflitti di scansione aumentando la consistenza
    private int size;
    private SortStrategy ordina;
    private RicercaStrategy ricerca;
    private FilterStrategy filtro;
    private Persister persister;

    private CollezioneSingleton() {
        persister = new JsonPersister("collezionesingleton.json");
        Type tipoListaFilm = new TypeToken<List<Film>>() {}.getType();
        List<Film> caricata = persister.carica(tipoListaFilm);
        films = (caricata != null) ? caricata : new ArrayList<>();
    }

    public int size() { return size; }
    public boolean isEmpty() { return size==0; }

    //settaggio strategie
    public void setSortStrategy( SortStrategy s ) { ordina = s; }
    public void setRicercaStrategy( RicercaStrategy s ) { ricerca = s; }
    public void setFilterStrategy( FilterStrategy s ) { filtro = s; }

    public void add( Film f ) {
        for( Film f1: films )
            if( f.same( f1 ) ) {
                System.out.println( "Il film è già presente." );
                return;
            }
        films.add( f );
        size++; modCounter++;
        salva();
    }//add
    public Film remove( Film f ) {
        for( Film f1 : films )
            if( f.same( f1 ) ) {
                f = f1;
                films.remove( f1 );
                size--; modCounter++;
                salva();
                return f;
            }
        return null;
    }//remove
    public void clear() {
        films.clear();
        filterFilms.clear();
        size = 0;
        modCounter++;
        salva();
    }//clear

    public void sort() {
        if( ordina!=null )
            Collections.sort( films, ordina );
        else
            Collections.sort( films );
    }//sort

    public List<Film> filtra() {
        if( filtro==null ) {
            System.out.println( "Nessun filtro." );
            return Collections.emptyList();         //ritorno una lista vuota
        }
        if( filterFilms.isEmpty() ) {
            for (Film f : films)
                if (filtro.include(f))
                    filterFilms.add(f);
        } else {
            Iterator<Film> it = filterFilms.iterator();
            Film fi;
            while( it.hasNext() ) {
                fi = it.next();
                if( !filtro.include( fi ) )
                    it.remove();
            }
        }
        return new ArrayList<>( filterFilms );     //ritorno una copia, per evitare manipolazioni esterne sulla lista privata
    }//filtra
    public void annullaFiltri() { filterFilms.clear(); }

    public List<Film> search() {
        if( ricerca==null ) {
            System.out.println( "Seleziona prima un criterio di ricerca." );
            return Collections.emptyList();
        }
        List<Film> filmcercati = new ArrayList<>();
        if( !filterFilms.isEmpty() ) {
            for( Film f : filterFilms )
                if( ricerca.accept( f ) )
                    filmcercati.add( f );
        } else {
            for( Film f : films )
                if( ricerca.accept( f ) )
                    filmcercati.add( f );
        }
        return filmcercati;
    }//search

    public void salva() {
        persister.salva( films );
    }//salva

    public Iteratore<Film> creaIteratore() {
        return new IteratoreConcreto();
    }
    private class IteratoreConcreto implements Iteratore<Film> {
        private int current = -1;
        private int modCounterMirror = modCounter; //durante l'iterazione non ci devono essere modifiche esterne a this
        private boolean flag = false;

        public boolean hasNext() {
            return current + 1 < films.size();
        }
        public void next() {
            if( modCounterMirror!=modCounter )
                throw new ConcurrentModificationException( "conflitto di iterazione" );
            if( !hasNext() )
                throw new IllegalStateException( "Fuori range." );
            current++;
            flag = true;
        }//next
        public Film currentItem() {
            if( modCounterMirror!=modCounter )
                throw new ConcurrentModificationException( "conflitto di iterazione" );
            if( current>=films.size() )
                throw new IllegalStateException( "Fuori range." );
            if( current<0 ) throw new IllegalStateException( "Nessun elemento corrente, chiama prima next()." );
            return films.get( current );
        }//currentItem
        public Film remove() {
            if( modCounterMirror!=modCounter )
                throw new ConcurrentModificationException( "conflitto di iterazione" );
            if( !flag )
                throw new IllegalStateException( "Nessun elemento corrente, chiama prima next()." );
            Film f = films.remove( current );
            modCounterMirror++; modCounter++;
            current--;
            size--;
            flag = false;
            return f;
        }//remove
    }//IteratoreConcreto

}//CollezioneSingleton

