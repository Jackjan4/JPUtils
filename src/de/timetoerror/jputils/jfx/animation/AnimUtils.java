/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.timetoerror.jputils.jfx.animation;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author jackjan
 */
public class AnimUtils {

    
    public static void hFadeOut(Node n, double x, Duration d, EventHandler handler) {
        TranslateTransition tT = new TranslateTransition(d, n);
        tT.setToX(x);

        FadeTransition ft = new FadeTransition(d, n);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(
                tT,
                ft
        );

        if (handler != null) {
            pt.setOnFinished(handler);
        }

        pt.play();
    }

    public static void hFadeOut(Node n, double x, Duration d) {
        hFadeOut(n, x, d, null);
    }

    public static void vFadeIn(Node n, double y, Duration d) {
        vFadeIn(n, y, d, null);
    }

    public static void vFadeIn(Node n, double y, Duration d, EventHandler handler) {

        // Move node
        TranslateTransition tT = new TranslateTransition(d, n);
        tT.setFromY(y);
        tT.setToY(0);

        // FadeTransition
        FadeTransition ft = new FadeTransition(d, n);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(
                tT,
                ft
        );

        // EventHandler
        if (handler != null) {
            pt.setOnFinished(handler);
        }

        pt.play();
    }

    public static void rotateFadeIn(Node n, double angle, Duration d, EventHandler handler) {
        RotateTransition rT = new RotateTransition(d, n);
        rT.setFromAngle(angle);
        rT.setToAngle(0);

        // FadeTransition
        FadeTransition ft = new FadeTransition(d, n);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(
                rT,
                ft
        );

        // EventHandler
        if (handler != null) {
            pt.setOnFinished(handler);
        }

        pt.play();
    }

    public static void rotateFadeIn(Node n, double angle, Duration d) {
        rotateFadeIn(n, angle, d, null);
    }

    public static void rotateFadeOut(Node n, double angle, Duration d, EventHandler handler) {
        // RotateTransition
        RotateTransition rT = new RotateTransition(d, n);
        rT.setFromAngle(0);
        rT.setToAngle(angle);

        // FadeTransition
        FadeTransition ft = new FadeTransition(d, n);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(
                rT,
                ft
        );

        // EventHandler
        if (handler != null) {
            pt.setOnFinished(handler);
        }

        pt.play();
    }

    public static void rotateFadeOut(Node n, double angle, Duration d) {
        rotateFadeOut(n, angle, d, null);
    }
    
    
    public static void clapFadeIn(Node n, double angle, Duration d, EventHandler handler)
    {
        RotateTransition rT = new RotateTransition(d, n);
        rT.setFromAngle(angle);
        rT.setAxis(new Point3D(0,1,0));
        rT.setToAngle(0);

        // FadeTransition
        FadeTransition ft = new FadeTransition(d, n);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(
                rT,
                ft
        );

        // EventHandler
        if (handler != null) {
            pt.setOnFinished(handler);
        }

        pt.play();
    }
    
    
    public static void hFadeIn(Node n, double y, Duration d) {
        hFadeIn(n, y, d, null);
    }

    public static void hFadeIn(Node n, double x, Duration d, EventHandler handler) {

        // Move node
        TranslateTransition tT = new TranslateTransition(d, n);
        tT.setFromX(-x);
        tT.setToX(0);

        // FadeTransition
        FadeTransition ft = new FadeTransition(d, n);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(
                tT,
                ft
        );

        // EventHandler
        if (handler != null) {
            pt.setOnFinished(handler);
        }

        pt.play();
    }
    
}
