package toolbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import drawingPanel.GDrawingPanel;
import global.Constants.EToolBar;

public class GToolBar extends JToolBar {
	//attributes
	private static final long serialVersionUID = 1L;
	
	//components
	private Vector<JRadioButton> buttons; 
	
	//associations
	private GDrawingPanel drawingPanel;//������������ ���ٿ� ������г��� ���������
	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public GToolBar() {
		ButtonGroup buttonGroup = new ButtonGroup();
		
		this.buttons = new Vector<JRadioButton>();
		ActionHandler actionHandler = new ActionHandler();
		for (EToolBar eToolBar: EToolBar.values()) {//array �ϳ��ϳ��� ���Ҹ� ����־��
			JRadioButton button = new JRadioButton();
			button.setIcon(new ImageIcon("icon/" + eToolBar.getIcon()));
			button.setSelectedIcon(new ImageIcon("icon/����.png"));
			button.setActionCommand(eToolBar.name());
			button.addActionListener(actionHandler);
			this.buttons.add(button);
			this.add(button);
			buttonGroup.add(button);
		}
	}
	
	public void initialize() {
		this.buttons.get(EToolBar.Rectangle.ordinal()).doClick();//default��ư
	}	
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			drawingPanel.setCurrentTool(EToolBar.valueOf(event.getActionCommand()));//�ؽ�Ʈ�� ������ �� ��° enum���� 
		}
	}	
}

	
