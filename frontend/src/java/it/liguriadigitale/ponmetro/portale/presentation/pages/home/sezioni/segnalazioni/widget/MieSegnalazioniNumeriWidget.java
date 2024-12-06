package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.segnalazioni.widget;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel;
import it.liguriadigitale.ponmetro.segnalazioni.model.Stat;
import it.liguriadigitale.ponmetro.segnalazioni.model.StatDataSeries;
import java.io.IOException;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class MieSegnalazioniNumeriWidget extends MyWidgetPanel {

  private static final long serialVersionUID = 1564385765948746138L;

  public MieSegnalazioniNumeriWidget(POSIZIONE posizione) {
    super(posizione);

    fillDati("");
  }

  @Override
  protected void mostraTestoWidget() {
    this.generateSegnalazioniInviateCounter();
    this.generateSegnalazioniInCaricoCounter();
    this.generateSegnalazioniChiuseCounter();
  }

  @Override
  protected void mostraIcona() {}

  private void generateSegnalazioniInviateCounter() {
    Integer countSegnalazioni = getCountInviate();
    NotEmptyLabel labelSegnalazioniCounter =
        new NotEmptyLabel("segnalazioniInviateCounter", countSegnalazioni);
    addOrReplace(labelSegnalazioniCounter);
  }

  private void generateSegnalazioniInCaricoCounter() {
    Integer countSegnalazioni = getCountInCarico();
    NotEmptyLabel labelSegnalazioniCounter =
        new NotEmptyLabel("segnalazioniInCaricoCounter", countSegnalazioni);
    addOrReplace(labelSegnalazioniCounter);
  }

  private void generateSegnalazioniChiuseCounter() {
    Integer countSegnalazioni = getCountChiuse();
    NotEmptyLabel labelSegnalazioniCounter =
        new NotEmptyLabel("segnalazioniChiuseCounter", countSegnalazioni);
    addOrReplace(labelSegnalazioniCounter);
  }

  private Stat getStats() {
    try {
      Stat stat =
          ServiceLocator.getInstance()
              .getServiziSegnalazioni()
              .getSegnalazioniInviateAperteInCarico(getUtente());
      return stat;
    } catch (BusinessException | ApiException | IOException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  private Integer getCountInviate() {
    Integer count = 0;
    Stat stat = getStats();
    StatDataSeries statDataSeries =
        stat.getData().getSeries().stream()
            .filter(elem -> "Inviata".equalsIgnoreCase(elem.getStatus()))
            .findAny()
            .orElse(null);
    if (statDataSeries != null) {
      count = statDataSeries.getCount();
    }
    return count;
  }

  private Integer getCountInCarico() {
    Integer count = 0;
    Stat stat = getStats();
    StatDataSeries statDataSeries =
        stat.getData().getSeries().stream()
            .filter(elem -> "In carico".equalsIgnoreCase(elem.getStatus()))
            .findAny()
            .orElse(null);
    if (statDataSeries != null) {
      count = statDataSeries.getCount();
    }
    return count;
  }

  private Integer getCountChiuse() {
    Integer count = 0;
    Stat stat = getStats();
    StatDataSeries statDataSeries =
        stat.getData().getSeries().stream()
            .filter(elem -> "Chiusa".equalsIgnoreCase(elem.getStatus()))
            .findAny()
            .orElse(null);
    if (statDataSeries != null) {
      count = statDataSeries.getCount();
    }
    return count;
  }
}
