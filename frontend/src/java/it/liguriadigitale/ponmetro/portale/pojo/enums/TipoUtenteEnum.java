package it.liguriadigitale.ponmetro.portale.pojo.enums;

public enum TipoUtenteEnum {
  RESIDENTE(true, 1),
  NON_RESIDENTE(false, 2);

  private Boolean isResidente;
  private Integer tipoNumerico;

  private TipoUtenteEnum(Boolean isResidente, Integer tipoNumerico) {
    this.isResidente = isResidente;
    this.tipoNumerico = tipoNumerico;
  }

  public static Integer getValoreNumerico(Boolean valoreBooleano) {
    for (TipoUtenteEnum tipo : TipoUtenteEnum.values()) {
      if (tipo.isResidente == valoreBooleano) {
        return tipo.tipoNumerico;
      }
    }
    return 0;
  }
}
