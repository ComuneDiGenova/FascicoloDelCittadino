package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.stato.statodomanda;

import it.liguriadigitale.ponmetro.portale.pojo.cedole.CedoleMinore;
import it.liguriadigitale.ponmetro.portale.pojo.cedole.RichiestaCedola;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoFunzioneDataBuilder;
import it.liguriadigitale.ponmetro.portale.presentation.components.link.funzione.LinkDinamicoLaddaFunzione;
import it.liguriadigitale.ponmetro.scuola.cedole.model.DomandaCedola;
import it.liguriadigitale.ponmetro.serviziristorazione.model.UtenteServiziRistorazione;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.panel.Panel;

public class DomandaNonPresentataPanel extends Panel {

  private static final long serialVersionUID = 1922276247258571694L;

  private Log log = LogFactory.getLog(DomandaNonPresentataPanel.class);

  public DomandaNonPresentataPanel(String id, CedoleMinore cedolaMinore) {
    super(id);
    UtenteServiziRistorazione iscritto = cedolaMinore.getMinore();

    //		Link presentaDomanda = new Link<Void>("nuova") {
    //			@Override
    //			public void onClick() {
    //				DomandaCedola domanda = convertiIscrittoCedola(iscritto);
    //				if (StringUtils.isEmpty(domanda.getIdScuola())) {
    //					setResponsePage(new DomandaCedolaStep1Page(cedolaMinore, domanda));
    //				} else {
    //					setResponsePage(new DomandaCedolaStep1Page(cedolaMinore, domanda));
    //				}
    //			}
    //		};

    DomandaCedola domanda = convertiIscrittoCedola(iscritto);
    RichiestaCedola richiestaCedola = new RichiestaCedola();
    richiestaCedola.setCedolaMinore(cedolaMinore);
    richiestaCedola.setDomanda(domanda);

    LinkDinamicoLaddaFunzione<RichiestaCedola> presentaDomanda =
        new LinkDinamicoLaddaFunzione<>(
            "nuova",
            LinkDinamicoFunzioneDataBuilder.getInstance()
                .setWicketLabelKeyText("DomandaNonPresentataPanel.nuova")
                .setWicketClassName("CedoleLibrarieNuovaPage")
                .setLinkTitleAdditionalText(iscritto.getNome())
                .build(),
            richiestaCedola,
            DomandaNonPresentataPanel.this,
            "color-orange col-auto icon-cedole-librarie");

    // presentaDomanda.setVisible(false);

    add(presentaDomanda);
  }

  public static DomandaCedola convertiIscrittoCedola(UtenteServiziRistorazione iscritto) {
    DomandaCedola cedola = new DomandaCedola();
    cedola.setCodiceFiscaleMinore(iscritto.getCodiceFiscale());
    cedola.setNomeMinore(iscritto.getNome());
    cedola.setCognomeMinore(iscritto.getCognome());
    cedola.setAnnoScolastico(getAnnoScolasticoCorrente());
    cedola.setClasse(iscritto.getClasse());
    cedola.setSezione(iscritto.getSezione());
    cedola.setIdScuola(iscritto.getCodiceScuola());
    return cedola;
  }

  private static String getAnnoScolasticoCorrente() {
    String annoScolastico = "";
    LocalDate oggi = LocalDate.now();
    if (oggi.getMonthValue() > Month.MAY.getValue()) {
      annoScolastico = oggi.getYear() + "-" + (oggi.getYear() + 1);
    } else {
      annoScolastico = (oggi.getYear() - 1) + "-" + oggi.getYear();
    }
    return annoScolastico;
  }

  public static boolean checkIfNull(Object obj) {
    return !Optional.ofNullable(obj).isPresent();
  }
}
