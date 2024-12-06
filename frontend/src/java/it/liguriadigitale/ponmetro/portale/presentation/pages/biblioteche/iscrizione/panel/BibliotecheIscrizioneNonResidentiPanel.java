package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.iscrizione.panel;

import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class BibliotecheIscrizioneNonResidentiPanel extends BasePanel {

  private static final long serialVersionUID = -8252037252297301765L;

  public BibliotecheIscrizioneNonResidentiPanel(String id) {
    super(id);

    setOutputMarkupId(true);
    createFeedBackPanel();

    fillDati("");
  }

  @Override
  public void fillDati(Object dati) {
    WebMarkupContainer iconaMarkup = new WebMarkupContainer("icona");
    Image image = new Image("image", new ContextRelativeResource("/images/bigmet.jpg"));
    iconaMarkup.add(image);
    addOrReplace(iconaMarkup);
  }
}
