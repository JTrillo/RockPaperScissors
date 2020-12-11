package com.jtrillo.RockPaperScissors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Game {

	public static final int N_ITERATIONS = 10;
	public static enum Option {ROCK, PAPER, SCISSORS};
	public static enum Mode {FAIR, UNFAIR, REMOTE};
	public static enum Output {CONSOLE, FILE};
	private int p1wins;
	private int p2wins;
	private int draws;
	private Mode mode;
	private Output output;
	private Random random;
	private int port;
	
	// Game constructor needs mode and output type
	public Game(Mode mode, Output output) {
		//Port number = 0 because it is not used in this case
		//Could be any other number
		this(mode, output, 0);
	}
	
	// If remote mode is chosen, also a port number is necessary
	public Game(Mode mode, Output output, int port) {
		this.p1wins = 0;
		this.p2wins = 0;
		this.draws = 0;
		this.mode = mode;
		this.output = output;
		this.random = new Random();
		this.port = port;
	}
	
	// Some getters
	public int getP1wins() {
		return this.p1wins;
	}
	
	public int getP2wins() {
		return this.p2wins;
	}
	
	public int getDraws() {
		return this.draws;
	}
	
	public int getPort() {
		return this.port;
	}
	
	
	// Class' only public function. Start the iterations
	public void play() {
		for(int i=1; i<=N_ITERATIONS; i++) {
			System.out.println("---------- GAME "+ i + " ----------");
			Option p1 = getPlayer1Option();
			Option p2 = getPlayer2Option();
			int result = checkWinner(p1, p2);
			if(result == 0) {
				System.out.println("IT'S A DRAW!");
				this.draws++;
			}else {
				System.out.println("PLAYER " + result + " HAS WON!");
				if(result == 1) {
					this.p1wins++;
				}else {
					this.p2wins++;
				}
			}
			// In order to verify games' resutls one by one, I decided
			// to stop execution after each iteration 1 second
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Print or save results
		if(this.output.equals(Output.CONSOLE)) {
			printResults();
		}else {
			saveResults();
		}
	}
	
	// Returns player 1 option. It's always random
	private Option getPlayer1Option() {
		Option ret = Option.values()[this.random.nextInt(3)];
		System.out.println ("Player 1 has chosen " + ret);
		return ret;
	}
	
	// Returns player 2 option. Depending on the selected mode, its behavior changes
	private Option getPlayer2Option() {
		Option ret = Option.ROCK;
		switch(this.mode) {
			case FAIR:
				ret = Option.values()[this.random.nextInt(3)];
				break;
			case UNFAIR:
				break;
			case REMOTE:
				try {
					ret = getRemotePlayerOption();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
		}
		System.out.println ("Player 2 has chosen " + ret.toString());
		return ret;
	}
	
	// This methods creates a connection with the server running at RemotePlayer instance
	private Option getRemotePlayerOption() throws IOException {
		Option option;
		URL url = new URL("http://localhost:" + this.port + "/remotePlay");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		int status = connection.getResponseCode();
		if(status == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String response = in.readLine();
			in.close();
			option = Option.valueOf(response);
		}else { // If it is not possible to reach the server, player 2 will choose 'Rock'
			option = Option.ROCK;
		}
		connection.disconnect();
		return option;
	}
	
	
	// Returns 0 = draw, 1 = player1 wins, 2 = player2 wins
	private int checkWinner(Option p1, Option p2) {
		switch(p1) {
			case ROCK:
				if(p2.equals(Option.PAPER)) {
					return 2;
				}else if(p2.equals(Option.SCISSORS)) {
					return 1;
				}else {
					return 0;
				}
			case PAPER:
				if(p2.equals(Option.SCISSORS)) {
					return 2;
				}else if(p2.equals(Option.ROCK)) {
					return 1;
				}else {
					return 0;
				}
			case SCISSORS:
			default:
				if(p2.equals(Option.ROCK)) {
					return 2;
				}else if(p2.equals(Option.PAPER)) {
					return 1;
				}else {
					return 0;
				}
		}
	}
	
	private String generateResults() {
		StringBuilder sb = new StringBuilder("---------- RESULTS ----------\n");
		sb.append("Player 1 wins: ");
		sb.append(this.p1wins + "\n");
		sb.append("Player 2 wins: ");
		sb.append(this.p2wins + "\n");
		sb.append("Draws: ");
		sb.append(this.draws + "\n");
		return sb.toString();
	}
	
	// We don't need file in this case
	private void printResults() {
		removeFile();
		System.out.print("\n" + generateResults());
	}
	
	private void removeFile() {
		File file = new File("results.txt");
		file.delete();
	}
	
	private void saveResults() {
		File file = new File("results.txt");
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.write(generateResults());
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}