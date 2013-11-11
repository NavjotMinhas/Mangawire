package net.managwire.components.tablecellrender;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Pride
 */
public class LeftTableCellRender extends DefaultTableCellRenderer {

    private static final Color FOREGROUND=new Color(0xffffff);
    private static final Color BACKGROUND=new Color(0x75bae5);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel cell=(JLabel)super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
        cell.setHorizontalAlignment(SwingConstants.LEFT);
        cell.setForeground(FOREGROUND);
        if(isSelected){
            cell.setBackground(BACKGROUND);
        }else{
           cell.setBackground(new Color(0,0,0,0));
        }
        return cell;
    }

}
