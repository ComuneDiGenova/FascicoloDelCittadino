package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudioiban.panel;

import it.liguriadigitale.ponmetro.borsestudio.model.BorsaStudioIBAN;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class BorseStudioModificaIbanPanel extends BasePanel {

  private static final long serialVersionUID = 1166175861930647811L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public BorseStudioModificaIbanPanel(String id, List<BorsaStudioIBAN> listaPratiche) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    fillDati(listaPratiche);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<BorsaStudioIBAN> listaPratiche = (List<BorsaStudioIBAN>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkPratiche(listaPratiche));
    addOrReplace(listaVuota);

    log.debug("CP listaPratiche = " + listaPratiche);

    PageableListView<BorsaStudioIBAN> listView =
        new PageableListView<BorsaStudioIBAN>("pratiche", listaPratiche, 4) {

          private static final long serialVersionUID = 1822576709880774399L;

          @Override
          protected void populateItem(ListItem<BorsaStudioIBAN> itemPratica) {
            final BorsaStudioIBAN pratica = itemPratica.getModelObject();

            String idDomandaOnlineValue = "";
            String idDomandaValue = "";

            if (LabelFdCUtil.checkIfNotNull(pratica.getIdDomandaOnline())) {
              idDomandaOnlineValue = String.valueOf(pratica.getIdDomandaOnline());
            }

            if (LabelFdCUtil.checkIfNotNull(pratica.getIdDomanda())) {
              idDomandaValue = String.valueOf(pratica.getIdDomanda());
            }

            Label idDomandaOnline = new Label("idDomandaOnline", idDomandaOnlineValue);
            idDomandaOnline.setVisible(PageUtil.isStringValid(idDomandaOnlineValue));
            itemPratica.addOrReplace(idDomandaOnline);

            Label idDomanda = new Label("idDomanda", idDomandaValue);
            idDomanda.setVisible(PageUtil.isStringValid(idDomandaValue));
            itemPratica.addOrReplace(idDomanda);

            String intestatarioBorsa = "";
            if (PageUtil.isStringValid(pratica.getNomeIntestatarioBorsa())) {
              intestatarioBorsa = pratica.getNomeIntestatarioBorsa().concat(" ");
            }
            if (PageUtil.isStringValid(pratica.getCognomeIntestatarioBorsa())) {
              intestatarioBorsa = intestatarioBorsa.concat(pratica.getCognomeIntestatarioBorsa());
            }

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "intestatarioBorsa", intestatarioBorsa, BorseStudioModificaIbanPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "codiceFiscaleIntestatarioBorsa",
                    pratica.getCodiceFiscaleIntestatarioBorsa(),
                    BorseStudioModificaIbanPanel.this));

            String richiedente = "";
            if (PageUtil.isStringValid(pratica.getNomeRichiedente())) {
              richiedente = pratica.getNomeRichiedente().concat(" ");
            }
            if (PageUtil.isStringValid(pratica.getCognomeRichiedente())) {
              richiedente = richiedente.concat(pratica.getCognomeRichiedente());
            }

            itemPratica.addOrReplace(
                new AmtCardLabel<>("richiedente", richiedente, BorseStudioModificaIbanPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "codiceFiscaleRichiedente",
                    pratica.getCodiceFiscaleRichiedente(),
                    BorseStudioModificaIbanPanel.this));

            DatiModificaBorseStudioIbanPanel datiModificaBorseStudioIbanPanel =
                new DatiModificaBorseStudioIbanPanel("datiModificaBorseStudioIbanPanel", pratica);
            datiModificaBorseStudioIbanPanel.setMarkupId("dati" + pratica.getIdDomandaOnline());
            itemPratica.addOrReplace(datiModificaBorseStudioIbanPanel);

            itemPratica.setMarkupId("pratica" + pratica.getIdDomandaOnline());
          }
        };

    listView.setVisible(checkPratiche(listaPratiche));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkPratiche(listaPratiche) && listaPratiche.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkPratiche(List<BorsaStudioIBAN> listaPratiche) {
    return LabelFdCUtil.checkIfNotNull(listaPratiche)
        && !LabelFdCUtil.checkEmptyList(listaPratiche);
  }
}
