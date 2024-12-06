package it.liguriadigitale.ponmetro.portale.presentation.pages.demografico.mieidati.panel.template;

import it.liguriadigitale.ponmetro.portale.presentation.components.label.CardLabel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

public class GeneratoreCardLabel<T> extends Panel {

  private static Log log = LogFactory.getLog(GeneratoreCardLabel.class);
  private static final long serialVersionUID = 7347983935970087616L;

  /**
   * @param id
   * @param mappaNomeValore
   * @param nomeClasseProveienza
   *     <p>Costruttore che va a creare automaticamente la lista di label data una mappa <String,
   *     Object> con key il nome del valore che andremo a mettere e come value il metodo.
   *     <p>Esempio: "nome", utente.getNome()
   *     <p>Il nome della proprietà nell'html legato alla classe di provenienza dovrà essere
   *     composto dallo stesso nome della classe seguito da un punto e dalla key
   *     <p>Esempio: NomeClasse.nome=Il nome è:
   *     <p>L'id sarà necessario per inserire il pannello che si creerà nell'html dove andrà
   *     dichiarato
   */
  @SuppressWarnings("rawtypes")
  public GeneratoreCardLabel(String id, Map mappaNomeValore, String nomeClasseProveienza) {
    super(id);
    addListView(mappaNomeValore, nomeClasseProveienza);
  }

  /**
   * @param id
   * @param istanzaClasseDaCuiEstrarreIMetodi
   * @param metodiDaRicevere
   * @param isWhiteList
   * @param nomeClasseDaCuiRichiamo
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   *     <p>Questo costruttore è necessario se si vuole utilizzare un sistema di Black List e White
   *     List che precede il passo generato dal costruttore GeneratoreListaLabelPerPanel(String id,
   *     Map mappaNomeValore , String nomeClasseProveienza). Passando l'istanza della classe da cui
   *     estrarremo i metodi riusciremo a estrarne tutti i metodi getter grazie a
   *     estrazioneMetodiGetter(istanzaClasseDaCuiEstrarreIMetodi). Passeremo poi una lista di
   *     String con i metodi da gestire e a seconda del Boolean isWhiteList la lista verrà utilizata
   *     in modi diversi
   *     <p>isWhiteList=true (SOLO i metodi che combaciano tra quelli trovati nella classe e quelli
   *     specificati verrano UTILIZZATI per la creazione della lista di label)
   *     <p>isWhiteList=false (I metodi combacianti tra la lista completa di metodi getter e la
   *     lista di metodi da cercare verranno SCARTATI e NON UTILIZZATI)
   *     <p>Il nome della classe per cui stiamo creando la lista di label sarà l'ultimo parametro da
   *     inserire il quale verrà utilizzato per la chiamat alle properties
   *     <p>NB:Usado questo costruttore i nomi delle properties saranno differenti ma
   *     standardizzati, sono composti dal nome della classe più il nome del metodo getter senza
   *     "get".
   *     <p>Esempio (se isWhitelist=true): metodiDaRicevere(x) = getCognome
   *     mappaNomevalore(x)=("Cognome", classObj.getCognome()) proprietà = NomeClasse.Cognome
   *     <p>Label: Cognome: Rossi "id=key" "classObj.getCognome()=value"
   */
  public GeneratoreCardLabel(
      String id,
      T istanzaClasseDaCuiEstrarreIMetodi,
      List<String> metodiDaRicevere,
      Boolean isWhiteList,
      String nomeClasseDaCuiRichiamo)
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    super(id);

