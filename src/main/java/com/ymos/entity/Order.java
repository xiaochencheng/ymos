package com.ymos.entity;



public class Order extends Id{

    //=================================================================
   private String bgh;                              //包裹号
   private String orderId;                          //订单号
   private String jyh;                              //交易号
   private String orderStatus;                      //订单状态
   private String ptqd;                             //平台渠道
   private String dpzh;                             //店铺账号
   private String orderRamker;                      //订单备注
   private String jhRamker;                         //拣货备注
   private String kfRamker;                         //客服备注
   private String tkly;                             //退款理由
   private String xdsj;                               //下单时间
   private String fksj;                               //付款时间
   private String tjsj;                               //提交时间
   private String fhsj;                               //发货时间
   private String tksj;                               //退款时间
   private String mddysj;                             //面单打印时间
   private String jhdysj;                             //拣货单打印时间
   private String fkfs;                             //付款方式
   private String bzsx;                             //币种缩写
   private String orderMoney;                   //订单金额
   private String mjzfyf;                       //卖家支付运费
   private String tkMoney;                      //退款金额
   private String ygly;                             //预估利润
   private String cbly;                             //成本利润
   private String xsly;                             //销售利润
   private String ygfy;                         //预估运费
   private String sku;                              //sku
   private String proId;                            //产品id
   private String proName;                          //产品名称
   private String proPrice;                     //产品售价
   private int proNum;                              //产品数量
   private String proGuige;                         //产品规格
   private String imgURL;                           //图片网址
   private String lyURL;                            //来源URL
   private String xslj;                             //销售链接
   private String dpm;                              //多品名
   private String spSKU;                            //商品SKU
   private String spbm;                             //商品编码
   private String spmc;                             //商品名称
   private int kcl;                                 //库存量
   private String spcgj;                        //商品采购价
   private String kcjg;                         //库存价格
   private String wxysp;                            //危险运输品
   private String fhck;                             //发货仓库
   private String hjw;                              //货架位
   private String mjzh;                             //买家账号
   private String mjname;                           //买家姓名
   private String mjEmail;                          //买家Email
   private String shrname;                          //收货人姓名
   private String shrcompany;                       //收货人公司
   private String shrsh;                            //收货人税号
   private String shrmph;                           //收货人门牌号
   private String xxaddress;                        //详细地址
   private String addresOne;                        //地址1
   private String addressTwo;                       //地址2
   private String addOneTwo;                        //地址1 地址2
   private String shrCity;                          //收货人城市
   private String shrShen;                          //收货人州/省
   private String code;                             //邮编
   private String shrCountry;                       //收货人国家
   private String chCountry;                        //中文国家名
   private String countryS;                         //国家二字码
   private String shrTel;                           //收货人电话
   private String shrIphone;                        //收货人手机
   private String mjzdwl;                           //买家指定物流
   private String wlfs;                             //物流方式
   private String ydh;                              //运单号
   private String czzl;                         //称重重量
   private String chbgName;                         //中文报关名
   private String enbgName;                         //英文报关名
   private String sbPrice;                      //申报单价
   private int bgWidth;                             //报关重量
   private String days;                             //天数
   private String wuliu;                            //物流信息
    // ==============================================================


   public String getWuliu() {
      return wuliu;
   }

   public void setWuliu(String wuliu) {
      this.wuliu = wuliu;
   }

   public String getDays() {
      return days;
   }

   public void setDays(String days) {
      this.days = days;
   }

   public String getBgh() {
      return bgh;
   }

   public void setBgh(String bgh) {
      this.bgh = bgh;
   }

   public String getOrderId() {
      return orderId;
   }

   public void setOrderId(String orderId) {
      this.orderId = orderId;
   }

   public String getJyh() {
      return jyh;
   }

   public void setJyh(String jyh) {
      this.jyh = jyh;
   }

   public String getOrderStatus() {
      return orderStatus;
   }

   public void setOrderStatus(String orderStatus) {
      this.orderStatus = orderStatus;
   }

   public String getPtqd() {
      return ptqd;
   }

   public void setPtqd(String ptqd) {
      this.ptqd = ptqd;
   }

   public String getDpzh() {
      return dpzh;
   }

   public void setDpzh(String dpzh) {
      this.dpzh = dpzh;
   }

