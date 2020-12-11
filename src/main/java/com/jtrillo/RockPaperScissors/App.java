package com.jtrillo.RockPaperScissors;

import java.util.Scanner;

import com.jtrillo.RockPaperScissors.Game.Mode;
import com.jtrillo.RockPaperScissors.Game.Output;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void printWelcome() {
		StringBuilder sb = new StringBuilder("-------------------------------------------------------------\n");
		sb.append("|                                                           |\n");
		sb.append("|                   ROCK, PAPER, SCISSORS                   |\n");
		sb.append("|                                                           |\n");
		sb.append("-------------------------------------------------------------\n");
		System.out.println(sb);
	}
	
	public static Mode getMode(Scanner sc) {
		System.out.println("Select mode: F/f=Fair, U/u=Unfair, R/r=Remote");
		String aux = "";
		do {
			aux = sc.nextLine().toLowerCase();
			if(aux.equals("f")) {
				System.out.println(Mode.FAIR.toString());
				return Mode.FAIR;
			}else if(aux.equals("u")) {
				System.out.println(Mode.UNFAIR.toString());
				return Mode.UNFAIR;
			}else if(aux.equals("r")) {
				System.out.println(Mode.REMOTE.toString());
				return Mode.REMOTE;
			}
			System.out.println("Select mode: ");
		} while(true);
	}
	
	public static Output getOutput(Scanner sc) {
		System.out.println("Select output: C/c=Console, F/f=file");
		String aux = "";
		do {
			aux = sc.nextLine().toLowerCase();
			if(aux.equals("c")) {
				System.out.println(Output.CONSOLE.toString());
				return Output.CONSOLE;
			}else if(aux.equals("f")) {
				System.out.println(Output.FILE.toString());
				return Output.FILE;
			}
			System.out.println("Select output: ");
		} while(true);
	}
	
	public static void startRemotePlayer(RemotePlayer rp) {
		rp.startServer();
	}
	
	public static void stopRemotePlayer(RemotePlayer rp) {
		rp.stopServer();
	}
	
    public static void main( String[] args )
    {
    	printWelcome();
		Scanner sc = new Scanner(System.in);
		Mode mode = getMode(sc);
		Output output = getOutput(sc);
		if(mode.equals(Mode.REMOTE)) {
			int port = 8008; // Port number of RemotePlayer server. It can be modified
			RemotePlayer rp = new RemotePlayer(port);
			startRemotePlayer(rp);
			Game game = new Game(mode, output, port);
			game.play();
			stopRemotePlayer(rp);
		}else {
			Game game = new Game(mode, output);
			game.play();
		}
    }
}
