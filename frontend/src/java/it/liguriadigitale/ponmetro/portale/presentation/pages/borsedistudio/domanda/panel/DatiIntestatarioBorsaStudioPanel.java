package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio.domanda.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica.AccettazioneNucleoIseeAnagraficoEnum;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.borsestudio.PraticaBorseStudioExtend;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.borsedistudio.ComponenteNucleoAnagraficoDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class DatiIntestatarioBorsaStudioPanel extends BasePanel {

  private static final long serialVersionUID = 8790327183700582755L;

  private int index;

  public DatiIntestatarioBorsaStudioPanel(
      String id, PraticaBorseStudioExtend datiDomanda, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    fillDati(datiDomanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    PraticaBorseStudioExtend datiDomanda = (PraticaBorseStudioExtend) dati;

    WebMarkupContainer containerDati = new WebMarkupContainer("containerDati");

    WebMarkupContainer containerDatiIseeIntestatario =
        new WebMarkupContainer("containerDatiIseeIntestatario");

    WebMarkupContainer containerIntestatarioLoggato =
        new WebMarkupContainer("containerIntestatarioLoggato");
    containerIntestatarioLoggato.setVisible(false);
    containerIntestatarioLoggato.setOutputMarkupId(true);
    containerIntestatarioLoggato.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerIntestatarioLoggato);

    WebMarkupContainer containerIseeDifformita = new WebMarkupContainer("containerIseeDifformita");
    containerIseeDifformita.setVisible(false);
    containerIseeDifformita.setOutputMarkupId(true);
    containerIseeDifformita.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerIseeDifformita);

    SiNoDropDownChoice accettazioneIntestatarioSiNo =
        new SiNoDropDownChoice(
            "accettazioneIntestatarioSiNo",
            new PropertyModel<String>(datiDomanda, "accettazioneIntestatarioSiNo"));
    accettazioneIntestatarioSiNo.setLabel(Model.of("Dichiarazione di essere l'intestatario borsa"));
    accettazioneIntestatarioSiNo.setOutputMarkupId(true);
    accettazioneIntestatarioSiNo.setOutputMarkupPlaceholderTag(true);
    accettazioneIntestatarioSiNo.setRequired(true);

    containerIntestatarioLoggato.addOrReplace(accettazioneIntestatarioSiNo);

    WebMarkupContainer lblAccettazioneIntestatario =
        new WebMarkupContainer("lblAccettazioneIntestatario");

    lblAccettazioneIntestatario.add(
        new AttributeModifier("for", accettazioneIntestatarioSiNo.getMarkupId()));
    containerIntestatarioLoggato.addOrReplace(lblAccettazioneIntestatario);

    WebMarkupContainer containerIseeNonPresente =
        new WebMarkupContainer("containerIseeNonPresente");
    containerIseeNonPresente.setVisible(false);
    containerIseeNonPresente.setOutputMarkupId(true);
    containerIseeNonPresente.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerIseeNonPresente);

    WebMarkupContainer containerNucleoIseeDiversoNucleoAnagrafico =
        new WebMarkupContainer("containerNucleoIseeDiversoNucleoAnagrafico");
    containerNucleoIseeDiversoNucleoAnagrafico.setVisible(false);
    containerNucleoIseeDiversoNucleoAnagrafico.setOutputMarkupId(true);
    containerNucleoIseeDiversoNucleoAnagrafico.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerNucleoIseeDiversoNucleoAnagrafico);

    WebMarkupContainer containerNonAccetto = new WebMarkupContainer("containerNonAccetto");
    containerNonAccetto.setVisible(false);
    containerNonAccetto.setOutputMarkupId(true);
    containerNonAccetto.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerNonAccetto);

    WebMarkupContainer containerAccetto = new WebMarkupContainer("containerAccetto");
    containerAccetto.setVisible(false);
    containerAccetto.setOutputMarkupId(true);
    containerAccetto.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerAccetto);

    FdCTextField importoIsee =
        new FdCTextField(
            "importoIsee",
            new PropertyModel(datiDomanda, "importoIsee"),
            DatiIntestatarioBorsaStudioPanel.this);
    importoIsee.setEnabled(false);
    containerDatiIseeIntestatario.addOrReplace(importoIsee);

    FdCTextField dataPresentazioneIsee =
        new FdCTextField(
            "dataPresentazioneIsee",
            new PropertyModel(datiDomanda, "dataPresentazioneIsee"),
            DatiIntestatarioBorsaStudioPanel.this);
    dataPresentazioneIsee.setEnabled(false);
    containerDatiIseeIntestatario.addOrReplace(dataPresentazioneIsee);

    FdCTextField dataValiditaIsee =
        new FdCTextField(
            "dataValiditaIsee",
            new PropertyModel(datiDomanda, "dataValiditaIsee"),
            DatiIntestatarioBorsaStudioPanel.this);
    dataValiditaIsee.setEnabled(false);
    containerDatiIseeIntestatario.addOrReplace(dataValiditaIsee);

    FdCTextField iseeDifforme =
        new FdCTextField(
            "iseeDifformeValue",
            new PropertyModel(datiDomanda, "iseeDifformeValue"),
            DatiIntestatarioBorsaStudioPanel.this);
    iseeDifforme.setEnabled(false);
    containerDatiIseeIntestatario.addOrReplace(iseeDifforme);

    FdCTextField nomeIntestatarioBorsa =
        new FdCTextField(
            "nomeIntestatarioBorsa",
            new PropertyModel(datiDomanda, "nomeIntestatarioBorsa"),
            DatiIntestatarioBorsaStudioPanel.this);
    nomeIntestatarioBorsa.setEnabled(false);
    nomeIntestatarioBorsa.setOutputMarkupId(true);
    nomeIntestatarioBorsa.setOutputMarkupPlaceholderTag(true);
    containerDati.addOrReplace(nomeIntestatarioBorsa);

    FdCTextField cognomeIntestatarioBorsa =
        new FdCTextField(
            "cognomeIntestatarioBorsa",
            new PropertyModel(datiDomanda, "cognomeIntestatarioBorsa"),
            DatiIntestatarioBorsaStudioPanel.this);
    cognomeIntestatarioBorsa.setEnabled(false);
    cognomeIntestatarioBorsa.setOutputMarkupId(true);
    cognomeIntestatarioBorsa.setOutputMarkupPlaceholderTag(true);
    containerDati.addOrReplace(cognomeIntestatarioBorsa);

    FdCTextField codiceIntestatarioBorsa =
        new FdCTextField(
            "codiceIntestatarioBorsa",
            new PropertyModel(datiDomanda, "codiceIntestatarioBorsa"),
            DatiIntestatarioBorsaStudioPanel.this);
    codiceIntestatarioBorsa.setEnabled(false);
    codiceIntestatarioBorsa.setOutputMarkupId(true);
    codiceIntestatarioBorsa.setOutputMarkupPlaceholderTag(true);
    containerDati.addOrReplace(codiceIntestatarioBorsa);

    containerDati.setOutputMarkupId(true);
    containerDati.setOutputMarkupPlaceholderTag(true);

    addOrReplace(containerDati);

    containerDatiIseeIntestatario.setVisible(false);
    containerDatiIseeIntestatario.setOutputMarkupId(true);
    containerDatiIseeIntestatario.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerDatiIseeIntestatario);

    List<NucleoFamiliareComponenteNucleoInner> listaComponentiNucleoIsee =
        new ArrayList<NucleoFamiliareComponenteNucleoInner>();
    if (LabelFdCUtil.checkIfNotNull(datiDomanda.getListaComponentiIsee())) {
      listaComponentiNucleoIsee = datiDomanda.getListaComponentiIsee();
    }

    ListView<NucleoFamiliareComponenteNucleoInner> listaNucleoIsee =
        new ListView<NucleoFamiliareComponenteNucleoInner>(
            "listaNucleoIsee", listaComponentiNucleoIsee) {

          private static final long serialVersionUID = 5048023465596077912L;

          @Override
          protected void populateItem(ListItem<NucleoFamiliareComponenteNucleoInner> itemIsee) {
            final NucleoFamiliareComponenteNucleoInner membroIsee = itemIsee.getModelObject();

            String nomeCognome = "";
            if (PageUtil.isStringValid(membroIsee.getNome())) {
              nomeCognome = membroIsee.getNome().concat(" ");
            }
            if (PageUtil.isStringValid(membroIsee.getCognome())) {
              nomeCognome = nomeCognome.concat(membroIsee.getCognome());
            }

            Label nominativoIsee = new Label("nominativoIsee", nomeCognome);
            itemIsee.addOrReplace(nominativoIsee);
          }
        };
    containerNucleoIseeDiversoNucleoAnagrafico.addOrReplace(listaNucleoIsee);

    List<Residente> listaComponentiNucleoAnagrafe = new ArrayList<Residente>();
    if (LabelFdCUtil.checkIfNotNull(datiDomanda.getListaComponentiAnagrafe())) {
      listaComponentiNucleoAnagrafe = datiDomanda.getListaComponentiAnagrafe();
    }

    ListView<Residente> listaNucleoAnagrafe =
        new ListView<Residente>("listaNucleoAnagrafe", listaComponentiNucleoAnagrafe) {

          private static final long serialVersionUID = -391099289120474175L;

          @Override
          protected void populateItem(ListItem<Residente> itemIsee) {
            final Residente membroAnagrafe = itemIsee.getModelObject();

            String nomeCognome = "";

            if (LabelFdCUtil.checkIfNotNull(membroAnagrafe.getRdfsLabel())) {
              nomeCognome = membroAnagrafe.getRdfsLabel();
            }

            Label nominativoAnagrafe = new Label("nominativoAnagrafe", nomeCognome);
            itemIsee.addOrReplace(nominativoAnagrafe);
          }
        };
    containerNucleoIseeDiversoNucleoAnagrafico.addOrReplace(listaNucleoAnagrafe);

    SiNoDropDownChoice accettazioneNucleiSiNo =
        new SiNoDropDownChoice(
            "accettazioneNucleiSiNo",
            new PropertyModel<String>(datiDomanda, "accettazioneNucleiSiNo"));
    accettazioneNucleiSiNo.setLabel(
        Model.of("Dichiarazione nucleo familiare dichiarato nella DSU è corretto"));
    accettazioneNucleiSiNo.setOutputMarkupId(true);
    accettazioneNucleiSiNo.setOutputMarkupPlaceholderTag(true);
    accettazioneNucleiSiNo.setRequired(true);

    accettazioneNucleiSiNo.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = 5640007522188279590L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {
            log.debug(
                "CP click scelta accettazioneNucleiSiNo = " + accettazioneNucleiSiNo.getValue());

            if (accettazioneNucleiSiNo.getValue().equalsIgnoreCase("0")) {

              datiDomanda.setAccettazioneNuclei(AccettazioneNucleoIseeAnagraficoEnum.ACCETTA);

              containerAccetto.setVisible(true);
              containerNonAccetto.setVisible(false);

            } else {

              datiDomanda.setAccettazioneNuclei(AccettazioneNucleoIseeAnagraficoEnum.NON_ACCETTA);

              containerAccetto.setVisible(false);
              containerNonAccetto.setVisible(true);
            }

            target.add(containerAccetto, containerNonAccetto);
          }
        });

    containerNucleoIseeDiversoNucleoAnagrafico.addOrReplace(accettazioneNucleiSiNo);

    ComponenteNucleoAnagraficoDropDownChoice nucleoAnagrafe =
        new ComponenteNucleoAnagraficoDropDownChoice(
            "nucleoAnagrafe", Model.of(new Residente()), datiDomanda.getListaComponentiAnagrafe());
    nucleoAnagrafe.setRequired(true);
    nucleoAnagrafe.setLabel(Model.of("Scegli l'interstatario della borsa di studio"));

    nucleoAnagrafe.add(
        new AjaxFormComponentUpdatingBehavior("change") {

          private static final long serialVersionUID = -3336789384245437306L;

          @Override
          protected void onUpdate(AjaxRequestTarget target) {

            datiDomanda.setAccettazioneNuclei(null);
            accettazioneNucleiSiNo.setModelObject(null);

            datiDomanda.setNomeIntestatarioBorsa(nucleoAnagrafe.getModelObject().getCpvGivenName());
            datiDomanda.setCognomeIntestatarioBorsa(
                nucleoAnagrafe.getModelObject().getCpvFamilyName());
            datiDomanda.setCodiceIntestatarioBorsa(nucleoAnagrafe.getModelObject().getCpvTaxCode());
            datiDomanda.setDataNascitaIntestatarioBorsa(
                nucleoAnagrafe.getModelObject().getCpvDateOfBirth());

            // chiamo isee
            try {
              ServiceLocator.getInstance()
                  .getServiziBorseDiStudio()
                  .setDatiIseePerIntestatarioBorsa(nucleoAnagrafe.getModelObject(), datiDomanda);

              if (checkIseePresentato(datiDomanda)) {
                containerDatiIseeIntestatario.setVisible(true);
              } else {
                containerDatiIseeIntestatario.setVisible(false);
              }

              if (checkIseePresentato(datiDomanda)
                  && !checkNucleoIseeNucleoAnagrafico(datiDomanda)
                  && LabelFdCUtil.checkIfNull(datiDomanda.getAccettazioneNuclei())) {
                containerNucleoIseeDiversoNucleoAnagrafico.setVisible(true);
              } else {
                containerNucleoIseeDiversoNucleoAnagrafico.setVisible(false);
              }

              if (!checkIseePresentato(datiDomanda)) {
                containerIseeNonPresente.setVisible(true);
              } else {
                containerIseeNonPresente.setVisible(false);

                if (datiDomanda
                    .getCodiceIntestatarioBorsa()
                    .equalsIgnoreCase(getUtente().getCodiceFiscaleOperatore())) {
                  accettazioneIntestatarioSiNo.setVisible(true);
                  accettazioneIntestatarioSiNo.setRequired(true);
                  containerIntestatarioLoggato.setVisible(true);
                } else {
                  accettazioneIntestatarioSiNo.setVisible(false);
                  accettazioneIntestatarioSiNo.setRequired(false);
                  containerIntestatarioLoggato.setVisible(false);
                }

                if (datiDomanda.getIseeDifforme()) {
                  containerIseeDifformita.setVisible(true);
                } else {
                  containerIseeDifformita.setVisible(false);
                }
              }

              if (LabelFdCUtil.checkIfNotNull(datiDomanda.getAccettazioneNuclei())
                  && datiDomanda
                      .getAccettazioneNuclei()
                      .equals(AccettazioneNucleoIseeAnagraficoEnum.NON_ACCETTA)) {
                containerNonAccetto.setVisible(true);
              } else {
                containerNonAccetto.setVisible(false);
              }

              if (LabelFdCUtil.checkIfNotNull(datiDomanda.getAccettazioneNuclei())
                  && datiDomanda
                      .getAccettazioneNuclei()
                      .equals(AccettazioneNucleoIseeAnagraficoEnum.ACCETTA)) {
                containerAccetto.setVisible(true);
              } else {
                containerAccetto.setVisible(false);
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore durante setDatiIseePerIntestatarioBorsa = " + e.getMessage());
            }

            target.add(
                nomeIntestatarioBorsa,
                cognomeIntestatarioBorsa,
                codiceIntestatarioBorsa,
                importoIsee,
                dataPresentazioneIsee,
                dataValiditaIsee,
                iseeDifforme,
                containerDatiIseeIntestatario,
                containerNucleoIseeDiversoNucleoAnagrafico,
                containerIseeNonPresente,
                containerNonAccetto,
                containerAccetto,
                accettazioneIntestatarioSiNo,
                containerIntestatarioLoggato,
                containerIseeDifformita,
                accettazioneNucleiSiNo);
          }
        });

    containerDati.addOrReplace(nucleoAnagrafe);
    WebMarkupContainer lblnucleoAnagrafe = new WebMarkupContainer("lblnucleoAnagrafe");
    lblnucleoAnagrafe.add(AttributeAppender.append("for", nucleoAnagrafe.getMarkupId()));
    containerDati.addOrReplace(lblnucleoAnagrafe);
  }

  private boolean checkIseePresentato(Pratica datiDomanda) {
    return LabelFdCUtil.checkIfNotNull(datiDomanda)
        && LabelFdCUtil.checkIfNotNull(datiDomanda.getImportoIsee());
  }

  private boolean checkNucleoIseeNucleoAnagraficoUgualeOAccettato(
      PraticaBorseStudioExtend datiDomanda) {
    return LabelFdCUtil.checkIfNotNull(datiDomanda)
        && ((LabelFdCUtil.checkIfNotNull(datiDomanda.isNucleoIseeUgualeNucleoAnagrafico())
                && datiDomanda.isNucleoIseeUgualeNucleoAnagrafico())
            || (LabelFdCUtil.checkIfNotNull(datiDomanda.getAccettazioneNuclei())
                && (datiDomanda
                    .getAccettazioneNuclei()
                    .equals(AccettazioneNucleoIseeAnagraficoEnum.ACCETTA))));
  }

  private boolean checkNucleoIseeNucleoAnagrafico(PraticaBorseStudioExtend datiDomanda) {
    return LabelFdCUtil.checkIfNotNull(datiDomanda)
        && LabelFdCUtil.checkIfNotNull(datiDomanda.isNucleoIseeUgualeNucleoAnagrafico())
        && datiDomanda.isNucleoIseeUgualeNucleoAnagrafico();
  }
}
