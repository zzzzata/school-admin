package io.renren.dao;

import io.renren.entity.ContractCompanyEntity;

import java.util.List;


/**
 * 分公司云合同注册信息表
 *
 * @author hq
 * @date 2019-9-20 10:00:00
 */
public interface ContractCompanyDao {
    void insertCompany(ContractCompanyEntity company);
    void insertBatch(List<ContractCompanyEntity> companyList);
    int queryCountCompanyByNcid(String ncid);
}
