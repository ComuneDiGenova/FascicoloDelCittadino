package it.liguriadigitale.ponmetro.portale.pojo.enums;

public enum CpvParentTypeEnum {
  MC("MC", "Minore convivente"),
  IC(String.valueOf("IC"), "Convivente in Convivenza"),
  AC(String.valueOf("AC"), "nonnastra"),
  AD(String.valueOf("AD"), "Adottato"),
  AE(String.valueOf("AE"), "Affiliante"),
  AF(String.valueOf("AF"), "Affiliato"),
  AI(String.valueOf("AI"), "Affine"),
  AT(String.valueOf("AT"), "Affidato"),
  AV(String.valueOf("AV"), "Avo"),
  BC(String.valueOf("BC"), "BisCugino"),
  BN(String.valueOf("BN"), "Bisnipote"),
  BO(String.valueOf("BO"), "Bisnonno"),
  CC(String.valueOf("CC"), "Capo Convivenza"),
  CF(String.valueOf("CF"), "Coppia di fatto"),
  CN(String.valueOf("CN"), "Consuocero"),
  CR(String.valueOf("CR"), "Convittore"),
  CT(String.valueOf("CT"), "Cognato"),
  CU(String.valueOf("CU"), "Cugino"),
  CV(String.valueOf("CV"), "Convivente in famiglia"),
  DM(String.valueOf("DM"), "Domestico"),
  FG(String.valueOf("FG"), "Figlio"),
  FN(String.valueOf("FN"), "Figlio naturale"),
  FR(String.valueOf("FR"), "Fratello"),
  FS(String.valueOf("FS"), "Figliastro"),
  FT(String.valueOf("FT"), "Fratellastro"),
  GE(String.valueOf("GE"), "Genero"),
  IS(String.valueOf("IS"), "Intestatario scheda"),
  MA(String.valueOf("MA"), "Madre Adottiva"),
  MD(String.valueOf("MD"), "Madre"),
  MG(String.valueOf("MG"), "Moglie"),
  MN(String.valueOf("MN"), "Matrigna"),
  MR(String.valueOf("MR"), "Marito"),
  NC(String.valueOf("NC"), "EX  I.S."),
  NN(String.valueOf("NN"), "Nonno"),
  NP(String.valueOf("NP"), "Nipote"),
  NU(String.valueOf("NU"), "Nuora"),
  PD(String.valueOf("PD"), "Padre"),
  PE(String.valueOf("PE"), "Pronipote"),
  PN(String.valueOf("PN"), "Patrigno"),
  PT(String.valueOf("PT"), "Padre adottivo"),
  PZ(String.valueOf("PZ"), "Prozio"),
  SC(String.valueOf("SC"), "Suocero"),
  SR(String.valueOf("SR"), "Sorella"),
  ST(String.valueOf("ST"), "Sorellastra"),
  UC(String.valueOf("UC"), "Unito Civilmente"),
  XX(String.valueOf("XX"), "Sconosciuto"),
  ZA(String.valueOf("ZA"), "Zia"),
  ZO(String.valueOf("ZO"), "Zio");

  private String value;
  private String description;

  CpvParentTypeEnum(String v, String description) {
    this.value = v;
    this.description = description;
  }

  public String value() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public String getDescription() {
    return description;
  }

  public static CpvParentTypeEnum fromValue(String value) {
    for (CpvParentTypeEnum b : CpvParentTypeEnum.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}
