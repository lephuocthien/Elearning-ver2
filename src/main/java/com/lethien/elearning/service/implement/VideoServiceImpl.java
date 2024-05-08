/**
 * Dec 18, 2020
 * 6:27:16 PM
 *
 * @author LeThien
 */
package com.lethien.elearning.service.implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lethien.elearning.dto.VideoDto;
import com.lethien.elearning.entity.Video;
import com.lethien.elearning.repository.VideoRepository;
import com.lethien.elearning.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;

    /**
     * @param videoRepository
     */
    public VideoServiceImpl(VideoRepository videoRepository) {
        super();
        this.videoRepository = videoRepository;
    }

    @Override
    public List<VideoDto> getAll() {
        List<Video> videos = videoRepository.findAll();
        List<VideoDto> dtos = new ArrayList<VideoDto>();
        for (Video video : videos) {
            VideoDto dto = new VideoDto();
            dto.setId(video.getId());
            dto.setTitle(video.getTitle());
            dto.setUrl(video.getUrl());
            dto.setTimeCount(video.getTimeCount());
            dto.setCourseId(video.getCourseId());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public VideoDto getById(int id) {
        Video video = videoRepository.findById(id).orElse(null);
        VideoDto dto = new VideoDto();
        if (video != null) {
            dto.setId(video.getId());
            dto.setTitle(video.getTitle());
            dto.setUrl(video.getUrl());
            dto.setTimeCount(video.getTimeCount());
            dto.setCourseId(video.getCourseId());
        }
        return dto;
    }

    @Override
    public void save(VideoDto dto) {
        Video video = new Video();
        video.setTitle(dto.getTitle());
        video.setUrl(dto.getUrl());
        video.setTimeCount(dto.getTimeCount());
        video.setCourseId(dto.getCourseId());
        videoRepository.save(video);

    }

    @Override
    public void edit(VideoDto dto) {
        Video video = videoRepository.findById(dto.getId()).orElse(null);
        if (video != null) {
            video.setTitle(dto.getTitle());
            video.setUrl(dto.getUrl());
            video.setTimeCount(dto.getTimeCount());
            video.setCourseId(dto.getCourseId());
            videoRepository.save(video);
        }
    }

    @Override
    public void remove(int id) {
        videoRepository.deleteById(id);
    }

    @Override
    public List<VideoDto> getAllVideoByCourseId(int courseId) {
        return videoRepository.getAllVideoByCourseId(courseId);
    }

    @Override
    public Page<VideoDto> getVideoDtoPagingByCourseId(
            Pageable pageable,
            int courseId
    ) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Page<VideoDto> videoDtoPage;
        if (videoRepository.count() < startItem) {
            videoDtoPage = new PageImpl<VideoDto>(
                    Collections.emptyList(),
                    PageRequest.of(currentPage, pageSize),
                    videoRepository.count()
            );
        } else {
            videoDtoPage = videoRepository.getVideoDtoPagingByCourseId(
                    PageRequest.of(currentPage, pageSize),
                    courseId
            );
        }
        return videoDtoPage;
    }

    @Override
    public Page<VideoDto> getVideoDtoResultPagingByCourseId(
            Pageable pageable,
            int courseId,
            String key
    ) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Page<VideoDto> videoDtoPage;
        if (videoRepository.getVideoDtoResultCountByCourseId(courseId, key) < startItem) {
            videoDtoPage = new PageImpl<VideoDto>(
                    Collections.emptyList(),
                    PageRequest.of(currentPage, pageSize),
                    videoRepository.getVideoDtoResultCountByCourseId(courseId, key)
            );
        } else {
            videoDtoPage = videoRepository.getVideoDtoResultPagingByCourseId(
                    PageRequest.of(currentPage, pageSize),
                    courseId,
                    key
            );
        }
        return videoDtoPage;
    }
}
