import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ZBlock extends Block {

    private List<int[][]> blockMatrix = new ArrayList<>();

    public ZBlock() {
        super(Color.RED);

        blockMatrix.add(new int[][] {
                {4, 4, 0, 0},
                {0, 4, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        });

        blockMatrix.add(new int[][] {
                {0, 0, 4, 0},
                {0, 4, 4, 0},
                {0, 4, 0, 0},
                {0, 0, 0, 0}
        });
    }


    public List<int[][]> getBlockMatrix() {
        return blockMatrix;
    }
}