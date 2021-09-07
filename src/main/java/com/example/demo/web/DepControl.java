package com.example.demo.web;

import com.example.demo.MyUtil;
import com.example.demo.entity.DepEntity;
import com.example.demo.repository.DepStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController()
@RequestMapping("/api")
public class DepControl {
    @Autowired
    DepStore depStore;

    @RequestMapping("/deps/{id}")
    public DepEntity get(@PathVariable("id") Long id){
        Optional<DepEntity> dep = depStore.findWithChildById(id);
        MyUtil.checkExist(dep, "dep not found");
        return dep.get();
    }




}
