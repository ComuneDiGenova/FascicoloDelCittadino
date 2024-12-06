package it.liguriadigitale.ponmetro.portale.pojo.anagrafici;

import java.io.Serializable;

public class FeaturesGeoserver implements Serializable {

  private static final long serialVersionUID = -1404074483251663920L;

  private Integer ID;

  private String DESVIA;

  private String COD_STRADA;

  private String NUMERO;

  private String LETTERA;

  private String COLORE;

  private String TESTO;

  private Integer CODICE_INDIRIZZO;

  private Integer IDOGGETTORIFERIMENTO;

  private String MACHINE_LAST_UPD;

  private String COD_TOPON;

  public Integer getID() {
    return ID;
  }

  public void setID(Integer iD) {
    ID = iD;
  }

  public String getDESVIA() {
    return DESVIA;
  }

  public void setDESVIA(String dESVIA) {
    DESVIA = dESVIA;
  }

  public String getCOD_STRADA() {
    return COD_STRADA;
  }

  public void setCOD_STRADA(String cOD_STRADA) {
    COD_STRADA = cOD_STRADA;
  }

  public String getNUMERO() {
    return NUMERO;
  }

  public void setNUMERO(String nUMERO) {
    NUMERO = nUMERO;
  }

  public String getLETTERA() {
    return LETTERA;
  }

  public void setLETTERA(String lETTERA) {
    LETTERA = lETTERA;
  }

  public String getCOLORE() {
    return COLORE;
  }

  public void setCOLORE(String cOLORE) {
    COLORE = cOLORE;
  }

  public String getTESTO() {
    return TESTO;
  }

  public void setTESTO(String tESTO) {
    TESTO = tESTO;
  }

  public Integer getCODICE_INDIRIZZO() {
    return CODICE_INDIRIZZO;
  }

  public void setCODICE_INDIRIZZO(Integer cODICE_INDIRIZZO) {
    CODICE_INDIRIZZO = cODICE_INDIRIZZO;
  }

  public Integer getIDOGGETTORIFERIMENTO() {
    return IDOGGETTORIFERIMENTO;
  }

  public void setIDOGGETTORIFERIMENTO(Integer iDOGGETTORIFERIMENTO) {
    IDOGGETTORIFERIMENTO = iDOGGETTORIFERIMENTO;
  }

  public String getMACHINE_LAST_UPD() {
    return MACHINE_LAST_UPD;
  }

  public void setMACHINE_LAST_UPD(String mACHINE_LAST_UPD) {
    MACHINE_LAST_UPD = mACHINE_LAST_UPD;
  }

  public String getCOD_TOPON() {
    return COD_TOPON;
  }

  public void setCOD_TOPON(String cOD_TOPON) {
    COD_TOPON = cOD_TOPON;
  }

  @Override
  public String toString() {
    return "FeaturesGeoserver [ID="
        + ID
        + ", DESVIA="
        + DESVIA
        + ", COD_STRADA="
        + COD_STRADA
        + ", NUMERO="
        + NUMERO
        + ", LETTERA="
        + LETTERA
        + ", COLORE="
        + COLORE
        + ", TESTO="
        + TESTO
        + ", CODICE_INDIRIZZO="
        + CODICE_INDIRIZZO
        + ", IDOGGETTORIFERIMENTO="
        + IDOGGETTORIFERIMENTO
        + ", MACHINE_LAST_UPD="
        + MACHINE_LAST_UPD
        + ", COD_TOPON="
        + COD_TOPON
        + "]";
  }

  //	@Override
  //	public String toString() {
  //		return MACHINE_LAST_UPD;
  //	}

}
