/**
 * 
 */
package otf;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.net.URI;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
/**
 *
 */
public class About implements ActionListener {
	/**
	 * Anirban Das
	 */
	private JLabel about,credit,log;
	private JDialog aboutDialog;
	private JOptionPane pane;
	private JButton ok;
	private Box contents,credits,changelog;
	private Object content[],option[];
	private JTabbedPane tab;
	private KeyStroke keyStroke1;
	
	public About()
	{
		createComponents();
		addComponents();
		setUpComponents();
		
	}
	private void createComponents()
	{
		pane=new JOptionPane();
		about=new JLabel("<html><b>BMS Map Generator Version 2.0</b><br><br>An University "
						+ "Relation Remote Mentoring"
						+ " Project by the Students of<br><b>RCCIIT (Kolkata, India)</b> under <b>IBM (India)</b>"
		+"<br><br><b>For IBM internal use only to : </b><br>Create new BMS Maps<br>Edit existing BMS Maps"
		+"<br><br><b>Registered under IBM.</b> Copyright © 2012-2014"
		+"<br><br><b>Resources used : </b><br>Swing Framework<br>Apache Commons Net 3.3<br>Apache Commons IO 2.4<br>Javatuples 1.2<br>Miu Icon set by LinhPham.me<br>XSwingX's prompt framework"
		+"<br><br><b>Requires Java 1.6 or Up.</b> Installed Java Version : "+System.getProperty("java.version")
		+"<br><br>To use the tool in your project contact"
				+ ":<a href=\"https://"
				+ "w3-connections.ibm.com/wikis/home?lang=en#/wiki/"
				+ "Custom%20AMS%20TAC \">Custom AMS AAO TAC,GBS,IBM "
				+"India.</a></html>");
		credit=new JLabel("<html><b>BMS Map Generator Version 2.0</b><br><br><br><b>Programmers : </b><br>Nirupam Das<br>Sounak Roy<br>Prameet Ghosh<br>Anirban Das<br>Piyali Banerjee<br>Raktim Talukdar<br><br><br><b>Mentors :</b><br>Manas Ghosh (RCCIIT, Kolkata, India)<br>Joydeep Banerjee (IBM, India)<br>Sripathi R. Dantuluri (IBM, India)</html>");
		log=new JLabel("<html><b>BMS Map Generator Version 2.0</b><br><br><table align='center'>"
		+"<tr><td>1.  Supports BMS Maps written in any format.</td></tr>"
		+"<tr><td>2.  New improved hassle free Login Screen which allows users to log into Mainframe at a single go.</td></tr>"
		+"<tr><td>3.  User can create, edit or delete Profiles used for logging into Mainframe and save them to local machine for later use.</td></tr>"
		+"<tr><td>4.  PDS can now be created from the tool itself during login if the PDS does not tend to exist.</td></tr>"
		+"<tr><td>5.  New improved interactive GUI for the tool has been provided.</td></tr>"
		+"<tr><td>6.  Arrays can now be created as a part of BMS Map DFHMDF(s) with the tool.</td></tr>"
		+"<tr><td>7.  New notification window has been provided to notify the user of all ongoing tasks.</td></tr>"
		+"<tr><td>8.  New improved About dialog box has been provided with greater details about the tool and it's lateest version.</td></tr>"
		+"<tr><td>9.  Icons have been added to buttons and menus in the tool.</td></tr>"
		+"<tr><td>10. Workspace and profile directories gets created by the tool automatically on the first time launch of the tool.</td></tr>"
		+"<tr><td>11. Temp directory under Workspace gets deleted automatically on tool exit.</td></tr>"
		+"<tr><td>12. Organized heirarchical directory structure to store working files.</td></tr>"
		+"<tr><td>13. Introduction of a change log.</td></tr>"
		+"<tr><td>14. Minor improvements and Bug fixes.</td></tr></table>"
				);
		contents=Box.createVerticalBox();
		credits=Box.createVerticalBox();
		changelog=Box.createVerticalBox();
		ok=new JButton("OK");
		content=new Object[1];
		option=new Object[1];
		tab=new JTabbedPane();
		keyStroke1 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
	}
	private void setUpComponents()
	{
		contents.setBorder(new EmptyBorder(10,10,10,10));
		credits.setBorder(new EmptyBorder(10,10,10,10));
		changelog.setBorder(new EmptyBorder(10,10,10,10));
		pane.setMessage(content);
		pane.setOptions(option);
		aboutDialog=pane.createDialog("BMS Map Generator");
		JRootPane rootPane = aboutDialog.getRootPane();
		rootPane.registerKeyboardAction(this, keyStroke1,JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	private void addComponents()
	{
		contents.add(about);
		credits.add(credit);
		changelog.add(log);
		tab.addTab("About", contents);
		tab.addTab("Credits", credits);
		tab.addTab("Change Log", changelog);
		content[0]=tab;
		option[0]=ok;
		ok.addActionListener(this);
		about.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				try {
					Desktop.getDesktop()
							.browse(new URI("https://w3-connections.ibm.com/wikis/home?lang=en#/wiki/Custom%20AMS%20TAC"));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
	}
	public void showUI()
	{
		new utils.SetLAF();
		aboutDialog.setVisible(true);
		ok.requestFocusInWindow();
	}
	public void actionPerformed(ActionEvent arg0)
	{
		aboutDialog.dispose();
	}
}
