package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.riepilogo.panel;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.GloboListView;
import java.util.ArrayList;
import java.util.List;

public class RiepilogoMiMuovoPanel extends BasePanel {

  private static final long serialVersionUID = 4296881125810679901L;

  public RiepilogoMiMuovoPanel(String id) {
    super(id);

    Utente.inizializzaPrivacyServiziBrav(getUtente());

    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {

    List<String> listaOrdini = getOrdinamento();
    GloboListView link = new GloboListView("funzioni", getListaOrdinata(listaOrdini));

    link.setRenderBodyOnly(true);
    addOrReplace(link);
    creaPulsantiGlobo(3L);
  }

  private List<String> getOrdinamento() {
    List<String> lista = new ArrayList<String>();
    lista.add("InfoTrafficoPage");
    lista.add("IMieiMezziPage");
    lista.add("MieiVerbaliPage");
    lista.add("MieIstanzePage");
    lista.add("RicorsiAlPrefettoPage");
    lista.add("DichiarazionePuntiPatenteConducentePage");
    lista.add("AbbonamentiAmtPage");
    lista.add("SanzioniAMTPage");
    lista.add("PermessiPersonalizzatiPage");
    lista.add("GenovaParcheggiPage");
    lista.add("PermessiZTLPage");
    lista.add("PermessiCUDEPage");
    lista.add("AccessoAiVarchiPage");

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
    GloboListView link = new GloboListView("globo", idSezione);
    link.setRenderBodyOnly(true);
    addOrReplace(link);
  }

  private List<FunzioniDisponibili> getListaGLobo() {
    List<FunzioniDisponibili> pagineAbilitate =
        ((LoginInSession) getSession()).getPagineAbilitate();
    log.debug("pagineAbilitate_test " + pagineAbilitate);
    return ServiceLocator.getInstance()
        .getServiziHomePage()
        .getFunzioniBySezione(pagineAbilitate, 3L);
  }

  private List<FunzioniDisponibili> getLinkDisponibiliSezione() {

    List<FunzioniDisponibili> list = new ArrayList<>();

    for (FunzioniDisponibili funzione : getListaGLobo()) {
      if (funzione.getClassePaginaStd() != null) {

        log.debug("UFF = " + funzione.getClassePaginaStd());

        if (funzione.getClassePaginaStd().equalsIgnoreCase("InfoTrafficoPage")) {
          funzione.setDescrizioneFunz("Informazioni sul traffico");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("IMieiMezziPage")) {
          funzione.setDescrizioneFunz("I miei mezzi");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("MieiVerbaliPage")) {
          funzione.setDescrizioneFunz("I miei verbali");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("MieIstanzePage")) {
          funzione.setDescrizioneFunz("Le mie istanze");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("RicorsiAlPrefettoPage")) {
          funzione.setDescrizioneFunz("I miei ricorsi al Prefetto");
          list.add(funzione);
        }
        if (funzione
            .getClassePaginaStd()
            .equalsIgnoreCase("DichiarazionePuntiPatenteConducentePage")) {
          // funzione.setIdFunzPadre(null);
          funzione.setDescrizioneFunz("Dichiarazione dati conducente");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("AbbonamentiAmtPage")) {
          // funzione.setIdFunzPadre(null);
          funzione.setDescrizioneFunz("I miei abbonamenti AMT");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("SanzioniAmtPage")) {
          // funzione.setIdFunzPadre(null);
          funzione.setDescrizioneFunz("Le mie sanzioni AMT");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("PermessiPersonalizzatiPage")) {
          funzione.setDescrizioneFunz("I miei parcheggi personalizzati");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("GenovaParcheggiPage")) {
          funzione.setDescrizioneFunz("I miei permessi di sosta di Genova Parcheggi");
          list.add(funzione);
        }

        if (funzione.getClassePaginaStd().equalsIgnoreCase("PermessiZTLPage")) {
          funzione.setDescrizioneFunz("I miei permessi ZTL");
          list.add(funzione);
        }

        if (funzione.getClassePaginaStd().equalsIgnoreCase("PermessiCUDEPage")) {
          funzione.setDescrizioneFunz("I miei permessi CUDE");
          list.add(funzione);
        }

        if (funzione.getClassePaginaStd().equalsIgnoreCase("AccessoAiVarchiPage")) {
          funzione.setDescrizioneFunz("Accesso ai Varchi non autorizzato");
          list.add(funzione);
        }
      }
    }

    return list;
  }
}
