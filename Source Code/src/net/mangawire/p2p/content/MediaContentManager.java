package net.mangawire.p2p.content;

import java.io.File;
import java.io.IOException;
import net.jxta.share.CMS;
import net.jxta.share.Content;
import net.jxta.share.ContentAdvertisement;
import net.jxta.share.ContentManager;

/**
 *
 * @author Pride
 */
public class MediaContentManager {

    private File sharedFolder = null;
    private CMS cms = null;
    private ContentManager contentManager = null;

    //Temporary variable that will be used to store a file hash value
    // Instead ofcreating a new variable each time we will use this one
    // for micro optimization
    private String hashValue;

    private String series;

    private String language;

    private String type;

    private SharedFile file;


    public MediaContentManager(File sharedFolder, CMS cms) {
        // the cms init method should have been called prior to this contructor
        this.sharedFolder = sharedFolder;
        this.cms = cms;
    }
    /*
     *starts the cms and publishes the advertisments of the content in the shared
     * folder during start up.
     */

    public void startSharing() {
        cms.startApp(sharedFolder);
        contentManager = cms.getContentManager();
        File[] type = sharedFolder.listFiles(new ContentTypeFilter());
        for (int i = 0; i < type.length; i++) {
            File[] languageFolders = type[i].listFiles(new ContentLanguageFilter());
            for (int x = 0; x < languageFolders.length; x++) {
                File[] files = languageFolders[x].listFiles();
                for (int y = 0; y < files.length; y++) {
                    publishListFiles(files[y].listFiles(new ContentFilter()));
                }
            }
        }

    }

    private void publishListFiles(File [] files) {
        for (int i = 0; i < files.length; i++) {
            try {
                file=new SharedFile(files[i]);
                hashValue=HashCalculator.computeFileHash(files[i], HashCalculator.SHA_256);
                type=file.getType();
                series=file.getSeries();
                language=file.getLanguage();
                
                // description=hashValue/type/language/series

                contentManager.share(file, hashValue+"/"+type+"/"+language+"/"+series);
                System.out.println(files[i].getName()+" Shared  Hash Value:"+hashValue+"/"+type+"/"+language+"/"+series);
                //Log 4j needed
            } catch (IOException ie) {
                ie.printStackTrace();
                //Log 4j needed
            }
        }
    }

    /**
     * This is mainly called if a new is downloaded file during the application life 
     * cycle which would require us to manually publish the advertisement to the
     * peer group
     * @param sharedFile the new file that needs to be published to the peer group
     */
    public void publishFile(File sharedFile) {
        try {
            contentManager.share(sharedFile);
        } catch (IOException e) {
            e.printStackTrace();
            //Log 4j needed
        }
    }

    /**
     * Removes the content advertisement from the peer group thus not sharing
     * the file no more.
     * @param adv
     */
    public void removePublishing(ContentAdvertisement adv) {
        try {
            contentManager.unshare(adv);
        } catch (IOException e) {
            e.printStackTrace();
            //Log4j needed 
        }
    }
    
        /**
     *  Removes the content advertisement from the peer group thus not sharing
     * the file no more.
     * @param content
     */
    public void removePublishing(Content content) {
        try {
            contentManager.unshare(content);
        } catch (IOException e) {
            e.printStackTrace();
            //Log4j needed 
        }
    }
    /*
     * unshares all the
     */
    public void unshareAll(){
        Content[] content=contentManager.getContent();
        for(int i=0;i<content.length;i++){
            removePublishing(content[i]);
        }
    }

}
