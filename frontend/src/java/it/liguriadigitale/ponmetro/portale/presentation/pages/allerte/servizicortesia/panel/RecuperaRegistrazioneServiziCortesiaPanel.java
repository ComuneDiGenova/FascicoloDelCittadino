package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.DettagliUtente;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiRegistrazioneAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCPasswordField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ServiziCortesiaConPrivacyPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class RecuperaRegistrazioneServiziCortesiaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 6980567079613969493L;

  public RecuperaRegistrazioneServiziCortesiaPanel(String id, Object datiRegistrazione) {
    super(id, datiRegistrazione);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(datiRegistrazione);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    DatiRegistrazioneAllerteCortesia datiRegistrazioneUtente =
        (DatiRegistrazioneAllerteCortesia) dati;

    form.add(
        new FdCTitoloPanel(
            "titolo", getString("RecuperaRegistrazioneServiziCortesiaPanel.titolo")));

    FdCEmailTextField email =
        new FdCEmailTextField(
            "email",
            new PropertyModel(datiRegistrazioneUtente, "email"),
            RecuperaRegistrazioneServiziCortesiaPanel.this);
    email.getTextField().setRequired(true);
    form.addOrReplace(email);

    FdCPasswordField password =
        new FdCPasswordField(
            "password",
            new PropertyModel(datiRegistrazioneUtente, "password"),
            RecuperaRegistrazioneServiziCortesiaPanel.this);
    password.getTextField().setRequired(true);
    form.addOrReplace(password);

    form.addOrReplace(creaBtnAccedi(datiRegistrazioneUtente));
    form.addOrReplace(creaBtnAnnulla());
  }

  private FdcAjaxButton creaBtnAccedi(DatiRegistrazioneAllerteCortesia datiRegistrazioneUtente) {
    return new FdcAjaxButton("accedi") {

      private static final long serialVersionUID = -7282600184237662687L;

      @Override
      public void onSubmit(AjaxRequestTarget target) {
        log.debug("CP click avanti su accedi = " + datiRegistrazioneUtente);

        // CON YAML non va
        //				try {
        //					ServiceLocator.getInstance().getServiziAllerteCortesia()
        //							.getWsLoginEMAIL(datiRegistrazioneUtente.getEmail(),
        // datiRegistrazioneUtente.getPassword());
        //				} catch (BusinessException | ApiException | IOException e) {
        //					log.error("Errore put recupera registrazione allerte cortesia: " + e.getMessage(),
        // e);
        //
        //					error("Login o password errate");
        //
        //				}

        // Con modo NO YAML va
        ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
        try {

          DettagliUtente dettagliUtente =
              instance
                  .getApiAllerteCortesiaNoYaml()
                  .getWsLoginEMAIL(
                      datiRegistrazioneUtente.getEmail(),
                      datiRegistrazioneUtente.getPassword(),
                      datiRegistrazioneUtente.getCodiceFiscale());

          if (dettagliUtente != null) {
            setResponsePage(new ServiziCortesiaConPrivacyPage(dettagliUtente));
          } else {
            error("Login o password errate.");
          }
        } catch (BusinessException e) {
          log.error("Errore put recupera registrazione allerte cortesia: " + e.getMessage(), e);
          error("Login o password errate.");
        } finally {
          instance.closeConnection();
        }

        target.add(RecuperaRegistrazioneServiziCortesiaPanel.this);
      }

      @Override
      protected void onError(AjaxRequestTarget target) {
        target.add(RecuperaRegistrazioneServiziCortesiaPanel.this);
      }
    };
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAnnulla() {
    FdCButtonBootstrapAjaxLink<Object> annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = -3289904920737050739L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new ServiziCortesiaConPrivacyPage());
          }
        };

    annulla.setLabel(Model.of(getString("RecuperaRegistrazioneServiziCortesiaPanel.annulla")));

    return annulla;
  }
}
