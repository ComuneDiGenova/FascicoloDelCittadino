package it.liguriadigitale.ponmetro.portale.presentation.pages.impiantitermici.panel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Documento;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Documento.TipoDocumentoEnum;
import it.liguriadigitale.ponmetro.catastoimpianti.model.Impianto;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.bottoni.FdCButtonBootstrapAjaxLink;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.impiantitermici.DettagliCaitelPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

public class CaitelPanel extends BasePanel {

  private static final long serialVersionUID = -3096850765266097236L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public CaitelPanel(String id) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<Impianto> listaImpianti = (List<Impianto>) dati;
    log.debug("CP fill dati caitel = " + listaImpianti);

    if (!checkImpianti(listaImpianti)) {
      warn(
          "Nel Catasto Impianti Termici della Regione Liguria non risultano impianti di tua competenza");
    }

    PageableListView<Impianto> listView =
        new PageableListView<Impianto>("impianti", listaImpianti, 4) {

          private static final long serialVersionUID = 1017847452690460810L;

          @Override
          protected void populateItem(ListItem<Impianto> itemImpianto) {
            final Impianto impianto = itemImpianto.getModelObject();

            NotEmptyLabel identificativoImpianto =
                new NotEmptyLabel("identificativoImpianto", impianto.getIdentificativoImpianto());
            itemImpianto.addOrReplace(identificativoImpianto);

            itemImpianto.addOrReplace(
                new AmtCardLabel<>("idImpianto", impianto.getIdImpianto(), CaitelPanel.this)
                    .setVisible(false));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>("idGruppo", impianto.getIdGruppo(), CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>("indirizzo", impianto.getIndirizzo(), CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>("nomeComune", impianto.getNomeComune(), CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>("targa", impianto.getTarga(), CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>("email", impianto.getEmail(), CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>(
                    "potenzaTotaleMassima", impianto.getPotenzaTotaleMassima(), CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>("ditta", impianto.getDitta(), CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>(
                    "annoScadenzaBollino", impianto.getAnnoScadenzaBollino(), CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>(
                    "frequenzaManutenzione",
                    impianto.getFrequenzaManutenzione(),
                    CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>("operazioni", impianto.getOperazioni(), CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>(
                    "dataInizioManutenzione",
                    impianto.getDataIninzioManutenzione(),
                    CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>(
                    "dichiarazioneManutenzione",
                    impianto.getDichiarazioneManutenzione(),
                    CaitelPanel.this));

            @SuppressWarnings("rawtypes")
            BottoneAJAXDownloadWithError btnScaricaDichiarazioneManutanzione =
                creaDownloadDichiarazioneManutenzione(impianto);
            itemImpianto.addOrReplace(btnScaricaDichiarazioneManutanzione);

            itemImpianto.addOrReplace(
                new AmtCardLabel<>(
                    "dataDismissione", impianto.getDataDismissione(), CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>(
                    "tipoGeneratore", impianto.getTipoGeneratore(), CaitelPanel.this));

            itemImpianto.addOrReplace(
                new AmtCardLabel<>("tipoMacchina", impianto.getTipoMacchina(), CaitelPanel.this));

            itemImpianto.addOrReplace(creaBtnDettagli(impianto));
          }
        };
    listView.setOutputMarkupId(true);
    listView.setOutputMarkupPlaceholderTag(true);
    listView.setVisible(checkImpianti(listaImpianti));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkImpianti(listaImpianti) && listaImpianti.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkImpianti(List<Impianto> listaImpianti) {
    return LabelFdCUtil.checkIfNotNull(listaImpianti)
        && !LabelFdCUtil.checkEmptyList(listaImpianti);
  }

  private FdCButtonBootstrapAjaxLink<Object> creaBtnDettagli(Impianto impianto) {
    FdCButtonBootstrapAjaxLink<Object> btnDettagli =
        new FdCButtonBootstrapAjaxLink<Object>("btnDettagli", Type.Primary) {

          private static final long serialVersionUID = 7146869887641747178L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(CaitelPanel.this);

            setResponsePage(new DettagliCaitelPage(impianto));
          }
        };

    btnDettagli.setLabel(Model.of(getString("CaitelPanel.dettagli")));

    return btnDettagli;
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError creaDownloadDichiarazioneManutenzione(Impianto impianto) {
    BottoneAJAXDownloadWithError btnScaricaDichiarazioneManutanzione =
        new BottoneAJAXDownloadWithError("btnScaricaDichiarazioneManutanzione", CaitelPanel.this) {

          private static final long serialVersionUID = 4754933155658117356L;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            Documento documento;
            try {

              String idImpianto = "";
              String idGruppo = "";

              // TODO controllare yaml
              // String idRapporto = null;
              String idRapporto = "1";

              TipoDocumentoEnum tipoDocumento = TipoDocumentoEnum.MANUTENZIONE;

              if (LabelFdCUtil.checkIfNotNull(impianto)) {

                if (LabelFdCUtil.checkIfNotNull(impianto.getIdImpianto())) {
                  idImpianto = String.valueOf(impianto.getIdImpianto());
                }

                if (LabelFdCUtil.checkIfNotNull(impianto.getIdGruppo())) {
                  idGruppo = String.valueOf(impianto.getIdGruppo());
                }
              }

              documento =
                  ServiceLocator.getInstance()
                      .getServiziImpiantiTermici()
                      .getDocumento(idImpianto, idGruppo, idRapporto, tipoDocumento.value());

              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              fileDaScaricare.setFileBytes(documento.getFile());
              fileDaScaricare.setFileName(documento.getNomeFile());
              fileDaScaricare.setEsitoOK(true);
              return fileDaScaricare;

            } catch (ApiException | BusinessException | IOException e) {

              String prefisso = "GNC-000-Server was unable to process request. --->";
              String message = e.getMessage();
              FileDaScaricare fileDaScaricare = new FileDaScaricare();
              if (message.contains(prefisso)) {
                log.error("ERRORE API: " + e);
                fileDaScaricare.setMessaggioErrore(message.replace(prefisso, ""));
              } else {
                fileDaScaricare.setMessaggioErrore(message);
              }
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }
        };

    btnScaricaDichiarazioneManutanzione.setVisible(
        LabelFdCUtil.checkIfNotNull(impianto)
            && PageUtil.isStringValid(impianto.getDichiarazioneManutenzione()));

    btnScaricaDichiarazioneManutanzione.setVisibileDopoDownload(true);

    return btnScaricaDichiarazioneManutanzione;
  }
}
