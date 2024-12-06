package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.panel;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.AbbonamentiAmtPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto.AmtPromozioneXXL;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto.AmtPromozioneXXLSmall;
import it.liguriadigitale.ponmetro.portale.presentation.util.AmtEncrypterUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.modelmapper.ModelMapper;

public class AbbonamentiAmtDettaglioPanel extends BasePanel {

  private static final long serialVersionUID = -1603235792042541738L;

  public AbbonamentiAmtDettaglioPanel(
      String id, CompoundPropertyModel<AmtPromozioneXXL> amtPromozioneXXL) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    fillDati(amtPromozioneXXL);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    CompoundPropertyModel<AmtPromozioneXXL> amtPromozioneXXL =
        (CompoundPropertyModel<AmtPromozioneXXL>) dati;

    log.debug("CP AbbonamentiAmtDettaglioPanel amtPromozioneXXL = " + amtPromozioneXXL);

    add(
        new FdCTitoloPanel(
            "titoloTitolare", getString("AbbonamentiAmtDettaglioPanel.titoloTitolare")));

    addOrReplace(
        new FdCTextField<>(
                "nominativoTitolare",
                amtPromozioneXXL.bind("titolare.nominativo"),
                AbbonamentiAmtDettaglioPanel.this)
            .setEnabled(false));

    addOrReplace(
        new FdCTextField<>(
                "codiceFiscaleTitolare",
                amtPromozioneXXL.bind("titolare.codiceFiscale"),
                AbbonamentiAmtDettaglioPanel.this)
            .setEnabled(false));

    addOrReplace(
        new FdCTextField<>(
                "dataNascitaTitolare",
                amtPromozioneXXL.bind("titolare.dataNascita"),
                AbbonamentiAmtDettaglioPanel.this)
            .setEnabled(false)
            .setVisible(
                LabelFdCUtil.checkIfNotNull(
                    amtPromozioneXXL.getObject().getTitolare().getDataNascita())));

    addOrReplace(
        new FdCTextField<>(
                "luogoNascitaTitolare",
                amtPromozioneXXL.bind("titolare.luogoNascita"),
                AbbonamentiAmtDettaglioPanel.this)
            .setEnabled(false)
            .setVisible(
                LabelFdCUtil.checkIfNotNull(
                    amtPromozioneXXL.getObject().getTitolare().getLuogoNascita())));

    addOrReplace(
        new FdCTextField<>(
                "cityPass", amtPromozioneXXL.bind("cityPass"), AbbonamentiAmtDettaglioPanel.this)
            .setEnabled(false)
            .setVisible(!Strings.isNullOrEmpty(amtPromozioneXXL.getObject().getCityPass())));

    WebMarkupContainer containerRichiedente = new WebMarkupContainer("containerRichiedente");

    containerRichiedente.add(
        new FdCTitoloPanel(
            "titoloRichiedente", getString("AbbonamentiAmtDettaglioPanel.titoloRichiedente")));

    containerRichiedente.addOrReplace(
        new FdCTextField<>(
                "nominativoRichiedente",
                amtPromozioneXXL.bind("richiedente.nominativo"),
                AbbonamentiAmtDettaglioPanel.this)
            .setEnabled(false));

    containerRichiedente.addOrReplace(
        new FdCTextField<>(
                "codiceFiscaleRichiedente",
                amtPromozioneXXL.bind("richiedente.codiceFiscale"),
                AbbonamentiAmtDettaglioPanel.this)
            .setEnabled(false));

    containerRichiedente.addOrReplace(
        new FdCTextField<>(
                "dataNascitaRichiedente",
                amtPromozioneXXL.bind("richiedente.dataNascita"),
                AbbonamentiAmtDettaglioPanel.this)
            .setEnabled(false));

