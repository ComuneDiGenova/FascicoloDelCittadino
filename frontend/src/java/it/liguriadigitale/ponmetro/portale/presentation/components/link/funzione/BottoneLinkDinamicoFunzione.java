package it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione;

import org.apache.wicket.Component;

public class BottoneLinkDinamicoFunzione<T> extends LinkDinamicoFunzione<T> {

  private static final long serialVersionUID = -9090638500514932310L;

  public BottoneLinkDinamicoFunzione(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component, String cssIcona) {
    super(id, linkF, t, component, cssIcona);
  }

  public BottoneLinkDinamicoFunzione(
      String id, LinkDinamicoFunzioneData linkF, T t, Component component) {
    this(id, linkF, t, component, null);
  }

  public BottoneLinkDinamicoFunzione(String id, LinkDinamicoFunzioneData linkF, T t) {
    this(id, linkF, t, null);
  }

  public BottoneLinkDinamicoFunzione(String id, LinkDinamicoFunzioneData linkF) {
    this(id, linkF, null, null);
  }
}
