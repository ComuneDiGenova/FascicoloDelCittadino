package it.liguriadigitale.ponmetro.api.integration.dao.teleriscaldamento;

/**
 * TrDomande
 *
 * <p>WARNING! Automatically generated file with TableGen null! Do not edit! created:
 * 2022-11-08T11:44:55.215
 */
import it.liguriadigitale.framework.integration.dao.AbstractTableDAO;
import it.liguriadigitale.ponmetro.api.pojo.teleriscaldamento.TrDomande;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrDomandeDAO extends AbstractTableDAO {

  private TrDomande trdomande;

  public TrDomandeDAO(TrDomande trdomande) {
    super();
    this.trdomande = trdomande;
  }

  @Override
  protected String getSqlRetrieveObjectByKey() {
    String sql = "SELECT *  from TR_DOMANDE where ID=?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, trdomande.getId());
  }

  @Override
  protected String getSqlRetrieveObjectByWhere() {
    String sql = "SELECT *  from TR_DOMANDE where 1=1 ";
    if (trdomande.getId() != null) sql += " and ID  = ?";
    if (trdomande.getCfRichiedente() != null) sql += " and CF_RICHIEDENTE  = ?";
    //		if (trdomande.getIdStatoDomanda() != null)
    //			sql += " and ID_STATO_DOMANDA  > ?";
    return sql;
  }

  @Override
  protected void setStatementRetrieveObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (trdomande.getId() != null) st.setBigDecimal(index++, trdomande.getId());
    if (trdomande.getCfRichiedente() != null) st.setString(index++, trdomande.getCfRichiedente());
    //		if (trdomande.getIdStatoDomanda() != null)
    //			st.setLong(index++, trdomande.getIdStatoDomanda());
  }

  @Override
  public Object getFromResultSet(ResultSet r) throws SQLException {
    TrDomande obj = new TrDomande();

    obj.setId(r.getBigDecimal("ID"));
    obj.setIdStatoDomanda(r.getLong("ID_STATO_DOMANDA"));
    if (r.wasNull()) {
      obj.setIdStatoDomanda(null);
    }

    obj.setStatoDomanda(r.getString("STATO_DOMANDA"));
    obj.setDataConsegnaIren(
        r.getTimestamp("DATA_CONSEGNA_IREN") != null
            ? r.getTimestamp("DATA_CONSEGNA_IREN").toLocalDateTime()
            : null);
    obj.setNumProtocollo(r.getString("NUM_PROTOCOLLO"));
    obj.setDataProtocollo(r.getString("DATA_PROTOCOLLO"));
    obj.setCognomeRichiedente(r.getString("COGNOME_RICHIEDENTE"));
    obj.setNomeRichiedente(r.getString("NOME_RICHIEDENTE"));
    obj.setCfRichiedente(r.getString("CF_RICHIEDENTE"));
    obj.setTelefono(r.getString("TELEFONO"));
    obj.setCellulare(r.getString("CELLULARE"));
    obj.setEmail(r.getString("EMAIL"));
    obj.setNumNucleoFamiliare(r.getString("NUM_NUCLEO_FAMILIARE"));
    obj.setIndicatoreIsee12(r.getString("INDICATORE_ISEE_12"));
    obj.setIndicatoreIsee20(r.getString("INDICATORE_ISEE_20"));
    obj.setTipoUtenza(r.getString("TIPO_UTENZA"));
    obj.setNumeroCliente(r.getString("NUMERO_CLIENTE"));
    obj.setNumContratto(r.getString("NUM_CONTRATTO"));
    obj.setViaFornitura(r.getString("VIA_FORNITURA"));
    obj.setComuneFornitura(r.getString("COMUNE_FORNITURA"));
    obj.setNumCivicoFornitura(r.getString("NUM_CIVICO_FORNITURA"));
    obj.setProvinciaFornitura(r.getString("PROVINCIA_FORNITURA"));
    obj.setNominativoContratto(r.getString("NOMINATIVO_CONTRATTO"));
    obj.setCfIntestatarioContratto(r.getString("CF_INTESTATARIO_CONTRATTO"));
    obj.setPivaContratto(r.getString("PIVA_CONTRATTO"));
    obj.setNominativoApt(r.getString("NOMINATIVO_APT"));
    obj.setNominativoAammCond(r.getString("NOMINATIVO_AAMM_COND"));
    obj.setViaAmmCondominio(r.getString("VIA_AMM_CONDOMINIO"));
    obj.setComuneAmmCondominio(r.getString("COMUNE_AMM_CONDOMINIO"));
    obj.setCivicoAmmCondominio(r.getString("CIVICO_AMM_CONDOMINIO"));
    obj.setCapAmmCondominio(r.getString("CAP_AMM_CONDOMINIO"));
    obj.setProvAmmCondominio(r.getString("PROV_AMM_CONDOMINIO"));
    obj.setTelAmmCondominio(r.getString("TEL_AMM_CONDOMINIO"));
    obj.setCelAmmCondominio(r.getString("CEL_AMM_CONDOMINIO"));
    obj.setEmailAmmCondominio(r.getString("EMAIL_AMM_CONDOMINIO"));
    obj.setConsensoPrivacy(r.getString("CONSENSO_PRIVACY"));
    obj.setConsensoInf(r.getString("CONSENSO_INF"));
    obj.setDatiVerificati(r.getString("DATI_VERIFICATI"));
    obj.setEsitoVerifica(r.getString("ESITO_VERIFICA"));
    obj.setAnnoDomanda(r.getString("ANNO_DOMANDA"));
    obj.setCapFornitura(r.getString("CAP_FORNITURA"));
    obj.setIndicatoreIsee25(r.getString("INDICATORE_ISEE_25"));
    return obj;
  }

  @Override
  protected String getSqlDeleteByKey() {
    String sql = "DELETE  from TR_DOMANDE where ID=?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByKey(PreparedStatement st) throws SQLException {
    int index = 1;
    st.setBigDecimal(index++, trdomande.getId());
  }

  @Override
  protected String getSqlDeleteByWhere() {
    String sql = "DELETE  from TR_DOMANDE where 1=1 ";
    if (trdomande.getId() != null) sql += " and ID  = ?";
    if (trdomande.getCfRichiedente() != null) sql += " and CF_RICHIEDENTE  = ?";
    if (trdomande.getIdStatoDomanda() != null) sql += " and ID_STATO_DOMANDA  = ?";
    return sql;
  }

  @Override
  protected void setStatementDeleteObjectByWhere(PreparedStatement st) throws SQLException {
    int index = 1;
    if (trdomande.getId() != null) st.setBigDecimal(index++, trdomande.getId());
    if (trdomande.getCfRichiedente() != null) st.setString(index++, trdomande.getCfRichiedente());
    if (trdomande.getIdStatoDomanda() != null) st.setLong(index++, trdomande.getIdStatoDomanda());
  }

  @Override
  protected String getSqlUpdateByKey() {

    String sql = getUpdateFields() + " where ID=?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByKey(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    st.setBigDecimal(index++, trdomande.getId());
  }

  @Override
  protected String getSqlUpdateByWhere() {

    String sql = getUpdateFields() + " where 1=1 ";
    if (trdomande.getId() != null) sql += " and ID  = ?";
    if (trdomande.getCfRichiedente() != null) sql += " and CF_RICHIEDENTE  = ?";
    if (trdomande.getIdStatoDomanda() != null) sql += " and ID_STATO_DOMANDA  = ?";
    return sql;
  }

  @Override
  protected void setStatementUpdateObjectByWhere(PreparedStatement st) throws SQLException {
    int index = setFieldStatement(st);
    if (trdomande.getId() != null) st.setBigDecimal(index++, trdomande.getId());
    if (trdomande.getCfRichiedente() != null) st.setString(index++, trdomande.getCfRichiedente());
    if (trdomande.getIdStatoDomanda() != null) st.setLong(index++, trdomande.getIdStatoDomanda());
  }

  private int setFieldStatement(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (trdomande.getId() != null) {
      pst.setBigDecimal(index++, trdomande.getId());
    }
    if (trdomande.getIdStatoDomanda() != null) {
      pst.setLong(index++, trdomande.getIdStatoDomanda());
    }
    if (trdomande.getStatoDomanda() != null) {
      pst.setString(index++, trdomande.getStatoDomanda());
    }
    if (trdomande.getDataConsegnaIren() != null) {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(trdomande.getDataConsegnaIren()));
    }
    if (trdomande.getNumProtocollo() != null) {
      pst.setString(index++, trdomande.getNumProtocollo());
    }
    if (trdomande.getDataProtocollo() != null) {
      pst.setString(index++, trdomande.getDataProtocollo());
    }
    if (trdomande.getCognomeRichiedente() != null) {
      pst.setString(index++, trdomande.getCognomeRichiedente());
    }
    if (trdomande.getNomeRichiedente() != null) {
      pst.setString(index++, trdomande.getNomeRichiedente());
    }
    if (trdomande.getCfRichiedente() != null) {
      pst.setString(index++, trdomande.getCfRichiedente());
    }
    if (trdomande.getTelefono() != null) {
      pst.setString(index++, trdomande.getTelefono());
    }
    if (trdomande.getCellulare() != null) {
      pst.setString(index++, trdomande.getCellulare());
    }
    if (trdomande.getEmail() != null) {
      pst.setString(index++, trdomande.getEmail());
    }
    if (trdomande.getNumNucleoFamiliare() != null) {
      pst.setString(index++, trdomande.getNumNucleoFamiliare());
    }
    if (trdomande.getIndicatoreIsee12() != null) {
      pst.setString(index++, trdomande.getIndicatoreIsee12());
    }
    if (trdomande.getIndicatoreIsee20() != null) {
      pst.setString(index++, trdomande.getIndicatoreIsee20());
    }
    if (trdomande.getTipoUtenza() != null) {
      pst.setString(index++, trdomande.getTipoUtenza());
    }
    if (trdomande.getNumeroCliente() != null) {
      pst.setString(index++, trdomande.getNumeroCliente());
    }
    if (trdomande.getNumContratto() != null) {
      pst.setString(index++, trdomande.getNumContratto());
    }
    if (trdomande.getViaFornitura() != null) {
      pst.setString(index++, trdomande.getViaFornitura());
    }
    if (trdomande.getComuneFornitura() != null) {
      pst.setString(index++, trdomande.getComuneFornitura());
    }
    if (trdomande.getNumCivicoFornitura() != null) {
      pst.setString(index++, trdomande.getNumCivicoFornitura());
    }
    if (trdomande.getProvinciaFornitura() != null) {
      pst.setString(index++, trdomande.getProvinciaFornitura());
    }
    if (trdomande.getNominativoContratto() != null) {
      pst.setString(index++, trdomande.getNominativoContratto());
    }
    if (trdomande.getCfIntestatarioContratto() != null) {
      pst.setString(index++, trdomande.getCfIntestatarioContratto());
    }
    if (trdomande.getPivaContratto() != null) {
      pst.setString(index++, trdomande.getPivaContratto());
    }
    if (trdomande.getNominativoApt() != null) {
      pst.setString(index++, trdomande.getNominativoApt());
    }
    if (trdomande.getNominativoAammCond() != null) {
      pst.setString(index++, trdomande.getNominativoAammCond());
    }
    if (trdomande.getViaAmmCondominio() != null) {
      pst.setString(index++, trdomande.getViaAmmCondominio());
    }
    if (trdomande.getComuneAmmCondominio() != null) {
      pst.setString(index++, trdomande.getComuneAmmCondominio());
    }
    if (trdomande.getCivicoAmmCondominio() != null) {
      pst.setString(index++, trdomande.getCivicoAmmCondominio());
    }
    if (trdomande.getCapAmmCondominio() != null) {
      pst.setString(index++, trdomande.getCapAmmCondominio());
    }
    if (trdomande.getProvAmmCondominio() != null) {
      pst.setString(index++, trdomande.getProvAmmCondominio());
    }
    if (trdomande.getTelAmmCondominio() != null) {
      pst.setString(index++, trdomande.getTelAmmCondominio());
    }
    if (trdomande.getCelAmmCondominio() != null) {
      pst.setString(index++, trdomande.getCelAmmCondominio());
    }
    if (trdomande.getEmailAmmCondominio() != null) {
      pst.setString(index++, trdomande.getEmailAmmCondominio());
    }
    if (trdomande.getConsensoPrivacy() != null) {
      pst.setString(index++, trdomande.getConsensoPrivacy());
    }
    if (trdomande.getConsensoInf() != null) {
      pst.setString(index++, trdomande.getConsensoInf());
    }
    if (trdomande.getDatiVerificati() != null) {
      pst.setString(index++, trdomande.getDatiVerificati());
    }
    if (trdomande.getEsitoVerifica() != null) {
      pst.setString(index++, trdomande.getEsitoVerifica());
    }
    if (trdomande.getAnnoDomanda() != null) {
      pst.setString(index++, trdomande.getAnnoDomanda());
    }
    if (trdomande.getCapFornitura() != null) {
      pst.setString(index++, trdomande.getCapFornitura());
    }
    if (trdomande.getIndicatoreIsee25() != null) {
      pst.setString(index++, trdomande.getIndicatoreIsee25());
    }
    return index;
  }

  protected String getUpdateFields() {
    String sql = "UPDATE TR_DOMANDE set ";

    if (trdomande.getId() != null) {
      sql += " ID= ? ";
      sql += " ,";
    }
    if (trdomande.getIdStatoDomanda() != null) {
      sql += " ID_STATO_DOMANDA= ? ";
      sql += " ,";
    }
    if (trdomande.getStatoDomanda() != null) {
      sql += " STATO_DOMANDA= ? ";
      sql += " ,";
    }
    if (trdomande.getDataConsegnaIren() != null) {
      sql += " DATA_CONSEGNA_IREN= ? ";
      sql += " ,";
    }
    if (trdomande.getNumProtocollo() != null) {
      sql += " NUM_PROTOCOLLO= ? ";
      sql += " ,";
    }
    if (trdomande.getDataProtocollo() != null) {
      sql += " DATA_PROTOCOLLO= ? ";
      sql += " ,";
    }
    if (trdomande.getCognomeRichiedente() != null) {
      sql += " COGNOME_RICHIEDENTE= ? ";
      sql += " ,";
    }
    if (trdomande.getNomeRichiedente() != null) {
      sql += " NOME_RICHIEDENTE= ? ";
      sql += " ,";
    }
    if (trdomande.getCfRichiedente() != null) {
      sql += " CF_RICHIEDENTE= ? ";
      sql += " ,";
    }
    if (trdomande.getTelefono() != null) {
      sql += " TELEFONO= ? ";
      sql += " ,";
    }
    if (trdomande.getCellulare() != null) {
      sql += " CELLULARE= ? ";
      sql += " ,";
    }
    if (trdomande.getEmail() != null) {
      sql += " EMAIL= ? ";
      sql += " ,";
    }
    if (trdomande.getNumNucleoFamiliare() != null) {
      sql += " NUM_NUCLEO_FAMILIARE= ? ";
      sql += " ,";
    }
    if (trdomande.getIndicatoreIsee12() != null) {
      sql += " INDICATORE_ISEE_12= ? ";
      sql += " ,";
    }
    if (trdomande.getIndicatoreIsee20() != null) {
      sql += " INDICATORE_ISEE_20= ? ";
      sql += " ,";
    }
    if (trdomande.getTipoUtenza() != null) {
      sql += " TIPO_UTENZA= ? ";
      sql += " ,";
    }
    if (trdomande.getNumeroCliente() != null) {
      sql += " NUMERO_CLIENTE= ? ";
      sql += " ,";
    }
    if (trdomande.getNumContratto() != null) {
      sql += " NUM_CONTRATTO= ? ";
      sql += " ,";
    }
    if (trdomande.getViaFornitura() != null) {
      sql += " VIA_FORNITURA= ? ";
      sql += " ,";
    }
    if (trdomande.getComuneFornitura() != null) {
      sql += " COMUNE_FORNITURA= ? ";
      sql += " ,";
    }
    if (trdomande.getNumCivicoFornitura() != null) {
      sql += " NUM_CIVICO_FORNITURA= ? ";
      sql += " ,";
    }
    if (trdomande.getProvinciaFornitura() != null) {
      sql += " PROVINCIA_FORNITURA= ? ";
      sql += " ,";
    }
    if (trdomande.getNominativoContratto() != null) {
      sql += " NOMINATIVO_CONTRATTO= ? ";
      sql += " ,";
    }
    if (trdomande.getCfIntestatarioContratto() != null) {
      sql += " CF_INTESTATARIO_CONTRATTO= ? ";
      sql += " ,";
    }
    if (trdomande.getPivaContratto() != null) {
      sql += " PIVA_CONTRATTO= ? ";
      sql += " ,";
    }
    if (trdomande.getNominativoApt() != null) {
      sql += " NOMINATIVO_APT= ? ";
      sql += " ,";
    }
    if (trdomande.getNominativoAammCond() != null) {
      sql += " NOMINATIVO_AAMM_COND= ? ";
      sql += " ,";
    }
    if (trdomande.getViaAmmCondominio() != null) {
      sql += " VIA_AMM_CONDOMINIO= ? ";
      sql += " ,";
    }
    if (trdomande.getComuneAmmCondominio() != null) {
      sql += " COMUNE_AMM_CONDOMINIO= ? ";
      sql += " ,";
    }
    if (trdomande.getCivicoAmmCondominio() != null) {
      sql += " CIVICO_AMM_CONDOMINIO= ? ";
      sql += " ,";
    }
    if (trdomande.getCapAmmCondominio() != null) {
      sql += " CAP_AMM_CONDOMINIO= ? ";
      sql += " ,";
    }
    if (trdomande.getProvAmmCondominio() != null) {
      sql += " PROV_AMM_CONDOMINIO= ? ";
      sql += " ,";
    }
    if (trdomande.getTelAmmCondominio() != null) {
      sql += " TEL_AMM_CONDOMINIO= ? ";
      sql += " ,";
    }
    if (trdomande.getCelAmmCondominio() != null) {
      sql += " CEL_AMM_CONDOMINIO= ? ";
      sql += " ,";
    }
    if (trdomande.getEmailAmmCondominio() != null) {
      sql += " EMAIL_AMM_CONDOMINIO= ? ";
      sql += " ,";
    }
    if (trdomande.getConsensoPrivacy() != null) {
      sql += " CONSENSO_PRIVACY= ? ";
      sql += " ,";
    }
    if (trdomande.getConsensoInf() != null) {
      sql += " CONSENSO_INF= ? ";
      sql += " ,";
    }
    if (trdomande.getDatiVerificati() != null) {
      sql += " DATI_VERIFICATI= ? ";
      sql += " ,";
    }
    if (trdomande.getEsitoVerifica() != null) {
      sql += " ESITO_VERIFICA= ? ";
      sql += " ,";
    }
    if (trdomande.getAnnoDomanda() != null) {
      sql += " ANNO_DOMANDA= ? ";
      sql += " ,";
    }
    if (trdomande.getCapFornitura() != null) {
      sql += " CAP_FORNITURA= ? ";
      sql += " ,";
    }
    if (trdomande.getIndicatoreIsee25() != null) {
      sql += " INDICATORE_ISEE_25= ? ";
    }
    sql = sql.substring(0, sql.length() - 1);
    return sql;
  }

  @Override
  protected String getSqlInsertObject() {

    String sql =
        "INSERT INTO TR_DOMANDE ( ID,ID_STATO_DOMANDA,STATO_DOMANDA,DATA_CONSEGNA_IREN,NUM_PROTOCOLLO,DATA_PROTOCOLLO,COGNOME_RICHIEDENTE,NOME_RICHIEDENTE,CF_RICHIEDENTE,TELEFONO,CELLULARE,EMAIL,NUM_NUCLEO_FAMILIARE,INDICATORE_ISEE_12,INDICATORE_ISEE_20,TIPO_UTENZA,NUMERO_CLIENTE,NUM_CONTRATTO,VIA_FORNITURA,COMUNE_FORNITURA,NUM_CIVICO_FORNITURA,PROVINCIA_FORNITURA,NOMINATIVO_CONTRATTO,CF_INTESTATARIO_CONTRATTO,PIVA_CONTRATTO,NOMINATIVO_APT,NOMINATIVO_AAMM_COND,VIA_AMM_CONDOMINIO,COMUNE_AMM_CONDOMINIO,CIVICO_AMM_CONDOMINIO,CAP_AMM_CONDOMINIO,PROV_AMM_CONDOMINIO,TEL_AMM_CONDOMINIO,CEL_AMM_CONDOMINIO,EMAIL_AMM_CONDOMINIO,CONSENSO_PRIVACY,CONSENSO_INF,DATI_VERIFICATI,ESITO_VERIFICA,ANNO_DOMANDA,CAP_FORNITURA,INDICATORE_ISEE_25 ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  public void setStatementInsert(PreparedStatement pst) throws SQLException {
    int index = 1;
    pst.setBigDecimal(index++, trdomande.getId());
    if (trdomande.getIdStatoDomanda() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, trdomande.getIdStatoDomanda());
    }
    pst.setString(index++, trdomande.getStatoDomanda());
    if (trdomande.getDataConsegnaIren() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(trdomande.getDataConsegnaIren()));
    }
    pst.setString(index++, trdomande.getNumProtocollo());
    pst.setString(index++, trdomande.getDataProtocollo());
    pst.setString(index++, trdomande.getCognomeRichiedente());
    pst.setString(index++, trdomande.getNomeRichiedente());
    pst.setString(index++, trdomande.getCfRichiedente());
    pst.setString(index++, trdomande.getTelefono());
    pst.setString(index++, trdomande.getCellulare());
    pst.setString(index++, trdomande.getEmail());
    pst.setString(index++, trdomande.getNumNucleoFamiliare());
    pst.setString(index++, trdomande.getIndicatoreIsee12());
    pst.setString(index++, trdomande.getIndicatoreIsee20());
    pst.setString(index++, trdomande.getTipoUtenza());
    pst.setString(index++, trdomande.getNumeroCliente());
    pst.setString(index++, trdomande.getNumContratto());
    pst.setString(index++, trdomande.getViaFornitura());
    pst.setString(index++, trdomande.getComuneFornitura());
    pst.setString(index++, trdomande.getNumCivicoFornitura());
    pst.setString(index++, trdomande.getProvinciaFornitura());
    pst.setString(index++, trdomande.getNominativoContratto());
    pst.setString(index++, trdomande.getCfIntestatarioContratto());
    pst.setString(index++, trdomande.getPivaContratto());
    pst.setString(index++, trdomande.getNominativoApt());
    pst.setString(index++, trdomande.getNominativoAammCond());
    pst.setString(index++, trdomande.getViaAmmCondominio());
    pst.setString(index++, trdomande.getComuneAmmCondominio());
    pst.setString(index++, trdomande.getCivicoAmmCondominio());
    pst.setString(index++, trdomande.getCapAmmCondominio());
    pst.setString(index++, trdomande.getProvAmmCondominio());
    pst.setString(index++, trdomande.getTelAmmCondominio());
    pst.setString(index++, trdomande.getCelAmmCondominio());
    pst.setString(index++, trdomande.getEmailAmmCondominio());
    pst.setString(index++, trdomande.getConsensoPrivacy());
    pst.setString(index++, trdomande.getConsensoInf());
    pst.setString(index++, trdomande.getDatiVerificati());
    pst.setString(index++, trdomande.getEsitoVerifica());
    pst.setString(index++, trdomande.getAnnoDomanda());
    pst.setString(index++, trdomande.getCapFornitura());
    pst.setString(index++, trdomande.getIndicatoreIsee25());
  }

  @Override
  protected String getSqlInsertObjectWithGeneratedKey() {

    String sql =
        "INSERT INTO TR_DOMANDE ( ID_STATO_DOMANDA,STATO_DOMANDA,DATA_CONSEGNA_IREN,NUM_PROTOCOLLO,DATA_PROTOCOLLO,COGNOME_RICHIEDENTE,NOME_RICHIEDENTE,CF_RICHIEDENTE,TELEFONO,CELLULARE,EMAIL,NUM_NUCLEO_FAMILIARE,INDICATORE_ISEE_12,INDICATORE_ISEE_20,TIPO_UTENZA,NUMERO_CLIENTE,NUM_CONTRATTO,VIA_FORNITURA,COMUNE_FORNITURA,NUM_CIVICO_FORNITURA,PROVINCIA_FORNITURA,NOMINATIVO_CONTRATTO,CF_INTESTATARIO_CONTRATTO,PIVA_CONTRATTO,NOMINATIVO_APT,NOMINATIVO_AAMM_COND,VIA_AMM_CONDOMINIO,COMUNE_AMM_CONDOMINIO,CIVICO_AMM_CONDOMINIO,CAP_AMM_CONDOMINIO,PROV_AMM_CONDOMINIO,TEL_AMM_CONDOMINIO,CEL_AMM_CONDOMINIO,EMAIL_AMM_CONDOMINIO,CONSENSO_PRIVACY,CONSENSO_INF,DATI_VERIFICATI,ESITO_VERIFICA,ANNO_DOMANDA,CAP_FORNITURA,INDICATORE_ISEE_25 ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?   )";
    return sql;
  }

  @Override
  protected void setStatementInsertWithGeneratedKey(PreparedStatement st) throws SQLException {
    setFieldStatementWithGeneratedKey(st);
  }

  private int setFieldStatementWithGeneratedKey(PreparedStatement pst) throws SQLException {
    int index = 1;
    if (trdomande.getIdStatoDomanda() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setLong(index++, trdomande.getIdStatoDomanda());
    }
    pst.setString(index++, trdomande.getStatoDomanda());
    if (trdomande.getDataConsegnaIren() == null) {
      pst.setObject(index++, null);
    } else {
      pst.setTimestamp(index++, java.sql.Timestamp.valueOf(trdomande.getDataConsegnaIren()));
    }
    pst.setString(index++, trdomande.getNumProtocollo());
    pst.setString(index++, trdomande.getDataProtocollo());
    pst.setString(index++, trdomande.getCognomeRichiedente());
    pst.setString(index++, trdomande.getNomeRichiedente());
    pst.setString(index++, trdomande.getCfRichiedente());
    pst.setString(index++, trdomande.getTelefono());
    pst.setString(index++, trdomande.getCellulare());
    pst.setString(index++, trdomande.getEmail());
    pst.setString(index++, trdomande.getNumNucleoFamiliare());
    pst.setString(index++, trdomande.getIndicatoreIsee12());
    pst.setString(index++, trdomande.getIndicatoreIsee20());
    pst.setString(index++, trdomande.getTipoUtenza());
    pst.setString(index++, trdomande.getNumeroCliente());
    pst.setString(index++, trdomande.getNumContratto());
    pst.setString(index++, trdomande.getViaFornitura());
    pst.setString(index++, trdomande.getComuneFornitura());
    pst.setString(index++, trdomande.getNumCivicoFornitura());
    pst.setString(index++, trdomande.getProvinciaFornitura());
    pst.setString(index++, trdomande.getNominativoContratto());
    pst.setString(index++, trdomande.getCfIntestatarioContratto());
    pst.setString(index++, trdomande.getPivaContratto());
    pst.setString(index++, trdomande.getNominativoApt());
    pst.setString(index++, trdomande.getNominativoAammCond());
    pst.setString(index++, trdomande.getViaAmmCondominio());
    pst.setString(index++, trdomande.getComuneAmmCondominio());
    pst.setString(index++, trdomande.getCivicoAmmCondominio());
    pst.setString(index++, trdomande.getCapAmmCondominio());
    pst.setString(index++, trdomande.getProvAmmCondominio());
    pst.setString(index++, trdomande.getTelAmmCondominio());
    pst.setString(index++, trdomande.getCelAmmCondominio());
    pst.setString(index++, trdomande.getEmailAmmCondominio());
    pst.setString(index++, trdomande.getConsensoPrivacy());
    pst.setString(index++, trdomande.getConsensoInf());
    pst.setString(index++, trdomande.getDatiVerificati());
    pst.setString(index++, trdomande.getEsitoVerifica());
    pst.setString(index++, trdomande.getAnnoDomanda());
    pst.setString(index++, trdomande.getCapFornitura());
    pst.setString(index++, trdomande.getIndicatoreIsee25());
    return index;
  }

  @Override
  protected String[] getKeyColumnsNames() {
    return new String[] {"ID"};
  }
}
