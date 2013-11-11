package net.managwire.youtube;

/**
 *
 * @author Thievery
 */
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeQuery.OrderBy;
import com.google.gdata.client.youtube.YouTubeQuery.SafeSearch;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Youtube {

    private final static int MAX_RESULTS = 5;

    public static List getLatestFeed() throws Exception {
        YouTubeService service = new YouTubeService("MangaWire");
        YouTubeQuery query = new YouTubeQuery(new URL("http://gdata.youtube.com/feeds/api/videos"));
        query.setAuthor("Ulquiorra43");
        query.setOrderBy(OrderBy.PUBLISHED);
        query.setSafeSearch(SafeSearch.STRICT);
        VideoFeed feed = service.query(query, VideoFeed.class);
        System.out.println("Number of entries: " + feed.getEntries().size());
        LinkedList<VideoEntry> videoList = new LinkedList<VideoEntry>();
        if (feed.getEntries().size() < MAX_RESULTS) {
            for (int i = 0; i < feed.getEntries().size(); i++) {
                if (feed.getEntries().get(i).getMediaGroup().getYouTubeContents().isEmpty()) {
                    //DO NOTHING
                } else {
                    videoList.add(feed.getEntries().get(i));
                }
            }
        } else {
            for (int i = 0; i < MAX_RESULTS; i++) {
                if (feed.getEntries().get(i).getMediaGroup().getYouTubeContents().isEmpty()) {
                    //DO NOTHING
                } else {
                    videoList.add(feed.getEntries().get(i));
                }
            }
        }
        return videoList;
    }

    public static String getVideoTitle(VideoEntry v) {
        return v.getMediaGroup().getTitle().getPlainTextContent();
    }

    public static String getPlayer(VideoEntry v) {
        if (v.getMediaGroup().getYouTubeContents().isEmpty()) {
            return "http://www.mangawire.net/forums";
        } else {
            String url = v.getMediaGroup().getYouTubeContents().get(0).getUrl();
            if (url.startsWith("http://")) {
                return url;
            } else {
                return "http://www.mangawire.net/forums";
            }
        }
    }

    public static String getThumbNail(VideoEntry v) {
        return v.getMediaGroup().getThumbnails().get(0).getUrl();
    }
}
