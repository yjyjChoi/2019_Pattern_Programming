package global;

import shape.GEllipse;
import shape.GGroup;
import shape.GLine;
import shape.GPolygon;
import shape.GRectangle;
import shape.GRoundRectangle;
import shape.GShape;


public class Constants {
   public enum EMainFrame {
      w("1000"),
      h("700"),
      title("���� GraphicsEditor")
      ;
      private String value;
      private EMainFrame(String value) {
         this.value = value;
      }
      public String getValue() {
         return this.value;
      }
      public int getInt() {return Integer.parseInt(this.value);}
   }
   
   public enum EToolBar {
      //��ⷹ��Ƽ//select.getName �ϸ� select��� ���ڰ� ���� ��(�ڹٸ� ����)
      Group("�׷�", new GGroup(), "�׷�.png"),
      Rectangle("�׸�", new GRectangle(), "�׸�.png"),
      Polygon("�ٰ���", new GPolygon(), "������.png"),
      Line("����", new GLine(), "����.png"),
      Ellipse("��", new GEllipse(), "���׶��.png"),
      RoundRect("�ձٳ׸�", new GRoundRectangle(), "�ձٸ𼭸��׸�.png")
      ;
      private String text;
      private GShape shape;
      private String icon;
      private EToolBar(String text, GShape shape, String icon) {
         this.text = text;
         this.shape = shape;
         this.icon = icon;
      }
      public String getText() {
         return this.text;
      }
      public GShape getShape() {
         return this.shape;
      }
      public String getIcon() {
         return this.icon;
      }
   }
   
   public enum EMenu {
      fileMenu("File"),
      editMenu("Edit"),
      colorMenu("Color")
      ;
      private String text;
      private EMenu(String text) {
         this.text = text;
      }
      public String getText() {
         return this.text;
      }
   }
   
   public enum EColorMenuItems { 
      setLineColor("���� ����") , 
      setFillColor("������ ����") ,
      clearLineColor("���� �ʱ�ȭ"),
      clearFillColor("������ �ʱ�ȭ"),
      setBackgroundColor("���� ����"),
      clearBackgroundColor("���� �ʱ�ȭ")
      ;
      private String text;
      private EColorMenuItems(String text) {
         this.text = text;
      }
      public String getText() {
         return this.text;
      }
   }
   
   public enum EFileMenu {//reflection(�Լ� �̸��� enum�� ����)
      newItem("���θ����", "nnew", 'n'),
      openItem("����", "open", 'o'),
      saveItem("����", "save", 's'),
      saveAsItem("�ٸ��̸����� ����", "saveAs", 'q'),
      printItem("�μ��ϱ�", "print", 'p'),
      closeItem("�ݱ�", "close", 'r'),
      goToAWT("AWT����Ʈ", "goToOracleSite", 'a'),
      ;
      private String text;
      private String method;
      private Character shortcut;
      private EFileMenu(String text, String method, Character shortCut) {
         this.text = text;
         this.method = method;
         this.shortcut = shortCut;
      }
      public String getText() {
         return this.text;
      }
      public String getMethod() {
         return this.method;
      }
      public Character getShortCut() {
         return this.shortcut;
      }
   }
   
   public enum EEditMenu {//reflection(�Լ� �̸��� enum�� ����)
      clearPanel("��� �����", "clearPanel", "������ ���� ����ϴ�", 'a'),
      undo("�ǵ�����", "undo", "��������", 'z'),
      redo("�ٽý���", "redo", "�ٽý���", 'y'),
      delete("����", "delete", "���õ����� �����մϴ�", 'd'),
      cut("�߶󳻱�", "cut", "���õ����� �߶���ϴ�", 'x'),
      copy("�����ϱ�", "copy", "���õ����� �����մϴ�", 'c'),
      paste("�ٿ��ֱ�", "paste", "���õ����� �ٿ��ֽ��ϴ�", 'v'),
      group("�׷�", "group", "�׷�ȭ�մϴ�", 'g'),
      ungroup("�׷�����", "ungroup", "�׷��� �����մϴ�", 'u')
      ;
      private String text;
      private String method;
      private String tooltip;
      private Character shortcut;
      private EEditMenu(String text, String method, String tooltip, Character shortCut) {
         this.text = text;
         this.method = method;
         this.tooltip = tooltip;
         this.shortcut = shortCut;
      }
      public String getText() {
         return this.text;
      }
      public String getMethod() {
         return this.method;
      }
      public String getToolTip() {
         return this.tooltip;
      }
      public Character getShortCut() {
         return this.shortcut;
      }
   }
}