package erp.modelos.tablemodel;

import erp.modelos.banco.BaixaPagar;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 * @author Arthur
 */
public class BaixaPagarTableModel extends TableModelPadrao {

    public BaixaPagarTableModel(List linhas) {
        super(linhas);
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{
                    "Data Pagamento",
                    "Valor Pago",
                    "Juros   ",
                    "Tipo Pagamento    ",
                    "Observação                               "};

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (linhas == null || linhas.isEmpty()) {
            return null;
        } else {
            BaixaPagar objeto = (BaixaPagar) linhas.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return objeto.getDataPagamento();
                case 1:
                    return objeto.getValor();
                case 2:
                    return objeto.getJuros();
                case 3:
                    if (objeto.getTipoPagamento() != null) {
                        return objeto.getTipoPagamento().getDescricao();
                    } else {
                        return "";
                    }
                case 4:
                    return objeto.getObservacao();
                default:
                    return null;
            }
        }
    }
}
