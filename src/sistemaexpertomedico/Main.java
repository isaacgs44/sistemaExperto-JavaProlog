/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaexpertomedico;

import java.util.Map;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Util;
import org.jpl7.Variable;

/**
 *
 * @author IsaacGS
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        String experto = "consult('expertojava.pl')";//aqui colocan el nombre de su archivo a compilar
        String conocimiento = "consult('conocimiento.pl')";//aqui colocan el nombre de su archivo a compilar

        Query q1 = new Query(experto);
        Query q2 = new Query(conocimiento);
        System.out.println(experto + " " + (q1.hasSolution() ? "true" : "false")); //mostrara mensaje  si hay o no conexion           
        System.out.println(conocimiento + " " + (q2.hasSolution() ? "true" : "false")); //mostrara mensaje  si hay o no conexion     

        if (q1.hasSolution() && q2.hasSolution()) {
            System.out.println("------------->Sistema Cargado correctamente");

            Query q3 = new Query("conocimiento(X,Y)");
            System.out.println("---Goal-->" + q3.goal());
            leeConsulta(q3);
        }else
            System.out.println("--------------->El sistema experto no se pudo cargar");
    }

    private static void leeConsulta(Query q4) {
        System.out.println("-------Goal----->" + q4.goal());
        System.out.println("hasSol: " + (q4.hasSolution() ? "true" : "false"));
        Map<String, Term>[] allSolutions = q4.allSolutions();
        System.out.println("lenght solution: " + q4.allSolutions().length);

        int j = 0;
        while (q4.hasMoreSolutions()) {

            System.out.println("----->" + Util.toString(q4.nextSolution()));

            System.out.println("j: " + j);
            Map<String, Term> solution = q4.nextSolution();
            System.out.println("X = " + solution.get("X"));
            Term sintomas = solution.get("Y");
            Term enfermedad = solution.get("X");

            System.out.println("length: " + sintomas.toTermArray().length);
            Term[] sintomasArray = sintomas.toTermArray();

            for (int i = 0; i < sintomasArray.length; i++) {

                System.out.println("--Argument--->" + sintomasArray[i]);
                Query q5 = new Query("prueba_verdad_de(" + enfermedad.typeName()
                        + ", " + sintomasArray[i].typeName() + ")");

                System.out.println("-----Sol--->" + q5.hasSolution());
            }

            j++;
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

        radSi = new javax.swing.JRadioButton();
        radNo = new javax.swing.JRadioButton();
        radPorQue = new javax.swing.JRadioButton();
        lblPregunta = new javax.swing.JLabel();
        lblRespuesta = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        radSi.setText("Si");

        radNo.setText("No");

        radPorQue.setText("Porque");
        radPorQue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPorQueActionPerformed(evt);
            }
        });

        lblPregunta.setText("Pregunta");

        lblRespuesta.setText("Respuesta");

        btnOk.setText("Ok");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(radSi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(radNo)
                .addGap(153, 153, 153)
                .addComponent(radPorQue)
                .addGap(70, 70, 70))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(lblPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(lblRespuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(btnOk)))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lblPregunta)
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radSi)
                    .addComponent(radPorQue)
                    .addComponent(radNo))
                .addGap(31, 31, 31)
                .addComponent(lblRespuesta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(btnOk)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radPorQueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPorQueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radPorQueActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel lblPregunta;
    private javax.swing.JLabel lblRespuesta;
    private javax.swing.JRadioButton radNo;
    private javax.swing.JRadioButton radPorQue;
    private javax.swing.JRadioButton radSi;
    // End of variables declaration//GEN-END:variables
}
