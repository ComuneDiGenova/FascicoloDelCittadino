package it.liguriadigitale.ponmetro.portale.presentation.pages.domandeMatrimonio.panel;

import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.DatiMatrimonio;
import it.liguriadigitale.ponmetro.portale.business.accenture.matrimonio.utils.TipologiaRichiestaEnum;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.util.List;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.StringResourceModel;

public class DomandeMatrimonioPanel extends BasePanel {

  private static final long serialVersionUID = 2992378006382767303L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private TipologiaRichiestaEnum tipologia;

  public TipologiaRichiestaEnum getTipologia() {
    return tipologia;
  }

  public void setTipologia(TipologiaRichiestaEnum tipologia) {
    this.tipologia = tipologia;
  }

  private static final String ICON_CUORE = "color-yellow col-3 icon-aggiungi-preferiti";
  private static final String ICON_FOGLI = "color-yellow col-3 icon-fogli";

  public DomandeMatrimonioPanel(
      String id, List<DatiMatrimonio> matrimoni, TipologiaRichiestaEnum tipologia) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    setTipologia(tipologia);

    fillDati(matrimoni);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<DatiMatrimonio> listaMatrimoni = (List<DatiMatrimonio>) dati;

    String descrizioneFunzione = "";

    if (getTipologia().equals(TipologiaRichiestaEnum.MATRIMONIO)) {
      descrizioneFunzione = " pubblicazioni di matrimonio";
    } else if (getTipologia().equals(TipologiaRichiestaEnum.UNIONECIVILE)) {
      descrizioneFunzione = " richieste di unioni civili";
    } else if (getTipologia().equals(TipologiaRichiestaEnum.SEPARAZIONEDIVORZIO)) {
      descrizioneFunzione = " richieste di separazioni e divorzi";
    } else if (getTipologia().equals(TipologiaRichiestaEnum.TRASCRIZIONEMATRIMONIO)) {
      descrizioneFunzione = " trascrizioni di matrimonio";
    } else if (getTipologia().equals(TipologiaRichiestaEnum.TRASCRIZIONESCIGLIOMENTO)) {
      descrizioneFunzione = " trascrizioni di scioglimento unioni civili/divorzi";
    }

    NotEmptyLabel titolo =
        new NotEmptyLabel(
            "titolo",
            new StringResourceModel("DomandeMatrimonioPanel.titolo", this)
                .setParameters(descrizioneFunzione));
    addOrReplace(titolo);

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    NotEmptyLabel messaggioListaVuota =
        new NotEmptyLabel(
            "messaggioListaVuota",
            new StringResourceModel("DomandeMatrimonioPanel.vuoto", this)
                .setParameters(descrizioneFunzione));
    listaVuota.addOrReplace(messaggioListaVuota);
    listaVuota.setVisible(!checkMatrimoni(listaMatrimoni));
    addOrReplace(listaVuota);

    PageableListView<DatiMatrimonio> listView =
        new PageableListView<DatiMatrimonio>("matrimoni", listaMatrimoni, 4) {

          private static final long serialVersionUID = 5670876069947623574L;

          @Override
          protected void populateItem(ListItem<DatiMatrimonio> itemMatrimonio) {
            final DatiMatrimonio matrimonio = itemMatrimonio.getModelObject();

            log.debug("CP matrimonio = " + matrimonio);

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIcona(getTipologia()));
            itemMatrimonio.addOrReplace(icona);

            itemMatrimonio.addOrReplace(new NotEmptyLabel("name", matrimonio.getName()));

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>(
                    "statoPraticaC", matrimonio.getStatoPraticaC(), DomandeMatrimonioPanel.this));

