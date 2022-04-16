package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.admin.AddUserRequest;
import com.doantotnghiep.demo.dto.response.admin.UserDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.UserListResponse;
import com.doantotnghiep.demo.dto.response.user.UserPrincipal;
import com.doantotnghiep.demo.entity.User;
import com.doantotnghiep.demo.mapper.UserMapper;
import com.doantotnghiep.demo.repository.UserRepository;
import com.doantotnghiep.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService , UserDetailsService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;

    //dang ki user cho admin
    @Override
    public void addUser(AddUserRequest addUserRequest){

        User userUserName = userRepository.findUserByusername(addUserRequest.getUsername());
        if(userUserName != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Đã có username này trong database");
        }

        User user = User.builder()
                .name(addUserRequest.getName())
                .roles(addUserRequest.getRoles())
                .username(addUserRequest.getUsername())
                .password(passwordEncoder.encode(addUserRequest.getPassword()))
                .address(addUserRequest.getAddress())
                .age(addUserRequest.getAge())
                .email(addUserRequest.getEmail())
                .gender(addUserRequest.getGender())
                .phone(addUserRequest.getPhone())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .isDeleted(false).build();

        userRepository.save(user);

    }

    //sua user cho admin
    @Override
    public void modifiedUser(AddUserRequest addUserRequest){

        User user = userRepository.getOne(addUserRequest.getId());
        if(user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy user theo id truyền vào");
        }

        user.setId(user.getId());

        if(addUserRequest.getName() != null){
            user.setName(addUserRequest.getName());
        }

        if(addUserRequest.getUsername() != null){
            user.setUsername(addUserRequest.getUsername());
        }

        if(addUserRequest.getAge() != null){
            user.setAge(addUserRequest.getAge());
        }

        if(addUserRequest.getRoles() != null){
            user.setRoles(addUserRequest.getRoles());
        }

        if(addUserRequest.getAddress() != null){
            user.setAddress(addUserRequest.getAddress());
        }

        if(addUserRequest.getGender() != null){
            user.setGender(addUserRequest.getGender());
        }

        if(addUserRequest.getPhone() != null){
            user.setPhone(addUserRequest.getPhone());
        }

        if(addUserRequest.getEmail() != null){
            user.setEmail(addUserRequest.getEmail());
        }

        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        userRepository.save(user);

    }

    //lay chi tiet 1 user
    @Override
    public UserDetailResponse getUserDetail(Long id){

        User user = userRepository.getOne(id);

        UserDetailResponse userRequest = new UserDetailResponse();
        userRequest.setId(id);
        userRequest.setName(user.getName());
        userRequest.setUserName(user.getUsername());
        userRequest.setAge(user.getAge());
        userRequest.setRoles(user.getRoles());
        userRequest.setAddress(user.getAddress());
        userRequest.setGender(user.getGender());
        userRequest.setPhone(user.getPhone());
        userRequest.setEmail(user.getEmail());

        return userRequest;
    }



    //danh sach user cho admin
    @Override
    public UserListResponse getListUser(String search, Integer sortBy, Pageable pageable){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        List<Predicate> listPredicate = new ArrayList<>();

        if (search != null) {
            listPredicate.add(cb.or(cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("username")),"%" + search.toLowerCase() + "%")));
        }

        Path<Object> sort = null;
        Order order = null;

        if (sortBy != null) {
            switch (sortBy) {
                case 0:
                    sort = root.get("updatedAt");
                    order = cb.desc(sort);
                    break;
                case 1:
                    sort = root.get("updatedAt");
                    order = cb.asc(sort);
            }
        }

        Predicate[] finalPredicate = new Predicate[listPredicate.size()];
        listPredicate.toArray(finalPredicate);

        TypedQuery<User> query = em.createQuery(cq.select(root).where(cb.and(finalPredicate)).orderBy(order));
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        Long count = em.createQuery(countQuery.select(cb.count(countRoot)).where(cb.and(finalPredicate))).getSingleResult();

        List<UserDetailResponse> responseDTOS = new ArrayList<>();
        query.getResultList().forEach(product -> responseDTOS.add(userMapper.toListDTO(product)));

        UserListResponse productListResponse = new UserListResponse();
        productListResponse.setList(responseDTOS);
        productListResponse.setTotal(count);

        return productListResponse;
    }

    @Override
    public UserDetailResponse modifiedPassword(String username, String password, String newPassword){

        User user = userRepository.findUserByusername(username);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Sai mật khẩu!");
        }

        if (newPassword.length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password mới không đủ 6 kí tự!");
        }

        user.setPassword(passwordEncoder.encode(password));

        UserDetailResponse userRequest = new UserDetailResponse();
        userRequest.setName(user.getName());
        userRequest.setUserName(user.getUsername());

        return userRequest;
    }

    @Override
    public void deleteUser(Long id){
        User user = userRepository.getOne(id);
        if(user != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy user với id truyền vào");
        }else {
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,NullPointerException {
        User user = userRepository.findUserByusername(username.trim());

        if (user == null) {
            throw new UsernameNotFoundException("Username,Password not found");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        UserPrincipal userPrincipal = new UserPrincipal(user.getPhone(), user.getPassword(), true, true,
                true, true, authorities);
        userPrincipal.setId(user.getId());
        userPrincipal.setName(user.getName());
        userPrincipal.setRoles(user.getRoles());

        return userPrincipal;
    }


}
