package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.scolastici;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.RiepilogoIoGenitorePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.SezionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel.POSIZIONE;
import java.util.List;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;

public class RistorazioneSezionePanel extends SezionePanel {

  private static final String IO_GENITORE = "ioGenitore";
  private static final long serialVersionUID = -4383547572947053427L;

  public RistorazioneSezionePanel(String id) {
    super(id);

    DatiSezione sezione = estraiSezioneCorrente(IO_GENITORE);
    List<DatiVisualizzazioneSezioneWidget> listaWidget = estraiWidgetSezione(sezione);
    // mostraTitolo(RiepilogoIscrizioniPage.class, new
    // Model<String>(sezione.getDescrizioneSez()));

    mostraTitolo(
        RiepilogoIoGenitorePage.class,
        new Model<String>(sezione.getDescrizioneSez()),
        listaWidget.size() > 2);

    fillDati(listaWidget);
    Utente utente = getUtente();
    utente.aggiornaStatoFigli();
    if (getUtente().getListaFigli().isEmpty()) {
      this.setVisible(false);
    }
  }

  @Override
  protected void addWidget(
      String denominazione, POSIZIONE posizione, DatiVisualizzazioneSezioneWidget widgetTop) {
    StringBuilder builder = getWidgetClasseNameStringBuilder(denominazione);
    log.debug("builder=" + builder.toString());
    if (getUtente().getListaFigli().isEmpty()) {
      EmptyPanel panel = new EmptyPanel(posizione.getWicketId());
      add(panel);
    } else {
      add(addWidgetDynamico(builder, posizione));
    }
  }
}
