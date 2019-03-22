SELECT s.M_Product_ID,
       s.M_Locator_ID,
       s.M_AttributeSetInstance_ID,
       s.AD_Client_ID,
       s.AD_Org_ID,
       s.IsActive,
       s.Created,
       s.CreatedBy,
       s.Updated,
       s.UpdatedBy,
       s.QtyOnHand,
       s.DateLastInventory,
       s.M_StorageOnHand_UU,
       s.DateMaterialPolicy
FROM M_StorageOnHand s
       INNER JOIN M_Locator l ON (l.M_Locator_ID = s.M_Locator_ID)
       LEFT OUTER JOIN M_AttributeSetInstance asi ON (s.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID)