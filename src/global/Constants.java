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
      title("윤정 GraphicsEditor")
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
      //모듈레러티//select.getName 하면 select라는 글자가 리턴 됨(자바만 가능)
      Group("그룹", new GGroup(), "그룹.png"),
      Rectangle("네모", new GRectangle(), "네모.png"),
      Polygon("다각형", new GPolygon(), "폴리곤.png"),
      Line("직선", new GLine(), "직선.png"),
      Ellipse("원", new GEllipse(), "동그라미.png"),
      RoundRect("둥근네모", new GRoundRectangle(), "둥근모서리네모.png")
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
      setLineColor("선색 설정") , 
      setFillColor("도형색 설정") ,
      clearLineColor("선색 초기화"),
      clearFillColor("도형색 초기화"),
      setBackgroundColor("배경색 설정"),
      clearBackgroundColor("배경색 초기화")
      ;
      private String text;
      private EColorMenuItems(String text) {
         this.text = text;
      }
      public String getText() {
         return this.text;
      }
   }
   
   public enum EFileMenu {//reflection(함수 이름을 enum에 넣음)
      newItem("새로만들기", "nnew", 'n'),
      openItem("열기", "open", 'o'),
      saveItem("저장", "save", 's'),
      saveAsItem("다른이름으로 저장", "saveAs", 'q'),
      printItem("인쇄하기", "print", 'p'),
      closeItem("닫기", "close", 'r'),
      goToAWT("AWT사이트", "goToOracleSite", 'a'),
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
   
   public enum EEditMenu {//reflection(함수 이름을 enum에 넣음)
      clearPanel("모두 지우기", "clearPanel", "도형을 전부 지웁니다", 'a'),
      undo("되돌리기", "undo", "이전실행", 'z'),
      redo("다시실행", "redo", "다시실행", 'y'),
      delete("삭제", "delete", "선택도형을 삭제합니다", 'd'),
      cut("잘라내기", "cut", "선택도형을 잘라냅니다", 'x'),
      copy("복사하기", "copy", "선택도형을 복사합니다", 'c'),
      paste("붙여넣기", "paste", "선택도형을 붙여넣습니다", 'v'),
      group("그룹", "group", "그룹화합니다", 'g'),
      ungroup("그룹해재", "ungroup", "그룹을 해재합니다", 'u')
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