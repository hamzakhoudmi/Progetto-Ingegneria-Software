package infrastruttura;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dominio.Collezione;
import dominio.Film;

import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;

public class EsportaJson {
    public static void main(String[] args) throws Exception {
        JsonPersister jp = new JsonPersister("collezionesingleton.json");
        Type tipoListaFilm = new TypeToken<List<Film>>() {}.getType();
        List<Film> caricata = jp.carica(tipoListaFilm);
        //Collezione collezione = JsonPersister.carica("persistenza.txt"); // tuo metodo
        Gson gson = new Gson();
        String json = gson.toJson(caricata);

        try (FileWriter writer = new FileWriter("collezione.json")) {
            writer.write(json);
        }

        System.out.println("Esportazione completata:\n" + json);
    }
}
