package com.bale.bombsweeper;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Bombsweeper extends JFrame {
	private static final long serialVersionUID = 51942903193432700L;
	private final int FRAME_WIDTH = 250;
	private final int FRAME_HEIGHT = 290;
	private final JLabel statusBar;

	public Bombsweeper() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Bombsweeper");
		statusBar = new JLabel("");
		add(statusBar, BorderLayout.SOUTH);
		add(new Board(statusBar));
		setResizable(false);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame ex = new Bombsweeper();
				ex.setVisible(true);
			}
		});
	}
}