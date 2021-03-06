package erp.visoes.pagar;

import erp.controles.CFrame;
import erp.controles.cadastro.CTipoTitulo;
import erp.controles.pagar.CTitulosPagarPorDia;
import erp.util.ERPData;
import erp.util.ERPSelectAll;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Arthur
 */
public class VTituloPagarPorDia extends javax.swing.JInternalFrame {

    CTitulosPagarPorDia controle;


    public VTituloPagarPorDia() {
        initComponents();

        controle = new CTitulosPagarPorDia();

        CFrame.configuraAtalhoCampo(idcorrentista, btnPesqCliente, KeyEvent.VK_F3, JComponent.WHEN_FOCUSED);

        CFrame.configuraAtalhoBotao(btnSalva, KeyEvent.VK_F5, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.configuraAtalhoBotao(btnCancela, KeyEvent.VK_F8, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.configuraAtalhoFecharVisao(this, KeyEvent.VK_ESCAPE, JComponent.WHEN_IN_FOCUSED_WINDOW);
        //CFrame.configuraAtalhoMaximizarVisao(this,KeyEvent.VK_F12, JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        CFrame.acaoEnter(btnPesqCliente);
        CFrame.acaoEnter(btnSalva);
        CFrame.acaoEnter(btnCancela);
        
        idtipotitulo.setModel(CTipoTitulo.comboTipoTituloPagar());
        controle.cancela(this);                
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idSituac = new javax.swing.ButtonGroup();
        btnCancela = new javax.swing.JButton();
        btnSalva = new javax.swing.JButton();
        idcorrentista = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnPesqCliente = new javax.swing.JButton();
        pesquisa = new javax.swing.JTextField();
        idtipotitulo = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        numero_ini = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        numero_fim = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        dataEmissao_ini = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        dataEmissao_fim = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        dataVencimento_ini = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        dataVencimento_fim = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        valor_ini = new javax.swing.JFormattedTextField();
        valor_fim = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        radioTodos = new javax.swing.JRadioButton();
        radioAbertos = new javax.swing.JRadioButton();
        radioPagos = new javax.swing.JRadioButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("T??tulos por Dia");
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

        btnCancela.setText("Cancelar (F8)");
        btnCancela.setName("btnCancela"); // NOI18N
        btnCancela.setRequestFocusEnabled(false);
        btnCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelaActionPerformed(evt);
            }
        });

