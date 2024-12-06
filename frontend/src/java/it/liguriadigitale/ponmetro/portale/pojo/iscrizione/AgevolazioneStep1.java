package it.liguriadigitale.ponmetro.portale.pojo.iscrizione;

import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCF200;
import it.liguriadigitale.ponmetro.portale.pojo.portale.AgevolazioneTariffariaRistorazione;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria.AccettazioneNucleoIseeAnagraficoEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.Serializable;
import java.util.List;

public class AgevolazioneStep1 implements Serializable {

  private static final long serialVersionUID = -4277033902090228116L;

  private Integer annoScolastico;
  private boolean iseePresentato;
  private UtenteServiziRistorazione iscrittoSelezionato;
  private List<AgevolazioneTariffariaRistorazione> listaFigliPerRichiesta;

  private ConsultazioneAttestazioneCF200 attestazioneIseeModi;
  private ConsultazioneDichiarazioneCF200 dichiarazioneIseeModi;

  private AccettazioneNucleoIseeAnagraficoEnum accettazioneNuclei;

  private String accettazioneNucleiSiNo;

  public Integer getAnnoScolastico() {
    return annoScolastico;
  }

  public void setAnnoScolastico(Integer annoScolastico) {
    this.annoScolastico = annoScolastico;
  }

  public boolean isIseePresentato() {
    return iseePresentato;
  }

  public void setIseePresentato(boolean iseePresentato) {
    this.iseePresentato = iseePresentato;
  }

  public UtenteServiziRistorazione getIscrittoSelezionato() {
    return iscrittoSelezionato;
  }

  public void setIscrittoSelezionato(UtenteServiziRistorazione iscrittoSelezionato) {
    this.iscrittoSelezionato = iscrittoSelezionato;
  }

  public List<AgevolazioneTariffariaRistorazione> getListaFigliPerRichiesta() {
    return listaFigliPerRichiesta;
  }

  public void setListaFigliPerRichiesta(
      List<AgevolazioneTariffariaRistorazione> listaFigliPerRichiesta) {
    this.listaFigliPerRichiesta = listaFigliPerRichiesta;
  }

  public ConsultazioneAttestazioneCF200 getAttestazioneIseeModi() {
    return attestazioneIseeModi;
  }

  public void setAttestazioneIseeModi(ConsultazioneAttestazioneCF200 attestazioneIseeModi) {
    this.attestazioneIseeModi = attestazioneIseeModi;
  }

  public ConsultazioneDichiarazioneCF200 getDichiarazioneIseeModi() {
    return dichiarazioneIseeModi;
  }

  public void setDichiarazioneIseeModi(ConsultazioneDichiarazioneCF200 dichiarazioneIseeModi) {
    this.dichiarazioneIseeModi = dichiarazioneIseeModi;
  }

  public AccettazioneNucleoIseeAnagraficoEnum getAccettazioneNuclei() {
    return accettazioneNuclei;
  }

  public void setAccettazioneNuclei(AccettazioneNucleoIseeAnagraficoEnum accettazioneNuclei) {
    this.accettazioneNuclei = accettazioneNuclei;
  }

  public String getAccettazioneNucleiSiNo() {
    return accettazioneNucleiSiNo;
  }

  public void setAccettazioneNucleiSiNo(String accettazioneNucleiSiNo) {
    this.accettazioneNucleiSiNo = accettazioneNucleiSiNo;
  }

  @Override
  public String toString() {
    return "AgevolazioneStep1 [annoScolastico="
        + annoScolastico
        + ", iseePresentato="
        + iseePresentato
        + ", iscrittoSelezionato="
        + iscrittoSelezionato
        + ", listaFigliPerRichiesta="
        + listaFigliPerRichiesta
        + ", attestazioneIseeModi="
        + attestazioneIseeModi
        + ", dichiarazioneIseeModi="
        + dichiarazioneIseeModi
        + ", accettazioneNuclei="
        + accettazioneNuclei
        + ", accettazioneNucleiSiNo="
        + accettazioneNucleiSiNo
        + "]";
  }
}
