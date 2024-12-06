/** */
package it.liguriadigitale.ponmetro.portale.pojo.login;

import it.liguriadigitale.framework.pojo.common.BasePojo;
import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.portale.pojo.enums.SorgenteDatiNucleoEnum;
import java.time.LocalDate;

/**
 * The Class ComponenteNucleo.
 *
 * @author Airaldi
 */
public class ComponenteNucleo extends BasePojo {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 3763399866293916963L;

  /** The dati cittadino. */
  private Residente datiCittadino;

  /** The relazione. */
  private ItemRelazioneParentale relazione;

  /** The sorgente. */
  private SorgenteDatiNucleoEnum sorgente;

  /** The figlio stato civile. */
  private boolean figlioStatoCivile;

  /** co-residente nel nucleo famigliare dell'utente FdC. */
  private boolean coResidente;

  /** The residente comune genova. */
  private boolean residenteComuneGenova;

  /** The autodichiarazione figlio. */
  private Long autodichiarazioneFiglio;

  /** The blocco autodichiarazione. */
  private Long bloccoAutodichiarazione;

  /** The id person. */
  private Long idPerson;

  /**
   * Checks if is figlio stato civile.
   *
   * @return true, if is figlio stato civile
   */
  public boolean isFiglioStatoCivile() {
    return figlioStatoCivile;
  }

  /**
   * Sets the figlio stato civile.
   *
   * @param figlioStatoCivile the new figlio stato civile
   */
  public void setFiglioStatoCivile(boolean figlioStatoCivile) {
    this.figlioStatoCivile = figlioStatoCivile;
  }

  /**
   * Gets the autodichiarazione figlio.
   *
   * @return the autodichiarazione figlio
   */
  public Long getAutodichiarazioneFiglio() {
    return autodichiarazioneFiglio;
  }

  /**
   * Sets the autodichiarazione figlio.
   *
   * @param autodichiarazioneFiglio the new autodichiarazione figlio
   */
  public void setAutodichiarazioneFiglio(Long autodichiarazioneFiglio) {
    this.autodichiarazioneFiglio = autodichiarazioneFiglio;
  }

  /**
   * Gets the blocco autodichiarazione.
   *
   * @return the blocco autodichiarazione
   */
  public Long getBloccoAutodichiarazione() {
    return bloccoAutodichiarazione;
  }

  /**
   * Sets the blocco autodichiarazione.
   *
   * @param bloccoAutodichiarazione the new blocco autodichiarazione
   */
  public void setBloccoAutodichiarazione(Long bloccoAutodichiarazione) {
    this.bloccoAutodichiarazione = bloccoAutodichiarazione;
  }

  /**
   * Gets the id person.
   *
   * @return the id person
   */
  public Long getIdPerson() {
    return idPerson;
  }

  /**
   * Sets the id person.
   *
   * @param idPerson the new id person
   */
  public void setIdPerson(Long idPerson) {
    this.idPerson = idPerson;
  }

  /**
   * Gets the dati cittadino.
   *
   * @return the dati cittadino
   */
  public Residente getDatiCittadino() {
    return datiCittadino;
  }

  /**
   * Sets the dati cittadino.
   *
   * @param datiCittadino the new dati cittadino
   */
  public void setDatiCittadino(Residente datiCittadino) {
    this.datiCittadino = datiCittadino;
  }

  /**
   * Gets the relazione.
   *
   * @return the relazione
   */
  public ItemRelazioneParentale getRelazione() {
    return relazione;
  }

  /**
   * Sets the relazione.
   *
   * @param relazione the new relazione
   */
  public void setRelazione(ItemRelazioneParentale relazione) {
    this.relazione = relazione;
  }

  /**
   * Gets the sorgente.
   *
   * @return the sorgente
   */
  public SorgenteDatiNucleoEnum getSorgente() {
    return sorgente;
  }

  /**
   * Sets the sorgente.
   *
   * @param sorgente the new sorgente
   */
  public void setSorgente(SorgenteDatiNucleoEnum sorgente) {
    this.sorgente = sorgente;
  }

  /**
   * Checks if is residente nel nucleo famigliare dell'utente FdC (=co-residente).
   *
   * @return true, if is residente
   */
  public boolean isCoResidente() {
    return coResidente;
  }

  /**
   * Sets the residente nel nucleo famigliare dell'utente FdC.
   *
   * @param residente the new residente
   */
  public void setCoResidente(boolean coResidente) {
    this.coResidente = coResidente;
  }

  /**
   * Checks if is residente comune genova.
   *
   * @return true, if is residente comune genova
   */
  public boolean isResidenteComuneGenova() {
    return residenteComuneGenova;
  }

  /**
   * Sets the residente comune genova.
   *
   * @param residenteComuneGenova the new residente comune genova
   */
  public void setResidenteComuneGenova(boolean residenteComuneGenova) {
    this.residenteComuneGenova = residenteComuneGenova;
  }

  public LocalDate getDataNascita() {
    if (getDatiCittadino() != null) {
      return getDatiCittadino().getCpvDateOfBirth();
    } else {
      return null;
    }
  }

  public String getCodiceFiscale() {
    if (getDatiCittadino() != null) {
      return getDatiCittadino().getCpvTaxCode();
    } else if (getRelazione() != null) {
      return getRelazione().getCpvComponentTaxCode();
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    return "ComponenteNucleo [datiCittadino="
        + datiCittadino
        + ", relazione="
        + relazione
        + ", sorgente="
        + sorgente
        + ", figlioStatoCivile="
        + figlioStatoCivile
        + ", coResidente="
        + coResidente
        + ", residenteComuneGenova="
        + residenteComuneGenova
        + ", autodichiarazioneFiglio="
        + autodichiarazioneFiglio
        + ", bloccoAutodichiarazione="
        + bloccoAutodichiarazione
        + ", idPerson="
        + idPerson
        + "]";
  }
}
