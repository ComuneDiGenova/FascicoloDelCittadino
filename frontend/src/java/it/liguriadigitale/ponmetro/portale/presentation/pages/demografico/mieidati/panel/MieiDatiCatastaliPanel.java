package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.DatiCatastali;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.LoadData;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template.GeneratoreCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template.MieiDatiTemplate;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.core.NoContentException;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;

public class MieiDatiCatastaliPanel extends MieiDatiTemplate {

  private static final long serialVersionUID = -1642481424336076636L;

  private Residente residente = LoadData.caricaMieiDatiResidente(getSession());

  @SuppressWarnings("rawtypes")
  private CardLabel indirizzoLabel;

  private DatiCatastali datiCatastali;

  public MieiDatiCatastaliPanel(String id) {
    super(id);
    fillDati(residente);
  }

  @Override
  protected void logicaBusinessDelPannello(Object dati)
      throws BusinessException, ApiException, IOException {

    String indirizzo = "";
    if (residente.getCpvHasAddress() != null) {
      indirizzo = residente.getCpvHasAddress().getClvFullAddress().toLowerCase();
    }

    if (presenzaDatiCatastali()) {
      Map<String, Object> mappaNomeValore = fillMappa(datiCatastali, indirizzo);
      GeneratoreCardLabel<Utente> panel =
          new GeneratoreCardLabel<>("panel", mappaNomeValore, this.getClass().getSimpleName());
      myAdd(panel);
    } else {
      throw new NoContentException("Nessun dato catastale");
    }
  }

  private boolean presenzaDatiCatastali() throws BusinessException, ApiException {
    if (!getUtente().isResidente()) {
      return false;
    } else {
      datiCatastali =
          ServiceLocator.getInstance().getServizioDemografico().getDatiCatastali(getUtente());
      if (datiCatastali == null) return false;
      else return true;
    }
  }

  @Override
  protected void gestioneErroreBusiness(Exception e) {
    super.gestioneErroreBusiness(e);
    String messaggioErrore = getString("MieiDatiTemplate.messaggioErrore");
    templateFeedBackPanel.warn(messaggioErrore);
    templateFeedBackPanel.setFilter(
        new ContainerFeedbackMessageFilter(MieiDatiCatastaliPanel.this));
  }

  protected Map<String, Object> fillMappa(DatiCatastali datiCatastali, String indirizzo) {
    Map<String, Object> mappaNomeValore = new LinkedHashMap<>();
    mappaNomeValore.put("belfiore", datiCatastali.getCodiceComuneCatastale());
    mappaNomeValore.put("indirizzo", indirizzo);
    mappaNomeValore.put(
        "foglio", datiCatastali.getFoglio() + "/" + datiCatastali.getSezioneUrbana());
    mappaNomeValore.put(
        "particella", datiCatastali.getParticella() + "/" + datiCatastali.getSubalterno());
    mappaNomeValore.put("zona", datiCatastali.getZonaCensuaria());
    mappaNomeValore.put(
        "categoria", datiCatastali.getCategoria() + "/" + datiCatastali.getClasse());
    mappaNomeValore.put("consistenza", datiCatastali.getConsistenza());
    mappaNomeValore.put("superficie", datiCatastali.getSuperficieCatastale());
    mappaNomeValore.put("rendita", datiCatastali.getRendita());
    mappaNomeValore.put(
        "nota",
        "I dati non sono certificati, ma desunti dalle dichiarazioni effettuate al Comune dagli stessi cittadini");
    return mappaNomeValore;
  }
}
