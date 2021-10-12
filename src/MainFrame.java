
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Alejo
 */
public class MainFrame extends javax.swing.JFrame {

    //Initialize variables
    List<Player> playerList = new ArrayList<>();
    private String tournamentName;
    private String player1;
    private String player2;
    private int score1;
    private int score2;
    private String round;
    File playersFile = new File("players.csv");
    File p1File = new File("player1.txt");
    File p2File = new File("player2.txt");
    File p1SponsorFile = new File("sponsor1.txt");
    File p2SponsorFile = new File("sponsor2.txt");
    File p1FullFile = new File("fullP1.txt");
    File p2FullFile = new File("fullP2.txt");
    File roundFile = new File("round.txt");
    File score1File = new File("score1.txt");
    File score2File = new File("score2.txt");
    Set<String> s = new TreeSet<>();

    //Methods
    private void setTournament() {
        Tournament tournament = new Tournament(this, true);
        tournament.setVisible(true);
        tournamentName = tournament.getTxtTournamentName().getText();
        this.setTitle(tournamentName);
    }

    private void initFiles() throws IOException {
        if (!playersFile.exists()) {
            playersFile.createNewFile();
        }
        if (!p1File.exists()) {
            p1File.createNewFile();
        }
        if (!p2File.exists()) {
            p2File.createNewFile();
        }
        if (!p1SponsorFile.exists()) {
            p1SponsorFile.createNewFile();
        }
        if (!p2SponsorFile.exists()) {
            p2SponsorFile.createNewFile();
        }
        if (!p1FullFile.exists()) {
            p1FullFile.createNewFile();
        }
        if (!p2FullFile.exists()) {
            p2FullFile.createNewFile();
        }
        if (!roundFile.exists()) {
            roundFile.createNewFile();
        }
        if (!score1File.exists()) {
            score1File.createNewFile();
        }
        if (!score2File.exists()) {
            score2File.createNewFile();
        }
    }

