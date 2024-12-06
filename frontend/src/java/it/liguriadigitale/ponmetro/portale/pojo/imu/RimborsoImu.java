package it.liguriadigitale.ponmetro.portale.pojo.imu;

import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import it.liguriadigitale.ponmetro.taririmborsieng.model.DatiIstanza.ModalitaPagamentoEnum;
import it.liguriadigitale.ponmetro.tributi.model.DettaglioPraticaRimborso;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.form.upload.FileUploadField;

public class RimborsoImu implements Serializable {
  private static final long serialVersionUID = 1L;

  private String nome;
  private String cognome;
  private String codiceFiscale;
  private LocalDate dataNascita;
  private String sesso;
  private String luogoNascita;
  private String cittadinanza;
  private String provincia;
  private String comune;
  private String indirizzo;
  private Integer civico;
  private String esponente;
  private String scala;
  private String interno;
  private String colore;
  private String cap;
  private String telefono;
  private String cellulare;
  private String email;
  private String pec;
  private String altro;
  private String cognomeDefunto;
  private String nomeDefunto;
  private String codiceFiscaleDefunto;
  private SessoEnum sessoDefunto;
  private LocalDate dataNascitaDefunto;
  private String luogoNascitaDefunto;
  private String cittadinanzaDefunto;
  private LocalDate dataDecesso;
  private String luogoDecesso;

  private Long annoProtocollo;
  private String numeroProtocollo;
  private String dataProtocollo;

  public Long getAnnoProtocollo() {
    return annoProtocollo;
  }

  public void setAnnoProtocollo(Long anno) {
    this.annoProtocollo = anno;
  }

  public String getNumeroProtocollo() {
    return numeroProtocollo;
  }

  public void setNumeroProtocollo(String numeroProtocollo) {
    this.numeroProtocollo = numeroProtocollo;
  }

  public String getDataProtocollo() {
    return dataProtocollo;
  }

  public void setDataProtocollo(String dataProtocollo) {
    this.dataProtocollo = dataProtocollo;
  }

  private Long codiceSoggetto;
  private Long codiceSoggettoTutelato;

  private InQuantoEnum inQuanto;
  private StatoRimborsoEnum stato;
  private TipoEredeEnum tipoErede;

  private ModalitaPagamentoEnum modalitaPagamento;

  private boolean ck1;
  private boolean ck2;
  private boolean ck3;
  private boolean ck4;

  @Nullable private BonificoBancario bonificoBancario;

  @Nullable private Delegato delegato;

  List<Versamento> versamenti;
  List<Immobile> immobili;

  List<RimborsoImuAllegato> delegaErede;

  private Log log = LogFactory.getLog(getClass());

  private Integer numeroDocumento;
  private int annoDocumento;
  private LocalDate dataPresentazione;
  private int uri;
  private String annotazioni;
  private Integer uriPratica;

  private FileUploadField fileAttestazioneEredeUpload;

  public RimborsoImu() {
    // TODO Auto-generated constructor stub
    delegaErede = new ArrayList<RimborsoImuAllegato>();
    versamenti = new ArrayList<Versamento>();
  }

  public RimborsoImu(DettaglioPraticaRimborso praticaRimborso, Integer praticaRimborsoImuId) {
    this.codiceFiscale = praticaRimborso.getDatiRichiedente().getCf();
    this.uriPratica = praticaRimborsoImuId;
    this.codiceSoggetto = praticaRimborso.getDatiRichiedente().getCodSogg();
    this.nome = praticaRimborso.getDatiRichiedente().getNomRich();
    this.cognome = praticaRimborso.getDatiRichiedente().getCognRich();

    this.annoProtocollo = praticaRimborso.getDatiIstanza().getAnnoProt();
    this.dataProtocollo = praticaRimborso.getDatiIstanza().getDatProt();
    this.numeroProtocollo = praticaRimborso.getDatiIstanza().getNumProt();

    this.annoDocumento = praticaRimborso.getDatiIstanza().getAnnDoc().intValue();
    this.numeroDocumento = praticaRimborso.getDatiIstanza().getNumDoc().intValue();
    this.dataPresentazione =
        LocalDateUtil.convertiDaFormatoEuropeoPerControlloIstanzeTarga(
            praticaRimborso.getDatiIstanza().getDatPre());

    log.debug("[Stato] " + praticaRimborso.getDatiIstanza().getStatoOpe());

    this.stato = StatoRimborsoEnum.valueOf(praticaRimborso.getDatiIstanza().getStatoOpe());

    if (LabelFdCUtil.checkIfNotNull(praticaRimborso.getDatiIstanza().getTipRichRimborso())) {
      this.tipoErede = TipoEredeEnum.getById(praticaRimborso.getDatiIstanza().getTipRichRimborso());
      this.codiceFiscaleDefunto = praticaRimborso.getDatiIstanza().getCfTutelato();
      this.nomeDefunto = praticaRimborso.getDatiIstanza().getNomTutelato();
      this.cognomeDefunto = praticaRimborso.getDatiIstanza().getCognTutelato();
      this.codiceSoggettoTutelato = praticaRimborso.getDatiIstanza().getCodSoggTutelato();
    }
  }

