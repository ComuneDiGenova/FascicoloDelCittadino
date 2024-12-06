package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.minori;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.BibliotecheIscrizioneNonResidentiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.BibliotecheIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.BibliotecheMovimentiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.List;

public class BiblioMinoreLandingPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 2169944233025224432L;

  ComponenteNucleo bambino;

  public BiblioMinoreLandingPage(ComponenteNucleo bambino) {
    super();

    this.bambino = bambino;

    setOutputMarkupId(true);
  }

  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    if (LabelFdCUtil.checkIfNotNull(bambino)
        && PageUtil.isStringValid(bambino.getCodiceFiscale())) {
      try {
        List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> listaFindByDocIdentita =
            ServiceLocator.getInstance()
                .getServiziBiblioteche()
                .getUtenteByCf(bambino.getCodiceFiscale());

        if (LabelFdCUtil.checkIfNotNull(listaFindByDocIdentita)
            && !LabelFdCUtil.checkEmptyList(listaFindByDocIdentita)) {

          String nomeBambinoSpid = "";
          String cognomeBambinoSpid = "";

          if (LabelFdCUtil.checkIfNotNull(bambino.getDatiCittadino())) {
            if (PageUtil.isStringValid(bambino.getDatiCittadino().getCpvGivenName())) {
              nomeBambinoSpid = bambino.getDatiCittadino().getCpvGivenName().trim();
            }
            if (PageUtil.isStringValid(bambino.getDatiCittadino().getCpvFamilyName())) {
              cognomeBambinoSpid = bambino.getDatiCittadino().getCpvFamilyName().trim();
            }
          }

          String nomeBambinoSebina = listaFindByDocIdentita.get(0).getGivenName().trim();
          String cognomeBambinoSebina = listaFindByDocIdentita.get(0).getSn().trim();

          boolean datiSpidSebinaOk =
              nomeBambinoSpid.equalsIgnoreCase(nomeBambinoSebina)
                  && cognomeBambinoSpid.equalsIgnoreCase(cognomeBambinoSebina);

          if (!datiSpidSebinaOk) {
            setResponsePage(new BibliotecheIscrizionePage(datiSpidSebinaOk));
          } else {
            boolean maggiorenne = true;
            setResponsePage(new BibliotecheMovimentiPage(bambino, maggiorenne));
          }

        } else {
          if (LabelFdCUtil.checkIfNotNull(bambino.getDatiCittadino())
              && bambino.isResidenteComuneGenova()) {
            setResponsePage(new BibliotecheIscrizionePage(bambino));
          } else {
            setResponsePage(new BibliotecheIscrizioneNonResidentiPage(bambino));
          }
        }
      } catch (BusinessException | ApiException | IOException e) {
        log.error("Errore durante biblioteche iscrizione bambino: " + e.getMessage());
      }
    }
  }
}
