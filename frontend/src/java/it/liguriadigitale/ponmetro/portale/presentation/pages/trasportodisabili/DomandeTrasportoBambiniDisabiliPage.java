package it.liguriadigitale.ponmetro.portale.presentation.pages.trasportodisabili;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.AlertBoxPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.alert.builder.AlertBoxPanelBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.prova.ErroreGenericoPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.trasportodisabili.panel.DomandeTrasportoBambiniDisabiliPanel;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DomandeInviate;
import it.liguriadigitale.ponmetro.trasporto.disabili.model.DomandeInviateRecordsInner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DomandeTrasportoBambiniDisabiliPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = -4433799001951918986L;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public DomandeTrasportoBambiniDisabiliPage(UtenteServiziRistorazione iscrizione) {
    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    List<DomandeInviateRecordsInner> trasporti =
        popolaTrasportoBambiniDisabili(getUtente().getCodiceFiscaleOperatore());

    boolean isRichiestaVisibile = !esisteUnaRichiestaInCorsoPerOgniFiglio(trasporti);

    LinkDinamicoLaddaFunzione<Object> btnDomandaTrasportoBambiniDisabili =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaTrasportoBambiniDisabili",
            new LinkDinamicoFunzioneData(
                "DomandeTrasportoBambiniDisabiliPage.btnDomandaTrasportoBambiniDisabili",
                "RichiestaTrasportoBambiniDisabiliPage",
                "DomandeTrasportoBambiniDisabiliPage.btnDomandaTrasportoBambiniDisabili"),
            null,
            DomandeTrasportoBambiniDisabiliPage.this,
            "color-orange col-auto icon-camper",
            isRichiestaVisibile);
    addOrReplace(btnDomandaTrasportoBambiniDisabili);

    DomandeTrasportoBambiniDisabiliPanel domandeTrasportoBambiniDisabiliPanel =
        new DomandeTrasportoBambiniDisabiliPanel("domandeTrasportoBambiniDisabiliPanel", trasporti);
    addOrReplace(domandeTrasportoBambiniDisabiliPanel);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public DomandeTrasportoBambiniDisabiliPage() {

    super();

    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    addOrReplace(breadcrumbPanel);

    AlertBoxPanel<ErroreGenericoPage> boxMessaggi =
        AlertBoxPanelBuilder.getInstance().addClazz(this.getClass()).build();
    addOrReplace(boxMessaggi);

    List<DomandeInviateRecordsInner> trasporti =
        popolaTrasportoBambiniDisabili(getUtente().getCodiceFiscaleOperatore());

    boolean isRichiestaVisibile = !esisteUnaRichiestaInCorsoPerOgniFiglio(trasporti);

    LinkDinamicoLaddaFunzione<Object> btnDomandaTrasportoBambiniDisabili =
        new LinkDinamicoLaddaFunzione<Object>(
            "btnDomandaTrasportoBambiniDisabili",
            new LinkDinamicoFunzioneData(
                "DomandeTrasportoBambiniDisabiliPage.btnDomandaTrasportoBambiniDisabili",
                "RichiestaTrasportoBambiniDisabiliPage",
                "DomandeTrasportoBambiniDisabiliPage.btnDomandaTrasportoBambiniDisabili"),
            null,
            DomandeTrasportoBambiniDisabiliPage.this,
            "color-orange col-auto icon-camper",
            isRichiestaVisibile);
    addOrReplace(btnDomandaTrasportoBambiniDisabili);

    DomandeTrasportoBambiniDisabiliPanel domandeTrasportoBambiniDisabiliPanel =
        new DomandeTrasportoBambiniDisabiliPanel("domandeTrasportoBambiniDisabiliPanel", trasporti);
    addOrReplace(domandeTrasportoBambiniDisabiliPanel);
  }

  private List<DomandeInviateRecordsInner> popolaTrasportoBambiniDisabili(String codiceFiscale) {
    log.debug("DomandeTrasportoBambiniDisabiliPage popolaTrasportoBambiniDisabili");

    List<DomandeInviateRecordsInner> listaRecords = new ArrayList<DomandeInviateRecordsInner>();
    try {
      DomandeInviate dati =
          ServiceLocator.getInstance()
              .getTrasportoDisabiliAccenture()
              .getListaDomandeTrasportoBambiniDisabili(codiceFiscale);

      if (dati != null && dati.getRecords() != null) {
        // listaRecords = dati.getRecords();

        listaRecords =
            dati.getRecords().stream()
                .sorted(Comparator.comparing(DomandeInviateRecordsInner::getCreatedDate).reversed())
                .collect(Collectors.toList());
      }
    } catch (BusinessException | ApiException | IOException e) {
      log.error("Errore popola richieste bambini disabili: " + e.getMessage(), e);
    }
    return listaRecords;
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  @SuppressWarnings("unlikely-arg-type")
  private boolean esisteUnaRichiestaInCorsoPerOgniFiglio(
      List<DomandeInviateRecordsInner> trasporti) {

    List<String> statiDomandeTrasportoNonRichiedibili =
        new ArrayList<String>() {

          private static final long serialVersionUID = -2439229267657408804L;

          {
            add("Presentata");
            add("Da prendere in carico");
            add("Presa in carico");
            add("Sospesa");
          }
        };

    //			Map<String, DomandeInviateRecordsInner> mapTrasportiPerCf = trasporti.parallelStream()
    //					.collect(Collectors.toMap(elem -> elem.getCodiceFiscaleBeneficiarioC(), elem -> elem));
    //
    //			log.debug("CP mapTrasportiPerCf = + " + mapTrasportiPerCf);

    //			Map<String, List<DomandeInviateRecordsInner>> mapTrasportiPerCfConTutteLeDomandePerCF =
    //
    //	trasporti.stream().collect(Collectors.groupingBy(DomandeInviateRecordsInner::getCodiceFiscaleBeneficiarioC));
    //
    //			log.debug("CP mapTrasportiPerCfConTutteLeDomandePerCF = " +
    // mapTrasportiPerCfConTutteLeDomandePerCF);

    List<DomandeInviateRecordsInner> listaDomandeOrdinatePerMaxDataCreazione =
        trasporti.stream()
            .sorted(
                Comparator.comparing(DomandeInviateRecordsInner::getCodiceFiscaleBeneficiarioC)
                    .thenComparing(
                        Comparator.comparing(DomandeInviateRecordsInner::getCreatedDate)
                            .reversed()))
            .collect(Collectors.toList());

    log.debug(
        "CP listaDomandeOrdinatePerMaxDataCreazione = " + listaDomandeOrdinatePerMaxDataCreazione);

    List<DomandeInviateRecordsInner> listaDomandeOrdinatePerMaxDataCreazioneFiltrataPerCF =
        listaDomandeOrdinatePerMaxDataCreazione.stream()
            .filter(distinctByKey(elem -> elem.getCodiceFiscaleBeneficiarioC()))
            .collect(Collectors.toList());

    log.debug(
        "CP listaDomandeOrdinatePerMaxDataCreazioneFiltrataPerCF = "
            + listaDomandeOrdinatePerMaxDataCreazioneFiltrataPerCF);

    List<String> listaFigli =
        getUtente().getListaFigli().stream()
            .map(UtenteServiziRistorazione::getCodiceFiscale)
            .collect(Collectors.toList());

    log.debug("CP listaFigli = " + listaFigli);

    //			List<String> listaCfBambiniDiCuiCiSonoDomande = listaFigli.stream()
    //					.filter(item ->
    // mapTrasportiPerCfConTutteLeDomandePerCF.keySet().contains(item)).collect(Collectors.toList());
    //
    //
    //			boolean listaRichiesteFatteContieneTuttiCfDeiFigli =
    // listaFigli.stream().allMatch(listaCfBambiniDiCuiCiSonoDomande::contains);
    //
    //			log.debug("CP listaCfBambiniDiCuiCiSonoDomande = " + listaCfBambiniDiCuiCiSonoDomande);
    //
    //			log.debug("CP listaRichiesteFatteContieneTuttiCfDeiFigli = " +
    // listaRichiesteFatteContieneTuttiCfDeiFigli);

    List<DomandeInviateRecordsInner> listaBambiniDiCuiCiSonoDomande =
        listaDomandeOrdinatePerMaxDataCreazioneFiltrataPerCF.stream()
            .filter(elem -> listaFigli.contains(elem.getCodiceFiscaleBeneficiarioC()))
            .collect(Collectors.toList());

    log.debug("CP listaBambiniDiCuiCiSonoDomande = " + listaBambiniDiCuiCiSonoDomande);

    /*boolean controlloStatiDiTuttiBimbi = mapTrasportiPerCf.values().stream().allMatch(elem -> statiDomandeTrasportoNonRichiedibili.contains(elem.getStatoPraticaC()));

    log.debug("CP controlloStatiDiTuttiBimbi = " + controlloStatiDiTuttiBimbi);

    boolean tuttiIBimbiHannoRichieste = listaRichiesteFatteContieneTuttiCfDeiFigli && controlloStatiDiTuttiBimbi;

    log.debug("CP tuttiIBimbiHannoRichieste = " + tuttiIBimbiHannoRichieste);

     */

    //			boolean controlloStatiDiTuttiBimbi =
    // mapTrasportiPerCfConTutteLeDomandePerCF.values().stream()
    //					.allMatch(elem ->
    // statiDomandeTrasportoNonRichiedibili.contains(elem.getStatoPraticaC()));
    //
    //			boolean tuttiIBimbiHannoRichieste = listaRichiesteFatteContieneTuttiCfDeiFigli &&
    // controlloStatiDiTuttiBimbi;

    boolean tuttiIBimbiHannoRichieste = false;

    if (listaFigli.size() == listaBambiniDiCuiCiSonoDomande.size()) {
      tuttiIBimbiHannoRichieste =
          listaDomandeOrdinatePerMaxDataCreazioneFiltrataPerCF.stream()
              .allMatch(
                  elem -> statiDomandeTrasportoNonRichiedibili.contains(elem.getStatoFrontendC()));
    }

    log.debug("CP tuttiIBimbiHannoRichieste = " + tuttiIBimbiHannoRichieste);

    return tuttiIBimbiHannoRichieste;
  }
}
