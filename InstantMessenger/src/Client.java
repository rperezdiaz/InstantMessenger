import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9090;

	public static void main(String[] args) throws IOException {

		Socket s = new Socket(SERVER_IP, SERVER_PORT);
		MessageHandler serverConn = new MessageHandler(s);
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);

		System.out.print("Enter a nickname: ");
		String nickname = kb.readLine();
		out.println(nickname + " entered the chatroom!");
		
		new Thread(serverConn).start(); // start message handler on client side
		
		while (true) {
			String str = kb.readLine();
			if (!str.equals("/quit"))
				out.println(nickname + ": "+ str); // send to server
			else {
				out.println(nickname +" exited the chatroom.");
				out.println(str);
				break; //exit loop
			}
			
		}
		
		s.close();
		System.exit(0);
	}
}
