package net.managwire.library;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.SwingWorker;
import net.managwire.common.Data;
import net.mangawire.p2p.content.ContentFilter;
import net.mangawire.p2p.content.ContentLanguageFilter;
import net.mangawire.p2p.content.ContentTypeFilter;

/**
 *
 * @author Pride
 */
public class LibraryLoader extends SwingWorker<ArrayList, String> {

    private ArrayList fileList=new ArrayList();

    @Override
    protected ArrayList doInBackground() throws Exception {
        if(!Data.downloadDirectory.exists()){
            Data.downloadDirectory.mkdirs();
        }
        File[] type = Data.downloadDirectory.listFiles(new ContentTypeFilter());
        for (int i = 0; i < type.length; i++) {
            File[] languageFolders = type[i].listFiles(new ContentLanguageFilter());
            for (int x = 0; x < languageFolders.length; x++) {
                File[] files = languageFolders[x].listFiles();
                for (int y = 0; y < files.length; y++) {
                    List list=Arrays.asList(files[y].listFiles(new ContentFilter()));

                    fileList.addAll(list);

                }
            }
        }
        return fileList;
    }

    public static void main(String[]args) throws Exception{
       LibraryLoader loader= new LibraryLoader();
       loader.execute();
       ArrayList list=loader.get();
       for(int i=0;i<list.size();i++){
           System.out.println(list.get(i));
       }
    }
}
