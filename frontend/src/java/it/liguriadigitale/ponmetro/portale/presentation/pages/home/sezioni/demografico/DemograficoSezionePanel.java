package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.demografico;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.riepilogo.RiepilogoMieiDatiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.SezionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel.POSIZIONE;
import java.util.List;
import org.apache.wicket.model.Model;

public class DemograficoSezionePanel extends SezionePanel {

  private static final String IO_CITTADINO = "ioCittadino";
  private static final long serialVersionUID = 7125438447173293823L;

  public DemograficoSezionePanel(String id) {
    super(id);
    DatiSezione sezione = estraiSezioneCorrente(IO_CITTADINO);
    List<DatiVisualizzazioneSezioneWidget> listaWidget = estraiWidgetSezione(sezione);
    mostraTitolo(
        RiepilogoMieiDatiPage.class, new Model<String>(getNomeUtente()), listaWidget.size() > 2);
    fillDati(listaWidget);
  }

  @Override
  protected void addWidget(
      String denominazione, POSIZIONE posizione, DatiVisualizzazioneSezioneWidget widgetTop) {

    StringBuilder builder = getWidgetClasseNameStringBuilder(denominazione);
    log.debug("builder=" + builder.toString());
    add(addWidgetDynamico(builder, posizione));
  }

  private String getNomeUtente() {
    Utente utente = getUtente();
    return "io " + utente.getNome() + " " + utente.getCognome();
  }
}
