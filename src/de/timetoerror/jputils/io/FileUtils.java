package de.timetoerror.jputils.io;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

/**
 * @author Jackjan FileUtils.java stellt Methoden bereit um ein
 * Swing-JFileChooser zu öffnen und eine ausgwählte Datei aus diesem auszulesen
 */
public class FileUtils {

    public FileUtils() {
    }

    /**
     * Deletes a directory with also deleting the subfolders with their items
     *
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String children1 : children) {
                boolean success = deleteDir(new File(dir, children1));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete(); // The directory is empty now or is a file and can be deleted.
    }

    /**
     * Reads all lines from a file which is available as its byte-Array
     * representation
     *
     * @param file - The file which should be read. This file must be a text
     * file.
     * @return
     */
    public static ArrayList<String> readAllLines(byte[] file) {

        return readAllLines(new InputStreamReader(new ByteArrayInputStream(file)));
    }

    private static ArrayList<String> readAllLines(Reader r) {
        ArrayList<String> lines = new ArrayList<>();
        Pattern m = Pattern.compile("(\r)|(\n)");

        try (BufferedReader br = new BufferedReader(r)) {
            // Iteration über alle Zeilen der Datei
            String line = "";
            while ((line = br.readLine()) != null) {
                lines.add(m.matcher(line).replaceAll(""));
            }
            // Wenn mehr als 0 Elemente nach der Iteration in der Liste sind,
            // wird die Liste im Ergebnisarray gespeichert
            if (lines.isEmpty()) {

            }

        } catch (IOException ex) {
            System.out.println("IO Exception: " + ex.getMessage());
            ex.printStackTrace();
        }

        return lines;
    }

    /**
     * Returns the files inside a folder with a given ending
     *
     * @param path
     * @param endings
     * @return
     */
    public static ArrayList<File> getFilesInFolder(File path, String... endings) {
        ArrayList<File> result = new ArrayList<>();

        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    int i = file.getName().lastIndexOf('.');
                    if (i > 0) {
                        for (String s : endings) {
                            if (file.getName().substring(i).equals(s)) {
                                result.add(file);
                                break;
                            }
                        }
                    }
                }
            }
            
            
        }
        Collections.sort(result);
        return result;
    }

    /**
     *
     * @param dir
     * @return
     */
    public static ArrayList<File> getSubFolder(File dir) {

        ArrayList<File> result = new ArrayList<File>();

        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                result.add(f);
            }
        }

        return result;
    }

    public static void writeAllLines(File f, ObservableList<Text> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            for (Text t : list) {
                bw.write(t.getText());
                bw.newLine();
            }

        } catch (IOException ex) {
            
        }
    }
}
