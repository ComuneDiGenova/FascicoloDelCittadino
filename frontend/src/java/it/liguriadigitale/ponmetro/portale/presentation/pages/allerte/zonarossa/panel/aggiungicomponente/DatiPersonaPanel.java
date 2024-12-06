package it.liguriadigitale.ponmetro.portale.presentation.pages.allerte.zonarossa.panel.aggiungicomponente;

import it.liguriadigitale.ponmetro.portale.pojo.allerte.zonarossa.ComponenteNucleoZonaRossa;
import it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa.TipoPersonaDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.allerte.zonarossa.VulnerabilitaPersonaleDropDownChoice;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCEmailTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.FdCTextField;
import it.liguriadigitale.ponmetro.portale.presentation.components.form.input.panel.FdCTitoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class DatiPersonaPanel extends BasePanel {

  private static final long serialVersionUID = 912343369007969014L;

  private int index;

  public DatiPersonaPanel(String id, ComponenteNucleoZonaRossa componente, int index) {
    super(id);

    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);

    this.setIndex(index);

    fillDati(componente);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void fillDati(Object dati) {
    ComponenteNucleoZonaRossa componente = (ComponenteNucleoZonaRossa) dati;

    addOrReplace(new FdCTitoloPanel("titolo", getString("DatiPersonaPanel.titolo")));

    FdCTextField nome =
        new FdCTextField("nome", new PropertyModel(componente, "nome"), DatiPersonaPanel.this);
    nome.getTextField().setRequired(true);
    addOrReplace(nome);

    FdCTextField cognome =
        new FdCTextField(
            "cognome", new PropertyModel(componente, "cognome"), DatiPersonaPanel.this);
    cognome.getTextField().setRequired(true);
    addOrReplace(cognome);

    FdCTextField codiceFiscale =
        new FdCTextField(
            "codiceFiscale", new PropertyModel(componente, "codiceFiscale"), DatiPersonaPanel.this);
    codiceFiscale.getTextField().setRequired(true);
    addOrReplace(codiceFiscale);

    FdCEmailTextField eMail =
        new FdCEmailTextField(
            "eMail", new PropertyModel(componente, "eMail"), DatiPersonaPanel.this);
    eMail.getTextField().setRequired(true);
    addOrReplace(eMail);

    VulnerabilitaPersonaleDropDownChoice vulnerabilitaPersonale =
        new VulnerabilitaPersonaleDropDownChoice(
            "vulnerabilitaPersonale", new PropertyModel(componente, "vulnerabilitaPersonale"));
    vulnerabilitaPersonale.setRequired(true);
    vulnerabilitaPersonale.setLabel(Model.of("Vulnerabilità personale"));
    addOrReplace(vulnerabilitaPersonale);

    TipoPersonaDropDownChoice tipo =
        new TipoPersonaDropDownChoice("tipo", new PropertyModel(componente, "tipo"));
    tipo.setRequired(true);
    tipo.setLabel(Model.of("Tipo"));
    addOrReplace(tipo);
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
