package utils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JComponent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class NotificationUI extends JDialog
{
	/**
	 * Anirban Das
	 */
	private static final long serialVersionUID = -1678273431759941433L;
	private int WIN_HOR_LEN,WIN_VER_LEN,LENGTH,TEXT_HOR_LEN,TEXT_VER_LEN,TIME,SLOT;
	private String title,message[];
	private boolean messageStatus[];
	private Font f;
	private ImageIcon icTick,icCross;
	private JPanel pBar,l,button,body;
	private JProgressBar progressBar;
	private JLabel label[];
	private JButton ok;
	private JFrame parent;
	public JButton start;
	private DimensionsInPixels dip;
	private JOptionPane j;
	private Object x[],y[],msg[];
	
	public NotificationUI(JFrame parent,String title, String message[], boolean messageStatus[],int TIME)
	{
		super(parent);
		this.parent=parent;
		this.title=title;
		this.message=message;
		this.messageStatus=messageStatus;
		LENGTH=message.length;
		this.TIME=TIME;
		WIN_VER_LEN=0;
		WIN_HOR_LEN=0;
		SLOT=0;
		f=new JLabel().getFont();
		new SetLAF();
		x=new Object[1];
		y=new Object[1];
		msg=new Object[1+LENGTH];
	}
	public NotificationUI(JFrame parent,String title, String message[], boolean messageStatus[],int TIME,Font f)
	{
		super(parent);
		this.title=title;
		this.message=message;
		this.messageStatus=messageStatus;
		this.TIME=TIME;
		WIN_VER_LEN=0;
		WIN_HOR_LEN=0;
		LENGTH=message.length;
		this.f=f;
		new SetLAF();
		x=new Object[1];
		y=new Object[1];
		msg=new Object[1+LENGTH];
	}
	private void createComponents()
	{
		int i=0;
		pBar=new JPanel(new GridLayout(3,0));
		l=new JPanel(new GridLayout(LENGTH,0));
		button=new JPanel(new GridLayout(3,SLOT));
		body=new JPanel(new GridLayout(3,0));
		progressBar=new JProgressBar();
		CreateIcon ci=new CreateIcon();
		icTick=ci.createImageIcon("correct.png");
		icCross=ci.createImageIcon("cross.png");
		label=new JLabel[LENGTH];
		for(;i<LENGTH;i++)
		{
			label[i]=new JLabel("");
		}
		ok=new JButton("OK");
		start=new JButton("START");
		j=new JOptionPane();
	}
	private void setUpComponents()
	{
		body.setBorder(new EmptyBorder(5,5,5,5));
		pBar.setBorder(new EmptyBorder(2,2,2,2));
		l.setBorder(new EmptyBorder(2,2,2,2));
		button.setBorder(new EmptyBorder(2,2,2,2));
		progressBar.setMaximum(0);
		progressBar.setMaximum(LENGTH);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		for(int i=0;i<LENGTH;i++)
		{
			label[i].setFont(f);
			label[i].setPreferredSize(new Dimension(TEXT_HOR_LEN+10, TEXT_VER_LEN+10));
		}
		ok.setEnabled(false);
		JPanel inter=new JPanel();
		inter.setPreferredSize(ok.getPreferredSize());
		x[0]=inter;
		y[0]=ok;
		msg[0]=pBar;
		for(int loop=0;loop<LENGTH;loop++)
		{
			msg[loop+1]=label[loop];
		}
		
		j.setMessage(msg);
		j.setOptions(x);
	}
	private void addComponents()
	{
		pBar.add(progressBar,BorderLayout.NORTH);
		pBar.add(new JLabel(""),BorderLayout.CENTER);
		pBar.add(new JLabel(""),BorderLayout.SOUTH);
		l.add(label[0],BorderLayout.NORTH);
		for(int i=1;i<LENGTH-1;i++)
		{
			l.add(label[i],BorderLayout.CENTER);
		}
		l.add(label[LENGTH-1],BorderLayout.SOUTH);
		for(int i=0;i<2;i++)
		{
			button.add(new JLabel(""),BorderLayout.LINE_START);
			for(int j=1;j<SLOT-1;j++)
			{
				button.add(new JLabel(""),BorderLayout.CENTER);
			}
			button.add(new JLabel(""),BorderLayout.LINE_END);
		}
		button.add(new JLabel(""),BorderLayout.LINE_START);
		for(int j=1;j<(SLOT-1)/2;j++)
		{
			button.add(new JLabel(""),BorderLayout.CENTER);
		}
		button.add(ok,BorderLayout.CENTER);
		for(int j=1;j<(SLOT-1)/2;j++)
		{
			button.add(new JLabel(""),BorderLayout.CENTER);
		}
		button.add(new JLabel(""),BorderLayout.LINE_END);
		body.add(pBar,BorderLayout.NORTH);
		body.add(l,BorderLayout.CENTER);
		body.add(button,BorderLayout.SOUTH);
		ok.addActionListener(okListener);
		start.addActionListener(generateListener);
    }
	private void getWindowDimensionsInPixels()
	{
		WIN_HOR_LEN=label[0].getPreferredSize().width+130;
		WIN_VER_LEN=(label[0].getPreferredSize().height*LENGTH)+((LENGTH+1)*2)+250;
	}
	private void registerKeyStrokes()
	{
		KeyStroke keyStroke1 = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
			JRootPane rootPane = getRootPane();
			rootPane.registerKeyboardAction(okListener, keyStroke1,JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	ActionListener okListener=new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			dispose();
		}
	};
	private void paintComponent(JComponent c)
	{
		 Rectangle r=c.getBounds();
	     r.x=0;
	     r.y=0;
	     c.paintImmediately(r);
	}
	public void showUI()
	{
			String a[]=Arrays.copyOf(message, message.length+1);
			a[a.length-1]=title;
			dip=new DimensionsInPixels();
			Dimension d=dip.getTextDimensionsInPixels(f,a);
			TEXT_HOR_LEN=d.width;
			TEXT_VER_LEN=d.height;
			SLOT=TEXT_HOR_LEN/42;
			//SLOT=TEXT_HOR_LEN/39;
			if(SLOT%2==0)
			{
				SLOT=SLOT+1;
			}
			createComponents();
			setUpComponents();
			registerKeyStrokes();
			addComponents();
			getWindowDimensionsInPixels();
			setTitle(title);
			setContentPane(body);
			setSize(WIN_HOR_LEN,WIN_VER_LEN);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(parent);
			setVisible(true);
			requestFocusInWindow();
	}
	ActionListener generateListener=new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			showNotifications();
		}
	};
	public void showNotifications()
	{
		try
		{
			int i;
			String flag=" : Success";
			for(i=0;i<LENGTH;i++)
			{
				this.paint(getGraphics());
				Thread.sleep(TIME);
				if(messageStatus[i])
				{
					progressBar.setValue(i+1);
					paintComponent(progressBar);
					label[i].setText(message[i]);
					label[i].setIcon(icTick);
					paintComponent(label[i]);
				}
				else
				{
					label[i].setText(message[i]);
					label[i].setIcon(icCross);
					paintComponent(label[i]);
					flag=" : Faliure";
					break;
				}
			}
			setTitle(title+flag);
			ok.setEnabled(true);
			ok.requestFocusInWindow();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.toString(),"Error",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}