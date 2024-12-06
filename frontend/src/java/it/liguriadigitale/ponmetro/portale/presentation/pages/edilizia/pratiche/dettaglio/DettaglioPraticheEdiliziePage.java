package it.liguriadigitale.ponmetro.portale.presentation.pages.edilizia.pratiche.dettaglio;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.Indirizzo;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.MovimentoAltri;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.MovimentoIter;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.MovimentoPareri;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.MovimentoSpostamenti;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.Pratica;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.PraticaSingola;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.Progettista;
import it.liguriadigitale.ponmetro.edilizia.pratiche.model.Richiedente;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.BreadcrumbFdC;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.abbonamentiamt.common.AmtCardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class DettaglioPraticheEdiliziePage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 7660309620639141558L;

  PraticaSingola praticaSingola;

  public DettaglioPraticheEdiliziePage(PraticaSingola praticaSingola) {
    super();
    this.praticaSingola = praticaSingola;
    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    try {

      log.debug("numero pratica = " + praticaSingola.getPRANPROTNUMERO());

      addOrReplace(
          new CardLabel<>(
              "PRAVAPROTCODTIPOPROTOCOLLO",
              praticaSingola.getPRAVAPROTCODTIPOPROTOCOLLO(),
              DettaglioPraticheEdiliziePage.this));

      addOrReplace(
          new CardLabel<>(
              "PRANPROTNUMERO",
              praticaSingola.getPRANPROTNUMERO(),
              DettaglioPraticheEdiliziePage.this));

      addOrReplace(
          new CardLabel<>(
              "PRANPROTANNO",
              praticaSingola.getPRANPROTANNO(),
              DettaglioPraticheEdiliziePage.this));

      addOrReplace(
          new CardLabel<>(
              "TPAADESCRPRATICA",
              praticaSingola.getTPAADESCRPRATICA(),
              DettaglioPraticheEdiliziePage.this));

      addOrReplace(
          new CardLabel<>(
              "PRAVAOGGETTO",
              praticaSingola.getPRAVAOGGETTO(),
              DettaglioPraticheEdiliziePage.this));

      addOrReplace(
          new CardLabel<>(
              "PRADPROTRICHIESTA",
              praticaSingola.getPRADPROTRICHIESTA(),
              DettaglioPraticheEdiliziePage.this));

      addOrReplace(
          new CardLabel<>(
              "PRADPROTINGRESSOCOMUNE",
              praticaSingola.getPRADPROTINGRESSOCOMUNE(),
              DettaglioPraticheEdiliziePage.this));

      addOrReplace(
          new CardLabel<>(
              "PRADPROTIMMISSIONE",
              praticaSingola.getPRADPROTIMMISSIONE(),
              DettaglioPraticheEdiliziePage.this));

      addOrReplace(
          new CardLabel<>(
              "PRAVAPRATICACHIUSA",
              praticaSingola.getPRAVAPRATICACHIUSA(),
              DettaglioPraticheEdiliziePage.this));

      Pratica pratica =
          ServiceLocator.getInstance()
              .getServiziEdiliziaPratiche()
              .getDettaglioPratica("" + praticaSingola.getPRANCODPRATICHE());

      ListView<Indirizzo> listViewIndirizzi =
          new ListView<Indirizzo>("listViewIndirizzi", pratica.getIndirizzi()) {

            private static final long serialVersionUID = -1949303234023054323L;

            @Override
            protected void populateItem(ListItem<Indirizzo> item) {
              Indirizzo indirizzo = item.getModelObject();

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RCPACCIVICOLETTERA",
                      indirizzo.getRCPACCIVICOLETTERA(),
                      DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RCPAINTERNOLETTERA",
                      indirizzo.getRCPAINTERNOLETTERA(),
                      DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RCPAINTERNONUMERO",
                      indirizzo.getRCPAINTERNONUMERO(),
                      DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RCPAINTERNOSCALA",
                      indirizzo.getRCPAINTERNOSCALA(),
                      DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RCPSICIVICOCOLORE",
                      indirizzo.getRCPSICIVICOCOLORE(),
                      DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RCPSICIVICONUMERO",
                      indirizzo.getRCPSICIVICONUMERO(),
                      DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RCPSICIVICONUMERO",
                      indirizzo.getRCPSICIVICONUMERO(),
                      DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RCPVALOCALITA",
                      indirizzo.getRCPVALOCALITA(),
                      DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "STRASTRADADESCRIZIONE",
                      indirizzo.getSTRASTRADADESCRIZIONE(),
                      DettaglioPraticheEdiliziePage.this));
            }
          };
      add(listViewIndirizzi);

      ListView<MovimentoAltri> listViewMovimentoAltri =
          new ListView<MovimentoAltri>("listViewMovimentoAltri", pratica.getMovimentiAltri()) {

            private static final long serialVersionUID = -1949303234023054323L;

            @Override
            protected void populateItem(ListItem<MovimentoAltri> item) {
              MovimentoAltri movimentoAltri = item.getModelObject();

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "MOVADESCRIZIONEMOVIMENTO",
                      movimentoAltri.getMOVADESCRIZIONEMOVIMENTO(),
                      DettaglioPraticheEdiliziePage.this));

              LocalDate dataInizioMov = null;
              try {

                if (movimentoAltri.getRMPDINIMOV() != null) {
                  dataInizioMov =
                      LocalDateUtil.convertiDaFormatoEuropeoCondoni(movimentoAltri.getRMPDINIMOV());
                }
              } catch (BusinessException e) {
                log.error("Errore di parsing della data");
              }

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPDINIMOV", dataInizioMov, DettaglioPraticheEdiliziePage.this));

              LocalDate dataFineMov = null;
              try {

                if (movimentoAltri.getRMPDFINEMOV() != null) {
                  dataFineMov =
                      LocalDateUtil.convertiDaFormatoEuropeoCondoni(
                          movimentoAltri.getRMPDFINEMOV());
                }
              } catch (BusinessException e) {
                log.error("Errore di parsing della data");
              }

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPDFINEMOV", dataFineMov, DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPVANOTEMOVIMENTO",
                      movimentoAltri.getRMPVANOTEMOVIMENTO(),
                      DettaglioPraticheEdiliziePage.this));
            }
          };
      add(listViewMovimentoAltri);

      ListView<MovimentoIter> listViewMovimentoIter =
          new ListView<MovimentoIter>("listViewMovimentoIter", pratica.getMovimentiIter()) {

            private static final long serialVersionUID = -1949303234023054323L;

            @Override
            protected void populateItem(ListItem<MovimentoIter> item) {
              MovimentoIter movimentoIter = item.getModelObject();

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "MOVADESCRIZIONEMOVIMENTO",
                      movimentoIter.getMOVADESCRIZIONEMOVIMENTO(),
                      DettaglioPraticheEdiliziePage.this));

              LocalDate dataInizioMov = null;
              try {

                if (movimentoIter.getRMPDINIMOV() != null) {
                  dataInizioMov =
                      LocalDateUtil.convertiDaFormatoEuropeoCondoni(movimentoIter.getRMPDINIMOV());
                }
              } catch (BusinessException e) {
                log.error("Errore di parsing della data");
              }

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPDINIMOV", dataInizioMov, DettaglioPraticheEdiliziePage.this));

              LocalDate dataFineMov = null;
              try {

                if (movimentoIter.getRMPDFINEMOV() != null) {
                  dataFineMov =
                      LocalDateUtil.convertiDaFormatoEuropeoCondoni(movimentoIter.getRMPDFINEMOV());
                }
              } catch (BusinessException e) {
                log.error("Errore di parsing della data");
              }

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPDFINEMOV", dataFineMov, DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPVANOTEMOVIMENTO",
                      movimentoIter.getRMPVANOTEMOVIMENTO(),
                      DettaglioPraticheEdiliziePage.this));
            }
          };
      add(listViewMovimentoIter);

      ListView<MovimentoPareri> listViewMovimentiPareri =
          new ListView<MovimentoPareri>("listViewMovimentiPareri", pratica.getMovimentiPareri()) {

            private static final long serialVersionUID = -1949303234023054323L;

            @Override
            protected void populateItem(ListItem<MovimentoPareri> item) {
              MovimentoPareri movimentoPareri = item.getModelObject();

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "MOVADESCRIZIONEMOVIMENTO",
                      movimentoPareri.getMOVADESCRIZIONEMOVIMENTO(),
                      DettaglioPraticheEdiliziePage.this));

              LocalDate dataInizioMov = null;
              try {

                if (movimentoPareri.getRMPDINIMOV() != null) {
                  dataInizioMov =
                      LocalDateUtil.convertiDaFormatoEuropeoCondoni(
                          movimentoPareri.getRMPDINIMOV());
                }
              } catch (BusinessException e) {
                log.error("Errore di parsing della data");
              }

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPDINIMOV", dataInizioMov, DettaglioPraticheEdiliziePage.this));

              LocalDate dataFineMov = null;
              try {

                if (movimentoPareri.getRMPDFINEMOV() != null) {
                  dataFineMov =
                      LocalDateUtil.convertiDaFormatoEuropeoCondoni(
                          movimentoPareri.getRMPDFINEMOV());
                }
              } catch (BusinessException e) {
                log.error("Errore di parsing della data");
              }

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPDFINEMOV", dataFineMov, DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPVANOTEMOVIMENTO",
                      movimentoPareri.getRMPVANOTEMOVIMENTO(),
                      DettaglioPraticheEdiliziePage.this));
            }
          };
      add(listViewMovimentiPareri);

      ListView<MovimentoSpostamenti> listViewMovimentoSpostamenti =
          new ListView<MovimentoSpostamenti>(
              "listViewMovimentoSpostamenti", pratica.getMovimentiSpostamenti()) {

            private static final long serialVersionUID = -1949303234023054323L;

            @Override
            protected void populateItem(ListItem<MovimentoSpostamenti> item) {
              MovimentoSpostamenti movimentoSpostamenti = item.getModelObject();

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "MOVADESCRIZIONEMOVIMENTO",
                      movimentoSpostamenti.getMOVADESCRIZIONEMOVIMENTO(),
                      DettaglioPraticheEdiliziePage.this));

              LocalDate dataInizioMov = null;
              try {

                if (movimentoSpostamenti.getRMPDINIMOV() != null) {
                  dataInizioMov =
                      LocalDateUtil.convertiDaFormatoEuropeoCondoni(
                          movimentoSpostamenti.getRMPDINIMOV());
                }
              } catch (BusinessException e) {
                log.error("Errore di parsing della data");
              }

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPDINIMOV", dataInizioMov, DettaglioPraticheEdiliziePage.this));

              LocalDate dataFineMov = null;
              try {

                if (movimentoSpostamenti.getRMPDFINEMOV() != null) {
                  dataFineMov =
                      LocalDateUtil.convertiDaFormatoEuropeoCondoni(
                          movimentoSpostamenti.getRMPDFINEMOV());
                }
              } catch (BusinessException e) {
                log.error("Errore di parsing della data");
              }

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPDFINEMOV", dataFineMov, DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RMPVANOTEMOVIMENTO",
                      movimentoSpostamenti.getRMPVANOTEMOVIMENTO(),
                      DettaglioPraticheEdiliziePage.this));
            }
          };
      add(listViewMovimentoSpostamenti);

      WebMarkupContainer progettistiVuoti = new WebMarkupContainer("progettistiVuoti");
      progettistiVuoti.setVisible(
          pratica.getProgettisti() == null
              || pratica.getProgettisti().isEmpty()
              || pratica.getProgettisti().get(0).getPROACOGN() == null);
      add(progettistiVuoti);

      ListView<Progettista> listViewProgettista =
          new ListView<Progettista>("listViewProgettista", pratica.getProgettisti()) {

            private static final long serialVersionUID = -1949303234023054323L;

            @Override
            protected void populateItem(ListItem<Progettista> item) {
              Progettista progettista = item.getModelObject();

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "ALBVAALBO", progettista.getALBVAALBO(), DettaglioPraticheEdiliziePage.this));
              item.addOrReplace(
                  new AmtCardLabel<>(
                      "PROACAP", progettista.getPROACAP(), DettaglioPraticheEdiliziePage.this));
              item.addOrReplace(
                  new AmtCardLabel<>(
                      "PROACITTA", progettista.getPROACITTA(), DettaglioPraticheEdiliziePage.this));
              item.addOrReplace(
                  new AmtCardLabel<>(
                      "PROACODICEFISCALE",
                      progettista.getPROACODICEFISCALE(),
                      DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "PROAEMAIL", progettista.getPROAEMAIL(), DettaglioPraticheEdiliziePage.this));
              item.addOrReplace(
                  new AmtCardLabel<>(
                      "PROAFAX", progettista.getPROAFAX(), DettaglioPraticheEdiliziePage.this));
              item.addOrReplace(
                  new AmtCardLabel<>(
                      "PROAIND", progettista.getPROAIND(), DettaglioPraticheEdiliziePage.this));
              item.addOrReplace(
                  new AmtCardLabel<>(
                      "PROAISCALBON",
                      progettista.getPROAISCALBON(),
                      DettaglioPraticheEdiliziePage.this));
              item.addOrReplace(
                  new AmtCardLabel<>(
                      "PROAISCALBOPR",
                      progettista.getPROAISCALBOPR(),
                      DettaglioPraticheEdiliziePage.this));
              item.addOrReplace(
                  new AmtCardLabel<>(
                      "PROANAZ", progettista.getPROANAZ(), DettaglioPraticheEdiliziePage.this));

              AmtCardLabel cognomeNome =
                  new AmtCardLabel<>(
                      "PROACOGNNOME",
                      progettista.getPROACOGN() + " " + progettista.getPROANOME(),
                      DettaglioPraticheEdiliziePage.this);
              cognomeNome.setVisible(!(progettista.getPROACOGN() == null));
              item.addOrReplace(cognomeNome);

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "PROATEL", progettista.getPROATEL(), DettaglioPraticheEdiliziePage.this));
              item.addOrReplace(
                  new AmtCardLabel<>(
                      "PROVADESCRIZIONEENTE",
                      progettista.getPROVADESCRIZIONEENTE(),
                      DettaglioPraticheEdiliziePage.this));
              item.addOrReplace(
                  new AmtCardLabel<>(
                      "TPGATITOLOPROGETTISTA",
                      progettista.getTPGATITOLOPROGETTISTA(),
                      DettaglioPraticheEdiliziePage.this));
            }
          };
      add(listViewProgettista);

      ListView<Richiedente> listViewRichiedente =
          new ListView<Richiedente>("listViewRichiedente", pratica.getRichiedenti()) {

            private static final long serialVersionUID = -1949303234023054323L;

            @Override
            protected void populateItem(ListItem<Richiedente> item) {
              Richiedente richiedente = item.getModelObject();

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RICACAP", richiedente.getRICACAP(), DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RICACITTA", richiedente.getRICACITTA(), DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RICACOGNNOME",
                      richiedente.getRICACOGN() + "  " + richiedente.getRICANOME(),
                      DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RICAIND", richiedente.getRICAIND(), DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RICANAZ", richiedente.getRICANAZ(), DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "RICAPROV", richiedente.getRICAPROV(), DettaglioPraticheEdiliziePage.this));

              item.addOrReplace(
                  new AmtCardLabel<>(
                      "TIRATITOLORICHIED",
                      richiedente.getTIRATITOLORICHIED(),
                      DettaglioPraticheEdiliziePage.this));
            }
          };
      add(listViewRichiedente);

    } catch (BusinessException | ApiException e) {
      log.debug("Errore durante la chiamata delle API", e);
      throw new RestartResponseAtInterceptPageException(
          new ErroreServiziPage("elenco dettaglio pratiche edilizie"));
    }
  }

  public List<BreadcrumbFdC> getListaBreadcrumb() {
    List<BreadcrumbFdC> listaBreadcrumb = new ArrayList<>();

    listaBreadcrumb.add(new BreadcrumbFdC("home", "Home"));
    listaBreadcrumb.add(new BreadcrumbFdC("ioAbito", "io abito"));
    listaBreadcrumb.add(new BreadcrumbFdC("praticheEdilizia", "Le mie pratiche edilizie"));
    listaBreadcrumb.add(
        new BreadcrumbFdC("dettaglioPraticheEdilizia", "Dettaglio pratiche edilizie"));

    return listaBreadcrumb;
  }
}
