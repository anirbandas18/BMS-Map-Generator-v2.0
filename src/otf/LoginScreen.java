package otf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultEditorKit;

import java.util.prefs.Preferences;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPClient;
import org.javatuples.Pair;
import org.jdesktop.xswingx.PromptSupport;

import mainframelogin.Connect;
import mainframelogin.Login;
import otf.OTF;
import utils.DirectoryHandler;
import utils.InputDialogUI;
import utils.NotificationUI;

public class LoginScreen extends JDialog implements FocusListener {

	/**
	 * Anirban Das
	 */
	private static final long serialVersionUID = -3507416824579405555L;
	private JComboBox<String> profile;
	private JFrame parent;
	private JLabel tail_label1, tail_label2;
	private JFormattedTextField host, port, tso_id, bms_pds, temp_pds, email;
	private JPasswordField pass;
	private JButton connect, edit, exit;
	private JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7,
			panelMajor, tailer, p;
	private Font f;
	private Preferences prefs;
	private String path, profileName, reply[], timeStamp;
	private Date time;
	private OTF ob;
	private BufferedReader br;
	private BufferedWriter bw;
	private File theDir, t;
	private ArrayList<String> name;
	private ArrayList<File> listOfFiles;
	private boolean replyStatus[], firstTime, error, validpds, flag[];
	private KeyStroke keyStroke1, keyStroke4;
	private Dimension d[];
	private InputDialogUI indui;
	private NotificationUI nui;
	private Component c[];
	private FTPClient ftp;
	private DirectoryHandler dh;

	public LoginScreen(JFrame parent, OTF x, File theDir) {
		super(parent);
		this.parent = parent;
		this.theDir = theDir;
		ob = x;
		profileName = "";
		timeStamp = "";
		path = System.getProperty("user.dir") + "/Profiles";
		firstTime = false;
		error = false;
		validpds = true;
	}

	private void init() {
		if (theDir.list().length == 0) {
			firstTimeSetUp();
		} else {
			loadFiles();
			getProfile();
			if (loadProfile()) {
				profile.setSelectedItem((String) profileName);
			} else {
				profile.setSelectedIndex(0);
			}
			showToast("Last used profile " + profileName
					+ " loaded successfully");
			edit.setEnabled(true);
			toggleFields(false);
		}
	}

	private void firstTimeSetUp() {
		firstTime = true;
		profile.addItem("Create New Profile");
		profile.setEnabled(false);
		toggleFields(true);
		connect.setEnabled(false);
		edit.setEnabled(true);
		edit.setText("Save");
		exit.setText("Exit");
		showToast("First time start : Create new profile");
		firstTime = false;
	}

	private void createComponents() {
		listOfFiles = new ArrayList<File>();
		dh = new DirectoryHandler();
		name = new ArrayList<String>();
		profile = new JComboBox<String>();
		reply = new String[2];
		replyStatus = new boolean[2];
		c = new Component[5];
		time = new Date();
		indui = new InputDialogUI();
		host = new JFormattedTextField();
		port = new JFormattedTextField();
		tso_id = new JFormattedTextField();
		bms_pds = new JFormattedTextField();
		email = new JFormattedTextField();
		temp_pds = new JFormattedTextField();
		pass = new JPasswordField("Password");
		connect = new JButton("Connect");
		edit = new JButton("Edit");
		exit = new JButton("Exit");
		d = new Dimension[10];
		p = new JPanel(new BorderLayout(2, 0));
		panelMajor = new JPanel(new GridLayout(7, 0, 2, 5));
		panel7 = new JPanel(new GridLayout(0, 3, 6, 0));
		panel1 = new JPanel(new GridLayout(0, 3, 6, 0));
		panel2 = new JPanel(new GridLayout(0, 6, 6, 0));
		panel3 = new JPanel(new GridLayout(0, 6, 6, 0));
		panel4 = new JPanel(new GridLayout(0, 3, 6, 0));
		panel5 = new JPanel(new GridLayout(0, 3, 6, 0));
		panel6 = new JPanel(new GridLayout(0, 7, 6, 0));
		tailer = new JPanel(new BorderLayout());
		tail_label1 = new JLabel(
				"<html><br><br><br>An University "
						+ "Relation Remote Mentoring"
						+ " Project by Nirupam Das,Prameet Ghosh, Sounak Roy, Anirban Das,Raktim Talukdar,Piyali Banerjee<br>"
						+ " of <a href=\"http://"
						+ "www.rcciit.in\">RCCIIT,Kolkata</a>"
						+ " under the guidance of "
						+ "Manas Ghosh(RCCIIT),Sripathi R Dantuluri & "
						+ "Joydeep Banerjee(IBM India).<br><br></html>");
		tail_label2 = new JLabel("<html>For IBM internal use "
				+ "only.To use the tool in your project contact"
				+ ":<a href=\"https://"
				+ "w3-connections.ibm.com/wikis/home?lang=en#/wiki/"
				+ "Custom%20AMS%20TAC \">Custom AMS AAO TAC,GBS,IBM "
				+ "India.</a><br><br></html>");
		prefs = Preferences.userRoot().node(this.getClass().getName());
	}

