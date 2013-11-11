package net.managwire.browser;

import net.managwire.common.MangawireInitializer;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import org.mozilla.browser.MozillaConfig;
import org.mozilla.browser.MozillaInitialization;

/**
 *
 * @author Thievery
 */
public class BrowserInitializer {

    public static void initializeXULRunner() {
        File XULRunner = new File("browser\\xulrunner\\xulrunner.exe");
        System.out.println(XULRunner.getAbsolutePath());
        if (!(XULRunner.getParentFile().exists())) {
            setupXULRunnner();
        } else if (!(XULRunner.exists())) {
            deleteDirectory(XULRunner.getParentFile().getParentFile());
            setupXULRunnner();
        } else {
            setupXULRunnner();
        }
    }

    private static void deleteDirectory(File directory) {
        if (directory.isFile()) {
            System.err.println("The specified file is a directory");
            return;
        }
        File[] Content = directory.listFiles();
        for (int i = 0; i < Content.length; i++) {
            if (Content[i].isDirectory()) {
                deleteDirectory(Content[i]);
            } else {
                Content[i].delete();
            }
        }

        directory.delete();
    }

    private static void setupXULRunnner() {
        File XULRunner = new File("browser\\xulrunner");
        if (!XULRunner.exists()) {
            XULRunner.mkdirs();
            try {
                ZipInputStream XULRunnerZip = new ZipInputStream(
                        new FileInputStream(new File("XULRunner.zip")));
                int NumberOfBytes;
                byte[] buffer = new byte[1024];
                ZipEntry FileName;
                while ((FileName = XULRunnerZip.getNextEntry()) != null) {
                    File temp = new File(XULRunner.getAbsolutePath(),
                            FileName.getName());
                    System.out.println(temp.getName());
                    System.out.println(temp.getParentFile().exists());
                    System.out.println(FileName.isDirectory());
                    if (!temp.getParentFile().exists()) {
                        temp.getParentFile().mkdir();
                    }
                    if (!FileName.isDirectory()) {
                        FileOutputStream fos = new FileOutputStream(temp);
                        while ((NumberOfBytes = XULRunnerZip.read(buffer, 0, 1024)) > -1) {
                            fos.write(buffer, 0, NumberOfBytes);
                        }
                        fos.close();
                    }
                    XULRunnerZip.closeEntry();
                }
                XULRunnerZip.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String Path = System.getProperty("java.library.path") + File.pathSeparator + XULRunner.getAbsolutePath();
        System.setProperty("java.library.path", Path);

        File Profile = new File("profile");
        if (!Profile.exists()) {
            Profile.mkdirs();
        }
        MozillaConfig.setProfileDir(Profile);
        MozillaConfig.setXULRunnerHome(new File(XULRunner.getAbsolutePath()));
        MozillaInitialization.initialize();
    }
}