    containerRichiedente.addOrReplace(
        new FdCTextField<>(
                "luogoNascitaRichiedente",
                amtPromozioneXXL.bind("richiedente.luogoNascita"),
                AbbonamentiAmtDettaglioPanel.this)
            .setEnabled(false)
            .setVisible(
                LabelFdCUtil.checkIfNotNull(
                    amtPromozioneXXL.getObject().getRichiedente().getLuogoNascita())));

    containerRichiedente.setVisible(
        !amtPromozioneXXL
            .getObject()
            .getRichiedente()
            .getCodiceFiscale()
            .equalsIgnoreCase(amtPromozioneXXL.getObject().getTitolare().getCodiceFiscale()));
    addOrReplace(containerRichiedente);

    WebMarkupContainer containerIsee = new WebMarkupContainer("containerIsee");
    FdCTextField importoIsee =
        new FdCTextField(
            "importoIsee", amtPromozioneXXL.bind("importoIsee"), AbbonamentiAmtDettaglioPanel.this);
    importoIsee.setEnabled(false);
    importoIsee.setVisible(
        LabelFdCUtil.checkIfNotNull(amtPromozioneXXL.getObject().getImportoIsee()));
    containerIsee.addOrReplace(importoIsee);

    FdCTextField protocolloIsee =
        new FdCTextField(
            "protocolloIsee",
            amtPromozioneXXL.bind("protocolloIsee"),
            AbbonamentiAmtDettaglioPanel.this);
    protocolloIsee.setEnabled(false);
    protocolloIsee.setVisible(
        LabelFdCUtil.checkIfNotNull(amtPromozioneXXL.getObject().getProtocolloIsee()));
    containerIsee.addOrReplace(protocolloIsee);

    WebMarkupContainer isseNonPresente = new WebMarkupContainer("isseNonPresente");
    isseNonPresente.setVisible(
        LabelFdCUtil.checkIfNull(amtPromozioneXXL.getObject().getImportoIsee())
            && amtPromozioneXXL.getObject().isResidente());
    containerIsee.addOrReplace(isseNonPresente);

    WebMarkupContainer issePresenteMaFuoriRange =
        new WebMarkupContainer("issePresenteMaFuoriRange");
    issePresenteMaFuoriRange.setVisible(
        LabelFdCUtil.checkIfNotNull(amtPromozioneXXL.getObject().getImportoIsee())
            && !amtPromozioneXXL.getObject().isAgevolazione());
    containerIsee.addOrReplace(issePresenteMaFuoriRange);

    WebMarkupContainer issePresenteNelRange = new WebMarkupContainer("issePresenteNelRange");
    issePresenteNelRange.setVisible(
        LabelFdCUtil.checkIfNotNull(amtPromozioneXXL.getObject().getImportoIsee())
            && amtPromozioneXXL.getObject().isAgevolazione());
    containerIsee.addOrReplace(issePresenteNelRange);

    WebMarkupContainer iseeForesto = new WebMarkupContainer("iseeForesto");
    iseeForesto.setVisible(!amtPromozioneXXL.getObject().isResidente());
    containerIsee.addOrReplace(iseeForesto);

    WebMarkupContainer alertGratuita = new WebMarkupContainer("alertGratuita");
    alertGratuita.setVisible(isAlertGratuitaVisible(amtPromozioneXXL.getObject()));
    containerIsee.addOrReplace(alertGratuita);

    boolean isRinnovo = PageUtil.isStringValid(amtPromozioneXXL.getObject().getUrlRinnovo());

    ExternalLink linkRicaricaCriptazione =
        new ExternalLink(
            "linkRicaricaCriptazione", createUrlCriptato(amtPromozioneXXL.getObject(), true));
    linkRicaricaCriptazione.setVisible(
        isRinnovo && isResidenteAndMinore(amtPromozioneXXL.getObject()));
    addOrReplace(linkRicaricaCriptazione);

    ExternalLink linkNuovaTesseraCriptazione =
        new ExternalLink(
            "linkNuovaTesseraCriptazione", createUrlCriptato(amtPromozioneXXL.getObject(), false));
    linkNuovaTesseraCriptazione.setVisible(
        !isRinnovo && isResidenteAndMinore(amtPromozioneXXL.getObject()));
    addOrReplace(linkNuovaTesseraCriptazione);