   public String getOrderRamker() {
      return orderRamker;
   }

   public void setOrderRamker(String orderRamker) {
      this.orderRamker = orderRamker;
   }

   public String getJhRamker() {
      return jhRamker;
   }

   public void setJhRamker(String jhRamker) {
      this.jhRamker = jhRamker;
   }

   public String getKfRamker() {
      return kfRamker;
   }

   public void setKfRamker(String kfRamker) {
      this.kfRamker = kfRamker;
   }

   public String getTkly() {
      return tkly;
   }

   public void setTkly(String tkly) {
      this.tkly = tkly;
   }

   public String getXdsj() {
      return xdsj;
   }

   public void setXdsj(String xdsj) {
      this.xdsj = xdsj;
   }

   public String getFksj() {
      return fksj;
   }

   public void setFksj(String fksj) {
      this.fksj = fksj;
   }

   public String getTjsj() {
      return tjsj;
   }

   public void setTjsj(String tjsj) {
      this.tjsj = tjsj;
   }

   public String getFhsj() {
      return fhsj;
   }

   public void setFhsj(String fhsj) {
      this.fhsj = fhsj;
   }

   public String getTksj() {
      return tksj;
   }

   public void setTksj(String tksj) {
      this.tksj = tksj;
   }

   public String getMddysj() {
      return mddysj;
   }

   public void setMddysj(String mddysj) {
      this.mddysj = mddysj;
   }

   public String getJhdysj() {
      return jhdysj;
   }

   public void setJhdysj(String jhdysj) {
      this.jhdysj = jhdysj;
   }

   public String getFkfs() {
      return fkfs;
   }

   public void setFkfs(String fkfs) {
      this.fkfs = fkfs;
   }

   public String getBzsx() {
      return bzsx;
   }

   public void setBzsx(String bzsx) {
      this.bzsx = bzsx;
   }

   public String getOrderMoney() {
      return orderMoney;
   }

   public void setOrderMoney(String orderMoney) {
      this.orderMoney = orderMoney;
   }

   public String getMjzfyf() {
      return mjzfyf;
   }

   public void setMjzfyf(String mjzfyf) {
      this.mjzfyf = mjzfyf;
   }

   public String getTkMoney() {
      return tkMoney;
   }

   public void setTkMoney(String tkMoney) {
      this.tkMoney = tkMoney;
   }

   public String getYgly() {
      return ygly;
   }

   public void setYgly(String ygly) {
      this.ygly = ygly;
   }

   public String getCbly() {
      return cbly;
   }

   public void setCbly(String cbly) {
      this.cbly = cbly;
   }

   public String getXsly() {
      return xsly;
   }

   public void setXsly(String xsly) {
      this.xsly = xsly;
   }

   public String getYgfy() {
      return ygfy;
   }

   public void setYgfy(String ygfy) {
      this.ygfy = ygfy;
   }

   public String getSku() {
      return sku;
   }

   public void setSku(String sku) {
      this.sku = sku;
   }

   public String getProId() {
      return proId;
   }

   public void setProId(String proId) {
      this.proId = proId;
   }

   public String getProName() {
      return proName;
   }

   public void setProName(String proName) {
      this.proName = proName;
   }

   public String getProPrice() {
      return proPrice;
   }

   public void setProPrice(String proPrice) {
      this.proPrice = proPrice;
   }

   public int getProNum() {
      return proNum;
   }

   public void setProNum(int proNum) {
      this.proNum = proNum;
   }

   public String getProGuige() {
      return proGuige;
   }

   public void setProGuige(String proGuige) {
      this.proGuige = proGuige;
   }

   public String getImgURL() {
      return imgURL;
   }

   public void setImgURL(String imgURL) {
      this.imgURL = imgURL;
   }

   public String getLyURL() {
      return lyURL;
   }

   public void setLyURL(String lyURL) {
      this.lyURL = lyURL;
   }

   public String getXslj() {
      return xslj;
   }

   public void setXslj(String xslj) {
      this.xslj = xslj;
   }

   public String getDpm() {
      return dpm;
   }

   public void setDpm(String dpm) {
      this.dpm = dpm;
   }

   public String getSpSKU() {
      return spSKU;
   }

   public void setSpSKU(String spSKU) {
      this.spSKU = spSKU;
   }

