package com.example._6.Controller;


import com.example._6.DTO.TestDTO;
import com.example._6.Service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final TestService testService;

    @GetMapping("/findall")
    public ResponseEntity<List<TestDTO>> findAll(){

        return new ResponseEntity<>(testService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TestDTO>> findById(@PathVariable int id){
        List<TestDTO> testDTOS = testService.findById(id);
        return new ResponseEntity<>(testDTOS,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<TestDTO> posting(@RequestBody TestDTO testDTO){
        testService.posting(testDTO);
        return new ResponseEntity<>(testDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TestDTO> delete(@PathVariable int id){
        testService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
