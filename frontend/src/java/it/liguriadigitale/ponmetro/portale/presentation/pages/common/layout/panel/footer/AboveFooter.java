package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.footer;

import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.framework.presentation.components.panel.FrameworkWebPanel;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneData;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneLinkEsterni;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.link.ExternalLink;

public class AboveFooter extends FrameworkWebPanel {

  private static final long serialVersionUID = 2259145769570634317L;

  static final String URL_SUGGERIMENTI =
      "https://smart.comune.genova.it/form/fascicolo-digitale-help";

  static final String URL_SODDISFAZIONE =
      "https://forms.office.com/pages/responsepage.aspx?id=-qqHhIpVokKdwG1JGQY1A8qCL3dVHwxIjOSnZN7nIuhUM1M5VkFLQkRKVEgzRVVORDlXMEpPVVgyMy4u";

  LinkDinamicoLaddaFunzione<Object> linkSuggerimenti;

  private static final String CHIAVE_CHATBOT = "CHATBOT";
  private static final String ENABLE = "ENABLE";

  public AboveFooter(String id) {
    super(id);

    addOrReplace(creaBottoneSuggerimenti());

    addOrReplace(linkSuggerimenti = creaBottoneSuggerimentiNew());

    ExternalLink linkSoddisfazione = new ExternalLink("linkSoddisfazione", URL_SODDISFAZIONE);
    addOrReplace(linkSoddisfazione);
    /*

    		Label scriptChatBot = new Label("scriptChatBot", "document.addEventListener(\"DOMContentLoaded\", function() {\r\n"
    				+ "			var initESW = function(gslbBaseURL) {\r\n"
    				+ "				embedded_svc.settings.displayHelpButton = true; //Or false\r\n"
    				+ "				embedded_svc.settings.language = 'it'; //For example, enter 'en' or 'en-US'\r\n"
    				+ "\r\n"
    				+ "				embedded_svc.settings.defaultMinimizedText = 'Chiedi a Cristoforo'; //(Defaults to Chat with an Expert)\r\n"
    				+ "				embedded_svc.settings.disabledMinimizedText = 'Cristoforo Colombo Offline'; //(Defaults to Agent Offline)\r\n"
    				+ "				embedded_svc.settings.avatarImgURL = 'https://www.comune.genova.it/risorse/chatbot/Colombo-40x40.png';\r\n"
    				+ "\r\n"
    				+ "				embedded_svc.settings.loadingText = 'Caricamento in corso ...'; //(Defaults to Loading)\r\n"
    				+ "				//embedded_svc.settings.storageDomain = 'yourdomain.com'; //(Sets the domain for your deployment so that visitors can navigate subdomains during a chat session)\r\n"
    				+ "\r\n"
    				+ "				// Settings for Chat\r\n"
    				+ "				//embedded_svc.settings.directToButtonRouting = function(prechatFormData) {\r\n"
    				+ "					// Dynamically changes the button ID based on what the visitor enters in the pre-chat form.\r\n"
    				+ "					// Returns a valid button ID.\r\n"
    				+ "				//};\r\n"
    				+ "				//embedded_svc.settings.prepopulatedPrechatFields = {}; //Sets the auto-population of pre-chat form fields\r\n"
    				+ "				//embedded_svc.settings.fallbackRouting = []; //An array of button IDs, user IDs, or userId_buttonId\r\n"
    				+ "				//embedded_svc.settings.offlineSupportMinimizedText = '...'; //(Defaults to Contact Us)\r\n"
    				+ "\r\n"
    				+ "				embedded_svc.settings.enabledFeatures = ['LiveAgent'];\r\n"
    				+ "				embedded_svc.settings.entryFeature = 'LiveAgent';\r\n"
    				+ "\r\n"
    				+ "				embedded_svc.init(\r\n"
    				+ "'" + BaseServiceImpl.CHATBOT_SALESFORCE + "'," + "\r\n"
    				+ "'" + BaseServiceImpl.CHATBOT_SALESFORCE_PARTNER + "'," + "\r\n"
    				+ "					gslbBaseURL,\r\n"
    				+ "'" + BaseServiceImpl.CHATBOT_ID_INIT + "'," + "\r\n"
    				+ "'" +BaseServiceImpl.CHATBOT_CRISTOFOROCOLOMBO + "'" + "\r\n"
    				+ ",\r\n"
    				+ "					{\r\n"
    				+ "						baseLiveAgentContentURL: "
    				+ "'" +BaseServiceImpl.CHATBOT_SALESFORCE_LIVEAGENT_CONTENT + "'," +  "\r\n"
    				+ "						deploymentId: "
    				+ "'" + BaseServiceImpl.CHATBOT_ID_DEPLOYMENT +"',"  +  "\r\n"
    				+ "						buttonId: " + "'" + BaseServiceImpl.CHATBOT_ID_BUTTON + "'," +  "\r\n"
    				+ "						baseLiveAgentURL: " + "'" + BaseServiceImpl.CHATBOT_SALESFORCE_LIVEAGENT_CHAT + "'," +  "\r\n"
    				+ "						eswLiveAgentDevName: " + "'" + BaseServiceImpl.CHATBOT_DEVNAME + "'," +  "\r\n"
    				+ "						isOfflineSupportEnabled: false\r\n"
    				+ "					}\r\n"
    				+ "				);\r\n"
    				+ "			};\r\n"
    				+ "\r\n"
    				+ "			if (!window.embedded_svc) {\r\n"
    				+ "				var s = document.createElement('script');\r\n"
    				+ "				s.setAttribute('src', 'https://comunedigenova--uat.sandbox.my.salesforce.com/embeddedservice/5.0/esw.min.js');\r\n"
    				+ "				s.onload = function() {\r\n"
    				+ "					initESW(null);\r\n"
    				+ "				};\r\n"
    				+ "				document.body.appendChild(s);\r\n"
    				+ "			} else {\r\n"
    				+ "				initESW('https://service.force.com');\r\n"
    				+ "			}\r\n"
    				+ "			});");

    		scriptChatBot.setEscapeModelStrings(false);
    		scriptChatBot.setVisible(isVisibleChatBot());
    		scriptChatBot.setOutputMarkupId(true);
    		scriptChatBot.setOutputMarkupPlaceholderTag(true);
    		addOrReplace(scriptChatBot);
    */

  }

