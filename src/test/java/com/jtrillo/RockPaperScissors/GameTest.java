package com.jtrillo.RockPaperScissors;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.jtrillo.RockPaperScissors.Game.Mode;
import com.jtrillo.RockPaperScissors.Game.Output;

public class GameTest {

	@Test
	public void testGameModeOutput() {
		Game game = new Game(Mode.FAIR, Output.CONSOLE);
		assertEquals("Port must be 0", 0, game.getPort());
	}

	@Test
	public void testGameModeOutputInt() {
		int port = 5555;
		Game game = new Game(Mode.FAIR, Output.CONSOLE, port);
		assertNotEquals("Port must not be 0", 0, game.getPort());
		assertEquals("Port must be 5555", port, game.getPort());
	}

	@Test
	public void testPlay() {
		Game game = new Game(Mode.FAIR, Output.CONSOLE);
		game.play();
		assertEquals("The sum of games won by player1, games won by player2 and tied games must be 10",
				10, game.getP1wins()+game.getP2wins()+game.getDraws());
		File file = new File("results.txt");
		assertTrue("File 'results.txt' should not exist", !file.exists());
	}
	
	@Test
	public void testPlay2() {
		Game game = new Game(Mode.FAIR, Output.FILE);
		game.play();
		File file = new File("results.txt");
		assertTrue("File 'results.txt' must exist", file.exists());
	}
}
