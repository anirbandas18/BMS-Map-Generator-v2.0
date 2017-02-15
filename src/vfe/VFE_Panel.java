package vfe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dfhmdf.DFHMDF;
import dfhmdf.DFHMDF_UI1;
import utils.CreateIcon;
import dfhmdf.OrganizeDFHMDF;

/**
 * This class is used to create a visual form editor. Here the user can draw on
 * the panel, after which the field properties can be defined.
 * 
 * @author NIRUPAM
 * 
 */
@SuppressWarnings("serial")
public final class VFE_Panel extends JPanel implements MouseListener,
		MouseMotionListener {

	private boolean drawable = true, draggable = false, editable = false,
			delete = false;
	private int x, y, width, height, rClicked = -1, mode;
	private ArrayList<Rectangle2D.Double> fieldRect = new ArrayList<>();
	private ArrayList<Rectangle2D.Double> backRect = new ArrayList<>();
	private ArrayList<DFHMDF> fieldProp = new ArrayList<>();

	public ArrayList<DFHMDF> getFieldProp() {
		return fieldProp;
	}

	private ArrayList<DFHMDF> backProp = new ArrayList<>();

	private final DFHMDF_UI1 uiobj;
	private boolean firstTime = true, added = false;
	/**
	 * It holds the selected Rectangle object used for editing and dragging
	 * DFHMDF object
	 */
	private Rectangle2D.Double srect;
	/**
	 * It holds the selected DFHMDF object used for editing and dragging
	 */
	private DFHMDF sdfhmdf;
	private String fieldSize;
	private JPopupMenu popMenu = new JPopupMenu();
	private Font f = new Font("Courier New", Font.PLAIN, 20);
	private JMenuItem editMenuItem = new JMenuItem("Edit"),
			deleteMenuItem = new JMenuItem("Delete");
	private CreateIcon ci = new CreateIcon();
	private ImageIcon editIcon = ci.createImageIcon("editSmall.png"),
			deleteIcon = ci.createImageIcon("cross.png");
	private static final int offset = 20, w = 60;

	public VFE_Panel() {
		new utils.SetLAF();
		addMouseListener(this);
		addMouseMotionListener(this);
		uiobj = new DFHMDF_UI1();
		init();

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					DFHMDF obj = uiobj.getFieldProperty();
					if (obj != null && drawable) {
						String pos = obj.getPos();
						pos = pos.substring(1, pos.length() - 1);
						int nx = Integer.parseInt(pos.split(",")[1])
								* Constants.XSPAN, ny = Integer.parseInt(pos
								.split(",")[0]) * Constants.YSPAN;
						Rectangle2D.Double r = new Rectangle2D.Double(nx, ny,
								width, Constants.YSPAN);

						fieldRect.add(r);
						fieldProp.add(obj);
						backup();
						added = true;
						repaint();
					} else if (obj != null && editable) {
						System.out.println(obj + " editable=" + editable);
						backup();
						fieldRect.remove(rClicked);
						fieldProp.remove(rClicked);
						fieldRect.add(srect);
						fieldProp.add(obj);
						srect = null;
						sdfhmdf = null;
						editable = false;
						drawable = true;
						rClicked = -1;
						repaint();
					}
				}
			}
		});
		t.start();
	}

	private void init() {
		popMenu.add(editMenuItem);
		popMenu.add(deleteMenuItem);
		editMenuItem.setIcon(editIcon);
		deleteMenuItem.setIcon(deleteIcon);
		ActionListener popListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == editMenuItem)
					editAction();
				else if (e.getSource() == deleteMenuItem)
					deleteAction();
			}
		};

		editMenuItem.addActionListener(popListener);
		deleteMenuItem.addActionListener(popListener);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.white);
		g2d.setFont(f);

		drawScale(g2d);

		if (!added)
			g2d.drawRect(x, y, width, height);

		if (draggable && srect != null) {
			g2d.draw(srect);
			g2d.setColor(getColor(sdfhmdf.getColor()));
			if (sdfhmdf.getOccur() == 0) {
				g2d.drawString(sdfhmdf.getInitial(), (int) (srect.getX()) + 2,
						(int) (srect.getY() + srect.getHeight()) - 2);
			} else {
				drawInterior(g2d, srect, sdfhmdf, true);
			}
		}

		drawFields(g2d);

		/*
		 * f=g2d.getFont(); FontMetrics fm=g2d.getFontMetrics(f);
		 * System.out.println(f.getName()+"  "+fm.stringWidth("a"));
		 */
	}

	/**
	 * This method draws the scale of the editor
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	private void drawScale(Graphics2D g2d) {
		g2d.fillRect(0, 0, getWidth(), 5);
		for (int i = 0; i <= getWidth(); i += Constants.XSPAN) {
			if ((i / Constants.XSPAN) % 10 == 0)
				g2d.drawLine(i, 0, i, 15);
			else
				g2d.drawLine(i, 0, i, 8);
		}

		g2d.fillRect(0, 0, 5, getHeight());
		for (int i = 0; i <= getHeight(); i += Constants.YSPAN) {
			if ((i / Constants.YSPAN) % 5 == 0)
				g2d.drawLine(0, i, 15, i);
			else
				g2d.drawLine(0, i, 8, i);
		}
	}

	/**
	 * This method is used for drawing the DFHMDF fields on Visual Form Editor
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	private void drawFields(Graphics2D g2d) {
		for (int i = 0; i < fieldRect.size(); i++) {
			Rectangle2D.Double r = fieldRect.get(i);
			g2d.setColor(Color.white);
			g2d.setFont(f);
			if (fieldProp.get(i).getOccur() > 0) {
				drawArray(g2d, fieldProp.get(i), r);
			} else {
				String name = fieldProp.get(i).getInitial();
				if (name == null) {
					name = ".";
					for (int loop = 1; loop < fieldProp.get(i).getLength(); loop++) {
						name += ".";
					}
				} else if (name.equals("")) {
					name = ".";
					for (int loop = 1; loop < fieldProp.get(i).getLength(); loop++) {
						name += ".";
					}
				}
				FontRenderContext frc = g2d.getFontRenderContext();
				TextLayout t = new TextLayout(name, f, frc);
				double w = (t.getBounds().getWidth());
				String pos = fieldProp.get(i).getPos();
				pos = pos.substring(1, pos.length() - 1);
				double nx = Integer.parseInt(pos.split(",")[1])
						* Constants.XSPAN, ny = Integer
						.parseInt(pos.split(",")[0]) * Constants.YSPAN;
				r.setFrame(nx, ny, w + Constants.XSPAN, r.getHeight());
				g2d.draw(r);
				g2d.setColor(getColor(fieldProp.get(i).getColor()));
				g2d.drawString(name, (int) (r.getX()) + 2,
						(int) (r.getY() + r.getHeight()) - 2);
			}
		}
	}

	public void setFieldType(int mode) {
		this.mode = mode;
		uiobj.setFieldTypeDetails(mode);
	}

	public void setDraggable(boolean isDraggable) {
		draggable = isDraggable;
	}

	public void setDrawable(boolean isDrawable) {
		drawable = isDrawable;
	}

	public void setEditable(boolean isEditable) {
		editable = isEditable;
	}

	public void setDelete(boolean isDeletable) {
		delete = isDeletable;
	}

	/**
	 * This method sets the fieldProp array-list. This method should only be
	 * called from outside when reconstructing the UI from XML
	 * 
	 * @param list
	 */
	public void setFieldProp(ArrayList<DFHMDF> list) {
		fieldProp.addAll(list);
		for (DFHMDF foo : fieldProp) {
			String pos = foo.getPos();
			pos = pos.substring(1, pos.length() - 1);
			int ry = Integer.parseInt(pos.split(",")[0]) * Constants.YSPAN;
			int rx = Integer.parseInt(pos.split(",")[1]) * Constants.XSPAN;
			Rectangle2D.Double r = new Rectangle2D.Double(rx, ry, 10,
					Constants.YSPAN);
			fieldRect.add(r);
			// System.out.println(foo);
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int cx = e.getX();
		int cy = e.getY();
		if (drawable) {
			width = cx - x;
			height = cy - y;
			added = false;
			repaint();
		}
		if (draggable && srect != null) {
			srect.setFrame(cx - width, cy - height, srect.getWidth(),
					srect.getHeight());
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (editable || delete) {
			editable = false;
			delete = false;
			drawable = true;
		}
		Point p = e.getPoint();
		rClicked = search(p);
		if (drawable && rClicked == -1) {
			x = e.getX();
			y = e.getY();
		} else if (drawable && rClicked != -1) {
			drawable = false;
			editable = true;
			delete = true;
		} else if (draggable) {
			x = e.getX();
			y = e.getY();
			added = true;
			rClicked = search(e.getPoint());
			if (rClicked != -1) {
				backup();
				srect = fieldRect.remove(rClicked);
				sdfhmdf = fieldProp.remove(rClicked);
				width = (int) (x - srect.getX());
				height = (int) (y - srect.getY());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger() && (editable || delete))
			popMenu.show(e.getComponent(), e.getX(), e.getY());

		if (drawable) {
			width = e.getX() - x;
			height = e.getY() - y;

			repaint();
			final Rectangle2D r = new Rectangle2D.Double(x, y, width, height);
			if (intersects(r) || !isInsside(r)) {
				JOptionPane.showMessageDialog(null,
						"Drawing in invalid location ", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (firstTime) {
						firstTime = false;
					}
					uiobj.clearAll();
					uiobj.setFieldTypeDetails(mode);
					int nx = (int) Math.round(r.getX() / Constants.XSPAN), ny = (int) Math
							.round(r.getY() / Constants.YSPAN);
					uiobj.setPosText(nx, ny);
					uiobj.buildUI();
				}
			});
		} else if (draggable && srect != null) {
			// System.out.println(intersects(srect));

			if (intersects(srect)) {
				JOptionPane.showMessageDialog(null,
						"Trying to drop a field on another", "Error",
						JOptionPane.ERROR_MESSAGE);

				setToOriginal();
				repaint();
				return;
			}

			if (!isInsside(srect)) {
				double nx = this.getWidth() - srect.getWidth();
				srect.setFrame(nx, srect.getY(), srect.getWidth(),
						srect.getHeight());
				if (intersects(srect)) {
					JOptionPane.showMessageDialog(null, "Repositioning failed",
							"Error", JOptionPane.ERROR_MESSAGE);
					setToOriginal();
					repaint();
					return;
				}
			}

			int nx = (int) Math.round(srect.getX() / Constants.XSPAN), ny = (int) Math
					.round(srect.getY() / Constants.YSPAN);
			srect.setFrame(nx * Constants.XSPAN, ny * Constants.YSPAN,
					srect.getWidth(), srect.getHeight());
			fieldRect.add(srect);

			DFHMDF ndf = new DFHMDF();
			ndf.setPos(ny, nx);
			ndf.setName(sdfhmdf.getName());
			ndf.setComment(sdfhmdf.getComment());
			ndf.setAttrb(sdfhmdf.getAttrb());
			ndf.setLength(sdfhmdf.getLength());
			ndf.setOccur(sdfhmdf.getOccur());
			ndf.setColor(sdfhmdf.getColor());
			ndf.setPicin(sdfhmdf.getPicin());
			ndf.setPicout(sdfhmdf.getPicout());
			ndf.setInitial(sdfhmdf.getInitial());
			ndf.setFieldSize(fieldSize);

			fieldProp.add(ndf);
			srect = null;
			sdfhmdf = null;
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * This method searches for the index of the Rectangle object in the
	 * ArrayList already created for the DFHMDF's from the Point passed as
	 * parameter. If ihe search is unsuccessful it returns -1
	 * 
	 * @param p
	 *            Point
	 * @return int
	 */
	private int search(Point p) {
		for (int i = 0; i < fieldRect.size(); i++) {
			Rectangle2D r = fieldRect.get(i);
			if (r.contains(p))
				return i;
		}
		return -1;
	}

	/**
	 * This method checks whether a field is intersecting with other drawn
	 * fields
	 * 
	 * @param rparam
	 *            Rectangle2D
	 * @return
	 */
	private boolean intersects(Rectangle2D rparam) {
		for (Rectangle2D.Double r : fieldRect) {
			Area rarea = new Area(r);
			if (rarea.intersects(rparam))
				return true;
		}
		return false;
	}

	/**
	 * This method checks whether a field is within the drawing surface or not
	 * 
	 * @param rparam
	 *            Rectangle2D
	 * @return
	 */
	private boolean isInsside(Rectangle2D rparam) {
		Rectangle2D r = new Rectangle2D.Double(0, 0, this.getWidth(),
				this.getHeight());
		Point2D p1 = new Point2D.Double(rparam.getX(), rparam.getY()), p2 = new Point2D.Double(
				rparam.getX() + rparam.getWidth(), rparam.getY()), p3 = new Point2D.Double(
				rparam.getX() + rparam.getWidth(), rparam.getY()
						+ rparam.getHeight()), p4 = new Point2D.Double(
				rparam.getX(), rparam.getY() + rparam.getHeight());

		if (r.contains(p1) && r.contains(p2) && r.contains(p3)
				&& r.contains(p4))
			return true;
		else
			return false;
	}

	/**
	 * This method sets a field to its original location if repositioning fails
	 */
	private void setToOriginal() {
		String pos = sdfhmdf.getPos();
		pos = pos.substring(1, pos.length() - 1);
		int ny = Integer.parseInt(pos.split(",")[0]) * Constants.YSPAN, nx = Integer
				.parseInt(pos.split(",")[1]) * Constants.XSPAN;
		srect.setFrame(nx, ny, srect.getWidth(), srect.getHeight());
		fieldRect.add(srect);
		fieldProp.add(sdfhmdf);
		srect = null;
		sdfhmdf = null;
	}

	/**
	 * This method performs delete operation when delete-menu is clicked
	 */
	private void deleteAction() {
		if (delete && rClicked != -1) {
			int option = JOptionPane.showConfirmDialog(null,
					"Confirm to delete", "Confirmation",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				backup();
				fieldProp.remove(rClicked);
				fieldRect.remove(rClicked);
				repaint();
			}
		}
	}

	/**
	 * This method performs edit operation when edit-menu is clicked
	 */
	private void editAction() {
		if (editable && rClicked != -1) {
			if (firstTime) {
				sdfhmdf = fieldProp.get(rClicked);
				srect = fieldRect.get(rClicked);
				if (sdfhmdf.getOccur() > 0) {
					uiobj.setFieldTypeDetails(DFHMDF_UI1.ARRAY);
				} else {
					if (sdfhmdf.getName().equals("")) {
						uiobj.setFieldTypeDetails(DFHMDF_UI1.FIXED);
					} else {
						uiobj.setFieldTypeDetails(DFHMDF_UI1.VARIABLE);
					}
				}
				uiobj.setComponentValues(sdfhmdf);
				uiobj.buildUI();
				firstTime = false;
			} else {
				sdfhmdf = fieldProp.get(rClicked);
				srect = fieldRect.get(rClicked);
				if (sdfhmdf.getOccur() > 0) {
					uiobj.setFieldTypeDetails(DFHMDF_UI1.ARRAY);
					uiobj.dialog.setTitle("Field Properties : Array Field");
				} else {
					if (sdfhmdf.getName().equals("")) {
						uiobj.setFieldTypeDetails(DFHMDF_UI1.FIXED);
						uiobj.dialog.setTitle("Field Properties : Text Field");
					} else {
						uiobj.setFieldTypeDetails(DFHMDF_UI1.VARIABLE);
						uiobj.dialog
								.setTitle("Field Properties : Variable Field");
					}
				}
				uiobj.setComponentValues(sdfhmdf);
				uiobj.dialog.setVisible(true);
			}
		}
	}

	private void backup() {
		if (backProp.size() > 0) {
			backProp.removeAll(backProp);
			backRect.removeAll(backRect);
		}
		backProp.addAll(fieldProp);
		backRect.addAll(fieldRect);
	}

	public void doUndo() {
		if (fieldProp.size() > 0) {
			fieldProp.removeAll(fieldProp);
			fieldRect.removeAll(fieldRect);
		}
		fieldProp.addAll(backProp);
		fieldRect.addAll(backRect);

		repaint();
	}

	/**
	 * Converts a String representation of color to Color object
	 * 
	 * @param c
	 *            String representation of color
	 * @return Color
	 */
	private Color getColor(String c) {
		if (c.equals("red"))
			return Color.red;
		else if (c.equals("green"))
			return Color.green;
		else if (c.equals("blue"))
			return Color.blue;
		else if (c.equals("yellow"))
			return Color.yellow;
		else if (c.equals("pink"))
			return Color.pink;
		else if (c.equals("turquoise"))
			return Constants.TURQUOISE;
		else if (c.equals("neutral"))
			return Color.white;
		else
			return Color.green;
	}

	private void drawArray(Graphics2D g2d, DFHMDF ob, Rectangle2D.Double r) {
		String pos = ob.getPos();
		pos = pos.substring(1, pos.length() - 1);
		double width, height, nx = Integer.parseInt(pos.split(",")[1])
				* Constants.XSPAN, ny = Integer.parseInt(pos.split(",")[0])
				* Constants.YSPAN;
		TextLayout t[] = drawInterior(g2d, r, ob, false);
		width = (t[0].getBounds().getWidth()) + offset + offset / 4;
		height = (int) (t[1].getBounds().getHeight() + (w / 2)
				* (ob.getOccur()) + offset + offset / 4);
		if (ob.getOccur() % 2 == 0 || ob.getOccur() != 1) {
			r.setFrame(nx, ny, width, height);
		} else {
			r.setFrame(nx, ny, width, height + offset / 5);
		}
		g2d.setColor(Color.white);
		g2d.draw(r);
		drawInterior(g2d, r, ob, true);
	}

	public void generate(int i) {
		/* i is the variable storing the map number */
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
			Document xdoc = docBuilder.parse(otf.OTF.xmlFile);
			NodeList dfhmdiList = xdoc.getElementsByTagName("DFHMDI");
			Node dfhmdi = dfhmdiList.item(i);
			OrganizeDFHMDF odfhmdf = new OrganizeDFHMDF(fieldProp);
			ArrayList<DFHMDF> p = odfhmdf.organizeList();
			System.out.print(p.size());
			for (DFHMDF f : p/* fieldProp */) {
				Element dfhmdf = xdoc.createElement("DFHMDF");
				dfhmdf.setAttribute("Name", f.getName());
				dfhmdi.appendChild(dfhmdf);
				Element dfhmdf_val;
				dfhmdf_val = xdoc.createElement("POS");
				dfhmdf_val.setTextContent(f.getPos());
				dfhmdf.appendChild(dfhmdf_val);
				if (f.getLength() != 0) {
					dfhmdf_val = xdoc.createElement("LENGTH");
					dfhmdf_val.setTextContent(f.getLength() + "");
					dfhmdf.appendChild(dfhmdf_val);
				}
				if (f.getOccur() > 0) {
					dfhmdf_val = xdoc.createElement("OCCURS");
					dfhmdf_val.setTextContent(f.getOccur() + "");
					dfhmdf.appendChild(dfhmdf_val);
				}
				dfhmdf_val = xdoc.createElement("ATTRB");
				dfhmdf_val.setTextContent(f.getAttrb());
				dfhmdf.appendChild(dfhmdf_val);
				if (getValue("MAPATTS", dfhmdi) != "") {
					dfhmdf_val = xdoc.createElement("COLOR");
					String color = f.getColor();
					if (color == null)
						color = "green";
					dfhmdf_val.setTextContent(color.toUpperCase());
					dfhmdf.appendChild(dfhmdf_val);
				}
				if (!f.getPicin().equals("")) {
					dfhmdf_val = xdoc.createElement("PICIN");
					dfhmdf_val.setTextContent(f.getPicin());
					dfhmdf.appendChild(dfhmdf_val);
				}
				if (!f.getPicout().equals("")) {
					dfhmdf_val = xdoc.createElement("PICOUT");
					dfhmdf_val.setTextContent(f.getPicout());
					dfhmdf.appendChild(dfhmdf_val);
				}
				if (!f.getInitial().equals("")) {
					dfhmdf_val = xdoc.createElement("INITIAL");
					dfhmdf_val.setTextContent(f.getInitial());
					dfhmdf.appendChild(dfhmdf_val);
				}
				if (!f.getComment().equals("")) {
					dfhmdf_val = xdoc.createElement("COMMENT");
					dfhmdf_val.setTextContent(f.getComment());
					dfhmdf.appendChild(dfhmdf_val);
				}
			}
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(xdoc);
			StreamResult result = new StreamResult(otf.OTF.xmlFile);
			transformer.transform(source, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.toString(), "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private String getValue(String tag, Node node) {
		try {
			NodeList childNodes = node.getChildNodes();
			int i;
			for (i = 0; i < childNodes.getLength(); i++) {
				if (childNodes.item(i).getNodeName().equals(tag))
					break;
			}
			String nodeValue = "";
			nodeValue = childNodes.item(i).getTextContent();
			return nodeValue;
		} catch (NullPointerException a) {
			return "";
		}

	}

	private TextLayout[] drawInterior(Graphics2D g2d, Rectangle2D.Double srect,
			DFHMDF ob, boolean drawMode) {
		FontRenderContext frc = g2d.getFontRenderContext();
		String blank = "";
		for (int i = 0; i < ob.getLength(); i++) {
			blank = blank + "_";
		}
		System.out.println("DFHMDF LENGTH : " + ob.getLength());
		TextLayout t1, t2;
		t1 = new TextLayout(blank, f, frc);
		t2 = new TextLayout(ob.getInitial(), f, frc);
		if (ob.getInitial().length() > blank.length()) {
			while (t1.getBounds().getWidth() < t2.getBounds().getWidth()) {
				blank = blank + "_";
				t1 = new TextLayout(blank, f, frc);
			}
		}
		StringBuffer sbf = new StringBuffer(blank);
		sbf.insert(0, " ");
		sbf.append(" ");
		blank = new String(sbf);
		double diff = srect.getWidth() - t2.getBounds().getWidth();
		g2d.setColor(getColor(ob.getColor()));
		if (drawMode) {
			g2d.drawString(ob.getInitial(),
					(int) (srect.getX() + (int) diff / 2), (int) (srect.getY()
							+ t2.getBounds().getHeight() + offset / 6));
			for (int i = 1; i <= ob.getOccur() + 1; i++) {
				g2d.drawString(blank, (int) (srect.getX()),
						(int) (srect.getY() + (i) * w / 2));
			}
		}
		fieldSize = blank;
		TextLayout tl[] = { t1, t2 };
		return tl;
	}
}
