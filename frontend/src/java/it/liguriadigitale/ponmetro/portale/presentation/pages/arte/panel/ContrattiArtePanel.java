package it.liguriadigitale.ponmetro.portale.presentation.pages.arte.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.ponmetro.arte.model.Contratto;
import it.liguriadigitale.ponmetro.arte.model.Daticontr;
import it.liguriadigitale.ponmetro.arte.model.Fattura;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLinkArte;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.arte.ComponentiNucleoFamiliareArtePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.arte.FattureArtePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.arte.MoreArtePage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class ContrattiArtePanel extends BasePanel {

  private static final long serialVersionUID = -866099960364357092L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public ContrattiArtePanel(String id) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);
  }

  @Override
  public void fillDati(Object dati) {
    Daticontr datiContratti = (Daticontr) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkContratti(datiContratti));
    addOrReplace(listaVuota);

    WebMarkupContainer containerIsee = new WebMarkupContainer("containerIsee");
    String iseeValore = "";
    if (LabelFdCUtil.checkIfNotNull(datiContratti)
        && PageUtil.isStringValid(datiContratti.getIsee())) {
      iseeValore = datiContratti.getIsee();
    }
    NotEmptyLabel isee =
        new NotEmptyLabel(
            "isee",
            new StringResourceModel("ContrattiArtePanel.isee", this).setParameters(iseeValore));
    containerIsee.addOrReplace(isee);
    containerIsee.setVisible(checkContratti(datiContratti));
    addOrReplace(containerIsee);

    WebMarkupContainer containerMessaggioIsee = new WebMarkupContainer("containerMessaggioIsee");
    containerMessaggioIsee.setVisible(checkAnnoIsee(datiContratti));
    addOrReplace(containerMessaggioIsee);

    addOrReplace(creaBtnComponenti(checkContratti(datiContratti)));

    List<Contratto> listaContratti = new ArrayList<>();
    if (LabelFdCUtil.checkIfNotNull(datiContratti)
        && LabelFdCUtil.checkIfNotNull(datiContratti.getContratti())) {
      listaContratti = datiContratti.getContratti();
    }
    PageableListView<Contratto> listView =
        new PageableListView<Contratto>("contratti", listaContratti, 4) {

          private static final long serialVersionUID = -3689267696156997458L;

          @Override
          protected void populateItem(ListItem<Contratto> itemContatto) {
            final Contratto contratto = itemContatto.getModelObject();

            NotEmptyLabel id = new NotEmptyLabel("id", contratto.getId());
            itemContatto.addOrReplace(id);

            itemContatto.addOrReplace(
                new AmtCardLabel<>("desctui", contratto.getDesCTui(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>("indirizzo", contratto.getIndirizzo(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>("localita", contratto.getLocalita(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>(
                    "desContratto", contratto.getDesContratto(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>("numReg", contratto.getNumReg(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>("serie", contratto.getSerie(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>("anno", contratto.getAnno(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>(
                    "dregcontratto", contratto.getdRegContratto(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>(
                    "dscadcontratto", contratto.getdScadContratto(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>("cCat", contratto.getcCat(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>("rendCat", contratto.getRenCatT(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>("sezione", contratto.getSezione(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>("foglio", contratto.getFoglio(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>(
                    "subalterno", contratto.getSubalterno(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>("mappale", contratto.getMappale(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>(
                    "canoneAnnuo", contratto.getCanoneAnnuo(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>(
                    "desctuipert", contratto.getDesCTuiPert(), ContrattiArtePanel.this));

            itemContatto.addOrReplace(
                new AmtCardLabel<>(
                    "indirizzoPert", contratto.getIndirizzoPert(), ContrattiArtePanel.this));

            List<Fattura> fatture = contratto.getFatture();
            itemContatto.addOrReplace(creaBtnFatture(fatture));

            itemContatto.addOrReplace(creaBtnMore(contratto.getId()));
          }
        };

    listView.setVisible(checkContratti(datiContratti));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkContratti(datiContratti) && listaContratti.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkContratti(Daticontr datiContratti) {
    return LabelFdCUtil.checkIfNotNull(datiContratti)
        && LabelFdCUtil.checkIfNotNull(datiContratti.getContratti())
        && !LabelFdCUtil.checkEmptyList(datiContratti.getContratti());
  }

  private boolean checkAnnoIsee(Daticontr datiContratti) {
    boolean annoOk = false;

    Integer annoInCorso = LocalDate.now().getYear();
    String annoIseeArte = "";
    int annoIseeArteInt = 0;
    if (LabelFdCUtil.checkIfNotNull(datiContratti)
        && PageUtil.isStringValid(datiContratti.getIsee())) {
      annoIseeArte = datiContratti.getIsee();

      annoIseeArteInt = Integer.parseInt(annoIseeArte);
    }
    Integer annoMinimoIsee = annoInCorso - 3;

    if (PageUtil.isStringValid(annoIseeArte) && annoIseeArteInt >= annoMinimoIsee) {
      annoOk = true;
    }

    return annoOk;
  }

  private FdCButtonBootstrapAjaxLinkArte<Object> creaBtnComponenti(boolean contrattiPresenti) {
    FdCButtonBootstrapAjaxLinkArte<Object> btnComponenti =
        new FdCButtonBootstrapAjaxLinkArte<Object>("btnComponenti", Type.Primary) {

          private static final long serialVersionUID = 1L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(ContrattiArtePanel.this);
            setResponsePage(new ComponentiNucleoFamiliareArtePage());
          }
        };

    btnComponenti.setLabel(Model.of(getString("ContrattiArtePanel.componenti")));

    btnComponenti.setVisible(contrattiPresenti);

    return btnComponenti;
  }

  private FdCButtonBootstrapAjaxLinkArte<Object> creaBtnFatture(List<Fattura> fatture) {
    FdCButtonBootstrapAjaxLinkArte<Object> btnFatture =
        new FdCButtonBootstrapAjaxLinkArte<Object>("btnFatture", Type.Primary) {

          private static final long serialVersionUID = 5532032978853904267L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(ContrattiArtePanel.this);
            setResponsePage(new FattureArtePage(fatture));
          }
        };

    btnFatture.setLabel(Model.of(getString("ContrattiArtePanel.fatture")));

    return btnFatture;
  }

  private FdCButtonBootstrapAjaxLinkArte<Object> creaBtnMore(String idImmobile) {
    FdCButtonBootstrapAjaxLinkArte<Object> btnMore =
        new FdCButtonBootstrapAjaxLinkArte<Object>("btnMore", Type.Primary) {

          private static final long serialVersionUID = -6529208313785196366L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(ContrattiArtePanel.this);

            // String idImmobileValue = String.valueOf(idImmobile);
            setResponsePage(new MoreArtePage(idImmobile));
          }
        };

    btnMore.setLabel(Model.of(getString("ContrattiArtePanel.more")));

    return btnMore;
  }
}
