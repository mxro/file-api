package de.mxro.file.internal.jre;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Based on: http://www.rgagnon.com/javadetails/java-0416.html
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
public class Md5 {

    public static byte[] createChecksum(final File file) throws Exception {
        final InputStream fis = new FileInputStream(file);

        final byte[] buffer = new byte[1024];
        final MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }

    public static String getMD5Checksum(final File file) throws Exception {
        final byte[] b = createChecksum(file);
        String result = "";

        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

}