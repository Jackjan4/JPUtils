/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.janroslan.jputils.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * @author jackjan
 */
public class NetUtils {

    public String downloadHTML(URL url) {
        String result = "";

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
