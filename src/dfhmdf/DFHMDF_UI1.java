package dfhmdf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dfhmdi.DFHMDI_UI1;
import vfe.ColorPanel;


/**
 * This class is used to generate the UI of the field properties. It takes in
 * all the properties of a field and sends it to the form editor.
 * 
 * @author NIRUPAM and Anirban Das
 * 
 */
public class DFHMDF_UI1  implements WindowListener
{
	public static final int FIXED=1,VARIABLE=2,ARRAY=3;
	public JDialog dialog;
	private JOptionPane pane;
	private Object message[],option[];
	private JLabel field_nameLabel,commentLabel,posLabel,rowLabel,columnLabel,attrbLabel , lengthLabel,picinLabel,picoutLabel,initialLabel,occurLabel;
	private JButton okButton,cancelButton,colorButton;
	private JRadioButton askipRadio,protRadio,unprotRadio,normRadio, brtRadio, drkRadio ,invisible1 , invisible2 ;
	private ButtonGroup group1,group2;
	private Box hBox[],vBox[],field,comment,attrb,pos,length,pic,initial,contentPanel,controlPanel;
	private JCheckBox icCheck,fsetCheck,numCheck;
	private JTextField field_nameText,rowText, columnText,lengthText, picinText,picoutText,initialText,commentText,occurText;
	private JRootPane rootPane;
	private JComponent firstComp;
	private KeyStroke enter,escape;
	private boolean empty,done;
	private int mode;
	private String color;
	private DFHMDF fieldProperty;
	
