package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.RichiestaResidenzaPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.richiestaresidenza.form.DatiGeneraliResidenzaForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.time.LocalDate;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class DatiGeneraliRichiestaResidenzaPanel extends BasePanel {

  private static final long serialVersionUID = 2781955826041290508L;

  private DatiGeneraliResidenzaForm form = null;

  private Integer index;

  private WebMarkupContainer alertComune = new WebMarkupContainer("alertComune");

  private WebMarkupContainer alertStato = new WebMarkupContainer("alertStato");

  public DatiGeneraliRichiestaResidenzaPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    createFeedBackPanel();

    fillDati(new VariazioniResidenza());
  }

  public DatiGeneraliRichiestaResidenzaPanel(
      String id, Integer index, VariazioniResidenza variazione) {

    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.index = index;

    createFeedBackPanel();

    fillDati(variazione);
  }

  @Override
  public void fillDati(Object dati) {
    VariazioniResidenza variazioniResidenza = (VariazioniResidenza) dati;

    form = new DatiGeneraliResidenzaForm("form", variazioniResidenza, getUtente());

    form.addOrReplace(creaBottoneAvanti(variazioniResidenza));

    form.setOutputMarkupId(true);
    form.setMultiPart(true);
    form.addOrReplace(creaBottoneAnnulla());
    addOrReplace(form);

    alertComune.setVisible(false);
    alertComune.setOutputMarkupId(true);
    addOrReplace(alertComune);

    alertStato.setVisible(false);
    alertStato.setOutputMarkupId(true);
    addOrReplace(alertStato);

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance()
            .addClazz(DatiGeneraliRichiestaResidenzaPanel.this.getClass())
            .build();
    addOrReplace(boxMessaggi);
  }

  private LaddaAjaxButton creaBottoneAvanti(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = -1714476552556728557L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(DatiGeneraliRichiestaResidenzaPanel.this);

            try {
              if (VariazioniResidenzaUtil.checkComune(variazioniResidenza.getComuneProvenienza())
                  && VariazioniResidenzaUtil.checkStato(
                      variazioniResidenza.getStatoProvenienza())) {
                index = index + 1;

                variazioniResidenza.setDataDecorrenza(LocalDate.now());
                variazioniResidenza.setNomeRichiedente(getUtente().getNome());
                variazioniResidenza.setCognomeRichiedente(getUtente().getCognome());

                Residente datiDemografico = getUtente().getDatiCittadinoResidente();
                if (getUtente().isResidente()) {
                  variazioniResidenza.setNominativoRichiedente(datiDemografico.getRdfsLabel());
                  variazioniResidenza.setCfRichiedente(getUtente().getCodiceFiscaleOperatore());
                  if (LabelFdCUtil.checkIfNotNull(datiDemografico.getCpvPersonID())) {
                    variazioniResidenza.setPersonIdDemografico(
                        Integer.parseInt(datiDemografico.getCpvPersonID()));
                  }

                  if (LabelFdCUtil.checkIfNotNull(datiDemografico.getCpvInRegisteredFamily())
                      && LabelFdCUtil.checkIfNotNull(
                          datiDemografico.getCpvInRegisteredFamily().getCpvRegisteredFamilyID())) {
                    variazioniResidenza.setRegisteredFamilyId(
                        Integer.parseInt(
                            datiDemografico.getCpvInRegisteredFamily().getCpvRegisteredFamilyID()));
                  }

                  variazioniResidenza.setUltimoIdPerNonResidente(-1);

                } else {
                  // non residente

                  variazioniResidenza.setNominativoRichiedente(
                      getUtente().getCognome().concat(" ").concat(getUtente().getNome()));
                  variazioniResidenza.setCfRichiedente(getUtente().getCodiceFiscaleOperatore());
                  variazioniResidenza.setPersonIdDemografico(-1);
                  variazioniResidenza.setRegisteredFamilyId(-1);

                  variazioniResidenza.setUltimoIdPerNonResidente(-1);
                }

                if (PageUtil.isStringValid(variazioniResidenza.getComuneProvenienza())) {
                  String[] comuneProvenienzaSplitted =
                      variazioniResidenza.getComuneProvenienza().split("-");
                  String idComuneProvenienza =
                      comuneProvenienzaSplitted[comuneProvenienzaSplitted.length - 1];
                  String descrizioneComuneProvenienza =
                      variazioniResidenza
                          .getComuneProvenienza()
                          .substring(
                              0, variazioniResidenza.getComuneProvenienza().lastIndexOf("-"));
                  if (LabelFdCUtil.checkIfNotNull(idComuneProvenienza)) {
                    variazioniResidenza.setCodiceComuneProvenienza(idComuneProvenienza);
                    variazioniResidenza.setDescrizioneComuneProvenienza(
                        descrizioneComuneProvenienza);
                  }
                }

                if (PageUtil.isStringValid(variazioniResidenza.getStatoProvenienza())) {
                  String[] statoProvenienzaSplitted =
                      variazioniResidenza.getStatoProvenienza().split("-");
                  String idStatoProvenienza =
                      statoProvenienzaSplitted[statoProvenienzaSplitted.length - 1];
                  if (LabelFdCUtil.checkIfNotNull(idStatoProvenienza)) {
                    variazioniResidenza.setCodiceStatoProvenienza(idStatoProvenienza);
                  }
                }

                setResponsePage(new RichiestaResidenzaPage(index, variazioniResidenza));
              } else {
                alertComune.setVisible(
                    !VariazioniResidenzaUtil.checkComune(
                        variazioniResidenza.getComuneProvenienza()));
                alertStato.setVisible(
                    !VariazioniResidenzaUtil.checkStato(variazioniResidenza.getStatoProvenienza()));

                onError();
              }
            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore durante on submit step 1 richiesta residenza: " + e.getMessage());
            }
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(DatiGeneraliRichiestaResidenzaPanel.this);

            log.error("CP errore step 1 richiesta residenza");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DatiGeneraliRichiestaResidenzaPanel.avanti",
                    DatiGeneraliRichiestaResidenzaPanel.this)));

    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = 676497196973643776L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DatiGeneraliRichiestaResidenzaPanel.this);

            form.clearInput();

            setResponsePage(new RichiestaResidenzaPage());
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DatiGeneraliRichiestaResidenzaPanel.annulla",
                    DatiGeneraliRichiestaResidenzaPanel.this)));

    return annulla;
  }
}
