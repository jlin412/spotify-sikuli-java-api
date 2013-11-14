package com.spotify.automation;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;

public abstract class BaseScreen {

	protected ScreenRegion window;
	protected Mouse mouse;
	protected Keyboard keyboard;
	
	public  BaseScreen() {
		
		window = new DesktopScreenRegion();
		mouse = new DesktopMouse();
		keyboard = new DesktopKeyboard();
	}
	

	protected void waitAndClick(ImageTarget target) {
		ScreenRegion sr = window.wait(target, 3000);
		mouse.click(sr.getCenter());
	}	
	
	protected void waitAndDoubleClick(ImageTarget target) {
		ScreenRegion sr = window.wait(target, 3000);
		mouse.doubleClick(sr.getCenter());
	}		
	
}
