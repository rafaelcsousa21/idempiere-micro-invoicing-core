package org.idempiere.process;

import org.compiere.model.IProcessInfoParameter;
import org.compiere.orm.Query;
import org.compiere.process.SvrProcess;
import org.compiere.product.MProductPrice;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

/**
 * Process that copies product information such as substitutes, related, prices, downloads etc from
 * another product. Purchase information and accounting is not copied at this moment.
 *
 * @author Daniel Tamm (usrdno)
 */
public class CopyProduct extends SvrProcess {

    private int m_copyFromId;

    @Override
    protected void prepare() {

        IProcessInfoParameter[] para = getParameter();
        for (IProcessInfoParameter iProcessInfoParameter : para) {
            String name = iProcessInfoParameter.getParameterName();

            if (name.equals("C_CopyFrom_ID")) m_copyFromId = iProcessInfoParameter.getParameterAsInt();
            else if (name.equals("M_Product_ID")) m_copyFromId = iProcessInfoParameter.getParameterAsInt();
            else log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
        }
    }

    @Override
    protected String doIt() throws Exception {

        int toMProductID = getRecordId();
        if (log.isLoggable(Level.INFO))
            log.info("From M_Product_ID=" + m_copyFromId + " to " + toMProductID);
        if (toMProductID == 0) throw new IllegalArgumentException("Target M_Product_ID == 0");
        if (m_copyFromId == 0) throw new IllegalArgumentException("Source M_Product_ID == 0");

        // Get product price from the source product
        List<MProductPrice> prices =
                new Query(getCtx(), MProductPrice.Table_Name, "M_Product_ID=?")
                        .setParameters(m_copyFromId)
                        .setOnlyActiveRecords(true)
                        .list();

        // Copy prices
        MProductPrice priceSrc;
        MProductPrice priceDst;
        for (Iterator<MProductPrice> it = prices.iterator(); it.hasNext(); ) {
            priceSrc = it.next();
            priceDst = new MProductPrice(getCtx(), 0);
            priceDst.setProductId(toMProductID);
            priceDst.setPriceListVersionId(priceSrc.getPriceListVersionId());
            priceDst.setPrices(priceSrc.getPriceList(), priceSrc.getPriceStd(), priceSrc.getPriceLimit());
            priceDst.saveEx();
        }

        int count = prices.size();

        throw new NotImplementedException();

    /*
    // Copy substitutes
    List<X_M_Substitute> subs = new Query(getCtx(), X_M_Substitute.Table_Name, "M_Product_ID=? and NOT substitute_ID=?", null)
    							.setParameters(new Object[]{m_copyFromId, toMProductID})
    							.setOnlyActiveRecords(true)
    							.list();

    X_M_Substitute subSrc;
    X_M_Substitute subDst;
    for (Iterator<X_M_Substitute> it = subs.iterator(); it.hasNext();) {
    	subSrc = it.next();
    	subDst = new X_M_Substitute(getCtx(), 0, null);
    	subDst.setProductId(toMProductID);
    	subDst.setSubstituteId(subSrc.getSubstituteId());
    	subDst.setName(subSrc.getName());
    	subDst.setDescription(subSrc.getDescription());
    	subDst.saveEx(null);
    }

    count += subs.size();

    // Copy related
    List<X_M_RelatedProduct> related = new Query(getCtx(), X_M_RelatedProduct.Table_Name, "M_Product_ID=? and NOT relatedProduct_ID=?", null)
    									.setParameters(new Object[]{m_copyFromId, toMProductID})
    									.setOnlyActiveRecords(true)
    									.list();

    X_M_RelatedProduct relatedSrc;
    X_M_RelatedProduct relatedDst;
    for (Iterator<X_M_RelatedProduct> it = related.iterator(); it.hasNext();) {
    	relatedSrc = it.next();
    	relatedDst = new X_M_RelatedProduct(getCtx(), 0, null);
    	relatedDst.setProductId(toMProductID);
    	relatedDst.setRelatedProductId(relatedSrc.getRelatedProductId());
    	relatedDst.setRelatedProductType(relatedSrc.getRelatedProductType());
    	relatedDst.setName(relatedSrc.getName());
    	relatedDst.setDescription(relatedSrc.getDescription());
    	relatedDst.saveEx(null);
    }

    count += related.size();

    // Copy replenish
    List<X_M_Replenish> replenish = new Query(getCtx(), X_M_Replenish.Table_Name, "M_Product_ID=?", null)
    								.setParameters(new Object[]{m_copyFromId})
    								.setOnlyActiveRecords(true)
    								.list();

    X_M_Replenish replenishSrc;
    X_M_Replenish replenishDst;
    for (Iterator<X_M_Replenish> it = replenish.iterator(); it.hasNext();) {
    	replenishSrc = it.next();
    	replenishDst = new X_M_Replenish(getCtx(), 0, null);
    	replenishDst.setProductId(toMProductID);
    	replenishDst.setWarehouseId(replenishSrc.getWarehouseId());
    	replenishDst.setWarehouseSourceId(replenishSrc.getWarehouseSourceId());
    	replenishDst.setReplenishType(replenishSrc.getReplenishType());
    	replenishDst.setLocatorId(replenishSrc.getLocatorId());
    	replenishDst.setLevel_Min(replenishSrc.getLevel_Min());
    	replenishDst.setLevel_Max(replenishSrc.getLevel_Max());
    	replenishDst.saveEx(null);
    }

    count += replenish.size();

    // Don't copy purchasing since it demands a unique vendor product no


    // Copy business partner
    List<MBPartnerProduct> bpList = new Query(getCtx(), MBPartnerProduct.Table_Name, "M_Product_ID=?", null)
    								.setParameters(new Object[]{m_copyFromId})
    								.setOnlyActiveRecords(true)
    								.list();

    MBPartnerProduct bpSrc;
    MBPartnerProduct bpDst;
    for (Iterator<MBPartnerProduct> it = bpList.iterator(); it.hasNext();) {
    	bpSrc = it.next();
    	bpDst = new MBPartnerProduct(getCtx(), 0, null);
    	bpDst.setBusinessPartnerId(bpSrc.getBusinessPartnerId());
    	bpDst.setDescription(bpSrc.getDescription());
    	bpDst.setIsManufacturer(bpSrc.isManufacturer());
    	bpDst.setProductId(toMProductID);
    	bpDst.setManufacturer(bpSrc.getManufacturer());
    	bpDst.setQualityRating(bpSrc.getQualityRating());
    	bpDst.setShelfLifeMinDays(bpSrc.getShelfLifeMinDays());
    	bpDst.setShelfLifeMinPct(bpSrc.getShelfLifeMinPct());
    	bpDst.setVendorCategory(bpSrc.getVendorCategory());
    	bpDst.setVendorProductNo(bpSrc.getVendorProductNo());
    	bpDst.saveEx(null);
    }
    count += bpList.size();

    // Copy download
    List<MProductDownload> dlList = new Query(getCtx(), MProductDownload.Table_Name, "M_Product_ID=?", null)
    								.setParameters(new Object[]{m_copyFromId})
    								.setOnlyActiveRecords(true)
    								.list();

    MProductDownload dlSrc;
    MProductDownload dlDst;
    for (Iterator<MProductDownload> it = dlList.iterator(); it.hasNext();) {
    	dlSrc = it.next();
    	dlDst = new MProductDownload(getCtx(), 0, null);
    	dlDst.setProductId(toMProductID);
    	dlDst.setName(dlSrc.getName());
    	dlDst.setDownloadURL(dlSrc.getDownloadURL());
    	dlDst.saveEx(null);
    }
    count += dlList.size();

    // Don't copy accounting because of constraints.

    // TODO Auto-generated method stub
    return "@Copied@=" + count;
    */
    }
}
