package view;


import controller.GameController;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    public static final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private static ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    public List<List> steps = new ArrayList<>();
    private ChessGameFrame chessGameFrame;
    TestThread testThread = new TestThread();
//    private GameController controller = new GameController(this);

    public Chessboard(int width, int height, ChessGameFrame chessGameFrame) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        this.chessGameFrame = chessGameFrame;
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initiateEmptyChessboard();
//        ChessGameFrame.changeCurrentPlayer();
    }

    public List<String> getLastStep(){
        List<String> lastStep = steps.get(steps.size() - 2);
        steps.remove(steps.size() - 1);
        return lastStep;
    }

    public void storeStep(){
        ArrayList<String> storedChessboard = new ArrayList<>();
        String[] result = {"","","","","","","",""};
        for(int i = 0;i < 8;i++){
            for(int j = 0;j < 8;j++){
                System.out.println("chessComponents[i][j].getName1()" + chessComponents[i][j].getName1());
                switch(chessComponents[i][j].getName1()){
                    case 'R' :
                        result[i]+="R";
                        break;
                    case 'r' :
                        result[i]+="r";
                        break;
                    case 'N' :
                        result[i]+="N";
                        break;
                    case 'n' :
                        result[i]+="n";
                        break;
                    case 'B' :
                        result[i]+="B";
                        break;
                    case 'b' :
                        result[i]+="b";
                        break;
                    case 'K' :
                        result[i]+="K";
                        break;
                    case 'k' :
                        result[i]+="k";
                        break;
                    case 'Q' :
                        result[i]+="Q";
                        break;
                    case 'q' :
                        result[i]+="q";
                        break;
                    case 'P' :
                        result[i]+="P";
                        break;
                    case 'p' :
                        result[i]+="p";
                        break;
                    default :
                        result[i]+="_";
                }
            }
            System.out.println("result[i]" + result[i]);
            storedChessboard.add(result[i]);
        }
        if(getCurrentColor() == ChessColor.WHITE){
            storedChessboard.add("w");
        }else{
            storedChessboard.add("b");
        }
        steps.add(storedChessboard);
    }

    public String stepToString(){
        List<String> step = steps.get(steps.size() - 1);
        String result = "";
        for(int i = 0;i < step.size() - 1;i++){
            //System.out.println("step.get(i)" + step.get(i));
            result = result + step.get(i) + "\n";
            //System.out.println("result" + result);
        }
        if(getCurrentColor() == ChessColor.WHITE){
            result+="w";
        }else{
            result+="b";
        }
        //System.out.println("final result" + result);
        return result;
    }

    public static ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
        chessComponent.repaint();
        //storeStep();
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) throws MalformedURLException {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            if(chess2.getName1() == 'K'){
                chessGameFrame.addWhiteWin();
                //chessGameFrame.startNewGame();
            }
            if(chess2.getName1() == 'k'){
                chessGameFrame.addBlackWin();
                //chessGameFrame.startNewGame();
            }
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE, '_'));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        //swapColor();
        ChessGameFrame.changeCurrentPlayer();
        chess1.repaint();
        chess2.repaint();
        storeStep();
        Music.playmovemusic();
        ChessGameFrame.newTime();
//        for (int i = 0; i < ; i++) {
//
//        }
    }

    public ChessboardPoint getKingPoint(ChessColor color){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(color == ChessColor.WHITE) {
                    if (chessComponents[i][j].getName1() == 'k'){
                        return new ChessboardPoint(i,j);
                    }
                }else{
                    if (chessComponents[i][j].getName1() == 'K'){
                        return new ChessboardPoint(i,j);
                    }
                }
            }
        }
        return null;
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE, '_'));
            }
        }
    }

    public static void swapColor() {
        currentColor = currentColor == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
    }

    private void initRookOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }
    private void initKingOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }
    private void initQueenOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }
    private void initKnightOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }
    private void initBishopOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }
    private void initPawnOnBoard(int row, int col, ChessColor color,char name) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, name);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

//    protected void repaintComponent1(Graphics g) {
//        super.paintComponent(g);
//        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].repaint();
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (chessData.get(i).charAt(j)) {
                    case 'R':
                        initRookOnBoard(i, j, ChessColor.BLACK, 'R');
                        break;
                        case 'r':
                            initRookOnBoard(i, j, ChessColor.WHITE, 'r');
                            break;
                        case 'N':
                            initKnightOnBoard(i, j, ChessColor.BLACK, 'N');
                            break;
                        case 'n':
                            initKnightOnBoard(i, j, ChessColor.WHITE, 'n');
                            break;
                        case 'B':
                            initBishopOnBoard(i, j, ChessColor.BLACK, 'B');
                            break;
                        case 'b':
                            initBishopOnBoard(i, j, ChessColor.WHITE, 'b');
                            break;
                        case 'Q':
                            initQueenOnBoard(i, j, ChessColor.BLACK, 'Q');
                            break;
                        case 'q':
                            initQueenOnBoard(i, j, ChessColor.WHITE, 'q');
                            break;
                        case 'K':
                            initKingOnBoard(i, j, ChessColor.BLACK, 'K');
                            break;
                        case 'k':
                            initKingOnBoard(i, j, ChessColor.WHITE, 'k');
                            break;
                        case 'P':
                            initPawnOnBoard(i, j, ChessColor.BLACK, 'P');
                            break;
                            case 'p':
                            initPawnOnBoard(i, j, ChessColor.WHITE, 'p');
                            break;
                    }
                }
            }
            if (chessData.get(8).charAt(0) == 'w') {
                currentColor = ChessColor.WHITE;
            } else {
                currentColor = ChessColor.BLACK;
            }
            ChessGameFrame.changeCurrentPlayer();
            storeStep();
            ChessGameFrame.second = 60;
            testThread.start();
        }

    public void loadUndoGame(List<String> chessData) {
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponents[i][j].repaint();
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (chessData.get(i).charAt(j)) {
                    case 'R':
                        initRookOnBoard(i, j, ChessColor.BLACK, 'R');
                        break;
                    case 'r':
                        initRookOnBoard(i, j, ChessColor.WHITE, 'r');
                        break;
                    case 'N':
                        initKnightOnBoard(i, j, ChessColor.BLACK, 'N');
                        break;
                    case 'n':
                        initKnightOnBoard(i, j, ChessColor.WHITE, 'n');
                        break;
                    case 'B':
                        initBishopOnBoard(i, j, ChessColor.BLACK, 'B');
                        break;
                    case 'b':
                        initBishopOnBoard(i, j, ChessColor.WHITE, 'b');
                        break;
                    case 'Q':
                        initQueenOnBoard(i, j, ChessColor.BLACK, 'Q');
                        break;
                    case 'q':
                        initQueenOnBoard(i, j, ChessColor.WHITE, 'q');
                        break;
                    case 'K':
                        initKingOnBoard(i, j, ChessColor.BLACK, 'K');
                        break;
                    case 'k':
                        initKingOnBoard(i, j, ChessColor.WHITE, 'k');
                        break;
                    case 'P':
                        initPawnOnBoard(i, j, ChessColor.BLACK, 'P');
                        break;
                    case 'p':
                        initPawnOnBoard(i, j, ChessColor.WHITE, 'p');
                        break;
                }
            }
        }
        swapColor();
//        if (chessData.get(8).charAt(0) == 'w') {
//            currentColor = ChessColor.WHITE;
//        } else {
//            currentColor = ChessColor.BLACK;
//        }
        ChessGameFrame.changeCurrentPlayer();
        ChessGameFrame.second = 60;
        testThread.start();
    }
}
