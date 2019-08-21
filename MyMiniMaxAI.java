package connect4;

import java.util.ArrayList;
import java.util.Random;

public class MyMiniMaxAI {

		int maxDepth=3;
		int aiPlayer=0;
		//int aiPlayer_2=1;
		
		public MyMiniMaxAI(int maxDepth,int aiPlayer_1)
		{
			this.maxDepth=maxDepth;
			this.aiPlayer=aiPlayer_1;
		//	this.aiPlayer_2=aiPlayer_2;
		}

		public int getMaxDepth() {
			return maxDepth;
		}

		public void setMaxDepth(int maxDepth) {
			this.maxDepth = maxDepth;
		}

		public int getAiPlayer_1() {
			return aiPlayer;
		}

		public void setAiPlayer_1(int aiPlayer_1) {
			this.aiPlayer = aiPlayer_1;
		}

		
		//Initialize the miniMax algorithm
		
		public Move miniMax(Board board)
		{
			if(aiPlayer==1) {
				return max(new Board(board), 0); // 0 is the exit point of minimax alogorithm
			}
			else
				return min(new Board(board),0);
			
		}

		private Move min(Board board, int depth) {
			// TODO Auto-generated method stub
			
			int player_2=1;
			ArrayList<Board> children = new ArrayList<Board>(board.getChildren(player_2));
			Random random = new Random();
			if(board.checkGameOver()||depth==maxDepth)
			{
				Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
				return lastMove;
			}
			Move minMove= new Move(999999999);
			
			for(Board successor:children)
			{
				Move move = max(successor, depth + 1);
				if(move.getValue()>=minMove.getValue())
				{
					if(move.getValue()==minMove.getValue())
					{
						if (random.nextInt(2) == 0) {
							minMove.setRow(successor.getLastMove().getRow());
							minMove.setCol(successor.getLastMove().getCol());
							minMove.setValue(move.getValue());
	                    }
						
						else
						{
							minMove.setRow(successor.getLastMove().getRow());
							minMove.setCol(successor.getLastMove().getCol());
							minMove.setValue(move.getValue());	
						}
					}
				}
			}
			return minMove;
			
		}

		private Move max(Board board, int depth) {
			// TODO Auto-generated method stub
			int player_1=0;
			ArrayList<Board> children = new ArrayList<Board>(board.getChildren(player_1));
			Random random = new Random();
			if(board.checkGameOver()||depth==maxDepth)
			{
				Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
				return lastMove;
			}
			Move maxMove= new Move(-999999999);
			
			for(Board successor:children)
			{
				Move move = min(successor, depth + 1);
				if(move.getValue()>=maxMove.getValue())
				{
					if(move.getValue()==maxMove.getValue())
					{
						if (random.nextInt(2) == 0) {
	                        maxMove.setRow(successor.getLastMove().getRow());
	                        maxMove.setCol(successor.getLastMove().getCol());
	                        maxMove.setValue(move.getValue());
	                    }
						
						else
						{
							  maxMove.setRow(successor.getLastMove().getRow());
		                      maxMove.setCol(successor.getLastMove().getCol());
		                      maxMove.setValue(move.getValue());	
						}
					}
				}
			}
			return maxMove;
		}
}