    addOrReplace(containerIsee);

    addOrReplace(creaBottoneIndietro());
  }

  // torna false se minore < 14 anni e residente
  private boolean isResidenteAndMinore(AmtPromozioneXXL abbonmentoXXL) {
    if (!abbonmentoXXL.isResidente()) {
      return true;
    }

    int eta = LocalDateUtil.calcolaEta(abbonmentoXXL.getTitolare().getDataNascita());
    return Integer.compare(eta, 14) < 0 ? false : true;
  }

  // Da errore in caso non esista la data di nascita
  private boolean isAlertGratuitaVisible(AmtPromozioneXXL abbonmentoXXL) {

    int eta = LocalDateUtil.calcolaEta(abbonmentoXXL.getTitolare().getDataNascita());

    return (Integer.compare(eta, 14) < 0 || Integer.compare(eta, 70) >= 0)
        && Strings.isNullOrEmpty(abbonmentoXXL.getCityPass());
  }

  private String createUrlCriptato(AmtPromozioneXXL amtPromozioneXXL, boolean isRinnovo) {

    log.debug("createUrlCriptato amtPromozioneXXL = " + amtPromozioneXXL);

    String url = "";

    try {

      ModelMapper mapper = new ModelMapper();
      AmtPromozioneXXLSmall amtDaCriptare =
          mapper.map(amtPromozioneXXL, AmtPromozioneXXLSmall.class);

      String obj = new Gson().toJson(amtDaCriptare);
      String objCriptato = AmtEncrypterUtil.encrypt(obj);

      url =
          isRinnovo
              ? String.format("%s&citt=%s", amtPromozioneXXL.getUrlRinnovo(), objCriptato)
              : String.format("%s?citt=%s", amtPromozioneXXL.getUrlNuovaTessera(), objCriptato);

    } catch (InvalidKeyException
        | NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidAlgorithmParameterException
        | BadPaddingException
        | IllegalBlockSizeException
        | UnsupportedEncodingException e) {
      log.error("Errore createUrlCriptato = " + e.getMessage());
      // e.printStackTrace();
    }

    log.debug("createUrlCriptato url = " + url);

    return url;
  }

  private String createUrlCriptatoSenzaSmall(AmtPromozioneXXL amtPromozioneXXL, boolean isRinnovo) {

    log.debug("createUrlCriptatoSenzaSmall amtPromozioneXXL = " + amtPromozioneXXL);

    String url = "";

    try {

      String obj = new Gson().toJson(amtPromozioneXXL);
      String objCriptato = AmtEncrypterUtil.encrypt(obj);

      log.debug("objCriptato = " + objCriptato);

      url =
          isRinnovo
              ? String.format("%s&citt=%s", amtPromozioneXXL.getUrlRinnovo(), objCriptato)
              : String.format("%s?citt=%s", amtPromozioneXXL.getUrlNuovaTessera(), objCriptato);

    } catch (InvalidKeyException
        | NoSuchPaddingException
        | NoSuchAlgorithmException
        | InvalidAlgorithmParameterException
        | BadPaddingException
        | IllegalBlockSizeException
        | UnsupportedEncodingException e) {
      log.error("Errore createUrlCriptatoSenzaSmall = " + e.getMessage());
    }

    log.debug("createUrlCriptatoSenzaSmall url = " + url);

    return url;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro() {
    FdCButtonBootstrapAjaxLink btnIndietro =
        new FdCButtonBootstrapAjaxLink<Object>("btnIndietro", Type.Default) {

          private static final long serialVersionUID = 1758735357368618861L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            setResponsePage(new AbbonamentiAmtPage());
          }
        };

    btnIndietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "AbbonamentiAmtDettaglioPanel.btnIndietro",
                    AbbonamentiAmtDettaglioPanel.this)));

    return btnIndietro;
  }
}
