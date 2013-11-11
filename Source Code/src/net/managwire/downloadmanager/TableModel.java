package net.managwire.downloadmanager;

/**
 *
 * @author Thievery
 */
import javax.swing.table.AbstractTableModel;
import javax.swing.JProgressBar;
import java.util.Observer;
import java.util.Observable;
import java.util.ArrayList;

public class TableModel extends AbstractTableModel implements Observer {

    private String[] ColumnNames = {"File Name", "Status", "Progress", "Size"};
    private Class[] Classes = {String.class, String.class, JProgressBar.class, Integer.class};
    private ArrayList<Download> Downloads = new ArrayList<Download>();
    private Download download;
    public TableModel() {
    }

    public Object getValueAt(int row, int col) {
        Download download = Downloads.get(row);
        switch(col){
            case 0: return download.getFile().getName();
            case 1: return download.getStatus();
            case 2: return download.getProgress();
            case 3: return download.getFile().length();
            default:throw new IllegalStateException();

        }
    }

    public int getRowCount() {
        return Downloads.size();
    }

    public int getColumnCount() {
        return ColumnNames.length;
    }

    public void addDownload(Download download) {
        Downloads.add(download);
        download.addObserver(this);
        this.fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    @Override
    public String getColumnName(int column) {
        return ColumnNames[column];
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return Classes[columnIndex];
    }

    public Download getDownload(int n) {
        return (Download) Downloads.get(n);
    }

    public void update(Observable obs, Object o) {
        int row = Downloads.indexOf(obs);
        this.fireTableCellUpdated(row, 1);
    }
}
