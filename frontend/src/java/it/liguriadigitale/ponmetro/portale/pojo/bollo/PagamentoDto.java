package it.liguriadigitale.ponmetro.portale.pojo.bollo;

import it.liguriadigitale.ponmetro.tassaauto.model.Bollo;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioCalcoloBollo;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import java.io.Serializable;

public class PagamentoDto implements Serializable {

  private static final long serialVersionUID = 8730839547321675934L;
  private Bollo bollo;
  private DettaglioCalcoloBollo dettaglioCalcoloBollo;
  private Veicolo veicolo;

  public PagamentoDto(Bollo bollo, DettaglioCalcoloBollo dettaglioCalcoloBollo, Veicolo veicolo) {
    super();
    this.bollo = bollo;
    this.dettaglioCalcoloBollo = dettaglioCalcoloBollo;
    this.veicolo = veicolo;
  }

  public Bollo getBollo() {
    return bollo;
  }

  public void setBollo(Bollo bollo) {
    this.bollo = bollo;
  }

  public DettaglioCalcoloBollo getDettaglioCalcoloBollo() {
    return dettaglioCalcoloBollo;
  }

  public void setDettaglioCalcoloBollo(DettaglioCalcoloBollo dettaglioCalcoloBollo) {
    this.dettaglioCalcoloBollo = dettaglioCalcoloBollo;
  }

  public Veicolo getVeicolo() {
    return veicolo;
  }

  public void setVeicolo(Veicolo veicolo) {
    this.veicolo = veicolo;
  }
}
