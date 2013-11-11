package net.managwire.browser;

import org.mozilla.browser.MozillaExecutor;
import org.mozilla.browser.MozillaPanel;
import org.mozilla.interfaces.nsIDOMEventTarget;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIDOMWindow2;
import org.mozilla.interfaces.nsIWebBrowser;
import static org.mozilla.browser.XPCOMUtils.qi;

/**
 *
 * @author Thievery
 */
public class BrowserUtils {

    public static void installDOMListener(final MozillaPanel moz) {
        MozillaExecutor.mozSyncExec(new Runnable() {

            public void run() {
                nsIWebBrowser webBrowser = moz.getChromeAdapter().getWebBrowser();
                nsIDOMWindow domWin = webBrowser.getContentDOMWindow();
                nsIDOMWindow2 domWin2 = qi(domWin, nsIDOMWindow2.class);
                nsIDOMEventTarget et = domWin2.getWindowRoot();
                DOMListener l = new DOMListener();
                et.addEventListener("click", l, true);
            }
        });
    }
}
