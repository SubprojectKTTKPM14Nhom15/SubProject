
package com.example.songservice.service;

import com.example.songservice.vo.ResponseTemplateVO;
import com.example.songservice.vo.Singer;
import com.example.songservice.entity.Song;
import com.example.songservice.repository.SongRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class SongService {

    @Autowired
    private SongRepository songRepository;
    @Autowired
    private RestTemplate restTemplate;
    public List<Song> getAllSong() {
        return songRepository.findAll();

    }
    public Song saveSong(Song song) {
        log.info("inside saveSong in songService");
        return songRepository.save(song);
    }
    @Retry(name="basic")
    public ResponseTemplateVO getSongWithSinger(Long songId) {
        log.info("inside getStudentWithFaculty in studentRepository");
        ResponseTemplateVO vo=new ResponseTemplateVO();
        Song song= songRepository.findById(songId).get();
        Singer singer  =restTemplate.getForObject("http://SINGER-SERVICE/singers/" + song.getSingerId(), Singer.class);
        vo.setSinger(singer);
        vo.setSong(song);
        return  vo;
    }



    public ResponseEntity<String> orderFallback(Exception e){
        return
                new ResponseEntity<String>("Item service is down!", HttpStatus.OK);
    }

    //Update
    public void updateSong(Song song, Long id){
        Song songTemp = songRepository.findById(id).orElse(null);
        songTemp.setName(song.getName());
        songTemp.setCategory(song.getCategory());
        songTemp.setDateCreate(song.getDateCreate());
        songTemp.setLyric(song.getLyric());
        songTemp.setSingerId(song.getSingerId());
        songRepository.save(songTemp);
    }

    //Detele
    public void deleteSong(Long id){
        songRepository.deleteById(id);
    }



}
