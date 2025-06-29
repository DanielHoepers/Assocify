package classes;

import enuns.TipoTorneio;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "Torneio")
public class Torneio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String local;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTorneio tipo;

    private boolean finalizado;

    @Column(nullable = false)
    private int limiteParticipantes; // <-- NOVO atributo adicionado

    public Torneio() {}

    public Torneio(String nome, LocalDate data, String local, TipoTorneio tipo, boolean finalizado) {
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.tipo = tipo;
        this.finalizado = finalizado;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public TipoTorneio getTipo() {
        return tipo;
    }

    public void setTipo(TipoTorneio tipo) {
        this.tipo = tipo;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public int getLimiteParticipantes() {
        return limiteParticipantes;
    }

    public void setLimiteParticipantes(int limiteParticipantes) {
        this.limiteParticipantes = limiteParticipantes;
    }
}
