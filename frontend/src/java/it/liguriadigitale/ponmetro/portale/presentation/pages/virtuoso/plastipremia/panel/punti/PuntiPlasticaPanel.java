package it.liguriadigitale.ponmetro.portale.presentation.pages.virtuoso.plastipremia.panel.punti;

import it.liguriadigitale.ponmetro.plastipremia.model.PlastiPunti;
import it.liguriadigitale.ponmetro.plastipremia.model.Storico;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class PuntiPlasticaPanel extends BasePanel {

  private static final long serialVersionUID = 8129252761288518372L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public PuntiPlasticaPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  public PuntiPlasticaPanel(String panelId, PlastiPunti popolaPuntiPlatica) {
    super(panelId);
    fillDati(popolaPuntiPlatica);

    setOutputMarkupId(true);
    createFeedBackPanel();
  }

  @Override
  public void fillDati(Object dati) {
    PlastiPunti puntiPlastica = (PlastiPunti) dati;

    List<Storico> listaStorico = new ArrayList<Storico>();
    if (LabelFdCUtil.checkIfNotNull(puntiPlastica)
        && LabelFdCUtil.checkIfNotNull(puntiPlastica.getStorico())) {
      listaStorico = puntiPlastica.getStorico();
    }

    Label codiceFiscale = new Label("codiceFiscale", puntiPlastica.getCodiceFiscale());
    codiceFiscale.setVisible(LabelFdCUtil.checkIfNotNull(puntiPlastica.getCodiceFiscale()));
    addOrReplace(codiceFiscale);

    String utenteRestratoValue =
        puntiPlastica.getUtenteRegistrato() != null && puntiPlastica.getUtenteRegistrato() == true
            ? "Sì"
            : "No";
    Label utenteRegistrato = new Label("utenteRegistrato", utenteRestratoValue);
    utenteRegistrato.setVisible(LabelFdCUtil.checkIfNotNull(puntiPlastica.getUtenteRegistrato()));
    addOrReplace(utenteRegistrato);

    ExternalLink linkRegistrazione =
        new ExternalLink("linkRegistrazione", puntiPlastica.getUrlRegistrazione());
    linkRegistrazione.setVisible(LabelFdCUtil.checkIfNotNull(puntiPlastica.getUrlRegistrazione()));
    linkRegistrazione.setVisible(!puntiPlastica.getUtenteRegistrato());
    addOrReplace(linkRegistrazione);

    String puntiAttualiTotaliValue = "";
    if (LabelFdCUtil.checkIfNotNull(puntiPlastica.getPuntiAttuali())) {
      puntiAttualiTotaliValue = String.valueOf(puntiPlastica.getPuntiAttuali());
    }
    Label puntiAttuali = new Label("puntiAttuali", puntiAttualiTotaliValue);
    puntiAttuali.setVisible(PageUtil.isStringValid(puntiAttualiTotaliValue));
    addOrReplace(puntiAttuali);

    PageableListView<Storico> listView =
        new PageableListView<Storico>("box", listaStorico, 6) {

          private static final long serialVersionUID = -6372272791067556311L;

          @Override
          protected void populateItem(ListItem<Storico> item) {
            final Storico storico = item.getModelObject();

            Label anno = new Label("anno", storico.getAnno());
            anno.setVisible(LabelFdCUtil.checkIfNotNull(storico.getAnno()));
            item.addOrReplace(anno);

            String puntiAccumulatiValue = "";
            if (LabelFdCUtil.checkIfNotNull(storico.getPuntiAccumulati())) {
              puntiAccumulatiValue = String.valueOf(storico.getPuntiAccumulati());
            }
            String puntiUtilizzatiValue = "";
            if (LabelFdCUtil.checkIfNotNull(storico.getPuntiUtilizzati())) {
              puntiUtilizzatiValue = String.valueOf(storico.getPuntiUtilizzati());
            }

            Label puntiAccumulati = new Label("puntiAccumulati", puntiAccumulatiValue);
            puntiAccumulati.setVisible(PageUtil.isStringValid(puntiAccumulatiValue));
            item.addOrReplace(puntiAccumulati);

            Label puntiUtilizzati = new Label("puntiUtilizzati", puntiUtilizzatiValue);
            puntiUtilizzati.setVisible(PageUtil.isStringValid(puntiUtilizzatiValue));
            item.addOrReplace(puntiUtilizzati);
          }
        };

    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("paginationPlasTiPremia", listView);
    paginazioneFascicolo.setVisible(
        LabelFdCUtil.checkIfNotNull(listaStorico)
            && !LabelFdCUtil.checkEmptyList(listaStorico)
            && listaStorico.size() > 6);
    addOrReplace(paginazioneFascicolo);
  }
}
