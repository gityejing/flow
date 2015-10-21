/**
 * @(#)FlowGraphModel.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.Edge;


/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-26
 * @since       JDK1.4
 */

public class FlowGraphModel extends DefaultGraphModel {

    // Override Superclass Method
    public boolean acceptsSource(Object edge, Object port) {
        // Source only Valid if not Equal Target
        return (((Edge) edge).getTarget() != port);
    }

    // Override Superclass Method
    public boolean acceptsTarget(Object edge, Object port) {
        // Target only Valid if not Equal Source
        return (((Edge) edge).getSource() != port);
    }
}
