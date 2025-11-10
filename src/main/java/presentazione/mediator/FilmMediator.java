package presentazione.mediator;
import presentazione.*;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import dominio.*;
import applicazione.strategy.ricerca.*;
import applicazione.strategy.filter.*;
import applicazione.strategy.sort.*;

public class FilmMediator implements Mediator {
    private JTextField searchField, titoloField, registaField, genereField, annoField, votoField;
    private JComboBox<String> filtroBox, sortBox;
    private JButton addButton, removeButton, modifyButton;
    private DefaultListModel<Film> listModel;    //adatta la collezione alla lista nella gui
    private JList<Film> filmList;                //componente swing che mostra a schermo i film
    private Collezione collezione;

    public FilmMediator( DefaultListModel<Film> listModel, JList<Film> filmList, Collezione collezione ) {
        this.listModel = listModel;
        this.filmList = filmList;
        this.collezione = collezione;
    }//costruttore
    public FilmMediator( Collezione collezione ) {
        this.collezione = collezione;
        listModel = new DefaultListModel<>();
        filmList = new JList<>(listModel);
    }
    public DefaultListModel<Film> getListModel() { return listModel; }
    public JList<Film> getJList() { return filmList; }

    public void search( String tipo, String s ) {
        switch( tipo ) {
            case "Titolo" :
                collezione.setRicercaStrategy( new RicercaByTitolo( s ) );
                break;
            case "Regista" :
                collezione.setRicercaStrategy( new RicercaByRegista( s ) );
                break;
            default :
                JOptionPane.showMessageDialog(null, "Criterio non riconosciuto!");
                return;
        }
        collezione.sort();  //ordinamento di default
        refreshList( collezione.search() );
    }//cerca

    public void filtra(String tipo, String s ) {
        switch( tipo ) {
            case "Genere" :
                collezione.setFilterStrategy( new FilterByGenere( s ) );
                break;
            case "Stato" :
                collezione.setFilterStrategy( new FilterByStato( Film.Stato.convert( s ) ) );
                break;
            default :
                JOptionPane.showMessageDialog(null, "Filtro non riconosciuto!");
                return;
        }
        collezione.sort();  //ordinamento di default
        refreshList( collezione.filtra() );
    }//filtra
    public void annullaFiltri() {
        collezione.annullaFiltri();
        refreshList();
    }

    public void sort( String tipo ) {
        switch( tipo ) {
            case "Titolo" :
                collezione.setSortStrategy( new SortByTitolo() );
                break;
            case "Anno" :
                collezione.setSortStrategy( new SortByAnno() );
                break;
            case "Voto" :
                collezione.setSortStrategy( new SortByVoto() );
                break;
            default :
                JOptionPane.showMessageDialog(null, "Criterio non riconosciuto!");
                return;
        }
        collezione.sort();
        refreshList();
    }//sort
    public void annullaRicerca() {
        refreshList();
    }

    public void aggiungi() {
        JDialog dialogo = new JDialog((Frame) null, "Aggiungi Film", true);
        dialogo.setModal(true);
        dialogo.setSize(400,350);
        dialogo.setLayout(new GridLayout(7,2));

        titoloField = new JTextField();
        registaField = new JTextField();
        genereField = new JTextField();
        annoField = new JTextField();
        votoField = new JTextField();
        JComboBox<String> statoBox = new JComboBox<>(new String[]{"DA_VEDERE","IN_VISIONE","VISTO"});
        statoBox.setSelectedIndex(-1);  //nessun valore di default

        dialogo.add(new JLabel("Titolo*: "));
        dialogo.add(titoloField);
        dialogo.add(new JLabel("Regista*: "));
        dialogo.add(registaField);
        dialogo.add(new JLabel("Genere: "));
        dialogo.add(genereField);
        dialogo.add(new JLabel("Stato: "));
        dialogo.add(statoBox);
        dialogo.add(new JLabel("Anno: "));
        dialogo.add(annoField);
        dialogo.add(new JLabel("Voto: "));
        dialogo.add(votoField);

        JButton conferma = new JButton("Conferma");
        JButton annulla = new JButton("Annulla");
        conferma.addActionListener(new ConfermaAggiuntaListener(dialogo, titoloField, registaField,
                genereField, annoField, votoField, statoBox, collezione, this));
        annulla.addActionListener( new ChiudiDialogListener(dialogo));

        dialogo.add(conferma);
        dialogo.add(annulla);
        dialogo.setVisible(true);

    }

