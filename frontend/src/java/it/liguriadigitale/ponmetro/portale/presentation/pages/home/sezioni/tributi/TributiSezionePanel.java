package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.tributi;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.SezionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel.POSIZIONE;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.riepilogo.RiepilogoContribuentePage;
import java.util.List;
import org.apache.wicket.model.Model;

public class TributiSezionePanel extends SezionePanel {

  private static final String IO_CONTRIBUENTE = "IoContribuente";
  private static final long serialVersionUID = -5257491611390757875L;

  public TributiSezionePanel(String id) {
    super(id);
    DatiSezione sezione = estraiSezioneCorrente(IO_CONTRIBUENTE);
    List<DatiVisualizzazioneSezioneWidget> listaWidget = estraiWidgetSezione(sezione);

    mostraTitolo(
        RiepilogoContribuentePage.class,
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
