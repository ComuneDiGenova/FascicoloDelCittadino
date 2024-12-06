package it.liguriadigitale.ponmetro.portale.pojo.portale.configurazione;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import java.io.Serializable;

public class WidgetSelezionati implements Serializable {

  private static final long serialVersionUID = -701414416054449603L;

  private DatiSezione sezione;
  private DatiVisualizzazioneSezioneWidget widgetTop;
  private DatiVisualizzazioneSezioneWidget widgetBottom;
  private DatiVisualizzazioneSezioneWidget widgetTopStatoIniziale;
  private DatiVisualizzazioneSezioneWidget widgetBottomStatoIniziale;

  public WidgetSelezionati(DatiSezione sezione) {
    super();
    this.sezione = sezione;
  }

  public DatiSezione getSezione() {
    return sezione;
  }

  public void setSezione(DatiSezione sezione) {
    this.sezione = sezione;
  }

  public DatiVisualizzazioneSezioneWidget getWidgetTop() {
    return widgetTop;
  }

  public void setWidgetTop(DatiVisualizzazioneSezioneWidget widgetTop) {
    this.widgetTop = widgetTop;
  }

  public DatiVisualizzazioneSezioneWidget getWidgetBottom() {
    return widgetBottom;
  }

  public void setWidgetBottom(DatiVisualizzazioneSezioneWidget widgetBottom) {
    this.widgetBottom = widgetBottom;
  }

  public DatiVisualizzazioneSezioneWidget getWidgetTopStatoIniziale() {
    return widgetTopStatoIniziale;
  }

  public void setWidgetTopStatoIniziale(DatiVisualizzazioneSezioneWidget widgetTopStatoIniziale) {
    this.widgetTopStatoIniziale = widgetTopStatoIniziale;
  }

  public DatiVisualizzazioneSezioneWidget getWidgetBottomStatoIniziale() {
    return widgetBottomStatoIniziale;
  }

  public void setWidgetBottomStatoIniziale(
      DatiVisualizzazioneSezioneWidget widgetBottomStatoIniziale) {
    this.widgetBottomStatoIniziale = widgetBottomStatoIniziale;
  }

  @Override
  public String toString() {
    return "WidgetSelezionati [sezione="
        + sezione.getDenominazioneSez()
        + ", widgetTop="
        + widgetTop.getWidget().getDescrizioneWidg()
        + ", widgetBottom="
        + widgetBottom.getWidget().getDescrizioneWidg()
        + ", widgetTopStatoIniziale="
        + widgetTopStatoIniziale.getWidget().getDescrizioneWidg()
        + ", widgetBottomStatoIniziale="
        + widgetBottomStatoIniziale.getWidget().getDescrizioneWidg()
        + "]";
  }
}
