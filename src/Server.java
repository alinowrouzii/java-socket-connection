import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	private static ArrayList<String> namesOfClients = new ArrayList<>();

	private static ExecutorService executerPool = Executors.newCachedThreadPool();

	public static void main(String[] args){

		try (ServerSocket welcomingSocket = new ServerSocket(4321);) {
			System.out.println("Server started!\nWaiting for client ...\n");
			while (true) {
				Socket connectionSocket = welcomingSocket.accept();
				System.out.println("server acceped a client\n");

				ClientHandler client = new ClientHandler(connectionSocket, clients, namesOfClients);
				clients.add(client);
				namesOfClients.add("");//we don't even know what is the client name
				executerPool.execute(client);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}