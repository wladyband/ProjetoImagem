package com.gravar.imagem.main;

import com.gravar.imagem.util.HibernateUtil;


public class GeraTabela {
	public static void main(String[] args) {
		
		HibernateUtil.getSessionFactory();
		HibernateUtil.getSessionFactory().close();
		
		
	}

}
	