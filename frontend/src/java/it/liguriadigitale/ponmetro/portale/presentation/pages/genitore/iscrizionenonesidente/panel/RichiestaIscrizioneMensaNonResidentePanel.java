package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.iscrizionenonesidente.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiFiglioExtend;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiGenitoreExtend;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizionemensanonresidente.DatiIscrizioneMensaNonResidente;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.riepilogo.RiepilogoIscrizioniPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAnagrafeGenitore;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDipartimentaliBambino;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiGeneraliAnagrafe;
import java.io.IOException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class RichiestaIscrizioneMensaNonResidentePanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -7980528587488590572L;

  private int index;

  private StepPanel stepPanel;

  private DatiAnagraficiIscrizioneMensaNonResidentePanel datiGenitorePanel;

  private DatiAnagraficiIscrizioneMensaNonResidentePanel datiFiglioPanel;

  private EsitoIscrizioneMensaNonResidentePanel esitoPanel;

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> indietro;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  CompoundPropertyModel<DatiIscrizioneMensaNonResidente> datiDomanda;

  public RichiestaIscrizioneMensaNonResidentePanel(String id, Object pojo) {
    super(id, pojo);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = 1;

    fillDati(pojo);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    datiDomanda = (CompoundPropertyModel<DatiIscrizioneMensaNonResidente>) dati;

    stepPanel =
        new StepPanel(
            "stepPanel",
            index,
            ServiceLocator.getInstance()
                .getServiziRistorazione()
                .getListaStepIscrizioneMensaNonResidenti());
    form.addOrReplace(stepPanel);

    datiGenitorePanel =
        new DatiAnagraficiIscrizioneMensaNonResidentePanel(
            "datiGenitorePanel", datiDomanda, index, feedbackPanel, true);
    datiGenitorePanel.setVisible(index == 1);
    form.addOrReplace(datiGenitorePanel);

    datiFiglioPanel =
        new DatiAnagraficiIscrizioneMensaNonResidentePanel(
            "datiFiglioPanel", datiDomanda, index, feedbackPanel, false);
    datiFiglioPanel.setVisible(index == 2);
    form.addOrReplace(datiFiglioPanel);

    esitoPanel = new EsitoIscrizioneMensaNonResidentePanel("esitoPanel", datiDomanda, index);
    esitoPanel.setVisible(index == 4);
    form.addOrReplace(esitoPanel);

    form.addOrReplace(creaBottoneAvanti());
    form.addOrReplace(creaBottoneIndietro());
    form.addOrReplace(creaBottoneAnnulla());
  }

  private FdcAjaxButton creaBottoneAvanti() {
    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = 2257353456297886396L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            index = index + 1;

            DatiGenitoreExtend datiGenitoreGoadev = getDatiGenitoreExtend();

            DatiGeneraliAnagrafe datiGeneraliAnagrafeBambino = null;
            if (LabelFdCUtil.checkIfNotNull(datiDomanda)
                && LabelFdCUtil.checkIfNotNull(datiDomanda.getObject())
                && LabelFdCUtil.checkIfNotNull(datiDomanda.getObject().getDatiBambino())
                && LabelFdCUtil.checkIfNotNull(
                    datiDomanda.getObject().getDatiBambino().getDatiGeneraliAnagrafe())) {
              datiGeneraliAnagrafeBambino =
                  datiDomanda.getObject().getDatiBambino().getDatiGeneraliAnagrafe();
            }
            DatiFiglioExtend datiFiglioGoadev = getDatiFiglioExted(datiGeneraliAnagrafeBambino);

            if (LabelFdCUtil.checkIfNull(
                datiDomanda.getObject().getDatiGenitore().getAutocompleteStatoNascita())) {
              datiDomanda
                  .getObject()
                  .getDatiGenitore()
                  .setAutocompleteStatoNascita(datiGenitoreGoadev.getAutocompleteStatoNascita());
            }

            if (LabelFdCUtil.checkIfNotNull(
                datiDomanda.getObject().getDatiGenitore().getDatiNascitaResidenzaDomicilio())) {

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaNascita())) {
                datiDomanda
                    .getObject()
                    .getDatiGenitore()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteProvinciaNascita(
                        datiGenitoreGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteProvinciaNascita());
              }

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneNascita())) {
                datiDomanda
                    .getObject()
                    .getDatiGenitore()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteComuneNascita(
                        datiGenitoreGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteComuneNascita());
              }

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaResidenza())) {
                datiDomanda
                    .getObject()
                    .getDatiGenitore()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteProvinciaResidenza(
                        datiGenitoreGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteProvinciaResidenza());
              }

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneResidenza())) {
                datiDomanda
                    .getObject()
                    .getDatiGenitore()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteComuneResidenza(
                        datiGenitoreGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteComuneResidenza());
              }

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaDomicilio())) {
                datiDomanda
                    .getObject()
                    .getDatiGenitore()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteProvinciaDomicilio(
                        datiGenitoreGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteProvinciaDomicilio());
              }

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiGenitore()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneDomicilio())) {
                datiDomanda
                    .getObject()
                    .getDatiGenitore()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteComuneDomicilio(
                        datiGenitoreGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteComuneDomicilio());
              }
            }

            if (LabelFdCUtil.checkIfNull(
                datiDomanda.getObject().getDatiBambino().getAutocompleteCittadinanze())) {
              datiDomanda
                  .getObject()
                  .getDatiBambino()
                  .setAutocompleteCittadinanze(datiFiglioGoadev.getAutocompleteCittadinanze());
            }

            if (LabelFdCUtil.checkIfNotNull(
                datiDomanda.getObject().getDatiBambino().getDatiNascitaResidenzaDomicilio())) {

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaNascita())) {
                datiDomanda
                    .getObject()
                    .getDatiBambino()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteProvinciaNascita(
                        datiFiglioGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteProvinciaNascita());
              }

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneNascita())) {
                datiDomanda
                    .getObject()
                    .getDatiBambino()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteComuneNascita(
                        datiFiglioGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteComuneNascita());
              }

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaResidenza())) {
                datiDomanda
                    .getObject()
                    .getDatiBambino()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteProvinciaResidenza(
                        datiFiglioGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteProvinciaResidenza());
              }

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneResidenza())) {
                datiDomanda
                    .getObject()
                    .getDatiBambino()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteComuneResidenza(
                        datiFiglioGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteComuneResidenza());
              }

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteProvinciaDomicilio())) {
                datiDomanda
                    .getObject()
                    .getDatiBambino()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteProvinciaDomicilio(
                        datiFiglioGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteProvinciaDomicilio());
              }

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteComuneDomicilio())) {
                datiDomanda
                    .getObject()
                    .getDatiBambino()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteComuneDomicilio(
                        datiFiglioGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteComuneDomicilio());
              }

              if (LabelFdCUtil.checkIfNull(
                  datiDomanda
                      .getObject()
                      .getDatiBambino()
                      .getDatiNascitaResidenzaDomicilio()
                      .getAutocompleteViario())) {
                datiDomanda
                    .getObject()
                    .getDatiBambino()
                    .getDatiNascitaResidenzaDomicilio()
                    .setAutocompleteViario(
                        datiFiglioGoadev
                            .getDatiNascitaResidenzaDomicilio()
                            .getAutocompleteViario());
              }
            }

            if (index == 4) {
              try {
                ServiceLocator.getInstance()
                    .getServiziRistorazione()
                    .iscriviBambinoNonResidente(datiDomanda.getObject());
              } catch (BusinessException | ApiException | IOException e) {
                log.error("Errore POST iscrivi bambino non residente: " + e.getMessage(), e);

                index = index - 1;

                error("Errore durante invio domanda");
              }
            }

            getTargetPanel(target);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {

            target.add(RichiestaIscrizioneMensaNonResidentePanel.this);

            log.error("CP errore domanda iscrizione mensa non residente ");
          }
        };

    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaIscrizioneMensaNonResidentePanel.avanti",
                    RichiestaIscrizioneMensaNonResidentePanel.this)));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 6345073562857072724L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RichiestaIscrizioneMensaNonResidentePanel.this);

            form.clearInput();

            setResponsePage(new RiepilogoIscrizioniPage());
          }
        };

    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaIscrizioneMensaNonResidentePanel.annulla",
                    RichiestaIscrizioneMensaNonResidentePanel.this)));

    return annulla;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBottoneIndietro() {
    indietro =
        new FdCButtonBootstrapAjaxLink<Object>("indietro", Type.Default) {

          private static final long serialVersionUID = 6211876176877158042L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            index = index - 1;

            getTargetPanel(target);
          }
        };

    indietro.setVisible(false);
    indietro.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "RichiestaIscrizioneMensaNonResidentePanel.indietro",
                    RichiestaIscrizioneMensaNonResidentePanel.this)));

    return indietro;
  }

  private AjaxRequestTarget getTargetPanel(AjaxRequestTarget target) {
    stepPanel.setIndexStep(index);
    target.add(stepPanel, feedbackPanel, indietro, avanti, annulla);

    if (index == 1) {

      datiGenitorePanel.setVisible(true);
      datiGenitorePanel.setEnabled(true);

      datiFiglioPanel.setVisible(false);
      datiFiglioPanel.setEnabled(false);

      esitoPanel.setVisible(false);
      esitoPanel.setEnabled(false);

      indietro.setVisible(false);

      target.add(this);

    } else if (index == 2) {

      datiGenitorePanel.setVisible(false);
      datiGenitorePanel.setEnabled(false);

      datiFiglioPanel.setVisible(true);
      datiFiglioPanel.setEnabled(true);

      esitoPanel.setVisible(false);
      esitoPanel.setEnabled(false);

      indietro.setVisible(true);

      target.add(this);

    } else if (index == 3) {
      datiGenitorePanel.setVisible(true);
      datiGenitorePanel.setEnabled(false);

      datiFiglioPanel.setVisible(true);
      datiFiglioPanel.setEnabled(false);

      esitoPanel.setVisible(false);
      esitoPanel.setEnabled(false);

      avanti.setVisible(true);
      indietro.setVisible(true);

      target.add(this);

    } else if (index == 4) {
      datiGenitorePanel.setVisible(false);
      datiGenitorePanel.setEnabled(false);

      datiFiglioPanel.setVisible(false);
      datiFiglioPanel.setEnabled(false);

      esitoPanel.setVisible(true);
      esitoPanel.setEnabled(true);

      avanti.setVisible(false);
      indietro.setVisible(false);
      annulla.setVisible(false);

      target.add(this);
    }

    return target;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    StringBuilder sb = new StringBuilder();

    sb.append(
        "$('html,body').animate({\r\n"
            + " scrollTop: $('#indicator').offset().top,\r\n"
            + " }, 650);");

    response.render(OnLoadHeaderItem.forScript(sb.toString()));
  }

  private DatiAnagrafeGenitore getDatiGenitore() {
    DatiAnagrafeGenitore datiGenitore = null;
    try {
      datiGenitore =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getDatiAnagraficiGenitore(getUtente().getCodiceFiscaleOperatore());
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore dati genitore: " + e.getMessage(), e);
    }
    return datiGenitore;
  }

  private DatiGenitoreExtend getDatiGenitoreExtend() {
    return ServiceLocator.getInstance()
        .getServiziRistorazione()
        .getDatiGenitoreExted(getUtente(), getDatiGenitore());
  }

  private DatiDipartimentaliBambino getDatiFiglio(String codiceFiscaleIscritto) {
    DatiDipartimentaliBambino datiFiglio = null;
    try {
      datiFiglio =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getDatiAnagraficiFiglio(codiceFiscaleIscritto);
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore dati figlio: " + e.getMessage(), e);
    }
    return datiFiglio;
  }

  private DatiFiglioExtend getDatiFiglioExted(DatiGeneraliAnagrafe datiGeneraliAnagrafeBambino) {
    DatiFiglioExtend datiFiglioExtend = null;
    if (LabelFdCUtil.checkIfNotNull(datiGeneraliAnagrafeBambino)) {
      DatiDipartimentaliBambino datiFiglio =
          getDatiFiglio(datiGeneraliAnagrafeBambino.getCodiceFiscale());
      datiFiglioExtend =
          ServiceLocator.getInstance()
              .getServiziRistorazione()
              .getDatiFiglioExted(
                  datiGeneraliAnagrafeBambino.getCodiceFiscale(),
                  datiGeneraliAnagrafeBambino.getNome(),
                  datiGeneraliAnagrafeBambino.getCognome(),
                  datiGeneraliAnagrafeBambino.getDataNascita(),
                  datiFiglio);
    }
    return datiFiglioExtend;
  }
}
