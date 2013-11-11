package net.mangawire.type;

import java.util.ArrayList;
import net.jxta.share.ContentAdvertisement;

/**
 *
 * @author Pride
 */
public class SharedFileType extends Type {

    //The issue number of the Manga ir the episode number of the anime
    private int issueNumber=0;
    //The hash that will be used to verify the file once it is done downloading
    private String hash;
    //The number of peers found with the same advertisment
    private ArrayList<ContentAdvertisement> advertisements=new ArrayList<ContentAdvertisement>();
    //A corrected version of the file name, in a conical string reprsentation.
    private String correctedTitle;

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void addAdvertisement(ContentAdvertisement advertisements) {
        this.advertisements.add(advertisements);
    }

    public void setCorrectedTitle(String correctedTitle) {
        this.correctedTitle = correctedTitle;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public String getHash() {
        return hash;
    }

    public ContentAdvertisement[] getAdvertisements() {
        return advertisements.toArray(new ContentAdvertisement[advertisements.size()]);
    }

    public String getCorrectedTitle() {
        return correctedTitle;
    }

    @Override
    public int getNumOfPeers() {
        return advertisements.size();
    }

    @Override
    public String toString() {
        return getTitle()+" "+"\n#Number of Peers: "+ getNumOfPeers()+"\nHash: "+getHash();
    }



}
