package com.example.demo.business;

import com.example.demo.entity.EmpEntity;
import com.example.demo.repository.EmpStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmpService {

    @Autowired
    EmpStore store;

    @Transactional
    public EmpEntity save(EmpEntity emp) {
        store.save(emp);
        return emp;
    }

    @Transactional
    public int transfer(long fromEmp, long toEmp, double inc) {
        int n1 = store.changeData(fromEmp, -inc);
        if(inc==123.0){
            throw new RuntimeException("inc-123 is not allowed");
        }
        int n2 = store.changeData(toEmp, inc);
        int ret = (n1 * 10000 + n2) - 10000 ;
        return ret;
    }
}
