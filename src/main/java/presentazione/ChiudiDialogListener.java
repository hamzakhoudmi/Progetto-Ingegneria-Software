package presentazione;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public class ChiudiDialogListener implements ActionListener {
    private JDialog dialog;

    public ChiudiDialogListener(JDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.dispose();
    }
}
