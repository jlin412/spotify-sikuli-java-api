package com.spotify.automation;


import java.io.File;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

import com.spotify.utils.DataProperties;

public class LoginScreen extends BaseScreen {
	private ImageTarget username;
	private ImageTarget password;
	private ImageTarget loginButton;
	private ImageTarget errorMessage;
	private ImageTarget loginWindow;
	
	public LoginScreen() {
		super();
		username =  new ImageTarget(new File(DataProperties.path("loginUsername.png")));
		password = new ImageTarget(new File(DataProperties.path("loginPassword.png")));
		loginButton = new ImageTarget(new File(DataProperties.path("loginSubmiButton.png")));
		loginWindow = new ImageTarget(new File(DataProperties.path("loginWindow.png")));
		errorMessage = new ImageTarget(new File(DataProperties.path("loginFailedErrorMessage.png")));
		window.wait(loginButton, 5000);
	}

	public LoginScreen enterLoginData(String user, String pass) {
//		System.out.println(user);
		ScreenRegion username_sr = window.find(username);
		mouse.click(username_sr.getCenter());
		keyboard.type(user);
//		System.out.println(pass);
		ScreenRegion password_sr = window.find(password);
		mouse.click(password_sr.getCenter());
		keyboard.type(pass);
		return this;
	}
	
	public LoginScreen clickLogInFailed() {
		ScreenRegion loginButton_sr = window.find(loginButton);
		mouse.click(loginButton_sr.getCenter());
		return this;
	}
	
	public MainScreen clickLogInSuccess() {
		ScreenRegion loginButton_sr = window.find(loginButton);
		mouse.click(loginButton_sr.getCenter());
		ImageTarget mainWindow = new ImageTarget(new File(DataProperties.path("loginWindow.png")));
		window.wait(mainWindow, 5000);
		return new MainScreen();
	}	
	
	public boolean isLoginWindowExist() {
		ScreenRegion login_window_sr = window.find(loginWindow);
		if (login_window_sr != null) {
			Canvas canvas = new DesktopCanvas();
			canvas.addBox(login_window_sr);
			canvas.display(1);
			return true;
		} 
		return false;	
	}
	
	public boolean isErrorExist() {
		
		ScreenRegion error_mg_sr = window.wait(errorMessage, 3000);
		if (error_mg_sr != null) {
			Canvas canvas = new DesktopCanvas();
			canvas.addBox(error_mg_sr);
			canvas.display(1);			
			return true;
		} 
		return false;
	}
	
}
