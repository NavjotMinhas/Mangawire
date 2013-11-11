package net.managwire.search.table;

/**
 *
 * @author Thievery
 */
import net.managwire.components.tablecellrender.TableCellRender;
import ca.odell.glazedlists.BasicEventList;
import javax.swing.table.TableCellRenderer;
import net.managwire.components.tablecellrender.TableHeaderCellRender;
import net.managwire.ui.SearchTableUI;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.mangawire.p2p.content.BootStrapping;
import net.mangawire.type.Type;
import org.jdesktop.swingx.JXTable;

public class SearchTable extends JXTable {

    private BasicEventList<Type> listModel = new BasicEventList<Type>();

    public SearchTable() {
        init();
    }

    private void init() {
        installDecoraters();
        getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                // do somethinglike update buttons
                //System.out.println("click");
            }
        });
    }

    private void installDecoraters() {
        setUI(new SearchTableUI());
        getTableHeader().setDefaultRenderer(new TableHeaderCellRender());
    }

    public void addResult(Type type) {
        listModel.getReadWriteLock().writeLock().lock();
        try {
            listModel.add(type);
        } finally {
            listModel.getReadWriteLock().writeLock().unlock();
        }
    }

    public BasicEventList<Type> getListModel() {
        return listModel;
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return new TableCellRender();
    }

    public static void main(String[] args) {
        BootStrapping.BootStrap_Network();

        SearchPanel searchPanel = new SearchPanel();

        // TableComparatorChooser tableSorter = TableComparatorChooser.install(issuesJTable, sortedIssues, TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

        // create a frame with that panel
        JFrame frame = new JFrame("Test Run");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(540, 380);
        frame.getContentPane().add(searchPanel);
        frame.setVisible(true);


    }
}
