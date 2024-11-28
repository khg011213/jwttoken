package com.example._6.Service;

import com.example._6.DTO.TestDTO;
import com.example._6.Entity.TestEntitly;
import com.example._6.Repository.TestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public List<TestDTO> findAll() {
       List<TestEntitly> testEntitlyList= testRepository.findAll();

        return testEntitlyList.stream()
                .map(TestDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void posting(TestDTO testDTO) {
        TestEntitly testEntitly = TestEntitly
                .builder()
                .id(testDTO.getId())
                .title(testDTO.getTitle())
                .content(testDTO.getContent())
                .build();

        testRepository.save(TestEntitly
                .builder()
                .id(testDTO.getId())
                .title(testDTO.getTitle())
                .content(testDTO.getContent())
                .build());
    }


    public List<TestDTO> findById(int id) {
        Optional<TestEntitly> testEntitly = testRepository.findById(id);

        if(testEntitly.isPresent()){
            TestEntitly preEntity = testEntitly.get();
            return List.of(TestDTO.fromEntity(preEntity));
        }else{
            return Collections.emptyList();
        }
    }

    public void delete(int id) {
        Optional<TestEntitly> testEntityOptional = testRepository.findById(id);

        if (testEntityOptional.isPresent()) {
            TestEntitly testEntity = testEntityOptional.get(); // 엔티티 가져오기

            testRepository.delete(testEntity); // 엔티티 삭제
            TestDTO testDTO = TestDTO.fromEntity(testEntity); // DTO로 변환
            // 필요 시 testDTO를 반환하거나 다른 작업을 할 수 있습니다.
        } else {
            // 엔티티가 존재하지 않는 경우에 대한 처리 (예: 예외 던지기)
            throw new EntityNotFoundException("Entity with id " + id + " not found.");
        }
    }
}
