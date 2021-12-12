package com.example.singerservice.controller;

import com.example.singerservice.entity.Singer;
import com.example.singerservice.service.SingerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/singers")
@Slf4j
public class SingerController {

    @Autowired
    private SingerService singerService;
    @PostMapping("/")
    public Singer saveSinger(@RequestBody Singer singer){
        log.info("inside saveSinger method of Singer controller!");
        return singerService.saveSinger(singer);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "singerById", key = "#singerId")
    public Singer findSingerById(@PathVariable("id") Long singerId){
        log.info("inside findSingerById method of Singer controller!");
        return singerService.findSingerById(singerId);
    }
    //Update
    @PutMapping("/{id}")
    public Object updateSingerById(@RequestBody Singer singer, @PathVariable("id") Long id){
        try{
            singerService.updateSinger(singer, id);
            return new ResponseEntity<String>("Update thành công", HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<String>("Error update!",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("")
    @Cacheable(value = "allsinger")
    public Object getAllSinger(){
        try {
            return new ResponseEntity<List<Singer>>(singerService.getAllSinger(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>("Error get danh sách Singer!",HttpStatus.BAD_REQUEST);
        }
    }
    //Delete
    @DeleteMapping("/{id}")
    public Object deleteSingerById(@PathVariable("id") Long id){
        try {
            singerService.deleteSinger(id);
            return new ResponseEntity<String>("Delete thành công!", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>("Error delete!",HttpStatus.BAD_REQUEST);
        }
    }


}
