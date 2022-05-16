package com.example.qlkh.service;

import com.example.qlkh.model.Custormer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServiceimpl implements CustomerService {
    private static Map<Integer, Custormer> custormers;
    static {
        custormers = new HashMap<>();
        custormers.put(1,new Custormer(1,"Hien","Hien@codegym.vn","ThaiBinh"));
        custormers.put(2,new Custormer(2,"Long","Long@codegym.vn","HaNoi"));
        custormers.put(3,new Custormer(3,"Alex","alex@codegym.vn","SaiGon"));
        custormers.put(4,new Custormer(4,"Adam","adam@codegym.vn","Beijin"));
        custormers.put(5,new Custormer(5,"Sophia","Sophia@codegym.vn","Miami"));
        custormers.put(6,new Custormer(6,"Rose","Rose@codegym.vn","NewYork"));
    }
    @Override
    public List<Custormer> findAll() {
        return new ArrayList<>(custormers.values());
    }

    @Override
    public void save(Custormer custormer) {
        custormers.put(custormer.getId(), custormer);
    }

    @Override
    public Custormer findById(int id) {
        return custormers.get(id);
    }

    @Override
    public void update(int id, Custormer custormer) {
        custormers.put(id, custormer);
    }

    @Override
    public void remove(int id) {
        custormers.remove(id);
    }
}
