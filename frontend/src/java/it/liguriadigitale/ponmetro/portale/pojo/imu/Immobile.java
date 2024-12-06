package it.liguriadigitale.ponmetro.portale.pojo.imu;

import it.liguriadigitale.ponmetro.portale.pojo.anagrafici.FeaturesGeoserver;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class Immobile implements Serializable {

  private static final long serialVersionUID = -210653782298693089L;

  private UUID id;
  private TipoImmobileEnum tipo;
  private String codiceCatastale;
  private String sezione;

  private Long foglio;

  private Long particella;

  private Long subalterno;
  private CategoriaCatastale categoria;
  private String provincia;
  private String comune;
  private String indirizzo;
  private long civico;
  private String esponente;
  private String scala;
  private String piano;
  private String interno;
  private String colore;
  private String cap;
  private String classe;
  private BigDecimal percentualePossesso;
  private String altro;
  private FeaturesGeoserver geoServerFeature;

  private UtilizzoEnum utilizzo;
  private boolean isPrecompilato;

  public boolean isPrecompilato() {
    return isPrecompilato;
  }

  public void setPrecompilato(boolean isPrecompilato) {
    this.isPrecompilato = isPrecompilato;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public TipoImmobileEnum getTipo() {
    return tipo;
  }

  public void setTipo(TipoImmobileEnum tipo) {
    this.tipo = tipo;
  }

  public String getCodiceCatastale() {
    return codiceCatastale;
  }

  public void setCodiceCatastale(String codiceCatastale) {
    this.codiceCatastale = codiceCatastale;
  }

  public String getSezione() {
    return sezione;
  }

  public void setSezione(String sezione) {
    this.sezione = sezione;
  }

  public Long getFoglio() {
    return foglio;
  }

  public void setFoglio(Long foglio) {
    this.foglio = foglio;
  }

  public Long getParticella() {
    return particella;
  }

  public void setParticella(Long particella) {
    this.particella = particella;
  }

  public Long getSubalterno() {
    return subalterno;
  }

  public void setSubalterno(Long subalterno) {
    this.subalterno = subalterno;
  }

  public CategoriaCatastale getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaCatastale categoria) {
    this.categoria = categoria;
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

  public long getCivico() {
    return civico;
  }

  public void setCivico(long civico) {
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

  public String getPiano() {
    return piano;
  }

  public void setPiano(String piano) {
    this.piano = piano;
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

  public String getClasse() {
    return classe;
  }

  public void setClasse(String classe) {
    this.classe = classe;
  }

  public BigDecimal getPercentualePossesso() {
    return percentualePossesso;
  }

  public void setPercentualePossesso(BigDecimal percentualePossesso) {
    this.percentualePossesso = percentualePossesso;
  }

  public UtilizzoEnum getUtilizzo() {
    return utilizzo;
  }

  public void setUtilizzo(UtilizzoEnum utilizzo) {
    this.utilizzo = utilizzo;
  }

  public String getAltro() {
    return altro;
  }

  public void setAltro(String value) {
    this.altro = value;
  }

  public FeaturesGeoserver getGeoServerFeature() {
    return geoServerFeature;
  }

  public void setGeoServerFeature(FeaturesGeoserver geoServerFeature) {
    this.geoServerFeature = geoServerFeature;
  }
}
