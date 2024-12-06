package it.liguriadigitale.ponmetro.portale.pojo.imu;

import java.io.Serializable;

public class Anno implements Serializable {

  private static final long serialVersionUID = 8904513166807524065L;
  private Integer year;
  private String yearStr;

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getYearStr() {
    return yearStr;
  }

  public void setYearStr(String yearStr) {
    this.yearStr = yearStr;
  }
}
