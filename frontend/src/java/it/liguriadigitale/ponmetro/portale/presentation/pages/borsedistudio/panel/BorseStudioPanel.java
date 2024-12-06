package it.liguriadigitale.ponmetro.portale.presentation.pages.borsedistudio.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.borsestudio.model.Annualita;
import it.liguriadigitale.ponmetro.borsestudio.model.Documento;
import it.liguriadigitale.ponmetro.borsestudio.model.FileAllegato;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica.AccettazioneNucleoIseeAnagraficoEnum;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica.StatoPraticaEnum;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.BottoneAJAXDownloadWithError;
import it.liguriadigitale.ponmetro.portale.presentation.components.download.pojo.FileDaScaricare;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;

public class BorseStudioPanel extends BasePanel {

  private static final long serialVersionUID = -1199146446645343227L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  private static final String ICON_ACCETTATA = "color-fc-success col-3 icon-borsa_studio";
  private static final String ICON_IN_ELABORAZIONE = "color-fc-warning col-3 icon-borsa_studio";
  private static final String ICON_RIFIUTATA = "color-fc-secondary col-3 icon-borsa_studio";
  private static final String ICON_INCOMPLETA = "color-fc-secondary col-3 icon-borsa_studio";
  private static final String ICON_BORSA = "color-fc-pink col-3 icon-borsa_studio";

  public BorseStudioPanel(String id, List<Pratica> listaPratiche) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    fillDati(listaPratiche);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<Pratica> listaPratiche = (List<Pratica>) dati;

    Annualita annualita = null;
    try {
      annualita =
          ServiceLocator.getInstance().getServiziBorseDiStudio().validitaDomandaBorseDiStudio();
    } catch (BusinessException | ApiException | IOException e1) {
      log.error("Errore validita annualita borse = " + e1.getMessage(), e1);
    }

    boolean visibilitaBottoneDomanda = false;

    OffsetDateTime dataOraAdesso = OffsetDateTime.now();
    log.debug("CP dataOraAdesso = " + dataOraAdesso);

    if (LabelFdCUtil.checkIfNotNull(annualita)) {

      visibilitaBottoneDomanda =
          annualita.getAperto()
              && (LabelFdCUtil.checkIfNotNull(annualita.getDataInizio())
                  && LabelFdCUtil.checkIfNotNull(annualita.getDataFine())
                  && ((dataOraAdesso.isAfter(annualita.getDataInizio())
                          || dataOraAdesso.isEqual(annualita.getDataInizio()))
                      && (dataOraAdesso.isBefore(annualita.getDataFine())
                          || dataOraAdesso.isEqual(annualita.getDataFine()))));
    }

