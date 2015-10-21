/**
 * @(#)StructTreeModel.java 2007-5-27
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-27
 * @since       JDK1.4
 */

public class StructTreeModel extends DefaultTreeModel {

     public StructTreeModel(TreeNode root) {
        this(root, false);
    }

    public StructTreeModel(TreeNode root, boolean asksAllowsChildren) {
        super(root,asksAllowsChildren);
    }
}
