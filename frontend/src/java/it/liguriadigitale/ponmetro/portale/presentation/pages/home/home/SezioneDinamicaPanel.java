package it.liguriadigitale.ponmetro.portale.presentation.pages.home.home;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.riepilogo.RiepilogoBibliotechePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.riepilogo.MenuRiepilogoIoCittadinoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.riepilogo.RiepilogoMieiDatiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.RiepilogoIoGenitorePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.MenuRiepilogoDinamicoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.SezionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.MyWidgetPanel.POSIZIONE;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.WidgetDinamico;
import it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget.WidgetGloboDinamico;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.riepilogo.MenuRiepilogoMiMuovoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.riepilogo.RiepilogoMiMuovoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.propongo.riepilogo.RiepilogoPropongoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.segnalazioni.riepilogo.RiepilogoSegnalazioniPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.riepilogo.MenuRiepilogoTributiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.riepilogo.RiepilogoContribuentePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.riepilogo.RiepilogoIoSostenibilePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;

public class SezioneDinamicaPanel extends SezionePanel {

  private static final long serialVersionUID = -8729619365722827992L;

  private String nomeSezione;

  private static final int NUMERO_MINIMO_FUNZIONI_PER_SEZIONE = 2;
  private static final String IO_CITTADINO = "ioCittadino";
  private static final String IO_GENITORE = "ioGenitore";
  private static final String IO_CONTRIBUENTE = "ioContribuente";
  private static final String IO_SOSTENIBILE = "ioSostenibile";
  private static final String IO_SEGNALO = "ioSegnalo";
  private static final String IO_PROPONGO = "ioPropongo";
  private static final String IO_LEGGO = "ioLeggo";
  private static final String IO_MI_MUOVO = "ioMiMuovo";
  private static final String GLOBO_PAGE = "GloboPage";

  public SezioneDinamicaPanel(String id, String nomeSezione) {
    super(id);
    this.nomeSezione = nomeSezione;
    DatiSezione sezione = estraiSezioneCorrente(nomeSezione);
    List<DatiVisualizzazioneSezioneWidget> listaWidget = estraiWidgetSezione(sezione);
    String descrizioneSez = sezione.getDescrizioneSez();
    if (IO_CITTADINO.equalsIgnoreCase(nomeSezione)) {
      descrizioneSez = getNomeUtente();
    }
    mostraTitolo(
        getPaginaRiepilogoDaSezione(nomeSezione),
        new Model<String>(descrizioneSez),
        checkVisibilitaLinkVaiTuttiServizi(),
        nomeSezione);
    loggaWidget(listaWidget);
    fillDati(listaWidget);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();
    if (IO_GENITORE.equalsIgnoreCase(nomeSezione)) {
      Utente utente = getUtente();
      if (utente.isRichiestoRefreshFigli()) {
        log.debug("isRichiestoRefreshFigli!!!");
        utente.aggiornaStatoFigli();
      }
      if (getUtente().getListaFigli().isEmpty()) {
        log.debug("getListaFigli().isEmpty()=");
        this.setVisible(false);
      }
    }
  }

  @Override
  protected void addWidget(
      String denominazione, POSIZIONE posizione, DatiVisualizzazioneSezioneWidget widget) {
    if (widget != null) {
      StringBuilder builder = getWidgetClasseNameStringBuilder(denominazione);

      if (GLOBO_PAGE.equalsIgnoreCase(widget.getFunzione().getClassePagina())) {
        WidgetGloboDinamico panel = new WidgetGloboDinamico(posizione, widget);
        add(panel);
      } else if (widget.getWidget().getUriWidg().contains(WIDGET)) {

        WidgetDinamico panel = new WidgetDinamico(posizione, widget);
        add(panel);
      } else if (StringUtils.isEmpty(denominazione)) {
        add(new EmptyPanel(posizione.getWicketId()));
      } else {
        add(addComponenteWidget(denominazione, posizione));
      }
    } else {
      add(new EmptyPanel(posizione.getWicketId()));
    }
  }

  private Class<? extends Page> getPaginaRiepilogoDaSezione(String nomeSezione) {

    if (getUtente().isUtenteDelegato()) {
      return getSezioneUtenteConDelega(nomeSezione);
    } else {
      return getSezioneUtenteNormale(nomeSezione);
    }
  }

  private Class<? extends Page> getSezioneUtenteNormale(String nomeSezione) {
    if (IO_GENITORE.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoIoGenitorePage.class;
    } else if (IO_CITTADINO.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoMieiDatiPage.class;
    } else if (IO_MI_MUOVO.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoMiMuovoPage.class;
    } else if (IO_LEGGO.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoBibliotechePage.class;
    } else if (IO_SEGNALO.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoSegnalazioniPage.class;
    } else if (IO_PROPONGO.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoPropongoPage.class;
    } else if (IO_SOSTENIBILE.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoIoSostenibilePage.class;
    } else if (IO_CONTRIBUENTE.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoContribuentePage.class;
    } else {
      return MenuRiepilogoDinamicoPage.class;
    }
  }

  private Class<? extends Page> getSezioneUtenteConDelega(String nomeSezione) {
    if (IO_GENITORE.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoIoGenitorePage.class;
    } else if (IO_CITTADINO.equalsIgnoreCase(nomeSezione)) {
      return MenuRiepilogoIoCittadinoPage.class;
    } else if (IO_MI_MUOVO.equalsIgnoreCase(nomeSezione)) {
      return MenuRiepilogoMiMuovoPage.class;
    } else if (IO_LEGGO.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoBibliotechePage.class;
    } else if (IO_SEGNALO.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoSegnalazioniPage.class;
    } else if (IO_PROPONGO.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoPropongoPage.class;
    } else if (IO_SOSTENIBILE.equalsIgnoreCase(nomeSezione)) {
      return RiepilogoIoSostenibilePage.class;
    } else if (IO_CONTRIBUENTE.equalsIgnoreCase(nomeSezione)) {
      return MenuRiepilogoTributiPage.class;
    } else {
      return MenuRiepilogoDinamicoPage.class;
    }
  }

  private Class<? extends Page> getRiepilogoSeUtenteDelegato() {
    if (getUtente().isUtenteDelegato()) {
      return MenuRiepilogoIoCittadinoPage.class;
    } else {
      return RiepilogoMieiDatiPage.class;
    }
  }

  private void loggaWidget(List<DatiVisualizzazioneSezioneWidget> listaWidget) {
    for (DatiVisualizzazioneSezioneWidget dati : listaWidget) {
      log.trace("----------------------------------------");
      log.trace(WIDGET + ": " + dati);
      log.trace("----------------------------------------");
    }
  }

  private String getNomeUtente() {
    Utente utente = getUtente();
    return "io " + utente.getNome() + " " + utente.getCognome();
  }

  private boolean checkVisibilitaLinkVaiTuttiServizi() {
    LoginInSession session = (LoginInSession) this.getSession();
    List<FunzioniDisponibili> lista = session.getPagineAbilitate();
    int numeroFunzioniTrovate = PageUtil.isNumeroFunzioniPerSezione(lista, nomeSezione);
    log.debug("numeroFunzioniTrovate=" + numeroFunzioniTrovate);
    return numeroFunzioniTrovate > NUMERO_MINIMO_FUNZIONI_PER_SEZIONE;
  }
}
