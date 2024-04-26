package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Chatbox extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private TextArea chatWindow;
    private JTextField messageField;
    private JButton sendButton;
    private Socket clientSocket;
    private PrintStream out;

	public Chatbox() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        // Add chat window
        chatWindow = new TextArea();
        chatWindow.setEditable(false);
        chatPanel.add(chatWindow, BorderLayout.CENTER);

        // Add message field and send button
        messageField = new JTextField();
        messageField.setColumns(10);
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        JPanel inputPanel = new JPanel();
        inputPanel.add(messageField);
        inputPanel.add(sendButton);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            String message = messageField.getText();
            messageField.setText(""); // Clear message field after sending

            try {
                // Connect to server (replace with actual server IP and port)
                clientSocket = new Socket("127.0.0.1", 5000);
                out = new PrintStream(clientSocket.getOutputStream());

                // Send message
                out.println(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
