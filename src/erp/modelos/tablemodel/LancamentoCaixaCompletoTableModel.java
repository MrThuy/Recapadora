package erp.modelos.tablemodel;

import erp.modelos.banco.LancamentoCaixa;
import erp.util.ERPValor;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 * @author Arthur
 */
public class LancamentoCaixaCompletoTableModel extends TableModelPadrao {

    public LancamentoCaixaCompletoTableModel(List linhas) {
        super(linhas);
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{
                    "Data      ",
                    "Número",
                    "Conta",
                    "Descrição                  ",
                    "Obsercação                 ",
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
                    return objeto.getId().getDataLancamento();
                case 1:
                    return objeto.getId().getIdlancamento();
                case 2:
                    return objeto.getConta().getIdconta();
                case 3:
                    return objeto.getConta().getDescricao();
                case 4:
                    return objeto.getObservacao();
                case 5:
                    if (objeto.getFlagMovimento() == 'E') {
                        return objeto.getValor();
                    } else {
                        return ERPValor.toBigDecimal("0,00");
                    }
                case 6:
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
