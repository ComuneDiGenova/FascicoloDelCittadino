package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.dietespeciali.RevocaDietaSpeciale;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.DieteSpecialiPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.RevocaDietaSpecialePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.dietespeciali.form.RevocaDietaSpecialeForm;
import it.liguriadigitale.ponmetro.serviziristorazione.model.DatiDietaSpeciale;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

public class RevocaDietaSpecialePanel extends BasePanel {

  private static final long serialVersionUID = 127679166862105436L;

  private RevocaDietaSpecialeForm form = null;

  private DatiDietaSpeciale dieta;

  private RevocaDietaSpeciale revocaDietaSpeciale;

  private UtenteServiziRistorazione iscritto;

  private Integer index;

  private WebMarkupContainer containerStep1 = new WebMarkupContainer("containerStep1");

  private WebMarkupContainer containerMessaggio = new WebMarkupContainer("containerMessaggio");

  public RevocaDietaSpecialePanel(
      DatiDietaSpeciale dieta, UtenteServiziRistorazione iscritto, Integer index) {
    super("revocaDietaPanel");
    createFeedBackPanel();
    setOutputMarkupId(true);

    this.dieta = dieta;
    this.iscritto = iscritto;
    this.index = index;
    this.revocaDietaSpeciale = new RevocaDietaSpeciale();

    containerStep1.setOutputMarkupId(true);
    containerStep1.setVisible(index == 1);
    addOrReplace(containerStep1);

    containerMessaggio.setOutputMarkupId(true);
    containerMessaggio.setVisible(index != 1);
    addOrReplace(containerMessaggio);

    fillDati(dieta);
  }

  public RevocaDietaSpecialePanel(
      DatiDietaSpeciale dieta,
      UtenteServiziRistorazione iscritto,
      Integer index,
      String messaggioFeedback,
      RevocaDietaSpeciale revocaDietaSpeciale,
      boolean esitoRevoca) {
    super("revocaDietaPanel");

    createFeedBackPanel();
    setOutputMarkupId(true);

    this.dieta = dieta;
    this.iscritto = iscritto;
    this.index = index;
    this.revocaDietaSpeciale = revocaDietaSpeciale;

    containerStep1.setOutputMarkupId(true);
    containerStep1.setVisible(index == 1);
    addOrReplace(containerStep1);

    containerMessaggio.setOutputMarkupId(true);
    containerMessaggio.setVisible(index != 1);
    Label messaggio = new Label("messaggio", messaggioFeedback);
    containerMessaggio.addOrReplace(messaggio);

    if (esitoRevoca) {
      AttributeAppender attributeAppender = new AttributeAppender("class", "alert alert-success");
      containerMessaggio.add(attributeAppender);
    } else {
      AttributeAppender attributeAppender = new AttributeAppender("class", "alert alert-danger");
      containerMessaggio.add(attributeAppender);
    }
    addOrReplace(containerMessaggio);

    fillDati(dieta);
  }

  @Override
  public void fillDati(Object dati) {
    DatiDietaSpeciale dieta = (DatiDietaSpeciale) dati;

    form =
        new RevocaDietaSpecialeForm("form", revocaDietaSpeciale, getUtente(), dieta) {

          private static final long serialVersionUID = -6077278759276325882L;

          @Override
          protected void onSubmit() {
            log.debug("CP click revoca = " + revocaDietaSpeciale);

            revocaDietaSpeciale.setCodiceFiscaleIscritto(
                dieta.getCodiceFiscaleIscritto().toUpperCase());
            revocaDietaSpeciale.setIdentificativo(dieta.getIdentificativo());

            revocaDietaSpeciale.setCodiceFiscaleRichiedenteRevoca(
                getUtente().getCodiceFiscaleOperatore().toUpperCase());
            revocaDietaSpeciale.setCognomeRichiedenteRevoca(getUtente().getCognome().toUpperCase());
            revocaDietaSpeciale.setNomeRichiedenteRevoca(getUtente().getNome().toUpperCase());

            String messaggioFeedback = "";
            boolean esitoRevoca = false;
            try {
              ServiceLocator.getInstance()
                  .getServiziRistorazione()
                  .revocaDietaSpeciale(
                      revocaDietaSpeciale.getCodiceFiscaleIscritto(),
                      revocaDietaSpeciale.getIdentificativo(),
                      revocaDietaSpeciale.getCodiceFiscaleRichiedenteRevoca(),
                      revocaDietaSpeciale.getNomeRichiedenteRevoca(),
                      revocaDietaSpeciale.getCognomeRichiedenteRevoca(),
                      revocaDietaSpeciale.getEmailRichiedenteRevoca());

              messaggioFeedback =
                  "Richiesta revoca dieta inviata correttamente. La dieta non verrà più erogata indicativamente entro 2 giorni lavorativi.";
              esitoRevoca = true;

            } catch (ApiException e) {

              messaggioFeedback = "Attenzione, errore nella richiesta di revoca dieta speciale: ";

              String myMessage = e.getMyMessage();
              String eccezione = "javax.ws.rs.WebApplicationException:";
              String messaggioRicevuto = myMessage;
              if (myMessage.contains(eccezione)) {
                messaggioRicevuto = myMessage.substring(eccezione.length(), myMessage.length());
              } else {
                messaggioRicevuto = "Servizio momentaneamente non disponibile, riprovare più tardi";
              }
              log.debug("Errore gestito durante la chiamata delle API:" + myMessage, e);

              Integer indexOf = messaggioRicevuto.indexOf(":");
              String messaggioDaVisualizzare =
                  messaggioRicevuto.substring(indexOf + 1, messaggioRicevuto.length());

              messaggioFeedback = messaggioFeedback.concat(messaggioDaVisualizzare);
              esitoRevoca = false;

              log.error(
                  "Errore revoca dieta speciale: " + e.getMessage() + " - " + e.getMyMessage());

            } catch (BusinessException e) {
              log.error(
                  "BusinessException gestito durante la chiamata delle API revoca dieta: ", e);
            }

            index = index + 1;
            setResponsePage(
                new RevocaDietaSpecialePage(
                    dieta, iscritto, index, messaggioFeedback, revocaDietaSpeciale, esitoRevoca));
          }
        };

    form.addOrReplace(creaBtnAnnulla());
    form.setOutputMarkupId(true);
    containerStep1.addOrReplace(form);
  }

