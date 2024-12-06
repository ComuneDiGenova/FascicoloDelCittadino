package it.liguriadigitale.ponmetro.portale.business.demografico.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.enums.AutocertificazioneTipoMinoreEnum;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.FamilyResponse;
import it.liguriadigitale.ponmetro.api.pojo.family.dto.Relative;
import it.liguriadigitale.ponmetro.demografico.model.DatiCatastali;
import it.liguriadigitale.ponmetro.demografico.model.ItemFiglio;
import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale;
import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale.CpvParentTypeEnum;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.demografico.model.Residente.CpvHasSexEnum;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteTari;
import it.liguriadigitale.ponmetro.demografico.model.SchedaAnagrafica;
import it.liguriadigitale.ponmetro.demografico.model.StrutturaFigli;
import it.liguriadigitale.ponmetro.demografico.model.StrutturaGenitori;
import it.liguriadigitale.ponmetro.demografico.model.TesseraElettorale;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.demografico.service.DemograficoInterface;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.mail.SendMailUtil;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.pojo.enums.SorgenteDatiNucleoEnum;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.mail.ContenutoMessaggio;
import it.liguriadigitale.ponmetro.portale.pojo.mail.ContenutoMessaggioBuilder;
import it.liguriadigitale.ponmetro.portale.pojo.portale.MinoreConvivente;
import it.liguriadigitale.ponmetro.portale.pojo.portale.builder.MinoreConviventeBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.LoginErrorPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.validator.CodiceFiscaleValidatorUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;

public class DemograficoImpl implements DemograficoInterface {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_DEMOGRAFICO = "Errore di connessione alle API Demografico";
  private static final String ERRORE_POOL_THREAD = "Errore nel recupero dei dati Demografici";
  private static final String ERRORE_API_UTENTE_NOT_FOUND =
      "Errore durante la ricerca del codice fiscale";

  @Override
  public List<ResidenteTari> getTuttiConviventiTuttiNuclei(Utente utente)
      throws BusinessException, ApiException {

    log.debug(
        "DemograficoImpl -- getTuttiConviventiTuttiNuclei(utente: "
            + utente.getCodiceFiscaleOperatore()
            + ")");

    List<ResidenteTari> listResidenteTari = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      listResidenteTari =
          instance
              .getApiDemografico()
              .tariResidentiCodiceFiscaleGet(utente.getCodiceFiscaleOperatore());

      log.debug("listResidenteTari = " + listResidenteTari);

    } catch (WebApplicationException e) {
      log.error("Errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "DemograficoImpl -- getTuttiConviventiTuttiNuclei: errore durante la chiamata delle API Demografico ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("demografico"));
    } finally {
      instance.closeConnection();
    }

    log.debug("listResidenteTari: " + listResidenteTari.size());

