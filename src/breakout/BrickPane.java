package breakout;

import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class BrickPane {

    public static final int BRICK_GAP = 2;
    public static final int ROW_NUM = 8;
    public static final int COL_NUM = 6;

    Brick[][] bricks;
    Integer[][] brickRepresentations;
    GridPane gridPane;

    public BrickPane(String filename) {
        bricks = new Brick[ROW_NUM][COL_NUM];
        brickRepresentations = new Integer[ROW_NUM][COL_NUM];
        readRepresentations(filename);
        makeBricks();
        setupPane();
    }

    private void readRepresentations(String filename) {
        String s = fileInput(filename);
        System.out.println(s);

        for (int r = 0; r < ROW_NUM; r++) {
            for (int c = 0; c < COL_NUM; c++) {
                brickRepresentations[r][c] = Integer.valueOf(s.charAt(r * COL_NUM * 2 + r + c * 2)) - 48;
            }
        }
    }

    private String fileInput(String filename) {
        FileInputStream in = null;
        String s = null;
        try {
            File inputFile = new File(filename);
            in = new FileInputStream(inputFile);
            byte bt[] = new byte[(int) inputFile.length()];
            in.read(bt);
            s = new String(bt);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    private void makeBricks() {
        for (int r = 0; r < ROW_NUM; r++) {
            for (int c = 0; c < COL_NUM; c++) {
                bricks[r][c] = new Brick(brickRepresentations[r][c]);
            }
        }
    }

    private void setupPane() {
        gridPane = new GridPane();
        gridPane.setHgap(BRICK_GAP);
        gridPane.setVgap(BRICK_GAP);

        for (int r = 0; r < ROW_NUM; r++) {
            for (int c = 0; c < COL_NUM; c++) {
                gridPane.add(bricks[r][c].getInstance(), r, c);
            }
        }
    }


    public GridPane getInstance() {
        return gridPane;
    }
}
