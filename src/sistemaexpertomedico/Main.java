package sistemaexpertomedico;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Main extends javax.swing.JFrame implements SistemaExperto.CallBackMain {

    private SistemaExperto sistemaExp;

    public Main() {
        initComponents();
        sistemaExp = new SistemaExperto(this);
        if (sistemaExp.loadDataBase()) {
            sistemaExp.startQuestions();
        } else {
            System.out.println("No se pudo cargar la base de conocimiento");
        }
    }

    @Override
    public void setQuestion(String question) {
        lblPregunta.setText(question);
    }

    @Override
    public void diagnosisCompleted(String enfermedad) {
        lblPregunta.setText("Enfermedad encontrada: " + enfermedad);

        String[] options = new String[]{"Aceptar", "Justificar diagnóstico"};
        int response = JOptionPane.showOptionDialog(null, "La enfermedad encontrada es " + enfermedad, "Diagnóstico completo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (response == 1) {
            ArrayList<String> sintomasEncontrados = sistemaExp.getSintomasEncontrados();
            String message = "Se determino este diagnostico porque se encontraron los siguentes\n"
                    + "sintomas: \n";

            for (String s : sintomasEncontrados) {
                message += "\n";
                message += s;
            }
            showDialog(message);
        }
        closeDiagnosis();
    }

    @Override
    public void finish() {
        showDialog("No tengo suficiente conocimiento para diagnosticar esta enfermedad");
        closeDiagnosis();
    }

    public void closeDiagnosis() {
        Inicio iFrame = new Inicio();
        iFrame.setVisible(true);
        dispose();
    }

    @Override
    public void showDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radSi = new javax.swing.JRadioButton();
        radNo = new javax.swing.JRadioButton();
        radPorQue = new javax.swing.JRadioButton();
        lblPregunta = new javax.swing.JLabel();
        lblRespuesta = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        radSi.setText("Si");
        radSi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radSiStateChanged(evt);
            }
        });
        radSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radSiActionPerformed(evt);
            }
        });

        radNo.setText("No");
        radNo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radNoStateChanged(evt);
            }
        });
        radNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radNoActionPerformed(evt);
            }
        });

        radPorQue.setText("Porque");
        radPorQue.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                radPorQueStateChanged(evt);
            }
        });
        radPorQue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPorQueActionPerformed(evt);
            }
        });

        lblPregunta.setText("Pregunta");

        lblRespuesta.setText("Respuesta:");

        btnOk.setText("Siguiente");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRespuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radSi)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(159, 159, 159)
                                .addComponent(radNo))
                            .addComponent(btnCancel))
                        .addGap(97, 97, 97)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(radPorQue)
                            .addComponent(btnOk)))
                    .addComponent(lblPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(lblPregunta)
                .addGap(48, 48, 48)
                .addComponent(lblRespuesta)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radSi)
                    .addComponent(radNo)
                    .addComponent(radPorQue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnOk))
                .addGap(41, 41, 41))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void radPorQueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPorQueActionPerformed
        if (radPorQue.isSelected()) {
            if (radNo.isSelected()) {
                radNo.setSelected(false);
            }
            if (radSi.isSelected()) {
                radSi.setSelected(false);
            }
        }
    }//GEN-LAST:event_radPorQueActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        String answer = "";

        if (radSi.isSelected()) {
            answer = "si";
        } else if (radNo.isSelected()) {
            answer = "no";
        } else if (radPorQue.isSelected()) {
            answer = "porque";
        }

        if (!answer.isEmpty()) {
            sistemaExp.setAnswer(answer);
            if (!sistemaExp.findDiagnostic()) {
                sistemaExp.startQuestions();
            }
        } else {
            showDialog("Seleccione una respuesta");
        }
        
        radSi.setSelected(false);
        radNo.setSelected(false);
        radPorQue.setSelected(false);
    }//GEN-LAST:event_btnOkActionPerformed

    private void radNoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radNoStateChanged
    }//GEN-LAST:event_radNoStateChanged

    private void radSiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radSiStateChanged
    }//GEN-LAST:event_radSiStateChanged

    private void radPorQueStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_radPorQueStateChanged
    }//GEN-LAST:event_radPorQueStateChanged

    private void radNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radNoActionPerformed
        if (radNo.isSelected()) {
            if (radSi.isSelected()) {
                radSi.setSelected(false);
            }
            if (radPorQue.isSelected()) {
                radPorQue.setSelected(false);
            }
        }
    }//GEN-LAST:event_radNoActionPerformed

    private void radSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radSiActionPerformed
        if (radSi.isSelected()) {
            if (radNo.isSelected()) {
                radNo.setSelected(false);
            }
            if (radPorQue.isSelected()) {
                radPorQue.setSelected(false);
            }
        }
    }//GEN-LAST:event_radSiActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        closeDiagnosis();
    }//GEN-LAST:event_btnCancelActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Main().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel lblPregunta;
    private javax.swing.JLabel lblRespuesta;
    private javax.swing.JRadioButton radNo;
    private javax.swing.JRadioButton radPorQue;
    private javax.swing.JRadioButton radSi;
    // End of variables declaration//GEN-END:variables
}
