import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
//import javax.swing.text.StyledDocument;

import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9090;
	
	//private static Color clr;
	private static JPanel panel;
	private static JTextPane tPane;
	private static JTextField tField;
	
	static void clientGUI() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setResizable(false);
		
		
		tField = new JTextField();
		// arguments:    x    y  width height
		tField.setBounds(10, 425 , 470, 35);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		tPane = new JTextPane();
		tPane.setEditable(false);
		tPane.setPreferredSize(new Dimension(460,415));
		
		JScrollPane scrollPane = new JScrollPane( tPane );
		scrollPane.setViewportView(tPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(tPane.getPreferredSize());   
		panel.add(scrollPane, BorderLayout.PAGE_START);
		
		frame.add(tField);
		frame.add(panel); 
		frame.setVisible(true);
		
	}

	public static void main(String[] args) throws IOException {
		String username = JOptionPane.showInputDialog("Enter a Username:");
		//clr = getRandomColor();
		clientGUI();
		
		Socket s = new Socket(SERVER_IP, SERVER_PORT);
		MessageHandler serverConn = new MessageHandler(s, tPane);
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);


		out.println(username + " entered the chatroom!");
		
		new Thread(serverConn).start(); // start message handler on client side
		
		while (true) {
			String str = kb.readLine();
			if(str.equals(""))
				continue;
			else if (!str.equals("/quit"))
				out.println(username + ": "+ str); // send to server
			else {
				out.println(username +" left the chatroom.");
				out.println(str);
				break; //exit loop
			}
		}
		
		s.close();
		System.exit(0);
	}
}
