/**
 * JScan
 * A MalwareBazaar hash scanner that scans a directory for files that match SHA256 hashes in the MalwareBazaar database.
 * GitHub: https://www.github.com/0x4248/JScan
 * Licence: GNU General Public License v3.0
 * By: 0x4248
 */

package com.github.0x4248;

import java.io.*;
import java.util.ArrayList;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String csvFileLocation = "full.csv";
        String startLocation = "";
        for (String arg : args) {
            if (arg.startsWith("-s=")) {
                startLocation = arg.substring(3);
            }
        }

        if (startLocation.equals("")) {
            System.out.println("Please specify a start location with -s=");
            System.exit(1);
        }

        ArrayList<String> sha256Hashes = new ArrayList<String>();
        ArrayList<String> signatures = new ArrayList<String>();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFileLocation));
            String row;
            while ((row = csvReader.readLine()) != null) {
                if (row.startsWith("#")) {
                    continue;
                }
                String[] data = row.replace("\"", "").split(",");
                try {
                    sha256Hashes.add(data[1].replace(" ", ""));
                    signatures.add(data[8]);

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("There was a problem with the CSV file skipping this line");
                }
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] filePaths = FileTraversal.getFilePaths(startLocation);

        for (String filePath : filePaths) {
            try {
                String hash = Hasher.hashFile(filePath);
                if (sha256Hashes.contains(hash)) {
                    System.out.println("Found a match for " + filePath);
                    System.out.println("Signature: " + signatures.get(sha256Hashes.indexOf(hash)));
                } else {
                    System.out.println("No match for " + filePath);
                }
            } catch (Exception e) {
                System.out.println("There was a problem hashing " + filePath);
            }
        }
    }
}