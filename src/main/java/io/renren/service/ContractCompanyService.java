package io.renren.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
*
* @author zhangqiang
* @date 2019/9/19 16:08
*
*/
public interface ContractCompanyService {


    /**
     * 注册公司
      * @param userName 公司名称
     * @param certifyType 企业证件类型
     * @param certifyNum 企业证件号码
     * @param phoneNo 手机号
     * @param caType 证书类型
     * @return
     */
    Long saveCompany(String userName,String certifyType,String certifyNum,String phoneNo,String caType);

    /**
    * 获得本地公司信息云id并入库
    *
    */
    void addCompany();


}
