package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.agevolazioni.richiesta.form.pojo;

import it.liguriadigitale.ponmetro.inps.modi.model.NucleoFamiliareComponenteNucleoInner;
import java.io.Serializable;
import java.time.LocalDate;

public class MembroNucleoChePercepivaReddito implements Serializable {

  private static final long serialVersionUID = -5957065864203909865L;

  private NucleoFamiliareComponenteNucleoInner datiMembroNucleo;

  private String comboSiNoMembroNucleo;

  private LocalDate dataDisoccupazioneMembroNucleo;

  public NucleoFamiliareComponenteNucleoInner getDatiMembroNucleo() {
    return datiMembroNucleo;
  }

  public void setDatiMembroNucleo(NucleoFamiliareComponenteNucleoInner datiMembroNucleo) {
    this.datiMembroNucleo = datiMembroNucleo;
  }

  public String getComboSiNoMembroNucleo() {
    return comboSiNoMembroNucleo;
  }

  public void setComboSiNoMembroNucleo(String comboSiNoMembroNucleo) {
    this.comboSiNoMembroNucleo = comboSiNoMembroNucleo;
  }

  public LocalDate getDataDisoccupazioneMembroNucleo() {
    return dataDisoccupazioneMembroNucleo;
  }

  public void setDataDisoccupazioneMembroNucleo(LocalDate dataDisoccupazioneMembroNucleo) {
    this.dataDisoccupazioneMembroNucleo = dataDisoccupazioneMembroNucleo;
  }

  @Override
  public String toString() {
    return "MembroNucleoChePercepivaReddito [datiMembroNucleo="
        + datiMembroNucleo
        + ", comboSiNoMembroNucleo="
        + comboSiNoMembroNucleo
        + ", dataDisoccupazioneMembroNucleo="
        + dataDisoccupazioneMembroNucleo
        + "]";
  }
}
