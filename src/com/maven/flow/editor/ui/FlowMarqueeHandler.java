/**
 * @(#)FlowMarqueeHandler.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;

import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.Port;
import org.jgraph.graph.PortView;


/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-26
 * @since       JDK1.4
 */

public class FlowMarqueeHandler extends BasicMarqueeHandler {

	// Holds the Start and the Current Point
	protected Point2D start, current;

	// Holds the First and the Current Port
	protected PortView port, firstPort;

	protected GraphEditor editor = null;
	//protected FlowGraph graph = null;

	public FlowMarqueeHandler(GraphEditor editor) {
		this.editor = editor;
	}

	// Override to Gain Control (for PopupMenu and ConnectMode)
	public boolean isForceMarqueeEvent(MouseEvent e) {
		if (e.isShiftDown())
			return false;
		// If Right Mouse Button we want to Display the PopupMenu
		if (SwingUtilities.isRightMouseButton(e))
			// Return Immediately
			return true;
		// Find and Remember Port
		port = getSourcePortAt(e.getPoint());
		// If Port Found and in ConnectMode (=Ports Visible)
		if (port != null && editor.getFlowGraph().isPortsVisible())
			return true;
		// Else Call Superclass
		return super.isForceMarqueeEvent(e);
	}

	// Display PopupMenu or Remember Start Location and First Port
	public void mousePressed(final MouseEvent e) {
		// If Right Mouse Button
		if (SwingUtilities.isRightMouseButton(e)) {
			// Find Cell in Model Coordinates
			//Object cell = graph.getFirstCellForLocation(e.getX(), e.getY());
			// Create PopupMenu for the Cell
			//JPopupMenu menu = createPopupMenu(e.getPoint(), cell);
			// Display PopupMenu
			//menu.show(graph, e.getX(), e.getY());
			// Else if in ConnectMode and Remembered Port is Valid
		} else if (port != null && editor.getFlowGraph().isPortsVisible()) {
			// Remember Start Location
			start = editor.getFlowGraph().toScreen(port.getLocation());
			// Remember First Port
			firstPort = port;
		} else {
			// Call Superclass
			super.mousePressed(e);
		}
	}

	// Find Port under Mouse and Repaint Connector
	public void mouseDragged(MouseEvent e) {
		// If remembered Start Point is Valid
		if (start != null) {
			// Fetch Graphics from Graph
			Graphics g = editor.getFlowGraph().getGraphics();
			// Reset Remembered Port
			PortView newPort = getTargetPortAt(e.getPoint());
			// Do not flicker (repaint only on real changes)
			if (newPort == null || newPort != port) {
				// Xor-Paint the old Connector (Hide old Connector)
				paintConnector(Color.black, editor.getFlowGraph().getBackground(), g);
				// If Port was found then Point to Port Location
				port = newPort;
				if (port != null)
					current = editor.getFlowGraph().toScreen(port.getLocation());
				// Else If no Port was found then Point to Mouse Location
				else
					current = editor.getFlowGraph().snap(e.getPoint());
				// Xor-Paint the new Connector
				paintConnector(editor.getFlowGraph().getBackground(), Color.black, g);
			}
		}
		// Call Superclass
		super.mouseDragged(e);
	}

	public PortView getSourcePortAt(Point2D point) {
		// Disable jumping
		editor.getFlowGraph().setJumpToDefaultPort(false);
		PortView result;
		try {
			// Find a Port View in Model Coordinates and Remember
			result = editor.getFlowGraph().getPortViewAt(point.getX(), point.getY());
		} finally {
			editor.getFlowGraph().setJumpToDefaultPort(true);
		}
		return result;
	}

	// Find a Cell at point and Return its first Port as a PortView
	protected PortView getTargetPortAt(Point2D point) {
		// Find a Port View in Model Coordinates and Remember
		return editor.getFlowGraph().getPortViewAt(point.getX(), point.getY());
	}

	// Connect the First Port and the Current Port in the Graph or Repaint
	public void mouseReleased(MouseEvent e) {
		// If Valid Event, Current and First Port
		if (e != null && port != null && firstPort != null && firstPort != port) {
			// Then Establish Connection
			editor.connect((Port) firstPort.getCell(), (Port) port.getCell());
			e.consume();
			// Else Repaint the Graph
		} else
			editor.getFlowGraph().repaint();
		// Reset Global Vars
		firstPort = port = null;
		start = current = null;
		// Call Superclass
		super.mouseReleased(e);
	}

	// Show Special Cursor if Over Port
	public void mouseMoved(MouseEvent e) {
		// Check Mode and Find Port
		if (e != null
				&& getSourcePortAt(e.getPoint()) != null
				&& editor.getFlowGraph().isPortsVisible()) {
			// Set Cusor on Graph (Automatically Reset)
			editor.getFlowGraph().setCursor(new Cursor(Cursor.HAND_CURSOR));
			// Consume Event
			// Note: This is to signal the BasicGraphUI's
			// MouseHandle to stop further event processing.
			e.consume();
		} else
			// Call Superclass
			super.mouseMoved(e);
	}

	// Use Xor-Mode on Graphics to Paint Connector
	protected void paintConnector(Color fg, Color bg, Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Set Foreground
		g2.setColor(fg);
		// Set Xor-Mode Color
		g2.setXORMode(bg);
		// Highlight the Current Port
		paintPort(editor.getFlowGraph().getGraphics());
		g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, new float[] { 4f, 4f }, 1.0f));
		// If Valid First Port, Start and Current Point
		if (firstPort != null && start != null && current != null)
			// Then Draw A Line From Start to Current Point
			g2.drawLine((int) start.getX(), (int) start.getY(), (int) current
					.getX(), (int) current.getY());
	}

	// Use the Preview Flag to Draw a Highlighted Port
	protected void paintPort(Graphics g) {
		// If Current Port is Valid
		if (port != null) {
			// If Not Floating Port...
			boolean o = (GraphConstants.getOffset(port.getAllAttributes()) != null);
			// ...Then use Parent's Bounds
			Rectangle2D r = (o) ? port.getBounds() : port.getParentView()
					.getBounds();
			// Scale from Model to Screen
			r = editor.getFlowGraph().toScreen((Rectangle2D) r.clone());
			// Add Space For the Highlight Border
			r.setFrame(r.getX() - 3, r.getY() - 3, r.getWidth() + 6, r
					.getHeight() + 6);
			// Paint Port in Preview (=Highlight) Mode
			editor.getFlowGraph().getUI().paintCell(g, port, r, true);
		}
	}
}
