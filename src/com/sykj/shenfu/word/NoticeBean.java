/*
 * ==================================================================
 * The Huateng Software License
 *
 * Copyright (c) 2008-2012 TOPSCF  All rights reserved.
 * ==================================================================
 */

package com.sykj.shenfu.word;

import fr.opensagres.xdocreport.document.images.IImageProvider;



public class NoticeBean {

	private java.lang.String mortgageName;      	//货物名称
	private java.lang.String mortgageModel;			//规格型号
	private java.lang.String vender;				//生产厂商（产地）
	private java.lang.String confirmPrice;			//调整后单价
	private java.lang.String mortgageNo;			//押品编号
	private java.lang.String quantity;				//数量
	private java.lang.String mortgageUnits;			//单位
	private java.lang.String curcd;					//币种
	private java.lang.String totPrice;				//总价
	private java.lang.String coreName;				//核心企业
	private java.lang.String cname;
	private java.lang.String amount;
	private java.lang.String billDate;
	private java.lang.String billDueDate;
	private java.lang.String warehouse;
	private java.lang.String freightarea;
	private java.lang.String loanOverdueFlag;
	private java.lang.String appno;
	private java.lang.String lnciType;
	private java.lang.String startDate;
	private java.lang.String endDate;
	private java.lang.String lnciAmt;
	private java.lang.String warehouseAddress;
	private java.lang.String billno;
	private java.lang.String debetId;
	private java.lang.String totalRemainAmount;
	private java.lang.String totalRateAmount;
	private java.lang.String totalRateRemainAmount;
	private java.lang.String returnInterestAmount;
	private java.lang.String returnCapitalAmount;
	private java.lang.String money;
	private java.lang.String billsNo;
	private java.lang.String billsDate;
	private java.lang.String debtEnd;
	private java.lang.String deadLine;	
	private java.lang.String remainingAmount;
	private java.lang.String removeAmount;
	private java.lang.String status;
	private java.lang.String billNo;
	private java.lang.String custcdBuyer;
	private java.lang.String custcdSaller;
	private java.lang.String billEndDate;
	private java.lang.String msrno;
	private java.lang.String purchaseDate;
	private java.lang.String billsAmount;
	private java.lang.String loanRemainingAmount;
	private java.lang.String loanAmount;
	private java.lang.String rateCollectAmount;
	private java.lang.String ownRateCollectAmount;
	private java.lang.String payPrincipalAmount;
	private java.lang.String payRateAmount;
	private java.lang.String cashRemainingAmount;
	private java.lang.String reverseAmount;
	private java.lang.String aging;
	private java.lang.String openAmount;
	private java.lang.String loanPercent;
	private java.lang.String assurePercent;
	private java.lang.String totalDebtRemainAmount;
	private java.lang.String poolRemainingAmount;
	private java.lang.String poolUseableAmount;
	private java.lang.String finacingStartDt;
	private java.lang.String finacingEndDt;
	private java.lang.String totalAmount;
	private java.lang.String type;
	private java.lang.String financingStatus;
	private java.lang.String lnciStatus;
	private java.lang.String deferStatus;
	private java.lang.String deferEndDate;
	private java.lang.String deferRate;
	private java.lang.String deferDate;
	private java.lang.String rate;
	private java.lang.String insertDate;
	private java.lang.String billsAmountView;
	private java.lang.String assurePayDate;
	private java.lang.String rebateAmount;
	private java.lang.String buyBackAmount;
	private java.lang.String rebatebalance;
	private java.lang.String remainingUseableAmount;
	private java.lang.String issueReason;
	private java.lang.String memo;
	private java.lang.String warnCount;
	private java.lang.String pressCount;

	private java.lang.String costName;
	private java.lang.String costClassName;
	private java.lang.String costTypeName;
	private java.lang.String chargeTypeName;
	private java.lang.String costCalcTypeName;
	private java.lang.String costRate;
	private java.lang.String costAccount;
	private java.lang.String totalAmt;
	private java.lang.String receiveAmount;
	private java.lang.String reduceAmt;
	private java.lang.String costAmt;

