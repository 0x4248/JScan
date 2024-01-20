/**
 * JScan
 * A MalwareBazaar hash scanner that scans a directory for files that match SHA256 hashes in the MalwareBazaar database.
 * GitHub: https://www.github.com/lewisevans2007/JScan
 * Licence: GNU General Public License v3.0
 * By: Lewis Evans
 */

package com.github.lewisevans2007;

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