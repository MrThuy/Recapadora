/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.util;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Cliente
 */
public class ERPFrames {

    public static void limparTodosCampos(Container frame) {
        Component components[] = frame.getComponents();
        for (Component component : components) {
            limpaCampo(component);
        }
    }

    public static void limpaCampo(Component component) {
        if (component instanceof JFormattedTextField) {
            JFormattedTextField campo = (JFormattedTextField) component;
            campo.setValue(null);
        } else if (component instanceof JTextField) {
            JTextField campo = (JTextField) component;
            campo.setText("");
        } else if (component instanceof JComboBox) {
            JComboBox campo = (JComboBox) component;
            campo.setSelectedIndex(0);
        } else if (component instanceof JEditorPane) {
            JEditorPane campo = (JEditorPane) component;
            campo.setText("");
        } else if (component instanceof JTextArea) {
            JTextArea campo = (JTextArea) component;
            campo.setText("");
        } else if (component instanceof JCheckBox) {
            JCheckBox campo = (JCheckBox) component;
            campo.setSelected(false);
        } else if (component instanceof Container) {
            limparTodosCampos((Container) component);
        }
    }

    public static Component getComponent(Container frame, String nomeCampo) {
        Component[] componentes = frame.getComponents();

        Component retorno = null;

        for (Component componente : componentes) {

            if (componente.getName() != null) {
                if (componente.getName().equalsIgnoreCase(nomeCampo)) {
                    retorno = componente;
                    break;
                }
            }
            if (componente instanceof Container) {
                retorno = getComponent((Container) componente, nomeCampo);

                if (retorno != null) {
                    break;
                }
            }
        }
        return retorno;
    }

