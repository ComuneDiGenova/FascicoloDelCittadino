package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheIscrizione;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.BibliotecheIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.RiassuntoBibliotecheIscrizionePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.form.BibliotecheIscrizioneForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteFull.IndPrincEnum;
import it.liguriadigitale.ponmetro.sebinaBiblioteche.model.UtenteFull.RecPrefEnum;
import java.io.IOException;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class BibliotecheIscrizionePanel extends BasePanel {

  private static final long serialVersionUID = 6667396662346988915L;

  private BibliotecheIscrizioneForm form = null;

  private BibliotecheIscrizione bibliotecheIscrizione;

  private ComponenteNucleo componenteNucleo;

  public BibliotecheIscrizionePanel(String id, ComponenteNucleo componenteNucleo) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    this.setComponenteNucleo(componenteNucleo);

    this.bibliotecheIscrizione = new BibliotecheIscrizione();
    fillDati(bibliotecheIscrizione);
  }

  public BibliotecheIscrizionePanel(
      String id, BibliotecheIscrizione bibliotecheIscrizione, ComponenteNucleo componenteNucleo) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    this.setComponenteNucleo(componenteNucleo);

    this.bibliotecheIscrizione = bibliotecheIscrizione;

    fillDati(bibliotecheIscrizione);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    BibliotecheIscrizione bibliotecheIscrizione = (BibliotecheIscrizione) dati;

    WebMarkupContainer alertToggle = new WebMarkupContainer("alertToggle");
    alertToggle.setVisible(false);

    CompoundPropertyModel<BibliotecheIscrizione> compoundPropertyModel =
        new CompoundPropertyModel<BibliotecheIscrizione>(bibliotecheIscrizione);

    form =
        new BibliotecheIscrizioneForm(
            "form",
            bibliotecheIscrizione,
            compoundPropertyModel,
            getComponenteNucleo(),
            getUtente(),
            this) {

          private static final long serialVersionUID = -2041826142160584927L;

          @Override
          protected void onSubmit() {

            if (!checkToggle()) {
              alertToggle.setVisible(!checkToggle());

              onError();

            } else {

              Residente datiUtente = getComponenteNucleo().getDatiCittadino();

              if (PageUtil.isStringValid(datiUtente.getGenovaOntoIDCardNumber())) {
                bibliotecheIscrizione.setNome(datiUtente.getCpvGivenName());
                bibliotecheIscrizione.setCognome(datiUtente.getCpvFamilyName());
                bibliotecheIscrizione.setSesso(datiUtente.getCpvHasSex());
                bibliotecheIscrizione.setDataNascita(datiUtente.getCpvDateOfBirth());

                bibliotecheIscrizione.setLuogoNascita(
                    datiUtente.getCpvHasBirthPlace().getClvCity());

                bibliotecheIscrizione.setCodiceFiscale(datiUtente.getCpvTaxCode());

                if (LabelFdCUtil.checkIfNotNull(datiUtente.getCpvHasAddress())) {
                  bibliotecheIscrizione.setIndirizzoResidenza(
                      datiUtente.getCpvHasAddress().getClvFullAddress());
                  bibliotecheIscrizione.setCapResidenza(
                      datiUtente.getCpvHasAddress().getClvPostCode());
                  bibliotecheIscrizione.setCittaResidenza(
                      datiUtente.getCpvHasAddress().getClvCity());
                }

                bibliotecheIscrizione.setProvinciaResidenza(getProvinciaResidenza());
                bibliotecheIscrizione.setStatoResidenza(getStatoResidenza());
                bibliotecheIscrizione.setIndirizzoPrincipale(IndPrincEnum.INDRES);

                bibliotecheIscrizione.setEmail(getModelObject().getEmail());
                bibliotecheIscrizione.setCellulare(getModelObject().getCellulare());

                bibliotecheIscrizione.setRecapitoPreferenziale(RecPrefEnum.EMAIL);

                String codiceTipoUtente = "";
                try {
                  codiceTipoUtente =
                      ServiceLocator.getInstance().getServiziBiblioteche().getCodiceTipoUtente();
                } catch (BusinessException | ApiException | IOException e) {
                  log.error("Errore durante codice tipo utente: " + e.getMessage());
                }
                bibliotecheIscrizione.setCodiceTipoUtente(codiceTipoUtente);

                bibliotecheIscrizione.setAutorizzazioneTrattamentoDati(
                    getModelObject().isAutorizzazioneTrattamentoDati());

                bibliotecheIscrizione.setNumeroCI(datiUtente.getGenovaOntoIDCardNumber());
                bibliotecheIscrizione.setLuogoCI(
                    datiUtente.getGenovaOntoIDCardIssuingMunicipality());
                bibliotecheIscrizione.setDataRilascioCI(datiUtente.getGenovaOntoIDCardIssueDate());
                bibliotecheIscrizione.setDataScadenzaCI(
                    datiUtente.getGenovaOntoIDCardValidUntilDate());

                bibliotecheIscrizione.setNomeGenitore(getUtente().getNome());
                bibliotecheIscrizione.setCognomeGenitore(getUtente().getCognome());
                Residente datiLoggato = getUtente().getDatiCittadinoResidente();
                bibliotecheIscrizione.setNumeroCIGenitore(datiLoggato.getGenovaOntoIDCardNumber());
                bibliotecheIscrizione.setLuogoCIGenitore(
                    datiLoggato.getGenovaOntoIDCardIssuingMunicipality());
                bibliotecheIscrizione.setDataRilascioCIGenitore(
                    datiLoggato.getGenovaOntoIDCardIssueDate());
                bibliotecheIscrizione.setDataScadenzaCIGenitore(
                    datiLoggato.getGenovaOntoIDCardValidUntilDate());

                log.debug("CP on submit prosegui iscrizione = " + bibliotecheIscrizione.toString());

                setResponsePage(
                    new RiassuntoBibliotecheIscrizionePage(
                        bibliotecheIscrizione, getComponenteNucleo()));
              } else {
                error(
                    "Attenzione, non si può iscrivere chi non ha un documento di riconoscimento valido");
              }
            }
          }
          ;

          private boolean checkToggle() {
            return getModelObject().isAutorizzazioneTrattamentoDati();
          }

          @Override
          protected void onError() {
            log.error("Errore iscrizione Sebina");
          }
        };

    log.debug("CP dopo form");

    addOrReplace(alertToggle);

    form.addOrReplace(creaBottoneAnnulla());
    form.setOutputMarkupId(true);
    form.setMultiPart(true);
    addOrReplace(form);
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  private LaddaAjaxLink<Object> creaBottoneAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = -8772002694366497234L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(BibliotecheIscrizionePanel.this);

            form.clearInput();

            setResponsePage(new BibliotecheIscrizionePage(getComponenteNucleo()));
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("BibliotecheIscrizionePanel.annulla", BibliotecheIscrizionePanel.this)));

    return annulla;
  }

  public BibliotecheIscrizione getBibliotecheIscrizione() {
    return bibliotecheIscrizione;
  }

  public void setBibliotecheIscrizione(BibliotecheIscrizione bibliotecheIscrizione) {
    this.bibliotecheIscrizione = bibliotecheIscrizione;
  }

  public ComponenteNucleo getComponenteNucleo() {
    return componenteNucleo;
  }

  public void setComponenteNucleo(ComponenteNucleo componenteNucleo) {
    this.componenteNucleo = componenteNucleo;
  }
}
