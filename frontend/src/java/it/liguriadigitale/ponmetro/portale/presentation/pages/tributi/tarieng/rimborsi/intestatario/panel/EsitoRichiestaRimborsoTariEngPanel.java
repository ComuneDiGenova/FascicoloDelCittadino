package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.intestatario.panel;

import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipologiaRichiedenteRimborsoEnum;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;

public class EsitoRichiestaRimborsoTariEngPanel extends BasePanel {

  private static final long serialVersionUID = 8457924058419621922L;

  private int index;

  DatiRichiestaRimborsoTariEng rimborso;

  private WebMarkupContainer erroreTari = new WebMarkupContainer("erroreTari");

  private WebMarkupContainer erroreTefa = new WebMarkupContainer("erroreTefa");

  private WebMarkupContainer erroreTariReale = new WebMarkupContainer("erroreTariReale");

  private WebMarkupContainer okTari = new WebMarkupContainer("okTari");

  private WebMarkupContainer okTefa = new WebMarkupContainer("okTefa");

  private WebMarkupContainer okTariReale = new WebMarkupContainer("okTariReale");

  public EsitoRichiestaRimborsoTariEngPanel(
      String id, DatiRichiestaRimborsoTariEng rimborso, int index) {
    super(id);
    this.rimborso = rimborso;
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(rimborso);
    this.setIndex(index);
  }

  @SuppressWarnings("unused")
  @Override
  public void fillDati(Object dati) {
    DatiRichiestaRimborsoTariEng rimborso = (DatiRichiestaRimborsoTariEng) dati;

    erroreTari.setOutputMarkupId(true);
    erroreTari.setOutputMarkupPlaceholderTag(true);

    erroreTari.setVisible(false);
    addOrReplace(erroreTari);

    erroreTefa.setOutputMarkupId(true);
    erroreTefa.setOutputMarkupPlaceholderTag(true);

    erroreTefa.setVisible(false);
    addOrReplace(erroreTefa);

    erroreTariReale.setOutputMarkupId(true);
    erroreTariReale.setOutputMarkupPlaceholderTag(true);

    erroreTariReale.setVisible(false);
    addOrReplace(erroreTariReale);

    okTari.setOutputMarkupId(true);
    okTari.setOutputMarkupPlaceholderTag(true);

    okTari.setVisible(false);
    addOrReplace(okTari);

    okTefa.setOutputMarkupId(true);
    okTefa.setOutputMarkupPlaceholderTag(true);

    okTefa.setVisible(false);
    addOrReplace(okTefa);

    okTariReale.setOutputMarkupId(true);
    okTariReale.setOutputMarkupPlaceholderTag(true);

    okTariReale.setVisible(false);
    addOrReplace(okTariReale);
  }

