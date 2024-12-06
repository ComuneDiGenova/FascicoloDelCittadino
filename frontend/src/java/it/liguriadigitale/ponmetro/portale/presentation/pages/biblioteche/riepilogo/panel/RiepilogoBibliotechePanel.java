package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.riepilogo.panel;

import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.portale.pojo.enums.ScaricaPrivacyEnum;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzionePrivacySebina;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.GloboListView;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.util.ArrayList;
import java.util.List;

public class RiepilogoBibliotechePanel extends BasePanel {

  private static final long serialVersionUID = -8051473841027086710L;

  static final String URL_SEBINA = "https://bibliometroge.sebina.it/opac/.do";

  public RiepilogoBibliotechePanel(String id) {
    super(id);
    fillDati("");
  }

  public boolean isVisibileIscrizione() {
    return !isDatiSpidSebinaOK();
  }

  public boolean isVisibileMovimenti() {
    return isDatiSpidSebinaOK();
  }

  private boolean isDatiSpidSebinaOK() {
    log.debug(
        "getUtente().getListaUtenteBiblioteche() != null=" + getUtente().getListaUtenteBiblioteche()
            != null);
    if (getUtente().getListaUtenteBiblioteche() != null
        && !getUtente().getListaUtenteBiblioteche().isEmpty()) {
      String nomeSpid = getUtente().getNome().trim();
      String cognomeSpid = getUtente().getCognome().trim();

      String nomeSebina = getUtente().getListaUtenteBiblioteche().get(0).getGivenName().trim();
      String cognomeSebina = getUtente().getListaUtenteBiblioteche().get(0).getSn().trim();

      log.debug(
          "nomeSpid = "
              + nomeSpid
              + " cognomeSpid = "
              + cognomeSpid
              + " nomeSebina = "
              + nomeSebina
              + " cognomeSebina = "
              + cognomeSebina);

      boolean datiSpidSebinaOk =
          nomeSpid.equalsIgnoreCase(nomeSebina) && cognomeSpid.equalsIgnoreCase(cognomeSebina);
      log.debug("datiSpidSebinaOk=" + datiSpidSebinaOk);
      return datiSpidSebinaOk;
    } else {
      return false;
    }
  }

