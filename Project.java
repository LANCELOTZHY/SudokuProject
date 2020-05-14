package Project;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Project {
    public static void main(String[] args) {
        new SudokuGrid();
    }
}

class SudokuGrid extends JFrame {
    JLabel jllNewGame;
    JLabel jllHintNum;
    JButton jbnEasy;
    JButton jbnMid;
    JButton jbnDit;
    JButton jbnBackout;
    JButton jbnHint;
    JButton jbnWipe;
    JButton jbnNote;
    JButton jbnArchive;
    JButton jbnRead;
    JButton jbnEnd;
    JPanel[] pnlGame;
    JTextField[][][] txtGame;
    UndoManager undoManager;

    public SudokuGrid() {
        pnlGame = new JPanel[9];
        txtGame = new JTextField[9][3][3];
        gridInit();
    }

    public void gridInit() {
        Container container = this.getContentPane();
        container.setLayout(null);
        this.setSize(1100, 725);
        this.setLocationRelativeTo(null);
        this.setTitle("快乐数独");
        undoManager = new UndoManager();

        jllNewGame = new JLabel();
        jllNewGame.setIcon(new ImageIcon("shudu.png"));
        jllNewGame.setSize(375, 100);
        jllNewGame.setLocation(700, 0);
        container.add(jllNewGame);

        jbnEasy = new JButton("简单");
        jbnEasy.setBackground(Color.green);
        jbnEasy.setSize(125, 100);
        jbnEasy.setLocation(700, 100);
        jbnEasy.setFont(new Font("宋体", Font.PLAIN, 40));
        jbnEasy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                int[][] sudoku = easyGame();
                setBoard(sudoku);
            }
        });
        container.add(jbnEasy);

        jbnMid = new JButton("中等");
        jbnMid.setBackground(Color.yellow);
        jbnMid.setSize(125, 100);
        jbnMid.setLocation(825, 100);
        jbnMid.setFont(new Font("宋体", Font.PLAIN, 40));
        jbnMid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                int[][] sudoku = midGame();
                setBoard(sudoku);
            }
        });
        container.add(jbnMid);

        jbnDit = new JButton("困难");
        jbnDit.setBackground(Color.red);
        jbnDit.setSize(125, 100);
        jbnDit.setLocation(950, 100);
        jbnDit.setFont(new Font("宋体", Font.PLAIN, 40));
        jbnDit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                int[][] sudoku = hardGame();
                setBoard(sudoku);
            }
        });
        container.add(jbnDit);

        jbnEnd = new JButton("判断结束");
        jbnEnd.setBackground(Color.pink);
        jbnEnd.setSize(375, 100);
        jbnEnd.setLocation(700, 200);
        jbnEnd.setFont(new Font("宋体", Font.PLAIN, 40));
        jbnEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                end();
            }
        });
        container.add(jbnEnd);

        jbnArchive = new JButton("存档");
        jbnArchive.setFont(new Font("宋体", Font.PLAIN, 40));
        jbnArchive.setBackground(Color.cyan);
        jbnArchive.setSize(150, 100);
        jbnArchive.setLocation(735, 350);
        jbnArchive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                storage();
            }
        });
        container.add(jbnArchive);

        jbnRead = new JButton("读取");
        jbnRead.setSize(150, 100);
        jbnRead.setLocation(885, 350);
        jbnRead.setFont(new Font("宋体", Font.PLAIN, 40));
        jbnRead.setBackground(Color.cyan);
        jbnRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                read();
            }
        });
        container.add(jbnRead);

        jbnBackout = new JButton("撤销");
        jbnBackout.setBackground(Color.ORANGE);
        jbnBackout.setSize(150, 100);
        jbnBackout.setLocation(735, 450);
        jbnBackout.setFont(new Font("楷体", Font.PLAIN, 40));
        jbnBackout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });
        container.add(jbnBackout);

        jbnHint = new JButton("提示");
        jbnHint.setBackground(Color.ORANGE);
        jbnHint.setSize(150, 100);
        jbnHint.setLocation(735, 550);
        jbnHint.setFont(new Font("楷体", Font.PLAIN, 40));
        container.add(jbnHint);

        jbnWipe = new JButton("擦除");
        jbnWipe.setBackground(Color.ORANGE);
        jbnWipe.setSize(150, 100);
        jbnWipe.setLocation(885, 450);
        jbnWipe.setFont(new Font("楷体", Font.PLAIN, 40));
        container.add(jbnWipe);

        jbnNote = new JButton("笔记");
        jbnNote.setBackground(Color.ORANGE);
        jbnNote.setSize(150, 100);
        jbnNote.setLocation(885, 550);
        jbnNote.setFont(new Font("楷体", Font.PLAIN, 40));
        container.add(jbnNote);

        jllHintNum = new JLabel("3");//剩余提示次数
        jllHintNum.setLocation(715, 600);
        jllHintNum.setSize(30, 40);
        jllHintNum.setFont(new Font("楷体", Font.PLAIN, 30));
        container.add(jllHintNum);

        //创建文本区域
        for (int i = 0; i < 9; i++) {
            pnlGame[i] = new JPanel();
            pnlGame[i].setBorder(BorderFactory.createLineBorder(Color.black, 2));
            pnlGame[i].setLayout(new GridLayout(3, 3));
            pnlGame[i].setSize(225, 225);
            if (i == 0) {
                pnlGame[i].setLocation(0, 0);
            } else if (i == 1) {
                pnlGame[i].setLocation(225, 0);
            } else if (i == 2) {
                pnlGame[i].setLocation(450, 0);
            } else if (i == 3) {
                pnlGame[i].setLocation(0, 225);
            } else if (i == 4) {
                pnlGame[i].setLocation(225, 225);
            } else if (i == 5) {
                pnlGame[i].setLocation(450, 225);
            } else if (i == 6) {
                pnlGame[i].setLocation(0, 450);
            } else if (i == 7) {
                pnlGame[i].setLocation(225, 450);
            } else if (i == 8) {
                pnlGame[i].setLocation(450, 450);
            }
            container.add(pnlGame[i]);
        }

        for (int z = 0; z < 9; z++) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    txtGame[z][x][y] = new JTextField();
                    txtGame[z][x][y].setBorder(BorderFactory.createLineBorder(Color.black, 1));
                    txtGame[z][x][y].setFont(new Font("Dialog", Font.ITALIC, 40));
                    txtGame[z][x][y].setHorizontalAlignment(JTextField.CENTER);
                    pnlGame[z].add(txtGame[z][x][y]);
                }
            }
        }

        for (int z = 0; z < 9; z++) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    txtGame[z][x][y].getDocument().addUndoableEditListener(undoManager);
                }
            }
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    //判断结果是否正确
    public void end() {
        JFrame frameJudge = new JFrame("Winning or Losing");
        frameJudge.setSize(600, 100);
        JPanel jpJudge = new JPanel();
        JLabel labelWin = new JLabel();
        JLabel labelLose = new JLabel();

        //输赢判断部分
        ArrayList<String> result = new ArrayList<>();
        newSudoku:
        for (int i = 1; i <= 1; i++) {
            int[][] sudoku = new int[9][9];
            int z = 0;
            for (int line = 0; line < 9; line += 3) {
                for (int column = 0; column < 9; column += 3) {
                    for (int squareLine = line; squareLine < line + 3; squareLine++) {
                        for (int squareCol = column; squareCol < column + 3; squareCol++) {
                            String str = txtGame[z][squareLine % 3][squareCol % 3].getText();
                            if (str.equals("")) {
                                sudoku[squareLine][squareCol] = 0;
                            } else {
                                sudoku[squareLine][squareCol] = Integer.parseInt(str);
                            }
                        }
                    }
                    z = z + 1;
                }
            }

            //判断是否含0，0代表没有填写
            for (int line = 0; line < 9; line++) {
                for (int column = 0; column < 9; column++) {
                    if (sudoku[line][column] == 0) {
                        result.add("Wrong");
                        continue newSudoku;
                    }
                }
            }

            //判断每一行是否符合标准
            for (int line = 0; line < 9; line++) {
                int[] arrLine = new int[9];
                for (int column = 0; column < 9; column++) {
                    if (arrLine[sudoku[line][column] - 1] > 0) {
                        result.add("Wrong");
                        continue newSudoku;
                    } else {
                        arrLine[sudoku[line][column] - 1] = 1;
                    }
                }
            }

            //判断每一列是否符合标准
            for (int column = 0; column < 9; column++) {
                int[] arrCol = new int[9];
                for (int line = 0; line < 9; line++) {
                    if (arrCol[sudoku[line][column] - 1] > 0) {
                        result.add("Wrong");
                        continue newSudoku;
                    } else {
                        arrCol[sudoku[line][column] - 1] = 1;
                    }
                }
            }

            //判断每一方块是否符合标准
            for (int line = 0; line < 9; line += 3) {
                for (int column = 0; column < 9; column += 3) {
                    int[] arrSquare = new int[9];
                    for (int squareLine = line; squareLine < line + 3; squareLine++) {
                        for (int squareCol = column; squareCol < column + 3; squareCol++) {
                            if (arrSquare[sudoku[squareLine][squareCol] - 1] > 0) {
                                result.add("Wrong");
                                continue newSudoku;
                            } else {
                                arrSquare[sudoku[squareLine][squareCol] - 1] = 1;
                            }
                        }
                    }
                }
            }
            result.add("Right");
        }

        for (String str : result) {
            if (str.equals("Right")) {
                jpJudge.add(labelWin);
                labelWin.setText("Congratulation!You Win!");
                labelWin.setFont(new Font("微软雅黑", Font.PLAIN, 40));
            }
            if (str.equals("Wrong")) {
                jpJudge.add(labelLose);
                labelLose.setText("Sorry~You Lose~");
                labelLose.setFont(new Font("微软雅黑", Font.PLAIN, 40));
            }
        }

        frameJudge.add(jpJudge);
        frameJudge.setVisible(true);
        frameJudge.setLocationRelativeTo(null);
    }

    public void storage() {
        File fileStorage = new File("Storage.txt");
        File fileEditable = new File("Editable.txt");

        //将数独输入二维数组sudokuStore[][]
        //将txtGame[z][x][y]转成sudokuStore[][]
        int[][] sudokuStore = new int[9][9];
        int z = 0;
        for (int line = 0; line < 9; line += 3) {
            for (int column = 0; column < 9; column += 3) {
                for (int squareLine = line; squareLine < line + 3; squareLine++) {
                    for (int squareCol = column; squareCol < column + 3; squareCol++) {
                        String str = txtGame[z][squareLine % 3][squareCol % 3].getText();
                        if (str.equals("")) {
                            sudokuStore[squareLine][squareCol] = 0;
                        }
                        else {
                            sudokuStore[squareLine][squareCol] = Integer.parseInt(str);
                        }
                    }
                }
                z = z + 1;
            }
        }

        //将二维数组sudokuStore[][]存入Storage.txt中保存之前的数独
        PrintWriter outputStorage = null;
        try {
            outputStorage = new PrintWriter(fileStorage);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                outputStorage.print(sudokuStore[i][j] + " ");
            }
            outputStorage.println();
        }
        outputStorage.close();

        //将原数独中数字是否可编辑存入Editable.txt中
        int[][] sudokuEditable = new int[9][9];
        z = 0;
        for (int line = 0; line < 9; line += 3) {
            for (int column = 0; column < 9; column += 3) {
                for (int squareLine = line; squareLine < line + 3; squareLine++) {
                    for (int squareCol = column; squareCol < column + 3; squareCol++) {
                        String str = txtGame[z][squareLine % 3][squareCol % 3].getText();
                        if (txtGame[z][squareLine % 3][squareCol % 3].isEditable() == true) {
                            sudokuEditable[squareLine][squareCol] = 1;
                        }
                        else {
                            sudokuEditable[squareLine][squareCol] = 0;
                        }
                    }
                }
                z = z + 1;
            }
        }

        //将二维数组sudokuStore[][]存入Storage.txt中保存之前的数独
        PrintWriter outputEditable = null;
        try {
            outputEditable = new PrintWriter(fileEditable);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                outputEditable.print(sudokuEditable[i][j] + " ");
            }
            outputEditable.println();
        }
        outputEditable.close();
    }

    public void read() {
        File fileStorage = new File("Storage.txt");
        File fileEditable = new File("Editable.txt");

        InputStreamReader inputStreamStorage = null;
        try {
            inputStreamStorage = new InputStreamReader(new FileInputStream(fileStorage));
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        BufferedReader bufferStorage = new BufferedReader(inputStreamStorage);

        InputStreamReader inputStreamEditable = null;
        try {
            inputStreamEditable = new InputStreamReader(new FileInputStream(fileEditable));
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        BufferedReader bufferEditable = new BufferedReader(inputStreamEditable);

        //创建二维数组sudokuLoad[][]以及sudokuEditable[][]
        int[][] sudokuLoad = new int[9][9];
        int[][] sudokuEditable = new int[9][9];

        //将Storage.txt当当中的二维数组读取到sudokuLoad[][]中
        for (int line = 0; line < 9; line++) {
            String[] intStr = new String[0];
            try {
                intStr = bufferStorage.readLine().split(" ");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            for (int column = 0; column < 9; column++) {
                sudokuLoad[line][column] = Integer.parseInt(intStr[column]);
            }
        }
        try {
            inputStreamStorage.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //将Editable.txt当当中的二维数组读取到sudokuEditable[][]中
        for (int line = 0; line < 9; line++) {
            String[] intStr = new String[0];
            try {
                intStr = bufferEditable.readLine().split(" ");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            for (int column = 0; column < 9; column++) {
                sudokuEditable[line][column] = Integer.parseInt(intStr[column]);
            }
        }
        try {
            inputStreamEditable.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //刷新数独
        int z = 0;
        for (int line = 0; line < 9; line += 3) {
            for (int column = 0; column < 9; column += 3) {
                for (int squareLine = line; squareLine < line + 3; squareLine++) {
                    for (int squareCol = column; squareCol < column + 3; squareCol++) {
                        txtGame[z][squareLine % 3][squareCol % 3].setText("");
                        txtGame[z][squareLine % 3][squareCol % 3].setEditable(true);
                    }
                }
                z = z + 1;
            }
        }

        //将sudokuLoad[][]赋值给txtGame[z][x][y]
        z = 0;
        for (int line = 0; line < 9; line += 3) {
            for (int column = 0; column < 9; column += 3) {
                for (int squareLine = line; squareLine < line + 3; squareLine++) {
                    for (int squareCol = column; squareCol < column + 3; squareCol++) {
                        if (sudokuLoad[squareLine][squareCol] != 0 ) {
                            txtGame[z][squareLine % 3][squareCol % 3].setText(String.valueOf(sudokuLoad[squareLine][squareCol]));
                        }
                        if(sudokuEditable[squareLine][squareCol] == 0){
                            txtGame[z][squareLine % 3][squareCol % 3].setEditable(false);
                        }
                        if(sudokuLoad[squareLine][squareCol] == 1){
                            txtGame[z][squareLine % 3][squareCol % 3].setEditable(true);
                        }
                    }
                }
                z = z + 1;
            }
        }
    }

    public void reset() {
        for (int z = 0; z < 9; z++) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    txtGame[z][x][y].setText("");
                    txtGame[z][x][y].setEditable(true);
                }
            }
        }
    }

    public void setBoard(int[][] sudoku) {
        int z = 0;
        for (int line = 0; line < 9; line += 3) {
            for (int column = 0; column < 9; column += 3) {
                for (int squareLine = line; squareLine < line + 3; squareLine++) {
                    for (int squareCol = column; squareCol < column + 3; squareCol++) {
                        if(sudoku[squareLine][squareCol]!=0) {
                            txtGame[z][squareLine % 3][squareCol % 3].setText(String.valueOf(sudoku[squareLine][squareCol]));
                            txtGame[z][squareLine % 3][squareCol % 3].setEditable(false);
                        }
                    }
                }
                z = z + 1;
            }
        }
    }

    public int[][] easyGame() {
        // TODO Auto-generated method stub
        int a = (int) (Math.random() * 10);
        switch (a) {
            case 0:
                int easy1[][] = {
                        {0, 6, 1, 0, 3, 0, 0, 2, 0},
                        {0, 5, 0, 0, 0, 8, 1, 0, 7},
                        {0, 0, 0, 0, 0, 7, 0, 3, 4},
                        {0, 0, 9, 0, 0, 6, 3, 7, 8},
                        {0, 0, 3, 2, 7, 9, 5, 0, 0},
                        {5, 7, 0, 3, 0, 0, 9, 0, 2},
                        {1, 9, 0, 7, 6, 0, 0, 0, 0},
                        {8, 0, 2, 4, 0, 0, 7, 6, 0},
                        {6, 4, 0, 0, 1, 0, 2, 5, 0},
                };
                return easy1;

            case 1:
                int easy2[][] = {
                        {1, 0, 0, 8, 3, 0, 0, 0, 2},
                        {5, 7, 0, 0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 5, 0, 9, 0, 6, 4},
                        {7, 0, 4, 0, 0, 8, 5, 9, 0},
                        {0, 0, 3, 0, 1, 0, 4, 0, 0},
                        {0, 5, 1, 4, 0, 0, 3, 0, 6},
                        {3, 6, 0, 7, 0, 4, 0, 0, 0},
                        {0, 0, 0, 6, 0, 0, 0, 7, 9},
                        {8, 0, 0, 0, 5, 2, 0, 0, 3},
                };
                return easy2;

            case 2:
                int easy3[][] = {
                        {0, 3, 0, 0, 0, 7, 0, 0, 4},
                        {6, 0, 2, 0, 4, 1, 0, 0, 0},
                        {0, 5, 0, 0, 3, 0, 9, 6, 7},
                        {0, 4, 0, 0, 0, 3, 0, 0, 6},
                        {0, 8, 7, 0, 0, 0, 3, 5, 0},
                        {9, 0, 0, 7, 0, 0, 0, 2, 0},
                        {7, 1, 8, 0, 2, 0, 0, 4, 0},
                        {0, 0, 0, 1, 6, 0, 8, 0, 9},
                        {4, 0, 0, 5, 0, 0, 0, 3, 0},
                };
                return easy3;

            case 3:
                int easy4[][] = {
                        {0, 8, 5, 0, 0, 0, 2, 1, 0},
                        {0, 9, 4, 0, 1, 2, 0, 0, 3},
                        {0, 0, 0, 3, 0, 0, 7, 0, 4},
                        {5, 0, 3, 4, 0, 9, 0, 0, 0},
                        {0, 4, 0, 2, 0, 6, 0, 3, 0},
                        {0, 0, 0, 1, 0, 3, 9, 0, 7},
                        {6, 0, 8, 0, 0, 5, 0, 0, 0},
                        {1, 0, 0, 8, 4, 0, 3, 6, 0},
                        {0, 2, 7, 0, 0, 0, 8, 9, 0},
                };
                return easy4;

            case 4:
                int easy5[][] = {
                        {0, 8, 0, 0, 0, 1, 6, 0, 0},
                        {0, 7, 0, 4, 0, 0, 0, 2, 1},
                        {5, 0, 0, 3, 9, 6, 0, 0, 0},
                        {2, 0, 4, 0, 5, 0, 1, 3, 0},
                        {0, 0, 8, 9, 0, 7, 5, 0, 0},
                        {0, 5, 7, 0, 3, 0, 9, 0, 0},
                        {0, 0, 0, 5, 6, 3, 0, 0, 9},
                        {3, 1, 0, 0, 0, 2, 0, 5, 0},
                        {0, 0, 5, 8, 0, 0, 0, 4, 0},
                };
                return easy5;

            case 5:
                int easy6[][] = {
                        {0, 0, 1, 0, 0, 0, 5, 0, 0},
                        {0, 8, 0, 0, 0, 6, 2, 9, 0},
                        {6, 3, 0, 2, 0, 0, 0, 0, 4},
                        {0, 5, 0, 8, 0, 9, 7, 0, 0},
                        {0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 9, 5, 0, 7, 0, 3, 0},
                        {5, 0, 0, 0, 0, 1, 0, 6, 9},
                        {0, 9, 3, 7, 0, 0, 0, 1, 0},
                        {0, 0, 2, 0, 0, 0, 3, 0, 0},
                };
                return easy6;

            case 6:
                int easy7[][] = {
                        {9, 1, 0, 0, 0, 0, 0, 3, 7},
                        {0, 0, 2, 0, 0, 0, 6, 0, 0},
                        {8, 0, 0, 6, 0, 9, 0, 0, 5},
                        {0, 9, 0, 3, 0, 2, 0, 5, 0},
                        {0, 0, 4, 0, 8, 0, 7, 0, 0},
                        {0, 6, 0, 7, 0, 1, 0, 8, 0},
                        {6, 0, 0, 2, 0, 8, 0, 0, 4},
                        {0, 0, 1, 0, 0, 0, 3, 0, 0},
                        {2, 5, 0, 0, 0, 0, 0, 1, 9},
                };
                return easy7;

            case 7:
                int easy8[][] = {
                        {8, 0, 3, 0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 4, 0, 0},
                        {1, 0, 0, 0, 2, 8, 0, 0, 3},
                        {9, 0, 4, 0, 6, 0, 0, 1, 7},
                        {0, 0, 0, 4, 0, 5, 0, 0, 0},
                        {2, 1, 0, 0, 8, 0, 3, 0, 6},
                        {3, 0, 0, 2, 7, 0, 0, 0, 5},
                        {0, 0, 9, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 8, 0, 0, 6, 0, 2},
                };
                return easy8;

            case 8:
                int easy9[][] = {
                        {0, 0, 8, 0, 0, 0, 6, 0, 0},
                        {0, 5, 0, 0, 4, 0, 0, 8, 0},
                        {7, 9, 0, 6, 0, 8, 0, 4, 5},
                        {4, 0, 0, 0, 5, 0, 0, 0, 6},
                        {0, 0, 0, 2, 0, 1, 0, 0, 0},
                        {2, 0, 0, 0, 7, 0, 0, 0, 3},
                        {9, 1, 0, 5, 0, 7, 0, 3, 8},
                        {0, 3, 0, 0, 6, 0, 0, 2, 0},
                        {0, 0, 4, 0, 0, 0, 7, 0, 0},
                };
                return easy9;

            case 9:
                int easy10[][] = {
                        {0, 0, 0, 0, 0, 7, 0, 0, 4},
                        {8, 0, 0, 0, 0, 0, 0, 6, 0},
                        {5, 4, 0, 9, 2, 0, 0, 0, 1},
                        {4, 5, 9, 0, 1, 0, 0, 0, 3},
                        {2, 0, 0, 0, 0, 0, 0, 0, 9},
                        {1, 0, 0, 0, 3, 0, 4, 8, 2},
                        {7, 0, 0, 0, 5, 8, 0, 9, 6},
                        {0, 1, 0, 0, 0, 0, 0, 0, 5},
                        {6, 0, 0, 7, 0, 0, 0, 0, 0},
                };
                return easy10;
        }
        return null;
    }

    public int[][] midGame() {
        // TODO Auto-generated method stub
        int a = (int) (Math.random() * 10);
        switch (a) {
            case 0:
                int mid1[][] = {
                        {0, 0, 0, 1, 0, 0, 2, 6, 0},
                        {7, 0, 0, 0, 3, 0, 0, 0, 0},
                        {3, 0, 2, 0, 8, 0, 4, 0, 0},
                        {0, 0, 0, 4, 0, 8, 0, 0, 1},
                        {0, 3, 5, 0, 0, 0, 9, 4, 0},
                        {2, 0, 0, 3, 0, 5, 0, 0, 0},
                        {0, 0, 6, 0, 5, 0, 7, 0, 9},
                        {0, 0, 0, 0, 4, 0, 0, 0, 8},
                        {0, 5, 7, 0, 0, 9, 0, 0, 0},
                };
                return mid1;

            case 1:
                int mid2[][] = {
                        {0, 0, 8, 0, 9, 0, 0, 0, 0},
                        {0, 7, 0, 0, 0, 0, 2, 8, 0},
                        {0, 6, 4, 1, 0, 0, 3, 0, 9},
                        {0, 0, 0, 8, 0, 5, 9, 0, 0},
                        {5, 0, 0, 0, 0, 0, 0, 0, 1},
                        {0, 0, 9, 3, 0, 4, 0, 0, 0},
                        {8, 0, 2, 0, 0, 7, 5, 6, 0},
                        {0, 9, 7, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 6, 0, 0, 7, 0},
                };
                return mid2;

            case 2:
                int mid3[][] = {
                        {0, 0, 0, 7, 0, 2, 0, 0, 0},
                        {1, 0, 0, 0, 4, 0, 0, 0, 7},
                        {6, 5, 0, 0, 0, 0, 0, 9, 4},
                        {4, 7, 0, 8, 0, 1, 0, 6, 2},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {5, 8, 0, 2, 0, 9, 0, 1, 3},
                        {8, 6, 0, 0, 0, 0, 0, 7, 5},
                        {9, 0, 0, 0, 6, 0, 0, 0, 8},
                        {0, 0, 0, 9, 0, 8, 0, 0, 0},
                };
                return mid3;

            case 3:
                int mid4[][] = {
                        {0, 0, 7, 2, 3, 8, 0, 0, 0},
                        {0, 6, 0, 7, 0, 0, 0, 5, 0},
                        {0, 0, 4, 0, 0, 0, 0, 0, 2},
                        {9, 0, 0, 0, 0, 0, 8, 6, 7},
                        {1, 0, 0, 0, 0, 0, 0, 0, 3},
                        {6, 4, 8, 0, 0, 0, 0, 0, 5},
                        {7, 0, 0, 0, 0, 3, 0, 0, 0},
                        {0, 2, 0, 0, 0, 5, 0, 3, 0},
                        {0, 0, 0, 1, 7, 4, 9, 0, 0},
                };
                return mid4;

            case 4:
                int mid5[][] = {
                        {5, 0, 7, 0, 0, 0, 0, 0, 9},
                        {0, 8, 0, 0, 0, 2, 1, 7, 0},
                        {0, 1, 0, 0, 6, 0, 0, 0, 4},
                        {0, 9, 0, 0, 3, 0, 0, 0, 0},
                        {0, 0, 1, 7, 0, 9, 3, 0, 0},
                        {0, 0, 0, 0, 4, 0, 0, 6, 0},
                        {8, 0, 0, 0, 5, 0, 0, 2, 0},
                        {0, 7, 6, 2, 0, 0, 0, 9, 0},
                        {4, 0, 0, 0, 0, 0, 6, 0, 8},
                };
                return mid5;

            case 5:
                int mid6[][] = {
                        {0, 0, 9, 7, 0, 0, 0, 0, 0},
                        {5, 0, 0, 0, 0, 2, 7, 0, 9},
                        {8, 0, 0, 0, 1, 0, 0, 0, 6},
                        {0, 0, 1, 6, 0, 0, 4, 0, 5},
                        {0, 0, 0, 0, 4, 0, 0, 0, 0},
                        {7, 0, 6, 0, 0, 8, 2, 0, 0},
                        {4, 0, 0, 0, 9, 0, 0, 0, 8},
                        {6, 0, 2, 3, 0, 0, 0, 0, 4},
                        {0, 0, 0, 0, 0, 7, 9, 0, 0},
                };
                return mid6;

            case 6:
                int mid7[][] = {
                        {0, 0, 9, 0, 0, 0, 0, 6, 4},
                        {4, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 0, 0, 3, 6, 0, 0, 7, 2},
                        {0, 0, 4, 6, 0, 0, 0, 0, 9},
                        {0, 0, 0, 9, 0, 3, 0, 0, 0},
                        {2, 0, 0, 0, 0, 5, 4, 0, 0},
                        {9, 2, 0, 0, 5, 7, 0, 0, 8},
                        {0, 0, 0, 0, 0, 0, 0, 0, 5},
                        {3, 4, 0, 0, 0, 0, 6, 0, 0},
                };
                return mid7;

            case 7:
                int mid8[][] = {
                        {0, 3, 0, 0, 0, 8, 0, 0, 5},
                        {0, 0, 5, 0, 0, 0, 8, 0, 7},
                        {0, 0, 0, 4, 0, 0, 9, 0, 0},
                        {0, 0, 0, 3, 9, 0, 4, 0, 0},
                        {0, 5, 9, 0, 7, 0, 2, 1, 0},
                        {0, 0, 2, 0, 6, 5, 0, 0, 0},
                        {0, 0, 7, 0, 5, 0, 0, 0, 0},
                        {5, 0, 1, 0, 0, 0, 7, 0, 0},
                        {6, 0, 0, 9, 0, 0, 0, 2, 0},
                };
                return mid8;

            case 8:
                int mid9[][] = {
                        {3, 0, 2, 7, 0, 0, 0, 0, 9},
                        {0, 0, 8, 0, 0, 0, 0, 4, 5},
                        {0, 0, 4, 0, 0, 1, 3, 0, 0},
                        {0, 0, 0, 0, 5, 9, 0, 0, 0},
                        {0, 9, 0, 0, 3, 0, 0, 6, 0},
                        {0, 0, 0, 2, 6, 0, 0, 0, 0},
                        {0, 0, 1, 4, 0, 0, 2, 0, 0},
                        {2, 6, 0, 0, 0, 0, 1, 0, 0},
                        {4, 0, 0, 0, 0, 2, 5, 0, 3},
                };
                return mid9;
                case 9:
                    int mid10[][] = {
                        {0, 9, 5, 0, 0, 8, 0, 0, 0},
                        {0, 0, 2, 0, 0, 6, 7, 0, 0},
                        {0, 4, 0, 0, 0, 0, 0, 0, 5},
                        {0, 5, 0, 0, 2, 0, 0, 0, 7},
                        {0, 6, 0, 0, 5, 0, 0, 2, 0},
                        {4, 0, 0, 0, 7, 0, 0, 8, 0},
                        {2, 0, 0, 0, 0, 0, 0, 4, 0},
                        {0, 0, 6, 1, 0, 0, 3, 0, 0},
                        {0, 0, 0, 3, 0, 0, 2, 5, 0},
                    };
                return mid10;
        }
        return null;
    }

    public int[][] hardGame() {
        // TODO Auto-generated method stub
        int a = (int) (Math.random() * 10);
        switch (a) {
            case 0:
                int hard1[][] = {
                        {0, 1, 0, 0, 0, 8, 4, 0, 7},
                        {9, 5, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 8, 0, 1, 0, 0, 0, 0},
                        {0, 8, 2, 0, 0, 0, 0, 0, 0},
                        {7, 0, 0, 4, 0, 6, 0, 0, 8},
                        {0, 0, 0, 0, 0, 0, 6, 2, 0},
                        {0, 0, 0, 0, 5, 0, 7, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 8, 2},
                        {5, 0, 3, 2, 0, 0, 0, 1, 0},
                };
                return hard1;

            case 1:
                int hard2[][] = {
                        {7, 5, 0, 9, 0, 0, 0, 4, 6},
                        {9, 0, 1, 0, 0, 0, 3, 0, 2},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {2, 0, 0, 6, 0, 1, 0, 0, 7},
                        {0, 8, 0, 0, 0, 0, 0, 2, 0},
                        {1, 0, 0, 3, 0, 8, 0, 0, 5},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {3, 0, 9, 0, 0, 0, 2, 0, 4},
                        {8, 4, 0, 0, 3, 0, 0, 7, 9},
                };
                return hard2;

            case 2:
                int hard3[][] = {
                        {0, 0, 0, 8, 9, 0, 0, 2, 0},
                        {0, 0, 9, 0, 0, 5, 0, 0, 7},
                        {0, 5, 0, 0, 0, 0, 3, 0, 0},
                        {0, 9, 3, 5, 0, 0, 1, 0, 0},
                        {0, 0, 0, 1, 0, 7, 0, 0, 0},
                        {0, 0, 1, 0, 0, 6, 8, 4, 0},
                        {0, 0, 8, 0, 0, 0, 0, 6, 0},
                        {9, 0, 0, 6, 0, 0, 4, 0, 0},
                        {0, 1, 0, 0, 2, 8, 0, 0, 0},
                };
                return hard3;

            case 3:
                int hard4[][] = {
                        {0, 8, 0, 7, 9, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 2, 0, 9, 0},
                        {0, 0, 3, 0, 0, 8, 4, 5, 0},
                        {0, 0, 8, 0, 0, 0, 0, 0, 1},
                        {0, 9, 6, 0, 0, 0, 3, 7, 0},
                        {3, 0, 0, 0, 0, 0, 2, 0, 0},
                        {0, 3, 2, 5, 0, 0, 9, 0, 0},
                        {0, 4, 0, 8, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 6, 4, 0, 2, 0},
                };
                return hard4;

            case 4:
                int hard5[][] = {
                        {0, 0, 0, 4, 0, 0, 0, 0, 2},
                        {0, 0, 4, 0, 1, 2, 0, 0, 9},
                        {0, 7, 0, 0, 0, 8, 0, 0, 0},
                        {0, 2, 0, 0, 9, 0, 1, 7, 0},
                        {0, 0, 0, 0, 8, 0, 0, 0, 0},
                        {0, 6, 1, 0, 5, 0, 0, 4, 0},
                        {0, 0, 0, 9, 0, 0, 0, 5, 0},
                        {6, 0, 0, 1, 2, 0, 3, 0, 0},
                        {1, 0, 0, 0, 0, 3, 0, 0, 0},
                };
                return hard5;

            case 5:
                int hard6[][] = {
                        {1, 0, 0, 0, 3, 4, 0, 0, 9},
                        {7, 4, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 8, 0, 2, 0, 0},
                        {0, 9, 0, 7, 2, 0, 1, 5, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 7, 0, 9, 3, 0, 2, 0},
                        {0, 0, 3, 0, 5, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 9, 6},
                        {6, 0, 0, 9, 7, 0, 0, 0, 5},
                };
                return hard6;

            case 6:
                int hard7[][] = {
                        {0, 6, 0, 0, 0, 0, 0, 2, 7},
                        {0, 0, 0, 5, 1, 0, 0, 0, 0},
                        {7, 0, 0, 8, 0, 0, 0, 0, 9},
                        {5, 4, 0, 0, 7, 0, 0, 0, 0},
                        {0, 0, 0, 4, 0, 8, 0, 0, 0},
                        {0, 0, 0, 0, 3, 0, 0, 8, 2},
                        {3, 0, 0, 0, 0, 2, 0, 0, 1},
                        {0, 0, 0, 0, 6, 3, 0, 0, 0},
                        {6, 9, 0, 0, 0, 0, 0, 3, 0},
                };
                return hard7;

            case 7:
                int hard8[][] = {
                        {0, 0, 0, 3, 4, 0, 0, 0, 0},
                        {2, 0, 0, 0, 0, 0, 4, 0, 7},
                        {0, 7, 0, 0, 0, 8, 0, 0, 5},
                        {0, 0, 3, 0, 0, 1, 0, 0, 2},
                        {0, 0, 9, 0, 6, 0, 8, 0, 0},
                        {7, 0, 0, 2, 0, 0, 3, 0, 0},
                        {5, 0, 0, 6, 0, 0, 0, 1, 0},
                        {1, 0, 2, 0, 0, 0, 0, 0, 9},
                        {0, 0, 0, 0, 1, 4, 0, 0, 0},
                };
                return hard8;

            case 8:
                int hard9[][] = {
                        {0, 8, 0, 0, 0, 0, 0, 2, 0},
                        {0, 0, 1, 0, 0, 0, 6, 0, 0},
                        {2, 0, 0, 0, 5, 0, 0, 0, 3},
                        {0, 0, 6, 5, 0, 0, 1, 2, 0},
                        {7, 0, 0, 6, 0, 0, 4, 0, 9},
                        {0, 0, 4, 7, 0, 0, 9, 3, 0},
                        {6, 0, 0, 0, 1, 0, 0, 0, 5},
                        {0, 0, 7, 0, 0, 0, 9, 0, 0},
                        {0, 4, 0, 0, 0, 0, 0, 3, 0},
                };
                return hard9;

            case 9:
                int hard10[][] = {
                        {0, 1, 9, 2, 0, 0, 5, 0, 0},
                        {7, 0, 0, 0, 8, 0, 3, 0, 0},
                        {0, 4, 0, 5, 0, 0, 0, 0, 0},
                        {3, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 2, 0, 1, 0, 7, 0, 8, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {0, 0, 0, 0, 0, 4, 0, 5, 0},
                        {0, 0, 5, 0, 1, 0, 0, 0, 6},
                        {0, 0, 2, 0, 0, 6, 7, 9, 0},
                };
                return hard10;
        }
        return null;
    }
}



