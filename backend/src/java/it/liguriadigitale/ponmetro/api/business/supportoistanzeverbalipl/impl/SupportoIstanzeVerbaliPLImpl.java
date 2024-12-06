package it.liguriadigitale.ponmetro.api.business.supportoistanzeverbalipl.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.supportoistanzeverbalipl.service.SupportoIstanzeVerbaliPLInterface;
import it.liguriadigitale.ponmetro.api.integration.dao.VPlTIstanzeDocumentiDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.VPlTIstanzeSerieDAO;
import it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.VPlTIstanzeDocumenti;
import it.liguriadigitale.ponmetro.api.pojo.supportoistanzeverbalipl.VPlTIstanzeSerie;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SupportoIstanzeVerbaliPLImpl implements SupportoIstanzeVerbaliPLInterface {

  private static Log log = LogFactory.getLog(SupportoIstanzeVerbaliPLImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public List<VPlTIstanzeSerie> getListaDatiMotivi(
      Boolean mettiliInAnd, Boolean wantEqual, String serie, String articoli, String codiceHermes) {
    List<VPlTIstanzeSerie> lista = new ArrayList<>();
    try {
      log.debug("SupportoIstanzeVerbaliPLImpl getListaDatiMotivi: serie:" + serie);
      if ("-".equals(serie))
        log.debug("SupportoIstanzeVerbaliPLImpl getListaDatiMotivi: serie == '-' ");
      if ("-".equalsIgnoreCase(serie))
        log.debug("SupportoIstanzeVerbaliPLImpl getListaDatiMotivi: serie equalsIgnoreCase(serie)");
      if ("-".compareTo(serie) == 0)
        log.debug("SupportoIstanzeVerbaliPLImpl getListaDatiMotivi: serie uguale a '-'");
      log.debug("SupportoIstanzeVerbaliPLImpl getListaDatiMotivi: articoli:" + articoli);
      log.debug("SupportoIstanzeVerbaliPLImpl getListaDatiMotivi: codiceHermes:" + codiceHermes);
      PonMetroBusinessHelper helper =
          buildHelperMotivi(
              mettiliInAnd,
              wantEqual,
              ("-".equals(serie) || "-".equalsIgnoreCase(serie))
                  ? null
                  : Stream.of(serie.split("-")).collect(Collectors.toList()),
              ("-".equals(articoli) || "-".equalsIgnoreCase(articoli))
                  ? null
                  : (Stream.of(articoli.split("-"))
                      .map(ith -> new BigDecimal(ith))
                      .collect(Collectors.toList())),
              ("-".equals(codiceHermes) || "-".equalsIgnoreCase(codiceHermes))
                  ? null
                  : codiceHermes);
      lista = helper.cercaOggetti();
    } catch (Exception e) {
      log.error(e);
    }
    return lista;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<VPlTIstanzeDocumenti> getListaDatiDocumenti(String codiceHermes) {
    List<VPlTIstanzeDocumenti> toRet = new ArrayList<>();
    try {
      PonMetroBusinessHelper helper =
          buildHelperDocumenti(
              "-".equals(codiceHermes) || "-".equalsIgnoreCase(codiceHermes) ? null : codiceHermes);
      toRet = helper.cercaOggetti();
    } catch (Exception e) {
      log.error(e);
    }
    return toRet;
  }

  private PonMetroBusinessHelper buildHelperMotivi(
      Boolean mettiliInAnd,
      Boolean wantEqual,
      List<String> serie,
      List<BigDecimal> articoli,
      String codiceHermes)
      throws BusinessException {
    log.debug("SupportoIstanzeVerbaliPLImpl buildHelperMotivi: mettiliInAnd:" + mettiliInAnd);
    log.debug("SupportoIstanzeVerbaliPLImpl buildHelperMotivi: wantEqual:" + wantEqual);
    log.debug("SupportoIstanzeVerbaliPLImpl buildHelperMotivi: serie:" + serie);
    log.debug("SupportoIstanzeVerbaliPLImpl buildHelperMotivi: articoli:" + articoli);
    log.debug("SupportoIstanzeVerbaliPLImpl buildHelperMotivi: codiceHermes:" + codiceHermes);
    if (serie == null && articoli == null && codiceHermes == null) {
      throw new BusinessException(
          "serie e articolo e codiceHermes non possono essere nulli tutti e 3");
    }
    VPlTIstanzeSerieDAO vPlTIstanzeSerieDAO =
        new VPlTIstanzeSerieDAO(mettiliInAnd, wantEqual, serie, articoli, codiceHermes);

    return new PonMetroBusinessHelper(vPlTIstanzeSerieDAO);
  }

  private PonMetroBusinessHelper buildHelperDocumenti(String codiceHermes)
      throws BusinessException {
    log.debug("SupportoIstanzeVerbaliPLImpl buildHelperDocumenti: codiceHermes:" + codiceHermes);
    if (codiceHermes == null) {
      throw new BusinessException("codiceHermes non puo' essere nullo");
    }
    VPlTIstanzeDocumenti vPlTIstanzeDocumenti = new VPlTIstanzeDocumenti();
    vPlTIstanzeDocumenti.setCodiceHermes(codiceHermes);

    VPlTIstanzeDocumentiDAO vPlTIstanzeDocumentiDAO =
        new VPlTIstanzeDocumentiDAO(vPlTIstanzeDocumenti);

    return new PonMetroBusinessHelper(vPlTIstanzeDocumentiDAO);
  }
}
