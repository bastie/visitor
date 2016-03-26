package biz.ritter.app.visitor;

import netscape.javascript.JSObject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * 
 * @author Sͬeͥbͭaͭsͤtͬian
 *
 */
public class Browser extends Region {

	private final WebView browser = new WebView();
	private final WebEngine webEngine = browser.getEngine();
	private final Worker<?> worker = webEngine.getLoadWorker();
	
	private final XMLHttpRequest requestHandler = new XMLHttpRequest();
	
	public Browser() {
		
		worker.stateProperty().addListener(new ChangeListener<State>() {

			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
				if (newState == State.SUCCEEDED) {
					JSObject browserWindow = (JSObject) webEngine.executeScript("window");
					browserWindow.setMember("localXMLHttpRequest", requestHandler);
					browserWindow.getClass();
				}
			}

		});
		try {
			webEngine.load(this.getClass().getResource("Welcome_de.html").toURI().toURL().toString());
		} catch (Exception ignored) {
			webEngine.loadContent(ignored.getLocalizedMessage(), "text/plain");
		}
		
		
		this.getChildren().add(browser); // add the web view to the scene
		
		this.webEngine.userAgentProperty().setValue(this.webEngine.userAgentProperty().get().replace("JavaFX/8.0", "Visitor/0.1"));
	}
	
	@Override protected void layoutChildren() {
		final double w = this.getWidth();
		final double h = this.getHeight();
		layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
	}
	
	@Override protected double computePrefWidth(double height) {
		return 750;
	}
	
	@Override
	protected double computePrefHeight(double width) {
		return 500;
	}
		
}

