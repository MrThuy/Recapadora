package erp.modelos.tablemodel;

import erp.modelos.banco.Pneu;
import erp.util.TableModelPadrao;
import java.util.List;

/**
 * @author Arthur
 */
public class PneuTableModel extends TableModelPadrao {

    public PneuTableModel(List linhas) {
        super(linhas);
    }

    @Override
    protected String[] criarColunas() {
        return new String[]{
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (linhas == null || linhas.isEmpty()) {
            return null;
        } else {
            Pneu objeto = (Pneu) linhas.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return objeto.getTamanhoPneu().getDescricao();
                case 1:
                    return objeto.getMarca().getDescricao();
                case 2:
                    return objeto.getLona();
                case 3:
                    if(objeto.getStatus() == 'O')
                    return "Original";
                    else
                    return "Recapado";    
                case 4:
                    return objeto.getValorPreco();
                case 5:
                    return objeto.getCodigo();
                case 6:
                    return objeto.getValorVendido()== null ? 0.00 : objeto.getValorVendido();
                case 7:
                    return objeto.getIdpneu();
                default:
                    return null;
            }
        }
    }
}
