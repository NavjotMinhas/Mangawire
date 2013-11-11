package net.managwire.library;

import ca.odell.glazedlists.FilterList;
import java.awt.event.ActionEvent;
import net.managwire.library.table.LibraryTableFormat;
import net.managwire.library.table.LibraryTable;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumn;
import net.managwire.common.Data;
import net.managwire.components.panel.LoadingStage;
import net.managwire.components.button.NavigationButton;
import net.managwire.components.NavigationHeader;
import net.managwire.components.field.RoundTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Pride
 */
public class LibraryPanel extends LoadingStage {

    private FilterList filterList;
    private TypeMatchEditor matchEditor = new TypeMatchEditor();
    private LibraryTable table = new LibraryTable();
    private RoundTextField field = new RoundTextField(20);
    private NavigationButton all = new NavigationButton("All");
    private NavigationButton manga = new NavigationButton("Manga");
    private NavigationButton anime = new NavigationButton("Anime");
    private NavigationHeader header = new NavigationHeader();

    public LibraryPanel() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("Insets 0, fill, flowy, nogrid"));
        setOpaque(false);
        startLoad(true);
        LibraryLoader loader = new LibraryLoader();
        loader.execute();
        SortedList sortedList;
        table.getListModel().getReadWriteLock().readLock().lock();
        try {
            sortedList = new SortedList(table.getListModel());
            filterList = new FilterList(sortedList, matchEditor);
            installListeners();

            MatcherEditor<File> matcher = new TextComponentMatcherEditor<File>(field, new LibraryTextFilterator());
            FilterList<File> list = new FilterList<File>(filterList, matcher);
            EventTableModel model = new EventTableModel(list, new LibraryTableFormat());
            table.setModel(model);
            TableComparatorChooser tableSorter = TableComparatorChooser.install(table, sortedList, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

            JScrollPane tableScrollPane = new JScrollPane(table);
            TableColumn series = table.getColumnModel().getColumn(1);
            TableColumn size = table.getColumnModel().getColumn(2);
            TableColumn type = table.getColumnModel().getColumn(3);
            //initialize %column widths
            series.setMaxWidth(250);
            size.setMaxWidth(150);
            type.setMaxWidth(150);

            header.add(all, "width 100, growy, gap 0");
            header.add(manga, "width 100, growy, gap 0");
            header.add(anime, "width 100, growy, gap 0");
            header.add(field, "height 20, gap 30 0 0 0");
            add(header, "height 30, growx, gap 0");
            add(tableScrollPane, "growx ,growy, gap 0");
        } finally {
            try {
                table.getListModel().getReadWriteLock().readLock().unlock();
                table.addResult(loader.get());
                startLoad(false);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void installListeners() {
        all.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                matchEditor.setMatcher(null);
            }
        });
        anime.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                matchEditor.setMatcher(Data.downloadDirectory.getName().toLowerCase() + "\\anime");
            }
        });
        manga.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                matchEditor.setMatcher(Data.downloadDirectory.getName().toLowerCase() + "\\manga");
            }
        });
    }

    public static void main(String[] args) {
        LibraryPanel searchPanel = new LibraryPanel();

        // TableComparatorChooser tableSorter = TableComparatorChooser.install(issuesJTable, sortedIssues, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

        // create a frame with that panel
        JFrame frame = new JFrame("Test Run");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(540, 380);
        frame.getContentPane().add(searchPanel);
        frame.setVisible(true);


    }
}
