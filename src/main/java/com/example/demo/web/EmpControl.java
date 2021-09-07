package com.example.demo.web;

import com.example.demo.MyUtil;
import com.example.demo.business.EmpService;
import com.example.demo.entity.AbstractEmpAgg;
import com.example.demo.entity.EmpEntity;
import com.example.demo.repository.EmpStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api")
public class EmpControl {
    final static Logger log = LoggerFactory.getLogger(EmpControl.class);
    @Autowired
    EmpService empService;
    @Autowired
    EmpStore empStore;

    @RequestMapping(path="/emps/{id}", method = RequestMethod.GET)
    public EmpEntity get(@PathVariable("id") Long id){
        log.info("get emp by {}", id);
        Optional<EmpEntity> emp = empStore.findEntireById(id);
        MyUtil.checkExist(emp, "emp not found");

        return emp.get();
    }

    @RequestMapping(path = "/emps2")
    public List<EmpEntity> specialEmpList(@RequestParam("dep") Long depId){
        log.info("get emp-list by dep {}", depId);
        List<EmpEntity> emps = empStore.findSpecial(depId);
        //MyUtil.checkExist(emp, "emp not found");
        return emps;
    }


    // update tel or addre
    @RequestMapping(value = "/emp1/{id}", method = RequestMethod.GET)
    public EmpEntity update1(@PathVariable("id") Long id,
                             @RequestParam(value = "tel") Optional<String> tel,
                             @RequestParam(value = "addr") Optional<String> addr,
                             @RequestParam(value = "data") Optional<Long> data){
        log.info("get emp by {}", id);
        Optional<EmpEntity> emp1 = empStore.findById(id);
        MyUtil.checkExist(emp1, "emp not found");

        EmpEntity emp = emp1.get();
        MyUtil.sleep(5000);
        String msg = putEmp(emp, tel, addr, data);

        empService.save(emp);
        log.info("emp update1 with {}", msg);

        return emp;
    }

    // update tel or addre
    @RequestMapping(value = "/emp2/{id}", method = RequestMethod.GET)
    public EmpEntity update2(@PathVariable("id") Long id,
                             @RequestParam(value = "tel") Optional<String> tel,
                             @RequestParam(value = "addr") Optional<String> addr,
                             @RequestParam(value = "data") Optional<Long> data){
        log.info("get emp by {}", id);
        Optional<EmpEntity> emp1 = empStore.findById(id);
        MyUtil.checkExist(emp1, "emp not found");
//        String msg ;
        EmpEntity emp = emp1.get();
        String msg = putEmp(emp, tel, addr, data);

        empService.save(emp);
        log.info("emp update2 with {}", msg);

        return emp;
    }
    @RequestMapping(value = "/emp-transfer/{id}/{id2}", method = RequestMethod.GET)
    public int transfer(@PathVariable("id") Long id, @PathVariable("id2") Long id2,
                        @RequestParam(value = "inc") Optional<Double> inc){

        double incValue = inc.orElse(10.0);
        int ret = empService.transfer(id, id2, incValue);
        return ret;
    }

    @RequestMapping(value = "/emp-group-by-dept")
    public List<AbstractEmpAgg> empGroupByDept(){
        return empStore.totalPerDept();
    }

    static String putEmp(EmpEntity emp, Optional<String> tel, Optional<String> addr, Optional<Long> data){
        ArrayList<String> arr = new ArrayList<>();
        //StringBuffer buf = new StringBuffer();
        if(tel.isPresent()){
            emp.setTel(tel.get());
            arr.add( "tel " + tel.get() );
        }
        if(addr.isPresent()) {
            emp.setAddr(addr.get());
            arr.add( "addr " + addr.get());
        }
        if(data.isPresent()){
            emp.setData(data.get().doubleValue());
            arr.add( "data " + data.get());
        }
        if(arr.size()==0){
            log.info("neither tel nor addr/data is provided");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "neither tel nor addr/data is provided");
        }
        return String.join(",", arr);
    }



}
