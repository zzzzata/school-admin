package io.renren.service.impl;

import io.renren.dao.ContractCompanyDao;
import io.renren.entity.ContractCompanyEntity;
import io.renren.service.ContractCompanyService;
import io.renren.service.ContractService;
import io.renren.utils.http.HttpClientUtil4_3;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import io.renren.utils.ExcelReaderJXL;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ContractCompanyServiceImpl implements ContractCompanyService {

    @Autowired
    private ContractCompanyDao contractCompanyDao;
    @Autowired
    private ContractService contractService;


    static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
    private static String URL = "";

    @Resource
    public StringRedisTemplate mainRedis;
    @Value("#{application['online.contract.url']}")
    private void setURL(String str){
        URL = str;
    }




    @Override
    public Long saveCompany(String userName,String certifyType,String certifyNum,String phoneNo,String caType) {
        String url = URL+"/user/company";
        JSONObject json = new JSONObject();
        json.put("userName", userName);
        json.put("certifyType", certifyType);
        json.put("certifyNum", certifyNum);
        json.put("phoneNo",phoneNo);
        json.put("caType", caType);
        JSONObject response = null;
        try{
            response = HttpClientUtil4_3.doPost(url,json,contractService.getToken(false,null));
            int code = response.getInt("code");
            if(code == 200){
                return response.getJSONObject("data").getLong("signerId");
            }else if(code == 20209){
                return contractService.getSingerId(certifyNum);
            }
        }catch (Exception e){
            logger.error("云合同企业认证号码已经存在："+response+"；请求参数："+json);
        }
        return 0l;

    }

    @Override
    public void addCompany(){
        List<ContractCompanyEntity> companyList=getcomList();
        List<ContractCompanyEntity> resultList=new ArrayList<>();
        if(companyList!=null && companyList.size()>0){
            for(ContractCompanyEntity company: companyList){
                if(company.getNcid()!=null && contractCompanyDao.queryCountCompanyByNcid(company.getNcid())==0){
                    //判断表中是否已经存在该公司信息
                    if(company.getCompanyId()==null && StringUtils.isNotBlank(company.getNcid())){
                        company.setCompanyId(saveCompany(company.getCompanyName(),"1",
                                company.getCreditCode(),company.getPhoneNo(),"B2"));
                    }
                    resultList.add(company);
                }
            }
            contractCompanyDao.insertBatch(resultList);
        }
    }


    private List<String[]> getFileData() {
        ContractCompanyEntity entity=null;
        List<ContractCompanyEntity> comList=new ArrayList<ContractCompanyEntity>();
        try {
            File file=new File("C:/Users/ouchujian/Desktop/schoolList.xls");
            FileInputStream is=new FileInputStream(file);
            List<String[]> dataList=ExcelReaderJXL.readExcel(is);
            dataList.remove(0);
            return dataList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<ContractCompanyEntity> getcomList(){
        ContractCompanyEntity entity=null;
        List<String[]> dataList=getFileData();
        List<ContractCompanyEntity> companyList=new ArrayList<ContractCompanyEntity>();
        if (dataList!=null && dataList.size()>0){
            for(String[] strs:dataList){
                entity=new ContractCompanyEntity();
                if(strs[4].equals("9252")){
                    entity.setCompanyId(9252L);
                }
                if(StringUtils.isNotBlank(strs[0])){
                    entity.setDeptName(strs[0]);
                }
                if(StringUtils.isNotBlank(strs[1])){
                    entity.setCompanyName(strs[1]);
                }
                if(StringUtils.isNotBlank(strs[2])){
                    entity.setCreditCode(strs[2]);
                }
                if(StringUtils.isNotBlank(strs[3])){
                    entity.setPhoneNo(strs[3]);
                }
                if(StringUtils.isNotBlank(strs[5])){
                    entity.setNcid(strs[5]);
                }
                companyList.add(entity);
            }
            return companyList;//返回公司对象List
        }
        return null;
    }




}
