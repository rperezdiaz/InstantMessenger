/*
 * this class manages incoming messages from the server
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextPane;

public class MessageHandler implements Runnable {

	private Socket server;
	private BufferedReader in;
	private JTextPane t;

	MessageHandler(Socket s, JTextPane t) throws IOException {
		server = s;
		this.t = t;
		in = new BufferedReader(new InputStreamReader(server.getInputStream()));
	}

	@Override
	public void run() {

		try {
			String serverResponse;
			while (!server.isClosed()) {
				serverResponse = in.readLine(); // receive message from server
				if (serverResponse == "null")
					break;
				
				
				//Write message to textArea
				t.setText(t.getText() + serverResponse+"\n");
				//position textArea to the bottom of the screen
				t.setCaretPosition(t.getDocument().getLength()); 
			}
		} catch (IOException e) {
			System.out.println("Error in MessageHandler.java");
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Client closed.");
		}

	}

}
