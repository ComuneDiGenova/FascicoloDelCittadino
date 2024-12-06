package it.liguriadigitale.ponmetro.portale.pojo.mip.builder;

import it.liguriadigitale.riuso.mip.PaymentRequest.UserDataExt.UtenteLogin;

public class UtenteLoginBuilder {

  private String nome;
  private String cognome;
  private String identificativoUtente;
  private String tipoIdentificativo;
  private String tipoAutenticazione;

  public UtenteLoginBuilder setNome(String nome) {
    this.nome = nome;
    return this;
  }

  public UtenteLoginBuilder setCognome(String cognome) {
    this.cognome = cognome;
    return this;
  }

  public UtenteLoginBuilder setIdentificativoUtente(String identificativoUtente) {
    this.identificativoUtente = identificativoUtente;
    return this;
  }

  public UtenteLoginBuilder setTipoIdentificativo(String tipoIdentificativo) {
    this.tipoIdentificativo = tipoIdentificativo;
    return this;
  }

  public UtenteLoginBuilder setTipoAutenticazione(String tipoAutenticazione) {
    this.tipoAutenticazione = tipoAutenticazione;
    return this;
  }

  public static UtenteLoginBuilder getInstance() {
    return new UtenteLoginBuilder();
  }

  public UtenteLogin build() {

    UtenteLogin utenteLogin = new UtenteLogin();
    utenteLogin.setIdentificativoUtente(identificativoUtente);
    utenteLogin.setCognome(cognome);
    utenteLogin.setNome(nome);
    utenteLogin.setTipoAutenticazione(tipoAutenticazione);
    utenteLogin.setTipoIdentificativo(tipoIdentificativo);
    return utenteLogin;
  }
}
