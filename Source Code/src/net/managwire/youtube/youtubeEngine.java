package net.managwire.youtube;

/**
 *
 * @author Thievery
 */
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Observable;

public class youtubeEngine extends Observable implements Runnable {

    private static final String YOUTUBE = "http://gdata.youtube.com/feeds/api/videos";
    private YouTubeService service = new YouTubeService("Search");
    private LinkedList<VideoEntry> videoEntries;
    private YouTubeQuery query;
    private VideoFeed videoFeed;
    private String search;

    public youtubeEngine(String search) {
        this.search = search;
        try {
            query = new YouTubeQuery(new URL(YOUTUBE));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
        query.setFullTextQuery(this.search);
    }

    public void run() {
        try {
            videoFeed = service.query(query, VideoFeed.class);
            videoEntries=(LinkedList)videoFeed.getEntries();
            setChanged();
            notifyObservers();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ServiceException e) {
            e.printStackTrace();
            return;
        }
    }

    public LinkedList<VideoEntry> getResults(){
        return videoEntries;
    }
}
