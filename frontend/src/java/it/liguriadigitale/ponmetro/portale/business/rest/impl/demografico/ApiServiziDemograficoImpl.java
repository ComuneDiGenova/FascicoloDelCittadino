package it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico;

import it.liguriadigitale.ponmetro.demografico.apiclient.PortaleApi;
import it.liguriadigitale.ponmetro.demografico.model.DatiCatastali;
import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale;
import it.liguriadigitale.ponmetro.demografico.model.ItemRelazioneParentale.CpvParentTypeEnum;
import it.liguriadigitale.ponmetro.demografico.model.ListaCarteInScadenza;
import it.liguriadigitale.ponmetro.demografico.model.Residente;
import it.liguriadigitale.ponmetro.demografico.model.Residente.CpvHasSexEnum;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteCpvHasAddress;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteCpvHasBirthPlace;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteCpvHasCitizenship;
import it.liguriadigitale.ponmetro.demografico.model.ResidenteTari;
import it.liguriadigitale.ponmetro.demografico.model.SchedaAnagrafica;
import it.liguriadigitale.ponmetro.demografico.model.StrutturaFigli;
import it.liguriadigitale.ponmetro.demografico.model.StrutturaGenitori;
import it.liguriadigitale.ponmetro.demografico.model.TesseraElettorale;
import it.liguriadigitale.ponmetro.portale.business.common.BaseServiceImpl;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao.HasIdCard;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao.Member;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao.Person;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao.ResidentIn;
import it.liguriadigitale.ponmetro.portale.business.rest.impl.demografico.dao.Root;
import it.liguriadigitale.ponmetro.portale.presentation.util.LocalDateUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class ApiServiziDemograficoImpl implements PortaleApi {

  private static Log log = LogFactory.getLog(ApiServiziDemograficoImpl.class);

  /* https://www.baeldung.com/resteasy-client-tutorial */

  PortaleApi istance;

  public ApiServiziDemograficoImpl(PortaleApi createDemograficoApi) {
    istance = createDemograficoApi;
  }

  @Override
  public Residente demograficoResidenteCodiceFiscaleGet(String codiceFiscale) {

    if (("SI").equalsIgnoreCase(BaseServiceImpl.API_COM_GE_DEMOGRAFICO_GRAPHQL)) {
      log.debug("MP codice fiscale:" + codiceFiscale);
      return demograficoResidenteCodiceFiscaleGetByGraphQL(codiceFiscale);
    } else {
      return istance.demograficoResidenteCodiceFiscaleGet(codiceFiscale);
    }

    // return istance.demograficoResidenteCodiceFiscaleGet(codiceFiscale);
  }

  private Residente demograficoResidenteCodiceFiscaleGetByGraphQL(String codiceFiscale) {
    Residente residente = new Residente();
    Root root = iMieiDati(codiceFiscale);

    Person person = null;
    if (!root.getData().getPersons().isEmpty()) {
      person = root.getData().getPersons().get(0);
    }

    if (person != null) {
      residente.setCpvDateOfBirth(
          LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(
              person.getDateOfBirth().getFormatted()));

      residente.setCpvTaxCode(person.getTaxCode());

      ResidentIn residentIn = person.getResidentIn();

      ResidenteCpvHasAddress cpvHasAddress = new ResidenteCpvHasAddress();
      cpvHasAddress.setClvToponymQualifier(residentIn.getToponymQualifier());
      cpvHasAddress.setClvOfficialStreetName(residentIn.getOfficialStreetName());
      cpvHasAddress.setClvStreetNumber(residentIn.getStreetNumber());
      cpvHasAddress.setGenovaOntoStreetNumberCode(residentIn.getStreetNumberCode());
      cpvHasAddress.setClvFlatNumber(residentIn.getFlatNumber());
      cpvHasAddress.setClvCity("Genova");
      cpvHasAddress.setGenovaOntoOfficialStreetNameCode(residentIn.getOfficialStreetNameCode());
      cpvHasAddress.setGenovaOntoFlatNumberCode(residentIn.getIdentifier());
      cpvHasAddress.setClvFullAddress(residentIn.getFullAddress());
      cpvHasAddress.setClvPeculiarity(null);
      cpvHasAddress.setClvPostCode(residentIn.getPostCode());
      cpvHasAddress.setClvStaircase(residentIn.getStair());
      cpvHasAddress.setClvStreenNumberOnly(residentIn.getStreetNumber());
      cpvHasAddress.setClvStreetNumberExponent(residentIn.getStreetNumberExponent());
      cpvHasAddress.setClvStreetNumberColor(residentIn.getStreetNumberColor());
      cpvHasAddress.setClvStair(residentIn.getStair());
      cpvHasAddress.setClvFlatNumberOnly(residentIn.getFlatNumber());
      cpvHasAddress.setClvFlatExponent(residentIn.getFlatExponent());
      residente.setCpvHasAddress(cpvHasAddress);

      residente.setGenovaOntoIDCardIsCIE(
          person.getHasIdCard() != null && !person.getHasIdCard().isEmpty());
      residente.setGenovaOntoResidentSince(null);
      residente.setRdfsLabel(person.familyName + " " + person.givenName);
      residente.setCpvDateOfDeath(null);

      ResidenteCpvHasBirthPlace residenteCpvHasBirthPlace = new ResidenteCpvHasBirthPlace();
      residenteCpvHasBirthPlace.setClvCity(person.getBirthPlace());
      residenteCpvHasBirthPlace.setClvCountry(null);
      residenteCpvHasBirthPlace.setClvProvince(null);
      residenteCpvHasBirthPlace.setClvRegion(null);

      residente.setCpvHasBirthPlace(residenteCpvHasBirthPlace);
      residente.setCpvPersonID(null);
      residente.setCpvGivenName(person.getGivenName());

      //			[class ItemRelazioneParentale {
      //			    cpvComponentTaxCode: PCAMSM71S21I480W
      //			    cpvParentType: IS
      //			}, class ItemRelazioneParentale {
      //			    cpvComponentTaxCode: GCLMCL77M61I480T
      //			    cpvParentType: MG
      //			}, class ItemRelazioneParentale {
      //			    cpvComponentTaxCode: PCAMNL13D11D969H
      //			    cpvParentType: FG
      //			}]

      SchedaAnagrafica schedaAnagrafica = new SchedaAnagrafica();

      List<ItemRelazioneParentale> listaItemRelazioneParentale =
          new ArrayList<ItemRelazioneParentale>();

      ItemRelazioneParentale itemRelazioneParentale = new ItemRelazioneParentale();
      itemRelazioneParentale.setCpvComponentTaxCode(codiceFiscale);
      itemRelazioneParentale.setCpvParentType(CpvParentTypeEnum.IS);
      listaItemRelazioneParentale.add(itemRelazioneParentale);

      if (root.getData() != null
          && root.getData().getRegisteredFamilyFromTaxCode() != null
          && root.getData().getRegisteredFamilyFromTaxCode().getMembers() != null) {
        ArrayList<Member> members = root.getData().getRegisteredFamilyFromTaxCode().getMembers();
        schedaAnagrafica.setCpvNumberRegFamilyComponents(members.size());
        for (Member member : members) {
          itemRelazioneParentale = new ItemRelazioneParentale();
          itemRelazioneParentale.setCpvComponentTaxCode(member.getPerson().getTaxCode());

          itemRelazioneParentale.setCpvParentType(
              CpvParentTypeEnum.valueOf(member.getMemberTypeCode()));
          listaItemRelazioneParentale.add(itemRelazioneParentale);
        }
      }

      schedaAnagrafica.setCpvBelongsToFamily(listaItemRelazioneParentale);

      residente.setCpvInRegisteredFamily(schedaAnagrafica);

      // residente.setCpvHasBirthPlace(residenteCpvHasBirthPlace);

      if (person.getHasIdCard() != null
          && !person.getHasIdCard().isEmpty()
          && person.getHasIdCard().get(0) != null) {

        HasIdCard hasIdCard = person.getHasIdCard().get(0);
        residente.setGenovaOntoIDCardNumber(hasIdCard.getIdentifier());
        if (hasIdCard.getEndTime() != null && hasIdCard.getEndTime().getFormatted() != null) {
          residente.setGenovaOntoIDCardValidUntilDate(
              LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(
                  hasIdCard.getEndTime().getFormatted()));
        }

        if (hasIdCard.getStartTime() != null && hasIdCard.getStartTime().getFormatted() != null) {
          residente.setGenovaOntoIDCardIssueDate(
              LocalDateUtil.convertiDaFormatoEuropeoPerEngMunicipia(
                  (hasIdCard.getStartTime().getFormatted())));
        }

        residente.setGenovaOntoIDCardIssuingMunicipality(hasIdCard.getOrganization());
      }

      residente.setCpvFamilyName(person.getFamilyName());

      CpvHasSexEnum cpvHasSexEnum;
      if ("M".equalsIgnoreCase(person.getSex())) {
        cpvHasSexEnum = CpvHasSexEnum.M;
      } else if ("F".equalsIgnoreCase(person.getSex())) {
        cpvHasSexEnum = CpvHasSexEnum.F;
      } else {
        cpvHasSexEnum = CpvHasSexEnum.X;
      }
      residente.setCpvHasSex(cpvHasSexEnum);

      residente.setGenovaStatoCivile(person.getMaritalStatus());

      ResidenteCpvHasCitizenship residenteCpvHasCitizenship = new ResidenteCpvHasCitizenship();
      residenteCpvHasCitizenship.setClvCountry(person.getCitizenship());
      residenteCpvHasCitizenship.setClvHasIdentifier(null);
      residente.setCpvHasCitizenship(residenteCpvHasCitizenship);
    }
    return residente;
  }

  @Override
  public SchedaAnagrafica demograficoSchedaAnagraficaCodiceFiscaleGet(String arg0) {
    return istance.demograficoSchedaAnagraficaCodiceFiscaleGet(arg0);
  }

  @Override
  public TesseraElettorale demograficoTesseraElettoraleCodiceFiscaleGet(String arg0) {
    return istance.demograficoTesseraElettoraleCodiceFiscaleGet(arg0);
  }

  @Override
  public DatiCatastali oggettiDatiCatastaliCodiceIndirizzoGet(String arg0) {
    return istance.oggettiDatiCatastaliCodiceIndirizzoGet(arg0);
  }

  @Override
  public StrutturaGenitori oggettiStatoCivileGenitoreCodiceFiscaleFiglioGet(String arg0) {
    log.debug(
        "[ApiServiziDemograficoImpl] oggettiStatoCivileGenitoreCodiceFiscaleFiglioGet-- inizio");
    return istance.oggettiStatoCivileGenitoreCodiceFiscaleFiglioGet(arg0);
  }

  @Override
  public StrutturaFigli oggettiStatoCivileFiglioCodiceFiscaleGenitoreGet(String arg0) {
    log.debug(
        "[ApiServiziDemograficoImpl] oggettiStatoCivileFiglioCodiceFiscaleGenitoreGet-- inizio");
    return istance.oggettiStatoCivileFiglioCodiceFiscaleGenitoreGet(arg0);
  }

  @Override
  public ListaCarteInScadenza getScadenze(String arg0) {
    log.debug("[ApiServiziDemograficoImpl] ListaCarteInScadenza-- inizio");
    return istance.getScadenze(arg0);
  }

  @Override
  public List<ResidenteTari> tariResidentiCodiceFiscaleGet(String arg0) {
    log.debug("[ApiServiziDemograficoImpl] tariResidentiCodiceFiscaleGet-- inizio");
    return istance.tariResidentiCodiceFiscaleGet(arg0);
  }

  private Root iMieiDati(String codiceFiscale) {

    String query =
        "# Dato un taxCode di un nodo Person, viene restituita la RegisteredFamily dei nodi Person\r\n"
            + "# la cui relazione con RegisteredFamily è belongsToFamily � di tipo belongsToFamily\r\n"
            + "query getDatiFdC($codiceFiscale: String=\""
            + codiceFiscale
            + "\") {\r\n"
            + "    #DATI ANAGRAFICI\r\n"
            + "    persons(taxCode: $codiceFiscale) {\r\n"
            + "        givenName\r\n"
            + "        familyName\r\n"
            + "        birthPlace\r\n"
            + "        citizenship\r\n"
            + "        dateOfBirth {formatted}\r\n"
            + "        taxCode\r\n"
            + "        sex\r\n"
            + "        maritalStatus\r\n"
            + "        residentIn {\r\n"
            + "            fullAddress\r\n"
            + "            flatExponent\r\n"
            + "            flatNumber\r\n"
            + "            geoAttributes \r\n"
            + "            identifier\r\n"
            + "            officialStreetName\r\n"
            + "            officialStreetNameCode\r\n"
            + "            postCode\r\n"
            + "            stair\r\n"
            + "            streetNumber\r\n"
            + "            streetNumberCode\r\n"
            + "            streetNumberColor\r\n"
            + "            streetNumberExponent\r\n"
            + "            toponymQualifier\r\n"
            + "            }\r\n"
            + "        hasIdCard{\r\n"
            + "            identifier\r\n"
            + "            electronicID\r\n"
            + "            startTime{formatted}\r\n"
            + "            organization\r\n"
            + "            endTime{formatted}\r\n"
            + "        }\r\n"
            + "        \r\n"
            + "        #I MIEI DATI TESSERA ELETTORALE\r\n"
            + "        hasElectoralCard{\r\n"
            + "            identifier\r\n"
            + "            organization\r\n"
            + "            startTime{formatted}\r\n"
            + "\r\n"
            + "        }\r\n"
            + "        hasElectoralSection {\r\n"
            + "            identifier\r\n"
            + "        }\r\n"
            + "\r\n"
            + "        #I DATI CATASTALI DELLA MIA RESIDENZA\r\n"
            + "        residentIn {fullAddress} \r\n"
            + "    }\r\n"
            + "\r\n"
            + "    registeredFamilyFromTaxCode(taxCode: $codiceFiscale) {\r\n"
            + "        registeredFamilyId\r\n"
            + "         isFamilySheetHolderOf {\r\n"
            + "             givenName\r\n"
            + "             familyName\r\n"
            + "             dateOfBirth{formatted}\r\n"
            + "             taxCode\r\n"
            + "        }\r\n"
            + "        members {\r\n"
            + "            person {\r\n"
            + "            givenName\r\n"
            + "             familyName\r\n"
            + "             dateOfBirth{formatted}\r\n"
            + "             taxCode\r\n"
            + "            }\r\n"
            + "            memberTypeCode\r\n"
            + "        }\r\n"
            + "    }\r\n"
            + "}\r\n"
            + "";

    // String variables = "{" + " \"codiceFiscale\": \"STLDNL75E13D969D\"\r\n" +
    // "}";

    String url = "https://apiprod.comune.genova.it:28243/galileo/graphql";

    final String BEARER = "cf1bb497-47fd-3298-9c27-1ed002a3668a";
    StringBuilder builder = callGraphQL(query, url, BEARER);

    Root root = null;
    try {

      root = new Root().load(builder.toString());
      log.debug("builder:" + root.toString());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return root;
  }

  /*
   * private void registeredFamilyByPersonTaxCode() { String query =
   * "# Dato un taxCode di un nodo Person, viene restituita la RegisteredFamily dei nodi Person\r\n"
   * + "# la cui relazione con RegisteredFamily è di tipo belongsToFamily\r\n" +
   * "{\r\n" + "    registeredFamilies(\r\n" +
   * "        filter: { belongsToFamily_single: { taxCode: \"CRNTMS00B01D969T\" } }\r\n"
   * + "    ) {\r\n" + "        registeredFamilyId\r\n" + "        members {\r\n"
   * + "            person {\r\n" + "                givenName\r\n" +
   * "                familyName\r\n" + "                sex\r\n" +
   * "                taxCode\r\n" + "                dateOfBirth {\r\n" +
   * "                    day\r\n" + "                    month\r\n" +
   * "                    year\r\n" + "                    formatted\r\n" +
   * "                }\r\n" + "            }\r\n" + "            memberType\r\n"
   * + "            memberTypeCode\r\n" + "        }\r\n" +
   * "        isFamilySheetHolderOf {\r\n" + "            givenName\r\n" +
   * "            familyName\r\n" + "            sex\r\n" +
   * "            taxCode\r\n" + "            dateOfBirth {\r\n" +
   * "                day\r\n" + "                month\r\n" +
   * "                year\r\n" + "                formatted\r\n" +
   * "            }\r\n" + "        }\r\n" + "    }\r\n" + "}\r\n" + "";
   *
   * String url = "https://apiprod.comune.genova.it:28243/galileo/graphql";
   *
   * final String BEARER = "cf1bb497-47fd-3298-9c27-1ed002a3668a"; StringBuilder
   * builder = callGraphQL(query, url, BEARER);
   *
   * try { Root root = new Root().load(builder.toString()); } catch (IOException
   * e) { // TODO Auto-generated catch block e.printStackTrace(); }
   *
   * }
   */
  private StringBuilder callGraphQL(String query, String url, String bearer) {
    StringBuilder builder = new StringBuilder();
    CloseableHttpClient client = null;
    CloseableHttpResponse response = null;

    client = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost(url);

    httpPost.addHeader("Authorization", "Bearer " + bearer);
    httpPost.addHeader("Accept", "application/json");

    JSONObject jsonObj = new JSONObject();

    jsonObj.putOnce("query", query);

    try {
      StringEntity entity = new StringEntity(jsonObj.toString());

      httpPost.setEntity(entity);
      response = client.execute(httpPost);

    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      String line = null;

      while ((line = reader.readLine()) != null) {

        builder.append(line);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return builder;
  }
}
