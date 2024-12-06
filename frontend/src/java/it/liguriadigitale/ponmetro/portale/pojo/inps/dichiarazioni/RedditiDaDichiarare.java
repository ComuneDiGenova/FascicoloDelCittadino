package it.liguriadigitale.ponmetro.portale.pojo.inps.dichiarazioni;

import java.io.Serializable;
import java.math.BigDecimal;

public class RedditiDaDichiarare implements Serializable {

  private static final long serialVersionUID = 3311922847228238411L;

  private BigDecimal RedditoImpSost;
  private BigDecimal RedditiEsentiImposta;
  private BigDecimal RedditiIRAP;
  private BigDecimal RedditiFondiari;
  private BigDecimal RedditiTassatiEstero;
  private BigDecimal RedditiAIRE;
  private BigDecimal RedditiFondiariEstero;
  private BigDecimal TrattamentiAssistenziali;

  public BigDecimal getRedditoImpSost() {
    return RedditoImpSost;
  }

  public void setRedditoImpSost(BigDecimal redditoImpSost) {
    RedditoImpSost = redditoImpSost;
  }

  public BigDecimal getRedditiEsentiImposta() {
    return RedditiEsentiImposta;
  }

  public void setRedditiEsentiImposta(BigDecimal redditiEsentiImposta) {
    RedditiEsentiImposta = redditiEsentiImposta;
  }

  public BigDecimal getRedditiIRAP() {
    return RedditiIRAP;
  }

  public void setRedditiIRAP(BigDecimal redditiIRAP) {
    RedditiIRAP = redditiIRAP;
  }

  public BigDecimal getRedditiFondiari() {
    return RedditiFondiari;
  }

  public void setRedditiFondiari(BigDecimal redditiFondiari) {
    RedditiFondiari = redditiFondiari;
  }

  public BigDecimal getRedditiTassatiEstero() {
    return RedditiTassatiEstero;
  }

  public void setRedditiTassatiEstero(BigDecimal redditiTassatiEstero) {
    RedditiTassatiEstero = redditiTassatiEstero;
  }

  public BigDecimal getRedditiAIRE() {
    return RedditiAIRE;
  }

  public void setRedditiAIRE(BigDecimal redditiAIRE) {
    RedditiAIRE = redditiAIRE;
  }

  public BigDecimal getRedditiFondiariEstero() {
    return RedditiFondiariEstero;
  }

  public void setRedditiFondiariEstero(BigDecimal redditiFondiariEstero) {
    RedditiFondiariEstero = redditiFondiariEstero;
  }

  public BigDecimal getTrattamentiAssistenziali() {
    return TrattamentiAssistenziali;
  }

  public void setTrattamentiAssistenziali(BigDecimal trattamentiAssistenziali) {
    TrattamentiAssistenziali = trattamentiAssistenziali;
  }

  @Override
  public String toString() {
    return "RedditiDaDichiarare [RedditoImpSost="
        + RedditoImpSost
        + ", RedditiEsentiImposta="
        + RedditiEsentiImposta
        + ", RedditiIRAP="
        + RedditiIRAP
        + ", RedditiFondiari="
        + RedditiFondiari
        + ", RedditiTassatiEstero="
        + RedditiTassatiEstero
        + ", RedditiAIRE="
        + RedditiAIRE
        + ", RedditiFondiariEstero="
        + RedditiFondiariEstero
        + ", TrattamentiAssistenziali="
        + TrattamentiAssistenziali
        + "]";
  }
}
