package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import connection.ADDRESS;

public class Server {

	private static final int PORT = ADDRESS.SERVER_PORT;
	private static ArrayList<ClientHandler> clients;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(4);

	public static void main(String[] args) throws IOException {
		ServerSocket listener = new ServerSocket(PORT);
		clients = new ArrayList<>();

		while (true) {
			Socket clientConn = listener.accept(); // wait for connection to port 9090
			System.out.println("[SERVER] A client connected to server from " + clientConn.getRemoteSocketAddress());

			ClientHandler clientThread = new ClientHandler(clientConn, clients); // create new ClientHandler
			clients.add(clientThread); // adds clients to

			threadPool.execute(clientThread); // Start Client Threads
		}

	}

}
