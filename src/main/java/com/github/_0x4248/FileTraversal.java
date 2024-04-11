/**
 * JScan
 * A MalwareBazaar hash scanner that scans a directory for files that match SHA256 hashes in the MalwareBazaar database.
 * GitHub: https://www.github.com/0x4248/JScan
 * Licence: GNU General Public License v3.0
 * By: 0x4248
 */

package com.github._0x4248;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTraversal {
    public static String[] getFilePaths(String directory) {
        List<String> filePaths = new ArrayList<String>();
        File[] files = new File(directory).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                filePaths.add(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                String[] subDirectoryFiles = getFilePaths(file.getAbsolutePath());
                for (String subDirectoryFile : subDirectoryFiles) {
                    filePaths.add(subDirectoryFile);
                }
            }
        }

        return filePaths.toArray(new String[filePaths.size()]);
    }
}