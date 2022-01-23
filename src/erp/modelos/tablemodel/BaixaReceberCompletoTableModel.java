package erp.modelos.tablemodel;

import erp.controles.cadastro.CCorrentista;
import erp.modelos.banco.BaixaReceber;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 * @author Arthur
 */
public class BaixaReceberCompletoTableModel extends TableModelPadrao {

    public BaixaReceberCompletoTableModel(List linhas) {
        super(linhas);
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{
                    "Fornecedor           ",
                    "Número",
                    "Parcela",
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
            BaixaReceber objeto = (BaixaReceber) linhas.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return CCorrentista.procuraCorrentista(objeto.getId().getIdcorrentista()).getPesquisa();
                case 1:
                    return objeto.getId().getNumero();
                case 2:
                    return objeto.getId().getParcela();
                case 3:
                    return objeto.getDataPagamento();
                case 4:
                    return objeto.getValor();
                case 5:
                    return objeto.getJuros();
                case 6:
                    if (objeto.getTipoPagamento() != null) {
                        return objeto.getTipoPagamento().getDescricao();
                    } else {
                        return "";
                    }
                case 7:
                    return objeto.getObservacao();
                default:
                    return null;
            }
        }
    }
}
