import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 9090;
	
	//private static Color clr;
	
	private static JTextArea tArea;
	private static JTextField tField;
	private static JPanel panel;
	
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
		
		tArea = new JTextArea(25, 41);
		tArea.setBackground(Color.WHITE);
		tArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane( tArea );
		scrollPane.setViewportView(tArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		panel.add(scrollPane, BorderLayout.PAGE_START);
		
		frame.add(tField);
		frame.add(panel); 
		frame.setVisible(true);
		
	}
	
	static Color getRandomColor() {
		Random rand = new Random();
		// Java 'Color' class takes 3 floats, from 0 to 1.
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color randomColor = new Color(r, g, b);
		return randomColor;
	}

	public static void main(String[] args) throws IOException {
		String username = JOptionPane.showInputDialog("Enter a Username:");
		//clr = getRandomColor();
		clientGUI();
		
		Socket s = new Socket(SERVER_IP, SERVER_PORT);
		MessageHandler serverConn = new MessageHandler(s, tArea);
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