   public String getSpbm() {
      return spbm;
   }

   public void setSpbm(String spbm) {
      this.spbm = spbm;
   }

   public String getSpmc() {
      return spmc;
   }

   public void setSpmc(String spmc) {
      this.spmc = spmc;
   }

   public int getKcl() {
      return kcl;
   }

   public void setKcl(int kcl) {
      this.kcl = kcl;
   }

   public String getSpcgj() {
      return spcgj;
   }

   public void setSpcgj(String spcgj) {
      this.spcgj = spcgj;
   }

   public String getKcjg() {
      return kcjg;
   }

   public void setKcjg(String kcjg) {
      this.kcjg = kcjg;
   }

   public String getWxysp() {
      return wxysp;
   }

   public void setWxysp(String wxysp) {
      this.wxysp = wxysp;
   }

   public String getFhck() {
      return fhck;
   }

   public void setFhck(String fhck) {
      this.fhck = fhck;
   }

   public String getHjw() {
      return hjw;
   }

   public void setHjw(String hjw) {
      this.hjw = hjw;
   }

   public String getMjzh() {
      return mjzh;
   }

   public void setMjzh(String mjzh) {
      this.mjzh = mjzh;
   }

   public String getMjname() {
      return mjname;
   }

   public void setMjname(String mjname) {
      this.mjname = mjname;
   }

   public String getMjEmail() {
      return mjEmail;
   }

   public void setMjEmail(String mjEmail) {
      this.mjEmail = mjEmail;
   }

   public String getShrname() {
      return shrname;
   }

   public void setShrname(String shrname) {
      this.shrname = shrname;
   }

   public String getShrcompany() {
      return shrcompany;
   }

   public void setShrcompany(String shrcompany) {
      this.shrcompany = shrcompany;
   }

   public String getShrsh() {
      return shrsh;
   }

   public void setShrsh(String shrsh) {
      this.shrsh = shrsh;
   }

   public String getShrmph() {
      return shrmph;
   }

   public void setShrmph(String shrmph) {
      this.shrmph = shrmph;
   }

   public String getXxaddress() {
      return xxaddress;
   }

   public void setXxaddress(String xxaddress) {
      this.xxaddress = xxaddress;
   }

   public String getAddresOne() {
      return addresOne;
   }

   public void setAddresOne(String addresOne) {
      this.addresOne = addresOne;
   }

   public String getAddressTwo() {
      return addressTwo;
   }

   public void setAddressTwo(String addressTwo) {
      this.addressTwo = addressTwo;
   }

   public String getAddOneTwo() {
      return addOneTwo;
   }

   public void setAddOneTwo(String addOneTwo) {
      this.addOneTwo = addOneTwo;
   }

   public String getShrCity() {
      return shrCity;
   }

   public void setShrCity(String shrCity) {
      this.shrCity = shrCity;
   }

   public String getShrShen() {
      return shrShen;
   }

   public void setShrShen(String shrShen) {
      this.shrShen = shrShen;
   }

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getShrCountry() {
      return shrCountry;
   }

   public void setShrCountry(String shrCountry) {
      this.shrCountry = shrCountry;
   }

   public String getChCountry() {
      return chCountry;
   }

   public void setChCountry(String chCountry) {
      this.chCountry = chCountry;
   }

   public String getCountryS() {
      return countryS;
   }

   public void setCountryS(String countryS) {
      this.countryS = countryS;
   }

   public String getShrTel() {
      return shrTel;
   }

   public void setShrTel(String shrTel) {
      this.shrTel = shrTel;
   }

   public String getShrIphone() {
      return shrIphone;
   }

   public void setShrIphone(String shrIphone) {
      this.shrIphone = shrIphone;
   }

   public String getMjzdwl() {
      return mjzdwl;
   }

   public void setMjzdwl(String mjzdwl) {
      this.mjzdwl = mjzdwl;
   }

   public String getWlfs() {
      return wlfs;
   }

   public void setWlfs(String wlfs) {
      this.wlfs = wlfs;
   }

   public String getYdh() {
      return ydh;
   }

   public void setYdh(String ydh) {
      this.ydh = ydh;
   }

   public String getCzzl() {
      return czzl;
   }

   public void setCzzl(String czzl) {
      this.czzl = czzl;
   }

