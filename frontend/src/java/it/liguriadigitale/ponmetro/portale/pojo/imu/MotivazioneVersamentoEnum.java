package it.liguriadigitale.ponmetro.portale.pojo.imu;

/*
*  M7("Variazione estimi catastali"), M8("Errata aliquota"),
M14("Avviso di accertamento pagato e non dovuto"), M15("Altro");
* */

public enum MotivazioneVersamentoEnum {
  M7("Calcolo eseguito su rendita catastale errata"),
  M8("Errore nell'applicazione aliquota"),
  M14("Avviso di liquidazione / accertamento pagato e non dovuto"),
  M15("Altro");

  //
  // Calcolo eseguito su rendita catastale errata
  // Errore nell'applicazione aliquota

  private final String categoria;

  MotivazioneVersamentoEnum(final String text) {
    this.categoria = text;
  }

  public static MotivazioneVersamentoEnum getById(String id) {
    for (MotivazioneVersamentoEnum e : values()) {
      if (e.categoria.equalsIgnoreCase(id)) return e;
    }
    return null;
  }

  @Override
  public String toString() {
    return categoria;
  }
}
