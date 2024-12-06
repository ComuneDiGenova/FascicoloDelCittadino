package it.liguriadigitale.ponmetro.portale.presentation.pages.mimuovo.permessipersonalizzati.richiestapermessipersonalizzati.util;

import org.apache.wicket.request.resource.PackageResourceReference;

public class Select2Reference extends PackageResourceReference {

  private static final long serialVersionUID = -3138959425429793708L;

  private static final String nomeFileJs = "select2.min.js";

  public Select2Reference(final Class<?> scope) {
    super(scope, nomeFileJs);
  }

  private static final class Holder {
    private static final Select2Reference INSTANCE = new Select2Reference(Select2Reference.class);
  }

  public static Select2Reference instance() {
    return Holder.INSTANCE;
  }
}
