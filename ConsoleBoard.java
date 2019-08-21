package gui;

import java.util.ArrayList;

import connect4.Constants;

public class ConsoleBoard {
	
	private Move lastMove;
	private int lastPlayerPlayed; 
	private int winner;
	private int [][] mainBoard;
	
	private boolean isOverflowBoard;
	private boolean gameOver;
	
	public ConsoleBoard() {
		this.lastMove = new Move();
		this.lastPlayerPlayed = Player.two;
		this.winner = Player.noPlayer;
		this.mainBoard = new int [6][7];
		this.isOverflowBoard = false;
		this.gameOver = false;
		
		for(int i=0; i<6; i++) {
			for(int j=0; j<7; j++) {
				mainBoard[i][j] = Player.noPlayer;
			}
		}
	}
	
	public ConsoleBoard(ConsoleBoard board) {
		lastMove = board.lastMove;
		lastPlayerPlayed = board.lastPlayerPlayed;
		winner = board.winner;
		mainBoard = new int [6][7];
		this.isOverflowBoard = false;
		this.gameOver = false;
		
		for(int i=0; i<6; i++) {
			for(int j=0; j<7; j++) {
				mainBoard[i][j] = board.mainBoard[i][j];
			}
		}
	}
	
	public Move getLastMove() {
		return lastMove;
	}
	
	public void setLastMove(Move lastMove) {
		this.lastMove.setRow(lastMove.getRow());
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	
	
	public int getLastPlayerPlayed() {
		return lastPlayerPlayed;
	}
	
	
	public void setLastPlayerPlayed(int lastLetterPlayed) {
		this.lastPlayerPlayed = lastLetterPlayed;
	}
	
	
	public int[][] getMainBoard() {
		return mainBoard;
	}
	
	
	public void setMainBoard(int[][] gameBoard) {
		for(int i=0; i<6; i++) {
			for(int j=0; j<7; j++) {
				this.mainBoard[i][j] = mainBoard[i][j];
			}
		}
	}
	
	
	public int getWinner() {
		return winner;
	}
	
	
	public void setWinner(int winner) {
		this.winner = winner;
	}
	
	
	public boolean isGameOver() {
		return gameOver;
	}


	public void setGameOver(boolean isGameOver) {
		this.gameOver = isGameOver;
	}
	
	
	public boolean hasOverflowOccured() {
		return isOverflowBoard;
	}

	
	public void setOverflowBoard(boolean overflowBoard) {
		this.isOverflowBoard = overflowBoard;
	}
	
	public void createMove(int col, int player) {
		
		this.lastMove = new Move(getRowPosition(col), col);
		this.lastPlayerPlayed = player;
		this.mainBoard[getRowPosition(col)][col] = player;
	}
	
	public int getRowPosition(int col) {
		int rowPosition = -1;
		for (int row=0; row<6; row++) {
			if (mainBoard[row][col] == Player.noPlayer) {
				rowPosition = row;
			}
		}
		return rowPosition;
	}
	
	public boolean canMove(int row, int col) {
		if ((row <= -1) || (col <= -1) || (row > 5) || (col > 6)) {
			return false;
		}
		return true;
	}
	
	public boolean checkFullColumn(int col) {
		if (mainBoard[0][col] == Player.noPlayer)
			return false;
		return true;
	}
	
	public ArrayList<ConsoleBoard> getChild(int player){
		ArrayList<ConsoleBoard> child = new ArrayList<ConsoleBoard>();
		for(int col=0; col<7; col++) {
			if(checkFullColumn(col)) {
				ConsoleBoard childBoard = new ConsoleBoard(this);
				childBoard.createMove(col, player);
				child.add(childBoard);
			}
		}
		return child;
	}
	
	public boolean isWin() {
		//horizontal
		for(int i=5; i>=0; i--) {
			for(int j=0; j<4; j++) {
				if(mainBoard[i][j] == mainBoard[i][j+1] && mainBoard[i][j] == mainBoard[i][j+2] && mainBoard[i][j] == mainBoard[i][j+3] && mainBoard[i][j] != Player.noPlayer) {
					setWinner(mainBoard[i][j]);
					return true;
				}
			}
		}
		//vertical
		for(int i=5; i>=3; i--) {
			for(int j=0; j<7; j++) {
				if(mainBoard[i][j] == mainBoard[i-1][j] && mainBoard[i][j] == mainBoard[i-2][j] && mainBoard[i][j] == mainBoard[i-3][j] && mainBoard[i][j] != Player.noPlayer) {
					setWinner(mainBoard[i][j]);
					return true;
				}
			}
		}
		//descending diagonal
		for(int i=0; i<3; i++) {
			for(int j=0; j<4; j++) {
				if(mainBoard[i][j] == mainBoard[i+1][j+1] && mainBoard[i][j] == mainBoard[i+2][j+2] && mainBoard[i][j] == mainBoard[i+3][j+3] && mainBoard[i][j] != Player.noPlayer) {
					setWinner(mainBoard[i][j]);
					return true;
				}
			}
		}
		//ascending diagonal
		for(int i=5; i>=3; i--) {
			for(int j=0; j<7; j++) {
				if(mainBoard[i][j] == mainBoard[i-1][j+1] && mainBoard[i][j] == mainBoard[i-2][j+2] && mainBoard[i][j] == mainBoard[i-3][j+3] && mainBoard[i][j] != Player.noPlayer) {
					setWinner(mainBoard[i][j]);
					return true;
				}
			}
		}
		
		setWinner(Player.noPlayer);
		return false;
		
	}
	
	public boolean checkGameOver() {
		if(isWin()) {
			return true;
		}
		
		for(int i=0; i<6; i++) {
			for(int j=0; j<7; j++) {
				if(mainBoard[i][i] == Player.noPlayer) {
                    return false;
                }
            }
        }
		return true;
	}
	
	public int check3InARow(int playerSymbol)
	{
		int times = 0;
		for(int row=5;row>=0;row--)
		{
			for(int col=0;col<7;col++)
			{
				if (canMove(row, row + 2)) {
					if (mainBoard[row][col] == mainBoard[row][col+ 1]
							&& mainBoard[row][col] == mainBoard[row][col+ 2];
							&& mainBoard[row][col] == playerSymbol) {
						times++;
					}
				}
			}
		}

	
		// Check for 3 consecutive checkers in a row, vertically.
		for (int row = 5; row >= 0; row--) {
			for (int col = 0; col < 7; col++) {
				if (canMove(row - 2, col)) {
					if (mainBoard[row][col] == mainBoard[row - 1][col]
							&& mainBoard[row][col] == mainBoard[row - 2][col]
							&& mainBoard[row][col] == playerSymbol) {
						times++;
					}
				}
			}
		}

		// Check for 3 consecutive checkers in a row, in descending diagonal.
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				if (canMove(row + 2, col + 2)) {
					if (mainBoard[row][col] == mainBoard[row + 1][col + 1]
							&& mainBoard[row][col] == mainBoard[row + 2][col + 2]
							&& mainBoard[row][col] == playerSymbol) {
						times++;
					}
				}
			}
		}

