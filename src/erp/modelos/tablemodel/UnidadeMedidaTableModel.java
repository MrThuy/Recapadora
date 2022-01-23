package erp.modelos.tablemodel;

import erp.modelos.banco.UnidadeMedida;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 * @author Arthur
 */
public class UnidadeMedidaTableModel extends TableModelPadrao {

    public UnidadeMedidaTableModel(List linhas) {
        super(linhas);
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{"Sigla ",
                    "Descrição                                                    "};

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (linhas == null || linhas.isEmpty()) {
            return null;
        } else {
            UnidadeMedida objeto = (UnidadeMedida) linhas.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return objeto.getIdUnidade();
                case 1:
                    return objeto.getDescricao();
                default:
                    return null;
            }
        }
    }
}
