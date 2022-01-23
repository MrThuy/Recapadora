package erp.modelos;

import erp.modelos.banco.LancamentoCaixa;
import erp.modelos.banco.SaldoCaixa;

/**
 *  @author Arthur
 */
public class FluxoCaixa {


     private SaldoCaixa saldo;
     private LancamentoCaixa lancamentoCaixa;

    public FluxoCaixa() {
    }

    public FluxoCaixa(SaldoCaixa saldo, LancamentoCaixa lancamentoCaixa) {
        this.saldo = saldo;
        this.lancamentoCaixa = lancamentoCaixa;
    }

    public LancamentoCaixa getLancamentoCaixa() {
        return lancamentoCaixa;
    }

    public void setLancamentoCaixa(LancamentoCaixa lancamentoCaixa) {
        this.lancamentoCaixa = lancamentoCaixa;
    }

    public SaldoCaixa getSaldo() {
        return saldo;
    }

    public void setSaldo(SaldoCaixa saldo) {
        this.saldo = saldo;
    }     


}


