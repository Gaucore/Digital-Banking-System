package com.gautam.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.gautam.bank.entity.sequence.CodeSequence;

import jakarta.persistence.LockModeType;

@Repository
public interface CodeSequenceRepository extends JpaRepository<CodeSequence, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<CodeSequence> findBySequenceName(String sequenceName);
}
