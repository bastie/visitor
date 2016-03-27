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
		// use the JFXPanel to embedded the Browser engine
		final JFXPanel fxPanel = new JFXPanel();
		frame.add(fxPanel);
		frame.setSize(800,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Embedded our Browser engine. It is with Java 8 the WebKit engine */
		Platform.runLater(new Runnable() {
			@Override public void run() {
				Browser browser = new Browser();
				Scene scene = new Scene(browser,750,500, Color.web("#666970"));
				fxPanel.setScene(scene);
				// Load the welcome page
				try {
					browser.load(this.getClass().getResource("Welcome_de.html").toURI().toURL().toString());
				} catch (Exception ignored) {
					//webEngine.loadContent(ignored.getLocalizedMessage(), "text/plain");
				}
				
			}
		});
	}
	
	/**
	 * Start the Visitor Browser application
	 * @param ignored
	 */
	public static void main(String[] ignored) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				initAndShowGUI();
			}
		});
	}
	
	
}
