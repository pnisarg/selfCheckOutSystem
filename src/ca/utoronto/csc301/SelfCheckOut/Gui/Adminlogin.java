/**
 * The Login GUI for  Administrator.
 * 
 * @author Nisarg Patel
 * 
 */
package ca.utoronto.csc301.SelfCheckOut.Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.utoronto.csc301.SelfCheckOut.App.Database;
import ca.utoronto.csc301.SelfCheckOut.Exceptions.DatabaseConnectionException;
import ca.utoronto.csc301.SelfCheckOut.Exceptions.JDCBDriverException;

public class Adminlogin extends JFrame {
	// container for login window
	private Container pane;

	// layout manager for window
	private GridBagConstraints gridConstraints;

	// Label for username
	private JLabel label1;

	// Label for Password
	private JLabel label2;

	// username Input Field
	private JTextField username;

	// Password Input Field
	private JPasswordField password;

	// login button
	private JButton submitButton;

	// add user button
	private JButton addUserButton;

	// error message
	private JLabel errorMsgLabel;

	// username password required error
	private JLabel inputMissingLabel;

	// width of login screen
	private int WIDTH = 550;

	// height of login screen
	private int HEIGHT = 300;
	/**
	 * Path to the login icon
	 */
	protected static final String LoginIconPath = "pic/login.png";

	public Adminlogin() {
		super("Nelson's Store AdminLogin ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);

		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginIconPath)); // add
		// custom
		// icon
		pane = getContentPane();
		pane.setLayout(new GridBagLayout());
		gridConstraints = new GridBagConstraints();
		label1 = new JLabel(" Username ", SwingConstants.LEFT);
		label2 = new JLabel(" Password ", SwingConstants.LEFT);
		errorMsgLabel = new JLabel("*username or password is "
				+ "invalid or you are not authorised admistrator!");
		inputMissingLabel = new JLabel("*username and password is required.");
		username = new JTextField(10);
		password = new JPasswordField(10);
		submitButton = new JButton(" Login ");
		addUserButton = new JButton("Create New");
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 1;
		gridConstraints.gridwidth = 1;
		gridConstraints.gridheight = 1;
		gridConstraints.insets = new Insets(2, 3, 2, 2);
		gridConstraints.fill = GridBagConstraints.BOTH;
		Font font = new Font("Helvitica", Font.PLAIN, 18);
		label1.setFont(font);
		label2.setFont(font);
		username.setFont(font);
		password.setFont(font);
		pane.add(label1, gridConstraints);

		gridConstraints.gridwidth = 10;
		gridConstraints.gridx = 5;
		pane.add(username, gridConstraints);
		gridConstraints.gridx = 1;
		gridConstraints.gridwidth = 1;
		gridConstraints.gridy = 15;
		pane.add(label2, gridConstraints);
		gridConstraints.gridwidth = 10;
		gridConstraints.gridx = 5;

		pane.add(password, gridConstraints);
		gridConstraints.gridwidth = 10;
		gridConstraints.gridx = 5;
		gridConstraints.gridy = 40;
		pane.add(submitButton, gridConstraints);
		gridConstraints.gridy = 50;
		pane.add(addUserButton, gridConstraints);
		gridConstraints.gridwidth = 20;
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 60;
		setVisible(true);
		// Event handling for adminLogin
		Handler handler = new Handler();
		submitButton.addActionListener(handler);
		addUserButton.addActionListener(handler);
	}

	/*
	 * this class is event handler for AdminLoginGui
	 */

	private class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			if (username.getText().length() > 0
					&& password.getPassword().length > 0) {
				if (event.getSource() == submitButton) {

					User user;
					user = User.getUser(username.getText());
					if (user != null
							&& user.comparePassword(password.getPassword())
							&& user.privilege == 1) {
						// access granted. allow user to view admin view
						dispose();
						JFrame  adminView = (new SelfCheckOutAdministratorView(user));
						adminView.addWindowListener(new java.awt.event.WindowAdapter() {
				            public void windowClosing(java.awt.event.WindowEvent e) {
				                System.exit(0);
				            }
				        });
					} else {
						pane.remove(inputMissingLabel);
						//pane.validate();
						pane.add(errorMsgLabel, gridConstraints);
						setVisible(true);
					}
				} 
				
			}
			else if (event.getSource() == addUserButton) {
				dispose(); // dispose of this window
				new createNewAdminLogin();
			}
			else {
				pane.remove(errorMsgLabel);
				//pane.validate();
				pane.add(inputMissingLabel, gridConstraints);
				setVisible(true);
			}
		}

	}
}
