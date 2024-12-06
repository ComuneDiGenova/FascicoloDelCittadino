package it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa;

import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto.LinguaEnum;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Contatto.TipoEnum;
import it.liguriadigitale.ponmetro.allertezonarossa.model.Lingua;
import java.io.Serializable;

public class ContattoTelefonicoZonaRossa implements Serializable {

  private static final long serialVersionUID = 5368080200682090234L;

  private int idUtente;

  private int id;

  private String numero;

  private TipoEnum tipo;

  private LinguaEnum lingua;

  private Lingua linguaNoItalia;

  public int getIdUtente() {
    return idUtente;
  }

  public void setIdUtente(int idUtente) {
    this.idUtente = idUtente;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public TipoEnum getTipo() {
    return tipo;
  }

  public void setTipo(TipoEnum tipo) {
    this.tipo = tipo;
  }

  public LinguaEnum getLingua() {
    return lingua;
  }

  public void setLingua(LinguaEnum lingua) {
    this.lingua = lingua;
  }

  public Lingua getLinguaNoItalia() {
    return linguaNoItalia;
  }

  public void setLinguaNoItalia(Lingua linguaNoItalia) {
    this.linguaNoItalia = linguaNoItalia;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ContattoTelefonicoZonaRossa [idUtente=");
    builder.append(idUtente);
    builder.append(", id=");
    builder.append(id);
    builder.append(", numero=");
    builder.append(numero);
    builder.append(", tipo=");
    builder.append(tipo);
    builder.append(", lingua=");
    builder.append(lingua);
    builder.append(", linguaNoItalia=");
    builder.append(linguaNoItalia);
    builder.append("]");
    return builder.toString();
  }
}
