/*
 * Copyright 2016 - Jan-Philipp Roslan - Alle Rechte vorbehalten
 */
package de.janroslan.jputils.jfx;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
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
     * @param stylesheet - (optional) The path for the CSS stylesheet file -
     * null, if you don't need it
     * @param title - (optional) The title of the window if the system uses a
     * window system
     * @param pStage - (option) The stage on which the window should be
     * displayed
     * @param icon - (optional) The icon of the window (and taskbar) when the
     * system uses a window system
     * @param bundle - (optional)
     * @param stagestyle - (optional) Window style if the system uses a window
     * system
     * @param owner
     * @param modality
     * @return
     */
    public static <T> T openWindow(URL fxmlPath, String stylesheet, String title, Stage pStage, String icon, ResourceBundle bundle, StageStyle stagestyle, Window owner, Modality modality) {

        // Call unload from old controller to make last executes
        // Set stage
        Stage stage;
        if (pStage == null) {
            stage = new Stage();

            if (modality != null) {
                stage.initModality(modality);
            }
            if (owner != null) {
                stage.initOwner(owner);
            }

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

            // Set ResourceBundle
            if (bundle != null) {
                loader.setResources(bundle);
            }

            Parent root = (Parent) loader.load();

            // Set StageStyle
            if (pStage == null && stagestyle != null) {
                stage.initStyle(stagestyle);
            }

            Scene scene = new Scene(root);

            // Execute stylesheet on new window
            if (stylesheet != null && !stylesheet.equals("")) {
                scene.getStylesheets().add(stylesheet);
            }

            // Show scene
            stage.setScene(scene);
            stage.setTitle(title);

            if (pStage == null || !pStage.isShowing()) {
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


    public static FXMLPackage loadFXML(URL fxmlPath) {
        Parent result = null;
        FXMLLoader loader = new FXMLLoader(fxmlPath);

        try {
            result = (Parent) loader.load();
            // Use AdvancedController (JPUtils) features if available
            if (loader.getController() != null && loader.getController() instanceof AdvancedController) {
                ((AdvancedController) loader.getController()).onInitFinished();
            }

        } catch (IOException ex) {

        }

        return new FXMLPackage(result, loader.getController());
    }


    /**
     * Converts a BufferedImage to an JavaFX Image
     *
     * @param bimg
     * @param wimg
     * @return
     */
    public static Image toFXImage(BufferedImage bimg, WritableImage wimg) {
        int bw = bimg.getWidth();
        int bh = bimg.getHeight();
        switch (bimg.getType()) {
            case BufferedImage.TYPE_INT_ARGB:
            case BufferedImage.TYPE_INT_ARGB_PRE:
                break;
            default:
                BufferedImage converted
                        = new BufferedImage(bw, bh, BufferedImage.TYPE_INT_ARGB_PRE);
                Graphics2D g2d = converted.createGraphics();
                g2d.drawImage(bimg, 0, 0, null);
                g2d.dispose();
                bimg = converted;
                break;
        }
        // assert(bimg.getType == TYPE_INT_ARGB[_PRE]);
        if (wimg != null) {
            int iw = (int) wimg.getWidth();
            int ih = (int) wimg.getHeight();
            if (iw < bw || ih < bh) {
                wimg = null;
            } else if (bw < iw || bh < ih) {
                int empty[] = new int[iw];
                PixelWriter pw = wimg.getPixelWriter();
                PixelFormat<IntBuffer> pf = PixelFormat.getIntArgbPreInstance();
                if (bw < iw) {
                    pw.setPixels(bw, 0, iw - bw, bh, pf, empty, 0, 0);
                }
                if (bh < ih) {
                    pw.setPixels(0, bh, iw, ih - bh, pf, empty, 0, 0);
                }
            }
        }
        if (wimg == null) {
            wimg = new WritableImage(bw, bh);
        }
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


    public static BufferedImage toBufferedImage(Image img) {
        PixelReader pr = img.getPixelReader();
        if (pr == null) {
            return null;
        }
        int iw = (int) img.getWidth();
        int ih = (int) img.getHeight();
        int prefBimgType = getBestBufferedImageType(pr.getPixelFormat(), null);

        BufferedImage bimg = new BufferedImage(iw, ih, prefBimgType);

        IntegerComponentRaster icr = (IntegerComponentRaster) bimg.getRaster();
        int offset = icr.getDataOffset(0);
        int scan = icr.getScanlineStride();
        int data[] = icr.getDataStorage();
        WritablePixelFormat<IntBuffer> pf = getAssociatedPixelFormat(bimg);
        pr.getPixels(0, 0, iw, ih, pf, data, offset, scan);
        return bimg;
    }


    private static int getBestBufferedImageType(PixelFormat<?> fxFormat, BufferedImage bimg) {
        if (bimg != null) {
            int bimgType = bimg.getType();
            if (bimgType == BufferedImage.TYPE_INT_ARGB
                    || bimgType == BufferedImage.TYPE_INT_ARGB_PRE) {
                // We will allow the caller to give us a BufferedImage
                // that has an alpha channel, but we might not otherwise
                // construct one ourselves.
                // We will also allow them to choose their own premultiply
                // type which may not match the image.
                // If left to our own devices we might choose a more specific
                // format as indicated by the choices below.
                return bimgType;
            }
        }
        switch (fxFormat.getType()) {
            default:
            case BYTE_BGRA_PRE:
            case INT_ARGB_PRE:
                return BufferedImage.TYPE_INT_ARGB_PRE;
            case BYTE_BGRA:
            case INT_ARGB:
                return BufferedImage.TYPE_INT_ARGB;
            case BYTE_RGB:
                return BufferedImage.TYPE_INT_RGB;
            case BYTE_INDEXED:
                return (fxFormat.isPremultiplied()
                        ? BufferedImage.TYPE_INT_ARGB_PRE
                        : BufferedImage.TYPE_INT_ARGB);
        }
    }


    private static WritablePixelFormat<IntBuffer> getAssociatedPixelFormat(BufferedImage bimg) {
        switch (bimg.getType()) {
            // We lie here for xRGB, but we vetted that the src data was opaque
            // so we can ignore the alpha.  We use ArgbPre instead of Argb
            // just to get a loop that does not have divides in it if the
            // PixelReader happens to not know the data is opaque.
            case BufferedImage.TYPE_INT_RGB:
            case BufferedImage.TYPE_INT_ARGB_PRE:
                return PixelFormat.getIntArgbPreInstance();
            case BufferedImage.TYPE_INT_ARGB:
                return PixelFormat.getIntArgbInstance();
            default:
                // Should not happen...
                throw new InternalError("Failed to validate BufferedImage type");
        }
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