		// Check for 3 consecutive checkers in a row, in ascending diagonal.
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				if (canMove(row - 2, col + 2)) {
					if (mainBoard[row][col] == mainBoard[row - 1][col + 1]
							&& mainBoard[row][col] == mainBoard[row - 2][col + 2]
							&& mainBoard[row][col] == playerSymbol) {
						times++;
					}
				}
			}
		}

		return times;
				
	}
	
	
public int check2InARow(int player) {
		
		int times = 0;
		
		for (int row = 5; row >= 0; row--) {
			for (int col = 0; col < 7; col++) {
				if (canMove(row, col + 1)) {
					if (mainBoard[row][col] == mainBoard[row][col + 1]
							&& mainBoard[row][col] == player) {
						times++;
					}
				}
			}
		}

		for (int row = 5; row >= 0; row--) {
			for (int col = 0; col < 7; col++) {
				if (canMove(row - 1, col)) {
					if (mainBoard[row][col] == mainBoard[row - 1][col]
							&& mainBoard[row][col] == player) {
						times++;
					}
				}
			}
		}

		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				if (canMove(row + 1, col + 1)) {
					if (mainBoard[row][col] == mainBoard[row + 1][col + 1]
							&& mainBoard[row][col] == player) {
						times++;
					}
				}
			}
		}

		
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 7; col++) {
				if (canMove(row - 1, col + 1)) {
					if (mainBoard[row][col] == mainBoard[row - 1][col + 1]
							&& mainBoard[row][col] == player) {
						times++;
					}
				}
			}
		}

		return times;
				
	}

	
	
}
