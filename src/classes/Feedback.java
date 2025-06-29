package classes;

import enuns.StatusFeedback;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "Feedback")
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "associado_id", nullable = false)
    private Associado associado;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "torneio_id", nullable = true)
    private Torneio torneio;

    @Column(nullable = false)
    private String comentario;

    @Column(nullable = false)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusFeedback status;

    public Feedback() {}

    public Feedback(Associado associado, Evento evento, Torneio torneio, String comentario) {
        this.associado = associado;
        this.evento = evento;
        this.torneio = torneio;
        this.comentario = comentario;
        this.data = LocalDate.now();
        this.status = StatusFeedback.Enviado;
    }

    public int getId() {
        return id;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Torneio getTorneio() {
        return torneio;
    }

    public void setTorneio(Torneio torneio) {
        this.torneio = torneio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public StatusFeedback getStatus() {
        return status;
    }

    public void setStatus(StatusFeedback status) {
        this.status = status;
    }
}
