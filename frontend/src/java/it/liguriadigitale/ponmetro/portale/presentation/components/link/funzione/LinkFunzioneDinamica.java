package it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione;

import it.liguriadigitale.ponmetro.entitaconfigurazione.utente.model.DatiCompletiFunzione;
import org.apache.wicket.markup.html.panel.Panel;

public class LinkFunzioneDinamica extends Panel {

  private static final long serialVersionUID = 2113723183626067203L;

  //	private final Log			log						= LogFactory.getLog(getClass());
  //	private static final String	LINK_ID					= "link";
  //	private static final String	LABEL_ID				= "label";
  //	private static final String	ATTRIBUTE_TITLE			= "title";
  //	private static final String	ATTRIBUTE_TARGET		= "target";
  //	private static final String	ATTRIBUTE_TARGET_VALUE	= "_blank";
  //	private Component			object;
  //	private Link<Void>			link;
  //	private DatiCompletiFunzione datiFunzione;

  public LinkFunzioneDinamica(String id, DatiCompletiFunzione datiFunzione) {
    super(id);
    //		this.datiFunzione = datiFunzione;
    creaLink();
  }

  private void creaLink() {

    // link = new Link<Void>(datiFunzione.getDatiFunzione().g)

  }
}
