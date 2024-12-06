package it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.impl;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteTari;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.PrestazioneDaErogareEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneAttestazioneRequestRicercaCF.StatodomandaPrestazioneEnum;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCF200;
import it.liguriadigitale.ponmetro.inps.modi.model.ConsultazioneIndicatoreCFBody;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.inps.modi.model.Ordinario;
import it.liguriadigitale.ponmetro.portale.business.exception.ApiException;
import it.liguriadigitale.ponmetro.portale.business.inpsmodi.impl.InpsModiHelper;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUnoTariNetribe;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.mapper.DatiIseeTariNetribeMapper;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.mapper.DatiResidentiTuttiNucleiTariNetribeMapper;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.mapper.DatiRichiestaContributoTariNetribeMapper;
import it.liguriadigitale.ponmetro.portale.business.tributi.tarinetribe.service.ServiziTariContributo;
import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiDomandaContributoTari;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiIseeExtend;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiPersoneACaricoContributoTariNetribe;
import it.liguriadigitale.ponmetro.portale.pojo.tributi.tarinetribe.DatiRichiestaContributoTariNetribeResult;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.error.ErroreServiziPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.step.StepFdC;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.tarinetribe.model.AgevolazioneTariffariaTariInformazioni;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiAgevolazioneTariffariaTari;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIsee;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiIseeRichiederenteECoresidenti;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiModalitaPagamentoAgevolazioneTariffariaTari.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiResidenti;
import it.liguriadigitale.ponmetro.tarinetribe.model.DatiRichiestaAgevolazioneTariffariaTari;
import it.liguriadigitale.ponmetro.tarinetribe.model.FileAllegato;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.modelmapper.ModelMapper;

public class ServiziTariContributoImpl implements ServiziTariContributo {

  private Log log = LogFactory.getLog(getClass());

  private static final String ERRORE_API_TARI = "Errore di connessione alle API Contributo TARI";

