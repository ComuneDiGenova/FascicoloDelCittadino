package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel;

import it.liguriadigitale.ponmetro.portale.pojo.login.Utente;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template.GeneratoreCardLabel;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class MieiDatiNonResidentiPanel<T> extends BasePanel {

  private static final long serialVersionUID = -1665841515484077830L;

  private static final String ICON_MIEI_DATI = "color-yellow col-3 icon-carta-id";

  public MieiDatiNonResidentiPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {

    WebMarkupContainer icona = new WebMarkupContainer("icona");
    icona.add(getCssIconaDati());
    addOrReplace(icona);

    Utente istanza = this.getUtente();
    Map<String, Object> mappaNomeValore = fillMappa(istanza);

    GeneratoreCardLabel<Utente> panel =
        new GeneratoreCardLabel<>("panel", mappaNomeValore, this.getClass().getSimpleName());
    add(panel);
  }

  private AttributeAppender getCssIconaDati() {
    String css = ICON_MIEI_DATI;

    return new AttributeAppender("class", " " + css);
  }

  protected Map<String, Object> fillMappa(Utente istanza) {

    Map<String, Object> mappaNomeValore = new LinkedHashMap<>();
    mappaNomeValore.put("Nome", istanza.getNome());
    mappaNomeValore.put("Cognome", istanza.getCognome());
    mappaNomeValore.put("CodiceFiscaleOperatore", istanza.getCodiceFiscaleOperatore());
    mappaNomeValore.put("LuogoNascita", istanza.getLuogoNascita());
    mappaNomeValore.put("DataDiNascita", istanza.getDataDiNascita());
    mappaNomeValore.put("Domicilio", istanza.getDomicilio());
    mappaNomeValore.put("Mobile", istanza.getMobile());
    mappaNomeValore.put("Mail", istanza.getMail());
    return mappaNomeValore;
  }
}
