package com.example._6.DTO;

import com.example._6.Entity.TestEntitly;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestDTO {
    private int id;
    private String title;
    private String content;

    public static TestDTO fromEntity(TestEntitly testEntitly) {
        return TestDTO.builder()
                .id(testEntitly.getId())
                .title(testEntitly.getTitle())
                .content(testEntitly.getContent())
                .build();
    }
}
