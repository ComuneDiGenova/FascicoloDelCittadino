package it.liguriadigitale.ponmetro.api.presentation.rest.supportoistanzeverbalipl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.supportoistanzeverbalipl.service.SupportoIstanzeVerbaliPLInterface;
import it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.VPlTIstanzeDocumenti;
import it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.VPlTIstanzeSerie;
import it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.builder.DatiDocumentoBuilder;
import it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.builder.DatiMotivoBuilder;
import it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.builder.DatiMotivoSummaryBuilder;
import it.liguriadigitale.ponmetro.api.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.BadRequestException;
import it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.NotFoundException;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.apiclient.IstanzeVerbaliPlApi;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiDocumento;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivo;
import it.liguriadigitale.ponmetro.supporto.istanzeverbalipl.model.DatiMotivoSummary;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SupportoIstanzeVerbaliPLResource implements IstanzeVerbaliPlApi {

  private static Log log = LogFactory.getLog(SupportoIstanzeVerbaliPLResource.class);

  @Override
  public List<DatiDocumento> istanzeplCompleteDocumentiCodiceHermesGet(String codiceHermes) {
    // arg0 is codiceHermes
    List<DatiDocumento> listDatiDocumento = new ArrayList<>();
    try {
      List<VPlTIstanzeDocumenti> lista = getListaDocumenti(codiceHermes);
      listDatiDocumento = buildBusinessDocumentoListComplete(lista);
      if (listDatiDocumento == null || listDatiDocumento.isEmpty()) {
        String s = "Lista builder some error, lista vuota";
        log.warn(s);
        throw new NotFoundException(s);
      }
    } catch (BadRequestException e) {
      throw e;
    } catch (Exception e) {
      String s = "Generic error get lista motivi complete";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    return listDatiDocumento;
  }

  @Override
  public List<DatiMotivo> istanzeplCompleteMotiviAndorWantequalSerieArticoliCodiceHermesGet(
      Boolean arg0, Boolean arg1, String arg2, String codiceHermes, String arg4) {
    // arg0 true se da mettere in and altrimenti in or
    // arg1 is serie separate da doppio pipe ||
    // arg2 is articolo separati da doppio pipe ||
    // arg3 is codiceHermes
    List<DatiMotivo> listDatiMotivo = new ArrayList<>();
    try {
      List<VPlTIstanzeSerie> lista = getListaMotivi(arg0, arg1, arg2, codiceHermes, arg4);
      listDatiMotivo = buildBusinessMotivoListComplete(lista);
      if (listDatiMotivo == null || listDatiMotivo.isEmpty()) {
        String s = "Lista builder some error, lista vuota";
        log.warn(s);
        throw new NotFoundException(s);
      }
    } catch (BadRequestException e) {
      throw e;
    } catch (NotFoundException e) {
      throw e;
    } catch (Exception e) {
      String s = "Generic error get lista motivi complete";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    return listDatiMotivo;
  }

  @Override
  public List<DatiMotivoSummary> istanzeplSummaryMotiviAndorWantequalSerieArticoliGet(
      Boolean arg0, Boolean arg1, String arg2, String arg3) {
    // arg0 true se da mettere in and altrimenti in or
    // arg1 is serie separate da doppio pipe ||
    // arg2 is articoli separati da doppio pipe ||
    List<DatiMotivoSummary> listDatiMotivoSummary = new ArrayList<>();
    try {
      List<VPlTIstanzeSerie> lista = getListaMotivi(arg0, arg1, arg2, arg3, "-");
      listDatiMotivoSummary = buildBusinessMotivoListSummary(lista);
      if (listDatiMotivoSummary == null || listDatiMotivoSummary.isEmpty()) {
        String s = "Lista builder some error, lista vuota";
        log.warn(s);
        // throw new NotFoundException(s);
      }
      // it.liguriadigitale.ponmetro.api.presentation.rest.application.exception.NoContentException
    } catch (BadRequestException e) {
      throw e;
    } catch (NotFoundException e) {
      throw e;
    } catch (Exception e) {
      String s = "Generic error get lista motivi summary";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    return listDatiMotivoSummary;
  }

  @Override
  public List<DatiMotivoSummary> istanzeplSummaryMotivoCodiceHermesGet(String arg0) {
    List<DatiMotivoSummary> listDatiMotivoSummary = new ArrayList<>();
    try {
      List<VPlTIstanzeSerie> lista = getListaMotivi(true, true, "-", "-", arg0);
      listDatiMotivoSummary = buildBusinessMotivoListSummary(lista);
      if (listDatiMotivoSummary == null || listDatiMotivoSummary.isEmpty()) {
        String s = "Lista builder some error, lista vuota";
        log.warn(s);
        throw new NotFoundException(s);
      }
    } catch (BadRequestException e) {
      throw e;
    } catch (Exception e) {
      String s = "Generic error get lista motivi summary";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    return listDatiMotivoSummary;
  }

  private List<VPlTIstanzeSerie> getListaMotivi(
      Boolean mettiliInAnd, Boolean wantEqual, String serie, String articolo, String codiceHermes) {
    List<VPlTIstanzeSerie> listVPlTIstanzeSerie = new ArrayList<>();
    try {
      // limit/ offset cercaoggetti da fare (helper tablegen)
      SupportoIstanzeVerbaliPLInterface service =
          ServiceLocator.getInstance().getSupportoIstanzeVerbaliPL();
      listVPlTIstanzeSerie =
          service.getListaDatiMotivi(mettiliInAnd, wantEqual, serie, articolo, codiceHermes);
      if (listVPlTIstanzeSerie == null || listVPlTIstanzeSerie.isEmpty()) {
        String s =
            "Nessun motivo trovato per la serie: "
                + serie
                + " articolo: "
                + articolo
                + " codiceHermes: "
                + codiceHermes;
        log.warn(s);
        throw new NotFoundException(s);
      }
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista motivi";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    return listVPlTIstanzeSerie;
  }

  private List<VPlTIstanzeDocumenti> getListaDocumenti(String codiceHermes) {
    List<VPlTIstanzeDocumenti> listVPlTIstanzeDocumenti = new ArrayList<>();
    try {
      SupportoIstanzeVerbaliPLInterface service =
          ServiceLocator.getInstance().getSupportoIstanzeVerbaliPL();
      listVPlTIstanzeDocumenti = service.getListaDatiDocumenti(codiceHermes);
      if (listVPlTIstanzeDocumenti == null || listVPlTIstanzeDocumenti.isEmpty()) {
        String s = "Nessun documento trovato per codiceHermes: " + codiceHermes;
        log.warn(s);
        throw new NotFoundException(s);
      }
    } catch (BusinessException e) {
      log.error(e);
      throw new BadRequestException(e.getMessage());
    } catch (Exception e) {
      String s = "Generic error get lista documenti";
      log.error(s + e);
      throw new BadRequestException(s);
    }
    return listVPlTIstanzeDocumenti;
  }

  private DatiMotivo buildDatiMotivoComplete(VPlTIstanzeSerie vPlTIstanzeSerie) {
    return new DatiMotivoBuilder()
        .setDatiSummary(buildDatiMotivoSummary(vPlTIstanzeSerie))
        .setRiferimentoLegge(vPlTIstanzeSerie.getRiferimentoLegge())
        .setMinDoc(
            vPlTIstanzeSerie.getMinDoc() == null ? "" : vPlTIstanzeSerie.getMinDoc().toString())
        .setMaxDoc(
            vPlTIstanzeSerie.getMaxDoc() == null ? "" : vPlTIstanzeSerie.getMaxDoc().toString())
        .setTipoRelazioneSerie(
            vPlTIstanzeSerie.getTipoRelazioneSerie() == null
                ? ""
                : vPlTIstanzeSerie.getTipoRelazioneSerie().toString())
        .setCodice(vPlTIstanzeSerie.getCodice())
        .setTipo(vPlTIstanzeSerie.getTipo() == null ? "" : vPlTIstanzeSerie.getTipo().toString())
        .setTipoDescrizione(vPlTIstanzeSerie.getTipoDescrizione())
        .setArticolo(
            vPlTIstanzeSerie.getArticolo() == null ? "" : vPlTIstanzeSerie.getArticolo().toString())
        .setSerie(vPlTIstanzeSerie.getSerie() == null ? "" : vPlTIstanzeSerie.getSerie().toString())
        .build();
  }

  private DatiMotivoSummary buildDatiMotivoSummary(VPlTIstanzeSerie vPlTIstanzeSerie) {
    return new DatiMotivoSummaryBuilder()
        .setCodice(vPlTIstanzeSerie.getCodiceHermes())
        .setRiferimentoLegge(vPlTIstanzeSerie.getRiferimentoLegge())
        .setDescrizione(vPlTIstanzeSerie.getTipologia())
        .setFlagIntegrazione(vPlTIstanzeSerie.getFlgIntegrazioni())
        .setDescrizioneSerieArticolo("-") // vPlTIstanzeSerie.getDescrizioneSerie())
        .build();
  }

  // Utility function
  private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
    Map<Object, Boolean> map = new ConcurrentHashMap<>();
    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  public static <T> Predicate<T> distinctByKey2(Function<? super T, ?> keyExtractor) {
    Set<Object> seen = ConcurrentHashMap.newKeySet();
    return t -> seen.add(keyExtractor.apply(t));
  }

  private List<DatiMotivoSummary> buildBusinessMotivoListSummary(List<VPlTIstanzeSerie> lista) {
    log.debug("Inizio -- SupportoIstanzeVerbaliPLResource.buildBusinessMotivoListSummary:::");
    if (lista != null && !lista.isEmpty()) {
      log.debug("helper.cercaOggetti().size: " + lista.size());
      log.debug("helper.cercaOggetti().lista: " + lista);
      List<DatiMotivoSummary> datiToRet =
          lista.stream()
              .filter(ithitem -> ithitem != null)
              .map(ithitem -> buildDatiMotivoSummary(ithitem))
              .filter(distinctByKey(ithitem -> ithitem.getCodice()))
              // .filter(distinctByKey2(DatiMotivoSummary::getCodice))
              .collect(Collectors.toList());
      // Comparator comp = new Comparator<DatiMotivoSummary>() {
      // @Override
      // public int compare(DatiMotivoSummary h1, DatiMotivoSummary h2) {
      // return h1.getCodice().compareTo(h2.getCodice());
      // }
      // };

      // datiToRet = datiToRet.stream()
      // .filter( culo -> culo.)
      // .collect(Collectors.toList());

      log.debug("helper.cercaOggetti().datiToRet: " + datiToRet);
      log.debug("datiToRet: " + datiToRet.size());
      return datiToRet;
    }
    log.debug("buildBusinessList lista null ");
    return new ArrayList<>();
  }

  private List<DatiMotivo> buildBusinessMotivoListComplete(List<VPlTIstanzeSerie> lista) {
    log.debug("Inizio -- SupportoIstanzeVerbaliPLResource.buildBusinessMotivoListComplete:::");
    if (lista != null && !lista.isEmpty()) {
      log.debug("helper.cercaOggetti().size: " + lista.size());
      log.debug("helper.cercaOggetti().lista: " + lista);
      List<DatiMotivo> datiToRet =
          lista.stream()
              .filter(ithitem -> ithitem != null)
              .map(ithitem -> buildDatiMotivoComplete(ithitem))
              .collect(Collectors.toList());
      log.debug("helper.cercaOggetti().datiToRet: " + datiToRet);
      log.debug("datiToRet: " + datiToRet.size());
      return datiToRet;
    }
    log.debug("buildBusinessList lista null ");
    return new ArrayList<>();
  }

  private DatiDocumento buildDatiDocumentoComplete(VPlTIstanzeDocumenti vPlTIstanzeDocumenti) {
    return new DatiDocumentoBuilder()
        .setCodice(vPlTIstanzeDocumenti.getCodiceHermes())
        .setDescrizione(vPlTIstanzeDocumenti.getTipologia())
        .setRiferimentoLegge(vPlTIstanzeDocumenti.getRiferimentoLegge())
        .setMinDoc(
            vPlTIstanzeDocumenti.getMinDoc() == null
                ? ""
                : vPlTIstanzeDocumenti.getMinDoc().toString())
        .setMaxDoc(
            vPlTIstanzeDocumenti.getMaxDoc() == null
                ? ""
                : vPlTIstanzeDocumenti.getMaxDoc().toString())
        .setCodiceInterno(
            vPlTIstanzeDocumenti.getCodice() == null
                ? ""
                : vPlTIstanzeDocumenti.getCodice().toString())
        .setDocumento(vPlTIstanzeDocumenti.getDocumento())
        .setFlagAutodichiarazione(vPlTIstanzeDocumenti.getFlgAutodichiarazione())
        .setIdDocumentoAlternativo(
            vPlTIstanzeDocumenti.getIdDocAlternativo() == null
                ? ""
                : vPlTIstanzeDocumenti.getIdDocAlternativo().toString())
        .setDocumentoAlternativo(vPlTIstanzeDocumenti.getDocumentoAlternativo())
        .setObbligatorio(vPlTIstanzeDocumenti.getObbligatorio())
        .build();
  }

  private List<DatiDocumento> buildBusinessDocumentoListComplete(List<VPlTIstanzeDocumenti> lista) {
    log.debug("Inizio -- SupportoIstanzeVerbaliPLResource.buildBusinessDocumentoListComplete:::");
    if (lista != null && !lista.isEmpty()) {
      log.debug("helper.cercaOggetti().size: " + lista.size());
      log.debug("helper.cercaOggetti().lista: " + lista);
      List<DatiDocumento> datiToRet =
          lista.stream()
              .filter(ithitem -> ithitem != null)
              .map(ithitem -> buildDatiDocumentoComplete(ithitem))
              .collect(Collectors.toList());
      log.debug("helper.cercaOggetti().datiToRet: " + datiToRet);
      log.debug("datiToRet: " + datiToRet.size());
      return datiToRet;
    }
    log.debug("buildBusinessList lista null ");
    return new ArrayList<>();
  }
}
