package com.jtrillo.RockPaperScissors;

import static org.junit.Assert.*;

import org.junit.Test;

public class RemotePlayerTest {

	@Test(expected = NullPointerException.class)
	public void testRemotePlayer() {
		int port = 5555;
		RemotePlayer rp = new RemotePlayer(port);
		assertEquals("Port must be 5555", port, rp.getPort());
		// Server has not yet been initialized, NullPointerException will be triggered
		rp.getAddress().toString();
	}

	@Test()
	public void testStartServer() {
		int port = 5555;
		RemotePlayer rp = new RemotePlayer(port);
		rp.startServer();
		String address = "/127.0.0.1:"+port;
		assertEquals("Address must be '" + port + ",",
				address, rp.getAddress().toString());
	}
}
