package presentazione;
import dominio.Film;
import presentazione.mediator.*;

import dominio.CollezioneSingleton;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // uso SwingUtilities.invokeLater per sicurezza thread GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // la collezione principale
                CollezioneSingleton collezione = CollezioneSingleton.CATALOGO;
                DefaultListModel<Film> listModel = new DefaultListModel<>();
                JList<Film> filmList = new JList<>(listModel);

                // creiamo la GUI con i riferimenti a collezione
                // FilmMediator prenderà listModel e JList direttamente dentro il costruttore
                FilmMediator mediator = new FilmMediator(listModel, filmList, collezione);

                // creiamo la GUI, passando il mediator
                FilmGUI gui = new FilmGUI(mediator);

                // popoliamo la lista iniziale
                mediator.refreshList();
            }
        });
    }
}
