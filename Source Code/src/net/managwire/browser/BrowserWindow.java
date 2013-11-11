package net.managwire.browser;

/**
 *
 * @author Thievery
 */

import net.managwire.components.button.MorphingButton;
import net.managwire.components.field.RoundTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.mozilla.browser.MozillaPanel;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import net.managwire.common.MangawireInitializer;
import net.managwire.homepanel.HomePanel;
import org.mozilla.browser.impl.components.JChromeButton;
import org.mozilla.browser.mt;

public class BrowserWindow extends MozillaPanel {
    // Provides a short version of the title

    private String BrowserTitle;
    private String OrignalTitle;


    public BrowserWindow() {
        super(VisibilityMode.FORCED_VISIBLE, VisibilityMode.FORCED_HIDDEN);

    }

    @Override
    public void setTitle(String title) {

    }

    public String getBrowserTitle() {
        return OrignalTitle;
    }

    public LinkedList<String> getAllLinks() {
        LinkedList<String> Links = new LinkedList<String>();
        // Needs code for getting links
        return Links;
    }

    @Override
    protected void createToolbar() {
            toolbar = new BrowserToolBar();
            toolbar.setOpaque(false);
            toolbar.setFloatable(false); //bug #18229
            add(toolbar, BorderLayout.NORTH);
            backButton = new MorphingButton("Back", SwingConstants.WEST); //$NON-NLS-1$ //$NON-NLS-2$
            backButton.setPreferredSize(new Dimension(80, 30));
            // backButton.setEnabled(false);
            backButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    goBack();
                }
            });
            toolbar.add(backButton);
            toolbar.add(new JToolBar.Separator());
            forwardButton = new MorphingButton("Forward", SwingConstants.EAST); //$NON-NLS-1$ //$NON-NLS-2$
            // forwardButton.setEnabled(false);
            forwardButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    goForward();
                }
            });
            forwardButton.setPreferredSize(new Dimension(80, 30));
            toolbar.add(forwardButton);
            reloadButton = new JChromeButton("reload", mt.t("MozillaPanel.Tooltip_Reload")); //$NON-NLS-1$ //$NON-NLS-2$
            reloadButton.setEnabled(false);
            reloadButton.setOpaque(false);
            reloadButton.setContentAreaFilled(false);
            reloadButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    reload();
                }
            });
            toolbar.add(reloadButton);
            stopButton = new JChromeButton("stop", mt.t("MozillaPanel.Tooltip_Stop")); //$NON-NLS-1$ //$NON-NLS-2$
            stopButton.setOpaque(false);
            stopButton.setContentAreaFilled(false);
            stopButton.setEnabled(false);
            stopButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    stop();
                }
            });
            toolbar.add(stopButton);

            //enclose the textfield into a panel,
            //to keep the default height
            JPanel p = new JPanel();
            p.setLayout(new GridBagLayout());
            p.setOpaque(false);
            urlBar = new RoundTextField(30);
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 1.0;
            c.weighty = 0.0;
            c.fill = GridBagConstraints.HORIZONTAL;
            p.add(urlBar, c);
            urlBar.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String uri = urlBar.getText();
                    load(uri);
                }
            });
            toolbar.add(p);

            goButton = new JChromeButton("go", mt.t("MozillaPanel.Tooltip_Go")); //$NON-NLS-1$ //$NON-NLS-2$
            goButton.setOpaque(false);
            goButton.setContentAreaFilled(false);
            goButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String uri = urlBar.getText();
                    load(uri);
                }
            });
            toolbar.add(goButton);
        
    }

    @Override
    public void onSetStatus(String text) {
        // Do Nothing
    }

    @Override
    protected void createStatusbar() {
        statusField = new JTextField(""); //$NON-NLS-1$
        statusField.setEditable(false);
        statusField.setFocusable(false);
        add(statusField,BorderLayout.SOUTH);
    }

    public static void main(String []args) throws Exception{
        //mangafoxSpider manga=new mangafoxSpider();
        //MangawireInitializer.preSetup();
        BrowserInitializer.initializeXULRunner();
        JFrame frame=new JFrame("String");
        frame.setLayout(new BorderLayout());
        //win.loadHTML("<html><head><link rel="+'"'+"stylesheet"+'"'+" type="+'"'+"text/css"+'"'+" href="+'"'+"file:/"+BrowserWindow.class.getResource("main.css").getPath()+'"'+"/></head><body><div id=main>"+manga.prepQuery("bl")+"</div>"+"</body>"+"</html>","file:/"+BrowserWindow.class.getResource("main.css").getPath());
        MozillaPanel panel=new MozillaPanel();
        //System.out.println("file:/"+BrowserWindow.class.getResource("infogrid.js").getPath());
        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setVisible(true);
        panel.load("file:///C:/Users/Pride/Desktop/InfoGrid/index.html");
        BrowserUtils.installDOMListener(panel);
    }
}

