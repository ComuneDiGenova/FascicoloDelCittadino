package it.liguriadigitale.ponmetro.portale.presentation.pages.tributi.tarieng.rimborsi.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarieng.DatiRichiestaRimborsoTariEng;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.Legenda;
import it.liguriadigitale.ponmetro.portale.presentation.components.legenda.LegendaPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.variazioniresidenza.cambioindirizzo.util.VariazioniResidenzaUtil;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiRimborso;
import it.liguriadigitale.ponmetro.taririmborsieng.model.FileAllegato;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.StringResourceModel;

public class RimborsiTariEngPanel extends BasePanel {

  private static final long serialVersionUID = -8413296134803597444L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public RimborsiTariEngPanel(String id, List<DatiRimborso> rimborsi) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    fillDati(rimborsi);

    createFeedBackPanel();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {

    List<Legenda> listaLegenda =
        ServiceLocator.getInstance().getServiziTariEng().getListaLegendaRimborsi();
    LegendaPanel<Component> legendaPanel =
        new LegendaPanel<Component>("legendaPanel", listaLegenda);
    addOrReplace(legendaPanel);

    DatiRichiestaRimborsoTariEng datiRimborso = new DatiRichiestaRimborsoTariEng();
    datiRimborso.setCodiceBelfiore("D969");

    datiRimborso.setNomeBeneficiario(getUtente().getNome());
    datiRimborso.setCognomeBeneficiario(getUtente().getCognome());
    datiRimborso.setCodiceFiscaleBeneficiario(getUtente().getCodiceFiscaleOperatore());
    datiRimborso.setEmailRichiedente(getUtente().getMail());

    if (getUtente().isResidente()) {

      if (LabelFdCUtil.checkIfNotNull(getUtente().getDatiCittadinoResidente())) {
        if (LabelFdCUtil.checkIfNotNull(
            getUtente().getDatiCittadinoResidente().getCpvHasAddress())) {
          if (PageUtil.isStringValid(
              getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress())) {
            datiRimborso.setIndirizzoBeneficiario(
                getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvFullAddress());
          }
          if (PageUtil.isStringValid(
              getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity())) {
            datiRimborso.setComuneBeneficiario(
                getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvCity());
          }
          if (PageUtil.isStringValid(
              getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode())) {
            datiRimborso.setCapBeneficiario(
                getUtente().getDatiCittadinoResidente().getCpvHasAddress().getClvPostCode());
          }
        }
      }
    }

    LinkDinamicoLaddaFunzione<DatiRichiestaRimborsoTariEng> btnRimborso =
        new LinkDinamicoLaddaFunzione<>(
            "btnRimborso",
            LinkDinamicoFunzioneDataBuilder.getInstance()
                .setWicketLabelKeyText("RimborsiTariEngPanel.rimborso")
                .setWicketClassName("RichiestaRimborsoTariEngEredePage")
                .build(),
            datiRimborso,
            RimborsiTariEngPanel.this,
            "color-green col-auto icon-cassonetto");

    addOrReplace(btnRimborso);

    List<DatiRimborso> listaRimborsi = (List<DatiRimborso>) dati;

    // if (LabelFdCUtil.checkIfNotNull(listaRimborsi)) {
    // Collections.reverse(listaRimborsi);
    // }

    WebMarkupContainer listaRimborsiVuota = new WebMarkupContainer("listaRimborsiVuota");
    listaRimborsiVuota.setVisible(
        LabelFdCUtil.checkIfNotNull(listaRimborsi) && LabelFdCUtil.checkEmptyList(listaRimborsi));
    addOrReplace(listaRimborsiVuota);

    PageableListView<DatiRimborso> listView =
        new PageableListView<DatiRimborso>("box", listaRimborsi, 4) {

          private static final long serialVersionUID = -5010668907040231407L;

          @Override
          protected void populateItem(ListItem<DatiRimborso> itemRimborso) {
            final DatiRimborso rimborso = itemRimborso.getModelObject();

            String capBeneficiario = "";
            String codiceFiscaleBeneficiario = "";
            String codiceFiscaleDelegato = "";
            String comuneBeneficiario = "";
            String iban = "";
            String indirizzoBeneficiario = "";
            String modalitaPagamento = "";
            String nominativoBeneficiario = "";
            String nominativoDelegato = "";
            String swift = "";

            String annoProtocollo = "";
            String cfPIva = "";
            String nominativoDebitore = "";
            String cognome = "";
            String dataAnnullamento = "";
            LocalDate dataAnnullamentoLocaldate = null;
            String dataIstanza = "";
            LocalDate dataIstanzaLocaldate = null;
            String dataValidazione = "";
            LocalDate dataValidazioneLocaldate = null;
            String dataProtocollazione = "";
            LocalDate dataProtocollazioneLocaldate = null;
            String emailDiContatto = "";
            String enteBeneficiarioRimborso = "";
            Integer idDebitore = null;
            BigDecimal idIstanza = null;
            Double importoInteressiRimborso = null;
            Double importoIstanza = null;
            Double importoRimborso = null;
            String nome = "";
            String note = "";
            String numeroDocumento = "";
            String numeroProtocollo = "";
            String numeroProtocolloAnnoProtocollo = "";
            String stato = "";
            String tipologiaRichiedenteRimborso = "";
            String tipoRimborso = "";

            List<FileAllegato> listaAllegati = new ArrayList<>();

            if (LabelFdCUtil.checkIfNotNull(rimborso.getDatiIstanza())) {
              capBeneficiario = rimborso.getDatiIstanza().getCapBeneficiario();
              codiceFiscaleBeneficiario = rimborso.getDatiIstanza().getCodiceFiscaleBeneficiario();
              codiceFiscaleDelegato = rimborso.getDatiIstanza().getCodiceFiscaleDelegato();
              comuneBeneficiario = rimborso.getDatiIstanza().getComuneBeneficiario();
              iban = rimborso.getDatiIstanza().getIban();
              indirizzoBeneficiario = rimborso.getDatiIstanza().getIndirizzoBeneficiario();

              if (PageUtil.isStringValid(rimborso.getDatiIstanza().getModalitaPagamento())) {
                if (rimborso.getDatiIstanza().getModalitaPagamento().equalsIgnoreCase("CAS")) {
                  modalitaPagamento = "Ritiro presso tesoreria";
                }
                if (rimborso.getDatiIstanza().getModalitaPagamento().equalsIgnoreCase("CAB")) {
                  modalitaPagamento = "Accredito su conto corrente";
                }
              }

              nominativoBeneficiario = rimborso.getDatiIstanza().getNominativoBeneficiario();
              nominativoDelegato = rimborso.getDatiIstanza().getNominativoDelegato();
              swift = rimborso.getDatiIstanza().getSwift();

              listaAllegati = rimborso.getDatiIstanza().getDocumentiAllegati();
            }

            if (LabelFdCUtil.checkIfNotNull(rimborso.getDatiRimborso())) {
              annoProtocollo = rimborso.getDatiRimborso().getAnnoProtocollo();
              cfPIva = rimborso.getDatiRimborso().getCfPIva();
              cognome = rimborso.getDatiRimborso().getCognome();
              dataAnnullamento = rimborso.getDatiRimborso().getDataAnnullamento();
              dataIstanza = rimborso.getDatiRimborso().getDataIstanza();
              dataValidazione = rimborso.getDatiRimborso().getDataValidazione();
              dataProtocollazione = rimborso.getDatiRimborso().getDataProtocollazione();

              // try {
              if (PageUtil.isStringValid(dataAnnullamento)) {
                dataAnnullamentoLocaldate =
                    LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(dataAnnullamento);
              }
              if (PageUtil.isStringValid(dataIstanza)) {
                dataIstanzaLocaldate =
                    LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(dataIstanza);
              }
              if (PageUtil.isStringValid(dataValidazione)) {
                dataValidazioneLocaldate =
                    LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(dataValidazione);
              }
              if (PageUtil.isStringValid(dataProtocollazione)) {
                dataProtocollazioneLocaldate =
                    LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(dataProtocollazione);
              }
              // } catch (BusinessException e) {
              // log.error("Errori date Eng rimborsi: " + e.getMessage(), e);
              // }

              emailDiContatto = rimborso.getDatiRimborso().getEmailDiContatto();
              enteBeneficiarioRimborso = rimborso.getDatiRimborso().getEnteBeneficiarioRimborso();
              idDebitore = rimborso.getDatiRimborso().getIdDebitore();
              idIstanza = rimborso.getDatiRimborso().getIdIstanza();
              importoInteressiRimborso = rimborso.getDatiRimborso().getImportoInteressiRimborso();
              importoIstanza = rimborso.getDatiRimborso().getImportoIstanza();
              importoRimborso = rimborso.getDatiRimborso().getImportoRimborso();
              nome = rimborso.getDatiRimborso().getNome();
              note = rimborso.getDatiRimborso().getNote();
              numeroDocumento = rimborso.getDatiRimborso().getNumeroDocumento();
              numeroProtocollo = rimborso.getDatiRimborso().getNumeroProtocollo();
              stato = rimborso.getDatiRimborso().getStato();
              tipologiaRichiedenteRimborso =
                  rimborso.getDatiRimborso().getTipologiaRichiedenteRimborso();

              numeroProtocolloAnnoProtocollo = numeroProtocollo.concat("/").concat(annoProtocollo);

              nominativoDebitore = rimborso.getDatiRimborso().getNome().concat(" ").concat(cognome);

              if (PageUtil.isStringValid(rimborso.getDatiRimborso().getTipoRimborso())) {
                if (rimborso
                    .getDatiRimborso()
                    .getTipoRimborso()
                    .equalsIgnoreCase("EccedenzaReale")) {
                  tipoRimborso = "Eccedenza da altri pagamenti";
                }
                if (rimborso
                    .getDatiRimborso()
                    .getTipoRimborso()
                    .equalsIgnoreCase("EccedenzaDaResiduoNegativo")) {

                  if (PageUtil.isStringValid(
                      rimborso.getDatiRimborso().getEnteBeneficiarioRimborso())) {
                    if (rimborso
                        .getDatiRimborso()
                        .getEnteBeneficiarioRimborso()
                        .equalsIgnoreCase("Provincia")) {
                      tipoRimborso = "TEFA";
                    }
                    if (rimborso
                        .getDatiRimborso()
                        .getEnteBeneficiarioRimborso()
                        .equalsIgnoreCase("Ente")) {
                      tipoRimborso = "TARI";
                    }
                  }
                }
              }
            }

            String idIstanzaString = "";
            if (LabelFdCUtil.checkIfNotNull(idIstanza)) {
              idIstanzaString = String.valueOf(idIstanza);
            }
            NotEmptyLabel idIstanzaLabel = new NotEmptyLabel("idIstanza", idIstanzaString);
            idIstanzaLabel.setVisible(LabelFdCUtil.checkIfNotNull(idIstanza));
            itemRimborso.addOrReplace(idIstanzaLabel);

            NotEmptyLabel numeroProtocolloAnnoProtocolloLabel =
                new NotEmptyLabel("numeroProtocolloAnnoProtocollo", numeroProtocolloAnnoProtocollo);
            numeroProtocolloAnnoProtocolloLabel.setVisible(
                PageUtil.isStringValid(numeroProtocolloAnnoProtocollo));
            itemRimborso.addOrReplace(numeroProtocolloAnnoProtocolloLabel);

            NotEmptyLabel dataProtocollazioneLabel =
                new NotEmptyLabel("dataProtocollazione", dataProtocollazioneLocaldate);
            dataProtocollazioneLabel.setVisible(
                LabelFdCUtil.checkIfNotNull(dataProtocollazioneLocaldate));
            itemRimborso.addOrReplace(dataProtocollazioneLabel);

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("capBeneficiario", capBeneficiario, RimborsiTariEngPanel.this)
                    .setVisible(false));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "codiceFiscaleBeneficiario",
                    codiceFiscaleBeneficiario,
                    RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "codiceFiscaleDelegato", codiceFiscaleDelegato, RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                        "comuneBeneficiario", comuneBeneficiario, RimborsiTariEngPanel.this)
                    .setVisible(false));

