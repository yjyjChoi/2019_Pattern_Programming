package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import drawingPanel.GDrawingPanel;
import global.Constants.EEditMenu;
import shape.GGroup;

public class GEditMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	//associations
	private GDrawingPanel drawingPanel;
	public void associate(GDrawingPanel drawingPanel) { this.drawingPanel = drawingPanel; }
	
	public GEditMenu(String text) {
		super(text);
		
		ActionHandler actionHandler = new ActionHandler();
		
		for (EEditMenu eMenuItem: EEditMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getText());
			menuItem.setToolTipText(eMenuItem.getToolTip());
			menuItem.setAccelerator(KeyStroke.getKeyStroke(eMenuItem.getShortCut(),InputEvent.CTRL_MASK));
			menuItem.setActionCommand(eMenuItem.getMethod());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
	}

	public void initialize() {
		
	}
	
	//������ �帧��
	//operation�� ������� ����(����� �������̼��� �и����Ѿ� ��)
	public void clearPanel() {
		this.drawingPanel.clearPanel();
	}
	
	public void undo() {
		this.drawingPanel.undo();
	}
	
	public void redo() {
		this.drawingPanel.redo();
	}
	
	public void delete() {
		this.drawingPanel.delete();
	}
	
	public void cut() {
		this.drawingPanel.cut();
	}
	
	public void copy() {//http://www.javapractices.com/topic/TopicAction.do?Id=82
		this.drawingPanel.copy();
	}
	
	public void paste() {
		this.drawingPanel.paste();
	}
	
	public void group() {
		this.drawingPanel.group(new GGroup());
	}
	
	public void ungroup() {
		this.drawingPanel.ungroup();
	}
	
	private void invokeMethod(String name) {
		try {
			this.getClass().getMethod(name).invoke(this);//this : fileMenue �����ͻ��׸�Ʈ //�� �����ͻ��׸�Ʈ ����ؼ� �Լ� ȣ���ض�
		} catch (IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// IllegalAccessException : �߸����� ��, InvocationTargetException : �Լ� ��ü�� �߸����� ��
			e.printStackTrace();
		}
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			invokeMethod(event.getActionCommand());
		}
	}	
}
