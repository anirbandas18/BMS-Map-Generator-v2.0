package utils;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/*
 * Anirban Das
 */

public class InputDialogUI implements FocusListener {
	public static final int ERROR = 4, WARNING = 3, SUCCESS = 1,
			INFORMATION = 2, QUESTION = 5;
	private JTextField t;
	private JOptionPane pane;
	private JDialog dialog;
	private JLabel l;

	public InputDialogUI() {
		new SetLAF();
		t = new JTextField("");
		t.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent arg0) {
				// TODO Auto-generated method stub
				t.requestFocusInWindow();
			}

			@Override
			public void ancestorMoved(AncestorEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ancestorRemoved(AncestorEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		t.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(final FocusEvent e) {
			}

			@Override
			public void focusLost(final FocusEvent e) {
				if (isFirstTime) {
					// When we lose focus, ask for it back but only once
					t.requestFocusInWindow();
					isFirstTime = false;
				}
			}

			private boolean isFirstTime = true;
		});
		l = new JLabel("");
		t.addFocusListener(this);
	}

	public String showInputUI(String message, String title, String toast) {
		pane = new JOptionPane();
		String r = "";
		l.setText(message);
		t.setText(toast);
		Object comp[] = { l, t };
		pane.setMessage(comp);
		pane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		pane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
		dialog = pane.createDialog(title);
		dialog.setVisible(true);
		if (null == pane.getValue()) {
			r = "";
		} else {
			switch (((Integer) pane.getValue()).intValue()) {
			case JOptionPane.OK_OPTION:
				r = t.getText();
				break;

			default:
				r = "cancel";
				break;
			}
		}
		return r;
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		if (arg0.getSource() == t) {
			t.selectAll();
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {

	}
}
