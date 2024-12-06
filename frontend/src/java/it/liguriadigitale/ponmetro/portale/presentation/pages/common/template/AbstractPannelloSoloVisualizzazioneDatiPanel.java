package it.liguriadigitale.ponmetro.portale.presentation.pages.common.template;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import java.io.Serializable;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;

public abstract class AbstractPannelloSoloVisualizzazioneDatiPanel extends BasePanel {

  private static final long serialVersionUID = 1257639040270528420L;

  protected String testoDescrizionepanel;

  protected boolean visibile;

  public AbstractPannelloSoloVisualizzazioneDatiPanel(String id) {
    super(id);
    visibile = true;
  }

  public AbstractPannelloSoloVisualizzazioneDatiPanel(String id, boolean visibile) {
    super(id);
    this.visibile = visibile;
  }

  protected abstract String creaPannello(Object dati, Fragment viewFragment);

  public String getTestoDescrizionepanel() {
    return testoDescrizionepanel;
  }

  public void setTestoDescrizionepanel(String testoDescrizionepanel) {
    this.testoDescrizionepanel = testoDescrizionepanel;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void fillDati(final Object dati) {
    removeAll();
    setTestoDescrizionepanel(creaTestoDescrizionePanel(dati));
    add(
        new Label(
            "descrizionepanel",
            new Model() {
              private static final long serialVersionUID = 1L;

              @Override
              public Serializable getObject() {
                return testoDescrizionepanel;
              }
            }));
    Fragment viewFragment = fillDatiPannelloView(dati);
    this.add(viewFragment);
  }

  protected Fragment fillDatiPannelloView(final Object dati) {
    final Fragment viewFragment = new Fragment("group", "view", this);
    @SuppressWarnings("unused")
    String testoEtichettaBottone = creaPannello(dati, viewFragment);
    return viewFragment;
  }

  protected abstract String creaTestoDescrizionePanel(Object dati);
}
