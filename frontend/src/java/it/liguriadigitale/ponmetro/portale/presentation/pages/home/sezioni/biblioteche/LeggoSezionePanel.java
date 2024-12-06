package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.biblioteche;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.riepilogo.RiepilogoBibliotechePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.SezionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel.POSIZIONE;
import java.util.List;
import org.apache.wicket.model.Model;

public class LeggoSezionePanel extends SezionePanel {

  private static final long serialVersionUID = 6938330984799609079L;

  private static final String IO_LEGGO = "ioLeggo";
  private static final long ID_SEZIONE_IO_LEGGO = 7L;

  public LeggoSezionePanel(String id) {
    super(id);
    DatiSezione sezione = estraiSezioneCorrente(IO_LEGGO);
    List<DatiVisualizzazioneSezioneWidget> listaWidget = estraiWidgetSezione(sezione);

    log.debug("CP lista widget io leggo = " + listaWidget);

    mostraTitolo(
        RiepilogoBibliotechePage.class,
        new Model<String>(sezione.getDescrizioneSez()),
        isVisualizzabileVaiTuttiIServizi(listaWidget));
    fillDati(listaWidget);
  }

  @Override
  protected void addWidget(
      String denominazione, POSIZIONE posizione, DatiVisualizzazioneSezioneWidget widgetTop) {
    StringBuilder builder = getWidgetClasseNameStringBuilder(denominazione);
    log.debug("builder io leggo=" + builder.toString());
    add(addWidgetDynamico(builder, posizione));
  }

  private boolean isVisualizzabileVaiTuttiIServizi(
      List<DatiVisualizzazioneSezioneWidget> listaWidget) {

    return getListaFunzioniDisponibili().size() > 2;
  }

  private List<FunzioniDisponibili> getListaFunzioniDisponibili() {
    List<FunzioniDisponibili> pagineAbilitate =
        ((LoginInSession) getSession()).getPagineAbilitate();
    return ServiceLocator.getInstance()
        .getServiziHomePage()
        .getFunzioniBySezione(pagineAbilitate, ID_SEZIONE_IO_LEGGO);
  }
}
