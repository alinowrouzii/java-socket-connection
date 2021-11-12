import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
	private ArrayList<ClientHandler> clients ;
	private ArrayList<String> clientsName;

	private Socket client;
	private BufferedReader in;
	PrintWriter out;

	ClientHandler(Socket connectionSocket, ArrayList<ClientHandler> clients,ArrayList<String> clientsName) throws IOException {
		this.client = connectionSocket;
		this.clients = clients;
		this.clientsName = clientsName;

		InputStreamReader isr = new InputStreamReader(client.getInputStream());
		this.in = new BufferedReader(isr);

		this.out = new PrintWriter(client.getOutputStream(), true);
	}

	@Override
	public void run() {
		try{
			while(true){
				String msg = in.readLine();
				System.out.println("ur msg: "+ msg);
				if (msg.contains("-setname")) {
					String newName = msg.split(" ")[1];
					changeNameOfClient(this,newName);

				} else if(msg.contains("-whoami")){
					sendMyName(this);
				}
				else if (msg.contains("hey")) {
					sendToAll(msg);
				}
				else{
					//do some thing
					out.println("hey how r u?");
				}

			}
		}catch(IOException e){
			System.err.println(e);
		}catch(NullPointerException e){
			//it means this user should be remove
			//because he was dissconnected from server and get null msg
			String myName = whatIsMyName(this);
			clientsName.remove(myName);
			clients.remove(this);

			sendToAll("User \""+myName+"\" removed from game!!");
		}
		finally{

		}
	}
	private void changeNameOfClient(ClientHandler client, String newName){
		for(int i=0; i<clients.size(); i++){
			if(clients.get(i) == client){
				clientsName.set(i, newName );
				client.out.println("your name has been changed successfully\n Your new name is: "+ newName+"\n");
				break;
			}
		}
	}

	String whatIsMyName(ClientHandler client){
		for(int i=0; i<clients.size(); i++){
			if(clients.get(i) == client){
				String clientName = clientsName.get(i);
				return clientName;
			}
		}
		return "";
	}
	void sendMyName(ClientHandler client){
		for(int i=0; i<clients.size(); i++){
			if(clients.get(i) == client){
				String clientName = clientsName.get(i);
				if(clientName.equals("")){
					client.out.println("You have already no name!!\n");
					return;
				}
				client.out.println("your name is: "+ clientName+"\n");
				return;
			}
		}
	}

	void sendToAll(String msg){
		for(ClientHandler client: clients){
			System.out.println(client);
			client.out.println("size is"+clients.size());
			client.out.println(msg);
		}
	}
}
