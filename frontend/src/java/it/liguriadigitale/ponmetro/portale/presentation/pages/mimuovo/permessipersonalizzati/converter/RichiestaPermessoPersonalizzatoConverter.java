package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.converter;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.util.DateUtil;
import it.liguriadigitale.ponmetro.genovaparcheggi.model.PermitVerificationResult;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.AllegatoBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.AllegatoFile;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.AllegatoMetadata;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.AllegatoMetadataCompleta;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Anagrafica;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.AnagraficaSoggetto;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.AutoBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Contatti;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Contrassegno;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DichiarazioniBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DichiarazioniResponseCompleta;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaBody;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.DomandaCompletaResponse;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Patente;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.Residenza;
import it.liguriadigitale.ponmetro.permessipersonalizzati.model.TipologiaProcedimento;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.Accompagnatore;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.AllegatoPermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.DichiarazionePermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.DichiarazioniPermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.Disabile;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.DocumentiAllegati;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.RichiestaPermessoPersonalizzato;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.SintesiPermessiPersonalizzati;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.SoggettiCoinvolti;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.Tutore;
import it.liguriadigitale.ponmetro.portale.pojo.permessipersonalizzati.Veicolo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RichiestaPermessoPersonalizzatoConverter implements Serializable {

  private static final long serialVersionUID = -4104686609337597253L;

  RichiestaPermessoPersonalizzato richiestaPermessoPersonalizzato;

  private Log log = LogFactory.getLog(getClass());

  DomandaCompletaResponse domandaCompletaResponse;

  public RichiestaPermessoPersonalizzatoConverter(
      RichiestaPermessoPersonalizzato richiestaPermessoPersonalizzato) {
    this.richiestaPermessoPersonalizzato = richiestaPermessoPersonalizzato;
  }

  public RichiestaPermessoPersonalizzatoConverter(DomandaCompletaResponse domandaCompletaResponse) {
    this.domandaCompletaResponse = domandaCompletaResponse;
  }

  public RichiestaPermessoPersonalizzato toRichiestaPermessoPersonalizzato(
      String descrizioneProcedimento, int idDomanda) {

    richiestaPermessoPersonalizzato = new RichiestaPermessoPersonalizzato();

    DichiarazioniPermessiPersonalizzati dichiarazioniPermessiPersonalizzati =
        new DichiarazioniPermessiPersonalizzati();

    List<DichiarazionePermessiPersonalizzati> listaDichiarazioniPermessiPersonalizzati =
        new ArrayList<>();

    for (Iterator<DichiarazioniResponseCompleta> iterator =
            domandaCompletaResponse.getDichiarazioni().iterator();
        iterator.hasNext(); ) {
      DichiarazioniResponseCompleta dichiarazioniResponseCompleta = iterator.next();

      DichiarazionePermessiPersonalizzati dichiarazionePermessiPersonalizzati =
          new DichiarazionePermessiPersonalizzati();
      dichiarazionePermessiPersonalizzati.setCodiceDichiarazione(
          dichiarazioniResponseCompleta.getCodiceDichiarazione());
      dichiarazionePermessiPersonalizzati.setDescrizioneDichiarazione(
          dichiarazioniResponseCompleta.getDescrizioneDichiarazione());
      dichiarazionePermessiPersonalizzati.setDichiarazioneAlternativa(
          null); // Manca la dichiarazione alternativa

      if (dichiarazionePermessiPersonalizzati.getCodiceDichiarazione().equalsIgnoreCase("VCL")) {

        AutoBody autoBody = domandaCompletaResponse.getVeicolo();

        if (autoBody != null) {
          Veicolo veicolo = new Veicolo();
          veicolo.setCodiceFiscaleAutoVeicoloProprioODelFamiliare(
              autoBody.getCodiceFiscaleProprietario());
          veicolo.setCognomeProprietarioAutoVeicoloProprioODelFamiliare(
              autoBody.getCognomeProprietario());
          veicolo.setNomeProprietarioAutoVeicoloProprioODelFamiliare(
              autoBody.getNomeProprietario());
          veicolo.setTargaAutoVeicoloProprioODelFamiliare(autoBody.getTarga());

          dichiarazionePermessiPersonalizzati.setVeicolo(veicolo);
        }
      }
      dichiarazionePermessiPersonalizzati.setValoreDichiarazione(
          dichiarazioniResponseCompleta.getValoreDichiarazione());

      listaDichiarazioniPermessiPersonalizzati.add(dichiarazionePermessiPersonalizzati);
    }

    dichiarazioniPermessiPersonalizzati.setListaDichiarazioni(
        listaDichiarazioniPermessiPersonalizzati);

    richiestaPermessoPersonalizzato.setDichiarazioni(dichiarazioniPermessiPersonalizzati);

    DocumentiAllegati documentiAllegati = new DocumentiAllegati();

    List<AllegatoPermessiPersonalizzati> listaAllegatoPermessiPersonalizzati = new ArrayList<>();

    for (Iterator<AllegatoMetadataCompleta> iterator =
            domandaCompletaResponse.getAllegati().iterator();
        iterator.hasNext(); ) {
      AllegatoMetadataCompleta allegatoMetadataCompleta = iterator.next();

      AllegatoPermessiPersonalizzati allegatoPermessiPersonalizzati =
          new AllegatoPermessiPersonalizzati();

      allegatoPermessiPersonalizzati.setCodiceAllegato(
          allegatoMetadataCompleta.getCodiceAllegato());
      allegatoPermessiPersonalizzati.setDescrizioneAllegato(
          allegatoMetadataCompleta.getDescrizioneAllegato());
      allegatoPermessiPersonalizzati.setDimensioneFile(allegatoMetadataCompleta.getDimensione());
      allegatoPermessiPersonalizzati.setNomeFile(allegatoMetadataCompleta.getNome());

      listaAllegatoPermessiPersonalizzati.add(allegatoPermessiPersonalizzati);
    }

    documentiAllegati.setListaAllegatoPermessiPersonalizzati(listaAllegatoPermessiPersonalizzati);

    richiestaPermessoPersonalizzato.setDocumentiAllegati(documentiAllegati);

    FeaturesGeoserver featuresGeoserver = new FeaturesGeoserver();
    featuresGeoserver.setCODICE_INDIRIZZO(
        domandaCompletaResponse.getUbicazioneRichiestaCodCivico());
    featuresGeoserver.setMACHINE_LAST_UPD(domandaCompletaResponse.getUbicazioneRichiesta());
    richiestaPermessoPersonalizzato.setGeoserverUbicazioneParcheggio(featuresGeoserver);
    richiestaPermessoPersonalizzato.setProtocollazione(domandaCompletaResponse.getProtocollo());

    SoggettiCoinvolti soggettiCoinvolti = new SoggettiCoinvolti();

    Accompagnatore accompagnatore = new Accompagnatore();
    Disabile disabile = new Disabile();
    Tutore tutore = new Tutore();
    List<AnagraficaSoggetto> listaAnagraficaSoggetto = domandaCompletaResponse.getSoggetti();
    for (Iterator<AnagraficaSoggetto> iterator = listaAnagraficaSoggetto.iterator();
        iterator.hasNext(); ) {
      AnagraficaSoggetto anagraficaSoggetto = iterator.next();

      if (anagraficaSoggetto.getContatti() == null) anagraficaSoggetto.setContatti(new Contatti());
      if (anagraficaSoggetto.getPatente() == null) anagraficaSoggetto.setPatente(new Patente());

      if (anagraficaSoggetto.getAnagrafica().getDataNascita() != null)
        anagraficaSoggetto
            .getAnagrafica()
            .setDataNascita(anagraficaSoggetto.getAnagrafica().getDataNascita().substring(0, 10));

      if (anagraficaSoggetto.getPatente().getDataRilascio() != null)
        anagraficaSoggetto
            .getPatente()
            .setDataRilascio(anagraficaSoggetto.getPatente().getDataRilascio().substring(0, 10));

      if (anagraficaSoggetto.getPatente().getDataScadenza() != null)
        anagraficaSoggetto
            .getPatente()
            .setDataScadenza(anagraficaSoggetto.getPatente().getDataScadenza().substring(0, 10));

      if (anagraficaSoggetto.getAnagrafica().getTipoSoggetto().equalsIgnoreCase("ACCOMPAGNATORE"))
        accompagnatore = getAccompagnatore(anagraficaSoggetto);

      if (anagraficaSoggetto.getAnagrafica().getTipoSoggetto().equalsIgnoreCase("DISABILE"))
        disabile = getDisabile(anagraficaSoggetto, domandaCompletaResponse.getContrassegno());

      if (anagraficaSoggetto.getAnagrafica().getTipoSoggetto().equalsIgnoreCase("TUTORE"))
        tutore = getTutore(anagraficaSoggetto);
    }

    soggettiCoinvolti.setAccompagnatore(accompagnatore);
    soggettiCoinvolti.setDisabile(disabile);
    soggettiCoinvolti.setTutore(tutore);

    soggettiCoinvolti.setTutoreCoincideConAccompagnatore(false);

    richiestaPermessoPersonalizzato.setSoggettiCoinvolti(soggettiCoinvolti);

    TipologiaProcedimento tipologiaProcedimento = new TipologiaProcedimento();
    tipologiaProcedimento.setCodice("" + domandaCompletaResponse.getProcedimento());
    richiestaPermessoPersonalizzato.setTipoDomanda(tipologiaProcedimento);

    SintesiPermessiPersonalizzati sintesiPermessiPersonalizzati =
        new SintesiPermessiPersonalizzati();
    sintesiPermessiPersonalizzati.setDescrizioneProcedimento(descrizioneProcedimento);
    sintesiPermessiPersonalizzati.setIdDomanda(idDomanda);

    if (richiestaPermessoPersonalizzato.getProtocollazione() != null) {
      sintesiPermessiPersonalizzati.setNumeroProtocollo(
          richiestaPermessoPersonalizzato.getProtocollazione().getNumero());
      sintesiPermessiPersonalizzati.setAnnoProtocollo(
          richiestaPermessoPersonalizzato.getProtocollazione().getAnno());
    }

    richiestaPermessoPersonalizzato.setSintesiPermessiPersonalizzati(sintesiPermessiPersonalizzati);

    return richiestaPermessoPersonalizzato;
  }

  private Accompagnatore getAccompagnatore(AnagraficaSoggetto anagraficaSoggetto) {
    Accompagnatore accompagnatore = new Accompagnatore();

    accompagnatore.setCapResidenzaAccompagnatore(
        anagraficaSoggetto.getResidenza().getCapResidenza());
    accompagnatore.setCartaIdentitaPerRicercaAccompagnatore(null);
    accompagnatore.setCellulareAccompagnatore(anagraficaSoggetto.getContatti().getCellulare());
    accompagnatore.setCittaResidenzaAccompagnatore(
        anagraficaSoggetto.getResidenza().getLuogoResidenza());
    if (anagraficaSoggetto.getResidenza().getCodCivicoResidenza() != null)
      accompagnatore.setCodCivicoResidenzaAccompagnatore(
          anagraficaSoggetto.getResidenza().getCodCivicoResidenza());
    accompagnatore.setCodiceFiscaleAccompagnatore(
        anagraficaSoggetto.getAnagrafica().getCodiceFiscale());
    accompagnatore.setCodiceFiscalePerRicercaAccompagnatore(null);
    accompagnatore.setCodiceIndividuoAccompagnatore(
        anagraficaSoggetto.getAnagrafica().getCodiceIndividuo());
    accompagnatore.setCodInternoResidenzaAccompagnatore(
        anagraficaSoggetto.getResidenza().getCodInternoResidenza());
    accompagnatore.setCognomeAccompagnatore(anagraficaSoggetto.getAnagrafica().getCognome());
    accompagnatore.setComponenteNucleoFamigliare(null);

    try {
      accompagnatore.setDataNascitaAccompagnatore(
          DateUtil.toLocalDateFromString(anagraficaSoggetto.getAnagrafica().getDataNascita()));
      accompagnatore.setDataRilascioPatenteAccompagnatore(
          DateUtil.toLocalDateFromString(anagraficaSoggetto.getPatente().getDataRilascio()));
      accompagnatore.setDataScadenzaPatenteAccompagnatore(
          DateUtil.toLocalDateFromString(anagraficaSoggetto.getPatente().getDataScadenza()));
    } catch (BusinessException e) {
      log.error("Richiesta PermessoPersonalizzatoConverter: errore nella conversione della data.");
    }

    accompagnatore.setEmailAccompagnatore(anagraficaSoggetto.getContatti().getEmail());
    accompagnatore.setFaParteDelNucleoFamigliare(true);
    accompagnatore.setIndirizzoResidenzaAccompagnatore(
        anagraficaSoggetto.getResidenza().getIndirizzoResidenza());
    accompagnatore.setLuogoNascitaAccompagnatore(
        anagraficaSoggetto.getAnagrafica().getLuogoNascita());
    accompagnatore.setLuogoResidenzaAccompagnatore(
        anagraficaSoggetto.getResidenza().getLuogoResidenza());
    accompagnatore.setNomeAccompagnatore(anagraficaSoggetto.getAnagrafica().getNome());
    accompagnatore.setNumeroPantenteAccompagnatore(anagraficaSoggetto.getPatente().getNumero());
    accompagnatore.setPecAccompagnatore(anagraficaSoggetto.getContatti().getPec());
    accompagnatore.setProvinciaNascitaAccompagnatore(
        anagraficaSoggetto.getAnagrafica().getProvinciaNascita());
    accompagnatore.setProvinciaResidenzaAccompagnatore(
        anagraficaSoggetto.getResidenza().getProvinciaResidenza());
    accompagnatore.setTelefonoAccompagnatore(anagraficaSoggetto.getContatti().getTelefono());
    accompagnatore.setTipoPatenteAccompagnatore(anagraficaSoggetto.getPatente().getTipo());
    return accompagnatore;
  }

  private Disabile getDisabile(AnagraficaSoggetto anagraficaSoggetto, Contrassegno contrassegno) {
    Disabile disabile = new Disabile();

    // disabile.setFaParteDelNucleoFamigliare(true);

    disabile.setCapResidenzaDisabile(anagraficaSoggetto.getResidenza().getCapResidenza());
    disabile.setCartaIdentitaPerRicercaDisabile(null);
    disabile.setCellulareDisabile(anagraficaSoggetto.getContatti().getCellulare());
    disabile.setCittaResidenzaDisabile(anagraficaSoggetto.getResidenza().getLuogoResidenza());
    if (anagraficaSoggetto.getResidenza().getCodCivicoResidenza() != null)
      disabile.setCodCivicoResidenzaDisabile(
          anagraficaSoggetto.getResidenza().getCodCivicoResidenza());
    disabile.setCodiceFiscaleDisabile(anagraficaSoggetto.getAnagrafica().getCodiceFiscale());
    disabile.setCodiceFiscalePerRicercaDisabile(null);
    disabile.setCodiceIndividuoDisabile(anagraficaSoggetto.getAnagrafica().getCodiceIndividuo());
    disabile.setCodInternoResidenzaDisabile(
        anagraficaSoggetto.getResidenza().getCodInternoResidenza());
    disabile.setCognomeDisabile(anagraficaSoggetto.getAnagrafica().getCognome());
    disabile.setComponenteNucleoFamigliare(null);

    try {

      disabile.setDataNascitaDisabile(
          DateUtil.toLocalDateFromString(anagraficaSoggetto.getAnagrafica().getDataNascita()));
      disabile.setDataRilascioPatenteDisabile(
          DateUtil.toLocalDateFromString(anagraficaSoggetto.getPatente().getDataRilascio()));
      disabile.setDataScadenzaPatenteDisabile(
          DateUtil.toLocalDateFromString(anagraficaSoggetto.getPatente().getDataScadenza()));
    } catch (BusinessException e) {
      log.error("Richiesta PermessoPersonalizzatoConverter: errore nella conversione della data.");
    }

    PermitVerificationResult datiPermessoDisabile = new PermitVerificationResult();
    datiPermessoDisabile.setPermitCode(contrassegno.getNumero());
    disabile.setDatiPermessoDisabile(datiPermessoDisabile);

    disabile.setEmailDisabile(anagraficaSoggetto.getContatti().getEmail());
    disabile.setFaParteDelNucleoFamigliare(true);
    disabile.setIndirizzoResidenzaDisabile(
        anagraficaSoggetto.getResidenza().getIndirizzoResidenza());
    disabile.setLuogoNascitaDisabile(anagraficaSoggetto.getAnagrafica().getLuogoNascita());
    disabile.setLuogoResidenzaDisabile(anagraficaSoggetto.getResidenza().getLuogoResidenza());
    disabile.setNomeDisabile(anagraficaSoggetto.getAnagrafica().getNome());
    disabile.setNumeroPantenteDisabile(anagraficaSoggetto.getPatente().getNumero());
    disabile.setPecDisabile(anagraficaSoggetto.getContatti().getPec());
    disabile.setProvinciaNascitaDisabile(anagraficaSoggetto.getAnagrafica().getProvinciaNascita());
    disabile.setProvinciaResidenzaDisabile(
        anagraficaSoggetto.getResidenza().getProvinciaResidenza());
    disabile.setTelefonoDisabile(anagraficaSoggetto.getContatti().getTelefono());
    disabile.setTipoPatenteDisabile(anagraficaSoggetto.getPatente().getTipo());

    disabile.setNumeroContrassegnoH(contrassegno.getNumero());

    try {
      disabile.setDataRilascioContrassegnoH(
          DateUtil.toLocalDateFromString(contrassegno.getDataRilascio().substring(0, 10)));
    } catch (BusinessException e) {
      log.error("Richiesta PermessoPersonalizzatoConverter: errore nella conversione della data.");
    }
    try {
      disabile.setDataScadenzaContrassegnoH(
          DateUtil.toLocalDateFromString(contrassegno.getDataScadenza().substring(0, 10)));

    } catch (BusinessException e) {
      log.error("Richiesta PermessoPersonalizzatoConverter: errore nella conversione della data.");
    }

    return disabile;
  }

  private Tutore getTutore(AnagraficaSoggetto anagraficaSoggetto) {
    Tutore tutore = new Tutore();

    tutore.setCapResidenzaTutore(anagraficaSoggetto.getResidenza().getCapResidenza());
    // disabile.setCartaIdentitaPerRicercaTutore(null);
    tutore.setCellulareTutore(anagraficaSoggetto.getContatti().getCellulare());
    tutore.setCittaResidenzaTutore(anagraficaSoggetto.getResidenza().getLuogoResidenza());
    if (anagraficaSoggetto.getResidenza().getCodCivicoResidenza() != null)
      tutore.setCodCivicoResidenzaTutore(anagraficaSoggetto.getResidenza().getCodCivicoResidenza());
    tutore.setCodiceFiscaleTutore(anagraficaSoggetto.getAnagrafica().getCodiceFiscale());
    // disabile.setCodiceFiscalePerRicercaTutore(null);
    tutore.setCodiceIndividuoTutore(anagraficaSoggetto.getAnagrafica().getCodiceIndividuo());
    tutore.setCodInternoResidenzaTutore(anagraficaSoggetto.getResidenza().getCodInternoResidenza());
    tutore.setCognomeTutore(anagraficaSoggetto.getAnagrafica().getCognome());
    // disabile.setComponenteNucleoFamigliare(null);

    try {
      tutore.setDataNascitaTutore(
          DateUtil.toLocalDateFromString(anagraficaSoggetto.getAnagrafica().getDataNascita()));
      tutore.setDataRilascioPatenteTutore(
          DateUtil.toLocalDateFromString(anagraficaSoggetto.getPatente().getDataRilascio()));
      tutore.setDataScadenzaPatenteTutore(
          DateUtil.toLocalDateFromString(anagraficaSoggetto.getPatente().getDataScadenza()));
    } catch (BusinessException e) {
      log.error("Richiesta PermessoPersonalizzatoConverter: errore nella conversione della data.");
    }

    tutore.setEmailTutore(anagraficaSoggetto.getContatti().getEmail());

    tutore.setIndirizzoResidenzaTutore(anagraficaSoggetto.getResidenza().getIndirizzoResidenza());
    tutore.setLuogoNascitaTutore(anagraficaSoggetto.getAnagrafica().getLuogoNascita());
    tutore.setLuogoResidenzaTutore(anagraficaSoggetto.getResidenza().getLuogoResidenza());
    tutore.setNomeTutore(anagraficaSoggetto.getAnagrafica().getNome());
    tutore.setNumeroPantenteTutore(anagraficaSoggetto.getPatente().getNumero());
    tutore.setPecTutore(anagraficaSoggetto.getContatti().getPec());
    tutore.setProvinciaNascitaTutore(anagraficaSoggetto.getAnagrafica().getProvinciaNascita());
    tutore.setProvinciaResidenzaTutore(anagraficaSoggetto.getResidenza().getProvinciaResidenza());
    tutore.setTelefonoTutore(anagraficaSoggetto.getContatti().getTelefono());
    tutore.setTipoPatenteTutore(anagraficaSoggetto.getPatente().getTipo());
    return tutore;
  }

  public DomandaBody toDomandaBody() {

    DomandaBody domandaBody = new DomandaBody();

    List<AllegatoPermessiPersonalizzati> listaAllegatiPermessiPersonalizzati =
        richiestaPermessoPersonalizzato
            .getDocumentiAllegati()
            .getListaAllegatoPermessiPersonalizzati();

    List<AllegatoBody> allegati = new ArrayList<>();
    for (Iterator<AllegatoPermessiPersonalizzati> iterator =
            listaAllegatiPermessiPersonalizzati.iterator();
        iterator.hasNext(); ) {
      AllegatoPermessiPersonalizzati allegatoPermessiPersonalizzati = iterator.next();

      AllegatoBody allegatoBody = new AllegatoBody();

      AllegatoFile allegatoFile = new AllegatoFile();
      allegatoFile.setFile(allegatoPermessiPersonalizzati.getByteFile());
      allegatoBody.setAllegatoFile(allegatoFile);

      AllegatoMetadata allegatoMetaData = new AllegatoMetadata();
      allegatoMetaData.setDimensione(allegatoPermessiPersonalizzati.getDimensioneFile());
      allegatoMetaData.setCodiceAllegato(allegatoPermessiPersonalizzati.getCodiceAllegato());
      allegatoMetaData.setNome(allegatoPermessiPersonalizzati.getNomeFile());
      allegatoBody.setMetaDati(allegatoMetaData);

      allegati.add(allegatoBody);
    }

    domandaBody.setAllegati(allegati);

    Contrassegno contrassegno = new Contrassegno();

    if (richiestaPermessoPersonalizzato
            .getSoggettiCoinvolti()
            .getDisabile()
            .getDatiPermessoDisabile()
        == null) {
      contrassegno.setDataRilascio(
          DateUtil.toStringFromLocalDate(
              richiestaPermessoPersonalizzato
                  .getSoggettiCoinvolti()
                  .getDisabile()
                  .getDataRilascioContrassegnoH()));
      contrassegno.setDataScadenza(
          DateUtil.toStringFromLocalDate(
              richiestaPermessoPersonalizzato
                  .getSoggettiCoinvolti()
                  .getDisabile()
                  .getDataScadenzaContrassegnoH()));
      contrassegno.setNumero(
          ""
              + richiestaPermessoPersonalizzato
                  .getSoggettiCoinvolti()
                  .getDisabile()
                  .getNumeroContrassegnoH());
    } else {

      contrassegno.setDataRilascio(
          DateUtil.toStringFromLocalDate(
              richiestaPermessoPersonalizzato
                  .getSoggettiCoinvolti()
                  .getDisabile()
                  .getDatiPermessoDisabile()
                  .getValidFrom()
                  .toLocalDate()));
      contrassegno.setDataScadenza(
          DateUtil.toStringFromLocalDate(
              richiestaPermessoPersonalizzato
                  .getSoggettiCoinvolti()
                  .getDisabile()
                  .getDatiPermessoDisabile()
                  .getValidTo()
                  .toLocalDate()));
      contrassegno.setNumero(
          ""
              + richiestaPermessoPersonalizzato
                  .getSoggettiCoinvolti()
                  .getDisabile()
                  .getDatiPermessoDisabile()
                  .getPermitAliasCode());
    }

    domandaBody.setContrassegno(contrassegno);

    List<DichiarazioniBody> dichiarazioni = new ArrayList<>();

    for (Iterator<DichiarazionePermessiPersonalizzati> iterator =
            richiestaPermessoPersonalizzato.getDichiarazioni().getListaDichiarazioni().iterator();
        iterator.hasNext(); ) {
      DichiarazionePermessiPersonalizzati dichiarazionePermessiPersonalizzati = iterator.next();

      DichiarazioniBody dichiarazioniBody = new DichiarazioniBody();
      dichiarazioniBody.setCodiceDichiarazione(
          dichiarazionePermessiPersonalizzati.getCodiceDichiarazione());
      dichiarazioniBody.setValoreDichiarazione(
          dichiarazionePermessiPersonalizzati.getValoreDichiarazione());

      dichiarazioni.add(dichiarazioniBody);
    }

    domandaBody.setDichiarazioni(dichiarazioni);

    domandaBody.setProcedimento(richiestaPermessoPersonalizzato.getTipoDomanda().getCodice());

    List<AnagraficaSoggetto> listaAnagraficaSoggetto = new ArrayList<>();

    AnagraficaSoggetto disabile = datiDisabile();
    if (disabile.getAnagrafica() != null) listaAnagraficaSoggetto.add(disabile);

    AnagraficaSoggetto tutore = datiTutore();
    if (tutore.getAnagrafica() != null) listaAnagraficaSoggetto.add(tutore);

    AnagraficaSoggetto accompagnatore = datiAccompagnatore();
    if (accompagnatore.getAnagrafica() != null) listaAnagraficaSoggetto.add(accompagnatore);

    domandaBody.setSoggetti(listaAnagraficaSoggetto);

    domandaBody.setUbicazioneRichiesta(
        richiestaPermessoPersonalizzato
            .getGeoserverUbicazioneParcheggio()
            .getMACHINE_LAST_UPD()); // TODO
    // controllare
    // se giusto
    domandaBody.setUbicazioneRichiestaCodCivico(
        richiestaPermessoPersonalizzato.getGeoserverUbicazioneParcheggio().getCODICE_INDIRIZZO());
    AutoBody autoBody = new AutoBody();

    for (Iterator<DichiarazionePermessiPersonalizzati> iterator =
            richiestaPermessoPersonalizzato.getDichiarazioni().getListaDichiarazioni().iterator();
        iterator.hasNext(); ) {
      DichiarazionePermessiPersonalizzati dichiarazionePermessiPersonalizzati = iterator.next();

      if (dichiarazionePermessiPersonalizzati.getCodiceDichiarazione().equalsIgnoreCase("VCL")) {
        autoBody.setCodiceFiscaleProprietario(
            dichiarazionePermessiPersonalizzati
                .getVeicolo()
                .getCodiceFiscaleAutoVeicoloProprioODelFamiliare());
        autoBody.setCognomeProprietario(
            dichiarazionePermessiPersonalizzati
                .getVeicolo()
                .getCognomeProprietarioAutoVeicoloProprioODelFamiliare());
        autoBody.setNomeProprietario(
            dichiarazionePermessiPersonalizzati
                .getVeicolo()
                .getNomeProprietarioAutoVeicoloProprioODelFamiliare());
        autoBody.setTarga(
            dichiarazionePermessiPersonalizzati
                .getVeicolo()
                .getTargaAutoVeicoloProprioODelFamiliare());
      }
    }

    domandaBody.setVeicolo(autoBody);

    return domandaBody;
  }

  private AnagraficaSoggetto datiDisabile() {
    AnagraficaSoggetto anagraficaSoggetto = new AnagraficaSoggetto();
    if (richiestaPermessoPersonalizzato
            .getSoggettiCoinvolti()
            .getDisabile()
            .getCodiceFiscaleDisabile()
        != null) {

      Disabile disabile = richiestaPermessoPersonalizzato.getSoggettiCoinvolti().getDisabile();

      Anagrafica anagrafica = new Anagrafica();
      anagrafica.setCodiceFiscale(disabile.getCodiceFiscaleDisabile());
      anagrafica.setCodiceIndividuo(disabile.getCodiceIndividuoDisabile());
      anagrafica.setCognome(disabile.getCognomeDisabile());
      anagrafica.setDataNascita(DateUtil.toStringFromLocalDate(disabile.getDataNascitaDisabile()));
      anagrafica.setLuogoNascita(disabile.getLuogoNascitaDisabile());
      anagrafica.setNome(disabile.getNomeDisabile());
      anagrafica.setProvinciaNascita(disabile.getProvinciaNascitaDisabile());

      anagrafica.setRichiedente(disabile.isRichiedente(richiestaPermessoPersonalizzato));
      anagrafica.setTipoSoggetto("DISABILE");

      anagraficaSoggetto.setAnagrafica(anagrafica);

      // if (disabile.isRichiedente(richiestaPermessoPersonalizzato)) {
      Contatti contatti = new Contatti();
      contatti.setCellulare(disabile.getCellulareDisabile());
      contatti.setEmail(disabile.getEmailDisabile());
      contatti.setPec(disabile.getPecDisabile());
      contatti.setTelefono(disabile.getTelefonoDisabile());

      anagraficaSoggetto.setContatti(contatti);

      if (disabile.getNumeroPantenteDisabile() != null) {
        Patente patente = new Patente();
        patente.setDataRilascio(
            DateUtil.toStringFromLocalDate(disabile.getDataRilascioPatenteDisabile()));
        patente.setDataScadenza(
            DateUtil.toStringFromLocalDate(disabile.getDataScadenzaPatenteDisabile()));
        patente.setNumero(disabile.getNumeroPantenteDisabile());
        patente.setTipo(disabile.getTipoPatenteDisabile());

        anagraficaSoggetto.setPatente(patente);
      }
      // }

      Residenza residenza = new Residenza();
      residenza.setCapResidenza(disabile.getCapResidenzaDisabile());
      residenza.setCodCivicoResidenza(disabile.getCodCivicoResidenzaDisabile());
      residenza.setCodInternoResidenza(disabile.getCodInternoResidenzaDisabile());
      residenza.setIndirizzoResidenza(disabile.getIndirizzoResidenzaDisabile());
      residenza.setLuogoResidenza(disabile.getLuogoResidenzaDisabile());
      residenza.setProvinciaResidenza(disabile.getProvinciaResidenzaDisabile());

      anagraficaSoggetto.setResidenza(residenza);

      // listaAnagraficaSoggetto.add(anagraficaSoggetto);
    }
    return anagraficaSoggetto;
  }

  private AnagraficaSoggetto datiTutore() {
    AnagraficaSoggetto anagraficaSoggetto = new AnagraficaSoggetto();
    if (richiestaPermessoPersonalizzato.getSoggettiCoinvolti().getTutore().getCodiceFiscaleTutore()
        != null) {

      Tutore tutore = richiestaPermessoPersonalizzato.getSoggettiCoinvolti().getTutore();

      Anagrafica anagrafica = new Anagrafica();
      anagrafica.setCodiceFiscale(tutore.getCodiceFiscaleTutore());
      anagrafica.setCodiceIndividuo(tutore.getCodiceIndividuoTutore());
      anagrafica.setCognome(tutore.getCognomeTutore());
      anagrafica.setDataNascita(DateUtil.toStringFromLocalDate(tutore.getDataNascitaTutore()));
      anagrafica.setLuogoNascita(tutore.getLuogoNascitaTutore());
      anagrafica.setNome(tutore.getNomeTutore());
      anagrafica.setProvinciaNascita(tutore.getProvinciaNascitaTutore());

      anagrafica.setRichiedente(
          !richiestaPermessoPersonalizzato
              .getSoggettiCoinvolti()
              .getDisabile()
              .isRichiedente(richiestaPermessoPersonalizzato));
      anagrafica.setTipoSoggetto("TUTORE"); // TODO ???????????????

      anagraficaSoggetto.setAnagrafica(anagrafica);

      // if (tutore.isRichiedente(richiestaPermessoPersonalizzato)) {
      Contatti contatti = new Contatti();
      contatti.setCellulare(tutore.getCellulareTutore());
      contatti.setEmail(tutore.getEmailTutore());
      contatti.setPec(tutore.getPecTutore());
      contatti.setTelefono(tutore.getTelefonoTutore());

      anagraficaSoggetto.setContatti(contatti);

      /*
       * if (tutore.getNumeroPantenteTutore() != null) { Patente patente = new
       * Patente(); patente.setDataRilascio(DateUtil.toStringFromLocalDate(tutore.
       * getDataRilascioPatenteTutore()));
       * patente.setDataScadenza(DateUtil.toStringFromLocalDate(tutore.
       * getDataScadenzaPatenteTutore()));
       * patente.setNumero(tutore.getNumeroPantenteTutore());
       * patente.setTipo(tutore.getTipoPatenteTutore());
       *
       * anagraficaSoggetto.setPatente(patente); }
       */
      // }

      Residenza residenza = new Residenza();
      residenza.setCapResidenza(tutore.getCapResidenzaTutore());
      residenza.setCodCivicoResidenza(tutore.getCodCivicoResidenzaTutore());
      residenza.setCodInternoResidenza(tutore.getCodInternoResidenzaTutore());
      residenza.setIndirizzoResidenza(tutore.getIndirizzoResidenzaTutore());
      residenza.setLuogoResidenza(tutore.getLuogoResidenzaTutore());
      residenza.setProvinciaResidenza(tutore.getProvinciaResidenzaTutore());

      anagraficaSoggetto.setResidenza(residenza);
    }
    return anagraficaSoggetto;
  }

  private AnagraficaSoggetto datiAccompagnatore() {
    AnagraficaSoggetto anagraficaSoggetto = new AnagraficaSoggetto();

    if (richiestaPermessoPersonalizzato
        .getSoggettiCoinvolti()
        .isTutoreCoincideConAccompagnatore()) {

      if (richiestaPermessoPersonalizzato
              .getSoggettiCoinvolti()
              .getTutore()
              .getCodiceFiscaleTutore()
          != null) {

        Tutore tutore = richiestaPermessoPersonalizzato.getSoggettiCoinvolti().getTutore();

        Anagrafica anagrafica = new Anagrafica();
        anagrafica.setCodiceFiscale(tutore.getCodiceFiscaleTutore());
        anagrafica.setCodiceIndividuo(tutore.getCodiceIndividuoTutore());
        anagrafica.setCognome(tutore.getCognomeTutore());
        anagrafica.setDataNascita(DateUtil.toStringFromLocalDate(tutore.getDataNascitaTutore()));
        anagrafica.setLuogoNascita(tutore.getLuogoNascitaTutore());
        anagrafica.setNome(tutore.getNomeTutore());
        anagrafica.setProvinciaNascita(tutore.getProvinciaNascitaTutore());

        anagrafica.setRichiedente(false);
        anagrafica.setTipoSoggetto("ACCOMPAGNATORE");
        anagraficaSoggetto.setAnagrafica(anagrafica);

        Contatti contatti = new Contatti();
        contatti.setCellulare(tutore.getCellulareTutore());
        contatti.setEmail(tutore.getEmailTutore());
        contatti.setPec(tutore.getPecTutore());
        contatti.setTelefono(tutore.getTelefonoTutore());

        anagraficaSoggetto.setContatti(contatti);

        if (tutore.getNumeroPantenteTutore() != null) {
          Patente patente = new Patente();
          patente.setDataRilascio(
              DateUtil.toStringFromLocalDate(tutore.getDataRilascioPatenteTutore()));
          patente.setDataScadenza(
              DateUtil.toStringFromLocalDate(tutore.getDataScadenzaPatenteTutore()));
          patente.setNumero(tutore.getNumeroPantenteTutore());
          patente.setTipo(tutore.getTipoPatenteTutore());

          anagraficaSoggetto.setPatente(patente);
        }

        Residenza residenza = new Residenza();
        residenza.setCapResidenza(tutore.getCapResidenzaTutore());
        residenza.setCodCivicoResidenza(tutore.getCodCivicoResidenzaTutore());
        residenza.setCodInternoResidenza(tutore.getCodInternoResidenzaTutore());
        residenza.setIndirizzoResidenza(tutore.getIndirizzoResidenzaTutore());
        residenza.setLuogoResidenza(tutore.getLuogoResidenzaTutore());
        residenza.setProvinciaResidenza(tutore.getProvinciaResidenzaTutore());

        anagraficaSoggetto.setResidenza(residenza);
      }

    } else {

      if (richiestaPermessoPersonalizzato
              .getSoggettiCoinvolti()
              .getAccompagnatore()
              .getCodiceFiscaleAccompagnatore()
          != null) {

        Accompagnatore accompagnatore =
            richiestaPermessoPersonalizzato.getSoggettiCoinvolti().getAccompagnatore();

        Anagrafica anagrafica = new Anagrafica();
        anagrafica.setCodiceFiscale(accompagnatore.getCodiceFiscaleAccompagnatore());
        anagrafica.setCodiceIndividuo(accompagnatore.getCodiceIndividuoAccompagnatore());
        anagrafica.setCognome(accompagnatore.getCognomeAccompagnatore());
        anagrafica.setDataNascita(
            DateUtil.toStringFromLocalDate(accompagnatore.getDataNascitaAccompagnatore()));
        anagrafica.setLuogoNascita(accompagnatore.getLuogoNascitaAccompagnatore());
        anagrafica.setNome(accompagnatore.getNomeAccompagnatore());
        anagrafica.setProvinciaNascita(accompagnatore.getProvinciaNascitaAccompagnatore());

        anagrafica.setRichiedente(
            !richiestaPermessoPersonalizzato
                .getSoggettiCoinvolti()
                .getDisabile()
                .isRichiedente(richiestaPermessoPersonalizzato));
        anagrafica.setTipoSoggetto("ACCOMPAGNATORE");

        anagraficaSoggetto.setAnagrafica(anagrafica);

        Contatti contatti = new Contatti();
        contatti.setCellulare(accompagnatore.getCellulareAccompagnatore());
        contatti.setEmail(accompagnatore.getEmailAccompagnatore());
        contatti.setPec(accompagnatore.getPecAccompagnatore());
        contatti.setTelefono(accompagnatore.getTelefonoAccompagnatore());

        anagraficaSoggetto.setContatti(contatti);

        if (accompagnatore.getNumeroPantenteAccompagnatore() != null) {
          Patente patente = new Patente();
          patente.setDataRilascio(
              DateUtil.toStringFromLocalDate(
                  accompagnatore.getDataRilascioPatenteAccompagnatore()));
          patente.setDataScadenza(
              DateUtil.toStringFromLocalDate(
                  accompagnatore.getDataScadenzaPatenteAccompagnatore()));
          patente.setNumero(accompagnatore.getNumeroPantenteAccompagnatore());
          patente.setTipo(accompagnatore.getTipoPatenteAccompagnatore());

          anagraficaSoggetto.setPatente(patente);
        }

        Residenza residenza = new Residenza();
        residenza.setCapResidenza(accompagnatore.getCapResidenzaAccompagnatore());
        residenza.setCodCivicoResidenza(accompagnatore.getCodCivicoResidenzaAccompagnatore());
        residenza.setCodInternoResidenza(accompagnatore.getCodInternoResidenzaAccompagnatore());
        residenza.setIndirizzoResidenza(accompagnatore.getIndirizzoResidenzaAccompagnatore());
        residenza.setLuogoResidenza(accompagnatore.getLuogoResidenzaAccompagnatore());
        residenza.setProvinciaResidenza(accompagnatore.getProvinciaResidenzaAccompagnatore());

        anagraficaSoggetto.setResidenza(residenza);
      }
    }
    return anagraficaSoggetto;
  }
}
