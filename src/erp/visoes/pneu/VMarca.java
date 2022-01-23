package erp.visoes.pneu;

import erp.visoes.cadastro.*;
import erp.controles.CFrame;
import erp.controles.cadastro.CUnidade_Medida;
import erp.controles.pneu.CMarca;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;

/**
 * @author Arthur
 */
public class VMarca extends JInternalFrame {

    CMarca controle;

    /** Creates new form VProduto */
    public VMarca() {
        initComponents();
        
        CFrame.configuraAtalhoCampo(idMarca, btnPesqIdUnidade, KeyEvent.VK_F3, JComponent.WHEN_FOCUSED);


        CFrame.configuraAtalhoBotao(btnSalva, KeyEvent.VK_F5, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.configuraAtalhoBotao(btnCancela, KeyEvent.VK_F8, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.configuraAtalhoBotao(btnExclui, KeyEvent.VK_F9, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.configuraAtalhoFecharVisao(this, KeyEvent.VK_ESCAPE, JComponent.WHEN_IN_FOCUSED_WINDOW);
        //CFrame.configuraAtalhoMaximizarVisao(this,KeyEvent.VK_F12, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.acaoEnter(btnPesqIdUnidade);
        CFrame.acaoEnter(btnSalva);
        CFrame.acaoEnter(btnCancela);
        CFrame.acaoEnter(btnExclui);
        
        controle = new CMarca();

        idMarca.requestFocus();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        idMarca = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        descricao = new javax.swing.JTextField();
        btnPesqIdUnidade = new javax.swing.JButton();
        btnExclui = new javax.swing.JButton();
        btnCancela = new javax.swing.JButton();
        btnSalva = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Marca");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jLabel1.setText("ID:");
        jLabel1.setName("jLabel1"); // NOI18N

        idMarca.setName("idMarca"); // NOI18N
        idMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idMarcaActionPerformed(evt);
            }
        });
        idMarca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                idMarcaFocusLost(evt);
            }
        });

        jLabel2.setText("Descrição:");
        jLabel2.setName("jLabel2"); // NOI18N

        descricao.setName("descricao"); // NOI18N
        descricao.setNextFocusableComponent(btnSalva);

        btnPesqIdUnidade.setText("Pesquisa (F3)");
        btnPesqIdUnidade.setFocusable(false);
        btnPesqIdUnidade.setName("btnPesqIdUnidade"); // NOI18N
        btnPesqIdUnidade.setRequestFocusEnabled(false);
        btnPesqIdUnidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqIdUnidadeActionPerformed(evt);
            }
        });

        btnExclui.setText("Excluir (F9)");
        btnExclui.setName("btnExclui"); // NOI18N
        btnExclui.setRequestFocusEnabled(false);
        btnExclui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluiActionPerformed(evt);
            }
        });

        btnCancela.setText("Cancelar (F8)");
        btnCancela.setName("btnCancela"); // NOI18N
        btnCancela.setRequestFocusEnabled(false);
        btnCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelaActionPerformed(evt);
            }
        });

        btnSalva.setText("Salvar (F5)");
        btnSalva.setName("btnSalva"); // NOI18N
        btnSalva.setRequestFocusEnabled(false);
        btnSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(idMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesqIdUnidade)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(descricao, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnExclui)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancela)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalva)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesqIdUnidade)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(descricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExclui)
                    .addComponent(btnCancela)
                    .addComponent(btnSalva))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void idMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idMarcaActionPerformed

    private void btnPesqIdUnidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqIdUnidadeActionPerformed
        controle.pesquisa(this);
}//GEN-LAST:event_btnPesqIdUnidadeActionPerformed

    private void btnExcluiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluiActionPerformed
       controle.exclui(this);
}//GEN-LAST:event_btnExcluiActionPerformed

    private void btnCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelaActionPerformed
        controle.cancela(this);
}//GEN-LAST:event_btnCancelaActionPerformed

    private void btnSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvaActionPerformed
        controle.salva(this);
        idMarca.grabFocus();
}//GEN-LAST:event_btnSalvaActionPerformed

    private void idMarcaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idMarcaFocusLost
        controle.criticaId(this);
    }//GEN-LAST:event_idMarcaFocusLost

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        CFrame.fechaVisao(CFrame.VISOES.MARCA);
    }//GEN-LAST:event_formInternalFrameClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancela;
    private javax.swing.JButton btnExclui;
    private javax.swing.JButton btnPesqIdUnidade;
    private javax.swing.JButton btnSalva;
    private javax.swing.JTextField descricao;
    private javax.swing.JTextField idMarca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

}
