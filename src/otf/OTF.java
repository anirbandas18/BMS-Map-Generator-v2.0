package otf;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.net.URI;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.apache.commons.net.ftp.FTPClient;

import dfhmdi.DFHMDI_UI1;
import dfhmdi.RetrieveDFHMDI;
import utils.CreateIcon;
import utils.DimensionsInPixels;
import utils.DirectoryHandler;
import utils.QuickFTP;
import vfe.VFE_Dialog;
import logger.CustomLogger;
/**
 * @author Sounak Roy and Anirban Das
 * 
 */
public class OTF 
{
	public static JFrame otf;
	private JMenuBar mainMenu;
	private JMenu fileMenu, projectMenu, helpMenu;
	private JMenuItem newItem, openItem, exitItem, addMap, about,helpItem,vidTutItem;
	private JLabel file,project,help;
	private JButton newP,open,exit,add,helpC,vid,abt;
	private JPanel leftPanel,centerPanel,rightPanel[];
	private JSeparator s;
	private ImageIcon blank,arrow,newIcon,openIcon,exitIcon,addIcon,helpIcon,vidIcon,aboutIcon;
	private JRootPane rootPane;
	private KeyStroke ctrlN,ctrlO,esc,ctrlA,ctrlH,ctrlV,ctrlAB;
	private Border emptyBorder,bevelBorder,etchedBorder;
	private DateFormatSymbols dfs ;
	private Dimension otfDim,textDim,labelDim;
	private Font normal,bold;
	private Box b,parent;
	private String month ;
	private JComponent c[];
	private DimensionsInPixels dip;
	private DirectoryHandler dh;
	private QuickFTP qftp;
	private static final int gap=50;
	public File Temp;
	public static File xmlFile;
	public static int num, lastMap, currentMap, mapNumber;
	public static String mapsetName,mapName,bms_pds,temp_pds,presentMonth,tso_id,host,password,port;
	public static FTPClient ftp;
	public static boolean isNew;
	public static Calendar cal;
	public static CustomLogger log;
	public static VFE_Dialog VFE_Diag;
	public static Preferences pref;
	public static String mainframePassword;
	public OTF otf1;
	
