package net.managwire.search.table;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import net.managwire.common.MangawireInitializer;
import net.managwire.common.ThemeConstants;
import net.managwire.components.field.RoundTextField;
import net.managwire.components.button.OceanButton;
import net.managwire.ui.tabbedpane.SearchResultUI;
import net.managwire.ui.tabbedpane.TabComponent;
import net.managwire.ui.tabbedpane.TabPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Pride
 */
public class SearchPanel extends TabPanel {

    private static final Color BORDER_COLOR = new Color(0x074b4b);
    private SearchControls control = new SearchControls();
    private JPanel pane = new JPanel();
    private JTabbedPane tabbedPane = new JTabbedPane();
    private MangawireDefaultBanner banner;

    public SearchPanel() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 10,fill"));
        pane.setLayout(new MigLayout("flowy, fill"));
        pane.setOpaque(false);
        Slider slider = new Slider();
        ImageButton button1 = new ImageButton("images/testAdv.png");
        ImageButton button2 = new ImageButton("images/anmen.png");
        ImageButton button3 = new ImageButton("images/discopy.jpg");
        ImageButton button4 = new ImageButton("images/sigggcopy.jpg");
        ImageButton dummyButton = new ImageButton("images/sigggcopy.jpg");



        slider.add(dummyButton, "width 450, growy, pad 0");
        slider.addButton(button1, "MangaWire");
        slider.addButton(button2, "NeuroAnime");
        slider.addButton(button3, "What's New?");
        slider.addButton(button4, "Bleach");

        banner = new MangawireDefaultBanner(slider);

        tabbedPane.setUI(new SearchResultUI());
        //add the components and do not change the add stack
        add(control, "width 180, growy");
        add(banner, "growx,growy");

        pane.add(tabbedPane, "grow");
        pane.add(MangawireInitializer.downloadPanel, "growx, height 200 ");

    }

    public void addBanner() {
        remove(pane);
        add(banner, "growx,growy");
        repaint();
    }

    /**
     * The search can be accessed through other outside classes such as the
     * DOM Listener. 
     * @param query search string
     */
    public void setSearch(final String query) {
        new Thread() {

            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        if (tabbedPane.getTabCount() == 0) {
                            SearchPanel.this.remove(banner);
                            SearchPanel.this.add(pane, "grow");
                            SearchPanel.this.repaint();
                        }
                        tabbedPane.addTab(query, new SearchResultPanel(query));
                        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, new TabComponent(tabbedPane, SearchPanel.this));
                    }
                });
            }
        }.start();
    }

    class SearchControls extends JPanel {

        private JLabel mangaToolsLabel = new JLabel("Search");
        private OceanButton searchButton = new OceanButton("Search Title");
        private RoundTextField searchField = new RoundTextField(20);

        public SearchControls() {

            /**
             * Set up the component
             * before adding more components
             */
            setLayout(new MigLayout("center"));
            setOpaque(false);
            mangaToolsLabel.setForeground(Color.WHITE);
            mangaToolsLabel.setFont(ThemeConstants.TAB_FONT);
            searchButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    setSearch(searchField.getText());
                }
            });
            add(mangaToolsLabel, "wrap");
            add(searchField, "width 150,wrap");
            add(searchButton, "gaptop 5,gapbottom 30,wrap");
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0, getHeight(),
                    new float[]{0.0f, 1.0f}, new Color[]{ThemeConstants.TOP_GRADIENT_COLOR,
                        ThemeConstants.BOTTOM_GRADIENT_COLOR});
            g2d.setPaint(paint);
            g2d.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
            g2d.setColor(BORDER_COLOR);
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            g2d.dispose();
        }
    }
}
