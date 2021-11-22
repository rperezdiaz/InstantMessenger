/*
 * this class manages incoming messages from the server
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageHandler implements Runnable {

	private Socket server;
	private BufferedReader in;

	MessageHandler(Socket s) throws IOException {
		server = s;
		in = new BufferedReader(new InputStreamReader(server.getInputStream()));
	}

	@Override
	public void run() {

		try {
			while (true) {
				String serverResponse = null;
				serverResponse = in.readLine(); // receive from server
				if (serverResponse == null)
					break;
				System.out.println(serverResponse);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
