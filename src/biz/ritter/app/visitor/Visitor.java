package biz.ritter.app.visitor;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * The Visitor!
 * 
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class Visitor {
	
	private static Browser webEngine;

	private static void initAndShowGUI () {
		
		final JFrame frame = new JFrame("Visitor");
		frame.setLayout(new BorderLayout());
		
		// NORTH
		JPanel northPanel = new JPanel(new GridLayout(1, 50));
		JPanel leftFillerPanel = new JPanel();
		leftFillerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 0));
		JTextField searchText = new JTextField ("Search Visitor");
		searchText.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			if (java.awt.event.KeyEvent.VK_ENTER == e.getKeyCode()) {
				if (searchText.getText().length()>0)
					Visitor.webEngine.loadFromSwing("https://www.google.com/search?q="+searchText.getText());
			}
		}});
		northPanel.add(leftFillerPanel);
		northPanel.add(searchText);
		frame.add(northPanel, BorderLayout.NORTH);
		
		// CENTER use the JFXPanel to embedded the Browser engine
		final JFXPanel fxPanel = new JFXPanel();
		frame.add(fxPanel, BorderLayout.CENTER);
		frame.setSize(800,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Embedded our Browser engine. It is with Java 8 the WebKit engine */
		Platform.runLater(new Runnable() {
			@Override public void run() {
				Visitor.webEngine  = new Browser();
				Scene scene = new Scene(Visitor.webEngine,750,500, Color.web("#666970"));
				fxPanel.setScene(scene);
				// Load the welcome page
				try {
					Visitor.webEngine.load(this.getClass().getResource("Welcome_de.html").toURI().toURL().toString());
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
