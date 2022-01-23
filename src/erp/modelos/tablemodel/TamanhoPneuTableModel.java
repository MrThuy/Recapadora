/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.modelos.tablemodel;

import erp.modelos.banco.TamanhoPneu;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 *
 * @author Cliente
 */
public class TamanhoPneuTableModel extends TableModelPadrao {

    public TamanhoPneuTableModel(List linhas) {
        super(linhas);
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{"Descrição                                                       ",
            "ID"};

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (linhas == null || linhas.isEmpty()) {
            return null;
        } else {
            TamanhoPneu objeto = (TamanhoPneu) linhas.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return objeto.getDescricao();
                case 1:
                    return objeto.getIdtamanhopneu();
                default:
                    return null;
            }
        }
    }
}
