package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.buttonLaddaGeParc;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.AvvisoPagoPA;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.Permit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.Model;

public class DueButtoniPagoPAGeparkPanel extends BasePanel {

  private static final long serialVersionUID = -2372479771173568031L;

  private Permit permesso;

  private PermitAllowedAction azione;

  private Log log = LogFactory.getLog(getClass());

  public DueButtoniPagoPAGeparkPanel(String id, Permit permesso, PermitAllowedAction azione) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.permesso = permesso;
    this.azione = azione;

    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {
    addOrReplace(creaBtnAzionePagaPaOnLine(permesso));
    addOrReplace(creaButtoneAllegatoGeneraAvviso(permesso, azione));
  }

  @SuppressWarnings("rawtypes")
  private FdCButtonBootstrapAjaxLink creaBtnAzionePagaPaOnLine(Permit permit) {

    log.debug("CP creaBtnAzionePagaPaOnLine ");

    FdCButtonBootstrapAjaxLink<Object> btnAzione =
        new FdCButtonBootstrapAjaxLink<Object>("btnAzioneLaddaPagoPa", Type.Primary) {

          private static final long serialVersionUID = -24738033520157567L;

          @Override
          public void onClick(AjaxRequestTarget target) {

            target.add(DueButtoniPagoPAGeparkPanel.this);

            String urlPerPagoPa = "";

            try {
              AvvisoPagoPA avviso =
                  ServiceLocator.getInstance()
                      .getServiziGenovaParcheggi()
                      .getAvvisoByPermitConBoolean(permit, false);

              if (LabelFdCUtil.checkIfNotNull(avviso)) {
                urlPerPagoPa =
                    BaseServiceImpl.URI_SERVER_PAGAMENTO_GEPARK
                        + "?iuv="
                        + avviso.getIuv()
                        + "&url="
                        + BaseServiceImpl.URL_PAGOPA_GEPARK_OK
                        + "&url_back="
                        + BaseServiceImpl.URL_PAGOPA_GEPARK_KO;

                throw new RestartResponseAtInterceptPageException(new RedirectPage(urlPerPagoPa));

              } else {
                throw new RestartResponseAtInterceptPageException(
                    new ErroreServiziPage("Avviso Pagamento con PagoPA"));
              }

            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore creaBtnAzionePagaPaOnLine PagoPa GePark = " + e.getMessage(), e);
            }
          }
        };

    btnAzione.setLabel(Model.of(getString("DueButtoniPagoPAGeparkPanel.paga")));

    // btnAzione.add(AttributeModifier.append("aaaeaaaa", "min-width:200px"));

    btnAzione.setOutputMarkupId(true);
    btnAzione.setOutputMarkupPlaceholderTag(true);

    return btnAzione;
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaButtoneAllegatoGeneraAvviso(
      Permit permit, PermitAllowedAction azione) {

    BottoneAJAXDownloadWithError btnScaricaAllegato =
        new BottoneAJAXDownloadWithError(
            "btnAzioneLaddaScaricaAvviso", DueButtoniPagoPAGeparkPanel.this) {

          private static final long serialVersionUID = 484110276400416126L;

          @Override
          protected String creaLabelEtichetta(Component pannello, String id) {
            log.debug("permit_description_test " + permit);
            log.debug("description_test " + permit.getOperationDescription());
            return azione.getOperationDescription();
          }

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {
            byte[] file = null;

            try {

              AvvisoPagoPA avviso =
                  ServiceLocator.getInstance()
                      .getServiziGenovaParcheggi()
                      .getAvvisoByPermitConBoolean(permit, true);

              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              if (LabelFdCUtil.checkIfNotNull(permit)
                  && LabelFdCUtil.checkIfNotNull(avviso)
                  && LabelFdCUtil.checkIfNotNull(avviso.getPdfNotice())) {

                file = DatatypeConverter.parseBase64Binary(avviso.getPdfNotice());
                fileDaScaricare.setFileBytes(file);

                String estensione = FileFdCUtil.getEstensionFileUploadAllegato(file);

                String nomeFileAvvisoPagoPa =
                    "AvvisoPagoPA_".concat(avviso.getIuv()).concat(".").concat(estensione);

                fileDaScaricare.setFileName(nomeFileAvvisoPagoPa);
                fileDaScaricare.setEsitoOK(true);
                return fileDaScaricare;

              } else {
                throw new RestartResponseAtInterceptPageException(
                    new ErroreServiziPage("Scarica Avviso PagoPA"));
              }

            } catch (Exception e) {

              log.debug("CP catch pdf pago pa = " + e);

              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              fileDaScaricare.setMessaggioErrore("Errore durante scarica Avviso PagoPA");

              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };

    btnScaricaAllegato.add(AttributeModifier.append("aaaeaaaa", "min-width:200px"));

    btnScaricaAllegato.setVisibileDopoDownload(true);

    return btnScaricaAllegato;
  }
}
