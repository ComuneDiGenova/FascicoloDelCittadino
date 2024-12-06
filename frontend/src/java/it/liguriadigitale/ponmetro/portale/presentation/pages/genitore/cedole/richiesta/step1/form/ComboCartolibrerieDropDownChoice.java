package it.liguriadigitale.ponmetro.portale.presentation.pages.genitore.cedole.richiesta.step1.form;

import it.liguriadigitale.framework.presentation.components.combo.ComboLoadableDetachableModel;
import it.liguriadigitale.ponmetro.portale.presentation.components.combo.renderer.cedole.CartolibreriaRenderer;
import it.liguriadigitale.ponmetro.scuola.cedole.model.Cartolibreria;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ComboCartolibrerieDropDownChoice<T> extends DropDownChoice<T> {

  private static final long serialVersionUID = 6227197185578449030L;

  private IChoiceRenderer render;

  private List<Cartolibreria> listaCartolibrerie;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public ComboCartolibrerieDropDownChoice(
      String id, IModel modello, List<Cartolibreria> listaCartolibrerie) {
    super(id);
    this.render = new CartolibreriaRenderer();
    this.listaCartolibrerie = listaCartolibrerie;

    setModel(modello);
    setChoiceRenderer(render);

    setRequired(true);
    setOutputMarkupId(true);
    setOutputMarkupPlaceholderTag(true);
    setLabel(Model.of("Seleziona cartoleria"));

    setChoices(new ComboLoadableDetachableModel(listaCartolibrerie));
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.onBeforeRender();

    for (Cartolibreria cartolibreria : listaCartolibrerie) {
      if (cartolibreria.getQuartiere() == null) {
        cartolibreria.setQuartiere("N.C.");
      }
    }

    listaCartolibrerie.sort(
        (p1, p2) ->
            p1.getDenominazioneCartolibreria()
                .toUpperCase()
                .compareTo(p2.getDenominazioneCartolibreria().toUpperCase()));

    Map<String, List<Cartolibreria>> cartolibrerieRaggruppatePerQuartiere =
        listaCartolibrerie.stream().collect(Collectors.groupingBy(Cartolibreria::getQuartiere));

    StringBuilder sb = new StringBuilder();

    sb.append("$('#" + getMarkupId() + "')\r\n" + "    .find('option')\r\n" + "    .remove();\r\n");

    Set<String> quartieri = cartolibrerieRaggruppatePerQuartiere.keySet();

    List<String> listaQuartieri = new ArrayList<>(quartieri);

    listaQuartieri.sort((p1, p2) -> p1.toUpperCase().compareTo(p2.toUpperCase()));

    int idQuartiere = 1;
    for (String quartiere : listaQuartieri) {
      String strIdQuartiere = "q" + idQuartiere;

      sb.append("var " + strIdQuartiere + " = [\r\n");
      List<Cartolibreria> listaCarto = cartolibrerieRaggruppatePerQuartiere.get(quartiere);

      for (Cartolibreria cartolibreria : listaCarto) {
        sb.append(
            "  {descrizione: '"
                + cartolibreria.getDenominazioneCartolibreria().replace("'", "\"")
                + " - "
                + cartolibreria.getIndirizzo().replace("'", "\"")
                + "',id: '"
                + cartolibreria.getIdCartolibreria()
                + "'},\r\n");
      }
      sb.append("];\r\n");

      idQuartiere++;
    }

    idQuartiere = 1;
    for (String quartiere : listaQuartieri) {
      String strIdQuartiere = "q" + idQuartiere;
      sb.append("var optgroup = \"<optgroup label='" + quartiere + "'>\";\r\n");

      sb.append(
          "for (var i = 0; i < "
              + strIdQuartiere
              + ".length; i++) {\r\n"
              + "		    descrizione = "
              + strIdQuartiere
              + "[i].descrizione; id = "
              + strIdQuartiere
              + "[i].id\r\n"
              + "		    optgroup += '<option value=\"' + id + '\">' + descrizione + '</option>';\r\n"
              + "		}\r\n");

      // sb.append("optgroup += \"<optgroup>;\"\r\n");
      sb.append("$('#" + getMarkupId() + "').append(optgroup);\r\n");

      idQuartiere++;
    }

    sb.append(
        "$('#"
            + getMarkupId()
            + "').prepend('<option selected=\"selected\" value=\"\">Sceglierne uno</option>');\r\n");

    response.render(OnDomReadyHeaderItem.forScript(sb.toString()));
  }
}
