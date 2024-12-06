package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiFileAllegatoNetribe;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarinetribe.rimborsi.RimborsiTariNetribePage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import it.liguriadigitale.ponmetro.tarinetribe.model.Saldi;
import it.liguriadigitale.ponmetro.tarinetribe.model.TARIRimborsi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;

public class RimborsiTariNetribePanel extends BasePanel {

  private static final long serialVersionUID = 6933013952898640685L;

  protected PaginazioneFascicoloPanel pagination;

  public RimborsiTariNetribePanel(String panelId, List<TARIRimborsi> listaRimborsi) {
    super(panelId);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(listaRimborsi);

    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(RimborsiTariNetribePage.class).build();
    addOrReplace(boxMessaggi);

    List<TARIRimborsi> listaRimborsi = (List<TARIRimborsi>) dati;

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkRimborsi(listaRimborsi));
    addOrReplace(listaVuota);

    LinkDinamicoLaddaFunzione<Object> btnRimborsoTariErede =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnRimborsoTariErede",
            new LinkDinamicoFunzioneData(
                "RimborsiTariNetribePanel.btnRimborsoTariErede",
                "RichiestaRimborsoTariEredeNetribePage",
                "RimborsiTariNetribePanel.btnRimborsoTariErede"),
            null,
            RimborsiTariNetribePanel.this,
            "color-green col-auto icon-cassonetto");
    addOrReplace(btnRimborsoTariErede);

    PageableListView<TARIRimborsi> listViewRimborsi =
        new PageableListView<TARIRimborsi>("rimborsi", listaRimborsi, 4) {

          private static final long serialVersionUID = -8549299819975515211L;

          @Override
          protected void populateItem(ListItem<TARIRimborsi> itemRimborso) {
            final TARIRimborsi rimborso = itemRimborso.getModelObject();

            NotEmptyLabel id =
                new NotEmptyLabel("id", rimborso.getDatiRichiedenteRimborso().getId());
            id.setVisible(LabelFdCUtil.checkIfNotNull(rimborso.getDatiRichiedenteRimborso()));
            itemRimborso.addOrReplace(id);

            NotEmptyLabel numeroProtocollo =
                new NotEmptyLabel(
                    "numeroProtocollo",
                    rimborso.getDatiRichiedenteRimborso().getNumeroProtocollo());
            numeroProtocollo.setVisible(
                LabelFdCUtil.checkIfNotNull(rimborso.getDatiRichiedenteRimborso()));
            itemRimborso.addOrReplace(numeroProtocollo);

            NotEmptyLabel dataProtocollazione =
                new NotEmptyLabel(
                    "dataProtocollazione",
                    rimborso.getDatiRichiedenteRimborso().getDataProtocollazione());
            dataProtocollazione.setVisible(
                LabelFdCUtil.checkIfNotNull(rimborso.getDatiRichiedenteRimborso()));
            itemRimborso.addOrReplace(dataProtocollazione);

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "stato",
                    rimborso.getDatiRichiedenteRimborso().getStato(),
                    RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "tipologiaRichiedenteRimborso",
                    rimborso.getDatiRichiedenteRimborso().getTipologiaRichiedenteRimborso(),
                    RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "idAvviso",
                    rimborso.getDatiRichiedenteRimborso().getIdAvviso(),
                    RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "cfPIva",
                    rimborso.getDatiRichiedenteRimborso().getCfPIva(),
                    RimborsiTariNetribePanel.this));

            String nominativoRichiedente =
                rimborso
                    .getDatiRichiedenteRimborso()
                    .getNome()
                    .concat(" ")
                    .concat(rimborso.getDatiRichiedenteRimborso().getCognome());
            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "nominativoRichiedente", nominativoRichiedente, RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "emailDiContatto",
                    rimborso.getDatiRichiedenteRimborso().getEmailDiContatto(),
                    RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "dataIstanza",
                    rimborso.getDatiRichiedenteRimborso().getDataIstanza(),
                    RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "dataValidazione",
                    rimborso.getDatiRichiedenteRimborso().getDataValidazione(),
                    RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "dataAnnullamento",
                    rimborso.getDatiRichiedenteRimborso().getDataAnnullamento(),
                    RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "nominativoDelegato",
                    rimborso.getDatiIstanza().getNominativoDelegato(),
                    RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "codiceFiscaleDelegato",
                    rimborso.getDatiIstanza().getCodiceFiscaleDelegato(),
                    RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "nominativoErede",
                    rimborso.getDatiIstanza().getNominativoErede(),
                    RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "codiceFiscaleErede",
                    rimborso.getDatiIstanza().getCodiceFiscaleErede(),
                    RimborsiTariNetribePanel.this));

            String indirizzoCompletoErede = "";
            if (PageUtil.isStringValid(rimborso.getDatiIstanza().getIndirizzoErede())) {
              indirizzoCompletoErede = rimborso.getDatiIstanza().getIndirizzoErede();
            }
            if (LabelFdCUtil.checkIfNotNull(rimborso.getDatiIstanza().getCapErede())) {
              indirizzoCompletoErede =
                  indirizzoCompletoErede
                      .concat(" ")
                      .concat(String.valueOf(rimborso.getDatiIstanza().getCapErede()));
            }
            if (PageUtil.isStringValid(rimborso.getDatiIstanza().getComuneErede())) {
              indirizzoCompletoErede =
                  indirizzoCompletoErede
                      .concat(" ")
                      .concat(rimborso.getDatiIstanza().getComuneErede());
            }
            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "indirizzoCompletoErede",
                    indirizzoCompletoErede,
                    RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "iban", rimborso.getDatiIstanza().getIban(), RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "swift", rimborso.getDatiIstanza().getSwift(), RimborsiTariNetribePanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "note",
                    rimborso.getDatiRichiedenteRimborso().getNote(),
                    RimborsiTariNetribePanel.this));

            String modalitaPagamento = "";
            if (LabelFdCUtil.checkIfNotNull(rimborso.getDatiIstanza().getModalitaPagamento())) {
              if (rimborso
                  .getDatiIstanza()
                  .getModalitaPagamento()
                  .equalsIgnoreCase(ModalitaPagamentoEnum.CAB.name())) {
                modalitaPagamento = "ACCREDITO SU CONTO CORRENTE";
              } else if (rimborso
                  .getDatiIstanza()
                  .getModalitaPagamento()
                  .equalsIgnoreCase(ModalitaPagamentoEnum.CAS.name())) {
                modalitaPagamento = "RITIRO PRESSO TESORERIA";
              }
            }
            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "modalitaPagamento", modalitaPagamento, RimborsiTariNetribePanel.this));

            ListView<Saldi> listViewSaldi =
                new ListView<Saldi>("saldi", rimborso.getDatiRichiedenteRimborso().getSaldi()) {

                  @Override
                  protected void populateItem(ListItem<Saldi> itemSaldo) {
                    final Saldi saldo = itemSaldo.getModelObject();

                    itemSaldo.addOrReplace(
                        new AmtCardLabel<>(
                            "saldoTributo", saldo.getTributo(), RimborsiTariNetribePanel.this));

                    itemSaldo.addOrReplace(
                        new AmtCardLabel<>(
                            "saldoImporto", saldo.getImporto(), RimborsiTariNetribePanel.this));
                  }
                };
            listViewSaldi.setVisible(
                !LabelFdCUtil.checkEmptyList(rimborso.getDatiRichiedenteRimborso().getSaldi()));
            itemRimborso.addOrReplace(listViewSaldi);

            List<FileAllegato> listaAllegati = new ArrayList<>();
            if (LabelFdCUtil.checkIfNotNull(rimborso.getDatiIstanza().getDocumentiAllegati())) {
              listaAllegati = rimborso.getDatiIstanza().getDocumentiAllegati();
            }

            ListView<FileAllegato> listViewDocumenti =
                new ListView<FileAllegato>("documenti", listaAllegati) {

                  private static final long serialVersionUID = -3767783174293582562L;

                  @Override
                  protected void populateItem(ListItem<FileAllegato> itemDocumento) {
                    final FileAllegato documento = itemDocumento.getModelObject();

                    @SuppressWarnings("rawtypes")
                    BottoneAJAXDownloadWithError btnDocumento =
                        creaDownloadDocumento(
                            documento.getIdFile(), "btnDocumento", documento.getNomeFile());
                    itemDocumento.addOrReplace(btnDocumento);
                  }
                };
            listViewDocumenti.setVisible(!LabelFdCUtil.checkEmptyList(listaAllegati));
            itemRimborso.addOrReplace(listViewDocumenti);

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnDocumentoProtocollo =
                creaDownloadDocumento(
                    rimborso.getDatiRichiedenteRimborso().getIdFileProtocollo(),
                    "btnDocumentoProtocollo",
                    null);
            itemRimborso.addOrReplace(btnDocumentoProtocollo);
          }
        };

    listViewRimborsi.setVisible(checkRimborsi(listaRimborsi));

    addOrReplace(listViewRimborsi);

    pagination = new PaginazioneFascicoloPanel("pagination", listViewRimborsi);
    pagination.setVisible(checkRimborsi(listaRimborsi) && listaRimborsi.size() > 4);
    addOrReplace(pagination);
  }

  private boolean checkRimborsi(List<TARIRimborsi> listaRimborsi) {
    return LabelFdCUtil.checkIfNotNull(listaRimborsi)
        && !LabelFdCUtil.checkEmptyList(listaRimborsi);
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadDocumento(
      String identificativoDocumento, String wicketId, String nomeFile) {
    BottoneAJAXDownloadWithError btnDocumento =
        new BottoneAJAXDownloadWithError(wicketId, RimborsiTariNetribePanel.this) {

          private static final long serialVersionUID = -8882801682193577502L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            try {

              DatiFileAllegatoNetribe datiFileAllegato =
                  ServiceLocator.getInstance()
                      .getServiziTariNetribe()
                      .datiFileAllegatoNetribe(identificativoDocumento);

              FileDaScaricare fileDaScaricare = new FileDaScaricare();

              if (datiFileAllegato.isScaricato()) {
                FileAllegato documento = datiFileAllegato.getFileAllegatoNetribe();

                fileDaScaricare.setFileBytes(documento.getFile());
                fileDaScaricare.setFileName(documento.getNomeFile());
                fileDaScaricare.setEsitoOK(datiFileAllegato.isScaricato());
              } else {
                fileDaScaricare.setMessaggioErrore(datiFileAllegato.getMessaggioErrore());
                fileDaScaricare.setEsitoOK(datiFileAllegato.isScaricato());
              }

              return fileDaScaricare;

            } catch (ApiException | BusinessException | IOException e) {
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setMessaggioErrore("Errore durante download file");
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }

          @Override
          protected String creaLabelEtichetta(Component pannello, String id) {

            if (PageUtil.isStringValid(nomeFile)) {
              String nomeFileRidotto = nomeFile.substring(13, nomeFile.length());
              return "Scarica file ".concat(nomeFileRidotto);
            } else {
              String nomePannello = pannello.getClass().getSimpleName();
              String resourceId = nomePannello + "." + wicketId;
              String etichetta = getLocalizer().getString(resourceId, pannello);

              return etichetta;
            }
          }
        };

    btnDocumento.setVisibileDopoDownload(true);

    return btnDocumento;
  }
}
