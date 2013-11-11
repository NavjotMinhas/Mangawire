package net.managwire.search.table;

import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;
import net.managwire.common.Data;
import net.managwire.common.MangawireInitializer;
import net.managwire.components.field.RoundTextField;
import net.managwire.ui.tabbedpane.TabPanel;
import net.mangawire.p2p.content.SearchContentRequest;
import net.mangawire.type.SharedFileType;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Pride
 */
public class SearchResultPanel extends TabPanel {

    private SearchTable table = new SearchTable();
    private RoundTextField textField = new RoundTextField(20);
    private SearchContentRequest request;
    private SortedList<SharedFileType> sortedList;
    private FilterList<SharedFileType> filterList;

    public SearchResultPanel(String query) {
        this.request = new SearchContentRequest(Data.PEER_GROUP, query, table.getListModel());
        init();
    }

    private void init() {
        setLayout(new MigLayout("insets 10 0 10 0 ,fill, flowx, nogrid"));
        table.getListModel().getReadWriteLock().readLock().lock();
        try {
            MatcherEditor<SharedFileType> matcher = new TextComponentMatcherEditor<SharedFileType>(textField, new SearchResultTextFilterator());

            sortedList = new SortedList(table.getListModel(), new SearchComparator());
            filterList=new FilterList(sortedList,matcher);
            EventTableModel model = new EventTableModel(filterList, new SearchTableFormat());
            table.setModel(model);
            TableComparatorChooser tableSorter = TableComparatorChooser.install(table, sortedList, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

            JLabel label = new JLabel("Filter:");
            label.setForeground(Color.WHITE);

            JScrollPane tableScrollPane = new JScrollPane(table);
            TableColumn title = table.getColumnModel().getColumn(0);

            //initialize column widths
            title.setPreferredWidth(500);

            //add the components and do not change the add stack
            add(label);
            add(textField, "width 150, wrap");
            add(tableScrollPane, "growx ,growy");
        } finally {
            table.getListModel().getReadWriteLock().readLock().unlock();
            //Starts the search
            table.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        if (sortedList != null) {
                            SharedFileType type = sortedList.get(table.getSelectedRow());
                            MangawireInitializer.downloadPanel.addDownload(type);
                        }

                    }

                }
            });
            this.request.startSearch();
        }
    }

    public void stopSearch() {
        request.cancelSearch();
    }
}
