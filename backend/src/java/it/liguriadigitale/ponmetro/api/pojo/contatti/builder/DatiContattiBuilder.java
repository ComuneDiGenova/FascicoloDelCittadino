package it.liguriadigitale.ponmetro.api.pojo.contatti.builder;

import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente;
import it.liguriadigitale.ponmetro.contattiutente.model.ContattiUtente.TipoEnum;

public class DatiContattiBuilder {

  private Long idFcitt;
  private String contatto;
  private TipoEnum tipo;
  private String dataIns;
  private String dataAgg;

  public DatiContattiBuilder setIdFcitt(Long idFcitt) {
    this.idFcitt = idFcitt;
    return this;
  }

  public DatiContattiBuilder setContatto(String contatto) {
    this.contatto = contatto;
    return this;
  }

  public DatiContattiBuilder setTipo(TipoEnum tipo) {
    this.tipo = tipo;
    return this;
  }

  public DatiContattiBuilder setDataIns(String dataIns) {
    this.dataIns = dataIns;
    return this;
  }

  public DatiContattiBuilder setDataAgg(String dataAgg) {
    this.dataAgg = dataAgg;
    return this;
  }

  public ContattiUtente build() {
    ContattiUtente toRet = new ContattiUtente();

    toRet.setIdFcitt(idFcitt);
    toRet.setContatto(contatto);
    toRet.setTipo(tipo);
    toRet.setDataIns(dataIns);
    toRet.setDataAgg(dataAgg);

    return toRet;
  }
}
