/**
 * JScan
 * A MalwareBazaar hash scanner that scans a directory for files that match SHA256 hashes in the MalwareBazaar database.
 * GitHub: https://www.github.com/0x4248/JScan
 * Licence: GNU General Public License v3.0
 * By: 0x4248
 */

package com.github.0x4248;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    public static String hashFile(String filePath) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(filePath);
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        
        byte[] mdbytes = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        fis.close();
        return sb.toString();
    }
}
