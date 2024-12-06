package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.contributotari.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiFileAllegatoNetribe;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCNumberField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiAgevolazioneTariffariaTari;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIsee;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import java.io.IOException;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class DettagliContributoTariPanel extends BasePanel {

  private static final long serialVersionUID = 8643628820523443178L;

  private String statoValue = "";
  private String motivoRifiutoValue = "";
  private String dataAnnullamentoValue = "";
  private String dataValidazioneValue = "";
  private String dataAutorizzazioneValue = "";
  private String annoProtocolloValue = "";
  private String numeroProtocolloValue = "";
  private String idFileProtocolloValue = "";

  private String nomeValue = "";
  private String cognomeValue = "";
  private String codiceFiscaleValue = "";
  private String emailValue = "";
  private String telefonoValue = "";

  private String flagBeneficarioNonFruitoreAltreAgevolazioniValue = "";
  private String flagCategoriaImmobileDiversaDaA1A8A9Value = "";
  private String flagInRegolaTariValue = "";
  private String flagSuperficieImmobileEntroMqValue = "";

  private String tipologiamodalitaPagamentoValue = "";
  private String codiceFiscaleDelegatoAlRitiroValue = "";
  private String cognomeDelegatoAlRitiroValue = "";
  private String nomeDelegatoAlRitiroValue = "";
  private String swiftValue = "";
  private String ibanValue = "";
  private String nomeFileValue = "";
  private String idFileValue = "";

  private Double valoreTotaleIseeValue;
  private List<DatiIsee> datiIseeValue;

  private List<DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari> datiPersoneACaricoValue;

  private Double mqMassimi;

  public DettagliContributoTariPanel(
      String id, DatiAgevolazioneTariffariaTari domanda, Double mqMassimi) {
    super(id);

    this.mqMassimi = mqMassimi;

    createFeedBackPanel();

    setOutputMarkupId(true);

    fillDati(domanda);
  }

  @SuppressWarnings({"rawtypes", "unchecked", "serial"})
  @Override
  public void fillDati(Object dati) {

    DatiAgevolazioneTariffariaTari dettagli = (DatiAgevolazioneTariffariaTari) dati;

    getValoriDettaglioContributo(dettagli);

    addOrReplace(
        new FdCTitoloPanel("datiGenerali", getString("DettagliContributoTariPanel.datiGenerali")));

    FdCNumberField idRichiesta =
        new FdCNumberField(
            "idRichiesta", Model.of(dettagli.getIdRichiesta()), DettagliContributoTariPanel.this);
    idRichiesta.setEnabled(false);
    addOrReplace(idRichiesta);

    FdCTextField dataRichiesta =
        new FdCTextField(
            "dataRichiesta",
            Model.of(dettagli.getDataRichiesta()),
            DettagliContributoTariPanel.this);
    dataRichiesta.setEnabled(false);
    addOrReplace(dataRichiesta);

    FdCTextField stato =
        new FdCTextField("stato", Model.of(statoValue), DettagliContributoTariPanel.this);
    stato.setEnabled(false);
    addOrReplace(stato);

    FdCTextField motivoRifiuto =
        new FdCTextField(
            "motivoRifiuto", Model.of(motivoRifiutoValue), DettagliContributoTariPanel.this);
    motivoRifiuto.setEnabled(false);
    motivoRifiuto.setVisible(PageUtil.isStringValid(motivoRifiutoValue));
    addOrReplace(motivoRifiuto);

    FdCTextField annoProtocollo =
        new FdCTextField(
            "annoProtocollo", Model.of(annoProtocolloValue), DettagliContributoTariPanel.this);
    annoProtocollo.setEnabled(false);
    annoProtocollo.setVisible(PageUtil.isStringValid(annoProtocolloValue));
    addOrReplace(annoProtocollo);

    FdCTextField numeroProtocollo =
        new FdCTextField(
            "numeroProtocollo", Model.of(numeroProtocolloValue), DettagliContributoTariPanel.this);
    numeroProtocollo.setEnabled(false);
    numeroProtocollo.setVisible(PageUtil.isStringValid(numeroProtocolloValue));
    addOrReplace(numeroProtocollo);

    BottoneAJAXDownloadWithError btnScaricaProtocollo =
        btnScaricaFile(idFileProtocolloValue, "btnScaricaProtocollo", " protocollo");
    addOrReplace(btnScaricaProtocollo);

    FdCTextField dataAnnullamento =
        new FdCTextField(
            "dataAnnullamento", Model.of(dataAnnullamentoValue), DettagliContributoTariPanel.this);
    dataAnnullamento.setEnabled(false);
    dataAnnullamento.setVisible(PageUtil.isStringValid(dataAnnullamentoValue));
    addOrReplace(dataAnnullamento);

    FdCTextField dataValidazione =
        new FdCTextField(
            "dataValidazione", Model.of(dataValidazioneValue), DettagliContributoTariPanel.this);
    dataValidazione.setEnabled(false);
    dataValidazione.setVisible(PageUtil.isStringValid(dataValidazioneValue));
    addOrReplace(dataValidazione);

    FdCTextField dataAutorizzazione =
        new FdCTextField(
            "dataAutorizzazione",
            Model.of(dataAutorizzazioneValue),
            DettagliContributoTariPanel.this);
    dataAutorizzazione.setEnabled(false);
    dataAutorizzazione.setVisible(PageUtil.isStringValid(dataAutorizzazioneValue));
    addOrReplace(dataAutorizzazione);

    addOrReplace(
        new FdCTitoloPanel(
            "datiRichiedente", getString("DettagliContributoTariPanel.datiRichiedente")));

    FdCTextField nome =
        new FdCTextField("nome", Model.of(nomeValue), DettagliContributoTariPanel.this);
    nome.setEnabled(false);
    addOrReplace(nome);

    FdCTextField cognome =
        new FdCTextField("cognome", Model.of(cognomeValue), DettagliContributoTariPanel.this);
    cognome.setEnabled(false);
    addOrReplace(cognome);

    FdCTextField codiceFiscale =
        new FdCTextField(
            "codiceFiscale", Model.of(codiceFiscaleValue), DettagliContributoTariPanel.this);
    codiceFiscale.setEnabled(false);
    addOrReplace(codiceFiscale);

    FdCTextField email =
        new FdCTextField("email", Model.of(emailValue), DettagliContributoTariPanel.this);
    email.setEnabled(false);
    addOrReplace(email);

    FdCTextField telefono =
        new FdCTextField("telefono", Model.of(telefonoValue), DettagliContributoTariPanel.this);
    telefono.setEnabled(false);
    addOrReplace(telefono);

    addOrReplace(
        new FdCTitoloPanel(
            "autodichiarazioni", getString("DettagliContributoTariPanel.autodichiarazioni")));

    FdCTextField flagBeneficarioNonFruitoreAltreAgevolazioni =
        new FdCTextField(
            "flagBeneficarioNonFruitoreAltreAgevolazioni",
            Model.of(flagBeneficarioNonFruitoreAltreAgevolazioniValue),
            DettagliContributoTariPanel.this);
    flagBeneficarioNonFruitoreAltreAgevolazioni.setEnabled(false);
    addOrReplace(flagBeneficarioNonFruitoreAltreAgevolazioni);

    FdCTextField flagCategoriaImmobileDiversaDaA1A8A9 =
        new FdCTextField(
            "flagCategoriaImmobileDiversaDaA1A8A9",
            Model.of(flagCategoriaImmobileDiversaDaA1A8A9Value),
            DettagliContributoTariPanel.this);
    flagCategoriaImmobileDiversaDaA1A8A9.setEnabled(false);
    addOrReplace(flagCategoriaImmobileDiversaDaA1A8A9);

    FdCTextField flagInRegolaTari =
        new FdCTextField(
            "flagInRegolaTari", Model.of(flagInRegolaTariValue), DettagliContributoTariPanel.this);
    flagInRegolaTari.setEnabled(false);
    addOrReplace(flagInRegolaTari);

    StringResourceModel mqMassimiModel =
        new StringResourceModel("DettagliContributoTariPanel.flagSuperficieImmobileEntroMq", this)
            .setParameters(mqMassimi);
    String mqMassimiDinamico = mqMassimiModel.getString();

    FdCTextField flagSuperficieImmobileEntroMq =
        new FdCTextField(
            "flagSuperficieImmobileEntroMq",
            Model.of(flagSuperficieImmobileEntroMqValue),
            mqMassimiDinamico);
    flagSuperficieImmobileEntroMq.setEnabled(false);
    addOrReplace(flagSuperficieImmobileEntroMq);

    addOrReplace(
        new FdCTitoloPanel(
            "modalitaPagamento", getString("DettagliContributoTariPanel.modalitaPagamento")));

    FdCTextField tipologiamodalitaPagamento =
        new FdCTextField(
            "tipologiamodalitaPagamento",
            Model.of(tipologiamodalitaPagamentoValue),
            DettagliContributoTariPanel.this);
    tipologiamodalitaPagamento.setEnabled(false);
    addOrReplace(tipologiamodalitaPagamento);

    FdCTextField codiceFiscaleDelegatoAlRitiro =
        new FdCTextField(
            "codiceFiscaleDelegatoAlRitiro",
            Model.of(codiceFiscaleDelegatoAlRitiroValue),
            DettagliContributoTariPanel.this);
    codiceFiscaleDelegatoAlRitiro.setEnabled(false);
    addOrReplace(codiceFiscaleDelegatoAlRitiro);

    FdCTextField cognomeDelegatoAlRitiro =
        new FdCTextField(
            "cognomeDelegatoAlRitiro",
            Model.of(cognomeDelegatoAlRitiroValue),
            DettagliContributoTariPanel.this);
    cognomeDelegatoAlRitiro.setEnabled(false);
    addOrReplace(cognomeDelegatoAlRitiro);

    FdCTextField nomeDelegatoAlRitiro =
        new FdCTextField(
            "nomeDelegatoAlRitiro",
            Model.of(nomeDelegatoAlRitiroValue),
            DettagliContributoTariPanel.this);
    nomeDelegatoAlRitiro.setEnabled(false);
    addOrReplace(nomeDelegatoAlRitiro);

    FdCTextField iban =
        new FdCTextField("iban", Model.of(ibanValue), DettagliContributoTariPanel.this);
    iban.setEnabled(false);
    iban.setVisible(PageUtil.isStringValid(ibanValue));
    addOrReplace(iban);

    FdCTextField swift =
        new FdCTextField("swift", Model.of(swiftValue), DettagliContributoTariPanel.this);
    swift.setEnabled(false);
    swift.setVisible(PageUtil.isStringValid(swiftValue));
    addOrReplace(swift);

    BottoneAJAXDownloadWithError btnScaricaDocumento =
        btnScaricaFile(idFileValue, "btnScaricaDocumento", nomeFileValue);
    addOrReplace(btnScaricaDocumento);

    addOrReplace(
        new FdCTitoloPanel(
            "datiPersoneACarico", getString("DettagliContributoTariPanel.datiPersoneACarico")));

    ListView<DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari> boxACarico =
        new ListView<DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari>(
            "boxACarico", datiPersoneACaricoValue) {

          private static final long serialVersionUID = 5409250766592729927L;

          @Override
          protected void populateItem(
              ListItem<DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari> itemACarico) {
            final DatiPersoneIndicazioneCaricoAgevolazioneTariffariaTari membroACarico =
                itemACarico.getModelObject();

            String nominativo = "";
            if (PageUtil.isStringValid(membroACarico.getNome())
                && PageUtil.isStringValid(membroACarico.getCognome())) {
              nominativo = membroACarico.getNome().concat(" ").concat(membroACarico.getCognome());
            }

            String carico = "";
            if (LabelFdCUtil.checkIfNotNull(membroACarico.getFlagIsACarico())) {
              carico = membroACarico.getFlagIsACarico() == 1 ? "Sì" : "No";
            }

            FdCTextField nominativoACarico =
                new FdCTextField(
                    "nominativoACarico", Model.of(nominativo), DettagliContributoTariPanel.this);
            nominativoACarico.setEnabled(false);
            itemACarico.addOrReplace(nominativoACarico);

            FdCTextField aCarico =
                new FdCTextField("aCarico", Model.of(carico), DettagliContributoTariPanel.this);
            aCarico.setEnabled(false);
            itemACarico.addOrReplace(aCarico);
          }
        };
    boxACarico.setVisible(LabelFdCUtil.checkIfNotNull(datiPersoneACaricoValue));
    addOrReplace(boxACarico);

    addOrReplace(new FdCTitoloPanel("datiIsee", getString("DettagliContributoTariPanel.datiIsee")));

    FdCTextField valoreTotaleIsee =
        new FdCTextField(
            "valoreTotaleIsee", Model.of(valoreTotaleIseeValue), DettagliContributoTariPanel.this);
    valoreTotaleIsee.setEnabled(false);
    addOrReplace(valoreTotaleIsee);

    ListView<DatiIsee> boxIsee =
        new ListView<DatiIsee>("boxIsee", datiIseeValue) {

          @Override
          protected void populateItem(ListItem<DatiIsee> itemIsee) {
            final DatiIsee isee = itemIsee.getModelObject();

            FdCTextField nominativoIsee =
                new FdCTextField(
                    "nominativoIsee",
                    Model.of(isee.getCodiceFiscale()),
                    DettagliContributoTariPanel.this);
            nominativoIsee.setEnabled(false);
            itemIsee.addOrReplace(nominativoIsee);

            FdCTextField protocolloIsee =
                new FdCTextField(
                    "protocolloIsee",
                    Model.of(isee.getProtocolloDSU()),
                    DettagliContributoTariPanel.this);
            protocolloIsee.setEnabled(false);
            itemIsee.addOrReplace(protocolloIsee);

            FdCTextField valoreIsee =
                new FdCTextField(
                    "valoreIsee",
                    Model.of(Double.valueOf(isee.getImporto())),
                    DettagliContributoTariPanel.this);
            valoreIsee.setEnabled(false);
            itemIsee.addOrReplace(valoreIsee);

            String data = "";
            if (LabelFdCUtil.checkIfNotNull(isee.getDataPresentazione())) {
              data = LocalDateUtil.getDataFormatoEuropeo(isee.getDataPresentazione());
            }
            FdCTextField dataIsee =
                new FdCTextField("dataIsee", Model.of(data), DettagliContributoTariPanel.this);
            dataIsee.setEnabled(false);
            itemIsee.addOrReplace(dataIsee);
          }
        };
    boxIsee.setVisible(LabelFdCUtil.checkIfNotNull(datiIseeValue));
    addOrReplace(boxIsee);
  }

  private void getValoriDettaglioContributo(DatiAgevolazioneTariffariaTari dettagli) {
    if (PageUtil.isStringValid(dettagli.getStato())) {
      statoValue = dettagli.getStato().replaceAll("_", " ");
    }

    if (PageUtil.isStringValid(dettagli.getMotivoRifiuto())) {
      motivoRifiutoValue = dettagli.getMotivoRifiuto();
    }

    if (LabelFdCUtil.checkIfNotNull(dettagli.getDataAnnullamento())) {
      dataAnnullamentoValue = LocalDateUtil.getDataFormatoEuropeo(dettagli.getDataAnnullamento());
    }

    if (LabelFdCUtil.checkIfNotNull(dettagli.getDataAutorizzazione())) {
      dataAutorizzazioneValue =
          LocalDateUtil.getDataFormatoEuropeo(dettagli.getDataAutorizzazione());
    }

    if (LabelFdCUtil.checkIfNotNull(dettagli.getDataValidazione())) {
      dataValidazioneValue = LocalDateUtil.getDataFormatoEuropeo(dettagli.getDataValidazione());
    }

    if (PageUtil.isStringValid(dettagli.getAnnoProtocollo())) {
      annoProtocolloValue = dettagli.getAnnoProtocollo();
    }

    if (PageUtil.isStringValid(dettagli.getNumeroProtocollo())) {
      numeroProtocolloValue = dettagli.getNumeroProtocollo();
    }

    if (PageUtil.isStringValid(dettagli.getIdFileProtocollo())) {
      idFileProtocolloValue = dettagli.getIdFileProtocollo();
    }

    if (LabelFdCUtil.checkIfNotNull(dettagli.getDatiCompletiRichiesta())) {
      if (LabelFdCUtil.checkIfNotNull(dettagli.getDatiCompletiRichiesta().getDatiRichiedente())) {
        nomeValue = dettagli.getDatiCompletiRichiesta().getDatiRichiedente().getNome();

        cognomeValue = dettagli.getDatiCompletiRichiesta().getDatiRichiedente().getCognome();

        codiceFiscaleValue =
            dettagli.getDatiCompletiRichiesta().getDatiRichiedente().getCodiceFiscale();

        emailValue = dettagli.getDatiCompletiRichiesta().getDatiRichiedente().getMail();

        telefonoValue = dettagli.getDatiCompletiRichiesta().getDatiRichiedente().getTelefono();
      }

      if (LabelFdCUtil.checkIfNotNull(
          dettagli.getDatiCompletiRichiesta().getAutodichiarazioniRichiedente())) {
        flagBeneficarioNonFruitoreAltreAgevolazioniValue =
            dettagli
                        .getDatiCompletiRichiesta()
                        .getAutodichiarazioniRichiedente()
                        .getFlagBeneficarioNonFruitoreAltreAgevolazioni()
                    == 1
                ? "Sì"
                : "No";

        flagCategoriaImmobileDiversaDaA1A8A9Value =
            dettagli
                        .getDatiCompletiRichiesta()
                        .getAutodichiarazioniRichiedente()
                        .getFlagCategoriaImmobileDiversaDaA1A8A9()
                    == 1
                ? "Sì"
                : "No";

        flagInRegolaTariValue =
            dettagli
                        .getDatiCompletiRichiesta()
                        .getAutodichiarazioniRichiedente()
                        .getFlagInRegolaTari()
                    == 1
                ? "Sì"
                : "No";

        flagSuperficieImmobileEntroMqValue =
            dettagli
                        .getDatiCompletiRichiesta()
                        .getAutodichiarazioniRichiedente()
                        .getFlagSuperficieImmobileEntroMq()
                    == 1
                ? "Sì"
                : "No";
      }

      if (LabelFdCUtil.checkIfNotNull(dettagli.getDatiCompletiRichiesta().getModalitaPagamento())) {
        tipologiamodalitaPagamentoValue =
            dettagli
                        .getDatiCompletiRichiesta()
                        .getModalitaPagamento()
                        .getModalitaPagamento()
                        .equalsIgnoreCase("CAB")
                    == true
                ? "Accredito su Conto Corrente"
                : "Ritiro in tesoreria";

        if (LabelFdCUtil.checkIfNotNull(
            dettagli.getDatiCompletiRichiesta().getModalitaPagamento().getDelegatoRitiro())) {
          codiceFiscaleDelegatoAlRitiroValue =
              dettagli
                  .getDatiCompletiRichiesta()
                  .getModalitaPagamento()
                  .getDelegatoRitiro()
                  .getDatiPersonali()
                  .getCodiceFiscale();

          cognomeDelegatoAlRitiroValue =
              dettagli
                  .getDatiCompletiRichiesta()
                  .getModalitaPagamento()
                  .getDelegatoRitiro()
                  .getDatiPersonali()
                  .getCognome();

          nomeDelegatoAlRitiroValue =
              dettagli
                  .getDatiCompletiRichiesta()
                  .getModalitaPagamento()
                  .getDelegatoRitiro()
                  .getDatiPersonali()
                  .getNome();

          if (LabelFdCUtil.checkIfNotNull(
              dettagli
                  .getDatiCompletiRichiesta()
                  .getModalitaPagamento()
                  .getDelegatoRitiro()
                  .getFileAllegato())) {
            idFileValue =
                dettagli
                    .getDatiCompletiRichiesta()
                    .getModalitaPagamento()
                    .getDelegatoRitiro()
                    .getFileAllegato()
                    .getIdFile();

            nomeFileValue =
                dettagli
                    .getDatiCompletiRichiesta()
                    .getModalitaPagamento()
                    .getDelegatoRitiro()
                    .getFileAllegato()
                    .getNomeFile();
          }
        }

        if (LabelFdCUtil.checkIfNotNull(
            dettagli
                .getDatiCompletiRichiesta()
                .getModalitaPagamento()
                .getCoordinateEIntestatarioContoBancario())) {
          swiftValue =
              dettagli
                  .getDatiCompletiRichiesta()
                  .getModalitaPagamento()
                  .getCoordinateEIntestatarioContoBancario()
                  .getSwift();
          ibanValue =
              dettagli
                  .getDatiCompletiRichiesta()
                  .getModalitaPagamento()
                  .getCoordinateEIntestatarioContoBancario()
                  .getIban();
        }
      }

      if (LabelFdCUtil.checkIfNotNull(
          dettagli.getDatiCompletiRichiesta().getIseeRichiederenteECoresidenti())) {
        valoreTotaleIseeValue =
            dettagli
                .getDatiCompletiRichiesta()
                .getIseeRichiederenteECoresidenti()
                .getValoreTotaleIsee();

        datiIseeValue =
            dettagli
                .getDatiCompletiRichiesta()
                .getIseeRichiederenteECoresidenti()
                .getDatiCompletiIsee();
      }

      if (LabelFdCUtil.checkIfNotNull(
          dettagli.getDatiCompletiRichiesta().getDatiPersoneACaricoONoRichiedente())) {
        datiPersoneACaricoValue =
            dettagli.getDatiCompletiRichiesta().getDatiPersoneACaricoONoRichiedente();
      }
    }
  }

  @SuppressWarnings("rawtypes")
  private BottoneAJAXDownloadWithError btnScaricaFile(
      String identificativoDocumento, String wicketId, String nomeFile) {
    BottoneAJAXDownloadWithError btnScaricaFile =
        new BottoneAJAXDownloadWithError(wicketId, DettagliContributoTariPanel.this) {

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
            return "Scarica file ".concat(nomeFile);
          }
        };

    btnScaricaFile.setVisibileDopoDownload(true);

    btnScaricaFile.setVisible(PageUtil.isStringValid(identificativoDocumento));

    return btnScaricaFile;
  }
}
