package transformer;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

import shape.GShape;

public abstract class GTransformer {
	private GShape gShape;
	protected BasicStroke dashedLineStroke;
	protected Point previousP;
	
	public GTransformer() {
		this.setGShape(null);
		float dashes[] = {5};
		dashedLineStroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashes, 0);
	}
	
	public GShape getGShape() {return gShape;}
	public void setGShape(GShape gShape) {this.gShape = gShape;}

	public abstract void initTransforming(Graphics2D graphics2d, int x, int y);
	public abstract void keepTransforming(Graphics2D graphics2d, int x, int y);
	public abstract void finishTransforming(Graphics2D graphics2d, int x, int y);
	public abstract void continueTransforming(Graphics2D graphics2d, int x, int y);
}
