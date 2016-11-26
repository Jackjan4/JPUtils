/*
 * Copyright 2016, Jan-Philipp Roslan, Alle Rechte vorbehalten
 */
package de.timetoerror.jputils.crypt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 *
 * @author Jackjan
 */
public class HashUtils
{

    /**
     * Returns the MD5 checksum of a file Returns null if the checksum could not
     * be created
     *
     * @param file
     * @return
     */
    public static String MD5Checksum(File file) {

        if (!file.exists()) {
            return null;
        }
        
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] input = Files.readAllBytes(Paths.get(file.toURI()));
            byte[] res = digest.digest(input);

            result = new HexBinaryAdapter().marshal(res);

        } catch (NoSuchAlgorithmException ex) {
        } catch (IOException ex) {
            System.out.println("Error: IOException occured while trying to create a Hash of a file. Maybe wrong filepath? : " + file.toString());
            ex.printStackTrace();
        }

        return result;
    }

    
    /**
     * Gets the hash of an input byte array with the specified hashing algorithm
     * @param input
     * @param algo
     * @return The hash result as String in hex
     */
    public static String hash(byte[] input, String algo) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(algo);
            byte[] res = digest.digest(input);

            result = new HexBinaryAdapter().marshal(res);

        } catch (NoSuchAlgorithmException ex) {

        }

        return result;
    }

    public static String MD5Checksum(String filePath) {
        return MD5Checksum(new File(filePath));
    }
}
