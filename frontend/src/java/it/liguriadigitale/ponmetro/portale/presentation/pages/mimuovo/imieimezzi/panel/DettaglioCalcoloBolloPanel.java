package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.imieimezzi.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tassaauto.model.DettaglioCalcoloBollo;
import org.apache.wicket.markup.html.basic.Label;

public class DettaglioCalcoloBolloPanel extends BasePanel {

  private static final long serialVersionUID = 2651742802165247330L;

  public DettaglioCalcoloBolloPanel(String id, DettaglioCalcoloBollo dettaglioCalcoloBollo) {
    super(id);
    fillDati(dettaglioCalcoloBollo);
  }

  @Override
  public void fillDati(Object dati) {

    DettaglioCalcoloBollo dettaglioCalcoloBollo = (DettaglioCalcoloBollo) dati;

    Label tipoRiduzione =
        new Label("tipoRiduzione", dettaglioCalcoloBollo.getTipoRiduzione().getDescrizione());
    tipoRiduzione.setVisible(
        PageUtil.isStringValid(dettaglioCalcoloBollo.getTipoRiduzione().getDescrizione()));
    addOrReplace(tipoRiduzione);

    Label percentualeRiduzione =
        new Label("percentualeRiduzione", dettaglioCalcoloBollo.getPercentualeRiduzione());
    percentualeRiduzione.setVisible(
        PageUtil.isStringValid(dettaglioCalcoloBollo.getPercentualeRiduzione()));
    addOrReplace(percentualeRiduzione);

    Label importoTassa =
        new Label(
            "importoTassa",
            dettaglioCalcoloBollo
                .getImporto()
                .getTassa()
                .getImporto()
                .concat(" ")
                .concat(
                    dettaglioCalcoloBollo.getImporto().getTassa().getDivisa().getDescrizione()));
    importoTassa.setVisible(
        PageUtil.isStringValid(dettaglioCalcoloBollo.getImporto().getTassa().getImporto())
            && PageUtil.isStringValid(
                dettaglioCalcoloBollo.getImporto().getTassa().getDivisa().getDescrizione()));

    addOrReplace(importoTassa);

    Label importoSanzioni =
        new Label(
            "importoSanzioni",
            dettaglioCalcoloBollo
                .getImporto()
                .getSanzioni()
                .getImporto()
                .concat(" ")
                .concat(
                    dettaglioCalcoloBollo.getImporto().getSanzioni().getDivisa().getDescrizione()));
    importoSanzioni.setVisible(
        PageUtil.isStringValid(dettaglioCalcoloBollo.getImporto().getSanzioni().getImporto())
            && PageUtil.isStringValid(
                dettaglioCalcoloBollo.getImporto().getSanzioni().getDivisa().getDescrizione()));
    addOrReplace(importoSanzioni);

    Label importoInteressi =
        new Label(
            "importoInteressi",
            dettaglioCalcoloBollo
                .getImporto()
                .getInteressi()
                .getImporto()
                .concat(" ")
                .concat(
                    dettaglioCalcoloBollo
                        .getImporto()
                        .getInteressi()
                        .getDivisa()
                        .getDescrizione()));
    importoInteressi.setVisible(
        PageUtil.isStringValid(dettaglioCalcoloBollo.getImporto().getInteressi().getImporto())
            && PageUtil.isStringValid(
                dettaglioCalcoloBollo.getImporto().getInteressi().getDivisa().getDescrizione()));
    addOrReplace(importoInteressi);

    Label importoTotale =
        new Label(
            "importoTotale",
            dettaglioCalcoloBollo
                .getImporto()
                .getTotale()
                .getImporto()
                .concat(" ")
                .concat(
                    dettaglioCalcoloBollo.getImporto().getTotale().getDivisa().getDescrizione()));
    importoTotale.setVisible(
        PageUtil.isStringValid(dettaglioCalcoloBollo.getImporto().getTotale().getImporto())
            && PageUtil.isStringValid(
                dettaglioCalcoloBollo.getImporto().getTotale().getDivisa().getDescrizione()));
    addOrReplace(importoTotale);

    Label esplicativo = new Label("esplicativo", dettaglioCalcoloBollo.getEsplicativo());
    esplicativo.setVisible(PageUtil.isStringValid(dettaglioCalcoloBollo.getEsplicativo()));
    addOrReplace(esplicativo);

    Label ultimoGiornoUtile =
        new Label("ultimoGiornoUtile", dettaglioCalcoloBollo.getDataUltimoGiornoPagamento());
    ultimoGiornoUtile.setVisible(
        PageUtil.isStringValid(dettaglioCalcoloBollo.getDataUltimoGiornoPagamento()));
    addOrReplace(ultimoGiornoUtile);

    Label ultimoGiornoUtileProrogato =
        new Label(
            "ultimoGiornoUtileProrogato",
            dettaglioCalcoloBollo.getDataUltimoGiornoPagamentoProrogato());
    ultimoGiornoUtileProrogato.setVisible(
        PageUtil.isStringValid(dettaglioCalcoloBollo.getDataUltimoGiornoPagamentoProrogato()));
    addOrReplace(ultimoGiornoUtileProrogato);
  }
}
