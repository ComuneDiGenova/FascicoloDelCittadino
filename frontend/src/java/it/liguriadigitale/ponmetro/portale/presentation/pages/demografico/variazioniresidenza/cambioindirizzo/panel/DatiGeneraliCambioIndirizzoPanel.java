package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.VariazioniResidenza;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.LoadData;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.CambioIndirizzoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.form.DatiGeneraliForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.time.LocalDate;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

public class DatiGeneraliCambioIndirizzoPanel extends BasePanel {

  private static final long serialVersionUID = 2781955826041290508L;

  private DatiGeneraliForm form = null;

  private Integer index;

  public DatiGeneraliCambioIndirizzoPanel(String id) {
    super(id);

    fillDati(LoadData.caricaMieiDatiResidente(getSession()));

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    createFeedBackPanel();

    fillDati(new VariazioniResidenza());
  }

  public DatiGeneraliCambioIndirizzoPanel(
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

    form = new DatiGeneraliForm("form", variazioniResidenza, getUtente());

    form.addOrReplace(creaBottoneAvanti(variazioniResidenza));

    form.setOutputMarkupId(true);
    form.setMultiPart(true);
    form.addOrReplace(creaBottoneAnnulla());
    addOrReplace(form);

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance()
            .addClazz(DatiGeneraliCambioIndirizzoPanel.this.getClass())
            .build();
    addOrReplace(boxMessaggi);
  }

  private LaddaAjaxButton creaBottoneAvanti(VariazioniResidenza variazioniResidenza) {
    LaddaAjaxButton avanti =
        new LaddaAjaxButton("avanti", Type.Primary) {

          private static final long serialVersionUID = 1031626225565871794L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            target.add(DatiGeneraliCambioIndirizzoPanel.this);

            index = index + 1;

            variazioniResidenza.setDataDecorrenza(LocalDate.now());
            variazioniResidenza.setNomeRichiedente(getUtente().getNome());
            variazioniResidenza.setCognomeRichiedente(getUtente().getCognome());

            Residente datiDemografico = getUtente().getDatiCittadinoResidente();
            if (LabelFdCUtil.checkIfNotNull(datiDemografico)) {
              variazioniResidenza.setNominativoRichiedente(datiDemografico.getRdfsLabel());
              variazioniResidenza.setCfRichiedente(getUtente().getCodiceFiscaleOperatore());
              variazioniResidenza.setPersonIdDemografico(
                  Integer.parseInt(datiDemografico.getCpvPersonID()));
              variazioniResidenza.setRegisteredFamilyId(
                  Integer.parseInt(
                      datiDemografico.getCpvInRegisteredFamily().getCpvRegisteredFamilyID()));
            }

            setResponsePage(new CambioIndirizzoPage(index, variazioniResidenza));
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(DatiGeneraliCambioIndirizzoPanel.this);

            log.error("CP errore step 1 cambio indirizzo");
          }
        };
    avanti.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DatiGeneraliCambioIndirizzoPanel.avanti",
                    DatiGeneraliCambioIndirizzoPanel.this)));

    return avanti;
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -5269371962796000761L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(DatiGeneraliCambioIndirizzoPanel.this);

            form.clearInput();

            setResponsePage(new CambioIndirizzoPage());
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "DatiGeneraliCambioIndirizzoPanel.annulla",
                    DatiGeneraliCambioIndirizzoPanel.this)));

    return annulla;
  }
}
