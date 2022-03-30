package View;

import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import Client.ClientWrite;

public class ViewConnect implements ActionListener {
	ClientWrite client;
	JFrame frame;
	JTextField nameText;
	JPasswordField pwdText;

	public ViewConnect(ClientWrite c) {
		client = c;
		frame = new JFrame("Connect");
		frame.setSize(500, 500);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x - 500 / 2;
		int dy = 0;
		frame.setLocation(dx, dy);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		GridLayout grid = new GridLayout(5, 1);
		SpringLayout sp = new SpringLayout();
		JLabel name = new JLabel("entrer votre pseudo ");
		nameText = new JTextField();
		JLabel pwd = new JLabel("entrer votre mot de passe ");
		pwdText = new JPasswordField();
		JButton accept = new JButton("connexion");
		accept.addActionListener(this);
		panel.setLayout(grid);
		panel.add(name);
		panel.add(nameText);
		panel.add(pwd);
		panel.add(pwdText);
		panel.add(accept);
		frame.add(panel);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		client.connect(nameText.getText(), pwdText.getText());
	}

}
