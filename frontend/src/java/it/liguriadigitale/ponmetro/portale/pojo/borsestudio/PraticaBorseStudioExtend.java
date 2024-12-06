package it.liguriadigitale.ponmetro.portale.pojo.borsestudio;

import it.liguriadigitale.ponmetro.borsestudio.model.Comune;
import it.liguriadigitale.ponmetro.borsestudio.model.FileAllegato;
import it.liguriadigitale.ponmetro.borsestudio.model.Pratica;
import it.liguriadigitale.ponmetro.borsestudio.model.Provincia;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import java.util.List;

public class PraticaBorseStudioExtend extends Pratica {

  private static final long serialVersionUID = 6574511502576328452L;

  private String fileAllegatoVittimaUpload;

  private String nomeAllegatoRimborsoUpload;

  private byte[] byteAllegatoRimborsoUpload;

  private FileAllegato fileAllegato;

  private String fileAllegatoScontriniUpload;

  private String nomeAllegatoRimborsoUploadScontrini;

  private byte[] byteAllegatoRimborsoUploadScontrini;

  private FileAllegato fileAllegatoScontrini;

  private String codiceProvincia;

  private String iseeDifformeValue;

  private String indirizzoResidenza;

  // private FeaturesGeoserver geoserver;

  private FeaturesGeoserver autocompleteViario;

  private String datiResidenzaDomicilioCoincidono;

  private String dichiarazioneSpesaFiscaleString;

  private String figlioVittimaLavoroString;

  private String dichiarazioneDiEssereAConoscenzaString;

  private FeaturesGeoserver toponomasticaUtenteLoggato;

  // private Provincia provinciaSelect2;

  private Provincia autocompleteProvincia;

  //	private Comune comuneSelect2;

  private Comune autocompleteComune;

  private Integer classeNumero;

  private boolean isNucleoIseeUgualeNucleoAnagrafico;

  private AccettazioneNucleoIseeAnagraficoEnum accettazioneNuclei;

  private List<NucleoFamiliareComponenteNucleoInner> listaComponentiIsee;

  private List<Residente> listaComponentiAnagrafe;

  private String accettazioneNucleiSiNo;

  private String accettazioneIntestatarioSiNo;

  private Boolean iseeNonCalcolabile;

  private Double importoEuro;

  //	public Provincia getProvinciaSelect2() {
  //		return provinciaSelect2;
  //	}
  //
  //	public void setProvinciaSelect2(Provincia provinciaSelect2) {
  //		this.provinciaSelect2 = provinciaSelect2;
  //	}

  //	public Comune getComuneSelect2() {
  //		return comuneSelect2;
  //	}
  //
  //	public void setComuneSelect2(Comune comuneSelect2) {
  //		this.comuneSelect2 = comuneSelect2;
  //	}

  public String getFileAllegatoVittimaUpload() {
    return fileAllegatoVittimaUpload;
  }

  public void setFileAllegatoVittimaUpload(String fileAllegatoVittimaUpload) {
    this.fileAllegatoVittimaUpload = fileAllegatoVittimaUpload;
  }

  public String getNomeAllegatoRimborsoUpload() {
    return nomeAllegatoRimborsoUpload;
  }

  public void setNomeAllegatoRimborsoUpload(String nomeAllegatoRimborsoUpload) {
    this.nomeAllegatoRimborsoUpload = nomeAllegatoRimborsoUpload;
  }

  public byte[] getByteAllegatoRimborsoUpload() {
    return byteAllegatoRimborsoUpload;
  }

  public void setByteAllegatoRimborsoUpload(byte[] byteAllegatoRimborsoUpload) {
    this.byteAllegatoRimborsoUpload = byteAllegatoRimborsoUpload;
  }

  public FileAllegato getFileAllegato() {
    return fileAllegato;
  }

  public void setFileAllegato(FileAllegato fileAllegato) {
    this.fileAllegato = fileAllegato;
  }

  public String getCodiceProvincia() {
    return codiceProvincia;
  }

  public void setCodiceProvincia(String codiceProvincia) {
    this.codiceProvincia = codiceProvincia;
  }

  public String getIseeDifformeValue() {
    return iseeDifformeValue;
  }

