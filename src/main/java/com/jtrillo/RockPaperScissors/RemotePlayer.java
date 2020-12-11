package com.jtrillo.RockPaperScissors;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Random;
import java.util.logging.Logger;

import com.jtrillo.RockPaperScissors.Game.Option;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class RemotePlayer {

	public static final Random random = new Random();
	public static final Logger logger = Logger.getLogger(RemotePlayer.class.getName());
	private int port;
	private HttpServer server;
	
	public RemotePlayer(int port) {
		this.port = port;
	}
	
	// Port number getter
	public int getPort() {
		return this.port;
	}
	
	// Address getter
	public InetSocketAddress getAddress() {
		return this.server.getAddress();
	}
	
	public void startServer() {
		try {
			this.server = HttpServer.create(new InetSocketAddress("localhost", this.port), 0);
			this.server.createContext("/remotePlay", new MyHttpHandle());
			this.server.setExecutor(null);
			this.server.start();
			logger.info("Server started on port " + this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopServer() {
		this.server.stop(0);
		logger.info("Server stopped");
	}
	
	// Handler for GET requests Game instance will make
	public class MyHttpHandle implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String response = Option.values()[random.nextInt(3)].toString();
			logger.info("Remote player has chosen " + response);
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.flush();
			os.close();
		}
	}

}