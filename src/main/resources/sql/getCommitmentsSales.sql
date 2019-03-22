SELECT *
FROM C_OrderLine ol
WHERE EXISTS
        (SELECT * FROM M_InOutLine il WHERE il.C_OrderLine_ID = ol.C_OrderLine_ID AND il.M_InOutLine_ID=?)