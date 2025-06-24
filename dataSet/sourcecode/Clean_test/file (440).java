/* This file is part of Frechet tools. 
 * 
 * Copyright (C) 2012  Sebastian Kuerten
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.topobyte.frechet.geometry.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.topobyte.frechet.geometry.ui.lineeditor.RunDualLineEditor;
import de.topobyte.frechet.geometry.ui.lineeditor.RunLineEditor;
import de.topobyte.frechet.geometry.ui.polylineeditor.RunPolyLineEditor;

public class ApplicationSelector extends JPanel {

	private static final long serialVersionUID = 6936940281397458433L;

	private static final int LINE_EDITOR = 0;
	private static final int SINGLE_FRECHET = 1;
	private static final int MULTI_FRECHET = 2;

	private static final int DEFAULT_WIDTH_TEXT = 300;

	private static List<LauncherDescription> descriptions = new ArrayList<ApplicationSelector.LauncherDescription>();

	static {
		descriptions.add(new LauncherDescription(LINE_EDITOR, "Line Editor",
				"A frame that shows an editor for a single line segment."));
		descriptions
				.add(new LauncherDescription(
						SINGLE_FRECHET,
						"Line Segment Free Space",
						"A frame showing two editors for single line segments along with the single cell of free space specified by both line segments."));
		descriptions
				.add(new LauncherDescription(
						MULTI_FRECHET,
						"Polygonal chain Free Space",
						"A frame showing an editor for arbitrary polygonal chains. You may then select among different actions concerning the lines on the editor's screen, most prominently a visualization of the free space."));
	}

	public ApplicationSelector() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.gridy = 0;
		for (LauncherDescription description : descriptions) {
			// create a panel and add it to the main layout
			JPanel component = new JPanel(new GridBagLayout());
			add(component, c);
			c.gridy++;
			// setup the panel's layout
			component.setBorder(BorderFactory
					.createTitledBorder(description.title));

			GridBagConstraints d = new GridBagConstraints();
			d.fill = GridBagConstraints.BOTH;
			d.weightx = 1.0;

			d.gridy = 0;
			JTextArea area = new JTextArea();
			area.setEditable(false);
			area.setText(description.description);
			area.setLineWrap(true);
			area.setWrapStyleWord(true);
			component.add(area, d);
			area.setSize(new Dimension(DEFAULT_WIDTH_TEXT, Short.MAX_VALUE));
			Dimension preferredSize = area.getPreferredSize();
			area.setPreferredSize(new Dimension(DEFAULT_WIDTH_TEXT,
					preferredSize.height));

			d.gridy = 1;
			JButton button = new JButton("Execute");
			component.add(button, d);

			button.addActionListener(new LauncherActionListener(description.id));
		}
	}

	private class LauncherActionListener implements ActionListener {

		private int id;

		public LauncherActionListener(int id) {
			this.id = id;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (id) {
			case LINE_EDITOR:
				RunLineEditor.runProgrammatically(false);
				break;
			case SINGLE_FRECHET:
				RunDualLineEditor.runProgrammatically(false);
				break;
			case MULTI_FRECHET:
				RunPolyLineEditor.runProgrammatically(false);
				break;
			}
		}

	}

	private static class LauncherDescription {

		private final int id;
		private final String title;
		private final String description;

		public LauncherDescription(int id, String title, String description) {
			this.id = id;
			this.title = title;
			this.description = description;
		}
	}

	public static void main(String[] args) {
		ApplicationSelector applicationSelector = new ApplicationSelector();
		JFrame frame = new JFrame();
		frame.setTitle("Select an application");
		frame.setContentPane(applicationSelector);
		// frame.setSize(600, 400);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
