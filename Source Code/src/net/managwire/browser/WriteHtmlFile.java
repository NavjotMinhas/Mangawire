package net.managwire.browser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import net.managwire.common.Data;
import net.mangawire.parser.OneManga;
import net.mangawire.parser.spider.SeriesInfo;

/**
 *
 * @author Pride
 */
public class WriteHtmlFile {

    public static void writeFile(String htmlLink) {
        File file = new File("dependencies/temp_popup.html");
        try {
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
            StringBuilder htmlSource = new StringBuilder();
            SeriesInfo seriesInfo = OneManga.getAllChapters(htmlLink);
            if(seriesInfo==null){
                return;
            }
            for (int i = 0; i < seriesInfo.getChapters().size(); i++) {
                htmlSource.append("<tr>\n<td>");
                htmlSource.append("<a href=\"{search}" + seriesInfo.getChapters().get(i) + "\">" + seriesInfo.getChapters().get(i) + "</a>");
                htmlSource.append("</td>\n</tr>");
            }
            String source = Data.HTML_POPUP_SOURCE.replace("{chapter list};", htmlSource.toString());
            source=source.replace("{Image Logo};", seriesInfo.getImgSrc());
            source=source.replace("{summary};", seriesInfo.getSummary());
            PrintWriter writer = new PrintWriter(file);
            writer.write(source);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getAbsolutePath());
    }
}