	public DFHMDF_UI1() 
	{
		createComponents();
		addComponents();
		init();
		setUpComponents();
		new utils.SetLAF();
	}
	private void createComponents()
	{
		contentPanel=Box.createVerticalBox();
		controlPanel=Box.createHorizontalBox();
		hBox=new Box[8];
		for (int i = 0; i < hBox.length; i++)
		{
			hBox[i]=Box.createHorizontalBox();
		}
		vBox=new Box[3];
		for (int i = 0; i < vBox.length; i++)
		{
			vBox[i]=Box.createVerticalBox();
		}
		field_nameLabel = new JLabel("Field Name : ");
		commentLabel = new JLabel("Comment : ");
		posLabel = new JLabel("POS*");
		rowLabel = new JLabel("Row : ");
		columnLabel = new JLabel("Column : ");
		attrbLabel = new JLabel("ATTRB*");
		lengthLabel = new JLabel("Length : ");
		picinLabel = new JLabel("PICIN");
		picoutLabel = new JLabel("PICOUT");
		initialLabel = new JLabel("Initial : ");
		occurLabel=new JLabel("Occur : ");
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		colorButton = new JButton("Choose Color");
		askipRadio = new JRadioButton("Askip");
		protRadio = new JRadioButton("Prot");
		unprotRadio = new JRadioButton("Unprot");
		normRadio = new JRadioButton("Norm");
		brtRadio = new JRadioButton("Brt");
		drkRadio = new JRadioButton("Drk");
		invisible1 = new JRadioButton();
		invisible2 = new JRadioButton();
		group1 = new ButtonGroup();
		group2 = new ButtonGroup();
		field = Box.createHorizontalBox();
		comment = Box.createHorizontalBox();
		length = Box.createHorizontalBox();
		pic = Box.createHorizontalBox();
		initial = Box.createHorizontalBox();
		pos = Box.createVerticalBox();
		attrb = Box.createVerticalBox();
		icCheck = new JCheckBox("Ic");
		fsetCheck = new JCheckBox("Fset");
		numCheck = new JCheckBox("Num");
		field_nameText = new JTextField(15);
		rowText = new JTextField(5);
		columnText = new JTextField(5);
		lengthText = new JTextField(5);
		occurText=new JTextField(3);
		picinText = new JTextField();
		picoutText = new JTextField();
		initialText = new JTextField();
		commentText = new JTextField();
		pane=new JOptionPane();
		message=new Object[1];
		option=new Object[1];
		empty = true;
		done = false;
		color = "green";
		enter=KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		escape=KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0);
	}
	private void addComponents()
	{
		hBox[0].add(field_nameLabel,BorderLayout.WEST);
		hBox[0].add(field_nameText,BorderLayout.CENTER);
		field.add(hBox[0]);
		
		hBox[1].add(commentLabel,BorderLayout.WEST);
		hBox[1].add(commentText,BorderLayout.EAST);
		comment.add(hBox[1]);
		
		hBox[2].add(rowLabel,BorderLayout.WEST);
		hBox[2].add(rowText,BorderLayout.CENTER);
		hBox[2].add(new JLabel("    "),BorderLayout.CENTER);
		hBox[2].add(columnLabel,BorderLayout.CENTER);
		hBox[2].add(columnText,BorderLayout.EAST);
		pos.add(posLabel);
		pos.add(hBox[2]);
		
		
		group1.add(askipRadio);
		group1.add(protRadio);
		group1.add(unprotRadio);
		group1.add(invisible1);
		group2.add(normRadio);
		group2.add(brtRadio);
		group2.add(drkRadio);
		group2.add(invisible2);
		vBox[0].add(askipRadio);
		vBox[0].add(protRadio);
		vBox[0].add(unprotRadio);
		vBox[1].add(normRadio);
		vBox[1].add(brtRadio);
		vBox[1].add(drkRadio);
		vBox[2].add(icCheck);
		vBox[2].add(fsetCheck);
		vBox[2].add(numCheck);
		hBox[3].add(vBox[0]);
		hBox[3].add(vBox[1]);
		hBox[3].add(vBox[2]);
		attrb.add(attrbLabel);
		attrb.add(hBox[3]);
		
		length.add(lengthLabel);
		length.add(lengthText);
		length.add(new JLabel("    "));
		length.add(occurLabel);
		length.add(occurText);
		length.add(new JLabel("    "));
		length.add(colorButton);
		
		pic.add(picinLabel);
		pic.add(picinText);
		pic.add(picoutLabel);
		pic.add(picoutText);
		
		initial.add(initialLabel);
		initial.add(initialText);
		
		contentPanel.add(field);
		contentPanel.add(comment);
		contentPanel.add(pos);
		contentPanel.add(attrb);
		contentPanel.add(length);
		contentPanel.add(initial);
		
		controlPanel.add(okButton);
		controlPanel.add(cancelButton);
		
		message[0]=contentPanel;
		option[0]=controlPanel;
		
		okButton.addActionListener(okListener);
		colorButton.addActionListener(colorListener);
		cancelButton.addActionListener(cancelListener);
	}
	private void init()
	{
		if (otf.OTF.isNew)
		{
			if (DFHMDI_UI1.enable.isSelected())
			{
				colorButton.setEnabled(true);
			}
			else
			{
				colorButton.setEnabled(false);
				color = "green";
			}
		} 
		else
		{
			Document xdoc = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try 
			{
				DocumentBuilder fxml = dbf.newDocumentBuilder();
				xdoc = fxml.parse(otf.OTF.xmlFile);
				xdoc.normalize();
			}
			catch (Exception e) 
			{
				JOptionPane.showMessageDialog(null, e.toString(), "Error",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			NodeList dfhmdi = xdoc.getElementsByTagName("DFHMDI");
			Node node = dfhmdi.item(otf.OTF.num);
			String str = getValue("MAPATTS", node);
			if (str.equals("(COLOR,HILIGHT)"))
			{
				colorButton.setEnabled(true);
			}
			else
			{
				colorButton.setEnabled(false);
				color = "green";
			}
		}
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
	private void setUpComponents()
	{
		contentPanel.setBorder(new EmptyBorder(5,5,5,5));
		for (int i = 0; i < hBox.length; i++)
		{
			hBox[i].setBorder(new EmptyBorder(5,5,5,5));
		}
		for (int i = 0; i < vBox.length; i++)
		{
			vBox[i].setBorder(new EmptyBorder(5,25,5,25));
		}
		length.setBorder(new EmptyBorder(5,5,5,5));
		initial.setBorder(new EmptyBorder(5,5,5,5));
		controlPanel.setBorder(new EmptyBorder(5,5,5,5));
		pane.setMessage(message);
		pane.setOptions(option);
		dialog=pane.createDialog("Field Properties");
		dialog.addWindowListener(this);
		rootPane=dialog.getRootPane();
		rootPane.registerKeyboardAction(okListener, enter,JComponent.WHEN_IN_FOCUSED_WINDOW);
		rootPane.registerKeyboardAction(cancelListener, escape,JComponent.WHEN_IN_FOCUSED_WINDOW);
		dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
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
	FocusListener fl=new FocusListener()
	{
		public void focusLost(FocusEvent e) 
		{
			lengthListener.actionPerformed(new ActionEvent(e.getSource(), e.getID(), "focusLost"));
		}
		public void focusGained(FocusEvent e) 
		{
			
		}
	};
	private ActionListener okListener = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(validateFields())
			{
				setFieldProperty();
				if (done)
				{
					dialog.setVisible(false);
				}
				done = false;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Rectify Field(s) marked in red!","Error", JOptionPane.ERROR_MESSAGE);
				firstComp.requestFocusInWindow();
			}
		}
	};
	ActionListener colorListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e) 
		{
			JColorChooser tcc = new JColorChooser();
			AbstractColorChooserPanel panels[] = { new ColorPanel() };
			tcc.setChooserPanels(panels);
			JDialog d = JColorChooser.createDialog(null, "Choose Color", true,tcc,null, null);
			d.setVisible(true);
			d.setSize(300, 500);
			color = getColor(tcc.getColor());
			d.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		}
	};
	private ActionListener cancelListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			dialog.setVisible(false);
		}
	};
	ActionListener lengthListener = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			lengthText.setText(initialText.getText().length() + "");
		}
	};
	public synchronized DFHMDF getFieldProperty() 
	{
		while (empty)
		{
			try
			{
				wait();
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		};
		empty = true;
		return fieldProperty;
	}
	public synchronized void setFieldProperty() 
	{
		if (!isEmpty())
		{
			if (empty)
			{
				try
				{
					fieldProperty = new DFHMDF();
					fieldProperty.setPos(Integer.parseInt(rowText.getText()),Integer.parseInt(columnText.getText()));
					fieldProperty.setName(field_nameText.getText());
					fieldProperty.setAttrb(attrbTextGenerator());
					fieldProperty.setPicin(picinText.getText());
					fieldProperty.setPicout(picoutText.getText());
					fieldProperty.setColor(color);
					fieldProperty.setComment(commentText.getText());
					fieldProperty.setLength(Integer.parseInt(lengthText.getText()));
					fieldProperty.setOccur(Integer.parseInt(occurText.getText()));
					fieldProperty.setInitial(initialText.getText());
					empty = false;
					done = true;
					notifyAll();
				}
				catch (NumberFormatException ex) 
				{
					JOptionPane.showMessageDialog(null, "Invalid Input "+ex.toString(),"Error", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Fill in all the fields","Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setPosText(int x, int y) 
	{
		rowText.setText(Integer.toString(y));
		columnText.setText(Integer.toString(x));
	}

	/**
	 * This method automatically sets certain values when the field is a fixed
	 * field.
	 * 
	 * @param isFixed
	 */
	public void setFieldTypeDetails(int mode)
	{
		this.mode=mode;
		if (mode==FIXED) 
		{
			protRadio.setSelected(true);
			normRadio.setSelected(true);
			field_nameLabel.setEnabled(false);
			field_nameText.setEnabled(false);
			commentLabel.setEnabled(false);
			commentText.setEnabled(false);
			occurLabel.setEnabled(false);
			occurText.setEnabled(false);
			occurText.setFocusable(false);
		} 
		if(mode==VARIABLE)
		{
			group1.clearSelection();
			group2.clearSelection();
			field_nameLabel.setEnabled(true);
			field_nameText.setEnabled(true);
			commentLabel.setEnabled(true);
			commentText.setEnabled(true);
			occurLabel.setEnabled(false);
			occurText.setEnabled(false);
			occurText.setFocusable(false);
		}
		if(mode==ARRAY)
		{
			field_nameLabel.setEnabled(true);
			field_nameText.setEnabled(true);
			group1.clearSelection();
			group2.clearSelection();
			askipRadio.setSelected(true);
			normRadio.setSelected(true);
			commentLabel.setEnabled(true);
			commentText.setEnabled(true);
			occurLabel.setEnabled(true);
			occurText.setEnabled(true);
			occurText.setFocusable(true);		
		}
	}

	/**
	 * Build the complete UI of the DFHMDF dialog window
	 */
	public void buildUI() 
	{
		setColor(rowText,true);
		setColor(columnText,true);
		setColor(lengthText,true);
		setColor(initialText,true);
		setColor(field_nameText,true);
		setColor(commentText,true);
		setColor(occurText,true);
		switch(mode)
		{
			case FIXED : initialText.requestFocusInWindow();
						 dialog.setTitle("Field Properties : Text Field");
						 break;
			case VARIABLE : field_nameText.requestFocusInWindow();
							dialog.setTitle("Field Properties : Variable Field");
							break;
			case ARRAY : field_nameText.requestFocusInWindow();
						 dialog.setTitle("Field Properties : Array Field");
						 break;
		}
		dialog.setVisible(true);
		otf.OTF.VFE_Diag.setEnabled(false);
	}


	private String getColor(Color c) {
		if (c == null)
			return "green";
		else if (c.equals(Color.red))
			return "red";
		else if (c.equals(Color.green))
			return "green";
		else if (c.equals(Color.blue))
			return "blue";
		else if (c.equals(Color.yellow))
			return "yellow";
		else if (c.equals(Color.pink))
			return "pink";
		else if (c.equals(new Color(64, 224, 208)))
			return "turquoise";
		else if (c.equals(Color.white))
			return "neutral";
		else
			return "green";
	}
	/*
	 * Generates the ATTRB text value from the selected radio buttons and check
	 * boxes.
	 * 
	 * @return String the generated ATTRB value
	 */
	private String attrbTextGenerator() 
	{
		String s = "(";
		if (askipRadio.isSelected())
			s += "ASKIP";
		else if (protRadio.isSelected())
			s += "PROT";
		else if (unprotRadio.isSelected())
			s += "UNPROT";

		if (normRadio.isSelected())
			s += ",NORM";
		else if (brtRadio.isSelected())
			s += ",BRT";
		else if (drkRadio.isSelected())
			s += ",DRK";

		if (icCheck.isSelected())
			s += ",IC";
		if (fsetCheck.isSelected())
			s += ",FSET";
		if (numCheck.isSelected())
			s += ",NUM";

		s += ")";

		return s;
	}

	private boolean isEmpty() 
	{
		if (rowText.getText() != null && columnText.getText() != null && lengthText.getText() != null) 
		{
			if (!askipRadio.isSelected()) 
			{
				if (initialText.getText().equals("") && !field_nameText.isEnabled())
				{
					return true;
				}
			}
			if (field_nameText.isEnabled() && !askipRadio.isSelected()) 
			{
				if (!field_nameText.getText().equals(""))
				{
					return false;
				}
				else
				{
					return true;
				}
			} 
			else
			{
				return false;
			}
		} 
		else
		{
			return true;
		}
	}

	public void setComponentValues(DFHMDF dfhmdf) 
	{
		clearAll();
		invisible1.setSelected(true);
		invisible2.setSelected(true);
		String name = dfhmdf.getName();
		if (name == null || name.equals("")) 
		{
			field_nameLabel.setEnabled(false);
			field_nameText.setEnabled(false);
			commentLabel.setEnabled(false);
			commentText.setEnabled(false);
		}
		else
		{
			field_nameLabel.setEnabled(true);
			field_nameText.setEnabled(true);
			commentLabel.setEnabled(true);
			commentText.setEnabled(true);
			field_nameText.setText(name);
			commentText.setText(dfhmdf.getComment());
		}
		lengthText.setText(Integer.toString(dfhmdf.getLength()));
		occurText.setText(Integer.toString(dfhmdf.getOccur()));
		initialText.setText(dfhmdf.getInitial());
		setAttrb(dfhmdf.getAttrb());
		color = dfhmdf.getColor();
		picinText.setText(dfhmdf.getPicin());
		picoutText.setText(dfhmdf.getPicout());
		String pos = dfhmdf.getPos();
		pos = pos.substring(1, pos.length() - 1);
		rowText.setText(pos.split(",")[0]);
		columnText.setText(pos.split(",")[1]);
		System.out.println(pos);
	}

	/**
	 * Sets the radio buttons and check boxes according to the ATTRB property of
	 * DFHMDF
	 * 
	 * @param attrb
	 */
	private void setAttrb(String attrb) 
	{
		if (attrb.contains("ASKIP"))
			askipRadio.setSelected(true);
		else if (attrb.contains("UNPROT"))
			unprotRadio.setSelected(true);
		else if (attrb.contains("PROT"))
			protRadio.setSelected(true);

		if (attrb.contains("NORM"))
			normRadio.setSelected(true);
		else if (attrb.contains("BRT"))
			brtRadio.setSelected(true);
		else if (attrb.contains("DRK"))
			drkRadio.setSelected(true);

		if (attrb.contains("IC"))
			icCheck.setSelected(true);
		if (attrb.contains("FSET"))
			fsetCheck.setSelected(true);
		if (attrb.contains("NUM"))
			numCheck.setSelected(true);
	}

	/**
	 * This method is used for clearing all the fields
	 */
	public void clearAll()
	{
		group1.clearSelection();
		group2.clearSelection();
		icCheck.setSelected(false);
		fsetCheck.setSelected(false);
		numCheck.setSelected(false);

		field_nameText.setText("");
		commentText.setText("");
		lengthText.setText("0");
		occurText.setText("0");
		picinText.setText("");
		picoutText.setText("");
		initialText.setText("");
	}

	private String getValue(String tag, Node node) 
	{
		try
		{
			NodeList childNodes = node.getChildNodes();
			int i;
			for (i = 0; i < childNodes.getLength(); i++) 
			{
				if (childNodes.item(i).getNodeName().equals(tag))
					break;
			}
			String nodeValue = "";
			nodeValue = childNodes.item(i).getTextContent();
			return nodeValue;
		}
		catch (NullPointerException a)
		{
			return "";
		}

	}
	private boolean validateFields()
	{
		boolean flag=true;
		boolean f[]=new boolean[6];
		Arrays.fill(f,true);
		int i=0;
		if(mode==FIXED)
		{
			if(rowText.getText().equals("")||Integer.parseInt(rowText.getText())<=0||allBlank(rowText.getText()))
			{
				f[0]=false;
				setColor(rowText,f[0]);
			}
			else
			{
				f[0]=true;
				setColor(rowText,f[0]);
			}
			if(columnText.getText().equals("")||Integer.parseInt(columnText.getText())<=0||allBlank(columnText.getText()))
			{
				f[1]=false;
				setColor(columnText,f[1]);
			}
			else
			{
				f[1]=true;
				setColor(columnText,f[1]);
			}
			if(lengthText.getText().equals("")||allBlank(lengthText.getText()))
			{
				f[2]=false;
				setColor(lengthText,f[2]);
			}
			else
			{
				f[2]=true;
				setColor(lengthText,f[2]);
			}
			if(initialText.getText().equals("")||allBlank(initialText.getText()))
			{
				f[3]=false;
				setColor(initialText,f[3]);
			}
			else
			{
				f[3]=true;
				setColor(initialText,f[3]);
			}
		}
		if(mode==VARIABLE)
		{
			if(rowText.getText().equals("")||Integer.parseInt(rowText.getText())<=0||allBlank(rowText.getText()))
			{
				f[0]=false;
				setColor(rowText,f[0]);
			}
			else
			{
				f[0]=true;
				setColor(rowText,f[0]);
			}
			if(columnText.getText().equals("")||Integer.parseInt(columnText.getText())<=0||allBlank(columnText.getText()))
			{
				f[1]=false;
				setColor(columnText,f[1]);
			}
			else
			{
				f[1]=true;
				setColor(columnText,f[1]);
			}
			if(lengthText.getText().equals("")||Integer.parseInt(lengthText.getText())<0||allBlank(lengthText.getText()))
			{
				f[2]=false;
				setColor(lengthText,f[2]);
			}
			else
			{
				f[2]=true;
				setColor(lengthText,f[2]);
			}
			if(initialText.getText().equals("")||allBlank(initialText.getText()))
			{
				f[3]=false;
				setColor(initialText,f[3]);
			}
			else
			{
				f[3]=true;
				setColor(initialText,f[3]);
			}
			if(field_nameText.getText().equals("")||allBlank(field_nameText.getText())||!isValidField(field_nameText.getText()))
			{
				f[4]=false;
				setColor(field_nameText,f[4]);
			}
			else
			{
				f[4]=true;
				setColor(field_nameText,f[4]);
			}
		}
		if(mode==ARRAY)
		{
			if(rowText.getText().equals("")||Integer.parseInt(rowText.getText())<=0)
			{
				f[0]=false;
				setColor(rowText,f[0]);
			}
			else
			{
				f[0]=true;
				setColor(rowText,f[0]);
			}
			if(columnText.getText().equals("")||Integer.parseInt(columnText.getText())<=0)
			{
				f[1]=false;
				setColor(columnText,f[1]);
			}
			else
			{
				f[1]=true;
				setColor(columnText,f[1]);
			}
			if(lengthText.getText().equals("")||Integer.parseInt(lengthText.getText())<=0)
			{
				f[2]=false;
				setColor(lengthText,f[2]);
			}
			else
			{
				f[2]=true;
				setColor(lengthText,f[2]);
			}
			if(initialText.getText().equals("")||allBlank(initialText.getText()))
			{
				f[3]=false;
				setColor(initialText,f[3]);
			}
			else
			{
				f[3]=true;
				setColor(initialText,f[3]);
			}
			if(field_nameText.getText().equals("")||allBlank(field_nameText.getText())||!isValidField(field_nameText.getText()))
			{
				f[4]=false;
				setColor(field_nameText,f[4]);
			}
			else
			{
				f[4]=true;
				setColor(field_nameText,f[4]);
			}
			if(occurText.getText().equals("")||Integer.parseInt(occurText.getText())<=0||allBlank(occurText.getText())
					)
			{
				f[5]=false;
				setColor(occurText,f[5]);
			}
			else
			{
				f[5]=true;
				setColor(occurText,f[5]);
			}
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
			case 1 : firstComp=rowText;
					 break;
			case 2 : firstComp=columnText;
					 break;
			case 3 : firstComp=lengthText;
					 break;
			case 4 : firstComp=initialText;
					 break;
			case 5 : firstComp=field_nameText;
					 break;
			case 6 : firstComp=commentText;
					 break;
			case 7 : firstComp=occurText;
					 break;
		}
		return flag;
	}
	private boolean isValidField(String text) 
	{
		if(text.length()>8)
		{
			return false;
		}
		else
		{
			boolean flag = true;
			for(char x:text.toCharArray())
			{
				int ascii=x;
				if((ascii>=65&&ascii<=90)||(ascii>=97&&ascii<=122)||x=='$'||x=='#'||x=='_'||x=='@'||ascii>=48&&ascii<=57)
				{
					flag=true;
				}
				else
				{
					flag=false;
					break;
				}
			}
			return flag;
		}
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
		// getParent().setEnabled(false);
		otf.OTF.VFE_Diag.setEnabled(false);
		otf.OTF.otf.setEnabled(false);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		// getParent().setEnabled(true);
		otf.OTF.VFE_Diag.setVisible(true);
		otf.OTF.VFE_Diag.setEnabled(true);
		otf.OTF.otf.setEnabled(true);
	}
}
