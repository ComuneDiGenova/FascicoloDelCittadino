package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.panel;

import it.liguriadigitale.ponmetro.portale.pojo.biblioteche.BibliotecheIscrizione;
import it.liguriadigitale.ponmetro.portale.pojo.login.ComponenteNucleo;
import it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.movimenti.form.BibliotecheMovimentiPrivacyForm;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;

public class BibliotecheMovimentiPrivacyPanel extends BasePanel {

  private static final long serialVersionUID = 4930825876935023885L;

  private Boolean maggiorenne;

  private ComponenteNucleo bambino;

  public BibliotecheMovimentiPrivacyPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    fillDati(new BibliotecheIscrizione());
  }

  public BibliotecheMovimentiPrivacyPanel(String id, Boolean maggiorenne) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    this.setMaggiorenne(maggiorenne);

    fillDati(new BibliotecheIscrizione());
  }

  public BibliotecheMovimentiPrivacyPanel(String id, ComponenteNucleo bambino) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    this.setBambino(bambino);

    fillDati(new BibliotecheIscrizione());
  }

  public BibliotecheMovimentiPrivacyPanel(
      String id, ComponenteNucleo bambino, boolean maggiorenne) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    this.setBambino(bambino);
    this.setMaggiorenne(maggiorenne);

    fillDati(new BibliotecheIscrizione());
  }

  @Override
  public void fillDati(Object dati) {
    BibliotecheIscrizione bibliotecheIscrizione = (BibliotecheIscrizione) dati;
    BibliotecheMovimentiPrivacyForm form =
        new BibliotecheMovimentiPrivacyForm(
            "form", bibliotecheIscrizione, getUtente(), getMaggiorenne(), getBambino());
    form.setOutputMarkupId(true);
    addOrReplace(form);
  }

  public Boolean getMaggiorenne() {
    return maggiorenne;
  }

  public void setMaggiorenne(Boolean maggiorenne) {
    this.maggiorenne = maggiorenne;
  }

  public ComponenteNucleo getBambino() {
    return bambino;
  }

  public void setBambino(ComponenteNucleo bambino) {
    this.bambino = bambino;
  }
}
