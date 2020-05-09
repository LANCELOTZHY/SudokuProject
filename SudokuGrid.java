package Project;

import javax.swing.*;
import java.awt.*;

public class SudokuGrid extends JFrame {
    JLabel jllNewGame;
    JLabel jllHintNum;//剩余提示次数
    JButton jbnNum1;
    JButton jbnNum2;
    JButton jbnNum3;
    JButton jbnNum4;
    JButton jbnNum5;
    JButton jbnNum6;
    JButton jbnNum7;
    JButton jbnNum8;
    JButton jbnNum9;
    JButton jbnEasy;
    JButton jbnMid;
    JButton jbnDit;
    JButton jbnBackout;
    JButton jbnHint;
    JButton jbnWipe;
    JButton jbnNote;
    JPanel[] pnlGame;
    JTextField[][][] txtGame;

    public SudokuGrid() {
        pnlGame = new JPanel[9];
        txtGame = new JTextField[9][3][3];
        gridInit();
    }

    public void gridInit(){
        Container container = this.getContentPane();
        container.setLayout(null);
        this.setSize(1600,1000);
        this.setLocationRelativeTo(null);
        this.setTitle("快乐数独");

        jllNewGame=new JLabel();
        jllNewGame.setIcon(new ImageIcon("shudu.png"));
        jllNewGame.setSize(375,100);
        jllNewGame.setLocation(700,0);
        container.add(jllNewGame);

        jbnNum1 = new JButton("1");
        jbnNum1.setBackground(Color.lightGray);
        jbnNum1.setSize(125,100);
        jbnNum1.setLocation(700,250);
        jbnNum1.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnNum1);

        jbnNum2 = new JButton("2");
        jbnNum2.setBackground(Color.lightGray);
        jbnNum2.setSize(125,100);
        jbnNum2.setLocation(825,250);
        jbnNum2.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnNum2);

        jbnNum3 = new JButton("3");
        jbnNum3.setBackground(Color.lightGray);
        jbnNum3.setSize(125,100);
        jbnNum3.setLocation(950,250);
        jbnNum3.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnNum3);

        jbnNum4 = new JButton("4");
        jbnNum4.setBackground(Color.lightGray);
        jbnNum4.setSize(125,100);
        jbnNum4.setLocation(700,350);
        jbnNum4.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnNum4);

        jbnNum5 = new JButton("5");
        jbnNum5.setBackground(Color.lightGray);
        jbnNum5.setSize(125,100);
        jbnNum5.setLocation(825,350);
        jbnNum5.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnNum5);

        jbnNum6 = new JButton("6");
        jbnNum6.setBackground(Color.lightGray);
        jbnNum6.setSize(125,100);
        jbnNum6.setLocation(950,350);
        jbnNum6.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnNum6);

        jbnNum7 = new JButton("7");
        jbnNum7.setBackground(Color.lightGray);
        jbnNum7.setSize(125,100);
        jbnNum7.setLocation(700,450);
        jbnNum7.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnNum7);

        jbnNum8 = new JButton("8");
        jbnNum8.setBackground(Color.lightGray);
        jbnNum8.setSize(125,100);
        jbnNum8.setLocation(825,450);
        jbnNum8.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnNum8);

        jbnNum9 = new JButton("9");
        jbnNum9.setBackground(Color.lightGray);
        jbnNum9.setSize(125,100);
        jbnNum9.setLocation(950,450);
        jbnNum9.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnNum9);

        jbnEasy = new JButton("简单");
        jbnEasy.setBackground(Color.green);
        jbnEasy.setSize(125,100);
        jbnEasy.setLocation(700,100);
        jbnEasy.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnEasy);

        jbnMid = new JButton("中等");
        jbnMid.setBackground(Color.yellow);
        jbnMid.setSize(125,100);
        jbnMid.setLocation(825,100);
        jbnMid.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnMid);

        jbnDit = new JButton("困难");
        jbnDit.setBackground(Color.red);
        jbnDit.setSize(125,100);
        jbnDit.setLocation(950,100);
        jbnDit.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnDit);

        jbnBackout = new JButton("撤销");
        jbnBackout.setBackground(Color.ORANGE);
        jbnBackout.setSize(150,100);
        jbnBackout.setLocation(735,550);
        jbnBackout.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnBackout);

        jbnHint = new JButton("提示");
        jbnHint.setBackground(Color.ORANGE);
        jbnHint.setSize(150,100);
        jbnHint.setLocation(735,650);
        jbnHint.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnHint);

        jbnWipe = new JButton("擦除");
        jbnWipe.setBackground(Color.ORANGE);
        jbnWipe.setSize(150,100);
        jbnWipe.setLocation(885,550);
        jbnWipe.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnWipe);

        jbnNote = new JButton("笔记");
        jbnNote.setBackground(Color.ORANGE);
        jbnNote.setSize(150,100);
        jbnNote.setLocation(885,650);
        jbnNote.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jbnNote);

        jllHintNum= new JLabel("3");//剩余提示次数
        jllHintNum.setLocation(700,600);
        jllHintNum.setSize(30,40);
        jllHintNum.setFont(new Font("微软雅黑",Font.PLAIN,30));
        container.add(jllHintNum);

        for( int i=0;i<9;i++ ){
            pnlGame[i]=new JPanel();
            pnlGame[i].setBorder(BorderFactory.createLineBorder(Color.black,2));
            pnlGame[i].setLayout(new GridLayout(3,3));
            pnlGame[i].setSize(225,225);
            if( i==0 ){
                pnlGame[i].setLocation(0,50);
            }else if( i==1 ){
                pnlGame[i].setLocation(225,50);
            }else if( i==2 ){
                pnlGame[i].setLocation(450,50);
            }else if( i==3 ){
                pnlGame[i].setLocation(0,275);
            }else if( i==4 ){
                pnlGame[i].setLocation(225,275);
            }else if( i==5 ){
                pnlGame[i].setLocation(450,275);
            }else if( i==6 ){
                pnlGame[i].setLocation(0,500);
            }else if( i==7 ){
                pnlGame[i].setLocation(225,500);
            }else if( i==8 ){
                pnlGame[i].setLocation(450,500);
            }
            container.add(pnlGame[i]);
        }

        for(int z=0;z<9;z++){
            for(int x=0;x<3;x++){
                for(int y=0;y<3;y++){
                    txtGame[z][x][y]=new JTextField();
                    txtGame[z][x][y].setBorder(BorderFactory.createLineBorder(Color.black,1));
                    txtGame[z][x][y].setFont(new Font("Dialog", Font.ITALIC, 20));
                    txtGame[z][x][y].setHorizontalAlignment(JTextField.CENTER);
                    pnlGame[z].add(txtGame[z][x][y]);
                }
            }
        }
        for(int z=0;z<9;z++){
            for(int x=0;x<3;x++){
                for(int y=0;y<3;y++) {
                    txtGame[z][x][y].setEditable(false);
                }
            }
        }



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

}

