package infrastruttura;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

public class JsonPersister implements Persister {
    private final Gson gson;
    private final String filePath;

    public JsonPersister( String filePath ) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.filePath = filePath;
    }//costruttore

    public void salva( Object dati ) {
        try( FileWriter writer = new FileWriter( filePath ) ) { //writer viene automaticamente chiusa, anche se capita eccezione
            gson.toJson( dati, writer );
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }//salva

    public <T> T carica( Type tipo ) {
        try( FileReader reader = new FileReader( filePath ) ) {
            return gson.fromJson( reader, tipo );
        } catch( IOException e ) {
            return null;  //se file vuoto ritorno null
        }
    }
}
