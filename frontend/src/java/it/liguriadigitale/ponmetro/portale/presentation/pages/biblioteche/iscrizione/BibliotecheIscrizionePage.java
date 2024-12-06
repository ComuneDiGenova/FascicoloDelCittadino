package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheIscrizione;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.panel.BibliotecheIscrizionePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.util.Select2Reference;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

public class BibliotecheIscrizionePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -5917138898309883384L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public BibliotecheIscrizionePage() {
    super();
    log.debug("BibliotecheIscrizionePage");
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    if (getUtente().getListaUtenteBiblioteche() != null
        && !getUtente().getListaUtenteBiblioteche().isEmpty()) {

      String nomeSpid = getUtente().getNome().trim();
      String cognomeSpid = getUtente().getCognome().trim();

      String nomeSebina = getUtente().getListaUtenteBiblioteche().get(0).getGivenName().trim();
      String cognomeSebina = getUtente().getListaUtenteBiblioteche().get(0).getSn().trim();

      boolean datiSpidSebinaOk =
          nomeSpid.equalsIgnoreCase(nomeSebina) && cognomeSpid.equalsIgnoreCase(cognomeSebina);
      log.debug("datiSpidSebinaOk=" + datiSpidSebinaOk);
      if (!datiSpidSebinaOk) {
        NotificationPanel notificationPanel = new NotificationPanel("bibliotecheIscrizionePanel");
        String messageInfo = getString("BibliotecheIscrizionePage.messageInfo");
        notificationPanel.info(messageInfo);
        addOrReplace(notificationPanel);

        List<Utente> listaUtenteSebina = getUtente().getListaUtenteBiblioteche();
        List<Long> listaIdSebina = new ArrayList<>();
        for (Utente utenteSebina : listaUtenteSebina) {
          listaIdSebina.add(utenteSebina.getId());
        }

        ServiceLocator.getInstance()
            .getServiziBiblioteche()
            .inviaMailSegnalazione(getUtente(), listaIdSebina);
      }
    } else {
      ComponenteNucleo componenteNucleo = getUtente().getUtenteLoggatoComeComponenteNucleo();
      BibliotecheIscrizionePanel bibliotecheIscrizionePanel =
          (BibliotecheIscrizionePanel)
              new BibliotecheIscrizionePanel("bibliotecheIscrizionePanel", componenteNucleo)
                  .setRenderBodyOnly(true);
      addOrReplace(bibliotecheIscrizionePanel);
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public BibliotecheIscrizionePage(ComponenteNucleo componenteNucleo) {
    super();
    log.debug("BibliotecheIscrizionePage - ComponenteNucleo");
    try {

      List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<BreadcrumbFdC>();

      if (LabelFdCUtil.checkIfNotNull(componenteNucleo)
          && LabelFdCUtil.checkIfNotNull(componenteNucleo.getDatiCittadino())
          && PageUtil.isStringValid(componenteNucleo.getDatiCittadino().getCpvTaxCode())) {
        if (componenteNucleo
            .getDatiCittadino()
            .getCpvTaxCode()
            .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {
          listaBreadcrumb =
              ServiceLocator.getInstance().getServiziBiblioteche().getListaBreadcrumbIscrizione();
        } else {
          listaBreadcrumb =
              ServiceLocator.getInstance()
                  .getServiziBiblioteche()
                  .getListaBreadcrumbIscrizioneBambini();
        }
      }

      FdCBreadcrumbPanel breadcrumbPanel =
          new FdCBreadcrumbPanel("breadcrumbPanel", listaBreadcrumb);
      addOrReplace(breadcrumbPanel);

      Residente datiBambino = componenteNucleo.getDatiCittadino();
      if (LabelFdCUtil.checkIfNotNull(datiBambino)) {
        List<it.liguriadigitale.ponmetro.sebinaBiblioteche.model.Utente> listaFindByDocIdentita;

        listaFindByDocIdentita =
            ServiceLocator.getInstance()
                .getServiziBiblioteche()
                .getUtenteByCf(datiBambino.getCpvTaxCode());

        if (LabelFdCUtil.checkIfNotNull(listaFindByDocIdentita)
            && !LabelFdCUtil.checkEmptyList(listaFindByDocIdentita)) {
          String nomeSpid = datiBambino.getCpvGivenName();
          String cognomeSpid = datiBambino.getCpvFamilyName();

          String nomeSebina = listaFindByDocIdentita.get(0).getGivenName();
          String cognomeSebina = listaFindByDocIdentita.get(0).getSn();

          boolean datiSpidSebinaOk =
              nomeSpid.equalsIgnoreCase(nomeSebina) && cognomeSpid.equalsIgnoreCase(cognomeSebina);

          log.debug("datiSpidSebinaOk=" + datiSpidSebinaOk);
          if (!datiSpidSebinaOk) {
            NotificationPanel notificationPanel =
                new NotificationPanel("bibliotecheIscrizionePanel");
            String messageInfo = getString("BibliotecheIscrizionePage.messageInfo");
            notificationPanel.info(messageInfo);
            addOrReplace(notificationPanel);

            List<Utente> listaUtenteSebina = getUtente().getListaUtenteBiblioteche();
            List<Long> listaIdSebina = new ArrayList<Long>();
            for (Utente utenteSebina : listaUtenteSebina) {
              listaIdSebina.add(utenteSebina.getId());
            }

            ServiceLocator.getInstance()
                .getServiziBiblioteche()
                .inviaMailSegnalazioneBambino(componenteNucleo, getUtente(), listaIdSebina);
          }
        } else {
          BibliotecheIscrizionePanel bibliotecheIscrizionePanel =
              new BibliotecheIscrizionePanel("bibliotecheIscrizionePanel", componenteNucleo);
          addOrReplace(bibliotecheIscrizionePanel);
        }
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore get bambino Sebina: " + e.getMessage());
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public BibliotecheIscrizionePage(
      BibliotecheIscrizione bibliotecheIscrizione, ComponenteNucleo componenteNucleo) {

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    BibliotecheIscrizionePanel bibliotecheIscrizionePanel =
        (BibliotecheIscrizionePanel)
            new BibliotecheIscrizionePanel(
                    "bibliotecheIscrizionePanel", bibliotecheIscrizione, componenteNucleo)
                .setRenderBodyOnly(true);
    addOrReplace(bibliotecheIscrizionePanel);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public BibliotecheIscrizionePage(boolean datiSpidSebinaOk) {

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    NotificationPanel notificationPanel = new NotificationPanel("bibliotecheIscrizionePanel");
    String messageInfo = getString("BibliotecheIscrizionePage.messageInfo");
    notificationPanel.info(messageInfo);
    addOrReplace(notificationPanel);

    List<Utente> listaUtenteSebina = getUtente().getListaUtenteBiblioteche();
    List<Long> listaIdSebina = new ArrayList<Long>();
    for (Utente utenteSebina : listaUtenteSebina) {
      listaIdSebina.add(utenteSebina.getId());
    }

    ServiceLocator.getInstance()
        .getServiziBiblioteche()
        .inviaMailSegnalazione(getUtente(), listaIdSebina);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    response.render(JavaScriptHeaderItem.forReference(Select2Reference.instance()));
  }
}
