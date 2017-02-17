package com.bale.bombsweeper;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {
	private JLabel statusBar;
	private final int NUM_IMAGES = 13;
	private final int CELL_SIZE = 15;
	
	private final int NUM_BOMBS = 40;
	private final int NUM_ROWS = 16;
	private final int NUM_COLS = 16;
	private final int FIELD_SIZE = NUM_ROWS * NUM_COLS;
	
	// data representation for images
	private final int COVER_FOR_CELL = 10;
	private final int MARK_FOR_CELL = 10;
	private final int EMPTY_CELL = 0;
	
	// array of integers represents the mine field
	private int[] field;
	private Image[] img;
	private boolean inGame;
	private int bombsLeft;
	
	public Board(JLabel statusBar) {
		this.statusBar = statusBar;
		img = new Image[NUM_IMAGES];
		for (int i = 0; i < NUM_IMAGES; i++) {
			img[i] = (new ImageIcon(i + ".png")).getImage();
		}
		setDoubleBuffered(true);
		addMouseListener(new BombsAdapter());
		newGame();
	}
	
	private void newGame() {
		Random rand = new Random();
		int currCol = 0;
		int pos = 0;
		int cell = 0;
		inGame = true;
		bombsLeft = NUM_BOMBS;
		field = new int[FIELD_SIZE];
		for (int i = 0; i < FIELD_SIZE; i++) {
			field[i] = COVER_FOR_CELL;
		}
		statusBar.setText(Integer.toString(bombsLeft));
		
	}
	
	class BombsAdapter extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			
			int cCol = x / CELL_SIZE;
			int cRow = y / CELL_SIZE;
				
			boolean rep = false;
			
		}
	}
}