package com.m1info.taquin.model;

import com.m1info.taquin.sprite.Board;
import com.m1info.taquin.sprite.Diamond;
import com.m1info.taquin.sprite.Shape;
import com.m1info.taquin.sprite.Square;
import com.m1info.taquin.sprite.Triangle;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {

    private Board mBoard;

    private GameState mGameState;

    private Square mSquareRed;
    private Triangle mTriangleRed;
    private Diamond mDiamondRed;

    private Square mSquareGreen;
    private Triangle mTriangleGreen;
    private Diamond mDiamondGreen;

    private Square mSquareBlue;
    private Triangle mTriangleBlue;

    private float[] mBoardPosition = {0.0f,0.0f};

    private float[] mSquareRedPosition = {-3.5f, 4.0f};
    private float[] mTriangleRedPosition = {-3.5f, 0.5f};
    private float[] mDiamondRedPosition = {-3.5f, -3.0f};

    private float[] mSquareGreenPosition = {0.0f, 4.0f};
    private float[] mTriangleGreenPosition = {0.0f, 0.5f};
    private float[] mDiamondGreenPosition = {0.0f, -3.0f};

    private float[] mSquareBluePosition = {3.5f, 4.0f};
    private float[] mTriangleBluePosition = {3.5f,0.5f};

    public Game(){
        mBoard = new Board(mBoardPosition);

        float[] redColor = {1.0f,0.0f,0.0f,0.0f,
                1.0f,0.0f,0.0f,0.0f,
                1.0f,0.0f,0.0f,0.0f,
                1.0f,0.0f,0.0f,0.0f};

        mSquareRed   = new Square(mSquareRedPosition,redColor);
        mTriangleRed = new Triangle(mTriangleRedPosition,redColor);
        mDiamondRed = new Diamond(mDiamondRedPosition,redColor);

        float[] greenColor = {0.0f,1.0f,0.0f,0.0f,
                0.0f,1.0f,0.0f,0.0f,
                0.0f,1.0f,0.0f,0.0f,
                0.0f,1.0f,0.0f,0.0f,};

        mSquareGreen = new Square(mSquareGreenPosition,greenColor);
        mTriangleGreen = new Triangle(mTriangleGreenPosition,greenColor);
        mDiamondGreen = new Diamond(mDiamondGreenPosition,greenColor);

        float[] blueColor ={0.0f,0.0f,1.0f,0.0f,
                0.0f,0.0f,1.0f,0.0f,
                0.0f,0.0f,1.0f,0.0f,
                0.0f,0.0f,1.0f,0.0f};

        mSquareBlue = new Square(mSquareBluePosition,blueColor);
        mTriangleBlue = new Triangle(mTriangleBluePosition,blueColor);

        ArrayList<Shape> lesShapes = new ArrayList<>(Arrays.asList(mSquareRed,mSquareGreen,mSquareBlue,
                mTriangleRed,mTriangleGreen,mTriangleBlue,
                mDiamondRed,mDiamondGreen));
        mGameState = new GameState(3,3,lesShapes);
    }

    public void drawBoard(float[] scratch){
        this.mBoard.draw(scratch);
    }

    public GameState getmGameState(){
        return this.mGameState;
    }
}