  private LinkDinamicoLaddaFunzione<Object> creaBottoneSuggerimentiNew() {

    LinkDinamicoLaddaFunzione<Object> linkSuggerimenti =
        new LinkDinamicoLaddaFunzione<Object>(
            "linkSuggerimentiNew",
            new LinkDinamicoFunzioneData("AboveFooter.aiuto", "HelpCzRMPage", "AboveFooter.aiuto"),
            null,
            AboveFooter.this,
            "icon-suggerimenti align-middle mr-2");

    return linkSuggerimenti;
  }

  private LinkDinamicoFunzioneLinkEsterni<Object> creaBottoneSuggerimenti() {
    LinkDinamicoFunzioneLinkEsterni<Object> linkSuggerimenti =
        new LinkDinamicoFunzioneLinkEsterni<Object>(
            "linkSuggerimenti",
            LinkDinamicoFunzioneDataBuilder.getInstance()
                .setWicketLabelKeyText("AboveFooter.aiuto")
                .setWicketClassName("HelpIstituzionalePage")
                .setTargetInANewWindow(true)
                .build(),
            null,
            AboveFooter.this,
            "icon-suggerimenti align-middle mr-2",
            "",
            false);

    return linkSuggerimenti;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    String script =
        String.format(
            "$('#%s').removeClass('ladda-button')", linkSuggerimenti.getLinkImg().getMarkupId());
    response.render(OnDomReadyHeaderItem.forScript(script));
  }

  private boolean getValore(String chiave) {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);

      boolean visibilita =
          LabelFdCUtil.checkIfNotNull(valore) && valore.getValore().equalsIgnoreCase(ENABLE);
      boolean visibilitaTestEse = Constants.DEPLOY != Constants.TIPO_DEPLOY.ESERCIZIO;

      return (visibilita || visibilitaTestEse) ? true : false;

    } catch (BusinessException e) {
      log.error("Errore getValore ChatBot: ", e);
      return false;
    } finally {
      instance.closeConnection();
    }
  }

  public boolean isVisibleChatBot() {
    return getValore(CHIAVE_CHATBOT);
  }
}
