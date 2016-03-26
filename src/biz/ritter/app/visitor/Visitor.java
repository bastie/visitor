package biz.ritter.app.visitor;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * The Visitor!
 * 
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class Visitor {

	private static void initAndShowGUI () {
		final JFrame frame = new JFrame("Visitor");
		final JFXPanel fxPanel = new JFXPanel();
		frame.add(fxPanel);
		frame.setSize(800,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Platform.runLater(new Runnable() {
			@Override public void run() {
				initFX(fxPanel);
			}
		});
	}
	
	private static void initFX(JFXPanel fxPanel) {
		Scene scene = new Scene(new Browser(),750,500, Color.web("#666970"));
		fxPanel.setScene(scene);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				initAndShowGUI();
			}
		});
	}
	
	
}
