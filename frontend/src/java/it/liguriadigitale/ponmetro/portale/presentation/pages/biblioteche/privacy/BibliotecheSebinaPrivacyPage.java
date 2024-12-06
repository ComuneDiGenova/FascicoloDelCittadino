package it.liguriadigitale.ponmetro.portale.presentation.pages.biblioteche.privacy;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.portale.business.configurazione.service.ConfigurazioneInterface;
import it.liguriadigitale.ponmetro.portale.presentation.components.behavior.AJAXDownload;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.LayoutNoFeedBackPage;
import it.liguriadigitale.ponmetro.portale.presentation.util.PageUtil;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.util.resource.IResourceStream;

public class BibliotecheSebinaPrivacyPage extends LayoutNoFeedBackPage {

  private static final long serialVersionUID = 9063982212522340400L;

  public BibliotecheSebinaPrivacyPage() {
    super();

    final AJAXDownload download =
        new AJAXDownload() {

          private static final long serialVersionUID = 7377472344072444750L;

          @Override
          protected IResourceStream getResourceStream() {

            ConfigurazioneInterface stampa = null;
            byte[] pdf = null;

            try {
              stampa = ServiceLocator.getInstance().getServiziConfigurazione();
              pdf = stampa.getInformativaSebina(getUtente());
            } catch (final BusinessException e) {
              log.error(
                  "[BibliotecheSebinaPrivacyPage] Errore durante scaricamento dell'informativa privacy Sebina: "
                      + e.getMessage(),
                  e);
              error("Errore durante scaricamento dell'informativa privacy Sebina");
            }
            return PageUtil.createResourceStream(pdf);
          }

          @Override
          protected String getFileName() {
            return getString("BibliotecheSebinaPrivacyPage.nomeFile");
          }
        };
    add(download);

    final AjaxLink<Page> linkDownload =
        new AjaxLink<Page>("btnPrivacySebina") {

          private static final long serialVersionUID = 1864472549025759815L;

          @Override
          protected void onComponentTag(final ComponentTag tag) {
            super.onComponentTag(tag);
            tag.put("target", "_blank");
          }

          @Override
          public void onClick(final AjaxRequestTarget target) {
            download.initiate(target);
          }
        };
    add(linkDownload);
  }
}
