/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.modelos.tablemodel;

import erp.modelos.banco.TipoTitulo;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 *
 * @author Cliente
 */
public class TipoTituloTableModel extends TableModelPadrao {

    public TipoTituloTableModel(List linhas) {
        super(linhas);
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{"Descrição                                                       ",
                    "ID",
                    "Receber",
                    "Pagar"};

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (linhas == null || linhas.isEmpty()) {
            return null;
        } else {
            TipoTitulo objeto = (TipoTitulo) linhas.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return objeto.getDescricao();
                case 1:
                    return objeto.getIdtipotitulo();
                case 2:
                    return objeto.getFlagReceber();
                case 3:
                    return objeto.getFlagPagar();
                default:
                    return null;
            }
        }
    }
}
