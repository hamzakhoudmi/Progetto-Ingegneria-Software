package presentazione;
import javax.swing.*;
//import java.awt.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dominio.*;
import presentazione.mediator.*;

public class FilmGUI extends JFrame {
    private FilmMediator mediator;

    private DefaultListModel<Film> model;
    private JList<Film> filmList;

    private JButton aggiungiBtn;
    private JButton modificaBtn;
    private JButton eliminaBtn;
    private JButton filtraBtn;
    private JButton ordinaBtn;
    private JButton cercaBtn;
    private JButton annullaFiltriBtn;
    private JButton annullaRicercaBtn;

    public FilmGUI(FilmMediator mediator) {
        this.mediator = mediator;
        model = this.mediator.getListModel();
        filmList = this.mediator.getJList();
        initGUI();
    }

    private void initGUI() {
        setTitle("Catalogo Film");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(null);

        // --- LISTA FILM ---
        //model = new DefaultListModel<>();
        //filmList = new JList<>(model);
        filmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(filmList);
        add(scrollPane, BorderLayout.CENTER);

        // --- PANNELLO BOTTONI ---
        JPanel buttonPanel = new JPanel(new FlowLayout());
        aggiungiBtn = new JButton("Aggiungi");
        modificaBtn = new JButton("Modifica");
        eliminaBtn = new JButton("Elimina");
        filtraBtn = new JButton("Filtra");
        ordinaBtn = new JButton("Ordina");
        cercaBtn = new JButton("Cerca");
        annullaFiltriBtn = new JButton("Aggiorna");
        annullaRicercaBtn = new JButton("Annulla Ricerca");

        buttonPanel.add(aggiungiBtn);
        buttonPanel.add(modificaBtn);
        buttonPanel.add(eliminaBtn);
        buttonPanel.add(filtraBtn);
        buttonPanel.add(ordinaBtn);
        buttonPanel.add(cercaBtn);
        buttonPanel.add(annullaFiltriBtn);
        buttonPanel.add(annullaRicercaBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // --- COLLEGA LISTENER ---
        aggiungiBtn.addActionListener(new AggiungiButtonListener());
        modificaBtn.addActionListener(new ModificaButtonListener());
        eliminaBtn.addActionListener(new EliminaButtonListener());
        filtraBtn.addActionListener(new FiltraButtonListener());
        ordinaBtn.addActionListener(new OrdinaButtonListener());
        cercaBtn.addActionListener(new CercaButtonListener());
        annullaFiltriBtn.addActionListener(e -> mediator.annullaFiltri());
        annullaRicercaBtn.addActionListener(e -> mediator.annullaRicerca());

        setVisible(true);
    }

    public Film getSelectedFilm() {
        return filmList.getSelectedValue();
    }

    public void aggiornaLista(java.util.List<Film> films) {
        model.clear();
        for (Film f : films) {
            model.addElement(f);
        }
    }

    //metto i listeners per i bottoni
    private class AggiungiButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mediator.aggiungi(); // il mediator aprirà il dialog di aggiunta
        }
    }

    private class ModificaButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mediator.modifica(); // nel mediator viene gestita la selezione tramite filmList interno
        }
    }

    private class EliminaButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mediator.rimuovi(); // idem: mediator rimuove film selezionato
        }
    }

    private class CercaButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String[] opzioni = {"Titolo", "Regista"};
            String tipo = (String) JOptionPane.showInputDialog(
                    FilmGUI.this,
                    "Scegli il tipo di ricerca:",
                    "Ricerca",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opzioni,
                    opzioni[0]
            );
            if (tipo == null) return;

            String testo = JOptionPane.showInputDialog(FilmGUI.this, "Inserisci il valore da cercare:");
            if (testo == null || testo.trim().isEmpty()) return;

            mediator.search(tipo, testo.trim());
        }
    }

    private class FiltraButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String[] opzioni = {"Genere", "Stato"};
            String tipo = (String) JOptionPane.showInputDialog(
                    FilmGUI.this,
                    "Scegli il tipo di filtro:",
                    "Filtro",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opzioni,
                    opzioni[0]
            );
            if (tipo == null) return;

            String valore;
            if (tipo.equals("Stato")) {
                String[] stati = {"DA_VEDERE", "IN_VISIONE", "VISTO"};
                valore = (String) JOptionPane.showInputDialog(
                        FilmGUI.this,
                        "Scegli lo stato:",
                        "Filtro per Stato",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        stati,
                        stati[0]
                );
            } else {
                valore = JOptionPane.showInputDialog(FilmGUI.this, "Inserisci il genere:");
            }

            if (valore == null || valore.trim().isEmpty()) return;

            mediator.filtra(tipo, valore.trim());
        }
    }

    private class OrdinaButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String[] opzioni = {"Titolo", "Anno", "Voto"};
            String tipo = (String) JOptionPane.showInputDialog(
                    FilmGUI.this,
                    "Scegli il criterio di ordinamento:",
                    "Ordina",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opzioni,
                    opzioni[0]
            );
            if (tipo == null) return;

            mediator.sort(tipo);
        }
    }

}
