package com.ems.espupdate.service;

import com.ems.espupdate.persistence.entity.ESPData;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by sbn on 16.03.2016.
 */
@Stateless
public class PersistenceService {

    @PersistenceContext
    private EntityManager em;

    public ESPData findDataByMac(String staMac) {


        return null;
    }

    public void persist(ESPData data) {
        em.persist(data);
    }
}
