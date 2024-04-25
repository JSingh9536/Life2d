package main;


import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Canvas;
import java.awt.Panel;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.awt.Font;

public class TextFrame {

	private JFrame frame;
	private JButton joinBtn;	
	private JTextField tfUsernameName;
	private JTextArea OUTPUT;
	private JLabel image;
	private JLabel spriteImg;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField taIPAddress;
	private JLabel lblIP;
	
	public String username;
	public String ipAddress;
	
	GamePanel gp;
	
	/**
	 * Launch the application.
	 */
	public  void draw() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextFrame window = new TextFrame(gp);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TextFrame(GamePanel gp) {
		this.gp = gp;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
	//	ReadyToStart = false;
		frame = new JFrame();
		frame.setResizable(false); // Prevents the window from being resized
		frame.setTitle("Life 2D INPUT");  // Sets the window title
		frame.setBounds(100, 100, 430, 230);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		joinBtn = new JButton("Join");
		joinBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				username = tfUsernameName.getText();
				ipAddress = taIPAddress.getText();
				OUTPUT.setText( ipAddress+ "\n" + username);
				gp.gameState = gp.playState;
			//	frame.setVisible(false);
			}
		});
		joinBtn.setBounds(322, 95, 89, 23);
		frame.getContentPane().add(joinBtn);
		
		tfUsernameName = new JTextField();
		tfUsernameName.setBounds(92, 96, 220, 19);
		frame.getContentPane().add(tfUsernameName);
		tfUsernameName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(24, 99, 65, 14);
		frame.getContentPane().add(lblNewLabel);
		
		OUTPUT = new JTextArea();
		OUTPUT.setBounds(49, 129, 323, 45);
		frame.getContentPane().add(OUTPUT);
		
	//	ImageIcon smile = new ImageIcon("smile.png");
		
	//	image = new JLabel("");
	//	Image img = new ImageIcon(this.getClass().getResource("/smiles.png")).getImage();
		//image.setIcon(new ImageIcon(img));
		//image.setBounds(178, 61, 200, 127);
		//frame.getContentPane().add(image);
		
		
	//	spriteImg = new JLabel("");
		//Image spriteImage = new ImageIcon(this.getClass().getResource("/SpritePhoto.png")).getImage();
	//	spriteImg.setIcon(new ImageIcon(spriteImage));
		//spriteImg.setBounds(455, 47, 109, 161);
	//	frame.getContentPane().add(spriteImg);
		
	//	lblNewLabel_1 = new JLabel("Sprite:");
	//	lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
	//	lblNewLabel_1.setBounds(477, 11, 78, 25);
	//	frame.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Life2D");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 30));
		lblNewLabel_2.setBounds(161, 11, 89, 39);
		frame.getContentPane().add(lblNewLabel_2);
		
	//	JTextArea textArea_1 = new JTextArea();
	//	textArea_1.setBounds(31, 135, 89, 22);
	//	frame.getContentPane().add(textArea_1);
	
		taIPAddress = new JTextField();
		taIPAddress.setBounds(92, 72, 220, 19);
		frame.getContentPane().add(	taIPAddress);
		taIPAddress.setColumns(10);
		
		lblIP = new JLabel("IP Address");
		lblIP.setBounds(24, 72, 65, 14);
		frame.getContentPane().add(lblIP);
		

	}
}
