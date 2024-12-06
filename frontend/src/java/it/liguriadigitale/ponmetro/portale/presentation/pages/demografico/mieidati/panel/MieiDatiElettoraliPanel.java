package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.demografico.model.TesseraElettorale;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.LoadData;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template.GeneratoreCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template.MieiDatiTemplate;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.core.NoContentException;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;

public class MieiDatiElettoraliPanel extends MieiDatiTemplate {

  private static final long serialVersionUID = -1642481424336076636L;

  private TesseraElettorale tesseraElettorale;

  public MieiDatiElettoraliPanel(String id) {
    super(id);
    Residente residente = LoadData.caricaMieiDatiResidente(getSession());
    fillDati(residente);
    log.debug("costruttore");
  }

  @Override
  protected void logicaBusinessDelPannello(Object dati)
      throws BusinessException, ApiException, NoContentException {
    log.debug("logicaBusinessDelPannello");
    Map<String, Object> mappaNomeValore = new LinkedHashMap<>();
    if (presenzaTesseraElettorale()) {

      mappaNomeValore = fillMappa(tesseraElettorale, mappaNomeValore);
      GeneratoreCardLabel<Utente> panel =
          new GeneratoreCardLabel<>("panel", mappaNomeValore, this.getClass().getSimpleName());
      myAdd(panel);
    } else {
      throw new NoContentException("Dati tessera elettorale non presenti");
    }
  }

  private boolean presenzaTesseraElettorale() throws BusinessException, ApiException {
    log.debug("presenzaTesseraElettorale");
    tesseraElettorale =
        ServiceLocator.getInstance()
            .getServizioDemografico()
            .getDatiTesseraElettorale(getUtente().getCodiceFiscaleOperatore());
    if (tesseraElettorale == null) return false;
    else return true;
  }

  protected Map<String, Object> fillMappa(
      TesseraElettorale tesseraElettorale, Map<String, Object> mappaNomeValore) {
    log.debug("fillMappa");
    mappaNomeValore.put("numeroTessera", tesseraElettorale.getNumeroTessera());
    mappaNomeValore.put(
        "comuneRilascio",
        (tesseraElettorale.getClvCity() == null
            ? null
            : tesseraElettorale.getClvCity().toLowerCase()));
    mappaNomeValore.put("dataRilascio", tesseraElettorale.getTiDate());
    mappaNomeValore.put("numeroSezione", tesseraElettorale.getNumeroSezione());
    mappaNomeValore.put(
        "indirizzoSezione",
        (tesseraElettorale.getIndirizzoSezione() == null
            ? null
            : tesseraElettorale.getIndirizzoSezione().toLowerCase()));

    return mappaNomeValore;
  }

  @Override
  protected void gestioneErroreBusiness(Exception e) {
    super.gestioneErroreBusiness(e);
    log.debug("gestioneErroreBusiness");
    String messaggioErrore = getString("MieiDatiTemplate.messaggioErrore");
    templateFeedBackPanel.warn(messaggioErrore);
    templateFeedBackPanel.setFilter(
        new ContainerFeedbackMessageFilter(MieiDatiElettoraliPanel.this));
  }
}
