package erp.visoes.receber;

import erp.controles.CFrame;
import erp.controles.cadastro.CTipoPagamento;
import erp.controles.cadastro.CTipoTitulo;
import erp.controles.receber.CBaixaReceber;
import erp.modelos.banco.BaixaReceber;
import erp.modelos.tablemodel.BaixaReceberTableModel;
import erp.util.ERPData;
import erp.util.ERPSelectAll;
import erp.util.ERPTable;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * @author Arthur
 */
public class VBaixaReceber extends javax.swing.JInternalFrame {

    CBaixaReceber controle;

    public VBaixaReceber() {
        initComponents();

        controle = new CBaixaReceber();

        CFrame.configuraAtalhoCampo(idcorrentista, btnPesqCliente, KeyEvent.VK_F3, JComponent.WHEN_FOCUSED);

        CFrame.configuraAtalhoCampo(idtipotitulo, btnPesq, KeyEvent.VK_F3, JComponent.WHEN_FOCUSED);
        CFrame.configuraAtalhoCampo(numero, btnPesq, KeyEvent.VK_F3, JComponent.WHEN_FOCUSED);
        CFrame.configuraAtalhoCampo(parcela, btnPesq, KeyEvent.VK_F3, JComponent.WHEN_FOCUSED);

        CFrame.configuraAtalhoBotao(btnSalva, KeyEvent.VK_F5, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.configuraAtalhoBotao(btnCancela, KeyEvent.VK_F8, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.configuraAtalhoBotao(btnExclui, KeyEvent.VK_F9, JComponent.WHEN_IN_FOCUSED_WINDOW);

        CFrame.configuraAtalhoFecharVisao(this, KeyEvent.VK_ESCAPE, JComponent.WHEN_IN_FOCUSED_WINDOW);
        //CFrame.configuraAtalhoMaximizarVisao(this,KeyEvent.VK_F12, JComponent.WHEN_IN_FOCUSED_WINDOW);
                
        CFrame.acaoEnter(btnPesq);
        CFrame.acaoEnter(btnPesqCliente);
        CFrame.acaoEnter(btnSalva);
        CFrame.acaoEnter(btnCancela);
        CFrame.acaoEnter(btnExclui);
        
        idtipotitulo.setModel(CTipoTitulo.comboTipoTituloReceber());
        idtipopagamento.setModel(CTipoPagamento.comboTipoPagamento());

        jTable1.setModel(new BaixaReceberTableModel(null));


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExclui = new javax.swing.JButton();
        btnCancela = new javax.swing.JButton();
        btnSalva = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        idtipopagamento = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        dataPagamento = new javax.swing.JFormattedTextField();
        valor = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        observacao = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        juros = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        idcorrentista = new javax.swing.JTextField();
        btnPesqCliente = new javax.swing.JButton();
        pesquisa = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        idtipotitulo = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        numero = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        parcela = new javax.swing.JTextField();
        btnPesq = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        dataVencimento = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        valortitulo = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        valorPago = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        observacaotitulo = new javax.swing.JTextArea();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Baixa do Receber");
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

        jScrollPane1.setName("jScrollPane1");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setName("jTable1");
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setName("jPanel2");

        jLabel18.setText("Tipo Pagamento:");
        jLabel18.setName("jLabel18");

        idtipopagamento.setName("idtipopagamento");
        idtipopagamento.setNextFocusableComponent(dataPagamento);

        jLabel14.setText("Data Pagamento:");
        jLabel14.setName("jLabel14");

        dataPagamento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        dataPagamento.setName("dataPagamento");
        dataPagamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dataPagamentoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dataPagamentoFocusLost(evt);
            }
        });

