package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.allertecortesia.model.DettagliUtente;
import it.liguriadigitale.ponmetro.api.pojo.messaggi.MessaggiInformativi;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteCpvHasAddress;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiCompletiRegistrazioneUtenteAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.pojo.allerte.cortesia.DatiRegistrazioneAllerteCortesia;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.servizicortesia.panel.datigenerali.DatiUtenteServiziCortesiaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

public class ServiziCortesiaConPrivacyPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -1218182417013177691L;

  private DettagliUtente dettagliUtente;
  private Panel panelRisultatoCortesia;

  public ServiziCortesiaConPrivacyPage() {
    this(null);

    Utente.inizializzaPrivacyServiziCortesia(getUtente());

    if (!getUtente().isServiziCortesiaPrivacyNonAccettata()) {
      throw new RestartResponseAtInterceptPageException(new ServiziCortesiaPrivacyPage());
    }

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  public ServiziCortesiaConPrivacyPage(DettagliUtente dettagliUtente) {
    super();

    Utente.inizializzaPrivacyServiziCortesia(getUtente());

    if (!getUtente().isServiziCortesiaPrivacyNonAccettata()) {
      throw new RestartResponseAtInterceptPageException(new ServiziCortesiaPrivacyPage());
    }

    this.dettagliUtente = dettagliUtente;
  }

  private DettagliUtente getDettagliUtenteByCf(String codiceFiscale)
      throws BusinessException, ApiException, IOException {
    return ServiceLocator.getInstance().getServiziAllerteCortesia().getWsLoginByCf(codiceFiscale);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  protected void onBeforeRender() {
    super.onBeforeRender();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    List<MessaggiInformativi> listaMessaggi =
        ServiceLocator.getInstance().getServiziAllerteCortesia().popolaListaMessaggiCortesia();

    AlertBoxPanel<Component> messaggi =
        (AlertBoxPanel<Component>)
            new AlertBoxPanel<Component>("messaggi", listaMessaggi).setRenderBodyOnly(true);
    addOrReplace(messaggi);

    try {
      if (dettagliUtente == null) {
        dettagliUtente = getDettagliUtenteByCf(getUtente().getCodiceFiscaleOperatore());
      }
      panelRisultatoCortesia =
          new DatiUtenteServiziCortesiaPanel("serviziCortesiaPanel", dettagliUtente);
      panelRisultatoCortesia.setVisible(
          dettagliUtente != null
              && dettagliUtente.getUTENTE() != null
              && dettagliUtente.getUTENTE().getEMAILVERIFICATA() != null
              && dettagliUtente.getUTENTE().getEMAILVERIFICATA().equalsIgnoreCase("S"));

    } catch (ApiException e) {
      NotificationPanel vuoto = creaFeedback("serviziCortesiaPanel");

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

      vuoto.error(messaggioDaVisualizzare);

      panelRisultatoCortesia = vuoto;

    } catch (IOException | BusinessException e) {
      log.debug("BusinessException gestito durante la chiamata delle API:", e);
    }

    addOrReplace(panelRisultatoCortesia);

    addOrReplace(creaBtnVerificaEmail(dettagliUtente));
    addOrReplace(creaBtnCambiaEmail(dettagliUtente));

    WebMarkupContainer nonIscritto = new WebMarkupContainer("nonIscritto");

    nonIscritto.addOrReplace(creaBtnPrimaRegistrazione());
    nonIscritto.addOrReplace(creaBtnRecuperaRegistrazione());

    nonIscritto.setVisible(!checkUtenteIscritto(dettagliUtente));
    nonIscritto.setOutputMarkupId(true);
    nonIscritto.setOutputMarkupPlaceholderTag(true);
    addOrReplace(nonIscritto);

    setOutputMarkupId(true);
  }

  private NotificationPanel creaFeedback(String panelId) {
    NotificationPanel vuoto =
        new NotificationPanel(panelId) {

          private static final long serialVersionUID = -2704806390703736280L;

          @Override
          protected boolean isCloseButtonVisible() {
            return false;
          }
        };
    add(vuoto);
    return vuoto;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnVerificaEmail(DettagliUtente dettagliUtente) {
    FdCButtonBootstrapAjaxLink<Object> btnVerificaEmail =
        new FdCButtonBootstrapAjaxLink<Object>("btnVerificaEmail", Type.Primary) {

          private static final long serialVersionUID = 5979717340051075728L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic verifica email = " + dettagliUtente);

            setResponsePage(new VerificaEmailAllerteCortesiaPage(dettagliUtente));
          }
        };

    btnVerificaEmail.setLabel(
        Model.of(getString("ServiziCortesiaConPrivacyPage.btnVerificaEmail")));

    btnVerificaEmail.setVisible(
        LabelFdCUtil.checkIfNotNull(dettagliUtente)
            && LabelFdCUtil.checkIfNotNull(dettagliUtente.getUTENTE())
            && PageUtil.isStringValid(dettagliUtente.getUTENTE().getEMAILVERIFICATA())
            && dettagliUtente.getUTENTE().getEMAILVERIFICATA().equalsIgnoreCase("N")
            && !panelRisultatoCortesia.isVisible());

    return btnVerificaEmail;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnCambiaEmail(DettagliUtente dettagliUtente) {
    FdCButtonBootstrapAjaxLink<Object> btnCambiaEmail =
        new FdCButtonBootstrapAjaxLink<Object>("btnCambiaEmail", Type.Primary) {

          private static final long serialVersionUID = 5979717340051075728L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            log.debug("CP clic cambia email = " + dettagliUtente);

            setResponsePage(new CambiaEmailAllerteCortesiaPage(dettagliUtente));
          }
        };

    btnCambiaEmail.setLabel(Model.of(getString("ServiziCortesiaConPrivacyPage.btnCambiaEmail")));

    btnCambiaEmail.setVisible(
        LabelFdCUtil.checkIfNotNull(dettagliUtente)
            && LabelFdCUtil.checkIfNotNull(dettagliUtente.getUTENTE())
            && PageUtil.isStringValid(dettagliUtente.getUTENTE().getEMAILVERIFICATA())
            && !panelRisultatoCortesia.isVisible());

    return btnCambiaEmail;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnPrimaRegistrazione() {
    FdCButtonBootstrapAjaxLink<Object> btnPrimaRegistrazione =
        new FdCButtonBootstrapAjaxLink<Object>("btnPrimaRegistrazione", Type.Primary) {

          private static final long serialVersionUID = 4954479516400131534L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(ServiziCortesiaConPrivacyPage.this);
            int index = 1;

            DatiCompletiRegistrazioneUtenteAllerteCortesia
                datiCompletiRegistrazioneUtenteAllerteCortesia =
                    new DatiCompletiRegistrazioneUtenteAllerteCortesia();

            DatiRegistrazioneAllerteCortesia datiRegistrazioneAllerteCortesia =
                new DatiRegistrazioneAllerteCortesia();
            datiRegistrazioneAllerteCortesia.setNome(getUtente().getNome());
            datiRegistrazioneAllerteCortesia.setCognome(getUtente().getCognome());
            datiRegistrazioneAllerteCortesia.setCodiceFiscale(
                getUtente().getCodiceFiscaleOperatore());
            datiRegistrazioneAllerteCortesia.setEmail(getUtente().getMail());
            datiRegistrazioneAllerteCortesia.setTelefonoCellulare(getUtente().getMobile());

            if (getUtente().isResidente()
                && LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())
                && LabelFdCUtil.checkIfNotNull(
                    getUtente().getDatiCittadinoResidente().getCpvHasAddress())) {
              ResidenteCpvHasAddress residenteCpvHasAddress =
                  getUtente().getDatiCittadinoResidente().getCpvHasAddress();
              datiRegistrazioneAllerteCortesia.setIndirizzo(
                  residenteCpvHasAddress.getClvFullAddress());
              datiRegistrazioneAllerteCortesia.setComune(residenteCpvHasAddress.getClvCity());
              datiRegistrazioneAllerteCortesia.setCap(residenteCpvHasAddress.getClvPostCode());
              datiRegistrazioneAllerteCortesia.setProvincia("GE");
            }

            datiCompletiRegistrazioneUtenteAllerteCortesia.setDatiRegistrazioneAllerteCortesia(
                datiRegistrazioneAllerteCortesia);

            log.debug(
                "CP creaBtnPrimaRegistrazione = " + datiCompletiRegistrazioneUtenteAllerteCortesia);

            CompoundPropertyModel<DatiCompletiRegistrazioneUtenteAllerteCortesia> datiCompleti =
                new CompoundPropertyModel<>(datiCompletiRegistrazioneUtenteAllerteCortesia);

            setResponsePage(new PrimaRegistrazioneServiziCortesiaPage(index, datiCompleti));
          }
        };
    btnPrimaRegistrazione.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "ServiziCortesiaConPrivacyPage.btnPrimaRegistrazione",
                    ServiziCortesiaConPrivacyPage.this)));

    IconType icon =
        new IconType("btnPrimaRegistrazione") {

          private static final long serialVersionUID = 1865608234655255453L;

          @Override
          public String cssClassName() {
            return "icon-servizi-allerta";
          }
        };
    btnPrimaRegistrazione.setIconType(icon);

    return btnPrimaRegistrazione;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnRecuperaRegistrazione() {
    FdCButtonBootstrapAjaxLink<Object> btnRecuperaRegistrazione =
        new FdCButtonBootstrapAjaxLink<Object>("btnRecuperaRegistrazione", Type.Primary) {

          private static final long serialVersionUID = 2175073687905242679L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(ServiziCortesiaConPrivacyPage.this);

            setResponsePage(new RecuperaRegistrazioneServiziCortesiaPage());
          }
        };
    btnRecuperaRegistrazione.setLabel(
        Model.of(
            Application.get()
                .getResourceSettings()
                .getLocalizer()
                .getString(
                    "ServiziCortesiaConPrivacyPage.btnRecuperaRegistrazione",
                    ServiziCortesiaConPrivacyPage.this)));

    IconType icon =
        new IconType("btnRecuperaRegistrazione") {

          private static final long serialVersionUID = 4555174330241554314L;

          @Override
          public String cssClassName() {
            return "icon-servizi-allerta";
          }
        };
    btnRecuperaRegistrazione.setIconType(icon);

    return btnRecuperaRegistrazione;
  }

  private boolean checkUtenteIscritto(DettagliUtente dettagliUtente) {
    return (LabelFdCUtil.checkIfNotNull(dettagliUtente));
  }
}