  public Long getCodiceSoggettoTutelato() {
    return codiceSoggettoTutelato;
  }

  public void setCodiceSoggettoTutelato(Long codiceSoggettoTutelato) {
    this.codiceSoggettoTutelato = codiceSoggettoTutelato;
  }

  public Long getCodiceSoggetto() {
    return codiceSoggetto;
  }

  public void setCodiceSoggetto(Long codiceSoggetto) {
    this.codiceSoggetto = codiceSoggetto;
  }

  public Integer getUriPratica() {
    return uriPratica;
  }

  public void setUriPratica(Integer integer) {
    this.uriPratica = integer;
  }

  public boolean isCk1() {
    return ck1;
  }

  public void setCk1(boolean ck1) {
    this.ck1 = ck1;
  }

  public boolean isCk2() {
    return ck2;
  }

  public void setCk2(boolean ck2) {
    this.ck2 = ck2;
  }

  public boolean isCk3() {
    return ck3;
  }

  public void setCk3(boolean ck3) {
    this.ck3 = ck3;
  }

  public boolean isCk4() {
    return ck4;
  }

  public void setCk4(boolean ck4) {
    this.ck4 = ck4;
  }

  public FileUploadField getFileAttestazioneEredeUpload() {
    return fileAttestazioneEredeUpload;
  }

  public void setFileAttestazioneEredeUpload(FileUploadField fileAttestazioneEredeUpload) {
    this.fileAttestazioneEredeUpload = fileAttestazioneEredeUpload;
  }

  public List<RimborsoImuAllegato> getDelegaErede() {
    return delegaErede;
  }

  public void setDelegaErede(List<RimborsoImuAllegato> delegaErede) {
    this.delegaErede = delegaErede;
  }

  public Integer getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(Integer integer) {
    this.numeroDocumento = integer;
  }

  public int getAnnoDocumento() {
    return annoDocumento;
  }

  public void setAnnoDocumento(int annoDocumento) {
    this.annoDocumento = annoDocumento;
  }

  public LocalDate getDataPresentazione() {
    return dataPresentazione;
  }

  public void setDataPresentazione(LocalDate dataPresentazione) {
    this.dataPresentazione = dataPresentazione;
  }

  public int getUri() {
    return uri;
  }

  public void setUri(int uri) {
    this.uri = uri;
  }

  public String getNome() {
    return nome;
  }

  public String getCognome() {
    return cognome;
  }

  public String getCf() {
    return codiceFiscale;
  }

