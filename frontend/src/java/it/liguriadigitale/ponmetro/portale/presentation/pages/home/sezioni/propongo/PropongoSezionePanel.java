package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.propongo;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.SezionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel.POSIZIONE;
import it.liguriadigitale.ponmetro.portale.presentation.pages.propongo.riepilogo.RiepilogoPropongoPage;
import java.util.List;
import org.apache.wicket.model.Model;

public class PropongoSezionePanel extends SezionePanel {

  private static final long serialVersionUID = -175543562492925506L;

  private static final String SEZIONE_PROPONGO = "ioPropongo";

  public PropongoSezionePanel(String id) {
    super(id);

    log.debug("CP PropongoSezionePanel ");

    DatiSezione sezione = estraiSezioneCorrente(SEZIONE_PROPONGO);
    List<DatiVisualizzazioneSezioneWidget> listaWidget = estraiWidgetSezione(sezione);

    mostraTitolo(
        RiepilogoPropongoPage.class,
        new Model<String>(sezione.getDescrizioneSez()),
        listaWidget.size() > 2);
    fillDati(listaWidget);
  }

  @Override
  protected void addWidget(
      String denominazione, POSIZIONE posizione, DatiVisualizzazioneSezioneWidget widgetTop) {
    StringBuilder builder = getWidgetClasseNameStringBuilder(denominazione);
    log.debug("builder propongo=" + builder.toString());
    add(addWidgetDynamico(builder, posizione));
  }
}
