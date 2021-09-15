package transformer;

import java.awt.Color;
import java.awt.Graphics2D;

public class GDrawer extends GTransformer {

	public GDrawer() {
		
	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.getGShape().setOrigin(x, y);
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		graphics2d.setXORMode(Color.WHITE);
		graphics2d.setStroke(dashedLineStroke);
		
		this.getGShape().draw(graphics2d);
		this.getGShape().setPoint(x, y);
		this.getGShape().draw(graphics2d);
	}
	
	public void continueTransforming(Graphics2D graphics2d, int x, int y){ 
		this.getGShape().addPoint(x, y);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
		this.getGShape().draw(graphics2d);
	}
}
