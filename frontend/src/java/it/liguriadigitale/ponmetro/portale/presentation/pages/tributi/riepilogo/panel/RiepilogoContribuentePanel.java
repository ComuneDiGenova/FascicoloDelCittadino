package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.riepilogo.panel;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.GloboListView;
import java.util.ArrayList;
import java.util.List;

public class RiepilogoContribuentePanel extends BasePanel {

  private static final long serialVersionUID = 4296881125810679901L;

  public RiepilogoContribuentePanel(String id) {
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

    creaPulsantiGlobo(4L);
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
        .getFunzioniBySezione(pagineAbilitate, 4L);
  }

  private List<FunzioniDisponibili> getLinkDisponibiliSezione() {

    List<FunzioniDisponibili> list = new ArrayList<>();

    for (FunzioniDisponibili funzione : getListaGLobo()) {
      if (funzione.getClassePaginaStd() != null) {
        if (funzione.getClassePaginaStd().equalsIgnoreCase("QuadroTributarioTariPage")) {
          funzione = setNewWicketTexts(funzione, "La mia TARI");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("TariNetribePage")) {
          funzione = setNewWicketTexts(funzione, "La mia TARI");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("ContributoTariPage")) {
          funzione = setNewWicketTexts(funzione, "Contributo TARI 2024");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("QuadroTributarioPage")) {
          funzione = setNewWicketTexts(funzione, "Il mio quadro tributario-IMU");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("ScadenzeEVersatoPage")) {
          funzione = setNewWicketTexts(funzione, "Le mie scadenze e il mio versato-IMU");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("ProprietaEUtenzePage")) {
          funzione = setNewWicketTexts(funzione, "Le mie proprieta' e le mie utenze");
          list.add(funzione);
        }
        if (funzione.getClassePaginaStd().equalsIgnoreCase("RimborsiImuPage")) {
          funzione = setNewWicketTexts(funzione, "Rimborsi IMU");
          list.add(funzione);
        }
      }
    }
    return list;
  }

  private FunzioniDisponibili setNewWicketTexts(FunzioniDisponibili pagina, String string) {
    pagina.setDescrizioneFunz(string);
    pagina.setWicketLabelIdStd(string);
    pagina.setWicketTitleStd(string);
    return pagina;
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

  private List<String> getOrdinamento() {
    List<String> lista = new ArrayList<String>();
    lista.add("TariNetribePage");
    lista.add("ContributoTariPage");

    lista.add("QuadroTributarioTariPage");
    lista.add("QuadroTributarioPage");
    lista.add("ScadenzeEVersatoPage");
    lista.add("ProprietaEUtenzePage");
    lista.add("RimborsiImuPage");

    return lista;
  }
}
