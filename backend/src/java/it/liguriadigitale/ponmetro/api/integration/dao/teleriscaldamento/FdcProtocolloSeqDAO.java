package it.liguriadigitale.ponmetro.api.integration.dao.teleriscaldamento;

import it.liguriadigitale.framework.integration.dao.SequenceDAO;

public class FdcProtocolloSeqDAO extends SequenceDAO {

  private static final String FDC_PROTOCOLLO_SEQ = "FDC_PROTOCOLLO_SEQ";

  public FdcProtocolloSeqDAO() {
    super(FDC_PROTOCOLLO_SEQ);
  }
}
