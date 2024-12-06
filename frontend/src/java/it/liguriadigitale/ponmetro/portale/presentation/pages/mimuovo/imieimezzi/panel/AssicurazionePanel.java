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

public class AssicurazionePanel extends AssicurazioneRevisioneTemplate {

  private static final long serialVersionUID = 8238357103198862683L;

  private static Veicolo veicolo;

  @SuppressWarnings("unused")
  private static final String ICON_AUTOVEICOLO = "color-cyan col-3 icon-autoveicolo";

  @SuppressWarnings("unused")
  private static final String ICON_MOTOVEICOLO = "color-cyan col-3 icon-motoveicolo";

  @SuppressWarnings("unused")
  private static final String ICON_VEICOLI = "color-cyan col-3 icon-mezzi";

  private static final String ICON_ASSICURAZIONE = "color-cyan col-3 icon-assicurazione-veicolo";

  public AssicurazionePanel(String id) {
    super(id);
  }

  public AssicurazionePanel(String id, Veicolo veicolo) {
    super(id);

    AssicurazionePanel.veicolo = veicolo;
  }

  private AttributeAppender getCssIconaVeicolo(Veicolo veicolo) {
    String css = "";

    veicolo = AssicurazionePanel.veicolo;

    css = ICON_ASSICURAZIONE;

    return new AttributeAppender("class", " " + css);
  }

  @SuppressWarnings("unused")
  private VerificaAssicurazioneVeicoli getDatiAssicurazione(Veicolo veicolo) {
    log.debug("CP in assicurazione panel = " + veicolo.getTarga());
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

    String titoloValue = getString("AssicurazioneRevisioneTemplate.titoloAssicurazione");
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

    Label compagniaAssicurativa =
        new Label(
            "compagniaAssicurativa",
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getCompagniaAssicuratrice());
    compagniaAssicurativa.setVisible(
        PageUtil.isStringValid(
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getCompagniaAssicuratrice()));
    myAdd(compagniaAssicurativa);

    Label numeroPolizza =
        new Label(
            "numeroPolizza",
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getNumeroPolizza());
    numeroPolizza.setVisible(
        PageUtil.isStringValid(
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getNumeroPolizza()));
    myAdd(numeroPolizza);

    Label veicoloAssicurato =
        new Label(
            "veicoloAssicurato",
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getVeicoloAssicurato());
    veicoloAssicurato.setVisible(
        PageUtil.isStringValid(
            verificaAssicurazioneVeicoli
                .getVerificaAssicurazioneDatiAnagraficiResponse()
                .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
                .getVeicoloAssicurato()));
    myAdd(veicoloAssicurato);

    String scadenzaAssicurazioneFormatted = "";
    if (verificaAssicurazioneVeicoli
            .getVerificaAssicurazioneDatiAnagraficiResponse()
            .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
            .getDataScadenzaAssicurazione()
        != null) {
      String scadenzaAssicurazione =
          verificaAssicurazioneVeicoli
              .getVerificaAssicurazioneDatiAnagraficiResponse()
              .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
              .getDataScadenzaAssicurazione();
      String[] scadenzaAssicurazioneSplitted = scadenzaAssicurazione.split("-");
      scadenzaAssicurazioneFormatted =
          scadenzaAssicurazioneSplitted[2]
              .concat("/")
              .concat(scadenzaAssicurazioneSplitted[1])
              .concat("/")
              .concat(scadenzaAssicurazioneSplitted[0]);
    } else {
      scadenzaAssicurazioneFormatted = "-";
    }
    Label dataScadenzaAssicurazione =
        new Label("dataScadenzaAssicurazione", scadenzaAssicurazioneFormatted);
    dataScadenzaAssicurazione.setVisible(PageUtil.isStringValid(scadenzaAssicurazioneFormatted));
    myAdd(dataScadenzaAssicurazione);

    String decorrenzaAssicurazioneFormatted = "";
    if (verificaAssicurazioneVeicoli
            .getVerificaAssicurazioneDatiAnagraficiResponse()
            .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
            .getDataDecorrenzaAssicurazione()
        != null) {
      String decorrenzaAssicurazione =
          verificaAssicurazioneVeicoli
              .getVerificaAssicurazioneDatiAnagraficiResponse()
              .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
              .getDataDecorrenzaAssicurazione();
      String[] decorrenzaAssicurazioneSplitted = decorrenzaAssicurazione.split("-");
      decorrenzaAssicurazioneFormatted =
          decorrenzaAssicurazioneSplitted[2]
              .concat("/")
              .concat(decorrenzaAssicurazioneSplitted[1])
              .concat("/")
              .concat(decorrenzaAssicurazioneSplitted[0]);
    } else {
      decorrenzaAssicurazioneFormatted = "-";
    }
    Label dataDecorrenzaAssicurazione =
        new Label("dataDecorrenzaAssicurazione", decorrenzaAssicurazioneFormatted);
    dataDecorrenzaAssicurazione.setVisible(
        PageUtil.isStringValid(decorrenzaAssicurazioneFormatted));
    myAdd(dataDecorrenzaAssicurazione);

    String fineComportoFormatted = "";
    if (verificaAssicurazioneVeicoli
            .getVerificaAssicurazioneDatiAnagraficiResponse()
            .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
            .getDataFineComporto()
        != null) {
      String fineComporto =
          verificaAssicurazioneVeicoli
              .getVerificaAssicurazioneDatiAnagraficiResponse()
              .getVerificaCoperturaAssicurativaDatiAnagraficiOutput()
              .getDataFineComporto();
      String[] fineComportoSplitted = fineComporto.split("-");
      fineComportoFormatted =
          fineComportoSplitted[2]
              .concat("/")
              .concat(fineComportoSplitted[1])
              .concat("/")
              .concat(fineComportoSplitted[0]);
    } else {
      fineComportoFormatted = "-";
    }
    Label dataFineComporto = new Label("dataFineComporto", fineComportoFormatted);
    dataFineComporto.setVisible(PageUtil.isStringValid(fineComportoFormatted));
    myAdd(dataFineComporto);
  }

  @Override
  protected void gestioneErroreBusiness(Exception e) {
    super.gestioneErroreBusiness(e);
  }
}
