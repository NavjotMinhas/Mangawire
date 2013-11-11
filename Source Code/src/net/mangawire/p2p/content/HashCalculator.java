package net.mangawire.p2p.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Pride
 */
public class HashCalculator {

    public static final String MD5="MD5";

    public static final String SHA_1="SHA1";

    public static final String SHA_256="SHA-256";

    public static final String SHA_384="SHA-384";

    public static final String SHA_512="SHA-512";
    
    public static final char[] HEX_VALUE_TABLE = {
        '0', '1', '2', '3',
        '4', '5', '6', '7',
        '8', '9', 'a', 'b',
        'c', 'd', 'e', 'f'
    };

    public static String computeFileHash(File file, String hashAlogorithm) {
        try {
            MessageDigest SHA1Hash = MessageDigest.getInstance(hashAlogorithm);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int numOfBytesRead = 0;
            while (true) {
                numOfBytesRead = inputStream.read(buffer);
                if (numOfBytesRead == -1) {
                    break;
                }
                SHA1Hash.update(buffer, 0, numOfBytesRead);
            }
            inputStream.close();
            return convertByteToHex(SHA1Hash.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static String computeStringHash(String string, String hashAlogorithm) {
        try {
            MessageDigest hash = MessageDigest.getInstance(hashAlogorithm);
            hash.update(string.getBytes());
            return convertByteToHex(hash.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String convertByteToHex(byte[] hex) {
        StringBuffer hashValue = new StringBuffer();
        for (int i = 0; i < hex.length; i++) {
            //concatenate the byte to 8 bit value
            int b = hex[i] & 0xff;
            //gets the hex value of the first half
            hashValue.append(HEX_VALUE_TABLE[b >>> 4]);
            //gets the hex value of the second half
            hashValue.append(HEX_VALUE_TABLE[b & 0xf]);
        }
        return hashValue.toString();
    }
}
