
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try (Socket client = new Socket("127.00.0.1", 4321)){

			System.out.println("client connected to server!\n");

			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			ServerConnection conn = new ServerConnection(client);

			new Thread(conn).start();

			while(true){
				String userText = scanner.nextLine();
				if(userText.equals("over")){
					break;
				}
				out.println(userText);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		scanner.close();
	}
}
