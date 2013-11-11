package net.managwire.library.table;

import net.managwire.components.tablecellrender.CenterTableCellRender;
import net.managwire.components.tablecellrender.LeftTableCellRender;
import ca.odell.glazedlists.BasicEventList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import net.managwire.components.tablecellrender.TableHeaderCellRender;
import net.managwire.ui.SearchTableUI;
import org.jdesktop.swingx.JXTable;

public class LibraryTable extends JXTable {

    private BasicEventList<File> listModel = new BasicEventList<File>();

    public LibraryTable() {
        init();
    }

    private void init() {
        installDecoraters();
        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                // do somethinglike update buttons
            }
        });
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                }

            }
        });
    }

    private void installDecoraters() {
        setUI(new SearchTableUI());
        getTableHeader().setDefaultRenderer(new TableHeaderCellRender());
    }

    public void addResult(File file) {
        listModel.getReadWriteLock().writeLock().lock();
        try {
            listModel.add(file);
        } finally {
            listModel.getReadWriteLock().writeLock().unlock();
        }
    }

    public void addResult(List<File> list) {
        listModel.getReadWriteLock().writeLock().lock();
        try {
            listModel.addAll(list);
        } finally {
            listModel.getReadWriteLock().writeLock().unlock();
        }
    }

    public BasicEventList<File> getListModel() {
        return listModel;
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        if (column == 0 || column ==1) {
            return new LeftTableCellRender();
        }else {
            return new CenterTableCellRender();
        }
    }
}
