package erp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;

/**
 * @author Arthur
 */
public class ERPData {

    public static String completaData(String data) {
        String novaData = data.replaceAll("/", "").replaceAll("\\.", "");

        Calendar calendar = new GregorianCalendar();
        if (novaData.length() == 2) {
            return (novaData + "/"
                    + ERPString.lpad(String.valueOf(calendar.get(Calendar.MONTH) + 1), "0", 2) + "/"
                    + calendar.get(Calendar.YEAR));
        } else if (novaData.length() == 4) {
            return (novaData.substring(0, 2) + "/"
                    + novaData.substring(2) + "/"
                    + calendar.get(Calendar.YEAR));
        } else if (novaData.length() == 6) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return sdf.format(sdf.parse(novaData.substring(0, 2) + "/"
                        + novaData.substring(2, 4) + "/20"
                        + novaData.substring(4, 6)));
            } catch (Exception ex) {
                return (novaData.substring(0, 2) + "/"
                        + novaData.substring(2, 4) + "/20"
                        + novaData.substring(4));
            }
        } else if (novaData.length() == 8) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return sdf.format(sdf.parse(novaData.substring(0, 2) + "/"
                        + novaData.substring(2, 4) + "/"
                        + novaData.substring(4)));
            } catch (Exception ex) {
                return (novaData.substring(0, 2) + "/"
                        + novaData.substring(2, 4) + "/"
                        + novaData.substring(4));
            }
        } else {
            return data;
        }
    }

    public static void completaData(JFormattedTextField data) {
        data.setText(completaData(data.getText()));
    }

    public static java.util.Date toUtilDate(String data) {
        Date resultado = null;
        if (data.length() == 8) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            try {
                resultado = sdf.parse(data);
            } catch (ParseException ex) {
                Logger.getLogger(ERPData.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (data.length() == 10) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                resultado = sdf.parse(data);
            } catch (ParseException ex) {
            }
        }

        return resultado;
    }

    public static String toString(java.util.Date data) {
        if (data == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(data);
        }
    }

    public static java.sql.Date toSqlDate(String data) {
        try{
            return new java.sql.Date(toUtilDate(data).getTime());
        }catch(Exception e){
            return null;
        }        
    }

    public static java.sql.Date toSqlDate(Date data) {
        return new java.sql.Date(data.getTime());
    }

    public static java.sql.Date agora() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    public static String agoraString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(System.currentTimeMillis());
    }

    public static String somaDias(Date data, int dias) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_MONTH, dias);
        return sdf.format(calendar.getTime());
    }

    public static String somaDias(String data, int dias) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return somaDias(sdf.parse(data), dias);
        } catch (ParseException ex) {
            return data;
        }
    }

    public static String somaMes(Date data, int mes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        calendar.add(Calendar.MONTH, mes);
        return sdf.format(calendar.getTime());
    }

    public static String primeiroDiaMes(Date data) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        return "01/"
                + ERPString.lpad(String.valueOf(calendar.get(Calendar.MONTH) + 1), "0", 2) + "/"
                + calendar.get(Calendar.YEAR);
    }

    public static String primeiroDiaMes(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return primeiroDiaMes(sdf.parse(data));
        } catch (ParseException ex) {
            return data;
        }
    }

    public static String ultimoDiaMes(Date data) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + "/"
                + ERPString.lpad(String.valueOf(calendar.get(Calendar.MONTH) + 1), "0", 2) + "/"
                + calendar.get(Calendar.YEAR);
    }

    public static String ultimoDiaMes(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return ultimoDiaMes(sdf.parse(data));
        } catch (ParseException ex) {
            return data;
        }
    }

    public static String getDiaSemanaDescritivo(Date data) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        String[] diaSemana = new String[]{"Domingo", "Segunda",
            "Terca", "Quarta", "Quinta", "Sexta", "Sabado"};
        return diaSemana[calendar.get(GregorianCalendar.DAY_OF_WEEK) - 1];
    }
}
