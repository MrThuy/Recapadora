package erp.modelos.tablemodel;

import erp.modelos.SelecionarObjeto;
import erp.modelos.banco.Pneu;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 * @author Arthur
 */
public class PneuSelecionavelTableModel extends TableModelPadrao {

    public PneuSelecionavelTableModel(List linhas) {
        super(linhas);

    }

    @Override
    protected String[] criarColunas() {
        return new String[]{
            "",
            "Tamanho   ",
            "Marca     ",
            "Lona",
            "Status  ",
            "Valor   ",
            "Código  ",
            "Valor Vendido",
            "ID  "};

    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return (col == 0);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (linhas != null && !linhas.isEmpty()) {
            if (columnIndex == 0) {
                SelecionarObjeto objeto = (SelecionarObjeto) linhas.get(rowIndex);
                objeto.setSelecionado((Boolean) aValue);                         
                linhas.set(rowIndex, objeto);
            }
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (linhas == null || linhas.isEmpty()) {
            return null;
        } else {
            SelecionarObjeto objetoSelecionado = (SelecionarObjeto) linhas.get(rowIndex);
            Pneu objeto;
            objeto = (Pneu) objetoSelecionado.getObjeto();
            switch (columnIndex) {
                case 0:
                    return objetoSelecionado.isSelecionado();
                case 1:
                    return objeto.getTamanhoPneu().getDescricao();
                case 2:
                    return objeto.getMarca().getDescricao();
                case 3:
                    return objeto.getLona();
                case 4:
                    if (objeto.getStatus() == 'O') {
                        return "Original";
                    } else {
                        return "Recapado";
                    }
                case 5:
                    return objeto.getValorPreco();
                case 6:
                    return objeto.getCodigo();
                case 7:
                    return objeto.getValorVendido() == null ? 0.00 : objeto.getValorVendido();
                case 8:
                    return objeto.getIdpneu();
                default:
                    return null;
            }
        }
    }
}
