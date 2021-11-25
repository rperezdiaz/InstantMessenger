import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
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
	
	private static JPanel panel;
	private static JTextPane tPane;
	private static JTextField tField;
	
	private static ArrayList<Color> colors;
	
	private static String username;
	
	static void buildGUI() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,530);
		frame.setResizable(false);
		frame.setLayout(new FlowLayout());
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		tField = new JTextField();
		tField.setPreferredSize(new Dimension(398,35));
		
		JButton button = new JButton("SEND");
		button.setPreferredSize(new Dimension(75,35));
		
		inputPanel.add(tField);
		inputPanel.add(button);
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		tPane = new JTextPane();
		tPane.setEditable(false);
		tPane.setPreferredSize(new Dimension(478,415));
		
		JScrollPane scrollPane = new JScrollPane( tPane );
		scrollPane.setViewportView(tPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(tPane.getPreferredSize());   
		panel.add(scrollPane, BorderLayout.PAGE_START);
		
		
		frame.add(panel); 
		frame.add(inputPanel);
//		frame.add(tField);
//		frame.add(button, BorderLayout.SOUTH);
		frame.setVisible(true);
		
	}

	public static void main(String[] args) throws IOException {
		colors =new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.MAGENTA);
		colors.add(Color.GRAY);
		colors.add(Color.BLACK);
		colors.add(Color.ORANGE);
		
		username = JOptionPane.showInputDialog("Enter a Username:");
		buildGUI();
		
		Socket s = new Socket(SERVER_IP, SERVER_PORT);
		MessageHandler serverConn = new MessageHandler(s, tPane);
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		String colorID = generateColorID();
		
		out.println(username + " entered the chatroom!");
		
		new Thread(serverConn).start(); // start message handler on client side
		
		while (true) {
			String str = kb.readLine();
//			String str = kb.readLine();
			if(str.equals(""))
				continue;
			else if (!str.equals("/quit"))
				out.println(colorID + " " +username + ": "+ str); // send to server
			else {
				out.println(username +" left the chatroom.");
				out.println(str);
				break; //exit loop
			}
		}
		
		s.close();
		System.exit(0);
	}
	
	private static String generateColorID() {
		Random rnd = new Random();
		int num = rnd.nextInt(colors.size());
		int r =colors.get(num).getRed();
		int g =colors.get(num).getGreen();
		int b = colors.get(num).getBlue();
		return Integer.toString(r) + " "  + Integer.toString(g)+ " " +  Integer.toString(b);
	}
	
}
