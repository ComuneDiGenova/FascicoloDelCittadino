package it.liguriadigitale.ponmetro.portale.pojo.common;

import java.util.List;

public class DatiPersona extends DatiBasePersona {
  private DatiBaseContatti contatti;
  private List<DatiBaseBancari> bancari;
  private DatiFabbricato residenza;
  private DatiFabbricato domicilio;
  private List<DatiFabbricato> proprietaTutte;
}
