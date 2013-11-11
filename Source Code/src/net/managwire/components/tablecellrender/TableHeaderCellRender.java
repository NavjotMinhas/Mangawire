package net.managwire.components.tablecellrender;

import net.managwire.components.label.Label;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Thievery
 */
public class TableHeaderCellRender extends Label implements TableCellRenderer {

    public static final Color BOTTOM_RIGHT_UNSELECTED_BORDER_COLOR = new Color(0x6d695e);
    public static final Color TOP_LEFT_UNSELECTED_BORDER_COLOR = new Color(0xa09a8d);
    public static final Color TOP_UNSELECTED_FILL_COLOR = new Color(0xaaa393);
    public static final Color TOP_UNSELECTED_FILL_COLOR_2 = new Color(0x898477);
    public static final Color BOTTOM_UNSELECTED_FILL_COLOR = new Color(0x7d786c);

    public TableHeaderCellRender() {
        setFont(UIManager.getFont("Table.font").deriveFont(11.0f));
        setHorizontalTextPosition(JLabel.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        Border topLeftBorder = BorderFactory.createMatteBorder(1, 1, 0, 0, TOP_LEFT_UNSELECTED_BORDER_COLOR);
        Border emptyBorder = BorderFactory.createEmptyBorder(2, 4, 0, 4);
        Border bottomRightBorder = BorderFactory.createMatteBorder(0, 0, 1, 1, BOTTOM_RIGHT_UNSELECTED_BORDER_COLOR);
        setBorder(BorderFactory.createCompoundBorder(bottomRightBorder,BorderFactory.createCompoundBorder(topLeftBorder,emptyBorder)));
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0,
                getHeight(), new float[]{0.0f,0.499f,0.5f},new Color[]{TOP_UNSELECTED_FILL_COLOR,TOP_UNSELECTED_FILL_COLOR_2,BOTTOM_UNSELECTED_FILL_COLOR});
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        super.paintComponent(g);
    }
}
