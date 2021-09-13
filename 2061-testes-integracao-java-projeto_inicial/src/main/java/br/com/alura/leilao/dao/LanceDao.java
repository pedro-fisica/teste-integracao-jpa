package br.com.alura.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;

@Repository
public class LanceDao {

	private EntityManager em;

	@Autowired
	public LanceDao(EntityManager em) {
		this.em = em;
	}

	public void salvar(Lance lance) {
		em.persist(lance);
	}

	public Lance buscarMaiorLanceDoLeilao(long leilaoId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT lance ");
		sb.append("FROM Lance lance ");
		sb.append("WHERE lance.leilao.id = :leilaoId ");
		sb.append("ORDER BY lance.valor DESC ");

		Query query = em.createQuery(sb.toString(), Lance.class);
		query.setMaxResults(1);
		query.setParameter("leilaoId", leilaoId);

		return (Lance) query.getSingleResult();
	}
	
}