  public void esitoRimborso(DatiRichiestaRimborsoTariEng rimborso) {

    Label messaggioErroreTari = new Label("messaggioErroreTari");
    Label messaggioErroreTefa = new Label("messaggioErroreTefa");
    Label messaggioErroreTariReale = new Label("messaggioErroreTariReale");

    Label messaggioSuccessoTari = new Label("messaggioSuccessoTari");
    Label messaggioSuccessoTefa = new Label("messaggioSuccessoTefa");
    Label messaggioSuccessoTariReale = new Label("messaggioSuccessoTariReale");

    if (LabelFdCUtil.checkIfNotNull(rimborso)) {

      if (LabelFdCUtil.checkIfNotNull(rimborso.getResponsePostRimborsoTARI())) {

        log.debug("CP getResponsePostRimborsoTARI");

        if (PageUtil.isStringValid(rimborso.getResponsePostRimborsoTARI().getStato())
            && rimborso.getResponsePostRimborsoTARI().getStato().equalsIgnoreCase("OK")) {

          // String messaggioEngSenzaPrefisso = "";
          //
          // String messaggioEng = rimborso.getResponsePostRimborsoTARI().getMessaggio();
          // if (PageUtil.isStringValid(messaggioEng)) {
          // messaggioEngSenzaPrefisso = messaggioEng.replace("POST IstanzaRimborso", "");
          // }
          //
          // messaggioSuccessoTari = new Label("messaggioSuccessoTari",
          // new
          // StringResourceModel("EsitoRichiestaRimborsoTariEngPanel.messaggioSuccessoTari",
          // this)
          // .setParameters(messaggioEngSenzaPrefisso));

          messaggioSuccessoTari =
              new Label(
                  "messaggioSuccessoTari",
                  new StringResourceModel(
                          "EsitoRichiestaRimborsoTariEngPanel.messaggioSuccessoTari", this)
                      .setParameters(
                          getString("EsitoRichiestaRimborsoTariEngPanel.eseguitaCorrettamente")));

          okTari.setVisible(true);

          erroreTari.setVisible(false);

        } else {

          String messaggioEngSenzaPrefisso = "";

          String messaggioEng = rimborso.getResponsePostRimborsoTARI().getMessaggioErrore();
          if (PageUtil.isStringValid(messaggioEng)) {
            messaggioEngSenzaPrefisso = messaggioEng.replace("POST IstanzaRimborso", "");
          }

          messaggioErroreTari =
              new Label(
                  "messaggioErroreTari",
                  new StringResourceModel(
                          "EsitoRichiestaRimborsoTariEngPanel.messaggioErroreTari", this)
                      .setParameters(messaggioEngSenzaPrefisso));

          erroreTari.setVisible(true);

          okTari.setVisible(false);
        }
      }

      if (LabelFdCUtil.checkIfNotNull(rimborso.getResponsePostRimborsoTEFA())) {

        log.debug("CP getResponsePostRimborsoTEFA");

        if (PageUtil.isStringValid(rimborso.getResponsePostRimborsoTEFA().getStato())
            && rimborso.getResponsePostRimborsoTEFA().getStato().equalsIgnoreCase("OK")) {

          // String messaggioEngSenzaPrefisso = "";
          //
          // String messaggioEng = rimborso.getResponsePostRimborsoTEFA().getMessaggio();
          // if (PageUtil.isStringValid(messaggioEng)) {
          // messaggioEngSenzaPrefisso = messaggioEng.replace("POST IstanzaRimborso", "");
          // }
          //
          // messaggioSuccessoTefa = new Label("messaggioSuccessoTefa",
          // new
          // StringResourceModel("EsitoRichiestaRimborsoTariEngPanel.messaggioSuccessoTefa",
          // this)
          // .setParameters(messaggioEngSenzaPrefisso));

          messaggioSuccessoTefa =
              new Label(
                  "messaggioSuccessoTefa",
                  new StringResourceModel(
                          "EsitoRichiestaRimborsoTariEngPanel.messaggioSuccessoTefa", this)
                      .setParameters(
                          getString("EsitoRichiestaRimborsoTariEngPanel.eseguitaCorrettamente")));

          okTefa.setVisible(true);

          erroreTefa.setVisible(false);

        } else {

          String messaggioEngSenzaPrefisso = "";

          String messaggioEng = rimborso.getResponsePostRimborsoTEFA().getMessaggioErrore();
          if (PageUtil.isStringValid(messaggioEng)) {
            messaggioEngSenzaPrefisso = messaggioEng.replace("POST IstanzaRimborso", "");
          }

          messaggioErroreTefa =
              new Label(
                  "messaggioErroreTefa",
                  new StringResourceModel(
                          "EsitoRichiestaRimborsoTariEngPanel.messaggioErroreTefa", this)
                      .setParameters(messaggioEngSenzaPrefisso));

          erroreTefa.setVisible(true);

          okTefa.setVisible(false);
        }
      }

      if (LabelFdCUtil.checkIfNotNull(rimborso.getResponsePostRimborsoTARIREALE())) {

        log.debug("CP getResponsePostRimborsoTARIREALE");

        if (PageUtil.isStringValid(rimborso.getResponsePostRimborsoTARIREALE().getStato())
            && rimborso.getResponsePostRimborsoTARIREALE().getStato().equalsIgnoreCase("OK")) {

          // String messaggioEngSenzaPrefisso = "";
          //
          // String messaggioEng =
          // rimborso.getResponsePostRimborsoTARIREALE().getMessaggio();
          // if (PageUtil.isStringValid(messaggioEng)) {
          // messaggioEngSenzaPrefisso = messaggioEng.replace("POST IstanzaRimborso", "");
          // }
          //
          // messaggioSuccessoTariReale = new Label("messaggioSuccessoTariReale",
          // new
          // StringResourceModel("EsitoRichiestaRimborsoTariEngPanel.messaggioSuccessoTariReale",
          // this).setParameters(messaggioEngSenzaPrefisso));

          messaggioSuccessoTariReale =
              new Label(
                  "messaggioSuccessoTariReale",
                  new StringResourceModel(
                          "EsitoRichiestaRimborsoTariEngPanel.messaggioSuccessoTariReale", this)
                      .setParameters(
                          getString("EsitoRichiestaRimborsoTariEngPanel.eseguitaCorrettamente")));

          okTariReale.setVisible(true);

          erroreTariReale.setVisible(false);

        } else {

          String messaggioEngSenzaPrefisso = "";

          String messaggioEng = rimborso.getResponsePostRimborsoTARIREALE().getMessaggioErrore();
          if (PageUtil.isStringValid(messaggioEng)) {
            messaggioEngSenzaPrefisso = messaggioEng.replace("POST IstanzaRimborso", "");
          }

          messaggioErroreTariReale =
              new Label(
                  "messaggioErroreTariReale",
                  new StringResourceModel(
                          "EsitoRichiestaRimborsoTariEngPanel.messaggioErroreTariReale", this)
                      .setParameters(messaggioEngSenzaPrefisso));

          erroreTariReale.setVisible(true);

          okTariReale.setVisible(false);
        }
      }

      erroreTari.addOrReplace(messaggioErroreTari);

      erroreTefa.addOrReplace(messaggioErroreTefa);

      erroreTariReale.addOrReplace(messaggioErroreTariReale);

      addOrReplace(erroreTari);

      addOrReplace(erroreTefa);

      addOrReplace(erroreTariReale);

      okTari.addOrReplace(messaggioSuccessoTari);

      okTefa.addOrReplace(messaggioSuccessoTefa);

      okTariReale.addOrReplace(messaggioSuccessoTariReale);

      addOrReplace(okTari);

      addOrReplace(okTefa);

      addOrReplace(okTariReale);

      log.debug("CP rimborso tipologia richiedente = " + rimborso.getTipologiaRichiedente());

      if (LabelFdCUtil.checkIfNotNull(rimborso.getTipologiaRichiedente())
          && !rimborso
              .getTipologiaRichiedente()
              .value()
              .equalsIgnoreCase(TipologiaRichiedenteRimborsoEnum.INTESTATARIO.value())) {

        log.debug("CP tipologia diversa da intestatario");

        if ((LabelFdCUtil.checkIfNotNull(rimborso.getResponsePostRimborsoTARI())
                && PageUtil.isStringValid(rimborso.getResponsePostRimborsoTARI().getStato())
                && rimborso.getResponsePostRimborsoTARI().getStato().equalsIgnoreCase("OK"))
            || (LabelFdCUtil.checkIfNotNull(rimborso.getResponsePostRimborsoTEFA())
                && PageUtil.isStringValid(rimborso.getResponsePostRimborsoTEFA().getStato())
                && rimborso.getResponsePostRimborsoTEFA().getStato().equalsIgnoreCase("OK"))
            || (LabelFdCUtil.checkIfNotNull(rimborso.getResponsePostRimborsoTARIREALE())
                && PageUtil.isStringValid(rimborso.getResponsePostRimborsoTARIREALE().getStato())
                && rimborso.getResponsePostRimborsoTARIREALE().getStato().equalsIgnoreCase("OK"))) {

          log.debug("CP almeno una POST  andata OK");

          rimborso.setTipoRimborso(null);

          rimborso.setEnteBeneficiarioTari(null);
          rimborso.setEnteBeneficiarioTefa(null);

          rimborso.setEccTari(null);
          rimborso.setEccTefa(null);
          rimborso.setEccTariR(null);

          rimborso.setResponsePostRimborsoTARI(null);
          rimborso.setResponsePostRimborsoTEFA(null);
          rimborso.setResponsePostRimborsoTARIREALE(null);

          rimborso.setTipoEccedenzaRimborso(null);

          rimborso.setEccTariRichiedibile(false);
          rimborso.setEccTefaRichiedibile(false);
          rimborso.setEccTariRealeRichiedibile(false);

          log.debug("CP dati rimborso ora = " + rimborso);

          LinkDinamicoLaddaFunzione<DatiRichiestaRimborsoTariEng> btnNuovoRimborso =
              new LinkDinamicoLaddaFunzione<>(
                  "btnNuovoRimborso",
                  LinkDinamicoFunzioneDataBuilder.getInstance()
                      .setWicketLabelKeyText("EsitoRichiestaRimborsoTariEngPanel.btnNuovoRimborso")
                      .setWicketClassName("RichiestaRimborsoTariEngEredePage")
                      .build(),
                  rimborso,
                  EsitoRichiestaRimborsoTariEngPanel.this,
                  "color-green col-auto icon-cassonetto");

          addOrReplace(btnNuovoRimborso);
        } else {
          log.debug("CP nessuna POST OK");

          WebMarkupContainer btnNuovoRimborso = new WebMarkupContainer("btnNuovoRimborso");
          btnNuovoRimborso.setVisible(false);
          addOrReplace(btnNuovoRimborso);
        }

      } else {
        log.debug("CP tipologia uguale a intestatario");

        WebMarkupContainer btnNuovoRimborso = new WebMarkupContainer("btnNuovoRimborso");
        btnNuovoRimborso.setVisible(false);
        addOrReplace(btnNuovoRimborso);
      }
    }
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
