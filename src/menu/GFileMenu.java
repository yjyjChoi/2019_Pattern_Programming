package menu;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.print.PrinterJob;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.DialogTypeSelection;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import drawingPanel.GDrawingPanel;
import global.Constants.EFileMenu;

public class GFileMenu extends JMenu {
   private static final long serialVersionUID = 1L;

   //instance variable
   private File directory, file;//���̾�α� ��� �� ������ ��ġ�� ���ְ�
   
   //associations
   private GDrawingPanel drawingPanel;
   public void associate(GDrawingPanel drawingPanel) { this.drawingPanel = drawingPanel; }
   
   public GFileMenu(String text) {
      super(text);
      
      this.file = null;
      this.directory = new File("data");
      
      ActionHandler actionHandler = new ActionHandler();
      
      for (EFileMenu eMenuItem: EFileMenu.values()) {
         JMenuItem menuItem = new JMenuItem(eMenuItem.getText());
         menuItem.setAccelerator(KeyStroke.getKeyStroke(eMenuItem.getShortCut(),InputEvent.CTRL_MASK));
         menuItem.setActionCommand(eMenuItem.getMethod());
         menuItem.addActionListener(actionHandler);
         this.add(menuItem);
      }
   }

   public void initialize() {
      //�޴������� ���� ���� �� �ƴ϶� �ʱ�ȭ ���ص� ��
   }

   public void nnew() {
      this.save();
      
      this.drawingPanel.restoreShapeVector(null);//new�ϸ� ���� �ִ� �׸� �����ߵ�, �ٵ� ������ �ȵǾ������� save�� �� �ҷ��� �Ѵ�.
      this.drawingPanel.setUpdated(true);
   }
   
   private void readObject() {
      try {
         ObjectInputStream objectInputStream;
         objectInputStream = new ObjectInputStream(new FileInputStream(file));
         Object object = objectInputStream.readObject();
         this.drawingPanel.restoreShapeVector(object);
         objectInputStream.close();
         this.drawingPanel.setUpdated(false);//�����ߴٰ� �׳� close�ϸ� �׳� close�Ǿ��ϱ� ����
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (ClassNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   public void open() {
      this.save();
      
      JFileChooser chooser = new JFileChooser(this.directory);//���� ���� ��ġ default
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data", "god");//.god�� ������(filtering)//xml�� �����ϴ� �͵� ������
      chooser.setFileFilter(filter);
      int returnVal = chooser.showOpenDialog(this.drawingPanel);//�� ���̾�α׿��� ���� parent�ΰ�
      if (returnVal == JFileChooser.APPROVE_OPTION) {//0K ������ -> APPROVE_OPTION
         this.directory = chooser.getCurrentDirectory();
         this.file = chooser.getSelectedFile();
         this.readObject();   
      }
   }
   
   private void writeObject() {
      try {            
         ObjectOutputStream objectOutputStream;
         objectOutputStream = new ObjectOutputStream(//�����͸� �Ϸķ� �� ����...?, �����͸� �޴� �ְ� ��ũ
               new BufferedOutputStream(
                     new FileOutputStream(file)));
         objectOutputStream.writeObject(this.drawingPanel.getShapeVector());
         objectOutputStream.close();
         this.drawingPanel.setUpdated(false);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   public void save() {
      if (this.drawingPanel.isUpdated()) {//������ ��쿡�� �����ض�
         if (file == null) {
            this.saveAs();
         } else {
            this.writeObject();
         }
      }
   }
   
   public void saveAs() {//new�� ��� ����� ������.
      JFileChooser chooser = new JFileChooser(this.directory);//���� ���� ��ġ default
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data", "god");//.god�� ������(filtering)
      chooser.setFileFilter(filter);
      int returnVal = chooser.showSaveDialog(this.drawingPanel);//�� ���̾�α׿��� ���� parent�ΰ�
      if (returnVal == JFileChooser.APPROVE_OPTION) {//0K ������ -> APPROVE_OPTION
         this.directory = chooser.getCurrentDirectory();
         this.file = chooser.getSelectedFile();
         this.writeObject();
      }
   }
   
   public void print() {
        PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        attributes.add(DialogTypeSelection.NATIVE);
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.printDialog(attributes);
   }
   
   public void close() {
      this.save();
      System.exit(0);//0 : �������� ����
   }
   
   public void goToOracleSite() throws IOException, URISyntaxException {
      Desktop.getDesktop().browse(new URI("https://docs.oracle.com/javase/8/docs/api/java/awt/package-frame.html"));
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