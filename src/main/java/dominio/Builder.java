package dominio;

public interface Builder {

    Builder stato( Film.Stato stato );
    Builder anno( int anno );
    Builder genere( String genere );
    Builder voto( int voto );

    Film build();

}//Builder
