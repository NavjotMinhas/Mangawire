package net.managwire.browser;

import net.managwire.common.Data;
import net.managwire.common.MangawireInitializer;
import org.w3c.dom.Node;
import org.mozilla.dom.NodeFactory;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMEventListener;
import org.mozilla.xpcom.Mozilla;

/**
 *
 * @author Thievery
 */
public class DOMListener implements nsIDOMEventListener {

    public void handleEvent(nsIDOMEvent event) {
        if (event.getCancelable()) {
            Node node = NodeFactory.getNodeInstance(event.getTarget());
            Node Node2 = node.getAttributes().getNamedItem("href");
            String name = Node2.getNodeValue();
            if (name != null && name.startsWith("{search}")) {
                MangawireInitializer.searchPanel.setSearch(name.substring("{search}".length()));
                MangawireInitializer.mainTabs.setSelectedIndex(2);
                event.preventDefault();
                event.stopPropagation();
            } else {
                String textValue = (String) Data.MANGAS.get(node.getTextContent());
                if (textValue == null) {
                    event.preventDefault();
                    event.stopPropagation();
                    return;
                }
                WriteHtmlFile.writeFile(textValue);
            }
            // event.preventDefault();

        }
        //event.stopPropagation();
    }

    public nsISupports queryInterface(String uuid) {
        return Mozilla.queryInterface(this, uuid);
    }
}
