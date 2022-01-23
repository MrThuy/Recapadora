/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package erp.util;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;

/**
 *
 * @author Cliente
 */
public class ERPDados {

    public enum OPERACOES {

        INCLUIR,
        ALTERAR,
        ALTERAR_OU_INCLUIR,
        EXCLUIR
    }

    public static ERPResultadoProcesso gravaObjetos(Object[] objetos) throws Exception {
        return executaOperacao(objetos, OPERACOES.ALTERAR_OU_INCLUIR);
    }

    public static ERPResultadoProcesso gravaObjeto(Object objeto) throws Exception {
        return executaOperacao(objeto, OPERACOES.ALTERAR_OU_INCLUIR);
    }

    public static ERPResultadoProcesso atualizaObjeto(Object objeto) throws Exception {
        return executaOperacao(objeto, OPERACOES.ALTERAR);
    }

    public static ERPResultadoProcesso adcionaObjeto(Object objeto) throws Exception {
        return executaOperacao(objeto, OPERACOES.INCLUIR);
    }

    public static ERPResultadoProcesso apagaObjeto(Object objeto) throws Exception {
        return executaOperacao(objeto, OPERACOES.EXCLUIR);
    }

    public static ERPResultadoProcesso executaOperacao(Object[] objetos, OPERACOES operacao) throws Exception {

        Session sessao = HibernateUtil.openSession();
        Transaction transacao = sessao.beginTransaction();

        transacao.begin();

        ERPResultadoProcesso retorno = null;
        retorno = executaOperacao(sessao, objetos, operacao);

        if (retorno.haErros()) {
            retorno.setRetornoPadrao(null);
            transacao.rollback();
        } else {
            transacao.commit();
        }
        sessao.close();

        return retorno;
    }

    public static ERPResultadoProcesso executaOperacao(Object[] objetos, OPERACOES[] operacoes) throws Exception {

        Session sessao = HibernateUtil.openSession();
        Transaction transacao = sessao.beginTransaction();

        transacao.begin();

        ERPResultadoProcesso[] retorno = new ERPResultadoProcesso[objetos.length];

        for (int i = 0; i < objetos.length; i++) {
            retorno[i] = executaOperacao(sessao, objetos[i], operacoes[i]);
        }

        boolean temErros = false;

        for (int i = 0; i < retorno.length; i++) {
            if (retorno[i].haErros()) {
                retorno[1].setRetornoPadrao(null);
                temErros = true;
            }
        }

        if (temErros) {
            transacao.rollback();
        } else {
            transacao.commit();
        }
        sessao.close();

        return retorno[0];
    }

    public static ERPResultadoProcesso executaOperacao(Object objeto, OPERACOES operacao) throws Exception {
        return executaOperacao(new Object[]{objeto}, operacao);
    }

    public static ERPResultadoProcesso executaOperacao(Session sessao, Object objeto, OPERACOES operacao) throws Exception {
        return executaOperacao(sessao, new Object[]{objeto}, operacao);
    }

    public static ERPResultadoProcesso executaOperacao(Session sessao, Object[] objetos, OPERACOES operacao) throws Exception {
        ERPResultadoProcesso ret = new ERPResultadoProcesso();

        try {
            switch (operacao) {
                case ALTERAR: {
                    for (Object objeto : objetos) {
                        sessao.update(objeto);
                    }
                    break;
                }
                case EXCLUIR: {
                    for (Object objeto : objetos) {
                        sessao.delete(objeto);
                    }
                    break;
                }
                case INCLUIR: {
                    for (Object objeto : objetos) {
                        sessao.save(objeto);
                    }
                    break;
                }
                case ALTERAR_OU_INCLUIR: {
                    for (Object objeto : objetos) {
                        sessao.saveOrUpdate(objeto);
                    }
                    break;
                }
            }
        } catch (Exception ex) {
            ret.adicionaErro(ex);
        }

        return ret;
    }

    public static Object consultaObjeto(Class classe, Criterion[] criterios, int maxDados) {
        List l = consultaLista(classe, criterios, maxDados);
        return proximoObjeto(l);
    }

    public static Object consultaObjeto(Class classe, Criterion[] criterios, String[] fetchMode, int maxDados) {
        List l = consultaLista(classe, criterios, fetchMode, maxDados);
        return proximoObjeto(l);
    }

    public static List consultaLista(Class classe, Criterion[] criterios, int maxDados) {
        List retorno = null;
        try {
            Session sessao = HibernateUtil.openSession();

            Criteria criteria = sessao.createCriteria(classe);
            if (maxDados > -1) {
                criteria.setMaxResults(maxDados);
            }

            for (int i = 0; i < criterios.length; i++) {
                criteria.add(criterios[i]);
            }

            retorno = criteria.list();

            sessao.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace().toString());
            retorno = null;
        }

        return retorno;
    }

    public static List consultaLista(Class classe, Criterion[] criterios, String[] fetchMode, int maxDados) {
        List retorno = null;
        try {
            Session sessao = HibernateUtil.openSession();

            Criteria criteria = sessao.createCriteria(classe);
            if (maxDados > -1) {
                criteria.setMaxResults(maxDados);
            }

            for (int i = 0; i < criterios.length; i++) {
                criteria.add(criterios[i]);
            }

            for (int i = 0; i < fetchMode.length; i++) {
                criteria.setFetchMode(fetchMode[i], FetchMode.JOIN);
            }

            retorno = criteria.list();

            sessao.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace().toString());
            retorno = null;
        }

        return retorno;
    }

    public static List consultaLista(Class classe, int maxDados) {
        List retorno = null;
        try {
            Session sessao = HibernateUtil.openSession();

            Criteria criteria = sessao.createCriteria(classe);
            if (maxDados > -1) {
                criteria.setMaxResults(maxDados);
            }

            retorno = criteria.list();

            sessao.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace().toString());
            retorno = null;
        }

        return retorno;
    }

    public static Object proximoObjeto(List l) {
        if (l == null) {
            return null;
        }

        Iterator iterador = l.iterator();

        if (iterador.hasNext()) {
            return iterador.next();
        } else {
            return null;
        }
    }

    public static Object consultaMaxObjeto(Class classe, Criterion[] criterios, String propertyName) {
        Object retorno = null;
        try {
            Session sessao = HibernateUtil.openSession();

            Criteria criteria = sessao.createCriteria(classe);


            for (int i = 0; i < criterios.length; i++) {
                criteria.add(criterios[i]);
            }

            criteria.setProjection(Projections.max(propertyName)).uniqueResult();

            retorno = criteria.uniqueResult();

            sessao.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace().toString());
            retorno = null;
        }

        return retorno;
    }

    public static Object consultaSUMObjeto(Class classe, Criterion[] criterios, String propertyName) {
        Object retorno = null;
        try {
            Session sessao = HibernateUtil.openSession();

            Criteria criteria = sessao.createCriteria(classe);


            for (int i = 0; i < criterios.length; i++) {
                criteria.add(criterios[i]);
            }

            criteria.setProjection(Projections.sum(propertyName)).uniqueResult();

            retorno = criteria.uniqueResult();

            sessao.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace().toString());
            retorno = null;
        }

        return retorno;
    }
}
