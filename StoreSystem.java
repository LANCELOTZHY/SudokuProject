package Project;

import java.io.*;

public class StoreSystem {
    public static void main(String[] args) throws IOException {

        File file = new File("Storage.txt");
        int[][] sudoku = new int[9][9];

        //将数独输入二维数组
        for (int line = 0; line < 9; line++) {
            for (int column = 0; column < 9; column++) {
                sudoku[line][column] = (int) (Math.random()*10);
            }
        }

        //输出二维数组
        for (int line = 0; line < 9; line++) {
            for (int column = 0; column < 9; column++) {
                System.out.print(sudoku[line][column] + " ");
            }
            System.out.println();
        }

        //将数独存入Storage中保存
        PrintWriter output = new PrintWriter(file);
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                output.print(sudoku[i][j]+" ");
            }
            output.println();
        }
        output.close();

    }
}
