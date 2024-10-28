package lab7;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RecursiveFileSearchTest {

    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();

    // Test Case 1: Path to an empty directory
    @Test
    public void testDirectoryWithoutFiles() throws IOException {
        File emptyDirectory = tempDir.newFolder("emptyDirectory");

        List<String> targetFiles = new ArrayList<>();
        targetFiles.add("sampleFile.txt");
        Map<String, Integer> fileOccurrences = new HashMap<>();
        fileOccurrences.put("sampleFile.txt", 0);

        RecursiveFileSearch.findFiles(emptyDirectory, targetFiles, false, fileOccurrences);

        assertEquals("File should not be found in an empty directory", 
                0, fileOccurrences.get("sampleFile.txt").intValue());
    }

    // Test Case 2: Directory path that does not exist
    @Test
    public void testDirectoryNotFound() {
        File missingDirectory = new File("missingDirectory");

        List<String> targetFiles = new ArrayList<>();
        targetFiles.add("sampleFile.txt");
        Map<String, Integer> fileOccurrences = new HashMap<>();
        fileOccurrences.put("sampleFile.txt", 0);

        RecursiveFileSearch.findFiles(missingDirectory, targetFiles, false, fileOccurrences);

        assertEquals("Non-existent directory should return zero occurrences",
                0, fileOccurrences.get("sampleFile.txt").intValue());
    }

    // Test Case 3: Valid directory with nested subdirectories and the file being present
    @Test
    public void testFileInNestedDirectory() throws IOException {
        File mainDirectory = tempDir.newFolder("mainDirectory");
        File innerDirectory = new File(mainDirectory, "innerDirectory");
        innerDirectory.mkdir();
        File sampleFile = new File(innerDirectory, "sampleFile.txt");
        assertTrue("File creation failed", sampleFile.createNewFile());

        List<String> targetFiles = new ArrayList<>();
        targetFiles.add("sampleFile.txt");
        Map<String, Integer> fileOccurrences = new HashMap<>();
        fileOccurrences.put("sampleFile.txt", 0);

        RecursiveFileSearch.findFiles(mainDirectory, targetFiles, false, fileOccurrences);

        assertEquals("File should be found in the nested directory", 
                1, fileOccurrences.get("sampleFile.txt").intValue());
    }
}
