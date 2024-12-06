package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.prenotazione.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.form.AbstracFrameworkForm;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.components.anagrafici.combo.SiNoDropDownChoiceBoolean;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step1.form.ComboCartolibrerieDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.CedoleLibrarieConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cartolibreria;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.SceltaCartolibreria;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class PrenotazioneCedolaPanel extends BasePanel {

  private static final long serialVersionUID = 3630106380046949776L;

  private UtenteServiziRistorazione iscritto;
  private ComboCartolibrerieDropDownChoice<Cartolibreria> comboCartolibrerie;
  private WebMarkupContainer lblComboCartolibrerie;

  @SuppressWarnings("rawtypes")
  private SiNoDropDownChoiceBoolean fasciatura;

  @SuppressWarnings("unused")
  private String strFasciatura;

  private WebMarkupContainer wmkInfo3;

  public PrenotazioneCedolaPanel(String id, UtenteServiziRistorazione iscritto, Cedola cedola) {
    super(id);
    this.iscritto = iscritto;
    fillDati(cedola);
  }

  @Override
  public void fillDati(Object dati) {
    Cedola cedola = (Cedola) dati;
    SceltaCartolibreria prenotazione = new SceltaCartolibreria();
    prenotazione.setCedolaCodice(cedola.getCedolaCodice());
    prenotazione.setCodiceFiscaleMinore(cedola.getCodiceFiscaleMinore());
    prenotazione.setDataPrenotazione(LocalDate.now());

    wmkInfo3 = new WebMarkupContainer("wmkInfo3");
    wmkInfo3.setOutputMarkupId(true);
    wmkInfo3.setOutputMarkupPlaceholderTag(true);
    wmkInfo3.setVisible(false);

    AbstracFrameworkForm<SceltaCartolibreria> form =
        new AbstracFrameworkForm<SceltaCartolibreria>("form", prenotazione) {

          private static final long serialVersionUID = 7602157250344140043L;

          @Override
          public void addElementiForm() {
            creaComboCartolibrerie();
            lblComboCartolibrerie = new WebMarkupContainer("lblComboCartolibrerie");
            lblComboCartolibrerie.add(
                new AttributeModifier("for", comboCartolibrerie.getMarkupId()));
            add(lblComboCartolibrerie);
            creaComboFasciatura();
          }

          @Override
          protected void onSubmit() {
            ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
            try {
              SceltaCartolibreria request = copioDatiComboSuModelloForm();
              instance.getApiCedoleLibrarie().prenotazioneCartolibreriaPut(request);
              setResponsePage(new CedoleLibrarieConPrivacyPage(iscritto));
            } catch (BusinessException e) {
              log.error("Errore: ", e);
              error("Impossibile contattare il backend");
            } finally {
              instance.closeConnection();
            }
          }

          private SceltaCartolibreria copioDatiComboSuModelloForm() {
            SceltaCartolibreria modelloForm = getModelObject();
            Cartolibreria libreriScelta = comboCartolibrerie.getModelObject();
            modelloForm.setFasciatura(
                fasciatura.getModelObject().toString().equalsIgnoreCase("SI"));
            modelloForm.setIdCartolibreria(libreriScelta.getIdCartolibreria());
            modelloForm.setDenominazioneCartolibreria(
                libreriScelta.getDenominazioneCartolibreria());

            return modelloForm;
          }

          @SuppressWarnings("unchecked")
          private void creaComboCartolibrerie() {
            Cartolibreria cartolibreria = new Cartolibreria();

            comboCartolibrerie =
                new ComboCartolibrerieDropDownChoice<>(
                    "comboCartolibrerie", Model.of(cartolibreria), getElencoCartolibrerie());
            comboCartolibrerie.setOutputMarkupId(true);

            this.add(comboCartolibrerie);
          }

          private void creaComboFasciatura() {

            fasciatura =
                new SiNoDropDownChoiceBoolean(
                    "fasciatura",
                    new PropertyModel<>(PrenotazioneCedolaPanel.this, "strFasciatura"));

            fasciatura.add(
                new AjaxFormComponentUpdatingBehavior("change") {

                  @Override
                  protected void onUpdate(AjaxRequestTarget target) {

                    if (LabelFdCUtil.checkIfNotNull(fasciatura)) {
                      wmkInfo3.setVisible(
                          fasciatura.getModelObject().toString().equalsIgnoreCase("SI"));
                    }

                    target.add(wmkInfo3);
                  }
                });

            fasciatura.setLabel(Model.of("Richiedo la fasciatura del libro"));
            fasciatura.setRequired(true);
            addOrReplace(fasciatura);
          }

          private List<Cartolibreria> getElencoCartolibrerie() {
            try {
              return ServiceLocator.getInstance().getCedoleLibrarie().elencoCartoliberie();
            } catch (BusinessException | ApiException e) {
              log.debug("Impossibile recuperare le cartolerie: ", e);
            }
            return new ArrayList<Cartolibreria>();
          }
        };

    Link<Void> linkannulla =
        new Link<Void>("annulla") {

          private static final long serialVersionUID = -3743805429895310539L;

          @Override
          public void onClick() {
            setResponsePage(new CedoleLibrarieConPrivacyPage(iscritto));
          }
        };
    form.add(linkannulla);
    form.addOrReplace(wmkInfo3);

    add(form);
    createFeedBackPanel();
  }
}
