package shape;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class GPolygon extends GShape {
	private static final long serialVersionUID = 1L;
	
	private java.awt.Polygon polygon;
	
	public GPolygon() {
		super();
		this.shape = new java.awt.Polygon();
		this.polygon = (java.awt.Polygon)this.shape;
	}
	
	public GShape newInstance() {
		return new GPolygon();
	}
	
	@Override
	public GShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform(); 
		Shape newShape = affineTransform.createTransformedShape(myShape); 
		GPolygon shape = new GPolygon(); 
		shape.setShape(newShape); 
		shape.setGraphicsAttributes(this);
		return shape;
	}
	
	public void setOrigin(int x, int y) {//처음에 찍는 좌표(2개의 점)
		this.polygon.addPoint(x, y);	
		this.polygon.addPoint(x, y);	
	}
	
	public void setPoint(int x, int y) {//마우스 움직일 때 따라 움직이는 좌표
		this.polygon.xpoints[this.polygon.npoints-1] = x;
		this.polygon.ypoints[this.polygon.npoints-1] = y;
		
		if (anchors!=null){ 
			anchors.setBoundingRect(this.polygon.getBounds());
		} 
	}
	
	public void addPoint(int x, int y) {//현재점 고정, 한 점을 추가
		this.polygon.addPoint(x, y);
	}

	@Override
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
