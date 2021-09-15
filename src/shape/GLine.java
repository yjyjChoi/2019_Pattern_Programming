package shape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class GLine extends GShape {
	private static final long serialVersionUID = 1L;

	private java.awt.geom.Line2D.Double line;
	
	public GLine() {
		super();
		this.shape = new java.awt.geom.Line2D.Double();
		this.line = (java.awt.geom.Line2D.Double)this.shape;
	}
	
	public GShape newInstance() {
		return new GLine();
	}
	
	@Override
	public GShape deepCopy() {
		AffineTransform affineTransform = new AffineTransform();
		Shape newShape = affineTransform.createTransformedShape(myShape); 
		GLine shape = new GLine(); 
		shape.setShape(newShape);
		shape.setGraphicsAttributes(this); 
		return shape;
	}
	
	@Override
	public void setOrigin(int x, int y) {
		this.px = x;
		this.py = y;
	}

	@Override
	public void setPoint(int x, int y) {
		this.line.setLine(px, py, x,  y); 
		if (anchors!=null){ 
			anchors.setBoundingRect(this.line.getBounds());
		}
	}
	
	public boolean contains(Point p){
		Line2D tempLine = (Line2D)myShape; 
		Rectangle tempRectangle = new Rectangle();
		tempRectangle.setFrameFromDiagonal(tempLine.getP1(), tempLine.getP2()); 
		return tempRectangle.contains(p);
	}

	@Override
	public void addPoint(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
