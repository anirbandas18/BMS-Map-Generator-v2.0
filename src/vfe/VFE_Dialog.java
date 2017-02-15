package vfe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import utils.CreateIcon;
import filetransfer.MapLoadUnload;
import converter.Xml2Bms;
import dfhmdf.DFHMDF_UI1;
import dfhmdf.RetrieveDFHMDF;
import dfhmdi.RetrieveDFHMDI;

@SuppressWarnings("serial")
public class VFE_Dialog extends JDialog implements WindowListener {

	private JPanel leftSidePanel = new JPanel(),subPanel[] = new JPanel[5];
	private CreateIcon ci=new CreateIcon();
	private ImageIcon saveIcon=ci.createImageIcon("saveSmall.png"),exitIcon=ci.createImageIcon("exitSmall.png"),undoIcon=ci.createImageIcon("undoSmall.png"),editIcon=ci.createImageIcon("editSmall.png");
	private JRadioButton drawFieldsRadio = new JRadioButton("Draw a field"),
			fixedFieldRadio = new JRadioButton("Text"),
			varFieldRadio = new JRadioButton("Variable"),
			arrFieldRadio = new JRadioButton("Array"),
			dragFieldsRadio = new JRadioButton("Drag a Field"),
			deleteFieldsRadio = new JRadioButton("Delete a field");

	private VFE_Panel p;
	private JMenuBar mainMenu = new JMenuBar();
	private JMenu file = new JMenu("File");
	private JMenu edit = new JMenu("Edit");
	private JMenuItem save = new JMenuItem("Save and Generate BMS Code");
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenuItem undo = new JMenuItem("Undo");
	private JMenuItem editMap = new JMenuItem("Edit Map Identifiers");
	private String ret1[];
	
	private ActionListener radioListener1 = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (drawFieldsRadio.isSelected()) {
				fixedFieldRadio.setEnabled(true);
				varFieldRadio.setEnabled(true);
				arrFieldRadio.setEnabled(true);
				p.setFieldType(DFHMDF_UI1.FIXED);
				p.setDraggable(false);
				p.setEditable(false);
				p.setDelete(false);
				p.setDrawable(true);
			} else if (dragFieldsRadio.isSelected()) {
				fixedFieldRadio.setEnabled(false);
				varFieldRadio.setEnabled(false);
				arrFieldRadio.setEnabled(false);
				p.setDraggable(true);
				p.setDrawable(false);
				p.setEditable(false);
				p.setDelete(false);
			} else if (deleteFieldsRadio.isSelected()) {
				fixedFieldRadio.setEnabled(false);
				varFieldRadio.setEnabled(false);
				arrFieldRadio.setEnabled(false);
				p.setDraggable(false);
				p.setDrawable(false);
				p.setEditable(false);
				p.setDelete(true);
			}
		}
	};
	private ActionListener radioListener2 = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(fixedFieldRadio.isSelected())
			{
				p.setFieldType(DFHMDF_UI1.FIXED);
			}
			if(varFieldRadio.isSelected())
			{
				p.setFieldType(DFHMDF_UI1.VARIABLE);
			}
			if(arrFieldRadio.isSelected())
			{
				p.setFieldType(DFHMDF_UI1.ARRAY);
			}
		}
	};

	private ActionListener undoListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			p.doUndo();
		}
	};

	private int r, c;

	public VFE_Dialog(JFrame f, int r, int c)
	{
		super(f);
		setTitle("Visual Form Editor : "+otf.OTF.mapsetName.toUpperCase()+"."+otf.OTF.mapName.toUpperCase());
		this.r = r;
		this.c = c;
		ret1 = new String[6];
		Arrays.fill(ret1, "");
		new utils.SetLAF();
	}

	private void init() 
	{
		drawFieldsRadio.setSelected(true);
		fixedFieldRadio.setSelected(true);

		for (int i = 0; i < subPanel.length; i++)
			subPanel[i] = new JPanel();

		drawFieldsRadio.addActionListener(radioListener1);
		dragFieldsRadio.addActionListener(radioListener1);
		deleteFieldsRadio.addActionListener(radioListener1);
		fixedFieldRadio.addActionListener(radioListener2);
		varFieldRadio.addActionListener(radioListener2);
		arrFieldRadio.addActionListener(radioListener2);
		undo.addActionListener(undoListener);
		KeyStroke keyStroke1 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		JRootPane rootPane =getRootPane();
		rootPane.registerKeyboardAction(exitListener, keyStroke1,JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	private void setVFESize(int row, int col) {
		Dimension d = new Dimension((col) * Constants.XSPAN, (row)
				* Constants.YSPAN + 10);
		p.setPreferredSize(d);
	}

	private void setLayouts() {
		subPanel[0].setLayout(new BoxLayout(subPanel[0], BoxLayout.Y_AXIS));
		subPanel[1].setLayout(new FlowLayout(FlowLayout.RIGHT));
		subPanel[2].setLayout(new BoxLayout(subPanel[2], BoxLayout.Y_AXIS));
	}

	public void buildUI() 
	{
		init();
		p = new VFE_Panel();
		p.setFieldType(DFHMDF_UI1.FIXED);
		setVFESize(r, c);
		setLayouts();
		if (!otf.OTF.isNew)
		{
			RetrieveDFHMDF ret = new RetrieveDFHMDF();
			p.setFieldProp(ret.retrieve());
		}
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		otf.OTF.otf.setTitle("TEAM Z BMS Map Generator : "+otf.OTF.tso_id.toUpperCase()+"."+otf.OTF.bms_pds.toUpperCase()+"("+otf.OTF.mapsetName.toUpperCase()+")");
		ButtonGroup grp1 = new ButtonGroup(), grp2 = new ButtonGroup();

		grp1.add(drawFieldsRadio);
		grp1.add(dragFieldsRadio);
		grp1.add(deleteFieldsRadio);

		grp2.add(fixedFieldRadio);
		grp2.add(varFieldRadio);
		grp2.add(arrFieldRadio);
		
		subPanel[2].add(fixedFieldRadio);
		subPanel[2].add(varFieldRadio);
		subPanel[2].add(arrFieldRadio);
		
		subPanel[1].add(subPanel[2]);

		subPanel[0].add(drawFieldsRadio);
		subPanel[0].add(subPanel[1]);
		subPanel[0].add(Box.createVerticalStrut(10));
		subPanel[0].add(dragFieldsRadio);
		
		leftSidePanel.add(subPanel[0]);
		setJMenuBar(mainMenu);
		mainMenu.add(file);
		mainMenu.add(edit);
		file.add(save);
		file.add(exit);
		file.addMouseListener(getMenuHover(file));
		edit.add(undo);
		edit.add(editMap);
		edit.addMouseListener(getMenuHover(edit));
		exit.addActionListener(exitListener);
		drawFieldsRadio.addMouseListener(getRadioButtonHover(drawFieldsRadio));
		fixedFieldRadio.addMouseListener(getRadioButtonHover(fixedFieldRadio));
		varFieldRadio.addMouseListener(getRadioButtonHover(varFieldRadio));
		arrFieldRadio.addMouseListener(getRadioButtonHover(arrFieldRadio));
		dragFieldsRadio.addMouseListener(getRadioButtonHover(dragFieldsRadio));
		exit.setMnemonic(27);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0));
		exit.setIcon(exitIcon);
		save.addActionListener(saveListener);
		save.setIcon(saveIcon);
		undo.setMnemonic('Z');
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		undo.setIcon(undoIcon);
		editMap.addActionListener(editMapListener);
		editMap.setMnemonic('E');
		editMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
		editMap.setIcon(editIcon);
		save.setMnemonic('S');
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		add(BorderLayout.WEST, leftSidePanel);
		add(BorderLayout.CENTER, p);
		addWindowListener(this);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		this.toBack();
	}

	ActionListener editMapListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			otf.OTF.isNew = false;
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					RetrieveDFHMDI ob2 = new RetrieveDFHMDI( true);
					ob2.retreive(otf.OTF.num);
				}
			});
		}
	};
	ActionListener exitListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated catch block
			int reply = JOptionPane.showConfirmDialog(null,
					"Are you sure to exit? "
							+ "Any unsaved changes will be lost",
					"Save Prompt", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				otf.OTF.otf.setEnabled(true);
				dispose();
				otf.OTF.otf.setTitle("TEAM Z BMS Map Generator : "+otf.OTF.tso_id.toUpperCase()+"."+otf.OTF.bms_pds.toUpperCase());
			}
		}
	};
	ActionListener saveListener = new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated catch block
			try
			{
				RetrieveDFHMDF retr = new RetrieveDFHMDF();
				retr.clear();
				ret1[0] = retr.ret[0];
				ret1[1] = retr.ret[1];
				if(otf.OTF.isNew)
				{
					p.generate(otf.OTF.currentMap);
				}
				else
				{
					p.generate(otf.OTF.num);
				}
				//System.out.println(otf.OTF.num);
				String file_path = otf.OTF.xmlFile.getAbsolutePath();
				String file_name = otf.OTF.xmlFile.getName();
				file_name = file_name.substring(0, file_name.length() - 4)+ ".txt";
				Xml2Bms ob = new Xml2Bms(file_path, file_name);
				ob.convert2BMS();
				ret1[2]=ob.ret[0];
				ret1[3]=ob.ret[1];
				otf.OTF.log.info(otf.OTF.cal.getTime() + "  Map created "+ "with " + p.getFieldProp().size() + " fields.");
				final File sourceFile = new File(System.getProperty("user.dir")+ "\\Workspace\\"+otf.OTF.tso_id.toUpperCase()+"\\"+otf.OTF.bms_pds.toUpperCase()+"\\" + file_name);
				//JOptionPane.showMessageDialog(null, System.getProperty("user.dir")+ "\\Workspace\\"+otf.OTF.tso_id.toUpperCase()+"\\"+otf.OTF.bms_pds.toUpperCase()+"\\" + file_name);
				SwingUtilities.invokeLater(new Runnable() 
				{
					@Override
					public void run()
					{
						MapLoadUnload ob=new MapLoadUnload(true, sourceFile,ret1);
						ob.startUI();
					}
				});
			}
			catch (Exception e1) 
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,e1.toString(), "Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	private MouseListener getRadioButtonHover(final JRadioButton radio)
	{
		MouseListener radioHover=new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				
			}
			public void mouseEntered(MouseEvent arg0) 
			{
				radio.setFocusable(true);
				radio.requestFocusInWindow();
			}
			public void mouseExited(MouseEvent arg0) 
			{
				radio.setFocusable(false);
				radio.setFocusable(true);
			}
			public void mousePressed(MouseEvent arg0)
			{
				
			}
			public void mouseReleased(MouseEvent arg0) 
			{
				
			}
		};
		return radioHover;
	}
	private MouseListener getMenuHover(final JMenu menu)
	{
		MouseListener menuHover=new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				
			}
			public void mouseEntered(MouseEvent arg0) 
			{
				menu.doClick();
			}
			public void mouseExited(MouseEvent arg0) 
			{
				menu.setSelected(false);
			}
			public void mousePressed(MouseEvent arg0)
			{
				
			}
			public void mouseReleased(MouseEvent arg0) 
			{
				
			}
		};
		return menuHover;
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		getParent().setEnabled(false);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		// getParent().setEnabled(true);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		int reply = JOptionPane.showConfirmDialog(this,
				"Are you sure to exit? " + "Any unsaved changes will be lost",
				"Save Prompt", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			getParent().setEnabled(true);
			dispose();
		}
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		getParent().setEnabled(false);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		getParent().setEnabled(true);
	}
}
