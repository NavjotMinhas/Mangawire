package net.managwire.ui.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class CurvedArrowButton extends JButton implements SwingConstants {

	protected int direction;

	transient Color shadow = Color.GRAY;

	transient Color darkShadow = new Color(102, 102, 102);

	transient Color highlight = Color.WHITE;

	private Arc2D ARC;

	private Rectangle REC;

	public CurvedArrowButton(int direction) {
		super();
		setContentAreaFilled(false);
		setBorderPainted(false);
		setDirection(direction);
		setFocusable(false);
	}

	@Override
	public boolean isFocusTraversable() {
		return false;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int dir) {
		this.direction = dir;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		int height = getHeight();
		int size = height / 4;

		int x = (getWidth() - size) / 2;
		int y = (height - size) / 2;

		ButtonModel m = getModel();
		if (m.isArmed()) {
			x++;
			y++;
		}

		paintTriangle(g, x, y, size, direction, isEnabled());
	}

	@Override
	public Dimension getPreferredSize() {

		return new Dimension(16, 16);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(5, 5);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public void paintTriangle(Graphics g, int x, int y, int size,
			int direction, boolean isEnabled) {
		Color savedColor = g.getColor();
		switch (direction) {
		case NORTH:
			paintTriangleNorth(g, x, y, size, isEnabled);
			break;
		case SOUTH:
			paintTriangleSouth(g, x, y, size, isEnabled);
			break;

		case LEFT:
		case WEST:
			paintTriangleWest(g, x, y, size, isEnabled);
			break;
		case RIGHT:
		case EAST:
			paintTriangleEast(g, x, y, size, isEnabled);
			break;
		}
		g.setColor(savedColor);
	}

	@Override
	public boolean contains(int x, int y) {
		if (ARC.contains(x, y) || REC.contains(x, y)) {
			return true;

		} else {
			return false;
		}
	}

	@Override
	public boolean contains(Point p) {
		if (ARC.contains(p.getX(), p.getY())
				|| REC.contains(p.getX(), p.getY())) {
			return true;

		} else {
			return false;
		}
	}

	private void paintTriangleEast(Graphics g, int x, int y, int size,
			boolean isEnabled) {
		int tipX = x + (size - 1);
		int tipY = y + (size - 2) / 2;
		int baseX = x;
		int baseY1 = tipY - (size - 1);
		int baseY2 = tipY + (size - 1);

		Polygon triangle = new Polygon();
		triangle.addPoint(tipX, tipY);
		triangle.addPoint(baseX, baseY1);
		triangle.addPoint(baseX, baseY2);

		/**
		 * @Modification
		 * @PaintBackGorund
		 */
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillArc(0, 0, getWidth(), getHeight(), 270, 180);
		g2d.fillRect(0, 0, getWidth() / 2, getHeight());

		/**
		 * @ButtonShapes
		 */
		ARC = new Arc2D.Double(0.0, 0.0, (double) getWidth(),
				(double) getHeight(), 270.0, 180.0, Arc2D.CHORD);
		REC = new Rectangle(0, 0, getWidth() / 2, getHeight());

		if (isEnabled) {
			g.setColor(Color.WHITE);
			g.fillPolygon(triangle);
			g.drawPolygon(triangle);
		} else {
			g.setColor(Color.GRAY);
			g.fillPolygon(triangle);
			g.drawPolygon(triangle);
			g.setColor(Color.WHITE);
			g.drawLine(baseX + 1, baseY2, tipX, tipY + 1);
			g.drawLine(baseX + 1, baseY2 + 1, tipX + 1, tipY + 1);
		}
	}

	private void paintTriangleWest(Graphics g, int x, int y, int size,
			boolean isEnabled) {
		int tipX = x;
		int tipY = y + (size - 2) / 2;
		int baseX = x + (size - 1);
		int baseY1 = tipY - (size - 1);
		int baseY2 = tipY + (size - 1);

		Polygon triangle = new Polygon();
		triangle.addPoint(tipX, tipY);
		triangle.addPoint(baseX, baseY1);
		triangle.addPoint(baseX, baseY2);

		/**
		 * @Modification
		 * @PaintBackGorund
		 */
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillArc(0, 0, getWidth(), getHeight(), 90, 180);
		g2d.fillRect(0 + getWidth() / 2, 0, getWidth() / 2, getHeight());

		/**
		 * @ButtonShapes
		 */
		ARC = new Arc2D.Double(0.0, 0.0, (double) getWidth(),
				(double) getHeight(), 90.0, 180.0, Arc2D.CHORD);
		REC = new Rectangle(0 + getWidth() / 2, 0, getWidth() / 2, getHeight());

		if (isEnabled) {
			g.setColor(Color.WHITE);
			g.fillPolygon(triangle);
			g.drawPolygon(triangle);
		} else {
			g.setColor(Color.GRAY);
			g.fillPolygon(triangle);
			g.drawPolygon(triangle);
			g.setColor(Color.WHITE);
			g.drawLine(baseX + 1, baseY1 + 1, baseX + 1, baseY2 + 1);
		}
	}

	private void paintTriangleSouth(Graphics g, int x, int y, int size,
			boolean isEnabled) {
		int tipX = x + (size - 2) / 2;
		int tipY = y + (size - 1);
		int baseX1 = tipX - (size - 1);
		int baseX2 = tipX + (size - 1);
		int baseY = y;
		Polygon triangle = new Polygon();
		triangle.addPoint(tipX, tipY);
		triangle.addPoint(baseX1, baseY);
		triangle.addPoint(baseX2, baseY);

		/**
		 * @Modification
		 * @PaintBackGorund
		 */
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillArc(0, 0, getWidth(), getHeight(), 180, 180);
		g2d.fillRect(0, 0, getWidth(), getHeight() / 2);

		/**
		 * @ButtonShapes
		 */
		ARC = new Arc2D.Double(0.0, 0.0, (double) getWidth(),
				(double) getHeight(), 180.0, 180.0, Arc2D.CHORD);
		REC = new Rectangle(0, 0, getWidth(), getHeight() / 2);

		if (isEnabled) {
			g.setColor(Color.WHITE);
			g.fillPolygon(triangle);
			g.drawPolygon(triangle);
		} else {
			g.setColor(Color.GRAY);
			g.fillPolygon(triangle);
			g.drawPolygon(triangle);
			g.setColor(Color.WHITE);
			g.drawLine(tipX + 1, tipY, baseX2, baseY + 1);
			g.drawLine(tipX + 1, tipY + 1, baseX2 + 1, baseY + 1);
		}
	}

	private void paintTriangleNorth(Graphics g, int x, int y, int size,
			boolean isEnabled) {
		int tipX = x + (size - 2) / 2;
		int tipY = y;
		int baseX1 = tipX - (size - 1);
		int baseX2 = tipX + (size - 1);
		int baseY = y + (size - 1);
		Polygon triangle = new Polygon();
		triangle.addPoint(tipX, tipY);
		triangle.addPoint(baseX1, baseY);
		triangle.addPoint(baseX2, baseY);

		/**
		 * @Modification
		 * @PaintBackGorund
		 */
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillArc(0, 0, getWidth(), getHeight(), 0, 180);
		g2d.fillRect(0, 0 + getHeight() / 2, getWidth(), getHeight() / 2);

		/**
		 * @ButtonShapes
		 */
		ARC = new Arc2D.Double(0.0, 0.0, (double) getWidth(),
				(double) getHeight(), 0.0, 180.0, Arc2D.CHORD);
		REC = new Rectangle(0, 0 + getHeight() / 2, getWidth(), getHeight() / 2);

		if (isEnabled) {
			g.setColor(Color.WHITE);
			g.fillPolygon(triangle);
			g.drawPolygon(triangle);
		} else {
			g.setColor(Color.GRAY);
			g.fillPolygon(triangle);
			g.drawPolygon(triangle);
			g.setColor(Color.WHITE);
			g.drawLine(baseX1 + 1, baseY + 1, baseX2 + 1, baseY + 1);
		}
	}

}
