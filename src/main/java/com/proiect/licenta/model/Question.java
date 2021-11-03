package com.proiect.licenta.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Question")
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id")
    private Long id;

    @NotBlank(message = "Question Text cannot be blank")
    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "has_multiple_answers")
    private boolean hasMultipleAnswers;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Answer> answersList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
}
