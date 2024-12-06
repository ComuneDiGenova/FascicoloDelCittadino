package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.panel;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.GloboListView;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class RiepilogoIoGenitorePanel extends BasePanel {

  private static final long serialVersionUID = -6974858626303229665L;

  public RiepilogoIoGenitorePanel(String id) {
    super(id);
    setOutputMarkupId(true);
    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {

    List<String> listaOrdini = getOrdinamento();
    GloboListView link = new GloboListView("funzioni", getListaOrdinata(listaOrdini));

    link.setRenderBodyOnly(true);
    addOrReplace(link);

    creaPulsantiGlobo(2L);
  }

  private List<String> getOrdinamento() {
    List<String> lista = new ArrayList<String>();
    lista.add("Iscrizione06Page");
    lista.add("SimulazioneTariffariaPage");
    lista.add("MieiFigliPage");
    lista.add("RiepilogoIscrizioniPage");
    lista.add("PresenzeInMensaPage");
    lista.add("PagamentiBollettiniMensaPage");
    lista.add("AutocertificazioneGenitorePage");
    // lista.add("DomandaIscrizioneMensaResidentePage");
    // lista.add("DomandaAgevolazioneTariffariaMensaPage");
    lista.add("DomandeTrasportoBambiniDisabiliPage");
    lista.add("DomandeAutodichiarazioneFigliPage");
    lista.add("BollettazioneMensaScolasticaPage");
    return lista;
  }

  private List<FunzioniDisponibili> getListaOrdinata(List<String> listaOrdini) {
    List<FunzioniDisponibili> listaDaRiordinare = getLinkDisponibiliSezione();
    List<FunzioniDisponibili> listaRiordinata = new ArrayList<FunzioniDisponibili>();

    for (String StringaDiControllo : listaOrdini) {
      for (FunzioniDisponibili elementoDaControllare : listaDaRiordinare) {
        if (StringaDiControllo.equalsIgnoreCase(elementoDaControllare.getClassePaginaStd())) {
          listaRiordinata.add(elementoDaControllare);
        }
      }
    }

    return listaRiordinata;
  }

  private void creaPulsantiGlobo(Long idSezione) {
    if (isUtenteHaFigli()) {
      GloboListView link = new GloboListView("globo", idSezione);
      link.setRenderBodyOnly(true);
      addOrReplace(link);
    } else {
      EmptyPanel panel = new EmptyPanel("globo");
      addOrReplace(panel);
    }
  }

  private boolean isUtenteHaFigli() {
    boolean visibilitaLinkMieiFigli = getUtente() != null && getUtente().hasFigli();
    return visibilitaLinkMieiFigli;
  }

  private List<FunzioniDisponibili> getListaGLobo() {
    List<FunzioniDisponibili> pagineAbilitate =
        ((LoginInSession) getSession()).getPagineAbilitate();
    log.debug("pagineAbilitate_test " + pagineAbilitate);
    return ServiceLocator.getInstance()
        .getServiziHomePage()
        .getFunzioniBySezione(pagineAbilitate, 2L);
  }

  private List<FunzioniDisponibili> getLinkDisponibiliSezione() {

    List<FunzioniDisponibili> list = new ArrayList<>();
    boolean visibilitaLinkIscrizione06 = getUtente() != null && getUtente().hasFigli();
    boolean visibilitaBambiniAScuola = getUtente() != null && getUtente().hasFigli();
    boolean visibilitaPresenzeMensaMenu =
        getUtente() != null && getUtente().hasFigliIscrittiAMensa();
    boolean visibilitaLinkPagamanetiMensa =
        (getUtente() != null && getUtente().hasFigliIscrittiAMensa());

    for (FunzioniDisponibili funzione : getListaGLobo()) {
      if (funzione.getClassePaginaStd() != null) {
        log.debug("ClassePaginaStd funzione = " + funzione.getClassePaginaStd());
        if (funzione.getClassePaginaStd().equalsIgnoreCase("Iscrizione06Page")) {
          if (visibilitaLinkIscrizione06) {
            setNewWicketTexts(funzione, "Iscrizione servizi 0-6 anni");
            list.add(funzione);
          }
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("SimulazioneTariffariaPage")) {
          funzione.setIdFunzPadre(null);
          setNewWicketTexts(funzione, "Simulazione tariffaria");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("MieiFigliPage")) {
          if (isUtenteHaFigli()) {
            setNewWicketTexts(funzione, "I miei figli");
            list.add(funzione);
          }
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("RiepilogoIscrizioniPage")) {
          if (visibilitaBambiniAScuola) {
            // setNewWicketTexts(funzione, "");
            list.add(funzione);
          }
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("PresenzeInMensaPage")) {
          if (visibilitaPresenzeMensaMenu) {
            funzione.setIdFunzPadre(null);
            setNewWicketTexts(funzione, "Presenze in mensa e menu'");
            list.add(funzione);
          }
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("PagamentiBollettiniMensaPage")) {
          if (visibilitaLinkPagamanetiMensa) {
            funzione.setIdFunzPadre(null);
            funzione.setIconaCss("color-orange icon-tributi");
            setNewWicketTexts(funzione, "Pagamenti ristorazione e servizi comunali");
            list.add(funzione);
          }
        }

        if (funzione.getClassePaginaStd().equalsIgnoreCase("AutocertificazioneGenitorePage")) {
          funzione.setIdFunzPadre(null);
          funzione.setIconaCss("color-orange icon-tributi");
          setNewWicketTexts(funzione, "Autodichiarazione nucleo familiare");
          list.add(funzione);
        }

        //				if
        // (funzione.getClassePaginaStd().equalsIgnoreCase("DomandaIscrizioneMensaResidentePage")) {
        //					funzione.setIdFunzPadre(null);
        //					funzione.setIconaCss("color-orange icon-tributi");
        //					setNewWicketTexts(funzione, "Iscrizione mensa residenti nuova");
        //					list.add(funzione);
        //				}

        //				if
        // (funzione.getClassePaginaStd().equalsIgnoreCase("DomandaAgevolazioneTariffariaMensaPage")) {
        //					funzione.setIdFunzPadre(null);
        //					funzione.setIconaCss("color-orange icon-tributi");
        //					setNewWicketTexts(funzione, "Agevolzione mensa nuova");
        //					list.add(funzione);
        //				}

        if (funzione.getClassePaginaStd().equalsIgnoreCase("DomandeTrasportoBambiniDisabiliPage")) {
          funzione.setIdFunzPadre(null);
          funzione.setIconaCss("icon-camper col-auto color-orange");
          setNewWicketTexts(funzione, "Trasporto scolastico per alunni con disabilità");
          list.add(funzione);
        }

        if (funzione.getClassePaginaStd().equalsIgnoreCase("DomandeAutodichiarazioneFigliPage")) {
          funzione.setIdFunzPadre(null);
          funzione.setIconaCss("icon-studenti col-auto color-orange");
          setNewWicketTexts(funzione, "Autodichirazione figli T6");
          list.add(funzione);
        }

        if (funzione.getClassePaginaStd().equalsIgnoreCase("BollettazioneMensaScolasticaPage")) {
          funzione.setIdFunzPadre(null);
          funzione.setIconaCss("icon-studenti col-auto color-orange");
          setNewWicketTexts(funzione, "Scelta Modalità Invio Bollettino");
          list.add(funzione);
        }
      }
    }

    return list;
  }

  private FunzioniDisponibili setNewWicketTexts(FunzioniDisponibili pagina, String string) {
    pagina.setWicketLabelIdStd(string);
    pagina.setWicketTitleStd(string);
    if (pagina.getDescrizioneFunz() == null) {
      pagina.setDescrizioneFunz(string);
    }
    return pagina;
  }
}
