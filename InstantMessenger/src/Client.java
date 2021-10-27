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
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);

		System.out.print("Enter a nickname: ");
		String nickname = kb.readLine();
		
		while (true) {
			System.out.print("> ");
			String str = kb.readLine();
			
			out.println(str); // send to server
			
			if (str.equals("quit")) 
				break; //exit loop
			
			String serverResponse = in.readLine(); // receive from server
			System.out.println(serverResponse);
		}
		
		s.close();
		System.exit(0);
	}
}