  public void setIseeDifformeValue(String iseeDifformeValue) {
    this.iseeDifformeValue = iseeDifformeValue;
  }

  public String getIndirizzoResidenza() {
    return indirizzoResidenza;
  }

  public void setIndirizzoResidenza(String indirizzoResidenza) {
    this.indirizzoResidenza = indirizzoResidenza;
  }

  public String getDatiResidenzaDomicilioCoincidono() {
    return datiResidenzaDomicilioCoincidono;
  }

  public void setDatiResidenzaDomicilioCoincidono(String datiResidenzaDomicilioCoincidono) {
    this.datiResidenzaDomicilioCoincidono = datiResidenzaDomicilioCoincidono;
  }

  public String getDichiarazioneSpesaFiscaleString() {
    return dichiarazioneSpesaFiscaleString;
  }

  public void setDichiarazioneSpesaFiscaleString(String dichiarazioneSpesaFiscaleString) {
    this.dichiarazioneSpesaFiscaleString = dichiarazioneSpesaFiscaleString;
  }

  public String getFiglioVittimaLavoroString() {
    return figlioVittimaLavoroString;
  }

  public void setFiglioVittimaLavoroString(String figlioVittimaLavoroString) {
    this.figlioVittimaLavoroString = figlioVittimaLavoroString;
  }

  public FeaturesGeoserver getToponomasticaUtenteLoggato() {
    return toponomasticaUtenteLoggato;
  }

  public void setToponomasticaUtenteLoggato(FeaturesGeoserver toponomasticaUtenteLoggato) {
    this.toponomasticaUtenteLoggato = toponomasticaUtenteLoggato;
  }

  public String getFileAllegatoScontriniUpload() {
    return fileAllegatoScontriniUpload;
  }

  public void setFileAllegatoScontriniUpload(String fileAllegatoScontriniUpload) {
    this.fileAllegatoScontriniUpload = fileAllegatoScontriniUpload;
  }

  public String getNomeAllegatoRimborsoUploadScontrini() {
    return nomeAllegatoRimborsoUploadScontrini;
  }

  public void setNomeAllegatoRimborsoUploadScontrini(String nomeAllegatoRimborsoUploadScontrini) {
    this.nomeAllegatoRimborsoUploadScontrini = nomeAllegatoRimborsoUploadScontrini;
  }

  public byte[] getByteAllegatoRimborsoUploadScontrini() {
    return byteAllegatoRimborsoUploadScontrini;
  }

  public void setByteAllegatoRimborsoUploadScontrini(byte[] byteAllegatoRimborsoUploadScontrini) {
    this.byteAllegatoRimborsoUploadScontrini = byteAllegatoRimborsoUploadScontrini;
  }

  public FileAllegato getFileAllegatoScontrini() {
    return fileAllegatoScontrini;
  }

  public void setFileAllegatoScontrini(FileAllegato fileAllegatoScontrini) {
    this.fileAllegatoScontrini = fileAllegatoScontrini;
  }

  public String getDichiarazioneDiEssereAConoscenzaString() {
    return dichiarazioneDiEssereAConoscenzaString;
  }

  public void setDichiarazioneDiEssereAConoscenzaString(
      String dichiarazioneDiEssereAConoscenzaString) {
    this.dichiarazioneDiEssereAConoscenzaString = dichiarazioneDiEssereAConoscenzaString;
  }

  public Integer getClasseNumero() {
    return classeNumero;
  }

  public void setClasseNumero(Integer classeNumero) {
    this.classeNumero = classeNumero;
  }

  public boolean isNucleoIseeUgualeNucleoAnagrafico() {
    return isNucleoIseeUgualeNucleoAnagrafico;
  }

  public void setNucleoIseeUgualeNucleoAnagrafico(boolean isNucleoIseeUgualeNucleoAnagrafico) {
    this.isNucleoIseeUgualeNucleoAnagrafico = isNucleoIseeUgualeNucleoAnagrafico;
  }

  public List<NucleoFamiliareComponenteNucleoInner> getListaComponentiIsee() {
    return listaComponentiIsee;
  }

