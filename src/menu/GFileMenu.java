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
   private File directory, file;//다이얼로그 띄울 때 오픈한 위치로 가있게
   
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
      //메뉴아이템 내가 만든 게 아니라 초기화 안해도 됨
   }

   public void nnew() {
      this.save();
      
      this.drawingPanel.restoreShapeVector(null);//new하면 현재 있는 그림 지워야됨, 근데 저장이 안되어있으면 save를 또 불러야 한다.
      this.drawingPanel.setUpdated(true);
   }
   
   private void readObject() {
      try {
         ObjectInputStream objectInputStream;
         objectInputStream = new ObjectInputStream(new FileInputStream(file));
         Object object = objectInputStream.readObject();
         this.drawingPanel.restoreShapeVector(object);
         objectInputStream.close();
         this.drawingPanel.setUpdated(false);//오픈했다가 그냥 close하면 그냥 close되야하기 때문
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
      
      JFileChooser chooser = new JFileChooser(this.directory);//파일 오픈 위치 default
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data", "god");//.god만 보여줌(filtering)//xml로 저장하는 것도 만들어라
      chooser.setFileFilter(filter);
      int returnVal = chooser.showOpenDialog(this.drawingPanel);//이 다이얼로그에서 누가 parent인가
      if (returnVal == JFileChooser.APPROVE_OPTION) {//0K 누르면 -> APPROVE_OPTION
         this.directory = chooser.getCurrentDirectory();
         this.file = chooser.getSelectedFile();
         this.readObject();   
      }
   }
   
   private void writeObject() {
      try {            
         ObjectOutputStream objectOutputStream;
         objectOutputStream = new ObjectOutputStream(//데이터를 일렬로 쫙 보냄...?, 데이터를 받는 애가 싱크
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
      if (this.drawingPanel.isUpdated()) {//수정된 경우에만 저장해라
         if (file == null) {
            this.saveAs();
         } else {
            this.writeObject();
         }
      }
   }
   
   public void saveAs() {//new일 경우 여기로 빠진다.
      JFileChooser chooser = new JFileChooser(this.directory);//파일 오픈 위치 default
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data", "god");//.god만 보여줌(filtering)
      chooser.setFileFilter(filter);
      int returnVal = chooser.showSaveDialog(this.drawingPanel);//이 다이얼로그에서 누가 parent인가
      if (returnVal == JFileChooser.APPROVE_OPTION) {//0K 누르면 -> APPROVE_OPTION
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
      System.exit(0);//0 : 정상적인 종료
   }
   
   public void goToOracleSite() throws IOException, URISyntaxException {
      Desktop.getDesktop().browse(new URI("https://docs.oracle.com/javase/8/docs/api/java/awt/package-frame.html"));
   }
   
   private void invokeMethod(String name) {
      try {
         this.getClass().getMethod(name).invoke(this);//this : fileMenue 데이터새그먼트 //이 데이터새그먼트 사용해서 함수 호출해라
      } catch (IllegalAccessException | IllegalArgumentException | 
            InvocationTargetException | NoSuchMethodException
            | SecurityException e) {
         // IllegalAccessException : 잘못들어갔을 때, InvocationTargetException : 함수 자체가 잘못됐을 때
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