package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.cedole.CedoleMinore;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.panel.template.CedoleTemplate;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.statodomanda.DomandaAccettataPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.statodomanda.DomandaInFaseInserimentoPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.statodomanda.DomandaInFaseIstruttoriaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.statodomanda.DomandaNonPresentataPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.statodomanda.DomandaRifiutataPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cedola.StatoDomandaEnum;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.io.IOException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class DomandaCedolePanel extends CedoleTemplate {

  private static final long serialVersionUID = -3021289773196275267L;

  public DomandaCedolePanel(String id, CedoleMinore cedola) {
    super(id);
    setOutputMarkupId(true);
    fillDati(cedola);
  }

  @Override
  protected void logicaBusinessDelPannello(Object dati)
      throws BusinessException, ApiException, IOException {

    CedoleMinore cedolaMinore = (CedoleMinore) dati;
    if (cedolaMinore.getErrore()) {
      throw new BusinessException("Backend cedole in errore");
    }
    creaPannelloDomandaCedola(cedolaMinore);
    myAdd(new NotEmptyLabel("stato", getSstatoDomanda(cedolaMinore.getCedola())));

    WebMarkupContainer statoInIstruttoria = new WebMarkupContainer("statoInIstruttoria");
    statoInIstruttoria.setVisible(
        LabelFdCUtil.checkIfNotNull(cedolaMinore.getCedola())
            && LabelFdCUtil.checkIfNotNull(cedolaMinore.getCedola().getStatoDomanda())
            && cedolaMinore
                .getCedola()
                .getStatoDomanda()
                .equalsIgnoreCase(StatoDomandaEnum.IN_ISTRUTTORIA.value()));
    myAdd(statoInIstruttoria);
  }

  private String getSstatoDomanda(Cedola cedola) {
    if (cedola != null) {
      return cedola.getStatoDomanda().replace("_", " ");
    } else {
      return StatoDomandaEnum.NON_PRESENTATA.toString().replace("_", " ");
    }
  }

  private void creaPannelloDomandaCedola(CedoleMinore cedolaMinore) {
    log.debug("creaPannelloDomandaCedola");
    Cedola cedola = cedolaMinore.getCedola();
    UtenteServiziRistorazione minore = cedolaMinore.getMinore();
    String wicketID = "statoDomanda";
    if (cedola == null) {
      DomandaNonPresentataPanel panel = new DomandaNonPresentataPanel(wicketID, cedolaMinore);
      myAdd(panel);
    } else if (isDomandaNonPresentata(cedola)) {
      log.debug("---NON_PRESENTATA");
      DomandaNonPresentataPanel panel = new DomandaNonPresentataPanel(wicketID, cedolaMinore);
      myAdd(panel);
    } else if (isDomandaRifiutata(cedola) || isDomandaAnnullata(cedola)) {
      log.debug("---RIFIUTATA");
      DomandaRifiutataPanel panel = new DomandaRifiutataPanel(wicketID, cedolaMinore);
      myAdd(panel);
    } else if (isDomandaInFaseIstruttuoria(cedola)) {
      log.debug("---IN_ISTRUTTORIA");
      DomandaInFaseIstruttoriaPanel panel = new DomandaInFaseIstruttoriaPanel(wicketID, cedola);
      myAdd(panel);
    } else if (isDomandaInFaseInserimento(cedola)) {
      log.debug("---IN_FASE_DI_INSERIMENTO");
      DomandaInFaseInserimentoPanel panel = new DomandaInFaseInserimentoPanel(wicketID, cedola);
      myAdd(panel);
    } else if (isDomandaAccettata(cedola)) {

      log.debug("---ACCETTATA");
      DomandaAccettataPanel panel = new DomandaAccettataPanel(wicketID, cedola);
      myAdd(panel);
    } else {
      creaEmptyPanel(wicketID);
    }
  }

  private void creaEmptyPanel(String wicketID) {
    log.debug("---EmptyPanel");
    EmptyPanel panel = new EmptyPanel(wicketID);
    myAdd(panel);
  }

  private boolean isDomandaAnnullata(Cedola cedola) {
    if (cedola.getStatoDomanda() != null) {
      return cedola.getStatoDomanda().equalsIgnoreCase(StatoDomandaEnum.ANNULLATA.toString());
    } else {
      return false;
    }
  }

  private boolean isDomandaAccettata(Cedola cedola) {
    if (cedola.getStatoDomanda() != null) {
      return cedola.getStatoDomanda().equalsIgnoreCase(StatoDomandaEnum.ACCETTATA.toString());
    } else {
      return false;
    }
  }

  private boolean isDomandaInFaseInserimento(Cedola cedola) {
    if (cedola.getStatoDomanda() != null) {
      return cedola
          .getStatoDomanda()
          .equalsIgnoreCase(StatoDomandaEnum.IN_FASE_DI_INSERIMENTO.toString());
    } else {
      return false;
    }
  }

  private boolean isDomandaInFaseIstruttuoria(Cedola cedola) {
    if (cedola.getStatoDomanda() != null) {
      return cedola.getStatoDomanda().equalsIgnoreCase(StatoDomandaEnum.IN_ISTRUTTORIA.toString());
    } else {
      return false;
    }
  }

  private boolean isDomandaNonPresentata(Cedola cedola) {
    if (cedola.getStatoDomanda() != null) {
      return cedola.getStatoDomanda().equalsIgnoreCase(StatoDomandaEnum.NON_PRESENTATA.toString());
    } else {
      return false;
    }
  }

  private boolean isDomandaRifiutata(Cedola cedola) {
    if (cedola.getStatoDomanda() != null) {
      return cedola.getStatoDomanda().equalsIgnoreCase(StatoDomandaEnum.RIFIUTATA.toString());
    } else {
      return false;
    }
  }
}
