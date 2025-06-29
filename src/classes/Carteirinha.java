package classes;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "Carteirinha")
public class Carteirinha implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "associado_id", referencedColumnName = "id")
    private Associado associado;

    @Column(nullable = false)
    private String codigoSocio;

    private String filiacao;
    private LocalDate dataEmissao;

    @Column(nullable = false)
    private LocalDate validade;

    @Column(nullable = false)
    private String nomeAssociacao;

    @Column(nullable = false)
    private String cnpjAssociacao;

    public Carteirinha() {}

    public Carteirinha(Associado associado, String codigoSocio, String filiacao, LocalDate validade, String nomeAssociacao, String cnpjAssociacao) {
        this.associado = associado;
        this.codigoSocio = codigoSocio;
        this.filiacao = filiacao;
        this.dataEmissao = LocalDate.now();
        this.validade = validade;
        this.nomeAssociacao = nomeAssociacao;
        this.cnpjAssociacao = cnpjAssociacao;
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

    public String getCodigoSocio() {
        return codigoSocio;
    }

    public void setCodigoSocio(String codigoSocio) {
        this.codigoSocio = codigoSocio;
    }

    public String getFiliacao() {
        return filiacao;
    }

    public void setFiliacao(String filiacao) {
        this.filiacao = filiacao;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public String getNomeAssociacao() {
        return nomeAssociacao;
    }

    public void setNomeAssociacao(String nomeAssociacao) {
        this.nomeAssociacao = nomeAssociacao;
    }

    public String getCnpjAssociacao() {
        return cnpjAssociacao;
    }

    public void setCnpjAssociacao(String cnpjAssociacao) {
        this.cnpjAssociacao = cnpjAssociacao;
    }

  
}