    public static void setValorCampo(Component campo, Object valorCampo) {
        if (campo == null) {
            return;
        }
        if (valorCampo == null) {
            return;
        }

        Class classeValor = valorCampo.getClass();

        String valor = null;

        if (classeValor == String.class) {
            valor = (String) valorCampo;
        } else if (classeValor == Float.class) {
            Float fValor = (Float) valorCampo;
            try {
                valor = fValor.toString().replace('.', ',');
            } catch (Exception e2) {
            }
        } else if (classeValor == float.class) {
            Float fValor = (Float) valorCampo;
            try {
                valor = fValor.toString().replace('.', ',');
            } catch (Exception e2) {
            }
        } else if (classeValor == Double.class) {

            Double dValor = (Double) valorCampo;
            try {
                valor = dValor.toString().replace('.', ',');
            } catch (Exception e2) {
                valor = "0";
            }
        } else if (classeValor == BigDecimal.class) {

            BigDecimal dValor = (BigDecimal) valorCampo;
            try {
                //valor = dValor.toString().replace('.', ',');
                NumberFormat format = new DecimalFormat("#,##0.00");
                valor = format.format(dValor.doubleValue());
            } catch (Exception e2) {
                valor = "0";
            }
        } else if (classeValor == Date.class) {
            Date d = (Date) valorCampo;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                valor = sdf.format(d);
            } catch (Exception e2) {
                valor = null;
            }
        } else if (classeValor == java.sql.Date.class) {
            Date d = (Date) valorCampo;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                valor = sdf.format(d);
            } catch (Exception e2) {
                valor = null;
            }
        } else if (classeValor == Integer.class) {
            valor = valorCampo.toString();
        } else if (classeValor == boolean.class) {
            valor = valorCampo.equals(true) ? "true" : "false";
        } else if (classeValor == Boolean.class) {
            valor = valorCampo.equals(true) ? "true" : "false";
        }

        if (campo instanceof JFormattedTextField) {
            ((JFormattedTextField) campo).setText(valor);
            if ((valor != null) && (!valor.equals(""))) {
                try {
                    ((JFormattedTextField) campo).commitEdit();
                } catch (ParseException ex) {
                    Logger.getLogger(ERPFrames.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (campo instanceof JTextField) {
            ((JTextField) campo).setText(valor);
        } else if (campo instanceof JComboBox) {
            JComboBox comboBox = ((JComboBox) campo);
            for (int i = 0; i < comboBox.getItemCount(); i++) {
                if (comboBox.getItemAt(i) != null) {
                    String item = comboBox.getItemAt(i).toString().split(" - ")[0].trim();
                    if (item.equalsIgnoreCase(valor.toString())) {
                        comboBox.setSelectedIndex(i);
                    }
                }
            }
        } else if (campo instanceof JCheckBox) {
            ((JCheckBox) campo).setSelected(valor.toString().equalsIgnoreCase("true"));
        } else if (campo instanceof JRadioButton) {
            ((JRadioButton) campo).setSelected(valor.toString().equalsIgnoreCase("true"));
        } else if (campo instanceof JTextArea) {
            ((JTextArea) campo).setText(valor);
        }
    }

    public static String getValorCampo(Component campo) {
        if (campo == null) {
            return null;
        } else if (campo instanceof JTextField) {
            return ((JTextField) campo).getText();
        } else if (campo instanceof JFormattedTextField) {
            return ((JFormattedTextField) campo).getText();
        } else if (campo instanceof JComboBox) {
            try {
                String valor = ((JComboBox) campo).getSelectedItem().toString();
                valor = valor.split(" - ")[0].trim();
                return valor;
            } catch (Exception e) {
                return null;
            }
        } else if (campo instanceof JTextArea) {
            return ((JTextArea) campo).getText();
        } else if (campo instanceof JCheckBox) {
            return ((JCheckBox) campo).isSelected() ? "True" : "False";
        } else if (campo instanceof JRadioButton) {
            return ((JRadioButton) campo).isSelected() ? "True" : "False";
        }
        return null;
    }

    public static void frameParaObjeto(JInternalFrame frame, Object objeto) throws Exception {
        if (objeto == null) {
            return;
        }

        Class classeObjeto = objeto.getClass();

//        try {

        Method[] metodos = classeObjeto.getDeclaredMethods();

        for (Method metodo : metodos) {
            if (metodo.getParameterTypes().length == 1) {
                if (metodo.getName().toLowerCase().startsWith("set")) {

                    String nomeDoCampo = metodo.getName().toLowerCase().replaceAll("set", "");
                    String valorNoFrame = getValorCampo(getComponent(frame, nomeDoCampo));

                    if (!(valorNoFrame == null)) {
                        Class c = metodo.getParameterTypes()[0];

                        System.out.println(nomeDoCampo + " " + c + " " + valorNoFrame);

                        if (c == String.class) {
                            metodo.invoke(objeto, valorNoFrame);
                        } else if (c == Double.class) {
                            Double dValor = new Double("0");
                            try {
                                dValor = Double.parseDouble(valorNoFrame.replace(',', '.'));
                            } catch (Exception e2) {
                            }
                            metodo.invoke(objeto, dValor);
                        } else if (c == BigDecimal.class) {
                            BigDecimal fDecimal = new BigDecimal("0");
                            try {
                                valorNoFrame = valorNoFrame.replaceAll("\\.", "");
                                valorNoFrame = valorNoFrame.replaceAll(",", "\\.");
                                fDecimal = BigDecimal.valueOf(new Double(valorNoFrame));
                            } catch (Exception e2) {
                            }
                            metodo.invoke(objeto, fDecimal);
                        } else if (c == Float.class) {
                            Float fValor = new Float("0");
                            try {
                                fValor = Float.parseFloat(valorNoFrame.replace(',', '.'));
                            } catch (Exception e2) {
                            }
                            metodo.invoke(objeto, fValor);
                        } else if (c == float.class) {
                            Float fValor = new Float("0");
                            try {
                                fValor = Float.parseFloat(valorNoFrame.replace(',', '.'));
                            } catch (Exception e2) {
                            }
                            metodo.invoke(objeto, fValor);
                        } else if (c == Date.class) {
                            Date d = null;
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                d = sdf.parse(valorNoFrame);
                            } catch (Exception e2) {
                                d = null;
                            }
                            metodo.invoke(objeto, d);

                        } else if (c == boolean.class) {
                            metodo.invoke(objeto, valorNoFrame.equalsIgnoreCase("true"));
                        } else if (c == Boolean.class) {
                            metodo.invoke(objeto, valorNoFrame.equalsIgnoreCase("true"));
                        } else if (c == int.class) {
                            int i = 0;
                            try {
                                i = Integer.parseInt(valorNoFrame);
                            } catch (Exception e2) {
                                i = 0;
                            }
                            metodo.invoke(objeto, i);
                        } else if (c == Integer.class) {
                            Integer i = null;
                            try {
                                i = Integer.parseInt(valorNoFrame);
                            } catch (Exception e2) {
                                i = null;
                            }
                            metodo.invoke(objeto, i);
                        }
                    }
                }
            }
        }
//        } catch (Throwable throwable) {
//            throw throwable;
//        }
    }

    public static void objetoParaFrame(Container frame, Object objeto) {
        if (objeto == null) {
            return;
        }

        Class classeObjeto = objeto.getClass();

        try {

            Method[] metodos = classeObjeto.getDeclaredMethods();

            for (Method metodo : metodos) {
                if (metodo.getName().toLowerCase().startsWith("get")) {

                    String nomeDoCampo = metodo.getName().toLowerCase().replaceFirst("get", "");

                    Class classeMetodo = metodo.getReturnType();

                    System.out.println(nomeDoCampo + " " + classeMetodo);

                    if (classeMetodo == String.class || classeMetodo == Date.class
                            || classeMetodo == Double.class || classeMetodo == BigDecimal.class
                            || classeMetodo == Float.class || classeMetodo == float.class
                            || classeMetodo == Integer.class || classeMetodo == int.class
                            || classeMetodo == boolean.class || classeMetodo == Boolean.class) {

                        Component componente = getComponent(frame, nomeDoCampo);

                        if (componente != null) {
                            Object resultado = metodo.invoke(objeto);

                            setValorCampo(componente, resultado);
                        }
                    }
                } else if (metodo.getName().toLowerCase().startsWith("is")) {

                    String nomeDoCampo = metodo.getName().toLowerCase().replaceFirst("is", "");

                    Class classeMetodo = metodo.getReturnType();

                    System.out.println(nomeDoCampo + " " + classeMetodo);

                    if (classeMetodo == boolean.class || classeMetodo == Boolean.class) {

                        Component componente = getComponent(frame, nomeDoCampo);

                        if (componente != null) {
                            Object resultado = metodo.invoke(objeto);

                            setValorCampo(componente, resultado);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
    }

    public static String getValorCampoFrame(Container frame, String nomeCampo) {
        return getValorCampo(getComponent(frame, nomeCampo));
    }

    public static void setValorCampoFrame(Container frame, String nomeCampo, Object valor) {
        setValorCampo(getComponent(frame, nomeCampo), valor);
    }

    public static void setFocus(Container frame, String nomeCampo) {
        getComponent(frame, nomeCampo).requestFocus();
    }
}