	private void setUpComponents() {
		profile.setEditable(false);
		profile.addActionListener(comboSelectListener);
		pass.addFocusListener(this);
		PromptSupport.setPrompt("Mainframe Address", host);
		PromptSupport.setPrompt("Port", port);
		PromptSupport.setPrompt("Mainframe ID", tso_id);
		PromptSupport.setPrompt("PDS", bms_pds);
		PromptSupport.setPrompt("Email ID", email);
		PromptSupport.setPrompt("Template PDS (Optional)", temp_pds);
		pass.setEchoChar((char) 0);
		f = pass.getFont();
		connect.setToolTipText("Return Key");
		exit.setToolTipText("Esc");
		connect.addActionListener(connectSaveListener);
		edit.addActionListener(editDeleteSaveListener);
		exit.addActionListener(exitBackListener);
		pass.setText("Password");
		host.setComponentPopupMenu(this.getPopupAction(host));
		port.setComponentPopupMenu(this.getPopupAction(port));
		tso_id.setComponentPopupMenu(this.getPopupAction(tso_id));
		bms_pds.setComponentPopupMenu(this.getPopupAction(bms_pds));
		temp_pds.setComponentPopupMenu(this.getPopupAction(temp_pds));
		pass.setHorizontalAlignment(JTextField.CENTER);
		tail_label1.setFont(new Font((new JLabel("")).getFont().getFontName(),
				Font.PLAIN, 9));
		tail_label1.setHorizontalAlignment(SwingConstants.CENTER);
		tail_label2.setFont(new Font((new JLabel("")).getFont().getFontName(),
				Font.PLAIN, 9));
		tail_label2.setHorizontalAlignment(SwingConstants.CENTER);
		tail_label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop()
							.browse(new URI("http://www.rcciit.in"));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.toString(), "Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});
		tail_label2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop()
							.browse(new URI(
									"https://w3-connections.ibm.com/wikis/home?lang=en#/wiki/Custom%20AMS%20TAC"));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.toString(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		panel1.setBorder(new EmptyBorder(2, 2, 2, 2));
		panel2.setBorder(new EmptyBorder(2, 2, 2, 2));
		panel3.setBorder(new EmptyBorder(2, 2, 2, 2));
		panel4.setBorder(new EmptyBorder(2, 2, 2, 2));
		panel5.setBorder(new EmptyBorder(2, 2, 2, 2));
		panel6.setBorder(new EmptyBorder(2, 2, 2, 2));
		panelMajor.setBorder(new EmptyBorder(5, 5, 5, 5));
		p.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("BMS Map Generator - Login Screen");
		setContentPane(p);
		setSize(700, 350);
		// setSize(750,350);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void addComponents() {
		panel1.add(new JLabel(""), BorderLayout.LINE_START);
		panel1.add(profile, BorderLayout.CENTER);
		panel1.add(new JLabel(""), BorderLayout.LINE_END);
		panel2.add(new JLabel(""), BorderLayout.LINE_START);
		panel2.add(new JLabel(""), BorderLayout.CENTER);
		panel2.add(host, BorderLayout.CENTER);
		panel2.add(port, BorderLayout.CENTER);
		panel2.add(new JLabel(""), BorderLayout.CENTER);
		panel2.add(new JLabel(""), BorderLayout.LINE_END);
		panel3.add(new JLabel(""), BorderLayout.LINE_START);
		panel3.add(new JLabel(""), BorderLayout.CENTER);
		panel3.add(tso_id, BorderLayout.CENTER);
		panel3.add(pass, BorderLayout.CENTER);
		panel3.add(new JLabel(""), BorderLayout.CENTER);
		panel3.add(new JLabel(""), BorderLayout.LINE_END);
		panel4.add(new JLabel(""), BorderLayout.EAST);
		panel4.add(bms_pds, BorderLayout.CENTER);
		panel4.add(new JLabel(""), BorderLayout.WEST);
		panel7.add(new JLabel(""), BorderLayout.EAST);
		panel7.add(email, BorderLayout.CENTER);
		panel7.add(new JLabel(""), BorderLayout.WEST);
		panel5.add(new JLabel(""), BorderLayout.EAST);
		panel5.add(temp_pds, BorderLayout.CENTER);
		panel5.add(new JLabel(""), BorderLayout.WEST);
		panel6.add(new JLabel(""), BorderLayout.LINE_START);
		panel6.add(new JLabel(""), BorderLayout.CENTER);
		panel6.add(connect, BorderLayout.CENTER);
		panel6.add(edit, BorderLayout.CENTER);
		panel6.add(exit, BorderLayout.CENTER);
		panel6.add(new JLabel(""), BorderLayout.CENTER);
		panel6.add(new JLabel(""), BorderLayout.LINE_END);
		tailer.add(tail_label1, BorderLayout.NORTH);
		tailer.add(tail_label2, BorderLayout.SOUTH);
		panelMajor.add(panel1, BorderLayout.NORTH);
		panelMajor.add(panel2, BorderLayout.CENTER);
		panelMajor.add(panel3, BorderLayout.CENTER);
		panelMajor.add(panel4, BorderLayout.CENTER);
		panelMajor.add(panel7, BorderLayout.CENTER);
		panelMajor.add(panel5, BorderLayout.CENTER);
		panelMajor.add(panel6, BorderLayout.SOUTH);
		p.add(panelMajor, BorderLayout.NORTH);
		p.add(tailer, BorderLayout.SOUTH);
		c[0] = host;
		c[1] = port;
		c[2] = tso_id;
		c[3] = bms_pds;
		c[4] = temp_pds;
	}

	private void registerKeyStrokes() {
		keyStroke1 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		keyStroke4 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		rootPane.registerKeyboardAction(Listener1, keyStroke1,
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		exit.registerKeyboardAction(Listener3, keyStroke4,
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		rootPane.registerKeyboardAction(Listener3, keyStroke4,
				JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	public void showUI() {
		createComponents();
		setUpComponents();
		addComponents();
		registerKeyStrokes();
		getSizeOfComponents();
		init();
		setSizeOfComponents();
		setVisible(true);
		toFront();
		if (firstTime) {
			host.requestFocusInWindow();
		} else {
			pass.requestFocusInWindow();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == pass) {
			if (new String(pass.getPassword()).equalsIgnoreCase("Password")) {
				pass.setText("");
				pass.setEchoChar('*');
				pass.setFont(new Font("Calibri", Font.BOLD, 15));
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == pass) {
			if (new String(pass.getPassword()).equals("")) {
				pass.setText("Password");
				pass.setEchoChar((char) 0);
				pass.setFont(f);
			}
		}
	}

	ActionListener Listener1 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			connectSaveListener.actionPerformed(new ActionEvent(keyStroke1,
					998, connect.getText()));
		}
	};
	ActionListener connectSaveListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Connect")) {
				setColorOfComponent(pass, true);
				String p = new String(pass.getPassword());
				if (p.equals("Password") || p.equals("")) {
					setColorOfComponent(pass, false);
					JOptionPane.showMessageDialog(null,
							"Password field is blank! Enter Password", "Error",
							JOptionPane.ERROR_MESSAGE);
					pass.requestFocusInWindow();
					pass.selectAll();
				} else {
					setCredentials();
					initOTF();
				}
			}
			if (e.getActionCommand().equals("Save")) {
				validateFields();
				if (error) {
					if (saveAction()) {
						profile.removeItemAt(profile.getItemCount() - 1);
						profile.requestFocusInWindow();
					}
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"Invalid Field(s) marked in Red! Rectify them before saving.",
									"Error", JOptionPane.ERROR_MESSAGE);

				}
			}
		}
	};

