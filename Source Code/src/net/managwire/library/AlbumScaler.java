/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.managwire.library;

import net.managwire.common.AlbumDirectory;
import net.managwire.common.MangawireInitializer;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Thievery
 */
public class AlbumScaler {

    public static void imageScaler(File f, String series, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(f);
            BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) buff.getGraphics();
            AffineTransform at = AffineTransform.getScaleInstance((double) width / img.getWidth(), (double) height / img.getHeight());
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.drawRenderedImage(img, at);
            ImageIO.write(buff, "JPG", new File(AlbumDirectory.getDir().getAbsolutePath() + "\\" + f.getName()));
            AlbumDirectory.writeData(AlbumDirectory.getDir().getAbsolutePath() + "\\" + f.getName(), series);
        } catch (IOException e) {
            MangawireInitializer.errorConsole.write(e.getMessage());
            MangawireInitializer.errorConsole.setVisible(true);
            return;
        }
    }
}
