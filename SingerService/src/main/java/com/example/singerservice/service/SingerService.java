package com.example.singerservice.service;

import com.example.singerservice.entity.Singer;
import com.example.singerservice.repository.SingerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SingerService {
    @Autowired
    private SingerRepository singerRepository;

    public Singer saveSinger(Singer singer) {
        log.info("inside saveSinger of Singer controller!");
        return  singerRepository.save(singer);
    }

    public Singer findSingerById(Long singerId) {
        log.info("inside findSingerById of Singer controller!");
        return singerRepository.findById(singerId).get();
    }
    public List<Singer> getAllSinger(){
        System.out.println(singerRepository.findAll() + "la");
        return singerRepository.findAll();
    }
    //Update
    public void updateSinger(Singer singer, Long id){
        Singer singerTemp = singerRepository.findById(id).orElse(null);
        singerTemp.setName(singer.getName());
        singerTemp.setAddress(singer.getAddress());
        singerTemp.setPhoneNumber(singer.getPhoneNumber());
        singerRepository.save(singerTemp);
    }

    //Detele
    public void deleteSinger(Long id){
        singerRepository.deleteById(id);
    }

}
