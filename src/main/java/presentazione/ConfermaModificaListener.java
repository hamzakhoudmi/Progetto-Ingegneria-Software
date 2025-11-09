package presentazione;
import presentazione.mediator.*;

import dominio.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ConfermaModificaListener implements ActionListener {
    private Film f;
    private JTextField titoloField, registaField, genereField, annoField, votoField;
    private JComboBox<String> statoBox;
    private JDialog dialog;
    private FilmMediator mediator;

    public ConfermaModificaListener(Film f,
                                    JTextField titoloField,
                                    JTextField registaField,
                                    JTextField genereField,
                                    JTextField annoField,
                                    JTextField votoField,
                                    JComboBox<String> statoBox,
                                    JDialog dialog,
                                    FilmMediator mediator) {
        this.f = f;
        this.titoloField = titoloField;
        this.registaField = registaField;
        this.genereField = genereField;
        this.annoField = annoField;
        this.votoField = votoField;
        this.statoBox = statoBox;
        this.dialog = dialog;
        this.mediator = mediator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String nuTitolo = titoloField.getText().trim();
            String nuRegista = registaField.getText().trim();
            if (nuTitolo.isEmpty() || nuRegista.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Titolo e Regista sono obbligatori.");
                return;
            }

            f.setTitolo(nuTitolo);
            f.setRegista(nuRegista);

            String nuGenere = genereField.getText().trim();
            if (!nuGenere.isEmpty()) f.setGenere(nuGenere);

            String statoStr = (String) statoBox.getSelectedItem();
            if (statoStr != null && !statoStr.isEmpty()) {
                f.setStato(Film.Stato.valueOf(statoStr));
            }

            String annoStr = annoField.getText().trim();
            if (!annoStr.isEmpty()) f.setAnno(Integer.parseInt(annoStr));

            String votoStr = votoField.getText().trim();
            if (!votoStr.isEmpty()) f.setVoto(Integer.parseInt(votoStr));

            if (mediator != null) mediator.refreshList();
            JOptionPane.showMessageDialog(dialog, "Modifica completata!");
            CollezioneSingleton.CATALOGO.salva();  //salvo le modifiche in modo persistente nell'unica istanza
            dialog.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dialog, "Errore: " + ex.getMessage());
        }
    }
}
