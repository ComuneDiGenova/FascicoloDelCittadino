package it.liguriadigitale.ponmetro.api.business.anonimizza.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.anonim.model.AnonimoData;
import it.liguriadigitale.ponmetro.api.business.anonimizza.service.AnonimizzaInterface;
import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.api.business.common.datasource.PersonBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.common.datasource.PersonDatasourceTransactionManager;
import it.liguriadigitale.ponmetro.api.business.livello1.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.api.integration.dao.CpvAnonimDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CpvPersonDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.query.CpvAnonimNonCancellatiDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.seq.CvpPersonSequenceDAO;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.db.CpvAnonim;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.db.CpvPerson;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.db.builder.CpvAnonimBuilder;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.db.builder.CpvPersonBuilder;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.dto.UserAnonymous;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.dto.builder.UserAnonymousBuilder;
import it.liguriadigitale.ponmetro.api.pojo.common.builder.EsitoResponseBuilder;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AnonimizzaImpl implements AnonimizzaInterface {

  private static Log log = LogFactory.getLog(AnonimizzaImpl.class);

  @Override
  public UserAnonymous gestioneNonResidentiConCambioResidenza(AnonimoData anonim)
      throws BusinessException {

    log.debug("[AnonimizzaImpl] gestioneNonResidentiConCambioResidenza");
    boolean isResidente = false;
    Residente residente = null;
    if (isStringaLunga16Caratteri(anonim.getCodiceFiscale())) {
      residente = getResidenteComuneGenova(anonim.getCodiceFiscale());
      isResidente = isCittadinoResidenteComuneGe(residente);
    }

    if (isResidente) { // residenti
      Long idPerson = controllaCodiceFiscaleSuCpvPerson(anonim.getCodiceFiscale());
      if (idPerson > 0L) {
        return returnResidente(idPerson);
      } else {
        Long idAnonimo = controllaCodiceFiscaleSuCpvAnonim(anonim.getCodiceFiscale());
        if (idAnonimo > 0L) {
          return returnResidenteEraNonResidente(idAnonimo, anonim, residente);
        } else {
          return returnNuovoResidente(anonim, residente);
        }
      }
    } else { // non residenti
      Long idAnonimo = controllaCodiceFiscaleSuCpvAnonim(anonim.getCodiceFiscale());
      if (idAnonimo > 0L) {
        return returnNonResidente(idAnonimo);
      } else {
        Long idPerson = controllaCodiceFiscaleSuCpvPerson(anonim.getCodiceFiscale());
        if (idPerson > 0L) {
          return returnNonResidenteEraResidente(idPerson, anonim);
        } else {
          return returnNuovoNonResidente(anonim);
        }
      }
    }
  }

  private static boolean isStringaLunga16Caratteri(String codiceFiscale) {
    return StringUtils.length(codiceFiscale) == 16;
  }

  private UserAnonymous returnNuovoNonResidente(AnonimoData anonim) throws BusinessException {
    Long idAnonimo;
    String mex = "Utente non residente e non ancora registrato";
    log.debug(mex);
    idAnonimo = inserisciCittadinoInAnonim(anonim);
    return new UserAnonymousBuilder()
        .setIdAnonimo(idAnonimo)
        .setIsResidente(false)
        .setResponse(new EsitoResponseBuilder().setEsito(true).setDescrizione(mex).build())
        .build();
  }

  private UserAnonymous returnNonResidenteEraResidente(Long idPerson, AnonimoData anonim)
      throws BusinessException {
    String mex = "Utente non residente, ma precedentemente registrato come residente";
    log.debug("[AnonimizzaImpl]" + mex + ": " + idPerson);
    spostaIdDaPersonAAnonim(idPerson, anonim);
    return new UserAnonymousBuilder()
        .setIdAnonimo(idPerson)
        .setIsResidente(false)
        .setResponse(new EsitoResponseBuilder().setEsito(true).setDescrizione(mex).build())
        .build();
  }

  private UserAnonymous returnNonResidente(Long idAnonimo) {
    String mex = "Utente registrato come non residente";
    log.debug("[AnonimizzaImpl]" + mex + ": " + idAnonimo);
    return new UserAnonymousBuilder()
        .setIdAnonimo(idAnonimo)
        .setIsResidente(false)
        .setResponse(new EsitoResponseBuilder().setEsito(true).setDescrizione(mex).build())
        .build();
  }

  private UserAnonymous returnNuovoResidente(AnonimoData anonim, Residente residente)
      throws BusinessException {

    log.debug("Utente residente ma non ancora registrato");
    Long idPerson = inserisciCittadinoInPerson(anonim, residente);
    return new UserAnonymousBuilder()
        .setIdAnonimo(idPerson)
        .setIsResidente(true)
        .setResponse(new EsitoResponseBuilder().setEsito(true).build())
        .build();
  }

  private UserAnonymous returnResidenteEraNonResidente(
      Long idAnonimo, AnonimoData anonim, Residente residente) throws BusinessException {
    String mex = "Utente residente, ma precedentemente registrato come non residente";
    log.debug("[AnonimizzaImpl]" + mex + ": " + idAnonimo);
    spostaIdDaAnonimAPerson(idAnonimo, anonim, residente);
    return new UserAnonymousBuilder()
        .setIdAnonimo(idAnonimo)
        .setIsResidente(true)
        .setResponse(new EsitoResponseBuilder().setEsito(true).setDescrizione(mex).build())
        .build();
  }

  private UserAnonymous returnResidente(Long idPerson) {
    log.debug("[AnonimizzaImpl]" + "Utente residente e registrato:" + idPerson);
    return new UserAnonymousBuilder()
        .setIdAnonimo(idPerson)
        .setIsResidente(true)
        .setResponse(new EsitoResponseBuilder().setEsito(true).build())
        .build();
  }

  private Long inserisciCittadinoInAnonim(AnonimoData anonim) throws BusinessException {
    log.debug("[AnonimizzaImpl] inserisciCittadinoInAnonim");
    CpvAnonim nonResidente = convertiInputRest(anonim);
    final CpvAnonim cpvanonim =
        new CpvAnonimBuilder()
            .setCodiceFiscale(nonResidente.getCodiceFiscale())
            .setCodiceBelfioreNascita(nonResidente.getCodiceBelfioreNascita())
            .setCognome(nonResidente.getCognome())
            .setNome(nonResidente.getNome())
            .setDataNascita(nonResidente.getDataNascita())
            .setSesso(nonResidente.getSesso())
            .setDataAgg(LocalDateTime.now())
            .setDataIns(LocalDateTime.now())
            .setIdStatoRec(1L)
            .setUtenteAgg(BaseServiceImpl.COD_APP)
            .setUtenteIns(BaseServiceImpl.COD_APP)
            .build();

    PersonDatasourceTransactionManager manager =
        new PersonDatasourceTransactionManager() {

          @Override
          protected void execute(Connection paramConnection) throws Exception {

            CvpPersonSequenceDAO daoSeq = new CvpPersonSequenceDAO();
            @SuppressWarnings("unchecked")
            List<Long> lista = daoSeq.retrieveWhere(paramConnection);
            if (!lista.isEmpty()) {
              cpvanonim.setPersonId(lista.get(0));
              log.debug("valore sequence:" + lista.get(0));
            }
            CpvAnonimDAO daoInsert = new CpvAnonimDAO(cpvanonim);
            log.debug("valori passati: " + cpvanonim);
            int nRecord = daoInsert.insertPrepared(paramConnection);
            log.debug("record inseriti: " + nRecord);
            paramConnection.commit();
          }
        };
    manager.executeTransaction();
    return cpvanonim.getPersonId();
  }

  private void spostaIdDaPersonAAnonim(Long idAnonimo, AnonimoData anonim)
      throws BusinessException {
    log.debug("[AnonimizzaImpl] spostaIdDaPersonAAnonim");
    CpvAnonim nonResidente = convertiInputRest(anonim);
    final CpvAnonim cpvanonim =
        new CpvAnonimBuilder()
            .setCodiceFiscale(nonResidente.getCodiceFiscale())
            .setCodiceBelfioreNascita(nonResidente.getCodiceBelfioreNascita())
            .setCognome(nonResidente.getCognome())
            .setNome(nonResidente.getNome())
            .setDataNascita(nonResidente.getDataNascita())
            .setSesso(nonResidente.getSesso())
            .setPersonId(idAnonimo)
            .setDataAgg(LocalDateTime.now())
            .setDataIns(LocalDateTime.now())
            .setIdStatoRec(1L)
            .setUtenteAgg(BaseServiceImpl.COD_APP)
            .setUtenteIns(BaseServiceImpl.COD_APP)
            .build();

    final CpvPerson cpvperson = new CpvPersonBuilder().setPersonId(idAnonimo).build();

    PersonDatasourceTransactionManager manager =
        new PersonDatasourceTransactionManager() {

          @Override
          protected void execute(Connection paramConnection) throws Exception {

            CpvPersonDAO daoPerson = new CpvPersonDAO(cpvperson);
            int nrecord = daoPerson.deleteByKey(paramConnection);
            log.debug("record cancellati: " + nrecord);
            CpvAnonimDAO daoInsert = new CpvAnonimDAO(cpvanonim);
            log.debug("valori passati: " + cpvanonim);
            int nRecord = daoInsert.insertPrepared(paramConnection);
            log.debug("record inseriti: " + nRecord);
            paramConnection.commit();
          }
        };
    manager.executeTransaction();
  }

  private void spostaIdDaAnonimAPerson(Long idAnonimo, AnonimoData anonim, Residente residente)
      throws BusinessException {
    log.debug("[AnonimizzaImpl] spostaIdDaAnonimAPerson");
    final CpvAnonim cpvanonim = new CpvAnonimBuilder().setPersonId(idAnonimo).build();

    final CpvPerson person =
        new CpvPersonBuilder()
            .setCodiceFiscale(anonim.getCodiceFiscale())
            .setPersonId(idAnonimo)
            .setDataAgg(LocalDateTime.now())
            .setDataIns(LocalDateTime.now())
            .setIdAnagrafica(residente.getCpvPersonID())
            .setIdStatoRec(1L)
            .setUtenteAgg(BaseServiceImpl.COD_APP)
            .setUtenteIns(BaseServiceImpl.COD_APP)
            .build();

    PersonDatasourceTransactionManager manager =
        new PersonDatasourceTransactionManager() {

          @Override
          protected void execute(Connection paramConnection) throws Exception {

            CpvAnonimDAO daoAnonim = new CpvAnonimDAO(cpvanonim);
            int nrecord = daoAnonim.deleteByKey(paramConnection);
            log.debug("record cancellati: " + nrecord);
            CpvPersonDAO daoInsert = new CpvPersonDAO(person);
            log.debug("valori passati: " + person);
            int nRecord = daoInsert.insertPrepared(paramConnection);
            log.debug("record inseriti: " + nRecord);
            paramConnection.commit();
          }
        };
    manager.executeTransaction();
  }

  private Long inserisciCittadinoInPerson(AnonimoData anonim, Residente residente)
      throws BusinessException {
    log.debug("[AnonimizzaImpl] inserisciCittadinoInPerson");
    final CpvPerson person =
        new CpvPersonBuilder()
            .setCodiceFiscale(anonim.getCodiceFiscale())
            .setDataAgg(LocalDateTime.now())
            .setDataIns(LocalDateTime.now())
            .setIdAnagrafica(residente.getCpvPersonID())
            .setIdStatoRec(1L)
            .setUtenteAgg(BaseServiceImpl.COD_APP)
            .setUtenteIns(BaseServiceImpl.COD_APP)
            .build();

    PersonDatasourceTransactionManager manager =
        new PersonDatasourceTransactionManager() {

          @Override
          protected void execute(Connection paramConnection) throws Exception {

            CvpPersonSequenceDAO daoSeq = new CvpPersonSequenceDAO();
            @SuppressWarnings("unchecked")
            List<Long> lista = daoSeq.retrieveWhere(paramConnection);
            if (!lista.isEmpty()) {
              person.setPersonId(lista.get(0));
              log.debug("valore sequence:" + lista.get(0));
            }
            CpvPersonDAO daoInsert = new CpvPersonDAO(person);
            log.debug("valori passati: " + person);
            int nRecord = daoInsert.insertPrepared(paramConnection);
            log.debug("record inseriti: " + nRecord);
            paramConnection.commit();
          }
        };
    manager.executeTransaction();
    return person.getPersonId();
  }

  private Boolean isCittadinoResidenteComuneGe(Residente residente) throws BusinessException {
    log.debug("[AnonimizzaImpl] isCittadinoResidenteComuneGe");
    if (residente == null) {
      log.debug("[AnonimizzaImpl] CF non trovato in bd comune Genova");
      return false;
    } else {
      return true;
    }
  }

  @Override
  @Deprecated
  public UserAnonymous getIdUtenteAnonimo(String codiceFiscale) throws BusinessException {

    Long idAnonimo = controllaCodiceFiscaleSuCpvPerson(codiceFiscale);
    if (idAnonimo > 0L) {
      log.debug("Utente trovato:" + idAnonimo);
      return new UserAnonymousBuilder()
          .setIdAnonimo(idAnonimo)
          .setResponse(new EsitoResponseBuilder().setEsito(true).build())
          .build();
    } else {
      try {
        Residente residente = getResidenteComuneGenova(codiceFiscale);
        if (residente == null) {
          log.debug("CF non trovato in bd comune Genova");
          String mex = codiceFiscale + " non residente nel comune di Genova";
          return new UserAnonymousBuilder()
              .setIdAnonimo(idAnonimo)
              .setResponse(new EsitoResponseBuilder().setEsito(false).setDescrizione(mex).build())
              .build();
        }
        CpvPerson person = inserisciResidenteInCpvPerson(residente);
        log.debug("Record creato, primo accesso");
        return new UserAnonymousBuilder()
            .setIdAnonimo(person.getPersonId())
            .setResponse(
                new EsitoResponseBuilder().setEsito(true).setDescrizione("Primo accesso").build())
            .build();
      } catch (BusinessException e) {
        String mex = "Errore durante l'accesso alle API";
        log.error(mex, e);
        return new UserAnonymousBuilder()
            .setIdAnonimo(idAnonimo)
            .setResponse(
                new EsitoResponseBuilder()
                    .setEsito(false)
                    .setDescrizione(mex)
                    .setEccezione(e.getMessage())
                    .build())
            .build();
      } catch (WebApplicationException e) {
        log.debug("CF non ha residenza in comune Genova");
        if (e.getResponse().getStatus() == 204) {
          String mex = codiceFiscale + " non residente nel comune di Genova";
          return new UserAnonymousBuilder()
              .setIdAnonimo(idAnonimo)
              .setResponse(
                  new EsitoResponseBuilder()
                      .setEsito(false)
                      .setDescrizione(mex)
                      .setEccezione(e.getMessage())
                      .build())
              .build();
        } else {
          String mex = "Errore durante l'accesso alle API";
          log.error(mex, e);
          throw e;
        }
      }
    }
  }

  private CpvPerson inserisciResidenteInCpvPerson(Residente residente) throws BusinessException {

    final CpvPerson person =
        new CpvPersonBuilder()
            .setCodiceFiscale(residente.getCpvTaxCode())
            .setDataAgg(LocalDateTime.now())
            .setDataIns(LocalDateTime.now())
            .setIdAnagrafica(residente.getCpvPersonID())
            .setIdStatoRec(1L)
            .setUtenteAgg(BaseServiceImpl.COD_APP)
            .setUtenteIns(BaseServiceImpl.COD_APP)
            .build();

    PersonDatasourceTransactionManager manager =
        new PersonDatasourceTransactionManager() {

          @Override
          protected void execute(Connection paramConnection) throws Exception {

            CvpPersonSequenceDAO daoSeq = new CvpPersonSequenceDAO();
            @SuppressWarnings("unchecked")
            List<Long> lista = daoSeq.retrieveWhere(paramConnection);
            if (!lista.isEmpty()) {
              person.setPersonId(lista.get(0));
              log.debug("valore sequence:" + lista.get(0));
            }
            CpvPersonDAO daoInsert = new CpvPersonDAO(person);
            log.debug("valori passati: " + person);
            int nRecord = daoInsert.insertPrepared(paramConnection);
            log.debug("record inseriti: " + nRecord);
            paramConnection.commit();
          }
        };
    manager.executeTransaction();
    return person;
  }

  private Long controllaCodiceFiscaleSuCpvPerson(String codiceFiscale) throws BusinessException {

    CpvPerson person = new CpvPerson();
    person.setCodiceFiscale(codiceFiscale);
    CpvPersonDAO dao = new CpvPersonDAO(person);
    PersonBusinessHelper helper = new PersonBusinessHelper(dao);
    try {
      person = (CpvPerson) helper.cercaOggetto();
      if (person != null) return person.getPersonId();
      else return 0L;
    } catch (BusinessException e) {
      log.error(" errore: ", e);
      throw new BusinessException("Errore sul DB: " + e.getMessage());
    }
  }

  private Residente getResidenteComuneGenova(String codiceFiscale) throws BusinessException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    return closeConnectionInGetResidenteComuneGenova(instance, codiceFiscale);
  }

  private Residente closeConnectionInGetResidenteComuneGenova(
      ServiceLocatorLivelloUno instance, String codiceFiscale) {
    try {
      return instance.getApiDemografico().demograficoResidenteCodiceFiscaleGet(codiceFiscale);
    } catch (BusinessException | WebApplicationException e) {
      throw new WebApplicationException("Errore in closeConnection" + e.getMessage());
    } finally {
      instance.closeConnection();
    }
  }

  private CpvAnonim convertiInputRest(AnonimoData anonim) {
    log.debug("--convertiInputRest anonim: " + anonim);
    CpvAnonim cpvAnonim =
        new CpvAnonimBuilder()
            .setCodiceBelfioreNascita(anonim.getCodiceBelfiore())
            .setCodiceFiscale(anonim.getCodiceFiscale())
            .setCognome(anonim.getCognome())
            .setNome(anonim.getNome())
            .setSesso(anonim.getSesso())
            .setDataNascita(anonim.getDataNascita().atStartOfDay())
            .build();
    log.trace("--convertiInputRest cpvAnonim: " + cpvAnonim);
    return cpvAnonim;
  }

  @Override
  public String getCodiceFiscale(Long idAnonimo) throws BusinessException {

    CpvPerson person = new CpvPerson();
    person.setPersonId(idAnonimo);
    CpvPersonDAO dao = new CpvPersonDAO(person);
    PersonBusinessHelper helper = new PersonBusinessHelper(dao);
    person = (CpvPerson) helper.cercaOggetto();
    if (person != null) {
      return person.getCodiceFiscale();
    } else {
      CpvAnonim anonim = new CpvAnonim();
      anonim.setPersonId(idAnonimo);
      CpvAnonimNonCancellatiDAO daoAnonim = new CpvAnonimNonCancellatiDAO(anonim);
      PersonBusinessHelper helperAnonim = new PersonBusinessHelper(daoAnonim);
      anonim = (CpvAnonim) helperAnonim.cercaOggetto();
      if (anonim != null) {
        return anonim.getCodiceFiscale();
      } else {
        log.error("Codice fiscale non reperito in bd, per personId:" + idAnonimo);
        // throw new BusinessException("Codice fiscale non reperito in
        // bd, per
        // personId:" + idAnonimo);
        return "";
      }
    }
  }

  @Override
  @Deprecated
  public UserAnonymous gestioneNonResidenti(AnonimoData anonim) throws BusinessException {

    log.debug("anonim.getSesso: " + anonim.getSesso());
    log.debug("anonim.getDataNascita: " + anonim.getDataNascita());

    UserAnonymous user = getIdUtenteAnonimo(anonim.getCodiceFiscale());
    if (user.getIdAnonimo() > 0L) { // residente e registrato
      user.setIsResidente(true);
      return user;
    } else { // 1 cerca se cf presente in bd non residenti
      Long idAnonimo;
      try {
        idAnonimo = controllaCodiceFiscaleSuCpvAnonim(anonim.getCodiceFiscale());
        if (idAnonimo > 0L) { // 2 se presente restituiscce id anonimo
          String mex = anonim.getCodiceFiscale() + " non residente nel comune di Genova";
          log.debug("Utente trovato:" + idAnonimo + " non residente");
          log.debug(mex);
          return new UserAnonymousBuilder()
              .setIdAnonimo(idAnonimo)
              .setResponse(new EsitoResponseBuilder().setEsito(false).setDescrizione(mex).build())
              .setIsResidente(false)
              .build();
        } else { // 3 se non presente inserisce tutti i dati
          CpvAnonim nonResidente;
          try {
            CpvAnonim cpvAnonim = convertiInputRest(anonim);
            nonResidente = inserisciNonResidenteInAnonim(cpvAnonim);
            log.debug("Record creato, primo accesso");
            return new UserAnonymousBuilder()
                .setIdAnonimo(nonResidente.getPersonId())
                .setResponse(
                    new EsitoResponseBuilder()
                        .setEsito(true)
                        .setDescrizione("Primo accesso")
                        .build())
                .setIsResidente(false)
                .build();
          } catch (BusinessException e) {
            String mex = "Errore durante l'accesso al DB";
            log.error(mex, e);
            return new UserAnonymousBuilder()
                .setIdAnonimo(idAnonimo)
                .setIsResidente(false)
                .setResponse(
                    new EsitoResponseBuilder()
                        .setEsito(false)
                        .setDescrizione(mex)
                        .setEccezione(e.getMessage())
                        .build())
                .build();
          }
        }
      } catch (BusinessException e) {
        String mex = "Errore durante l'accesso al DB";
        log.error(mex, e);
        return new UserAnonymousBuilder()
            .setIsResidente(false)
            .setResponse(
                new EsitoResponseBuilder()
                    .setEsito(false)
                    .setDescrizione(mex)
                    .setEccezione(e.getMessage())
                    .build())
            .build();
      }
    }
  }

  private CpvAnonim inserisciNonResidenteInAnonim(CpvAnonim nonResidente) throws BusinessException {

    final CpvAnonim cpvanonim =
        new CpvAnonimBuilder()
            .setCodiceFiscale(nonResidente.getCodiceFiscale())
            .setCodiceBelfioreNascita(nonResidente.getCodiceBelfioreNascita())
            .setCognome(nonResidente.getCognome())
            .setNome(nonResidente.getNome())
            .setDataNascita(nonResidente.getDataNascita())
            .setSesso(nonResidente.getSesso())
            .setDataAgg(LocalDateTime.now())
            .setDataIns(LocalDateTime.now())
            .setIdStatoRec(1L)
            .setUtenteAgg(BaseServiceImpl.COD_APP)
            .setUtenteIns(BaseServiceImpl.COD_APP)
            .build();

    PersonDatasourceTransactionManager manager =
        new PersonDatasourceTransactionManager() {

          @Override
          protected void execute(Connection paramConnection) throws Exception {

            CvpPersonSequenceDAO daoSeq = new CvpPersonSequenceDAO();
            @SuppressWarnings("unchecked")
            List<Long> lista = daoSeq.retrieveWhere(paramConnection);
            if (!lista.isEmpty()) {
              cpvanonim.setPersonId(lista.get(0));
              log.debug("valore sequence:" + lista.get(0));
            }
            CpvAnonimDAO daoInsert = new CpvAnonimDAO(cpvanonim);
            log.debug("valori passati: " + cpvanonim);
            int nRecord = daoInsert.insertPrepared(paramConnection);
            log.debug("record inseriti: " + nRecord);
            paramConnection.commit();
          }
        };
    manager.executeTransaction();
    return cpvanonim;
  }

  private Long controllaCodiceFiscaleSuCpvAnonim(String codiceFiscale) throws BusinessException {

    CpvAnonim cpvanonim = new CpvAnonimBuilder().setCodiceFiscale(codiceFiscale).build();
    CpvAnonimDAO dao = new CpvAnonimDAO(cpvanonim);
    PersonBusinessHelper helper = new PersonBusinessHelper(dao);
    cpvanonim = (CpvAnonim) helper.cercaOggetto();
    if (cpvanonim != null) {
      return cpvanonim.getPersonId();
    } else {
      return 0L;
    }
  }
}