	ActionListener editDeleteSaveListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			setColorOfComponent(pass, true);
			if (e.getActionCommand().equals("Edit")) {

				toggleFields(true);
				connect.setText("Save");
				edit.setText("Delete");
				exit.setText("Back");
				connect.setEnabled(true);
				profile.removeItemAt(profile.getItemCount() - 1);
				setSizeOfComponents();
				host.requestFocusInWindow();
			}
			if (e.getActionCommand().equals("Delete")) {
				int ret = 0;
				ret = JOptionPane.showConfirmDialog(null, "Profile Name : "
						+ profileName.toUpperCase(), "Confirm Delete Profile",
						JOptionPane.YES_NO_OPTION);
				if (ret == JOptionPane.YES_OPTION) {
					boolean res = deleteProfile();
					if (res) {
						resetFields();
						String bck = profileName;
						listOfFiles.remove(new File(path + "/"
								+ profileName.toUpperCase() + ".txt"));
						name.remove(profileName.toUpperCase());
						makeComboBox();
						if (profile.getItemCount() > 1) {
							profile.removeItemAt(profile.getItemCount() - 1);
							profileName = profile.getItemAt(0);
						}
						if (listOfFiles.size() > 0) {
							loadProfile();
							edit.setText("Delete");
							edit.setEnabled(true);
							exit.setText("Back");
							exit.setEnabled(true);
							pass.requestFocusInWindow();
						} else {
							firstTimeSetUp();
						}
						JOptionPane.showMessageDialog(null,
								"Profile " + bck.toUpperCase()
										+ " successfully deleted!", "Success",
								JOptionPane.INFORMATION_MESSAGE);

					} else {
						JOptionPane.showMessageDialog(null, "Profile "
								+ profileName + " could not be deleted!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			if (e.getActionCommand().equals("Save")) {
				validateFields();
				if (error) {
					if (saveAction()) {
						toggleFields(false);
						connect.setText("Connect");
						edit.setText("Edit");
						exit.setText("Exit");
						exit.setToolTipText("Escape");
						edit.setEnabled(true);
						connect.setEnabled(true);
						pass.requestFocusInWindow();
					}
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"Invalid Field(s) marked in Red! Rectify them before saving.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};

	private boolean deleteProfile() {
		boolean res = false;
		try {
			t = new File(path + "/" + profileName + ".txt");
			res = t.delete();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return res;
	}

	ActionListener exitBackListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			setColorOfComponent(pass, true);
			if (e.getActionCommand().equals("Exit")) {
				setProfile();
				dispose();
				System.exit(0);
			}
			if (e.getActionCommand().equals("Back")) {
				setSizeOfComponents();
				for (Component x : c) {
					setColorOfComponent(x, true);
				}
				if (edit.getText().equals("Delete")) {
					connect.setText("Connect");
					edit.setText("Edit");
					System.out.println(error);
					if (error == false) {
						loadProfile();
						getProfile();
					}
					toggleFields(false);
					exit.setText("Exit");
					exit.setToolTipText("Escape");
					connect.setEnabled(true);
					profile.addItem("Create New Profile");
					pass.requestFocusInWindow();
				}
				if (edit.getText().equals("Save")) {
					if (error) {
						int reply = JOptionPane.showConfirmDialog(null,
								"Save New Profile? ", "Confirm Action",
								JOptionPane.YES_NO_CANCEL_OPTION);
						if (reply == JOptionPane.YES_OPTION) {
							saveAction();
						}
					}
					for (Component i : c) {
						setColorOfComponent(i, true);
					}
					edit.setText("Edit");
					toggleFields(false);
					exit.setText("Exit");
					exit.setToolTipText("Escape");
					connect.setEnabled(true);
					profile.setEnabled(true);
					profile.setSelectedIndex(0);
					getProfile();
					loadProfile();
					pass.requestFocusInWindow();
				}
			}
		}
	};
	ActionListener Listener3 = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if (exit.getText().equals("Exit")) {
				exitBackListener.actionPerformed(new ActionEvent(keyStroke4,
						99, "Exit"));
			} else {
				exitBackListener.actionPerformed(new ActionEvent(keyStroke4,
						99, "Back"));
			}
		}
	};
	ActionListener comboSelectListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (profile.getSelectedItem().toString()
					.equals("Create New Profile")) {
				resetFields();
				toggleFields(true);
				profile.setEnabled(false);
				connect.setEnabled(false);
				edit.setEnabled(true);
				edit.setText("Save");
				exit.setText("Back");
				host.requestFocusInWindow();
			} else {
				profileName = profile.getSelectedItem().toString();
				loadProfile();
				pass.requestFocusInWindow();
			}
		}
	};

