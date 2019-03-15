SELECT distinct inv.c_invoice_id,
                inv.documentno,
                inv.docstatus,
                inv.ad_client_id,
                inv.ad_org_id,
                inv.dateinvoiced,
                inv.dateacct,
                line.c_invoiceline_id,
                line.qtyinvoiced,
                line.line,
                line.linenetamt,
                (line.linenetamt / line.qtyinvoiced)                                                     as unitprice,
                line2.description                                                                        as line_description,
                org_loc.address1                                                                         as org_loc_address1,
                org_loc.address2                                                                         as org_loc_address2,
                org_loc.city                                                                             as org_loc_city,
                org_loc.postal                                                                           as org_loc_postal,
                org_country.name                                                                         as org_country_name,
                org.name                                                                                 as org_client_name,
                coalesce(org.description, '')                                                            as org_description,
                bpartner.name                                                                            as bpartner_name,
                bpartner.duns                                                                            as bpartner_duns,
                bpartner.taxid                                                                           as bpartner_taxid,
                loc.address1                                                                             as loc_address1,
                loc.address2                                                                             as loc_address2,
                loc.city                                                                                 as loc_city,
                loc.postal                                                                               as loc_postal,
                country.name                                                                             as country_name,
                prod.name,
                org.duns,
                org.taxid,
                case when inv.docstatus = 'DR' then '21%' else tax.taxindicator end,
                tax.discount,
                tax.priceactual,
                tax.linenetamt,
                tax.grandtotal,
                tax.totallines,
                tax.rate,
                round(tax.linenetamt * (1 + tax.rate / 100), 2)                                          as lineincvat,
                round(tax.linenetamt * tax.rate / 100, 2)                                                as linetax,
                round(tax.grandtotal / (1 + tax.rate / 100), 2)                                          as grandtotalwovat,
                round(tax.grandtotal - tax.grandtotal / (1 + tax.rate / 100), 2)                         as grandtotalvat,
                case when curr.iso_code = 'CZK' then 'Kč' else curr.iso_code end                         as curr_iso_code,
                case when inv.docstatus = 'DR' then current_date else sched.duedate end,
                coalesce(case
                           when curr.iso_code = 'CZK' then bacc.accountno
                           else
                             case
                               when curr.iso_code = 'EUR' then bacc.iban
                               else bacc.iban || '/' || bank.swiftcode
                               end end,
                         '')                                                                             as bank_account,
                case when acc.amtacctcr is null then 0.0 else acc.amtacctcr end                          as amtacctcr,
                case when acc.amtacctcr is null then true else false end                                 as reverse_charge,
                case when inv.docstatus = 'DR' then coalesce(bacc.iban, '') else bacc.iban end           as iban,
                case
                  when inv.docstatus = 'DR' then coalesce(bacc.accountno, '')
                  else bacc.accountno end                                                                as basic_bank_account_no,
                case
                  when inv.docstatus = 'DR' then
                    case
                      when curr.iso_code = 'EUR' then tax.totallines * 25.5
                      else tax.totallines
                      end
                  else Coalesce((SELECT SUM(amtacctcr)
                                 FROM fact_acct
                                 WHERE fact_acct.ad_org_id = inv.ad_org_id
                                   AND inv.ad_client_id = fact_acct.ad_client_id
                                   AND fact_acct.record_id = inv.c_invoice_id
                                   and fact_acct.m_product_id is not null
                                ), tax.totallines) end                                                   as base_czk,
                case
                  when inv.docstatus = 'DR' then current_date
                  else (CASE (EXTRACT(ISODOW FROM sched.duedate)::integer) % 7
                          WHEN 1 THEN sched.duedate - 3
                          WHEN 0 THEN sched.duedate - 2
                          ELSE sched.duedate - 1
                    END) end                                                                             AS due_previous_business_day,
                case
                  when inv.docstatus = 'DR' then current_date
                  else (CASE (EXTRACT(ISODOW FROM sched.duedate)::integer) % 7
                          WHEN 6 THEN sched.duedate - 5
                          WHEN 7 THEN sched.duedate - 6
                          ELSE sched.duedate - 7
                    END) end                                                                             AS due_previous_5business_days,
                info.phone,
                info.email
FROM adempiere.c_invoice_v inv
       LEFT JOIN c_invoiceline_v line ON inv.c_invoice_id = line.c_invoice_id
       LEFT JOIN c_invoiceline line2 ON line2.c_invoiceline_id = line.c_invoiceline_id
       LEFT JOIN ad_org_v org ON org.ad_org_id = inv.ad_org_id AND inv.ad_client_id = org.ad_client_id
       LEFT JOIN ad_orginfo info ON info.ad_org_id = inv.ad_org_id AND inv.ad_client_id = info.ad_client_id
       LEFT JOIN c_location org_loc ON org_loc.c_location_id = org.c_location_id
       LEFT JOIN c_country org_country ON org_loc.c_country_id = org_country.c_country_id
       LEFT JOIN ad_client client ON client.ad_client_id = inv.ad_client_id
       LEFT JOIN c_bpartner bpartner ON bpartner.c_bpartner_id = inv.c_bpartner_id
       LEFT JOIN c_bpartner_location bploc ON inv.c_bpartner_location_id = bploc.c_bpartner_location_id
       LEFT JOIN c_location loc ON loc.c_location_id = bploc.c_location_id
       LEFT JOIN c_country country ON loc.c_country_id = country.c_country_id
       LEFT JOIN m_product prod ON prod.m_product_id = line.m_product_id
       LEFT JOIN c_invoice_linetax_vt tax ON tax.c_invoiceline_id = line.c_invoiceline_id
       LEFT JOIN c_currency curr ON curr.c_currency_id = inv.c_currency_id
       LEFT JOIN c_invoicepayschedule sched ON sched.c_invoice_id = inv.c_invoice_id
       LEFT JOIN c_bankaccount bacc ON bacc.ad_org_id = inv.ad_org_id AND inv.ad_client_id = bacc.ad_client_id
       LEFT JOIN c_bank bank ON bank.c_bank_id = bacc.c_bank_id
       LEFT JOIN fact_acct acc ON acc.ad_org_id = inv.ad_org_id AND inv.ad_client_id = acc.ad_client_id AND
                                  acc.record_id = inv.c_invoice_id AND amtsourcecr > 0.0 and acc.m_product_id is null
WHERE inv.c_invoice_id = $P{RECORD_ID}
ORDER BY inv.documentno, line.line