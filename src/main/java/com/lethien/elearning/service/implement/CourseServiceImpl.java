/**
 * Dec 18, 2020
 * 7:23:52 PM
 *
 * @author LeThien
 */
package com.lethien.elearning.service.implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lethien.elearning.dto.VideoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lethien.elearning.dto.CourseDto;
import com.lethien.elearning.entity.Course;
import com.lethien.elearning.repository.CategoryRepository;
import com.lethien.elearning.repository.CourseRepository;
import com.lethien.elearning.repository.TargetRepository;
import com.lethien.elearning.repository.VideoRepository;
import com.lethien.elearning.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;
    private VideoRepository videoRepository;
    private TargetRepository targetRepository;
    private CategoryRepository categoryRepository;


    /**
     * @param courseRepository
     * @param videoRepository
     * @param targetRepository
     * @param categoryRepository
     */
    public CourseServiceImpl(CourseRepository courseRepository, VideoRepository videoRepository,
                             TargetRepository targetRepository, CategoryRepository categoryRepository) {
        super();
        this.courseRepository = courseRepository;
        this.videoRepository = videoRepository;
        this.targetRepository = targetRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CourseDto> getAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDto> dtos = new ArrayList<CourseDto>();
        for (Course course : courses) {
            CourseDto dto = new CourseDto();
            dto.setId(course.getId());
            dto.setTitle(course.getTitle());
            dto.setImage(course.getImage());
            dto.setLeturesCount(course.getLeturesCount());
            dto.setHourCount(course.getHourCount());
            dto.setViewCount(course.getViewCount());
            dto.setPrice(course.getPrice());
            dto.setDiscount(course.getDiscount());
            dto.setPromotionPrice(course.getPromotionPrice());
            dto.setDescription(course.getDescription());
            dto.setContent(course.getContent());
            dto.setCategoryId(course.getCategoryId());
            dto.setLastUpdate(course.getLastUpdate());
            dto.setCategoryTitle(
                    categoryRepository
                            .findById(course
                                    .getCategoryId())
                            .get()
                            .getTitle());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<CourseDto> getAllCourseDto() {
        List<CourseDto> dtos = courseRepository.findAllDtoCourse();
        for (int i = 0; i < dtos.size(); i++) {
            dtos.get(i).setVideos(videoRepository.getAllVideoByCourseId(dtos.get(i).getId()));
            dtos.get(i).setTargets(targetRepository.getAllTargetByCourseId(dtos.get(i).getId()));
        }
        return dtos;
    }

    @Override
    public List<CourseDto> getAllCourseDtoByUserId(int userId) {
        List<CourseDto> dtos = courseRepository.findAllDtoCourseByUserId(userId);
        for (int i = 0; i < dtos.size(); i++) {
            dtos.get(i).setVideos(videoRepository.getAllVideoByCourseId(dtos.get(i).getId()));
            dtos.get(i).setTargets(targetRepository.getAllTargetByCourseId(dtos.get(i).getId()));
        }
        return dtos;
    }

    @Override
    public List<CourseDto> getAllCourseDtoByTitle(String key) {
        List<CourseDto> dtos = courseRepository.findAllDtoCourseByTitle(key);
        for (int i = 0; i < dtos.size(); i++) {
            dtos.get(i).setVideos(videoRepository.getAllVideoByCourseId(dtos.get(i).getId()));
            dtos.get(i).setTargets(targetRepository.getAllTargetByCourseId(dtos.get(i).getId()));
        }
        return dtos;
    }

    @Override
    public CourseDto getById(int id) {
        Course course = courseRepository.findById(id).get();
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setImage(course.getImage());
        dto.setLeturesCount(course.getLeturesCount());
        dto.setHourCount(course.getHourCount());
        dto.setViewCount(course.getViewCount());
        dto.setPrice(course.getPrice());
        dto.setDiscount(course.getDiscount());
        dto.setPromotionPrice(course.getPromotionPrice());
        dto.setDescription(course.getDescription());
        dto.setContent(course.getContent());
        dto.setCategoryId(course.getCategoryId());
        dto.setLastUpdate(course.getLastUpdate());
        return dto;
    }

    @Override
    public CourseDto getDtoById(int id) {
        CourseDto dto = courseRepository.findDtoCourseById(id);
        dto.setVideos(videoRepository.getAllVideoByCourseId(dto.getId()));
        dto.setTargets(targetRepository.getAllTargetByCourseId(dto.getId()));
        return dto;
    }

    @Override
    public void save(CourseDto dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setImage(dto.getImage());
        course.setLeturesCount(dto.getLeturesCount());
        course.setHourCount(dto.getHourCount());
        course.setViewCount(dto.getViewCount());
        course.setPrice(dto.getPrice());
        course.setDiscount(dto.getDiscount());
        course.setPromotionPrice(dto.getPromotionPrice());
        course.setDescription(dto.getDescription());
        course.setContent(dto.getContent());
        course.setCategoryId(dto.getCategoryId());
        course.setLastUpdate(dto.getLastUpdate());
        courseRepository.save(course);
    }

    @Override
    public Integer saveGetBackId(CourseDto dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setImage(dto.getImage());
        course.setLeturesCount(dto.getLeturesCount());
        course.setHourCount(dto.getHourCount());
        course.setViewCount(dto.getViewCount());
        course.setPrice(dto.getPrice());
        course.setDiscount(dto.getDiscount());
        course.setPromotionPrice(dto.getPromotionPrice());
        course.setDescription(dto.getDescription());
        course.setContent(dto.getContent());
        course.setCategoryId(dto.getCategoryId());
        course.setLastUpdate(dto.getLastUpdate());
        return courseRepository.save(course).getId();
    }

    @Override
    public void edit(CourseDto dto) {
        Course course = courseRepository.findById(dto.getId()).get();
        if (course != null) {
            course.setTitle(dto.getTitle());
            course.setImage(dto.getImage());
            List<VideoDto> videos = videoRepository.getAllVideoByCourseId(dto.getId());
            int hourCount = 0;
            for (VideoDto video : videos) {
                hourCount += video.getTimeCount();
            }
            //course.setLeturesCount(dto.getLeturesCount());
            course.setLeturesCount(videos.size());
            course.setHourCount(hourCount);
            course.setViewCount(dto.getViewCount());
            course.setPrice(dto.getPrice());
            course.setDiscount(dto.getDiscount());
            course.setPromotionPrice(dto.getPromotionPrice());
            course.setDescription(dto.getDescription());
            course.setContent(dto.getContent());
            course.setCategoryId(dto.getCategoryId());
            course.setLastUpdate(dto.getLastUpdate());
            courseRepository.save(course);
        }

    }

    @Override
    public void remove(int id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Page<CourseDto> getCourseDtoPaging(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Page<CourseDto> courseDtoPage;
        if (courseRepository.count() < startItem) {
            courseDtoPage = new PageImpl<CourseDto>(Collections.emptyList(), PageRequest.of(currentPage, pageSize), courseRepository.count());
        } else {
            courseDtoPage = courseRepository.getCourseDtoPaging(PageRequest.of(currentPage, pageSize));
        }
        return courseDtoPage;
    }
}
