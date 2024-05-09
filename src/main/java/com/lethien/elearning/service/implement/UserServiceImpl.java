/**
 * Dec 18, 2020
 * 4:14:38 PM
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lethien.elearning.dto.UserDto;
import com.lethien.elearning.entity.User;
import com.lethien.elearning.repository.CourseRepository;
import com.lethien.elearning.repository.UserRepository;
import com.lethien.elearning.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * @param userRepository
     */
    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtos = new ArrayList<UserDto>();
        for (User user : users) {
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
            dto.setFullname(user.getFullname());
            dto.setPhone(user.getPhone());
            dto.setAddress(user.getAddress());
            dto.setAvatar(user.getAvatar());
            dto.setRoleId(user.getRoleId());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<UserDto> getAllUserDto() {
        return userRepository.findAllUserDto();
    }

    @Override
    public UserDto getUserDtoById(int id) {
        return userRepository.findUserDtoById(id);
    }

    @Override
    public UserDto getUserDtoByEmail(String email) {
        return userRepository.findUserDtoByEmail(email);
    }

    @Override
    public UserDto getById(int id) {
        UserDto dto = new UserDto();
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
            dto.setFullname(user.getFullname());
            dto.setPhone(user.getPhone());
            dto.setAddress(user.getAddress());
            dto.setAvatar(user.getAvatar());
            dto.setRoleId(user.getRoleId());
        }
        return dto;
    }

    @Override
    public void save(UserDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(
                new BCryptPasswordEncoder()
                        .encode(dto.getPassword()));
        user.setFullname(dto.getFullname());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setAvatar(dto.getAvatar());
        user.setRoleId(dto.getRoleId());
        userRepository.save(user);

    }

    @Override
    public void edit(UserDto dto) {
        User user = userRepository.findById(dto.getId()).orElse(null);
        if (user != null) {
            user.setEmail(dto.getEmail());
            if (!dto.getPassword().isEmpty())
                user.setPassword(
                        new BCryptPasswordEncoder()
                                .encode(dto.getPassword()));
            user.setFullname(dto.getFullname());
            user.setPhone(dto.getPhone());
            user.setAddress(dto.getAddress());
            if (dto.getAvatar() != null) {
                user.setAvatar(dto.getAvatar());
            }
            user.setRoleId(dto.getRoleId());
            userRepository.save(user);
        }
    }

    @Override
    public void remove(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> getUserPaging(
            int pageIndex,
            int pageSize
    ) {
        PageRequest paging = PageRequest.of(pageIndex, pageSize);
        return userRepository.findAll(paging);
    }

    @Override
    public Page<UserDto> getUserDtoPaging(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Page<UserDto> userDtoPage;
        if (userRepository.count() < startItem) {
            userDtoPage = new PageImpl<UserDto>(
                    Collections.emptyList(),
                    PageRequest.of(currentPage, pageSize),
                    userRepository.count());
        } else {
            userDtoPage = userRepository.getUserDtoPaging(
                    PageRequest.of(currentPage, pageSize)
            );
        }
        return userDtoPage;
    }

    @Override
    public Page<UserDto> getUserDtoResultPaging(Pageable pageable, String key) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Page<UserDto> userDtoPage;
        if (userRepository.getUserDtoResultCount(key) < startItem) {
            userDtoPage = new PageImpl<UserDto>(
                    Collections.emptyList(),
                    PageRequest.of(currentPage, pageSize),
                    userRepository.getUserDtoResultCount(key));
        } else {
            userDtoPage = userRepository.getUserDtoResultPaging(
                    PageRequest.of(currentPage, pageSize),
                    key);
        }
        return userDtoPage;
    }

    @Override
    public Page<UserDto> getUserDtoPagingByCourseId(Pageable pageable, int courseId) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Page<UserDto> userDtoPage;
        if (userRepository.getUserDtoCountByCourseId(courseId) < startItem) {
            userDtoPage = new PageImpl<UserDto>(
                    Collections.emptyList(),
                    PageRequest.of(currentPage, pageSize),
                    userRepository.getUserDtoCountByCourseId(courseId));
        } else {
            userDtoPage = userRepository.getUserDtoPagingByCourseId(
                    PageRequest.of(currentPage, pageSize),
                    courseId);
        }
        return userDtoPage;
    }

    @Override
    public Page<UserDto> getUserDtoPagingWithoutCourseId(Pageable pageable, int courseId) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Page<UserDto> userDtoPage;
        if (userRepository.getUserDtoCountWithoutCourseId(courseId) < startItem) {
            userDtoPage = new PageImpl<UserDto>(
                    Collections.emptyList(),
                    PageRequest.of(currentPage, pageSize),
                    userRepository.getUserDtoCountWithoutCourseId(courseId)
            );
        } else {
            userDtoPage = userRepository.getUserDtoPagingWithoutCourseId(
                    PageRequest.of(currentPage, pageSize),
                    courseId);
        }
        return userDtoPage;
    }

    @Override
    public Page<UserDto> getUserDtoPagingWithoutCourseIdByKey(Pageable pageable, int courseId, String key) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Page<UserDto> userDtoPage;
        if (userRepository.getUserDtoCountWithoutCourseIdByKey(courseId, key) < startItem) {
            userDtoPage = new PageImpl<UserDto>(
                    Collections.emptyList(),
                    PageRequest.of(currentPage, pageSize),
                    userRepository.getUserDtoCountWithoutCourseIdByKey(courseId, key)
            );
        } else {
            userDtoPage = userRepository.getUserDtoPagingWithoutCourseIdByKey(
                    PageRequest.of(currentPage, pageSize),
                    courseId,
                    key);
        }
        return userDtoPage;
    }

    @Override
    public List<UserDto> getAllUserDtoOfCourseByTeacher(int courseId) {
        return userRepository.findUserDtoOfCourseByTeacher(courseId);
    }
}
