package com.jtrillo.RockPaperScissors;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import com.jtrillo.RockPaperScissors.App;
import com.jtrillo.RockPaperScissors.Game.Mode;
import com.jtrillo.RockPaperScissors.Game.Output;

public class AppTest {
	
	@Test
	public void getModeTest() {
		Scanner sc = new Scanner("f");
		Mode mode = App.getMode(sc);
		assertEquals("Chosen mode is fair", Mode.FAIR, mode);
	}
	
	@Test
	public void getOutputTest() {
		Scanner sc = new Scanner("c");
		Output output = App.getOutput(sc);
		assertEquals("Chosen output is console", Output.CONSOLE, output);
	}
	
	@Test
	public void startRemotePlayerTest() {
		RemotePlayer rp = mock(RemotePlayer.class);
		App.startRemotePlayer(rp);
		
		verify(rp, times(1)).startServer();
		verify(rp, times(0)).stopServer();
		verify(rp, times(0)).getAddress();
		verify(rp, times(0)).getPort();
	}
	
	@Test
	public void stopRemotePlayerTest() {
		RemotePlayer rp = mock(RemotePlayer.class);
		App.stopRemotePlayer(rp);
		
		verify(rp, times(0)).startServer();
		verify(rp, times(1)).stopServer();
		verify(rp, times(0)).getAddress();
		verify(rp, times(0)).getPort();
	}
	
	
}
