/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.modelos.tablemodel;

import erp.modelos.banco.Correntista;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 *
 * @author Cliente
 */
public class CorrentistaTableModel extends TableModelPadrao {

    public CorrentistaTableModel(List linhas) {
        super(linhas);
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{"Nome                                                       ",
                    "ID",
                    "Telefone            ",
                    "Celular             ",
                    "Cidade                    "};

    }

    public Object getValueAt(int rowIndex, int columnIndex) {

            Correntista objeto = (Correntista) linhas.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return objeto.getPesquisa();
                case 1:
                    return objeto.getIdcorrentista();
                case 2:
                    return objeto.getFone();
                case 3:
                    return objeto.getCelular();
                case 4:
                    return objeto.getCidade();
                default:
                    return null;
            }
    }
}
