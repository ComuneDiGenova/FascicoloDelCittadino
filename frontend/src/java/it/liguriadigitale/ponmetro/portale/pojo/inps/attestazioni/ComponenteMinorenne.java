package it.liguriadigitale.ponmetro.portale.pojo.inps.attestazioni;

import java.math.BigDecimal;

public class ComponenteMinorenne extends ComponenteNucleo {

  private static final long serialVersionUID = -5883342830510457836L;
  private BigDecimal ISEE;
  private BigDecimal ISEEOrd;
  private ISEEMinAttr ISEEMinAttr;
  private ISEEMinAgg ISEEMinAgg;
  private Boolean ISEENonCalcolabile;

  public BigDecimal getISEE() {
    return ISEE;
  }

  public void setISEE(BigDecimal iSEE) {
    ISEE = iSEE;
  }

  public BigDecimal getISEEOrd() {
    return ISEEOrd;
  }

  public void setISEEOrd(BigDecimal iSEEOrd) {
    ISEEOrd = iSEEOrd;
  }

  public ISEEMinAttr getISEEMinAttr() {
    return ISEEMinAttr;
  }

  public void setISEEMinAttr(ISEEMinAttr iSEEMinAttr) {
    ISEEMinAttr = iSEEMinAttr;
  }

  public ISEEMinAgg getISEEMinAgg() {
    return ISEEMinAgg;
  }

  public void setISEEMinAgg(ISEEMinAgg iSEEMinAgg) {
    ISEEMinAgg = iSEEMinAgg;
  }

  public Boolean getISEENonCalcolabile() {
    return ISEENonCalcolabile;
  }

  public void setISEENonCalcolabile(Boolean iSEENonCalcolabile) {
    ISEENonCalcolabile = iSEENonCalcolabile;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ComponenteMinorenne [ISEE=");
    builder.append(ISEE);
    builder.append(", ISEEOrd=");
    builder.append(ISEEOrd);
    builder.append(", ISEEMinAttr=");
    builder.append(ISEEMinAttr);
    builder.append(", ISEEMinAgg=");
    builder.append(ISEEMinAgg);
    builder.append(", ISEENonCalcolabile=");
    builder.append(ISEENonCalcolabile);
    builder.append("]");
    return builder.toString();
  }
}
