package org.compiere.validation;

/** @author hengsin */
public class LoginEventData {
  private int AD_Client_ID;
  private int AD_Org_ID;
  private int AD_Role_ID;
  private int AD_User_ID;

  /**
   * @param aD_Client_ID
   * @param aD_Org_ID
   * @param aD_Role_ID
   * @param aD_User_ID
   */
  public LoginEventData(int aD_Client_ID, int aD_Org_ID, int aD_Role_ID, int aD_User_ID) {
    super();
    AD_Client_ID = aD_Client_ID;
    AD_Org_ID = aD_Org_ID;
    AD_Role_ID = aD_Role_ID;
    AD_User_ID = aD_User_ID;
  }

  /** @return the aD_Client_ID */
  public int  getClientId() {
    return AD_Client_ID;
  }

  /** @return the aD_Org_ID */
  public int  getOrgId() {
    return AD_Org_ID;
  }

  /** @return the aD_Role_ID */
  public int getAD_Role_ID() {
    return AD_Role_ID;
  }

  /** @return the aD_User_ID */
  public int getAD_User_ID() {
    return AD_User_ID;
  }
}
