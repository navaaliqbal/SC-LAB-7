package lab7;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecursiveFileSearch {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java RecursiveFileSearch <directoryPath> <caseSensitive(true/false)> <fileName1> [<fileName2> ...]");
            return;
        }

        String directoryPath = args[0];
        boolean isCaseSensitive = Boolean.parseBoolean(args[1]);
        List<String> searchFileNames = new ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            searchFileNames.add(args[i]);
        }

        try {
            File startDirectory = new File(directoryPath);
            if (!startDirectory.exists() || !startDirectory.isDirectory()) {
                System.out.println("The specified path does not exist or is not a directory.");
                return;
            }

            Map<String, Integer> fileCounts = new HashMap<>();
            for (String fileName : searchFileNames) {
                fileCounts.put(fileName, 0);
            }

            findFiles(startDirectory, searchFileNames, isCaseSensitive, fileCounts);

            // Display search results
            for (String fileName : searchFileNames) {
                int occurrences = fileCounts.get(fileName);
                if (occurrences > 0) {
                    System.out.println("File '" + fileName + "' found " + occurrences + " times.");
                } else {
                    System.out.println("File '" + fileName + "' was not found.");
                }
            }

        } catch (SecurityException se) {
            System.out.println("Error: Insufficient permissions to access the directory.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    public static void findFiles(File directory, List<String> searchFileNames, boolean isCaseSensitive, Map<String, Integer> fileCounts) {
        if (directory == null || searchFileNames == null || fileCounts == null) {
            throw new IllegalArgumentException("Directory, file names list, and counts map cannot be null.");
        }

        File[] filesInDirectory = directory.listFiles();
        if (filesInDirectory == null) {
            return;
        }

        for (File file : filesInDirectory) {
            try {
                if (file.isDirectory()) {
                    findFiles(file, searchFileNames, isCaseSensitive, fileCounts);
                } else {
                    for (String fileName : searchFileNames) {
                        if (isMatchingFileName(file.getName(), fileName, isCaseSensitive)) {
                            System.out.println("File found at: " + file.getAbsolutePath());
                            fileCounts.put(fileName, fileCounts.get(fileName) + 1);
                        }
                    }
                }
            } catch (SecurityException se) {
                System.out.println("Unable to access file: " + file.getAbsolutePath());
            }
        }
    }

    private static boolean isMatchingFileName(String currentFileName, String targetFileName, boolean isCaseSensitive) {
        if (isCaseSensitive) {
            return currentFileName.equals(targetFileName);
        } else {
            return currentFileName.equalsIgnoreCase(targetFileName);
        }
    }
}
