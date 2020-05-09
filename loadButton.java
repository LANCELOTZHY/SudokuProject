package Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class loadButton {
    public static void main(String[] args) {

        //创建一个loadButton界面
        JFrame frame = new JFrame("loadButton");
        frame.setSize(400, 200);

        //创建一个加载存档按键
        JPanel jpLoad = new JPanel();
        JButton loadButton = new JButton("Load Button 加载存档");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File file = new File ("Storage.txt");
                InputStreamReader inputStream = null;
                try {
                    inputStream = new InputStreamReader(new FileInputStream(file));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                BufferedReader buffer = new BufferedReader(inputStream);

                //创建二维数组sudokuLoad[][]
                int[][] sudokuLoad = new int[9][9];

                //将Storage.txt当当中的二维数组读取到sudokuLoad[][]中
                for (int line = 0; line < 9; line++) {
                    String[] intStr = new String[0];
                    try {
                        intStr = buffer.readLine().split(" ");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    for (int column = 0; column < 9; column++) {
                        sudokuLoad[line][column] = Integer.parseInt(intStr[column]);
                    }
                }
                try {
                    inputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                //输出二维数组sudokuLoad[][]
                //这里是作为Storage.txt文件对应的检测
                //Todo:此处可以去掉
                for (int line = 0; line < 9; line++) {
                    for (int column = 0; column < 9; column++) {
                        System.out.print(sudokuLoad[line][column] + " ");
                    }
                    System.out.println();
                }
                System.out.println();

                //Todo:需要将sudokuLoad[][]赋值给txtGame[z][x][y]，以下是将sudoku赋值给txtGame，可以直接用
//                int z = 0;
//                for (int line = 0; line < 9; line += 3) {
//                    for (int column = 0; column < 9; column += 3) {
//                        for (int squareLine = line; squareLine < line + 3; squareLine++) {
//                            for (int squareCol = column; squareCol < column + 3; squareCol++) {
//                                txtGame[z][squareLine%3][squareCol%3].setText(String.valueOf(sudoku[squareLine][squareCol]+1));
//                            }
//                        }
//                        z = z + 1;
//                    }
//                }

            }
        });

        jpLoad.add(loadButton);
        frame.add(jpLoad);
        frame.setBounds(300, 200, 600, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
