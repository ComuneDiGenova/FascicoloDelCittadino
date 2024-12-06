package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.pericolosita.model.Features;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.LoadData;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template.GeneratoreCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template.MieiDatiTemplate;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.core.NoContentException;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;

public class MieiDatiZonaRischioPanel extends MieiDatiTemplate {
  private static final long serialVersionUID = -4568270500517707529L;

  private Features pericolosita;
  private boolean presenzaDatiZonaRischio = false;
  private Residente residente = LoadData.caricaMieiDatiResidente(getSession());
  private Utente utente = getUtente();

  public MieiDatiZonaRischioPanel(String id) {
    super(id);
    fillDati(residente);
  }

  @Override
  protected void logicaBusinessDelPannello(Object dati)
      throws BusinessException, ApiException, IOException {

    getDatiZonaRischio();

    if (presenzaDatiZonaRischio) {
      Map<String, Object> mappaNomeValore = new LinkedHashMap<>();
      mappaNomeValore = fillMappa(pericolosita, mappaNomeValore);
      GeneratoreCardLabel<Residente> panel =
          new GeneratoreCardLabel<>("panel", mappaNomeValore, this.getClass().getSimpleName());
      myAdd(panel);
    } else {
      throw new NoContentException("Dati zona rischio non presenti");
    }
  }

  private void getDatiZonaRischio() throws BusinessException, ApiException, IOException {

    if (LabelFdCUtil.checkIfNotNull(utente)) {

      FeaturesGeoserver datiToponomy =
          ServiceLocator.getInstance()
              .getServiziGeoserver()
              .getToponomasticaResidenzaUtenteLoggato(utente);

      if (LabelFdCUtil.checkIfNotNull(datiToponomy)) {

        String codStrada = datiToponomy.getCOD_STRADA();
        String lettera = datiToponomy.getLETTERA();
        String numero = datiToponomy.getNUMERO();
        String colore = datiToponomy.getCOLORE();

        if (!PageUtil.isStringValid(lettera)) {
          lettera = "0";
        }
        if (!PageUtil.isStringValid(colore)) {
          colore = "N";
        }

        log.debug(
            "Dati toponomy: "
                + datiToponomy
                + " -- "
                + codStrada
                + " - "
                + lettera
                + " - "
                + numero
                + " - "
                + colore);

        pericolosita =
            ServiceLocator.getInstance()
                .getServiziPericolosita()
                .getPericolosita(codStrada, lettera, numero, colore);
        presenzaDatiZonaRischio = true;
      } else {
        presenzaDatiZonaRischio = false;
      }
    }
  }

  protected Map<String, Object> fillMappa(
      Features pericolosita, Map<String, Object> mappaNomeValore) {

    if (LabelFdCUtil.checkIfNotNull(pericolosita)
        && LabelFdCUtil.checkIfNotNull(pericolosita.getFeatures())
        && !LabelFdCUtil.checkEmptyList(pericolosita.getFeatures())
        && pericolosita.getFeatures().size() == 1) {

      String pericoloFrane = pericolosita.getFeatures().get(0).getProperties().getPERICOLOSFRANE();
      String pericoloIncendio = pericolosita.getFeatures().get(0).getProperties().getPERICOLOSINC();
      String pericoloIdro = pericolosita.getFeatures().get(0).getProperties().getPERICOLOSIDRO();

      if (PageUtil.isStringValid(pericoloIdro)) {
        mappaNomeValore.put("idraluica", pericoloIdro);
      } else {
        mappaNomeValore.put("idraluica", "NESSUNO");
      }

      if (PageUtil.isStringValid(pericoloFrane)) {
        mappaNomeValore.put("frane", pericoloFrane);
      } else {
        mappaNomeValore.put("frane", "NESSUNO");
      }

      if (PageUtil.isStringValid(pericoloIncendio)) {
        mappaNomeValore.put("incendio", pericoloIncendio);
      } else {
        mappaNomeValore.put("incendio", "NESSUNO");
      }
    } else {
      mappaNomeValore.put("incendio", "Non disponibile");
      mappaNomeValore.put("frane", "Non disponibile");
      mappaNomeValore.put("idraluica", "Non disponibile");
    }
    return mappaNomeValore;
  }

  @Override
  protected void gestioneErroreBusiness(Exception e) {
    super.gestioneErroreBusiness(e);
    log.debug("gestioneErroreBusiness");
    String messaggioErrore = getString("MieiDatiTemplate.messaggioErrore");
    templateFeedBackPanel.warn(messaggioErrore);
    templateFeedBackPanel.setFilter(
        new ContainerFeedbackMessageFilter(MieiDatiZonaRischioPanel.this));
  }
}
