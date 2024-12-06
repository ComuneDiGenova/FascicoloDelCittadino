package it.liguriadigitale.ponmetro.portale.presentation.pages.home.sezioni.widget;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxLink;
import it.liguriadigitale.ponmetro.api.pojo.homepage.db.FunzioniDisponibili;
import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiVisualizzazioneSezioneWidget;
import it.liguriadigitale.ponmetro.portale.presentation.application.session.LoginInSession;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.GloboPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.globo.param.GloboPathParametersName;
import java.util.List;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class WidgetGloboDinamico extends MyWidgetPanel {

  private static final long serialVersionUID = -5219415438683741620L;

  private DatiVisualizzazioneSezioneWidget widget;

  public WidgetGloboDinamico(POSIZIONE posizione, DatiVisualizzazioneSezioneWidget widget) {
    super(posizione);
    this.widget = widget;
    fillDati("");
    setOutputMarkupId(true);
  }

  @Override
  protected void mostraTestoWidget() {
    add(creaLinkImg());
  }

  @Override
  protected void mostraIcona() {
    // TODO Auto-generated method stub
  }

  private LaddaAjaxLink<Object> creaLinkImg() {
    LaddaAjaxLink<Object> linkImg =
        new LaddaAjaxLink<Object>("linkImg", Type.Link) {

          private static final long serialVersionUID = -4851433943716452980L;

          @Override
          public void onClick(AjaxRequestTarget target) {
            target.add(WidgetGloboDinamico.this);
            PageParameters parameters = new PageParameters();
            parameters.set(
                GloboPathParametersName.ID_FUNZIONE.name(), widget.getFunzione().getIdFunzione());
            setResponsePage(new GloboPage(parameters));
          }
        };

    IconType iconType =
        new IconType("linkImg") {

          private static final long serialVersionUID = 7360298959160812194L;

          @Override
          public String cssClassName() {
            return getIconaCssDaFunzioneFdc();
          }
        };

    linkImg.setSpinnerColor("#5cdba6");
    linkImg.setIconType(iconType);
    linkImg.setLabel(Model.of(widget.getWidget().getDescrizioneWidg()));
    linkImg.setOutputMarkupId(true);

    return linkImg;
  }

  private String getIconaCssDaFunzioneFdc() {
    String codiceFdc = widget.getFunzione().getIdFunzione();
    if (codiceFdc != null) {
      LoginInSession session = (LoginInSession) Session.get();
      List<FunzioniDisponibili> lista = session.getPagineAbilitate();
      for (FunzioniDisponibili funzione : lista) {
        String iconaCss = funzione.getIconaCss();
        if (funzione.getIdFunz().toString().equals(codiceFdc)) {
          return iconaCss;
        }
      }
    }
    return "icon-foglio-penna col-auto color-blue-sebina";
  }
}
