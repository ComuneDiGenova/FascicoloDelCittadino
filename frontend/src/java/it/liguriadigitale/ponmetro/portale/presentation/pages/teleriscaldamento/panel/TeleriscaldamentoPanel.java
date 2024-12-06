package it.liguriadigitale.ponmetro.portale.presentation.pages.teleriscaldamento.panel;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.StatoPraticaEnum;
import java.time.LocalDate;
import java.util.List;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class TeleriscaldamentoPanel extends BasePanel {

  private static final long serialVersionUID = -9071461389654481615L;

  private static final String ICON_TELERISCALDAMENTO = "color-fc-secondary col-3 icon-rimborsi-imu";

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public TeleriscaldamentoPanel(String id) {
    super(id);

    createFeedBackPanel();

    setOutputMarkupId(true);

    @SuppressWarnings("unchecked")
    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void fillDati(Object dati) {
    List<DomandaTeleriscaldamento> domandeTeleriscaldamento = (List<DomandaTeleriscaldamento>) dati;

    LinkDinamicoLaddaFunzione<Object> btnDomandaTeleriscaldamento =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaTeleriscaldamento",
            new LinkDinamicoFunzioneData(
                "TeleriscaldamentoPanel.btnDomandaTeleriscaldamento",
                "TeleriscaldamentoDomandaPage",
                "TeleriscaldamentoPanel.btnDomandaTeleriscaldamento"),
            null,
            TeleriscaldamentoPanel.this,
            "color-fc-secondary col-auto icon-teleriscaldamento",
            isVisibileBtnRichiesta(domandeTeleriscaldamento));
    addOrReplace(btnDomandaTeleriscaldamento);

    WebMarkupContainer listaVuota = new WebMarkupContainer("listaVuota");
    listaVuota.setVisible(!checkDomandePresenti(domandeTeleriscaldamento));
    addOrReplace(listaVuota);

    PageableListView<DomandaTeleriscaldamento> listView =
        new PageableListView<DomandaTeleriscaldamento>("domande", domandeTeleriscaldamento, 4) {

          private static final long serialVersionUID = 2120468793936335572L;

          @Override
          protected void populateItem(ListItem<DomandaTeleriscaldamento> itemDomanda) {
            final DomandaTeleriscaldamento domanda = itemDomanda.getModelObject();

            /*
             * NotEmptyLabel identificativo = new NotEmptyLabel("identificativo",
             * domanda.getIdentificativo()); identificativo.setVisible(false);
             * itemDomanda.addOrReplace(identificativo);
             */

            itemDomanda.addOrReplace(
                new AmtCardLabel<>("idStato", domanda.getIdStato(), TeleriscaldamentoPanel.this)
                    .setVisible(false));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "annoDomanda", domanda.getAnnoDomanda(), TeleriscaldamentoPanel.this));

            String decodificaStato = "";
            log.debug("CP domanda.getStatoPratica() = " + domanda.getStatoPratica());
            if (LabelFdCUtil.checkIfNotNull(domanda.getStatoPratica())) {

              if (domanda
                  .getStatoPratica()
                  .equalsIgnoreCase(StatoPraticaEnum.INVIATA_DAL_COMUNE_AD_IREN.value())) {

                decodificaStato = "Inviata dal Comune ad IREN";
              } else if (domanda
                  .getStatoPratica()
                  .equalsIgnoreCase(StatoPraticaEnum.CONSEGNATA_AL_COMUNE.value())) {

                decodificaStato = "Consegnata al Comune";
              } else {

                decodificaStato = domanda.getStatoPratica();
              }

            } else {

              decodificaStato = domanda.getStatoPratica();
            }

            itemDomanda.addOrReplace(
                new AmtCardLabel<>("statoPratica", decodificaStato, TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "dataInvioIREN", domanda.getDataInvioIREN(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "numProtocollo", domanda.getNumProtocollo(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "dataProtocollo", domanda.getDataProtocollo(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "cognomeRichiedente",
                    domanda.getCognomeRichiedente(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "nomeRichiedente", domanda.getNomeRichiedente(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "cfRichiedente", domanda.getCfRichiedente(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>("telefono", domanda.getTelefono(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "cellulare", domanda.getCellulare(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>("email", domanda.getEmail(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "numNucleoFamiliare",
                    domanda.getNumNucleoFamiliare(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                        "indicatoreIsee12",
                        domanda.getIndicatoreIsee12(),
                        TeleriscaldamentoPanel.this)
                    .setVisible(false));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                        "indicatoreIsee20",
                        domanda.getIndicatoreIsee20(),
                        TeleriscaldamentoPanel.this)
                    .setVisible(false));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                        "indicatoreIsee25",
                        domanda.getIndicatoreIsee25(),
                        TeleriscaldamentoPanel.this)
                    .setVisible(false));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "tipoUtenza", domanda.getTipoUtenza(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "numeroCliente", domanda.getNumeroCliente(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "numContratto", domanda.getNumContratto(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "viaFornitura", domanda.getViaFornitura(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "comuneFornitura", domanda.getComuneFornitura(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "numCivicoFornitura",
                    domanda.getNumCivicoFornitura(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "provinciaFornitura",
                    domanda.getProvinciaFornitura(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "capFornitura", domanda.getCapFornitura(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "nominativoContratto",
                    domanda.getNominativoContratto(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "cfIntestarioContratto",
                    domanda.getCfIntestarioContratto(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "pIvaContratto", domanda.getpIvaContratto(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "nominativoApt", domanda.getNominativoApt(), TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "nominativoAmmCond",
                    domanda.getNominativoAmmCond(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "viaAmmCondominio",
                    domanda.getViaAmmCondominio(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "comuneAmmCondominio",
                    domanda.getComuneAmmCondominio(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "civicoAmmCondominio",
                    domanda.getCivicoAmmCondominio(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "capAmmCondominio",
                    domanda.getCapAmmCondominio(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "provAmmCondominio",
                    domanda.getProvAmmCondominio(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "telAmmCondominio",
                    domanda.getTelAmmCondominio(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "cellAmmCondominio",
                    domanda.getCellAmmCondominio(),
                    TeleriscaldamentoPanel.this));

            itemDomanda.addOrReplace(
                new AmtCardLabel<>(
                    "emailAmmCondominio",
                    domanda.getEmailAmmCondominio(),
                    TeleriscaldamentoPanel.this));

            NotEmptyLabel consensoPrivacy =
                new NotEmptyLabel("consensoPrivacy", domanda.getConsensoPrivacy());
            itemDomanda.addOrReplace(consensoPrivacy);

            NotEmptyLabel consensoInf = new NotEmptyLabel("consensoInf", domanda.getConsensoInf());
            itemDomanda.addOrReplace(consensoInf);

            NotEmptyLabel datiVerificati =
                new NotEmptyLabel("datiVerificati", domanda.getDatiVerificati());
            itemDomanda.addOrReplace(datiVerificati);
            datiVerificati.setVisible(false);

            NotEmptyLabel esitoVerifica =
                new NotEmptyLabel("esitoVerifica", domanda.getEsitoVerifica());
            itemDomanda.addOrReplace(esitoVerifica);
            esitoVerifica.setVisible(false);
          }
        };

    listView.setVisible(checkDomandePresenti(domandeTeleriscaldamento));
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(
        checkDomandePresenti(domandeTeleriscaldamento) && domandeTeleriscaldamento.size() > 4);
    addOrReplace(paginazioneFascicolo);
  }

  private boolean checkDomandePresenti(List<DomandaTeleriscaldamento> domandeTeleriscaldamento) {
    return LabelFdCUtil.checkIfNotNull(domandeTeleriscaldamento)
        && !LabelFdCUtil.checkEmptyList(domandeTeleriscaldamento);
  }

  private boolean checkDate() {
    boolean dataOggiNelRange = false;

    LocalDate dataOggi = LocalDate.now();

    String chiaveInizio = "TELERISCALDAMENTO_INIZIO";
    String chiaveFine = "TELERISCALDAMENTO_FINE";

    String inizio =
        ServiceLocator.getInstance()
            .getServiziTeleriscaldamento()
            .getValoreDaDbByChiave(chiaveInizio);

    LocalDate dataInizio = null;

    if (PageUtil.isStringValid(inizio)) {
      try {
        dataInizio = LocalDateUtil.convertiDaFormatoEuropeo(inizio);
      } catch (BusinessException e) {
        log.error("Errore data inizio teleriscaldamento = " + e.getMessage(), e);
      }
    }

    String fine =
        ServiceLocator.getInstance()
            .getServiziTeleriscaldamento()
            .getValoreDaDbByChiave(chiaveFine);

    LocalDate dataFine = null;

    if (PageUtil.isStringValid(fine)) {
      try {
        dataFine = LocalDateUtil.convertiDaFormatoEuropeo(fine);
      } catch (BusinessException e) {
        log.error("Errore data inizio teleriscaldamento = " + e.getMessage(), e);
      }
    }

    if ((LabelFdCUtil.checkIfNotNull(dataInizio)
            && (dataOggi.isEqual(dataInizio) || dataOggi.isAfter(dataInizio)))
        && (LabelFdCUtil.checkIfNotNull(dataFine)
            && (dataOggi.isEqual(dataFine) || dataOggi.isBefore(dataFine)))) {
      dataOggiNelRange = true;
    }

    return dataOggiNelRange;
  }

  private boolean checkAnnoDomanda(List<DomandaTeleriscaldamento> domandeTeleriscaldamento) {
    boolean esisteUnaDomandaDiQuelAnno = false;

    String chiaveAnnoDomanda = "TELERISCALDAMENTO_ANNO";
    String annoDomanda =
        ServiceLocator.getInstance()
            .getServiziTeleriscaldamento()
            .getValoreDaDbByChiave(chiaveAnnoDomanda);

    log.debug("CP anno domanda qui = " + annoDomanda);

    if (LabelFdCUtil.checkIfNotNull(domandeTeleriscaldamento)) {
      DomandaTeleriscaldamento trovato =
          domandeTeleriscaldamento.stream()
              .filter(
                  elem ->
                      LabelFdCUtil.checkIfNotNull(elem)
                          && elem.getAnnoDomanda().equalsIgnoreCase(annoDomanda))
              .findAny()
              .orElse(null);

      if (LabelFdCUtil.checkIfNotNull(trovato)) {
        esisteUnaDomandaDiQuelAnno = true;
      } else {
        esisteUnaDomandaDiQuelAnno = false;
      }
    }

    return esisteUnaDomandaDiQuelAnno;
  }

  private boolean isVisibileBtnRichiesta(List<DomandaTeleriscaldamento> domandeTeleriscaldamento) {

    log.debug(
        "checkDate = "
            + checkDate()
            + " - !checkAnnoDomanda = "
            + !checkAnnoDomanda(domandeTeleriscaldamento));

    return checkDate() && !checkAnnoDomanda(domandeTeleriscaldamento);
  }
}
