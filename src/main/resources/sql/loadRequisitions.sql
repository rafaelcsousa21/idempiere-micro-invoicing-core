SELECT *
FROM M_RequisitionLine rl
WHERE EXISTS
        (SELECT *
         FROM C_Order o
                INNER JOIN C_OrderLine ol ON (o.C_Order_ID = ol.C_Order_ID)
         WHERE ol.C_OrderLine_ID = rl.C_OrderLine_ID
           AND o.C_Order_ID=?)
ORDER BY rl.C_OrderLine_ID