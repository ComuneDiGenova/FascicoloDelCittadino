package it.liguriadigitale.ponmetro.api.pojo.avatar;

/**
 * CfgTAvatarDef
 *
 * <p>WARNING! Automatically generated file! Do not edit! created: 2020-07-24T09:56:10.969
 */
import java.io.Serializable;

public class CfgTAvatarDef implements Serializable {
  private static final long serialVersionUID = 1L;

  public CfgTAvatarDef() {
    super();
  }

  /** Type : BIGINT Name : ID_AVATAR */
  public Long idAvatar;

  /** Type : VARCHAR Name : NOME_FILE */
  public String nomeFile;

  /** Type : IMAGE Name : AVATAR_FILE */
  public javax.sql.rowset.serial.SerialBlob avatarFile;

  /** Type : VARCHAR Name : UTENTE_INS */
  public String utenteIns;

  /** Type : TIMESTAMP Name : DATA_INS */
  public java.time.LocalDateTime dataIns;

  /** Sets the value for idAvatar */
  public void setIdAvatar(Long idAvatar) {
    this.idAvatar = idAvatar;
  }

  /** Gets the value for idAvatar */
  public Long getIdAvatar() {
    return idAvatar;
  }

  /** Sets the value for nomeFile */
  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  /** Gets the value for nomeFile */
  public String getNomeFile() {
    return nomeFile;
  }

  /** Sets the value for avatarFile */
  public void setAvatarFile(javax.sql.rowset.serial.SerialBlob avatarFile) {
    this.avatarFile = avatarFile;
  }

  /** Gets the value for avatarFile */
  public javax.sql.rowset.serial.SerialBlob getAvatarFile() {
    return avatarFile;
  }

  /** Sets the value for utenteIns */
  public void setUtenteIns(String utenteIns) {
    this.utenteIns = utenteIns;
  }

  /** Gets the value for utenteIns */
  public String getUtenteIns() {
    return utenteIns;
  }

  /** Sets the value for dataIns */
  public void setDataIns(java.time.LocalDateTime dataIns) {
    this.dataIns = dataIns;
  }

  /** Gets the value for dataIns */
  public java.time.LocalDateTime getDataIns() {
    return dataIns;
  }

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
