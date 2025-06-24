package br.com.gstok.hibernate;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CRUD<T> {

    private static Session sessao;
    private static Transaction transacao;
    private List<T> lista;

    public void salvar(Object objeto) {

        try {

            sessao = TransactionUtil.obterSessao();
            transacao = TransactionUtil.iniciarTransacao(sessao);
            sessao.save(objeto);
            transacao.commit();

        } catch (HibernateException e) {

            transacao.rollback();
            System.err.println("Ocorreu um erro ao salvar o objeto.\n" + e.fillInStackTrace());
        } finally {

            TransactionUtil.fecharSessao(sessao);
        }
    }

    public void atualizar(Object objeto) {

        try {

            sessao = TransactionUtil.obterSessao();
            transacao = TransactionUtil.iniciarTransacao(sessao);
            sessao.update(objeto);
            transacao.commit();

        } catch (HibernateException e) {

            transacao.rollback();
            System.err.println("Ocorreu um erro ao atualizar o objeto.\n" + e.fillInStackTrace());
        } finally {

            TransactionUtil.fecharSessao(sessao);
        }
    }

    public List<T> listarTudo(Class classe) {

        try {

            sessao = TransactionUtil.obterSessao();
            transacao = TransactionUtil.iniciarTransacao(sessao);
            lista = sessao.createCriteria(classe).list();
            transacao.commit();
        } catch (HibernateException e) {

            transacao.rollback();
            System.err.println("Ocorreu um erro ao localizar o objeto.\n" + e.fillInStackTrace());
        } finally {

            TransactionUtil.fecharSessao(sessao);
            return lista;
        }
    }

    public void deletar(Object objeto) {

        try {

            sessao = TransactionUtil.obterSessao();
            transacao = TransactionUtil.iniciarTransacao(sessao);
            sessao.delete(objeto);
            transacao.commit();

        } catch (HibernateException e) {

            transacao.rollback();
            System.err.println("Ocorreu um erro ao deletar o objeto.\n" + e.fillInStackTrace());
        } finally {
            TransactionUtil.fecharSessao(sessao);

        }
    }
}
