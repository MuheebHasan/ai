/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.puzzel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author hp
 */

public class Page2 extends javax.swing.JFrame {

      public static int N =0;
    private int[] queens;
   private JLabel[] boardCells;
   
   public Page2() {
        initComponents();
    }

    public static void randomArray(int[][] ar, int[] s) {
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            s[i] = rand.nextInt(N);
            ar[s[i]][i] = 1;
        }
    }
    public static void printArray(int[][] ar) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(ar[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void printState(int[] s) {
        for (int i = 0; i < N; i++) {
            System.out.println(s[i]);
        }
    }
   
    public static void zeroArray(int[][] board, int value) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = value;
            }
        }
    }
    public static boolean compareStates(int[] state1, int[] state2) {
        for (int i = 0; i < N; i++) {
            if (state1[i] != state2[i]) {
                return false;
            }
        }
        return true;
    }
    public static void generateBoard(int[][] board, int[] state) {
        zeroArray(board, 0);
        for (int i = 0; i < N; i++) {
            board[state[i]][i] = 1;
        }
    }
    public static void copyState(int[] state1, int[] state2) {
        for (int i = 0; i < N; i++) {
            state1[i] = state2[i];
        }
    }
    public static int calc_h(int[][] ar, int[] s) {
        int attacking = 0;
        int row, col;

        for (int i = 0; i < N; i++) {
            if (ar[s[i]][i] == 1) {
                row = s[i];
                col = i - 1;
                while (col >= 0) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col--;
                }

                row = s[i];
                col = i + 1;
                while (col < N) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col++;
                }

                row = s[i] - 1;
                col = i - 1;
                while (col >= 0 && row >= 0) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col--;
                    row--;
                }

                row = s[i] + 1;
                col = i + 1;
                while (col < N && row < N) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col++;
                    row++;
                }

                row = s[i] + 1;
                col = i - 1;
                while (col >= 0 && row < N) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col--;
                    row++;
                }

                row = s[i] - 1;
                col = i + 1;
                while (col < N && row >= 0) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col++;
                    row--;
                }
            }
        }

        return attacking / 2;
    }
    public static void getopChild(int[][] ar, int[] s) {
        int[][] opar = new int[N][N];
        int[] ops = new int[N];
        int e;

        copyState(ops, s);
        generateBoard(opar, ops);

        int h_current = calc_h(opar, ops);

        int[][] childar = new int[N][N];
        int[] childs = new int[N];

        copyState(childs, s);
        generateBoard(childar, childs);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (j != s[i]) {
                    childs[i] = j;
                    childar[childs[i]][i] = 1;
                    childar[s[i]][i] = 0;

                    int h_next = calc_h(childar, childs);

                    e = h_current - h_next;

                    if (e >= 0) {
                        h_current = h_next;
                        copyState(ops, childs);
                        generateBoard(opar, ops);
                    }

                    childar[childs[i]][i] = 0;
                    childs[i] = s[i];
                    childar[s[i]][i] = 1;
                }
            }
        }

        copyState(s, ops);
        zeroArray(ar, 0);
        generateBoard(ar, s);
    }

    public static void hillClimbing(int[][] board, int[] state) {
        int[][] neighbourBoard = new int[N][N];
        int[] neighbourState = new int[N];

        copyState(neighbourState, state);
        generateBoard(neighbourBoard, neighbourState);

        while (true) {
            copyState(state, neighbourState);
            generateBoard(board, state);

            getopChild(neighbourBoard, neighbourState);

            if (compareStates(state, neighbourState)) {
                printArray(board);
                break;
            } else if (calc_h(board, state) <= calc_h(neighbourBoard, neighbourState)) {
                printArray(board);
                break;}
        }
    }
    
    
    private static int[][] getrandomchild(int[][] ar,int[] s,int[] nexts) {
        int[][] childar = new int[N][N];
        int[] childs = new int[N];

        copyState(childs, s);
        generateBoard(childar, childs);
        copyState(nexts,s);
        
        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            int j = rand.nextInt(N);
            childs[i] = j;
            childar[childs[i]][i] = 1;
            if(childs[i]!=s[i]){
            childar[s[i]][i] = 0;
            nexts[i]=childs[i];
            }
            
        }

        return childar;
    }
    
    
    
    
    public void draw(int[] ry) {
    queens = new int[N];
    boardCells = new JLabel[N * N];
   Color customColor = new Color(235, 235, 208);
   Color customColor2 = new Color(119, 148, 85);
        setTitle("N-Queens Problem Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       Dimension preferredSize = new Dimension(260, 260);
       jPanel2.setPreferredSize(preferredSize);
       jPanel2.setLayout(new GridLayout(N, N));

        // Initialize board cells
        for (int i = 0; i < N ; i++) {
          for (int j = 0; j < N; j++) {
            JLabel cell = new JLabel();
            cell.setOpaque(true);
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setBorder(BorderFactory.createLineBorder(customColor2));
            cell.setBackground(customColor);
            
            if(i%2==0 && j%2==0) {
                
            	cell.setBackground(customColor2);
            	cell.setBorder(BorderFactory.createLineBorder(customColor));
            }
            else if(!(i%2==0 || j%2==0)) {
        
            	cell.setBackground(customColor2);
            	cell.setBorder(BorderFactory.createLineBorder(customColor));
            }
            if(i==ry[j]){
            System.out.println("the heuristic: " + i +j);
            cell.setIcon(new javax.swing.ImageIcon("C:\\Users\\Hp\\Desktop\\Puzzel\\src\\main\\java\\com\\mycompany\\puzzel/q.gif"));
            }
            boardCells[i] = cell;
            jPanel2.add(cell);
            
        }
      
    }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        button1 = new java.awt.Button();
        jPanel2 = new javax.swing.JPanel();
        button2 = new java.awt.Button();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Yu Gothic Medium", 3, 24)); // NOI18N
        jLabel1.setText("Hill Clambing");

        jLabel2.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N
        jLabel2.setText("Enter N    ");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        button1.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N
        button1.setLabel("Back");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 261, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 261, Short.MAX_VALUE)
        );

        button2.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N
        button2.setLabel("Solve");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(324, Short.MAX_VALUE)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(47, 47, 47)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(111, 111, 111)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(296, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
         if(!jTextField1.getText().toString().equals("")){
        String n=jTextField1.getText();
        N=Integer.parseInt(n);
        if(N>8||N<4){
            jLabel3.setText("choose N between 4-8"); 
        }
         
        else{
           jPanel2.removeAll();
            jPanel2.revalidate();
           
            int[] s = new int[N];
            int[][] ar = new int[N][N];
            randomArray(ar, s);
            printArray(ar);
            int r = calc_h(ar, s);
            System.out.println("the heuristic: " + r);
            hillClimbing(ar, s);         
            r = calc_h(ar, s);
            System.out.println("the heuristic: " + r);
            jLabel3.setText("the heuristic: " + r);
            printState(s);
            draw(s);
        }
         }
    }//GEN-LAST:event_button2ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
         Page1 tp = new Page1();
         tp.setVisible(true);
        dispose();
    }//GEN-LAST:event_button1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Page2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Page2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Page2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Page2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Page2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.Button button2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
