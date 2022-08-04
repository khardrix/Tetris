package objects;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import tetraminos.*;

public class Block {

    private int x;
    private int y;
    private Color color;


    // 1-arg constructor
    public Block(Color color) {
        this.color = color;

        this.x = 20;
        this.y = 155;
    }


    // 2-arg constructor
    public Block(int x, int y) {
        this.x = x;
        this.y = y;

        this.color = Color.WHITE;
    }


    // 3-arg constructor
    public Block(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }


    // Set the Color of the Block
    public void setColor(Color color) {
        this.color = color;
    }


    // Get the correct matrix
    public int[][] getBlockMatrix(int shape, int orientation) {
        List<int[][]> blockList = new ArrayList<>();

        switch (shape) {
            case 1:
                OBlock oBlock = new OBlock();
                blockList = oBlock.getBlockMatrix();
                break;
            case 2:
                IBlock iBlock = new IBlock();
                blockList = iBlock.getBlockMatrix();
                break;
            case 3:
                SBlock sBlock = new SBlock();
                blockList = sBlock.getBlockMatrix();
                break;
            case 4:
                ZBlock zBlock = new ZBlock();
                blockList = zBlock.getBlockMatrix();
                break;
            case 5:
                LBlock lBlock = new LBlock();
                blockList = lBlock.getBlockMatrix();
                break;
            case 6:
                JBlock jBlock = new JBlock();
                blockList = jBlock.getBlockMatrix();
                break;
            case 7:
                TBlock tBlock = new TBlock();
                blockList = tBlock.getBlockMatrix();
                break;
        }

        return blockList.get(orientation);
    }
}