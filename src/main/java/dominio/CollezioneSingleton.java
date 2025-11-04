package dominio;
import java.util.*;

public enum CollezioneSingleton implements Collezione {
    CATALOGO;
    private List<Film> films = new ArrayList<>();
    private int modCounter = 0;      //gestisce i conflitti di scansione aumentando la consistenza
    //private Strategy strategia;

    public List<Film> getFilms() { return films; }
    //public void setStrategy( Strategy s ) {}

    public void add( Film f ) {
        for( Film f1: films )
            if( f.same( f1 ) ) {
                System.out.println( "Il film è già presente." );
                return;
            }
        films.add(f);
        modCounter++;
    }//add
    public Film remove( Film f ) {
        for( Film f1 : films )
            if( f.same( f1 ) ) {
                f = f1;
                films.remove( f1 );
                modCounter++;
                return f;
            }
        return null;
    }//remove

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
            current--;
            flag = false;
            return f;
        }//remove
    }//IteratoreConcreto

}//CollezioneSingleton

//metodi: ricerca() filter() sort() salva()