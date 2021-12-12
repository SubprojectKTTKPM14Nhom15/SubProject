package com.example.songservice.controller;

import com.example.songservice.vo.ResponseTemplateVO;
import com.example.songservice.entity.Song;
import com.example.songservice.service.SongService;
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
@RequestMapping("/rest/songs")
@Slf4j
public class SongController {

    private static final String SONG_SERVICE ="songService" ;
    @Autowired
    private SongService songService;

    @PostMapping("/")
    public Song saveSong(@RequestBody Song song){
        log.info("inside saveSong in SongController");
        return songService.saveSong(song);
    }
    @GetMapping("")
    @Cacheable(value = "allsong")
    public Object getAllSong(){
        try {
            return new ResponseEntity<List<Song>>(songService.getAllSong(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>("Error get danh sách Song!",HttpStatus.BAD_REQUEST);
        }
    }
    @Cacheable(value = "songById", key = "#songId")
    @GetMapping("/{id}")
    public ResponseTemplateVO getSongWithSinger(@PathVariable("id") Long songId){
        log.info("inside getSongWithFaculty in songController");
        return songService.getSongWithSinger(songId);
    }

    //Update
//    @CachePut(value = "allsong",key = "#songupdateId")
    @PutMapping("/{id}")
    public Object updateSongById(@RequestBody Song song, @PathVariable("id") Long id){
        try{
            songService.updateSong(song, id);
            return new ResponseEntity<String>("Update thành công",HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<String>("Error update!",HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
 //   @CacheEvict(value = "allsong", allEntries = true)
    @DeleteMapping("/{id}")
    public Object deleteSongById(@PathVariable("id") Long id){
        try {
            songService.deleteSong(id);
            return new ResponseEntity<String>("Delete thành công!", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>("Error delete!",HttpStatus.BAD_REQUEST);
        }
    }
}