        btnSalva.setText("Gerar (F5)");
        btnSalva.setName("btnSalva"); // NOI18N
        btnSalva.setRequestFocusEnabled(false);
        btnSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvaActionPerformed(evt);
            }
        });

        idcorrentista.setName("idcorrentista");
        idcorrentista.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                idcorrentistaFocusLost(evt);
            }
        });

        jLabel12.setText("Cliente:");
        jLabel12.setName("jLabel12");

        btnPesqCliente.setText("Pesquisa (F3)");
        btnPesqCliente.setFocusable(false);
        btnPesqCliente.setName("btnPesqCliente");
        btnPesqCliente.setRequestFocusEnabled(false);
        btnPesqCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqClienteActionPerformed(evt);
            }
        });

        pesquisa.setEnabled(false);
        pesquisa.setName("pesquisa");

        idtipotitulo.setName("idtipotitulo");
        idtipotitulo.setNextFocusableComponent(numero_ini);

        jLabel16.setText("Tipo de T??tulo:");
        jLabel16.setName("jLabel16");

        jLabel10.setText("N??mero:");
        jLabel10.setName("jLabel10"); // NOI18N

        numero_ini.setName("numero_ini");
        numero_ini.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                numero_iniFocusGained(evt);
            }
        });

        jLabel11.setText("a");
        jLabel11.setName("jLabel11");

        numero_fim.setName("numero_fim");
        numero_fim.setNextFocusableComponent(dataEmissao_ini);
        numero_fim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                numero_fimFocusGained(evt);
            }
        });

        jLabel13.setText("Emiss??o:");
        jLabel13.setName("jLabel13");

        dataEmissao_ini.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        dataEmissao_ini.setName("dataEmissao_ini");
        dataEmissao_ini.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dataEmissao_iniFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dataEmissao_iniFocusLost(evt);
            }
        });

        jLabel19.setText("a");
        jLabel19.setName("jLabel19");

        dataEmissao_fim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        dataEmissao_fim.setName("dataEmissao_fim");
        dataEmissao_fim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dataEmissao_fimFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dataEmissao_fimFocusLost(evt);
            }
        });

        jLabel14.setText("Vencimento:");
        jLabel14.setName("jLabel14");

        dataVencimento_ini.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        dataVencimento_ini.setName("dataVencimento_ini");
        dataVencimento_ini.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dataVencimento_iniFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dataVencimento_iniFocusLost(evt);
            }
        });

        jLabel20.setText("a");
        jLabel20.setName("jLabel20");

        dataVencimento_fim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        dataVencimento_fim.setName("dataVencimento_fim");
        dataVencimento_fim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dataVencimento_fimFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dataVencimento_fimFocusLost(evt);
            }
        });

        jLabel15.setText("Valor:");
        jLabel15.setName("jLabel15");

        valor_ini.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        valor_ini.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valor_ini.setName("valor_ini");
        valor_ini.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                valor_iniFocusGained(evt);
            }
        });

        valor_fim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        valor_fim.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valor_fim.setName("valor_fim");
        valor_fim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                valor_fimFocusGained(evt);
            }
        });

        jLabel21.setText("a");
        jLabel21.setName("jLabel21");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1");

        idSituac.add(radioTodos);
        radioTodos.setSelected(true);
        radioTodos.setText("Todos");
        radioTodos.setName("radioTodos");

        idSituac.add(radioAbertos);
        radioAbertos.setText("Abertos");
        radioAbertos.setName("radioAbertos");

        idSituac.add(radioPagos);
        radioPagos.setText("Pagos");
        radioPagos.setName("radioPagos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radioTodos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                .addComponent(radioAbertos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioPagos)
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioTodos)
                    .addComponent(radioAbertos)
                    .addComponent(radioPagos)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel16)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dataVencimento_ini, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel20))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(dataVencimento_fim, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(numero_ini, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(numero_fim, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel15))
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dataEmissao_ini, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dataEmissao_fim, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(valor_ini, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(valor_fim, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(70, 70, 70))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(idtipotitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(idcorrentista, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesqCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancela)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalva)
                        .addGap(41, 41, 41))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(idcorrentista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesqCliente)
                    .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idtipotitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(numero_ini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(numero_fim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dataVencimento_ini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel20)
                            .addComponent(dataVencimento_fim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(dataEmissao_ini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(dataEmissao_fim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(valor_ini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(valor_fim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalva)
                    .addComponent(btnCancela))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelaActionPerformed
        controle.cancela(this);
}//GEN-LAST:event_btnCancelaActionPerformed

    private void btnSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvaActionPerformed
        controle.salva(this);
}//GEN-LAST:event_btnSalvaActionPerformed

    private void btnPesqClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqClienteActionPerformed
        controle.pesquisaFornecedor(this);
    }//GEN-LAST:event_btnPesqClienteActionPerformed

    private void idcorrentistaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idcorrentistaFocusLost
       controle.criticaFornecedor(this);
    }//GEN-LAST:event_idcorrentistaFocusLost

    private void dataEmissao_iniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataEmissao_iniFocusGained
        SwingUtilities.invokeLater(new ERPSelectAll(dataEmissao_ini));
    }//GEN-LAST:event_dataEmissao_iniFocusGained

    private void dataVencimento_iniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataVencimento_iniFocusGained
        SwingUtilities.invokeLater(new ERPSelectAll(dataVencimento_ini));
    }//GEN-LAST:event_dataVencimento_iniFocusGained

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        CFrame.fechaVisao(CFrame.VISOES.TITULO_PAGAR_POR_DIA);
    }//GEN-LAST:event_formInternalFrameClosed

    private void numero_fimFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_numero_fimFocusGained
        numero_fim.selectAll();
    }//GEN-LAST:event_numero_fimFocusGained

    private void dataEmissao_fimFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataEmissao_fimFocusGained
       SwingUtilities.invokeLater(new ERPSelectAll(dataEmissao_fim));
    }//GEN-LAST:event_dataEmissao_fimFocusGained

    private void dataVencimento_fimFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataVencimento_fimFocusGained
        SwingUtilities.invokeLater(new ERPSelectAll(dataVencimento_fim));
    }//GEN-LAST:event_dataVencimento_fimFocusGained

    private void numero_iniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_numero_iniFocusGained
        numero_ini.selectAll();
    }//GEN-LAST:event_numero_iniFocusGained

    private void dataVencimento_iniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataVencimento_iniFocusLost
        ERPData.completaData(dataVencimento_ini);
    }//GEN-LAST:event_dataVencimento_iniFocusLost

    private void dataVencimento_fimFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataVencimento_fimFocusLost
        ERPData.completaData(dataVencimento_fim);
    }//GEN-LAST:event_dataVencimento_fimFocusLost

    private void dataEmissao_iniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataEmissao_iniFocusLost
        ERPData.completaData(dataEmissao_ini);
    }//GEN-LAST:event_dataEmissao_iniFocusLost

    private void dataEmissao_fimFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataEmissao_fimFocusLost
        ERPData.completaData(dataEmissao_fim);
    }//GEN-LAST:event_dataEmissao_fimFocusLost

    private void valor_iniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_valor_iniFocusGained
      SwingUtilities.invokeLater(new ERPSelectAll(valor_ini));
    }//GEN-LAST:event_valor_iniFocusGained

    private void valor_fimFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_valor_fimFocusGained
         SwingUtilities.invokeLater(new ERPSelectAll(valor_fim));
    }//GEN-LAST:event_valor_fimFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancela;
    private javax.swing.JButton btnPesqCliente;
    private javax.swing.JButton btnSalva;
    private javax.swing.JFormattedTextField dataEmissao_fim;
    private javax.swing.JFormattedTextField dataEmissao_ini;
    private javax.swing.JFormattedTextField dataVencimento_fim;
    private javax.swing.JFormattedTextField dataVencimento_ini;
    private javax.swing.ButtonGroup idSituac;
    private javax.swing.JTextField idcorrentista;
    private javax.swing.JComboBox idtipotitulo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField numero_fim;
    private javax.swing.JTextField numero_ini;
    private javax.swing.JTextField pesquisa;
    private javax.swing.JRadioButton radioAbertos;
    private javax.swing.JRadioButton radioPagos;
    private javax.swing.JRadioButton radioTodos;
    private javax.swing.JFormattedTextField valor_fim;
    private javax.swing.JFormattedTextField valor_ini;
    // End of variables declaration//GEN-END:variables
}
