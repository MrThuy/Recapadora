package erp.modelos.tablemodel;

import erp.modelos.banco.LancamentoCaixa;
import erp.util.ERPValor;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 * @author Arthur
 */
public class LancamentoCaixaTableModel extends TableModelPadrao {

    public LancamentoCaixaTableModel(List linhas) {
        super(linhas);
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{
                    "Número",
                    "Conta ",
                    "Descrição               ",
                    "Observação              ",
                    "Valor Entrada   ",
                    "Valor Saida     "};

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (linhas == null || linhas.isEmpty()) {
            return null;
        } else {
            LancamentoCaixa objeto = (LancamentoCaixa) linhas.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return objeto.getId().getIdlancamento();
                case 1:
                    return objeto.getConta().getIdconta();
                case 2:
                    return objeto.getConta().getDescricao();
                case 3:
                    return objeto.getObservacao();
                case 4:
                    if (objeto.getFlagMovimento() == 'E') {
                        return objeto.getValor();
                    } else {
                        return ERPValor.toBigDecimal("0,00");
                    }
                case 5:
                    if (objeto.getFlagMovimento() == 'S') {
                        return objeto.getValor();
                    } else {
                        return ERPValor.toBigDecimal("0,00");
                    }
                default:
                    return null;
            }
        }
    }
}
