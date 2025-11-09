package presentazione;
import presentazione.mediator.*;

import dominio.Film;
import dominio.Collezione;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ConfermaAggiuntaListener implements ActionListener {
    private JDialog dialog;
    private JTextField titoloField, registaField, genereField, annoField, votoField;
    private JComboBox<String> statoBox;
    private Collezione collezione;
    private FilmMediator mediator;

    public ConfermaAggiuntaListener(JDialog dialog,
                                    JTextField titoloField,
                                    JTextField registaField,
                                    JTextField genereField,
                                    JTextField annoField,
                                    JTextField votoField,
                                    JComboBox<String> statoBox,
                                    Collezione collezione,
                                    FilmMediator mediator) {
        this.dialog = dialog;
        this.titoloField = titoloField;
        this.registaField = registaField;
        this.genereField = genereField;
        this.annoField = annoField;
        this.votoField = votoField;
        this.statoBox = statoBox;
        this.collezione = collezione;
        this.mediator = mediator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String titolo = titoloField.getText().trim();
            String regista = registaField.getText().trim();
            if (titolo.isEmpty() || regista.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Titolo e Regista sono obbligatori!");
                return;
            }

            Film.ConcreteBuilder builder = new Film.ConcreteBuilder(titolo, regista);

            String genere = genereField.getText().trim();
            if (!genere.isEmpty()) builder.genere(genere);

            String annoStr = annoField.getText().trim();
            if (!annoStr.isEmpty()) builder.anno(Integer.parseInt(annoStr));

            String votoStr = votoField.getText().trim();
            if (!votoStr.isEmpty()) builder.voto(Integer.parseInt(votoStr));

            String statoStr = (String) statoBox.getSelectedItem();
            if (statoStr != null && !statoStr.isEmpty()) {
                builder.stato(Film.Stato.valueOf(statoStr));
            }

            Film f = builder.build();
            collezione.add(f);
            System.out.println("Film aggiunto: " + f);
            System.out.println("Totale film: " + collezione.size());

            // chiediamo al mediator di aggiornare la vista
            if (mediator != null) mediator.refreshList();
            dialog.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dialog, "Errore: " + ex.getMessage());
        }
    }
}
