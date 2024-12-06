package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.certificati.statocivile;

import it.liguriadigitale.ponmetro.portale.presentation.common.paginazione.PaginazioneFascicoloPanel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import it.liguriadigitale.ponmetro.portale.presentation.components.label.NotEmptyLabel;
import it.liguriadigitale.ponmetro.portale.presentation.delegate.ServiceLocator;
import it.liguriadigitale.ponmetro.portale.presentation.pages.common.layout.panel.BasePanel;
import it.liguriadigitale.ponmetro.servizianagrafici.model.CertificatoAttoPersona;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;

public class ElencoCertificatiRichiestiPanel extends BasePanel {

  private static final String E_MAIL = "E-MAIL";

  private static final String ON_LINE = "ON-LINE";

  private static final long serialVersionUID = -3021289773196275267L;

  protected PaginazioneFascicoloPanel paginazioneFascicolo;

  public ElencoCertificatiRichiestiPanel(String id) {
    super(id);
    List<CertificatoAttoPersona> lista =
        ServiceLocator.getInstance()
            .getCertificatiStatoCivile()
            .elencoCertificatiRichiesti(getUtente());
    setOutputMarkupId(true);
    fillDati(lista);
    createFeedBackPanel();
  }

  public ElencoCertificatiRichiestiPanel(String id, List<CertificatoAttoPersona> lista) {
    this(id);
    setOutputMarkupId(true);
    fillDati(lista);
    createFeedBackPanel();
  }

  @Override
  @SuppressWarnings("unchecked")
  public void fillDati(Object dati) {
    List<CertificatoAttoPersona> lista = (List<CertificatoAttoPersona>) dati;
    lista.stream()
        .sorted(Comparator.comparing(CertificatoAttoPersona::getDataRichiesta).reversed())
        .collect(Collectors.toList());
    mostraLista(lista);
  }

  private void mostraLista(List<CertificatoAttoPersona> lista) {

    PageableListView<CertificatoAttoPersona> listView =
        new PageableListView<CertificatoAttoPersona>("lista", lista, 4) {

          private static final long serialVersionUID = -593847571381978380L;

          @Override
          protected void populateItem(ListItem<CertificatoAttoPersona> item) {
            CertificatoAttoPersona certificato = item.getModelObject();

            String cfIntestatario = "";
            String nomeIntestatario = "";
            String cognomeIntestatario = "";
            String cfRichiedente = "";
            String nomeRichiedente = "";
            String cognomeRichiedente = "";

            // intestazione
            item.addOrReplace(new NotEmptyLabel("stato", certificato.getStatoRichiesta()));
            // valori
            item.add(
                new CardLabel<>(
                    "dataRichiesta",
                    certificato.getDataRichiesta(),
                    ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "tipo",
                    togliCodiceDaDescrizione(certificato.getTipoCertificato()),
                    ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "idRichiesta",
                    certificato.getIdentificativoRichiesta(),
                    ElencoCertificatiRichiestiPanel.this));
            if (certificato.getEstremiIntestatario() != null) {
              cfIntestatario = certificato.getEstremiIntestatario().getCodiceFiscale();
              nomeIntestatario = certificato.getEstremiIntestatario().getNome();
              cognomeIntestatario = certificato.getEstremiIntestatario().getCognome();
            }
            if (certificato.getEstremiRichiedente() != null) {
              cfRichiedente = certificato.getEstremiRichiedente().getCodiceFiscale();
              nomeRichiedente = certificato.getEstremiRichiedente().getNome();
              cognomeRichiedente = certificato.getEstremiRichiedente().getCognome();
            }
            item.add(
                new CardLabel<>(
                    "cfIntestatario", cfIntestatario, ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "nomeIntestatario", nomeIntestatario, ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "cognomeIntestatario",
                    cognomeIntestatario,
                    ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "cfRichiedente", cfRichiedente, ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "nomeRichiedente", nomeRichiedente, ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "cognomeRichiedente",
                    cognomeRichiedente,
                    ElencoCertificatiRichiestiPanel.this));
            item.add(
                new CardLabel<>(
                    "tipoConsegnaScelta",
                    null,
                    // decodificaTipoConsegna(certificato),
                    ElencoCertificatiRichiestiPanel.this));
          }

          private String togliCodiceDaDescrizione(String tipoCertificato) {

            String na01c = "na01c";
            String ma08 = "-ma08";
            String ma07 = "ma07";
            if (tipoCertificato.contains(na01c)) {
              return tipoCertificato.replace(na01c, "");
            } else if (tipoCertificato.contains(ma07)) {
              return tipoCertificato.replace(ma07, "");
            } else if (tipoCertificato.contains(ma08)) {
              return tipoCertificato.replace(ma08, "");
            } else {
              return tipoCertificato;
            }
          }
        };
    listView.setRenderBodyOnly(true);
    listView.setVisible(!lista.isEmpty());
    addOrReplace(listView);

    paginazioneFascicolo = new PaginazioneFascicoloPanel("pagination", listView);
    paginazioneFascicolo.setVisible(lista.size() > 4);
    addOrReplace(paginazioneFascicolo);

    if (lista.isEmpty()) {
      warn("Nessuna richiesta di certificati trovata");
    }
  }

  private String decodificaTipoConsegna(CertificatoAttoPersona certificato) {
    return StringUtils.isBlank(certificato.getTipoConsegnaScelta())
            || certificato.getTipoConsegnaScelta().equals("0")
        ? ON_LINE
        : E_MAIL;
  }
}
