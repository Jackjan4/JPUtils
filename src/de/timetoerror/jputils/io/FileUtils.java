package de.timetoerror.jputils.io;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * @author Jackjan FileUtils.java stellt Methoden bereit um ein
 * Swing-JFileChooser zu öffnen und eine ausgwählte Datei aus diesem auszulesen
 */
public class FileUtils
{

    public FileUtils() {
    }

    // Speichert eine Datei in ein String-Array ein und gibt diesen zurück
    public static ArrayList<String> readFileLines(String filePath) {

        try (FileReader reader = new FileReader((new File(filePath)))){
            return readAllLines(reader);
        } catch (FileNotFoundException ex) {
            System.out.println("");
            return null;
        } catch (IOException ex) {
            return null;
        }
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

    // Speichert eine Datei in ein String-Array ein und gibt diesen zurück
    public static ArrayList<String> readFileLines(File filePath) {

        if (!filePath.exists()) {
            return null;
        }

        try (FileReader r = new FileReader(filePath)){
            return readAllLines(new FileReader(filePath));
        } catch (FileNotFoundException ex) {
            System.out.println("Error: FileNotFoundException occured while reading all lines of a file: " + filePath.getAbsolutePath());
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Returns the files inside a folder with a given ending
     *
     * @param path
     * @param endings
     * @return
     */
    public static ArrayList<File> getFilesInFolder(File path, String... endings) {
        ArrayList<File> files = new ArrayList<>();

        for (File file : path.listFiles()) {
            if (file.isFile()) {
                int i = file.getName().lastIndexOf('.');
                if (i > 0) {
                    for (String s : endings) {
                        if (file.getName().substring(i).equals(s)) {
                            files.add(file);
                            break;
                        }
                    }
                }
            }
        }
        Collections.sort(files);
        return files;
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

    /**
     *
     * @param f
     * @param list
     * @return
     */
    public static boolean writeAllLines(File f, List<String> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            Pattern m = Pattern.compile("(\r)|(\n)");
            for (String s : list) {
                bw.write(m.matcher(s).replaceAll(""));
                bw.newLine();
            }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
}
