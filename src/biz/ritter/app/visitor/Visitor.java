package biz.ritter.app.visitor;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JToggleButton;

/**
 * The Visitor!
 * 
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class Visitor {
	
	private Browser webEngine;
	
	private JButton[] macCloseMinMax = new JButton[]{};
	private final JFrame frame = new JFrame("Visitor");

	private void initAndShowGUI () {
		
		
		if ("Mac OS X".equals(System.getProperty("os.name", ""))) {
			frame.setUndecorated(false); 
			this.macCloseMinMax = new JButton[]{
					new JButton(),
					new JButton(),
					new JButton()
			};
			for (JButton b : this.macCloseMinMax) { // TODO fix this hint
				b.setMaximumSize(new Dimension(10,10));
				b.setMinimumSize(new Dimension(10, 10));
				b.setPreferredSize(new Dimension(10, 10));
			}
			this.macCloseMinMax[0].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			});
			this.macCloseMinMax[1].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setState(JFrame.ICONIFIED);
				}
			});
			this.macCloseMinMax[2].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(frame.getExtendedState()==JFrame.NORMAL)
					    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);					
					    else
					    frame.setExtendedState(JFrame.NORMAL);				}
			});
		}
		frame.setLayout(new BorderLayout());

		// North
		JPanel ctrlPanel = new JPanel (new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0d, 0d, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,2,0), 0, 0);
		for (JButton b : this.macCloseMinMax) {
			ctrlPanel.add(b,gbc);
			gbc.gridx++;
		}
		ctrlPanel.add(new JButton("<"),gbc);
		
		gbc.gridx++;
		ctrlPanel.add(new JButton(">"),gbc);
		
		gbc.gridx++;
		JToggleButton sideBarToogle = new JToggleButton("oo");
		sideBarToogle.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof AbstractButton) { //defensive programming 
					final boolean sideBarNeedToShow = ((AbstractButton) e.getSource()).getModel().isSelected();
					SwingUtilities.invokeLater(new Runnable() {
						@Override public void run() {
							Visitor.this.setSidebarVisible(sideBarNeedToShow);
							Visitor.this.frame.validate();
						}
					});
				}
			}
		});;
		ctrlPanel.add(sideBarToogle,gbc);
		
		gbc.gridx++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1d;
		JTextField inputField = new JTextField(300);
		inputField.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			if (java.awt.event.KeyEvent.VK_ENTER == e.getKeyCode()) {
				if (inputField.getText().length()>0)
					try {
						new URL(inputField.getText());
						Visitor.this.webEngine.loadFromSwing(inputField.getText());
					}
					catch (MalformedURLException goToSearchEngine) { 
						Visitor.this.webEngine.loadFromSwing("https://www.google.com/search?q="+inputField.getText());
					}
			}
		}});
		ctrlPanel.add(inputField,gbc);

		gbc.gridx++;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0d;
		ctrlPanel.add(new JButton ("Senden"),gbc);
		
		gbc.gridx++;
		ctrlPanel.add(new JButton ("Seitenleiste"),gbc);
		frame.add(ctrlPanel, BorderLayout.NORTH);
		
		// CENTER use the JFXPanel to embedded the Browser engine
		final JFXPanel fxPanel = new JFXPanel();
		frame.add(fxPanel, BorderLayout.CENTER);
		frame.setSize(800,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Embedded our Browser engine. It is with Java 8 the WebKit engine */
		Platform.runLater(new Runnable() {
			@Override public void run() {
				Visitor.this.webEngine  = new Browser();
				Scene scene = new Scene(Visitor.this.webEngine,750,500, Color.web("#666970"));
				fxPanel.setScene(scene);
				// Load the welcome page
				try {
					Visitor.this.webEngine.load(this.getClass().getResource("Welcome_de.html").toURI().toURL().toString());
				} catch (Exception ignored) {
					//webEngine.loadContent(ignored.getLocalizedMessage(), "text/plain");
				}
				
			}
		});
	
	}
	
	
	private JTabbedPane sideBar;
	protected void setSidebarVisible(boolean sideBarNeedToShow) {
		if (null == sideBar) {
			sideBar = new JTabbedPane(JTabbedPane.TOP);
			JPanel favoritSideBar = new JPanel(new BorderLayout());
			favoritSideBar.setMinimumSize(new Dimension(400, 100));
			favoritSideBar.setMinimumSize(favoritSideBar.getMinimumSize());
			JList<Object> favoritList = new JList<>(new Object[]{"Blub blob blub"});
			favoritSideBar.add(favoritList, BorderLayout.CENTER);
			sideBar.addTab("F", favoritSideBar);
		}
		
		if (sideBarNeedToShow) {
			this.frame.add(this.sideBar,BorderLayout.WEST);
		}
		else {
			this.frame.remove(this.sideBar);
			this.sideBar = null;
		}
	}
	

	/**
	 * Start the Visitor Browser application
	 * @param ignored
	 */
	public static void main(String[] ignored) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				Visitor ui = new Visitor();
				ui.initAndShowGUI();
			}
		});
	}
	
	
}
