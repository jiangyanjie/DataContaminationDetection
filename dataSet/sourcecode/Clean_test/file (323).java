package com.saharmassachi.ttt.java;

import java.util.ArrayList;

//THOUGHTS
// what defines perfect?
// not heuristics or learning. Then there is the possibility for improvement.
// no, perfect means a full NOT BINARY search. lame.
// machine learning is where it is at.
// ok so how do we implement a   tree of moves / search?
// root - > (1-8 possible moves by me) -> (1- 7 moves by opponent) -> ...
// then an algorithm to figure out which branch is most desirable
// (is it "least number of losses", is it "most possible wins"?
// wait this isn't chess. 
// you want "if I make this move they can only tie or I can win." 

//ok so defining the   tree. we need nodes.
// does it dynamically remake the tree every time? 
// it's TTT: space is so small efficiency gains are negligible.



public class AIbetterPlayer extends GamePlayer{
	GamePlayer otherplayer;
	public AIbetterPlayer(String name) {
		super(name);
	}

	public AIbetterPlayer(String name, String mark){
		super(name, mark);
	}

	@Override
	public void move(GameBoard b) {
		initOtherPlayer(b);
		MoveNode root = new MoveNode(b);
		
		addChildren(root);
		//ok now we have a full map.
		
		scoreTree(root);
		//and we know, for each move, the number of wins, losses, and ties possible.
		
		//first, try to avoid losses.
		MoveNode bestNode = new MoveNode(b);
		int mostwins = -1;
		for (MoveNode m: root.getChildren()){
			if (m.lost == 0){
				if(m.wins > mostwins){
					mostwins = m.wins;
					bestNode = m;
				}
			}
		}

		if (mostwins > 0){
			b.place(bestNode.movedown, bestNode.moveacross, this);
			return;
		}
		//if you can't avoid losses
		//chose the one where you win the most.
	
		for (MoveNode m: root.getChildren()){
			if(m.wins > mostwins){	
					mostwins = m.wins;
					bestNode = m;
			}	
		}
		
		
		b.place(bestNode.movedown, bestNode.moveacross, this);
		//for each node, for each possible move, make more children. 

	}

	
	private void addChildren(MoveNode root){
		GameBoard b = root.getBoard();
		if (b.isEnded()){
			return;
		}
		
		//given a board:
		//for each possible move, make a MoveNode node. 
		String[] stringMoves = getMoves(b);
		//some StringMoves will be null. in that case, just ignore them.
		for (String m : stringMoves){
			try{
				int one = getFirst(m);
				int two = getSecond(m);
				GamePlayer p = b.nextPlayer(); //sometimes p is me = sometimes it is the opponent.
				GameBoard dummyBoard = new GameBoard(this, p, b.getBoardCopy());
				dummyBoard.place(one, two, p);
				root.addChild(new MoveNode(dummyBoard, one, two, root));
			}
			catch(Exception e){
				//some StringMoves will be null. in that case, just ignore them.
			}
		}
		
		for(MoveNode m: root.getChildren()){
			addChildren(m);
		}
		
	}
	
	//For each possible move:
	private String[] getMoves(GameBoard b){
		GameCell[][] cells = b.getBoardCopy();
		String[] moves = findEmptyCells(cells);
		return moves;
	}

	//which cells are empty? 
	//Returns an array of moves represented like so "a|b"
	private String[] findEmptyCells(GameCell[][] cells){
		//given a board-as-matrix, find the empty cells;
		//Each string represents a mvoe.
		String[] empties = new String[9];
		int place = 0; //the next open cell in empties.
		for (int i = 0; i <3; i++){
			for (int j = 0; j< 3; j++){
				if(cells[i][j].isEmpty()){
					empties[place] = i + "|" + j;
					place ++;
				}}	}
		return empties;
	}

	//given a move of form "a}b", return a;
	private int getFirst(String m){
		String[] s = m.split("|");
		return Integer.parseInt(s[1]); //yes 1. 0 ==""
	}

	private int getSecond(String m){
		String[] s = m.split("|");
		return Integer.parseInt(s[3]); //yes. 3.
	}

	private void initOtherPlayer(GameBoard b){
		GamePlayer[] players = b.getPlayers();
		if (players[0].equals(this)){
			otherplayer = players[1];
			return;
		}
		otherplayer = players[0];
	}

	private void score(MoveNode m){
		//simple, will be replaced.
		GameBoard b = m.getBoard();
		GamePlayer winner = b.getWinner();
		if(winner == null){
			m.tie += 1;
		}
		if (winner.equals(this)){
			m.wins += 1;
		}
		if( winner.equals(otherplayer)){
			m.lost +=1;
		}
		return;
	}
	
	
	private void scoreTree(MoveNode n){
	//given a tree of nodes with this as the root - score them.
		if(n.isLeaf()){
			score(n);
			n.tellParent();
		}
	}
}