    public void rimuovi() {
        Film f = filmList.getSelectedValue();
        if( f != null ) {
            collezione.remove( f );
            refreshList();
        } else {
            JOptionPane.showMessageDialog(null, "Seleziona un film.");
        }
    }//rimuovi


    public void modifica() {
        Film f = filmList.getSelectedValue();
        if( f==null ) {
            JOptionPane.showMessageDialog( null, "Seleziona un film." );
            return;
        }
        Iteratore<Film> it = collezione.creaIteratore();
        boolean esisteFilm = false;
        while( it.hasNext() ) {
            it.next();
            if( it.currentItem().same( f ) ) {
                f = it.currentItem();
                esisteFilm = true;
                break;
            }
        }
        if ( !esisteFilm ) {
            JOptionPane.showMessageDialog(null, "Nessun film del catalogo selezionato da modificare.");
            return;
        }

        //adesso apro il dialogo con tutti i campi
        JDialog dialogo = new JDialog((Frame) null, "Modifica Film", true);
        dialogo.setSize(400, 350);
        dialogo.setLayout(new GridLayout(7,2));

        titoloField = new JTextField(f.getTitolo());
        registaField = new JTextField(f.getRegista());
        genereField = new JTextField(f.getGenere()!=null ? f.getGenere() : "");
        annoField = new JTextField(f.getAnno() != 0 ? String.valueOf(f.getAnno()) : "");
        votoField = new JTextField(f.getVoto() != 0 ? String.valueOf(f.getVoto()) : "");

        JComboBox<String> statoBox = new JComboBox<>(new String[]{"DA_VEDERE","IN_VISIONE","VISTO"});
        statoBox.setSelectedItem(f.getStato().name());
        String nuovoStato = (String) statoBox.getSelectedItem();
        if (nuovoStato != null && !nuovoStato.isEmpty())
            f.setStato(Film.Stato.valueOf(nuovoStato));

        dialogo.add(new JLabel("Titolo*: "));
        dialogo.add(titoloField);
        dialogo.add(new JLabel("Regista*: "));
        dialogo.add(registaField);
        dialogo.add(new JLabel("Genere: "));
        dialogo.add(genereField);
        dialogo.add(new JLabel("Stato: "));
        dialogo.add(statoBox);
        dialogo.add(new JLabel("Anno: "));
        dialogo.add(annoField);
        dialogo.add(new JLabel("Voto: "));
        dialogo.add(votoField);

        JButton conferma = new JButton("Conferma");
        JButton annulla = new JButton("Annulla");
        conferma.addActionListener( new ConfermaModificaListener( f, titoloField, registaField, genereField, annoField, votoField, statoBox, dialogo, this));
        annulla.addActionListener( new ChiudiDialogListener(dialogo));

        dialogo.add(conferma);
        dialogo.add(annulla);
        dialogo.setLocationRelativeTo(null);
        dialogo.setVisible(true);
    }//modifica


    public void refreshList() {
        listModel.clear();
        collezione.sort();  //ordinamento di default
        Iteratore<Film> it = collezione.creaIteratore();
        while( it.hasNext() ) {
            it.next();
            listModel.addElement( it.currentItem() );
        }
        System.out.println("refreshList() chiamato, elementi mostrati: " + listModel.size());
    }//refreshList -> per aggiornare la lista modello

    public void refreshList( java.util.List<Film> lista ) {
        listModel.clear();
        for( Film f : lista )
            listModel.addElement( f );
        System.out.println("refreshList() chiamato, elementi mostrati: " + listModel.size());
    }//refreshList -> aggiorno la lista modello con una lista esterna

}//FilmMediator
