package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.segnalazioni;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.SezionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel.POSIZIONE;
import it.liguriadigitale.ponmetro.portale.presentation.pages.segnalazioni.riepilogo.RiepilogoSegnalazioniPage;
import java.util.List;
import org.apache.wicket.model.Model;

public class SegnalazioniSezionePanel extends SezionePanel {

  private static final long serialVersionUID = 190152629492538153L;

  private static final String SEZIONE_SEGNALAZIONI = "ioSegnalo";

  public SegnalazioniSezionePanel(String id) {
    super(id);

    DatiSezione sezione = estraiSezioneCorrente(SEZIONE_SEGNALAZIONI);
    List<DatiVisualizzazioneSezioneWidget> listaWidget = estraiWidgetSezione(sezione);

    mostraTitolo(
        RiepilogoSegnalazioniPage.class,
        new Model<String>(sezione.getDescrizioneSez()),
        listaWidget.size() > 2);
    fillDati(listaWidget);
  }

  @Override
  protected void addWidget(
      String denominazione, POSIZIONE posizione, DatiVisualizzazioneSezioneWidget widgetTop) {
    StringBuilder builder = getWidgetClasseNameStringBuilder(denominazione);
    log.debug("builder=" + builder.toString());
    add(addWidgetDynamico(builder, posizione));
  }
}
