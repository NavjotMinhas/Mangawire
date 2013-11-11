package net.mangawire.p2p.content;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.util.Enumeration;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.PeerGroup;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.managwire.common.Data;

/**
 *
 * @author Pride
 */
public class BootStrapping {

    public static int TCP_PORT = 9712;
    public static final String Name = "Dave";
    public static final File ConfigFile = new File("." + System.getProperty("file.separator") + Name);

    public static void BootStrap_Network() {
        try {
            NetworkManager manager = new NetworkManager(NetworkManager.ConfigMode.EDGE, "mangawire");
            NetworkConfigurator config = manager.getConfigurator();
            config.setTcpPort(TCP_PORT);
            config.setTcpEnabled(true);
            config.setTcpIncoming(true);
            config.setTcpOutgoing(true);
            URI uri=URI.create("tcp://" + InetAddress.getLocalHost().getHostAddress() + ":" + 9710);
            config.addRdvSeedingURI(uri);
            PeerGroup netPeerGroup = manager.startNetwork();
            if (manager.waitForRendezvousConnection(240000)) {
                BootStrapping.Find_Peergroup(netPeerGroup);
            } else {
                System.exit(-1);
            }
        } catch (IOException e) {
            //TO DO Inform message
        } catch (PeerGroupException e) {
            System.exit(0);
        }
    }

    private static void Find_Peergroup(PeerGroup netPeerGroup) {
        try {
            while (true) {
                DiscoveryService discoveryService = netPeerGroup.getDiscoveryService();
                //only checks the local chache however it will find the peer group if it does not have
                // an advertisement stored in local cache. In order to make it work we need to propegate the message
                Enumeration<Advertisement> list = discoveryService.getLocalAdvertisements(DiscoveryService.GROUP, "Name", "Manga*");
                if (list.hasMoreElements()) {
                    Advertisement adv = list.nextElement();
                    Data.PEER_GROUP = netPeerGroup.newGroup(adv);
                    System.out.println("Successfully Conencted to " + Data.PEER_GROUP.getPeerGroupName());
                    Data.PEER_GROUP.getDiscoveryService().remotePublish(Data.PEER_GROUP.getPeerAdvertisement());
                    //Main.shareFiles(newGroup);
                    break;
                }
                //Stores the advertisments locally
                discoveryService.getRemoteAdvertisements(null, DiscoveryService.GROUP, "Name", "Manga*", 1);
                System.out.println("finding peer group");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PeerGroupException e) {
            e.printStackTrace();
        }
    }
}
