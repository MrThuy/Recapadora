/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.controles;

import erp.modelos.banco.Usuario;
import erp.visoes.VBackup;
import erp.visoes.VConfigurações;
import erp.visoes.cadastro.*;
import erp.visoes.caixa.VConsultaLancCaixa;
import erp.visoes.caixa.VFluxoCaixaContabil;
import erp.visoes.caixa.VLancamentoCaixaS;
import erp.visoes.pagar.*;
import erp.visoes.pneu.VConsultaPneu;
import erp.visoes.pneu.VMarca;
import erp.visoes.pneu.VPneu;
import erp.visoes.pneu.VTamanho_Pneu;
import erp.visoes.receber.*;
import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Cliente
 */
public class CFrame {

    private static Usuario usuario;
    private static JFrame mainFrame;
    private static JDesktopPane desktopPane;
   
    static VBackup vBackup;
    static VConfigurações vConfigurações;
    static VCorrentista vCorrentista;
    static VTipoTitulo vTipoTitulo;
    static VTituloPagar vTituloPagar;
    static VTituloReceber vTituloReceber;
    static VTipoPagamento vTipoPagamento;
    static VBaixaPagar vBaixaPagar;
    static VBaixaReceber vBaixaReceber;
    static VDuplicataReceber vDuplicataReceber;
    static VEnvelope vEnvelope;
    static VRecibo vRecibo;
    static VPromissoria vPromissoria;
    static VConsultaReceber vConsultaReceber;
    static VConsultaPagar vConsultaPagar;
    static VConsultaBaixaPagar vConsultaBaixaPagar;
    static VTituloPagarPorDia vTituloPagarPorDia;
    static VConsultaBaixaReceber vConsultaBaixaReceber;
    static VConta vConta;
    static VLancamentoCaixaS vLancamentoCaixaS;
    static VConsultaLancCaixa vConsultaLancCaixa;
    static VFluxoCaixaContabil vFluxoCaixaContabil;
    static VFichaCorrentista vFichaCorrentista;
    static VUniadeMedida vUniadeMedida;
    static VMarca vMarca;
    static VTamanho_Pneu vTamanho_Pneu;
    static VPneu vPneu;
    static VConsultaPneu vConsultaPneu;

    public enum VISOES {

        BACKUP,
        BAIXA_PAGAR,
        BAIXA_RECEBER,
        CONFIGURACOES,
        CONSULTA_BAIXA_PAGAR,
        CONSULTA_BAIXA_RECEBER,
        CONSULTA_LANC_CAIXA,
        CONSULTA_PAGAR,
        CONSULTA_RECEBER,
        CONTA,
        CORRENTISTA,
        DUPLICATA_RECEBER,
        ENVELOPE,
        FICHA_CORRENTISTA,
        FLUXO_CAIXA_CONTABIL,
        LANCAMENTO_CAIXA_S,
        PROMISSORIA,
        RECIBO,
        TIPO_PAGAMENTO,
        TIPO_MOVIMENTO_ESTOQUE,
        TIPO_TITULO,
        TITULO_PAGAR,
        TITULO_PAGAR_POR_DIA,
        TITULO_RECEBER,
        UNIDADE_MEDIDA,
        MARCA,
        TAMANHO_PNEU,
        PNEU, CONSULTA_PNEU
    }

