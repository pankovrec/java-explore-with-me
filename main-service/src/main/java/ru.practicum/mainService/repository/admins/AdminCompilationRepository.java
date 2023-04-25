package ru.practicum.mainService.repository.admins;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.mainService.model.Compilation;

@Repository
public interface AdminCompilationRepository extends JpaRepository<Compilation, Long> {
}
