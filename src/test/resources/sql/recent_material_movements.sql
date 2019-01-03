select
       pro.name as pro_name, pro.m_product_id,

       trans.movementdate as move_date,

       case when trans.movementdate is null then null
            when trans.m_inventoryline_id is not null then doc_inv.documentno
            when trans.m_movementline_id is not null then doc_move.documentno
            when trans.m_inoutline_id is not null then doc_inout.documentno
            when trans.m_productionline_id is not null then doc_pro.documentno
            else null end as doc_no,

       p_cate.name as cate_name, p_cate.m_product_category_id as cate_id, pro.value as pro_value,

       case when trans.movementdate is null then null
            when trans.m_inventoryline_id is not null then doc_inv.doc_name
            when trans.m_movementline_id is not null then doc_move.doc_name
            when trans.m_inoutline_id is not null then doc_inout.doc_name
            when trans.m_productionline_id is not null then doc_pro.doc_name
            else null end  as doc_name,

       sum(trans.movementqty ) as movementqty ,

       sum(case when trans.movementtype in ('I+','P+','V+','C+','W+','M+')  then trans.movementqty else 0 end) as amout_in,

       sum(case when trans.movementtype in ('I-','P-','V-','C-','W-','M-') then -1 * trans.movementqty else 0 end) as amout_out,

       case when trans.movementdate is null then null
            when trans.m_inventoryline_id is not null  then trans.m_inventoryline_id
            when trans.m_movementline_id is not null then trans.m_movementline_id
            when trans.m_inoutline_id is not null then trans.m_inoutline_id
            when trans.m_productionline_id is not null then trans.m_productionline_id
            else null end as line_id
from
     (select
             AD_Org_ID, m_product_id, m_locator_id,
             case when movementdate< current_date - 10 then null else movementdate END as movementdate,
             movementtype, movementqty, m_inventoryline_id, m_movementline_id, m_inoutline_id, m_productionline_id
      from
           m_transaction
      where
          movementdate<=current_date + 10 and ad_client_id= 1000000
        and m_product_id in (select DISTINCT m_product_id
                             from
                                  m_transaction
                             where
                                 movementdate>=current_date - 10 and movementdate<=current_date + 10 and ad_client_id=1000000)
     ) as trans
       INNER JOIN (SELECT m_product_category_id, m_product_id, value, name
                   FROM m_product
                   where m_product_id in (select DISTINCT m_product_id
                                          from
                                               m_transaction
                                          where
                                              movementdate>=current_date - 10 and movementdate<=current_date + 10 and ad_client_id=1000000)
                  ) AS pro on trans.m_product_id=pro.m_product_id
       inner join m_product_category p_cate on pro.m_product_category_id=p_cate.m_product_category_id
       inner join m_locator kho on trans.m_locator_id=kho.m_locator_id

       inner join AD_Org bp on trans.AD_Org_ID=bp.AD_Org_ID

       left outer join (select m_inventoryline.m_inventoryline_id, m_inventory.movementdate, m_inventory.documentno, c_doctype.name as doc_name from m_inventoryline
                                                                                                                                                       inner join m_inventory on m_inventory.m_inventory_id = m_inventoryline.m_inventory_id
                                                                                                                                                       inner join c_doctype on c_doctype.c_doctype_id = m_inventory.c_doctype_id
                       ) as doc_inv on trans.m_inventoryline_id = doc_inv.m_inventoryline_id
       left outer join (select m_movementline.m_movementline_id, m_movement.movementdate, m_movement.documentno, c_doctype.name as doc_name from m_movementline
                                                                                                                                                   inner join m_movement on m_movement.m_movement_id = m_movementline.m_movement_id
                                                                                                                                                   inner join c_doctype on c_doctype.c_doctype_id = m_movement.c_doctype_id
                       ) as doc_move on trans.m_movementline_id = doc_move.m_movementline_id
       left outer join (select m_inoutline.m_inoutline_id, m_inout.movementdate, m_inout.documentno, c_doctype.name as doc_name from m_inoutline
                                                                                                                                       inner join m_inout on m_inout.m_inout_id = m_inoutline.m_inout_id
                                                                                                                                       inner join c_doctype on c_doctype.c_doctype_id = m_inout.c_doctype_id
                       ) as doc_inout on trans.m_inoutline_id = doc_inout.m_inoutline_id
       left outer join (select m_productionline.m_productionline_id, m_production.movementdate, m_production.documentno, 'production'::varchar as doc_name from m_productionline
                                                                                                                                                                  inner join m_production on m_production.m_production_id = m_productionline.m_production_id
                       ) as doc_pro on trans.m_productionline_id = doc_pro.m_productionline_id

Group By

         trans.movementdate, p_cate.name, p_cate.m_product_category_id, pro.name, pro.m_product_id, pro.value,

         case when trans.movementdate is null then null
              when trans.m_inventoryline_id is not null  then trans.m_inventoryline_id
              when trans.m_movementline_id is not null then trans.m_movementline_id
              when trans.m_inoutline_id is not null then trans.m_inoutline_id
              when trans.m_productionline_id is not null then trans.m_productionline_id
              else null END,

         case when trans.movementdate is null then null
              when trans.m_inventoryline_id is not null then doc_inv.documentno
              when trans.m_movementline_id is not null then doc_move.documentno
              when trans.m_inoutline_id is not null then doc_inout.documentno
              when trans.m_productionline_id is not null then doc_pro.documentno
              else null end ,

         case when trans.movementdate is null then null
              when trans.m_inventoryline_id is not null then doc_inv.doc_name
              when trans.m_movementline_id is not null then doc_move.doc_name
              when trans.m_inoutline_id is not null then doc_inout.doc_name
              when trans.m_productionline_id is not null then doc_pro.doc_name
              else null end

ORDER BY
         pro.name, move_date NULLS first, doc_no