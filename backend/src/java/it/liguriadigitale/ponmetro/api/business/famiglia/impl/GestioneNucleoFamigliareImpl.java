package it.liguriadigitale.ponmetro.api.business.famiglia.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.business.anonimizza.impl.AnonimizzaImpl;
import it.liguriadigitale.ponmetro.api.business.anonimizza.service.AnonimizzaInterface;
import it.liguriadigitale.ponmetro.api.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.common.PonMetroDatasourceTransactionManager;
import it.liguriadigitale.ponmetro.api.business.common.datasource.PersonBusinessHelper;
import it.liguriadigitale.ponmetro.api.business.famiglia.service.GestioneNucleoFamigliareInterface;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgTCatRelativesDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgTFcittDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CfgTFcittRelativesDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.CpvAnonimDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.seq.CfgTFCittRelativeSequenceDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.seq.CfgTFcittSeqDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.update.CfgTFcittRelativesUpdateBloccoDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.update.CfgTFcittRelativesUpdateDAO;
import it.liguriadigitale.ponmetro.api.integration.dao.update.CpvAnonimUpdateDAO;
import it.liguriadigitale.ponmetro.api.pojo.anonimous.db.CpvAnonim;
import it.liguriadigitale.ponmetro.api.pojo.common.builder.EsitoResponseBuilder;
import it.liguriadigitale.ponmetro.api.pojo.enums.AutocertificazioneTipoMinoreEnum;
import it.liguriadigitale.ponmetro.api.pojo.family.db.CfgTCatRelatives;
import it.liguriadigitale.ponmetro.api.pojo.family.db.CfgTFcitt;
import it.liguriadigitale.ponmetro.api.pojo.family.db.CfgTFcittRelatives;
import it.liguriadigitale.ponmetro.api.pojo.family.db.builder.CfgTFcittBuilder;
import it.liguriadigitale.ponmetro.api.pojo.family.db.builder.CfgTFcittRelativesBuilder;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.CitizenResponse;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.DatiAnagrafici;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.FamilyResponse;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.Relative;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.builder.CitizenResponseBuilder;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.builder.DatiAnagraficiBuilder;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.builder.FamilyResponseBuilder;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.builder.RelativeBuilder;
import it.liguriadigitale.ponmetro.api.util.LocalDateUtil;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GestioneNucleoFamigliareImpl implements GestioneNucleoFamigliareInterface {

  private static Log log = LogFactory.getLog(GestioneNucleoFamigliareImpl.class);

  @Override
  public CitizenResponse getIdFCittadinoByIdPerson(Long idPerson) {

    CfgTFcitt fCitt = new CfgTFcittBuilder().setPersonId(idPerson).build();
    CfgTFcittDAO dao = new CfgTFcittDAO(fCitt);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    try {
      @SuppressWarnings("unchecked")
      List<CfgTFcitt> lista = helper.cercaOggetti();
      CfgTFcitt result = new CfgTFcitt();

      if (lista == null || lista.isEmpty()) {
        result = inserisciCfgTFcittNuovo(idPerson);
      } else {
        result = lista.get(0);
      }
      CitizenResponse response =
          new CitizenResponseBuilder()
              .setIdPersonCitizen(idPerson)
              .setIdFcittCitizen(result.getIdFcitt())
              .setEsito(new EsitoResponseBuilder().setEsito(true).build())
              .build();
      log.debug("Response:" + response);
      return response;

    } catch (BusinessException e) {
      log.error("[" + this.getClass().getName() + "] Errore");
      return new CitizenResponseBuilder()
          .setIdFcittCitizen(fCitt.getIdFcitt())
          .setEsito(new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build())
          .build();
    }
  }

  @SuppressWarnings("unused")
  private Long cercaIdPersonByIdFCitt(Long idFcitt) throws BusinessException {

    log.debug("cercaIdPersonByIdFCitt -- idFcitt=" + idFcitt);
    CfgTFcitt fCitt = new CfgTFcittBuilder().setIdFcitt(idFcitt).build();
    CfgTFcittDAO dao = new CfgTFcittDAO(fCitt);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    CfgTFcitt result = (CfgTFcitt) helper.cercaOggetto();
    if (result != null) return result.getPersonId();
    else throw new BusinessException("Nessun record trovato!");
  }

  private CfgTFcitt inserisciCfgTFcittNuovo(Long idPerson) throws BusinessException {

    CfgTFcittSeqDAO seqDao = new CfgTFcittSeqDAO();
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(seqDao);
    Long idFcitt = (Long) helper.cercaOggetto();
    CfgTFcitt cfgtfcitt =
        new CfgTFcittBuilder()
            .setPersonId(idPerson)
            .setIdFcitt(idFcitt)
            .setDataAgg(LocalDateTime.now())
            .setDataIns(LocalDateTime.now())
            .setIdStatoRec(1L)
            .setTimeLastLogin(LocalDateTime.now())
            .setUtenteAgg(BaseServiceImpl.COD_APP)
            .setUtenteIns(BaseServiceImpl.COD_APP)
            .build();
    CfgTFcittDAO daoCitt = new CfgTFcittDAO(cfgtfcitt);
    helper = new PonMetroBusinessHelper(daoCitt);
    helper.inserisciOggetto();
    return cfgtfcitt;
  }

  @Override
  public FamilyResponse getListaFamigliari(Long idFCittGenitore) {

    try {
      CfgTFcittRelatives relative = new CfgTFcittRelatives();
      relative.setIdFcitt(idFCittGenitore);
      CfgTFcittRelativesDAO dao = new CfgTFcittRelativesDAO(relative);
      PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
      @SuppressWarnings("unchecked")
      List<CfgTFcittRelatives> lista = helper.cercaOggetti();
      return new FamilyResponseBuilder()
          .setEsito(new EsitoResponseBuilder().setEsito(true).build())
          .setListaParenti(cercaCfEconvertiLista(lista))
          .setIdFcitt(idFCittGenitore)
          .build();
    } catch (BusinessException e) {
      log.error("[" + this.getClass().getName() + "] Errore", e);
      return new FamilyResponseBuilder()
          .setEsito(new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build())
          .setIdPerson(idFCittGenitore)
          .build();
    }
  }

  private List<Relative> cercaCfEconvertiLista(List<CfgTFcittRelatives> lista)
      throws BusinessException {
    List<Relative> listaParenti = new ArrayList<>();
    for (CfgTFcittRelatives parente : lista) {
      Long idPersonMinore = parente.getIdPersonParente();
      String codiceFiscale = cercaCodiceFiscale(idPersonMinore);
      if (StringUtils.isNotBlank(codiceFiscale)) {
        Relative relative =
            new RelativeBuilder()
                .setCategoriaParentela(getCatRelatives(parente.getIdCatRelatives()))
                .setIdFCittGenitore(parente.getIdFcitt())
                .setIdPersonMinore(idPersonMinore)
                .setIdStatoRegistrazione(parente.getIdStatoRec())
                .setCodiceFiscale(codiceFiscale)
                .setBloccoAutodichiarazione(parente.getBloccoAutodichiarazione())
                .build();
        listaParenti.add(relative);
      }
    }
    return listaParenti;
  }

  private String cercaCodiceFiscale(Long idPersonMinore) throws BusinessException {

    AnonimizzaInterface service = new AnonimizzaImpl();
    return service.getCodiceFiscale(idPersonMinore);
  }

  @Override
  public CitizenResponse salvaFamigliare(
      Long idPersonMinore, Long idFCitt, String categoryRelatives, Boolean isAutocertificazione) {
    log.debug("START SALVA FAMIGLIARE ");

    Long idFCittMinore = 0L;
    final String userInserimentoAggiornamento =
        getUserAggiornamentoInserimento(idFCitt, isAutocertificazione);

    Long idCatRelatives;
    try {
      // problema
      idCatRelatives = getCatRelatives(categoryRelatives);
    } catch (BusinessException e) {
      log.error("[" + this.getClass().getName() + "] Errore:" + e.getMessage(), e);
      return new CitizenResponseBuilder()
          .setIdPersonCitizen(idPersonMinore)
          .setIdFcittCitizen(idFCittMinore)
          .setEsito(new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build())
          .build();
    }

    final CfgTFcittRelatives relative =
        new CfgTFcittRelativesBuilder()
            .setIdFcitt(idFCitt)
            .setIdPersonParente(idPersonMinore)
            .setDataAgg(LocalDateTime.now())
            .setUtenteAgg(userInserimentoAggiornamento)
            .setBloccoAutodichiarazione(false)
            .setIdCatRelatives(idCatRelatives)
            .build();

    PonMetroDatasourceTransactionManager manager =
        new PonMetroDatasourceTransactionManager() {

          @Override
          protected void execute(Connection conn) throws Exception {

            CfgTFcittRelativesDAO dao = new CfgTFcittRelativesDAO(relative);
            @SuppressWarnings("unchecked")
            List<CfgTFcittRelatives> lista = dao.retrieveWhere(conn);
            if (lista.isEmpty()) {
              CfgTFCittRelativeSequenceDAO seqDao = new CfgTFCittRelativeSequenceDAO();
              Long idFcittRelatives = (Long) seqDao.retrieveWhere(conn).get(0);
              relative.setIdFcittRelatives(idFcittRelatives);
              relative.setDataIns(LocalDateTime.now());
              relative.setUtenteIns(userInserimentoAggiornamento);
              relative.setIdStatoRec(1L);
              CfgTFcittRelativesDAO daoInsert = new CfgTFcittRelativesDAO(relative);
              int nRecord = daoInsert.insertPrepared(conn);
              log.debug("record inseriti: " + nRecord);
              conn.commit();
            } else {
              Long idFcittRelatives = lista.get(0).getIdFcittRelatives();
              relative.setIdFcittRelatives(idFcittRelatives);
              relative.setIdStatoRec(1L);
              CfgTFcittRelativesUpdateDAO daoUpdate = new CfgTFcittRelativesUpdateDAO(relative);
              int nRecord = daoUpdate.updateWhere(conn);
              log.debug("record modificati: " + nRecord);
              conn.commit();
            }
          }
        };
    try {
      manager.executeTransaction();
    } catch (BusinessException e) {
      log.error("[" + this.getClass().getName() + "] Errore:" + e.getMessage(), e);
      return new CitizenResponseBuilder()
          .setIdFcittCitizen(idFCitt)
          .setEsito(new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build())
          .build();
    }
    return new CitizenResponseBuilder()
        .setIdPersonCitizen(idPersonMinore)
        .setIdFcittCitizen(idFCittMinore)
        .setEsito(new EsitoResponseBuilder().setEsito(true).build())
        .build();
  }

  private Long getCatRelatives(String categoryRelatives) throws BusinessException {

    CfgTCatRelatives category = new CfgTCatRelatives();
    CfgTCatRelativesDAO dao = new CfgTCatRelativesDAO(category);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    @SuppressWarnings("unchecked")
    List<CfgTCatRelatives> lista = helper.cercaOggetti();
    Long valoreRitorno = 1L;
    for (CfgTCatRelatives valoreDb : lista) {
      if (categoryRelatives.equalsIgnoreCase(valoreDb.getDenominazione()))
        valoreRitorno = valoreDb.getIdCatRelatives();
    }
    return valoreRitorno;
  }

  private AutocertificazioneTipoMinoreEnum getCatRelatives(Long idCategoryRelatives)
      throws BusinessException {

    CfgTCatRelatives category = new CfgTCatRelatives();
    CfgTCatRelativesDAO dao = new CfgTCatRelativesDAO(category);
    PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
    @SuppressWarnings("unchecked")
    List<CfgTCatRelatives> lista = helper.cercaOggetti();
    AutocertificazioneTipoMinoreEnum valoreRitorno = AutocertificazioneTipoMinoreEnum.MC;
    for (CfgTCatRelatives valoreDb : lista) {
      if (idCategoryRelatives.equals(valoreDb.getIdCatRelatives()))
        return AutocertificazioneTipoMinoreEnum.fromValue(
            valoreDb.getDenominazione().toUpperCase());
    }
    return valoreRitorno;
  }

  private String getUserAggiornamentoInserimento(Long idFCitt, Boolean isAutocertificazione) {
    String userInserimentoAggiornamento = BaseServiceImpl.COD_APP;
    if (isAutocertificazione) userInserimentoAggiornamento = "idFCitt=" + idFCitt;
    return userInserimentoAggiornamento;
  }

  @Override
  public CitizenResponse updateBloccoAutodichiarazione(
      Long idPersonMinore, Long idFCittGenitore, Boolean isBloccoAutocertificazione) {

    final String userInserimentoAggiornamento =
        getUserAggiornamentoInserimento(idFCittGenitore, true);
    log.debug("isBloccoAutocertificazione: " + isBloccoAutocertificazione);
    final CfgTFcittRelatives relative =
        new CfgTFcittRelativesBuilder()
            .setIdFcitt(idFCittGenitore)
            .setIdPersonParente(idPersonMinore)
            .setDataAgg(LocalDateTime.now())
            .setUtenteAgg(userInserimentoAggiornamento)
            .setBloccoAutodichiarazione(isBloccoAutocertificazione)
            .build();

    PonMetroDatasourceTransactionManager manager =
        new PonMetroDatasourceTransactionManager() {

          @Override
          @SuppressWarnings("unchecked")
          protected void execute(Connection conn) throws Exception {

            CfgTFcittRelativesDAO dao = new CfgTFcittRelativesDAO(relative);

            List<CfgTFcittRelatives> lista = dao.retrieveWhere(conn);
            if (lista.isEmpty()) {
              throw new BusinessException("Nessun minore trovato");
            } else {
              Long idFcittRelatives = lista.get(0).getIdFcittRelatives();
              relative.setIdFcittRelatives(idFcittRelatives);
              CfgTFcittRelativesUpdateBloccoDAO daoUpdate =
                  new CfgTFcittRelativesUpdateBloccoDAO(relative);
              daoUpdate.updateWhere(conn);
              int nRecord = daoUpdate.updateWhere(conn);
              log.debug("record modificati: " + nRecord);
              conn.commit();
            }
          }
        };
    try {
      manager.executeTransaction();
    } catch (BusinessException e) {
      log.error("[" + this.getClass().getName() + "] Errore:" + e.getMessage(), e);
      return new CitizenResponseBuilder()
          .setIdFcittCitizen(idFCittGenitore)
          .setEsito(new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build())
          .build();
    }
    return new CitizenResponseBuilder()
        .setIdFcittCitizen(idFCittGenitore)
        .setEsito(new EsitoResponseBuilder().setEsito(true).build())
        .build();
  }

  @Override
  public FamilyResponse getListaFamigliariNonResidenti(Long idFCittGenitore) {
    log.debug("getListaFamigliariNonResidenti -- INIZIO");
    FamilyResponse nucleoFamigliare = this.getListaFamigliari(idFCittGenitore);
    log.debug(
        "getListaFamigliariNonResidenti -- nucleoFamigliare.getListaParenti().size="
            + nucleoFamigliare.getListaParenti().size());
    for (Relative minore : nucleoFamigliare.getListaParenti()) {

      try {
        CpvAnonim cpvAnonim = new CpvAnonim();
        cpvAnonim.setCodiceFiscale(minore.getCodiceFiscale());
        CpvAnonimDAO dao = new CpvAnonimDAO(cpvAnonim);
        PersonBusinessHelper helper = new PersonBusinessHelper(dao);
        CpvAnonim datiAnagrafici = (CpvAnonim) helper.cercaOggetto();
        if (isMinorenne(datiAnagrafici)) {
          DatiAnagrafici datiMinore =
              DatiAnagraficiBuilder.getInstance()
                  .setCodiceBelfioreLuogoNascita(datiAnagrafici.getCodiceBelfioreNascita())
                  .setCognome(datiAnagrafici.getCognome())
                  .setNome(datiAnagrafici.getNome())
                  .setDataNascita(datiAnagrafici.getDataNascita().toLocalDate())
                  .setSesso(datiAnagrafici.getSesso().charAt(0))
                  .setCodiceFiscale(minore.getCodiceFiscale())
                  .build();
          minore.setDatiAnagraficiMinore(datiMinore);
        }

      } catch (BusinessException e) {
        log.error("[" + this.getClass().getName() + "] Errore:" + e.getMessage(), e);
        return new FamilyResponseBuilder()
            .setEsito(
                new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build())
            .setIdPerson(idFCittGenitore)
            .build();
      }
    }
    log.debug("nucleoFamigliare= " + nucleoFamigliare);
    return nucleoFamigliare;
  }

  private boolean isMinorenne(CpvAnonim datiAnagrafici) {

    if (datiAnagrafici != null
        && datiAnagrafici.getDataNascita() != null
        && !LocalDateUtil.isMaggiorenne(datiAnagrafici.getDataNascita().toLocalDate())) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public CitizenResponse cancellaFamigliare(Long idPersonMinore, Long idPerson) {

    try {
      CpvAnonim anonim = new CpvAnonim();
      anonim.setPersonId(idPersonMinore);
      CpvAnonimUpdateDAO dao = new CpvAnonimUpdateDAO(anonim);
      PersonBusinessHelper helper = new PersonBusinessHelper(dao);
      helper.aggiornaOggetto();
      return new CitizenResponseBuilder()
          .setIdFcittCitizen(idPerson)
          .setIdPersonCitizen(idPerson)
          .setEsito(new EsitoResponseBuilder().setEsito(true).build())
          .build();
    } catch (BusinessException e) {
      log.error("Errore durante l'update: ", e);
      return new FamilyResponseBuilder()
          .setEsito(new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build())
          .setIdPerson(idPerson)
          .build();
    }
  }

  @Override
  public CitizenResponse cancellaFamigliareResidente(Long idMinore, Long idAdulto) {

    try {
      log.debug("adulto : " + idMinore + " minore : " + idAdulto);
      CfgTFcittRelatives relatives = new CfgTFcittRelatives();
      relatives.setIdFcitt(idAdulto);
      relatives.setIdPersonParente(idMinore);
      CfgTFcittRelativesDAO dao = new CfgTFcittRelativesDAO(relatives);
      PonMetroBusinessHelper helper = new PonMetroBusinessHelper(dao);
      CfgTFcittRelatives oggettoDaAggiornare = (CfgTFcittRelatives) helper.cercaOggetto();

      if (oggettoDaAggiornare != null) {

        log.debug(
            "oggetto individuato con ID_FCITT : "
                + idAdulto
                + " e ID_PERSON_PARENTE : "
                + idMinore
                + "\n"
                + oggettoDaAggiornare);

        if (oggettoDaAggiornare.getIdFcitt().equals(idAdulto)
            && oggettoDaAggiornare.getIdPersonParente().equals(idMinore)) {

          oggettoDaAggiornare.setIdStatoRec(0L);

          CfgTFcittRelativesDAO daoDaAggiornare = new CfgTFcittRelativesDAO(oggettoDaAggiornare);
          PonMetroBusinessHelper helperDaAggiornare = new PonMetroBusinessHelper(daoDaAggiornare);
          log.debug("aggiorna oggetto : " + oggettoDaAggiornare);
          helperDaAggiornare.aggiornaOggetto();
        }
      } else {
        log.debug("Oggetto da aggiornare è o nullo o incongruente :\n" + oggettoDaAggiornare);
        return new CitizenResponseBuilder()
            .setIdFcittCitizen(idAdulto)
            .setIdPersonCitizen(idMinore)
            .setEsito(new EsitoResponseBuilder().setEsito(false).build())
            .build();
      }

      return new CitizenResponseBuilder()
          .setIdFcittCitizen(idAdulto)
          .setIdPersonCitizen(idMinore)
          .setEsito(new EsitoResponseBuilder().setEsito(true).build())
          .build();
    } catch (BusinessException e) {
      log.error("Errore durante l'update: ", e);
      return new FamilyResponseBuilder()
          .setEsito(new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build())
          .setIdPerson(idAdulto)
          .build();
    }
  }

  /******************************************************************************************************/
  @Override
  public CitizenResponse inserisciFamigliare(
      Long idPersonMinore, Long idFCitt, String categoryRelatives, Boolean isAutocertificazione) {

    log.debug("START INSERISCI FAMIGLIARE ");
    Long idFCittMinore = 0L;
    final String userInserimentoAggiornamento =
        getUserAggiornamentoInserimento(idFCitt, isAutocertificazione);

    Long idCatRelatives;
    try {
      // problema
      idCatRelatives = getCatRelatives(categoryRelatives);
    } catch (BusinessException e) {
      log.error("[" + this.getClass().getName() + "] Errore:" + e.getMessage(), e);
      return new CitizenResponseBuilder()
          .setIdPersonCitizen(idPersonMinore)
          .setIdFcittCitizen(idFCittMinore)
          .setEsito(new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build())
          .build();
    }

    final CfgTFcittRelatives relative =
        new CfgTFcittRelativesBuilder()
            .setIdFcitt(idFCitt)
            .setIdPersonParente(idPersonMinore)
            .setDataAgg(LocalDateTime.now())
            .setUtenteAgg(userInserimentoAggiornamento)
            .setBloccoAutodichiarazione(false)
            .setIdCatRelatives(idCatRelatives)
            .build();
    PonMetroDatasourceTransactionManager manager =
        new PonMetroDatasourceTransactionManager() {

          @Override
          protected void execute(Connection conn) throws Exception {

            CfgTFcittRelativesDAO dao = new CfgTFcittRelativesDAO(relative);
            @SuppressWarnings("unchecked")
            List<CfgTFcittRelatives> lista = dao.retrieveWhere(conn);
            if (!lista.isEmpty()) {
              log.debug(
                  "Autodichiarazione di minore già presente; idMinore:"
                      + idPersonMinore
                      + "idGenitore: "
                      + idFCitt);
              throw new IllegalArgumentException("Autodichiarazione di minore già presente");
            }

            log.debug(
                "Autodichiarazione di minore non presente per CF; idMinore:"
                    + idPersonMinore
                    + "idGenitore: "
                    + idFCitt);

            CfgTFCittRelativeSequenceDAO seqDao = new CfgTFCittRelativeSequenceDAO();
            Long idFcittRelatives = (Long) seqDao.retrieveWhere(conn).get(0);
            relative.setIdFcittRelatives(idFcittRelatives);
            relative.setDataIns(LocalDateTime.now());
            relative.setUtenteIns(userInserimentoAggiornamento);
            relative.setIdStatoRec(1L);
            CfgTFcittRelativesDAO daoInsert = new CfgTFcittRelativesDAO(relative);
            int nRecord = daoInsert.insertPrepared(conn);
            log.debug("record inseriti: " + nRecord);
            conn.commit();
          }
        };

    try {
      manager.executeTransaction();
    } catch (BusinessException e) {
      log.error("[" + this.getClass().getName() + "] Errore:" + e.getMessage(), e);
      return new CitizenResponseBuilder()
          .setIdFcittCitizen(idFCitt)
          .setEsito(new EsitoResponseBuilder().setEsito(false).setEccezione(e.getMessage()).build())
          .build();
    }
    return new CitizenResponseBuilder()
        .setIdPersonCitizen(idPersonMinore)
        .setIdFcittCitizen(idFCittMinore)
        .setEsito(new EsitoResponseBuilder().setEsito(true).build())
        .build();
  }
  /******************************************************************************************************/
}
