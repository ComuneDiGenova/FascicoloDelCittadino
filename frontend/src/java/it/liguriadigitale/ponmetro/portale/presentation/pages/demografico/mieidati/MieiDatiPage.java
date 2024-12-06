package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati;

import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.FdCBreadcrumbPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.breadcrumb.MapBreadcrumbsFdc;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.MieiDatiAnagraficiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.MieiDatiCatastaliPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.MieiDatiElettoraliPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.MieiDatiNonResidentiPanel;
import it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.MieiDatiZonaRischioPanel;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class MieiDatiPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 1369846032127346813L;

  public MieiDatiPage() {
    super();

    if (getUtente().isResidente()) {
      log.debug("Utente is residente, valore: " + getUtente().isResidente());
      creaPannelliResidente();
    } else {
      log.debug("Utente is NON residente, valore: " + getUtente().isResidente());
      MieiDatiNonResidentiPanel mieiDatiNonResidentiPanel =
          (MieiDatiNonResidentiPanel)
              new MieiDatiNonResidentiPanel("mieiDati").setRenderBodyOnly(true);
      addOrReplace(mieiDatiNonResidentiPanel);
      addOrReplace(new EmptyPanel("mieiDatiCastastali"));
      addOrReplace(new EmptyPanel("mieiDatiZonaRischio"));
      addOrReplace(new EmptyPanel("mieiDatiElettorali"));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    FdCBreadcrumbPanel breadcrumbPanel =
        new FdCBreadcrumbPanel(
            "breadcrumbPanel",
            MapBreadcrumbsFdc.getListaBreadcrumbDaVisualizzare(this.getClass(), getUtente()));
    add(breadcrumbPanel);
  }

  private void creaPannelliResidente() {
    add(createPanelDatiCatastali("mieiDatiCastastali"));
    add(createPanelDatiAnagrafici("mieiDati"));
    add(createPanelDatiZonaRischio("mieiDatiZonaRischio"));

    if (LocalDateUtil.isMaggiorenne(getUtente().getDataDiNascita())
        && getUtente()
            .getDatiCittadinoResidente()
            .getCpvHasCitizenship()
            .getClvHasIdentifier()
            .equalsIgnoreCase("100")) {
      add(createPanelDatiElettorali("mieiDatiElettorali"));
    } else {
      add(new EmptyPanel("mieiDatiElettorali").setRenderBodyOnly(true));
    }
  }

  @SuppressWarnings({"rawtypes", "unused"})
  private void addLazyPanelMieiDatiElettorali() {
    add(
        new AjaxLazyLoadPanel("mieiDatiElettorali") {

          private static final long serialVersionUID = -1621006830078864694L;

          @Override
          public Component getLazyLoadComponent(String id) {
            return createPanelDatiElettorali(id).setRenderBodyOnly(true);
          }
        });
  }

  @SuppressWarnings({"rawtypes", "unused"})
  private void addLazyPanelDatiAnagrafici() {
    add(
        new AjaxLazyLoadPanel("mieiDati") {

          private static final long serialVersionUID = -1621006830078864694L;

          @Override
          public Component getLazyLoadComponent(String id) {
            return createPanelDatiAnagrafici(id).setRenderBodyOnly(true);
          }
        });
  }

  @SuppressWarnings({"rawtypes", "unused"})
  private void addLazyPanelDatiCatastali() {
    add(
        new AjaxLazyLoadPanel("mieiDatiCastastali") {

          private static final long serialVersionUID = 8698174100395587885L;

          @Override
          public Component getLazyLoadComponent(String id) {

            MieiDatiCatastaliPanel panel = createPanelDatiCatastali(id);
            panel.setRenderBodyOnly(true);
            this.setVisible(panel.isVisible());
            return panel;
          }
        });
  }

  @SuppressWarnings({"rawtypes", "unused"})
  private void addLazyPanelDatiZonaRischio() {
    add(
        new AjaxLazyLoadPanel("mieiDatiZonaRischio") {

          private static final long serialVersionUID = -5906391410106780081L;

          @Override
          public Component getLazyLoadComponent(String id) {

            MieiDatiZonaRischioPanel panel = createPanelDatiZonaRischio(id);
            panel.setRenderBodyOnly(true);
            this.setVisible(panel.isVisible());
            return panel;
          }
        });
  }

  private MieiDatiZonaRischioPanel createPanelDatiZonaRischio(String id) {
    return new MieiDatiZonaRischioPanel(id);
  }

  private MieiDatiElettoraliPanel createPanelDatiElettorali(String id) {
    return new MieiDatiElettoraliPanel(id);
  }

  private MieiDatiAnagraficiPanel createPanelDatiAnagrafici(String id) {
    return new MieiDatiAnagraficiPanel(id);
  }

  private MieiDatiCatastaliPanel createPanelDatiCatastali(String id) {
    return new MieiDatiCatastaliPanel(id);
  }
}