  /*
   * private LaddaAjaxLink<Object> creaBtnRevoca2(RevocaDietaSpeciale revoca)
   * { LaddaAjaxLink<Object> btnRevoca = new LaddaAjaxLink<Object>(
   * "btnRevoca", Type.Primary) {
   *
   * private static final long serialVersionUID = 4858643835046170787L;
   *
   * @Override public void onClick(AjaxRequestTarget target) {
   * target.add(RevocaDietaSpecialePanel.this);
   *
   * log.debug("CP click revoca = " + revoca);
   *
   * revocaDietaSpeciale.setCodiceFiscaleIscritto(dieta.
   * getCodiceFiscaleIscritto().toUpperCase());
   * revocaDietaSpeciale.setIdentificativo(dieta.getIdentificativo());
   *
   * revocaDietaSpeciale
   * .setCodiceFiscaleRichiedenteRevoca(getUtente().getCodiceFiscaleOperatore(
   * ).toUpperCase());
   * revocaDietaSpeciale.setCognomeRichiedenteRevoca(getUtente().getCognome().
   * toUpperCase());
   * revocaDietaSpeciale.setNomeRichiedenteRevoca(getUtente().getNome().
   * toUpperCase());
   *
   *
   * String messaggioFeedback = "";
   *
   * // qui chiamo servizio revoca try {
   * ServiceLocator.getInstance().getServiziRistorazione().
   * revocaDietaSpeciale( revoca.getCodiceFiscaleIscritto(),
   * revoca.getIdentificativo(), revoca.getCodiceFiscaleRichiedenteRevoca(),
   * revoca.getNomeRichiedenteRevoca(), revoca.getCognomeRichiedenteRevoca(),
   * revoca.getEmailRichiedenteRevoca());
   *
   * messaggioFeedback = "Richiesta revoca dieta inviata correttamente";
   *
   * } catch (ApiException | BusinessException e) {
   *
   * messaggioFeedback =
   * "Attenzione, errore nella richiesta di revoca dieta speciale. Si prega di riprovare più tardi. Grazie."
   * ;
   *
   * log.error("Errore revoca dieta speciale: " + e.getMessage());
   *
   * }
   *
   * index = index + 1; setResponsePage(new RevocaDietaSpecialePage(dieta,
   * iscritto, index, messaggioFeedback)); } }; btnRevoca.setLabel(
   * Model.of(Application.get().getResourceSettings().getLocalizer()
   * .getString("RevocaDietaSpecialePanel.prosegui",
   * RevocaDietaSpecialePanel.this)));
   *
   * btnRevoca.setVisible(index == 1);
   *
   * return btnRevoca; }
   */

  /*
   * private LaddaAjaxLink<Object> creaBtnRevoca(DatiDietaSpeciale dieta) {
   * LaddaAjaxLink<Object> btnRevoca = new LaddaAjaxLink<Object>( "btnRevoca",
   * Type.Primary) {
   *
   * private static final long serialVersionUID = 4858643835046170787L;
   *
   * @Override public void onClick(AjaxRequestTarget target) {
   * target.add(RevocaDietaSpecialePanel.this);
   *
   * String messaggioFeedback = "";
   *
   * // qui chiamo servizio revoca try {
   * ServiceLocator.getInstance().getServiziRistorazione().
   * revocaDietaSpeciale( dieta.getCodiceFiscaleIscritto(),
   * dieta.getIdentificativo());
   *
   * messaggioFeedback = "Richiesta revoca dieta inviata correttamente";
   *
   * } catch (ApiException | BusinessException e) {
   *
   * messaggioFeedback =
   * "Attenzione, errore nella richiesta di revoca dieta speciale. Si prega di riprovare più tardi. Grazie."
   * ;
   *
   * log.error("Errore revoca dieta speciale: " + e.getMessage());
   *
   * }
   *
   * index = index+1; setResponsePage(new RevocaDietaSpecialePage(dieta,
   * iscritto, index, messaggioFeedback)); } }; btnRevoca.setLabel(
   * Model.of(Application.get().getResourceSettings().getLocalizer()
   * .getString("RevocaDietaSpecialePanel.prosegui",
   * RevocaDietaSpecialePanel.this)));
   *
   * btnRevoca.setVisible(index == 1);
   *
   * return btnRevoca; }
   */

  private LaddaAjaxLink<Object> creaBtnAnnulla() {
    LaddaAjaxLink<Object> annulla =
        new LaddaAjaxLink<Object>("annulla", Type.Primary) {

          private static final long serialVersionUID = 2772094167956451979L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(RevocaDietaSpecialePanel.this);
            setResponsePage(new DieteSpecialiPage(iscritto));
          }
        };
    annulla.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString("RevocaDietaSpecialePanel.annulla", RevocaDietaSpecialePanel.this)));
    annulla.setVisible(index == 1);

    return annulla;
  }
}
