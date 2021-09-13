package br.com.alura.leilao.builder;

import br.com.alura.leilao.model.Usuario;

import javax.persistence.EntityManager;

public class UsuarioBuilder {

    private String nome;
    private String email;
    private String password;

    public UsuarioBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public UsuarioBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public UsuarioBuilder comPassword(String password) {
        this.password = password;
        return this;
    }

    public Usuario criar() {
        return new Usuario(nome, email, password);
    }

    public Usuario persistir(EntityManager em) {
        Usuario usuario = criar();
        em.persist(usuario);
        return usuario;
    }

}
