package erp.modelos.tablemodel;

import erp.modelos.banco.TituloReceber;
import erp.util.ERPData;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 * @author Arthur
 */
public class TituloReceberCompletoTableModel extends TableModelPadrao {

    public TituloReceberCompletoTableModel(List linhas) {
        super(linhas);
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{
                    "Número",
                    "Parcela",
                    "Cliente              ",
                    "Emissão   ",
                    "Vencimento",
                    "Valor     ",
                    "Valor Pago",
                    "Data do Pgto",
                    "Tipo de Título      ",
                    "Financeira      "};

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (linhas == null || linhas.isEmpty()) {
            return null;
        } else {
            TituloReceber objeto = (TituloReceber) linhas.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return objeto.getId().getNumero();
                case 1:
                    return objeto.getId().getParcela();
                case 2:
                    return objeto.getCorrentista().getPesquisa();
                case 3:
                    return objeto.getDataEmissao();
                case 4:
                    return objeto.getDataVencimento();
                case 5:
                    return objeto.getValor();
                case 6:
                    return objeto.getValorPago() == null ? 0.00 : objeto.getValorPago();
                case 7:
                    return ERPData.toString(objeto.getDataPagamento());
                case 8:
                    return objeto.getTipoTitulo().getDescricao();
                case 9:
                    if (objeto.getIdfinanceira() == null) {
                        return "";
                    } else {
                        return objeto.getIdfinanceira().getPesquisa();
                    }
                default:
                    return null;
            }
        }
    }
}