	private java.lang.String billsId;//来单批次号
	private java.lang.String ag;//来单AG编号
	private java.lang.String sendDate;//寄单日期
	private java.lang.String applyDate;//来单日期
	private java.lang.String processStatus;//单据状态

	private java.lang.String id;
	private java.lang.String billType;//单据类型
	private java.lang.String totAmt;
	private java.lang.String manufacturer;
	
	private java.lang.String no;
	private java.lang.String bussContcode;//商务合同号
	private java.lang.String lnciBal;
	private java.lang.String mastContcode;
	private java.lang.String inTableInt;
	private java.lang.String avaliableRiskAmt;
	private java.lang.String reason;
	private java.lang.String payAmount;//核销入账金额
	private java.lang.String repayAmount;//偿还/追加保证金金额
	private java.lang.String bailAccount;
	
	private java.lang.String elecFlag;//电票标识
	private java.lang.String drawer;//出票人
    private java.lang.String certificationNo;  //合格证号    
    
    private String billsType;//凭证类型
	private String bussContAmt;//合同金额
    private String bussPayAmt;//已付金额
    
    private IImageProvider pictrue;
    
    public String getBillsType() {
		return billsType;
	}
	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}
	public String getBussContAmt() {
		return bussContAmt;
	}
	public void setBussContAmt(String bussContAmt) {
		this.bussContAmt = bussContAmt;
	}
	public String getBussPayAmt() {
		return bussPayAmt;
	}
	public void setBussPayAmt(String bussPayAmt) {
		this.bussPayAmt = bussPayAmt;
	}
	public java.lang.String getCertificationNo() {
		return certificationNo;
	}
	public void setCertificationNo(java.lang.String certificationNo) {
		this.certificationNo = certificationNo;
	}
	public java.lang.String getBussContcode() {
		return bussContcode;
	}
	public void setBussContcode(java.lang.String bussContcode) {
		this.bussContcode = bussContcode;
	}
	public java.lang.String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(java.lang.String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public java.lang.String getWarnCount() {
		return warnCount;
	}
	public void setWarnCount(java.lang.String warnCount) {
		this.warnCount = warnCount;
	}
	public java.lang.String getPressCount() {  
		return pressCount;
	}
	public void setPressCount(java.lang.String pressCount){
		this.pressCount = pressCount;
	}
	public java.lang.String getMemo() {
		return memo;
	}
	public void setMemo(java.lang.String memo) {
		this.memo = memo;
	}
	public java.lang.String getIssueReason() {
		return issueReason;
	}
	public void setIssueReason(java.lang.String issueReason) {
		this.issueReason = issueReason;
	}
	public java.lang.String getRemainingUseableAmount() {
		return remainingUseableAmount;
	}
	public void setRemainingUseableAmount(java.lang.String remainingUseableAmount) {
		this.remainingUseableAmount = remainingUseableAmount;
	}
	public java.lang.String getRebatebalance() {
		return rebatebalance;
	}
	public void setRebatebalance(java.lang.String rebatebalance) {
		this.rebatebalance = rebatebalance;
	}
	public java.lang.String getBuyBackAmount() {
		return buyBackAmount;
	}
	public void setBuyBackAmount(java.lang.String buyBackAmount) {
		this.buyBackAmount = buyBackAmount;
	}
	public java.lang.String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(java.lang.String insertDate) {
		this.insertDate = insertDate;
	}
	public java.lang.String getBillsAmountView() {
		return billsAmountView;
	}
	public void setBillsAmountView(java.lang.String billsAmountView) {
		this.billsAmountView = billsAmountView;
	}
	public java.lang.String getAssurePayDate() {
		return assurePayDate;
	}
	public void setAssurePayDate(java.lang.String assurePayDate) {
		this.assurePayDate = assurePayDate;
	}
	public java.lang.String getRebateAmount() {
		return rebateAmount;
	}
	public void setRebateAmount(java.lang.String rebateAmount) {
		this.rebateAmount = rebateAmount;
	}
	public java.lang.String getLoanPercent() {
		return loanPercent;
	}
	public void setLoanPercent(java.lang.String loanPercent) {
		this.loanPercent = loanPercent;
	}
	public java.lang.String getAssurePercent() {
		return assurePercent;
	}
	public void setAssurePercent(java.lang.String assurePercent) {
		this.assurePercent = assurePercent;
	}
	public java.lang.String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(java.lang.String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public java.lang.String getMsrno() {
		return msrno;
	}
	public void setMsrno(java.lang.String msrno) {
		this.msrno = msrno;
	}
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.lang.String getBillsNo() {
		return billsNo;
	}
	public void setBillsNo(java.lang.String billsNo) {
		this.billsNo = billsNo;
	}
	public java.lang.String getBillsDate() {
		return billsDate;
	}
	public void setBillsDate(java.lang.String billsDate) {
		this.billsDate = billsDate;
	}
	public java.lang.String getDebtEnd() {
		return debtEnd;
	}
	public void setDebtEnd(java.lang.String debtEnd) {
		this.debtEnd = debtEnd;
	}
	public java.lang.String getRemainingAmount() {
		return remainingAmount;
	}
	public void setRemainingAmount(java.lang.String remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	public java.lang.String getRemoveAmount() {
		return removeAmount;
	}
	public void setRemoveAmount(java.lang.String removeAmount) {
		this.removeAmount = removeAmount;
	}
	public java.lang.String getBillno() {
		return billno;
	}
	public void setBillno(java.lang.String billno) {
		this.billno = billno;
	}
	public java.lang.String getWarehouseAddress() {
		return warehouseAddress;
	}
	public void setWarehouseAddress(java.lang.String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}
	public java.lang.String getMortgageNo() {
		return mortgageNo;
	}
	public void setMortgageNo(java.lang.String mortgageNo) {
		this.mortgageNo = mortgageNo;
	}
	public java.lang.String getQuantity() {
		return quantity;
	}
	public void setQuantity(java.lang.String quantity) {
		this.quantity = quantity;
	}
	public java.lang.String getMortgageUnits() {
		return mortgageUnits;
	}
	public void setMortgageUnits(java.lang.String mortgageUnits) {
		this.mortgageUnits = mortgageUnits;
	}
	public java.lang.String getCurcd() {
		return curcd;
	}
	public void setCurcd(java.lang.String curcd) {
		this.curcd = curcd;
	}
	public java.lang.String getTotPrice() {
		return totPrice;
	}
	public void setTotPrice(java.lang.String totPrice) {
		this.totPrice = totPrice;
	}
	public java.lang.String getMortgageName() {
		return mortgageName;
	}
	public void setMortgageName(java.lang.String mortgageName) {
		this.mortgageName = mortgageName;
	}
	public java.lang.String getMortgageModel() {
		return mortgageModel;
	}
	public void setMortgageModel(java.lang.String mortgageModel) {
		this.mortgageModel = mortgageModel;
	}
	public java.lang.String getVender() {
		return vender;
	}
	public void setVender(java.lang.String vender) {
		this.vender = vender;
	}
	public java.lang.String getConfirmPrice() {
		return confirmPrice;
	}
	public void setConfirmPrice(java.lang.String confirmPrice) {
		this.confirmPrice = confirmPrice;
	}
	public java.lang.String getCoreName() {
		return coreName;
	}
	public void setCoreName(java.lang.String coreName) {
		this.coreName = coreName;
	}
	public java.lang.String getCname() {
		return cname;
	}
	public void setCname(java.lang.String cname) {
		this.cname = cname;
	}
	public java.lang.String getAmount() {
		return amount;
	}
	public void setAmount(java.lang.String amount) {
		this.amount = amount;
	}
	public java.lang.String getBillDate() {
		return billDate;
	}
	public void setBillDate(java.lang.String billDate) {
		this.billDate = billDate;
	}
	public java.lang.String getBillDueDate() {
		return billDueDate;
	}
	public void setBillDueDate(java.lang.String billDueDate) {
		this.billDueDate = billDueDate;
	}
	public java.lang.String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(java.lang.String warehouse) {
		this.warehouse = warehouse;
	}
	public java.lang.String getFreightarea() {
		return freightarea;
	}
	public void setFreightarea(java.lang.String freightarea) {
		this.freightarea = freightarea;
	}
	public java.lang.String getLoanOverdueFlag() {
		return loanOverdueFlag;
	}
	public void setLoanOverdueFlag(java.lang.String loanOverdueFlag) {
		this.loanOverdueFlag = loanOverdueFlag;
	}
	public java.lang.String getAppno() {
		return appno;
	}
	public void setAppno(java.lang.String appno) {
		this.appno = appno;
	}
	public java.lang.String getLnciType() {
		return lnciType;
	}
	public void setLnciType(java.lang.String lnciType) {
		this.lnciType = lnciType;
	}
	public java.lang.String getStartDate() {
		return startDate;
	}
	public void setStartDate(java.lang.String startDate) {
		this.startDate = startDate;
	}
	public java.lang.String getEndDate() {
		return endDate;
	}
	public void setEndDate(java.lang.String endDate) {
		this.endDate = endDate;
	}
	public java.lang.String getLnciAmt() {
		return lnciAmt;
	}
	public void setLnciAmt(java.lang.String lnciAmt) {
		this.lnciAmt = lnciAmt;
	}
	public java.lang.String getDebetId() {
		return debetId;
	}
	public void setDebetId(java.lang.String debetId) {
		this.debetId = debetId;
	}
	public java.lang.String getTotalRemainAmount() {
		return totalRemainAmount;
	}
	public void setTotalRemainAmount(java.lang.String totalRemainAmount) {
		this.totalRemainAmount = totalRemainAmount;
	}
	public java.lang.String getTotalRateAmount() {
		return totalRateAmount;
	}
	public void setTotalRateAmount(java.lang.String totalRateAmount) {
		this.totalRateAmount = totalRateAmount;
	}
	public java.lang.String getTotalRateRemainAmount() {
		return totalRateRemainAmount;
	}
	public void setTotalRateRemainAmount(java.lang.String totalRateRemainAmount) {
		this.totalRateRemainAmount = totalRateRemainAmount;
	}
	public java.lang.String getReturnInterestAmount() {
		return returnInterestAmount;
	}
	public void setReturnInterestAmount(java.lang.String returnInterestAmount) {
		this.returnInterestAmount = returnInterestAmount;
	}
	public java.lang.String getReturnCapitalAmount() {
		return returnCapitalAmount;
	}
	public void setReturnCapitalAmount(java.lang.String returnCapitalAmount) {
		this.returnCapitalAmount = returnCapitalAmount;
	}
	public java.lang.String getMoney() {
		return money;
	}
	public void setMoney(java.lang.String money) {
		this.money = money;
	}
	public java.lang.String getBillNo() {
		return billNo;
	}
	public void setBillNo(java.lang.String billNo) {
		this.billNo = billNo;
	}
	public java.lang.String getCustcdBuyer() {
		return custcdBuyer;
	}
	public void setCustcdBuyer(java.lang.String custcdBuyer) {
		this.custcdBuyer = custcdBuyer;
	}
	public java.lang.String getCustcdSaller() {
		return custcdSaller;
	}
	public void setCustcdSaller(java.lang.String custcdSaller) {
		this.custcdSaller = custcdSaller;
	}
	public java.lang.String getBillEndDate() {
		return billEndDate;
	}
	public void setBillEndDate(java.lang.String billEndDate) {
		this.billEndDate = billEndDate;
	}
	public java.lang.String getBillsAmount() {
		return billsAmount;
	}
	public void setBillsAmount(java.lang.String billsAmount) {
		this.billsAmount = billsAmount;
	}
	public java.lang.String getLoanRemainingAmount() {
		return loanRemainingAmount;
	}
	public void setLoanRemainingAmount(java.lang.String loanRemainingAmount) {
		this.loanRemainingAmount = loanRemainingAmount;
	}
	public java.lang.String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(java.lang.String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public java.lang.String getRateCollectAmount() {
		return rateCollectAmount;
	}
	public void setRateCollectAmount(java.lang.String rateCollectAmount) {
		this.rateCollectAmount = rateCollectAmount;
	}
	public java.lang.String getOwnRateCollectAmount() {
		return ownRateCollectAmount;
	}
	public void setOwnRateCollectAmount(java.lang.String ownRateCollectAmount) {
		this.ownRateCollectAmount = ownRateCollectAmount;
	}
	public java.lang.String getPayPrincipalAmount() {
		return payPrincipalAmount;
	}
	public void setPayPrincipalAmount(java.lang.String payPrincipalAmount) {
		this.payPrincipalAmount = payPrincipalAmount;
	}
	public java.lang.String getPayRateAmount() {
		return payRateAmount;
	}
	public void setPayRateAmount(java.lang.String payRateAmount) {
		this.payRateAmount = payRateAmount;
	}
	public java.lang.String getCashRemainingAmount() {
		return cashRemainingAmount;
	}
	public void setCashRemainingAmount(java.lang.String cashRemainingAmount) {
		this.cashRemainingAmount = cashRemainingAmount;
	}
	public java.lang.String getReverseAmount() {
		return reverseAmount;
	}
	public void setReverseAmount(java.lang.String reverseAmount) {
		this.reverseAmount = reverseAmount;
	}
	public java.lang.String getAging() {
		return aging;
	}
	public void setAging(java.lang.String aging) {
		this.aging = aging;
	}
	public java.lang.String getOpenAmount() {
		return openAmount;
	}
	public void setOpenAmount(java.lang.String openAmount) {
		this.openAmount = openAmount;
	}
	public java.lang.String getTotalDebtRemainAmount() {
		return totalDebtRemainAmount;
	}
	public void setTotalDebtRemainAmount(java.lang.String totalDebtRemainAmount) {
		this.totalDebtRemainAmount = totalDebtRemainAmount;
	}
	public java.lang.String getPoolRemainingAmount() {
		return poolRemainingAmount;
	}
	public void setPoolRemainingAmount(java.lang.String poolRemainingAmount) {
		this.poolRemainingAmount = poolRemainingAmount;
	}
	public java.lang.String getPoolUseableAmount() {
		return poolUseableAmount;
	}
	public void setPoolUseableAmount(java.lang.String poolUseableAmount) {
		this.poolUseableAmount = poolUseableAmount;
	}
	public java.lang.String getFinacingStartDt() {
		return finacingStartDt;
	}
	public void setFinacingStartDt(java.lang.String finacingStartDt) {
		this.finacingStartDt = finacingStartDt;
	}
	public java.lang.String getFinacingEndDt() {
		return finacingEndDt;
	}
	public void setFinacingEndDt(java.lang.String finacingEndDt) {
		this.finacingEndDt = finacingEndDt;
	}
	public java.lang.String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(java.lang.String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public java.lang.String getType() {
		return type;
	}
	public void setType(java.lang.String type) {
		this.type = type;
	}
	public java.lang.String getFinancingStatus() {
		return financingStatus;
	}
	public void setFinancingStatus(java.lang.String financingStatus) {
		this.financingStatus = financingStatus;
	}
	public java.lang.String getLnciStatus() {
		return lnciStatus;
	}
	public void setLnciStatus(java.lang.String lnciStatus) {
		this.lnciStatus = lnciStatus;
	}
	public java.lang.String getDeferStatus() {
		return deferStatus;
	}
	public void setDeferStatus(java.lang.String deferStatus) {
		this.deferStatus = deferStatus;
	}
	public java.lang.String getDeferEndDate() {
		return deferEndDate;
	}
	public void setDeferEndDate(java.lang.String deferEndDate) {
		this.deferEndDate = deferEndDate;
	}
	public java.lang.String getDeferRate() {
		return deferRate;
	}
	public void setDeferRate(java.lang.String deferRate) {
		this.deferRate = deferRate;
	}
	public java.lang.String getDeferDate() {
		return deferDate;
	}
	public void setDeferDate(java.lang.String deferDate) {
		this.deferDate = deferDate;
	}
	public java.lang.String getRate() {
		return rate;
	}
	public void setRate(java.lang.String rate) {
		this.rate = rate;
	}
	public java.lang.String getCostName() {
		return costName;
	}
	public void setCostName(java.lang.String costName) {
		this.costName = costName;
	}
	public java.lang.String getCostClassName() {
		return costClassName;
	}
	public void setCostClassName(java.lang.String costClassName) {
		this.costClassName = costClassName;
	}
	public java.lang.String getCostTypeName() {
		return costTypeName;
	}
	public void setCostTypeName(java.lang.String costTypeName) {
		this.costTypeName = costTypeName;
	}
	public java.lang.String getChargeTypeName() {
		return chargeTypeName;
	}
	public void setChargeTypeName(java.lang.String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	public java.lang.String getCostCalcTypeName() {
		return costCalcTypeName;
	}
	public void setCostCalcTypeName(java.lang.String costCalcTypeName) {
		this.costCalcTypeName = costCalcTypeName;
	}
	public java.lang.String getCostRate() {
		return costRate;
	}
	public void setCostRate(java.lang.String costRate) {
		this.costRate = costRate;
	}
	public java.lang.String getCostAccount() {
		return costAccount;
	}
	public void setCostAccount(java.lang.String costAccount) {
		this.costAccount = costAccount;
	}
	public java.lang.String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(java.lang.String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public java.lang.String getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(java.lang.String receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public java.lang.String getReduceAmt() {
		return reduceAmt;
	}
	public void setReduceAmt(java.lang.String reduceAmt) {
		this.reduceAmt = reduceAmt;
	}
	public java.lang.String getCostAmt() {
		return costAmt;
	}
	public void setCostAmt(java.lang.String costAmt) {
		this.costAmt = costAmt;
	}
	public java.lang.String getBillsId() {
		return billsId;
	}
	public void setBillsId(java.lang.String billsId) {
		this.billsId = billsId;
	}
	public java.lang.String getAg() {
		return ag;
	}
	public void setAg(java.lang.String ag) {
		this.ag = ag;
	}
	public java.lang.String getSendDate() {
		return sendDate;
	}
	public void setSendDate(java.lang.String sendDate) {
		this.sendDate = sendDate;
	}
	public java.lang.String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(java.lang.String applyDate) {
		this.applyDate = applyDate;
	}
	public java.lang.String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(java.lang.String processStatus) {
		this.processStatus = processStatus;
	}
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getBillType() {
		return billType;
	}
	public void setBillType(java.lang.String billType) {
		this.billType = billType;
	}
	public java.lang.String getTotAmt() {
		return totAmt;
	}
	public void setTotAmt(java.lang.String totAmt) {
		this.totAmt = totAmt;
	}
	public java.lang.String getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(java.lang.String deadLine) {
		this.deadLine = deadLine;
	}
	public java.lang.String getNo() {
		return no;
	}
	public void setNo(java.lang.String no) {
		this.no = no;
	}
	public java.lang.String getLnciBal() {
		return lnciBal;
	}
	public void setLnciBal(java.lang.String lnciBal) {
		this.lnciBal = lnciBal;
	}
	public java.lang.String getMastContcode() {
		return mastContcode;
	}
	public void setMastContcode(java.lang.String mastContcode) {
		this.mastContcode = mastContcode;
	}
	public java.lang.String getInTableInt() {
		return inTableInt;
	}
	public void setInTableInt(java.lang.String inTableInt) {
		this.inTableInt = inTableInt;
	}
	public java.lang.String getAvaliableRiskAmt() {
		return avaliableRiskAmt;
	}
	public void setAvaliableRiskAmt(java.lang.String avaliableRiskAmt) {
		this.avaliableRiskAmt = avaliableRiskAmt;
	}
	public java.lang.String getReason() {
		return reason;
	}
	public void setReason(java.lang.String reason) {
		this.reason = reason;
	}
	public java.lang.String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(java.lang.String payAmount) {
		this.payAmount = payAmount;
	}
	public java.lang.String getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(java.lang.String repayAmount) {
		this.repayAmount = repayAmount;
	}
	public java.lang.String getBailAccount() {
		return bailAccount;
	}
	public void setBailAccount(java.lang.String bailAccount) {
		this.bailAccount = bailAccount;
	}
	public java.lang.String getElecFlag() {
		return elecFlag;
	}
	public void setElecFlag(java.lang.String elecFlag) {
		this.elecFlag = elecFlag;
	}
	public java.lang.String getDrawer() {
		return drawer;
	}
	public void setDrawer(java.lang.String drawer) {
		this.drawer = drawer;
	}
	public IImageProvider getPictrue() {
		return pictrue;
	}
	public void setPictrue(IImageProvider pictrue) {
		this.pictrue = pictrue;
	}

}
