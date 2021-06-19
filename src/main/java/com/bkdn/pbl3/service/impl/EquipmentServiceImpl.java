package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.repository.EquipmentRepository;
import com.bkdn.pbl3.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    EquipmentRepository equipmentRepository;

    @Override
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment getById(String s) {
        return equipmentRepository.getById(s);
    }

    @Override
    public Optional<Equipment> findById(String s) {
        return equipmentRepository.findById(s);
    }

    @Override
    public <S extends Equipment> S save(S s) {
        return equipmentRepository.save(s);
    }

    @Override
    public void deleteById(String s) {
        equipmentRepository.deleteById(s);
    }
}