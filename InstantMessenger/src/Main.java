import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.UnknownHostException;

public class Main {

	public static Client client;
	public static String username;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		
		username = JOptionPane.showInputDialog("Enter a Username:");
		while (username == null || username.equals("") || username.contains(" ") || username.contains(":")) {
			username = JOptionPane.showInputDialog("Please enter a valid Username.\n"
					+ "(Your name cannot contain any spaces or colons (:))");
			if (username == null)
				System.exit(0);
		}
		
		client = new Client(username);
		
	}
	

}
