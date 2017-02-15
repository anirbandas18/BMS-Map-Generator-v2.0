package dfhmsd;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import dfhmdi.DFHMDI_UI1;
import dfhmdi.RetrieveDFHMDI;


public class DFHMSD_UI
{

	/**
	 * 
	 */
	/**
	 * @param args
	 */
	public static JComboBox<String> TYPE, LANG, MODE, TERM, CTRL, STORAGE,
			TIOAPFX;
	private JLabel label1, label2, label3, label4, label5, label6, label7;
	private String[] typeStrings = { "SYSPARM", "DSECT", "MAP" };
	private String[] langStrings = { "COBOL", "Assembler", "PL/I", "REXX", "C","C++", "Java" };
	private String[] modStrings = { "INOUT", "IN", "OUT" };
	private String[] trmStrings = { "ALL", "3270" };
	private String[] ctrlStrings = { "FREEKB", "PRINT", "ALARM", "FRSET","L40", "L64", "L80", "HONEOM" };
	private String[] strStrings = { "AUTO" };
	private String[] tiopStrings = { "YES" };
	public JButton ok,cancel;
	private JDialog dialog;
	private JOptionPane pane;
	private JRootPane rootPane;
	private KeyStroke enter,esc;
	private Object parent[],options[];
	private JPanel gui, labels, values;
	public static String mapSetName;
	public static int num;

	public DFHMSD_UI(JFrame f, boolean modal) 
	{
		createComponents();
		addComponents();
		setUpComponents();
	}
	private void createComponents()
	{
		gui = new JPanel(new BorderLayout());
		labels = new JPanel(new GridLayout(7, 1));
		values = new JPanel(new GridLayout(7, 1,10,10));
		TYPE = new JComboBox<String>(typeStrings);
		LANG = new JComboBox<String>(langStrings);
		MODE = new JComboBox<String>(modStrings);
		TERM = new JComboBox<String>(trmStrings);
		CTRL = new JComboBox<String>(ctrlStrings);
		STORAGE = new JComboBox<String>(strStrings);
		TIOAPFX = new JComboBox<String>(tiopStrings);
		label1 = new JLabel("TYPE:");
		label2 = new JLabel("LANG:");
		label3 = new JLabel("MODE:");
		label4 = new JLabel("TERM:");
		label5 = new JLabel("CTRL:");
		label6 = new JLabel("STORAGE: ");
		label7 = new JLabel("TIOAPFX: ");
		ok = new JButton("OK");
		cancel=new JButton("Cancel");
		pane=new JOptionPane();
		enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		esc=KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0);
		parent=new Object[1];
		options=new Object[2];
	}
	private void setUpComponents()
	{
		parent[0]=gui;
		options[0]=ok;
		options[1]=cancel;
		pane.setMessage(parent);
		pane.setOptions(options);
		dialog=pane.createDialog("Mapset Definitions");
		rootPane = dialog.getRootPane();
		rootPane.registerKeyboardAction(okListener, enter,JComponent.WHEN_IN_FOCUSED_WINDOW);
		rootPane.registerKeyboardAction(cancelListener, esc,JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	ActionListener cancelListener=new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			dialog.dispose();
		}
	};
	private ActionListener okListener = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e)
		{
			dialog.setEnabled(false);
			if (otf.OTF.isNew) 
			{
				CreateXML ob1 = new CreateXML();
				ob1.generate(System.getProperty("user.dir") + "\\Workspace\\"+otf.OTF.tso_id.toUpperCase()+"\\"+otf.OTF.bms_pds.toUpperCase()+"\\");
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						DFHMDI_UI1 ob2 = new DFHMDI_UI1( true);
						ob2.buildUI();
					}
				});
			}
			else
			{
				ModifyXML ob1 = new ModifyXML();
				ob1.modify();
				SwingUtilities.invokeLater(new Runnable() 
				{
					public void run()
					{
						RetrieveDFHMDI ob2 = new RetrieveDFHMDI(otf.OTF.otf, true);
						ob2.retreive(otf.OTF.num);
					}
				});
			}
			dialog.dispose();
		}
	};
	private void addComponents()
	{

		//values.setBorder(new EmptyBorder(10,10,10,10));
		labels.add(label1);
		labels.add(label2);
		labels.add(label3);
		labels.add(label4);
		labels.add(label5);
		labels.add(label6);
		labels.add(label7);
		values.add(TYPE);
		values.add(LANG);
		values.add(MODE);
		values.add(TERM);
		values.add(CTRL);
		values.add(STORAGE);
		values.add(TIOAPFX);
		gui.add(labels, BorderLayout.WEST);
		gui.add(values, BorderLayout.EAST);
		ok.addActionListener(okListener);
		cancel.addActionListener(cancelListener);
	}

	public void showUI(String m) 
	{
		mapSetName = m;
		dialog.setVisible(true);
	}
}
