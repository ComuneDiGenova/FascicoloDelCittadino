package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiVerificaEmailAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.ServiziCortesiaConPrivacyPage;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

public class VerificaEmailAllerteCortesiaPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = 8871550539618164092L;

  WebMarkupContainer containerCodiceEmail = new WebMarkupContainer("containerCodiceEmail");

  private FdcAjaxButton avanti;
  private FdCButtonBootstrapAjaxLink<Object> annulla;
  private FdCButtonBootstrapAjaxLink<Object> vaiAlServizio;

  private FdcAjaxButton avantiEmail;
  private FdCTextField codiceEmail;

  public VerificaEmailAllerteCortesiaPanel(String id, Object datiVerifica) {
    super(id, datiVerifica);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(datiVerifica);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    containerCodiceEmail.setOutputMarkupId(true);
    containerCodiceEmail.setOutputMarkupPlaceholderTag(true);
    containerCodiceEmail.setVisible(false);

    DatiVerificaEmailAllerteCortesia datiUtente = (DatiVerificaEmailAllerteCortesia) dati;

    form.addOrReplace(containerCodiceEmail);

    form.add(new FdCTitoloPanel("titolo", getString("VerificaEmailAllerteCortesiaPanel.titolo")));

    FdCTextField email =
        new FdCTextField(
            "email",
            new PropertyModel(datiUtente, "email"),
            VerificaEmailAllerteCortesiaPanel.this);
    email.getTextField().setRequired(true);
    email.setEnabled(false);
    form.addOrReplace(email);

    form.addOrReplace(creaBtnAvanti(datiUtente));
    form.addOrReplace(creaBtnAnnulla());

    containerCodiceEmail.add(
        new FdCTitoloPanel(
            "titoloEmail", getString("VerificaEmailAllerteCortesiaPanel.titoloEmail")));

    codiceEmail =
        new FdCTextField(
            "codiceEmail",
            new PropertyModel(datiUtente, "codiceEmail"),
            VerificaEmailAllerteCortesiaPanel.this);

    codiceEmail.getTextField().add(StringValidator.exactLength(4));
    codiceEmail.getTextField().add(new AttributeAppender("style", "text-transform:uppercase"));

    containerCodiceEmail.addOrReplace(codiceEmail);

    containerCodiceEmail.addOrReplace(creaBtnAvantiEmail(datiUtente));
    containerCodiceEmail.addOrReplace(vaiAlServizo());
  }

  private FdcAjaxButton creaBtnAvanti(DatiVerificaEmailAllerteCortesia datiVerifica) {

    avanti =
        new FdcAjaxButton("avanti") {

          private static final long serialVersionUID = 8155775905142756173L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            log.debug("CP click avanti su verifica cel = " + datiVerifica);

            containerCodiceEmail.setVisible(true);

            this.setEnabled(false);

            target.add(VerificaEmailAllerteCortesiaPanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(VerificaEmailAllerteCortesiaPanel.this);
          }
        };
    avanti.setLabel(Model.of(getString("VerificaEmailAllerteCortesiaPanel.avanti")));

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

    annulla.setLabel(Model.of(getString("VerificaEmailAllerteCortesiaPanel.annulla")));

    return annulla;
  }

  private FdcAjaxButton creaBtnAvantiEmail(DatiVerificaEmailAllerteCortesia datiVerifica) {
    avantiEmail =
        new FdcAjaxButton("avantiEmail") {

          private static final long serialVersionUID = -1033603344747640823L;

          @Override
          public void onSubmit(AjaxRequestTarget target) {
            log.debug(
                "CP click avanti Email = "
                    + datiVerifica.getCodiceEmail()
                    + " - "
                    + StringUtils.isAlphanumeric(datiVerifica.getCodiceEmail()));

            try {

              if (!StringUtils.isAlphanumeric(datiVerifica.getCodiceEmail())) {

                error("Attenzione, il codice deve contenere solo lettere maiuscole e numeri");

              } else {

                String codiceToUpper = datiVerifica.getCodiceEmail().toUpperCase();

                ServiceLocator.getInstance()
                    .getServiziAllerteCortesia()
                    .getWsGetCheckEmail(codiceToUpper);

                success("Email verificata correttamente");

                avantiEmail.setEnabled(false);
                annulla.setEnabled(false);
                codiceEmail.setEnabled(false);

                target.add(avantiEmail, annulla, codiceEmail);
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error(
                  "Errore put verifica email allerte cortesia get codice SMS: " + e.getMessage(),
                  e);

              error("Errore durante l'inserimento del codice");
            }

            target.add(VerificaEmailAllerteCortesiaPanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(VerificaEmailAllerteCortesiaPanel.this);
          }
        };

    // avantiSms.setLabel(Model.of(getString("VerificaCellulareAllerteCortesiaPanel.avanti")));

    return avantiEmail;
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

    vaiAlServizio.setLabel(Model.of(getString("VerificaEmailAllerteCortesiaPanel.tornaAlServizo")));

    return vaiAlServizio;
  }
}
