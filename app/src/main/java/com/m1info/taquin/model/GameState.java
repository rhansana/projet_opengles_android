package com.m1info.taquin.model;

import android.util.Log;

import com.m1info.taquin.sprite.Shape;

import java.util.ArrayList;
import java.util.Random;

public class GameState {
    private ArrayList<Shape> board;
    private ArrayList<Shape> solved;
    private final int nbLines;
    private final int nbCol;
    private boolean isShuffle;

    public GameState(int nbLines, int nbCol, ArrayList<Shape> board){
        this.nbLines = nbLines;
        this.nbCol = nbCol;
        this.board = board;
        this.board.add((nbLines*nbCol)-1,null);
        this.solved = new ArrayList<>();
        for(int i = 0; i<this.board.size();i++){
            solved.add(i,this.board.get(i));
        }
        for(int i = 0; i<50;i++) {
            randomizeMoves();
        }
        isShuffle = true;
    }

    public Shape findByLinesAndCol(int line, int col){
        return this.board.get(line*nbLines + col);
    }

    public void setShapeByLinesAndCol(int line, int col, Shape shape){
        this.board.set(line*nbLines+col,shape);
    }

    public void randomizeMoves() {
        int index = this.board.indexOf(null);
        int line = index/3;
        int col = (index%3);

        Random random = new Random();
        int rand = 0;
        while (true) {
            rand = random.nextInt(5);
            if (rand != 0) {
                break;
            }
        }
        Log.d("message", "rand = " + rand);
        Log.d("message", "line = " + line);
        Log.d("message", "col = " + col);
        Log.d("message", "======================");



        switch (rand) {
            case 1:
                if(line != 0) {
                    if (isMoveable(line-1, col)) {
                        moveShape(line-1, col);
                        Log.d("message", "FromTopToBottom");
                    }
                }
                break;
            case 2:
                if(line != nbLines - 1) {
                    if (isMoveable(line+1, col)) {
                        moveShape(line+1, col);
                        Log.d("message", "FromBottomToTop");
                    }
                }
                break;

            case 3:
                if(col != nbCol - 1) {
                    if (isMoveable(line, col+1)) {
                        moveShape(line, col+1);
                        Log.d("message", "FromRightToLeft");

                    }
                }
                break;

            case 4:
                if(col != 0 ) {
                    if (isMoveable(line, col-1)) {
                        moveShape(line, col-1);
                        Log.d("message", "FromLeftToRight");

                    }
                }
                break;
        }
    }

    public boolean isMoveable(int line,int col){
        int target = nbLines*line + col;
        if(target < this.board.size()){
            if(line !=0 && findByLinesAndCol(line - 1, col) == null){
                return true;
            }else{
                if(line != nbLines - 1 && findByLinesAndCol(line+1,col) == null){
                    return true;
                }else{
                    if(col != nbCol - 1 && findByLinesAndCol(line,col+1) == null){
                        return true;
                    }else{
                        if(col != 0 && findByLinesAndCol(line,col-1) == null){
                            return true;
                        }else{
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void moveShape(int line, int col) {
        int target = nbLines * line + col;
        if (target < this.board.size()) {
            if (line != 0 && findByLinesAndCol(line - 1, col) == null) {
                this.moveTop(line, col);
            } else {
                if (line != nbLines - 1 && findByLinesAndCol(line + 1, col) == null) {
                    this.moveBottom(line, col);
                } else {
                    if (col != nbCol - 1 && findByLinesAndCol(line, col + 1) == null) {
                        this.moveRight(line, col);
                    } else {
                        if (col != 0 && findByLinesAndCol(line, col - 1) == null) {
                            this.moveLeft(line, col);
                        }
                    }
                }
            }
        }
    }

    public void moveTop(int line, int col){
        Shape toMove = this.findByLinesAndCol(line, col);
        this.setShapeByLinesAndCol(line,col,null);
        this.setShapeByLinesAndCol(line-1,col,toMove);

        float[] newPos = {toMove.get_position()[0],toMove.get_position()[1]+3.5f};
        toMove.set_position(newPos);

        Log.d("message","Move Top");

    }

    public void moveBottom(int line,int col){
        Shape toMove = this.findByLinesAndCol(line, col);
        this.setShapeByLinesAndCol(line,col,null);
        this.setShapeByLinesAndCol(line+1,col,toMove);

        float[] newPos = {toMove.get_position()[0],toMove.get_position()[1]-3.5f};
        toMove.set_position(newPos);

        Log.d("message","Move Bot");

    }

    public void moveRight(int line, int col){
        Shape toMove = this.findByLinesAndCol(line, col);
        this.setShapeByLinesAndCol(line,col,null);
        this.setShapeByLinesAndCol(line,col+1,toMove);

        float[] newPos = {toMove.get_position()[0]+3.5f,toMove.get_position()[1]};
        toMove.set_position(newPos);

        Log.d("message","Move Right");

    }

    public void moveLeft(int line,int col){
        Shape toMove = this.findByLinesAndCol(line, col);
        this.setShapeByLinesAndCol(line,col,null);
        this.setShapeByLinesAndCol(line,col-1,toMove);

        float[] newPos = {toMove.get_position()[0]-3.5f,toMove.get_position()[1]};
        toMove.set_position(newPos);

        Log.d("message","Move Left");
    }

    public ArrayList<Shape> getAllShape(){
        return this.board;
    }

    public boolean getIsShuffle(){
        return isShuffle;
    }

    public void setIsShuffle(boolean c){
        this.isShuffle = c;
    }

    public boolean isSolved(){
        if(this.solved.equals(this.board)){
            this.isShuffle = false;
            return true;
        }else{
            return false;
        }
    }
}
