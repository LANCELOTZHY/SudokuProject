package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class stopButton {
    public static void main(String[] args) {

        //创建一个界面
        JFrame frame = new JFrame("stopButton");
        frame.setSize(400, 200);
        JPanel jp = new JPanel();

        //创建停止按键
        JButton stopButton = new JButton("Stop Button");

        //停止按键所执行的方法
        //进行输赢判断
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //创建输赢判断界面
                JFrame frameJudge = new JFrame("Winning or Losing");
                frameJudge.setSize(400, 200);
                JPanel jpJudge = new JPanel();
                JLabel labelWin = new JLabel();
                JLabel labelLose = new JLabel();

                //输赢判断部分
                InputStreamReader inputStream = new InputStreamReader(System.in);
                BufferedReader buffer = new BufferedReader(inputStream);
                ArrayList<String> result = new ArrayList<>();



                newSudoku: for (int i = 1; i <= 1; i++) {
                    int[][] sudoku = new int[9][9];

                    //将数独输入二维数组
                    for (int line = 0; line < 9; line++) {
                        String[] intStr = new String[0];
                        try {
                            intStr = buffer.readLine().split(" ");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        for (int column = 0; column < 9; column++) {
                            sudoku[line][column] = Integer.parseInt(intStr[column]);
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

                for (String str : result){
                    if(str.equals("Right")){
                        jpJudge.add(labelWin);
                        labelWin.setText("You Win");
                        labelWin.setFont(new Font("微软雅黑",0,40));
                    }
                    if(str.equals("Wrong")){
                        jpJudge.add(labelLose);
                        labelLose.setText("You Lose");
                        labelLose.setFont(new Font("微软雅黑",0,40));
                    }
                }
                frameJudge.add(jpJudge);
                frameJudge.setVisible(true);
                frameJudge.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

        jp.add(stopButton);
        frame.add(jp);
        frame.setBounds(300, 200, 600, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
