package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiDocumentiTariEng;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.DettagliQuadroTributarioTariPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarieng.model.DettaglioDocumentoEmesso;
import it.liguriadigitale.ponmetro.tarieng.model.QuadroTributario;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRichiedenteRimborso.TipologiaRichiedenteRimborsoEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class QuadroTributarioTariPanel extends BasePanel {

  private static final long serialVersionUID = -2339024555185198439L;

  private static final String ICON_TARI = "color-green col-3 icon-cassonetto";

  protected PaginazioneFascicoloPanel paginationDocumenti;

  protected PaginazioneFascicoloPanel paginationBoxRimborsi;

  public QuadroTributarioTariPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    createFeedBackPanel();
  }

  public QuadroTributarioTariPanel(String id, List<DatiDocumentiTariEng> listaDatiTari) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(listaDatiTari);

    createFeedBackPanel();
  }

  public QuadroTributarioTariPanel(String id, QuadroTributario quadro) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    List<DatiDocumentiTariEng> listaDatiTari = popolaTari(quadro);
    fillDati(listaDatiTari);

    createFeedBackPanel();
  }

  @SuppressWarnings({"unchecked", "unused"})
  @Override
  public void fillDati(Object dati) {
    List<DatiDocumentiTariEng> listaDatiTari = (List<DatiDocumentiTariEng>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkTari(listaDatiTari));
    addOrReplace(listaVuota);

    for (DatiDocumentiTariEng elem : listaDatiTari) {
      elem.setCodiceFiscale(getUtente().getCodiceFiscaleOperatore());
      ServiceLocator.getInstance().getServiziTariEng().setInfoSuEccedenzeEPosizione(elem);
    }

    List<DatiDocumentiTariEng> listaPerRimborsi =
        ServiceLocator.getInstance().getServiziTariEng().getListaDistinctSuIdDeb(listaDatiTari);

    PageableListView<DatiDocumentiTariEng> boxRimborsi = null;

    FdCTitoloPanel titoloBoxRimborsi =
        new FdCTitoloPanel(
            "titoloBoxRimborsi", getString("QuadroTributarioTariPanel.titoloBoxRimborsi"));
    titoloBoxRimborsi.setVisible(checkBoxRimborsiVisibile(listaPerRimborsi));
    addOrReplace(titoloBoxRimborsi);

    FdCTitoloPanel titoloBoxDocumenti =
        new FdCTitoloPanel(
            "titoloBoxDocumenti", getString("QuadroTributarioTariPanel.titoloBoxDocumenti"));
    titoloBoxDocumenti.setVisible(checkTariVisibilitaDocumenti(listaDatiTari));
    addOrReplace(titoloBoxDocumenti);

    boxRimborsi =
        new PageableListView<DatiDocumentiTariEng>("boxRimborsi", listaPerRimborsi, 4) {

          private static final long serialVersionUID = 8796000230787615389L;

          @Override
          protected void populateItem(ListItem<DatiDocumentiTariEng> itemMappaIdDeb) {

            final DatiDocumentiTariEng elementoTari = itemMappaIdDeb.getModelObject();

            String idDebStr = "";
            if (LabelFdCUtil.checkIfNotNull(elementoTari.getIdDeb())) {
              idDebStr = String.valueOf(elementoTari.getIdDeb());
            }
            Label boxRimborsiTitolo =
                new Label(
                    "boxRimborsiTitolo",
                    new StringResourceModel("QuadroTributarioTariPanel.boxRimborsiTitolo", this)
                        .setParameters(idDebStr));
            itemMappaIdDeb.addOrReplace(boxRimborsiTitolo);

            itemMappaIdDeb.addOrReplace(
                new AmtCardLabel<>(
                        "idDebRimborsi", elementoTari.getIdDeb(), QuadroTributarioTariPanel.this)
                    .setVisible(false));

            String tipoUtenza =
                ServiceLocator.getInstance()
                    .getServiziTariEng()
                    .decodicaTipoUtenza(elementoTari.getTipoUtz());
            itemMappaIdDeb.addOrReplace(
                new AmtCardLabel<>(
                    "tipoUtenzaRimborsi", tipoUtenza, QuadroTributarioTariPanel.this));

            itemMappaIdDeb.addOrReplace(
                new AmtCardLabel<>(
                        "totaleEccedenzeRimborsi",
                        elementoTari.getTotaleEccedenze(),
                        QuadroTributarioTariPanel.this)
                    .setVisible(
                        LabelFdCUtil.checkIfNotNull(elementoTari.getTotaleEccedenze())
                            && elementoTari.getTotaleEccedenze().compareTo(Double.valueOf(0)) > 0));

            itemMappaIdDeb.addOrReplace(
                new AmtCardLabel<>("dov", elementoTari.getDov(), QuadroTributarioTariPanel.this));

            itemMappaIdDeb.addOrReplace(
                new AmtCardLabel<>("pag", elementoTari.getPag(), QuadroTributarioTariPanel.this));

            Label messaggioRimborsi =
                new Label("messaggioRimborsi", elementoTari.getMessaggioPerEccedenze());
            messaggioRimborsi.setVisible(
                PageUtil.isStringValid(elementoTari.getMessaggioPerEccedenze()));
            messaggioRimborsi.setOutputMarkupId(true);
            messaggioRimborsi.setOutputMarkupPlaceholderTag(true);
            itemMappaIdDeb.addOrReplace(messaggioRimborsi);

            Label messaggioPosizione =
                new Label("messaggioPosizione", elementoTari.getMessaggioPosizione());
            messaggioPosizione.setVisible(
                PageUtil.isStringValid(elementoTari.getMessaggioPosizione()));
            messaggioPosizione.setOutputMarkupId(true);
            messaggioPosizione.setOutputMarkupPlaceholderTag(true);
            itemMappaIdDeb.addOrReplace(messaggioPosizione);

            DatiRichiestaRimborsoTariEng datiPerRimborsoIntestatario =
                new DatiRichiestaRimborsoTariEng();
            datiPerRimborsoIntestatario.setCodiceBelfiore("D969");
            datiPerRimborsoIntestatario.setNomeRichiedente(getUtente().getNome());
            datiPerRimborsoIntestatario.setCognomeRichiedente(getUtente().getCognome());
            datiPerRimborsoIntestatario.setCodiceFiscaleRichiedente(
                getUtente().getCodiceFiscaleOperatore());
            datiPerRimborsoIntestatario.setEmailRichiedente(getUtente().getMail());

            datiPerRimborsoIntestatario.setNomeBeneficiario(getUtente().getNome());
            datiPerRimborsoIntestatario.setCognomeBeneficiario(getUtente().getCognome());
            datiPerRimborsoIntestatario.setCodiceFiscaleBeneficiario(
                getUtente().getCodiceFiscaleOperatore());

            if (getUtente().isResidente()) {

              if (LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())) {
                if (LabelFdCUtil.checkIfNotNull(
                    getUtente().getDatiCittadinoResidente().getCpvHasAddress())) {
                  if (PageUtil.isStringValid(
                      getUtente()
                          .getDatiCittadinoResidente()
                          .getCpvHasAddress()
                          .getClvFullAddress())) {
                    datiPerRimborsoIntestatario.setIndirizzoBeneficiario(
                        getUtente()
                            .getDatiCittadinoResidente()
                            .getCpvHasAddress()
                            .getClvFullAddress());
                  }
                  if (PageUtil.isStringValid(
                      getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity())) {
                    datiPerRimborsoIntestatario.setComuneBeneficiario(
                        getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
                  }
                  if (PageUtil.isStringValid(
                      getUtente()
                          .getDatiCittadinoResidente()
                          .getCpvHasAddress()
                          .getClvPostCode())) {
                    datiPerRimborsoIntestatario.setCapBeneficiario(
                        getUtente()
                            .getDatiCittadinoResidente()
                            .getCpvHasAddress()
                            .getClvPostCode());
                  }
                }
              }
            }

            datiPerRimborsoIntestatario.setTipologiaRichiedente(
                TipologiaRichiedenteRimborsoEnum.INTESTATARIO);

            datiPerRimborsoIntestatario.setEccTari(elementoTari.getEccTari());

            datiPerRimborsoIntestatario.setEccTefa(elementoTari.getEccTefa());

            datiPerRimborsoIntestatario.setEccTariR(elementoTari.getEccTariR());

            datiPerRimborsoIntestatario.setSommaEccedenze(elementoTari.getTotaleEccedenze());

            datiPerRimborsoIntestatario.setIdDeb(elementoTari.getIdDeb());

            datiPerRimborsoIntestatario.setEccTariRichiedibile(
                elementoTari.isEccTariRichiedibile());
            datiPerRimborsoIntestatario.setEccTefaRichiedibile(
                elementoTari.isEccTefaRichiedibile());
            datiPerRimborsoIntestatario.setEccTariRealeRichiedibile(
                elementoTari.isEccTariRealeRichiedibile());

            if (elementoTari.isEccTariRichiedibile()) {
              datiPerRimborsoIntestatario.setInfoEccTari(
                  getString("QuadroTributarioTariPanel.eccedenzaRichiedibile"));
            } else {
              datiPerRimborsoIntestatario.setInfoEccTari(
                  getString("QuadroTributarioTariPanel.eccedenzaNonRichiedibile"));
            }

            if (elementoTari.isEccTariRealeRichiedibile()) {
              datiPerRimborsoIntestatario.setInfoEccTariR(
                  getString("QuadroTributarioTariPanel.eccedenzaRichiedibile"));
            } else {
              datiPerRimborsoIntestatario.setInfoEccTariR(
                  getString("QuadroTributarioTariPanel.eccedenzaNonRichiedibile"));
            }

            if (elementoTari.isEccTefaRichiedibile()) {
              datiPerRimborsoIntestatario.setInfoEccTefa(
                  getString("QuadroTributarioTariPanel.eccedenzaRichiedibile"));
            } else {
              datiPerRimborsoIntestatario.setInfoEccTefa(
                  getString("QuadroTributarioTariPanel.eccedenzaNonRichiedibile"));
            }

            datiPerRimborsoIntestatario.setDatiDocumentiTariEng(elementoTari);

            LinkDinamicoLaddaFunzione<DatiRichiestaRimborsoTariEng> btnRimborso =
                new LinkDinamicoLaddaFunzione<>(
                    "btnRimborso",
                    LinkDinamicoFunzioneDataBuilder.getInstance()
                        .setWicketLabelKeyText("QuadroTributarioTariPanel.rimborso")
                        .setWicketClassName("RichiestaRimborsoTariEngPage")
                        .build(),
                    datiPerRimborsoIntestatario,
                    QuadroTributarioTariPanel.this,
                    "color-green col-auto icon-cassonetto",
                    elementoTari.isRimborsoRichiedibile());

            itemMappaIdDeb.addOrReplace(btnRimborso);

            itemMappaIdDeb.setVisible(elementoTari.isCardRimborsiPosizioneVisibile());
          }
        };

    boxRimborsi.setOutputMarkupId(true);
    boxRimborsi.setOutputMarkupPlaceholderTag(true);
    addOrReplace(boxRimborsi);

    if (boxRimborsi == null) {
      WebMarkupContainer boxRimborsi2 = new WebMarkupContainer("boxRimborsi");
      boxRimborsi2.setVisible(false);
      addOrReplace(boxRimborsi2);
    } else {
      addOrReplace(boxRimborsi);
    }

    PageableListView<DatiDocumentiTariEng> listViewDocumenti =
        new PageableListView<DatiDocumentiTariEng>("documenti", listaDatiTari, 4) {

          private static final long serialVersionUID = -461827469230468427L;

          @Override
          protected void populateItem(ListItem<DatiDocumentiTariEng> itemDocumento) {
            final DatiDocumentiTariEng documento = itemDocumento.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaTari(documento.getEsito()));
            itemDocumento.addOrReplace(icona);

            String tipoUtenza =
                ServiceLocator.getInstance()
                    .getServiziTariEng()
                    .decodicaTipoUtenza(documento.getTipoUtz());

            NotEmptyLabel tipoUtz = new NotEmptyLabel("tipoUtz", tipoUtenza);
            tipoUtz.setVisible(PageUtil.isStringValid(tipoUtenza));
            itemDocumento.addOrReplace(tipoUtz);

            NotEmptyLabel tipoDoc = new NotEmptyLabel("tipoDoc", documento.getTipoDoc());
            itemDocumento.addOrReplace(tipoDoc);

            itemDocumento.addOrReplace(
                new AmtCardLabel<>("esito", documento.getEsito(), QuadroTributarioTariPanel.this)
                    .setVisible(false));

            itemDocumento.addOrReplace(
                new AmtCardLabel<>("idDeb", documento.getIdDeb(), QuadroTributarioTariPanel.this));

            itemDocumento.addOrReplace(
                new AmtCardLabel<>("desc", documento.getDesc(), QuadroTributarioTariPanel.this)
                    .setVisible(false));

            itemDocumento.addOrReplace(
                new AmtCardLabel<>("imp", documento.getImporto(), QuadroTributarioTariPanel.this));

            itemDocumento.addOrReplace(
                new AmtCardLabel<>(
                        "incassato", documento.getIncassato(), QuadroTributarioTariPanel.this)
                    .setVisible(
                        PageUtil.isStringValid(documento.getFlagMigrazioneEsperta())
                            && documento.getFlagMigrazioneEsperta().equalsIgnoreCase("N")));

            String ruolo = "";
            if (LabelFdCUtil.checkIfNotNull(documento)
                && PageUtil.isStringValid(documento.getRuolo())) {
              if (documento.getRuolo().equalsIgnoreCase("N")) {
                ruolo = "No";
              } else if (documento.getRuolo().equalsIgnoreCase("S")) {
                ruolo = "Sì";
              }
            }
            itemDocumento.addOrReplace(
                new AmtCardLabel<>("ruolo", ruolo, QuadroTributarioTariPanel.this)
                    .setVisible(
                        PageUtil.isStringValid(documento.getRuolo())
                            && documento.getRuolo().equalsIgnoreCase("S")));

            itemDocumento.addOrReplace(
                new AmtCardLabel<>(
                    "numDoc", documento.getNumDoc(), QuadroTributarioTariPanel.this));

            Double eccTariRealeTefa = 0.0;
            if (LabelFdCUtil.checkIfNotNull(documento.getEccTari())) {
              eccTariRealeTefa = Double.sum(eccTariRealeTefa, documento.getEccTari());
            }
            if (LabelFdCUtil.checkIfNotNull(documento.getEccTariR())) {
              eccTariRealeTefa = Double.sum(eccTariRealeTefa, documento.getEccTariR());
            }
            if (LabelFdCUtil.checkIfNotNull(documento.getEccTefa())) {
              eccTariRealeTefa = Double.sum(eccTariRealeTefa, documento.getEccTefa());
            }

            itemDocumento.addOrReplace(
                new AmtCardLabel<>("eccTari", eccTariRealeTefa, QuadroTributarioTariPanel.this)
                    .setVisible(false));

            itemDocumento.addOrReplace(
                new AmtCardLabel<>(
                    "annoEmissione", documento.getAnnoEmissione(), QuadroTributarioTariPanel.this));

            Label messaggioDocumento =
                new Label("messaggioDocumento", documento.getMessaggioDocumento());
            messaggioDocumento.setVisible(
                PageUtil.isStringValid(documento.getMessaggioDocumento()));
            messaggioDocumento.setOutputMarkupId(true);
            messaggioDocumento.setOutputMarkupPlaceholderTag(true);
            itemDocumento.addOrReplace(messaggioDocumento);

            itemDocumento.addOrReplace(creaBtnDettagli(documento));

            itemDocumento.setVisible(documento.isListaDocEmessiPiena());
          }
        };

    listViewDocumenti.setVisible(checkTari(listaDatiTari));

    addOrReplace(listViewDocumenti);

    paginationDocumenti = new PaginazioneFascicoloPanel("paginationDocumenti", listViewDocumenti);
    paginationDocumenti.setVisible(checkTari(listaDatiTari) && listaDatiTari.size() > 4);
    addOrReplace(paginationDocumenti);

    paginationBoxRimborsi = new PaginazioneFascicoloPanel("paginationBoxRimborsi", boxRimborsi);
    paginationBoxRimborsi.setVisible(checkBoxRimborsiVisibile(listaPerRimborsi));

    addOrReplace(paginationBoxRimborsi);
  }

  private boolean checkTari(List<DatiDocumentiTariEng> listaDatiTari) {
    return LabelFdCUtil.checkIfNotNull(listaDatiTari)
        && !LabelFdCUtil.checkEmptyList(listaDatiTari);
  }

  private boolean checkTariVisibilitaDocumenti(List<DatiDocumentiTariEng> listaDatiTari) {
    return checkTari(listaDatiTari)
        && listaDatiTari.stream()
            .filter(elem -> elem.isListaDocEmessiPiena())
            .findAny()
            .isPresent();
  }

  private boolean checkBoxRimborsiVisibile(List<DatiDocumentiTariEng> listaDatiTari) {
    boolean titoloVisibile = false;

    if (LabelFdCUtil.checkIfNotNull(listaDatiTari) && !LabelFdCUtil.checkEmptyList(listaDatiTari)) {
      Optional<DatiDocumentiTariEng> filtroCardVisibile =
          listaDatiTari.stream().filter(elem -> elem.isCardRimborsiPosizioneVisibile()).findAny();

      titoloVisibile = filtroCardVisibile.isPresent();
    }

    return titoloVisibile;
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnDettagli(DatiDocumentiTariEng documento) {
    FdCButtonBootstrapAjaxLink<Object> btnDettagli =
        new FdCButtonBootstrapAjaxLink<Object>("btnDettagli", Type.Primary) {

          private static final long serialVersionUID = 7762079603474644008L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(QuadroTributarioTariPanel.this);

            try {

              DettaglioDocumentoEmesso dettaglio =
                  ServiceLocator.getInstance()
                      .getServiziTariEng()
                      .getDettagliDocumentoTARI(documento.getUriDocumento());

              documento.setDettagliDocumento(dettaglio);

            } catch (BusinessException | ApiException e) {
              log.error("Errore durante get dettagli TARI Eng : " + e.getMessage(), e);
            }

            setResponsePage(new DettagliQuadroTributarioTariPage(documento));
          }
        };

    btnDettagli.setLabel(Model.of(getString("QuadroTributarioTariPanel.dettagli")));

    return btnDettagli;
  }

  private AttributeAppender getCssIconaTari(String esito) {
    String css = "";

    css = ICON_TARI;

    return new AttributeAppender("class", " " + css);
  }

  private List<DatiDocumentiTariEng> popolaTari(QuadroTributario quadro) {
    List<DatiDocumentiTariEng> res = new ArrayList<DatiDocumentiTariEng>();
    try {
      res = ServiceLocator.getInstance().getServiziTariEng().getDatiTariCompletiDellAnno(quadro);
    } catch (BusinessException | ApiException e) {
      log.error("Errore popolaTari " + e.getMessage(), e);
    }
    return res;
  }
}
