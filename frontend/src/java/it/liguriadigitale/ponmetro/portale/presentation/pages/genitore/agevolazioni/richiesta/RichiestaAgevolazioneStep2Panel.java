package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep1;
import it.liguriadigitale.ponmetro.portale.pojo.iscrizione.AgevolazioneStep2;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.RichiestaAgevolazioneStep1Page;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.RichiestaAgevolazioneStep2Page;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.RichiestaAgevolazioneStep3Page;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.form.RichiestaAgevolazioneStep2Form;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.form.pojo.MembroNucleoChePercepivaReddito;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiAgevolazioneTariffaria.AccettazioneNucleoIseeAnagraficoEnum;
import java.time.LocalDate;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;

public class RichiestaAgevolazioneStep2Panel extends BasePanel {

  private static final long serialVersionUID = -4652274627139372877L;
  private static final String ATTRIBUTE_TITLE = "title";

  private AgevolazioneStep1 datiStep1;

  public RichiestaAgevolazioneStep2Panel(AgevolazioneStep1 iscrizione) {
    super("agevolazionePanel");
    createFeedBackPanel();
    this.datiStep1 = iscrizione;
    fillDati(iscrizione);
  }

  @Override
  public void fillDati(Object dati) {
    AgevolazioneStep1 step1 = (AgevolazioneStep1) dati;
    AgevolazioneStep2 step2 = new AgevolazioneStep2();

    step2.setStep1(step1);
    step2.setEmailContatto(getUtente().getMail());
    step2.setTelefonoContatto(getUtente().getMobile());

    if (LabelFdCUtil.checkIfNotNull(step1)
        && LabelFdCUtil.checkIfNotNull(step1.getAccettazioneNuclei())
        && step1.getAccettazioneNuclei().equals(AccettazioneNucleoIseeAnagraficoEnum.ACCETTA)) {
      warn(
          "Hai dichiarato che il nucleo nella dichiarazione ISEE è corretto. La tua autodichiarazione verrà salvata per i fini previsti dalla legge.");
    }

    if (LabelFdCUtil.checkIfNotNull(step1)
        && LabelFdCUtil.checkIfNotNull(step1.getAccettazioneNuclei())
        && step1.getAccettazioneNuclei().equals(AccettazioneNucleoIseeAnagraficoEnum.NON_ACCETTA)) {
      warn(
          "Hai dichiarato che il nucleo nella dichiarazione ISEE non è corretto. E' necessario modificare la dichiarazione ISEE prima di poter presentare una nuova richiesta.");
    }

    addOrReplace(creaBottoneTornaIndietro(step1));

    WebMarkupContainer containerSitoInps = new WebMarkupContainer("containerSitoInps");
    containerSitoInps.setVisible(
        LabelFdCUtil.checkIfNotNull(step1)
            && LabelFdCUtil.checkIfNotNull(step1.getAccettazioneNuclei())
            && step1
                .getAccettazioneNuclei()
                .equals(AccettazioneNucleoIseeAnagraficoEnum.NON_ACCETTA));
    containerSitoInps.setOutputMarkupId(true);
    containerSitoInps.setOutputMarkupPlaceholderTag(true);
    addOrReplace(containerSitoInps);

    RichiestaAgevolazioneStep2Form form =
        new RichiestaAgevolazioneStep2Form("form", step2, this) {

          private static final long serialVersionUID = -5971371511446519160L;

          @Override
          protected void onSubmit() {
            try {
              boolean ok = true;

              int massimoAnnoIndietroPerDisoccupazione = LocalDate.now().getYear() - 2;

              if (LabelFdCUtil.checkIfNotNull(getModelObject().getListaMembriNucleo())) {
                for (MembroNucleoChePercepivaReddito elem :
                    getModelObject().getListaMembriNucleo()) {
                  if (LabelFdCUtil.checkIfNotNull(elem.getDataDisoccupazioneMembroNucleo())) {
                    if (elem.getDataDisoccupazioneMembroNucleo().getYear()
                        < massimoAnnoIndietroPerDisoccupazione) {

                      error(
                          "La data di disoccupazione di "
                              + elem.getDatiMembroNucleo().getNome()
                              + " "
                              + elem.getDatiMembroNucleo().getCognome()
                              + "  non può essere antecedente a due anni precedenti rispetto all'anno di presentazione dell'ISEE.");
                      ok = false;
                    }
                  }
                }
              }

              if (ok) {
                ServiceLocator.getInstance()
                    .getServiziRistorazione()
                    .richiediAgevolazione(getModelObject(), getUtente());
                success("Richiesta di agevolazione inviata correttamente.");
                log.debug("Richiesta inviata");
                throw new RestartResponseAtInterceptPageException(
                    new RichiestaAgevolazioneStep3Page(step2));
              }
            } catch (BusinessException | ApiException e) {
              log.error("Errore durante l'invio della domanda:", e);
              error("Errore durante l'invio della domanda");
            }
          }

          @Override
          protected void onError() {
            super.onError();
          }
        };
    form.addOrReplace(creaBottoneAnnulla());
    form.addOrReplace(creaBottoneIndietro(step1));

    form.setVisible(checkContainerDatiFormVisible(step1));

    addOrReplace(form);
  }

  private Component creaBottoneIndietro(AgevolazioneStep1 step1) {

    Link<Void> link =
        new Link<Void>("indietro") {

          private static final long serialVersionUID = 1802532275878600465L;

          @Override
          public void onClick() {
            setResponsePage(
                new RichiestaAgevolazioneStep1Page(
                    step1.getIscrittoSelezionato(), step1.getAnnoScolastico()));
          }
        };
    AttributeModifier attributeModifier = new AttributeModifier(ATTRIBUTE_TITLE, "indietro");
    link.add(attributeModifier);
    add(link);
    return link;
  }

  private Component creaBottoneAnnulla() {
    return (new Link<Void>("annulla") {

      private static final long serialVersionUID = 1802532275878600465L;

      @Override
      public void onClick() {
        setResponsePage(new RichiestaAgevolazioneStep2Page(datiStep1));
      }
    });
  }

  private Component creaBottoneTornaIndietro(AgevolazioneStep1 step1) {
    Link tornaIndietro =
        new Link<Void>("tornaIndietro") {

          private static final long serialVersionUID = 4247958601112960020L;

          @Override
          public void onClick() {
            setResponsePage(new RichiestaAgevolazioneStep1Page());
          }
        };

    tornaIndietro.setVisible(!checkContainerDatiFormVisible(step1));

    return tornaIndietro;
  }

  private boolean checkContainerDatiFormVisible(AgevolazioneStep1 step1) {
    return LabelFdCUtil.checkIfNotNull(step1)
        && LabelFdCUtil.checkIfNotNull(step1.getAccettazioneNuclei())
        && (step1.getAccettazioneNuclei().equals(AccettazioneNucleoIseeAnagraficoEnum.ACCETTA)
            || step1
                .getAccettazioneNuclei()
                .equals(AccettazioneNucleoIseeAnagraficoEnum.NUCLEI_COINCIDONO));
  }
}
