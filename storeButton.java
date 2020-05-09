package Project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class storeButton {
    public static void main(String[] args) {

        //创建一个storeButton界面
        JFrame frame = new JFrame("storeButton");
        frame.setSize(400, 200);

        //创建一个保存按键
        JPanel jpStore = new JPanel();
        JButton storeButton = new JButton("Store Button 保存");
        storeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("Storage.txt");
                int[][] sudokuStore = new int[9][9];

                //将数独输入二维数组sudokuStore[][]
                //Todo:可以使用之前的txtGame[z][x][y]转二维数组sudoku[][]的方法,将txtGame[z][x][y]转成sudokuStore[][]
                //这里先用随机数代替
                for (int line = 0; line < 9; line++) {
                    for (int column = 0; column < 9; column++) {
                        sudokuStore[line][column] = (int) (Math.random()*10);
                    }
                }

                //将二维数组sudokuStore[][]存入Storage.txt中保存之前的数独
                PrintWriter output = null;
                try {
                    output = new PrintWriter(file);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                for(int i = 0; i < 9; i++){
                    for(int j = 0; j < 9; j++){
                        output.print(sudokuStore[i][j]+" ");
                    }
                    output.println();
                }
                output.close();

                //输出二维数组sudokuStore[][]
                //这里是作为Storage.txt文件对应的检测
                //Todo:此处可以去掉
                for (int line = 0; line < 9; line++) {
                    for (int column = 0; column < 9; column++) {
                        System.out.print(sudokuStore[line][column] + " ");
                    }
                    System.out.println();
                }
                System.out.println();

            }
        });

        jpStore.add(storeButton);
        frame.add(jpStore);
        frame.setBounds(300, 200, 600, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
