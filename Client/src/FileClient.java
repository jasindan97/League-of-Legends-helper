import java.io.*;
import java.rmi.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Client Side of the Client/Server Application. This Application will allow the
 * client to enter commands (requesting server of a service) and have the server
 * respond with the service. 5 unique commands, 1 novel command, and
 * 
 * @author Jasindan Rasalingam - 100584595
 *
 */
public class FileClient {
	public static void main(String argv[]) throws IOException {
		if (argv.length != 1) {
			System.out.println("Usage: java FileClient fileName machineName");
			System.exit(0);
		}
		try {
			ArrayList<String> items = new ArrayList<String>(6);
			String pics = "C:/Users/jasin/Desktop/Dist_assignment2/Server/src/pics/";
			String name = "//" + argv[0] + "/FileServer";
			FileInterface fi = (FileInterface) Naming.lookup(name);

			Menu();

			while (true) {
				Scanner newInput = new Scanner(System.in);
				String r = newInput.nextLine();

				/**
				 * If user enters "NALCSTEAMS"... Calls teamStats() from server and prints out
				 * North American Teams updated statistics as a String whenever called.
				 */
				if (r.equals("NALCSTEAMS")) {
					System.out.println("Team Stats will be displayed below");
					System.out.println(" ");
					String q = fi.teamStats();

					System.out.println(q);
					Menu();
				}

				/**
				 * If user enters "PLAYERMHST"... Prompts user to enter a Player's name, then
				 * passes Clients' input "n" (String) as a parameter for matchHistory(). Prints
				 * Players past few matches and statistics (Kills/Deaths and percentage)
				 */
				if (r.equals("PLAYERMHST")) {
					System.out.println("Enter Summoner Name");
					String n = newInput.nextLine();
					System.out.println("List of past matches");
					System.out.println(" ");
					ArrayList<String> q = fi.matchHistory(n.replace(" ", "+"));

					System.out.println(q);
					Menu();
				}
				/**
				 * If user enters "CHAMPROLES"... Calls champRolez() and prints out entire list
				 * of champions in League of Legends with possible roles/lanes they can be
				 * played in.
				 */
				if (r.equals("CHAMPROLES")) {
					System.out.println("Here is a list of all champion roles");
					System.out.println(" ");
					String q = fi.champRolez();

					System.out.println(q);
					Menu();
				}

				/**
				 * If user enters "CHAMPBUILD"... Requests Client to enter a champion's name,
				 * passes Clients' input and an arraylist as parameters, then prints out the up
				 * to date build for that champion.
				 */
				if (r.equals("CHAMPBUILD")) {
					System.out.println("Enter a champion name");
					String q = newInput.nextLine();
					ArrayList<String> s = fi.championBuildz(q, items);

					System.out.println(" -- Here is the upto date Build for -- " + q);
					System.out.println(" ");
					System.out.println(s);
					Menu();
				}

				/**
				 * If user enters "CHAMPPICTS"... requests Client input (q) once again, then
				 * calls downloadFile() with parameter q. Downloads champion image from server
				 * and uses JFrame to display it.
				 */
				if (r.equals("CHAMPPICTS")) {
					System.out.println("Enter champ name you want a picture of: ");
					String q = newInput.nextLine();

					System.out.println("Retrievig file '" + q + "' from server.");
					byte[] filedata = fi.downloadFile(q);
					File file = new File(pics + q + ".jpg");
					System.out.println("Writing file: '" + q + "'.");
					BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(pics + file.getName()));
					output.write(filedata, 0, filedata.length);
					output.flush();
					output.close();
					System.out.println("Successfully downloaded " + q + "'.");

					BufferedImage image = ImageIO.read(file);
					JLabel label = new JLabel(new ImageIcon(image));
					JFrame f = new JFrame();
					f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					f.getContentPane().add(label);
					f.pack();
					f.setLocation(200, 200);
					f.setVisible(true);

					Menu();
				}

				/**
				 * If user enters "COUNTER"... requests Client input (in) once again, then calls
				 * champCounters() and passes "in" as a parameter.
				 */
				if (r.equals("COUNTER")) {
					System.out.println("Enter champion name to receive counters");
					String in = newInput.nextLine();
					System.out.println(" ");
					System.out.println("Champion Counters Are:");
					System.out.println(" ");
					String q = fi.champCounters(in);

					System.out.println(q);
					Menu();
				}
				
				/**
				 * exit if Client types "exit"
				 */
				if (r.equals("exit")) {
					System.exit(0);
				}

			}
		} catch (Exception e) {
			System.err.println("FileServer exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * for printing out command list.
	 */
	public static void Menu() {
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ======================================");
		System.out.println("|         RITO GAMES ESPORTS           |");
		System.out.println("|   LEAGUE OF LEGENDS CLIENT MENU      |");
		System.out.println("|======================================|");
		System.out.println("| Champion Roles Command: CHAMPROLES   |");
		System.out.println("| Champion Build Command: CHAMPBUILD   |");
		System.out.println("| Player History Command: PLAYERMHST   |");
		System.out.println("| LCS Team Stats Command: NALCSTEAMS   |");
		System.out.println("| LCS Team Stats Command: CHAMPPICTS   |");
		System.out.println("| Champ Counter  Command: COUNTER      |");
		System.out.println(" -------------------------------------- ");
		System.out.println("| Exit Command          : exit         |");
		System.out.println(" ====================================== ");
		System.out.println(" ");
	}
}