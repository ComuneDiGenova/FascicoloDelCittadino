package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.riepilogo.panel;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.GloboListView;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class RiepilogoMieiDatiPanel extends BasePanel {

  private static final long serialVersionUID = -8344051157360376583L;

  PageParameters pageParameters;

  public RiepilogoMieiDatiPanel(String id, PageParameters pageParameters) {
    super(id);
    log.debug("RiepilogoMieiDatiPanel");
    this.pageParameters = pageParameters;
    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {

    Long idSez = 1L;

    List<FunzioniDisponibili> pagineAbilitate = getListaFunzioni(idSez);
    List<FunzioniDisponibili> lista = new ArrayList<FunzioniDisponibili>();

    for (FunzioniDisponibili pagina : pagineAbilitate) {
      String classePaginaStd = pagina.getClassePaginaStd();
      if (pagina.getFlagAbilitazioneFunz() && !classePaginaStd.equalsIgnoreCase("GloboPage")) {
        if (classePaginaStd.equalsIgnoreCase("MieiDatiPage")) {
          log.debug("MieiDatiPage.add");
          pagina = setNewWicketTexts(pagina, "I miei dati");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("MieiDatiNonResidentiPage")) {
          log.debug("MieiDatiNonResidentiPage.add");
          pagina = setNewWicketTexts(pagina, "I miei dati");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("MioNucleoPage")) {
          log.debug("MioNucleoPage.add");
          pagina = setNewWicketTexts(pagina, "Il mio nucleo anagrafico");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("VariazioniDiResidenzaPage")) {
          log.debug("VariazioniDiResidenzaPage.add");
          pagina = setNewWicketTexts(pagina, "Variazioni di residenza");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("CertificatoAnagraficoPage")) {
          log.debug("CertificatoAnagraficoPage.add");
          pagina = setNewWicketTexts(pagina, "Certificati anagrafe");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("CertificatoStatoCivilePage")) {
          log.debug("CertificatoStatoCivilePage.add");
          pagina = setNewWicketTexts(pagina, "Certificati stato civile");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("ServiziSeggiElettoraliPage")) {
          log.debug("ServiziSeggiElettoraliPage.add");
          pagina = setNewWicketTexts(pagina, "Servizi seggi elettorali");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("MioISEEPage")) {
          log.debug("MioISEEPage.add");
          pagina = setNewWicketTexts(pagina, "Mio ISEE");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("DomandeMatrimonioPage")) {
          pagina = setNewWicketTexts(pagina, "Pubblicazioni di matrimonio");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("DomandeUnioniCiviliPage")) {
          pagina =
              setNewWicketTexts(
                  pagina, "Richieste di unione civile fra persone dello stesso sesso");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("DomandeSeparazioniDivorziPage")) {

          pagina = setNewWicketTexts(pagina, "Richieste di separazioni e divorzi");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("DomandeTrascrizioneMatrimonioPage")) {
          pagina = setNewWicketTexts(pagina, "Trascrizione atti matrimonio estero");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase(
            "DomandeTrascrizioneScioglimentoMatrimonioPage")) {

          pagina =
              setNewWicketTexts(pagina, "Trascrizione atti scioglimento unioni civili/divorzi");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("DomandeIscrizioneAlboPresidentiPage")) {

          pagina = setNewWicketTexts(pagina, "Albo Presidenti di seggio");
          lista.add(pagina);
        } else if (classePaginaStd.equalsIgnoreCase("DomandeIscrizioneAlboScrutatoriPage")) {

          pagina = setNewWicketTexts(pagina, "Albo Scrutatori di seggio");
          lista.add(pagina);
        }
      }
    }

    GloboListView newRiepilogo = new GloboListView("funzioni", lista);
    newRiepilogo.setRenderBodyOnly(true);
    addOrReplace(newRiepilogo);

    creaPulsantiGlobo(1L);
  }

  private void creaPulsantiGlobo(Long idSezione) {
    GloboListView link = new GloboListView("globo", idSezione, false);
    link.setRenderBodyOnly(true);
    addOrReplace(link);
  }

  private List<FunzioniDisponibili> getListaFunzioni(Long idSezione) {
    List<FunzioniDisponibili> pagineAbilitate =
        ((LoginInSession) getSession()).getPagineAbilitate();
    log.debug(
        "RiepilogoMieiDatiPanel > Lista Pagine Abilitate in getListaFunzioni = \n"
            + pagineAbilitate);
    List<FunzioniDisponibili> pagineAbilitatePerIdSez =
        ServiceLocator.getInstance()
            .getServiziHomePage()
            .getFunzioniBySezione(pagineAbilitate, idSezione);
    log.debug(
        "RiepilogoMieiDatiPanel > Lista Pagine Abilitate per idSezione in getListaFunzioni = \n"
            + pagineAbilitatePerIdSez);
    return pagineAbilitatePerIdSez;
  }

  private FunzioniDisponibili setNewWicketTexts(FunzioniDisponibili pagina, String string) {
    pagina.setWicketLabelIdStd(string);
    pagina.setWicketTitleStd(string);
    return pagina;
  }
}
