import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.io.PrintWriter;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;



public class Client extends JFrame implements ActionListener{

	// JFrame objects
	static final String SERVER_IP = "127.0.0.1";
	static final int SERVER_PORT = 9090;

	private JPanel inputPanel;
	private JPanel panel;
	private static JTextPane tPane;
	private JTextField tField;
	private JButton button;


	private Socket server;
	private MessageHandler serverConn;
	private PrintWriter out;

	private static ArrayList<Color> colors;
	private String colorID;
	private String username;

	public Client(String username) throws UnknownHostException, IOException {

		colors = new ArrayList<>();
		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.MAGENTA);
		colors.add(Color.GRAY);
		colors.add(Color.BLACK);
		colors.add(Color.ORANGE);

		// Main Frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 530);
		this.setResizable(false);
		this.setLayout(new FlowLayout());

		// Input Panel Frame
		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

		// Input bar
		tField = new JTextField();
		tField.setPreferredSize(new Dimension(398, 35));
		tField.setText("Type here...");

		// Button send
		button = new JButton("SEND");
		button.setPreferredSize(new Dimension(75, 35));

		button.addActionListener(this);

		// Output Panel and ScrollPane
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));

		// Output Panel
		tPane = new JTextPane();
		tPane.setEditable(false);
		tPane.setPreferredSize(new Dimension(478, 415));

		// ScrollPane 
		JScrollPane scrollPane = new JScrollPane(tPane);
		scrollPane.setViewportView(tPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(tPane.getPreferredSize());

		// Packing on main frame 
		inputPanel.add(tField);
		inputPanel.add(button);
		panel.add(scrollPane, BorderLayout.PAGE_START);
		this.add(panel);
		this.add(inputPanel);
		this.setVisible(true);

		this.setSocket(SERVER_IP, SERVER_PORT);
		this.setMessageHandler();
		this.setPrintWriter();
		this.colorID = generateColorID();

		this.username = username;
		out.println(this.username + " entered the chatroom!");
		new Thread(serverConn).start();
	}

	// Set the Socket
	public void setSocket (String IP, int port) throws UnknownHostException, IOException {
		this.server = new Socket(IP, port);
	}

	// Returns Socket object.
	public Socket getSocket() {
		return this.server;
	}

	// Setter function
	public void setMessageHandler() throws IOException {
		this.serverConn = new MessageHandler(this.getSocket(), this.tPane);

	}

	// Setter function
	public void setPrintWriter() throws IOException {
		this.out = new PrintWriter(getSocket().getOutputStream(), true);
	}

	private static String generateColorID() {
		Random rnd = new Random();
		int num = rnd.nextInt(colors.size());
		int r = colors.get(num).getRed();
		int g = colors.get(num).getGreen();
		int b = colors.get(num).getBlue();
		return Integer.toString(r) + " " + Integer.toString(g) + " " + Integer.toString(b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.button) {
			String str = this.tField.getText();this.tField.setText("");
			if (!str.equals("/quit")) {
				out.println(this.colorID + " " + this.username + ": " + str); // send to server
			} else {
				out.println(username + " left the chatroom.");
				out.println(str);
			}

		}
	}
}


