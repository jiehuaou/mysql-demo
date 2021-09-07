package com.example.demo.entity;

import com.example.demo.entity.EmpEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="dep", schema = "${db.schema}")
public class DepEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;
    public Long getVersion() {
        return version;
    }
    public void setVersion(Long version) {
        this.version = version;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public Set<EmpEntity> getEmpList() {
        return empList;
    }

    public void setEmpList(Set<EmpEntity> empList) {
        this.empList = empList;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    private String depName;
    private String leader;

    @OneToMany(mappedBy = "depId", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("dep")
    private Set<EmpEntity> empList = new HashSet<>();
}
