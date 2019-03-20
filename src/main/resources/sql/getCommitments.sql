SELECT *
FROM C_OrderLine ol
WHERE EXISTS
  (SELECT *
   FROM C_InvoiceLine il
   WHERE il.C_OrderLine_ID = ol.C_OrderLine_ID
     AND il.C_InvoiceLine_ID=?)
   OR EXISTS
  (SELECT *
   FROM M_MatchPO po
   WHERE po.C_OrderLine_ID = ol.C_OrderLine_ID
     AND po.C_InvoiceLine_ID=?)
