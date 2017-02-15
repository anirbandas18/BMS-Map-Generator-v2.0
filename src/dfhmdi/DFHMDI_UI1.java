package dfhmdi;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import vfe.VFE_Dialog;

/**
 * This class is used to generate the UI of the DFHMDI It takes in all the map
 * properties and opens up the VFE.
 * 
 * @author NIRUPAM
 * 
 */
public class DFHMDI_UI1 implements WindowListener
{
	private Box parent,hor1,hor2,hor3,hor4,hor5,buttons;
	public static JCheckBox enable;
	private JDialog parentDialog;
	private JPanel x;
	private JLabel mapNameLabel,rowLabel, colLabel,sizeLabel,lineLabel,columnLabel;
	public JTextField mapNameTextField,rowTextField,colTextField,lineTextField,columnTextField;
	private JButton okButton,cancelButton;
	private DFHMDI obj;
	private boolean proceedPressed;
	private JRootPane rootPane;
	private KeyStroke proceed,cancel ;
	private JOptionPane pane;
	private JComponent firstComp;
	private JDialog dialog;
	private Object message[],options[];
	private int currentMap;
	
	ActionListener cancelListener = new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			dialog.dispose();
		}
	};
	ActionListener okListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//code should be under finish
			Calendar c = Calendar.getInstance();
			proceedPressed=true;
			if (!validateFields())
			{
				JOptionPane.showMessageDialog(null, "Rectify Field(s) marked in red!","Error", JOptionPane.ERROR_MESSAGE);
				firstComp.requestFocusInWindow();
				
			} 
			else
			{
				otf.OTF.mapName=mapNameTextField.getText();
				try
				{
					obj.setSize(Integer.parseInt(rowTextField.getText()),Integer.parseInt(colTextField.getText()));
					obj.setMapName(mapNameTextField.getText());
					obj.setLine(Integer.parseInt(lineTextField.getText()));
					obj.setColumn(Integer.parseInt(columnTextField.getText()));
					obj.setMapatts(enable.isSelected());
				}
				catch (NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null, "Invalid Input","Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				obj.setDate(c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.MONTH), c.get(Calendar.YEAR));
				obj.setTime(c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
				if (otf.OTF.isNew) 
				{
					//otf.OTF.num = otf.OTF.lastMap;
					obj.generate();
					dialog.dispose();
					SwingUtilities.invokeLater(new Runnable() 
					{		
						public void run()
						{
							try 
							{
								otf.OTF.otf.setEnabled(false);
								otf.OTF.VFE_Diag = new VFE_Dialog(otf.OTF.otf,Integer.parseInt(rowTextField.getText()),Integer.parseInt(colTextField.getText()));
								otf.OTF.VFE_Diag.buildUI();
								otf.OTF.VFE_Diag.toFront();
							} 
							catch (NumberFormatException e)
							{
								JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
								e.printStackTrace();
							}
						}
					});
				} 
				else 
				{
					obj.modify();
					dialog.dispose();
				}
			}
		}
	};
	public DFHMDI_UI1(boolean modal)
	{
		otf.OTF.otf.setEnabled(false);
		//otf.OTF.currentMap++;
		this.currentMap = otf.OTF.currentMap;
		this.currentMap++;
		otf.OTF.currentMap = this.currentMap;
		System.out.println("DFHMDI_UI CurrentMap is : "+this.currentMap);
		createComponents();
		addComponents();
		setUpComponents();
		new utils.SetLAF();
	}

	public DFHMDI_UI1(JFrame f, boolean modal)
	{
		otf.OTF.otf.setEnabled(false);
		//otf.OTF.currentMap++;
		createComponents();
		addComponents();
		setUpComponents();
		new utils.SetLAF();
	}
	private void createComponents()
	{
		parent=Box.createVerticalBox();
		hor1=Box.createHorizontalBox();
		hor2=Box.createHorizontalBox();
		hor3=Box.createHorizontalBox();
		hor4=Box.createHorizontalBox();
		hor5=Box.createHorizontalBox();
		buttons=Box.createHorizontalBox();
		enable=new JCheckBox("Enable Color and Hilight",true);
		x=new JPanel();
		mapNameLabel = new JLabel("Map Name : ");
		rowLabel = new JLabel("Row : ");
		colLabel = new JLabel("Col : ");
		sizeLabel = new JLabel("Map Dimensions");
		lineLabel = new JLabel("Starting row of the map : ");
		columnLabel = new JLabel("Starting column of the map : ");
		mapNameTextField = new JTextField(7);
		rowTextField = new JTextField(2);
		colTextField = new JTextField(2);
		lineTextField = new JTextField(2);
		columnTextField = new JTextField(2);
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		proceed = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		cancel = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		pane=new JOptionPane();
		proceedPressed=false;
		obj = new DFHMDI();
		message=new Object[1];
		options=new Object[1];
	}
	private void setUpComponents()
	{
		rowTextField.setText("24");
		colTextField.setText("80");
		lineTextField.setText("1");
		columnTextField.setText("1");
		x.setName("Enter Map Definitions");
		message[0]=parent;
		options[0]=buttons;
		pane.setMessage(message);
		pane.setOptions(options);
		dialog=pane.createDialog("Map Definitions");
	}
	private void addComponents()
	{
		hor1.add(mapNameLabel);
		hor1.add(mapNameTextField);
		hor2.add(sizeLabel);
		hor2.add(new JLabel("  :  "));
		hor2.add(rowLabel);
		hor2.add(rowTextField);
		hor2.add(new JLabel(" "));
		hor2.add(colLabel);
		hor2.add(colTextField);
		hor3.add(lineLabel);
		hor3.add(lineTextField);
		hor4.add(columnLabel);
		hor4.add(columnTextField);
		hor5.add(enable);
		buttons.add(okButton);
		buttons.add(cancelButton);
		parent.add(hor1);
		parent.add(new JLabel(" "));
		parent.add(hor2);
		parent.add(new JLabel(" "));
		parent.add(hor3);
		parent.add(new JLabel(" "));
		parent.add(hor4);
		parent.add(new JLabel(" "));
		parent.add(hor5);
		parent.add(new JLabel(" "));
		okButton.addActionListener(okListener);
		cancelButton.addActionListener(cancelListener);	
	}
	private void registerKeyStrokes()
	{
		rootPane = dialog.getRootPane();
		rootPane.registerKeyboardAction(okListener, proceed,JComponent.WHEN_IN_FOCUSED_WINDOW);
		rootPane.registerKeyboardAction(cancelListener,cancel,JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	public void buildUI()
	{
		dialog.addWindowListener(this);
		registerKeyStrokes();
		dialog.setVisible(true);
		mapNameTextField.requestFocusInWindow();
	}
	private boolean allBlank(String s)
	{
		boolean f=true;
		for(char x:s.toCharArray())
		{
			if(x!=' ')
			{
				f=false;
				break;
			}
		}
		return f;
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		if(parentDialog!=null && !proceedPressed)
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run() 
				{
					try 
					{
						otf.OTF.otf.setEnabled(false);
						otf.OTF.VFE_Diag = new VFE_Dialog(otf.OTF.otf,Integer.parseInt(rowTextField.getText()),Integer.parseInt(colTextField.getText()));
						otf.OTF.VFE_Diag.buildUI();
					} 
					catch (NumberFormatException e)
					{
						JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			});
		}
		else
		{
			otf.OTF.otf.setEnabled(true);
			otf.OTF.otf.setVisible(true);
		}
	}
	private void setColor(JComponent c,boolean flag)
	{
		if(flag)
		{
			c.setBackground(Color.white);
		}
		else
		{
			c.setBackground(Color.red);
		}
	}
	private boolean validateFields()
	{
		boolean flag=true;
		boolean f[]=new boolean[5];
		int i=0;
		if(rowTextField.getText().equals("")||Integer.parseInt(rowTextField.getText())<=0||allBlank(rowTextField.getText()))
		{
			f[0]=false;
			setColor(rowTextField,f[0]);
		}
		else
		{
			f[0]=true;
			setColor(rowTextField,f[0]);
		}
		if(colTextField.getText().equals("")||Integer.parseInt(colTextField.getText())<=0||allBlank(colTextField.getText()))
		{
			f[1]=false;
			setColor(colTextField,f[1]);
			}
		else
		{
			f[1]=true;
			setColor(colTextField,f[1]);
		}
		if(lineTextField.getText().equals("")||Integer.parseInt(lineTextField.getText())<=0||allBlank(lineTextField.getText()))
		{
			f[2]=false;
			setColor(lineTextField,f[2]);
		}
		else
		{
			f[2]=true;
			setColor(lineTextField,f[2]);
		}
		if(columnTextField.getText().equals("")||Integer.parseInt(columnTextField.getText())<=0||allBlank(columnTextField.getText()))
		{
			f[3]=false;
			setColor(columnTextField,f[3]);
		}
		else
		{
			f[3]=true;
			setColor(columnTextField,f[3]);
		}
		if(mapNameTextField.getText().equals("")|| mapNameTextField.getText().charAt(0) == ' '|| mapNameTextField.getText().length() > 7||allBlank(mapNameTextField.getText()))
		{
			f[4]=false;
			setColor(mapNameTextField,f[4]);
		}
		else
		{
			f[4]=true;
			setColor(mapNameTextField,f[4]);
		}
		for(boolean x:f)
		{
			i++;
			if(x==false)
			{
				flag=false;
				break;
			}
		}
		switch(i)
		{
			case 1 : firstComp=rowTextField;
					 break;
			case 2 : firstComp=colTextField;
					 break;
			case 3 : firstComp=lineTextField;
					 break;
			case 4 : firstComp=columnTextField;
					 break;
			case 5 : firstComp=mapNameTextField;
					 break;
		}
		return flag;
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

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}
	public JPanel getPanel()
	{
		parent.remove(parent.getComponentCount()-1);
		parent.remove(parent.getComponentCount()-1);
		x.removeAll();
		x.add(parent);
		return x;
	}
}
