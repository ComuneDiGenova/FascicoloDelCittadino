package it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazione;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequest;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.PrestazioneDaErogareEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.StatodomandaPrestazioneEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazione;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneDichiarazioneRequest;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatore;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreRequest;
import it.liguriadigitale.ponmetro.inps.modi.model.Identity;
import it.liguriadigitale.ponmetro.inps.modi.model.Indicatore;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.inps.modi.model.Ordinario;
import it.liguriadigitale.ponmetro.inps.modi.model.RicercaCF;
import it.liguriadigitale.ponmetro.inps.modi.model.RichiestaConsultazioneAttestazione;
import it.liguriadigitale.ponmetro.inps.modi.model.RichiestaConsultazioneAttestazioneIdentity;
import it.liguriadigitale.ponmetro.inps.modi.model.RichiestaConsultazioneDichiarazione;
import it.liguriadigitale.ponmetro.inps.modi.model.RichiestaConsultazioneIndicatore;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class InpsModiHelper {

  private static Log log = LogFactory.getLog(InpsModiHelper.class);

  public static ConsultazioneIndicatoreCFBody createConsultazioneIndicatoreCFBody(
      String codiceFiscale,
      PrestazioneDaErogareEnum prestazioneDaErogare,
      StatodomandaPrestazioneEnum statoPrestazioneDaErogare,
      String tipoIndicatore) {

    ConsultazioneIndicatoreCFBody consultazioneIndicatoreCFBody =
        new ConsultazioneIndicatoreCFBody();
    RichiestaConsultazioneIndicatore richiestaConsultazioneIndicatore =
        new RichiestaConsultazioneIndicatore();

    ConsultazioneIndicatore consultazioneIndicatore = new ConsultazioneIndicatore();

    ConsultazioneIndicatoreRequest consultazioneIndicatoreRequest =
        new ConsultazioneIndicatoreRequest();

    RicercaCF ricercaCF = new RicercaCF();
    ricercaCF.setCodiceFiscale(codiceFiscale);
    ricercaCF.setDataValidita(LocalDate.now());

    ricercaCF.setPrestazioneDaErogare(prestazioneDaErogare.toString());
    ricercaCF.setProtocolloDomandaEnteErogatore("Prot");
    ricercaCF.setStatodomandaPrestazione(statoPrestazioneDaErogare.toString());

    consultazioneIndicatoreRequest.setRicercaCF(ricercaCF);
    consultazioneIndicatoreRequest.tipoIndicatore(tipoIndicatore);

    consultazioneIndicatore.setRequest(consultazioneIndicatoreRequest);

    richiestaConsultazioneIndicatore.setConsultazioneIndicatore(consultazioneIndicatore);

    Identity identity = new Identity();

    identity.setUserId(BaseServiceImpl.INPS_IDENTITY_USER_ID);
    identity.setCodiceUfficio(BaseServiceImpl.INPS_CODICE_UFFICIO);

    richiestaConsultazioneIndicatore.setIdentity(identity);

    consultazioneIndicatoreCFBody.setRichiesta(richiestaConsultazioneIndicatore);

    return consultazioneIndicatoreCFBody;
  }

  public static ConsultazioneAttestazioneCFBody createConsultazioneAttestazioneCFBody(
      Utente utente,
      PrestazioneDaErogareEnum prestazioneDaErogare,
      StatodomandaPrestazioneEnum statoPrestazioneDaErogare,
      LocalDate dataValidata,
      String protocollo) {

    ConsultazioneAttestazioneCFBody consultazioneAttestazioneCFBody =
        new ConsultazioneAttestazioneCFBody();
    RichiestaConsultazioneAttestazione richiestaConsultazioneAttestazione =
        new RichiestaConsultazioneAttestazione();

    ConsultazioneAttestazione consultazioneAttestazione = new ConsultazioneAttestazione();

    ConsultazioneAttestazioneRequest consultazioneAttestazioneRequest =
        new ConsultazioneAttestazioneRequest();

    ConsultazioneAttestazioneRequestRicercaCF ricercaCF =
        new ConsultazioneAttestazioneRequestRicercaCF();
    ricercaCF.setCodiceFiscale(utente.getCodiceFiscaleOperatore());
    ricercaCF.setDataValidita(LocalDateUtil.getDataFormatoEuropeoTariEngMunicipia(dataValidata));

    ricercaCF.setPrestazioneDaErogare(prestazioneDaErogare);
    ricercaCF.setProtocolloDomandaEnteErogatore(protocollo);
    ricercaCF.setStatodomandaPrestazione(statoPrestazioneDaErogare);

    consultazioneAttestazioneRequest.setRicercaCF(ricercaCF);

    consultazioneAttestazione.setRequest(consultazioneAttestazioneRequest);

    richiestaConsultazioneAttestazione.setConsultazioneAttestazione(consultazioneAttestazione);

    RichiestaConsultazioneAttestazioneIdentity identity =
        new RichiestaConsultazioneAttestazioneIdentity();

    identity.setUserId(BaseServiceImpl.INPS_IDENTITY_USER_ID);
    identity.setCodiceUfficio(BaseServiceImpl.INPS_CODICE_UFFICIO);

    richiestaConsultazioneAttestazione.setIdentity(identity);

    consultazioneAttestazioneCFBody.setRichiesta(richiestaConsultazioneAttestazione);

    return consultazioneAttestazioneCFBody;
  }

  public static ConsultazioneDichiarazioneCFBody createConsultazioneDichiarazioneCFBody(
      String codiceFiscale,
      PrestazioneDaErogareEnum prestazioneDaErogare,
      StatodomandaPrestazioneEnum statoPrestazioneDaErogare,
      LocalDate dataPresentazione,
      String protocolloDsu) {

    log.debug("[InpsModiHelper] createConsultazioneDichiarazioneCFBody");

    ConsultazioneDichiarazioneCFBody consultazioneDichiarazioneCFBody =
        new ConsultazioneDichiarazioneCFBody();
    RichiestaConsultazioneDichiarazione richiestaConsultazioneDichiarazione =
        new RichiestaConsultazioneDichiarazione();

    ConsultazioneDichiarazione consultazioneDichiarazione = new ConsultazioneDichiarazione();

    ConsultazioneDichiarazioneRequest consultazioneDichiarazioneRequest =
        new ConsultazioneDichiarazioneRequest();

    RicercaCF ricercaCF = new RicercaCF();
    ricercaCF.setCodiceFiscale(codiceFiscale);
    ricercaCF.setDataValidita(dataPresentazione);

    ricercaCF.setPrestazioneDaErogare(prestazioneDaErogare.toString());
    ricercaCF.setProtocolloDomandaEnteErogatore(protocolloDsu);
    ricercaCF.setStatodomandaPrestazione(statoPrestazioneDaErogare.toString());

    consultazioneDichiarazioneRequest.setRicercaCF(ricercaCF);

    consultazioneDichiarazione.setRequest(consultazioneDichiarazioneRequest);

    richiestaConsultazioneDichiarazione.setConsultazioneDichiarazione(consultazioneDichiarazione);

    Identity identity = new Identity();

    identity.setUserId(BaseServiceImpl.INPS_IDENTITY_USER_ID);
    identity.setCodiceUfficio(BaseServiceImpl.INPS_CODICE_UFFICIO);

    richiestaConsultazioneDichiarazione.setIdentity(identity);

    consultazioneDichiarazioneCFBody.setRichiesta(richiestaConsultazioneDichiarazione);

    return consultazioneDichiarazioneCFBody;
  }

  public static ConsultazioneAttestazioneCF200 getAttestazioneISEE(
      Utente utente,
      PrestazioneDaErogareEnum prestazioneDaErogare,
      StatodomandaPrestazioneEnum statoPrestazioneDaErogare,
      String tipoIndicatore)
      throws BusinessException {

    ConsultazioneIndicatoreCFBody consultazioneIndicatoreCFBody =
        createConsultazioneIndicatoreCFBody(
            utente.getCodiceFiscaleOperatore(),
            prestazioneDaErogare,
            statoPrestazioneDaErogare,
            tipoIndicatore);

    ConsultazioneIndicatoreCF200 consultazioneIndicatoreCF200 =
        ServiceLocator.getInstance()
            .getServiziINPSModi()
            .consultazioneIndicatoreCF(consultazioneIndicatoreCFBody);

    if (LabelFdCUtil.checkIfNull(consultazioneIndicatoreCF200)) {
      return null;
    }

    Indicatore indicatore = getIndicatoreIsee(consultazioneIndicatoreCF200);

    if (LabelFdCUtil.checkIfNull(indicatore)) {
      return null;
    }

    ConsultazioneAttestazioneCFBody consultazioneAttestazioneCFBody =
        createConsultazioneAttestazioneCFBody(
            utente,
            prestazioneDaErogare,
            statoPrestazioneDaErogare,
            indicatore.getRicercaCF().getDataValidita(),
            indicatore.getProtocolloDSU());

    return ServiceLocator.getInstance()
        .getServiziINPSModi()
        .consultazioneAttestazioneCF(consultazioneAttestazioneCFBody);
  }

  public static ConsultazioneAttestazioneCFBody createConsultazioneAttestazioneCFBodyConCF(
      String codiceFiscale,
      PrestazioneDaErogareEnum prestazioneDaErogare,
      StatodomandaPrestazioneEnum statoPrestazioneDaErogare,
      LocalDate dataValidata,
      String protocollo) {

    ConsultazioneAttestazioneCFBody consultazioneAttestazioneCFBody =
        new ConsultazioneAttestazioneCFBody();
    RichiestaConsultazioneAttestazione richiestaConsultazioneAttestazione =
        new RichiestaConsultazioneAttestazione();

    ConsultazioneAttestazione consultazioneAttestazione = new ConsultazioneAttestazione();

    ConsultazioneAttestazioneRequest consultazioneAttestazioneRequest =
        new ConsultazioneAttestazioneRequest();

    ConsultazioneAttestazioneRequestRicercaCF ricercaCF =
        new ConsultazioneAttestazioneRequestRicercaCF();
    ricercaCF.setCodiceFiscale(codiceFiscale);
    ricercaCF.setDataValidita(LocalDateUtil.getDataFormatoEuropeoTariEngMunicipia(dataValidata));

    ricercaCF.setPrestazioneDaErogare(prestazioneDaErogare);
    ricercaCF.setProtocolloDomandaEnteErogatore(protocollo);
    ricercaCF.setStatodomandaPrestazione(statoPrestazioneDaErogare);

    consultazioneAttestazioneRequest.setRicercaCF(ricercaCF);

    consultazioneAttestazione.setRequest(consultazioneAttestazioneRequest);

    richiestaConsultazioneAttestazione.setConsultazioneAttestazione(consultazioneAttestazione);

    RichiestaConsultazioneAttestazioneIdentity identity =
        new RichiestaConsultazioneAttestazioneIdentity();

    identity.setUserId(BaseServiceImpl.INPS_IDENTITY_USER_ID);
    identity.setCodiceUfficio(BaseServiceImpl.INPS_CODICE_UFFICIO);

    richiestaConsultazioneAttestazione.setIdentity(identity);

    consultazioneAttestazioneCFBody.setRichiesta(richiestaConsultazioneAttestazione);

    return consultazioneAttestazioneCFBody;
  }

  public static ConsultazioneAttestazioneCF200 getAttestazioneISEEConCF(
      String codiceFiscale,
      PrestazioneDaErogareEnum prestazioneDaErogare,
      StatodomandaPrestazioneEnum statoPrestazioneDaErogare,
      String tipoIndicatore,
      String protocolloDSU,
      LocalDate dataValidita)
      throws BusinessException {

    ConsultazioneAttestazioneCFBody consultazioneAttestazioneCFBody =
        createConsultazioneAttestazioneCFBodyConCF(
            codiceFiscale,
            prestazioneDaErogare,
            statoPrestazioneDaErogare,
            dataValidita,
            protocolloDSU);

    return ServiceLocator.getInstance()
        .getServiziINPSModi()
        .consultazioneAttestazioneCF(consultazioneAttestazioneCFBody);
  }

  public static Indicatore getIndicatoreIsee(
      ConsultazioneIndicatoreCF200 consultazioneIndicatoreCF200) {

    if (LabelFdCUtil.checkIfNull(
        consultazioneIndicatoreCF200.getConsultazioneIndicatoreResponse())) {
      return null;
    }

    if (LabelFdCUtil.checkIfNull(
        consultazioneIndicatoreCF200
            .getConsultazioneIndicatoreResponse()
            .getConsultazioneIndicatoreResult())) {
      return null;
    }

    if (LabelFdCUtil.checkIfNull(
        consultazioneIndicatoreCF200
            .getConsultazioneIndicatoreResponse()
            .getConsultazioneIndicatoreResult()
            .getXmlEsitoIndicatoreDecoded())) {
      return null;
    }

    return consultazioneIndicatoreCF200
        .getConsultazioneIndicatoreResponse()
        .getConsultazioneIndicatoreResult()
        .getXmlEsitoIndicatoreDecoded()
        .getIndicatore();
  }

  public static Ordinario getOrdinarioByAttestazioneIsee(
      ConsultazioneAttestazioneCF200 attestazioniISEE) {

    log.debug("[InpsModiHelper] getOrdinarioByAttestazioneIsee: ");

    if (LabelFdCUtil.checkIfNull(attestazioniISEE)
        || LabelFdCUtil.checkIfNull(attestazioniISEE.getConsultazioneAttestazioneResponse())
        || LabelFdCUtil.checkIfNull(
            attestazioniISEE
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult())
        || LabelFdCUtil.checkIfNull(
            attestazioniISEE
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded())
        || LabelFdCUtil.checkIfNull(
            attestazioniISEE
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione())
        || LabelFdCUtil.checkIfNull(
            attestazioniISEE
                .getConsultazioneAttestazioneResponse()
                .getConsultazioneAttestazioneResult()
                .getXmlEsitoAttestazioneDecoded()
                .getEsitoAttestazione()
                .getAttestazione())) {
      return null;
    }

    return attestazioniISEE
        .getConsultazioneAttestazioneResponse()
        .getConsultazioneAttestazioneResult()
        .getXmlEsitoAttestazioneDecoded()
        .getEsitoAttestazione()
        .getAttestazione()
        .getOrdinario();
  }

  public static ConsultazioneDichiarazioneCF200 getDichiarazioneISEE(
      String codiceFiscale,
      PrestazioneDaErogareEnum prestazioneDaErogare,
      StatodomandaPrestazioneEnum statoPrestazioneDaErogare,
      String tipoIndicatore)
      throws BusinessException {

    log.debug("[InpsModiHelper] getDichiarazioneISEE");

    ConsultazioneIndicatoreCFBody consultazioneIndicatoreCFBody =
        createConsultazioneIndicatoreCFBody(
            codiceFiscale, prestazioneDaErogare, statoPrestazioneDaErogare, tipoIndicatore);

    ConsultazioneIndicatoreCF200 consultazioneIndicatoreCF200 =
        ServiceLocator.getInstance()
            .getServiziINPSModi()
            .consultazioneIndicatoreCF(consultazioneIndicatoreCFBody);

    if (LabelFdCUtil.checkIfNull(consultazioneIndicatoreCF200)) {
      return null;
    }

    Indicatore indicatore = getIndicatoreIsee(consultazioneIndicatoreCF200);

    if (LabelFdCUtil.checkIfNull(indicatore)) {
      return null;
    }

    ConsultazioneDichiarazioneCFBody consultazioneDichiarazioneCFBody =
        createConsultazioneDichiarazioneCFBody(
            codiceFiscale,
            prestazioneDaErogare,
            statoPrestazioneDaErogare,
            indicatore.getRicercaCF().getDataValidita(),
            indicatore.getProtocolloDSU());

    log.debug("consultazioneDichiarazioneCFBody body = " + consultazioneDichiarazioneCFBody);

    return ServiceLocator.getInstance()
        .getServiziINPSModi()
        .consultazioneDichiarazioneCF(consultazioneDichiarazioneCFBody);
  }

  public static boolean checkNucleoIseeUgualeNucleoAnagrafico(
      List<NucleoFamiliareComponenteNucleoInner> listaNucleoIsee,
      List<Residente> listaComponentiNucleoInAnagrafe) {
    log.debug("[InpsModiHelper] checkNucleoIseeUgualeNucleoAnagrafico");

    boolean nucleiUguali = false;

    List<String> listaCodiciFiscaliIsee = new ArrayList<String>();
    List<String> listaCodiciFiscaliNucleoAllargato = new ArrayList<String>();

    if (LabelFdCUtil.checkIfNotNull(listaNucleoIsee)
        && LabelFdCUtil.checkIfNotNull(listaComponentiNucleoInAnagrafe)) {

      listaCodiciFiscaliIsee =
          listaNucleoIsee.stream()
              .map(NucleoFamiliareComponenteNucleoInner::getCodiceFiscale)
              .sorted(Comparator.nullsLast(Comparator.naturalOrder()))
              .collect(Collectors.toList());

      log.debug("CP listaCodiciFiscaliIsee = " + listaCodiciFiscaliIsee);

      listaCodiciFiscaliNucleoAllargato =
          listaComponentiNucleoInAnagrafe.stream()
              .map(Residente::getCpvTaxCode)
              .sorted(Comparator.nullsLast(Comparator.naturalOrder()))
              .collect(Collectors.toList());

      log.debug("CP listaCodiciFiscaliNucleoAllargato = " + listaCodiciFiscaliNucleoAllargato);

      nucleiUguali =
          CollectionUtils.isEqualCollection(
              listaCodiciFiscaliIsee, listaCodiciFiscaliNucleoAllargato);

      log.debug("CP nucleiUguali = " + nucleiUguali);
    }

    return nucleiUguali;
  }
}
