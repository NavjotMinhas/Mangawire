package net.mangawire.p2p.content;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.share.CMS;
import net.jxta.share.ContentAdvertisement;
import net.jxta.share.ContentManager;
import net.jxta.share.client.ListContentRequest;
import net.managwire.common.Data;
import net.managwire.downloadmanager.DownloadPanel;
import net.mangawire.type.SharedFileType;
import org.jdesktop.swingx.JXFrame;

/**
 *
 * @author Pride
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(HashCalculator.computeStringHash("Hello", HashCalculator.SHA_512));
        try {
            NetworkManager manager = new NetworkManager(NetworkManager.ConfigMode.EDGE, "mangawire");
            NetworkConfigurator configurator = manager.getConfigurator();
            configurator.setName("Nav");
            PeerGroup netPeerGroup = manager.startNetwork();
            //Main.createPeerGroup(netPeerGroup);
            Main.findPeerGroup(netPeerGroup);
            while (true) {
            }
            //manager.stopNetwork();
        } catch (IOException e) {
            //TO DO Inform message
        } catch (PeerGroupException e) {
        }
        /*System.out.println(Main.checkFileExsitance(new File(Data.downloadDirectory.getAbsolutePath()
        + File.separator
        + "Gundam Seed - 30 Flashing Blades - Copy.avi"), 0));*/
    }

    private static String checkFileExsitance(File f, int counter) {
        if (f.exists()) {
            String tempFileName = f.getAbsolutePath();
            int x = tempFileName.lastIndexOf(".");
            String ext = tempFileName.substring(x, tempFileName.length());
            counter++;
            tempFileName = tempFileName.substring(0, x) + "(" + counter + ")" + ext;
            return checkFileExsitance(new File(tempFileName), counter);
        } else {
            return f.getAbsolutePath();
        }
    }

    public static PeerGroup createPeerGroup(PeerGroup netPeerGroup) {
        try {
            ModuleImplAdvertisement adv = netPeerGroup.getAllPurposePeerGroupImplAdvertisement();
            PeerGroup mangaPeerGroup = netPeerGroup.newGroup(IDFactory.newPeerGroupID("Mangagroup".getBytes()), adv, "Mangagroup", "share your files");
            Advertisement mangaGroupAdvertisement = mangaPeerGroup.getPeerGroupAdvertisement();
            DiscoveryService discoveryService = netPeerGroup.getDiscoveryService();
            discoveryService.publish(mangaGroupAdvertisement);
            discoveryService.remotePublish(mangaGroupAdvertisement);
            return mangaPeerGroup;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void findPeerGroup(PeerGroup netPeerGroup) {
        try {
            while (true) {
                DiscoveryService discoveryService = netPeerGroup.getDiscoveryService();
                //only checks the local chache however it will find the peer group if it does not have
                // an advertisement stored in local cache. In order to make it work we need to propegate the message
                Enumeration<Advertisement> list = discoveryService.getLocalAdvertisements(DiscoveryService.GROUP, "Name", "SaEeD*");
                if (list.hasMoreElements()) {
                    Advertisement adv = list.nextElement();
                    Data.PEER_GROUP = netPeerGroup.newGroup(adv);
                    System.out.println("Successfully Conencted to " + Data.PEER_GROUP.getPeerGroupName());
                    Data.PEER_GROUP.getDiscoveryService().remotePublish(Data.PEER_GROUP.getPeerAdvertisement());
                    Main.shareFiles(Data.PEER_GROUP);
                    //Main.getFile(Data.PEER_GROUP);
                    break;
                }
                //Stores the advertisments locally
                discoveryService.getRemoteAdvertisements(null, DiscoveryService.GROUP, "Name", "SaEeD*", 1);
                System.out.println("find peer group");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PeerGroupException e) {
            e.printStackTrace();
        }

    }

    public static void getAllPeers(DiscoveryService service) {
        try {
            //service.getRemoteAdvertisements(null, DiscoveryService.PEER, "Name", "Thievery", 1);
            Enumeration<Advertisement> list = service.getLocalAdvertisements(DiscoveryService.PEER, "Name", "Thievery");
            while (true) {
                System.out.println(list.nextElement().getID());
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getFile(PeerGroup group) {
        ListContentRequest request = new ListContentRequest(group, "Gun*");
        request.activateRequest();
        while (request.getResults().length == 0) {
            try {
                System.out.println("NO RESULTS");
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        downloadFile(group, request.getResults()[0]);

    }

    public static void downloadFile(PeerGroup group, ContentAdvertisement adv) {
        JXFrame frame = new JXFrame("DownloadManager");
        DownloadPanel pane = new DownloadPanel();

        frame.add(pane);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JXFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SharedFileType type = new SharedFileType();
        type.setTitle(adv.getName());
        type.addAdvertisement(adv);
        pane.addDownload(type);
    }

    public static void shareFiles(PeerGroup group) {
        CMS cms = new CMS();
        try {
            cms.init(group, null, null);
        } catch (PeerGroupException e) {
            e.printStackTrace();
        }
        File sharedFileDir = new File("C:/Users/Pride/Documents/BrockU/APCO 2P99/MangaWire");
        if (!sharedFileDir.exists()) {
            sharedFileDir.mkdirs();
        }
        cms.startApp(sharedFileDir);
        ContentManager manager = cms.getContentManager();
        File[] files = sharedFileDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            try {
                if (files[i].isFile()) {
                    manager.share(files[i]);
                    System.out.println("shared");
                }
            } catch (Exception e) {
            }
        }
    }
}
