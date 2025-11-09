package infrastruttura;
import java.lang.reflect.Type;

public interface Persister {
    void salva( Object dati );
    <T> T carica( Type tipo );
}
