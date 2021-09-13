package br.com.alura.leilao.dao;

import br.com.alura.leilao.builder.LanceBuilder;
import br.com.alura.leilao.builder.LeilaoBuilder;
import br.com.alura.leilao.builder.UsuarioBuilder;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.math.BigDecimal;
import java.time.LocalDate;

class LanceDaoTest {

    private EntityManager em;
    private LanceDao lanceDao;
    private LeilaoDao leilaoDao;
    private UsuarioDao usuarioDao;

    @BeforeEach
    public void BeforeEach() {
        em = JPAUtil.getEntityManager();
        lanceDao = new LanceDao(em);
        leilaoDao = new LeilaoDao(em);
        usuarioDao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void AfterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void naoBuscarLanceDoLeilao() {
        Usuario usuario = getUsuario();
        Leilao leilao = getLeilao(usuario);

        Assert.assertThrows(NoResultException.class, () -> lanceDao.buscarMaiorLanceDoLeilao(leilao.getId()));
    }

    @Test
    void buscarUnicoLanceDoLeilao() {
        Usuario usuario = getUsuario();
        Leilao leilao = getLeilao(usuario);
        new LanceBuilder()
                .comValor(new BigDecimal("2000"))
                .comUsuario(usuario)
                .comLeilao(leilao)
                .persistir(em);

        Lance buscado = lanceDao.buscarMaiorLanceDoLeilao(leilao.getId());
        Assert.assertEquals(buscado.getValor(), new BigDecimal("2000"));
    }

    @Test
    void buscarMaiorLanceDoLeilao() {
        Usuario usuario = getUsuario();
        Leilao leilao = getLeilao(usuario);
        new LanceBuilder()
                .comValor(new BigDecimal("2000"))
                .comUsuario(usuario)
                .comLeilao(leilao)
                .persistir(em);

        new LanceBuilder()
                .comValor(new BigDecimal("2500"))
                .comUsuario(usuario)
                .comLeilao(leilao)
                .persistir(em);

        Lance buscado = lanceDao.buscarMaiorLanceDoLeilao(leilao.getId());
        Assert.assertEquals(buscado.getValor(), new BigDecimal("2500"));
    }

    private Leilao getLeilao(Usuario usuario) {
        Leilao leilao = new LeilaoBuilder()
                .comNome("celular")
                .comValorInicial(new BigDecimal("1000"))
                .comDataAbertura(LocalDate.now())
                .comUsuario(usuario)
                .persistir(em);
        return leilao;
    }

    private Usuario getUsuario() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("fulano")
                .comEmail("fulano@gmail.com")
                .comPassword("123456")
                .persistir(em);
        return usuario;
    }

}
