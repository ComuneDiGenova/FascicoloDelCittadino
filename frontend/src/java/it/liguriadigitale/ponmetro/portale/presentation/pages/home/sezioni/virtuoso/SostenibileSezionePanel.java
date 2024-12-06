package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.virtuoso;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.SezionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel.POSIZIONE;
import it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.riepilogo.RiepilogoIoSostenibilePage;
import java.util.List;
import org.apache.wicket.model.Model;

public class SostenibileSezionePanel extends SezionePanel {

  private static final long serialVersionUID = -343244557149148232L;

  private static final String IO_SOSTENIBILE = "ioSostenibile";

  public SostenibileSezionePanel(String id) {
    super(id);

    DatiSezione sezione = estraiSezioneCorrente(IO_SOSTENIBILE);
    List<DatiVisualizzazioneSezioneWidget> listaWidget = estraiWidgetSezione(sezione);

    mostraTitolo(
        RiepilogoIoSostenibilePage.class,
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
