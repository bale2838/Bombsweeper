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
	private final int BOMB_CELL = 9;
	private final int COVERED_BOMB_CELL = BOMB_CELL + COVER_FOR_CELL;
	private final int MARKED_BOMB_CELL = COVERED_BOMB_CELL + MARK_FOR_CELL;

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

		int i = 0;
		while (i < NUM_BOMBS) {
			pos = (int)(FIELD_SIZE * rand.nextDouble());
			if ((pos < FIELD_SIZE) && (field[pos] != COVERED_BOMB_CELL)) {
				currCol = pos  % NUM_COLS;
				field[pos] = COVERED_BOMB_CELL;
				i++;

				if (currCol > 0) {
					cell = pos - 1 - NUM_COLS;
					if (cell >= 0) {
						if (field[cell] != COVERED_BOMB_CELL) {
							field[cell]+=1;
						}
					}
					cell = pos - 1;
					if (cell >= 0) {
						if (field[cell] != COVERED_BOMB_CELL) {
							field[cell] += 1;
						}
					}
					cell = pos + NUM_COLS - 1;
					if (cell < FIELD_SIZE) {
						if (field[cell] != COVERED_BOMB_CELL) {
							field[cell] += 1;
						}
					}
				}
				
				cell = pos - NUM_COLS;
				if (cell >= 0) {
					if (field[cell] != COVERED_BOMB_CELL) {
						field[cell] += 1;
					}
				}
				cell = pos + NUM_COLS;
				if (cell < FIELD_SIZE) {
					if (field[cell] != COVERED_BOMB_CELL) {
						field[cell] += 1;
					}
				}

				if (currCol < (NUM_COLS - 1)) {
					cell = pos - NUM_COLS + 1;
					if (cell >= 0) {
						if (field[cell] != COVERED_BOMB_CELL) {
							field[cell] += 1;
						}
					}
					cell = pos + NUM_COLS + 1;
					if (cell < FIELD_SIZE) {
						if (field[cell] != COVERED_BOMB_CELL) {
							field[cell] += 1;
						}
					}
					cell = pos + 1;
					if (cell < FIELD_SIZE) {
						if (field[cell] != COVERED_BOMB_CELL) {
							field[cell] += 1;
						}
					}
				}
			}

		}
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