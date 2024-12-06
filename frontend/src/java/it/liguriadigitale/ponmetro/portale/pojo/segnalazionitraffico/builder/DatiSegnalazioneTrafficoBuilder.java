package it.liguriadigitale.ponmetro.portale.pojo.segnalazionitraffico.builder;

import it.liguriadigitale.ponmetro.portale.pojo.enums.SegnalazioniTrafficoDettaglioTipoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.enums.SegnalazioniTrafficoTipoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.segnalazionitraffico.DatiSegnalazioneTraffico;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateTimeUtil;
import java.time.LocalDateTime;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("unused")
public class DatiSegnalazioneTrafficoBuilder {

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

  public DatiSegnalazioneTrafficoBuilder setIdentificativo(String identificativo) {
    this.identificativo = identificativo;
    return this;
  }

  private LocalDateTime getTimeFromMillisecondi(String time) {
    if (StringUtils.isNotEmpty(time)) {
      String milliseconds = time.substring(time.indexOf("(") + 1);
      milliseconds = milliseconds.substring(0, milliseconds.indexOf(")"));
      // ad un certo punto tornano +0200
      if (milliseconds.contains("+")) {
        milliseconds = milliseconds.substring(0, milliseconds.indexOf("+"));
      }
      return LocalDateTimeUtil.getLocalDateTimeByMilliseconds(milliseconds);
    }
    return null;
  }

  public DatiSegnalazioneTrafficoBuilder setStart(String start) {
    this.start = getTimeFromMillisecondi(start);
    return this;
  }

  public DatiSegnalazioneTrafficoBuilder setEnd(String end) {
    this.end = getTimeFromMillisecondi(end);
    return this;
  }

  public DatiSegnalazioneTrafficoBuilder setLocalita(String localita) {
    this.localita = localita;
    return this;
  }

  public DatiSegnalazioneTrafficoBuilder setTipologia(Integer tipologia) {
    if (tipologia != null) {
      this.tipologia = SegnalazioniTrafficoTipoEnum.valueOf(tipologia);
    } else {
      this.tipologia = SegnalazioniTrafficoTipoEnum.UNKNOWN;
    }
    return this;
  }

  public DatiSegnalazioneTrafficoBuilder setDescrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

  public DatiSegnalazioneTrafficoBuilder setDettaglio(Integer dettaglio) {
    if (dettaglio != null) {
      this.dettaglio = SegnalazioniTrafficoDettaglioTipoEnum.valueOf(dettaglio);
    } else {
      this.dettaglio = SegnalazioniTrafficoDettaglioTipoEnum.UNKNOWN;
    }
    return this;
  }

  public DatiSegnalazioneTrafficoBuilder setLat(Double lat) {
    this.lat = lat;
    return this;
  }

  public DatiSegnalazioneTrafficoBuilder setLng(Double lng) {
    this.lng = lng;
    return this;
  }

  public DatiSegnalazioneTraffico build() {
    DatiSegnalazioneTraffico segnalazione = new DatiSegnalazioneTraffico();
    segnalazione.setIdentificativo(identificativo);
    segnalazione.setStart(start);
    segnalazione.setEnd(end);
    segnalazione.setLocalita(localita);
    segnalazione.setTipologia(tipologia);
    segnalazione.setDescrizione(descrizione);
    segnalazione.setDettaglio(dettaglio);
    segnalazione.setLat(lat);
    segnalazione.setLng(lng);

    return segnalazione;
  }
}