    return listResidenteTari;
  }

  @Override
  public List<ComponenteNucleo> getFigliSchedaAnagrafica(Utente capoFamiglia)
      throws BusinessException, ApiException {

    log.debug(
        "DemograficoImpl -- getFigliSchedaAnagrafica(utente: "
            + capoFamiglia.getCodiceFiscaleOperatore()
            + ")");

    List<ComponenteNucleo> listaFigli = new ArrayList<>();

    for (ComponenteNucleo parente : this.getNucleoFamiliareAllargato(capoFamiglia)) {
      log.debug("parente: " + parente.getIdPerson());

      if (LabelFdCUtil.checkIfNotNull(parente.getRelazione())
              && parente
                  .getRelazione()
                  .getCpvParentType()
                  .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FG.name())
          || parente
              .getRelazione()
              .getCpvParentType()
              .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FN.name())
          || parente
              .getRelazione()
              .getCpvParentType()
              .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FS.name()))
        listaFigli.add(parente);
    }

    log.debug("listaFigliSchedaAnagrafica: " + listaFigli.size());

    return listaFigli;
  }

  private List<ItemFiglio> getFigliStatoCivile(Utente capoFamiglia)
      throws BusinessException, ApiException {

    String codiceFiscaleUtenteCollegato = capoFamiglia.getCodiceFiscaleOperatore();
    log.debug("DemograficoImpl -- getFigliStatoCivile" + codiceFiscaleUtenteCollegato);
    List<ItemFiglio> listaFigli = new ArrayList<>();
    if (CodiceFiscaleValidatorUtil.isStringaLunga16Caratteri(codiceFiscaleUtenteCollegato)) {
      ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
      try {

        if (capoFamiglia.isResidente()) {
          StrutturaFigli figli = new StrutturaFigli();

          figli =
              instance
                  .getApiDemografico()
                  .oggettiStatoCivileFiglioCodiceFiscaleGenitoreGet(codiceFiscaleUtenteCollegato);

          log.debug("CP figli = " + figli);

          if (figli != null) {
            listaFigli = figli.getCpvHasParentalRelationshipWith();
          }
        }

      } catch (WebApplicationException e) {
        log.error("Errore nella Response getFigliStatoCivile:" + e.getMessage());
        throw new ApiException(e.getResponse(), e.getMessage());
      } catch (RuntimeException e) {
        log.error(
            "DemograficoImpl -- getFigliStatoCivile: errore durante la chiamata delle API Demografico ",
            e);
        throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("demografico"));
      } finally {
        log.debug("close connection");
        instance.closeConnection();
      }
    }
    log.debug("listaFigli: " + listaFigli.size());
    return listaFigli;
  }

  private List<ItemRelazioneParentale> getNucleoSchedaAnagrafica(Utente capoFamiglia)
      throws BusinessException, ApiException {
    log.debug(
        "DemograficoImpl -- getNucleoSchedaAnagrafica(utente: "
            + capoFamiglia.getCodiceFiscaleOperatore()
            + ")");
    log.debug("getNucleoSchedaAnagrafica residente = " + capoFamiglia.isResidente());

    SchedaAnagrafica scheda = new SchedaAnagrafica();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      if (capoFamiglia.isResidente()) {
        scheda =
            instance
                .getApiDemografico()
                .demograficoSchedaAnagraficaCodiceFiscaleGet(
                    capoFamiglia.getCodiceFiscaleOperatore());
      }
    } catch (WebApplicationException e) {
      log.error(
          "DemograficoImpl -- getNucleoSchedaAnagrafica: errore nel recupero della scheda anagrafica da codice fiscale:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("DemograficoImpl -- getNucleoSchedaAnagrafica: RunTimeException " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("demografico"));
    } finally {
      instance.closeConnection();
    }

    log.debug("getNucleoSchedaAnagrafica scheda = " + scheda);

    if (scheda == null) {
      throw new RestartResponseAtInterceptPageException(LoginErrorPage.class);
    } else {
      return scheda.getCpvBelongsToFamily();
    }
  }

  @Override
  public List<ComponenteNucleo> getFigli(Utente capoFamiglia)
      throws BusinessException, ApiException {
    if (capoFamiglia == null) {
      return new ArrayList<>();
    }

    log.debug(
        "DemograficoImpl -- getFigli(utente: " + capoFamiglia.getCodiceFiscaleOperatore() + ")");

    List<ComponenteNucleo> nucleoFamiliare = new ArrayList<>();

    for (ComponenteNucleo cn : this.getNucleoFamiliareAllargato(capoFamiglia)) {
      if (LabelFdCUtil.checkIfNotNull(cn)
              && LabelFdCUtil.checkIfNotNull(cn.getRelazione())
              && cn.getRelazione()
                  .getCpvParentType()
                  .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FG.name())
          || cn.getRelazione()
              .getCpvParentType()
              .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FN.name())
          || cn.getRelazione()
              .getCpvParentType()
              .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FS.name())
          || (cn.getAutodichiarazioneFiglio() != null && convertiAutoDichiarazioneFiglio(cn))) {
        nucleoFamiliare.add(cn);
      }
    }

    return nucleoFamiliare;
  }

  @Override
  public Residente getDatiResidente(String codiceFiscale) throws BusinessException, ApiException {

    log.debug("DemograficoImpl -- getDatiResidente");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      Residente residente =
          instance.getApiDemografico().demograficoResidenteCodiceFiscaleGet(codiceFiscale);
      if (residente != null) {
        log.debug(
            "isResidente: " + residente.getCpvFamilyName() + " " + residente.getCpvGivenName());
        return residente;
      } else {
        throw new ApiException(
            Response.serverError().status(204).tag(ERRORE_API_UTENTE_NOT_FOUND).build(),
            ERRORE_API_UTENTE_NOT_FOUND);
      }
    } catch (BusinessException e) {
      log.error(
          "DemograficoImpl -- getDatiResidente: errore di business nel recupero dei dati residente da codice fiscale:",
          e);
      throw new BusinessException(ERRORE_API_DEMOGRAFICO);
    } catch (WebApplicationException e) {
      log.error(
          "DemograficoImpl -- getDatiResidente: errore API nel recupero dei dati residente da codice fiscale:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("DemograficoImpl -- getDatiResidente: RunTimeException " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("demografico"));
    } finally {
      instance.closeConnection();
    }
  }

  private List<ComponenteNucleo> popolaNucleoFamiliareAllargatoDaStatoCivile(Utente capoFamiglia)
      throws BusinessException, ApiException {
    List<ComponenteNucleo> listaNucleo = new ArrayList<>();

    for (ItemFiglio figlio : this.getFigliStatoCivile(capoFamiglia)) {
      ComponenteNucleo newCn = new ComponenteNucleo();

      ItemRelazioneParentale parent = new ItemRelazioneParentale();
      parent.setCpvComponentTaxCode(figlio.getCpvIsParentOf());
      parent.setCpvParentType(CpvParentTypeEnum.FG);

      newCn.setRelazione(parent);
      newCn.setSorgente(SorgenteDatiNucleoEnum.STATO_CIVILE);
      newCn.setFiglioStatoCivile(true);
      newCn.setAutodichiarazioneFiglio(null);
      newCn.setBloccoAutodichiarazione(null);
      if (capoFamiglia.isResidente()) {
        newCn.setCoResidente(false);
      } else {
        newCn.setCoResidente(true);
      }
      newCn.setResidenteComuneGenova(true);

      listaNucleo.add(newCn);
    }

    return listaNucleo;
  }

  @Override
  public List<ComponenteNucleo> getNucleoFamiliareAllargato(Utente capoFamiglia)
      throws BusinessException, ApiException {
    if (capoFamiglia == null) {
      return new ArrayList<>();
    }

    log.debug(
        "DemograficoImpl -- getNucleoFamiliareAllargato (utente: "
            + capoFamiglia.getCodiceFiscaleOperatore()
            + ")");

    List<ComponenteNucleo> listaNucleoTemp = new ArrayList<>();

    if ((capoFamiglia.getNucleoFamiliareAllargato() == null)
        || capoFamiglia.getNucleoFamiliareAllargato().isEmpty()) {

      List<ComponenteNucleo> listaStatoCivile =
          popolaNucleoFamiliareAllargatoDaStatoCivile(capoFamiglia);
      log.debug(
          "DemograficoImpl -- getNucleoFamiliareAllargato:listaStatoCivile.size: "
              + listaStatoCivile.size());
      listaNucleoTemp =
          popolaNucleoFamiliareAllargatoDaSchedaAnagrafica(capoFamiglia, listaStatoCivile);
      log.debug(
          "DemograficoImpl -- getNucleoFamiliareAllargato:listaNucleoTemp.size: "
              + listaNucleoTemp.size());
    } else {
      listaNucleoTemp = capoFamiglia.getNucleoFamiliareAllargato();
    }

    List<ComponenteNucleo> listaNucleo =
        aggiornaNucleoFamiliareAllargatoDaAutodichiarazione(capoFamiglia, listaNucleoTemp);
    log.debug(
        "DemograficoImpl -- getNucleoFamiliareAllargato:listaNucleo.size: " + listaNucleo.size());
    aggiungiUtenteLoggatoAlNucleoSeNonPresente(capoFamiglia, listaNucleo);
    aggiornaDatiNucleoFamiliareAllargato(capoFamiglia, listaNucleo);
    return listaNucleo;
  }

  public void aggiungiUtenteLoggatoAlNucleoSeNonPresente(
      Utente capoFamiglia, List<ComponenteNucleo> listaNucleo) {

    if (LabelFdCUtil.checkIfNotNull(capoFamiglia) && !capoFamiglia.isResidente()) {
      if (isCodiceFiscaleNonPresenteInNucleo(
          listaNucleo, capoFamiglia.getCodiceFiscaleOperatore())) {
        ComponenteNucleo componenteNucleoPerCittadinoNonResidente = new ComponenteNucleo();
        Residente datiNonResidente = new Residente();
        datiNonResidente.setCpvTaxCode(capoFamiglia.getCodiceFiscaleOperatore());
        datiNonResidente.setCpvFamilyName(capoFamiglia.getCognome());
        datiNonResidente.setCpvGivenName(capoFamiglia.getNome());
        datiNonResidente.setRdfsLabel(getNominativoCapoFamiglia(capoFamiglia));
        datiNonResidente.setCpvDateOfBirth(capoFamiglia.getDataDiNascita());
        ItemRelazioneParentale relazione = new ItemRelazioneParentale();
        relazione.setCpvParentType(CpvParentTypeEnum.IS);
        relazione.setCpvComponentTaxCode(capoFamiglia.getCodiceFiscaleOperatore());
        componenteNucleoPerCittadinoNonResidente.setRelazione(relazione);
        log.debug("CP dati non res = " + datiNonResidente);
        componenteNucleoPerCittadinoNonResidente.setDatiCittadino(datiNonResidente);
        log.debug(
            "CP componenteNucleoPerCittadinoNonResidente = "
                + componenteNucleoPerCittadinoNonResidente);
        listaNucleo.add(componenteNucleoPerCittadinoNonResidente);
      }
    }
  }

  private String getNominativoCapoFamiglia(Utente capoFamiglia) {
    StringBuilder builder = new StringBuilder();
    return builder
        .append(capoFamiglia.getCognome())
        .append(" ")
        .append(capoFamiglia.getNome())
        .toString();
  }

  private boolean isCodiceFiscaleNonPresenteInNucleo(
      List<ComponenteNucleo> listaNucleo, String codiceFiscaleOperatore) {
    return !isCodiceFiscalePresenteInNucleo(listaNucleo, codiceFiscaleOperatore);
  }

  private boolean isCodiceFiscalePresenteInNucleo(
      List<ComponenteNucleo> listaNucleo, String codiceFiscaleOperatore) {
    Boolean found = false;
    for (ComponenteNucleo componente : listaNucleo) {
      if (componente.getDatiCittadino() != null
          && codiceFiscaleOperatore.equalsIgnoreCase(
              componente.getDatiCittadino().getCpvTaxCode())) {
        found = true;
      }
    }
    return found;
  }

  private List<ComponenteNucleo> popolaNucleoFamiliareAllargatoDaSchedaAnagrafica(
      Utente capoFamiglia, List<ComponenteNucleo> listaStatoCivile)
      throws BusinessException, ApiException {
    List<ComponenteNucleo> listaNucleo = new ArrayList<>();

    log.debug("popolaNucleoFamiliareAllargatoDaSchedaAnagrafica = " + capoFamiglia);
    List<ItemRelazioneParentale> nucleoSchedaAnagrafica =
        this.getNucleoSchedaAnagrafica(capoFamiglia);
    log.debug("getNucleoSchedaAnagrafica = " + nucleoSchedaAnagrafica);

    if (LabelFdCUtil.checkIfNotNull(nucleoSchedaAnagrafica)) {
      for (ItemRelazioneParentale cnSchedaAnagrafica : nucleoSchedaAnagrafica) {
        boolean found = false;

        for (ComponenteNucleo cnStatoCivile : listaStatoCivile) {
          if (cnSchedaAnagrafica
              .getCpvComponentTaxCode()
              .equals(cnStatoCivile.getRelazione().getCpvComponentTaxCode())) {
            found = true;
            cnStatoCivile.setCoResidente(true);
            cnStatoCivile.setResidenteComuneGenova(true);
            listaNucleo.add(cnStatoCivile);
          }
        }

        if (!found && !isEstraneoConviventeNelNucleo(cnSchedaAnagrafica, capoFamiglia)) {
          ComponenteNucleo newCn = new ComponenteNucleo();
          newCn.setRelazione(cnSchedaAnagrafica);
          newCn.setSorgente(SorgenteDatiNucleoEnum.SCHEDA_ANAGRAFICA);
          newCn.setCoResidente(true);
          newCn.setFiglioStatoCivile(false);
          newCn.setResidenteComuneGenova(true);
          newCn.setAutodichiarazioneFiglio(null);
          newCn.setBloccoAutodichiarazione(null);
          listaNucleo.add(newCn);
        }
      }
    }

    return mergeList(listaNucleo, listaStatoCivile);
  }

  public boolean isEstraneoConviventeNelNucleo(
      ItemRelazioneParentale cnSchedaAnagrafica, Utente capoFamiglia) {

    Boolean escludi = false;
    List<String> listaCodiciRelazioniParentali = new ArrayList<>();
    listaCodiciRelazioniParentali.add(CpvParentTypeEnum.IC.value());
    if (isComponenteNucleoDiversoDaUtenteCollegato(cnSchedaAnagrafica, capoFamiglia)) {
      for (String codice : listaCodiciRelazioniParentali) {
        if (codice.equalsIgnoreCase(cnSchedaAnagrafica.getCpvParentType())) {
          escludi = true;
        }
      }
    }
    return escludi;
  }

  private boolean isComponenteNucleoDiversoDaUtenteCollegato(
      ItemRelazioneParentale cnSchedaAnagrafica, Utente capoFamiglia) {
    return !capoFamiglia
        .getCodiceFiscaleOperatore()
        .equalsIgnoreCase(cnSchedaAnagrafica.getCpvComponentTaxCode());
  }

  @Override
  public List<Relative> getMinoriDaAutodichiarazione(Utente utente) throws BusinessException {
    List<Relative> listaAutodichiarazione = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    if (utente.isResidente()) {
      try {
        if (utente.getIdAnonimoComuneGenova() != null) {
          FamilyResponse response =
              instance.getApiFamily().getListaFamigliari(utente.getIdAnonimoComuneGenova());
          listaAutodichiarazione = response.getListaParenti();
          log.debug(
              "DemograficoImpl -- getListaFamigliari -- listaAutodichiarazione="
                  + listaAutodichiarazione.size());
        }
      } catch (Exception e) {
        log.debug("Errore: ", e);
        throw new BusinessException("Errore ");
      } finally {
        instance.closeConnection();
      }
      return listaAutodichiarazione;
    } else {
      try {
        if (utente.getIdAnonimoComuneGenova() != null) {
          FamilyResponse response =
              instance
                  .getApiFamily()
                  .getListaFamigliariNonResidenti(utente.getIdAnonimoComuneGenova());
          listaAutodichiarazione = response.getListaParenti();
          log.debug(
              "DemograficoImpl -- getListaFamigliariNonResidenti -- listaAutodichiarazione="
                  + listaAutodichiarazione.size());
        }
      } catch (Exception e) {
        log.debug("Errore: ", e);
        throw new BusinessException("Errore ");
      } finally {
        instance.closeConnection();
      }
      return listaAutodichiarazione;
    }
  }

  private List<ComponenteNucleo> aggiornaNucleoFamiliareAllargatoDaAutodichiarazione(
      Utente capoFamiglia, List<ComponenteNucleo> listaNucleoTemp)
      throws BusinessException, ApiException {
    List<ComponenteNucleo> listaNucleo = new ArrayList<>();

    log.debug("DemograficoImpl -- aggiornaNucleoFamiliareAllargatoDaAutodichiarazione");

    for (Relative relative : this.getMinoriDaAutodichiarazione(capoFamiglia)) {
      boolean found = false;
      for (ComponenteNucleo cn : listaNucleoTemp) {
        log.debug(
            "DemograficoImpl -- aggiornaNucleoFamiliareAllargatoDaAutodichiarazione -- cerco: "
                + cn);

        if (relative.getCodiceFiscale().equals(cn.getRelazione().getCpvComponentTaxCode())) {
          log.debug(
              "DemograficoImpl -- aggiornaNucleoFamiliareAllargatoDaAutodichiarazione -- trovato");
          found = true;
          cn.setAutodichiarazioneFiglio(figlioDaAutocertificazione(relative));
          cn.getRelazione().setCpvParentType(mappaAutocertificazioneMinore(relative));
          cn.setBloccoAutodichiarazione((relative.getBloccoAutodichiarazione() ? 1L : 0L));
          cn.setIdPerson(relative.getIdPersonMinore());
          listaNucleo.add(cn);
        }
      }

      if (!found) {
        ComponenteNucleo newCn = new ComponenteNucleo();
        newCn.setFiglioStatoCivile(false);
        newCn.setCoResidente(false);
        newCn.setAutodichiarazioneFiglio(figlioDaAutocertificazione(relative));
        newCn.setBloccoAutodichiarazione((relative.getBloccoAutodichiarazione() ? 1L : 0L));
        newCn.setIdPerson(relative.getIdPersonMinore());
        ItemRelazioneParentale parente = new ItemRelazioneParentale();
        parente.setCpvComponentTaxCode(relative.getCodiceFiscale());
        parente.setCpvParentType(mappaAutocertificazioneMinore(relative));
        newCn.setRelazione(parente);
        newCn.setSorgente(SorgenteDatiNucleoEnum.AUTODICHIARAZIONE);
        if (relative.getDatiAnagraficiMinore() != null) {
          Residente datiCittadino = new Residente();
          datiCittadino.setCpvDateOfBirth(relative.getDatiAnagraficiMinore().getDataNascita());
          datiCittadino.setCpvFamilyName(relative.getDatiAnagraficiMinore().getCognome());
          datiCittadino.setCpvGivenName(relative.getDatiAnagraficiMinore().getNome());
          datiCittadino.setCpvTaxCode(relative.getDatiAnagraficiMinore().getCodiceFiscale());
          if (CpvHasSexEnum.F.name()
              .equalsIgnoreCase(String.valueOf(relative.getDatiAnagraficiMinore().getSesso()))) {
            datiCittadino.setCpvHasSex(CpvHasSexEnum.F);
          } else {
            datiCittadino.setCpvHasSex(CpvHasSexEnum.M);
          }
          newCn.setDatiCittadino(datiCittadino);
          // se ci sono i dati anagrafici e' coresidente per forza
          if (!capoFamiglia.isResidente()) {
            newCn.setCoResidente(true);
          }
        }
        log.debug(
            "DemograficoImpl -- aggiornaNucleoFamiliareAllargatoDaAutodichiarazione -- popolato: "
                + newCn);
        listaNucleo.add(newCn);
      }
    }

    return mergeList(listaNucleo, listaNucleoTemp);
  }

  private CpvParentTypeEnum mappaAutocertificazioneMinore(Relative relative) {
    if (AutocertificazioneTipoMinoreEnum.FG.name().equals(relative.getCategoriaParentela().name()))
      return CpvParentTypeEnum.FG;
    if (AutocertificazioneTipoMinoreEnum.FP.name().equals(relative.getCategoriaParentela().name()))
      return CpvParentTypeEnum.FG;
    else return CpvParentTypeEnum.CV;
  }

  private long figlioDaAutocertificazione(Relative relative) {
    if (AutocertificazioneTipoMinoreEnum.FG.name().equals(relative.getCategoriaParentela().name()))
      return 1L;
    if (AutocertificazioneTipoMinoreEnum.FP.name().equals(relative.getCategoriaParentela().name()))
      return 2L;
    else return 0L;
  }

  private List<ComponenteNucleo> mergeList(
      List<ComponenteNucleo> source1, List<ComponenteNucleo> source2) {
    List<ComponenteNucleo> listaNucleo = new ArrayList<>();

    listaNucleo.addAll(source1);

    for (ComponenteNucleo cn2 : source2) {
      boolean found = false;

      for (ComponenteNucleo cn1 : source1) {
        if (cn1.getRelazione()
            .getCpvComponentTaxCode()
            .equals(cn2.getRelazione().getCpvComponentTaxCode())) {
          found = true;
        }
      }

      if (!found) {
        listaNucleo.add(cn2);
      }
    }

    return listaNucleo;
  }

  private void aggiornaDatiNucleoFamiliareAllargato(
      Utente capoFamiglia, final List<ComponenteNucleo> listaNucleo) throws BusinessException {
    ExecutorService ex = Executors.newWorkStealingPool();
    List<Callable<Residente>> tasks = new ArrayList<>();
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    for (final ComponenteNucleo componente : listaNucleo) {

      if (componente.getDatiCittadino() == null
          && LabelFdCUtil.checkIfNotNull(componente.getRelazione())) {
        if (isRelazioneParentaleCompleta(componente)) {
          log.debug("componente=" + componente);
          tasks.add(
              new Callable<Residente>() {

                @Override
                public Residente call() throws Exception {
                  log.debug(
                      "DemograficoImpl -- getNucleoFamiliareAllargato:call inizio: "
                          + componente.getRelazione().getCpvComponentTaxCode());

                  Residente res =
                      instance
                          .getApiDemografico()
                          .demograficoResidenteCodiceFiscaleGet(
                              componente.getRelazione().getCpvComponentTaxCode());
                  log.debug(
                      "DemograficoImpl -- getNucleoFamiliareAllargato:call fine: "
                          + componente.getRelazione().getCpvComponentTaxCode());

                  return res;
                }
              });
        } else {
          log.debug("relazione parentale: " + componente.getRelazione());
          if (!capoFamiglia.isInviataEmailPerRelazioneParentaleErrata()) {
            log.info("INVIARE EMAIL per l'errore");
            inviaMailErroreRelazioneParentale(capoFamiglia, listaNucleo);
            capoFamiglia.setInviataEmailPerRelazioneParentaleErrata(true);
          } else {
            log.info("NON INVIO EMAIL per l'errore su relazione parentale");
          }
        }
      }
    }

    try {
      for (final Future<Residente> future : ex.invokeAll(tasks, 8, TimeUnit.SECONDS)) {
        Residente res = future.get();
        for (final ComponenteNucleo componente : listaNucleo) {
          if ((res != null)
              && (componente.getRelazione().getCpvComponentTaxCode().equals(res.getCpvTaxCode()))) {
            componente.setDatiCittadino(res);
          }
        }
      }
    } catch (InterruptedException | ExecutionException e) {
      log.error("DemograficoImpl --- getNucleoFamiliareAllargato(utente) errore nei thread", e);
      ex.shutdown();

      throw new BusinessException(ERRORE_POOL_THREAD);
    } finally {
      instance.closeConnection();
    }
  }

  private boolean inviaMailErroreRelazioneParentale(
      Utente capoFamiglia, List<ComponenteNucleo> listaNucleo) {

    String mailFrom = BaseServiceImpl.FROM_ADDRESS;

    String mailCC = null;
    if (BaseServiceImpl.CC_ERRORE_ADDRESS != null && !BaseServiceImpl.CC_ERRORE_ADDRESS.isEmpty()) {
      mailCC = BaseServiceImpl.CC_ERRORE_ADDRESS + ";";
    }
    String mailto = BaseServiceImpl.TO_ANAGRAFE_ADDRESS;
    String oggetto =
        "Errore nei dati relazione parentale di: " + capoFamiglia.getCodiceFiscaleOperatore();
    String mailbody =
        "<p> E' stato rilevato un errore nei dati della relazione parentale del codice fiscale: "
            + capoFamiglia.getCodiceFiscaleOperatore();
    mailbody += "</p><br>";
    mailbody += "<p> Di seguito i dati ricevuti da WSO2:" + "</p>";
    mailbody += "<br><br><br>";
    mailbody += "<ul>";
    int componenteNumero = 1;
    for (ComponenteNucleo componente : listaNucleo) {
      mailbody += "<li> Componente nucleo n. " + componenteNumero + "</li>";
      if (componente.getRelazione() != null) {
        mailbody +=
            "<li> Codice fiscale: " + componente.getRelazione().getCpvComponentTaxCode() + "</li>";
        mailbody +=
            "<li> Codice relazione parentale: "
                + componente.getRelazione().getCpvParentType()
                + "</li>";
        mailbody += "<br/>";
        componenteNumero = componenteNumero + 1;
      }
    }
    mailbody += "</ul>";

    ContenutoMessaggio contenutoMessaggio =
        new ContenutoMessaggioBuilder()
            .getInstance()
            .setTo(mailto)
            .setCc(mailCC)
            .setBcc("")
            .setFrom(mailFrom)
            .setSubject(oggetto)
            .setText(mailbody)
            .setSmtpServer(BaseServiceImpl.HOST_SMTP)
            .build();

    try {
      log.debug("mailbody:" + mailbody);
      SendMailUtil.getInstance().sendHtmlMail(contenutoMessaggio);
      log.info("Mail inviata");
    } catch (MessagingException e) {
      log.error(
          "Errore durante l'invio della Mail Errore dati relazione parentale : " + e.getMessage());
      return false;
    }
    return true;
  }

  private boolean isRelazioneParentaleCompleta(final ComponenteNucleo componente) {
    boolean isNotEmpty =
        StringUtils.isNotBlank(componente.getRelazione().getCpvComponentTaxCode())
            && StringUtils.isNotBlank(componente.getRelazione().getCpvParentType());
    log.debug("isNotEmpty=" + isNotEmpty);
    return isNotEmpty;
  }

  @Override
  public List<ComponenteNucleo> getNucleoFamiliare(Utente capoFamiglia)
      throws BusinessException, ApiException {
    if (capoFamiglia == null) {
      return new ArrayList<>();
    }
    if (capoFamiglia.getNucleoFamiliare() != null) {
      log.debug("Trovato NucleoFamiliare NON effettuo chiamata REST");
      return capoFamiglia.getNucleoFamiliare();
    }

    log.debug(
        "DemograficoImpl -- getNucleoFamiliare(utente: "
            + capoFamiglia.getCodiceFiscaleOperatore()
            + ")");

    List<ComponenteNucleo> nucleoFamiliare = new ArrayList<>();

    if (LabelFdCUtil.checkIfNotNull(capoFamiglia.getDatiCittadinoResidente())) {
      for (ItemRelazioneParentale schedaAnagrafica :
          capoFamiglia
              .getDatiCittadinoResidente()
              .getCpvInRegisteredFamily()
              .getCpvBelongsToFamily()) {
        for (ComponenteNucleo cn : this.getNucleoFamiliareAllargato(capoFamiglia)) {
          if (LabelFdCUtil.checkIfNotNull(cn.getDatiCittadino())
              && schedaAnagrafica
                  .getCpvComponentTaxCode()
                  .equals(cn.getDatiCittadino().getCpvTaxCode())) {
            nucleoFamiliare.add(cn);
          }
        }
      }
    }
    capoFamiglia.setNucleoFamiliare(nucleoFamiliare);
    return nucleoFamiliare;
  }

  @Override
  public TesseraElettorale getDatiTesseraElettorale(String codiceFiscale)
      throws BusinessException, ApiException {

    log.debug("DemograficoImpl -- getDatiTesseraElettorale(cf: '" + codiceFiscale + "'");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      TesseraElettorale tesseraElettorale =
          instance.getApiDemografico().demograficoTesseraElettoraleCodiceFiscaleGet(codiceFiscale);
      log.debug("TesseraElettorale: " + tesseraElettorale);
      return tesseraElettorale;
    } catch (BusinessException e) {
      log.error(
          "DemograficoImpl -- getDatiTesseraElettorale: errore di business nel recupero dei dati tessera elettorale da codice fiscale",
          e);
      throw new BusinessException(ERRORE_API_DEMOGRAFICO);
    } catch (WebApplicationException e) {
      log.error(
          "DemograficoImpl -- getDatiTesseraElettorale: errore nel recupero dei dati della scheda elettorale: "
              + e);
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("DemograficoImpl -- getDatiTesseraElettorale: RunTimeException " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("demografico"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<ComponenteNucleo> getFigliMinorenni(Utente capoFamiglia)
      throws BusinessException, ApiException {
    log.debug(
        "DemograficoImpl -- getFigliMinorenni(utente: "
            + capoFamiglia.getCodiceFiscaleOperatore()
            + ")");

    List<ComponenteNucleo> nucleoFamiliare = this.getNucleoFamiliareAllargato(capoFamiglia);
    List<ComponenteNucleo> listaFigliMinorenni = new ArrayList<>();

    for (ComponenteNucleo cn : nucleoFamiliare) {

      if (LabelFdCUtil.checkIfNotNull(cn.getDatiCittadino())
          && (cn.isFiglioStatoCivile() || (cn.getAutodichiarazioneFiglio() != null))
          && (!LocalDateUtil.isMaggioreVariabileSuDB(cn.getDatiCittadino().getCpvDateOfBirth())
              && (cn.getRelazione()
                      .getCpvParentType()
                      .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FG.name())
                  || cn.getRelazione()
                      .getCpvParentType()
                      .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FN.name())
                  || cn.getRelazione()
                      .getCpvParentType()
                      .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FS.name())))) {
        listaFigliMinorenni.add(cn);
      }
    }

    return listaFigliMinorenni;
  }

  @Override
  public List<ComponenteNucleo> getFigliMinori16anni(Utente capoFamiglia)
      throws BusinessException, ApiException {
    log.debug(
        "DemograficoImpl -- getFigliMinori16anni(utente: "
            + capoFamiglia.getCodiceFiscaleOperatore()
            + ")");

    List<ComponenteNucleo> nucleoFamiliare = this.getNucleoFamiliareAllargato(capoFamiglia);
    List<ComponenteNucleo> listaFigliMinorenni = new ArrayList<>();

    for (ComponenteNucleo cn : nucleoFamiliare) {
      if (LabelFdCUtil.checkIfNotNull(cn.getDatiCittadino())) {
        if ((cn.isFiglioStatoCivile() || (cn.getAutodichiarazioneFiglio() != null))
            && (!LocalDateUtil.isMaggioreVariabileSuDB(cn.getDatiCittadino().getCpvDateOfBirth())
                && (cn.getRelazione()
                        .getCpvParentType()
                        .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FG.name())
                    || cn.getRelazione()
                        .getCpvParentType()
                        .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FN.name())
                    || cn.getRelazione()
                        .getCpvParentType()
                        .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FS.name())))) {
          listaFigliMinorenni.add(cn);
        }
      }
    }

    return listaFigliMinorenni;
  }

  @Override
  public DatiCatastali getDatiCatastali(Utente capoFamiglia)
      throws BusinessException, ApiException {

    log.debug("DemograficoImpl -- getDatiCatastali");
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      DatiCatastali dati =
          instance
              .getApiDemografico()
              .oggettiDatiCatastaliCodiceIndirizzoGet(
                  capoFamiglia
                      .getDatiCittadinoResidente()
                      .getCpvHasAddress()
                      .getGenovaOntoFlatNumberCode());
      return dati;
    } catch (BusinessException e) {
      log.error("DemograficoImpl -- getDatiCatastali: errore API Demografico:", e);
      throw new BusinessException(ERRORE_API_DEMOGRAFICO);
    } catch (WebApplicationException e) {
      log.error("DemograficoImpl -- getDatiCatastali: errore nella Response:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("DemograficoImpl -- getDatiCatastali: RunTimeException " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("demografico"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public Residente getDatiResidente(Utente utente) throws BusinessException, ApiException {

    Residente residente = this.getDatiResidente(utente.getCodiceFiscaleOperatore());

    if (residente != null) {
      // utente.setIdAnagrafica(residente.getCpvPersonID());
      // utente.setCognome(residente.getCpvFamilyName());
      // utente.setNome(residente.getCpvGivenName());
      return residente;
    } else {
      return new Residente();
    }
  }

  @Override
  public Residente getDatiResidenteDaSessione(String cf, Utente capoFamiglia) {
    try {
      for (ComponenteNucleo cn : this.getNucleoFamiliareAllargato(capoFamiglia)) {
        if (LabelFdCUtil.checkIfNotNull(cn.getDatiCittadino())
            && cf.equalsIgnoreCase(cn.getDatiCittadino().getCpvTaxCode())) {
          return cn.getDatiCittadino();
        }
      }
    } catch (BusinessException | ApiException e) {
      log.debug("DemograficoImpl -- getDatiResidenteDaSessione -- errore", e);
    }

    return new Residente();
  }

  @Override
  public boolean testGenitoreDiFigli(String codiceFiscale) throws BusinessException, ApiException {

    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      instance.getApiDemografico().oggettiStatoCivileGenitoreCodiceFiscaleFiglioGet(codiceFiscale);
      return true;
    } catch (BusinessException e) {
      log.error("Errore API Demografico testGenitoreDiFigli:", e);
      throw new BusinessException(ERRORE_API_DEMOGRAFICO);
    } catch (WebApplicationException e) {
      log.error("Errore nella Response testGenitoreDiFigli:" + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error("DemograficoImpl -- testGenitoreDiFigli: RunTimeException " + e.getMessage());
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("demografico"));
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<MinoreConvivente> getMinoriDaSchedaAnagrafica(Utente capoFamiglia)
      throws BusinessException, ApiException {

    log.debug("DemograficoImpl -- getFigliSchedaAnagrafica");
    List<MinoreConvivente> listaFigli = new ArrayList<>();

    for (ComponenteNucleo componente : this.getNucleoFamiliareAllargato(capoFamiglia)) {
      if (LabelFdCUtil.checkIfNotNull(componente.getDatiCittadino())
          && !LocalDateUtil.isMaggiorenne(componente.getDatiCittadino().getCpvDateOfBirth())) {
        MinoreConvivente minore = new MinoreConvivente();

        minore.setCodiceFiscale(componente.getDatiCittadino().getCpvTaxCode());
        minore.setCognome(componente.getDatiCittadino().getCpvFamilyName());
        minore.setNome(componente.getDatiCittadino().getCpvGivenName());
        minore.setDataNascita(componente.getDatiCittadino().getCpvDateOfBirth());
        minore.setDatiDemografico(componente.getDatiCittadino());
        minore.setIdPerson(0L);
        minore.setTipoParentela(
            (componente.getSorgente().name().equals(SorgenteDatiNucleoEnum.SCHEDA_ANAGRAFICA.name())
                ? AutocertificazioneTipoMinoreEnum.MC
                : AutocertificazioneTipoMinoreEnum.FG));

        listaFigli.add(minore);
      }
    }

    log.debug("listaFigli: " + listaFigli.size());
    return listaFigli;
  }

  @Override
  public List<MinoreConvivente> getFigliMieiDati(Utente capoFamiglia)
      throws BusinessException, ApiException {
    List<MinoreConvivente> nucleoFamiliare = new ArrayList<>();

    if (capoFamiglia == null) {
      log.debug("DemograficoImpl -- getFigliMieiDati(utente: null)");
    } else {
      log.debug(
          "DemograficoImpl -- getFigliMieiDati(utente: "
              + capoFamiglia.getCodiceFiscaleOperatore()
              + ")");

      for (ComponenteNucleo cn : this.getFigli(capoFamiglia)) {
        log.debug("DemograficoImpl -- getFigliMieiDati(ComponenteNucleo: " + cn + ")");
        if (cn != null
                && LabelFdCUtil.checkIfNotNull(cn.getDatiCittadino())
                && cn.isFiglioStatoCivile()
            // || cn.getAutodichiarazioneFiglio() == null
            || convertiAutoDichiarazioneFiglio(cn)) {
          nucleoFamiliare.add(popolaMinoreConvivente(cn));
        }
      }
    }

    log.debug("DemograficoImpl -- getFigliMieiDati(utente): " + nucleoFamiliare.size());
    return nucleoFamiliare;
  }

  @Override
  public List<MinoreConvivente> getFigliPerAutodichiarazione(Utente capoFamiglia)
      throws BusinessException, ApiException {
    List<MinoreConvivente> nucleoFamiliare = new ArrayList<>();

    if (capoFamiglia == null) {
      log.debug("DemograficoImpl -- getFigliPerAutodichiarazione(utente: null)");
    } else {
      log.debug(
          "DemograficoImpl -- getFigliPerAutodichiarazione(utente: "
              + capoFamiglia.getCodiceFiscaleOperatore()
              + ")");

      for (ComponenteNucleo cn : capoFamiglia.getNucleoFamiliareAllargato()) {

        log.debug("DemograficoImpl -- getFigliPerAutodichiarazione(ComponenteNucleo: " + cn + ")");
        if (!cn.isFiglioStatoCivile()
            && LabelFdCUtil.checkIfNotNull(cn.getDatiCittadino())
            && !cn.getDatiCittadino()
                .getCpvTaxCode()
                .equalsIgnoreCase(capoFamiglia.getCodiceFiscaleOperatore())
            && !LocalDateUtil.isMaggioreVariabileSuDB(cn.getDatiCittadino().getCpvDateOfBirth())
        // && (cn.getRelazione().getCpvParentType()
        // .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FG.name())
        // || cn.getRelazione().getCpvParentType()
        // .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FN.name())
        // || cn.getRelazione().getCpvParentType()
        // .equalsIgnoreCase(ItemRelazioneParentale.CpvParentTypeEnum.FS.name())
        // ||
        // cn.getSorgente().name().equalsIgnoreCase(SorgenteDatiNucleoEnum.AUTODICHIARAZIONE.name()))
        ) {
          nucleoFamiliare.add(popolaMinoreConvivente(cn));
        }
      }
    }
    log.debug("DemograficoImpl -- getFigliPerAutodichiarazione(utente): " + nucleoFamiliare);
    return nucleoFamiliare;
  }

  @Override
  public List<ComponenteNucleo> getFigliNonPerAutodichiarazione(Utente capoFamiglia)
      throws BusinessException, ApiException {
    List<ComponenteNucleo> componenteNucleo = new ArrayList<>();

    if (capoFamiglia == null) {
      log.debug("DemograficoImpl -- getFigliNonPerAutodichiarazione(utente: null)");
    } else {
      log.debug(
          "DemograficoImpl -- getFigliNonPerAutodichiarazione(utente: "
              + capoFamiglia.getCodiceFiscaleOperatore()
              + ")");

      for (ComponenteNucleo cn : this.getNucleoFamiliareAllargato(capoFamiglia)) {

        if (cn.isFiglioStatoCivile()) {
          componenteNucleo.add(cn);
        }
      }
    }

    log.debug("DemograficoImpl -- getFigliNonPerAutodichiarazione(utente): " + componenteNucleo);
    return componenteNucleo;
  }

  @Override
  public List<MinoreConvivente> getFigliPerScolastici(Utente capoFamiglia)
      throws BusinessException, ApiException {
    List<MinoreConvivente> nucleoFamiliare = new ArrayList<>();

    if (capoFamiglia == null) {
      log.debug("DemograficoImpl -- getFigliPerScolastici(utente: null)");
    } else {
      log.debug(
          "DemograficoImpl -- getFigliPerScolastici(utente: "
              + capoFamiglia.getCodiceFiscaleOperatore()
              + ")");

      for (ComponenteNucleo cn : this.getFigli(capoFamiglia)) {
        if (LabelFdCUtil.checkIfNotNull(cn.getDatiCittadino())
            && cn.isCoResidente()
            && (cn.isFiglioStatoCivile() || convertiAutoDichiarazioneFiglio(cn))) {
          nucleoFamiliare.add(popolaMinoreConvivente(cn));
        }
      }
    }

    log.debug("DemograficoImpl -- getFigliPerScolastici(utente): " + nucleoFamiliare.size());
    return nucleoFamiliare;
  }

  @Override
  public List<MinoreConvivente> getFigliPerIscrizioneRefezione(Utente capoFamiglia)
      throws BusinessException, ApiException {
    List<MinoreConvivente> nucleoFamiliare = new ArrayList<>();

    if (capoFamiglia == null) {
      log.debug("DemograficoImpl -- getFigliPerIscrizioneRefezione(utente: null)");
    } else {
      log.debug(
          "DemograficoImpl -- getFigliPerIscrizioneRefezione(utente: "
              + capoFamiglia.getCodiceFiscaleOperatore()
              + ")");

      for (ComponenteNucleo cn : this.getFigli(capoFamiglia)) {
        if (LabelFdCUtil.checkIfNotNull(cn.getDatiCittadino())
            && cn.getBloccoAutodichiarazione() != 1L
            && cn.isCoResidente()
            && (cn.isFiglioStatoCivile() || convertiAutoDichiarazioneFiglio(cn))) {
          nucleoFamiliare.add(popolaMinoreConvivente(cn));
        }
      }
    }
    log.debug(
        "DemograficoImpl -- getFigliPerIscrizioneRefezione(utente): " + nucleoFamiliare.size());
    return nucleoFamiliare;
  }

  @Override
  public MinoreConvivente popolaMinoreConvivente(ComponenteNucleo cn) {
    log.debug(
        "DemograficoImpl ------- popolaMinoreConvivente: cf="
            + (cn.getDatiCittadino() != null
                ? cn.getDatiCittadino().getCpvTaxCode()
                : " getDatiCittadino NULLO")
            + ", statocivile="
            + cn.isFiglioStatoCivile()
            + ", residente="
            + cn.isCoResidente()
            + ", cpvParentType="
            + cn.getRelazione().getCpvParentType());

    return new MinoreConviventeBuilder()
        .setCodiceFiscale(cn.getDatiCittadino().getCpvTaxCode())
        .setIdPerson(cn.getIdPerson())
        .setCognome(cn.getDatiCittadino().getCpvFamilyName())
        .setTipoParentela(ricostruiscoAutocertificazioneTipoMinoreEnum(cn))
        .setNome(cn.getDatiCittadino().getCpvGivenName())
        .setDatiDemografico(cn.getDatiCittadino())
        .setAutodichiarazioneFiglio(convertiAutoDichiarazioneFiglio(cn))
        .setBloccoAutodichiarazione(convertiAutodichiarazioneBlocco(cn))
        .setDataNascita(cn.getDatiCittadino().getCpvDateOfBirth())
        .setFiglioStatoCivile(cn.isFiglioStatoCivile())
        .setMinoreResidente(cn.isCoResidente())
        .build();
  }

  private AutocertificazioneTipoMinoreEnum ricostruiscoAutocertificazioneTipoMinoreEnum(
      ComponenteNucleo cn) {
    if (cn.isFiglioStatoCivile()) return AutocertificazioneTipoMinoreEnum.FG;
    else if (cn.getAutodichiarazioneFiglio() == null) {
      return AutocertificazioneTipoMinoreEnum.NN;
    } else if (cn.getAutodichiarazioneFiglio() != null) {
      if (cn.getAutodichiarazioneFiglio() == 1L) return AutocertificazioneTipoMinoreEnum.FG;
      else if (cn.getAutodichiarazioneFiglio() == 2L) return AutocertificazioneTipoMinoreEnum.FP;
      else return AutocertificazioneTipoMinoreEnum.MC;
    } else {
      return AutocertificazioneTipoMinoreEnum.NN;
    }
  }

  private boolean convertiAutodichiarazioneBlocco(ComponenteNucleo cn) {
    if (cn.getBloccoAutodichiarazione() != null) return cn.getBloccoAutodichiarazione() == 1L;
    else return false;
  }

  private boolean convertiAutoDichiarazioneFiglio(ComponenteNucleo cn) {
    if (cn.getAutodichiarazioneFiglio() != null) return cn.getAutodichiarazioneFiglio() > 0L;
    else return false;
  }

  @Override
  public StrutturaGenitori getGenitoriDaCfBambino(String cfBambino)
      throws BusinessException, ApiException {
    StrutturaGenitori genitori = null;
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      genitori =
          instance.getApiDemografico().oggettiStatoCivileGenitoreCodiceFiscaleFiglioGet(cfBambino);

      log.debug("CP genitori = " + genitori);

      return genitori;
    } catch (BusinessException e) {
      log.error("DemograficoImpl -- getGenitoriDaCfBambino: errore API demografico:", e);
      throw new BusinessException(ERRORE_API_DEMOGRAFICO);
    } catch (WebApplicationException e) {
      log.error(
          "DemograficoImpl -- getGenitoriDaCfBambino: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.debug(
          "DemograficoImpl -- getGenitoriDaCfBambino: errore durante la chiamata delle API demografico ",
          e);
      // throw new
      // RestartResponseAtInterceptPageException(ErrorPage.class);
      return genitori;
    } finally {
      instance.closeConnection();
    }
  }

  @Override
  public List<Residente> listaPersoneInAnagrafeDelDemografico(Utente utente)
      throws BusinessException, ApiException {
    List<Residente> lista = new ArrayList<Residente>();
    List<Residente> listaOrdinata = new ArrayList<Residente>();

    if (LabelFdCUtil.checkIfNotNull(utente.getDatiCittadinoResidente())
        && LabelFdCUtil.checkIfNotNull(
            utente.getDatiCittadinoResidente().getCpvInRegisteredFamily())
        && LabelFdCUtil.checkIfNotNull(
            utente
                .getDatiCittadinoResidente()
                .getCpvInRegisteredFamily()
                .getCpvBelongsToFamily())) {

      List<ItemRelazioneParentale> belongsToFamily =
          utente.getDatiCittadinoResidente().getCpvInRegisteredFamily().getCpvBelongsToFamily();

      for (ItemRelazioneParentale elem : belongsToFamily) {
        if (LabelFdCUtil.checkIfNotNull(elem)
            && LabelFdCUtil.checkIfNotNull(elem.getCpvComponentTaxCode())) {
          Residente dati = getDatiResidente(elem.getCpvComponentTaxCode());

          if (LabelFdCUtil.checkIfNotNull(dati)) {
            lista.add(dati);
          }
        }
      }
    }

    Comparator<Residente> comparator =
        Comparator.comparing(
            Residente::getCpvTaxCode, Comparator.nullsLast(Comparator.naturalOrder()));

    listaOrdinata = lista.stream().sorted(comparator).collect(Collectors.toList());

    return listaOrdinata;
  }
}
