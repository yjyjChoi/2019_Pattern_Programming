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
	
	public void setOrigin(int x, int y) {//ó���� ��� ��ǥ(2���� ��)
		this.polygon.addPoint(x, y);	
		this.polygon.addPoint(x, y);	
	}
	
	public void setPoint(int x, int y) {//���콺 ������ �� ���� �����̴� ��ǥ
		this.polygon.xpoints[this.polygon.npoints-1] = x;
		this.polygon.ypoints[this.polygon.npoints-1] = y;
		
		if (anchors!=null){ 
			anchors.setBoundingRect(this.polygon.getBounds());
		} 
	}
	
	public void addPoint(int x, int y) {//������ ����, �� ���� �߰�
		this.polygon.addPoint(x, y);
	}

	@Override
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
