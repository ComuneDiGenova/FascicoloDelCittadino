package it.liguriadigitale.ponmetro.portale.business.accenture.segnalazionedannibeniprivati.utils;

import java.io.Serializable;

public class DatiSegnalazioneDanniBeniPrivati implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nome;

  private String url;

  private String identificativo;

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getIdentificativo() {
    return identificativo;
  }

  public void setIdentificativo(String identificativo) {
    this.identificativo = identificativo;
  }
}
