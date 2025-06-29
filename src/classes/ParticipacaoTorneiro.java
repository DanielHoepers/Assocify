package classes;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "ParticipacaoTorneio")
public class ParticipacaoTorneiro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "torneio_id", nullable = false)
    private Torneio torneio;

    @ManyToOne
    @JoinColumn(name = "associado_id", nullable = false)
    private Associado associado;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "participacao_id")
    private List<Passaro> passaros;

    private boolean certificadoEmitido;
    private int colocacao;

    public ParticipacaoTorneiro() {}

    public ParticipacaoTorneiro(Torneio torneio, Associado associado, List<Passaro> passaros, boolean certificadoEmitido, int colocacao) {
        this.torneio = torneio;
        this.associado = associado;
        this.passaros = passaros;
        this.certificadoEmitido = certificadoEmitido;
        this.colocacao = colocacao;
    }

    public int getId() {
        return id;
    }

    public Torneio getTorneio() {
        return torneio;
    }

    public void setTorneio(Torneio torneio) {
        this.torneio = torneio;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public List<Passaro> getPassaros() {
        return passaros;
    }

    public void setPassaros(List<Passaro> passaros) {
        this.passaros = passaros;
    }

    public boolean isCertificadoEmitido() {
        return certificadoEmitido;
    }

    public void setCertificadoEmitido(boolean certificadoEmitido) {
        this.certificadoEmitido = certificadoEmitido;
    }

    public int getColocacao() {
        return colocacao;
    }

    public void setColocacao(int colocacao) {
        this.colocacao = colocacao;
    }

 
}