            String dichiarante = "";
            if (PageUtil.isStringValid(matrimonio.getNominativoDichiarante())) {
              dichiarante = matrimonio.getNominativoDichiarante().toUpperCase();
            }
            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>("dichiarante", dichiarante, DomandeMatrimonioPanel.this));

            String dichiaranteEmail = matrimonio.getEmailDichiarante();
            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>(
                    "dichiaranteEmail", dichiaranteEmail, DomandeMatrimonioPanel.this));

            String dichiaranteCel = matrimonio.getCellulareDichiarante();
            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>("dichiaranteCel", dichiaranteCel, DomandeMatrimonioPanel.this));

            String coniuge = "";
            if (PageUtil.isStringValid(matrimonio.getNominativoConiuge())) {
              coniuge = matrimonio.getNominativoConiuge();
            }

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>("unendo", coniuge, DomandeMatrimonioPanel.this)
                    .setVisible(tipologia.equals(TipologiaRichiestaEnum.UNIONECIVILE)));

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>("coniuge", coniuge, DomandeMatrimonioPanel.this)
                    .setVisible(!tipologia.equals(TipologiaRichiestaEnum.UNIONECIVILE)));

            String coniugeEmail = matrimonio.getEmailConiuge();

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>("unendoEmail", coniugeEmail, DomandeMatrimonioPanel.this)
                    .setVisible(tipologia.equals(TipologiaRichiestaEnum.UNIONECIVILE)));

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>("coniugeEmail", coniugeEmail, DomandeMatrimonioPanel.this)
                    .setVisible(!tipologia.equals(TipologiaRichiestaEnum.UNIONECIVILE)));

            String coniugeCel = matrimonio.getCellulareConiuge();

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>("unendoCel", coniugeCel, DomandeMatrimonioPanel.this)
                    .setVisible(tipologia.equals(TipologiaRichiestaEnum.UNIONECIVILE)));

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>("coniugeCel", coniugeCel, DomandeMatrimonioPanel.this)
                    .setVisible(!tipologia.equals(TipologiaRichiestaEnum.UNIONECIVILE)));

            String dataCreazione = "";
            if (LabelFdCUtil.checkIfNotNull(matrimonio.getCreatedDate())) {
              dataCreazione = LocalDateUtil.getDataOraCorretteInItalia(matrimonio.getCreatedDate());
            }
            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>("createdDate", dataCreazione, DomandeMatrimonioPanel.this));

            String dataUltimaModifica = "";
            if (LabelFdCUtil.checkIfNotNull(matrimonio.getLastModifiedDate())) {
              dataUltimaModifica =
                  LocalDateUtil.getDataOraCorretteInItalia(matrimonio.getLastModifiedDate());
            }
            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>(
                    "lastModifiedDate", dataUltimaModifica, DomandeMatrimonioPanel.this));

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>(
                    "comuneC", matrimonio.getComuneC(), DomandeMatrimonioPanel.this));

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>(
                    "provinciaC", matrimonio.getProvinciaC(), DomandeMatrimonioPanel.this));

            String dataAppuntamentoC = "";
            if (LabelFdCUtil.checkIfNotNull(matrimonio.getDataAppuntamentoC())) {
              dataAppuntamentoC =
                  LocalDateUtil.getDataOraCorretteInItalia(matrimonio.getDataAppuntamentoC());
            }

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>(
                    "dataAppuntamentoC", dataAppuntamentoC, DomandeMatrimonioPanel.this));

            String dataAppuntamento2C = "";
            if (matrimonio.getDataSecondoAppuntamentoFormulaC() != null) {
              dataAppuntamento2C = matrimonio.getDataSecondoAppuntamentoFormulaC();
            }
            if (matrimonio.getOraSecondoAppuntamentoFormulaC() != null
                && !matrimonio.getOraSecondoAppuntamentoFormulaC().equalsIgnoreCase(":0")) {
              dataAppuntamento2C += " " + matrimonio.getOraSecondoAppuntamentoFormulaC();
            }

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>(
                        "dataAppuntamento2C", dataAppuntamento2C, DomandeMatrimonioPanel.this)
                    .setVisible(
                        !dataAppuntamento2C.isEmpty()
                            && tipologia.equals(TipologiaRichiestaEnum.SEPARAZIONEDIVORZIO)));

            //				LocalDate dataPresuntaC = null;
            //				if(LabelFdCUtil.checkIfNotNull(matrimonio.getDataPresuntaC())) {
            //					dataPresuntaC =
            // LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(matrimonio.getDataPresuntaC());
            //				}
            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>(
                    "dataPresuntaC", matrimonio.getDataPresuntaC(), DomandeMatrimonioPanel.this));

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>(
                    "numeroProtocolloC",
                    matrimonio.getNumeroProtocolloC(),
                    DomandeMatrimonioPanel.this));

            itemMatrimonio.addOrReplace(
                new AmtCardLabel<>("ritoC", matrimonio.getRitoC(), DomandeMatrimonioPanel.this)
                    .setVisible(!tipologia.equals(TipologiaRichiestaEnum.UNIONECIVILE)));

            String pdfMatrimonio = "";
            if (PageUtil.isStringValid(matrimonio.getuRLPubblicoPdfC())) {
              pdfMatrimonio = matrimonio.getuRLPubblicoPdfC();
            }
            ExternalLink uRLPubblicoPdfC = new ExternalLink("uRLPubblicoPdfC", pdfMatrimonio);
            uRLPubblicoPdfC.setVisible(PageUtil.isStringValid(pdfMatrimonio));
            itemMatrimonio.addOrReplace(uRLPubblicoPdfC);
          }
        };

    listView.setVisible(checkMatrimoni(listaMatrimoni));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkMatrimoni(listaMatrimoni) && listaMatrimoni.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkMatrimoni(List<DatiMatrimonio> listaMatrimoni) {
    return LabelFdCUtil.checkIfNotNull(listaMatrimoni)
        && !LabelFdCUtil.checkEmptyList(listaMatrimoni);
  }

  private AttributeAppender getCssIcona(TipologiaRichiestaEnum tipologia) {
    String css = "";

    log.debug("CP getCssIcona = " + tipologia);

    if (tipologia.equals(TipologiaRichiestaEnum.MATRIMONIO)
        || tipologia.equals(TipologiaRichiestaEnum.UNIONECIVILE)
        || tipologia.equals(TipologiaRichiestaEnum.TRASCRIZIONEMATRIMONIO)) {
      css = ICON_CUORE;
    } else if (tipologia.equals(TipologiaRichiestaEnum.SEPARAZIONEDIVORZIO)
        || tipologia.equals(TipologiaRichiestaEnum.TRASCRIZIONESCIGLIOMENTO)) {
      css = ICON_FOGLI;
    } else {
      css = ICON_FOGLI;
    }

    return new AttributeAppender("class", " " + css);
  }
}
