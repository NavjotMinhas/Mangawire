package net.managwire.downloadmanager;

import java.awt.BorderLayout;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import net.managwire.components.tablecellrender.TableHeaderCellRender;
import net.managwire.ui.TableUI;
import net.managwire.ui.tabbedpane.TabPanel;
import net.mangawire.type.Type;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Pride
 */
public class DownloadPanel extends TabPanel {

    private TableModel model = new TableModel();
    private JXTable table = new JXTable(model);
    private JScrollPane scrollPane = new JScrollPane(table);

    public DownloadPanel() {
        super(new MigLayout("Insets 0, fill, flowy"));
        init();
    }

    private void init() {
        table.setUI(new TableUI());
        AnimeProgressBar renderer = new AnimeProgressBar(0, 100);
        renderer.setStringPainted(true);

        table.getTableHeader().setDefaultRenderer(new TableHeaderCellRender());
        table.setDefaultRenderer(JProgressBar.class, renderer);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(false);
        table.setShowGrid(false);

        setLayout(new BorderLayout());
        //add(new DownloadManagerToolBar(), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    }

    public void addDownload(Type type) {
        Download download = new Download(type);
        download.startDownload();
        model.addDownload(download);
    }
}
