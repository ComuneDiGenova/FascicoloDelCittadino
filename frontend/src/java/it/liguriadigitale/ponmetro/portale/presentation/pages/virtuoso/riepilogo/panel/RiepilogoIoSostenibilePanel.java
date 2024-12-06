package it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.riepilogo.panel;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.GloboListView;
import java.util.ArrayList;
import java.util.List;

public class RiepilogoIoSostenibilePanel extends BasePanel {

  private static final long serialVersionUID = 6456566197525690315L;

  public RiepilogoIoSostenibilePanel(String id) {
    super(id);
    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {

    Long idSez = 8L;
    List<FunzioniDisponibili> pagineAbilitate = getListaFunzioni(idSez);
    List<FunzioniDisponibili> lista = new ArrayList<FunzioniDisponibili>();

    log.debug("n pagineAbilitate riepilogoSostenibile = " + pagineAbilitate.size());
    for (FunzioniDisponibili pagina : pagineAbilitate) {
      String classePaginaStd = pagina.getClassePaginaStd();
      log.debug("classePaginaStd corrente = " + classePaginaStd);
      if (pagina.getFlagAbilitazioneFunz() && !classePaginaStd.equalsIgnoreCase("GloboPage")) {
        if (classePaginaStd.equalsIgnoreCase("PuntiTariPage")) {
          log.debug("PuntiTariPage.add");
          pagina = setNewWicketTexts(pagina, "Punti TARI");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("PlastiPremiaPage")) {
          log.debug("PlastiPremiaPage.add");
          pagina = setNewWicketTexts(pagina, "PlasTiPremia");
          lista.add(pagina);
        }
      }
    }

    GloboListView funzioni = new GloboListView("funzioni", lista);
    funzioni.setRenderBodyOnly(true);
    addOrReplace(funzioni);

    creaPulsantiGlobo(8L);
  }

  private FunzioniDisponibili setNewWicketTexts(FunzioniDisponibili pagina, String string) {
    pagina.setWicketLabelIdStd(string);
    pagina.setWicketTitleStd(string);
    return pagina;
  }

  private List<FunzioniDisponibili> getListaFunzioni(Long idSezione) {
    List<FunzioniDisponibili> pagineAbilitate =
        ((LoginInSession) getSession()).getPagineAbilitate();
    log.debug(
        "RiepilogoIoSostenibilePanel > Lista Pagine Abilitate in getListaFunzioni = \n"
            + pagineAbilitate);
    List<FunzioniDisponibili> pagineAbilitatePerIdSez =
        ServiceLocator.getInstance()
            .getServiziHomePage()
            .getFunzioniBySezione(pagineAbilitate, idSezione);
    log.debug(
        "RiepilogoIoSostenibilePanel > Lista Pagine Abilitate per idSezione in getListaFunzioni = \n"
            + pagineAbilitatePerIdSez);
    return pagineAbilitatePerIdSez;
  }

  private void creaPulsantiGlobo(Long idSezione) {
    GloboListView link = new GloboListView("globo", idSezione);
    link.setRenderBodyOnly(true);
    addOrReplace(link);
  }
}
