package com.trippy.back.repos;
import com.trippy.back.entities.Help;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpRepo extends JpaRepository <Help, Long> {


}