            itemRimborso.addOrReplace(new AmtCardLabel<>("iban", iban, RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                        "indirizzoBeneficiario", indirizzoBeneficiario, RimborsiTariEngPanel.this)
                    .setVisible(false));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "modalitaPagamento", modalitaPagamento, RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "nominativoBeneficiario", nominativoBeneficiario, RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "nominativoDelegato", nominativoDelegato, RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("swift", swift, RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "nominativoDebitore", nominativoDebitore, RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("cfPIva", cfPIva, RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("cognome", cognome, RimborsiTariEngPanel.this)
                    .setVisible(false));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "dataAnnullamento",
                    LocalDateUtil.getDataFormatoEuropeo(dataAnnullamentoLocaldate),
                    RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "dataIstanza",
                    LocalDateUtil.getDataFormatoEuropeo(dataIstanzaLocaldate),
                    RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "dataValidazione",
                    LocalDateUtil.getDataFormatoEuropeo(dataValidazioneLocaldate),
                    RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("emailDiContatto", emailDiContatto, RimborsiTariEngPanel.this));

            String enteBeneficiarioRimborsoDecodifica = "";
            if (PageUtil.isStringValid(enteBeneficiarioRimborso)) {
              if (enteBeneficiarioRimborso.equalsIgnoreCase("Ente")) {
                enteBeneficiarioRimborsoDecodifica = "Comune di Genova";
              } else if (enteBeneficiarioRimborso.equalsIgnoreCase("Provincia")) {
                enteBeneficiarioRimborsoDecodifica = "Città metropolitana";
              } else {
                enteBeneficiarioRimborsoDecodifica = enteBeneficiarioRimborso;
              }
            }
            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "enteBeneficiarioRimborso",
                    enteBeneficiarioRimborsoDecodifica,
                    RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("idDebitore", idDebitore, RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                        "importoInteressiRimborso",
                        importoInteressiRimborso,
                        RimborsiTariEngPanel.this)
                    .setVisible(
                        LabelFdCUtil.checkIfNotNull(importoInteressiRimborso)
                            && Double.compare(importoInteressiRimborso, 0.0) > 0));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("importoIstanza", importoIstanza, RimborsiTariEngPanel.this)
                    .setVisible(
                        LabelFdCUtil.checkIfNotNull(importoIstanza)
                            && Double.compare(importoIstanza, 0.0) > 0));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("importoRimborso", importoRimborso, RimborsiTariEngPanel.this)
                    .setVisible(
                        LabelFdCUtil.checkIfNotNull(importoRimborso)
                            && Double.compare(importoRimborso, 0.0) > 0));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("nome", nome, RimborsiTariEngPanel.this).setVisible(false));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("note", note, RimborsiTariEngPanel.this).setVisible(false));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("numeroDocumento", numeroDocumento, RimborsiTariEngPanel.this)
                    .setVisible(false));

            String statoDecodificato =
                ServiceLocator.getInstance()
                    .getServiziTariEng()
                    .decodificaStatoIstanzaDiRimborso(stato);
            itemRimborso.addOrReplace(
                new AmtCardLabel<>("stato", statoDecodificato, RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>(
                    "tipologiaRichiedenteRimborso",
                    tipologiaRichiedenteRimborso,
                    RimborsiTariEngPanel.this));

            itemRimborso.addOrReplace(
                new AmtCardLabel<>("tipoRimborso", tipoRimborso, RimborsiTariEngPanel.this));

            ListView<FileAllegato> listViewFile =
                new ListView<FileAllegato>("listViewFile", listaAllegati) {

                  private static final long serialVersionUID = 1526658560896615823L;

                  @Override
                  protected void populateItem(ListItem<FileAllegato> itemAllegato) {

                    FileAllegato fileAllegato = itemAllegato.getModelObject();

                    String nomeFile = fileAllegato.getNomeFile();
                    String nomeFileTogliendoDoppiaEstensione = "";

                    if (PageUtil.isStringValid(nomeFile)) {

                      long countPuntiInNomeFile =
                          nomeFile.chars().filter(elem -> elem == '.').count();

                      log.debug("CP countPuntiInNomeFile = " + countPuntiInNomeFile);

                      if (LabelFdCUtil.checkIfNotNull(countPuntiInNomeFile)
                          && countPuntiInNomeFile > 1) {
                        int lastIndexOfPunto = nomeFile.lastIndexOf(".");
                        nomeFileTogliendoDoppiaEstensione = nomeFile.substring(0, lastIndexOfPunto);
                      } else {
                        nomeFileTogliendoDoppiaEstensione = nomeFile;
                      }
                    }

                    try {

                      if (LabelFdCUtil.checkIfNotNull(fileAllegato.getFile())
                          && fileAllegato.getFile().length > 0) {
                        itemAllegato.addOrReplace(
                            new Label(
                                "nomeFile",
                                new StringResourceModel("RimborsiTariEngPanel.allegato", this)
                                    .setParameters(nomeFile)));

                        itemAllegato.addOrReplace(
                            VariazioniResidenzaUtil.createLinkDocumentoCaricato(
                                "btnScaricaFile",
                                nomeFileTogliendoDoppiaEstensione,
                                fileAllegato.getFile()));
                      } else {

                        itemAllegato.addOrReplace(
                            new WebMarkupContainer("nomeFile").setVisible(false));

                        itemAllegato.addOrReplace(
                            new WebMarkupContainer("btnScaricaFile").setVisible(false));
                      }

                    } catch (BusinessException | MagicMatchNotFoundException e) {
                      log.error(
                          "Errore durante scarico PDF Rimborso TARI Eng: " + e.getMessage(), e);
                    }
                  }
                };
            itemRimborso.addOrReplace(listViewFile);
          }
        };

    listView.setRenderBodyOnly(true);
    listView.setVisible(
        LabelFdCUtil.checkIfNotNull(listaRimborsi) && !LabelFdCUtil.checkEmptyList(listaRimborsi));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(
        LabelFdCUtil.checkIfNotNull(listaRimborsi)
            && !LabelFdCUtil.checkEmptyList(listaRimborsi)
            && listaRimborsi.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }
}
