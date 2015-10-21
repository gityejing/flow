package com.maven.flow.editor.ui;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JToggleButton;

public class _IconToggleButton extends JToggleButton {
	_IconToggleButton(Icon icon) {
		super(icon);

		this.setPreferredSize(new Dimension(25, 25));
		this.setMinimumSize(getPreferredSize());
		this.setMaximumSize(getPreferredSize());
	}

}