import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private static final int PORT = 9090;
	private static ArrayList<ClientHandler> clients;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(4);

	public static void main(String[] args) throws IOException {
		ServerSocket listener = new ServerSocket(PORT);
		clients = new ArrayList<>();
		
		while (true) {
			System.out.println("[SERVER] Waiting for client connection...");

			Socket clientConn = listener.accept(); // process waits for connection to port 9090
			System.out.println("[SERVER] A client connected to server.");
			
			ClientHandler clientThread = new ClientHandler(clientConn, clients); //adds clients to
			clients.add(clientThread);

			threadPool.execute(clientThread);
		}

	}

}