  @Override
  public void fillDati(Object dati) {

    log.debug("utente in riepilogo biblioteche =" + getUtente());

    Long idSez = 7L;
    log.debug(
        "isVisibleIscrizione = "
            + isVisibileIscrizione()
            + " && utente is residente = "
            + getUtente().isResidente());
    boolean iscrRes = isVisibileIscrizione() && getUtente().isResidente();
    log.debug("iscrRes = " + iscrRes);
    boolean iscrNonRes = isVisibileIscrizione() && !getUtente().isResidente();
    log.debug("iscrNonRes = " + iscrNonRes);
    Boolean isSebinaPrivNonAccett = getUtente().isSebinaPrivacyNonAccettata();
    log.debug("isSebinaPrivNonAccett =" + isSebinaPrivNonAccett);
    List<ComponenteNucleo> listaFigliUnder18 =
        getUtente().listaFigliInNucleoAllargatoCoresidentiUnder18();
    boolean isVisibileBiblioMinori =
        LabelFdCUtil.checkIfNotNull(listaFigliUnder18)
            && !LabelFdCUtil.checkEmptyList(listaFigliUnder18);
    log.debug("isVisibileBiblioMinori =" + isVisibileBiblioMinori);

    List<FunzioniDisponibili> pagineAbilitate = getListaFunzioni(idSez);
    List<FunzioniDisponibili> lista = new ArrayList<FunzioniDisponibili>();

    LinkDinamicoLaddaFunzionePrivacySebina<Object> privacySebina =
        new LinkDinamicoLaddaFunzionePrivacySebina<Object>(
            "privacySebina",
            new LinkDinamicoFunzioneData(
                "RiepilogoBibliotechePanel.privacySebina",
                "BibliotecheSebinaPrivacyPage",
                "RiepilogoBibliotechePanel.privacySebina"),
            null,
            RiepilogoBibliotechePanel.this,
            "color-blue-sebina col-auto icon-salva",
            isSebinaPrivNonAccett,
            ScaricaPrivacyEnum.PRIVACY_SEBINA);

    addOrReplace(privacySebina);
    // privacySebina.setEnabled(false);

    log.debug("lista pagine abilitate=" + pagineAbilitate);

    for (FunzioniDisponibili pagina : pagineAbilitate) {
      String classePaginaStd = pagina.getClassePaginaStd();
      if (pagina.getFlagAbilitazioneFunz() && !classePaginaStd.equalsIgnoreCase("GloboPage")) {
        if (classePaginaStd.equalsIgnoreCase("BibliotecheIscrizionePage")) {
          if (iscrRes) {
            log.debug("BibliotecheIscrizionePage.add");
            pagina = setNewWicketTexts(pagina, "Iscrizione alle biblioteche comunali");
            lista.add(pagina);
          }
        } else if (classePaginaStd.equalsIgnoreCase("BibliotecheIscrizioneNonResidentiPage")) {
          if (iscrNonRes) {
            log.debug("BibliotecheIscrizioneNonResidentiPage.add");
            pagina = setNewWicketTexts(pagina, "Iscrizione alle biblioteche comunali");
            lista.add(pagina);
          }
        } else if (classePaginaStd.equalsIgnoreCase("BibliotecheMovimentiPage")) {
          if (isVisibileMovimenti()) {
            log.debug("BibliotecheMovimentiPage.add");
            pagina = setNewWicketTexts(pagina, "I miei prestiti e prenotazioni");
            lista.add(pagina);
          }
        } else if (classePaginaStd.equalsIgnoreCase("BiblioMinoriPage")) {
          if (isVisibileBiblioMinori) {
            log.debug("BiblioMinoriPage.add");
            pagina = setNewWicketTexts(pagina, "Servizi per i miei figli");
            lista.add(pagina);
          }
        } else if (classePaginaStd.equalsIgnoreCase("BibliotecheSebinaLinkPage")) {
          log.debug("BibliotecheSebinaLinkPage.add");
          pagina = setNewWicketTexts(pagina, "Vai al catalogo biblioteche");
          lista.add(pagina);
        }
      }
    }

    log.debug("lista funzioni per globo list view = " + lista);

    GloboListView newRiepilogo = new GloboListView("funzioni", lista);
    newRiepilogo.setRenderBodyOnly(true);
    addOrReplace(newRiepilogo);

    creaPulsantiGlobo(7L);
  }

  private FunzioniDisponibili setNewWicketTexts(FunzioniDisponibili pagina, String string) {
    pagina.setWicketLabelIdStd(string);
    pagina.setWicketTitleStd(string);
    return pagina;
  }

  private void creaPulsantiGlobo(Long idSezione) {
    GloboListView link = new GloboListView("globo", idSezione);
    link.setRenderBodyOnly(true);
    addOrReplace(link);
  }

  private List<FunzioniDisponibili> getListaFunzioni(Long idSezione) {
    List<FunzioniDisponibili> pagineAbilitate =
        ((LoginInSession) getSession()).getPagineAbilitate();
    log.debug(
        "RiepilogoBibliotechePanel > Lista Pagine Abilitate in getListaFunzioni = \n"
            + pagineAbilitate);
    List<FunzioniDisponibili> pagineAbilitatePerIdSez =
        ServiceLocator.getInstance()
            .getServiziHomePage()
            .getFunzioniBySezione(pagineAbilitate, idSezione);
    log.debug(
        "RiepilogoBibliotechePanel > Lista Pagine Abilitate per idSezione in getListaFunzioni = \n"
            + pagineAbilitatePerIdSez);
    return pagineAbilitatePerIdSez;
  }
}
