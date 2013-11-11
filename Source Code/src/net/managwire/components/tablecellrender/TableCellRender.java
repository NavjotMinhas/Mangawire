package net.managwire.components.tablecellrender;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Pride
 */
public class TableCellRender extends DefaultTableCellRenderer {

    private static final Color FOREGROUND=new Color(0xffffff);
    private static final Color BACKGROUND=new Color(0x75bae5);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell=super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
        cell.setForeground(FOREGROUND);
        if(isSelected){
            cell.setBackground(BACKGROUND);
        }else{
           cell.setBackground(new Color(0,0,0,0));
        }
        return cell;
    }

}
