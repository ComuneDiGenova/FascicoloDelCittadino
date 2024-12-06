package it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.renderheader;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeCssReference;
import it.liguriadigitale.framework.business.exceptions.BusinessException;
import it.liguriadigitale.ponmetro.api.pojo.homepage.dto.ChiaveValore;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.delegate.ServiceLocatorLivelloUno;
import it.liguriadigitale.ponmetro.portale.presentation.common.Constants;
import it.liguriadigitale.ponmetro.portale.presentation.util.LabelFdCUtil;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.CssContentHeaderItem;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.MetaDataHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;

public class RenderHeader {

  private static final String CHIAVE_CHATBOT = "CHATBOT";
  private static final String ENABLE = "ENABLE";

  public static void render(IHeaderResponse response, Application application) {

    response.render(
        new PriorityHeaderItem(
            StringHeaderItem.forString(
                "\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n")));

    // META deprecato, lasciato per retro-compatibilita
    // https://www.w3.org/International/questions/qa-http-and-lang
    // response.render(new PriorityHeaderItem(StringHeaderItem
    // .forString("\n<meta http-equiv=\"content-language\" content=\"it\" />\n")));

    response.render(
        StringHeaderItem.forString(
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n"));
    response.render(
        StringHeaderItem.forString(
            "<meta  name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"));
    response.render(MetaDataHeaderItem.forMetaTag("Expires", "0"));
    response.render(
        MetaDataHeaderItem.forMetaTag(
            "Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, private"));
    response.render(MetaDataHeaderItem.forMetaTag("Pragma", "no-cache"));
    response.render(MetaDataHeaderItem.forMetaTag("Author", "Liguria Digitale"));
    response.render(
        MetaDataHeaderItem.forMetaTag(
            "Description", Constants.Componente.GESTORE.getNomeComponente()));
    // CSS
    Bootstrap.renderHead(response);
    // response.render(CssContentHeaderItem.forUrl("/" +
    // Constants.Componente.GESTORE.getWebContext() + "/css/" +
    // "genova.css"));
    response.render(
        CssContentHeaderItem.forUrl(
            "/"
                + Constants.Componente.GESTORE.getWebContext()
                + "/bootstrap-italia/css/bootstrap-italia.min.css"));
    response.render(
        CssContentHeaderItem.forUrl(
            "/" + Constants.Componente.GESTORE.getWebContext() + "/css/" + "style.css"));
    response.render(
        CssContentHeaderItem.forUrl(
            "/" + Constants.Componente.GESTORE.getWebContext() + "/css/" + "fascicolo.css"));
    response.render(CssHeaderItem.forReference(FontAwesomeCssReference.instance()));
    // // JavaScript
    // response.render(
    // JavaScriptHeaderItem.forReference(application.getJavaScriptLibrarySettings().getJQueryReference()));

    response.render(
        JavaScriptHeaderItem.forReference(
            application.getJavaScriptLibrarySettings().getJQueryReference()));
    response.render(
        JavaScriptHeaderItem.forUrl(
            "/"
                + Constants.Componente.GESTORE.getWebContext()
                + "/bootstrap-italia/js/bootstrap-italia.bundle.min.js"));

    // response.render(JavaScriptHeaderItem.forUrl("https://service.force.com/embeddedservice/5.0/esw.min.js"));
    // Aggiungi il file JavaScript remoto all'header della pagina

    StringBuilder sb = new StringBuilder();

    sb.append(
        "var observer = new MutationObserver(function(mutations) {\r\n"
            + " setTimeout(function() {\r\n"
            + "$(\".progress-spinner-active\").css(\"border-color\", \"#06c #06c #d4e9ff\");\r\n"
            + "    }, 0);"
            + " setTimeout(function() {\r\n"
            + "$(\".progress-spinner-active\").css(\"border-color\", \"#d9364f #d9364f #d4e9ff\");\r\n"
            + "    }, 6000);"
            + "  });\r\n"
            + "  var target = document.querySelector('#indicator');\r\n"
            + "  observer.observe(target, {\r\n"
            + "    attributes: true\r\n"
            + "  });\r\n");

    if (isVisibleChatBot()) {

      response.render(
          JavaScriptHeaderItem.forUrl("https://service.force.com/embeddedservice/5.0/esw.min.js"));

      sb.append(
          ""
              + "	    var initESW = function(gslbBaseURL) {\r\n"
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
              + "'"
              + BaseServiceImpl.CHATBOT_SALESFORCE
              + "',"
              + "\r\n"
              + "'"
              + BaseServiceImpl.CHATBOT_SALESFORCE_PARTNER
              + "',"
              + "\r\n"
              + "					gslbBaseURL,\r\n"
              + "'"
              + BaseServiceImpl.CHATBOT_ID_INIT
              + "',"
              + "\r\n"
              + "'"
              + BaseServiceImpl.CHATBOT_CRISTOFOROCOLOMBO
              + "'"
              + "\r\n"
              + ",\r\n"
              + "					{\r\n"
              + "						baseLiveAgentContentURL: "
              + "'"
              + BaseServiceImpl.CHATBOT_SALESFORCE_LIVEAGENT_CONTENT
              + "',"
              + "\r\n"
              + "						deploymentId: "
              + "'"
              + BaseServiceImpl.CHATBOT_ID_DEPLOYMENT
              + "',"
              + "\r\n"
              + "						buttonId: "
              + "'"
              + BaseServiceImpl.CHATBOT_ID_BUTTON
              + "',"
              + "\r\n"
              + "						baseLiveAgentURL: "
              + "'"
              + BaseServiceImpl.CHATBOT_SALESFORCE_LIVEAGENT_CHAT
              + "',"
              + "\r\n"
              + "						eswLiveAgentDevName: "
              + "'"
              + BaseServiceImpl.CHATBOT_DEVNAME
              + "',"
              + "\r\n"
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
              // + " });\r\n"

              + "$(\"head\").append(\"<style>.embeddedServiceSidebar li.chatMessage { opacity:1; transition:opacity 0.3s ease-in; } .embeddedServiceSidebar li.chatMessage:not([verificato]) {opacity:0;} .embeddedServiceSidebar li.chatMessage a { font-size:0.82rem; font-weight:bold; } .embeddedServiceSidebar li.chatMessage a::before { margin-right:7px; content:''; display:inline-block; vertical-align:middle; width:16px; height:16px; background:url('https://www.comune.genova.it/risorse/chatbot/maggiori-informazioni.svg'); } .embeddedServiceSidebar li.chatMessage a[href*='youtube.com']::before { background:url('https://www.comune.genova.it/risorse/chatbot/youtube.svg'); } .embeddedServiceSidebar li.chatMessage a[href*='youtu.be']::before { background:url('https://www.comune.genova.it/risorse/chatbot/youtube.svg'); } .embeddedServiceSidebar li.chatMessage a[href*='fascicolo.comune']::before { background:url('https://www.comune.genova.it/risorse/chatbot/servizio.svg'); } .embeddedServiceSidebar li.chatMessage a[href*='servizi.comune']::before { background:url('https://www.comune.genova.it/risorse/chatbot/servizio.svg'); } </style>\");\r\n"
              + "\r\n"
              + "\r\n"
              + "let chatBot_Interval = null;\r\n"
              + "clearInterval(chatBot_Interval);\r\n"
              + "chatBot_Interval = setInterval(function () {\r\n"
              + "	\r\n"
              + "	let chatBot_defaultText = \"Maggiori informazioni\";//Testo che avr&agrave;  il link\r\n"
              + "	let chatBot_youtubeText = \"Guarda su youtube\";//Testo che avr&agrave;  il link youtube\r\n"
              + "	let chatBot_serviceText = \"Accedi al servizio\";//Testo che avr&agrave;  il link al servizio\r\n"
              + "	\r\n"
              + "	$(\".embeddedServiceSidebar li.chatMessage:not([verificato])\").each(function( i,e ) {\r\n"
              + "	  if( $('a', e).length )\r\n"
              + "	  {\r\n"
              + "		let aLink = $('a', e).attr('href');\r\n"
              + "		$('a', e).text(chatBot_defaultText);\r\n"
              + "		\r\n"
              + "		if( aLink.indexOf('fascicolo.comune') >= 0 )\r\n"
              + "			$('a', e).text(chatBot_serviceText);\r\n"
              + "		if( aLink.indexOf('servizi.comune') >= 0 )\r\n"
              + "			$('a', e).text(chatBot_serviceText);\r\n"
              + "		if( aLink.indexOf('youtube.com') >= 0 )\r\n"
              + "			$('a', e).text(chatBot_youtubeText);\r\n"
              + "		if( aLink.indexOf('youtu.be') >= 0 )\r\n"
              + "			$('a', e).text(chatBot_youtubeText);\r\n"
              + "	  }\r\n"
              + "	  \r\n"
              + "	  $(this).attr('verificato',\"\");\r\n"
              + "	});\r\n"
              + "		\r\n"
              + "	\r\n"
              + "}, 500);");
    }

    response.render(JavaScriptHeaderItem.forScript(sb.toString(), null));

    // response.render(JavaScriptHeaderItem.forUrl("https://www.comune.genova.it/risorse/chatbot/script.js"));

  }

  private static boolean getValore(String chiave) {
    ServiceLocatorLivelloUno instance = ServiceLocatorLivelloUno.getInstance();
    try {
      ChiaveValore valore = instance.getApiHomePage().getValore(chiave);

      boolean visibilita =
          LabelFdCUtil.checkIfNotNull(valore) && valore.getValore().equalsIgnoreCase(ENABLE);
      // boolean visibilitaTestEse = Constants.DEPLOY != Constants.TIPO_DEPLOY.ESERCIZIO;

      // return (visibilita || visibilitaTestEse) ? true : false;

      return visibilita;

    } catch (BusinessException e) {
      return false;
    } finally {
      instance.closeConnection();
    }
  }

  public static boolean isVisibleChatBot() {
    return getValore(CHIAVE_CHATBOT);
  }
}
