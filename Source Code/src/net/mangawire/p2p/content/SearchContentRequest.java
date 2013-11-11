package net.mangawire.p2p.content;

import ca.odell.glazedlists.BasicEventList;
import java.util.Enumeration;
import java.util.HashMap;
import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.peergroup.PeerGroup;
import net.jxta.share.ContentAdvertisement;
import net.jxta.share.ContentAdvertisementImpl;
import net.mangawire.type.SharedFileType;

/**
 *
 * @author Pride
 */
public class SearchContentRequest implements DiscoveryListener, Runnable {

    private static final String ATTRIBUTE = "name";
    private static final int THRESHOLD = 75;
    private HashMap<String, SharedFileType> results = new HashMap<String, SharedFileType>();
    private BasicEventList<SharedFileType> resultList;
    private DiscoveryService service;
    private String searchString;
    private int queryID;

    /**
     *
     * @param peerGroup the peer group that the content is advertised in
     * @param searchString the query string
     * @param resultList the list in which to store all the results. This list
     * will bridge the search function with the gui.
     */
    public SearchContentRequest(PeerGroup peerGroup, String searchString, BasicEventList resultList) {
        this.service = peerGroup.getDiscoveryService();
        if (searchString == null) {
            throw new NullPointerException();
        }
        this.searchString = "*" + searchString + "*";
        this.resultList = resultList;
        init();
    }

    private void init() {
        service.addDiscoveryListener(this);
        //registers the ContentAdvertisement in the advertisment factory
        ContentAdvertisementImpl.registerAdvertisement();
    }

    /**
     * tells the discovery service to find the resource with the given query string.
     */
    public void startSearch() {
        new Thread(this).start();
    }

    public void run() {
        queryID = service.getRemoteAdvertisements(null, DiscoveryService.ADV, ATTRIBUTE, searchString, THRESHOLD);
    }

    /**
     * This method is responsible for making sure that files which have the same
     * hash function are grouped together by adding the advertisement to the same
     * "SharedFileType" object. If a "SharedFileType" object does not exist it will
     * create a new "SharedFileType" object.
     * @param adv an advertisement that was found in the peer group which fits
     * the criteria
     */
    protected synchronized void addResult(ContentAdvertisement adv) {
        String description = adv.getDescription();
        if (description == null) {
            return;
        }
        //TO DO: need more effiecient code here
        int i, x;
        String hashValue, type, language, series;
        i = description.indexOf("/");
        hashValue = description.substring(0, i);
        x = i;
        i = description.indexOf("/", i + 1);
        type = description.substring(x+1, i);
        x = i;
        i = description.indexOf("/", i + 1);
        language = description.substring(x+1, i);
        series = description.substring(i+1);
        SharedFileType entry = results.get(hashValue);
        if (entry == null) {
            SharedFileType newEntry = new SharedFileType();
            newEntry.setTitle(adv.getName());
            newEntry.addAdvertisement(adv);
            newEntry.setHash(hashValue);
            newEntry.setSize(adv.getLength());
            newEntry.setType(type);
            newEntry.setLanguage(language);
            newEntry.setSeries(series);
            results.put(hashValue, newEntry);
            addResult(newEntry);
        } else {
            entry.addAdvertisement(adv);
        }

    }

    /**
     * Adds the entry to the result set in a thread safe way.
     * @param type an entry found by the discovery listener.
     */
    protected void addResult(SharedFileType type) {
        resultList.getReadWriteLock().writeLock().lock();
        try {
            resultList.add(type);
        } finally {
            resultList.getReadWriteLock().writeLock().unlock();
        }
    }

    public void discoveryEvent(DiscoveryEvent event) {
        Enumeration<Advertisement> advertisements = event.getSearchResults();
        //filters the query responses since only one discovery listener can exist,
        //meaning that a discovery listener will show responses from mutiple queries.
        // In order to emilnate this and get the responses neeed, we must compare the query id.
        if (queryID == event.getQueryID()) {
            while (advertisements.hasMoreElements()) {
                try {
                    ContentAdvertisement adv = (ContentAdvertisement) advertisements.nextElement();
                    addResult(adv);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    /**
     * stops the search by removing the discovery listener. Hence stopping any events
     * from being fired.
     */
    public void cancelSearch() {
        service.removeDiscoveryListener(this);
    }

    /**
     * finalizes the search.
     */
    public void finishSearch() {
        cancelSearch();
    }
}
