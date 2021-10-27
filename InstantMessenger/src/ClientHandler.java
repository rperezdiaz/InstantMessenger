import java.net.Socket;
import java.util.ArrayList;
import java.io.*;

public class ClientHandler implements Runnable{
	
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	
	ClientHandler (Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException{
		client = clientSocket;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream(), true);
	}

	@Override
	public void run() {
		try {
			while (true) {
				String request = in.readLine();
				if (request.contains("quit"))
					break;
				
				if (request.contains("hello"))
					out.println("[SERVER ]Hello! o/");
				
				else
					out.println("[SERVER] Type \"hello\" to get a response!");

			}
		}
		catch (IOException e){
			System.err.println("Error in ClientHandler.java");
			e.printStackTrace();
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
		
}
