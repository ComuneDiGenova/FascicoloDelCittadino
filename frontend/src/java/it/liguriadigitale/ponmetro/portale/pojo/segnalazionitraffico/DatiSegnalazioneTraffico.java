package it.liguriadigitale.ponmetro.portale.pojo.segnalazionitraffico;

import it.liguriadigitale.ponmetro.portale.pojo.enums.SegnalazioniTrafficoDettaglioTipoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.enums.SegnalazioniTrafficoTipoEnum;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DatiSegnalazioneTraffico implements Serializable {

  private static final long serialVersionUID = 4699059822261623083L;

  private Log log = LogFactory.getLog(getClass());

  private String identificativo;
  private LocalDateTime start;
  private LocalDateTime end;
  private String localita;
  private SegnalazioniTrafficoTipoEnum tipologia;
  private String descrizione;
  private SegnalazioniTrafficoDettaglioTipoEnum dettaglio;
  private Double lat;
  private Double lng;

  public String getIdentificativo() {
    return identificativo;
  }

  public void setIdentificativo(String identificativo) {
    this.identificativo = identificativo;
  }

  public LocalDateTime getStart() {
    return start;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }

  public LocalDateTime getEnd() {
    return end;
  }

  public void setEnd(LocalDateTime end) {
    this.end = end;
  }

  public String getLocalita() {
    return localita;
  }

  public void setLocalita(String localita) {
    this.localita = localita;
  }

  public SegnalazioniTrafficoTipoEnum getTipologia() {
    return tipologia;
  }

  public String getStringTipologia() {
    return getTipologia().toString();
  }

  public void setTipologia(SegnalazioniTrafficoTipoEnum tipologia) {
    this.tipologia = tipologia;
  }

  public String getDefaultStringTipologia() {
    return SegnalazioniTrafficoTipoEnum.getStringUnknown();
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public SegnalazioniTrafficoDettaglioTipoEnum getDettaglio() {
    return dettaglio;
  }

  public String getStringDettaglio() {
    return getDettaglio().toString();
  }

  public void setDettaglio(SegnalazioniTrafficoDettaglioTipoEnum dettaglio) {
    this.dettaglio = dettaglio;
  }

  public String getDefaultStringDettaglio() {
    return SegnalazioniTrafficoDettaglioTipoEnum.getStringUnknown();
  }

  public Double getLat() {
    return lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }

  public Double getLng() {
    return lng;
  }

  public void setLng(Double lng) {
    this.lng = lng;
  }

  @Override
  public String toString() {
    return "DatiSegnalazioneTraffico [log="
        + log
        + ", identificativo="
        + identificativo
        + ", start="
        + start
        + ", end="
        + end
        + ", localita="
        + localita
        + ", tipologia="
        + tipologia
        + ", descrizione="
        + descrizione
        + ", dettaglio="
        + dettaglio
        + ", lat="
        + lat
        + ", lng="
        + lng
        + "]";
  }
}
