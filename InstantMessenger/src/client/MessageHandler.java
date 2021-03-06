package client;
/*
 * this class manages incoming messages from the server
 */

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

public class MessageHandler implements Runnable {

	private Socket server;
	private BufferedReader in;
	private JTextPane textPane;
	private static Style style;

	MessageHandler(Socket s, JTextPane t) throws IOException {
		server = s;
		this.textPane = t;
		in = new BufferedReader(new InputStreamReader(server.getInputStream()));
	}

	@Override
	public void run() {

		try {
			String serverResponse;
			style = textPane.addStyle("myStyle", null);
			StyleConstants.setBold(style, true);
			while (!server.isClosed()) {
				serverResponse = in.readLine(); // receive message from server
				if (serverResponse == "null")
					break;

				// process active user data....
				String username;
				if (serverResponse.contains(":")) {

					// Process Server Response
					int i = ordinalIndexOf(serverResponse, " ", 3);
					String RGB = (serverResponse.substring(0, i + 1));
					serverResponse = serverResponse.substring(i);

					// Process RGB String to get Assigned Color
					Color c = processRGBString(RGB);
					StyleConstants.setForeground(style, c);

					// extract user name from message string
					i = serverResponse.indexOf(":");
					username = serverResponse.substring(0, i);
					serverResponse = serverResponse.substring(i);
					textPane.getStyledDocument().insertString(textPane.getStyledDocument().getLength(), username, style);
				}

				// Write message to document
				textPane.getStyledDocument().insertString(textPane.getStyledDocument().getLength(), serverResponse + "\n", null);
				// position textArea to the bottom of the screen
				textPane.setCaretPosition(textPane.getDocument().getLength());
			}
		} catch (IOException e) {
			System.err.println("Error in MessageHandler.java");
			e.printStackTrace();
		} catch (BadLocationException e) {
			System.err.println("Error in MessageHandler.java");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Client closed.");
		}
	}

	private static Color processRGBString(String str) {
		String[] rgb = str.split(" ");
		int r = Integer.parseInt(rgb[0]);
		int g = Integer.parseInt(rgb[1]);
		int b = Integer.parseInt(rgb[2]);
		return new Color(r, g, b);
	}

	private static int ordinalIndexOf(String str, String substr, int n) {
		int pos = str.indexOf(substr);
		while (--n > 0 && pos != -1)
			pos = str.indexOf(substr, pos + 1);
		return pos;
	}

}
