package com.elf.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elf.app.models.ReportPictures;

public interface ReportPictureRepository extends JpaRepository<ReportPictures, Long> {
    <Optional> ReportPictures findByUuid(String uuid);
}
