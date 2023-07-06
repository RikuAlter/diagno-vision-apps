package com.diagno.vision.webapps.diagnovision.repository;

import com.diagno.vision.webapps.diagnovision.dto.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageData, Integer> {

    public List<ImageData> findByUser_Id(Integer user_id);
}
