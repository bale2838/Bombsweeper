package com.bale.bombsweeper;

import java.awt.Graphics;
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
	private final int COVERED_BOMB_CELL = COVER_FOR_CELL + BOMB_CELL;
	private final int MARKED_BOMB_CELL = MARK_FOR_CELL + COVERED_BOMB_CELL;

	private final int DRAW_BOMB = 9;
	private final int DRAW_COVER = 10;
	private final int DRAW_MARK = 11;
	private final int DRAW_WRONG_MARK = 12;

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

	public void findEmptyCells(int j) {
		int current_col = j % NUM_COLS;
		int cell;

		if (current_col > 0) { 
			cell = j - NUM_COLS - 1;
			if (cell >= 0)
				if (field[cell] > BOMB_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						findEmptyCells(cell);
				}

			cell = j - 1;
			if (cell >= 0)
				if (field[cell] > BOMB_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						findEmptyCells(cell);
				}

			cell = j + NUM_COLS - 1;
			if (cell < FIELD_SIZE)
				if (field[cell] > BOMB_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						findEmptyCells(cell);
				}
		}

		cell = j - NUM_COLS;
		if (cell >= 0)
			if (field[cell] > BOMB_CELL) {
				field[cell] -= COVER_FOR_CELL;
				if (field[cell] == EMPTY_CELL)
					findEmptyCells(cell);
			}

		cell = j + NUM_COLS;
		if (cell < FIELD_SIZE)
			if (field[cell] > BOMB_CELL) {
				field[cell] -= COVER_FOR_CELL;
				if (field[cell] == EMPTY_CELL)
					findEmptyCells(cell);
			}

		if (current_col < (NUM_COLS - 1)) {
			cell = j - NUM_COLS + 1;
			if (cell >= 0)
				if (field[cell] > BOMB_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						findEmptyCells(cell);
				}

			cell = j + NUM_COLS + 1;
			if (cell < FIELD_SIZE)
				if (field[cell] > BOMB_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						findEmptyCells(cell);
				}

			cell = j + 1;
			if (cell < FIELD_SIZE)
				if (field[cell] > BOMB_CELL) {
					field[cell] -= COVER_FOR_CELL;
					if (field[cell] == EMPTY_CELL)
						findEmptyCells(cell);
				}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		int cell = 0;
		int uncover = 0;

		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				cell = field[(i * NUM_COLS) + j];


				if ((inGame && cell == BOMB_CELL)) {
					inGame = false;
				}

				if (!inGame) {
					if (cell == COVERED_BOMB_CELL) {
						cell = DRAW_BOMB;
					} else if (cell == MARKED_BOMB_CELL) {
						cell = DRAW_MARK;
					} else if (cell > COVERED_BOMB_CELL) {
						cell = DRAW_WRONG_MARK;
					} else if (cell > BOMB_CELL) {
						cell = DRAW_COVER;
					}
				} else {
					if (cell > COVERED_BOMB_CELL) {
						cell = DRAW_MARK;
					} else if (cell > BOMB_CELL) {
						cell = DRAW_COVER;
						uncover++;
					}
				}
				g.drawImage(img[cell], (j*CELL_SIZE),
						(i*CELL_SIZE), this);
			}
		}

		if (uncover == 0 && inGame) {
			inGame = false;
			statusBar.setText("WIN!");
		} else if (!inGame) {
			statusBar.setText("LOSE!");
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

			if (!inGame) {
				newGame();
				repaint();
			}

			if ((x < NUM_COLS * CELL_SIZE) && (y < NUM_ROWS * CELL_SIZE)) {

				if (e.getButton() == MouseEvent.BUTTON3) {

					if (field[(cRow * NUM_COLS) + cCol] > BOMB_CELL) {
						rep = true;

						if (field[(cRow * NUM_COLS) + cCol] <= COVERED_BOMB_CELL) {
							if (bombsLeft > 0) {
								field[(cRow * NUM_COLS) + cCol] += MARK_FOR_CELL;
								bombsLeft--;
								statusBar.setText(Integer.toString(bombsLeft));
							} else
								statusBar.setText("No marks left");
						} else {

							field[(cRow * NUM_COLS) + cCol] -= MARK_FOR_CELL;
							bombsLeft++;
							statusBar.setText(Integer.toString(bombsLeft));
						}
					}

				} else {

					if (field[(cRow * NUM_COLS) + cCol] > COVERED_BOMB_CELL) {
						return;
					}

					if ((field[(cRow * NUM_COLS) + cCol] > BOMB_CELL) &&
							(field[(cRow * NUM_COLS) + cCol] < MARKED_BOMB_CELL)) {

						field[(cRow * NUM_COLS) + cCol] -= COVER_FOR_CELL;
						rep = true;

						if (field[(cRow * NUM_COLS) + cCol] == BOMB_CELL)
							inGame = false;
						if (field[(cRow * NUM_COLS) + cCol] == EMPTY_CELL)
							findEmptyCells((cRow * NUM_COLS) + cCol);
					}
				}

				if (rep)
					repaint();

			}
		}
	}
}