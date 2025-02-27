/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.puzzel;

import static com.mycompany.puzzel.Page2.N;
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
public class Page3 extends javax.swing.JFrame {
private int boardSize=8;
    
    private int[] queens;
   private JLabel[] boardCells;
    /**
     * Creates new form Page3
     */
    public Page3() {
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
            cell.setIcon(new javax.swing.ImageIcon("C:/Users/hp/OneDrive/Desktop/q.gif"));
            }
            /*
             if(i==2&&j==3){
            cell.setIcon(new javax.swing.ImageIcon("C:\\Users\\MASTAER\\OneDrive\\Documents\\NetBeansProjects\\Puzzel\\Puzzel\\src\\main\\java\\com\\mycompany\\puzzel\\q.gif"));
            }
              if(i==4&&j==4){
            cell.setIcon(new javax.swing.ImageIcon("C:\\Users\\MASTAER\\OneDrive\\Documents\\NetBeansProjects\\Puzzel\\Puzzel\\src\\main\\java\\com\\mycompany\\puzzel\\q.gif"));
            }*/
            boardCells[i] = cell;
            jPanel2.add(cell);
            
        }
      
    }
    }

     private  int[][] simulatedAnnealing(double t,double co,double it ,int[] bests ) {
        int iteration=0;
        
        int[][] currentState=new int[N][N];
        int[] s = new int[N];
        int[][] nextState = new int[N][N];
        int[] nexts = new int[N];

        int[][] bestState=new int[N][N];
        
        randomArray(currentState,s);
        
        copyState(bests,s);
        generateBoard(bestState,bests);
        
        int currenth = calc_h(currentState,s);
        printArray(currentState);
        System.out.print(currenth+"\n");

        while (currenth > 0 && t > it) {
            t = t * co;
            
            nextState = getrandomchild(currentState,s,nexts);
            int nexth = calc_h(nextState,nexts);
            currenth = calc_h(currentState,s);
          // System.out.print("current \n");
         // printArray(currentState);
         // System.out.print(currenth+"\n");
            
            int e = currenth-nexth;
            
           // System.out.print("\n");
           // System.out.print("next \n");
          //  printArray(nextState);
          //  System.out.print(nexth+"\n");
          //  System.out.print(e+"\n");
            
            if(e>0){
                copyState(s,nexts);
                generateBoard(currentState,s);
               // System.out.print("current 2 \n");
              // printArray(currentState);
                currenth = calc_h(currentState,s);
               // System.out.print(currenth+"\n");
                
                int besth = calc_h(bestState,bests);
                currenth = calc_h(currentState,s);
                
                if(besth>currenth){
                    copyState(bests,s);
                    generateBoard(bestState,bests);
                  // System.out.print("best \n");
                  //  printArray(bestState);
                  //  System.out.print(currenth+"\n");
                }
            }else if ( Math.exp(-e / t) > Math.round( Math.random() )) {
                copyState(s,nexts);
                generateBoard(currentState,s);
            }


            iteration++;
           // System.out.print(iteration+"\n");
        }

        System.out.println("Iterations: " + iteration);
        jLabel7.setText("Iterations: " + iteration);
        

        return bestState;
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        button1 = new java.awt.Button();
        button2 = new java.awt.Button();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N
        jLabel1.setText("N   ");

        jLabel2.setFont(new java.awt.Font("Yu Gothic Medium", 3, 24)); // NOI18N
        jLabel2.setText("Simulated Annealing  ");

        jLabel3.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N
        jLabel3.setText("Intial Temperature  ");

        jLabel4.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N
        jLabel4.setText("Cooling Rate  ");

        jLabel5.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N
        jLabel5.setText("Maximum Iteration   ");

        jTextField1.setFont(new java.awt.Font("Yu Gothic Medium", 0, 12)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N

        jTextField4.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
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

        button1.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N
        button1.setLabel("Solve");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setFont(new java.awt.Font("Yu Gothic Medium", 3, 12)); // NOI18N
        button2.setLabel("Back");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField2))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(button2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jTextField1))
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
        Page1 tp = new Page1();
         tp.setVisible(true);
        dispose();
    }//GEN-LAST:event_button2ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
         if(!(jTextField1.getText().toString().equals("")&&jTextField2.getText().toString().equals("") && jTextField3.getText().toString().equals("") && jTextField4.getText().toString().equals(""))){
        String n=jTextField1.getText();
        N=Integer.parseInt(n);
        if(N>8||N<4){
            jLabel7.setText("choose N between 4-8"); 
        }
        else{
            jPanel2.removeAll();
            jPanel2.revalidate();
            int iteration=0;
           
            double t=Double.parseDouble(jTextField2.getText());
            double co=Double.parseDouble(jTextField4.getText());
            double it=Double.parseDouble(jTextField3.getText());
            int[] bests = new int[N];
            int[][] solution = simulatedAnnealing(t,co,it,bests);
            printArray(solution);
            int r = calc_h(solution, bests);
            jLabel6.setText("the heuristic: " + r);
            draw(bests);
        }  
         }
          else{
            jLabel6.setText("enter information" );
            jLabel7.setText("   " );
        }
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
            java.util.logging.Logger.getLogger(Page3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Page3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Page3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Page3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Page3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.Button button2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
