package com.gautam.bank.entity.sequence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "code_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long is;

    @Column(name = "sequence_name", unique = true, nullable = false)
    private String sequenceName;

    @Column(name = "next_value", nullable = false)
    private Long nextValue;
}
