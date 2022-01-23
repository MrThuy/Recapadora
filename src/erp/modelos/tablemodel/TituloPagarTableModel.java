package erp.modelos.tablemodel;

import erp.modelos.banco.TituloPagar;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 * @author Arthur
 */
public class TituloPagarTableModel extends TableModelPadrao {

    public TituloPagarTableModel(List linhas) {
        super(linhas);
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{
                    "Número",
                    "Parcela",
                    "Emissão   ",
                    "Vencimento",
                    "Valor       ",
                    "Valor Pago  ",
                    "Tipo de Título              "};
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (linhas == null || linhas.isEmpty()) {
            return null;
        } else {
            TituloPagar objeto = (TituloPagar) linhas.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return objeto.getId().getNumero();
                case 1:
                    return objeto.getId().getParcela();
                case 2:
                    return objeto.getDataEmissao();
                case 3:
                    return objeto.getDataVencimento();
                case 4:
                    return objeto.getValor();
                case 5:
                    return objeto.getValorPago() == null ? 0.00 : objeto.getValorPago();
                case 6:
                    return objeto.getTipoTitulo().getDescricao();
                default:
                    return null;
            }
        }
    }
}
