package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.PrestazioneDaErogareEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.StatodomandaPrestazioneEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCFBody;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl.InpsModiHelper;
import it.liguriadigitale.ponmetro.portale.pojo.amt.TesseraAmtEsteso;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.AbbonamentiAmtDettaglioPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto.AmtPromozioneXXL;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto.DatiIsee;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.dto.DatiPersonaliAmt;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import java.time.LocalDate;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class AbbonamentiAmtPanel extends BasePanel {

  private static final long serialVersionUID = -8681270881644900461L;

  private static final String ICON_ABBONAMENTI = "color-cyan col-3 icon-abbonamenti";
  private static final String ICON_ABBONAMENTI_SCADUTI =
      "color-fc-secondary col-3 icon-abbonamenti";
  private static final String ICON_ABBONAMENTI_GENERICA = "color-black col-3 icon-abbonamenti";
  private static final String ICON_ABBONAMENTI_NUOVO = "color-cyan col-3 icon-abbonamenti";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public AbbonamentiAmtPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<TesseraAmtEsteso> listaTesseraAmtEsteso = (List<TesseraAmtEsteso>) dati;

    String promozioneXXL =
        ServiceLocator.getInstance().getServiziAbbonamentiAmt().getValoreDaDb("AMT_PROMOZIONE_XXL");

    PageableListView<TesseraAmtEsteso> listView =
        new PageableListView<TesseraAmtEsteso>("box", listaTesseraAmtEsteso, 4) {

          private static final long serialVersionUID = 3370611021285450019L;

          @Override
          protected void populateItem(ListItem<TesseraAmtEsteso> item) {
            final TesseraAmtEsteso tesseraEsteso = item.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(new AttributeAppender("class", " " + ICON_ABBONAMENTI));
            item.addOrReplace(icona);

            item.addOrReplace(
                new NotEmptyLabel("descrizioneTessera", tesseraEsteso.getDescrizioneTessera()));

            item.addOrReplace(new NotEmptyLabel("nominativo", tesseraEsteso.getNominativo()));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "cityPass", tesseraEsteso.getCityPass(), AbbonamentiAmtPanel.this));

            Integer prezzo = tesseraEsteso.getPrezzo();
            Double prezzoInDouble = null;
            if (LabelFdCUtil.checkIfNotNull(prezzo)) {
              prezzoInDouble = Double.valueOf(prezzo);
            }
            item.addOrReplace(
                new AmtCardLabel<>("prezzo", prezzoInDouble, AbbonamentiAmtPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "inizioValidita", tesseraEsteso.getInizioValidita(), AbbonamentiAmtPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "fineValidita", tesseraEsteso.getFineValidita(), AbbonamentiAmtPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "inizioAgevolazione",
                    tesseraEsteso.getInizioAgevolazione(),
                    AbbonamentiAmtPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>(
                    "fineAgevolazione",
                    tesseraEsteso.getFineAgevolazione(),
                    AbbonamentiAmtPanel.this));

            item.addOrReplace(
                new AmtCardLabel<>("stato", getStato(tesseraEsteso), AbbonamentiAmtPanel.this));

            ExternalLink urlRinnovo = new ExternalLink("urlRinnovo", tesseraEsteso.getUrlRinnovo());
            urlRinnovo.setVisible(
                PageUtil.isStringValid(tesseraEsteso.getUrlRinnovo())
                    && PageUtil.isStringValid(promozioneXXL)
                    && promozioneXXL.equalsIgnoreCase("NO"));
            item.addOrReplace(urlRinnovo);

            item.addOrReplace(
                new AmtCardLabel<>(
                    "messaggioNuovaTessera",
                    tesseraEsteso.getMessaggioNuovaTessera(),
                    AbbonamentiAmtPanel.this));

            ExternalLink urlNuovaTessera =
                new ExternalLink("urlNuovaTessera", tesseraEsteso.getUrlNuovaTessera());
            urlNuovaTessera.setVisible(
                PageUtil.isStringValid(tesseraEsteso.getUrlNuovaTessera())
                    && PageUtil.isStringValid(promozioneXXL)
                    && promozioneXXL.equalsIgnoreCase("NO"));
            item.addOrReplace(urlNuovaTessera);

            item.addOrReplace(creaBtnNuovaTesseraRinnovoTessera(tesseraEsteso, promozioneXXL));
          }
        };

    listView.setRenderBodyOnly(true);
    listView.setVisible(checkPresenzaAbbonamenti(listaTesseraAmtEsteso));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationAbbonamenti", listView);
    paginazioneFascicolo.setVisible(
        checkPresenzaAbbonamenti(listaTesseraAmtEsteso) && listaTesseraAmtEsteso.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkPresenzaAbbonamenti(List<TesseraAmtEsteso> listaTesseraAmtEsteso) {
    return LabelFdCUtil.checkIfNotNull(listaTesseraAmtEsteso);
  }

  private String getStato(TesseraAmtEsteso tesseraEsteso) {
    String stato = "";
    if (LabelFdCUtil.checkIfNotNull(tesseraEsteso)
        && LabelFdCUtil.checkIfNotNull(tesseraEsteso.getFineValidita())) {
      if (tesseraEsteso.getFineValidita().isBefore(LocalDate.now())) {
        stato = getString("AbbonamentiAmtPanel.scaduto");
      } else {
        stato = getString("AbbonamentiAmtPanel.valido");
      }
    } else {
      stato = getString("AbbonamentiAmtPanel.nuovo");
    }

    return stato;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnNuovaTesseraRinnovoTessera(
      TesseraAmtEsteso tesseraEsteso, String promozioneXXL) {
    FdCButtonBootstrapAjaxLink<Object> btnNuovaTesseraRinnovoTessera =
        new FdCButtonBootstrapAjaxLink<Object>("btnNuovaTesseraRinnovoTessera", Type.Primary) {

          private static final long serialVersionUID = 1325507980744884626L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            target.add(AbbonamentiAmtPanel.this);

            AmtPromozioneXXL amtPromozioneXXL = creteAmtPromozioneXXL(tesseraEsteso, promozioneXXL);

            CompoundPropertyModel<AmtPromozioneXXL> datiAmtXXL =
                new CompoundPropertyModel<>(amtPromozioneXXL);

            setResponsePage(new AbbonamentiAmtDettaglioPage(datiAmtXXL));
          }
        };

    // btnNuovaTesseraRinnovoTessera.setVisible(true);
    btnNuovaTesseraRinnovoTessera.setVisible(
        PageUtil.isStringValid(promozioneXXL)
            && promozioneXXL.equalsIgnoreCase("SI")
            && LabelFdCUtil.checkIfNotNull(tesseraEsteso)
            && (PageUtil.isStringValid(tesseraEsteso.getUrlNuovaTessera())
                || (PageUtil.isStringValid(tesseraEsteso.getUrlRinnovo()))));

    if (PageUtil.isStringValid(tesseraEsteso.getUrlNuovaTessera())) {
      btnNuovaTesseraRinnovoTessera.setLabel(
          Model.of(getString("AbbonamentiAmtPanel.urlNuovaTessera")));
    } else if (PageUtil.isStringValid(tesseraEsteso.getUrlRinnovo())) {
      btnNuovaTesseraRinnovoTessera.setLabel(
          Model.of(getString("AbbonamentiAmtPanel.vaiAlSitoAmt")));
    }

    return btnNuovaTesseraRinnovoTessera;
  }

  // TODO capire devo vedere se titolare e minorenne
  private ConsultazioneIndicatoreCF200 indicatoreIseeTitolare(
      String codiceFiscaleTitolare, String tipoIndicatore) throws BusinessException {

    ConsultazioneIndicatoreCFBody bodyIndicatoreIseeModi =
        InpsModiHelper.createConsultazioneIndicatoreCFBody(
            codiceFiscaleTitolare,
            PrestazioneDaErogareEnum.A1_16,
            StatodomandaPrestazioneEnum.EROGATA,
            tipoIndicatore);

    ConsultazioneIndicatoreCF200 indicatoreIseeModi =
        ServiceLocator.getInstance()
            .getServiziINPSModi()
            .consultazioneIndicatoreCF(bodyIndicatoreIseeModi);

    return indicatoreIseeModi;
  }

  private AmtPromozioneXXL creteAmtPromozioneXXL(
      TesseraAmtEsteso tesseraEsteso, String promozioneXXL) {

    log.debug("CP creteAmtPromozioneXXL tesseraEsteso= " + tesseraEsteso);

    AmtPromozioneXXL amtPromozioneXXL = new AmtPromozioneXXL();
    amtPromozioneXXL.setPromozioneXXLAttiva(promozioneXXL);

    amtPromozioneXXL.setCityPass(tesseraEsteso.getCityPass());

    if (PageUtil.isStringValid(tesseraEsteso.getUrlNuovaTessera())) {
      amtPromozioneXXL.setUrlNuovaTessera(tesseraEsteso.getUrlNuovaTessera());
    }

    if (PageUtil.isStringValid(tesseraEsteso.getUrlRinnovo())) {
      amtPromozioneXXL.setUrlRinnovo(tesseraEsteso.getUrlRinnovo());
    }

    DatiPersonaliAmt datiRichiedente = new DatiPersonaliAmt();
    datiRichiedente.setCognome(getUtente().getCognome());
    datiRichiedente.setNome(getUtente().getNome());
    datiRichiedente.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
    datiRichiedente.setLuogoNascita(getUtente().getLuogoDiNascita());
    datiRichiedente.setDataNascita(getUtente().getDataDiNascita());
    datiRichiedente.setNominativo(
        getUtente().getCognome().concat(" ").concat(getUtente().getNome()));
    amtPromozioneXXL.setRichiedente(datiRichiedente);

    DatiPersonaliAmt datiTitolare = new DatiPersonaliAmt();
    datiTitolare.setCodiceFiscale(tesseraEsteso.getCf());
    datiTitolare.setNominativo(tesseraEsteso.getNominativo());
    datiTitolare.setCognome(tesseraEsteso.getCognome());
    datiTitolare.setNome(tesseraEsteso.getNome());

    boolean isFiglio =
        !datiTitolare.getCodiceFiscale().equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore());

    if (isFiglio) {
      Residente figlio = getFiglio(tesseraEsteso.getCf());

      if (LabelFdCUtil.checkIfNotNull(figlio)) {
        amtPromozioneXXL.setResidente(true);
        datiTitolare.setLuogoNascita(figlio.getCpvHasBirthPlace().getClvCity());
        datiTitolare.setDataNascita(figlio.getCpvDateOfBirth());
        boolean isMinore = LocalDateUtil.isMaggiorenne(figlio.getCpvDateOfBirth());
        DatiIsee datiIsee = getDatiIsee(datiTitolare.getCodiceFiscale(), isFiglio, isMinore);
        amtPromozioneXXL.setImportoIsee(datiIsee.getValore());
        amtPromozioneXXL.setProtocolloIsee(datiIsee.getProtocollo());
        amtPromozioneXXL.setAgevolazione(isAgevolazione(amtPromozioneXXL.isResidente(), datiIsee));
      } else {
        datiTitolare.setDataNascita(
            CodiceFiscaleValidatorUtil.getDataNascitaFromCf(datiTitolare.getCodiceFiscale()));
        amtPromozioneXXL.setResidente(false);
        amtPromozioneXXL.setAgevolazione(false);
      }

    } else {

      datiTitolare.setLuogoNascita(getUtente().getLuogoDiNascita());
      datiTitolare.setDataNascita(getUtente().getDataDiNascita());
      amtPromozioneXXL.setResidente(getUtente().isResidente());

      if (amtPromozioneXXL.isResidente()) {
        DatiIsee datiIsee = getDatiIsee(datiTitolare.getCodiceFiscale(), false, false);
        amtPromozioneXXL.setImportoIsee(datiIsee.getValore());
        amtPromozioneXXL.setProtocolloIsee(datiIsee.getProtocollo());
        amtPromozioneXXL.setAgevolazione(isAgevolazione(amtPromozioneXXL.isResidente(), datiIsee));
      } else {
        amtPromozioneXXL.setAgevolazione(false);
      }
    }

    amtPromozioneXXL.setTitolare(datiTitolare);
    return amtPromozioneXXL;
  }

  private DatiIsee getDatiIsee(String codiceFiscaleTitolare, boolean isFiglio, boolean isMinore) {
    String tipoIsee = isFiglio && isMinore ? "Minorenne" : "Ordinario";

    DatiIsee datiIsee = new DatiIsee();

    try {

      ConsultazioneIndicatoreCF200 iseeTitolare =
          indicatoreIseeTitolare(codiceFiscaleTitolare, tipoIsee);

      if (LabelFdCUtil.checkIfNotNull(iseeTitolare)) {
        String iseeValore =
            iseeTitolare
                .getConsultazioneIndicatoreResponse()
                .getConsultazioneIndicatoreResult()
                .getXmlEsitoIndicatoreDecoded()
                .getIndicatore()
                .getISEE();

        datiIsee.setValore(Double.parseDouble(iseeValore));

        String protocolloIsee =
            iseeTitolare
                .getConsultazioneIndicatoreResponse()
                .getConsultazioneIndicatoreResult()
                .getXmlEsitoIndicatoreDecoded()
                .getIndicatore()
                .getProtocolloDSU();
        datiIsee.setProtocollo(protocolloIsee);
      }

    } catch (BusinessException e1) {
      log.error("Errore isee in amt : " + e1.getMessage());
      return null;
    }

    return datiIsee;
  }

  private Residente getFiglio(String codiceFiscaleFiglio) {
    try {
      return ServiceLocator.getInstance()
          .getServizioDemografico()
          .getDatiResidente(codiceFiscaleFiglio);
    } catch (BusinessException | ApiException e) {
      log.debug("[getFiglio] Impossibile recuperare il figlio");
      return null;
    }
  }

  /*
   * Se Cittadino non residente non accede all'agevolazione AMT. Se Figlio
   * prendiamo ISEE Minorenne Altrimenti Ordinario. Torna true solo se è uguale a
   * 12000€
   */
  private boolean isAgevolazione(boolean isResidente, DatiIsee datiIsee) {
    if (!isResidente || datiIsee == null || datiIsee.getValore() == null) {
      return false;
    }

    String valoreIseeDb =
        ServiceLocator.getInstance()
            .getServiziAbbonamentiAmt()
            .getValoreDaDb("AMT_PROMOZIONE_XXL_IMPORTO_ISEE");
    Double valoreMassimoIsee = Double.parseDouble(valoreIseeDb);

    return datiIsee.getValore().compareTo(valoreMassimoIsee) <= 0;
  }
}