	public OTF() 
	{
		month= "wrong";
		mainframePassword = "";
		dh=new DirectoryHandler();
		dip=new DimensionsInPixels();
		new utils.SetLAF();
		mapNumber = 0;
	}
	private void createComponents()
	{
		cal = Calendar.getInstance();
		dfs= new DateFormatSymbols();
		leftPanel=new JPanel(new GridLayout(7,0,0,10));
		centerPanel=new JPanel(new GridLayout(0,1,0,0));
		rightPanel=new JPanel[3];
		for(int i=0;i<3;i++)
		{
			rightPanel[i]=new JPanel(new GridLayout(2,2,200,160));
		}
		CreateIcon ci=new CreateIcon();
		blank=ci.createImageIcon("blank.png");
		arrow=ci.createImageIcon("arrow.png");
		newIcon=ci.createImageIcon("new.png");
		openIcon=ci.createImageIcon("open.png");
		exitIcon=ci.createImageIcon("exit.png");
		addIcon=ci.createImageIcon("add.png");
		helpIcon=ci.createImageIcon("help.png");
		vidIcon=ci.createImageIcon("video.png");
		aboutIcon=ci.createImageIcon("about.png");
		file=new JLabel("File   ");
		project=new JLabel("Project   ");
		help=new JLabel("Help   ");
		newP=new JButton("New Project");
		open=new JButton("Open Project");
		exit=new JButton("Exit");
		add=new JButton("Add New Map");
		helpC=new JButton("Help Contents");
		vid=new JButton("Video Tutorial");
		abt=new JButton("About");
		s=new JSeparator(JSeparator.VERTICAL);
		b=Box.createHorizontalBox();
		parent=Box.createHorizontalBox();
		otfDim=Toolkit.getDefaultToolkit().getScreenSize();
		otf = new JFrame();
		rootPane=otf.getRootPane();
		bevelBorder=BorderFactory.createBevelBorder(BevelBorder.RAISED);
		etchedBorder=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		emptyBorder=BorderFactory.createEmptyBorder(50,50,50,50);
		pref = Preferences.userRoot().node("BMS Map Generator");
		normal=new Font("Tahoma",Font.PLAIN,20);
		bold=new Font("Tahoma",Font.BOLD,25);
		c=new JComponent[7];
		qftp=new QuickFTP();
		
		newItem = new JMenuItem("New Project");
		openItem = new JMenuItem("Open Project");
		exitItem = new JMenuItem("Exit");
		addMap = new JMenuItem("Add New Map");
		helpItem=new JMenuItem("Help Topics");
		vidTutItem=new JMenuItem("Video Tutorial");
		about = new JMenuItem("About");
		fileMenu = new JMenu("File");
		projectMenu =new JMenu("Project");
		helpMenu =new JMenu("Help");
		mainMenu = new JMenuBar();
		
	}
	private void createKeyStrokes()
	{
		ctrlN=KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK);
		ctrlO=KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK);
		esc=KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0);
		ctrlA=KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK);
		ctrlH=KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK);
		ctrlV=KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK);
		ctrlAB=KeyStroke.getKeyStroke(KeyEvent.VK_B,InputEvent.CTRL_MASK);
	}
	private void registerKeyStrokes()
	{
		rootPane.registerKeyboardAction(exitListener,esc,JComponent.WHEN_IN_FOCUSED_WINDOW);
		rightPanel[0].registerKeyboardAction(newListener, ctrlN, JComponent.WHEN_IN_FOCUSED_WINDOW);
		rightPanel[0].registerKeyboardAction(openListener, ctrlO, JComponent.WHEN_IN_FOCUSED_WINDOW);
		rightPanel[0].registerKeyboardAction(exitListener, esc,  JComponent.WHEN_IN_FOCUSED_WINDOW);
		rightPanel[1].registerKeyboardAction(addMapListener, ctrlA,  JComponent.WHEN_IN_FOCUSED_WINDOW);
		rightPanel[2].registerKeyboardAction(helpListener, ctrlH,  JComponent.WHEN_IN_FOCUSED_WINDOW);
		rightPanel[2].registerKeyboardAction(vidTutListener, ctrlV,  JComponent.WHEN_IN_FOCUSED_WINDOW);
		rightPanel[2].registerKeyboardAction(aboutListener, ctrlAB,  JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	private void setUpComponents()
	{
		labelDim=new Dimension(textDim.width+gap+arrow.getIconWidth()+400,textDim.height+arrow.getIconHeight()+200);
		String[] months = dfs.getMonths();
		if (cal.get(Calendar.MONTH) >= 0 && cal.get(Calendar.MONTH) <= 11)
		{
			month = months[cal.get(Calendar.MONTH)];
		}
		presentMonth = month + "-" + cal.get(Calendar.YEAR);
		file.setBorder(etchedBorder);
		project.setBorder(etchedBorder);
		help.setBorder(etchedBorder);
		file.setPreferredSize(labelDim);
		project.setPreferredSize(labelDim);
		help.setPreferredSize(labelDim);
		file.setIconTextGap(gap);
		project.setIconTextGap(gap);
		help.setIconTextGap(gap);
		newP.setIconTextGap(gap);
		open.setIconTextGap(gap);
		exit.setIconTextGap(gap);
		add.setIconTextGap(gap);
		helpC.setIconTextGap(gap);
		vid.setIconTextGap(gap);
		abt.setIconTextGap(gap);
		newP.setMnemonic('N');
		open.setMnemonic('O');
		exit.setMnemonic(27);
		add.setMnemonic('A');
		helpC.setMnemonic('H');
		vid.setMnemonic('V');
		abt.setMnemonic('B');
		newP.setFont(bold);
		open.setFont(bold);
		exit.setFont(bold);
		add.setFont(bold);
		helpC.setFont(bold);
		vid.setFont(bold);
		abt.setFont(bold);
		newP.setIcon(newIcon);
		open.setIcon(openIcon);
		exit.setIcon(exitIcon);
		add.setIcon(addIcon);
		helpC.setIcon(helpIcon);
		vid.setIcon(vidIcon);
		abt.setIcon(aboutIcon);
		otf.setSize(otfDim);
		otf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		otf.setLayout(new GridBagLayout());
		leftPanel.setBorder(emptyBorder);
		centerPanel.setBorder(emptyBorder);
		centerPanel.setPreferredSize(new Dimension(2,2));
		for(int i=0;i<3;i++)
		{
			rightPanel[i].setBorder(emptyBorder);
		}
		c[0]=leftPanel;
		c[1]=file;
		c[2]=project;
		c[3]=help;
		c[4]=rightPanel[0];
		c[5]=rightPanel[1];
		c[6]=rightPanel[2];
	}
	private void addComponents() 
	{
		mainMenu.add(fileMenu);
		mainMenu.add(projectMenu);
		mainMenu.add(helpMenu);
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(exitItem);
		projectMenu.add(addMap);
		helpMenu.add(helpItem);
		helpMenu.add(vidTutItem);
		helpMenu.add(about);
		
		leftPanel.add(new JLabel(""),BorderLayout.NORTH);
		leftPanel.add(file,BorderLayout.CENTER);
		leftPanel.add(new JLabel(""),BorderLayout.CENTER);
		leftPanel.add(project,BorderLayout.CENTER);
		leftPanel.add(new JLabel(""),BorderLayout.CENTER);
		leftPanel.add(help,BorderLayout.CENTER);
		leftPanel.add(new JLabel(""),BorderLayout.CENTER);
		
		
		centerPanel.add(s);
		
		rightPanel[0].add(newP,BorderLayout.LINE_START);
		rightPanel[0].add(open,BorderLayout.LINE_END);
		rightPanel[0].add(exit,BorderLayout.LINE_START);
		rightPanel[0].add(new JLabel(""),BorderLayout.LINE_END);

		rightPanel[1].add(add,BorderLayout.LINE_START);
		rightPanel[1].add(new JLabel(""),BorderLayout.LINE_END);
		rightPanel[1].add(new JLabel(""),BorderLayout.LINE_START);
		rightPanel[1].add(new JLabel(""),BorderLayout.LINE_END);

		rightPanel[2].add(helpC,BorderLayout.LINE_START);
		rightPanel[2].add(vid,BorderLayout.LINE_END);
		rightPanel[2].add(abt,BorderLayout.LINE_START);
		rightPanel[2].add(new JLabel(""),BorderLayout.LINE_END);

		b.add(rightPanel[0]);
		b.add(rightPanel[1]);
		b.add(rightPanel[2]);
		
		parent.add(leftPanel);
		parent.add(centerPanel);
		parent.add(b);
		
		otf.add(parent);
	}
	private void addActionListeners() 
	{
		newItem.setMnemonic('N');
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		openItem.setMnemonic('O');
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		openItem.addActionListener(openListener);
		exitItem.addActionListener(exitListener);
		exitItem.setMnemonic(27);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		addMap.addActionListener(addMapListener);
		addMap.setMnemonic('A');
		addMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		helpItem.addActionListener(helpListener);
		helpItem.setMnemonic('H');
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		vidTutItem.addActionListener(vidTutListener);
		vidTutItem.setMnemonic('V');
		vidTutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		about.addActionListener(aboutListener);
		about.setMnemonic('B');
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		
		file.addMouseListener(fileHover);
		project.addMouseListener(projectHover);
		help.addMouseListener(helpHover);
		newP.addActionListener(newListener);
		open.addActionListener(openListener);
		exit.addActionListener(exitListener);
		add.addActionListener(addMapListener);
		helpC.addActionListener(helpListener);
		vid.addActionListener(vidTutListener);
		abt.addActionListener(aboutListener);
		newP.addMouseListener(getButtonHover(newP));
		open.addMouseListener(getButtonHover(open));
		exit.addMouseListener(getButtonHover(exit));
		add.addMouseListener(getButtonHover(add));
		helpC.addMouseListener(getButtonHover(helpC));
		vid.addMouseListener(getButtonHover(vid));
		abt.addMouseListener(getButtonHover(abt));
		otf.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{
				exitAction();
			}
			public void windowDeiconified(WindowEvent e) 
			{
				if(e.getOldState()==0)
				{
					otf.setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
			}
		});
		otf.addWindowFocusListener(new WindowAdapter()
		{
			public void windowGainedFocus(WindowEvent e)
			{
				if (xmlFile!=null)
					add.setEnabled(true);
				else
					add.setEnabled(false);
			}
			public void windowLostFocus(WindowEvent e)
			{
				
			}
		});
	}
	public void showOTF()
	{
		fileAction();
		otf.setTitle("TEAM Z BMS Map Generator : "+tso_id.toUpperCase()+"."+bms_pds.toUpperCase());
		otf.setVisible(true);
		otf.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.keepAlive();
	}

	ActionListener helpListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e) 
		{
			try 
			{
				String helpFilePath=System.getProperty("user.dir")+"\\Help\\helpFile.chm";
				Runtime.getRuntime().exec("hh.exe "+helpFilePath);
			} 
			catch (Exception e1) 
			{
				JOptionPane.showMessageDialog(null, e1.toString(),"Error",JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
	};
	ActionListener aboutListener=new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					About a=new About();
					a.showUI();
				}
			});
		}
	};
	ActionListener vidTutListener=new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated method stub
			JLabel vLink=new JLabel("<html><a href=\" http://www.youtube.com/watch?v=x3-OFGp5_dw&feature=youtu.be>Video Tutorial</a></html>");
			vLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
			vLink.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					try
					{
						Desktop.getDesktop().browse(new URI("http://www.youtube.com/watch?v=x3-OFGp5_dw&feature=youtu.be"));
					} 
					catch (Exception ex) 
					{
						JOptionPane.showMessageDialog(null, ex.toString(),"Error",JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				}
			});
			JPanel vidLinkPanel=new JPanel();
			vidLinkPanel.add(vLink,BorderLayout.CENTER);
			JOptionPane.showMessageDialog(null, vidLinkPanel);
		}
	};
	ActionListener newListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated catch block
			currentMap = -1;
			isNew = true;
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					NewProject ob = new NewProject(otf);
					ob.showUI();
				}
			});
		}
	};
	ActionListener exitListener = new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated catch block
			exitAction();
		}
	};
	ActionListener openListener = new ActionListener() 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// TODO Auto-generated catch block
			currentMap = -1;
			isNew = false;
			SwingUtilities.invokeLater(new Runnable() 
			{
				@Override
				public void run()
				{
					OpenProject ob = new OpenProject();
					ob.showUI();
				}
			});
		}
	};
	ActionListener addMapListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			isNew = true;
			DFHMDI_UI1 ob = new DFHMDI_UI1(true);
			ob.buildUI();
		}
	};
	ActionListener editMapListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated catch block
			isNew = false;
			num = currentMap + 1;
			RetrieveDFHMDI ob = new RetrieveDFHMDI(otf, true);
			ob.retreive(num);
		}
	};
	MouseListener fileHover=new MouseListener()
	{
		public void mouseClicked(MouseEvent arg0)
		{
			fileAction();
		}
		public void mouseEntered(MouseEvent arg0) 
		{
			fileAction();
		}
		public void mouseExited(MouseEvent arg0) 
		{
			
		}
		public void mousePressed(MouseEvent arg0)
		{
			
		}
		public void mouseReleased(MouseEvent arg0) 
		{
			
		}
		
	};
	MouseListener projectHover=new MouseListener()
	{
		public void mouseClicked(MouseEvent arg0)
		{
			projectAction();
		}
		public void mouseEntered(MouseEvent arg0) 
		{
			projectAction();
		}
		public void mouseExited(MouseEvent arg0) 
		{
			
		}
		public void mousePressed(MouseEvent arg0)
		{
			
		}
		public void mouseReleased(MouseEvent arg0) 
		{
			
		}
		
	};
	MouseListener helpHover=new MouseListener()
	{
		public void mouseClicked(MouseEvent arg0)
		{
			helpAction();
		}
		public void mouseEntered(MouseEvent arg0) 
		{
			helpAction();
		}
		public void mouseExited(MouseEvent arg0) 
		{
			
		}
		public void mousePressed(MouseEvent arg0)
		{
			
		}
		public void mouseReleased(MouseEvent arg0) 
		{
			
		}
	};

	private void fileAction()
	{
		setLook(file,rightPanel[0],true);
		setLook(project,rightPanel[1],false);
		setLook(help,rightPanel[2],false);
	}
	private void helpAction()
	{
		setLook(file,rightPanel[0],false);
		setLook(project,rightPanel[1],false);
		setLook(help,rightPanel[2],true);
	}
	private void projectAction()
	{
		setLook(file,rightPanel[0],false);
		setLook(project,rightPanel[1],true);
		setLook(help,rightPanel[2],false);
	}
	private void keepAlive()
	{
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    service.scheduleWithFixedDelay(new Runnable()
	      {
	        @Override
	        public void run()
	        {
	        	try
	        	{
	        		ftp.sendNoOp();
	        	}
	        	catch(Exception e)
	        	{
	        		JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
	        		e.printStackTrace();
	        	}
	        }
	      }, 0, 1, TimeUnit.MINUTES);
	  }
	private void exitAction() 
	{
		int reply = JOptionPane.showConfirmDialog(null,"Are you sure to exit? ", "Confirm Exit",JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION)
		{
			log.info(cal.getTime() + "  " + tso_id + " logged out.");
			if(dh.removeTemp()==false)
			{
				JOptionPane.showMessageDialog(null, "Directory : Workspace/Temp could not be deleted!", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			qftp.logoutAndDisconnect(ftp);
			otf.dispose();
			System.exit(0);
		}	
	}
	public void startOTF()
	{
		boolean f1=true,f2=true,f3=true,f4=true;
		f3=dh.copyHelpChm();
		//f4=dh.copyHelpChw();
		if(dh.makeWorkspace())
		{
			if(dh.makeTemp())
			{
				f1=true;
			}
			else
			{
				f1=false;
			}
		}
		else
		{
			f1=false;
		}
		File Profiles=dh.makeProfiles();
		if(Profiles!=null)
		{
			f2=true;
		}
		else
		{
			f2=false;
		}
		if(f1&f2&f3&f4)
		{
			createComponents();
			String a[]={"File   ","Project   ","Help   "};
			textDim=dip.getTextDimensionsInPixels(bold,a);
			setUpComponents();
			addComponents();
			addActionListeners();
			createKeyStrokes();
			registerKeyStrokes();
			log = new CustomLogger(System.getProperty("user.dir") + "\\Workspace\\"+ presentMonth + ".log");
			LoginScreen lgs=new LoginScreen(otf,this,Profiles);
			lgs.showUI();
		}
		else
		{
			if(dh.removeWorkspace()==false)
			{
				JOptionPane.showMessageDialog(null, "Directory : Workspace could not be deleted!", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			if(dh.removeTemp()==false)
			{
				JOptionPane.showMessageDialog(null, "Directory : Workspace/Temp could not be deleted!", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			if(dh.removeProfiles()==false)
			{
				JOptionPane.showMessageDialog(null, "Directory : Profiles could not be deleted!", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			System.exit(0);
		}
	}

	public static void main(String args[])
	{
		if(Double.parseDouble(System.getProperty("java.version").substring(0, 3))>=1.6)
		{
			SwingUtilities.invokeLater(new Runnable() 
			{
				public void run() 
				{
					OTF ob= new OTF();
					ob.startOTF();
				}
			});
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Installed Java Version : "+System.getProperty("java.version").substring(0, 3)+"\nRequired Java Version 1.7 or Up", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void setLook(JLabel l,JPanel p,boolean f)
	{
		if(f)
		{
			l.setBorder(etchedBorder);
			l.setIcon(arrow);
			l.setFont(bold);
		}
		else
		{
			l.setBorder(bevelBorder);
			l.setIcon(blank);
			l.setFont(normal);
		}
		p.setVisible(f);
	}
	private MouseListener getButtonHover(final JButton button)
	{
		MouseListener buttonHover=new MouseListener()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				
			}
			public void mouseEntered(MouseEvent arg0) 
			{
				button.setFocusable(true);
				button.requestFocusInWindow();
			}
			public void mouseExited(MouseEvent arg0) 
			{
				button.setFocusable(false);
				button.setFocusable(true);
			}
			public void mousePressed(MouseEvent arg0)
			{
				
			}
			public void mouseReleased(MouseEvent arg0) 
			{
				
			}
		};
		return buttonHover;
	}
}