        valor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        valor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valor.setName("valor");
        valor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                valorFocusGained(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2");

        observacao.setColumns(15);
        observacao.setRows(2);
        observacao.setTabSize(4);
        observacao.setName("observacao");
        jScrollPane2.setViewportView(observacao);

        jLabel17.setText("Obs.:");
        jLabel17.setName("jLabel17");

        jLabel15.setText("Valor:");
        jLabel15.setName("jLabel15");

        jLabel21.setText("Juros:");
        jLabel21.setName("jLabel21");

        juros.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        juros.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        juros.setName("juros");
        juros.setNextFocusableComponent(observacao);
        juros.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jurosFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(dataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valor, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(juros, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(idtipopagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(idtipopagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(juros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(66, 66, 66))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1");

        jLabel12.setText("Cliente:");
        jLabel12.setName("jLabel12");

        idcorrentista.setName("idcorrentista");
        idcorrentista.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                idcorrentistaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                idcorrentistaFocusLost(evt);
            }
        });

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

        jLabel16.setText("Tipo de Título:");
        jLabel16.setName("jLabel16");

        idtipotitulo.setName("idtipotitulo");
        idtipotitulo.setNextFocusableComponent(numero);
        idtipotitulo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                idtipotituloFocusLost(evt);
            }
        });

        jLabel10.setText("Número:");
        jLabel10.setName("jLabel10"); // NOI18N

        numero.setName("numero");

        jLabel11.setText("Parcela:");
        jLabel11.setName("jLabel11");

        parcela.setName("parcela");
        parcela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                parcelaFocusLost(evt);
            }
        });

        btnPesq.setText("Pesquisa (F3)");
        btnPesq.setFocusable(false);
        btnPesq.setName("btnPesq");
        btnPesq.setRequestFocusEnabled(false);
        btnPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqActionPerformed(evt);
            }
        });

        jLabel19.setText("Vcto.:");
        jLabel19.setName("jLabel19");

        dataVencimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        dataVencimento.setEnabled(false);
        dataVencimento.setName("dataVencimento");

        jLabel20.setText("Valor:");
        jLabel20.setName("jLabel20");

        valortitulo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        valortitulo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valortitulo.setEnabled(false);
        valortitulo.setName("valortitulo");

        jLabel22.setText("Pago:");
        jLabel22.setName("jLabel22");

        valorPago.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        valorPago.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valorPago.setEnabled(false);
        valorPago.setName("valorPago");

        jLabel23.setText("Obs:");
        jLabel23.setName("jLabel23");

        jScrollPane3.setName("jScrollPane3");

        observacaotitulo.setColumns(15);
        observacaotitulo.setLineWrap(true);
        observacaotitulo.setRows(2);
        observacaotitulo.setTabSize(4);
        observacaotitulo.setEnabled(false);
        observacaotitulo.setName("observacaotitulo");
        jScrollPane3.setViewportView(observacaotitulo);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel16)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(idcorrentista, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesqCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisa))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(parcela, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesq))
                            .addComponent(idtipotitulo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(valorPago))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(valortitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dataVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(idcorrentista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesqCliente)
                    .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(idtipotitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(valortitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(dataVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(parcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPesq)
                            .addComponent(jLabel22)
                            .addComponent(valorPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExclui)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancela)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalva))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalva)
                    .addComponent(btnCancela)
                    .addComponent(btnExclui))
                .addGap(27, 27, 27))
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

    private void btnPesqClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqClienteActionPerformed
        controle.pesquisaCliente(this);
    }//GEN-LAST:event_btnPesqClienteActionPerformed

    private void parcelaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_parcelaFocusLost
        controle.criticaParcela(this);
    }//GEN-LAST:event_parcelaFocusLost

    private void btnPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqActionPerformed
        controle.pesquisaNumeroParcela(this);
    }//GEN-LAST:event_btnPesqActionPerformed

    private void idcorrentistaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idcorrentistaFocusLost
        controle.criticaCliente(this);
    }//GEN-LAST:event_idcorrentistaFocusLost

    private void dataPagamentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataPagamentoFocusGained
        if (dataPagamento.getText().replaceAll("/", "").trim().equals("")) {
            dataPagamento.setText(dataVencimento.getText());
        }
        SwingUtilities.invokeLater(new ERPSelectAll(dataPagamento));
    }//GEN-LAST:event_dataPagamentoFocusGained

    private void idtipotituloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idtipotituloFocusLost
        controle.criticaTipoTitulo(this);
    }//GEN-LAST:event_idtipotituloFocusLost

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() > 1) {
            int viewRow = jTable1.getSelectedRow();
            int modelRow = jTable1.convertRowIndexToModel(viewRow);
            controle.setObjeto(this, (BaixaReceber) ((BaixaReceberTableModel) jTable1.getModel()).getLinha(modelRow));
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void valorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_valorFocusGained
        if (valor.getText().replaceAll("\\.", "").replaceAll(",", "").trim().equals("")) {
            valor.setValue(Double.parseDouble(valortitulo.getText().replaceAll("\\.", "").replaceAll(",", "."))
                    - Double.parseDouble(valorPago.getText().replaceAll("\\.", "").replaceAll(",", ".")));
        }
        SwingUtilities.invokeLater(new ERPSelectAll(valor));
    }//GEN-LAST:event_valorFocusGained

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        CFrame.fechaVisao(CFrame.VISOES.BAIXA_RECEBER);
    }//GEN-LAST:event_formInternalFrameClosed

    private void jurosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jurosFocusGained
        SwingUtilities.invokeLater(new ERPSelectAll(juros));
    }//GEN-LAST:event_jurosFocusGained

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        ERPTable.ajustaCabecalho(jTable1);
    }//GEN-LAST:event_formInternalFrameOpened

    private void dataPagamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataPagamentoFocusLost
        ERPData.completaData(dataPagamento);
    }//GEN-LAST:event_dataPagamentoFocusLost

    private void idcorrentistaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_idcorrentistaFocusGained
        idcorrentista.selectAll();
    }//GEN-LAST:event_idcorrentistaFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancela;
    private javax.swing.JButton btnExclui;
    private javax.swing.JButton btnPesq;
    private javax.swing.JButton btnPesqCliente;
    private javax.swing.JButton btnSalva;
    private javax.swing.JFormattedTextField dataPagamento;
    private javax.swing.JFormattedTextField dataVencimento;
    private javax.swing.JTextField idcorrentista;
    private javax.swing.JComboBox idtipopagamento;
    private javax.swing.JComboBox idtipotitulo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JFormattedTextField juros;
    private javax.swing.JTextField numero;
    private javax.swing.JTextArea observacao;
    private javax.swing.JTextArea observacaotitulo;
    private javax.swing.JTextField parcela;
    private javax.swing.JTextField pesquisa;
    private javax.swing.JFormattedTextField valor;
    private javax.swing.JFormattedTextField valorPago;
    private javax.swing.JFormattedTextField valortitulo;
    // End of variables declaration//GEN-END:variables
}
