package com.spotify.automation;


import java.io.File;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Key;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

import com.spotify.utils.DataProperties;

public class MainScreen extends BaseScreen {
	private ImageTarget search;
//	private ImageTarget showAllResults;
	private ImageTarget songButtons;
	private ImageTarget firstSong;
	private ImageTarget mainWindow;
	
	public MainScreen() {
		super();
		search = new ImageTarget(new File(DataProperties.path("mainSearch.png")));
//		showAllResults = new ImageTarget(new File(DataProperties.path("mainShowAllResults.png")));
		songButtons = new ImageTarget(new File(DataProperties.path("mainSongButtonsPlay.png")));
		mainWindow = new ImageTarget(new File(DataProperties.path("mainWindow.png")));
		firstSong = new ImageTarget(new File(DataProperties.path("mainFoundRecord.png")));
		
		window.wait(mainWindow, 5000);	
	}
	
	
	public MainScreen search(String text) {
		ScreenRegion search_sc = window.find(search);
		mouse.click(search_sc.getCenter());
		keyboard.type(text + Key.ENTER);
//		waitAndClick(showAllResults);
		return this;
	}
	
	public MainScreen clickFirstFoundSong() {
		//here can be more complex logic how to identify first records
		//however, i will do just click for for simplification
		waitAndDoubleClick(firstSong);
		return this;
	}

	public boolean isSongPlaying(){
		
		ScreenRegion play_sr = window.wait(songButtons, 3000);
		if (play_sr != null) {
			Canvas canvas = new DesktopCanvas();
			canvas.addBox(play_sr);
			canvas.display(3);
			return true;
		} 
		return false;
		
	}	
	
	public boolean isSearchResultFound(String searchFile) {
		ImageTarget target = new ImageTarget(new File(DataProperties.path(searchFile)));
		ScreenRegion sr = window.wait(target, 3000);
		if (sr != null) {
			Canvas canvas = new DesktopCanvas();
			canvas.addBox(sr);
			canvas.display(3);
			return true;
		}
		return false;
	}

	public boolean isMainWindowExist() {
		ScreenRegion sr = window.wait(new ImageTarget(new File(DataProperties.path("mainSongButtonsPause.png"))), 3000);
		if (sr != null) {
			Canvas canvas = new DesktopCanvas();
			canvas.addBox(sr);
			canvas.display(1);
			return true;
		}		
		return false;
	}
}
