/*
 * Copyright 2016 - Jan-Philipp Roslan - Alle Rechte vorbehalten
 */
package de.timetoerror.jputils.jfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.awt.image.IntegerComponentRaster;

/**
 * Utility methods for JavaFX applications
 *
 * @author Jackjan
 */
public class FXUtils {

    /**
     * Opens a new stage with a given view. Returns the view-controller for
     * further interaction or null if no controller is available for the window.
     *
     * @param <T>
     * @param fxmlPath - The path to the .FXML file
     * @param stylesheet - The path for the CSS stylesheet file
     * @param title - The title of the window if the system uses a window system
     * @param pStage - The stage on which the window should be displayed
     * @param icon - The icon of the window (and taskbar) when the system uses a
     * window system
     * @param style
     * @return 
     */
    public static <T> T openWindow(URL fxmlPath, String stylesheet, String title, Stage pStage, String icon, StageStyle style) {

        
        // Call unload from old controller to make last executes
        
        // Set stage
        Stage stage;
        if (pStage == null) {
            stage = new Stage();
            stage.centerOnScreen();
        } else {
            stage = pStage;
        }

        // If icon available, set it
        if (icon != null) {
            stage.getIcons().add(new Image(icon));
        }

        // Creating the window
        FXMLLoader loader = null;
        try {
            // Load FXML
            String path = fxmlPath.toExternalForm();
            URL url = new URL(path);
            loader = new FXMLLoader(url);
            Parent root = (Parent) loader.load();
            
            // Set StageStyle
            if (pStage == null && style != null)
            {
                stage.initStyle(style);
            }

            Scene scene = new Scene(root);
            
            // Execute stylesheet on new window
            if (stylesheet != null && !stylesheet.equals("")) {
                scene.getStylesheets().add(stylesheet);
            }

            // Show scene
            stage.setScene(scene);
            stage.setTitle(title);

            if (pStage == null) {
                stage.show();
            }

        } catch (IOException ex) {
            System.out.println("Error: FXML could not be opened: " + fxmlPath);
        }

        // Use AdvancedController (JPUtils) features if available
        if (loader.getController() != null && loader.getController() instanceof AdvancedController) {
            ((AdvancedController) loader.getController()).onInitFinished();
        }

        return loader.getController();
    }

    
    
    /**
     * Opens a new stage with a given view. Returns the view-controller for
     * further interaction or null if no controller is available for the window.
     *
     * @param fxmlPath - The path to the .FXML file
     * @param stylesheet - The path for the CSS stylesheet file
     * @param title - The title of the window if the system uses a window system
     * @param pStage - The stage on which the window should be displayed
     * @param icon - The icon of the window (and taskbar) when the system uses a
     * window system
     */
    public static <T> T openWindow(URL fxmlPath, String stylesheet, String title, Stage pStage, String icon) {

        return openWindow(fxmlPath, stylesheet, title, pStage, icon, null);
    }
    
    /**
     * Opens a new stage with a given view. Returns the view-controller for
     * further interaction or null if no controller is available for the window.
     *
     * @param <T>
     * @param fxmlPath - The path to the .FXML file
     * @param stylesheet - The path for the CSS stylesheet file
     * @param title - The title of the window if the system uses a window system
     * @param pStage - The stage on which the window should be displayed
     * @return 
     */
    public static <T> T openWindow(URL fxmlPath, String stylesheet, String title, Stage pStage) {

        return openWindow(fxmlPath, stylesheet, title, pStage, null, null);
    }
    
    
    /**
     * Converts a BufferedImage to an JavaFX Image
     *
     * @param bimg
     * @return
     */
    public static Image toFXImage(BufferedImage bimg) {
        WritableImage wimg = null;

        int bw = bimg.getWidth();
        int bh = bimg.getHeight();
        switch (bimg.getType()) {
            case BufferedImage.TYPE_INT_ARGB:
            case BufferedImage.TYPE_INT_ARGB_PRE:
                break;
            default:
                BufferedImage converted = new BufferedImage(bw, bh, BufferedImage.TYPE_INT_ARGB_PRE);
                Graphics2D g2d = converted.createGraphics();
                g2d.drawImage(bimg, 0, 0, null);
                g2d.dispose();
                bimg = converted;
                break;
        }

        wimg = new WritableImage(bw, bh);

        PixelWriter pw = wimg.getPixelWriter();
        IntegerComponentRaster icr = (IntegerComponentRaster) bimg.getRaster();
        int data[] = icr.getDataStorage();
        int offset = icr.getDataOffset(0);
        int scan = icr.getScanlineStride();
        PixelFormat<IntBuffer> pf = (bimg.isAlphaPremultiplied()
                ? PixelFormat.getIntArgbPreInstance()
                : PixelFormat.getIntArgbInstance());
        pw.setPixels(0, 0, bw, bh, pf, data, offset, scan);
        return wimg;
    }

    /**
     * Returns all children elements of a Node
     *
     * @param parent
     * @return
     */
    public static ArrayList<Node> getAllChildren(Parent parent) {
        // Creating a result ArrayList
        // There will probably 5 Nodes minimum in the scene
        ArrayList<Node> result = new ArrayList<>(5);

        getChildren(result, parent);

        return result;
    }

    /**
     * Is used for the getAllChildren(...) method. Returns all children of a
     * node by calling itself recursivly for every parent node
     *
     * @param result
     * @param parent
     */
    private static void getChildren(ArrayList<Node> result, Parent parent) {
        result.addAll(parent.getChildrenUnmodifiable());

        for (Node n : parent.getChildrenUnmodifiable()) {
            if (n instanceof Parent) {
                getChildren(result, (Parent) n);
            }
        }
    }

    private static MeshView createTile2D(int length, Image texture) {
        TriangleMesh triMesh = new TriangleMesh();
        triMesh.getTexCoords().addAll(
                0, 0,
                0, 1,
                1, 1,
                1, 0);

        triMesh.getPoints().addAll(
                0, 0, 0, // 0
                0, length, 0, // 1
                length, length, 0, // 2
                length, 0, 0); // 3

        triMesh.getFaces().addAll(
                0, 0, 1, 1, 3, 3,
                3, 3, 1, 1, 2, 2);

        MeshView meshView = new MeshView(triMesh);
        meshView.setDrawMode(DrawMode.FILL);
        PhongMaterial mat = new PhongMaterial(Color.WHITE);
        mat.setDiffuseMap(texture);
        meshView.setMaterial(mat);

        return meshView;
    }
}
