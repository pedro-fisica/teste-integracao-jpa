package br.com.alura.leilao.dao;

import br.com.alura.leilao.builder.UsuarioBuilder;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDaoTest {

    private EntityManager em;
    private UsuarioDao usuarioDao;

    @BeforeEach
    public void BeforeEach() {
        em = JPAUtil.getEntityManager();
        usuarioDao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void AfterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void buscarPorUsuarioCadastrado() {
        new UsuarioBuilder()
                .comNome("fulano")
                .comEmail("fulano@gmail.com")
                .comPassword("123456")
                .persistir(em);
        Usuario buscado = usuarioDao.buscarPorUsername("fulano");
        Assert.assertNotNull(buscado);

    }

    @Test
    void lancaExcecaoAoBuscarPorUsuarioNaoCadastrado() {
        new UsuarioBuilder()
                .comNome("fulano")
                .comEmail("fulano@gmail.com")
                .comPassword("123456")
                .persistir(em);
        Assert.assertThrows(NoResultException.class, () -> usuarioDao.buscarPorUsername("beltrano"));
    }

    @Test
    void deletaUmUsuario() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("fulano")
                .comEmail("fulano@gmail.com")
                .comPassword("123456")
                .persistir(em);
        usuarioDao.deletar(usuario);
        Assert.assertThrows(NoResultException.class, () -> usuarioDao.buscarPorUsername("fulano"));
    }

}