package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.panel;

import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.VCfgTCatGlobo;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.external.GloboExternalLink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.panel.Panel;

public class GloboListaProcedimentiPanel extends Panel {

  private static final long serialVersionUID = 1324516435596434302L;
  private VCfgTCatGlobo procedimento;

  private Log log = LogFactory.getLog(getClass());

  public GloboListaProcedimentiPanel(String id, VCfgTCatGlobo procedimento) {
    super(id);
    this.procedimento = procedimento;
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();
    log.debug("GloboListaProcedimentiPanel -- inizio");
    creaTitoloPagina();
    GloboExternalLink linkToGlobo =
        new GloboExternalLink("linkToGlobo", procedimento.getUrlSito(), "Apri nuovo procedimento");
    add(linkToGlobo);
  }

  private void creaTitoloPagina() {

    String nomeProcedimento = procedimento.getDenominazioneProcedimento();
    NotEmptyLabel label = new NotEmptyLabel("nomeProcedimento", nomeProcedimento);
    add(label);
  }
}