	private void loadFiles() {
		listOfFiles.addAll(Arrays.asList(theDir.listFiles()));
		Collections.sort(listOfFiles, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return Long.toString(f1.lastModified()).compareTo(
						Long.toString(f2.lastModified()));
			}
		});
		Collections.reverse(listOfFiles);
		try {
			for (File i : listOfFiles) {
				if (i.isFile()) {
					name.add(i.getName().substring(0, i.getName().length() - 4));
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		makeComboBox();
	}

	private void makeComboBox() {
		profile.removeActionListener(comboSelectListener);
		if (profile.getItemCount() > 0) {
			profile.removeAllItems();
		}
		for (int i = 0; i < name.size(); i++) {
			profile.addItem(name.get(i));
		}
		profile.addItem("Create New Profile");
		profile.addActionListener(comboSelectListener);
	}

	private void resetFields() {
		host.setText("");
		port.setText("");
		tso_id.setText("");
		pass.setText("Password");
		pass.setEchoChar((char) 0);
		bms_pds.setText("");
		temp_pds.setText("");
	}

	private void toggleFields(boolean f) {
		host.setEnabled(f);
		port.setEnabled(f);
		tso_id.setEnabled(f);
		bms_pds.setEnabled(f);
		temp_pds.setEnabled(f);
		pass.setEnabled(!f);
		host.setFocusable(f);
		port.setFocusable(f);
		tso_id.setFocusable(f);
		bms_pds.setFocusable(f);
		temp_pds.setFocusable(f);
		pass.setFocusable(!f);
	}

	private void setProfile() {
		prefs.put("Profile Name", profileName.toUpperCase());
		prefs.put("Mainframe IP Address", host.getText().toUpperCase());
		prefs.put("Port", port.getText().toUpperCase());
		prefs.put("Mainframe ID", tso_id.getText().toUpperCase());
		prefs.put("PDS", bms_pds.getText().toUpperCase());
		if (temp_pds.getText().equals("Template PDS (Optional)")) {
			prefs.put("Template PDS (Optional)", temp_pds.getText());
		} else {
			prefs.put("Template PDS (Optional)", temp_pds.getText()
					.toUpperCase());
		}
		timeStamp = Long.toString(time.getTime());
		prefs.put("Time Stamp", timeStamp);
	}

	private void getProfile() {
		profileName = prefs.get("Profile Name", "");
		host.setText(prefs.get("Mainframe IP Address", ""));
		port.setText(prefs.get("Port", ""));
		tso_id.setText(prefs.get("Mainframe ID", ""));
		bms_pds.setText(prefs.get("PDS", ""));
		temp_pds.setText(prefs.get("Template PDS (Optional)", ""));
		timeStamp = prefs.get("Time Stamp", "");
	}

	private void saveProfile() {
		try {
			bw = new BufferedWriter(new FileWriter(path + "/"
					+ profileName.toUpperCase() + ".txt"));
			bw.write(profileName.toUpperCase());
			bw.newLine();
			bw.write(host.getText().toUpperCase());
			bw.newLine();
			bw.write(port.getText().toUpperCase());
			bw.newLine();
			bw.write(tso_id.getText().toUpperCase());
			bw.newLine();
			bw.write(bms_pds.getText().toUpperCase());
			bw.newLine();
			bw.write(temp_pds.getText().toUpperCase());
			bw.newLine();
			bw.write(Long.toString(time.getTime()));
			bw.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean loadProfile() {
		try {
			File p = new File(path + "/" + profileName + ".txt");
			if (p.exists()) {
				br = new BufferedReader(new FileReader(p));
				profileName = br.readLine();
				host.setText(br.readLine());
				port.setText(br.readLine());
				tso_id.setText(br.readLine());
				bms_pds.setText(br.readLine());
				temp_pds.setText(br.readLine());
				timeStamp = br.readLine();
				br.close();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}

	private boolean saveAction() {
		if (profile.getSelectedItem().toString().equals("Create New Profile")) {
			profileName = indui.showInputUI("Enter Profile Name",
					"Create Profile - Save Mode", "PROFILE NAME");
		} else {
			profileName = indui.showInputUI("Enter Profile Name",
					"Edit Profile - Save Mode", profile.getSelectedItem()
							.toString());
		}
		if (profileName.equals("")) {
			JOptionPane.showMessageDialog(null,
					"Profile Name cannot be empty!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			if (profileName.equals("cancel")) {
				// JOptionPane.showMessageDialog(null,
				// "Enter a valid Profile Name!", "Warning",
				// JOptionPane.WARNING_MESSAGE);
				return false;
			} else {
				if (name.contains((String) profileName)) {
					int ret = JOptionPane.showConfirmDialog(null,
							profileName.toUpperCase()
									+ " already exists! Overwrite?", "Warning",
							JOptionPane.YES_NO_OPTION);
					if (ret == JOptionPane.YES_OPTION) {
						saveProfile();
						JOptionPane.showMessageDialog(null, "Profile "
								+ profileName.toUpperCase()
								+ " successfully saved!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
						t = new File(path + "/" + profileName.toUpperCase()
								+ ".txt");
						listOfFiles.remove(t);
						listOfFiles.add(0, t);
						name.remove(profileName.toUpperCase());
						name.add(0, profileName.toUpperCase());
						makeComboBox();
						profile.setEnabled(true);
						return true;
					} else {
						return false;
					}
				} else {
					saveProfile();
					JOptionPane.showMessageDialog(null, "Profile "
							+ profileName.toUpperCase()
							+ " successfully saved!", "Success",
							JOptionPane.INFORMATION_MESSAGE);
					t = new File(path + "/" + profileName.toUpperCase()
							+ ".txt");
					listOfFiles.remove(t);
					listOfFiles.add(0, t);
					name.remove(profileName.toUpperCase());
					name.add(0, profileName.toUpperCase());
					makeComboBox();
					profile.setEnabled(true);
					return true;
				}
			}
		}
	}

	private void showToast(String msg) {
		try {
			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().getImage("tray.gif");
			TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
			tray.add(trayIcon);
			trayIcon.displayMessage("BMS Map Generator", msg, MessageType.INFO);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(),
					"BMS Map Generator", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private boolean isInteger(char x) {
		int ascii = x;
		if (ascii >= 49 && ascii <= 57) {
			return true;
		} else {
			return false;
		}
	}

	private void validateFields() {
		boolean f = true;
		flag = new boolean[4];
		if (host.getText().equalsIgnoreCase("Mainframe Address")) {
			flag[0] = false;
			setColorOfComponent(host, false);
		} else {
			flag[0] = true;
			setColorOfComponent(host, true);
		}
		if (port.getText().equalsIgnoreCase("Port")
				|| !isInteger(port.getText().trim())) {
			flag[1] = false;
			setColorOfComponent(port, false);
		} else {
			flag[1] = true;
			setColorOfComponent(port, true);
		}
		if (tso_id.getText().equalsIgnoreCase("Mainframe ID")) {
			flag[2] = false;
			setColorOfComponent(tso_id, false);
		} else {
			flag[2] = true;
			setColorOfComponent(tso_id, true);
		}
		if (bms_pds.getText().equalsIgnoreCase("PDS")
				|| !validatePDSName(bms_pds.getText())) {
			flag[3] = false;
			setColorOfComponent(bms_pds, false);
		} else {
			flag[3] = true;
			setColorOfComponent(bms_pds, true);
		}
		for (boolean i : flag) {
			if (i == false) {

				f = false;
				break;
			} else {
				f = true;
			}
		}
		error = f;
	}

	private boolean validatePDSName(String name) {
		if (name.charAt(0) == '.' || name.charAt(name.length() - 1) == '.') {
			return false;
		} else {
			name = name + '.';
			for (int k = 0, i = 0; i < name.length(); i++) {
				if (name.charAt(i) == '.') {
					String part = name.substring(k, i);
					if (isInteger(part.charAt(0)) || part.length() > 8
							|| part.equals(".")) {
						return false;
					}
					k = i + 1;
				}
			}
			return true;
		}
	}

	private boolean validatePDSExistence(FTPClient ftp) {
		try {
			FTPFile dir[] = ftp.listDirectories();
			boolean f = false;
			for (FTPFile x : dir) {

				if (x.getName().equalsIgnoreCase(otf.OTF.bms_pds)) {
					f = true;
					break;
				}
			}
			return f;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}

	private void initOTF() {
		Connect ob1 = new Connect();
		Pair<FTPClient, String[]> p1 = null;
		String message1[] = { "", "" };
		try {
			p1 = ob1.connectFTP();
			this.ftp = p1.getValue0();
			message1 = p1.getValue1();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		if (message1[0].equalsIgnoreCase("true")) {
			replyStatus[0] = true;
			reply[0] = message1[1];
			Login ob2 = new Login(this.ftp);
			Pair<FTPClient, String[]> p2 = ob2.loginFTP();
			this.ftp = p2.getValue0();
			String message2[] = p2.getValue1();
			replyStatus[1] = message2[0].equalsIgnoreCase("true") ? true
					: false;
			reply[1] = message2[1];
			if (replyStatus[0] == true && replyStatus[1] == true) {
				validpds = validatePDSExistence(this.ftp);
				if (!validpds) {
					int answer = JOptionPane.showConfirmDialog(null, "PDS : "
							+ otf.OTF.bms_pds.toUpperCase()
							+ " doesn't exist. Create instead?", "Create PDS",
							JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {
						try {
							validpds = this.ftp.makeDirectory(otf.OTF.bms_pds);
							String rep = this.ftp.getReplyString();
							if (validpds) {
								OTF.log.info(otf.OTF.cal.getTime() + "  PDS "
										+ otf.OTF.bms_pds
										+ " has been created for user "
										+ otf.OTF.tso_id);
								boolean r = dh.makeUserDir(tso_id.getText(),
										bms_pds.getText());
								if (r == true) {
									replyStatus = Arrays.copyOf(replyStatus,
											replyStatus.length + 1);
									reply = Arrays.copyOf(reply,
											reply.length + 1);
									replyStatus[replyStatus.length - 1] = true;
									reply[reply.length - 1] = rep;
									nui = new NotificationUI(
											parent,
											"Notification - Mainframe Connect and Login",
											reply, replyStatus, 500);
									nui.showUI();
								} else {
									nui = new NotificationUI(
											parent,
											"Notification - Mainframe Connect and Login",
											reply, replyStatus, 500);
									nui.showUI();
								}
							} else {
								replyStatus = Arrays.copyOf(replyStatus,
										replyStatus.length + 1);
								reply = Arrays.copyOf(reply, reply.length + 1);
								replyStatus[replyStatus.length - 1] = false;
								reply[reply.length - 1] = rep;
								nui = new NotificationUI(
										parent,
										"Notification - Mainframe Connect and Login",
										reply, replyStatus, 500);
								nui.showUI();
							}
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, e.toString(),
									"Error", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					} else {
						setColorOfComponent(bms_pds, false);
						bms_pds.setFocusable(true);
						bms_pds.setEditable(true);
						bms_pds.setEnabled(true);
						bms_pds.requestFocusInWindow();
						bms_pds.selectAll();
					}
				} else {
					boolean r = dh.makeUserDir(tso_id.getText(),
							bms_pds.getText());
					if (r == true) {
						nui = new NotificationUI(parent,
								"Notification - Mainframe Connect and Login",
								reply, replyStatus, 500);
						nui.showUI();
					}
				}
			} else {
				nui = new NotificationUI(parent,
						"Notification - Mainframe Connect and Login", reply,
						replyStatus, 500);
				nui.showUI();
			}

		} else {
			replyStatus[0] = false;
			reply[0] = message1[1];
			nui = new NotificationUI(parent,
					"Notification - Mainframe Connect and Login", reply,
					replyStatus, 500);
			nui.showUI();
		}
		if (validpds == true) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					nui.start.doClick();
				}
			});
		}
		if (replyStatus[0] == true && replyStatus[1] == true
				&& validpds == true) {
			otf.OTF.ftp = this.ftp;
			setProfile();
			dispose();
			ob.showOTF();
			nui.toFront();
		} else {
			if (replyStatus[0] == false) {
				editDeleteSaveListener.actionPerformed(new ActionEvent(this,
						99, "Edit"));
			} else {
				if (replyStatus[1] == false && validpds == true) {
					setColorOfComponent(pass, false);
					pass.requestFocusInWindow();
				}
			}
		}
	}

	private void getSizeOfComponents() {
		d[0] = new Dimension(profile.getPreferredSize().width,
				profile.getPreferredSize().height);
		d[1] = new Dimension(host.getPreferredSize().width,
				host.getPreferredSize().height);
		d[2] = new Dimension(port.getPreferredSize().width,
				port.getPreferredSize().height);
		d[3] = new Dimension(tso_id.getPreferredSize().width,
				tso_id.getPreferredSize().height);
		d[4] = new Dimension(pass.getPreferredSize().width,
				pass.getPreferredSize().height);
		d[5] = new Dimension(bms_pds.getPreferredSize().width,
				bms_pds.getPreferredSize().height);
		d[6] = new Dimension(temp_pds.getPreferredSize().width,
				temp_pds.getPreferredSize().height);
		d[7] = new Dimension(connect.getPreferredSize().width,
				connect.getPreferredSize().height);
		d[8] = new Dimension(edit.getPreferredSize().width,
				edit.getPreferredSize().height);
		d[9] = new Dimension(exit.getPreferredSize().width,
				exit.getPreferredSize().height);
	}

	private void setSizeOfComponents() {
		profile.setPreferredSize(d[0]);
		host.setPreferredSize(d[1]);
		port.setPreferredSize(d[2]);
		tso_id.setPreferredSize(d[3]);
		pass.setPreferredSize(d[4]);
		bms_pds.setPreferredSize(d[5]);
		temp_pds.setPreferredSize(d[6]);
		connect.setPreferredSize(d[7]);
		edit.setPreferredSize(d[8]);
		exit.setPreferredSize(d[9]);
	}

	private void setColorOfComponent(Component c, boolean f) {
		if (f) {
			c.setBackground(Color.WHITE);
		} else {
			c.setBackground(Color.RED);
		}
	}

	private void setCredentials() {
		otf.OTF.host = host.getText();
		otf.OTF.port = port.getText();
		otf.OTF.tso_id = tso_id.getText();
		otf.OTF.password = new String(pass.getPassword());
		otf.OTF.bms_pds = bms_pds.getText();
		otf.OTF.temp_pds = temp_pds.getText();
	}

	private JPopupMenu getPopupAction(JTextField textField) {
		JPopupMenu popup = new JPopupMenu();
		Action copyAction = textField.getActionMap().get(
				DefaultEditorKit.copyAction);
		Action cutAction = textField.getActionMap().get(
				DefaultEditorKit.cutAction);
		Action pasteAction = textField.getActionMap().get(
				DefaultEditorKit.pasteAction);
		Action selectAllAction = textField.getActionMap().get(
				DefaultEditorKit.selectAllAction);
		copyAction.putValue(Action.NAME, "Copy");
		cutAction.putValue(Action.NAME, "Cut");
		pasteAction.putValue(Action.NAME, "Paste");
		selectAllAction.putValue(Action.NAME, "Select All");
		popup.add(cutAction);
		popup.add(copyAction);
		popup.add(pasteAction);
		popup.addSeparator();
		popup.add(selectAllAction);
		return popup;
	}
}