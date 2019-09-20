package io.renren.controller;

import io.renren.service.ContractCompanyService;
import io.renren.utils.R;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("contractCompany")
public class ContractCompanyController extends AbstractController{

    @Autowired
    private ContractCompanyService contractCompanyService;


    @ResponseBody
    @RequestMapping(value="/addCompany",method = RequestMethod.GET)
    public R addCompany(){
        contractCompanyService.addCompany();
        return R.ok();
    }

}
