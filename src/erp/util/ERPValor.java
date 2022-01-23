package erp.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.text.NumberFormatter;

/**
 * @author Arthur
 */
public class ERPValor {

    public static BigDecimal toBigDecimal(String valor) {
        valor = valor.replaceAll("\\.", "");
        valor = valor.replaceAll(",", "\\.");
        return BigDecimal.valueOf(new Double(valor));
    }

    public static int toInt(String valor) {
        int i;
        try {
            i = Integer.parseInt(valor);
        } catch (Exception e2) {
            i = 0;
        }
        return i;
    }

    public static String formatBigDecimal(BigDecimal value, String mask) {
        // Para documentação da máscara: http://java.sun.com/j2se/1.4.2/docs/api/java/text/DecimalFormat.html   
        if (value == null) {
            return "";
        }
        try {
            mask = mask.replace(".", ";").replace(",", ".").replace(";", ",");
            NumberFormatter formatter = new NumberFormatter(new DecimalFormat(mask));
            return formatter.valueToString(value);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String formatBigDecimal(BigDecimal value) {
        return formatBigDecimal(value, "###.###.###.##0,00");
    }
}
