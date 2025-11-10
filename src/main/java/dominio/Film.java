package dominio;
import java.util.*;

public class Film implements Comparable<Film> {

    //campi obbligatori
    private String titolo;
    private String regista;
    //campi facoltativi
    private String genere;
    private Stato stato;
    private int anno;
    private int voto;

    private Film( ConcreteBuilder b ) {
        titolo = b.t1;
        regista = b.r1;
        genere = b.g1;
        stato = b.s1;
        anno = b.a1;
        voto = b.v1;
    }//costruttore
    public Film() {}

    public enum Stato {
        DA_VEDERE, IN_VISIONE, VISTO;

        public static Stato convert( String s ) {
            if( s.equalsIgnoreCase( "DA_VEDERE" ) )
                return Stato.DA_VEDERE;
            if( s.equalsIgnoreCase( "IN_VISIONE" ) )
                return Stato.IN_VISIONE;
            if( s.equalsIgnoreCase( "VISTO" ) )
                return Stato.VISTO;
            throw new IllegalArgumentException( "Stato non idoneo." );
        }//convert
    }//Stato

    //getter
    public String getTitolo() { return titolo; }
    public String getRegista() { return regista; }
    public String getGenere() { return genere; }
    public Stato getStato() { return stato; }
    public int getAnno() { return anno; }
    public int getVoto() { return voto; }
    //setter
    public void setTitolo( String t ) { titolo = t; }
    public void setRegista( String r ) { regista = r; }
    public void setGenere( String g ) { genere = g; }
    public void setStato( Stato s ) { stato = s; }
    public void setAnno( int a ) { anno = a; }
    public void setVoto( int v ) {
        if( v<0 || v>10 ) throw new IllegalArgumentException( "Voto non ammissibile." );
        voto = v;
    }

    //il builder potrebbe ricreare diversamente lo stesso film, questo metodo valuta la somiglianza tra due film
    public boolean same( Film f ) {
        if( f==null ) return false;
        return Objects.equals( titolo, f.titolo )
                && Objects.equals( regista, f.regista );
    }//same

    //ordinamento di default: per titolo e se stesso titolo allora per regista
    public int compareTo( Film f ) {
        int c = titolo.compareToIgnoreCase( f.getTitolo() );
        if( c==0 )
            c = regista.compareToIgnoreCase( f.getRegista() );
        return c;
    }//compareto

    public String toString() {
        return "[ " + titolo + ", " + regista + ", " + genere + ", " + stato + ", "
                + anno + ", " + voto + " ]";
    }//toString

    public static class ConcreteBuilder implements Builder {
        private String t1, r1, g1;  //titolo, regista, genere
        private Stato s1;           //stato
        private int a1, v1;         //anno, voto

        public ConcreteBuilder( String t2, String r2 ) {
            t1 = t2;
            r1 = r2;
            s1 = Stato.DA_VEDERE;  //stato di default
        }//costruttore
        public Builder genere( String g2 ) {
            g1 = g2;
            return this;
        }
        public Builder stato( Stato s2 ) {
            s1 = s2;
            return this;
        }
        public Builder anno( int a2 ) {
            a1 = a2;
            return this;
        }
        public Builder voto( int v2 ) {
            if( v2<0 || v2>10 ) throw new IllegalArgumentException( "Voto non ammissibile." );
            v1 = v2;
            return this;
        }
        public Film build() {
            return new Film( this );
        }
    }//ConcreteBuilder

}//Film
