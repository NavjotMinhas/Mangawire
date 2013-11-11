package net.managwire.search.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Pride
 */
public class ImageCellRender extends JComponent implements TableCellRenderer {

    private int value=0;
    private  boolean isSelected=false;
    private static final Color BACKGROUND=new Color(0x75bae5);
    private static Image MUSIC_IMAGE;
    private static Image VIDEO_IMAGE;

    public ImageCellRender() {
        initImage();
    }

    private void initImage(){
        try{
           MUSIC_IMAGE=ImageIO.read(ImageCellRender.class.getResourceAsStream("images/Music.png"));
           VIDEO_IMAGE=ImageIO.read(ImageCellRender.class.getResourceAsStream("images/Movie.png"));
           VIDEO_IMAGE=VIDEO_IMAGE.getScaledInstance(18, 18, Image.SCALE_FAST);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(isSelected){
            g.setColor(BACKGROUND);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(VIDEO_IMAGE, 0, 0, null);
        }else{
            g.drawImage(VIDEO_IMAGE, 0, 0, null);
        }
    }



    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.isSelected=isSelected;
        this.value=0;
        return this;
    }

}
