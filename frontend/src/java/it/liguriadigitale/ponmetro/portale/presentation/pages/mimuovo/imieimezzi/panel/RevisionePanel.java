package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.assicurazioneveicoli.model.VerificaAssicurazioneVeicoli;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel.template.AssicurazioneRevisioneTemplate;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tassaauto.model.Veicolo;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

public class RevisionePanel extends AssicurazioneRevisioneTemplate {

  private static final long serialVersionUID = 1360927818033505297L;

  private static Veicolo veicolo;

  private static final String ICON_REVISIONE = "color-cyan col-3 icon-revisione-auto";

  public RevisionePanel(String id) {
    super(id);
  }

  public RevisionePanel(String id, Veicolo veicolo) {
    super(id);

    RevisionePanel.veicolo = veicolo;
  }

  private AttributeAppender getCssIconaVeicolo(Veicolo veicolo) {
    String css = "";
    veicolo = RevisionePanel.veicolo;

    css = ICON_REVISIONE;

    return new AttributeAppender("class", " " + css);
  }

  @SuppressWarnings("unused")
  private VerificaAssicurazioneVeicoli getDatiAssicurazione(Veicolo veicolo) {
    try {
      VerificaAssicurazioneVeicoli verificaAssicurazioneVeicoli =
          ServiceLocator.getInstance().getServiziMieiMezzi().getAssicurazione(veicolo);
      return verificaAssicurazioneVeicoli;
    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(ErrorPage.class);
    }
  }

  @Override
  protected void logicaBusinessDelPannello(Object dati) throws BusinessException, ApiException {
    WebMarkupContainer icona = new WebMarkupContainer("icona");
    icona.add(getCssIconaVeicolo(veicolo));
    add(icona);

    String titoloValue = getString("AssicurazioneRevisioneTemplate.titoloRevisione");
    Label titolo = new Label("titolo", titoloValue);
    add(titolo);

    VerificaAssicurazioneVeicoli verificaAssicurazioneVeicoli = (VerificaAssicurazioneVeicoli) dati;

    Label targa =
        new Label(
            "targa",
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getTarga());
    add(targa);

    Label tipoVeicolo =
        new Label(
            "tipoVeicolo",
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getTipoVeicolo());
    add(tipoVeicolo);

    Label situazioneAl =
        new Label(
            "situazioneAl",
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getSituazioneAl());
    situazioneAl.setVisible(
        PageUtil.isStringValid(
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getSituazioneAl()));
    myAdd(situazioneAl);

    Label revisioneValida =
        new Label(
            "revisioneValida",
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getRevisioneValida());
    revisioneValida.setVisible(
        PageUtil.isStringValid(
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getRevisioneValida()));
    myAdd(revisioneValida);

    String scadenzaRevisione =
        verificaAssicurazioneVeicoli
            .getVerificaAssicurazioneDatiAnagraficiResponse()
            .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
            .getDataScadenzaRevisione();
    Label dataScadenzaRevisione = new Label("dataScadenzaRevisione", scadenzaRevisione);
    dataScadenzaRevisione.setVisible(
        PageUtil.isStringValid(
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getDataScadenzaRevisione()));
    myAdd(dataScadenzaRevisione);
  }

  @Override
  protected void gestioneErroreBusiness(Exception e) {
    super.gestioneErroreBusiness(e);
  }
}
