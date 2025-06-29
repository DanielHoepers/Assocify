package service;

import daos.DaoUsuario;
import classes.Usuario;
import enuns.TipoUsuario;

public class AutenticacaoService {

    private DaoUsuario daoUsuario;

    public AutenticacaoService(DaoUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

    public Usuario criarUsuario(String login, String senha, TipoUsuario perfil, boolean ativo) throws Exception {
        if (login == null || login.isEmpty()) throw new Exception("Login obrigatório");
        if (senha == null || senha.length() < 4) throw new Exception("Senha fraca");
        if (perfil == null) throw new Exception("Perfil obrigatório");
        return new Usuario(login, senha, perfil, ativo);
    }

    public boolean salvarUsuario(Usuario usuario) {
        return daoUsuario.Inserir(usuario);
    }

    public boolean salvarObjeto(Usuario usuario) {
        return daoUsuario.Inserir(usuario);
    }

    public boolean editarObjeto(Usuario usuario) {
        return daoUsuario.Editar(usuario);
    }

    public boolean removerObjeto(Usuario usuario) {
        return daoUsuario.Remover(usuario);
    }

    public boolean autenticar(String login, String senha) {
        Usuario usuario = daoUsuario.buscarPorLogin(login);
        return usuario != null && usuario.getSenha() != null && usuario.getSenha().equals(senha);
    }
}
