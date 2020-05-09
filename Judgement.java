package Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Judgement {
    public static void main(String[] args) throws IOException {

        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(inputStream);
        ArrayList<String> result = new ArrayList<>();

        newSudoku:
        for (int i = 1; i <= 1; i++) {
            int[][] sudoku = new int[9][9];

            //将数独输入二维数组
            for (int line = 0; line < 9; line++) {
                String[] intStr = buffer.readLine().split(" ");
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

        //输出输赢结果
        for (String str : result) {
            if(str.equals("Right")){
            System.out.println("You Win");
            }
            if(str.equals("Wrong")){
                System.out.println("You Lose");
            }
        }

    }
}
