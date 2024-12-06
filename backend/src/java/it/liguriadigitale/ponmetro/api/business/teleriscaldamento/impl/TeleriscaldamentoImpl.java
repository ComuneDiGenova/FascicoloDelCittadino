package it.liguriadigitale.ponmetro.api.business.teleriscaldamento.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.teleriscaldamento.service.TeleriscaldamentoInterface;
import it.liguriadigitale.ponmetro.api.integration.dao.teleriscaldamento.FdcProtocolloSeqDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.teleriscaldamento.TrClientiDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.teleriscaldamento.TrContrattiDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.teleriscaldamento.TrDomandeDAO;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.ClientiBuilder;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.ContrattiBuilder;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.DomandaTeleriscaldamentoBuilder;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.TrClienti;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.TrContratti;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.TrDomande;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.TrDomandeBuilder;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Cliente;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.Contratto;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.DatiVerificatiEnum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.EsitoVerificaEnum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.IndicatoreIsee12Enum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.IndicatoreIsee20Enum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.IndicatoreIsee25Enum;
import it.liguriadigitale.ponmetro.teleriscaldamento.model.DomandaTeleriscaldamento.StatoPraticaEnum;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TeleriscaldamentoImpl implements TeleriscaldamentoInterface {

  private static Log log = LogFactory.getLog(TeleriscaldamentoImpl.class);

  @Override
  public List<DomandaTeleriscaldamento> getDatiCittadino(String codiceFiscale)
      throws BusinessException {

    TrDomande domanda =
        TrDomandeBuilder.getInstance()
            .addCfRichiedente(codiceFiscale)
            .addIdStatoDomanda(1L)
            .build();
    TrDomandeDAO dao = new TrDomandeDAO(domanda);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    @SuppressWarnings("unchecked")
    List<TrDomande> lista = helper.cercaOggetti();
    List<DomandaTeleriscaldamento> listaConvertita = convertiListaI(lista);
    return listaConvertita;
  }

  private List<DomandaTeleriscaldamento> convertiListaI(List<TrDomande> lista) {

    List<DomandaTeleriscaldamento> nuovaLista = new ArrayList<>();

    for (TrDomande domandaDB : lista) {
      DomandaTeleriscaldamento domandaRest =
          DomandaTeleriscaldamentoBuilder.getInstance()
              .addCfRichiedente(domandaDB.getCfRichiedente())
              .addCapAmmCondominio(domandaDB.getCapAmmCondominio())
              .addCellulare(domandaDB.getCellulare())
              .addCfIntestarioContratto(domandaDB.getCfIntestatarioContratto())
              .addCivicoAmmCondominio(domandaDB.getCivicoAmmCondominio())
              .addCognomeRichiedente(domandaDB.getCognomeRichiedente())
              .addComuneAmmCondominio(domandaDB.getComuneAmmCondominio())
              .addComuneFornitura(domandaDB.getComuneFornitura())
              .addConsensoInf(domandaDB.getConsensoInf())
              .addConsensoPrivacy(domandaDB.getConsensoPrivacy())
              .addDataInvioIREN(getDataInvioIrenLocalDate(domandaDB.getDataConsegnaIren()))
              .addDataProtocollo(domandaDB.getDataProtocollo())
              .addDatiVerificati(getEnumDatiVerificati(domandaDB.getDatiVerificati()))
              .addEmail(domandaDB.getEmail())
              .addEmailAmmCondominio(domandaDB.getEmailAmmCondominio())
              .addEsitoVerifica(getEnumEsitoVerifica(domandaDB.getEsitoVerifica()))
              .addIdentificativo(domandaDB.getId())
              .addIdStato(getIdStatoBigDecimal(domandaDB.getIdStatoDomanda()))
              .addIndicatoreIsee12(getEnumIsee12(domandaDB.getIndicatoreIsee12()))
              .addIndicatoreIsee20(getEnumIsee20(domandaDB.getIndicatoreIsee20()))
              .addIndicatoreIsee20(getEnumIsee25(domandaDB.getIndicatoreIsee25()))
              .addNomeRichiedente(domandaDB.getNomeRichiedente())
              .addNominativoAmmCond(domandaDB.getNominativoAammCond())
              .addNominativoApt(domandaDB.getNominativoApt())
              .addNominativoContratto(domandaDB.getNominativoContratto())
              .addNumCivicoFornitura(domandaDB.getNumCivicoFornitura())
              .addNumContratto(domandaDB.getNumContratto())
              .addNumeroCliente(domandaDB.getNumeroCliente())
              .addNumNucleoFamiliare(
                  getIntNumeroNucleoFamigliare(domandaDB.getNumNucleoFamiliare()))
              .addNumProtocollo(domandaDB.getNumProtocollo())
              .addpIvaContratto(domandaDB.getPivaContratto())
              .addProvAmmCondominio(domandaDB.getProvAmmCondominio())
              .addProvinciaFornitura(domandaDB.getProvinciaFornitura())
              .addStatoPratica(getEnumStatoPratica(domandaDB.getStatoDomanda()))
              .addTelAmmCondominio(domandaDB.getTelAmmCondominio())
              .addTelefono(domandaDB.getTelefono())
              .addTipoUtenza(domandaDB.getTipoUtenza())
              .addViaAmmCondominio(domandaDB.getViaAmmCondominio())
              .addViaFornitura(domandaDB.getViaFornitura())
              .addCapFornitura(domandaDB.getCapFornitura())
              .addAnnoDomanda(domandaDB.getAnnoDomanda())
              .build();

      nuovaLista.add(domandaRest);
    }
    return nuovaLista;
  }

  @Override
  public BigDecimal getProtocollo() throws BusinessException {

    FdcProtocolloSeqDAO seq = new FdcProtocolloSeqDAO();
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(seq);
    Long sequence = (Long) helper.cercaOggetto();
    return BigDecimal.valueOf(sequence);
  }

  @Override
  public void setDatiCittadino(DomandaTeleriscaldamento domanda) throws BusinessException {

    TrDomande trDomanda =
        TrDomandeBuilder.getInstance()
            .addCfRichiedente(domanda.getCfRichiedente())
            .addCapAmmCondominio(domanda.getCapAmmCondominio())
            .addCelAmmCondominio(domanda.getCellAmmCondominio())
            .addCellulare(domanda.getCellulare())
            .addCfIntestatarioContratto(domanda.getCfIntestarioContratto())
            .addCivicoAmmCondominio(domanda.getCivicoAmmCondominio())
            .addCognomeRichiedente(domanda.getCognomeRichiedente())
            .addComuneAmmCondominio(domanda.getComuneAmmCondominio())
            .addComuneFornitura(domanda.getComuneFornitura())
            .addConsensoInf(domanda.getConsensoInf())
            .addConsensoPrivacy(domanda.getConsensoPrivacy())
            .addDataConsegnaIren(convertiLocalDateToLocalDateTime(domanda.getDataInvioIREN()))
            .addDataProtocollo(domanda.getDataProtocollo())
            .addDatiVerificati(domanda.getDatiVerificati())
            .addEmail(domanda.getEmail())
            .addEmailAmmCondominio(domanda.getEmailAmmCondominio())
            .addEsitoVerifica(domanda.getEsitoVerifica())
            .addId(domanda.getIdentificativo())
            .addIdStatoDomanda(domanda.getIdStato().longValue())
            .addIndicatoreIsee12(domanda.getIndicatoreIsee12())
            .addIndicatoreIsee20(domanda.getIndicatoreIsee20())
            .addNomeRichiedente(domanda.getNomeRichiedente())
            .addNominativoAammCond(domanda.getNominativoAmmCond())
            .addNominativoApt(domanda.getNominativoApt())
            .addNominativoContratto(domanda.getNominativoContratto())
            .addNumCivicoFornitura(domanda.getNumCivicoFornitura())
            .addNumContratto(domanda.getNumContratto())
            .addNumeroCliente(domanda.getNumeroCliente())
            .addNumNucleoFamiliare(String.valueOf(domanda.getNumNucleoFamiliare()))
            .addNumProtocollo(domanda.getNumProtocollo())
            .addPivaContratto(domanda.getpIvaContratto())
            .addProvAmmCondominio(domanda.getProvAmmCondominio())
            .addProvinciaFornitura(domanda.getProvinciaFornitura())
            .addStatoDomanda(domanda.getStatoPratica())
            .addTelAmmCondominio(domanda.getTelAmmCondominio())
            .addTelefono(domanda.getTelefono())
            .addTipoUtenza(domanda.getTipoUtenza())
            .addViaAmmCondominio(domanda.getViaAmmCondominio())
            .addViaFornitura(domanda.getViaFornitura())
            .addCapFornitura(domanda.getCapFornitura())
            .addAnnoDomanda(domanda.getAnnoDomanda())
            .addIndicatoreIsee25(domanda.getIndicatoreIsee25())
            .build();
    TrDomandeDAO dao = new TrDomandeDAO(trDomanda);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    helper.inserisciOggetto();
  }

  private LocalDateTime convertiLocalDateToLocalDateTime(LocalDate localDate) {

    if (localDate != null) {
      return localDate.atStartOfDay();
    } else {
      return null;
    }
  }

  private LocalDate getDataInvioIrenLocalDate(LocalDateTime dataConsegnaIren) {
    if (dataConsegnaIren != null) {
      return dataConsegnaIren.toLocalDate();
    }
    return null;
  }

  private StatoPraticaEnum getEnumStatoPratica(String statoDomanda) {

    String upperCase = StringUtils.upperCase(statoDomanda);
    String noSpazi = StringUtils.replace(upperCase, " ", "_");
    try {

      return StatoPraticaEnum.valueOf(noSpazi);
    } catch (IllegalArgumentException | NullPointerException e) {
      log.debug("StatoPraticaEnum non decodificabile, statoDomanda =" + noSpazi, e);
      return null;
    }
  }

  private Integer getIntNumeroNucleoFamigliare(String numNucleoFamiliare) {
    return Integer.getInteger(numNucleoFamiliare);
  }

  private IndicatoreIsee20Enum getEnumIsee20(String indicatoreIsee20) {
    String upperCase = StringUtils.upperCase(indicatoreIsee20);
    try {

      return IndicatoreIsee20Enum.valueOf(upperCase);
    } catch (IllegalArgumentException | NullPointerException e) {
      log.debug(
          "IndicatoreIsee20Enum non decodificabile, indicatoreIsee20 = <" + upperCase + ">", e);
      return null;
    }
  }

  private IndicatoreIsee12Enum getEnumIsee12(String indicatoreIsee12) {
    String upperCase = StringUtils.upperCase(indicatoreIsee12);
    try {

      return IndicatoreIsee12Enum.valueOf(upperCase);
    } catch (IllegalArgumentException | NullPointerException e) {
      log.debug(
          "IndicatoreIsee12Enum non decodificabile, indicatoreIsee12 = <" + upperCase + ">", e);
      return null;
    }
  }

  private IndicatoreIsee25Enum getEnumIsee25(String indicatoreIsee25) {
    String upperCase = StringUtils.upperCase(indicatoreIsee25);
    try {

      return IndicatoreIsee25Enum.valueOf(upperCase);
    } catch (IllegalArgumentException | NullPointerException e) {
      log.debug(
          "IndicatoreIsee25Enum non decodificabile, indicatoreIsee25 = <" + upperCase + ">", e);
      return null;
    }
  }

  private BigDecimal getIdStatoBigDecimal(Long idStatoDomanda) {
    return BigDecimal.valueOf(idStatoDomanda);
  }

  private EsitoVerificaEnum getEnumEsitoVerifica(String esitoVerifica) {
    String upperCase = StringUtils.upperCase(esitoVerifica);
    try {

      return EsitoVerificaEnum.valueOf(upperCase);
    } catch (IllegalArgumentException | NullPointerException e) {
      log.debug("EsitoVerificaEnum non decodificabile, esitoVerifica =" + upperCase, e);
      return null;
    }
  }

  private DatiVerificatiEnum getEnumDatiVerificati(String datiVerificati) {
    String upperCase = StringUtils.upperCase(datiVerificati);
    try {

      return DatiVerificatiEnum.valueOf(upperCase);
    } catch (IllegalArgumentException | NullPointerException e) {
      log.debug("DatiVerificatiEnum non decodificabile, datiVerificati =" + upperCase, e);
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Contratto> getListaContratti() throws BusinessException {
    TrContratti contratti = new TrContratti();
    TrContrattiDAO dao = new TrContrattiDAO(contratti);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<TrContratti> lista = helper.cercaOggetti();
    List<Contratto> listaConvertita = convertiListaContratti(lista);
    return listaConvertita;
  }

  private List<Contratto> convertiListaContratti(List<TrContratti> lista) {
    List<Contratto> nuovaLista = new ArrayList<Contratto>();

    if (lista != null) {
      for (TrContratti elem : lista) {
        Contratto contratto =
            ContrattiBuilder.getInstance()
                .addNumeroCliente(elem.getNumeroCliente())
                .addNumeroContratto(elem.getNumContratto())
                .build();

        nuovaLista.add(contratto);
      }
    }

    return nuovaLista;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Cliente> getListaClienti() throws BusinessException {
    TrClienti clienti = new TrClienti();
    TrClientiDAO dao = new TrClientiDAO(clienti);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    List<TrClienti> lista = helper.cercaOggetti();
    List<Cliente> listaConvertita = convertiListaCliente(lista);
    return listaConvertita;
  }

  private List<Cliente> convertiListaCliente(List<TrClienti> lista) {
    List<Cliente> nuovaLista = new ArrayList<Cliente>();

    if (lista != null) {
      for (TrClienti elem : lista) {
        Cliente cliente =
            ClientiBuilder.getInstance().addNumeroCliente(elem.getNumeroCliente()).build();

        nuovaLista.add(cliente);
      }
    }

    return nuovaLista;
  }

  @Override
  public TrContratti getContratto(String numeroContratto) throws BusinessException {
    TrContratti contratti = new TrContratti();
    contratti.setNumContratto(numeroContratto);
    TrContrattiDAO dao = new TrContrattiDAO(contratti);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    TrContratti risultato = (TrContratti) helper.cercaOggetto();
    return risultato;
  }

  @Override
  public TrClienti getCliente(String numeroCliente) throws BusinessException {
    TrClienti clienti = new TrClienti();
    clienti.setNumeroCliente(numeroCliente);
    TrClientiDAO dao = new TrClientiDAO(clienti);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    TrClienti risultato = (TrClienti) helper.cercaOggetto();
    return risultato;
  }
}