  public void setListaComponentiIsee(
      List<NucleoFamiliareComponenteNucleoInner> listaComponentiIsee) {
    this.listaComponentiIsee = listaComponentiIsee;
  }

  public AccettazioneNucleoIseeAnagraficoEnum getAccettazioneNuclei() {
    return accettazioneNuclei;
  }

  public void setAccettazioneNuclei(AccettazioneNucleoIseeAnagraficoEnum accettazioneNuclei) {
    this.accettazioneNuclei = accettazioneNuclei;
  }

  public String getAccettazioneNucleiSiNo() {
    return accettazioneNucleiSiNo;
  }

  public void setAccettazioneNucleiSiNo(String accettazioneNucleiSiNo) {
    this.accettazioneNucleiSiNo = accettazioneNucleiSiNo;
  }

  public List<Residente> getListaComponentiAnagrafe() {
    return listaComponentiAnagrafe;
  }

  public void setListaComponentiAnagrafe(List<Residente> listaComponentiAnagrafe) {
    this.listaComponentiAnagrafe = listaComponentiAnagrafe;
  }

  public FeaturesGeoserver getAutocompleteViario() {
    return autocompleteViario;
  }

  public void setAutocompleteViario(FeaturesGeoserver autocompleteViario) {
    this.autocompleteViario = autocompleteViario;
  }

  public Provincia getAutocompleteProvincia() {
    return autocompleteProvincia;
  }

  public void setAutocompleteProvincia(Provincia autocompleteProvincia) {
    this.autocompleteProvincia = autocompleteProvincia;
  }

  public Comune getAutocompleteComune() {
    return autocompleteComune;
  }

  public void setAutocompleteComune(Comune autocompleteComune) {
    this.autocompleteComune = autocompleteComune;
  }

  public String getAccettazioneIntestatarioSiNo() {
    return accettazioneIntestatarioSiNo;
  }

  public void setAccettazioneIntestatarioSiNo(String accettazioneIntestatarioSiNo) {
    this.accettazioneIntestatarioSiNo = accettazioneIntestatarioSiNo;
  }

  public Boolean getIseeNonCalcolabile() {
    return iseeNonCalcolabile;
  }

  public void setIseeNonCalcolabile(Boolean iseeNonCalcolabile) {
    this.iseeNonCalcolabile = iseeNonCalcolabile;
  }

  public Double getImportoEuro() {
    return importoEuro;
  }

  public void setImportoEuro(Double importoEuro) {
    this.importoEuro = importoEuro;
  }

  @Override
  public String toString() {
    return "PraticaBorseStudioExtend [codiceProvincia="
        + codiceProvincia
        + ", iseeDifformeValue="
        + iseeDifformeValue
        + ", indirizzoResidenza="
        + indirizzoResidenza
        + ", autocompleteViario="
        + autocompleteViario
        + ", datiResidenzaDomicilioCoincidono="
        + datiResidenzaDomicilioCoincidono
        + ", dichiarazioneSpesaFiscaleString="
        + dichiarazioneSpesaFiscaleString
        + ", figlioVittimaLavoroString="
        + figlioVittimaLavoroString
        + ", dichiarazioneDiEssereAConoscenzaString="
        + dichiarazioneDiEssereAConoscenzaString
        + ", toponomasticaUtenteLoggato="
        + toponomasticaUtenteLoggato
        + ", autocompleteProvincia="
        + autocompleteProvincia
        + ", autocompleteComune="
        + autocompleteComune
        + ", classeNumero="
        + classeNumero
        + ", isNucleoIseeUgualeNucleoAnagrafico="
        + isNucleoIseeUgualeNucleoAnagrafico
        + ", accettazioneNuclei="
        + accettazioneNuclei
        + ", listaComponentiIsee="
        + listaComponentiIsee
        + ", listaComponentiAnagrafe="
        + listaComponentiAnagrafe
        + ", accettazioneNucleiSiNo="
        + accettazioneNucleiSiNo
        + ", accettazioneIntestatarioSiNo="
        + accettazioneIntestatarioSiNo
        + ", iseeNonCalcolabile="
        + iseeNonCalcolabile
        + ", importoEuro="
        + importoEuro
        + "]";
  }
}
