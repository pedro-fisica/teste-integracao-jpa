package br.com.alura.leilao.dao;

import br.com.alura.leilao.builder.LeilaoBuilder;
import br.com.alura.leilao.builder.UsuarioBuilder;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

class LeilaoDaoTest {

    private EntityManager em;
    private LeilaoDao leilaoDao;
    private UsuarioDao usuarioDao;

    @BeforeEach
    public void BeforeEach() {
        em = JPAUtil.getEntityManager();
        leilaoDao = new LeilaoDao(em);
        usuarioDao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void AfterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void buscarPorLeilaoCadastrado() {
        Leilao leilao = new LeilaoBuilder()
                .comNome("celular")
                .comValorInicial(new BigDecimal("1000"))
                .comDataAbertura(LocalDate.now())
                .comUsuario(new UsuarioBuilder()
                        .comNome("fulano")
                        .comEmail("fulano@gmail.com")
                        .comPassword("123456")
                        .persistir(em))
                .criar();
        Leilao salvo = leilaoDao.salvar(leilao);
        Leilao buscado = leilaoDao.buscarPorId(salvo.getId());
        Assert.assertNotNull(buscado);
    }

    @Test
    void atualizarLeilao() {
        Leilao leilao = new LeilaoBuilder()
                .comNome("celular")
                .comValorInicial(new BigDecimal("1000"))
                .comDataAbertura(LocalDate.now())
                .comUsuario(new UsuarioBuilder()
                        .comNome("fulano")
                        .comEmail("fulano@gmail.com")
                        .comPassword("123456")
                        .persistir(em))
                .criar();

        Leilao salvo = leilaoDao.salvar(leilao);

        salvo.setValorInicial(new BigDecimal("2000"));
        salvo.setNome("Celular");
        leilaoDao.salvar(salvo);

        Leilao buscado = leilaoDao.buscarPorId(salvo.getId());
        Assert.assertEquals(new BigDecimal("2000"), buscado.getValorInicial());
        Assert.assertEquals("Celular", buscado.getNome());
    }

    private Leilao criarLeilao() {
        Usuario usuario = criarUsuario();
        Leilao leilao = new Leilao("leilao1", new BigDecimal("1000"), LocalDate.now(), usuario);
        leilao = leilaoDao.salvar(leilao);
        return leilao;
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@gmail.com", "1234");
        em.persist(usuario);
        return usuario;
    }

}