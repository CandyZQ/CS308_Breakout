package breakout;

import javafx.scene.layout.ColumnConstraints;
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

    private Brick[][] bricks;
    private Integer[][] brickRepresentations;
    private GridPane gridPane;

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
                if (brickRepresentations[r][c] == 0) {
                    bricks[r][c] = null;
                } else {
                    bricks[r][c] = new Brick(brickRepresentations[r][c]);
                }
            }
        }

    }
    private void setupPane() {
        gridPane = new GridPane();
        gridPane.setPrefSize(Main.BG_WIDTH, Main.BG_HEIGHT);
//        for (int i = 0; i < COL_NUM; i++) {
//            ColumnConstraints c = new ColumnConstraints();
//            c.setPercentWidth(100 / COL_NUM);
//            gridPane.getColumnConstraints().add(c);
//        }
        gridPane.setHgap(BRICK_GAP);
        gridPane.setVgap(BRICK_GAP);

        for (int r = 0; r < ROW_NUM; r++) {
            for (int c = 0; c < COL_NUM; c++) {
                if (bricks[r][c] != null) {
                    gridPane.add(bricks[r][c].getInstance(), c, r);
                }
            }
        }
    }

    private void updataPane(Brick brick, int r, int c) {
        for (var node: gridPane.getChildren()) {
            if (gridPane.getRowIndex(node) == r && gridPane.getColumnIndex(node) == c) {
                gridPane.getChildren().remove(node);
                if (brick != null) {
                    gridPane.add(brick.getInstance(), c, r);
                }
            }
        }
    }

    public GridPane getInstance() {
        return gridPane;
    }

    public Brick[][] getBricks() {
        return bricks;
    }

    public void updateBrickStatus(int r, int c) {
        if (bricks[r][c].takeDamage()) {
            bricks[r][c] = null;
        }
        updataPane(bricks[r][c], r, c);
    }
}
