package it.liguriadigitale.ponmetro.portale.presentation.components.link;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.Icon;
import it.liguriadigitale.ponmetro.portale.pojo.gestore.Anno;
import org.apache.wicket.markup.html.link.Link;

public abstract class ScorriAnnoLink extends Link<Anno> {

  private static final long serialVersionUID = -4079789939195753439L;
  private Anno anno;
  private final Tipo tipoBottone;
  private Icon icona;

  public enum Tipo {
    PRECEDENTE,
    SUCCESSIVO
  }

  public ScorriAnnoLink(Anno anno, Tipo tipoBottone) {
    super("link");
    this.anno = anno;
    this.tipoBottone = tipoBottone;

    switch (tipoBottone) {
      case PRECEDENTE:
        setVisible(isVisualizzabileIconaPrecedente(anno));
        icona = new Icon("sign", GlyphIconType.chevronleft);
        break;
      case SUCCESSIVO:
        setVisible(isVisualizzabileIconaSuccessiva(anno));
        icona = new Icon("sign", GlyphIconType.chevronright);
        break;
      default:
        setVisible(false);
        break;
    }
    add(icona);
  }

  private boolean isVisualizzabileIconaSuccessiva(Anno anno) {
    return anno.getAnnoSelezionato() <= anno.getLimiteSuperiore();
  }

  private boolean isVisualizzabileIconaPrecedente(Anno anno) {
    return !anno.getAnnoSelezionato().equals(Anno.LIMITE_INFERIORE);
  }

  public Anno getAnno() {
    return anno;
  }

  public Tipo getTipoBottone() {
    return tipoBottone;
  }
}
