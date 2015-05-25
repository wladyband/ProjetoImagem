package com.gravar.imagem.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.gravar.imagem.model.Produto;
import com.gravar.imagem.util.HibernateUtil;



public class ProdutoDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Session session;

	public void save(Produto p) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			session.getTransaction().begin();
			session.save(p);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			return session.createCriteria(Produto.class, "p").list();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}
}