    LinkDinamicoLaddaFunzione<Object> btnDomandaBorsaStudio =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaBorsaStudio",
            new LinkDinamicoFunzioneData(
                "BorseStudioPanel.btnDomandaBorsaStudio",
                "DomandaBorseStudioPage",
                "BorseStudioPanel.btnDomandaBorsaStudio"),
            null,
            BorseStudioPanel.this,
            "color-fc-pink col-auto icon-borsa_studio",
            visibilitaBottoneDomanda);
    addOrReplace(btnDomandaBorsaStudio);

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkPratiche(listaPratiche));
    addOrReplace(listaVuota);

    PageableListView<Pratica> listView =
        new PageableListView<Pratica>("pratiche", listaPratiche, 4) {

          private static final long serialVersionUID = -4958590730986269569L;

          @SuppressWarnings("serial")
          @Override
          protected void populateItem(ListItem<Pratica> itemPratica) {
            final Pratica pratica = itemPratica.getModelObject();

            WebMarkupContainer icona = new WebMarkupContainer("icona");
            icona.add(getCssIconaBorsa(pratica.getStatoPratica()));
            itemPratica.addOrReplace(icona);

            NotEmptyLabel identificativoDomandaOnline =
                new NotEmptyLabel(
                    "identificativoDomandaOnline",
                    String.valueOf(pratica.getIdentificativoDomandaOnline()));
            // identificativoDomandaOnline.setVisible(LabelFdCUtil.checkIfNotNull(pratica.getIdentificativoDomandaOnline()));
            identificativoDomandaOnline.setVisible(false);
            itemPratica.addOrReplace(identificativoDomandaOnline);

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "nomeRichiedente", pratica.getNomeRichiedente(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "cognomeRichiedente", pratica.getCognomeRichiedente(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "codiceFiscaleRichiedente",
                    pratica.getCodiceFiscaleRichiedente(),
                    BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                        "matricolaRichiedente",
                        pratica.getMatricolaRichiedente(),
                        BorseStudioPanel.this)
                    .setVisible(false));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "nomeIntestatarioBorsa",
                    pratica.getNomeIntestatarioBorsa(),
                    BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "cognomeIntestatarioBorsa",
                    pratica.getCognomeIntestatarioBorsa(),
                    BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "codiceIntestatarioBorsa",
                    pratica.getCodiceIntestatarioBorsa(),
                    BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "dataNascitaIntestatarioBorsa",
                    pratica.getDataNascitaIntestatarioBorsa(),
                    BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "identificativoDocumentoIntestatarioBorsa",
                    pratica.getIdentificativoDocumentoIntestatarioBorsa(),
                    BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                        "scuolaModificata", pratica.getScuolaModificata(), BorseStudioPanel.this)
                    .setVisible(false));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "dataRichiesta", pratica.getDataRichiesta(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "numeroProtocolloIsee",
                    pratica.getNumeroProtocolloIsee(),
                    BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "annoProtocolloIsee", pratica.getAnnoProtocolloIsee(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "identificativoDomanda",
                    pratica.getIdentificativoDomanda(),
                    BorseStudioPanel.this));

            BigDecimal codiceAnnoScolastico = null;
            String descrizioneAnnoScolastico = "";

            if (LabelFdCUtil.checkIfNotNull(pratica.getAnnoScolastico())) {
              codiceAnnoScolastico = pratica.getAnnoScolastico().getCodice();
              descrizioneAnnoScolastico = pratica.getAnnoScolastico().getDescrizione();
            }

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                        "codiceAnnoScolastico", codiceAnnoScolastico, BorseStudioPanel.this)
                    .setVisible(false));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "descrizioneAnnoScolastico", descrizioneAnnoScolastico, BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "statoPratica", pratica.getStatoPratica(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "importoRichiesto", pratica.getImportoRichiesto(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "dataPresentazioneIsee",
                    pratica.getDataPresentazioneIsee(),
                    BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "dataValiditaIsee", pratica.getDataValiditaIsee(), BorseStudioPanel.this));

            Double importoIseeDouble = null;
            if (LabelFdCUtil.checkIfNotNull(pratica.getImportoIsee())) {
              importoIseeDouble = pratica.getImportoIsee().doubleValue();
            }
            itemPratica.addOrReplace(
                new AmtCardLabel<>("importoIsee", importoIseeDouble, BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>("email", pratica.getEmail(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>("cellulare", pratica.getCellulare(), BorseStudioPanel.this));

            BigDecimal codiceParentela = null;
            String descrizioneParentela = "";
            if (LabelFdCUtil.checkIfNotNull(pratica.getParentela())) {
              codiceParentela = pratica.getParentela().getCodice();
              descrizioneParentela = pratica.getParentela().getDescrizione();
            }

            itemPratica.addOrReplace(
                new AmtCardLabel<>("codiceParentela", codiceParentela, BorseStudioPanel.this)
                    .setVisible(false));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "descrizioneParentela", descrizioneParentela, BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "numeroFigliACarico", pratica.getNumeroFigliACarico(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "numeroPersoneDisabili",
                    pratica.getNumeroPersoneDisabili(),
                    BorseStudioPanel.this));

            String descrizioneProvinciaResidenza = "";
            try {
              descrizioneProvinciaResidenza =
                  ServiceLocator.getInstance()
                      .getServiziBorseDiStudio()
                      .getDescrizioneProvinciaDaCodice(pratica.getProvinciaResidenza());
            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore provincia residenza = " + e.getMessage(), e);
            }

            String descrizioneComuneResidenza = "";
            try {
              descrizioneComuneResidenza =
                  ServiceLocator.getInstance()
                      .getServiziBorseDiStudio()
                      .getDescrizioneComuneDaCodice(
                          pratica.getProvinciaResidenza(), pratica.getComuneResidenza());
            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore provincia residenza = " + e.getMessage(), e);
            }

            String indirizzoCompletoResidenza = "";
            if (PageUtil.isStringValid(pratica.getViaResidenza())) {
              indirizzoCompletoResidenza =
                  indirizzoCompletoResidenza.concat(pratica.getViaResidenza()).concat(", ");
            }
            if (PageUtil.isStringValid(pratica.getCivicoResidenza())) {
              indirizzoCompletoResidenza =
                  indirizzoCompletoResidenza.concat(pratica.getCivicoResidenza()).concat(", ");
            }
            if (PageUtil.isStringValid(pratica.getInternoResidenza())) {
              indirizzoCompletoResidenza =
                  indirizzoCompletoResidenza.concat(pratica.getInternoResidenza()).concat(", ");
            }
            if (PageUtil.isStringValid(pratica.getCapResidenza())) {
              indirizzoCompletoResidenza =
                  indirizzoCompletoResidenza.concat(pratica.getCapResidenza()).concat(", ");
            }
            if (PageUtil.isStringValid(descrizioneComuneResidenza)) {
              indirizzoCompletoResidenza =
                  indirizzoCompletoResidenza.concat(descrizioneComuneResidenza).concat(", ");
            }
            if (PageUtil.isStringValid(descrizioneProvinciaResidenza)) {
              indirizzoCompletoResidenza =
                  indirizzoCompletoResidenza.concat(descrizioneProvinciaResidenza);
            }

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "indirizzoCompletoResidenza",
                    indirizzoCompletoResidenza,
                    BorseStudioPanel.this));

            String descrizioneProvinciaDomicilio = "";
            try {
              descrizioneProvinciaDomicilio =
                  ServiceLocator.getInstance()
                      .getServiziBorseDiStudio()
                      .getDescrizioneProvinciaDaCodice(pratica.getProvinciaDomicilio());
            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore provincia residenza = " + e.getMessage(), e);
            }

            String descrizioneComuneDomicilio = "";
            try {
              descrizioneComuneDomicilio =
                  ServiceLocator.getInstance()
                      .getServiziBorseDiStudio()
                      .getDescrizioneComuneDaCodice(
                          pratica.getProvinciaDomicilio(), pratica.getComuneDomicilio());
            } catch (BusinessException | ApiException | IOException e) {
              log.error("Errore provincia residenza = " + e.getMessage(), e);
            }

            String domicilioGenovaCompleto = "";
            if (PageUtil.isStringValid(pratica.getViaDomicilioGenova())) {
              domicilioGenovaCompleto =
                  domicilioGenovaCompleto.concat(pratica.getViaDomicilioGenova()).concat(", ");
            }
            if (PageUtil.isStringValid(pratica.getCivicoDomicilioGenova())) {
              domicilioGenovaCompleto =
                  domicilioGenovaCompleto.concat(pratica.getCivicoDomicilioGenova()).concat(", ");
            }
            if (PageUtil.isStringValid(pratica.getInternoDomicilioGenova())) {
              domicilioGenovaCompleto =
                  domicilioGenovaCompleto.concat(pratica.getInternoDomicilioGenova()).concat(", ");
            }
            if (PageUtil.isStringValid(pratica.getCapDomicilioGenova())) {
              domicilioGenovaCompleto =
                  domicilioGenovaCompleto.concat(pratica.getCapDomicilioGenova());
            }
            if (PageUtil.isStringValid(descrizioneComuneDomicilio)
                && LabelFdCUtil.checkIfNotNull(pratica.getCodiceViaDomicilioGenova())) {
              domicilioGenovaCompleto =
                  domicilioGenovaCompleto.concat(", ").concat(descrizioneComuneDomicilio);
            }
            if (PageUtil.isStringValid(descrizioneProvinciaDomicilio)
                && LabelFdCUtil.checkIfNotNull(pratica.getCodiceViaDomicilioGenova())) {
              domicilioGenovaCompleto =
                  domicilioGenovaCompleto.concat(", ").concat(descrizioneProvinciaDomicilio);
            }
            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "domicilioGenovaCompleto", domicilioGenovaCompleto, BorseStudioPanel.this));

            String domicilioNoGenovaCompleto = "";
            if (PageUtil.isStringValid(pratica.getViaDomicilioNoGenova())) {
              domicilioNoGenovaCompleto =
                  domicilioNoGenovaCompleto.concat(pratica.getViaDomicilioNoGenova()).concat(", ");
            }
            if (PageUtil.isStringValid(pratica.getCivicoDomicilioNoGenova())) {
              domicilioNoGenovaCompleto =
                  domicilioNoGenovaCompleto
                      .concat(pratica.getCivicoDomicilioNoGenova())
                      .concat(", ");
            }
            if (PageUtil.isStringValid(pratica.getInternoDomicilioNoGenova())) {
              domicilioNoGenovaCompleto =
                  domicilioNoGenovaCompleto.concat(pratica.getInternoDomicilioNoGenova());
            }
            if (PageUtil.isStringValid(pratica.getCapDomicilioNoGenova())) {
              domicilioNoGenovaCompleto =
                  domicilioNoGenovaCompleto.concat(pratica.getCapDomicilioNoGenova());
            }
            if (PageUtil.isStringValid(descrizioneComuneDomicilio)
                && LabelFdCUtil.checkIfNull(pratica.getCodiceViaDomicilioGenova())) {
              domicilioNoGenovaCompleto =
                  domicilioNoGenovaCompleto.concat(", ").concat(descrizioneComuneDomicilio);
            }
            if (PageUtil.isStringValid(descrizioneProvinciaDomicilio)
                && LabelFdCUtil.checkIfNull(pratica.getCodiceViaDomicilioGenova())) {
              domicilioNoGenovaCompleto =
                  domicilioNoGenovaCompleto.concat(", ").concat(descrizioneProvinciaDomicilio);
            }

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "domicilioNoGenovaCompleto", domicilioNoGenovaCompleto, BorseStudioPanel.this));

            String codiceCategoria = "";
            String descrizioneCategoria = "";
            BigDecimal minClasse = null;
            BigDecimal maxClasse = null;

            if (LabelFdCUtil.checkIfNotNull(pratica.getCategoria())) {
              codiceCategoria = pratica.getCategoria().getCodice();
              descrizioneCategoria = pratica.getCategoria().getDescrizione();
              minClasse = pratica.getCategoria().getMinClasse();
              maxClasse = pratica.getCategoria().getMaxClasse();
            }

            itemPratica.addOrReplace(
                new AmtCardLabel<>("codiceCategoria", codiceCategoria, BorseStudioPanel.this)
                    .setVisible(false));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "descrizioneCategoria", descrizioneCategoria, BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>("minClasse", minClasse, BorseStudioPanel.this)
                    .setVisible(false));

            itemPratica.addOrReplace(
                new AmtCardLabel<>("maxClasse", maxClasse, BorseStudioPanel.this)
                    .setVisible(false));

            String codiceScuola = "";
            String descrizioneScuola = "";

            if (LabelFdCUtil.checkIfNotNull(pratica.getScuola())) {
              codiceScuola = pratica.getScuola().getCodice();
              descrizioneScuola = pratica.getScuola().getDescrizione();
            }

            itemPratica.addOrReplace(
                new AmtCardLabel<>("codiceScuola", codiceScuola, BorseStudioPanel.this)
                    .setVisible(false));

            itemPratica.addOrReplace(
                new AmtCardLabel<>("descrizioneScuola", descrizioneScuola, BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>("classe", pratica.getClasse(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>("sezione", pratica.getSezione(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>("iban", pratica.getIban(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>("iban", pratica.getIban(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>("importo", pratica.getImporto(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "figlioVittimaLavoro",
                    pratica.getFiglioVittimaLavoro(),
                    BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "figlioVittimaLavoro",
                    pratica.getFiglioVittimaLavoro(),
                    BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "iseeDifforme", pratica.getIseeDifforme(), BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "dichiarazioneSpesaFiscale",
                    pratica.getDichiarazioneSpesaFiscale(),
                    BorseStudioPanel.this));

            itemPratica.addOrReplace(
                new AmtCardLabel<>("liquidato", pratica.getLiquidato(), BorseStudioPanel.this));

            String accettazioneNucleoIseeAnagraficoValue = "";
            if (LabelFdCUtil.checkIfNotNull(pratica.getAccettazioneNucleoIseeAnagrafico())
                && PageUtil.isStringValid(pratica.getAccettazioneNucleoIseeAnagrafico())) {
              if (pratica
                  .getAccettazioneNucleoIseeAnagrafico()
                  .equalsIgnoreCase(AccettazioneNucleoIseeAnagraficoEnum.ACCETTA.value())) {
                accettazioneNucleoIseeAnagraficoValue = "Sì";
              }
              if (pratica
                  .getAccettazioneNucleoIseeAnagrafico()
                  .equalsIgnoreCase(AccettazioneNucleoIseeAnagraficoEnum.NON_ACCETTA.value())) {
                accettazioneNucleoIseeAnagraficoValue = "No";
              }
            }
            itemPratica.addOrReplace(
                new AmtCardLabel<>(
                    "accettazioneNucleoIseeAnagrafico",
                    accettazioneNucleoIseeAnagraficoValue,
                    BorseStudioPanel.this));

            ListView<Documento> listViewContainerDocumentiOut =
                new ListView<Documento>("containerDocumentiOut", pratica.getDocumentiOut()) {

                  @Override
                  protected void populateItem(ListItem<Documento> itemDocumentoOut) {
                    final Documento documentoOut = itemDocumentoOut.getModelObject();

                    @SuppressWarnings("rawtypes")
                    BottoneAJAXDownloadWithError btnScaricaFile =
                        creaDownloadFileDocumento(
                            documentoOut.getTipo(), pratica.getIdentificativoDomandaOnline());
                    itemDocumentoOut.addOrReplace(btnScaricaFile);
                  }
                };
            listViewContainerDocumentiOut.setVisible(
                LabelFdCUtil.checkIfNotNull(pratica.getDocumentiOut())
                    && !LabelFdCUtil.checkEmptyList(pratica.getDocumentiOut()));
            itemPratica.addOrReplace(listViewContainerDocumentiOut);
          }
        };

    listView.setVisible(checkPratiche(listaPratiche));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(checkPratiche(listaPratiche) && listaPratiche.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkPratiche(List<Pratica> listaPratiche) {
    return LabelFdCUtil.checkIfNotNull(listaPratiche)
        && !LabelFdCUtil.checkEmptyList(listaPratiche);
  }

  @SuppressWarnings({"rawtypes", "serial"})
  private BottoneAJAXDownloadWithError creaDownloadFileDocumento(
      String tipoFile, BigDecimal identificativoDomanda) {
    BottoneAJAXDownloadWithError btnScaricaFile =
        new BottoneAJAXDownloadWithError("btnScaricaFile", BorseStudioPanel.this) {

          private static final long serialVersionUID = -5847880505260090200L;

          String nomeFile = "";
          byte[] file = null;

          @Override
          protected FileDaScaricare eseguiBusinessPerGenerazionePDF() {

            FileAllegato fileAllegatoDomanda;
            FileDaScaricare fileDaScaricare = new FileDaScaricare();

            try {
              fileAllegatoDomanda =
                  ServiceLocator.getInstance()
                      .getServiziBorseDiStudio()
                      .getDocumentoBorse(tipoFile, identificativoDomanda);

              if (LabelFdCUtil.checkIfNotNull(fileAllegatoDomanda)) {

                if (PageUtil.isStringValid(fileAllegatoDomanda.getNomeFile())) {

                  if (tipoFile.equalsIgnoreCase(Documento.TipoEnum.FILEDOMANDA.value())) {
                    nomeFile =
                        fileAllegatoDomanda
                            .getNomeFile()
                            .concat(".")
                            .concat(fileAllegatoDomanda.getEstensioneFile());
                  } else {
                    nomeFile = fileAllegatoDomanda.getNomeFile();
                  }
                }

                file = fileAllegatoDomanda.getFile();
              }

              fileDaScaricare.setFileBytes(file);
              fileDaScaricare.setFileName(nomeFile);
              fileDaScaricare.setEsitoOK(true);
              return fileDaScaricare;
            } catch (ApiException | BusinessException | IOException e) {
              log.error(
                  "Errore scarico domanda borsa: "
                      + e.getMessage()
                      + " - "
                      + e.getLocalizedMessage(),
                  e);

              fileDaScaricare.setMessaggioErrore("Errore durante download file");
              fileDaScaricare.setEsitoOK(false);
              return fileDaScaricare;
            }
          }

          @Override
          protected String creaLabelEtichetta(Component pannello, String id) {
            String nomePannello = pannello.getClass().getSimpleName();

            String auxNomeFile = "";
            if (tipoFile.equalsIgnoreCase(Documento.TipoEnum.FILEDOMANDA.value())) {
              auxNomeFile = "btnScaricaFileDomanda";
            } else if (tipoFile.equalsIgnoreCase(
                Documento.TipoEnum.FILEFIGLIOVITTIMALAVORO.value())) {
              auxNomeFile = "btnScaricaFileFiglioVittima";
            } else if (tipoFile.equalsIgnoreCase(Documento.TipoEnum.FILESCONTRINI.value())) {
              auxNomeFile = "btnScaricaFileScontrini";
            } else {
              auxNomeFile = "btnFile";
            }

            String resourceId = nomePannello + "." + auxNomeFile;
            String etichetta = getLocalizer().getString(resourceId, pannello);

            return etichetta;
          }
        };

    btnScaricaFile.setVisibileDopoDownload(true);

    return btnScaricaFile;
  }

  private AttributeAppender getCssIconaBorsa(String stato) {
    String css = "";

    if (LabelFdCUtil.checkIfNotNull(stato)) {
      if (stato.equals(StatoPraticaEnum.ACCETTATA.value())) {
        css = ICON_ACCETTATA;
      } else if (stato.equals(StatoPraticaEnum.IN_ELABORAZIONE.value())) {
        css = ICON_IN_ELABORAZIONE;
      } else if (stato.equals(StatoPraticaEnum.RIFIUTATA.value())) {
        css = ICON_RIFIUTATA;
      } else if (stato.equals(StatoPraticaEnum.INCOMPLETA.value())) {
        css = ICON_INCOMPLETA;
      } else {
        css = ICON_BORSA;
      }
    } else {
      css = ICON_BORSA;
    }

    return new AttributeAppender("class", " " + css);
  }
}
