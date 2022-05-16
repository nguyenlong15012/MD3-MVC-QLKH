package com.example.qlkh.service;

import com.example.qlkh.model.Custormer;

import java.util.List;

public interface CustomerService {
    List<Custormer> findAll();

    void save(Custormer custormer);

    Custormer findById(int id);

    void update(int id, Custormer custormer);

    void remove(int id);
}