    String[] metodiGetter = estrazioneMetodiGetter(istanzaClasseDaCuiEstrarreIMetodi);
    if (metodiDaRicevere != null) {
      if (Boolean.TRUE.equals(isWhiteList))
        whiteList(
            metodiGetter,
            metodiDaRicevere,
            istanzaClasseDaCuiEstrarreIMetodi,
            nomeClasseDaCuiRichiamo);
      else
        blackList(
            metodiGetter,
            metodiDaRicevere,
            istanzaClasseDaCuiEstrarreIMetodi,
            nomeClasseDaCuiRichiamo);
    } else log.error("GeneratoreListaLabelPerPanel __ LISTA METODI DA CONTROLLARE NON INSERITA");
  }

  /**
   * @param istanzaClasseDaCuiEstrarreIMetodi
   * @return metodiGetter
   *     <p>Questo metodo ritorna un array di stringhe formato da tutti i metodi getter trovati all
   *     interno della classe a cui si riferisce l'istanza che passiamo
   */
  private String[] estrazioneMetodiGetter(T istanzaClasseDaCuiEstrarreIMetodi) {
    Method[] lista = istanzaClasseDaCuiEstrarreIMetodi.getClass().getMethods();
    int occorrenzadiMetodiGetter = 0;
    for (Method metodoCorrente : lista) {
      if (metodoCorrente.getName().startsWith("get")) {
        occorrenzadiMetodiGetter++;
      }
    }
    int indice = 0;
    String[] metodiGetter = new String[occorrenzadiMetodiGetter];
    for (Method metodoCorrente : lista) {
      if (metodoCorrente.getName().startsWith("get")) {
        metodiGetter[indice] = metodoCorrente.getName();
        indice++;
      }
    }
    return metodiGetter;
  }

  /**
   * @param metodiGetter
   * @param metodiDaRicevere
   * @param istanzaClasseDaCuiEstrarreIMetodi
   * @param nomeClasseDaCuiRichiamo
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   *     <p>Il metodo blackList genererà una mappa <String, Object> per la creazione del panel
   *     utilizzando la lista totale di metodi getter e la lista di metodi passati. Questi ultimi
   *     verrano usati per EVITARE le corrispondenze tra le due liste. Tramite lo scorrimento dell
   *     Array di metodi getter in combinazione con lo scorrimento della lista, ogni metodo getter
   *     verrà comparato tra se stesso e tutti i metodi della lista fino al primo riscontro
   *     positivo, per poi passare al prossimo metodo getter nell array
   *     <p>Al termine la mappa verrà passata al metodo per l generazione della lista di label
   */
  private void blackList(
      String[] metodiGetter,
      List<String> metodiDaRicevere,
      T istanzaClasseDaCuiEstrarreIMetodi,
      String nomeClasseDaCuiRichiamo)
      throws IllegalAccessException, InvocationTargetException {
    Map<String, Object> mappaDaUtilizzare = new LinkedHashMap<>();
    Class<? extends Object> classobj = istanzaClasseDaCuiEstrarreIMetodi.getClass();
    for (String metodoCorrentedaControllare : metodiGetter) {
      if (!presenzaMetodoDaControllareInLista(metodiDaRicevere, metodoCorrentedaControllare)) {
        generaMappaPerListView(
            istanzaClasseDaCuiEstrarreIMetodi,
            classobj,
            metodoCorrentedaControllare,
            mappaDaUtilizzare);
      }
    }
    addListView(mappaDaUtilizzare, nomeClasseDaCuiRichiamo);
  }

  private boolean presenzaMetodoDaControllareInLista(
      List<String> metodiDaRicevere, String metodoCorrentedaControllare) {
    for (String nomeMetodo : metodiDaRicevere) {
      if (nomeMetodo.equalsIgnoreCase(metodoCorrentedaControllare)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param istanzaClasseDaCuiEstrarreIMetodi
   * @param classobj
   * @param metodoDaInserireNellaMappa
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   *     <p>La generazione della mappa è gestita da questo metodo, il quale utilizzando l'istanza da
   *     cui verrà preso il metodo e l oggetto derivato, associati al nome del metodo presente sull
   *     istanza forniranno il risultato del metodo come valore della mappa e come chiave il nome
   *     del metodo.
   */
  private Map<String, Object> generaMappaPerListView(
      T istanzaClasseDaCuiEstrarreIMetodi,
      Class<? extends Object> classobj,
      String metodoDaInserireNellaMappa,
      Map<String, Object> mappaDaUtilizzare)
      throws IllegalAccessException, InvocationTargetException {
    try {
      log.debug(
          "generaMappaPerListView __ METODO DA INSERIRE NELLA MAPPA " + metodoDaInserireNellaMappa);
      Method metodo = classobj.getMethod(metodoDaInserireNellaMappa);
      String nome = generaNomePerMappaDaNomeMetodoCompleto(metodo);
      Object valore = metodo.invoke(istanzaClasseDaCuiEstrarreIMetodi);
      mappaDaUtilizzare.put(nome, valore);
      log.debug("generaMappaPerListView __ NOME " + nome);
      log.debug("generaMappaPerListView __ VALORE " + valore);
    } catch (NoSuchMethodException | SecurityException e) {
      log.error("generaMappaPerListView __  ERRORE __creazione della mappa impossibile");
    }
    return mappaDaUtilizzare;
  }

  /**
   * @param metodo
   * @return questo metodo genera il nome del metodo escludendo il "get" iniziale e le "()" finali
   *     secondo elle regole di standardizzazione.
   */
  private String generaNomePerMappaDaNomeMetodoCompleto(Method metodo) {
    return metodo.getName().substring(3, metodo.getName().length()).replace("()", ""); //
  }

  /**
   * @param metodiGetter
   * @param metodiDaRicevere
   * @param istanzaClasseDaCuiEstrarreIMetodi
   * @param nomeClasseDaCuiRichiamo
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   *     <p>Il metodo whiteList genererà una mappa <String, Object> per la creazione del panel
   *     utilizzando la lista totale di metodi getter e la lista di metodi passati. Questi ultimi
   *     verrano usati per INDIVIDUARE E UTILIZARE le corrispondenze tra le due liste. Tramite lo
   *     scorrimento dell Array di metodi getter in combinazione con lo scorrimento della lista,
   *     ogni metodo getter verrà comparato tra se stesso e tutti i metodi della lista fino al primo
   *     riscontro positivo, per poi passare al prossimo metodo getter nell array
   *     <p>Al termine la mappa verrà passata al metodo per l generazione della lista di label
   */
  private void whiteList(
      String[] metodiGetter,
      List<String> metodiDaRicevere,
      T istanzaClasseDaCuiEstrarreIMetodi,
      String nomeClasseDaCuiRichiamo)
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    Map<String, Object> mappaDaUtilizzare = new LinkedHashMap<>();
    Class<? extends Object> classobj = istanzaClasseDaCuiEstrarreIMetodi.getClass();
    for (String metodoCorrentedaControllare : metodiGetter) {
      if (presenzaMetodoDaControllareInLista(metodiDaRicevere, metodoCorrentedaControllare)) {
        generaMappaPerListView(
            istanzaClasseDaCuiEstrarreIMetodi,
            classobj,
            metodoCorrentedaControllare,
            mappaDaUtilizzare);
      }
    }
    addListView(mappaDaUtilizzare, nomeClasseDaCuiRichiamo);
  }

  /**
   * @param mappaNomeValore
   * @param nomeClasseProveienza
   *     <p>addListView crea il modello della list per far si che venga generata la lista di label.
   *     <p>dove item è il singolo elemento estratto dalla lista di keys appartenenti alla mappa, la
   *     listview agirà come una reiterazione
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  private void addListView(Map mappaNomeValore, String nomeClasseProveienza) {
    List<String> listaKeys = new ArrayList<>(mappaNomeValore.keySet());
    ListView<String> listaRiempimentoPanel =
        new ListView<String>("lista", listaKeys) {
          private static final long serialVersionUID = 1L;

          @Override
          protected void populateItem(ListItem<String> item) {
            log.debug("addListView __ ITEM " + item);
            log.debug("addListView __ CLASSE DI PROVENIENZA " + nomeClasseProveienza);
            log.debug("addListView __ MAPPA NOME VALORE " + mappaNomeValore);
            item.add(
                creazioneCardPanel(item.getModelObject(), mappaNomeValore, nomeClasseProveienza));
          }
        };
    add(listaRiempimentoPanel);
  }

  /**
   * @param nomeProprieta
   * @param mappaNomeValore
   * @param nomeClasseProveienza
   * @return CardLabel Questo metodo dati il nome della proprietà, la mappa chiave valore <String,
   *     Object> e il nome della classe di provenienza genererà una singola CardLabel con nome pari
   *     alla Key e descrizione pari al value della mappa
   */
  @SuppressWarnings("rawtypes")
  private CardLabel<GeneratoreCardLabel<T>> creazioneCardPanel(
      String nomeProprieta, Map mappaNomeValore, String nomeClasseProveienza) {

    Object valoreCorrente = mappaNomeValore.get(nomeProprieta);
    String resourceId = nomeClasseProveienza + "." + nomeProprieta;
    return new CardLabel<>("riga", valoreCorrente, this, resourceId);
  }
}
