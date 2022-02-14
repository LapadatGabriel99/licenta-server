package com.proiect.licenta.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "ScoredTest")
@Table(name = "scored_test")
@Getter
@Setter
@NoArgsConstructor
public class ScoredTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scored_test_id")
    private Long id;

    @Column(name = "name", unique = true)
    @NotBlank(message = "Name can't be empty!")
    private String name;

    @Transient
    private String categoryName;

    @Column(name = "was_test_modified")
    private boolean wasTestModified;

    @Column(name = "was_test_taken")
    private boolean wasTestTaken;

    @Column(name = "num_of_correct_answers")
    private int numOfCorrectAnswers;

    @Column(name = "num_of_wrong_answers")
    private int numOfWrongAnswers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
}
