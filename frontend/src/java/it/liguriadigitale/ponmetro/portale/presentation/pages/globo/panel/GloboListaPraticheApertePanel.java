package it.liguriadigitale.ponmetro.portale.presentation.pages.globo.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.backend.temp.VCfgTCatGlobo;
import it.liguriadigitale.ponmetro.portale.pojo.globo.pratica.PraticaGlobo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.GloboPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.common.external.GloboExternalLink;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

public class GloboListaPraticheApertePanel extends Panel {

  private static final long serialVersionUID = 1324516435596434302L;

  private Log log = LogFactory.getLog(getClass());
  private static final String STATO_COMPILAZIONE = "COMPILAZIONE";
  private VCfgTCatGlobo tipoProcedimento;

  public GloboListaPraticheApertePanel(String id, VCfgTCatGlobo tipoProcedimento) {
    super(id);
    this.tipoProcedimento = tipoProcedimento;
  }

  @Override
  protected void onConfigure() {
    super.onConfigure();
    log.debug("GloboListaPraticheApertePanel -- inizio");
    List<PraticaGlobo> listaPratiche = recuperaProcedimentiAperti();
    List<PraticaGlobo> listaPratichePerTipo = filtraListaPratiche(listaPratiche);
    List<PraticaGlobo> listaOrdinata = ordinaListaPerData(listaPratichePerTipo);

    ListView<PraticaGlobo> listView =
        new ListView<PraticaGlobo>("lista", listaOrdinata) {

          private static final long serialVersionUID = -7831590222387603790L;

          @Override
          protected void populateItem(ListItem<PraticaGlobo> riga) {
            riga.setRenderBodyOnly(true);
            PraticaGlobo record = riga.getModelObject();
            NotEmptyLabel label =
                new NotEmptyLabel("denominazioneProcedimento", record.getDescrizioneModulo());
            riga.add(label);
            CardLabel<GloboListaPraticheApertePanel> idPratica =
                new CardLabel<>(
                    "idPratica", record.getId().toString(), GloboListaPraticheApertePanel.this);
            CardLabel<GloboListaPraticheApertePanel> labelData =
                new CardLabel<>(
                    "data", record.getDataModifica(), GloboListaPraticheApertePanel.this);
            CardLabel<GloboListaPraticheApertePanel> labelStato =
                new CardLabel<>("stato", record.getStato(), GloboListaPraticheApertePanel.this);

            GloboExternalLink linkToGlobo =
                new GloboExternalLink(
                    "linkToGlobo",
                    BaseServiceImpl.URL_SSO_GLOBO + record.getUrl(),
                    geStringaPulsanteLinkToGlobo(record));
            GloboExternalLink scaricaPdf =
                new GloboExternalLink(
                    "scaricaPdf",
                    BaseServiceImpl.URL_SSO_GLOBO + record.getUrlDownload(),
                    "Scarica PDF");
            scaricaPdf.setVisible(!isInCompilazione(record));
            // linkToGlobo.setVisible(isInCompilazione(record));
            riga.add(linkToGlobo);
            riga.add(scaricaPdf);
            riga.add(labelData);
            riga.add(labelStato);
            riga.add(idPratica);
          }
        };
    add(new WebMarkupContainer("titolo").setVisible(!listaPratichePerTipo.isEmpty()));
    add(listView);
  }

  private List<PraticaGlobo> ordinaListaPerData(List<PraticaGlobo> listaPratichePerTipo) {
    List<PraticaGlobo> sorted =
        listaPratichePerTipo.stream()
            .sorted(Comparator.comparing(PraticaGlobo::getDataModifica).reversed())
            .collect(Collectors.toList());
    return sorted;
  }

  private boolean isInCompilazione(PraticaGlobo record) {
    if (StringUtils.isNotEmpty(record.getStato())) {
      return record.getStato().toUpperCase().contains(STATO_COMPILAZIONE);
    } else {
      return false;
    }
  }

  private List<PraticaGlobo> filtraListaPratiche(List<PraticaGlobo> listaPratiche) {
    if (!StringUtils.isEmpty(tipoProcedimento.getUrnProcedimentoGlobo())) {
      List<PraticaGlobo> listaFiltrata =
          listaPratiche.stream()
              .filter(
                  c ->
                      c.getURNModulo() != null
                          && c.getURNModulo()
                              .equalsIgnoreCase(tipoProcedimento.getUrnProcedimentoGlobo()))
              .collect(Collectors.toList());
      log.debug("listaFiltrata.size=" + listaFiltrata.size());
      return listaFiltrata;
    } else {
      return listaPratiche;
    }
  }

  private List<PraticaGlobo> recuperaProcedimentiAperti() {

    try {
      return ServiceLocator.getInstance().getGlobo().getPraticheGlobo(getUtente());
    } catch (BusinessException e) {
      log.error("Errore:", e);
      return new ArrayList<>();
    }
  }

  private Utente getUtente() {
    GloboPage page = (GloboPage) getPage();
    return page.getUtente();
  }

  private String geStringaPulsanteLinkToGlobo(PraticaGlobo record) {
    if (isInCompilazione(record)) {
      return "Concludi";
    } else {
      return "Visualizza";
    }
  }
}
