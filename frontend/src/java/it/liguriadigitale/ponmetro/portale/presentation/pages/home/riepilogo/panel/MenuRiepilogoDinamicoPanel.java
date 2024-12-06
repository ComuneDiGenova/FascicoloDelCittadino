package it.liguriadigitale.ponmetro.portale.presentation.pages.home.riepilogo.panel;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiSezione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.GloboListView;

public class MenuRiepilogoDinamicoPanel extends BasePanel {

  private static final long serialVersionUID = -8051473841027086710L;

  private static final String MENU_RIEPILOGO_DINAMICO_PAGE = "MenuRiepilogoDinamicoPage";

  static final String URL_SEBINA = "https://bibliometroge.sebina.it/opac/.do";

  public MenuRiepilogoDinamicoPanel(String id, DatiSezione datiSezione) {
    super(id);
    fillDati(datiSezione);
  }

  @SuppressWarnings("unused")
  private boolean isVisibileIscrizione() {
    return !isDatiSpidSebinaOK();
  }

  @SuppressWarnings("unused")
  private boolean isVisibileMovimenti() {
    return isDatiSpidSebinaOK();
  }

  private boolean isDatiSpidSebinaOK() {
    if (getUtente().getListaUtenteBiblioteche() != null
        && !getUtente().getListaUtenteBiblioteche().isEmpty()) {
      String nomeSpid = getUtente().getNome().trim();
      String cognomeSpid = getUtente().getCognome().trim();

      String nomeSebina = getUtente().getListaUtenteBiblioteche().get(0).getGivenName().trim();
      String cognomeSebina = getUtente().getListaUtenteBiblioteche().get(0).getSn().trim();

      boolean datiSpidSebinaOk =
          nomeSpid.equalsIgnoreCase(nomeSebina) && cognomeSpid.equalsIgnoreCase(cognomeSebina);

      return datiSpidSebinaOk;
    } else {
      return false;
    }
  }

  @Override
  public void fillDati(Object dati) {
    // boolean iscrRes = isVisibileIscrizione() &&
    // getUtente().isResidente();
    creaPulsantiGlobo(dati);
  }

  private void creaPulsantiGlobo(Object dati) {

    DatiSezione datiSezione = (DatiSezione) dati;
    GloboListView link =
        new GloboListView("listView", Long.valueOf(datiSezione.getIdSezione()), true);
    // isNecessarioVisualizzareTutteLeFunzioni(datiSezione));
    link.setRenderBodyOnly(true);
    addOrReplace(link);
  }

  public boolean isNecessarioVisualizzareTutteLeFunzioni(DatiSezione datiSezione) {
    return datiSezione.getUriSez().equalsIgnoreCase(MENU_RIEPILOGO_DINAMICO_PAGE);
  }
}
