package erp.modelos.banco;
// Generated 24/09/2013 17:05:52 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UnidadeMedida generated by hbm2java
 */
public class UnidadeMedida  implements java.io.Serializable {


     private String idUnidade;
     private Usuario usuario;
     private String descricao;
     private Date dataInclusao;
     private Set produtos = new HashSet(0);

    public UnidadeMedida() {
    }

	
    public UnidadeMedida(String idUnidade) {
        this.idUnidade = idUnidade;
    }
    public UnidadeMedida(String idUnidade, Usuario usuario, String descricao, Date dataInclusao, Set produtos) {
       this.idUnidade = idUnidade;
       this.usuario = usuario;
       this.descricao = descricao;
       this.dataInclusao = dataInclusao;
       this.produtos = produtos;
    }
   
    public String getIdUnidade() {
        return this.idUnidade;
    }
    
    public void setIdUnidade(String idUnidade) {
        this.idUnidade = idUnidade;
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
    public Date getDataInclusao() {
        return this.dataInclusao;
    }
    
    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }
    public Set getProdutos() {
        return this.produtos;
    }
    
    public void setProdutos(Set produtos) {
        this.produtos = produtos;
    }




}


