package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.genovaparcheggi.buttonLaddaGeParc;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.Permit;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitAllowedAction;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.FileFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import java.io.IOException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Component;

public class ScaricaQuietanzePanel extends BasePanel {

  private static final long serialVersionUID = -2372479771173568031L;

  private Permit permesso;
  private PermitAllowedAction azione;

  public ScaricaQuietanzePanel(String id, Permit permesso, PermitAllowedAction azione) {
    super(id);

    this.permesso = permesso;
    this.azione = azione;

    fillDati("");

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
  }

  @Override
  public void fillDati(Object dati) {
    addOrReplace(creaDownloadAllegato(permesso, azione));
  }

  @SuppressWarnings({"rawtypes"})
  private BottoneAJAXDownloadWithError creaDownloadAllegato(
      Permit permit, PermitAllowedAction azione) {

    BottoneAJAXDownloadWithError btnScaricaAllegato =
        new BottoneAJAXDownloadWithError("scaricaQuietanze", ScaricaQuietanzePanel.this) {

          private static final long serialVersionUID = -7748579762540044552L;

          @Override
          protected String creaLabelEtichetta(Component pannello, String id) {
            return azione.getOperationDescription();
          }

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            byte[] file = null;

            try {
              if (LabelFdCUtil.checkIfNotNull(permit)) {
                file =
                    ServiceLocator.getInstance()
                        .getServiziGenovaParcheggi()
                        .getAllegatoPdfQuietanza(permit);
              }

              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              fileDaScaricare.setFileBytes(file);

              if (LabelFdCUtil.checkIfNotNull(file)) {
                String estensione = FileFdCUtil.getEstensionFileUploadAllegato(file);
                String nomeFileQuietanza =
                    "Quietanza_".concat(permit.getPermitCode().concat(".").concat(estensione));
                fileDaScaricare.setFileName(nomeFileQuietanza);
              }

              fileDaScaricare.setEsitoOK(true);

              return fileDaScaricare;

            } catch (BusinessException
                | ApiException
                | IOException
                | MagicMatchNotFoundException e) {
              String prefisso = "GNC-000-Server was unable to process request. --->";
              String message = e.getMessage();
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              if (message.contains(prefisso)) {
                log.error("ERRORE API: " + e);
                fileDaScaricare.setMessaggioErrore(
                    message.replace(prefisso, "Errore durante scaricamento della quietanza"));

              } else {
                fileDaScaricare.setMessaggioErrore(message);
              }
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };
    btnScaricaAllegato.setVisibileDopoDownload(true);

    return btnScaricaAllegato;
  }
}
