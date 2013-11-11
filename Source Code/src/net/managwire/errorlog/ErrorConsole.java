package net.managwire.errorlog;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.jdesktop.swingx.JXFrame;

/**
 *
 * @author Thievery
 */
public class ErrorConsole extends JXFrame {

    private JTextArea textArea = new JTextArea();

    public ErrorConsole() {
        super("Debug Console");
        textArea.setEditable(false);
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        installListener();
        setSize(400, 400);
    }

    public void installListener() {
        addWindowListener(new WindowListener() {

            public void windowActivated(WindowEvent e) {
                // Do Nothing
            }

            public void windowDeactivated(WindowEvent e) {
                // Do Nothing
            }

            public void windowDeiconified(WindowEvent e) {
                // Do Nothing
            }

            public void windowIconified(WindowEvent e) {
                // DO Nothing
            }

            public void windowClosed(WindowEvent e) {
                setVisible(false);
            }

            public void windowClosing(WindowEvent e) {
                // Do nothing
            }

            public void windowOpened(WindowEvent e) {
                // Do Nothing
            }
        });
    }

    public void write(String message) {
        textArea.insert('\n' + message, 0);
    }
}
