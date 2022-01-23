package erp.visoes.caixa;

import erp.controles.CFrame;
import erp.controles.cadastro.CCorrentista;
import erp.controles.caixa.CLancamentoCaixaS;
import erp.modelos.tablemodel.LancamentoCaixaTableModel;
import erp.util.ERPData;
import erp.util.ERPSelectAll;
import erp.util.ERPTable;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Arthur
 */
public class VLancamentoCaixaS extends javax.swing.JInternalFrame {

    CLancamentoCaixaS controle;

    public VLancamentoCaixaS() {
        initComponents();

        controle = new CLancamentoCaixaS();

        CFrame.configuraAtalhoCampo(idconta, btnPesq, KeyEvent.VK_F3, JComponent.WHEN_FOCUSED);

        CFrame.configuraAtalhoBotao(btnSalva, KeyEvent.VK_F5, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.configuraAtalhoBotao(btnCancela, KeyEvent.VK_F8, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.configuraAtalhoBotao(btnExclui, KeyEvent.VK_F9, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.configuraAtalhoFecharVisao(this, KeyEvent.VK_ESCAPE, JComponent.WHEN_IN_FOCUSED_WINDOW);
        //CFrame.configuraAtalhoMaximizarVisao(this,KeyEvent.VK_F12, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.acaoEnter(btnPesq);
        CFrame.acaoEnter(btnSalva);
        CFrame.acaoEnter(btnCancela);
        CFrame.acaoEnter(btnExclui);

        idempresa.setModel(CCorrentista.comboEmpresa());
        JTable.setModel(new LancamentoCaixaTableModel(null));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnExclui = new javax.swing.JButton();
        btnCancela = new javax.swing.JButton();
        btnSalva = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTable = new javax.swing.JTable();
        dataLancamento = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        idempresa = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        idlancamento = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        idconta = new javax.swing.JTextField();
        btnPesq = new javax.swing.JButton();
        descricao = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        entrada = new javax.swing.JRadioButton();
        saida = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        valor = new javax.swing.JFormattedTextField();
        observacao = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        saldo_final = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        total_entrada = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        total_saida = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        saldo_ini = new javax.swing.JFormattedTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Lançamento Caixa Simples");
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
                formInternalFrameOpened(evt);
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

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        JTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        JTable.setName("JTable"); // NOI18N
        JTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTable);

        dataLancamento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        dataLancamento.setName("dataLancamento"); // NOI18N
        dataLancamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dataLancamentoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dataLancamentoFocusLost(evt);
            }
        });

        jLabel13.setText("Data:");
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel16.setText("Empresa");
        jLabel16.setName("jLabel16"); // NOI18N

