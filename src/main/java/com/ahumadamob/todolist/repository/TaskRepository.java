package com.ahumadamob.todolist.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ahumadamob.todolist.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByCompletedFalse();
	List<Task> findByCompletedTrue();
	List<Task> findByProjectId(Integer projectId);
	List<Task> findByCompletedFalseAndProjectId(Integer id);
	List<Task> findByCompletedTrueAndProjectId(Integer id);

}