  public String getCodiceFiscale() {
    return codiceFiscale;
  }

  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }

  public String getSesso() {
    return sesso;
  }

  public void setSesso(String sesso) {
    this.sesso = sesso;
  }

  public String getLuogoNascita() {
    return luogoNascita;
  }

  public void setLuogoNascita(String luogoNascita) {
    this.luogoNascita = luogoNascita;
  }

  public String getCittadinanza() {
    return cittadinanza;
  }

  public void setCittadinanza(String cittadinanza) {
    this.cittadinanza = cittadinanza;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public String getComune() {
    return comune;
  }

  public void setComune(String comune) {
    this.comune = comune;
  }

  public String getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  public Integer getCivico() {
    return civico;
  }

  public void setCivico(Integer civico) {
    this.civico = civico;
  }

  public String getEsponente() {
    return esponente;
  }

  public void setEsponente(String esponente) {
    this.esponente = esponente;
  }

  public String getScala() {
    return scala;
  }

  public void setScala(String scala) {
    this.scala = scala;
  }

  public String getInterno() {
    return interno;
  }

  public void setInterno(String interno) {
    this.interno = interno;
  }

  public String getColore() {
    return colore;
  }

  public void setColore(String colore) {
    this.colore = colore;
  }

  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getCellulare() {
    return cellulare;
  }

  public void setCellulare(String cellulare) {
    this.cellulare = cellulare;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPec() {
    return pec;
  }

  public void setPec(String pec) {
    this.pec = pec;
  }

  public String getAltro() {
    return altro;
  }

  public void setAltro(String altro) {
    this.altro = altro;
  }

  public String getCognomeDefunto() {
    return cognomeDefunto;
  }

  public void setCognomeDefunto(String cognomeDefunto) {
    this.cognomeDefunto = cognomeDefunto;
  }

  public String getNomeDefunto() {
    return nomeDefunto;
  }

  public void setNomeDefunto(String nomeDefunto) {
    this.nomeDefunto = nomeDefunto;
  }

  public String getCodiceFiscaleDefunto() {
    return codiceFiscaleDefunto;
  }

  public void setCodiceFiscaleDefunto(String codiceFiscaleDefunto) {
    this.codiceFiscaleDefunto = codiceFiscaleDefunto;
  }

  public SessoEnum getSessoDefunto() {
    return sessoDefunto;
  }

  public void setSessoDefunto(SessoEnum sessoDefunto) {
    this.sessoDefunto = sessoDefunto;
  }

  public LocalDate getDataNascitaDefunto() {
    return dataNascitaDefunto;
  }

  public void setDataNascitaDefunto(LocalDate dataNascitaDefunto) {
    this.dataNascitaDefunto = dataNascitaDefunto;
  }

  public String getLuogoNascitaDefunto() {
    return luogoNascitaDefunto;
  }

  public void setLuogoNascitaDefunto(String luogoNascitaDefunto) {
    this.luogoNascitaDefunto = luogoNascitaDefunto;
  }

  public String getCittadinanzaDefunto() {
    return cittadinanzaDefunto;
  }

  public void setCittadinanzaDefunto(String cittadinanzaDefunto) {
    this.cittadinanzaDefunto = cittadinanzaDefunto;
  }

  public LocalDate getDataDecesso() {
    return dataDecesso;
  }

  public void setDataDecesso(LocalDate dataDecesso) {
    this.dataDecesso = dataDecesso;
  }

  public String getLuogoDecesso() {
    return luogoDecesso;
  }

  public void setLuogoDecesso(String luogoDecesso) {
    this.luogoDecesso = luogoDecesso;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public TipoEredeEnum getTipoErede() {
    return tipoErede;
  }

  public void setTipoErede(TipoEredeEnum tipoErede) {
    this.tipoErede = tipoErede;
  }

  public void setStato(StatoRimborsoEnum stato) {
    this.stato = stato;
  }

  public StatoRimborsoEnum getStato() {
    return stato;
  }

  public InQuantoEnum getInQuanto() {
    return inQuanto;
  }

  public void setInQuanto(InQuantoEnum inQuanto) {
    this.inQuanto = inQuanto;
  }

  public List<Versamento> getVersamenti() {
    return versamenti;
  }

  public List<Immobile> getImmobili() {
    return immobili;
  }

  public void setVersamenti(List<Versamento> versamenti) {
    this.versamenti = versamenti;
  }

  public void setImmobili(List<Immobile> immobili) {
    this.immobili = immobili;
  }

  public ModalitaPagamentoEnum getModalitaPagamento() {
    return modalitaPagamento;
  }

  public void setModalitaPagamento(ModalitaPagamentoEnum modalitaPagamento) {
    this.modalitaPagamento = modalitaPagamento;
  }

  public BonificoBancario getBonificoBancario() {
    return bonificoBancario;
  }

  public void setBonificoBancario(BonificoBancario bonificoBancario) {
    this.bonificoBancario = bonificoBancario;
  }

  public Delegato getDelegato() {
    return delegato;
  }

  public void setDelegato(Delegato delegato) {
    this.delegato = delegato;
  }

  public String getAnnotazioni() {
    return annotazioni;
  }

  public void setAnnotazioni(String value) {
    this.annotazioni = value;
  }

  public String getCodiceFiscalePerAuriga() {
    return LabelFdCUtil.checkIfNull(getCodiceFiscaleDefunto())
            || getCodiceFiscaleDefunto().isEmpty()
        ? getCodiceFiscale()
        : getCodiceFiscaleDefunto();
  }

  public Long getCodiceSoggetoPerAuriga() {
    return LabelFdCUtil.checkIfNull(getCodiceSoggettoTutelato()) || getCodiceSoggettoTutelato() == 0
        ? getCodiceSoggetto()
        : getCodiceSoggettoTutelato();
  }

  // Proprietario, Eredeunico, Coerede, Altro
  public String getTipologiaRichiedenteEng() {
    String tipo = "";

    switch (inQuanto) {
      case EREDE:
        tipo = this.tipoErede.equals(TipoEredeEnum.COEREDE) ? "Coerede" : "Eredeunico";
        break;
      case ALTRO:
        tipo = "Altro";
        break;
      default:
        tipo = "Proprietario";
        break;
    }

    return tipo;
  }

  public String getCfTutelato() {
    return inQuanto.equals(InQuantoEnum.EREDE) || inQuanto.equals(InQuantoEnum.ALTRO)
        ? this.codiceFiscaleDefunto
        : null;
  }

  public String getDecodificaModalitaPagamentoEng() {
    String mod = null;

    switch (modalitaPagamento) {
      case CAS:
        mod = "T";
        break;

      case CAB:
        mod = "B";
        break;
    }

    return mod;
  }
}