        idempresa.setName("idempresa"); // NOI18N
        idempresa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                idempresaFocusLost(evt);
            }
        });

        jLabel10.setText("Número:");
        jLabel10.setName("jLabel10"); // NOI18N

        idlancamento.setName("idlancamento"); // NOI18N
        idlancamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                idlancamentoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                idlancamentoFocusLost(evt);
            }
        });

        jLabel18.setText("Conta:");
        jLabel18.setName("jLabel18"); // NOI18N

        idconta.setName("idconta"); // NOI18N
        idconta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                idcontaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                idcontaFocusLost(evt);
            }
        });

        btnPesq.setText("Pesquisa (F3)");
        btnPesq.setFocusable(false);
        btnPesq.setName("btnPesq"); // NOI18N
        btnPesq.setRequestFocusEnabled(false);
        btnPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqActionPerformed(evt);
            }
        });

        descricao.setEnabled(false);
        descricao.setName("descricao"); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setName("jPanel2"); // NOI18N

        buttonGroup1.add(entrada);
        entrada.setMnemonic('E');
        entrada.setSelected(true);
        entrada.setText("Entrada");
        entrada.setFocusable(false);
        entrada.setName("entrada"); // NOI18N
        entrada.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                entradaStateChanged(evt);
            }
        });

        buttonGroup1.add(saida);
        saida.setMnemonic('S');
        saida.setText("Saída");
        saida.setFocusable(false);
        saida.setName("saida"); // NOI18N
        saida.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                saidaStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(entrada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saida)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrada)
                    .addComponent(saida))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel15.setText("Valor:");
        jLabel15.setName("jLabel15"); // NOI18N

        valor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        valor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valor.setName("valor"); // NOI18N
        valor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                valorFocusGained(evt);
            }
        });
        valor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                valorKeyReleased(evt);
            }
        });

        observacao.setName("observacao"); // NOI18N
        observacao.setNextFocusableComponent(btnSalva);
        observacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                observacaoFocusGained(evt);
            }
        });

        jLabel17.setText("Obs.:");
        jLabel17.setName("jLabel17"); // NOI18N

        saldo_final.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        saldo_final.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        saldo_final.setEnabled(false);
        saldo_final.setName("saldo_final"); // NOI18N

        jLabel19.setText("Saldo Inicial:");
        jLabel19.setName("jLabel19"); // NOI18N

        total_entrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        total_entrada.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total_entrada.setEnabled(false);
        total_entrada.setName("total_entrada"); // NOI18N

        jLabel20.setText("Entradas:");
        jLabel20.setName("jLabel20"); // NOI18N

        jLabel21.setText("Saldo Final:");
        jLabel21.setName("jLabel21"); // NOI18N

        total_saida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        total_saida.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        total_saida.setEnabled(false);
        total_saida.setName("total_saida"); // NOI18N

        jLabel22.setText("Saídas:");
        jLabel22.setName("jLabel22"); // NOI18N

        saldo_ini.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        saldo_ini.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        saldo_ini.setEnabled(false);
        saldo_ini.setName("saldo_ini"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(idconta, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesq)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(descricao))
                            .addComponent(observacao)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(idempresa, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dataLancamento, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(idlancamento, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(valor, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnExclui)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancela)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSalva))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saldo_ini, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(total_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(total_saida, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel21)
                                .addGap(2, 2, 2)
                                .addComponent(saldo_final, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idempresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel13)
                    .addComponent(dataLancamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(idlancamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(idconta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesq)
                    .addComponent(descricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(observacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saldo_final, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(total_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(saldo_ini, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total_saida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalva)
                    .addComponent(btnCancela)
                    .addComponent(btnExclui))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExcluiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluiActionPerformed
        controle.exclui(this);
}//GEN-LAST:event_btnExcluiActionPerformed

    private void btnCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelaActionPerformed
        controle.cancela(this);
}//GEN-LAST:event_btnCancelaActionPerformed

    private void btnSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvaActionPerformed
        controle.salva(this);
}//GEN-LAST:event_btnSalvaActionPerformed

    private void btnPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqActionPerformed
        controle.pesquisaConta(this);
    }//GEN-LAST:event_btnPesqActionPerformed

    private void dataLancamentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataLancamentoFocusGained
        SwingUtilities.invokeLater(new ERPSelectAll(dataLancamento));
    }//GEN-LAST:event_dataLancamentoFocusGained

    private void idempresaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idempresaFocusLost
        controle.criticaEmpresa(this);
    }//GEN-LAST:event_idempresaFocusLost

    private void idlancamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idlancamentoFocusLost
        controle.criticaNumero(this);
    }//GEN-LAST:event_idlancamentoFocusLost

    private void JTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTableMouseClicked
        if (evt.getClickCount() > 1) {
            int viewRow = JTable.getSelectedRow();
            int modelRow = JTable.convertRowIndexToModel(viewRow);
            controle.setObjeto(this, ((LancamentoCaixaTableModel) JTable.getModel()).getLinha(modelRow));
        }
    }//GEN-LAST:event_JTableMouseClicked

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        CFrame.fechaVisao(CFrame.VISOES.LANCAMENTO_CAIXA_S);
    }//GEN-LAST:event_formInternalFrameClosed

    private void idlancamentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idlancamentoFocusGained
        controle.enterNumero(this);
        idlancamento.selectAll();
    }//GEN-LAST:event_idlancamentoFocusGained

    private void dataLancamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataLancamentoFocusLost
        ERPData.completaData(dataLancamento);
        controle.criticaData(this);
    }//GEN-LAST:event_dataLancamentoFocusLost

    private void valorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_valorFocusGained
        SwingUtilities.invokeLater(new ERPSelectAll(valor));
    }//GEN-LAST:event_valorFocusGained

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        ERPTable.ajustaCabecalho(JTable);
    }//GEN-LAST:event_formInternalFrameOpened

    private void idcontaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idcontaFocusGained
        idconta.selectAll();
    }//GEN-LAST:event_idcontaFocusGained

    private void idcontaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idcontaFocusLost
        controle.criticaConta(this);
    }//GEN-LAST:event_idcontaFocusLost

    private void observacaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_observacaoFocusGained
        //
    }//GEN-LAST:event_observacaoFocusGained

    private void entradaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_entradaStateChanged
        controle.mudouRadio(this);
    }//GEN-LAST:event_entradaStateChanged

    private void saidaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_saidaStateChanged
        controle.mudouRadio(this);
    }//GEN-LAST:event_saidaStateChanged

    private void valorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_valorKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ADD) {
            entrada.setSelected(true);
            valor.setText(valor.getText().replace("+", ""));
        } else if (evt.getKeyCode() == KeyEvent.VK_SUBTRACT) {
            saida.setSelected(true);
            valor.setText(valor.getText().replace("-", ""));
        }
    }//GEN-LAST:event_valorKeyReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTable;
    private javax.swing.JButton btnCancela;
    private javax.swing.JButton btnExclui;
    private javax.swing.JButton btnPesq;
    private javax.swing.JButton btnSalva;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFormattedTextField dataLancamento;
    private javax.swing.JTextField descricao;
    private javax.swing.JRadioButton entrada;
    private javax.swing.JTextField idconta;
    private javax.swing.JComboBox idempresa;
    private javax.swing.JTextField idlancamento;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField observacao;
    private javax.swing.JRadioButton saida;
    private javax.swing.JFormattedTextField saldo_final;
    private javax.swing.JFormattedTextField saldo_ini;
    private javax.swing.JFormattedTextField total_entrada;
    private javax.swing.JFormattedTextField total_saida;
    private javax.swing.JFormattedTextField valor;
    // End of variables declaration//GEN-END:variables
}