  @Override
  public List<DatiAgevolazioneTariffariaTari> getDomandeContributoTari(
      String codiceFiscale, Integer anno) throws ApiException, BusinessException {

    ServiceLocatorLivelloUnoTariNetribe instance =
        ServiceLocatorLivelloUnoTariNetribe.getInstance();

    try {
      return instance
          .getApiTariAgevolazioneTariffaria()
          .getAgevolazioneTariffariaTari(codiceFiscale, anno);

    } catch (BusinessException e) {
      log.error(
          "ServiziTariContributoImpl -- getDomandeContributoTari: errore API TARI Netribe:", e);
      throw new BusinessException(ERRORE_API_TARI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTariContributoImpl -- getDomandeContributoTari: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTariContributoImpl -- getDomandeContributoTari: errore durante la chiamata delle API TARI Netribe ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Contributo TARI"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public ConsultazioneAttestazioneCF200 getAttestazioneISEEModi(Utente utente) {
    log.debug("CP getAttestazioneISEEModi");

    try {
      String tipoIndicatore = "Ordinario";
      ConsultazioneAttestazioneCF200 attestazione =
          InpsModiHelper.getAttestazioneISEE(
              utente,
              PrestazioneDaErogareEnum.A2_06,
              StatodomandaPrestazioneEnum.EROGATA,
              tipoIndicatore);

      return attestazione;
    } catch (BusinessException e) {
      log.error("Errore getAttestazioneISEEModi Contributo TARI = " + e.getMessage(), e);
      return null;
    }
  }

  private ConsultazioneIndicatoreCF200 indicatoreIseeIS(String codiceFiscaleIS)
      throws BusinessException {

    ConsultazioneIndicatoreCFBody bodyIndicatoreIseeModi =
        InpsModiHelper.createConsultazioneIndicatoreCFBody(
            codiceFiscaleIS,
            PrestazioneDaErogareEnum.A2_06,
            StatodomandaPrestazioneEnum.EROGATA,
            "Ordinario");

    ConsultazioneIndicatoreCF200 indicatoreIseeModi =
        ServiceLocator.getInstance()
            .getServiziINPSModi()
            .consultazioneIndicatoreCF(bodyIndicatoreIseeModi);

    return indicatoreIseeModi;
  }

  private DatiIseeExtend getDatiIseeIS(
      String codiceFiscaleIS, DatiDomandaContributoTari datiDomanda) {

    DatiIseeExtend datiIsee = new DatiIseeExtend();

    try {

      ConsultazioneIndicatoreCF200 iseeTitolare = indicatoreIseeIS(codiceFiscaleIS);

      if (!codiceFiscaleIS.equalsIgnoreCase(datiDomanda.getCodiceFiscale())) {
        Residente datiAltroIS =
            ServiceLocator.getInstance().getServizioDemografico().getDatiResidente(codiceFiscaleIS);
        datiIsee.setNominativo(datiAltroIS.getRdfsLabel());
      } else {
        datiIsee.setNominativo(datiDomanda.getNominativo());
      }

      if (LabelFdCUtil.checkIfNotNull(iseeTitolare)) {

        datiIsee.setIseePresentato(true);

        datiIsee.setCodiceFiscale(codiceFiscaleIS);

        String iseeValore =
            iseeTitolare
                .getConsultazioneIndicatoreResponse()
                .getConsultazioneIndicatoreResult()
                .getXmlEsitoIndicatoreDecoded()
                .getIndicatore()
                .getISEE();
        datiIsee.setImporto(iseeValore);

        datiIsee.setImportoDouble(Double.valueOf(iseeValore));

        LocalDate presentazioneIsee =
            iseeTitolare
                .getConsultazioneIndicatoreResponse()
                .getConsultazioneIndicatoreResult()
                .getXmlEsitoIndicatoreDecoded()
                .getIndicatore()
                .getDataPresentazione();
        datiIsee.setDataPresentazione(presentazioneIsee);

        String protocolloIsee =
            iseeTitolare
                .getConsultazioneIndicatoreResponse()
                .getConsultazioneIndicatoreResult()
                .getXmlEsitoIndicatoreDecoded()
                .getIndicatore()
                .getProtocolloDSU();
        datiIsee.setProtocolloDSU(protocolloIsee);

      } else {
        datiIsee.setIseePresentato(false);
      }

    } catch (BusinessException | ApiException e) {
      log.error("Errore isee in tari contrinbuto : " + e.getMessage());
      return null;
    }

    return datiIsee;
  }

  @Override
  public Ordinario getIseeOrdinario(Utente utente) {
    log.debug("CP getIseeOrdinario");

    ConsultazioneAttestazioneCF200 attestazione = getAttestazioneISEEModi(utente);

    if (LabelFdCUtil.checkIfNull(attestazione)) {
      return null;
    }

    return InpsModiHelper.getOrdinarioByAttestazioneIsee(attestazione);
  }

  private DatiDomandaContributoTari getListaTuttiNucleiTari(
      Utente utente, DatiDomandaContributoTari datiDomanda) {
    List<ResidenteTari> listaTuttiNucleiTari = new ArrayList<ResidenteTari>();
    try {
      listaTuttiNucleiTari =
          ServiceLocator.getInstance()
              .getServizioDemografico()
              .getTuttiConviventiTuttiNuclei(utente);
    } catch (BusinessException | ApiException e) {
      log.error("Errore getListaTuttiNucleiTari: " + e.getMessage());
    }
    datiDomanda.setListaTuttiNucleiTari(listaTuttiNucleiTari);

    List<DatiResidenti> listaTuttiResidenti = new ArrayList<DatiResidenti>();
    for (ResidenteTari elemTuttiNuclei : datiDomanda.getListaTuttiNucleiTari()) {
      ModelMapper mapper = new ModelMapper();
      mapper.addMappings(new DatiResidentiTuttiNucleiTariNetribeMapper());

      DatiResidenti datiResidenti = mapper.map(elemTuttiNuclei, DatiResidenti.class);
      listaTuttiResidenti.add(datiResidenti);
    }

    datiDomanda.setListaDatiResidenti(listaTuttiResidenti);

    return datiDomanda;
  }

  private DatiDomandaContributoTari getListaTuttiIS(DatiDomandaContributoTari datiDomanda) {
    List<ResidenteTari> listaTuttiIS = new ArrayList<ResidenteTari>();

    listaTuttiIS =
        datiDomanda.getListaTuttiNucleiTari().stream()
            .filter(elem -> elem.getCpvParentType().equalsIgnoreCase("IS"))
            .collect(Collectors.toList());

    datiDomanda.setListaTuttiIS(listaTuttiIS);

    return datiDomanda;
  }

  private DatiDomandaContributoTari getListaNucleoAnagrafico(
      Utente utente, DatiDomandaContributoTari datiDomanda) {
    List<Residente> listaPersoneInAnagrafeDelDemografico = new ArrayList<Residente>();
    try {
      listaPersoneInAnagrafeDelDemografico =
          ServiceLocator.getInstance()
              .getServizioDemografico()
              .listaPersoneInAnagrafeDelDemografico(utente);
    } catch (BusinessException | ApiException e) {
      log.error("Errore getListaPersoneNucleoAnagrafico: " + e.getMessage());
    }
    datiDomanda.setListaComponentiNucleoAnagrafe(listaPersoneInAnagrafeDelDemografico);
    datiDomanda.setNumeroComponenti(datiDomanda.getListaComponentiNucleoAnagrafe().size());
    return datiDomanda;
  }

  private DatiDomandaContributoTari getListaNucleoIsee(
      Utente utente, DatiDomandaContributoTari datiDomanda) {

    Ordinario attestazioneRichiedente = getIseeOrdinario(utente);

    if (LabelFdCUtil.checkIfNotNull(attestazioneRichiedente)
        && LabelFdCUtil.checkIfNotNull(attestazioneRichiedente.getNucleoFamiliare())
        && LabelFdCUtil.checkIfNotNull(
            attestazioneRichiedente.getNucleoFamiliare().getComponenteNucleo())) {
      datiDomanda.setListaComponentiNucleoIsee(
          attestazioneRichiedente.getNucleoFamiliare().getComponenteNucleo());
    }
    return datiDomanda;
  }

  private DatiDomandaContributoTari getDatiIseeDiTuttiGliIS(
      Utente utente, DatiDomandaContributoTari datiDomanda) {

    List<DatiIseeExtend> listaISEETuttiIS = new ArrayList<>();

    for (ResidenteTari elemISTari : datiDomanda.getListaTuttiIS()) {

      DatiIseeExtend datiIseeExtend =
          getDatiIseeIS(elemISTari.getCpvComponentTaxCode(), datiDomanda);

      listaISEETuttiIS.add(datiIseeExtend);
    }

    log.debug("CP listaISEETuttiIS = " + listaISEETuttiIS);

    datiDomanda.setDatiIseeCompleti(listaISEETuttiIS);

    Optional<DatiIseeExtend> unISNonHaPresentatoISEE =
        listaISEETuttiIS.stream().filter(elem -> !elem.isIseePresentato()).findFirst();
    if (unISNonHaPresentatoISEE.isPresent()) {
      datiDomanda.setIseePresentato(false);
    } else {
      datiDomanda.setIseePresentato(true);
    }

    datiDomanda.setImportoIseeNelRange(false);

    if (datiDomanda.isIseePresentato()) {
      DatiIseeRichiederenteECoresidenti datiIseeRichiederenteECoresidenti =
          new DatiIseeRichiederenteECoresidenti();
      List<DatiIsee> listaIsee = new ArrayList<DatiIsee>();

      for (DatiIseeExtend elemIsee : datiDomanda.getDatiIseeCompleti()) {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new DatiIseeTariNetribeMapper());

        DatiIsee datiIsee = mapper.map(elemIsee, DatiIsee.class);
        listaIsee.add(datiIsee);

        datiIseeRichiederenteECoresidenti.setDatiCompletiIsee(listaIsee);

        if (!LabelFdCUtil.checkEmptyList(listaISEETuttiIS)) {
          Double totaleIseeTuttiIS =
              listaISEETuttiIS.stream()
                  .map(elem -> Double.valueOf(elem.getImporto()))
                  .collect(Collectors.summingDouble(Double::doubleValue));

          datiIseeRichiederenteECoresidenti.setValoreTotaleIsee(totaleIseeTuttiIS);

          datiDomanda.setDatiIseeTuttiNuclei(datiIseeRichiederenteECoresidenti);

          datiDomanda.setImportoIsee(totaleIseeTuttiIS);

          Double iseeMassimo = datiDomanda.getIseeMassimo();

          if (LabelFdCUtil.checkIfNotNull(totaleIseeTuttiIS)
              && totaleIseeTuttiIS.compareTo(iseeMassimo) < 0) {
            datiDomanda.setImportoIseeNelRange(true);
          }
        }
      }
    }

    return datiDomanda;
  }

  @Override
  public void setDatiDomanda(Utente utente, DatiDomandaContributoTari datiDomanda) {

    getListaTuttiNucleiTari(utente, datiDomanda);

    getListaTuttiIS(datiDomanda);

    getListaNucleoAnagrafico(utente, datiDomanda);

    if (check70AnniRichiedente(datiDomanda)) {
      getCasiOver70(datiDomanda);
    } else {
      getCasiConFigli(datiDomanda);
    }

    if (datiDomanda.isRequisitiMinimiEtaENucleo()) {
      getDatiIseeDiTuttiGliIS(utente, datiDomanda);

      getListaNucleoIsee(utente, datiDomanda);

      getControlloNucleoAnagrafeNucleoIseePersoneACarico(datiDomanda);
    }

    getDomandaPresentabile(datiDomanda);

    log.debug("CP setDatiDomanda = " + datiDomanda);
  }

  private void getDomandaPresentabile(DatiDomandaContributoTari datiDomanda) {
    datiDomanda.setDomandaPresentabile(
        datiDomanda.isRequisitiMinimiEtaENucleo()
            && datiDomanda.isImportoIseeNelRange()
            && datiDomanda.isNucleiAnagrafeIseeUguali());
  }

  private DatiDomandaContributoTari getControlloNucleoAnagrafeNucleoIseePersoneACarico(
      DatiDomandaContributoTari datiDomanda) {
    boolean checkNucleiAngrafeIsee = false;
    if (LabelFdCUtil.checkIfNotNull(datiDomanda.getListaComponentiNucleoIsee())
        && LabelFdCUtil.checkIfNotNull(datiDomanda.getListaComponentiNucleoAnagrafe())
        && !LabelFdCUtil.checkEmptyList(datiDomanda.getListaComponentiNucleoIsee())
        && !LabelFdCUtil.checkEmptyList(datiDomanda.getListaComponentiNucleoAnagrafe())) {
      checkNucleiAngrafeIsee =
          InpsModiHelper.checkNucleoIseeUgualeNucleoAnagrafico(
              datiDomanda.getListaComponentiNucleoIsee(),
              datiDomanda.getListaComponentiNucleoAnagrafe());
    }
    datiDomanda.setNucleiAnagrafeIseeUguali(checkNucleiAngrafeIsee);

    if (checkNucleiAngrafeIsee) {
      List<NucleoFamiliareComponenteNucleoInner> listaNucleoIseeSenzaRichiedente =
          new ArrayList<NucleoFamiliareComponenteNucleoInner>();
      if (LabelFdCUtil.checkIfNotNull(datiDomanda.getListaComponentiNucleoIsee())) {

        listaNucleoIseeSenzaRichiedente =
            datiDomanda.getListaComponentiNucleoIsee().stream()
                .filter(
                    elemIsee ->
                        !elemIsee
                            .getCodiceFiscale()
                            .equalsIgnoreCase(datiDomanda.getCodiceFiscale()))
                .collect(Collectors.toList());

        List<DatiPersoneACaricoContributoTariNetribe> listaACarico = new ArrayList<>();

        for (NucleoFamiliareComponenteNucleoInner membroIsee : listaNucleoIseeSenzaRichiedente) {
          DatiPersoneACaricoContributoTariNetribe membroACarico =
              new DatiPersoneACaricoContributoTariNetribe();

          membroACarico.setCodiceFiscale(membroIsee.getCodiceFiscale());
          membroACarico.setNome(membroIsee.getNome());
          membroACarico.setCognome(membroIsee.getCognome());
          membroACarico.setFlagIsACarico(null);

          listaACarico.add(membroACarico);
        }

        datiDomanda.setListaComponentiNucleoIseeACarico(listaACarico);
      }
    }

    return datiDomanda;
  }

  private boolean check70AnniRichiedente(DatiDomandaContributoTari datiDomanda) {
    Integer eta = LocalDateUtil.calcolaEta(datiDomanda.getDataNascita());
    Integer settantanni = 70;

    return eta.compareTo(settantanni) >= 0;
  }

  private DatiDomandaContributoTari getCasiOver70(DatiDomandaContributoTari datiDomanda) {

    boolean isOver70ConAltriIS =
        (LabelFdCUtil.checkIfNotNull(datiDomanda.getListaTuttiIS())
            && !LabelFdCUtil.checkEmptyList(datiDomanda.getListaTuttiIS())
            && datiDomanda.getListaTuttiIS().size() > 1);
    datiDomanda.setOver70ConAltriIs(isOver70ConAltriIS);

    boolean isOver70ConUnCoresidente =
        (LabelFdCUtil.checkIfNotNull(datiDomanda.getNumeroComponenti())
            && datiDomanda.getNumeroComponenti() == 2);
    datiDomanda.setOver70ConUnCoresidente(isOver70ConUnCoresidente);

    boolean isOver70DaSolo = !isOver70ConAltriIS && !isOver70ConUnCoresidente;
    datiDomanda.setOver70DaSolo(isOver70DaSolo);

    boolean isRequisitiMinimiEtaENucleo = isOver70ConUnCoresidente || isOver70DaSolo;
    datiDomanda.setRequisitiMinimiEtaENucleo(isRequisitiMinimiEtaENucleo);

    datiDomanda.setNucleoFamiliareConDa1A4FigliUnder26(false);

    datiDomanda.setUnder70(false);

    datiDomanda.setFlagPensionatoSuperiore70anni(1);

    int unoACarico = isOver70ConUnCoresidente ? 1 : 0;
    datiDomanda.setFlagPensionatoSuperiore70anni1FamiliareACarico(unoACarico);

    datiDomanda.setFlagBeneficarioConFigliACaricoMax4(0);

    return datiDomanda;
  }

  private DatiDomandaContributoTari getCasiConFigli(DatiDomandaContributoTari datiDomanda) {
    Integer ventiseianni = 26;

    if (LabelFdCUtil.checkIfNotNull(datiDomanda.getListaComponentiNucleoAnagrafe())) {
      List<Residente> listaNucleoAnagrafeFino26Anni =
          datiDomanda.getListaComponentiNucleoAnagrafe().stream()
              .filter(
                  elemNucleoAnagrafe ->
                      LocalDateUtil.calcolaEta(elemNucleoAnagrafe.getCpvDateOfBirth())
                          <= ventiseianni)
              .collect(Collectors.toList());

      ValueRange rangeNumeroFigli = ValueRange.of(1, 4);
      boolean isDa1A4FigliUnder26 =
          rangeNumeroFigli.isValidIntValue(listaNucleoAnagrafeFino26Anni.size());

      datiDomanda.setNucleoFamiliareConDa1A4FigliUnder26(isDa1A4FigliUnder26);

      int bambiniACarico = isDa1A4FigliUnder26 ? 1 : 0;
      datiDomanda.setFlagBeneficarioConFigliACaricoMax4(bambiniACarico);

      datiDomanda.setRequisitiMinimiEtaENucleo(isDa1A4FigliUnder26);

      datiDomanda.setOver70ConAltriIs(false);
      datiDomanda.setOver70ConUnCoresidente(false);
      datiDomanda.setOver70DaSolo(false);
      datiDomanda.setUnder70(true);

      datiDomanda.setFlagPensionatoSuperiore70anni(0);
      datiDomanda.setFlagPensionatoSuperiore70anni1FamiliareACarico(0);
    }

    return datiDomanda;
  }

  @Override
  public List<StepFdC> getListaStep() {
    List<StepFdC> listaStep = new ArrayList<StepFdC>();

    listaStep.add(new StepFdC("ISEE e nucleo", 1));
    listaStep.add(new StepFdC("Dati richiedente", 2));
    listaStep.add(new StepFdC("Autodichiarazioni", 3));
    listaStep.add(new StepFdC("Riepilogo", 4));
    listaStep.add(new StepFdC("Esito", 5));

    return listaStep;
  }

  @Override
  public AgevolazioneTariffariaTariInformazioni getInformazioni(Long anno)
      throws ApiException, BusinessException {

    ServiceLocatorLivelloUnoTariNetribe instance =
        ServiceLocatorLivelloUnoTariNetribe.getInstance();

    try {
      return instance
          .getApiTariAgevolazioneTariffaria()
          .getAgevolazioneTariffariaTariInformazioni(anno);

    } catch (BusinessException e) {
      log.error("ServiziTariContributoImpl -- getInformazioni: errore API TARI Netribe:", e);
      throw new BusinessException(ERRORE_API_TARI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTariContributoImpl -- getInformazioni: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTariContributoImpl -- getInformazioni: errore durante la chiamata delle API TARI Netribe ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Contributo TARI"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public FileAllegato getDocumento(String codiceIdentificativoFile)
      throws ApiException, BusinessException, IOException {

    ServiceLocatorLivelloUnoTariNetribe instance =
        ServiceLocatorLivelloUnoTariNetribe.getInstance();

    try {
      return instance
          .getApiTariDocumentiPdfApi()
          .getAgevolazioneTariffariaTariPdf(codiceIdentificativoFile);

    } catch (BusinessException e) {
      log.error("ServiziTariContributoImpl -- getDocumento: errore API TARI Netribe:", e);
      throw new BusinessException(ERRORE_API_TARI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTariContributoImpl -- getDocumento: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTariContributoImpl -- getDocumento: errore durante la chiamata delle API TARI Netribe ",
          e);
      throw new RestartResponseAtInterceptPageException(new ErroreServiziPage("Contributo TARI"));
    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }
  }

  @Override
  public List<ModalitaPagamentoEnum> getListaModalitaPagamento() {
    return Arrays.asList(ModalitaPagamentoEnum.values());
  }

  @Override
  public DatiRichiestaContributoTariNetribeResult richiediContributoTari(
      DatiDomandaContributoTari datiDomanda) throws ApiException, BusinessException, IOException {

    ServiceLocatorLivelloUnoTariNetribe instance =
        ServiceLocatorLivelloUnoTariNetribe.getInstance();
    instance.getInstance(true);

    DatiRichiestaContributoTariNetribeResult risultato =
        new DatiRichiestaContributoTariNetribeResult();

    try {
      ModelMapper mapper = new ModelMapper();
      mapper.addMappings(new DatiRichiestaContributoTariNetribeMapper());

      DatiRichiestaAgevolazioneTariffariaTari bodyPost =
          mapper.map(datiDomanda, DatiRichiestaAgevolazioneTariffariaTari.class);

      log.debug("CP datiDomanda = " + datiDomanda);
      log.debug("CP bodyPost = " + bodyPost);

      DatiAgevolazioneTariffariaTari response =
          instance.getApiTariAgevolazioneTariffaria().insertAgevolazioneTariffariaTari(bodyPost);

      if (LabelFdCUtil.checkIfNotNull(response)) {
        risultato.setEsito(true);
        risultato.setIdRichiesta(response.getIdRichiesta());
        risultato.setNumeroProtocollo(response.getNumeroProtocollo());
        risultato.setAnnoProtocollo(response.getAnnoProtocollo());
        risultato.setIdFileProtocollo(response.getIdFileProtocollo());
      }

    } catch (BusinessException e) {
      log.error("ServiziTariContributoImpl -- richiediContributoTari: errore API TARI Netribe:", e);
      throw new BusinessException(ERRORE_API_TARI);
    } catch (WebApplicationException e) {
      log.error(
          "ServiziTariContributoImpl -- richiediContributoTari: errore WebApplicationException:"
              + e.getMessage());
      throw new ApiException(e.getResponse(), e.getMessage());
    } catch (RuntimeException e) {
      log.error(
          "ServiziTariContributoImpl -- richiediContributoTari: errore durante la chiamata delle API TARI Netribe ",
          e);

      String messaggioErroreCompleto = "";

      String infoErrore =
          "L'istanza non  può essere accolta perchè non sono presenti tutti i requisiti richiesti dal bando: ";

      String erroreRicevuto = e.getMessage();
      String[] splitted = erroreRicevuto.split(":");
      String messaggioErrore = splitted[splitted.length - 1];

      byte[] messaggioErroreBytes = messaggioErrore.getBytes();

      String utf8EncodedMessaggioErroreBytes =
          new String(messaggioErroreBytes, StandardCharsets.UTF_8);

      messaggioErroreCompleto = infoErrore.concat(utf8EncodedMessaggioErroreBytes);

      risultato.setEsito(false);
      risultato.setMessaggioErrore(messaggioErroreCompleto);

    } finally {
      log.debug("close connection");
      instance.closeConnection();
    }

    return risultato;
  }
}