   public String getChbgName() {
      return chbgName;
   }

   public void setChbgName(String chbgName) {
      this.chbgName = chbgName;
   }

   public String getEnbgName() {
      return enbgName;
   }

   public void setEnbgName(String enbgName) {
      this.enbgName = enbgName;
   }

   public String getSbPrice() {
      return sbPrice;
   }

   public void setSbPrice(String sbPrice) {
      this.sbPrice = sbPrice;
   }

   public int getBgWidth() {
      return bgWidth;
   }

   public void setBgWidth(int bgWidth) {
      this.bgWidth = bgWidth;
   }

   @Override
   public String toString() {
      return "Order{" +
              "bgh='" + bgh + '\'' +
              ", orderId='" + orderId + '\'' +
              ", jyh='" + jyh + '\'' +
              ", orderStatus='" + orderStatus + '\'' +
              ", ptqd='" + ptqd + '\'' +
              ", dpzh='" + dpzh + '\'' +
              ", orderRamker='" + orderRamker + '\'' +
              ", jhRamker='" + jhRamker + '\'' +
              ", kfRamker='" + kfRamker + '\'' +
              ", tkly='" + tkly + '\'' +
              ", xdsj=" + xdsj +
              ", fksj=" + fksj +
              ", tjsj=" + tjsj +
              ", fhsj=" + fhsj +
              ", tksj=" + tksj +
              ", mddysj=" + mddysj +
              ", jhdysj=" + jhdysj +
              ", fkfs='" + fkfs + '\'' +
              ", bzsx='" + bzsx + '\'' +
              ", orderMoney=" + orderMoney +
              ", mjzfyf=" + mjzfyf +
              ", tkMoney=" + tkMoney +
              ", ygly='" + ygly + '\'' +
              ", cbly='" + cbly + '\'' +
              ", xsly='" + xsly + '\'' +
              ", ygfy=" + ygfy +
              ", sku='" + sku + '\'' +
              ", proId='" + proId + '\'' +
              ", proName='" + proName + '\'' +
              ", proPrice=" + proPrice +
              ", proNum=" + proNum +
              ", proGuige='" + proGuige + '\'' +
              ", imgURL='" + imgURL + '\'' +
              ", lyURL='" + lyURL + '\'' +
              ", xslj='" + xslj + '\'' +
              ", dpm='" + dpm + '\'' +
              ", spSKU='" + spSKU + '\'' +
              ", spbm='" + spbm + '\'' +
              ", spmc='" + spmc + '\'' +
              ", kcl=" + kcl +
              ", spcgj=" + spcgj +
              ", kcjg=" + kcjg +
              ", wxysp='" + wxysp + '\'' +
              ", fhck='" + fhck + '\'' +
              ", hjw='" + hjw + '\'' +
              ", mjzh='" + mjzh + '\'' +
              ", mjname='" + mjname + '\'' +
              ", mjEmail='" + mjEmail + '\'' +
              ", shrname='" + shrname + '\'' +
              ", shrcompany='" + shrcompany + '\'' +
              ", shrsh='" + shrsh + '\'' +
              ", shrmph='" + shrmph + '\'' +
              ", xxaddress='" + xxaddress + '\'' +
              ", addresOne='" + addresOne + '\'' +
              ", addressTwo='" + addressTwo + '\'' +
              ", addOneTwo='" + addOneTwo + '\'' +
              ", shrCity='" + shrCity + '\'' +
              ", shrShen='" + shrShen + '\'' +
              ", code='" + code + '\'' +
              ", shrCountry='" + shrCountry + '\'' +
              ", chCountry='" + chCountry + '\'' +
              ", countryS='" + countryS + '\'' +
              ", shrTel='" + shrTel + '\'' +
              ", shrIphone='" + shrIphone + '\'' +
              ", mjzdwl='" + mjzdwl + '\'' +
              ", wlfs='" + wlfs + '\'' +
              ", ydh='" + ydh + '\'' +
              ", czzl=" + czzl +
              ", chbgName='" + chbgName + '\'' +
              ", enbgName='" + enbgName + '\'' +
              ", sbPrice=" + sbPrice +
              ", bgWidth=" + bgWidth +
              ", days=" + days +
              ", wuliu=" + wuliu +
              '}';
   }
}
