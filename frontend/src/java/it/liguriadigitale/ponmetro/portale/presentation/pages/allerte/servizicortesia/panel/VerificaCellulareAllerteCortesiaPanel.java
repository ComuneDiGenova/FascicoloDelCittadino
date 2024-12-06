package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiVerificaCellulareAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ServiziCortesiaConPrivacyPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class VerificaCellulareAllerteCortesiaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 8871550539618164092L;

  WebMarkupContainer containerCodiceSMS = new WebMarkupContainer("containerCodiceSMS");

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> annulla;
  private FdCButtonBootstrapAjaxLink<Object> vaiAlServizio;

  private FdcAjaxButton avantiSms;

  public VerificaCellulareAllerteCortesiaPanel(String id, Object datiVerifica) {
    super(id, datiVerifica);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(datiVerifica);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    containerCodiceSMS.setOutputMarkupId(true);
    containerCodiceSMS.setOutputMarkupPlaceholderTag(true);
    containerCodiceSMS.setVisible(false);

    DatiVerificaCellulareAllerteCortesia datiUtente = (DatiVerificaCellulareAllerteCortesia) dati;

    form.addOrReplace(containerCodiceSMS);

    form.add(
        new FdCTitoloPanel("titolo", getString("VerificaCellulareAllerteCortesiaPanel.titolo")));

    FdCTextField cellulare =
        new FdCTextField(
            "cellulare",
            new PropertyModel(datiUtente, "cellulare"),
            VerificaCellulareAllerteCortesiaPanel.this);
    cellulare.getTextField().setRequired(true);
    cellulare.setEnabled(false);
    form.addOrReplace(cellulare);

    form.addOrReplace(creaBtnAvanti(datiUtente));
    form.addOrReplace(creaBtnAnnulla());

    containerCodiceSMS.add(
        new FdCTitoloPanel(
            "titoloSMS", getString("VerificaCellulareAllerteCortesiaPanel.titoloSMS")));

    FdCTextField codiceSMS =
        new FdCTextField(
            "codiceSMS",
            new PropertyModel(datiUtente, "codiceSms"),
            VerificaCellulareAllerteCortesiaPanel.this);

    codiceSMS.getTextField().add(StringValidator.exactLength(4));
    codiceSMS.getTextField().add(new AttributeAppender("style", "text-transform:uppercase"));

    containerCodiceSMS.addOrReplace(codiceSMS);

    // TODO
    containerCodiceSMS.addOrReplace(creaBtnAvantiSMS(datiUtente));
    containerCodiceSMS.addOrReplace(vaiAlServizo());
  }

  private FdcAjaxButton creaBtnAvanti(DatiVerificaCellulareAllerteCortesia datiVerifica) {

    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = 8155775905142756173L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            log.debug("CP click avanti su verifica cel = " + datiVerifica);

            containerCodiceSMS.setVisible(true);

            this.setEnabled(false);

            try {
              if (LabelFdCUtil.checkIfNotNull(datiVerifica)) {

                datiVerifica.setReinvia("N");
                ServiceLocator.getInstance()
                    .getServiziAllerteCortesia()
                    .putWsPutCheckTelefonoSMS(datiVerifica);

                avanti.setVisible(false);
                annulla.setVisible(false);

                success("Controlla il cellulare");
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore put verifica email allerte cortesia con N: " + e.getMessage(), e);

              try {
                datiVerifica.setReinvia("S");
                ServiceLocator.getInstance()
                    .getServiziAllerteCortesia()
                    .putWsPutCheckTelefonoSMS(datiVerifica);

                avanti.setVisible(false);
                annulla.setVisible(false);

                success("Controlla il cellulare");
              } catch (BusinessException | ApiException | IOException e1) {
                log.error(
                    "Errore put verifica email allerte cortesia con S: " + e1.getMessage(), e1);

                error("Impossibile verificare il cellulare.");
              }
            }

            target.add(VerificaCellulareAllerteCortesiaPanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(VerificaCellulareAllerteCortesiaPanel.this);
          }
        };
    avanti.setLabel(Model.of(getString("VerificaCellulareAllerteCortesiaPanel.avanti")));

    return avanti;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAnnulla() {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = -5588799431004151374L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new ServiziCortesiaConPrivacyPage());
          }
        };

    annulla.setLabel(Model.of(getString("VerificaCellulareAllerteCortesiaPanel.annulla")));

    return annulla;
  }

  private FdcAjaxButton creaBtnAvantiSMS(DatiVerificaCellulareAllerteCortesia datiVerifica) {
    avantiSms =
        new FdcAjaxButton("avantiSMS") {

          private static final long serialVersionUID = -1033603344747640823L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            log.debug("CP click avanti SMS = ");

            try {
              String email = datiVerifica.getEmail();
              String codiceSms = datiVerifica.getCodiceSms();

              log.debug("CP email = " + email + " " + codiceSms);

              if (!StringUtils.isAlphanumeric(codiceSms)) {

                error("Attenzione, il codice deve contenere solo lettere maiuscole e numeri");

              } else {
                ServiceLocator.getInstance()
                    .getServiziAllerteCortesia()
                    .getWsGetCheckTelefonoSMS(email, codiceSms);

                avantiSms.setEnabled(false);

                success("Cellulare verificato correttamente");
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error(
                  "Errore put verifica email allerte cortesia get codice SMS: " + e.getMessage(),
                  e);

              error("Errore durante l'inserimento del codice");
            }

            target.add(VerificaCellulareAllerteCortesiaPanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(VerificaCellulareAllerteCortesiaPanel.this);
          }
        };

    // avantiSms.setLabel(Model.of(getString("VerificaCellulareAllerteCortesiaPanel.avanti")));

    return avantiSms;
  }

  private FdCButtonBootstrapAjaxLink<Object> vaiAlServizo() {

    vaiAlServizio =
        new FdCButtonBootstrapAjaxLink<Object>("vaiAlServizio", Type.Default) {

          private static final long serialVersionUID = -5588799431004151374L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new ServiziCortesiaConPrivacyPage());
          }
        };

    vaiAlServizio.setLabel(
        Model.of(getString("VerificaCellulareAllerteCortesiaPanel.tornaAlServizo")));

    return vaiAlServizio;
  }
}