    private void loadPlayers() {
        BufferedReader br = null;
        try {
            String curLine;
            br = new BufferedReader(new FileReader(playersFile));
            playerList.clear();
            while ((curLine = br.readLine()) != null) {
                String[] playerInfo = curLine.split(",");
                playerList.add(new Player(playerInfo[0], playerInfo[1]));
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("File not found");
        } catch (IndexOutOfBoundsException ofBoundsException) {
            System.err.println("No users saved");
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Player p : playerList) {
            s.add(p.getNickname());
        }
    }
    
    private void savePlayer (JTextField sponsor, JTextField player) {
        Player p = new Player(sponsor.getText(), player.getText());
        if (!playerList.contains(p)) {
            playerList.add(p);
            try {
                BufferedWriter br = new BufferedWriter(new FileWriter(playersFile, true));
                br.write(p.toString());
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        s.add(p.getNickname());
    }

    private void autocomplete(JTextField txt) {
        String to_check = txt.getText();
        int to_check_len = to_check.length();
        for (String data : s) {
            String check_from_data = "";
            for (int i = 0; i < to_check_len; i++) {
                if (to_check_len <= data.length()) {
                    check_from_data = check_from_data + data.charAt(i);
                }
            }
            //System.out.print(check_from_data);
            if (check_from_data.equals(to_check)) {
                //System.out.print("Found");
                txt.setText(data);
                txt.setSelectionStart(to_check_len);
                txt.setSelectionEnd(data.length());
                break;
            }
        }
    }

    private void update() throws IOException {
        BufferedWriter p1BufferedWriter = new BufferedWriter(new FileWriter(p1File));
        BufferedWriter p2BufferedWriter = new BufferedWriter(new FileWriter(p2File));
        BufferedWriter p1sponsorBufferedWriter = new BufferedWriter(new FileWriter(p1SponsorFile));
        BufferedWriter p2sponsorBufferedWriter = new BufferedWriter(new FileWriter(p2SponsorFile));
        BufferedWriter roundBufferedWriter = new BufferedWriter(new FileWriter(roundFile));
        BufferedWriter p1ScoreBufferedWriter = new BufferedWriter(new FileWriter(score1File));
        BufferedWriter p2ScorBufferedWriter = new BufferedWriter(new FileWriter(score2File));
        BufferedWriter p1FullFileBufferedWriter = new BufferedWriter(new FileWriter(p1FullFile));
        BufferedWriter p2FullFileBufferedWriter = new BufferedWriter(new FileWriter(p2FullFile));
        p1BufferedWriter.write(player1 = txtPlayer1.getText());
        p2BufferedWriter.write(player2 = txtPlayer2.getText());
        p1sponsorBufferedWriter.write(txtSponsor1.getText());
        p2sponsorBufferedWriter.write(txtSponsor2.getText());
        roundBufferedWriter.write(round = btnWL.getText() + " " + cmbRound.getSelectedItem().toString());
        p1ScoreBufferedWriter.write(spnScore1.getValue().toString());
        p2ScorBufferedWriter.write(spnScore2.getValue().toString());
        p1FullFileBufferedWriter.write(txtSponsor1.getText() + "|" + player1);
        p2FullFileBufferedWriter.write(txtSponsor2.getText() + "|" + player2);
        p1BufferedWriter.close();
        p2BufferedWriter.close();
        p1sponsorBufferedWriter.close();
        p2sponsorBufferedWriter.close();
        roundBufferedWriter.close();
        p1ScoreBufferedWriter.close();
        p2ScorBufferedWriter.close();
        p1FullFileBufferedWriter.close();
        p2FullFileBufferedWriter.close();
    }

    private void errorDialog(String message) {
        JOptionPane.showMessageDialog(null,
                message,
                "Something went wrong...",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        try {
            initFiles();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setTournament();
        loadPlayers();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSponsor1 = new javax.swing.JTextField();
        txtPlayer1 = new javax.swing.JTextField();
        txtSponsor2 = new javax.swing.JTextField();
        txtPlayer2 = new javax.swing.JTextField();
        btnSwapPlayers = new javax.swing.JButton();
        cmbRound = new javax.swing.JComboBox<>();
        btnBestOf = new javax.swing.JButton();
        tglLoser2 = new javax.swing.JToggleButton();
        tglLoser1 = new javax.swing.JToggleButton();
        spnScore2 = new javax.swing.JSpinner();
        spnScore1 = new javax.swing.JSpinner();
        btnWL = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnSaveP1 = new javax.swing.JButton();
        btnSaveP2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);

        txtPlayer1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlayer1ActionPerformed(evt);
            }
        });
        txtPlayer1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPlayer1KeyReleased(evt);
            }
        });

        txtPlayer2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPlayer2KeyReleased(evt);
            }
        });

        btnSwapPlayers.setText("↔");
        btnSwapPlayers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSwapPlayersActionPerformed(evt);
            }
        });

        cmbRound.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Round 1", "Round 2", "Round 3", "Round 4", "Quarters", "Semis", "Final", "Grand Final", "Money Match", " " }));
        cmbRound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRoundActionPerformed(evt);
            }
        });

        btnBestOf.setText("BO3");
        btnBestOf.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBestOf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBestOfActionPerformed(evt);
            }
        });

        tglLoser2.setText("L");
        tglLoser2.setEnabled(false);

        tglLoser1.setText("L");
        tglLoser1.setEnabled(false);

        spnScore2.setModel(new javax.swing.SpinnerNumberModel(0, 0, 3, 1));

        spnScore1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 3, 1));

        btnWL.setText("Winners");
        btnWL.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnWL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWLActionPerformed(evt);
            }
        });

        btnUpdate.setText("UPDATE");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSaveP1.setText("Save P1");
        btnSaveP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveP1ActionPerformed(evt);
            }
        });

        btnSaveP2.setText("Save P2");
        btnSaveP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveP2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 0, 51));
        jButton1.setText("CLEAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSponsor1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPlayer1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSaveP1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                                .addComponent(spnScore1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tglLoser1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSwapPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSponsor2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPlayer2, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tglLoser2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnScore2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                                .addComponent(btnSaveP2))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnWL, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbRound, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBestOf))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSponsor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPlayer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSwapPlayers))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSponsor2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPlayer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tglLoser1)
                        .addComponent(spnScore1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSaveP1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tglLoser2)
                        .addComponent(spnScore2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSaveP2)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbRound, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBestOf)
                    .addComponent(btnWL))
                .addGap(18, 18, 18)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnWLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWLActionPerformed
        if (btnWL.getText().equals("Winners")) {
            btnWL.setText("Losers");
        } else
            btnWL.setText("Winners");
    }//GEN-LAST:event_btnWLActionPerformed

    private void btnBestOfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBestOfActionPerformed
        if (btnBestOf.getText().equals("BO3")) {
            btnBestOf.setText("BO5");
            //spnScore1.
        } else
            btnBestOf.setText("BO3");
    }//GEN-LAST:event_btnBestOfActionPerformed

    private void btnSwapPlayersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSwapPlayersActionPerformed
        String auxPlayer = txtPlayer1.getText();
        String auxSponsor = txtSponsor1.getText();
        Object auxScore = spnScore1.getValue();
        txtPlayer1.setText(txtPlayer2.getText());
        txtSponsor1.setText(txtSponsor2.getText());
        spnScore1.setValue(spnScore2.getValue());
        txtPlayer2.setText(auxPlayer);
        txtSponsor2.setText(auxSponsor);
        spnScore2.setValue(auxScore);
    }//GEN-LAST:event_btnSwapPlayersActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try {
            update();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void cmbRoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRoundActionPerformed
        if (cmbRound.getSelectedItem().toString().equals("Grand Final")) {
            tglLoser1.setEnabled(true);
            tglLoser2.setEnabled(true);
        } else {
            tglLoser1.setSelected(false);
            tglLoser2.setSelected(false);
            tglLoser1.setEnabled(false);
            tglLoser2.setEnabled(false);
        }
    }//GEN-LAST:event_cmbRoundActionPerformed

    private void btnSaveP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveP1ActionPerformed
        savePlayer(txtSponsor1, txtPlayer1);
    }//GEN-LAST:event_btnSaveP1ActionPerformed

    private void txtPlayer1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlayer1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlayer1ActionPerformed

    private void txtPlayer1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlayer1KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_DELETE) {
        } else {
            autocomplete(txtPlayer1);
        }
    }//GEN-LAST:event_txtPlayer1KeyReleased

    private void txtPlayer2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlayer2KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_DELETE) {
        } else {
            autocomplete(txtPlayer2);
        }
    }//GEN-LAST:event_txtPlayer2KeyReleased

    private void btnSaveP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveP2ActionPerformed
        savePlayer(txtSponsor2, txtPlayer2);
    }//GEN-LAST:event_btnSaveP2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        txtPlayer1.setText("");
        txtPlayer2.setText("");
        txtSponsor1.setText("");
        txtSponsor2.setText("");
        spnScore1.setValue((Integer) 0);
        spnScore2.setValue((Integer) 0);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBestOf;
    private javax.swing.JButton btnSaveP1;
    private javax.swing.JButton btnSaveP2;
    private javax.swing.JButton btnSwapPlayers;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnWL;
    private javax.swing.JComboBox<String> cmbRound;
    private javax.swing.JButton jButton1;
    private javax.swing.JSpinner spnScore1;
    private javax.swing.JSpinner spnScore2;
    private javax.swing.JToggleButton tglLoser1;
    private javax.swing.JToggleButton tglLoser2;
    private javax.swing.JTextField txtPlayer1;
    private javax.swing.JTextField txtPlayer2;
    private javax.swing.JTextField txtSponsor1;
    private javax.swing.JTextField txtSponsor2;
    // End of variables declaration//GEN-END:variables
}