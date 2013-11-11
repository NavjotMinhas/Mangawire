package net.managwire.downloadmanager;

import java.io.File;
import java.util.Observable;
import net.jxta.share.client.GetContentRequest;
import net.managwire.common.Data;
import net.mangawire.type.SharedFileType;
import net.mangawire.type.Type;

/**
 *
 * @author Pride
 */
public class Download extends Observable implements Runnable {

    private int progress;
    private SharedFileType fileInformation;
    private Thread thread;
    private GetContentRequest request;
    private String fileName;
    private File file;

    public Download(Type fileInformation) {
        this.fileInformation = (SharedFileType) fileInformation;
        initDownload();
    }

    private void initDownload() {
        fileName = checkFileExsitance(new File(Data.downloadDirectory.getAbsolutePath()
                + File.separator
                + fileInformation.getType()
                + File.separator
                + fileInformation.getLanguage()
                + File.separator
                + fileInformation.getSeries()
                + File.separator
                + fileInformation.getTitle()), 0);
        file = new File(fileName);

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
            f.getParentFile().mkdirs();
            return f.getAbsolutePath();
        }
    }

    public int getProgress() {
        return progress;
    }

    public void startDownload() {
        thread = new Thread(this);
        thread.start();
    }

    public GetContentRequest getRequest() {
        return request;
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return file;
    }

    public String getStatus() {
        if (request != null) {
            if (request.isDone()) {
                return "Done";
            } else if (request.hasFailed()) {
                return "Error";
            }
            return "Downloading";
        } else {
            return "Starting Dowload";
        }
    }

    public void run() {
        request = new GetContentRequest(Data.PEER_GROUP, fileInformation.getAdvertisements(), file);
        int x = 0;
        while (!request.isDone()) {
            x = request.getPercentDone();
            if (request.hasFailed()) {
                break;
            }
            if (progress != x) {
                progress = x;
                statusChanged();
            }
        }
    }

    private void statusChanged() {
        setChanged();
        notifyObservers();

    }
}
