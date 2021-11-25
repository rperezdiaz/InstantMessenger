//Handles Client Connections
//Each client is handled by its own server

import java.net.Socket;
import java.util.ArrayList;
import java.io.*;

public class ClientHandler implements Runnable{
	
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private ArrayList<ClientHandler> clients;
	
	ClientHandler (Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException{
		this.clients = clients;
		client = clientSocket;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream(), true);
	}

	@Override
	public void run() {
		try {
			
			while (true) {
				String request = in.readLine();
				if (request.equals("/quit"))
					break;
				else {
					outToAll(request);
				}

			}
		}
		catch (IOException e){
			System.err.println("Error in ClientHandler.java: Someone disconected abruptly.");
		}
		finally {
			out.close();
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void outToAll(String msg) {
		for(ClientHandler c : clients) {
			c.out.println(msg);
		}
		
	}
		
}