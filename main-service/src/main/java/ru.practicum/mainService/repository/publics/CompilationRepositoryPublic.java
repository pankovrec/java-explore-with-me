package ru.practicum.mainService.repository.publics;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.Compilation;

import java.util.List;

@Repository
public interface CompilationRepositoryPublic extends JpaRepository<Compilation, Long> {
    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageRequest);
}
