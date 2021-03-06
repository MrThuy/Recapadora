package erp.modelos.banco;
// Generated 24/09/2013 17:05:52 by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;

/**
 * BaixaPagar generated by hbm2java
 */
public class BaixaPagar  implements java.io.Serializable {


     private BaixaPagarId id;
     private TipoPagamento tipoPagamento;
     private TituloPagar tituloPagar;
     private Usuario usuario;
     private BigDecimal valor;
     private BigDecimal juros;
     private Date dataPagamento;
     private String observacao;
     private Date dataInclusao;

    public BaixaPagar() {
    }

	
    public BaixaPagar(BaixaPagarId id, TituloPagar tituloPagar) {
        this.id = id;
        this.tituloPagar = tituloPagar;
    }
    public BaixaPagar(BaixaPagarId id, TipoPagamento tipoPagamento, TituloPagar tituloPagar, Usuario usuario, BigDecimal valor, BigDecimal juros, Date dataPagamento, String observacao, Date dataInclusao) {
       this.id = id;
       this.tipoPagamento = tipoPagamento;
       this.tituloPagar = tituloPagar;
       this.usuario = usuario;
       this.valor = valor;
       this.juros = juros;
       this.dataPagamento = dataPagamento;
       this.observacao = observacao;
       this.dataInclusao = dataInclusao;
    }
   
    public BaixaPagarId getId() {
        return this.id;
    }
    
    public void setId(BaixaPagarId id) {
        this.id = id;
    }
    public TipoPagamento getTipoPagamento() {
        return this.tipoPagamento;
    }
    
    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
    public TituloPagar getTituloPagar() {
        return this.tituloPagar;
    }
    
    public void setTituloPagar(TituloPagar tituloPagar) {
        this.tituloPagar = tituloPagar;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public BigDecimal getValor() {
        return this.valor;
    }
    
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public BigDecimal getJuros() {
        return this.juros;
    }
    
    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }
    public Date getDataPagamento() {
        return this.dataPagamento;
    }
    
    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
    public String getObservacao() {
        return this.observacao;
    }
    
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    public Date getDataInclusao() {
        return this.dataInclusao;
    }
    
    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }




}


