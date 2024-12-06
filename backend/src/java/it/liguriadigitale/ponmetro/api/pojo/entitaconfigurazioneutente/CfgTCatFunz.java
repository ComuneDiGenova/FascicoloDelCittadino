package it.liguriadigitale.ponmetro.api.pojo.entitaconfigurazioneutente;

/**
 * CfgTCatFunz
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2019-10-31T15:42:09.255
 */
import java.io.Serializable;

public class CfgTCatFunz implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgTCatFunz() {
    super();
  }

  /** Type : BIGINT Name : ID_FUNZ */
  public Long idFunz;

  /** Type : BIGINT Name : ID_COMP */
  public Long idComp;

  /** Type : VARCHAR Name : DENOMINAZIONE_FUNZ */
  public String denominazioneFunz;

  /** Type : VARCHAR Name : DESCRIZIONE_FUNZ */
  public String descrizioneFunz;

  /** Type : VARCHAR Name : WICKET_LABEL_ID_STD */
  public String wicketLabelIdStd;

  /** Type : VARCHAR Name : WICKET_LABEL_ID_ALT */
  public String wicketLabelIdAlt;

  /** Type : VARCHAR Name : CLASSE_PAGINA_STD */
  public String classePaginaStd;

  /** Type : VARCHAR Name : CLASSE_PAGINA_ALT */
  public String classePaginaAlt;

  /** Type : VARCHAR Name : WICKET_TITLE_STD */
  public String wicketTitleStd;

  /** Type : VARCHAR Name : WICKET_TITLE_ALT */
  public String wicketTitleAlt;

  /** Type : BIGINT Name : FLAG_ABILITAZIONE */
  public Boolean flagAbilitazione;

  /** Type : BIGINT Name : ID_STATO_REC */
  public Boolean idStatoRec;

  public Boolean isResidente;

  public Boolean isNonResidente;

  public Boolean getIsResidente() {
    return isResidente;
  }

  public void setIsResidente(Boolean isResidente) {
    this.isResidente = isResidente;
  }

  public Boolean getIsNonResidente() {
    return isNonResidente;
  }

  public void setIsNonResidente(Boolean isNonResidente) {
    this.isNonResidente = isNonResidente;
  }

  /** Sets the value for idFunz */
  public void setIdFunz(Long idFunz) {
    this.idFunz = idFunz;
  }

  /** Gets the value for idFunz */
  public Long getIdFunz() {
    return idFunz;
  }

  /** Sets the value for idComp */
  public void setIdComp(Long idComp) {
    this.idComp = idComp;
  }

  /** Gets the value for idComp */
  public Long getIdComp() {
    return idComp;
  }

  /** Sets the value for denominazioneFunz */
  public void setDenominazioneFunz(String denominazioneFunz) {
    this.denominazioneFunz = denominazioneFunz;
  }

  /** Gets the value for denominazioneFunz */
  public String getDenominazioneFunz() {
    return denominazioneFunz;
  }

  /** Sets the value for descrizioneFunz */
  public void setDescrizioneFunz(String descrizioneFunz) {
    this.descrizioneFunz = descrizioneFunz;
  }

  /** Gets the value for descrizioneFunz */
  public String getDescrizioneFunz() {
    return descrizioneFunz;
  }

  /** Sets the value for wicketLabelIdStd */
  public void setWicketLabelIdStd(String wicketLabelIdStd) {
    this.wicketLabelIdStd = wicketLabelIdStd;
  }

  /** Gets the value for wicketLabelIdStd */
  public String getWicketLabelIdStd() {
    return wicketLabelIdStd;
  }

  /** Sets the value for wicketLabelIdAlt */
  public void setWicketLabelIdAlt(String wicketLabelIdAlt) {
    this.wicketLabelIdAlt = wicketLabelIdAlt;
  }

  /** Gets the value for wicketLabelIdAlt */
  public String getWicketLabelIdAlt() {
    return wicketLabelIdAlt;
  }

  /** Sets the value for classePaginaStd */
  public void setClassePaginaStd(String classePaginaStd) {
    this.classePaginaStd = classePaginaStd;
  }

  /** Gets the value for classePaginaStd */
  public String getClassePaginaStd() {
    return classePaginaStd;
  }

  /** Sets the value for classePaginaAlt */
  public void setClassePaginaAlt(String classePaginaAlt) {
    this.classePaginaAlt = classePaginaAlt;
  }

  /** Gets the value for classePaginaAlt */
  public String getClassePaginaAlt() {
    return classePaginaAlt;
  }

  /** Sets the value for wicketTitleStd */
  public void setWicketTitleStd(String wicketTitleStd) {
    this.wicketTitleStd = wicketTitleStd;
  }

  /** Gets the value for wicketTitleStd */
  public String getWicketTitleStd() {
    return wicketTitleStd;
  }

  /** Sets the value for wicketTitleAlt */
  public void setWicketTitleAlt(String wicketTitleAlt) {
    this.wicketTitleAlt = wicketTitleAlt;
  }

  /** Gets the value for wicketTitleAlt */
  public String getWicketTitleAlt() {
    return wicketTitleAlt;
  }

  /** Sets the value for flagAbilitazione */
  public void setFlagAbilitazione(Boolean flagAbilitazione) {
    this.flagAbilitazione = flagAbilitazione;
  }

  /** Gets the value for flagAbilitazione */
  public Boolean getFlagAbilitazione() {
    return flagAbilitazione;
  }

  /** Sets the value for idStatoRec */
  public void setIdStatoRec(Boolean idStatoRec) {
    this.idStatoRec = idStatoRec;
  }

  /** Gets the value for idStatoRec */
  public Boolean getIdStatoRec() {
    return idStatoRec;
  }

  @Override
  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append(super.toString());
    try {
      java.lang.reflect.Field[] fields = getClass().getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
        String fieldName = fields[i].getName();
        Object fieldValue = fields[i].get(this);
        String line = "\n" + fieldName + ": " + fieldValue;
        str.append(line);
      }
      return str.toString();
    } catch (Exception e) {
      return str.toString();
    }
  }
}
