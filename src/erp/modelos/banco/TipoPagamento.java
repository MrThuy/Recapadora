package erp.modelos.banco;
// Generated 24/09/2013 17:05:52 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TipoPagamento generated by hbm2java
 */
public class TipoPagamento  implements java.io.Serializable {


     private Integer idtipopagamento;
     private Usuario usuario;
     private String descricao;
     private Boolean flagAtivo;
     private Date dataInclusao;
     private Boolean flagGerarecibo;
     private Set baixaPagars = new HashSet(0);
     private Set baixaRecebers = new HashSet(0);

    public TipoPagamento() {
    }

	
    public TipoPagamento(String descricao) {
        this.descricao = descricao;
    }
    public TipoPagamento(Usuario usuario, String descricao, Boolean flagAtivo, Date dataInclusao, Boolean flagGerarecibo, Set baixaPagars, Set baixaRecebers) {
       this.usuario = usuario;
       this.descricao = descricao;
       this.flagAtivo = flagAtivo;
       this.dataInclusao = dataInclusao;
       this.flagGerarecibo = flagGerarecibo;
       this.baixaPagars = baixaPagars;
       this.baixaRecebers = baixaRecebers;
    }
   
    public Integer getIdtipopagamento() {
        return this.idtipopagamento;
    }
    
    public void setIdtipopagamento(Integer idtipopagamento) {
        this.idtipopagamento = idtipopagamento;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Boolean getFlagAtivo() {
        return this.flagAtivo;
    }
    
    public void setFlagAtivo(Boolean flagAtivo) {
        this.flagAtivo = flagAtivo;
    }
    public Date getDataInclusao() {
        return this.dataInclusao;
    }
    
    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }
    public Boolean getFlagGerarecibo() {
        return this.flagGerarecibo;
    }
    
    public void setFlagGerarecibo(Boolean flagGerarecibo) {
        this.flagGerarecibo = flagGerarecibo;
    }
    public Set getBaixaPagars() {
        return this.baixaPagars;
    }
    
    public void setBaixaPagars(Set baixaPagars) {
        this.baixaPagars = baixaPagars;
    }
    public Set getBaixaRecebers() {
        return this.baixaRecebers;
    }
    
    public void setBaixaRecebers(Set baixaRecebers) {
        this.baixaRecebers = baixaRecebers;
    }




}


