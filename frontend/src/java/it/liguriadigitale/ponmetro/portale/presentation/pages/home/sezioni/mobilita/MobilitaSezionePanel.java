package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.mobilita;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.SezionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel.POSIZIONE;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.riepilogo.RiepilogoMiMuovoPage;
import java.util.List;
import org.apache.wicket.model.Model;

public class MobilitaSezionePanel extends SezionePanel {

  private static final String IO_MI_MUOVO = "ioMiMuovo";
  private static final long serialVersionUID = -5257491611390757875L;

  public MobilitaSezionePanel(String id) {
    super(id);
    DatiSezione sezione = estraiSezioneCorrente(IO_MI_MUOVO);
    List<DatiVisualizzazioneSezioneWidget> listaWidget = estraiWidgetSezione(sezione);

    log.debug("CP titolo qui = " + sezione.getDescrizioneSez());

    mostraTitolo(
        RiepilogoMiMuovoPage.class,
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
