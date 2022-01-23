/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.util;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Arthur
 */
public abstract class TableModelPadrao extends AbstractTableModel {

    protected String[] colunas;
    protected List linhas;

    public TableModelPadrao(List linhas) {
        setColunas(criarColunas());
        this.linhas = linhas;
    }

    protected abstract String[] criarColunas();

    public int getRowCount() {
        if (linhas != null) {
            return linhas.size();
        } else {
            return 0;
        }
    }

    public int getColumnCount() {
        return colunas.length;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Class getColumnClass(int c) {        
        Class returnValue;
        if ((c >= 0) && (c < getColumnCount())) {
            returnValue = linhas == null || linhas.isEmpty() ? Object.class : getValueAt(0, c).getClass();
        } else {
            returnValue = Object.class;
        }
        return returnValue;
    }

    public String getColumnName(int col) {
        return colunas[col];
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }

    public List getLinhas() {
        return linhas;
    }

    public void setLinhas(List linhas) {
        this.linhas = linhas;
        fireTableDataChanged();
    }

    public void removeRow(int row) {
        getLinhas().remove(row);
        // informa a jtable que houve dados deletados passando a
        // linha reovida
        fireTableRowsDeleted(row, row);
    }

    /**
     * Remove a linha pelo valor da coluna informada
     *
     * @param val
     * @param col
     * @return
     */
    public boolean removeRow(String val, int col) {
        // obtem o iterator
        Iterator i = getLinhas().iterator();
        int linha = 0;
        // Faz um looping em cima das linhas
        while (i.hasNext()) {
            // Obtem as colunas da linha atual
            String[] linhaCorrente = (String[]) i.next();
            linha++;
            // compara o conteudo String da linha atual na coluna desejada
            // com o valor informado
            if (linhaCorrente[col].equals(val)) {
                getLinhas().remove(linha);
                // informa a jtable que houve dados deletados passando a
                // linha removida
                fireTableRowsDeleted(linha, linha);
                return true;
            }
        }
        // Nao encontrou nada
        return false;
    }

    public Object getLinha(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    /*
     * Remove todos os registros.
     */
    public void limpar() {
        linhas.clear();
        fireTableDataChanged();
    }

    /*
     * Verifica se este table model esta vazio.
     */
    public boolean isEmpty() {
        return linhas.isEmpty();
    }

    public BigDecimal getSomaCol(int indiceColuna) {
        BigDecimal soma = new BigDecimal(0);

        for (int i = 0; i < this.getRowCount(); i++) {
            if (this.getValueAt(i, indiceColuna).getClass() == BigDecimal.class) {
                soma = soma.add((BigDecimal) this.getValueAt(i, indiceColuna));
            }
        }
        return soma;
    }
}
