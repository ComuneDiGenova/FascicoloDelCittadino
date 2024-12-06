package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.VCfgTCatGlobo;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadata;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.linkdinamico.LinkDinamicoGloboConLink;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

public class GloboListaTipoProcedimentiPanel extends Panel {
  private Log log = LogFactory.getLog(getClass());
  private static final long serialVersionUID = 1324516435596434302L;
  private List<VCfgTCatGlobo> listaFiltrata;

  public GloboListaTipoProcedimentiPanel(String id, List<VCfgTCatGlobo> listaFiltrata) {
    super(id);
    this.listaFiltrata = listaFiltrata;
  }

  @Override
  protected void onConfigure() {
    log.debug("GloboListaTipoProcedimentiPanel -- inizio");
    super.onConfigure();
    creaTitoloPagina();
    ListView<VCfgTCatGlobo> listView =
        new ListView<VCfgTCatGlobo>("lista", listaFiltrata) {

          private static final long serialVersionUID = -7831590222387603790L;

          @Override
          protected void populateItem(ListItem<VCfgTCatGlobo> riga) {
            riga.setRenderBodyOnly(true);
            VCfgTCatGlobo record = riga.getModelObject();
            // riga.add(new Label("denominazioneProcedimento",
            // record.getDenominazioneProcedimento()));

            LinkGloboMetadata linkDinamicoFunzioneData =
                LinkGloboMetadataBuilder.getInstance().build(record);

            linkDinamicoFunzioneData.setListaLinkEsterni(
                getListaLinkConCodiceMaggioli(BigDecimal.valueOf(record.getIdMaggioli())));

            LinkDinamicoGloboConLink<Object> link =
                new LinkDinamicoGloboConLink<>(
                    "denominazioneProcedimento",
                    linkDinamicoFunzioneData,
                    null,
                    GloboListaTipoProcedimentiPanel.this,
                    true);
            riga.add(link);
          }
        };
    add(listView);
  }

  private List<CfgTCatFunzLink> getListaLinkConCodiceMaggioli(BigDecimal idMaggioli) {
    List<CfgTCatFunzLink> listaLinkEsterni = new ArrayList<CfgTCatFunzLink>();

    try {
      List<CfgTCatFunzLink> lista =
          ServiceLocatorLivelloUno.getInstance()
              .getApiHomePage()
              .getLinkEsterniPerIdFunz(String.valueOf("all"));

      if (lista.size() != 0) {

        log.debug("listaLinkConCodiceMaggioli = " + lista);
        for (CfgTCatFunzLink link : lista) {
          if (link.getCodiceMaggioli().equals(idMaggioli)) {
            link.setIdFunz(link.getCodiceMaggioli());
            listaLinkEsterni.add(link);
          }
        }
        return listaLinkEsterni;
      } else {
        return new ArrayList<CfgTCatFunzLink>();
      }
    } catch (BusinessException e) {
      log.error(e);
    }
    log.debug("getListaLinkConCodiceMaggioli > listaLinkEsterni blocco try non eseguito");
    return listaLinkEsterni;
  }

  private void creaTitoloPagina() {
    if (listaFiltrata.size() > 1) {
      VCfgTCatGlobo primoProcedimento = listaFiltrata.get(0);
      String nomeFunzione = primoProcedimento.getDescrizioneFunz();
      NotEmptyLabel label = new NotEmptyLabel("nomeFunzione", nomeFunzione);
      add(label);
    }
  }
}