    public static void abreVisao(VISOES visao, JDesktopPane pane) {
        JInternalFrame frame;

        switch (visao) {
            case CONFIGURACOES: {
                if (vConfigurações == null) {
                    vConfigurações = new VConfigurações(null, true);
                }
                vConfigurações.setVisible(true);
                vConfigurações.toFront();
                break;
            }
            case BACKUP: {
                if (vBackup == null) {
                    vBackup = new VBackup(getFrame(), false);
                }
                vBackup.setVisible(true);
                vBackup.toFront();
                break;
            }
            case CORRENTISTA: {
                frame = vCorrentista;
                if (frame == null) {
                    frame = new VCorrentista();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vCorrentista = (VCorrentista) frame;
                break;
            }
            case TIPO_TITULO: {
                frame = vTipoTitulo;
                if (frame == null) {
                    frame = new VTipoTitulo();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vTipoTitulo = (VTipoTitulo) frame;
                break;
            }
            case TITULO_RECEBER: {
                frame = vTituloReceber;
                if (frame == null) {
                    frame = new VTituloReceber();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vTituloReceber = (VTituloReceber) frame;
                break;
            }
            case TIPO_PAGAMENTO: {
                frame = vTipoPagamento;
                if (frame == null) {
                    frame = new VTipoPagamento();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vTipoPagamento = (VTipoPagamento) frame;
                break;
            }
            case BAIXA_RECEBER: {
                frame = vBaixaReceber;
                if (frame == null) {
                    frame = new VBaixaReceber();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vBaixaReceber = (VBaixaReceber) frame;
                break;
            }
            case DUPLICATA_RECEBER: {
                frame = vDuplicataReceber;
                if (frame == null) {
                    frame = new VDuplicataReceber();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vDuplicataReceber = (VDuplicataReceber) frame;
                break;
            }
            case ENVELOPE: {
                frame = vEnvelope;
                if (frame == null) {
                    frame = new VEnvelope();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vEnvelope = (VEnvelope) frame;
                break;
            }
            case RECIBO: {
                frame = vRecibo;
                if (frame == null) {
                    frame = new VRecibo();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vRecibo = (VRecibo) frame;
                break;
            }
            case PROMISSORIA: {
                frame = vPromissoria;
                if (frame == null) {
                    frame = new VPromissoria();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vPromissoria = (VPromissoria) frame;
                break;
            }
            case CONSULTA_RECEBER: {
                frame = vConsultaReceber;
                if (frame == null) {
                    frame = new VConsultaReceber();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vConsultaReceber = (VConsultaReceber) frame;
                break;
            }
            case TITULO_PAGAR: {
                frame = vTituloPagar;
                if (frame == null) {
                    frame = new VTituloPagar();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vTituloPagar = (VTituloPagar) frame;
                break;
            }
            case BAIXA_PAGAR: {
                frame = vBaixaPagar;
                if (frame == null) {
                    frame = new VBaixaPagar();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vBaixaPagar = (VBaixaPagar) frame;
                break;
            }
            case CONSULTA_PAGAR: {
                frame = vConsultaPagar;
                if (frame == null) {
                    frame = new VConsultaPagar();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vConsultaPagar = (VConsultaPagar) frame;
                break;
            }
            case TITULO_PAGAR_POR_DIA: {
                frame = vTituloPagarPorDia;
                if (frame == null) {
                    frame = new VTituloPagarPorDia();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vTituloPagarPorDia = (VTituloPagarPorDia) frame;
                break;
            }
            case CONSULTA_BAIXA_PAGAR: {
                frame = vConsultaBaixaPagar;
                if (frame == null) {
                    frame = new VConsultaBaixaPagar();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vConsultaBaixaPagar = (VConsultaBaixaPagar) frame;
                break;
            }
            case CONSULTA_BAIXA_RECEBER: {
                frame = vConsultaBaixaReceber;
                if (frame == null) {
                    frame = new VConsultaBaixaReceber();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vConsultaBaixaReceber = (VConsultaBaixaReceber) frame;
                break;
            }
            case CONTA: {
                frame = vConta;
                if (frame == null) {
                    frame = new VConta();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vConta = (VConta) frame;
                break;
            }
            case LANCAMENTO_CAIXA_S: {
                frame = vLancamentoCaixaS;
                if (frame == null) {
                    frame = new VLancamentoCaixaS();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vLancamentoCaixaS = (VLancamentoCaixaS) frame;
                break;
            }
            case CONSULTA_LANC_CAIXA: {
                frame = vConsultaLancCaixa;
                if (frame == null) {
                    frame = new VConsultaLancCaixa();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vConsultaLancCaixa = (VConsultaLancCaixa) frame;
                break;
            }
            case FLUXO_CAIXA_CONTABIL: {
                frame = vFluxoCaixaContabil;
                if (frame == null) {
                    frame = new VFluxoCaixaContabil();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vFluxoCaixaContabil = (VFluxoCaixaContabil) frame;
                break;
            }
            case FICHA_CORRENTISTA: {
                frame = vFichaCorrentista;
                if (frame == null) {
                    frame = new VFichaCorrentista();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vFichaCorrentista = (VFichaCorrentista) frame;
                break;
            }
            case UNIDADE_MEDIDA: {
                frame = vUniadeMedida;
                if (frame == null) {
                    frame = new VUniadeMedida();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vUniadeMedida = (VUniadeMedida) frame;
                break;
            }
            case MARCA: {
                frame = vMarca;
                if (frame == null) {
                    frame = new VMarca();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vMarca = (VMarca) frame;
                break;
            }
            case TAMANHO_PNEU: {
                frame = vTamanho_Pneu;
                if (frame == null) {
                    frame = new VTamanho_Pneu();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vTamanho_Pneu = (VTamanho_Pneu) frame;
                break;
            }
            case PNEU: {
                frame = vPneu;
                if (frame == null) {
                    frame = new VPneu();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vPneu = (VPneu) frame;
                break;
            }
            case CONSULTA_PNEU: {
                frame = vConsultaPneu;
                if (frame == null) {
                    frame = new VConsultaPneu();
                    pane.add(frame);
                }
                ajustarVisao(frame);
                vConsultaPneu = (VConsultaPneu) frame;
                break;
            }
        }
    }

    public static void fechaVisao(VISOES visao) {

        switch (visao) {
            case CORRENTISTA: {
                vCorrentista = null;
                break;
            }
            case TIPO_TITULO: {
                vTipoTitulo = null;
                break;
            }
            case TITULO_RECEBER: {
                vTituloReceber = null;
                break;
            }
            case TIPO_PAGAMENTO: {
                vTipoPagamento = null;
                break;
            }
            case BAIXA_RECEBER: {
                vBaixaReceber = null;
                break;
            }
            case DUPLICATA_RECEBER: {
                vDuplicataReceber = null;
                break;
            }
            case ENVELOPE: {
                vEnvelope = null;
                break;
            }
            case RECIBO: {
                vRecibo = null;
                break;
            }
            case PROMISSORIA: {
                vPromissoria = null;
                break;
            }
            case CONSULTA_RECEBER: {
                vConsultaReceber = null;
                break;
            }
            case TITULO_PAGAR: {
                vTituloPagar = null;
                break;
            }
            case BAIXA_PAGAR: {
                vBaixaPagar = null;
                break;
            }
            case CONSULTA_PAGAR: {
                vConsultaPagar = null;
                break;
            }
            case TITULO_PAGAR_POR_DIA: {
                vTituloPagarPorDia = null;
                break;
            }
            case CONSULTA_BAIXA_PAGAR: {
                vConsultaBaixaPagar = null;
                break;
            }
            case CONSULTA_BAIXA_RECEBER: {
                vConsultaBaixaReceber = null;
                break;
            }
            case CONTA: {
                vConta = null;
                break;
            }
            case LANCAMENTO_CAIXA_S: {
                vLancamentoCaixaS = null;
                break;
            }
            case CONSULTA_LANC_CAIXA: {
                vConsultaLancCaixa = null;
                break;
            }
            case FLUXO_CAIXA_CONTABIL: {
                vFluxoCaixaContabil = null;
                break;
            }
            case FICHA_CORRENTISTA: {
                vFichaCorrentista = null;
                break;
            }
            case UNIDADE_MEDIDA: {
                vUniadeMedida = null;
                break;
            }
            case MARCA: {
                vMarca = null;
                break;
            }
            case TAMANHO_PNEU: {
                vTamanho_Pneu = null;
                break;
            }
            case PNEU: {
                vPneu = null;
                break;
            }
            case CONSULTA_PNEU: {
                vConsultaPneu = null;
                break;
            }
        }
    }

    public static void setUsuario(int id) {
        usuario = (new CUsuario()).getUsuario(id);
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static JFrame getFrame() {
        return mainFrame;
    }

    public static void setFrame(JFrame frame) {
        mainFrame = frame;
    }
    
    public static JDesktopPane getDesktopPane() {
        return desktopPane;
    }

    public static void setDesktopPane(JDesktopPane desktopPane) {
        CFrame.desktopPane = desktopPane;
    }    

    public static String getDataHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date());
    }

    static private void ajustarVisao(JInternalFrame frame) {

        frame.setVisible(true);
        frame.toFront();
        Dimension paneSize = frame.getSize();
        Dimension screenSize = frame.getToolkit().getScreenSize();

        frame.setLocation((screenSize.width - paneSize.width) / 2,
                (10));
    }

    public static void configuraAtalhoBotao(final JButton botao, int tecla, int condicicao) {

        configuraAtalhoCampo(botao, botao, tecla, condicicao);

    }

    public static void configuraAtalhoCampo(JComponent componente, final JButton botao, int tecla, int condicicao) {

        Action actionTecla = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                botao.doClick();
            }
        };
        KeyStroke keyStroke = KeyStroke.getKeyStroke(tecla, 0);
        String actionName = "acao";
        InputMap inputMap = componente.getInputMap(condicicao);
        inputMap.put(keyStroke, actionName);
        ActionMap actionMap = componente.getActionMap();
        actionMap.put(actionName, actionTecla);
    }

    public static void configuraAtalhoFecharVisao(final JInternalFrame frame, int tecla, int condicicao) {

        Action actionTecla = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (JOptionPane.showConfirmDialog(frame, "Deseja fechar a tela atual?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    frame.dispose();
                }
            }
        };

        KeyStroke keyStroke = KeyStroke.getKeyStroke(tecla, 0);
        String actionName = "acao";
        InputMap inputMap = frame.getRootPane().getInputMap(condicicao);

        inputMap.put(keyStroke, actionName);
        ActionMap actionMap = frame.getRootPane().getActionMap();

        actionMap.put(actionName, actionTecla);
    }

    public static void configuraAtalhoMaximizarVisao(final JInternalFrame frame, int tecla, int condicicao) {

        Action actionTecla = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (frame.isMaximum()) {
                    try {
                        frame.setMaximum(false);
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(CFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        frame.setMaximum(true);
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(CFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        };

        KeyStroke keyStroke = KeyStroke.getKeyStroke(tecla, 0);
        String actionName = "acao";
        InputMap inputMap = frame.getRootPane().getInputMap(condicicao);
        inputMap.put(keyStroke, actionName);
        ActionMap actionMap = frame.getRootPane().getActionMap();
        actionMap.put(actionName, actionTecla);

    }

    public static void configuraAtalhoFecharFrame(final JFrame frame, int tecla, int condicicao) {

        Action actionTecla = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (JOptionPane.showConfirmDialog(frame, "Deseja fechar o sistema?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    frame.dispose();
                }

            }
        };
        KeyStroke keyStroke = KeyStroke.getKeyStroke(tecla, 0);
        String actionName = "acao";
        InputMap inputMap = frame.getRootPane().getInputMap(condicicao);
        inputMap.put(keyStroke, actionName);
        ActionMap actionMap = frame.getRootPane().getActionMap();
        actionMap.put(actionName, actionTecla);
    }

    public static void acaoEnter(JButton btn) {
        Set<AWTKeyStroke> forwardKeys = new HashSet<>();
        forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
        btn.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
        
        btn.registerKeyboardAction(
                btn.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
                JComponent.WHEN_FOCUSED);

        btn.registerKeyboardAction(
                btn.getActionForKeyStroke(
                KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
                JComponent.WHEN_FOCUSED);
    }
}
