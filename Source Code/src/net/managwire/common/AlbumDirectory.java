package net.managwire.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Thievery
 */
public class AlbumDirectory {

    private static File ALUBUMDIRECTORY;
    private static File ALBUMDATA;
    private static final String ALBUMDATA_DATANAME = "ALBUMDATA.DAT";
    private static final String ALBUMDATA_TEMP_DATANAME = "ALBUMDATA_1.DAT";

    //TO DO: NEEDS BLOWFISH ENCRYPTION METHOD IN ORDER TO ENCRYPT THE DATA
    // ALSO NEEDS AN ALBUMDATA PARSER IT MUST ALSO DELETE REPEATED SERIES NAMES
    //AND DELETE PRINT LINES
    public static File createDir() {
        if (ALUBUMDIRECTORY == null) {
            ALUBUMDIRECTORY = new File(System.getProperty("user.home") + "\\MangaWire\\Album");
        }
        if (!ALUBUMDIRECTORY.exists()) {
            ALUBUMDIRECTORY.mkdirs();
        }
        return ALUBUMDIRECTORY;
    }

    public static File getDir() {

        return createDir();
    }

    public static File createData() {
        if (ALBUMDATA == null) {
            ALBUMDATA = new File(System.getProperty("user.home") + "\\MangaWire\\Album\\" + ALBUMDATA_DATANAME);
        }
        if (!ALBUMDATA.exists()) {
            try {
                ALBUMDATA.createNewFile();
                System.out.println("true");
            } catch (IOException e) {
                MangawireInitializer.errorConsole.write(e.getMessage());
                MangawireInitializer.errorConsole.setVisible(true);
            }
        }
        return ALBUMDATA;
    }

    public static File getData() {

        return createData();
    }

    public static void writeData(String Album, String Series) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        if (ALBUMDATA == null) {
            createData();
        }
        File fileTemp = new File(System.getProperty("user.home") + "\\MangaWire\\Album\\" + ALBUMDATA_TEMP_DATANAME);
        try {
            if (!fileTemp.exists()) {
                System.out.println("true");
                fileTemp.createNewFile();
            }
            reader = new BufferedReader(new FileReader(ALBUMDATA));
            writer = new PrintWriter(new FileWriter(fileTemp));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                System.out.println(temp);
                writer.println(temp);
            }
            writer.println("file:" + Album + "," + Series);
            reader.close();
            writer.close();
            writeDataBack(fileTemp);

        } catch (IOException e) {
            MangawireInitializer.errorConsole.write(e.getMessage());
            MangawireInitializer.errorConsole.setVisible(true);
        }
    }

    private static void writeDataBack(File f) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            if (!f.exists()) {
                System.out.println("true");
                f.createNewFile();
            }
            reader = new BufferedReader(new FileReader(f));
            writer = new PrintWriter(new FileWriter(ALBUMDATA));
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                System.out.println(temp);
                writer.println(temp);
            }
            reader.close();
            writer.close();
            f.delete();
        } catch (IOException e) {
            MangawireInitializer.errorConsole.write(e.getMessage());
            MangawireInitializer.errorConsole.setVisible(true);
        }
    }
}
