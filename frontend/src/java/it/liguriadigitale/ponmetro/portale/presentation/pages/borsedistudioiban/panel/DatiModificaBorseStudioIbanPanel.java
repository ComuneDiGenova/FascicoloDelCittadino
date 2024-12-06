package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudioiban.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.BorsaStudioIBAN;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdcAjaxButton;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCIbanField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdcCardFormPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudioiban.BorseStudioModificaIbanPage;
import java.io.IOException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class DatiModificaBorseStudioIbanPanel extends FdcCardFormPanel {

  private static final long serialVersionUID = -1898517703909038264L;

  @SuppressWarnings("rawtypes")
  private FdCIbanField iban;

  private FdcAjaxButton modifica;
  private FdCButtonBootstrapAjaxLink<Object> annulla;

  public DatiModificaBorseStudioIbanPanel(String id, Object datiBorsa) {
    super(id, datiBorsa);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(datiBorsa);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public void fillDati(Object dati) {
    super.fillDati(dati);

    BorsaStudioIBAN datiBorsa = (BorsaStudioIBAN) dati;

    form.setMarkupId("form" + datiBorsa.getIdDomandaOnline());

    iban =
        new FdCIbanField(
            "iban", new PropertyModel(datiBorsa, "iban"), DatiModificaBorseStudioIbanPanel.this);
    iban.getTextField().setRequired(true);
    iban.getTextField().setMarkupId("iban" + datiBorsa.getIdDomandaOnline());
    iban.getTextField()
        .add(
            new AjaxFormComponentUpdatingBehavior("change") {

              private static final long serialVersionUID = -8410349483071603332L;

              @Override
              protected void onUpdate(AjaxRequestTarget target) {
                log.debug("CP inserisco iban");

                annulla.setVisible(true);

                target.add(annulla);
              }

              @Override
              protected void onError(AjaxRequestTarget target, RuntimeException e) {
                super.onError(target, e);

                annulla.setVisible(true);

                target.add(annulla);
              }
            });

    form.addOrReplace(iban);

    form.addOrReplace(creaBtnModifica(datiBorsa));
    form.addOrReplace(creaBtnAnnulla(datiBorsa));
  }

  private FdcAjaxButton creaBtnModifica(BorsaStudioIBAN datiBorsa) {

    modifica =
        new FdcAjaxButton("modifica") {

          private static final long serialVersionUID = -3299279558297493941L;

          @Override
          protected void onSubmit(AjaxRequestTarget target) {
            log.debug("CP click avanti su modifica dati iban = " + datiBorsa.getIban());

            annulla.setVisible(false);

            try {
              ServiceLocator.getInstance()
                  .getServiziBorseDiStudio()
                  .putModificaIbanBorsaStudio(datiBorsa.getCodiceFiscaleRichiedente(), datiBorsa);

              success("IBAN modificato correttamente");

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore durante PUT modifica IBAN Borse Studio : " + e.getMessage());

              error("Errore durante modifica IBAN");
            }

            target.add(DatiModificaBorseStudioIbanPanel.this);
          }

          @Override
          protected void onError(AjaxRequestTarget target) {
            target.add(DatiModificaBorseStudioIbanPanel.this);
          }
        };

    modifica.setMarkupId("modifica" + datiBorsa.getIdDomandaOnline());

    return modifica;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnAnnulla(BorsaStudioIBAN datiBorsa) {
    annulla =
        new FdCButtonBootstrapAjaxLink<Object>("annulla", Type.Default) {

          private static final long serialVersionUID = 7316084624689227127L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            setResponsePage(new BorseStudioModificaIbanPage());
          }
        };

    annulla.setVisible(false);
    annulla.setMarkupId("annulla" + datiBorsa.getIdDomandaOnline());
    annulla.setLabel(Model.of(getString("DatiModificaBorseStudioIbanPanel.annulla")));

    return annulla;
  }
}
