package it.liguriadigitale.ponmetro.portale.pojo.common;

import java.time.LocalDate;

public class DatiCatastaliFabbricato {
  private String tipologia; // enum?
  private Integer progressivo;
  private DatiCatastaliIdentificativi identificativi;
  private DatiCatastaliClassamento classamento;
  private Long percentualeProprieta;
  private LocalDate inCaricoDal;
  private LocalDate inCaricoAl;
}
