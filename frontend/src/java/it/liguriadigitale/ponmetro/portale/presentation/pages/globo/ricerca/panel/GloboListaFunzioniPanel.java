package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.ricerca.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.api.pojo.links.CfgTCatFunzLink;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.globo.Node;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadata;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.dto.LinkGloboMetadataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.linkdinamico.LinkDinamicoGloboConLink;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;

public class GloboListaFunzioniPanel extends Panel {

  private static final long serialVersionUID = 4961825517123020234L;

  private Log log = LogFactory.getLog(GloboListaFunzioniPanel.class);
  private List<Node> listaFiltrata;

  public GloboListaFunzioniPanel(String id) {
    this(id, new ArrayList<>());
  }

  public GloboListaFunzioniPanel(String id, List<Node> listaNodi) {
    super(id);
    log.debug("INIZIO GloboListaFunzioniPanel");
    log.debug("listaNodi= " + listaNodi.size());
    listaFiltrata = listaNodi;
    creaPannello();
  }

  private void creaPannello() {
    creaTitoloPagina();
    ListView<Node> listView =
        new ListView<Node>("lista", listaFiltrata) {

          private static final long serialVersionUID = 6896972623840638599L;

          @Override
          protected void populateItem(ListItem<Node> riga) {

            Node record = riga.getModelObject();
            riga.setRenderBodyOnly(true);
            log.debug("Nodo selezionato: " + record);
            String iconaCss = getIconaCssDaFunzioneFdc(record);
            LinkGloboMetadata linkDinamicoFunzioneData =
                LinkGloboMetadataBuilder.getInstance().build(record, iconaCss);

            List<CfgTCatFunzLink> lista = new ArrayList<>();
            try {
              log.debug("idfunz record = " + record.getCodice_fdc().toString());
              lista =
                  ServiceLocatorLivelloUno.getInstance()
                      .getApiHomePage()
                      .getLinkEsterniPerIdFunz(String.valueOf(record.getCodice_fdc()));
              lista =
                  lista.stream()
                      .filter(
                          elem ->
                              elem.getCodiceMaggioli()
                                  .equals(BigDecimal.valueOf(record.getCodice_maggioli())))
                      .collect(Collectors.toList());
              linkDinamicoFunzioneData.setListaLinkEsterni(lista);
              log.debug(
                  "listaLinkEsterni.size = "
                      + linkDinamicoFunzioneData.getListaLinkEsterni().size());

            } catch (BusinessException e) {
              log.warn(
                  "errore in ricerca lista link esterni per idfunz in GloboListaFunzioniPanel = "
                      + e);
            }

            LinkDinamicoGloboConLink<Object> link =
                new LinkDinamicoGloboConLink<>(
                    "denominazioneProcedimento",
                    linkDinamicoFunzioneData,
                    null,
                    GloboListaFunzioniPanel.this,
                    true);
            riga.add(link);
          }
        };
    add(listView);
    log.debug("FINE GloboListaFunzioniPanel");
  }

  @SuppressWarnings("unused")
  private String getUrlApplicazione() {
    final Url clientUrl = RequestCycle.get().getRequest().getClientUrl();
    log.debug("getProtocol: " + clientUrl.getProtocol());
    log.debug("getHost: " + clientUrl.getHost());
    log.debug("getPort: " + clientUrl.getPort());
    String porta = "";
    if (clientUrl.getPort() != null) {
      porta = ":" + clientUrl.getPort();
    }
    return clientUrl.getProtocol() + "//" + clientUrl.getHost() + porta + "/";
  }

  private void creaTitoloPagina() {
    String nomeFunzione = "Nessun procedimento trovato";
    if (listaFiltrata.size() > 1) {
      nomeFunzione = "Trovati n." + listaFiltrata.size() + " procedimenti";
    } else if (listaFiltrata.size() == 1) {
      nomeFunzione = "Trovato un procedimento";
    }
    NotEmptyLabel label = new NotEmptyLabel("nomeFunzione", nomeFunzione);
    add(label);
  }

  public String getIconaCssDaFunzioneFdc(Node record) {
    Long codiceFdc = record.getCodice_fdc();
    if (codiceFdc != null) {
      LoginInSession session = (LoginInSession) Session.get();
      List<FunzioniDisponibili> lista = session.getPagineAbilitate();
      for (FunzioniDisponibili funzione : lista) {
        String iconaCss = funzione.getIconaCss();
        if (funzione.getIdFunz().equals(codiceFdc)) {
          return iconaCss;
        }
      }
    }
    return "icon-foglio-penna col-auto color-blue-sebina";
  }
}
