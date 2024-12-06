package it.liguriadigitale.ponmetro.portale.presentation.pages.common.template;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;

public abstract class AbstractPannelloListaDatiPanel extends BasePanel {

  private static final long serialVersionUID = 1257639040270528420L;

  protected String testoDescrizionepanel;

  public AbstractPannelloListaDatiPanel(String id) {
    super(id);
  }

  public String getTestoDescrizionepanel() {
    return testoDescrizionepanel;
  }

  public void setTestoDescrizionepanel(String testoDescrizionepanel) {
    this.testoDescrizionepanel = testoDescrizionepanel;
  }

  @Override
  public void fillDati(final Object dati) {
    removeAll();

    setTestoDescrizionepanel(creaTestoDescrizionePanel(dati));
    this.add(fillDatiPannelloView(dati));
    createFeedBackPanel();
  }

  public Fragment fillDatiPannelloView(final Object dati) {
    Fragment viewFragment = new Fragment("group", "view", this);
    viewFragment.setOutputMarkupId(true);
    aggiungiElementiAlPannello(dati, viewFragment);
    viewFragment.add(new Label("descrizionepanel", Model.of(testoDescrizionepanel)));
    return viewFragment;
  }

  protected abstract String creaTestoDescrizionePanel(Object dati);

  protected abstract void aggiungiElementiAlPannello(Object dati, Fragment viewFragment);
}
