package erp.util;

import java.awt.FontMetrics;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author Arthur
 */
public class ERPTable {

    public static void ajustaCabecalho(JTable table) {
        TableModel modelo = table.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelo);

        table.setRowSorter(sorter);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        FontMetrics fm = table.getGraphics().getFontMetrics();       

        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnName(i);
            TableColumn col = table.getColumnModel().getColumn(i);            
            col.setMinWidth(fm.stringWidth(columnName) + 20);
        }
    }
}
