package com.spotify.automation;
import java.io.File;
import java.io.IOException;
import org.junit.*;
import org.sikuli.api.*;

import static org.junit.Assert.*;
import org.sikuli.api.robot.*;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;

import com.spotify.utils.DataProperties;


public class SpotifyAutoTest {
	private Process app;
	
	@Before
	public void setUp() throws InterruptedException {
//		openSpotify();
		app = run();
		ScreenRegion window = new DesktopScreenRegion();
		window.wait(new ImageTarget(new File(DataProperties.path("loginWindow.png"))), 5000);
		Keyboard keyboard =  new DesktopKeyboard(); 
		keyboard.type(Key.DELETE);
	}
	
	@After
	public void tearDown() {
//		closeSpotify();
		stop();
//		System.out.println("close?");
	}

	private Process run() throws InterruptedException {
		try {

			Thread.sleep(3000);
//			System.out.println("open");
			return Runtime.getRuntime().exec("open -a Spotify.app");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	private void stop() {
//		app.destroy();
		ScreenRegion window = new DesktopScreenRegion();
		Mouse mouse = new DesktopMouse();
		ScreenRegion spotify_menu = window.find(new ImageTarget(new File(DataProperties.path("spotify_menu_dropdown.png"))));
		mouse.click(spotify_menu.getCenter());
		ScreenRegion quit = window.wait(new ImageTarget(new File(DataProperties.path("quit.png"))), 1000);
		mouse.click(quit.getCenter());
//		app.destroy();
	}	
	
	@Test
	/**
	 * Test with invalid input and receive error message in login page.
	 * @throws Exception
	 */
	public void testInvalidLogin() throws Exception {
		LoginScreen login = new LoginScreen();
		login.enterLoginData(
				DataProperties.get("invalid.login"), 
				DataProperties.get("invalid.password"))
			.clickLogInFailed();
		assertTrue(login.isErrorExist());
	}

	@Test
	/**
	 * Test with valid input and main screen is displayed.
	 * @throws Exception
	 */
	public void testValidLogin() throws Exception {
		LoginScreen login = new LoginScreen();
		MainScreen main = login
				.enterLoginData(
						DataProperties.get("valid.login"), 
						DataProperties.get("valid.password"))
			.clickLogInSuccess();
		assertFalse(login.isLoginWindowExist());
		assertTrue(main.isMainWindowExist());
	}

	@Test
	/**
	 * Test if a song is searchable in all lower case search term.
	 */
	public void searchSongsInLowerCaseTest() throws InterruptedException  {
		LoginScreen login = new LoginScreen();
		MainScreen mainWindow = login
				.enterLoginData(
						DataProperties.get("valid.login"), 
						DataProperties.get("valid.password"))
				.clickLogInSuccess();
		mainWindow
			.search(DataProperties.get("search.requestWithAllLowerCase"));
		//Verify if the main track is found
		assertTrue(mainWindow.isSearchResultFound(DataProperties.get("images.mainRecord")));
	}	
	
	@Test
	/**
	 * Test if search term: artist:"For King and Country" can find the artist with that name.
	 */
	public void searchArtistTest() throws InterruptedException  {
		LoginScreen login = new LoginScreen();
		MainScreen mainWindow = login
				.enterLoginData(
						DataProperties.get("valid.login"), 
						DataProperties.get("valid.password"))
				.clickLogInSuccess();
		mainWindow
			.search(DataProperties.get("search.requestForArtist"));
		//Verify if the searched artist is found
		assertTrue(mainWindow.isSearchResultFound(DataProperties.get("images.artist")));
	}			
	
	@Test
	/**
	 * Test if search term: track:"The Proof of Your Love" can find tracks with that name.
	 */
	public void searchTrackTest() throws InterruptedException  {
		LoginScreen login = new LoginScreen();
		MainScreen mainWindow = login
				.enterLoginData(
						DataProperties.get("valid.login"), 
						DataProperties.get("valid.password"))
				.clickLogInSuccess();
		mainWindow
			.search(DataProperties.get("search.requestForTrack"));
		//Verify if the searched track is found
		assertTrue(mainWindow.isSearchResultFound(DataProperties.get("images.track")));
	}	
	
	@Test
	/**
	 * Test if search term: album:"The Proof of Your Love" can find the album with that name.
	 */
	public void searchAlbumTest() throws InterruptedException  {
		LoginScreen login = new LoginScreen();
		MainScreen mainWindow = login
				.enterLoginData(
						DataProperties.get("valid.login"), 
						DataProperties.get("valid.password"))
				.clickLogInSuccess();
		mainWindow
			.search(DataProperties.get("search.requestForAlbum"));
		//Verify if the main track is found
		assertTrue(mainWindow.isSearchResultFound(DataProperties.get("images.album")));
	}		
	
	
	@Test
	/**
	 * Test if a song is playable in main screen by examining play button is now pause button.
	 */
	public void searchAndPlayTest() throws InterruptedException  {
		LoginScreen login = new LoginScreen();
		MainScreen mainWindow = login
				.enterLoginData(
						DataProperties.get("valid.login"), 
						DataProperties.get("valid.password"))
				.clickLogInSuccess();
		mainWindow
			.search(DataProperties.get("search.request"))
			.clickFirstFoundSong();
		Thread.sleep(3000);//this here just to listen to music :) in fact, it should be deleted
		assertTrue(mainWindow.isSongPlaying());
	}		
	

}
